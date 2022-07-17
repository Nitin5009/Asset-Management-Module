package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class ProductAssignmentPage extends WebBasePage {

    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    DeployProductPage deployProductPage;
    String issuedToNameToBeSearch = "";
    String issuedToNameByBeSearch = "";
    String productNameToBeSearch = "";
    String uniqueNameToShare = "";
    String employeeNameToShare = "";
    String commentToCheckCommentButton = "";
    Map<String, String> headerAndListValue = new HashMap<>();

    public ProductAssignmentPage(WebDriver driver) {
        super(driver, "Product Assignment Page");
        this.deployProductPage = new DeployProductPage(driver);
        this.driver = driver;
    }

    public void navigateToProductAssignmentPage() {
        WebElement productAssignmentMenu = findElementPresence(By.xpath("//li[@class='menuitem']/a[text()='Product Assignment']"), 20);
        if (productAssignmentMenu != null) {
            scrollToWebelement(By.xpath("//li[@class='menuitem']/a[text()='Product Assignment']"), "Product Assignment Menu");
            click(By.xpath("//li[@class='menuitem']/a[text()='Product Assignment']"), "Product Assignment menu", 20);
        } else {
            deployProductPage.clickFullMenuDropDown();
            deployProductPage.clickAssetManagement();
            navigateToProductAssignmentPage();
        }
    }

    public void clickCheckBoxOfProduct() {
        waitForLoad(20);
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 180);
        click(By.xpath("//table[@id='example']//tbody//td[normalize-space(text())='" + productNamesToAssign.get(0) + "']/../td[1]/div"), "Check box of product", 20);
    }

    public void clickRequestForReturnOption() {
        click(By.xpath("//div[contains(@class,'theme-primary')]//a[@data-original-title='Request For Return']"), "Request for return option", 20);
    }

    public void clickRequestForReturnUnderProduct() {
        click(By.xpath("//td[normalize-space(text())='" + productNamesToAssign.get(0) + "']//parent::tr//a[@data-original-title='Request For Return']"), productNamesToAssign.get(0) + " Request For Return", 20);
    }

    public void enterRequestForReturnComment() {
        enter(By.cssSelector("textarea#txtComment"), "RequestForReturn", "Comment", 20);
    }

    public void saveRequestForReturn() {
        click(By.xpath("//div[@id='dvReturnStatus']//a[contains(@class,'btn btn-success')]"), "Request for return save", 20);
    }

    public void handleRequestSentSuccess() {
        WebElement requestSuccess = findElementVisibility(By.xpath("//span[text()='Request for Return saved successfully']"), 30);
        if (requestSuccess != null) {
            click(By.cssSelector("button#closenotifymessage"), "Request sent success close", 20);
        }
    }

    public void verifyTableHeaders() {
        int iteration = 0;
        int headerNameListNumber = 0;
        List<WebElement> tableHeaderLocators = findMultipleElement(By.xpath("//table[@id='example']//th/span"), 30);
        for (WebElement tableHeaderLocator : tableHeaderLocators) {
            String actualHeaderName = tableHeaderLocator.getText().trim();
            String[] expectedHeaderNames = {"Product Name", "Product Code", "Unique Name / Number of Products", "Location Name",
                    "Issued To", "Issued By", "Assigned From", "Assigned Till", "Return Date", "Actions"};
            for (int i = 0; i < expectedHeaderNames.length; i++) {
                iteration++;
                if (actualHeaderName.equalsIgnoreCase(expectedHeaderNames[i])) {
                    getTest().log(LogStatus.PASS, expectedHeaderNames[headerNameListNumber] + " header name is displayed as expected in the product assignment page table");
                    logger.info(expectedHeaderNames[headerNameListNumber] + " header name is displayed as expected in the product assignment page table");
                    iteration = 0;
                    break;
                } else if (iteration == expectedHeaderNames.length) {
                    getTest().log(LogStatus.FAIL, expectedHeaderNames[i] + " header name is not displayed in the product assignment page table");
                    logger.info(expectedHeaderNames[i] + " header name is not displayed in the product assignment page table");
                    takeScreenshot("HeaderName");
                }
            }
            headerNameListNumber++;
        }
    }

    public void clickHeaderName(String header) {
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 180);
        click(By.cssSelector("span#" + header), header + " header", 20);
    }

    public void verifyProductAssignmentHeaderSorting(String headerName, String order) {
        String header = (headerName.equalsIgnoreCase("Product Name")) ? "NAME" : headerName.equalsIgnoreCase("Product Code") ? "ITEMCODE" : headerName.equalsIgnoreCase("Unique Name") ? "UNIQUENAME" :
                headerName.equalsIgnoreCase("Location Name") ? "LOCATIONNAME" : headerName.equalsIgnoreCase("Issued To") ? "ISSUETONAME" : headerName.equalsIgnoreCase("Issued By") ? "ISSUEBYNAME" :
                        headerName.equalsIgnoreCase("Assigned From") ? "ISSUEDATE" : headerName.equalsIgnoreCase("Assigned Till") ? "ASSIGNEDTILL" : headerName.equalsIgnoreCase("Return Date") ? "RETURNDATE" : "";
        String headerNumber = (headerName.equalsIgnoreCase("Product Name")) ? "2" : headerName.equalsIgnoreCase("Product Code") ? "3" : headerName.equalsIgnoreCase("Unique Name") ? "4" :
                headerName.equalsIgnoreCase("Location Name") ? "5" : headerName.equalsIgnoreCase("Issued To") ? "6" : headerName.equalsIgnoreCase("Issue By") ? "7" :
                        headerName.equalsIgnoreCase("Assigned From") ? "8" : headerName.equalsIgnoreCase("Assigned Till") ? "9" : headerName.equalsIgnoreCase("Return Date") ? "10" : "";
        clickHeaderName(header);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[" + headerNumber + "]"), 30);
        for (WebElement element : rowElements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[" + headerNumber + "]"), 30);
                for (WebElement element : rowElements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equalsIgnoreCase("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "The " + headerName + " column is sorted in ascending Order when click the " + headerName + " header for one time");
                logger.info("The " + headerName + " column is sorted in ascending Order when click the " + headerName + " header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "The " + headerName + " column is not sorted in ascending Order when click the " + headerName + " header for one time");
                logger.info("The " + headerName + " column is not sorted in ascending Order when click the " + headerName + " header for one time");
                takeScreenshot(headerName + "sort");
            }
        }
        if (order.equalsIgnoreCase("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "The " + headerName + " column is sorted in descending Order when click the " + headerName + " header for two time");
                logger.info("The " + headerName + " column is sorted in descending Order when click the " + headerName + " header for two time");
            } else {
                getTest().log(LogStatus.FAIL, "The " + headerName + " column is not sorted in descending Order when click the " + headerName + " header for two time");
                logger.info("The " + headerName + " column is not sorted in descending Order when click the " + headerName + " header for two time");
                takeScreenshot(headerName + "Sort");
            }
        }
    }

    public void expandIssuedToField() {
        click(By.xpath("//span[text()='Issued To ']"), "Issue To field", 20);
    }

    public void expandIssuedByField() {
        click(By.xpath("//span[text()='Issued By']"), "Issue To field", 20);
    }

    public void expandProductNameField() {
        click(By.xpath("//span[text()='Product Name ']"), "Issue To field", 20);
    }

    public void expandShowByField() {
        click(By.xpath("//span[text()='Show By']"), "Show By field", 20);
    }

    public void selectReturnOverDueOption() {
        click(By.xpath("//label[text()='Return Overdue']"), "Return Over due", 20);
    }

    public void enterIssuedToUserName() {
        issuedToNameToBeSearch = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[6]"), 20);
        enter(By.xpath("//input[@id='search']"), issuedToNameToBeSearch, "Issued To Search field", 20);
    }

    public void enterIssuedByUserName() {
        issuedToNameByBeSearch = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[7]"), 20);
        enter(By.xpath("//span[text()='Issued By']/ancestor::div[@class='card']//input[contains(@class,'searchcustomfilter')]"), issuedToNameByBeSearch, "Issued By Search field", 20);
        click(By.xpath("//input[@name='issuedBy']/parent::div/label[contains(text(),'" + issuedToNameByBeSearch + "')]"), issuedToNameByBeSearch, 20);
    }

    public void enterProductName() {
        productNameToBeSearch = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[7]"), 20);
        enter(By.xpath("//input[@name='searchItemCode']"), productNameToBeSearch, "Product Name search Field", 20);
    }

    public void clickSearchButton() {
        click(By.cssSelector("a#Go"), "Search Button", 30);
    }

    public void verifySearchedResult(String columnName) {
        String columnNumber = "";
        String expectedName = "";
        if (columnName.equalsIgnoreCase("Issued To")) {
            columnNumber = "6";
            expectedName = issuedToNameToBeSearch;
        } else if (columnName.equalsIgnoreCase("Issued By")) {
            columnNumber = "7";
            expectedName = issuedToNameByBeSearch;
        } else if (columnName.equalsIgnoreCase("Product Name")) {
            columnNumber = "2";
            expectedName = productNameToBeSearch;
        }
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> searchedResult = new ArrayList<>();
        List<WebElement> rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[" + columnNumber + "]"), 30);
        for (WebElement element : rowElements) {
            if (element.getText().trim().equalsIgnoreCase(expectedName)) {
                searchedResult.add("True");
            } else {
                searchedResult.add("False");
            }
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[" + columnNumber + "]"), 30);
                for (WebElement element : rowElements) {
                    if (element.getText().trim().equalsIgnoreCase(expectedName)) {
                        searchedResult.add("True");
                    } else {
                        searchedResult.add("False");
                    }
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (!searchedResult.contains("False") && searchedResult.size() != 0) {
            getTest().log(LogStatus.PASS, "The search result for " + columnName + " field search provide the exact result for the user name - " + issuedToNameToBeSearch);
            logger.info("The search result for " + columnName + " field search provide the exact result for the user name - " + issuedToNameToBeSearch);
        } else {
            getTest().log(LogStatus.FAIL, "The search result for " + columnName + " field search is not providing the exact result for the user name - " + issuedToNameToBeSearch);
            logger.info("The search result for " + columnName + " field search is not providing the exact result for the user name - " + issuedToNameToBeSearch);
            takeScreenshot(columnName + "Search");
        }
    }

    public void verifyShowBySearchResult() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
        SimpleDateFormat sdFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
        String todayDate = simpleDateFormat.format(new Date());
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> searchedResult = new ArrayList<>();
        List<WebElement> rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[9]"), 30);
        for (WebElement element : rowElements) {
            String actualDate = element.getText().trim();
            try {
                Date d1 = sdFormat.parse(actualDate);
                Date d2 = sdFormat.parse(todayDate);
                if (d1.compareTo(d2) < 0) {
                    searchedResult.add("True");
                } else {
                    searchedResult.add("False");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                rowElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[9]"), 30);
                for (WebElement element : rowElements) {
                    String actualDate = element.getText().trim();
                    try {
                        Date d1 = sdFormat.parse(actualDate);
                        Date d2 = sdFormat.parse(todayDate);
                        if (d1.compareTo(d2) < 0) {
                            searchedResult.add("True");
                        } else {
                            searchedResult.add("False");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (!searchedResult.contains("False") && searchedResult.size() != 0) {
            getTest().log(LogStatus.PASS, "The search result for Show By field is provide the exact result for the option Return Over due");
            logger.info("The search result for Show By field is provide the exact result for the option Return Over due");
        } else {
            getTest().log(LogStatus.FAIL, "The search result for Show By field is not provide the exact result for the option Return Over due");
            logger.info("The search result for Show By field is not provide the exact result for the option Return Over due");
            takeScreenshot("ShoeBySearch");
        }
    }

    public void verifyComboSearchResult() {
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> searchedResult = new ArrayList<>();
        List<WebElement> productNameElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[2]"), 30);
        List<WebElement> issuedToElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[6]"), 30);
        for (WebElement element : productNameElements) {
            if (element.getText().trim().equalsIgnoreCase(productNameToBeSearch)) {
                searchedResult.add("True");
            } else {
                searchedResult.add("False");
            }
        }
        for (WebElement element : issuedToElements) {
            if (element.getText().trim().equalsIgnoreCase(issuedToNameToBeSearch)) {
                searchedResult.add("True");
            } else {
                searchedResult.add("False");
            }
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                productNameElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[2]"), 30);
                issuedToElements = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//td[6]"), 30);
                for (WebElement element : productNameElements) {
                    if (element.getText().trim().equalsIgnoreCase(productNameToBeSearch)) {
                        searchedResult.add("True");
                    } else {
                        searchedResult.add("False");
                    }
                }
                for (WebElement element : issuedToElements) {
                    if (element.getText().trim().equalsIgnoreCase(issuedToNameToBeSearch)) {
                        searchedResult.add("True");
                    } else {
                        searchedResult.add("False");
                    }
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (!searchedResult.contains("False") && searchedResult.size() != 0) {
            getTest().log(LogStatus.PASS, "The search result for product name and issued to combo field is provide the exact result");
            logger.info("The search result for product name and issued to combo field is provide the exact result");
        } else {
            getTest().log(LogStatus.FAIL, "The search result for product name and issued to combo field is not provide the exact result");
            logger.info("The search result for product name and issued to combo field is not provide the exact result");
            takeScreenshot("MultipleSearch");
        }
    }

    public void clickClearSearchButton() {
        click(By.xpath("//a[contains(@class,'clearsearchAlltext')]"), "Clear search", 30);
    }

    public void verifyClearedSearch() {
        String issueToFieldClass = getAtribute(By.xpath("//span[text()='Issued To ']"), "class", 20);
        String productNameFieldClass = getAtribute(By.xpath("//span[text()='Product Name ']"), "class", 20);
        if (issueToFieldClass.equalsIgnoreCase("") && productNameFieldClass.equalsIgnoreCase("")) {
            getTest().log(LogStatus.PASS, "The search result are cleared when click reset icon");
            logger.info("The search result are cleared when click reset icon");
        } else {
            getTest().log(LogStatus.FAIL, "The search result are not cleared when click reset icon");
            logger.info("The search result are not cleared when click reset icon");
            takeScreenshot("ResetSearch");
        }
    }

    public void upperSideExpandCollapseFilter() {
        String locator = "//span[@class='expand_all_filters']/span[@data-toggle='collapse']";
        clickExpandCollapseButton(locator, "Upper");
        verifyExpandedMenu("Expand", locator, "Upper");
        clickExpandCollapseButton(locator, "Upper");
        verifyExpandedMenu("Collapse", locator, "Upper");
    }

    public void bottomSideExpandCollapseFilter() {
        String locator = "//div[@class='bottom_filter_button']/a";
        clickExpandCollapseButton(locator, "Bottom");
        verifyExpandedMenu("Expand", locator, "Bottom");
        clickExpandCollapseButton(locator, "Bottom");
        verifyExpandedMenu("Collapse", locator, "Bottom");
    }

    public void clickExpandCollapseButton(String locator, String side) {
        click(By.xpath(locator), side + " side expand / collapse", 30);
        ;
    }

    public void verifyExpandedMenu(String menuPosition, String locator, String side) {
        String expectedClass = "";
        String reportMessage = "";
        String expandedMenuClass = getAtribute(By.xpath(locator), "aria-expanded", 30);
        if (menuPosition.equalsIgnoreCase("Expand")) {
            expectedClass = "true";
            reportMessage = "expanded";
        } else {
            expectedClass = "false";
            reportMessage = "collapsed";
        }
        if (expandedMenuClass.equalsIgnoreCase(expectedClass)) {
            getTest().log(LogStatus.PASS, "All search fields are got " + reportMessage + " when click on the " + side + " side expand / collapse button");
            logger.info("All search fields are got " + reportMessage + " when click on the " + side + " side expand / collapse button");
        } else {
            getTest().log(LogStatus.FAIL, "All search fields are not " + reportMessage + " when click on the " + side + " side expand / collapse button");
            logger.info("All search fields are not " + reportMessage + " when click on the " + side + " side expand / collapse button");
            takeScreenshot("ExpandORCollapseButton");
        }
    }

    public void verifyBreadCrumbInProductAssignmentPage() {
        WebElement breadCrumb = findElementVisibility(By.xpath("//div[@id='SiteMapLink']"), 30);
        if (breadCrumb != null) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed as expected in the Product Assignment page");
            logger.info("BreadCrumb is displayed as expected in the Product Assignment page");
            click(By.xpath("//div[@id='SiteMapLink']/ol/li/a"), "BreadCrumb Home", 30);
            waitForLoad(30);
            WebElement homePageElement = findElementVisibility(By.xpath("//div[@class='row']//span[normalize-space(text())='Dashboard']"), 30);
            if (homePageElement != null) {
                getTest().log(LogStatus.PASS, "Home page is displayed as expected when click on the Home option in the breadcrumb of product assignment page");
                logger.info("Home page is displayed as expected when click on the Home option in the breadcrumb of product assignment page");
            } else {
                getTest().log(LogStatus.FAIL, "Home page is not displayed when click on the Home option in the breadcrumb of product assignment page");
                logger.info("Home page is not displayed when click on the Home option in the breadcrumb of product assignment page");
                takeScreenshot("BradCrumbHome");
            }
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed in the Product Assignment page");
            logger.info("BreadCrumb is not displayed in the Product Assignment page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void verifyPaginationFunctionality() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='example']//tbody//tr[" + recordCount + "]//td[8]"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='example']//tbody//tr//td[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int nextPageStartRecordCount = Integer.parseInt(nextPaginationText[1]);
            if (nextPageStartRecordCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Next page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Next page is not displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is not displayed as expected when click on the \"Next\" pagination button");
                takeScreenshot("PaginationNext");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link previous' and text()='Prev']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='example']//tbody//tr[" + recordCount + "]//td[8]"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='example']//tbody//tr//td[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int previousPageEndCount = Integer.parseInt(previousPaginationText[3]);
            if (previousPageEndCount == nextPageStartRecordCount - 1) {
                getTest().log(LogStatus.PASS, "Previous page is displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is displayed as expected when click on the \"Previous\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Previous page is not displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is not displayed as expected when click on the \"Previous\" pagination button");
                takeScreenshot("PaginationPrevious");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link last' and text()='Last ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='example']//tbody//tr[" + recordCount + "]//td[8]"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='example']//tbody//tr//td[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int lastPageEndCount = Integer.parseInt(lastPagePaginationText[3]);
            if (lastPageEndCount == totalRecordCount) {
                getTest().log(LogStatus.PASS, "Last page is displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is displayed as expected when click on the \"Last\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Last page is not displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is not displayed as expected when click on the \"Last\" pagination button");
                takeScreenshot("PaginationLast");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link  first' and text()='First ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='example']//tbody//tr[" + recordCount + "]//td[8]"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='example']//tbody//tr//td[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageStartRecordCount = Integer.parseInt(firstPagePaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is displayed as expected when click on the \"First\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is not displayed as expected when click on the \"First\" pagination button");
                takeScreenshot("PaginationFirst");
            }
        } else {
            getTest().log(LogStatus.PASS, "Pagination section is not displayed since the record count is " + totalRecordCount);
            logger.info("Pagination section is not displayed since the record count is " + totalRecordCount);
        }
    }

    public void selectRecordPagination() {
        String selectRecordPage = "40";
        waitForLoader(20);
        staticWait(2000);
        selectValueWithValue(By.cssSelector("#pageSize"), selectRecordPage, "Page size", 10);
        waitForLoader(30);
        staticWait(2000);
        String selectedOption = getText(By.xpath("//select[@id='pageSize']//option[@selected='selected']"), 30);
        int checkRecord = Integer.parseInt(selectedOption);
        int recordCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 20).size();

        if (checkRecord == Integer.parseInt(selectRecordPage) && recordCount <= checkRecord) {
            getTest().log(LogStatus.PASS, "Records are displayed as expected based on the selected page size");
            logger.info("Records are displayed as expected based on the selected page size");

        } else {
            getTest().log(LogStatus.FAIL, "Records are not displayed as expected based on the selected page size");
            logger.info("Records are not displayed as expected based on the selected page size");
            takeScreenshot("Records");
        }
        selectValueWithValue(By.cssSelector("#pageSize"), "10", "Page size", 10);
        waitForLoader(20);
        staticWait(2000);
    }

    public void clickMoreIcon() {
        findElementPresence(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 120);
        click(By.xpath("//table[@id='example']//tbody//tr[1]//td[contains(@class,'mobile-action')]/span"), "More Icon", 30);
    }

    public void closeMoreOptionPopup() {
        click(By.xpath("//table[@id='example']//tbody//tr[1]//i[contains(@class,'close close-action')]"), "More Option Cancel", 20);
    }

    public void verifyMoreIconBehaviorForUniqueProd() {
        String shareIconClass = getAtribute(By.xpath("//table[@id='example']//tbody//tr[1]//td[contains(@class,'mobile-action')]//span//a[@data-original-title='Share']"), "class", 20);
        if (!shareIconClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The share icon is enabled for the unique product as expected");
            logger.info("The share icon is enabled for the unique product as expected");
            click(By.xpath("//table[@id='example']//tbody//tr[1]//td[contains(@class,'mobile-action')]//span//a[@data-original-title='Share']"), "Share Icon", 30);
        } else {
            getTest().log(LogStatus.FAIL, "The share icon is not enabled for the unique product as expected");
            logger.info("The share icon is not enabled for the unique product as expected");
            takeScreenshot("ShareIcon");
        }
    }

    public void verifySharingPopup() {
        WebElement sharingPopup = findElementVisibility(By.xpath("//div[@id='divDialogItemSharing']"), 30);
        if (sharingPopup != null) {
            getTest().log(LogStatus.PASS, "The Sharing popup is displayed as expected when click on the share icon");
            logger.info("The Sharing popup is displayed as expected when click on the share icon");
        } else {
            getTest().log(LogStatus.FAIL, "The Sharing popup is not displayed when click on the share icon");
            logger.info("The Sharing popup is not displayed when click on the share icon");
            takeScreenshot("SharePopup");
        }
    }

    public void clickAddButton() {
        click(By.xpath("//a[@id='btnAddToList']"), "Add Button", 20);
    }

    public void verifyMandatoryErrorPopup() {
        WebElement mandatoryErrorPopup = findElementVisibility(By.xpath("//div[@id='notifymessage']/div/span"), 10);
        if (mandatoryErrorPopup != null && !mandatoryErrorPopup.getText().equalsIgnoreCase("")) {
            getTest().log(LogStatus.PASS, "Mandatory field error message is displayed as expected when click on save button with out entering any field");
            logger.info("Mandatory field error message is displayed as expected when click on save button with out entering any field");
            closeErrorPopup();
        } else {
            getTest().log(LogStatus.FAIL, "Mandatory field error message is not displayed when click on save button with out entering any field");
            logger.info("Mandatory field error message is not displayed when click on save button with out entering any field");
            takeScreenshot("MandatoryErrorMessage");
        }
    }

    public void closeErrorPopup() {
        click(By.xpath("//button[@id='closenotifymessage']//span"), "Mandatory Message Close", 10);
    }

    public void verifyAsteriskAndMessage() {
        List<String> mandatoryFieldNames = new ArrayList<>();
        List<WebElement> mandatoryFields = findMultipleElement(By.xpath("//span[text()='*']//parent::label"), 30);
        for (WebElement mandatoryField : mandatoryFields) {
            mandatoryFieldNames.add(mandatoryField.getText().split(":")[0]);
        }
        for (String mandatoryFieldName : mandatoryFieldNames) {
            WebElement mandatoryError = findElementVisibility(By.xpath("//label[text()='" + mandatoryFieldName + ":']//parent::div/span"), 5);
            if (mandatoryError != null) {
                getTest().log(LogStatus.PASS, "Mandatory field error message is displayed as expected when click on the save button with out selecting any values for " + mandatoryFieldName + " field");
                logger.info("Mandatory field error message is displayed as expected when click on the save button with out selecting any values for " + mandatoryFieldName + " field");
            } else {
                getTest().log(LogStatus.FAIL, "Mandatory field error message is not displayed when click on the save button with out selecting any values for " + mandatoryFieldName + " field");
                logger.info("Mandatory field error message is not displayed when click on the save button with out selecting any values for " + mandatoryFieldName + " field");
                takeScreenshot(mandatoryFieldName + "ErrorMessage");
            }
        }
    }

    public void clickLocationField() {
        click(By.cssSelector("span.CompanyItemSharingselected"), "Location field dropdown", 30);
    }

    public void verifyLocationDropdownList() {
        WebElement locationList = findElementVisibility(By.xpath("//ul[@id='CompanyItemSharing']"), 20);
        if (locationList != null) {
            getTest().log(LogStatus.PASS, "The location list is displayed as expected when click on the Location field dropdown");
            logger.info("The location list is displayed as expected when click on the Location field dropdown");
        } else {
            getTest().log(LogStatus.FAIL, "The location list is not displayed when click on the Location field dropdown");
            logger.info("The location list is not displayed when click on the Location field dropdown");
            takeScreenshot("LocationList");
        }
    }

    public void clickAndVerifyLocationValue() {
        String locationToSelect = "";
        selectCompanyAdminLocation();
        String selectedLocationValue = getText(By.cssSelector("span.CompanyItemSharingselected"), 30);
        if (selectedLocationValue.equalsIgnoreCase(companyAdminLocationName)) {
            getTest().log(LogStatus.PASS, "The selected location is displayed in the Location field once click the value form the list");
            logger.info("The selected location is displayed in the Location field once click the value form the list");
        } else {
            getTest().log(LogStatus.FAIL, "The selected location is not displayed in the Location field once click the value form the list");
            logger.info("The selected location is not displayed in the Location field once click the value form the list");
            takeScreenshot("SelectedLocationValue");
        }
    }

    public void selectCompanyAdminLocation() {
        click(By.xpath("//ul[@id='CompanyItemSharing']//*[text()='" + companyAdminLocationName + "']"), "Location Value", 20);
    }

    public void selectProductManagerLocation() {
        click(By.xpath("//ul[@id='CompanyItemSharing']//*[text()='" + productManagerLocation + "']"), "Location Value", 20);
    }

    public void clickLocationFieldClearIcon() {
        click(By.xpath("//span[@class='float-right']/i[@title='Clear']"), "Location Field Clear", 20);
    }

    public void verifyLocationFieldClearFunction() {
        String selectedLocationValue = getText(By.cssSelector("span.CompanyItemSharingselected"), 30);
        clickLocationFieldClearIcon();
        String LocationValueAfterClear = getText(By.cssSelector("span.CompanyItemSharingselected"), 30);
        if (!LocationValueAfterClear.equalsIgnoreCase(selectedLocationValue)) {
            getTest().log(LogStatus.PASS, "The selected location is cleared when click on the clear icon of the location field");
            logger.info("The selected location is cleared when click on the clear icon of the location field");
        } else {
            getTest().log(LogStatus.FAIL, "The selected location is not cleared when click on the clear icon of the location field");
            logger.info("The selected location is not cleared when click on the clear icon of the location field");
            takeScreenshot("LocationClear");
        }
    }

    public void enterUserName(String name) {
        enter(By.xpath("//input[@id='txtItemSharing']"), name, "User Name to share", 20);
    }

    public void enterCompanyAdminUserName() {
        String userName = prop.getProperty("companyAdmin");
        enterUserName(userName);
        clickUserFromSuggestion(userName);
    }

    public void clickUserFromSuggestion(String userName) {
        staticWait(3000);
        click(By.xpath("//div[@class='unique_dynamicdatalist']//li[contains(text(),'" + userName + "')]"), userName, 60);
    }

    public void verifySelectedNameIsDisplayed() {
        List<WebElement> addedEmployeeNames = findMultipleElement(By.xpath("//table[@id='tblEmployeeList']//tbody//tr"), 10);
        if (addedEmployeeNames.size() != 0) {
            getTest().log(LogStatus.PASS, "Selected user name is added to the employee list when click on the add button");
            logger.info("Selected user name is added to the employee list when click on the add button");
        } else {
            getTest().log(LogStatus.FAIL, "Selected user name is not added to the employee list when click on the add button");
            logger.info("Selected user name is not added to the employee list when click on the add button");
            takeScreenshot("UserNameList");
        }
    }

    public void enterProjectManagerID() {
        enterUserName(prop.getProperty("projectManagerID"));
        clickUserFromSuggestion(prop.getProperty("projectManager"));
    }

    public void verifySameUserIssue() {
        WebElement sameUserIssue = findElementVisibility(By.xpath("//div[@id='notifymessage']//span[text()='Employee already exist!']"), 20);
        if (sameUserIssue != null) {
            getTest().log(LogStatus.PASS, "The error message is displayed as expected when try to add same user again");
            logger.info("The error message is displayed as expected when try to add same user again");
            click(By.xpath("//button[@id='closenotifymessage']"), "User Exists error close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "The error message is not displayed when try to add same user again");
            logger.info("The error message is not displayed when try to add same user again");
            takeScreenshot("UserExistError");
        }
    }

    public void clickCancelButton() {
        click(By.xpath("//a[@id='btnCancel']"), "cancel", 20);
    }

    public void verifySharingPopupClosed() {
        WebElement sharingPopup = findElementVisibility(By.xpath("//div[@id='divDialogItemSharing']"), 5);
        if (sharingPopup == null) {
            getTest().log(LogStatus.PASS, "The sharing popup is closed when click on the cancel button");
            logger.info("The sharing popup is closed when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "The sharing popup is not closed when click on the cancel button");
            logger.info("The sharing popup is not closed when click on the cancel button");
            takeScreenshot("SharingPopupClose");
        }
    }

    public void clickSaveButton() {
        uniqueNameToShare = getText(By.xpath("//span[@id='lbl-uniqeName']"), 20);
        employeeNameToShare = getText(By.xpath("//table[@id='tblEmployeeList']//tbody//tr[1]//td[1]"), 30);
        click(By.xpath("//a[@id='btnSave']"), "Save button", 20);
    }

    public void verifySharedProduct() {
        WebElement sharedProduct = findElementVisibility(By.xpath("//table[@id='example']//tbody//td[normalize-space(text())='" + uniqueNameToShare + "']//parent::tr//td[text()='" + prop.getProperty("projectManager") + "']"), 30);
        if (sharedProduct != null) {
            getTest().log(LogStatus.PASS, "The Product is successfully shared with " + employeeNameToShare);
            logger.info("The Product is successfully shared with " + employeeNameToShare);
        } else {
            getTest().log(LogStatus.FAIL, "The Product is not shared with " + employeeNameToShare);
            logger.info("The Product is not shared with " + employeeNameToShare);
            takeScreenshot("SharedProduct");
        }
    }

    public void verifyReturnPopup() {
        WebElement returnPopUp = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[text()='Return']"), 20);
        if (returnPopUp != null) {
            getTest().log(LogStatus.PASS, "Return popup is displayed as expected when click on the Return icon under action menu");
            logger.info("Return popup is displayed as expected when click on the Return icon under action menu");
        } else {
            getTest().log(LogStatus.FAIL, "Return popup is not displayed when click on the Return icon under action menu");
            logger.info("Return popup is not displayed when click on the Return icon under action menu");
            takeScreenshot("ReturnPopup");
        }
    }

    public void verifyProductQuantityField(String field) {
        boolean condition;
        String message = "";
        String productQuantityField = findElementPresence(By.xpath("//input[@id='NumberOfItems'][1]"), 30).getAttribute("disabled");
        if (field.equalsIgnoreCase("disabled")) {
            condition = productQuantityField.equalsIgnoreCase("disabled");
            message = "disabled";
            field = "unique name";

        } else {
            condition = (productQuantityField != null);
            message = "enabled";
            field = "non unique name";
        }
        if (condition) {
            getTest().log(LogStatus.PASS, "The product quantity field is " + message + " as expected for the " + field + " product");
            logger.info("The product quantity field is " + message + " as expected for the " + field + " product");
        } else {
            getTest().log(LogStatus.FAIL, "The product quantity field is not " + message + " for the " + field + " product");
            logger.info("The product quantity field is not " + message + " for the " + field + " product");
            takeScreenshot("ProductQuantityField");
        }
    }

    public void verifyEnabledProdQuantityField() {
        verifyProductQuantityField("Enabled");
    }

    public void verifyDisabledProdQuantityField() {
        verifyProductQuantityField("Disabled");
    }

    public void verifyProdQuantityField() {
        String dataToBeEnter = "Abc123#";
        enter(By.xpath("//input[@id='NumberOfItems']"), dataToBeEnter, "Product quantity field", 20);
        String actualString = getAtribute(By.xpath("//input[@id='NumberOfItems']"), "value", 20);
        if (actualString.equalsIgnoreCase("123")) {
            getTest().log(LogStatus.PASS, "The product quantity field accepts the numeric value only as expected");
            logger.info("The product quantity field accepts the numeric value only as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The product quantity field accepts the data other than numeric");
            logger.info("The product quantity field accepts the data other than numeric");
            takeScreenshot("ProductQuantityField");
        }
    }

    public void verifyReturnProdMandatoryMessage() {
        WebElement titleFieldError = findElementVisibility(By.xpath("//input[@id='Title']//parent::div/span"), 20);
        WebElement noOfProdError = findElementVisibility(By.xpath("//input[@id='NumberOfItems']//parent::div/span"), 20);
        if (titleFieldError != null && noOfProdError != null) {
            getTest().log(LogStatus.PASS, "The Mandatory field error message is displayed as expected when click on the save button with out enter any values");
            logger.info("The Mandatory field error message is displayed as expected when click on the save button with out enter any values");
        } else {
            getTest().log(LogStatus.PASS, "The Mandatory field error message is displayed as expected when click on the save button with out enter any values");
        }
    }

    public void verifyToolTipInList() {
        String[] columnNames = {"Product Name", "Product Code", "Unique Name / Number of Products", "Location Name", "Issued To",
                "Issued By", "Assigned From", "Assigned Till", "Return Date"};
        List<WebElement> dataInList = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr[1]//td"), 20);
        for (int i = 2; i < dataInList.size(); i++) {
            String title = getAtribute(By.xpath("//table[@id='example']//tbody//tr//td[" + i + "]"), "title", 5);
            if (!title.equalsIgnoreCase("")) {
                getTest().log(LogStatus.PASS, "The Tooltip is displayed for the data in the column " + columnNames[i - 2]);
                logger.info("The Tooltip is displayed for the data in the column " + columnNames[i - 2]);
            } else {
                getTest().log(LogStatus.FAIL, "The Tooltip is not displayed for the data in the column " + columnNames[i - 2]);
                logger.info("The Tooltip is not displayed for the data in the column " + columnNames[i - 2]);
            }
        }
    }

    public void getCountOfAllReturnOverDueProd() {
        int totalProduct = 0;
        String prodCountInText = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");
        String monthNumber = simpleDateFormat.format(new Date());
        int previousMonth = Integer.parseInt(monthNumber) - 1;
        deployProductPage.make100PageSize();
        waitForLoad(30);
        Object paginationNextButton = "";
        First:
        while (paginationNextButton != null) {
            int returnOverDueProductInList = findMultipleElement(By.xpath("//table[@id='example']//tr[@class='high-bar']"), 30).size();
            for (int i = 1; i <= returnOverDueProductInList; i++) {
                String assignStartDate = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[8]"), 20);
                if (assignStartDate.startsWith(monthNumber)) {
                    WebElement nonUniqueProductCount = findElementVisibility(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[4]/span/span"), 5);
                    if (nonUniqueProductCount != null) {
                        prodCountInText = nonUniqueProductCount.getText();
                    } else {
                        prodCountInText = "1";
                    }
                    totalProduct = totalProduct + Integer.parseInt(prodCountInText);
                } else if (assignStartDate.startsWith(String.valueOf(previousMonth))) {
                    break First;
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
            }
        }
        allReturnOverDueProductCount = String.valueOf(totalProduct);
    }

    public void getCountOfShiftReturnOverDueProd(String userType, String condition) {
        if (condition.equalsIgnoreCase("all")) {
            getCountOfAllReturnOverDueProd();
            shiftReturnOverDueProductCount = allReturnOverDueProductCount;
        } else {
            int totalProduct = 0;
            String prodCountInText = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");
            String monthNumber = simpleDateFormat.format(new Date());
            int previousMonth = Integer.parseInt(monthNumber) - 1;
            String expectedLocationName = productManagerLocation;
            deployProductPage.make100PageSize();
            waitForLoad(30);
            Object paginationNextButton = "";
            First:
            while (paginationNextButton != null) {
                int returnOverDueProductInList = findMultipleElement(By.xpath("//table[@id='example']//tr[@class='high-bar']"), 30).size();
                for (int i = 1; i <= returnOverDueProductInList; i++) {
                    String assignStartDate = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[8]"), 20);
                    if (assignStartDate.startsWith(monthNumber)) {
                        if (userType.equalsIgnoreCase("projectManager")) {
                            String locationName = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[5]"), 5);
                            if (locationName.equalsIgnoreCase(expectedLocationName)) {
                                WebElement productCount = findElementVisibility(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[text()='" + expectedLocationName + "']//parent::tr//td/span/span[@class='noti-circle noti-blue']"), 10);
                                if (productCount != null) {
                                    prodCountInText = productCount.getText();
                                } else {
                                    prodCountInText = "1";
                                }
                            } else {
                                prodCountInText = "0";
                            }
                        } else {
                            WebElement productCount = findElementVisibility(By.xpath("//table[@id='example']//tbody//tr[@class='high-bar'][" + i + "]/td[4]/span/span"), 10);
                            if (productCount != null) {
                                prodCountInText = productCount.getText();
                            } else {
                                prodCountInText = "1";
                            }
                        }
                        totalProduct = totalProduct + Integer.parseInt(prodCountInText);
                    } else if (assignStartDate.startsWith(String.valueOf(previousMonth))) {
                        break First;
                    }
                }
                paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
                if (paginationNextButton != null) {
                    click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
                }
            }
            shiftReturnOverDueProductCount = String.valueOf(totalProduct);
        }
    }

    public void getCountOfReturnOverDueProdForNU(String condition) {
        if (condition.equalsIgnoreCase("all")) {
            getCountOfAllReturnOverDueProd();
            shiftReturnOverDueProductCount = allReturnOverDueProductCount;
        } else {
            int totalProduct = 0;
            String prodCountInText = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");
            String monthNumber = simpleDateFormat.format(new Date());
            int previousMonth = Integer.parseInt(monthNumber) - 1;
            deployProductPage.make100PageSize();
            waitForLoad(30);
            Object paginationNextButton = "";
            First:
            while (paginationNextButton != null) {
                int returnOverDueProductInList = findMultipleElement(By.xpath("//table[@id='example']//tr[@class='high-bar']"), 30).size();
                for (int i = 1; i <= returnOverDueProductInList; i++) {
                    String assignStartDate = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[8]"), 20);
                    if (assignStartDate.startsWith(monthNumber)) {
                        String actualUserName = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[6]"), 5);
                        if (actualUserName.equalsIgnoreCase(prop.getProperty("secondUser"))) {
                            WebElement productCount = findElementVisibility(By.xpath("//table[@id='example']//tr[@class='high-bar'][" + i + "]//td[text()='" + prop.getProperty("secondUser") + "']//parent::tr//td/span/span[@class='noti-circle noti-blue']"), 5);
                            if (productCount != null) {
                                prodCountInText = productCount.getText();
                            } else {
                                prodCountInText = "1";
                            }
                        } else {
                            prodCountInText = "0";
                        }
                        totalProduct = totalProduct + Integer.parseInt(prodCountInText);
                    } else if (assignStartDate.startsWith(String.valueOf(previousMonth))) {
                        break First;
                    }
                }
                paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
                if (paginationNextButton != null) {
                    click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
                }
            }
            shiftReturnOverDueProductCount = String.valueOf(totalProduct);
        }
    }

    public void clickFirstOverDueMoreIcon() {
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 180);
        click(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]//td[11]/span"), "More icon of first over due product", 20);
    }

    public void clickFirstOverDueMoreIconByShift() {
        click(By.xpath("(//table[@id='example']//tr[@class='high-bar']//td[text()='" + productManagerLocation + "'])[1]//parent::tr//td[11]/span"), "More icon of first over due product", 20);
    }

    public void clickApproveRejectIcon() {
        click(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]//td[11]//a[@class='actions-onclick acceptReturnRequest']"), "Approve/Reject icon", 20);
    }

    public void clickCheckBoxOfProductForReturn(String productName) {
        click(By.xpath("//table[@id='tblReturnRequest']//tbody//td[text()='" + productName + "']//parent::tr//td[1]/div"), "Check box of " + productName, 40);
    }

    public void clickSaveInAcceptRejectReturnPopup() {
        click(By.xpath("//a[@id='btnUpdateReturnStatus']"), "Save in Return Accept Reject popup", 20);
    }

    public void clickFirstOverDueNotReturnedIcon() {
        click(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]//a[@data-original-title='Not Returned']"), "Not returned icon of first over due product", 10);
        ;
    }

    public void clickFirstOverDueNotReturnedIconByShift() {
        click(By.xpath("(//table[@id='example']//tr[@class='high-bar']//td[text()='" + productManagerLocation + "'])[1]//parent::tr//a[@data-original-title='Not Returned']"), "Not returned icon of first over due product", 10);
    }

    public void enterReturnTitle(String title) {
        enter(By.xpath("//input[@id='Title']"), title, "Return Title", 20);
    }

    public void clickSaveInReturnPopup() {
        click(By.xpath("//a[@id='btnUpdateAssignment']"), "Return popup save", 20);
    }

    public void returnProductFromProductAssignmentPage() {
        Object paginationNextButton = "";
        if (!allReturnOverDueProductCount.equalsIgnoreCase("0")) {
            while (paginationNextButton != null) {
                WebElement productInReturnOverDueList = findElementVisibility(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]"), 5);
                if (productInReturnOverDueList != null) {
                    clickFirstOverDueMoreIcon();
                    WebElement approveRejectIcon = findElementVisibility(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]//td[11]//a[@class='actions-onclick acceptReturnRequest']"), 5);
                    if (approveRejectIcon != null) {
                        String productToBeAccept = getText(By.xpath("//table[@id='example']//tr[@class='high-bar'][1]//td[4]"), 10);
                        clickApproveRejectIcon();
                        clickCheckBoxOfProductForReturn(productToBeAccept);
                        clickSaveInAcceptRejectReturnPopup();
                    } else {
                        clickFirstOverDueNotReturnedIcon();
                        enterReturnTitle("Test Return");
                        clickSaveInReturnPopup();
                    }
                    break;
                }
                paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
                if (paginationNextButton != null) {
                    click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
                }
            }
            waitForLoader(30);
            staticWait(2000);
        } else {
            getTest().log(LogStatus.WARNING, "Since there is no record for all location with return over due for current month so unable to complete the cumulative count change");
            logger.info("Since there is no record for all location with return over due for current month so unable to complete the cumulative count change");
        }
    }

    public void returnProductFromProductAssignmentPageByShift(String userType) {
        Object paginationNextButton = "";
        while (paginationNextButton != null) {
            WebElement productInReturnOverDueList = findElementVisibility(By.xpath("(//table[@id='example']//tr[@class='high-bar']//td[text()='" + productManagerLocation + "'])[1]//parent::tr//td[11]/span"), 5);
            if (productInReturnOverDueList != null) {
                clickFirstOverDueMoreIconByShift();
                WebElement approveRejectIcon = findElementVisibility(By.xpath("(//table[@id='example']//tr[@class='high-bar']//td[text()='" + toLocation + "'])[1]//parent::tr//td[11]//a[@class='actions-onclick acceptReturnRequest']"), 5);
                if (approveRejectIcon != null) {
                    String productToBeAccept = getText(By.xpath("(//table[@id='example']//tr[@class='high-bar']//td[text()='" + toLocation + "'])[1]//parent::tr//td[4]"), 10);
                    clickApproveRejectIcon();
                    clickCheckBoxOfProductForReturn(productToBeAccept);
                    clickSaveInAcceptRejectReturnPopup();
                } else {
                    clickFirstOverDueNotReturnedIconByShift();
                    enterReturnTitle("Test Return");
                    clickSaveInReturnPopup();
                }
                break;
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
            }
        }
        waitForLoader(30);
        staticWait(2000);
    }

    public void verifyYellowStripForOverDueProduct() {
        int pageNumber = 1;
        Object paginationNextButton = "";
        deployProductPage.make100PageSize();
        staticWait(2000);
        while (paginationNextButton != null) {
            List<WebElement> oveDueDateLocators = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 20);
            for (int i = 1; i < oveDueDateLocators.size(); i++) {
                String rowClass = getAtribute(By.xpath("//table[@id='example']//tbody//tr[" + i + "]"), "class", 10);
                if (rowClass.equalsIgnoreCase("high-bar")) {
                    getTest().log(LogStatus.PASS, "The product in the Row-" + i + " in the Page-" + pageNumber + " is over due product and it displays the yellow color strip");
                    logger.info("The product in the Row-" + i + " in the Page-" + pageNumber + " is over due product and it displays the yellow color strip");
                } else {
                    getTest().log(LogStatus.FAIL, "The product in the Row-" + i + " in the Page-" + pageNumber + " is over due product but it does not displays the yellow color strip");
                    logger.info("The product in the Row-" + i + " in the Page-" + pageNumber + " is over due product but it does not displays the yellow color strip");
                    takeScreenshot("YellowStripInRow" + i);
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 10);
                pageNumber++;
            }
            waitForLoader(30);
            staticWait(2000);
        }
    }

    public void clickReturnIconInTableTop() {
        click(By.xpath("//a[@id='ancReturnItem']"), "Return icon on the table", 20);
    }

    public void clickBulkSaveButton() {
        click(By.xpath("//a[@id='btnBulkReturnItem']"), "Bulk Return save", 20);
    }

    public void cancelReturnPopup() {
        click(By.xpath("//a[@id='btnBulkReturnItemCancel']"), "Return popup Cancel", 20);
    }

    public void verifyClosedReturnedPopup() {
        WebElement closedReturnPopup = findElementVisibility(By.xpath("//h5[text()='Return']"), 5);
        if (closedReturnPopup == null) {
            getTest().log(LogStatus.PASS, "The return popup is closed when click on the cancel button in the return popup");
            logger.info("The return popup is closed when click on the cancel button in the return popup");
        } else {
            getTest().log(LogStatus.FAIL, "The return popup is not closed when click on the cancel button in the return popup");
            logger.info("The return popup is not closed when click on the cancel button in the return popup");
            takeScreenshot("ReturnPopupCancel");
        }
    }

    public void getAndStoreProductDetails(String nameType) {
        String actualProductDetail = "";
        String numberOfProduct = "";
        String quantityFieldDisable = "";
        String[] productDetailField = {"Product Name", "Product Code", "Unique Name", "Location Name", "Issued To", "Issued By", "Assigned From", "Assigned Till", "Return Date"};
        List<WebElement> productDetails = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr[1]//td"), 20);
        for (int i = 2; i < productDetails.size(); i++) {
            if (i != 4) {
                actualProductDetail = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[" + i + "]"), 20);
            } else {
                if (nameType.equalsIgnoreCase("UniqueName")) {
                    actualProductDetail = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[" + i + "]"), 20);
                    numberOfProduct = "1";
                    quantityFieldDisable = "true";
                } else {
                    actualProductDetail = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[2]"), 20) + "_1";
                    numberOfProduct = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[" + i + "]/span/span"), 20);
                    quantityFieldDisable = "false";
                }
            }
            headerAndListValue.put(productDetailField[i - 2], actualProductDetail);
        }
        headerAndListValue.put("Number of Products", numberOfProduct);
        headerAndListValue.put("Quantity", quantityFieldDisable);
    }

    public void verifyTheDetailsInPopup(String nameType) {
        int iteration = 0;
        String actualProductDetail = "";
        boolean condition = false;
        List<WebElement> headerNameLocators = findMultipleElement(By.xpath("//table[@id='tblReturnRequestBulk']//th/span"), 20);
        for (WebElement headerName : headerNameLocators) {
            iteration++;
            if (iteration < 8) {
                actualProductDetail = getText(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[" + iteration + "]"), 10);
            } else if (iteration == 8) {
                actualProductDetail = getText(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[" + iteration + "]/span/span"), 10);
            } else {
                actualProductDetail = getAtribute(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[" + iteration + "]/input"), "value", 10);
            }

            String actualHeaderName = headerName.getText().trim();
            if (iteration != 9) {
                condition = actualProductDetail.equalsIgnoreCase(headerAndListValue.get(actualHeaderName));
            } else {
                if (nameType.equalsIgnoreCase("UniqueName")) {
                    String inputFieldState = getAtribute(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[9]/input"), "disabled", 5);
                    condition = inputFieldState.equalsIgnoreCase(headerAndListValue.get(actualHeaderName));
                } else {
                    String inputFieldState = getAtribute(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[9]/input"), "disable", 5);
                    condition = inputFieldState == null;
                }
            }
            if (condition) {
                getTest().log(LogStatus.PASS, "Value of the " + actualHeaderName + " header in the return popup is displays the value as it is in the list");
                logger.info("Value of the " + actualHeaderName + " header in the return popup is displays value the as it is in the list");
            } else {
                getTest().log(LogStatus.FAIL, "Value of the " + actualHeaderName + " header in the return popup is not displays the value as it is in the list");
                logger.info("Value of the " + actualHeaderName + " header in the return popup is not displays the value as it is in the list");
                takeScreenshot("ValueCopyInReturnPopup");
            }
        }
    }

    public void enterMoreCharInCommentField() {
        enter(By.xpath("//textarea[@id='txtComment']"), prop.getProperty("description400Character"), "Return Prod Comment", 20);
    }

    public void verifyCommentFieldLength() {
        String actualComment = getAtribute(By.xpath("//textarea[@id='txtComment']"), "value", 20);
        if (!actualComment.equalsIgnoreCase(prop.getProperty("description400Character"))) {
            getTest().log(LogStatus.PASS, "The comment field not accepts more than 400 character as expected in the return popup");
            logger.info("The comment field not accepts more than 400 character as expected in the return popup");
        } else {
            getTest().log(LogStatus.FAIL, "The comment field accepts more than 400 character in the return popup");
            logger.info("The comment field accepts more than 400 character in the return popup");
            takeScreenshot("CommentLength");
        }
    }

    public void verifyReturnedProduct() {
        waitForLoader(60);
        staticWait(2000);
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 120);
        String checkBoxOfProduct = findElementPresence(By.xpath("//table[@id='example']//tbody//tr[1]/td[1]//input"), 20).getAttribute("disabled");
        if (checkBoxOfProduct.equalsIgnoreCase("true")) {
            getTest().log(LogStatus.PASS, "The product is returned successfully when click on the save button in the return popup");
            logger.info("The product is returned successfully when click on the save button in the return popup");
        } else {
            getTest().log(LogStatus.FAIL, "The product is not returned when click on the save button in the return popup");
            logger.info("The product is not returned when click on the save button in the return popup");
            takeScreenshot("ProductNotReturn");
        }
    }

    public void enterQuantity(String quantity) {
        staticWait(2000);
        enter(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[9]/input"), quantity, "Quantity", 20);
    }

    public void enterEmptyStringInQuantity() {
        enterQuantity("");
    }

    public void verifyMandatoryErrorMessage() {
        WebElement mandatoryErrorMessage = findElementVisibility(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[9]/span[@class='invalid-feedback']"), 5);
        if (mandatoryErrorMessage != null) {
            getTest().log(LogStatus.PASS, "Mandatory Error message is displayed as expected when click save button with out entering quantity in the return popup");
            logger.info("Mandatory Error message is displayed as expected when click save button with out entering quantity in the return popup");
        } else {
            getTest().log(LogStatus.FAIL, "Mandatory Error message is not displayed when click save button with out entering quantity in the return popup");
            logger.info("Mandatory Error message is not displayed when click save button with out entering quantity in the return popup");
            takeScreenshot("MandatoryQuantityError");
        }
    }

    public void enterAlphaNumericInTitle() {
        enter(By.xpath("//input[@id='txtTitle']"), prop.getProperty("productCodeAlphaNumeric"), "Return title", 20);
    }

    public void verifyAlphaNumericInTitleField() {
        String actualTitle = getAtribute(By.xpath("//input[@id='txtTitle']"), "value", 10);
        if (actualTitle.equalsIgnoreCase(prop.getProperty("productCodeAlphaNumeric"))) {
            getTest().log(LogStatus.PASS, "The Title field in the return popup is accepts the alpha numeric  string as expected");
            logger.info("The Title field in the return popup is accepts the alpha numeric  string as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Title field in the return popup is not accepts the alpha numeric  string");
            logger.info("The Title field in the return popup is not accepts the alpha numeric  string");
            takeScreenshot("TitleAlphaNumeric");
        }
    }

    public void enterAlphaNumericInComment() {
        enter(By.xpath("//textarea[@id='txtComment']"), prop.getProperty("productCodeAlphaNumeric"), "Return Comment", 20);
    }

    public void verifyAlphaNumericInCommentField() {
        String actualComment = getAtribute(By.xpath("//textarea[@id='txtComment']"), "value", 10);
        if (actualComment.equalsIgnoreCase(prop.getProperty("productCodeAlphaNumeric"))) {
            getTest().log(LogStatus.PASS, "The Comment field in the return popup is accepts the alpha numeric  string as expected");
            logger.info("The Comment field in the return popup is accepts the alpha numeric  string as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Comment field in the return popup is not accepts the alpha numeric  string");
            logger.info("The Comment field in the return popup is not accepts the alpha numeric  string");
            takeScreenshot("CommentAlphaNumeric");
        }
    }

    public void enterAlphaNumericInQuantity() {
        String numberNeedToBeEnter = getText(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[8]/span/span"), 10);
        enterQuantity(numberNeedToBeEnter + "AB");
    }

    public void enterValidQuantity() {
        String numberNeedToBeEnter = getText(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[8]/span/span"), 10);
        enterQuantity(numberNeedToBeEnter);
    }

    public void verifyQuantityFiled() {
        String numberShouldBeDisplayed = getText(By.xpath("//table[@id='tblReturnRequestBulk']//tbody//tr//td[8]/span/span"), 10);
        String actualComment = getAtribute(By.xpath("//textarea[@id='txtComment']"), "value", 10);
        if (actualComment.equalsIgnoreCase(numberShouldBeDisplayed)) {
            getTest().log(LogStatus.PASS, "The Comment field in the return popup is accepts the alpha numeric  string as expected");
            logger.info("The Comment field in the return popup is accepts the alpha numeric  string as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Comment field in the return popup is not accepts the alpha numeric  string");
            logger.info("The Comment field in the return popup is not accepts the alpha numeric  string");
            takeScreenshot("CommentAlphaNumeric");
        }
    }

    public void verifyRequestForReturnPopup(String condition) {
        WebElement requestForReturnPopup = findElementVisibility(By.xpath("//h5[text()='Request For Return']"), 5);
        boolean expectedCondition = condition.equalsIgnoreCase("displayed") == (requestForReturnPopup != null);
        if (expectedCondition) {
            getTest().log(LogStatus.PASS, "Request for Return popup is " + condition + " as expected when click on the Request for Return icon");
            logger.info("Request for Return popup is " + condition + " as expected when click on the Request for Return icon");
        } else {
            getTest().log(LogStatus.FAIL, "Request for Return popup is not " + condition + " when click on the Request for Return icon");
            logger.info("Request for Return popup is not " + condition + " when click on the Request for Return");
            takeScreenshot("RequestForReturnPopup");
        }
    }

    public void closeRequestForReturnPopup() {
        click(By.xpath("//a[@id='btnCancelReturnRequesMgmt']"), "Request For Return Cancel", 20);
    }

    public void saveRequestForReturnViaMoreIcon() {
        click(By.xpath("//a[@id='btnSaveReturnForRequest']"), "Request For Return Save", 20);
        waitForLoader(60);
    }

    public void enterQuantityInRequestForReturn(String quantity) {
        if (quantity.equalsIgnoreCase("Same")) {
            String actualProductCount = findElementPresence(By.xpath("//table[@id='example']//tbody//tr[1]//td[4]/span/span"), 20).getText().trim();
            enter(By.xpath("//input[@id='NumberOfItems']"), actualProductCount, "quantity", 20);
        } else {
            enter(By.xpath("//input[@id='NumberOfItems']"), quantity, "quantity", 20);
        }
    }

    public void verifyMandatoryErrorRequestForReturn() {
        WebElement validationMessage = findElementVisibility(By.xpath("//input[@id='NumberOfItems']/parent::div/span"), 5);
        if (validationMessage != null) {
            getTest().log(LogStatus.PASS, "Mandatory field error message is displayed as expected when click on the save button with out entering quantity in Request for Return popup");
            logger.info("Mandatory field error message is displayed as expected when click on the save button with out entering quantity in Request for Return popup");
        } else {
            getTest().log(LogStatus.FAIL, "Mandatory field error message is not displayed when click on the save button with out entering quantity in Request for Return popup");
            logger.info("Mandatory field error message is not displayed when click on the save button with out entering quantity in Request for Return popup");
            takeScreenshot("MandatooryFieldMessage");
        }
    }

    public void verifyQuantityFieldForUniqueNameInRequestForQuantity() {
        String quantityField = getAtribute(By.xpath("//input[@id='NumberOfItems']"), "disabled", 20);
        if (quantityField.equalsIgnoreCase("true")) {
            getTest().log(LogStatus.PASS, "The quantity field is disabled in the request for return popup for the unique name product");
            logger.info("The quantity field is disabled in the request for return popup for the unique name product");
        } else {
            getTest().log(LogStatus.FAIL, "The quantity field is enabled in the request for return popup for the unique name product");
            logger.info("The quantity field is enabled in the request for return popup for the unique name product");
        }
    }

    public void clickDateField() {
        click(By.xpath("//input[@id='ReturnUntil']/parent::div/div"), "Date Field", 20);
    }

    public void verifyPastDateSelectionInReturnUntilField() {
        String day = deployProductPage.selectDate("Old");
        if (day.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The Past date for the Return Until field is disabled as expected");
            logger.info("The Past date for the Return Until field is disabled as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Past date for the Return Until field is enabled");
            logger.info("The Past date for the Return Until field is enabled");
        }
    }

    public void enterRequestForReturnCommentViaMoreIcon() {
        enter(By.xpath("//textarea[@id='Comment']"), prop.getProperty("productCodeAlphaNumeric"), "Request For Return comment", 20);
    }

    public void verifyCommentInRequestForReturnViaMoreIcon() {
        String actualComment = getAtribute(By.xpath("//textarea[@id='Comment']"), "value", 5);
        if (actualComment.equalsIgnoreCase(prop.getProperty("productCodeAlphaNumeric"))) {
            getTest().log(LogStatus.PASS, "User can able to enter the comment in the request for return popup");
            logger.info("User can able to enter the comment in the request for return popup");
        } else {
            getTest().log(LogStatus.FAIL, "User can able to enter the comment in the request for return popup");
            logger.info("User can able to enter the comment in the request for return popup");
            takeScreenshot("RequestForReturnComment");
        }
    }

    public void clickAddProductToAssign() {
        click(By.xpath("//a[@id='ancCreateJob']"), "Add Product to Assign", 20);
        currentTimeToTest = new SimpleDateFormat("hh:mm a").format(new Date());
    }

    public void verifyPreDefinedStartAndEndTime() {
        String startTimeText = getAtribute(By.xpath("//input[@id='StartTime']"), "value", 20);
        String tillTimeText = getAtribute(By.xpath("//input[@id='EndTime']"), "value", 20);
        if (!startTimeText.equals("") && !tillTimeText.equals("")) {
            getTest().log(LogStatus.PASS, "The Start Time and End Time fields are pre populated with " + startTimeText + " and " + tillTimeText + " respectively");
            logger.info("The Start Time and End Time fields are pre populated with " + startTimeText + " and " + tillTimeText + " respectively");
            verifyCurrentTimeInStartAndEndField(startTimeText, tillTimeText);
        } else {
            String fieldName = startTimeText.equals("") && tillTimeText.equals("") ? "Both" : startTimeText.equals("") ? "Start Time" : "End Time";
            getTest().log(LogStatus.FAIL, "The " + fieldName + " field is not pre populated with any value");
            logger.info("The " + fieldName + " field is not pre populated with any value");
        }
    }

    public void verifyCurrentTimeInStartAndEndField(String actualStartTime, String actualEndTime) {
        String expectedTime = currentTimeToTest;

        if (actualStartTime.equalsIgnoreCase(expectedTime) && actualEndTime.equalsIgnoreCase(expectedTime)) {
            getTest().log(LogStatus.PASS, "The Start time and End time fields are pre populated with current time as expected");
            logger.info("The Start time and End time fields are pre populated with current time as expected");
        } else {
            String fieldName = !actualStartTime.equals(expectedTime) && !actualEndTime.equals(expectedTime) ? "Both" : !actualStartTime.equals(expectedTime) ? "Start Time" : "End Time";
            getTest().log(LogStatus.PASS, "The " + fieldName + " field is not pre populated with current time");
            logger.info("The " + fieldName + " field is not pre populated with current time");
        }
    }

    public void verifyPreDefinedAssignedFromTIllDates() {
        String dateOfAssignFieldText = getAtribute(By.xpath("//input[@id='AssignedFrom']"), "value", 20);
        String dateOfAssignTillFieldText = getAtribute(By.xpath("//input[@id='AssignedTill']"), "value", 20);
        if (!dateOfAssignFieldText.equals("") && !dateOfAssignTillFieldText.equals("")) {
            getTest().log(LogStatus.PASS, "The Date of Assigned From and Date of Assigned Till fields are pre populated with " + dateOfAssignFieldText + " and " + dateOfAssignTillFieldText + " respectively");
            logger.info("The Date of Assigned From and Date of Assigned Till fields are pre populated with " + dateOfAssignFieldText + " and " + dateOfAssignTillFieldText + " respectively");
            verifyCurrentDatesInAssignedFromTIllField(dateOfAssignFieldText, dateOfAssignTillFieldText);
        } else {
            String fieldName = dateOfAssignFieldText.equals("") && dateOfAssignTillFieldText.equals("") ? "Both" : dateOfAssignFieldText.equals("") ? "Date Of Assigned" : "Date Of Assigned Till";
            getTest().log(LogStatus.FAIL, "The " + fieldName + " field is not pre populated with any value");
            logger.info("The " + fieldName + " field is not pre populated with any value");
        }
    }

    public void verifyCurrentDatesInAssignedFromTIllField(String fromDate, String tillDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        String expectedStartDate = sdf.format(new Date());
        long lTime = new Date().getTime() + 7 * 24 * 60 * 60 * 1000;
        Date seventhDay = new Date(lTime);
        String expectedTillDate = sdf.format(seventhDay);

        if (fromDate.equals(expectedStartDate) && tillDate.equals(expectedTillDate)) {
            getTest().log(LogStatus.PASS, "The Assigned from and Assigned till fields are pre populated with current date and current date + 7 days respectively");
            logger.info("The Assigned from and Assigned till fields are pre populated with current date and current date + 7 days respectively");
        } else {
            String fieldName = !fromDate.equals(expectedStartDate) && !tillDate.equals(expectedTillDate) ? "Both" : !fromDate.equals(expectedStartDate) ? "Date of Assigned" : "Date of Assigned Till";
            String date = !fromDate.equals(expectedStartDate) ? "current date" : "current date + 7";
            getTest().log(LogStatus.FAIL, "The " + fieldName + " field is not pre populated with " + date);
            logger.info("The " + fieldName + " field is not pre populated with " + date);
            takeScreenshot("AssignedDatePrepopulate");
        }
    }

    public void expandDepartmentButton() {
        WebElement departmentField = findElementClickable(By.xpath("//label[@for='Departments']//parent::div//button[contains(@class,'custom-select')]"), 10);
        if (departmentField != null) {
            getTest().log(LogStatus.PASS, "The Department dropdown is present as clickable");
            logger.info("The Department dropdown is present as clickable");
            click(By.xpath("//label[@for='Departments']//parent::div//button[contains(@class,'custom-select')]"), "Department field", 20);
        } else {
            getTest().log(LogStatus.FAIL, "The Department dropdown is not present as clickable");
            logger.info("The Department dropdown is not present as clickable");
        }
    }

    public void verifyExpandedDepartmentsMenu() {
        WebElement expandedMenuItems = findElementVisibility(By.xpath("//label[@for='Departments']//parent::div//ul[contains(@class,'multiselect-container')]"), 5);
        if (expandedMenuItems != null) {
            getTest().log(LogStatus.PASS, "The department dropdown values are displayed as expected when click on the department dropdown field");
            logger.info("The department dropdown values are displayed as expected when click on the department dropdown field");
        } else {
            getTest().log(LogStatus.FAIL, "The department dropdown values are not displayed when click on the department dropdown field");
            logger.info("The department dropdown values are not displayed when click on the department dropdown field");
            takeScreenshot("ExpandedMenu");
        }
    }

    public void verifySearchBoxInDepartmentDropdown() {
        WebElement searchBoxUnderDropdown = findElementVisibility(By.xpath("//label[text()='Departments']//parent::div//ul//input[contains(@class,'multiselect-search')]"), 10);
        if (searchBoxUnderDropdown != null) {
            getTest().log(LogStatus.PASS, "The department dropdown displays the search box as expected");
            logger.info("The department dropdown displays the search box as expected");
        } else {
            getTest().log(LogStatus.PASS, "The department dropdown not displays the search box");
            logger.info("The department dropdown not displays the search box");
            takeScreenshot("searchBoxInDD");
        }
    }

    public void verifyDepartmentNamesInDD() {
        int iteration = 0;
        List<WebElement> actualDepartmentLocators = findMultipleElement(By.xpath("//label[@for='Departments']//parent::div//ul//li//input[@class='checkmultiselect']/parent::label"), 20);
        First:
        for (String departmentName : departmentNameList) {
            for (WebElement actualDepartmentLocator : actualDepartmentLocators) {
                iteration++;
                String actualDepartment = actualDepartmentLocator.getText().trim();
                if (actualDepartment.equalsIgnoreCase(departmentName)) {
                    getTest().log(LogStatus.PASS, "The " + departmentName + " department is displayed in the department page as well as in the department dropdown");
                    logger.info("The " + departmentName + " department is displayed in the department page as well as in the department dropdown");
                    iteration = 0;
                    break;
                } else if (iteration == actualDepartmentLocators.size()) {
                    getTest().log(LogStatus.FAIL, "The " + departmentName + " department ss displayed in the department page but it is not displayed in the department dropdown");
                    logger.info("The " + departmentName + " department ss displayed in the department page but it is not displayed in the department dropdown");
                    takeScreenshot("DepartmentNameUnderDD");
                }
            }
        }
    }

    public void selectMultipleDepartmentFromList() {
        for (int i = 0; i < 2; i++) {
            String departmentNameToBeSelected = departmentNameList.get(i);
            staticWait(3000);
            clickByJavascript(By.xpath("//label[@for='Departments']//parent::div//ul//li//label[normalize-space(text())='" + departmentNameToBeSelected + "']"), departmentNameToBeSelected, 10);
            waitForLoader(60);
            staticWait(3000);
        }
    }

    public void selectSingleDepartmentFromList() {
        String departmentNameToBeSelected = departmentNameToVerify;
        enter(By.xpath("//label[@for='Departments']//parent::div//input[@class='form-control multiselect-search']"), departmentNameToBeSelected, "Department search field", 20);
        click(By.xpath("//label[@for='Departments']//parent::div//ul//li//label[normalize-space(text())='" + departmentNameToBeSelected + "']"), departmentNameToBeSelected, 10);
        waitForLoader(60);
    }

    public void clearSelectedDepartment() {
        click(By.xpath("//label[@for='Departments']//parent::div//button[@title='Refresh']"), "Department Clear", 20);
    }

    public void verifySelectedDepartmentName() {
        String actualSelectedDepartmentName = getAtribute(By.xpath("//label[@for='Departments']//parent::div//button[contains(@class,'btn btn-default')]"), "title", 20);
        for (int i = 0; i < 2; i++) {
            String expectedDepartmentName = departmentNameList.get(i);
            if (actualSelectedDepartmentName.contains(expectedDepartmentName)) {
                getTest().log(LogStatus.PASS, "The " + expectedDepartmentName + " is selected in the dropdown and the selected value is displayed in the department dropdown field");
                logger.info("The " + expectedDepartmentName + " is selected in the dropdown and the selected value is displayed in the department dropdown field");
            } else {
                getTest().log(LogStatus.FAIL, "The " + expectedDepartmentName + " is selected in the dropdown but the selected value is not displayed in the department dropdown field");
                logger.info("The " + expectedDepartmentName + " is selected in the dropdown but the selected value is not displayed in the department dropdown field");
                takeScreenshot("SelectedDepartmentName");
            }
        }
    }

    public void expandSelectUserField() {
        click(By.xpath("//select[@id='IssuedTo']"), "Select User dropdown", 10);
    }

    public void verifySelectUserDropDownValues() {
        List<WebElement> DropdownValuesLocators = findMultipleElement(By.xpath("//select[@id='IssuedTo']//option"), 20);
        if (DropdownValuesLocators.size() - 1 == usersInTheDepartment.size()) {
            for (String expectedUserName : usersInTheDepartment) {
                for (WebElement dropdownValue : DropdownValuesLocators) {
                    String actualUserName = dropdownValue.getText().trim();
                    if (!actualUserName.equalsIgnoreCase("Select")) {
                        if (actualUserName.equalsIgnoreCase(expectedUserName)) {
                            getTest().log(LogStatus.PASS, "The " + expectedUserName + " user is displayed in the user list dropdown when department is selected with " + departmentNameList.get(0));
                            logger.info("The " + expectedUserName + " user is displayed in the user list dropdown when department is selected with " + departmentNameList.get(0));
                        } else {
                            getTest().log(LogStatus.FAIL, "The " + expectedUserName + " user is in " + departmentNameList.get(0) + " department but the user is not displayed in the dropdown when department is selected with " + departmentNameList.get(0));
                            logger.info("The " + expectedUserName + " user is in " + departmentNameList.get(0) + " department but the user is not displayed in the dropdown when department is selected with " + departmentNameList.get(0));
                        }
                    }
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "The No.Of users in the department is not matched with the No.of users in the dropdown");
            logger.info("The No.Of users in the department is not matched with the No.of users in the dropdown");
            takeScreenshot("UserNameDropdown");
        }
    }

    public void verifyAllSelectUserDropDownValues() {
        int iteration = 0;
        List<WebElement> DropdownValuesLocators = findMultipleElement(By.xpath("//select[@id='IssuedTo']//option"), 20);
        for (String expectedUserName : allUserNameList) {
            for (WebElement dropdownValue : DropdownValuesLocators) {
                iteration++;
                String actualUserName = dropdownValue.getText().trim();
                if (!actualUserName.equalsIgnoreCase("Select")) {
                    if (actualUserName.equalsIgnoreCase(expectedUserName)) {
                        getTest().log(LogStatus.PASS, "The " + expectedUserName + " user is displayed in the user list and displayed in the user list dropdown list");
                        logger.info("The " + expectedUserName + " user is displayed in the user list and displayed in the user list dropdown list");
                    } else if (iteration == DropdownValuesLocators.size()) {
                        getTest().log(LogStatus.FAIL, "The " + expectedUserName + " user is displayed in the user list but not displayed in the user list dropdown list");
                        logger.info("The " + expectedUserName + " user is displayed in the user list but not displayed in the user list dropdown list");
                        takeScreenshot("UserNameField");
                    }
                }
            }
        }
    }

    public void clickDateField(String date) {
        click(By.xpath("//input[@id='" + date + "']/parent::div/div"), date, 20);
    }

    public void verifyPastDateSelection(String date) {
        String dayClass = deployProductPage.selectDate("Old");
        if (dayClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The past date is not selectable for the " + date + " field");
            logger.info("The past date is not selectable for the " + date + " field");
        } else {
            getTest().log(LogStatus.FAIL, "The past date is selectable for the " + date + " field");
            logger.info("The past date is selectable for the " + date + " field");
            takeScreenshot(date + "Field");
        }
    }

    public void enterRemark(String remarkText) {
        if (remarkText.equalsIgnoreCase("")) {
            enter(By.xpath("//textarea[@id='Remarks']"), prop.getProperty("description250Character"), "Product Assignment Remark", 20);
        } else {
            enter(By.xpath("//textarea[@id='Remarks']"), remarkText, "Product Assignment Remark", 20);
        }
    }

    public void verifyProductAssignmentRemark(String expectedRemark, String type) {
        if (expectedRemark.equalsIgnoreCase("")) {
            expectedRemark = prop.getProperty("description250Character");
        }
        String actualRemark = getAtribute(By.xpath("//textarea[@id='Remarks']"), "value", 20);
        if (!actualRemark.equalsIgnoreCase(expectedRemark)) {
            getTest().log(LogStatus.PASS, "The Remark field accepts the " + type + " words and not accepts more than 150 character");
            logger.info("The Remark field accepts the " + type + " words and not accepts more than 150 character");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field not accepts the " + type + " words or it accepts more than 150 character");
            logger.info("The Remark field not accepts the " + type + " words or it accepts more than 150 character");
            takeScreenshot("RemarkField");
        }
    }

    public void expandNotificationUserField() {
        click(By.xpath("//label[text()='Notification Users']//parent::div//button[contains(@class,'custom-select')]"), "Notification User dropdown", 20);
    }

    public void verifyAllUserNamesUnderNotificationUser() {
        int iteration = 0;
        List<WebElement> actualNotificationUserLocator = findMultipleElement(By.xpath("//label[text()='Notification Users']//parent::div//ul//input[@class='checkmultiselect']//parent::label"), 20);
        for (String expectedUserName : allUserNameList) {
            for (WebElement actualUserNameLocator : actualNotificationUserLocator) {
                iteration++;
                String actualName = actualUserNameLocator.getText().trim();
                if (actualName.contains(expectedUserName)) {
                    getTest().log(LogStatus.PASS, "The " + expectedUserName + " user is available in the system and displayed under notification user dropdown ");
                    logger.info("The " + expectedUserName + " user is available in the system and displayed under notification user dropdown ");
                    iteration = 0;
                    break;
                } else if (iteration == actualNotificationUserLocator.size()) {
                    iteration = 0;
                    getTest().log(LogStatus.FAIL, "The " + expectedUserName + " user is available in the system but it is not displayed under notification user dropdown ");
                    logger.info("The " + expectedUserName + " user is available in the system but it is not displayed under notification user dropdown ");
                    takeScreenshot("NotificationUserList");
                }
            }
        }
    }

    public void selectMultipleNotificationUserFromList(String namesToSelect) {
        click(By.xpath("//label[normalize-space(text())='" + namesToSelect + "']"), namesToSelect, 10);
    }

    public void selectAndVerifyNotificationUserName() {
        int iteration = 0;
        List<WebElement> namesInTheDropdown = findMultipleElement(By.xpath("//label[@for='NotificationUsers']//parent::div//input[@class='checkmultiselect']//parent::label"), 20);
        for (WebElement nameInDD : namesInTheDropdown) {
            iteration++;
            selectMultipleNotificationUserFromList(nameInDD.getText().trim());
            if (iteration == 2) {
                break;
            }
        }
        String actualSelectedDepartmentName = getAtribute(By.xpath("//label[@for='NotificationUsers']//parent::div//button[contains(@class,'btn btn-default')]"), "title", 20);
        for (int i = 0; i < 2; i++) {
            String expectedNotificationUserName = namesInTheDropdown.get(i).getText().trim();
            if (actualSelectedDepartmentName.contains(expectedNotificationUserName)) {
                getTest().log(LogStatus.PASS, "The " + expectedNotificationUserName + " is selected in the dropdown and the selected value is displayed in the notification user dropdown field");
                logger.info("The " + expectedNotificationUserName + " is selected in the dropdown and the selected value is displayed in the notification user dropdown field");
            } else {
                getTest().log(LogStatus.FAIL, "The " + expectedNotificationUserName + " is selected in the dropdown but the selected value is not displayed in the notification user dropdown field");
                logger.info("The " + expectedNotificationUserName + " is selected in the dropdown but the selected value is not displayed in the notification user dropdown field");
                takeScreenshot("SelectedDepartmentName");
            }
        }
    }

    public void verifySearchBoxInNotificationUserDropdown() {
        WebElement searchBoxUnderDropdown = findElementVisibility(By.xpath("//label[text()='Notification Users']//parent::div//ul//input[contains(@class,'multiselect-search')]"), 10);
        if (searchBoxUnderDropdown != null) {
            getTest().log(LogStatus.PASS, "The Notification Users dropdown displays the search box as expected");
            logger.info("The Notification Users dropdown displays the search box as expected");
        } else {
            getTest().log(LogStatus.PASS, "The Notification Users dropdown not displays the search box");
            logger.info("The Notification Users dropdown not displays the search box");
            takeScreenshot("searchBoxInDD");
        }
    }

    public void cancelProductAssignment() {
        click(By.xpath("//div[contains(@class,'text-right')]//a[contains(@class,'btn-danger') and text()='Cancel']"), "Product Assignment Cancel", 20);
        waitForLoader(60);
    }

    public void clickAssignSaveButton() {
        click(By.xpath("//div[contains(@class,'text-right')]//a[contains(@class,'btn-success') and text()='Save ']"), "Product Assignment Save", 20);
        waitForLoader(60);
    }

    public void closeErrorAlertPopup() {
        click(By.xpath("//button[@title='OK']"), "Error alert popup ok", 20);
    }

    public void verifyProductAssignmentListingPage() {
        WebElement productAssignmentList = findElementVisibility(By.xpath("//table[@id='example']"), 20);
        if (productAssignmentList != null) {
            getTest().log(LogStatus.PASS, "The Product Assignment listing page is displayed when click on the cancel button in the product assignment screen");
            logger.info("The Product Assignment listing page is displayed when click on the cancel button in the product assignment screen");
        } else {
            getTest().log(LogStatus.PASS, "The Product Assignment listing page is not displayed when click on the cancel button in the product assignment screen");
            logger.info("The Product Assignment listing page is not displayed when click on the cancel button in the product assignment screen");
            takeScreenshot("ProductAssignmentListingScreen");
        }
    }

    public void enterEmptyDateField(String fieldName) {
        enter(By.xpath("//input[@id='" + fieldName + "']"), "", fieldName, 20);
    }

    public void verifySelectUserErrorMessage() {
        WebElement selectUserErrorMessage = findElementVisibility(By.xpath("//select[@id='IssuedTo']/parent::div/span[@class='invalid-feedback']"), 10);
        verifyErrorMessages(selectUserErrorMessage, "Select User");
    }

    public void verifyAssignedFromErrorMessage() {
        WebElement assignedFromErrorMessage = findElementVisibility(By.xpath("//input[@id='AssignedFrom']/parent::div/span[@class='invalid-feedback']"), 10);
        verifyErrorMessages(assignedFromErrorMessage, "Assigned From");
    }

    public void verifyAssignedTillErrorMessage() {
        WebElement assignedTillErrorMessage = findElementVisibility(By.xpath("//input[@id='AssignedTill']/parent::div/span[@class='invalid-feedback']"), 10);
        verifyErrorMessages(assignedTillErrorMessage, "Assigned Till");
    }

    public void verifyStartTimeErrorMessage() {
        WebElement startTimeErrorMessage = findElementVisibility(By.xpath("//input[@id='StartTime']/parent::div/span[@class='invalid-feedback']"), 10);
        verifyErrorMessages(startTimeErrorMessage, "Start Time");
    }

    public void verifyEndTImeErrorMessage() {
        WebElement endTimeErrorMessage = findElementVisibility(By.xpath("//input[@id='EndTime']/parent::div/span[@class='invalid-feedback']"), 10);
        verifyErrorMessages(endTimeErrorMessage, "End Time");
    }

    public void verifyErrorMessages(WebElement errorMessage, String fieldName) {
        if (errorMessage != null) {
            getTest().log(LogStatus.PASS, "Error message for " + fieldName + " is displayed as expected when click on the save button if the " + fieldName + " field is empty");
            logger.info("Error message for " + fieldName + " is displayed as expected when click on the save button if the " + fieldName + " field is empty");
        } else {
            getTest().log(LogStatus.FAIL, "Error message for " + fieldName + " is not displayed when click on the save button if the " + fieldName + " field is empty");
            logger.info("Error message for " + fieldName + " is not displayed when click on the save button if the " + fieldName + " field is empty");
            takeScreenshot(fieldName + "ErrorMessage");
        }
    }

    public void selectUserToAssign(String userType) {
        if (userType.equalsIgnoreCase("NormalUser")) {
            expandSelectUserField();
            click(By.xpath("//div//select[@id='IssuedTo']//option[contains(text(),'" + prop.getProperty("secondUser") + "')]"), prop.getProperty("secondUser") + " User", 20);
        }
    }

    public void selectLocation(String locationName) {
        scrollToWebelement(By.xpath("//div[@data-toggle='dropdown']"), "Location Dropdown");
        click(By.xpath("//div[@data-toggle='dropdown']"), "Location Dropdown", 20);
        if (locationName.equalsIgnoreCase("")) {
            click(By.xpath("(//span[@class='float-left textvalue'])[1]"), "Location Value", 25);
        } else {
            click(By.xpath("//a[@data-text='" + productLocationsToAssign.get(0) + "']"), productLocationsToAssign.get(0), 20);
        }
    }

    public void closePleaseAddProdPopup() {
        click(By.xpath("//button[@id='closenotifymessage']"), "Please Add Prod Popup close", 30);
        waitForLoader(60);
    }

    public void verifyErrorMessageForProductType() {
        WebElement errorMessageLocator = findElementVisibility(By.xpath("//label[text()='Product Type:']//parent::div/span"), 10);
        verifyErrorMessages(errorMessageLocator, "Product Type");
    }

    public void verifyErrorMessageForProductName() {
        WebElement errorMessageLocator = findElementVisibility(By.xpath("//label[text()='Product Name:']//parent::div/span"), 10);
        verifyErrorMessages(errorMessageLocator, "Product Name");
    }

    public void verifyErrorMessageForProducts() {
        WebElement errorMessageLocator = findElementVisibility(By.xpath("//label[text()='Products:']//parent::div/span"), 10);
        verifyErrorMessages(errorMessageLocator, "Products");
    }

    public void selectProductType(String productTypeName) {
        if (productTypeName.equalsIgnoreCase("")) {
            selectValueWithIndex(By.xpath("//select[@id='AssetTypeFilter']"), 1, "Product Type", 20);
        } else {
            selectValueWithText(By.xpath("//select[@id='AssetTypeFilter']"), productTypesToAssign, "Product Type", 20);
        }
        Select select = new Select(findElementVisibility(By.xpath("//select[@id='AssetTypeFilter']"), 20));
        productTypeToGetProductName = select.getFirstSelectedOption().getText();
    }

    public void selectProductName(String productName) {
        if (productName.equalsIgnoreCase("")) {
            selectValueWithIndex(By.xpath("//select[@id='AssetCatalogFilter']"), 1, "Product Name", 20);
        } else {
            selectValueWithText(By.xpath("//select[@id='AssetCatalogFilter']"), productNamesToAssign.get(0), "Product Name", 20);
        }
        Select select = new Select(findElementVisibility(By.xpath("//select[@id='AssetCatalogFilter']"), 20));
        productNameToGetProducts = select.getFirstSelectedOption().getText();
    }

    public void enterProducts() {
        enter(By.xpath("//input[@id='txtAssetItems']"), productNameToGetProducts, "Products", 20);
        productToAssign = getText(By.xpath("//div[@class='unique_dynamicdatalist']//li[1]"), 10);
        try {
            findElementVisibility(By.xpath("//div[@class='unique_dynamicdatalist']//li[1]"), 10).click();
            getTest().log(LogStatus.PASS, "Product From dynamic Suggession is clicked");
            logger.info("Product From dynamic Suggession is clicked");
        } catch (Throwable e) {
            clickByJavascript(By.xpath("//div[@class='unique_dynamicdatalist']//li[1]"), "Product from suggestion", 10);
        }
    }

    public void clickProductSearchButton() {
        click(By.xpath("//a[@id='Searchassest']"), "Product Search", 20);
    }

    public void enterEmptyToQuantityField() {
        enter(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr//td[9]/input"), "", "Product Quantity", 20);
    }

    public void verifyQuantityFieldError() {
        WebElement errorMessageElement = findElementVisibility(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr//td[9]/input/parent::td/span"), 10);
        verifyErrorMessages(errorMessageElement, "Quantity To Assign");
    }

    public void enterBarCodeInSearchField(String barCodeValue) {
        if (barCodeValue.equalsIgnoreCase("")) {
            enter(By.xpath("//input[@id='txtBarcode']"), barCodeValues.get(0), "bar code search", 20);
        } else {
            enter(By.xpath("//input[@id='txtBarcode']"), barCodeValue, "bar code search", 20);
        }
    }

    public void clickBarCodeSearchIcon() {
        click(By.xpath("//a[@id='Searchassest_barcode']"), "Bar Code Search icon", 20);
    }

    public void verifyBarCodeMandatoryError() {
        WebElement errorMessageElement = findElementVisibility(By.xpath("//input[@id='txtBarcode']//parent::div/span"), 10);
        verifyErrorMessages(errorMessageElement, "Bar Code Search Field");
    }

    public void verifyAndCloseUnAssociatedIssue() {
        WebElement unAssociatedError = findElementVisibility(By.xpath("//div[text()='Product is not associated with the system']"), 20);
        if (unAssociatedError != null) {
            getTest().log(LogStatus.PASS, "The Error message is displayed as expected when click on the bar code search button after entering the incorrect bar code value");
            logger.info("The Error message is displayed as expected when click on the bar code search button after entering the incorrect bar code value");
            click(By.xpath("//button[@title='OK']"), "UnAssociater Error Close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "The Error message is not displayed when click on the bar code search button after entering the incorrect bar code value");
            logger.info("The Error message is not displayed when click on the bar code search button after entering the incorrect bar code value");
            takeScreenshot("BarCodeSearch");
        }
    }

    public void verifyBarCodeSearchResult() {
        waitForLoader(60);
        WebElement barcodeSearchedProduct = findElementVisibility(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr"), 20);
        if (barcodeSearchedProduct != null) {
            getTest().log(LogStatus.PASS, "The product details are displayed as expected when enter valid bar code");
            logger.info("The product details are displayed as expected when enter valid bar code");
        } else {
            getTest().log(LogStatus.FAIL, "The product details are not displayed when enter valid bar code");
            logger.info("The product details are not displayed when enter valid bar code");
            takeScreenshot("BarCodeSearchedProd");
        }
    }

    public void expandLocationField() {
        click(By.xpath("//div[@class='locationddl_designfix']/div/div"), "Location Dropdown", 20);
    }

    public void getAndVerifyActualLocationNames() {
        List<WebElement> actualLocationNames = findMultipleElement(By.xpath("//ul[@id='CompantLocationSelect']//a"), 20);
        for (WebElement actualLocation : actualLocationNames) {
            String actualName = actualLocation.getAttribute("data-original-title").trim();
            if (parentLocationNameList.contains(actualName) || childLocationNameList.contains(actualName)) {
                getTest().log(LogStatus.PASS, "The " + actualName + " is displayed in the company setup page as well as in the location dropdown in the product Assignment page");
                logger.info("The " + actualName + " is displayed in the company setup page as well as in the location dropdown in the product Assignment page");
            } else {
                getTest().log(LogStatus.FAIL, "The " + actualName + " is not displayed in the company setup page or in the location dropdown of the product Assignment page");
                logger.info("The " + actualName + " is not displayed in the company setup page or in the location dropdown of the product Assignment page");
                takeScreenshot("LocationDropdownValues");
            }
        }
    }

    public void verifySearchFieldUnderLocationDropdown() {
        WebElement searchBox = findElementVisibility(By.cssSelector("input.CompantLocationSelectresourceMagicBox"), 20);
        if (searchBox != null) {
            getTest().log(LogStatus.PASS, "The Search box is displayed under location dropdown field");
            logger.info("The Search box is displayed under location dropdown field");
        } else {
            getTest().log(LogStatus.FAIL, "The Search box is not displayed under location dropdown field");
            logger.info("The Search box is not displayed under location dropdown field");
            takeScreenshot("SearchFieldInLocation");
        }
    }

    public void enterLocationInSearchField() {
        enter(By.cssSelector("input.CompantLocationSelectresourceMagicBox"), parentLocationNameList.get(0), "Location field Search", 20);
        ;
    }

    public void selectLocationForProductAssignment(String locationName) {
        if (locationName.equalsIgnoreCase("")) {
            locationName = parentLocationNameList.get(0);
        }
        click(By.xpath("//a[@data-original-title='" + locationName + "']"), locationName, 20);
    }

    public void verifySearchedLocation() {
        List<WebElement> searchedLocations = findMultipleElement(By.xpath("//ul[@id='CompantLocationSelect']//a//parent::li"), 10);
        for (WebElement searchedLocation : searchedLocations) {
            String searchedLocationDisplay = searchedLocation.getCssValue("display");
            if (!searchedLocationDisplay.equalsIgnoreCase("none")) {
                String dataValue = searchedLocation.getAttribute("data-val");
                String searchedLocationName = findElementVisibility(By.xpath("//li[@data-val='" + dataValue + "']/a"), 5).getAttribute("data-original-title");
                if (searchedLocationName.contains(parentLocationNameList.get(0))) {
                    getTest().log(LogStatus.PASS, "The location names are displayed based on the entered location in the search field");
                    logger.info("The location names are displayed based on the entered location in the search field");
                } else {
                    getTest().log(LogStatus.FAIL, "The location name is not displayed based on the entered location in the search field");
                    logger.info("The location name is not displayed based on the entered location in the search field");
                    takeScreenshot("SearchedLocation");
                }
            }
        }
    }

    public void clickClearButtonInLocationSearch() {
        click(By.xpath("//div[contains(@class,'CompantLocationSelectdd-container')]//i[@title='Clear']"), "Location search clear", 20);
    }

    public void verifyClearedSearchInLocation() {
        String locationSearchFieldText = getAtribute(By.cssSelector("input.CompantLocationSelectresourceMagicBox"), "value", 20);
        if (locationSearchFieldText.equalsIgnoreCase("")) {
            getTest().log(LogStatus.PASS, "The entered location is cleared when click on the clear button along with the location search field");
            logger.info("The entered location is cleared when click on the clear button along with the location search field");
        } else {
            getTest().log(LogStatus.FAIL, "The entered location is not cleared when click on the clear button along with the location search field");
            logger.info("The entered location is not cleared when click on the clear button along with the location search field");
            takeScreenshot("LocationSearchClear");
        }
    }

    public void expandProductTypeField() {
        click(By.xpath("//select[@id='AssetTypeFilter']"), "Product Type field", 20);
    }

    public void verifyProductTypesInTheDropDown(String locationName) {
        int iteration = 0;
        if (locationName.equalsIgnoreCase("")) {
            locationName = parentLocationNameList.get(0);
        }
        List<WebElement> actualProductTypeLocators = findMultipleElement(By.xpath("//select[@id='AssetTypeFilter']/option"), 20);
        for (String expectedProductTypeName : productTypeNamesForProductAssignment) {
            for (WebElement actualProductLocator : actualProductTypeLocators) {
                iteration++;
                String actualLocationName = actualProductLocator.getText();
                if (!actualLocationName.equalsIgnoreCase("select")) {
                    if (actualLocationName.equalsIgnoreCase(expectedProductTypeName)) {
                        getTest().log(LogStatus.PASS, "The " + expectedProductTypeName + " location is displayed in the product type dropdown when selecting the location as " + locationName);
                        logger.info("The " + expectedProductTypeName + " location is displayed in the product type dropdown when selecting the location as " + locationName);
                        iteration = 0;
                        break;
                    } else if (iteration == actualProductTypeLocators.size()) {
                        iteration = 0;
                        getTest().log(LogStatus.FAIL, "The " + expectedProductTypeName + " location is not displayed in the product type dropdown when selecting the location as " + locationName);
                        logger.info("The " + expectedProductTypeName + " location is not displayed in the product type dropdown when selecting the location as " + locationName);
                        takeScreenshot("ProductType");
                    }
                }
            }
        }
    }

    public void verifyProductInProductField(String productTypeName) {
        int iteration = 0;
        if (productTypeName.equalsIgnoreCase("")) {
            productTypeName = productTypeNamesForProductAssignment.get(0);
        }
        List<WebElement> actualProductNameLocators = findMultipleElement(By.xpath("//select[@id='AssetCatalogFilter']/option"), 20);
        if (productNamesForProductAssignment.size() != 0) {
            for (String expectedProductName : productNamesForProductAssignment) {
                for (WebElement actualProductNameLocator : actualProductNameLocators) {
                    iteration++;
                    String actualLocationName = actualProductNameLocator.getText();
                    if (!actualLocationName.equalsIgnoreCase("select")) {
                        if (actualLocationName.equalsIgnoreCase(expectedProductName)) {
                            getTest().log(LogStatus.PASS, "The " + expectedProductName + " product is displayed in the product dropdown when selecting the product type as " + productTypeName);
                            logger.info("The " + expectedProductName + " product is displayed in the product dropdown when selecting the product type as " + productTypeName);
                            iteration = 0;
                            break;
                        } else if (iteration == actualProductNameLocators.size()) {
                            iteration = 0;
                            getTest().log(LogStatus.FAIL, "The " + expectedProductName + " product is not displayed when product type is selected as " + productTypeName);
                            logger.info("The " + expectedProductName + " product is not displayed when product type is selected as " + productTypeName);
                            takeScreenshot("ProductType");
                        }
                    }
                }
            }
        }
    }

    public void verifyCommentButtonStatus() {
        String actualClass = getAtribute(By.xpath("//table[@id='example']//tbody//tr[1]//a[@data-original-title='Comment']"), "class", 20);
        if (actualClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The comment button is disabled as expected if no comment is saved before");
            logger.info("The comment button is disabled as expected if no comment is saved before");
        } else {
            getTest().log(LogStatus.FAIL, "The comment button is not disabled if no comment is saved before");
            logger.info("The comment button is not disabled if no comment is saved before");
            takeScreenshot("CommentButton");
        }
    }

    public void enterCommentToVerifyCommentButton() {
        commentToCheckCommentButton = "Sample Test";
        enter(By.xpath("//textarea[@id='txtComment']"), commentToCheckCommentButton, "Return Comment", 20);
    }

    public void verifyEnabledCommentButton() {
        String actualClass = getAtribute(By.xpath("//table[@id='example']//tbody//tr[1]//a[@data-original-title='Comment']"), "class", 20);
        if (!actualClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The comment button is enabled as expected when comment is added to the product");
            logger.info("The comment button is enabled as expected when comment is added to the product");
            navigateToCommentPopup();
            verifyCommentInCommentPopup();
        } else {
            getTest().log(LogStatus.FAIL, "The comment button is still disabled even though comment is added to the product");
            logger.info("The comment button is still disabled even though comment is added to the product");
            takeScreenshot("CommentButton");
        }
    }

    public void navigateToCommentPopup() {
        click(By.xpath("//table[@id='example']//tbody//tr[1]//a[@data-original-title='Comment']"), "Comment Button", 0);
    }

    public void verifyCommentInCommentPopup() {
        WebElement commentPopup = findElementVisibility(By.xpath("//h5[text()='Comment']"), 30);
        if (commentPopup != null) {
            String actualComment = getText(By.xpath("//div[@class='post-description']/p"), 20);
            if (actualComment.equalsIgnoreCase(commentToCheckCommentButton)) {
                getTest().log(LogStatus.PASS, "The entered comment is displayed in the comment popup");
                logger.info("The entered comment is displayed in the comment popup");
            } else {
                getTest().log(LogStatus.FAIL, "The entered comment is not displayed in the comment popup");
                logger.info("The entered comment is not displayed in the comment popup");
                takeScreenshot("CommentInPopup");
            }
        }
    }

    public void navigateToNotReturnedPopup() {
        click(By.xpath("//table[@id='example']//tbody//tr[1]//a[@data-original-title='Not Returned']"), "Not Returned", 20);
    }

    public void enterTitleInReturnPopup() {
        findElementVisibility(By.xpath("//h5[text()='Return']"), 60);
        enter(By.xpath("//input[@id='Title']"), "Test Title", "Title", 20);
    }

    public void enterCommentInReturnPopup() {
        enter(By.xpath("//label[text()='Comment']//parent::div/textarea"), "Test Comment", "Comment", 20);
    }

    public void saveReturnPopup() {
        click(By.xpath("//a[@id='btnUpdateAssignment']"), "Return popup save", 20);
        staticWait(5000);
    }

    public void navigateToRequestForReturnPopup() {
        click(By.xpath("//table[@id='example']//tbody//tr[1]//a//span[text()='Request For Return']"), "Request For Return", 5);
    }

    public void verifyRequestAlreadySentButton() {
        WebElement requestAlreadySentButton = findElementVisibility(By.xpath("//table[@id='example']//tbody//tr[1]//a//span[text()='Request Already Sent']"), 20);
        if (requestAlreadySentButton != null) {
            getTest().log(LogStatus.PASS, "The Request Already Sent button is displayed as expected under more option if return request is already sent");
            logger.info("The Request Already Sent button is displayed as expected under more option if return request is already sent");
        } else {
            getTest().log(LogStatus.FAIL, "The Request Already Sent button is not displayed under more option even though the return request is already sent");
            logger.info("The Request Already Sent button is not displayed under more option even though the return request is already sent");
            takeScreenshot("RequestAlreadySent");
        }
    }

    public void verifyApproveRejectOption() {
        WebElement approveRejectButton = findElementVisibility(By.xpath("//table[@id='example']//tbody//tr[1]//a//span[text()='Approve/Reject Request']"), 20);
        if (approveRejectButton != null) {
            getTest().log(LogStatus.PASS, "The approve reject button is displayed under more icon if the product is returned from the assigned user");
            logger.info("The approve reject button is displayed under more icon if the product is returned from the assigned user");
        } else {
            getTest().log(LogStatus.FAIL, "The approve reject button is not displayed under more icon if the product is returned from the assigned user");
            logger.info("The approve reject button is not displayed under more icon if the product is returned from the assigned user");
            takeScreenshot("ApproveRejectIcon");
        }
    }

    public void clickApproveRejectForRetunredProd() {
        click(By.xpath("//table[@id='example']//tbody//tr[1]//a//span[text()='Approve/Reject Request']"), "Approve Reject", 20);
    }

    public void enableCheckBoxOfReturnedProduct() {
        clickCheckBoxOfProductForReturn(productsToAssign.get(0));
    }
}