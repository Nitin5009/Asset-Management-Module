package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class InTransitPage extends WebBasePage {
    WebDriver driver;
    String productName;
    String locationToSearch;
    int countInPopup;
    String transferProdName;
    String transferProdLocation;
    int transferProdQuantity;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public InTransitPage(WebDriver driver) {
        super(driver, "InTransitPage");
        this.driver = driver;
    }

    public void navigateToInTransitPage() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        deployProduct.clickManageProduct();
        clickInTransitLink();
    }

    public void clickInTransitLink() {
        click(By.cssSelector("a#ancInTransitItems"), "InTransit Link", 10);
    }

    public void verifyInTransitPage() {
        String actualValue = findElementVisibility(By.xpath("//span[@class='p-name text-white']"), 15).getText();
        if (actualValue.equals("In-Transit List")) {
            getTest().log(LogStatus.PASS, "In-Transit List Page is displayed as expected when click on the InTransit List menu");
            logger.info("In-Transit List Page is displayed as expected when click on the InTransit List menu");
        } else {
            getTest().log(LogStatus.FAIL, "In-Transit List Page is not displayed as expected when click on the InTransit Link menu");
            logger.info("In-Transit List Page is not displayed as expected when click on the InTransit List menu");
            takeScreenshot("In-Transit List Page");
        }
    }

    public void verifyBreadcrumbs() {
        WebElement breadCrumb = findElementVisibility(By.cssSelector("div#SiteMapLink"), 15);
        if (breadCrumb != null) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed as expected in the In-Transit List Page");
            logger.info("BreadCrumb is displayed as expected in the In-Transit List Page");
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed as expected in the In-Transit List Page");
            logger.info("BreadCrumb is not displayed as expected in the In-Transit List Page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void expandAllOrCollapseAll() {
        click(By.xpath("//a[text()='Expand All / Collapse All']"), "Expand All or Collapse All", 10);
    }

    public void verifyExpandAllLocation() {
        WebElement locationField = findElementVisibility(By.xpath("//div[contains(@class,'CompantLocationSelectdd')]//div[@data-toggle='dropdown']"), 20);
        if (locationField != null) {
            getTest().log(LogStatus.PASS, "Location field is displayed as expected when click on the expand all button");
            logger.info("Location field is displayed as expected when click on the expand all button");
        } else {
            getTest().log(LogStatus.FAIL, "Location field is not displayed as expected when click on the expand all button");
            logger.info("Location field is not displayed as expected when click on the expand all button");
            takeScreenshot("ExpandedLocation");
        }

    }

    public void verifyExpandAllProductName() {
        WebElement productNameFiled = findElementVisibility(By.cssSelector("input#search"), 20);
        if (productNameFiled != null) {
            getTest().log(LogStatus.PASS, "Product Name field is displayed as expected when click on the expand all button");
            logger.info("Product Name field is displayed as expected  when click on the expand all button");
        } else {
            getTest().log(LogStatus.FAIL, "Product Name field is not displayed as expected  when click on the expand all button");
            logger.info("Product Name field is not displayed as expected  when click on the expand all button");
            takeScreenshot("ExpandedProductName");
        }
    }

    public void enterProductName() {
        productName = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]"), 15);
        enter(By.cssSelector("input#search"), productName, "Product Name", 15);
    }

    public void verifySearchedProductName() {
        staticWait(1500);
        String searchProductNameResult = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]/span"), 120, 30);
        if (productName.equals(searchProductNameResult)) {
            getTest().log(LogStatus.PASS, "Searched product name is displayed as expected");
            logger.info("Searched product name is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Searched product name is not displayed as expected");
            logger.info("Searched product name is not displayed as expected");
            takeScreenshot("SearchedProductName");
        }
    }

    public void searchOption() {
        click(By.cssSelector("a#Go"), "Search Button", 15);
        waitForLoader(20);
    }

    public void clickLocationField() {
        click(By.xpath("//div[@class='btn form-control']"), "Location Field", 15);
    }

    public void selectLocation() {
        locationToSearch = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[3]"), 20);
        Actions action = new Actions(driver);
        WebElement locationDropdown = findElementVisibility(By.xpath("//ol[@class='scrollbar']"), 20);
        action.moveToElement(locationDropdown).perform();
        scrollUpDown("down");
        click(By.xpath("//a[@data-text='" + locationToSearch + "']"), locationToSearch, 20);
        scrollUpDown("up");
    }

    public void verifySelectedLocation() {
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[3]"), 20);
        List<String> sts = new ArrayList<>();
        if (elements.size() != 0) {
            for (WebElement ele : elements) {
                if (ele.getText().equals(locationToSearch)) {
                    sts.add("true");
                } else {
                    sts.add("false");
                }
            }
            if (sts.contains("false")) {
                getTest().log(LogStatus.FAIL, "Searched location are not displayed as expected");
                logger.info("Searched location are not displayed as expected");
                takeScreenshot("UserNameSearch");
            } else {
                getTest().log(LogStatus.PASS, "Searched location are displayed as expected");
                logger.info("Searched status are displayed as expected");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Searched location are not displayed as expected");
            logger.info("Searched location are not displayed as expected");
            takeScreenshot("LocationSearch");
        }
        resetButton();
    }

    public void resetButton() {
        waitForLoader(20);
        click(By.xpath("//span[@class='expand_all_filters']"), "Reset Button", 15);
    }

    public void checkSearchFiledIsEmpty() {
        String searchField;
        click(By.xpath("//span[text()='Product Name']"), "Product Name Header", 15);
        searchField = findElementVisibility(By.cssSelector("input#search"), 10).getAttribute("value");
        if (searchField != null && searchField.equals("")) {
            getTest().log(LogStatus.PASS, "The Product Name search field is Empty in the In-Transit Page after clicked the Reset button");
            logger.info("The  Product Name search field is Empty in the In-Transit Page after clicked the Reset button");

        } else {
            getTest().log(LogStatus.FAIL, "The Product Name search field is not Empty in the In-Transit Page after clicked the Reset button");
            logger.info("The Product Name search field is not Empty in the In-Transit Page after clicked the Reset button");
            takeScreenshot("ProductNameSearchField");
        }
    }

    public void checkSearchBarIsEmptyInLocationField() {
        click(By.xpath("//span[text()='Select Location']"), "Location Search field ", 30);
        String searchField = findElementVisibility(By.xpath("//div[@class='btn form-control']//span"), 10).getText();
        if (searchField.equals("Select")) {
            getTest().log(LogStatus.PASS, "The Location search field is Empty in the In-Transit Page after clicked the Reset button");
            logger.info("The Location search field is Empty in the In-Transit Page after clicked the Reset button");

        } else {
            getTest().log(LogStatus.FAIL, "The Location search field is not Empty in the In-Transit Page after clicked the Reset button");
            logger.info("The Location search field is not Empty in the In-Transit Page after clicked the Reset button");
            takeScreenshot("LocationSearchField");
        }
    }

    public void verifyInTransitPageHeaders() {
        int i = 0;
        List<WebElement> listHeader;
        List<String> expectedListHeader = new ArrayList<>();
        expectedListHeader.add("Product Name");
        expectedListHeader.add("Product Code");
        expectedListHeader.add("Location Name");
        expectedListHeader.add("Quantity");
        expectedListHeader.add("Accept/Reject");

        waitForVisibilityOfElement(By.xpath("//table[@id='tblProjectList']//tr//th//span"), 50);
        listHeader = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tr//th//span"), 30);
        for (String expected : expectedListHeader) {
            for (WebElement actual : listHeader) {
                i++;
                if (actual.getText().equals(expected)) {
                    getTest().log(LogStatus.PASS, "The \"" + expected + "\" Header is displayed in the In-Transit Page");
                    logger.info("Pass - The \"" + expected + "\" Header is displayed in the In-Transit page");
                    i = 0;
                    break;
                } else if (i == listHeader.size()) {
                    getTest().log(LogStatus.FAIL, "The \"" + expected + "\" Header is not displayed in the In-Transit Page");
                    logger.info("Fail - The \"" + expected + "\" Header is not displayed in the In-Transit page");
                    takeScreenshot(expected);
                }
            }
        }
    }

    public void addMultipleInTransitReq() {
        ProductTransferPage productTransfer = new ProductTransferPage(driver);
        productTransfer.clickProductTransfer();
        click(By.xpath("//label[text()='Location:']//parent::div//div[@type='button']"), "Location", 20);
        findMultipleElement(By.xpath("//a[contains(@class,'CompantLocationSelectdd')]"), 40).get(0).click();
        location = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 20);
        for (int i = 1; i <= 3; i++) {
            findElementPresence(By.xpath("//select[@id='AssetTypeFilter']//option[2]"), 40);
            selectValueWithIndex(By.xpath("//select[@id='AssetTypeFilter']"), 1, "Product Type", 20);
            findElementPresence(By.xpath("//select[@id='AssetCatalogFilter']//option[2]"), 40);
            int productNameCount = findMultipleElement(By.xpath("//select[@id='AssetCatalogFilter']//option"), 30).size();
            if (productNameCount <= 2) {
                findElementPresence(By.xpath("//select[@id='AssetTypeFilter']//option[2]"), 40);
                selectValueWithIndex(By.xpath("//select[@id='AssetTypeFilter']"), 2, "Product Type", 20);
            }
            findElementPresence(By.xpath("//select[@id='AssetCatalogFilter']//option[2]"), 40);
            selectValueWithIndex(By.xpath("//select[@id='AssetCatalogFilter']"), i, "Product Name", 20);
            scrollToWebelement(By.xpath("//span[text()='Search']"), "Search Title");
            click(By.xpath("//a[@id='Searchassest']"), "Search", 90);
            waitForLoader(20);
            click(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[3]//td[1]//div"), "Check box", 20);
            click(By.cssSelector("a#btn_AddRow"), "Add To List", 20);
        }
        productTransfer.selectToLocation("");
        click(By.xpath("//table[@id='tbltransferiteminfoList']//th//div[contains(@class,'custom-checkbox')]"), "Select All", 20);
        click(By.cssSelector("a#saveBtn"), "Save", 20);
        waitForLoader(20);
    }

    public void getStatusBeforeAcceptOrReject() {
        staticWait(15000);
        ProductListingPage productListing = new ProductListingPage(driver);
        transferProdName = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]//span"), 120);
        transferProdLocation = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[3]//span"), 120);
        productListing.navigateToManageProductPage();
        click(By.xpath("//span[text()='Product Name/Code ']"), "Product Name Search field", 30);
        enter(By.cssSelector("input#search"), transferProdName, "Product Name To Search", 20);
        productListing.clickSearchButton();
        productListing.navigateToSubStatusPopup();
        WebElement activeStatusCount = findElementVisibility(By.xpath("//td[text()='" + transferProdLocation + "']//parent::tr//td[text()='Active']//parent::tr//td[contains(@class,'single-action')]/span/span"), 60);
        if (activeStatusCount != null) {
            String temp = getText(By.xpath("//td[text()='" + transferProdLocation + "']//parent::tr//td[text()='Active']//parent::tr//td[contains(@class,'single-action')]/span/span"), 120);
            countInPopup = Integer.parseInt(temp);
        } else {
            countInPopup = 0;
        }
        click(By.xpath("//div[@aria-describedby='divDialogSubStatus']//button[@class='close']"), "Sub-status close", 30);
    }

    public void clickAcceptorRejectButton() {
        WebElement actualValue;
        waitForLoader(20);
        transferProdName = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]//span"), 20);
        transferProdLocation = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[3]//span"), 20);
        transferProdQuantity = Integer.parseInt(getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[4]//span"), 20));
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        click(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td//a"), "Accept or Reject button", 180);
        actualValue = findElementVisibility(By.xpath("//h5[text()='In-Transit Products']"), 15);
        if (actualValue != null) {
            getTest().log(LogStatus.PASS, "In-Transit Products Page is displayed as expected when click accept or reject button");
            logger.info("In-Transit Products Page is displayed as expected when click accept or reject button");
        } else {
            getTest().log(LogStatus.FAIL, "In-Transit Products Page is not displayed as expected when click accept or reject button");
            logger.info("In-Transit Products Page is not displayed as expected when click accept or reject button");
            takeScreenshot("InTransitProductsPage");
        }
    }

    public void selectAcceptOption() {
        chooseAcceptorReject("Accept");
    }

    public void selectRejectOption() {
        chooseAcceptorReject("Reject");
    }

    public void chooseAcceptorReject(String option) {
        if (option.equals("Accept")) {
            int acceptOptions = findMultipleElement(By.xpath("//div[text() [normalize-space()='Accept']]"), 30).size();
            for (int i = 1; i <= acceptOptions; i++) {
                clickByJavascript(By.xpath("//table[@id='tblViewInTransitList']//tr[" + i + "]//div[text() [normalize-space()='Accept']]"), "Accept Option", 15);
            }
            getTest().log(LogStatus.PASS, "Accept option is Selected");
            logger.info("Accept option is Selected");
        } else if (option.equals("Reject")) {
            int acceptOptions = findMultipleElement(By.xpath("//table[@id='tblViewInTransitList']//tr//div[text() [normalize-space()='Reject']]"), 30).size();
            for (int i = 1; i <= acceptOptions; i++) {
                clickByJavascript(By.xpath("//table[@id='tblViewInTransitList']//tr[" + i + "]//div[text() [normalize-space()='Reject']]//input"), "Reject Option", 15);
            }
            getTest().log(LogStatus.PASS, "Reject option is Selected");
            logger.info("Reject option is Selected");
        } else {
            getTest().log(LogStatus.FAIL, "In-Transit option is not Selected");
            logger.info("In-Transit option is not Selected");
            takeScreenshot("InTransitOption");
        }
    }

    public void saveAcceptorReject() {
        click(By.cssSelector("a#btnSave"), "Accept Reject save", 15);
    }

    public void navigateToSubStatusPopup() {
        ProductListingPage productListing = new ProductListingPage(driver);
        productListing.navigateToManageProductPage();
        click(By.xpath("//span[text()='Product Name/Code ']"), "Product Name Search field", 30);
        enter(By.cssSelector("input#search"), transferProdName, "Product Name To Search", 20);
        productListing.clickSearchButton();
        productListing.navigateToSubStatusPopup();
    }

    public void verifyTransferReqActiveStatus() {
        staticWait(15000);
        findElementVisibility(By.cssSelector("div#divDialogSubStatus"), 120);
        int actualCount = Integer.parseInt(getText(By.xpath("//td[text()='" + transferProdLocation + "']//parent::tr//td[text()='Active']//parent::tr//td[contains(@class,'single-action')]/span/span"), 120));
        if (actualCount == countInPopup + transferProdQuantity) {
            getTest().log(LogStatus.PASS, "Product is transferred to the requested location and status is displayed as Active in the sub status popup");
            logger.info("Product is transferred to the requested location and status is displayed as Active in the sub status popup");
        } else {
            getTest().log(LogStatus.FAIL, "Product is not transferred to the requested location and status is also not changed to Active in the sub status popup");
            logger.info("Product is not transferred to the requested location and status is also not changed to Active in the sub status popup");
            takeScreenshot("ProductTransferActive");
        }
        click(By.xpath("//div[@aria-describedby='divDialogSubStatus']//button[@class='close']"), "Sub-status close", 30);
    }

    public void verifyTransferReqRejectStatus() {
        WebElement requestedLocation = findElementVisibility(By.xpath("//td[text()='" + transferProdLocation + "']//parent::tr//td[text()='Active']//parent::tr//td[contains(@class,'single-action')]/span/span"), 20);
        if (requestedLocation != null) {
            int actualCount = Integer.parseInt(getText(By.xpath("//td[text()='" + transferProdLocation + "']//parent::tr//td[text()='Active']//parent::tr//td[contains(@class,'single-action')]/span/span"), 20));
            if (actualCount == countInPopup) {
                getTest().log(LogStatus.PASS, "Product is not transferred to the requested location when reject the transfer request");
                logger.info("Product is not transferred to the requested location when reject the transfer request");
            } else {
                getTest().log(LogStatus.FAIL, "Product is transferred to the requested location when reject the transfer request");
                logger.info("Product is transferred to the requested location when reject the transfer request");
                takeScreenshot("ProductTransferActive");
            }
            click(By.xpath("//div[@aria-describedby='divDialogSubStatus']//button[@class='close']"), "Sub-status close", 30);
        } else {
            getTest().log(LogStatus.PASS, "Product is not transferred to the requested location when reject the transfer request");
            logger.info("Product is not transferred to the requested location when reject the transfer request");
        }
    }

    public void productLocationAscendingOrder() {
        verifyLocationNameOrder("ascending");
    }

    public void productLocationDescendingOrder() {
        verifyLocationNameOrder("descending");
    }

    public void verifyPaginationFunctionality() {
        String lastRecord;
        int recordCount;
        int defaultStartRecordCount;
        int defaultEndCount;
        int totalRecordCount;
        String[] defaultPaginationText;
        String[] secondPage;
        String[] firstPage;
        String[] nextPaginationText;
        String[] previousPaginationText;
        String[] lastPagePaginationText;
        String[] firstPagePaginationText;
        defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and @val ='2']"), "Pagination 2", 20);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            secondPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageCount = Integer.parseInt(secondPage[1]);
            if (secondPageCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is not displayed as expected when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            click(By.xpath("//a[@class='page-link' and @val ='1']"), "Pagination 1", 20);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            firstPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageCount = Integer.parseInt(firstPage[1]);
            if (firstPageCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"1\" pagination button");
                logger.info("First page is displayed as expected when click on the \"1\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed as expected when click on the \"1\" pagination button");
                logger.info("First page is not displayed as expected when click on the \"1\" pagination button");
                takeScreenshot("Pagination1");
            }
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoad(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
        String selectedOption;
        int checkRecord;
        int recordCount;
        String selectRecordPage = prop.getProperty("selectRecordPage");
        selectValueWithValue(By.cssSelector("#pageSize"), selectRecordPage, "Page size", 10);
        waitForLoader(20);
        selectedOption = getText(By.xpath("//select[@id='pageSize']//option[@selected='selected']"), 20);
        checkRecord = Integer.parseInt(selectedOption);
        recordCount = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr"), 20).size();

        if (checkRecord == Integer.parseInt(selectRecordPage) && recordCount <= checkRecord) {
            getTest().log(LogStatus.PASS, "Records are displayed as expected based on the selected page size");
            logger.info("Records are displayed as expected based on the selected page size");

        } else {
            getTest().log(LogStatus.FAIL, "Records are not displayed as expected based on the selected page size");
            logger.info("Records are not displayed as expected based on the selected page size");
            takeScreenshot("Records");
        }
    }

    public void verifyLocationNameOrder(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements;
        WebElement paginationNavigator;
        WebElement paginationLast;
        List<String> expectedSortedList;
        click(By.cssSelector("#location_name"), "Product Type Name", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        elements = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[3]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoad(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[3]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (order.equals("ascending")) {
            expectedSortedList = actualSortedArray;
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product locations are sorted in ascending Order when click the Product locations header for one time");
                logger.info("Product locations are sorted in ascending Order when click the Product locations header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Product locations are not sorted in ascending Order when click the Product locations header for one time");
                logger.info("Product locations are not sorted in ascending Order when click the Product locations header for one time");
                takeScreenshot("ProductLocationsSort");
            }
        }
        if (order.equals("descending")) {
            expectedSortedList = actualSortedArray;
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product locations are sorted in descending Order when click the Product locations header for two times");
                logger.info("Product locations are sorted in descending Order when click the Product locations header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Product locations are not sorted in descending Order when click the Product locations header for two times");
                logger.info("Product locations are not sorted in descending Order when click the Product locations header for two times");
                takeScreenshot("ProductLocationsSort");
            }
        }
    }

    public void rejectAllProdBeforeVerifyWidget() {
        WebElement emptyListMessage = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[normalize-space(text())='No record(s) found']"), 5);
        while (emptyListMessage == null) {
            clickAcceptorRejectButton();
            selectRejectOption();
            saveAcceptorReject();
            waitForLoader(30);
            emptyListMessage = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[normalize-space(text())='No record(s) found']"), 5);
        }
    }

    public void getProductCountInLocation() {
        WebElement emptyListMessage = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[normalize-space(text())='No record(s) found']"), 5);
        if (emptyListMessage == null) {
            toLocation = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[3]//span"), 30);
            String productCountText = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[4]/span"), 20);
            int prodCount = Integer.parseInt(productCountText);
            inTransitProductCount = toLocation + "(" + prodCount + ")";
        } else {
            inTransitProductCount = null;
        }
    }

    public void verifyNotAcceptedProduct() {
        WebElement notAcceptedProduct = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td/span[text()='" + productToAssign + "']"), 20);
        if (notAcceptedProduct != null) {
            getTest().log(LogStatus.PASS, "The employee not accepted product is displayed in the InTransit page as expected");
            logger.info("The employee not accepted product is displayed in the InTransit page as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The employee not accepted product is not displayed in the InTransit page as expected");
            logger.info("The employee not accepted product is not displayed in the InTransit page as expected");
            takeScreenshot("NotAcceptedProduct");
        }
    }
}
