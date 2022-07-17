package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class NewProductTypeRequestPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    static String itemNameRandomValue;
    DeployProductPage deployProduct;

    public NewProductTypeRequestPage(WebDriver driver) {
        super(driver, "Product Listing page");
        this.driver = driver;
        this.deployProduct=new DeployProductPage(driver);
    }

    public void goToAssetManagementMenu()
    {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
    }
    public void clickNewProductTypeRequest() {
            scrollToWebelement(By.xpath("//a[text()='New Product Type Request']"), "New Product Type Request");
            WebElement element=findElementVisibility(By.xpath("//div[@id='scrollbar']//a[text()='New Product Type Request']"),40);
            if(element!=null) {
                click(By.xpath("//div[@id='scrollbar']//a[text()='New Product Type Request']"), "New Product Type Request", 20);
            }
            else
            {
                goToAssetManagementMenu();
                clickNewProductTypeRequest();
            }
    }

    public void verifyNewProductTypeRequestHeaders() {
        List<WebElement> headerElement;
        String actualText;
        int iteration = 0;
        String[] expectedHeaders = {"Request New Product Type", "Remove"};
        String actualHeader = getText(By.xpath("//div[@class='card-header filter-head']//h5"), 10).trim();
        String expectHeader = "SEARCH";
        if (actualHeader.equals(expectHeader)) {
            getTest().log(LogStatus.PASS, "The expected header name \"" + actualHeader + "\" is displayed in the table");
            logger.info("The expected header name \"" + actualHeader + "\" is displayed in the table");
        } else {
            getTest().log(LogStatus.FAIL, "The expected header name \"" + actualHeader + "\" is not displayed as expected");
            logger.info("The expected header name \"" + actualHeader + "\" is not displayed as expected");
            takeScreenshot("HeaderName");
        }
        headerElement = findMultipleElement(By.xpath("//span[@class='p-actions float-right']/a/span"), 30);
        for (String expectedHeader : expectedHeaders) {
            for (WebElement ele : headerElement) {
                iteration++;
                actualText = ele.getText().trim();
                if (expectedHeader.equals(actualText)) {
                    getTest().log(LogStatus.PASS, "The expected header name \"" + actualText + "\" is displayed in the table");
                    logger.info("The expected header name \"" + actualText + "\" is displayed in the table");
                    iteration = 0;
                    break;
                } else if (iteration == headerElement.size()) {
                    getTest().log(LogStatus.FAIL, "The expected header name \"" + actualText + "\" is not displayed as expected");
                    logger.info("The expected header name \"" + actualText + "\" is not displayed as expected");
                    takeScreenshot("HeaderName");
                }
            }

        }
    }

    public void verifyNewProductTypeRequestColumn() {
        String actualText;
        int iteration = 0;
        String[] expectedColumns = {"Title", "Description", "Created By", "Created At", "Status", "View Detail"};
        List<WebElement> columnElement = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tr//th//span"), 30);
        for (String expectedColumn : expectedColumns) {
            for (WebElement ele : columnElement) {
                iteration++;
                actualText = ele.getText().trim();
                if (expectedColumn.equals(actualText)) {
                    getTest().log(LogStatus.PASS, "The expected column name \"" + expectedColumn + "\" is displayed in the table");
                    logger.info("The expected column name \"" + expectedColumn + "\" is displayed in the table");
                    iteration = 0;
                    break;
                } else if (iteration == columnElement.size()) {
                    getTest().log(LogStatus.FAIL, "The expected column name \"" + actualText + "\" is not displayed as expected");
                    logger.info("The expected column name \"" + actualText + "\" is not displayed as expected");
                    takeScreenshot("ColumnName");
                }
            }

        }
    }

    public void verifyCheckboxIsSelected() {
        int iteration = 0;
        click(By.xpath("//th[@id='nonresize']/div/div"), "Checkbox", 20);
        List<WebElement> checked = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tr/td[1]/div"), 20);
        if(checked.size()!=0) {
            for (WebElement ele : checked) {
                iteration++;
                String checkBox=ele.findElement(By.name("DeleteInputs")).getAttribute("disabled");
                if (checkBox==null) {
                    String checkedAttribute = findElementPresence(By.xpath("//table[@id='tblTicketGroup']//tr["+iteration+"]/td[1]/div/input"),30).getAttribute("checked");
                    if (checkedAttribute.equalsIgnoreCase("true")) {
                        getTest().log(LogStatus.PASS, "Check box of the " + iteration + " row is enabled and selected when select all check box is selected");
                        logger.info("Check box of the " + iteration + " row is enabled and selected when select all check box is selected");
                    } else {
                        getTest().log(LogStatus.FAIL, "Check box of the " + iteration + " row is enabled but not selected when select all check box is selected");
                        logger.info("Check box of the " + iteration + " row is enabled but not selected when select all check box is selected");
                        takeScreenshot("CheckBoxNotSelected");
                    }
                }
                else
                {
                    getTest().log(LogStatus.PASS, "Check box of the " + iteration + " row is disabled");
                    logger.info( "Check box of the " + iteration + " row is disabled");
                }
            }
        }
        else
        {
            getTest().log(LogStatus.PASS, "No records found in the new product request list page");
            logger.info("No records found in the new product request list page");
        }
    }

    public void deleteTheActiveRecord() {
        findElementVisibility(By.xpath("//td/span[text()='Active']/../../td[1]/div"),40);
        String activeCheckBox = findElementPresence(By.xpath("//td/span[text()='Active']/../../td[1]/div/input"), 20).getAttribute("disabled");
        if (activeCheckBox != null) {
            createActiveStateProduct();
        }

        click(By.xpath("//td/span[text()='Active']/../../td[1]/div"), "Active item", 20);
        activeRecordName = getText(By.xpath("//td/span[text()='Active']/../../td[1]/div/./following::td[2]/span"), 10);
        click(By.id("DeleteMultiple"), "Delete", 20);

    }

    public void closeMessage() {
        click(By.xpath("//div[contains(@class,'alert-success')]//button"), "Close Success Message", 30);
    }

    public void verifyDeletedRecordDisplayed() {
        String str = activeRecordName;
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tr//td[2]/span"), 30);
        int iteration = 0;
        for (WebElement element : elements) {
            iteration++;
            if (!str.equals(element.getText().trim()) && elements.size() == iteration) {
                getTest().log(LogStatus.PASS, "Deleted Product - " + str + " is not displayed in the list as expected");
                logger.info("Deleted Product - " + str + " is not displayed in the list as expected");
            } else if (elements.size() == iteration) {
                getTest().log(LogStatus.FAIL, "Deleted Product - " + str + " is not removed from the list as expected");
                logger.info("Deleted Product - " + str + " is not removed from the list as expected");
                takeScreenshot("DeletedProduct");
            }

        }

    }

    public void newProductRequestTitleAscending() {
        click(By.id("th-TITLE"),"Title header",20);
        verifySortingOrder("ascending","Title");
    }

    public void newProductRequestTitleDescending() {
        click(By.id("th-TITLE"),"Title header",20);
        verifySortingOrder("descending","Title");
    }
    public void newProductRequestDescriptionAscending() {
        click(By.id("th-DESCRIPTION"),"Description header",20);
        verifySortingOrder("ascending","Description");
    }

    public void newProductRequestDescriptionDescending() {
        click(By.id("th-DESCRIPTION"),"Description header",20);
        verifySortingOrder("descending","Description");
    }
    public void newProductRequestCreatedByAscending() {
        click(By.id("th-CREATEDBY"),"Created By header",20);
        verifySortingOrder("ascending","Created By");
    }

    public void newProductRequestCreatedByDescending() {
        click(By.id("th-CREATEDBY"),"Created By header",20);
        verifySortingOrder("descending","Created By");
    }
    public void newProductRequestCreatedAtAscending() {
        click(By.id("th-CREATEDAT"),"Created At header",20);
        verifySortingOrder("ascending","Created At");
    }

    public void newProductRequestCreatedAtDescending() {
        click(By.id("th-CREATEDAT"),"Created At header",20);
        verifySortingOrder("descending","Created At");
    }
    public void newProductRequestStatusAscending() {
        click(By.id("th-STATUS"),"Status header",20);
        verifySortingOrder("ascending","Status");
    }

    public void newProductRequestStatusDescending() {
        click(By.id("th-STATUS"),"Status header",20);
        verifySortingOrder("descending","Status");
    }
    public List<String> dateSort(List<String> expectedSortedList) {
        Collections.sort(expectedSortedList, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("M/dd/yyyy hh:mm a");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return expectedSortedList;
    }
    public void verifySortingOrder(String order,String headerName) {
        List<String> actualSortedArray = new ArrayList<>();
        String columnNumber = headerName.equalsIgnoreCase("title")?"2":headerName.equalsIgnoreCase("description")?"3":
                headerName.equalsIgnoreCase("created by")?"4":headerName.equalsIgnoreCase("created at")?"5":
                        headerName.equalsIgnoreCase("status")?"6":"";
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tbody//tr//td["+columnNumber+"]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                ClickByView(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tbody//tr//td["+columnNumber+"]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            if(!headerName.equalsIgnoreCase("created at")) {
                expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            }
            else
            {
                expectedSortedList=dateSort(actualSortedArray);
            }
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, headerName+"s are sorted in ascending Order ");
                logger.info(headerName+"s are sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, headerName+"s are not sorted in ascending Order");
                logger.info(headerName+"s are not sorted in ascending Order ");
                takeScreenshot("Product"+headerName+"Sort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            if(!headerName.equalsIgnoreCase("created at")) {
                expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            }
            else
            {
                expectedSortedList=dateSort(actualSortedArray);
            }
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, headerName+"s are sorted in descending Order");
                logger.info(headerName+"s are sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, headerName+"s are not sorted in descending Order ");
                logger.info(headerName+"s are not sorted in descending Order");
                takeScreenshot("Product"+headerName+"Sort");
            }
        }

    }

    public void selectTitleForProductTypeRequest() {
        click(By.xpath("//a/span[text()='Title']"), "Title", 20);
        productTitleName = getText(By.xpath("//table[@id='tblTicketGroup']//tr[1]//td[2]/span"), 20);
        enter(By.id("search"), productTitleName, "Title name", 20);
    }

    public void verifySearchedTitleDisplayed() {
        String actualTitleName = getText(By.xpath("//table[@id='tblTicketGroup']//tr[1]//td[2]/span"),20, 60);
        if (actualTitleName.equals(productTitleName)) {
            getTest().log(LogStatus.PASS, "Searched Title - " + actualTitleName + " is displayed in the list as expected");
            logger.info("Searched Title - " + actualTitleName + " is displayed in the list as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Searched Title - " + actualTitleName + " is not displayed in the list as expected");
            logger.info("Searched Title - " + actualTitleName + " is not displayed in the list as expected");
            takeScreenshot("SearchedTitle");
        }
    }

    public void verifyStatusValues() {
        String[] expectedStatus = {"Active", "Rejected", "Approved"};
        String actualText;
        List<WebElement> statusElement = findMultipleElement(By.xpath("//div[@class='form-group filterscroll']/div/label"), 30);
        int iteration = 0;
        for (String expectedState : expectedStatus) {
            for (WebElement ele : statusElement) {
                iteration++;
                actualText = ele.getText().trim();
                if (actualText.equals(expectedState)) {
                    getTest().log(LogStatus.PASS, "The expected header name \"" + actualText + "\" is displayed in the table");
                    logger.info("The expected header name \"" + actualText + "\" is displayed in the table");
                    iteration = 0;
                    break;
                } else if (iteration == statusElement.size()) {
                    getTest().log(LogStatus.FAIL, "The expected header name \"" + actualText + "\" is not displayed as expected");
                    logger.info("The expected header name \"" + actualText + "\" is not displayed as expected");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void selectActiveStatusProductTypeRequest() {
        click(By.xpath("//span[text()='Status']"), "Status Search Field", 20);
        click(By.xpath("//label[text()='Active']"), "Active status", 20);
    }

    public void selectStatusForProductTypeRequest() {
        click(By.xpath("//span[text()='Status']"), "Status Search Field", 20);
        productTypeStatus = getText(By.xpath("//table[@id='tblTicketGroup']//tr//td[6]/span"), 20);
        click(By.xpath("//label[text()='" + productTypeStatus + "']"), productTypeStatus + " Status", 20);
    }

    public void verifySearchedStatusForProductTypeRequest() {
        List<String> actualSearchedStatus = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tr//td[6]/span"), 30);
        for (WebElement element : elements) {
            actualSearchedStatus.add(element.getText().trim());
        }
        WebElement paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 30);
        if (paginationNext != null) {
            click(By.xpath("//a[@class='page-link active']"),"Current pagination text",20);
            int currentPageNumber =  Integer.parseInt(getText(By.xpath("//a[@class='page-link active']"),20));
            String urlBeforeClickNext = driver.getCurrentUrl();
            getTest().log(LogStatus.INFO,"URL before click next button - "+urlBeforeClickNext);
            logger.info("URL before click next button - "+urlBeforeClickNext);
            paginationNext = findElementClickable(By.xpath("//a[@class='page-link next']"), 30);
            ClickByView((By.xpath("//a[@class='page-link next']")), "Pagination Next", 20);
            String urlAfterClickNext = driver.getCurrentUrl();
            getTest().log(LogStatus.INFO,"URL after click next button - " + urlAfterClickNext);
            logger.info("URL after click next button - "+urlAfterClickNext);
            if(urlBeforeClickNext.equalsIgnoreCase(urlAfterClickNext))
            {
                getTest().log(LogStatus.WARNING,"The next button is clicked but the application is not navigated to the next page. So directly opened next page with deep url");
                logger.info("The next button is clicked but the application is not navigated to the next page. So directly opened next page with deep url");
                driver.navigate().to(urlAfterClickNext.replace("pageNum="+currentPageNumber,"pageNum="+(currentPageNumber+1)));
            }
            while (paginationNext != null) {
                elements = findMultipleElement(By.xpath("//table[@id='tblTicketGroup']//tr//td[6]/span"), 30);
                for (WebElement element : elements) {
                    actualSearchedStatus.add(element.getText().trim());
                }
                urlBeforeClickNext = driver.getCurrentUrl();
                getTest().log(LogStatus.INFO,"URL before click next button - "+urlBeforeClickNext);
                logger.info("URL before click next button - "+urlBeforeClickNext);
                ClickByView((By.xpath("//a[@class='page-link next']")), "Pagination Next", 20);
                urlAfterClickNext = driver.getCurrentUrl();
                getTest().log(LogStatus.INFO,"URL after click next button - " + urlAfterClickNext);
                logger.info("URL after click next button - "+urlAfterClickNext);
                paginationNext = findElementClickable(By.xpath("//a[@class='page-link next']"), 10);
            }
        }
        for (String str : actualSearchedStatus) {
            if (str.equals(productTypeStatus)) {
                result.add("true");
            } else {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, "Searched status are displayed as expected when search with status field");
            logger.info("Searched status are displayed as expected when search with status field");
        } else {
            getTest().log(LogStatus.FAIL, "Searched status are not displayed as expected when search with status field");
            logger.info("Searched status are not displayed as expected when search with status field");
            takeScreenshot("SearchedStatus");
        }
    }

    public void selectTitleAndStatusFilter() {
        click(By.xpath("//a/span[text()='Title']"), "Title", 20);
        enter(By.id("search"), productTitleName, "Title name", 20);
        click(By.xpath("//span[text()='Status']"), "Status Search Field", 20);
        click(By.xpath("//label[text()='" + productTypeStatus + "']"), productTypeStatus + " Status", 20);
    }

    public void verifySearchedTitleAndStatusCombinationDisplayed() {
        WebElement actualTitle = findElementVisibility(By.xpath("//table[@id='tblTicketGroup']//tbody//tr//td[2]/span"),20);
        WebElement actualStatus = findElementVisibility(By.xpath("//table[@id='tblTicketGroup']//tbody//tr//td[6]/span"),20);
        if(actualTitle !=null && actualStatus != null) {
            if (actualTitle.getText().equalsIgnoreCase(productTitleName) && actualStatus.getText().equalsIgnoreCase(productTypeStatus)) {
                getTest().log(LogStatus.PASS, "Search results is displayed as expected based on the selected title and status");
                logger.info("Search results is displayed as expected based on the selected title and status");
            } else {
                getTest().log(LogStatus.FAIL, "Search results is not displayed when enter title as "+productTitleName+" and status is selected as "+productTypeStatus);
                logger.info( "Search results is not displayed when enter title as "+productTitleName+" and status is selected as "+productTypeStatus);
                takeScreenshot("BothCombination");
            }
        }
        else
        {
            getTest().log(LogStatus.FAIL, "Search results is not displayed when enter title as "+productTitleName+" and status is selected as "+productTypeStatus);
            logger.info( "Search results is not displayed when enter title as "+productTitleName+" and status is selected as "+productTypeStatus);
            takeScreenshot("BothCombination");
        }

    }

    public void verifyViewDetailIconClickable() {
        WebElement detailIcon = findElementClickable(By.xpath("//table[@id='tblTicketGroup']//tr//td[7]/a"), 20);
        if (detailIcon != null) {
            getTest().log(LogStatus.PASS, "Detail view icon is clickable");
            logger.info("Detail view icon is clickable");
            clickDetailIcon();
            WebElement detailPage = findElementPresence(By.xpath("//div[@id='SiteMapLink']/ol/li[text()='Requisition Detail']"), 20);
            if (detailPage != null) {
                getTest().log(LogStatus.PASS, "Detail page is displayed");
                logger.info("Detail page is displayed");
            } else {
                getTest().log(LogStatus.FAIL, "Detail page is not displayed");
                logger.info("Detail page is not displayed");
                takeScreenshot("DetailPage");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Detail view icon is not clickable");
            logger.info("Detail view icon is not clickable");
            takeScreenshot("DetailIcon");
        }
    }

    public void clickDetailIcon() {
        click(By.xpath("//table[@id='tblTicketGroup']//tr//td[7]/a"), " Detail icon", 50);
        activeRecordName = getAtribute(By.id("RequisitionTitle"), "value", 20);

    }

    public void verifyNotAbleToEditTitleName() {
        WebElement TitleTextBox = driver.findElement(By.id("RequisitionTitle"));
        String rgbFormat = TitleTextBox.getCssValue("background-color");

        if (rgbFormat.equals("rgba(233, 236, 239, 1)")) {
            getTest().log(LogStatus.PASS, "Greyed color Title text box is displayed");
            logger.info("Greyed color Title text box is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Greyed color Title text box is not displayed");
            logger.info("Greyed color Title text box is not displayed");
            takeScreenshot("GrayColorTitle");
        }
    }

    public void verifyColorForTitleTextBox() {
        WebElement TitleTextBox = driver.findElement(By.id("RequisitionTitle"));
        if (TitleTextBox.isEnabled()) {
            getTest().log(LogStatus.PASS, "Title and Description text box are editable");
            logger.info("Title text box is editable");
        } else {
            getTest().log(LogStatus.FAIL, "Title  text box is not editable");
            logger.info("Title  text box is not editable");
            takeScreenshot("TextBoxNotEdit");
        }

    }

    public void verifyNotAbleToEditDescription() {
        WebElement descriptionTextBox = driver.findElement(By.id("Description"));
        String rgbFormat = descriptionTextBox.getCssValue("background-color");

        if (rgbFormat.equals("rgba(233, 236, 239, 1)")) {
            getTest().log(LogStatus.PASS, "Greyed color Description text box is displayed");
            logger.info("Greyed color Description text box is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Greyed color Description text box is not displayed");
            logger.info("Greyed color Description text box is not displayed");
            takeScreenshot("GrayColorDescription");
        }
    }

    public void verifyColorForDescriptionTextBox() {
        WebElement descriptionTextBox = driver.findElement(By.id("Description"));
        if (descriptionTextBox.isEnabled()) {
            getTest().log(LogStatus.PASS, " Description text box is editable");
            logger.info("Description text box is editable");
        } else {
            getTest().log(LogStatus.FAIL, "Description text box is not editable");
            logger.info("Description text box is not editable");
            takeScreenshot("DescriptionTextBox");
        }
    }

    public void verifyAndClickBackToListButtonOnScreen() {
        WebElement backListButton = findElementPresence(By.xpath("//span[text()='Back to list']"), 20);
        if (backListButton != null) {
            getTest().log(LogStatus.PASS, "Back list button is displayed");
            logger.info("Back list button is displayed");
            clickBackListButton();
        } else {
            getTest().log(LogStatus.FAIL, "Back list button is not displayed");
            logger.info("Back list button is not displayed");
            takeScreenshot("BackToList");
        }
    }

    public void clickBackListButton() {
        click(By.xpath("//span[text()='Back to list']"), "Back to list", 0);
    }

    public void afterRedirectPage() {
        WebElement productListRequestScreen = findElementPresence(By.xpath("//div[@id='SiteMapLink']/ol/li[text()='New Product Type Request']"), 20);
        if (productListRequestScreen != null) {
            getTest().log(LogStatus.PASS, "New product type request screen displayed");
            logger.info("New product type request screen displayed");
        } else {
            getTest().log(LogStatus.FAIL, "New product type request screen not displayed");
            logger.info("New product type request screen not displayed");
            takeScreenshot("RequestScreen");
        }
    }

    public void verifyActiveRecordApproveButton() {
        WebElement approveButton = findElementPresence(By.xpath("//input[@value='Approve']"), 20);
        if (approveButton.isDisplayed()) {
            getTest().log(LogStatus.PASS, "Approve button is displayed");
            logger.info("Approve button is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Approve button is not displayed");
            logger.info("Approve button is not displayed");
            takeScreenshot("ApproveButton");
        }
    }

    public void verifyActiveRecordRejectButton() {
        WebElement rejectButton = findElementPresence(By.xpath("//input[@value='Reject']"), 20);
        if (rejectButton.isDisplayed()) {
            getTest().log(LogStatus.PASS, "Reject button is displayed");
            logger.info("Reject button is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Reject button is not displayed");
            logger.info("Reject button is not displayed");
            takeScreenshot("RejectButton");
        }
    }

    public void verifyActiveRecordBackListButton() {
        WebElement backListButton = findElementPresence(By.xpath("//span[text()='Back to list']"), 20);
        if (backListButton.isDisplayed()) {
            getTest().log(LogStatus.PASS, "Back list button is displayed");
            logger.info("Back list button is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Back list button is not displayed");
            logger.info("Back list button is not displayed");
            takeScreenshot("BackToListButton");
        }
    }

    public void clickApproveButton() {
        click(By.xpath("//input[@value='Approve']"), "Approve", 20);
    }

    public void clickRejectButton() {
        click(By.xpath("//input[@value='Reject']"), "Reject", 20);
    }

    public void verifyApprovedRecordStatus() {
        verifyTheStatusOfActiveRecord("Approved");
    }

    public void verifyRejectedRecordStatus() {
        verifyTheStatusOfActiveRecord("Rejected");
    }

    public void verifyActiveRecordStatus() {
        verifyTheStatusOfActiveRecord("Active");
    }

    public void verifyTheStatusOfActiveRecord(String expectedStatus) {
        String actualStatus = getText(By.xpath("//tr//td[2]/span[text()='" + activeRecordName + "']/../../td[6]/span"), 30);
        if (actualStatus.equals(expectedStatus)) {
            getTest().log(LogStatus.PASS, expectedStatus + " status is displayed as expected ");
            logger.info(actualStatus + " status is displayed");
        } else {
            getTest().log(LogStatus.FAIL, expectedStatus + " status is not displayed as expected");
            logger.info(expectedStatus + " status is not displayed as expected");
            takeScreenshot("Status");
        }
    }

    public void clickPostNewCommentButton() {
        click(By.id("postcomment"), "Post new comment", 20);
    }

    public void verifyMandatoryMarkForTitleTextBox() {
        verifyAsteriskSignForTextBox("Title", "*");
    }

    public void verifyMandatoryMarkForDescriptionTextBox() {
        verifyAsteriskSignForTextBox("Description", "*");
    }

    public void verifyAsteriskSignForTextBox(String textBox, String expectedSymbol) {
        String actualSymbol = getText(By.xpath("//div[@id='commentList']//div[@class='form-group']/label[text()='" + textBox + ":']/span"), 20);
        if (actualSymbol.equals(expectedSymbol)) {
            getTest().log(LogStatus.PASS, "Asterisk symbol is displayed for " + textBox + " is displayed as expected");
            logger.info("Asterisk symbol is displayed for " + textBox + " is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Asterisk symbol is not displayed for " + textBox + " is displayed as expected");
            logger.info("Asterisk symbol is not displayed for " + textBox + " is displayed as expected");
            takeScreenshot("MandatoryField");
        }
    }

    public void titleValidation() {
        verifyValidationMessage("Title", "Title is required");
    }

    public void descriptionValidation() {
        verifyValidationMessage("Description", "Description is required");
    }

    public void verifyValidationMessage(String textBox, String expectedMessage) {
        String actualMessage = getText(By.xpath("//span[text()='" + textBox + " is required']"), 20);

        if (actualMessage.equals(expectedMessage)) {
            getTest().log(LogStatus.PASS, textBox + " text box validation message '" + actualMessage + " ' is displayed");
            logger.info(textBox + " text box validation message '" + actualMessage + " ' is displayed");
        } else {
            getTest().log(LogStatus.FAIL, textBox + " text box validation message '" + actualMessage + " ' is not displayed");
            logger.info(textBox + " text box validation message '" + actualMessage + " ' is not displayed");
            takeScreenshot("Validation");
        }
    }

    public void verifyMoreThanMaxCharTile() {
        int size;
        enter(By.id("CommentTitle"), prop.getProperty("productName101Charc"), "Title name", 200);
        String typedValue = driver.findElement(By.id("CommentTitle")).getAttribute("value");
        size = typedValue.length();
        if (size == 100) {
            getTest().log(LogStatus.PASS, "Comment title box is not accepts the character more than 100 as expected");
            logger.info("Comment title box is not accepts the character more than 100 as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Comment title box is accepts the character more than 100");
            logger.info("Comment title box is accepts the character more than 100");
            takeScreenshot("TextBoxLimit");
        }
    }

    public void verifyMoreThanMaxCharDescription() {
        int size;
        enter(By.id("CommentDescription"), prop.getProperty("description251Character"), "Description name", 20);
        String typedValue = driver.findElement(By.id("CommentDescription")).getAttribute("value");
        size = typedValue.length();
        if (size == 250) {
            getTest().log(LogStatus.PASS, "User not allowed to enter more than 250 characters as expected");
            logger.info("User not allowed to enter more than 250 characters as expected");
        } else {
            getTest().log(LogStatus.FAIL, "User can able to enter " + size + " characters");
            logger.info("User can able to enter " + size + " characters");
            takeScreenshot("DescriptionSize");
        }
    }

    public void enterTitleName() {
        enter(By.id("CommentTitle"), prop.getProperty("productName"), "Title name", 200);
    }

    public void enterDescription() {
        enter(By.id("CommentDescription"), prop.getProperty("description"), "Description name", 20);
    }

    public void verifyCommentsSectionInUsername() {
        String userName;
        String[] actualText = getText(By.xpath("//h6/i/b"), 20).split(" ");
        String actualUsername = actualText[0];
        userName = System.getProperty("username") == null?prop.getProperty("username"):System.getProperty("username");
        String[] userNameArray = userName.split("@");
        String cap = userNameArray[0];
        String expectedUsername = cap.substring(0, 1).toUpperCase() + cap.substring(1);
        if (actualUsername.equals(expectedUsername)) {
            getTest().log(LogStatus.PASS, "User name " + actualUsername + " is displayed on the comment section");
            logger.info("User name " + actualUsername + " is displayed on the comment section");
        } else {
            getTest().log(LogStatus.FAIL, "User name " + actualUsername + " is not displayed on the comment section");
            logger.info("User name " + actualUsername + " is not displayed on the comment section");
            takeScreenshot("CommentSection");
        }

    }

    public void verifyCommentsSectionInProfilePicture() {
        WebElement profilePic = findElementPresence(By.xpath("//div[@class='card-heading d-flex align-items-center']/span/img"), 20);
        if (profilePic.isDisplayed()) {
            getTest().log(LogStatus.PASS, "Profile icon is displayed");
            logger.info("Profile icon is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Profile icon is not displayed");
            logger.info("Profile icon is not displayed");
            takeScreenshot("ProfileIcon");
        }
    }

    public void commentSectionFSA() {
        verifyCommentsSectionInPostedOnTime("few seconds ago");
    }

    public void verifyCommentsSectionInPostedOnTime(String expectedPostedTime) {
        String actualPostedTime = getText((By.xpath("//div[@class='card-detail-main']/h6/i/span")), 20);
        if (actualPostedTime.equals(expectedPostedTime)) {
            getTest().log(LogStatus.PASS, "Posted on: " + actualPostedTime);
            logger.info("Posted on: " + actualPostedTime);
        } else {
            getTest().log(LogStatus.FAIL, "Comment is not displayed in the comment section");
            logger.info("Comment is not displayed in the comment section");
            takeScreenshot("PostedTime");
        }
    }

    public void clickRequestNewProductType() {
        click(By.xpath("//span[text()='Request New  Product Type']"), "Request new product type ", 20);
    }

    public void verifyRequestNewItemScreen() {
        WebElement detailPage = findElementPresence(By.xpath("//div[@id='SiteMapLink']/ol/li[text()='Request New Product ']"), 20);
        if (detailPage != null) {
            getTest().log(LogStatus.PASS, " Request new product page is displayed");
            logger.info("Request new product page page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Request new product page page is not displayed");
            logger.info("Request new product page page is not displayed");
            takeScreenshot("NewItemPage");
        }
    }

    public void clickSaveButton() {
        click(By.xpath("//button[text()='Save']"), "Save", 20);
    }

    public void clickCancelButton() {
        click(By.xpath("//a[text()='Cancel']"), "Cancel", 20);
    }

    public void enterMoreThanMaxCharCreateTitle() {
        enter(By.id("RequisitionTitle"), prop.getProperty("productName51Charc"), "Title name", 200);
        String typedValue = driver.findElement(By.id("RequisitionTitle")).getAttribute("value");
        int size = typedValue.length();
        if (size == 50) {
            getTest().log(LogStatus.PASS, "Product type request field accepts the 50 characters as expected");
            logger.info("Product type request field accepts the 50 characters as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Product type request field accepts the " + size + " character");
            logger.info("Product type request field accepts the " + size + " character");
            takeScreenshot("TitleMaxSize");
        }
    }

    public void enterMoreThanMaxCharCreateDescription() {
        enter(By.id("Description"), prop.getProperty("description251Character"), "Description name", 200);
        clickSaveButton();
        WebElement errorMessage = findElementVisibility(By.xpath("//span[contains(@class,'invalid-feedback')]//span[contains(text(),'Only 250 characters')]"), 30);
        if (errorMessage != null) {
            getTest().log(LogStatus.PASS, "Description field shows error message as expected when enter more than 250 characters");
            logger.info("Description field shows error message as expected when enter more than 250 characters");
        } else {
            getTest().log(LogStatus.FAIL, "Description field shows error message when enter more than 250 characters");
            logger.info("Description field shows error message when enter more than 250 characters");
            takeScreenshot("DescriptionMaxChar");
        }
    }

    public void createActiveStateProduct() {
        clickRequestNewProductType();
        enterNewProductTitleName();
        enterNewProductDescription();
        clickSaveButton();
        closeMessage();

    }

    public void enterNewProductTitleName() {
        itemNameRandomValue = prop.getProperty("productName") + dateValue;
        enter(By.id("RequisitionTitle"), itemNameRandomValue, "Product Title Name", 10);
        activeRecordName = itemNameRandomValue;
    }

    public void enterNewProductDescription() {
        itemNameRandomValue = prop.getProperty("description") + dateValue;
        enter(By.id("Description"), itemNameRandomValue, "Description", 10);

    }

    public void clickUserGuide() {
        click(By.xpath("//a[text()='User Guide']"), "User guide", 20);
    }

    public void verifyUserGuideScreen() {
        WebElement userGuideScreen = findElementVisibility(By.id("mCSB_3_container"), 30);
        if (userGuideScreen.isDisplayed()) {
            getTest().log(LogStatus.PASS, "User guide screen is displayed");
            logger.info("User guide screen is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "User guide screen is not displayed");
            logger.info("User guide screen is not displayed");
            takeScreenshot("UserGuideScreen");
        }
    }

    public void verifyExpandAllTitle() {
        WebElement titleField = findElementVisibility(By.xpath("//div[@class='form-group']/input[@name='search']"), 30);
        if (titleField != null) {
            getTest().log(LogStatus.PASS, "Title field is displayed as expected when click on the expand all button");
            logger.info("Title field is displayed as expected when click on the expand all button");
        } else {
            getTest().log(LogStatus.FAIL, "Title field is not displayed as expected when click on the expand all button");
            logger.info("Title field is not displayed as expected when click on the expand all button");
            takeScreenshot("ExpandedTitle");
        }

    }

    public void verifyExpandAllStatus() {

        WebElement searchFiled = findElementVisibility(By.xpath("//div[contains(@class,'customsearchbox')]"), 30);
        if (searchFiled != null) {
            getTest().log(LogStatus.PASS, "Search field is displayed as expected when click on the expand all button");
            logger.info("Search field is displayed as expected  when click on the expand all button");
        } else {
            getTest().log(LogStatus.FAIL, "Search field is not displayed as expected  when click on the expand all button");
            logger.info("Search field is not displayed as expected  when click on the expand all button");
            takeScreenshot("ExpandedSearch");
        }
    }
}
