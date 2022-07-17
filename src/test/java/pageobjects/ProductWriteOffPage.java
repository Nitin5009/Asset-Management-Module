package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testcases.DeployProduct;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class ProductWriteOffPage extends WebBasePage {

    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    DeployProductPage deployProductPage;
    AddCheckOutRequestPage addCheckOutPage;
    CheckOutRequestPage checkOutRequestPage;
    MyProductPage myProductPage;
    CompanySetupPage companySetupPage;
    ProductTransferPage productTransferPage;
    ProductContainerPage productContainerPage;
    ProductTypePage productTypePage;
    List<String> productsNameToValidate = new ArrayList<>();
    String locationNameToSearch;
    String productCodeToSearch;
    String productNameToSearch;
    String productTobeAddInWriteOff;
    String productTypeNameForProduct;
    String barcodeValue;
    String productType;
    String barCodeProduct;
    int countBeforeReset;

    public ProductWriteOffPage(WebDriver driver) {
        super(driver, "Product Write Off Page");
        this.deployProductPage = new DeployProductPage(driver);
        this.addCheckOutPage = new AddCheckOutRequestPage(driver);
        this.myProductPage = new MyProductPage(driver);
        this.companySetupPage = new CompanySetupPage(driver);
        this.checkOutRequestPage = new CheckOutRequestPage(driver);
        this.productTransferPage = new ProductTransferPage(driver);
        this.productTypePage = new ProductTypePage(driver);
        this.productContainerPage = new ProductContainerPage(driver);
    }

    public void openProductWriteOffPage() {
        waitForLoader(20);
        staticWait(1500);
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        WebElement productWriteOffMenu = findElementPresence(By.xpath("//a[text()='Product Write Off']"), 20);
        if (productWriteOffMenu != null) {
            scrollToWebelement(By.xpath("//a[text()='Product Write Off']"), "Product Write Off menu");
            click(By.xpath("//a[text()='Product Write Off']"), "Product Write Off menu", 20);
        } else {
            openProductWriteOffPage();
        }
    }

    public void verifyProductWriteOffPage() {
        verifyProductWriteOffScreen("Product Write Off menu");
    }

    public void verifySearchFilterPresence() {
        WebElement searchFilter = findElementVisibility(By.cssSelector("div.filter-head"), 20);
        if (searchFilter != null) {
            getTest().log(LogStatus.PASS, "Search Filter is displayed as expected in the product write off page");
            logger.info("Search Filter is displayed as expected in the product write off page");
        } else {
            getTest().log(LogStatus.PASS, "Search Filter is not displayed in the product write off page");
            logger.info("Search Filter is not displayed in the product write off page");
            takeScreenshot("SearchFilter");
        }
    }

    public void findAddNewButtonPresence() {
        WebElement addNewButton = findElementVisibility(By.cssSelector("a#ancCreateJob"), 20);
        if (addNewButton != null) {
            getTest().log(LogStatus.PASS, "Add new button is displayed as expected in the product write off page");
            logger.info("Add new button is displayed as expected in the product write off page");
        } else {
            getTest().log(LogStatus.PASS, "Add new button is not displayed in the product write off page");
            logger.info("Add new button is not displayed in the product write off page");
            takeScreenshot("AddNewButton");
        }
    }

    public void verifyProductWriteOffHeaders() {
        String actualHeaderName;
        int iteration = 0;
        List<WebElement> writeOffTableHeaders = findMultipleElement(By.xpath("//table[@id='tblProjectList']//th/span"), 30);
        String[] expectedHeaderNames = {"Product Code", "Product Name", "Unique Name", "Location", "Available Quantity", "Purchase Value", "Current book Value", "Salvage Cost", "Write Off Value"};
        for (String expectedHeaderName : expectedHeaderNames) {
            for (WebElement writeOffTableHeader : writeOffTableHeaders) {
                iteration++;
                actualHeaderName = writeOffTableHeader.getText().trim();
                if (expectedHeaderName.equalsIgnoreCase(actualHeaderName)) {
                    getTest().log(LogStatus.PASS, "The header name " + expectedHeaderName + " is displayed in the product write off listing table as expected");
                    logger.info("The header name " + expectedHeaderName + " is displayed in the product write off listing table as expected");
                    iteration = 0;
                    break;
                } else if (iteration == writeOffTableHeaders.size()) {
                    getTest().log(LogStatus.FAIL, "The header name " + expectedHeaderName + " is not displayed in the product write off listing table");
                    logger.info("The header name " + expectedHeaderName + " is not displayed in the product write off listing table");
                    takeScreenshot("HeaderNames");
                }
            }
        }
    }

    public void verifyBreadCrumbInProductWriteOffPage() {
        checkOutRequestPage.verifyBreadCrumbPresence("Product WriteOff Page");
    }

    public void verifyProductInPWOBreadCrumb() {
        checkOutRequestPage.clickProductBreadCrumbProductMenu();
        checkOutRequestPage.verifyProductPage("Product Write Off page");
    }

    public void verifyHomeInPWOBreadCrumb() {
        checkOutRequestPage.clickHomeBreadCrumbProductMenu();
        checkOutRequestPage.verifyHomePage("Product Write Off page");
    }

    public void expandLocationSearchField() {
        click(By.xpath("//span[contains(@class,'CompantLocationselected')]"), "Location search field", 20);
    }

    public void getLocationToSearch() {
        locationNameToSearch = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[4]/span"), 20);
    }

    public void selectLocationToSearch() {
        click(By.xpath("//a[contains(@class,'CompantLocationdd-option')]/span[text()='" + locationNameToSearch + "']"), locationNameToSearch, 20);
    }

    public void verifyLocationSearch() {
        verifySearchedResults("Location");
    }

    public void verifySearchedResults(String searchName) {
        int iteration = 0;
        List<String> actualLocationNames = new ArrayList<>();
        int columnNumber = (searchName.equalsIgnoreCase("product code")) ? 1 : (searchName.equalsIgnoreCase("product name")) ? 2 : 4;
        String searchValue = (searchName.equalsIgnoreCase("product code")) ? productCodeToSearch : (searchName.equalsIgnoreCase("product name")) ? productNameToSearch : locationNameToSearch;
        List<WebElement> actualSearchedLocations = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[" + columnNumber + "]/span"), 30);
        for (WebElement actualSearchedName : actualSearchedLocations) {
            actualLocationNames.add(actualSearchedName.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                actualSearchedLocations = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[3]//span"), 30);
                for (WebElement element : actualSearchedLocations) {
                    actualLocationNames.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 20);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }

        for (String actualName : actualLocationNames) {
            iteration++;
            if (!actualName.equalsIgnoreCase(searchValue)) {
                getTest().log(LogStatus.FAIL, actualName + " is displayed in the list when location field is selected as " + searchValue);
                logger.info(actualName + " is displayed in the list when location field is selected as " + searchValue);
                takeScreenshot("LocationNameSearch");
            } else if (iteration == actualLocationNames.size()) {
                getTest().log(LogStatus.PASS, searchName + " names are displayed as expected when " + searchName + " field is selected as " + searchValue);
                logger.info(searchName + " names are displayed as expected when " + searchName + " field is selected as " + searchValue);
            }
        }
    }

    public void expandProductNameCodeField() {
        click(By.xpath("//span[text()='Product Name / Code']"), "Product name / code field", 20);
    }

    public void getProductCodeToSearch() {
        productCodeToSearch = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]/span"), 20);
    }

    public void enterProductCodeInSearchField() {
        enterInSearchField(productCodeToSearch);
    }

    public void verifyProductCodeSearch() {
        verifySearchedResults("Product Code");
    }

    public void getProductNameToSearch() {
        productNameToSearch = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[2]/span"), 20);
    }

    public void enterProductNameInSearchField() {
        enterInSearchField(productNameToSearch);
    }

    public void enterInSearchField(String searchTerm) {
        enter(By.cssSelector("div>input#search"), searchTerm, "Product name search field", 20);
    }

    public void verifyProductNameSearch() {
        verifySearchedResults("Product Name");
    }

    public void getCountBeforeReset() {
        countBeforeReset = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 30).size();
    }

    public void verifyResetButtonFunction() {
        waitForLoad(30);
        WebElement locationFieldClass = findElementVisibility(By.xpath("//span[text()='Select Location' and @class='filter']"),5);
        WebElement prodNameOrCodeClass = findElementVisibility(By.xpath("//span[text()='Product Name / Code' and @class='filter']"),5);
        if (locationFieldClass == null && prodNameOrCodeClass == null) {
            getTest().log(LogStatus.PASS, "The searched result are reset successfully when click on the reset icon");
            logger.info("The searched result are reset successfully when click on the reset icon");
        } else {
            getTest().log(LogStatus.FAIL, "The searched result are not reset when click on the reset icon");
            logger.info("The searched result are not reset when click on the reset icon");
            takeScreenshot("ResetButton");
        }
    }

    public void clickExpandCollapseButton() {
        click(By.cssSelector("span.ancExpandAllCollapseAll"), "Expand / Collapse button", 20);
    }

    public void verifyExpandedMenu() {
        WebElement locationSearchField = findElementVisibility(By.cssSelector("div#divmultilevelselect>div>div"), 20);
        WebElement pCodePNameSearchField = findElementVisibility(By.cssSelector("div>input#search"), 20);
        if (locationSearchField != null && pCodePNameSearchField != null) {
            getTest().log(LogStatus.PASS, "The search filter section is expanded when click expand / collapse icon");
            logger.info("The search filter section is expanded when click expand / collapse icon");
        } else {
            getTest().log(LogStatus.FAIL, "The search filter section is not expanded when click expand / collapse icon");
            logger.info("The search filter section is not expanded when click expand / collapse icon");
            takeScreenshot("SearchFiledExpand");
        }
    }

    public void productWriteOffPagination() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            int secondPageEndRecordCount = Integer.parseInt(secondPaginationText[3]);
            int pageSize = Integer.parseInt(getText(By.cssSelector("#pageSize>option[selected]"), 30));
            if (secondPageStartRecordCount == defaultEndCount + 1 && secondPageEndRecordCount <= 2 * pageSize) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is not displayed as expected when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + recordCount + "]//td[3]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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

    public void navigateToCreateProductWriteOffPage() {
        click(By.cssSelector("a#ancCreateJob"), "Product Write Off Add button", 20);
    }

    public void verifyAlphaNumericValue() {
        addCheckOutPage.verifyAlphaNumericBarCodeValue("Product write off create page");
    }

    public void clickBarCodeSearch() {
        click(By.cssSelector("a#Searchassest_barcode"), "Bar code search", 20);
    }

    public void verifyBarCodeValidationMsg() {
        WebElement errorMessagePopup = findElementVisibility(By.xpath("//div[text()='Please correct the highlighted errors.']"), 20);
        if (errorMessagePopup != null) {
            click(By.xpath("//button[contains(@class,'btn-success')]"), "Error message popup close", 20);
            WebElement validationMsgForBarCode = findElementVisibility(By.xpath("//input[@id='txtBarcode']//parent::div/span"), 20);
            if (validationMsgForBarCode != null) {
                getTest().log(LogStatus.PASS, "Error message for bar code search field is displayed as expected when click search button without enter any data");
                logger.info("Error message for bar code search field is displayed as expected when click search button without enter any data");
            } else {
                getTest().log(LogStatus.FAIL, "Error message for bar code search field is not displayed when click search button without enter any data");
                logger.info("Error message for bar code search field is not displayed when click search button without enter any data");
                takeScreenshot("BarCodeValidation");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Warning popup is not displayed when click bar code search button without enter data");
            logger.info("Warning popup is not displayed when click bar code search button without enter data");
            takeScreenshot("ErrorPopup");
        }
    }

    public void getLatestContainerProductType()
    {
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        waitForLoader(20);
        List<WebElement> containerStatus = findMultipleElement(By.xpath("//table[@id='tblAsset']//tr//td[6]"), 30);
        int iteration = 0;
        for (WebElement ele : containerStatus) {
            iteration++;
            if (ele.getText().equalsIgnoreCase("Yes")) {
                WebElement txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//span"), 1);
                if (txtBoxStatus == null) {
                    txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//select//option[@selected]"), 1);
                }
                if (txtBoxStatus.getText().equalsIgnoreCase("Active")) {
                    String actualName = getText(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[4]//a"), 5);
                    click(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[4]//a"),"Product Type Open",30);
                    String selfCheckOutOption = findElementPresence(By.xpath("//input[@id='IsSelfCheckOutN']//parent::label/parent::div/input"),10).getAttribute("value");
                    if(selfCheckOutOption.equalsIgnoreCase("true"))
                    {
                        click(By.xpath("//input[@id='IsSelfCheckOutN']//parent::label/span/span[text()='Yes']"),"Self Check out Toggle",20);
                        click(By.xpath("//a[@id='btnSave']"),"Product Type Update Save",20);
                        click(By.xpath("//button[@id='closenotifymessage']"),"Product Type Update Success message close",20);
                    }
                    containerProductType.add(actualName);
                    break;
                }
            }
        }
    }
    public void createProduct() {
        getLatestContainerProductType();
        if(containerProductType.size() == 0) {
            productTypePage.createProductTypeWithUploadingImage();
            productContainerPage.getContainerProductType();
        }
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        deployProductPage.clickManageProduct();
        productContainerPage.deployProductForContainer();
        deployProductPage.openProduct();
        WebElement selectedProdType = findElementVisibility(By.cssSelector("div>select#AssetTypeId"), 20);
        Select select = new Select(selectedProdType);
        productType = select.getFirstSelectedOption().getText();
        productCode = getAtribute(By.cssSelector("div>input#ItemCode"), "value", 20);
        waitForVisibilityOfElement(By.xpath("//ul[@id='myTab']//a[normalize-space(text())='Related Information']"), 50);
        click(By.xpath("//ul[@id='myTab']//a[normalize-space(text())='Related Information']"), "related Information button", 20);
        barcodeValue = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td[6]/div/div/span[contains(@class,'d-block')]"), 20);
        barCodeProduct = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td[3]//a"), 20).trim();
    }

    public void verifyBarCodeSearch() {
        addCheckOutPage.enterInBarCode(barcodeValue);
        addCheckOutPage.clickBarCodeSearch();
    }

    public void verifySearchedBarCodeProduct() {
        String expectedSearchResult = barCodeProduct;
        String actualSearchResult = getText(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist'][1]//td[4]"), 20);
        if (expectedSearchResult.equalsIgnoreCase(actualSearchResult)) {
            getTest().log(LogStatus.PASS, "The barcode search result is displayed as expected when search with barcode");
            logger.info("The barcode search result is displayed as expected when search with barcode");
        } else {
            getTest().log(LogStatus.FAIL, "The barcode search result is not displayed when search with barcode");
            logger.info("The barcode search result is not displayed when search with barcode");
            takeScreenshot("BarCodeSearch");
        }
    }

    public void clickAddToListButton() {
        scrollToWebelement(By.cssSelector("a#btn_AddRow"), "Add To List button");
        click(By.cssSelector("a#btn_AddRow"), "Add to list button", 20);
    }

    public void verifyProductIsAddedToWriteOffList() {
        String actualProductInList = getText(By.xpath("//table[@id='tbltransferiteminfoList']//tbody//tr//td[4]"), 20);
        if (actualProductInList.equalsIgnoreCase(productTobeAddInWriteOff)) {
            getTest().log(LogStatus.PASS, "The product " + productTobeAddInWriteOff + " is added to the write off list when click on the add to list button");
            logger.info("The product " + productTobeAddInWriteOff + " is added to the write off list when click on the add to list button");
        } else {
            getTest().log(LogStatus.FAIL, "The product " + productTobeAddInWriteOff + " is not added to the write off list when click on the add to list button");
            logger.info("The product " + productTobeAddInWriteOff + " is not added to the write off list when click on the add to list button");
            takeScreenshot("ProductWriteOffList");
        }
    }

    public void closeWarningPopup() {
        WebElement errorMessagePopup = findElementVisibility(By.xpath("//div[text()='Please correct the highlighted errors.']"), 20);
        if (errorMessagePopup != null) {
            click(By.xpath("//button[@title='OK']"), "Error message popup close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Warning popup is not displayed when click search button without enter data");
            logger.info("Warning popup is not displayed when click search button without enter data");
            takeScreenshot("ErrorPopup");
        }
    }

    public void verifyMandatoryErrorMessage() {
        int iteration = 0;
        List<WebElement> mandatoryFields = new ArrayList<>();
        List<WebElement> mandatoryFieldNames = findMultipleElement(By.xpath("//span[text()='*']//parent::label"), 30);
        for (WebElement element : mandatoryFieldNames) {
            if (!element.getAttribute("style").equalsIgnoreCase("display: none;")) {
                mandatoryFields.add(element);
            }
        }
        List<WebElement> mandatoryErrorMessages = findMultipleElement(By.xpath("//span[text()='*']//parent::label//parent::div/span"), 20);
        List<String> errorMessages = new ArrayList<>();
        for (WebElement mandatoryErrorMsg : mandatoryErrorMessages) {
            errorMessages.add(mandatoryErrorMsg.getText());
        }
        for (WebElement mandatoryField : mandatoryFields) {
            String fieldName = mandatoryField.getText().split(":")[0];
            for (String errorMsg : errorMessages) {
                iteration++;
                if (errorMsg.equalsIgnoreCase(fieldName + " is Required")) {
                    getTest().log(LogStatus.PASS, "Mandatory error message for \"" + fieldName + "\" field is displayed as expected when click add to list button without enter data");
                    logger.info("Mandatory error message for \"" + fieldName + "\" field is displayed as expected when click add to list button without enter data");
                    iteration = 0;
                    break;
                } else if (iteration == errorMessages.size()) {
                    getTest().log(LogStatus.FAIL, "Mandatory error message for \"" + fieldName + "\" field is not displayed when click add to list button without enter data");
                    logger.info("Mandatory error message for \"" + fieldName + "\" field is not displayed when click add to list button without enter data");
                    takeScreenshot("MandatoryError");
                }
            }
        }
    }

    public void getLocations() {
        companySetupPage.clickFullMenu();
        companySetupPage.clickCompanySetupMenu();
        companySetupPage.clickCompanySetupSubMenu();
        companySetupPage.sideBarLocation();
        deployProductPage.getLocationsFromSetup();
    }

    public void clickLocationField() {
        click(By.cssSelector(" span.CompantLocationSelectselected"), "Location", 20);
    }

    public void verifyLocations() {
        deployProductPage.verifyLocations("parent");
        deployProductPage.verifyLocations("child");
    }

    public void findLocationSearchField() {
        WebElement locationSearch = findElementVisibility(By.cssSelector(".CompantLocationSelectresourceMagicBox"), 20);
        if (locationSearch != null) {
            getTest().log(LogStatus.PASS, "Search box is displayed inside the location dropdown");
            logger.info("Search box is displayed inside the location dropdown");
        } else {
            getTest().log(LogStatus.FAIL, "Search box is not displayed inside the location dropdown");
            logger.info("Search box is not displayed inside the location dropdown");
            takeScreenshot("LocationSearchField");
        }
    }

    public void selectLocation() {
        toLocation = productLocationForContainer;
        productTransferPage.selectLocationOnly();
    }

    public void verifyLocationIsSelected() {
        String actualSelectedLocation = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 20);
        if (actualSelectedLocation.equalsIgnoreCase(productLocationForContainer)) {
            getTest().log(LogStatus.PASS, "The location field is selected with " + productLocationForContainer);
            logger.info("The location field is selected with " + productLocationForContainer);
        } else {
            getTest().log(LogStatus.FAIL, "The location field is not selected");
            logger.info("The location field is not selected");
        }
    }

    public void resetSelectedLocation() {
        click(By.xpath("//*[@title='Clear']"), "Location reset", 20);
    }

    public void verifyLocationReset() {
        String afterReset = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 20);
        if (afterReset.equalsIgnoreCase("select")) {
            getTest().log(LogStatus.PASS, "The selected location is removed from the location field when click on the reset icon");
            logger.info("The selected location is removed from the location field when click on the reset icon");
        } else {
            getTest().log(LogStatus.FAIL, "The selected location is not removed from the location field when click on the reset icon");
            logger.info("The selected location is not removed from the location field when click on the reset icon");
            takeScreenshot("LocationReset");
        }
    }

    public void getProductBasedOnTheProductType() {
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        deployProductPage.clickManageProduct();
        selectValueWithValue(By.cssSelector("#pageSize"), "100", "PageSize", 40);
        List<WebElement> productsToValidateLocators = findMultipleElement(By.xpath("//span[text()='" + productTypeForContainer + "']/../..//a[@id='ancEditAssetType']/span"), 30);
        for (WebElement ele : productsToValidateLocators) {
            productsNameToValidate.add(ele.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                productsToValidateLocators = findMultipleElement(By.xpath("//span[text()='" + productTypeForContainer + "']/../..//a[@id='ancEditAssetType']/span"), 30);
                for (WebElement ele : productsToValidateLocators) {
                    productsNameToValidate.add(ele.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
    }

    public void clickProductTypeDropDown() {
        click(By.cssSelector("div>select#AssetCatalogFilter"), "Product Type Dropdown", 20);
    }

    public void selectProductType() {
        selectValueWithText(By.cssSelector("div>select#AssetTypeFilter"), productType, "Product Type dropdown", 20);
    }

    public void clickProductDropDown() {
        click(By.cssSelector("div>select#AssetCatalogFilter"), "Product Dropdown", 20);
    }

    public void selectProduct() {
        selectValueWithText(By.cssSelector("div>select#AssetCatalogFilter"), productsNameToValidate.get(0), "Product dropdown", 20);
    }

    public void verifyProductDropdown() {
        String actualProductName;
        int iteration = 0;
        List<WebElement> actualProductLocators = findMultipleElement(By.cssSelector("div>select#AssetCatalogFilter>option"), 30);
        for (WebElement actualProductLocator : actualProductLocators) {
            actualProductName = actualProductLocator.getText().trim();
            if (!actualProductName.equalsIgnoreCase("select")) {
                for (String expectedProductName : productsNameToValidate) {
                    iteration++;
                    if (actualProductName.equalsIgnoreCase(expectedProductName)) {
                        getTest().log(LogStatus.PASS, "The product " + actualProductName + " is displayed in the product dropdown when product type selected as " + productsNameToValidate.get(0) + " as expected");
                        logger.info("The product " + actualProductName + " is displayed in the product dropdown when product type selected as " + productsNameToValidate.get(0) + " as expected");
                        iteration = 0;
                        break;
                    } else if (iteration == productsNameToValidate.size()) {
                        getTest().log(LogStatus.FAIL, "The product " + actualProductName + " is not displayed in the product dropdown when product type selected as " + productsNameToValidate.get(0));
                        logger.info("The product " + actualProductName + " is not displayed in the product dropdown when product type selected as " + productsNameToValidate.get(0));
                        takeScreenshot("ProductDropdown");
                    }
                }
            }
        }
    }

    public void enterProductUniqueName() {
        enter(By.cssSelector("input#txtAssetItems"), productsNameToValidate.get(0), "Product Unique name", 20);
    }

    public void verifyProductUniqueNameDropdown() {
        WebElement uniqueNameDropdown = findElementVisibility(By.cssSelector("div.unique_dynamicdatalist>ul>li"), 20);
        if (uniqueNameDropdown != null) {
            getTest().log(LogStatus.PASS, "Product unique names are displayed as expected when enter the product in the products field");
            logger.info("Product unique names are displayed as expected when enter the product in the products field");
        } else {
            getTest().log(LogStatus.FAIL, "Product unique names are not displayed when enter the product in the products field");
            logger.info("Product unique names are not displayed when enter the product in the products field");
        }
    }

    public void verifyProductSearchResult() {
        String actualProductName = getText(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[3]"), 20);
        String actualUniqueName = getText(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[4]"), 20);
        String actualLocationName = getText(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[5]"), 20);
        if (actualProductName.equalsIgnoreCase(productsNameToValidate.get(0)) && actualUniqueName.contains(barCodeProduct) && actualLocationName.equalsIgnoreCase(productLocationForContainer)) {
            getTest().log(LogStatus.PASS, "Search result for product is displayed as expected when enter all details in the search field");
            logger.info("Search result for product is displayed as expected when enter all details in the search field");
        } else {
            getTest().log(LogStatus.FAIL, "Search result for product is not displayed when enter all details in the search field");
            logger.info("Search result for product is not displayed when enter all details in the search field");
            takeScreenshot("ProductSearch");
        }
    }

    public void verifyAlphaNumericPCode() {
        enterInAutoSearch("PCode", "AlphaNumeric");
        verifyAlphaNumericValueInAutoSearch("PCode");
    }

    public void verifyAlphaNumericPName() {
        enterInAutoSearch("PName", "AlphaNumeric");
        verifyAlphaNumericValueInAutoSearch("PName");
    }

    public void verifyAlphaNumericUniqueName() {
        enterInAutoSearch("UniqueName", "AlphaNumeric");
        verifyAlphaNumericValueInAutoSearch("UniqueName");
    }

    public void enterInAutoSearch(String fieldName, String value) {
        String data = (value.equalsIgnoreCase("AlphaNumeric")) ? prop.getProperty("productCodeAlphaNumeric") : value;
        String field = fieldName.equalsIgnoreCase("PName") ? "itemName" : fieldName.equalsIgnoreCase("PCode") ? "itemCode" : "uniqueName";
        enter(By.cssSelector("td>input#" + field + "AutoSearch"), data, fieldName, 20);
    }

    public void verifyAlphaNumericValueInAutoSearch(String fieldName) {
        String field = fieldName.equalsIgnoreCase("PName") ? "itemName" : fieldName.equalsIgnoreCase("PCode") ? "itemCode" : "uniqueName";
        String actualAlphaNumericValue = getAtribute(By.cssSelector("td>input#" + field + "AutoSearch"), "value", 20);
        if (actualAlphaNumericValue.equalsIgnoreCase(prop.getProperty("productCodeAlphaNumeric"))) {
            getTest().log(LogStatus.PASS, "The auto search " + fieldName + " field is accepts the alphanumeric value");
            logger.info("The auto search " + fieldName + " field is accepts the alphanumeric value");
        } else {
            getTest().log(LogStatus.FAIL, "The auto search " + fieldName + " field is not accepts the alphanumeric value");
            logger.info("The auto search " + fieldName + " field is not accepts the alphanumeric value");
            takeScreenshot("AlphaNumeric" + fieldName);
        }
    }

    public void enterDataInAutoSearchField() {
        enterInAutoSearch("PCode", productCode);
        enterInAutoSearch("PName", productsNameToValidate.get(0));
        enterInAutoSearch("UniqueName", barCodeProduct);
    }

    public void verifyAutoSearchedResult() {
        String actualProductCode, actualProductName, actualUniqueName;
        scrollToWebelement(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist' and contains(@style,'table-row')]//td[4]"), "AutoSearch result");
        int autoSearchedResultCount = findMultipleElement(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist' and contains(@style,'table-row')]//td[4]"), 30).size();
        for(int i = 1 ; i <= autoSearchedResultCount ; i++) {
            actualProductCode = getText(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist' and @style='display: table-row;']["+i+"]//td[2]"), 20);
            actualProductName = getText(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist' and @style='display: table-row;']["+i+"]//td[3]"), 20);
            actualUniqueName = getText(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist' and @style='display: table-row;']["+i+"]//td[4]"), 20);

            if (actualProductCode.contains(productCode) && actualProductName.contains(productsNameToValidate.get(0)) && actualUniqueName.contains(barCodeProduct)) {
                getTest().log(LogStatus.PASS, "The result of auto search is displayed as expected when enter product code as " + productCode + " , productName as " + productsNameToValidate.get(0) + " and unique name as " + barCodeProduct+" in the result row - "+i);
                logger.info("The result of auto search is displayed as expected when enter product code as " + productCode + " , productName as " + productsNameToValidate.get(0) + " and unique name as " + barCodeProduct+" in the result row - "+i);
            } else {
                getTest().log(LogStatus.FAIL, "The result of auto search is not displayed correctly when enter product code as " + productCode + " , productName as " + productsNameToValidate.get(0) + " and unique name as " + barCodeProduct+" in the result row - "+i);
                logger.info("The result of auto search is not displayed correctly when enter product code as " + productCode + " , productName as " + productsNameToValidate.get(0) + " and unique name as " + barCodeProduct+" in the result row - "+i);
                takeScreenshot("AutoSearchResult");
            }
        }
    }

    public void clickResetInNewTable() {
        click(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='trctrl']//td[5]/a"), "Reset in New Table", 20);
    }

    public void verifyResetFields() {
        String prodCodeFiled = getAtribute(By.cssSelector("td>input#itemCodeAutoSearch"), "value", 20);
        String prodNameFiled = getAtribute(By.cssSelector("td>input#itemNameAutoSearch"), "value", 20);
        String uniqueNameFiled = getAtribute(By.cssSelector("td>input#uniqueNameAutoSearch"), "value", 20);
        if (prodCodeFiled.equalsIgnoreCase("") && prodNameFiled.equalsIgnoreCase("") && uniqueNameFiled.equalsIgnoreCase("")) {
            getTest().log(LogStatus.PASS, "The entered data is clears from the auto search fields when click reset button along with the auto search field");
            logger.info("The entered data is clears from the auto search fields when click reset button along with the auto search field");
        } else {
            getTest().log(LogStatus.FAIL, "The entered data is not cleared from the auto search fields when click reset button along with the auto search field");
            logger.info("The entered data is not cleared from the auto search fields when click reset button along with the auto search field");
        }
    }

    public void clickSearchButton() {
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        click(By.cssSelector("a#Searchassest"), "Product search", 20);
        waitForLoader(20);
    }

    public void clickSelectAllCheckBoxAfterSearch() {
        click(By.cssSelector("table#tblWriteoffItems>thead>tr>#nonresize"), "Select All check box", 20);
    }

    public void verifyAllSelectedCheckBoxAfterSearch() {
        int iteration = 0;
        scrollToWebelement(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist']"), "Auto Search Result");
        List<WebElement> checkBoxesAfterSearch = findMultipleElement(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist']"), 30);
        for (WebElement checkBoxAfterSearch : checkBoxesAfterSearch) {
            String displayStyle = checkBoxAfterSearch.getAttribute("style");
            if (!displayStyle.equalsIgnoreCase("display: none;")) {
                iteration++;
                String checkBoxStatus = checkBoxAfterSearch.findElement(By.xpath("//td[1]//div/input")).getAttribute("checked");
                if (checkBoxStatus.equalsIgnoreCase("true")) {
                    getTest().log(LogStatus.PASS, "The check box of the row - " + iteration + " is selected when select all check box is clicked");
                    logger.info("The check box of the row - " + iteration + " is selected when select all check box is clicked");
                } else {
                    getTest().log(LogStatus.FAIL, "The check box of the row - " + iteration + " is not selected when select all check box is clicked");
                    logger.info("The check box of the row - " + iteration + " is not selected when select all check box is clicked");
                    takeScreenshot("SelectAllCheckBox");
                }
            }
        }
    }

    public void enterWriteOffValue(String writeOffValue) {
        scrollToWebelement(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[10]/input"), "Write off value field");
        enter(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[10]/input"), writeOffValue, "Write Off Value", 20);
    }

    public void verifyWriteOffFieldValue(String valueType, String expectedValue) {
        String actualValue = getAtribute(By.xpath("//table[@id='tblWriteoffItems']//tbody//tr[@class='itemAddlist']//td[10]/input"), "value", 20);
        if (valueType.equalsIgnoreCase("Number")) {
            if (expectedValue.equalsIgnoreCase(actualValue)) {
                getTest().log(LogStatus.PASS, "The write off value field accepts the number as expected. Entered value is - " + expectedValue);
                logger.info("The write off value field accepts the number as expected. Entered value is - " + expectedValue);
            } else {
                getTest().log(LogStatus.FAIL, "The write off value field not accepts the number. Entered value is - " + expectedValue);
                logger.info("The write off value field not accepts the number. Entered value is - " + expectedValue);
                takeScreenshot("WriteOffNumber");
            }
        } else {
            if (!expectedValue.equalsIgnoreCase(actualValue)) {
                getTest().log(LogStatus.PASS, "The write off value field not accepts the alphabets as expected. Entered value is - " + expectedValue);
                logger.info("The write off value field not accepts the alphabets as expected. Entered value is - " + expectedValue);
            } else {
                getTest().log(LogStatus.FAIL, "The write off value field accepts the alphabets. Entered value is - " + expectedValue);
                logger.info("The write off value field accepts the alphabets. Entered value is - " + expectedValue);
                takeScreenshot("WriteOffAlphabets");
            }
        }
    }

    public void verifyNumericWriteOff() {
        String number = new SimpleDateFormat("ms").format(new Date());
        enterWriteOffValue(number);
        verifyWriteOffFieldValue("Number", number);
    }

    public void verifyAlphabetWriteOff() {
        String alphaNumeric = prop.getProperty("attachmentNameAlphaNumeric");
        enterWriteOffValue(alphaNumeric);
        verifyWriteOffFieldValue("AlphabetNumeric", alphaNumeric);
    }

    public String getSalvageCost() {
        return getText(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist']//td[9]"), 20);
    }

    public void enterWriteOffHighSalvage() {
        double actualSalvage = Double.parseDouble(getSalvageCost());
        int writeOff = (int) actualSalvage + 1;
        enterWriteOffValue(String.valueOf(writeOff));
    }

    public void enterWriteOffLowSalvage() {
        double actualSalvage = Double.parseDouble(getSalvageCost());
        int writeOff = (int) actualSalvage - 1;
        enterWriteOffValue(String.valueOf(writeOff));
    }

    public void verifyAddToListValidation()
    {
        WebElement addToListError = findElementVisibility(By.xpath("//div[contains(@class,'alert-dismissible')]"),20);
        if(addToListError != null)
        {
            getTest().log(LogStatus.PASS,"The error message is displayed as expected when click on the add to list button without selecting any check box");
            logger.info("The error message is displayed as expected when click on the add to list button without selecting any check box");
            click(By.cssSelector("#closenotifymessage"),"Add to list error popup close",20);
        }
        else {
            getTest().log(LogStatus.FAIL,"The error message is not displayed when click on the add to list button without selecting any check box");
            logger.info("The error message is not displayed when click on the add to list button without selecting any check box");
            takeScreenshot("AddToListError");
        }
    }

    public void verifyLowSalvageError() {
        WebElement warningPopup = findElementVisibility(By.xpath("//div[text()='Please correct the highlighted errors.']"), 20);
        if (warningPopup != null) {
            click(By.xpath("//div[text()='Please correct the highlighted errors.']/../../div/button[@title='OK']"), "Warning popup Ok", 20);
            staticWait(500);
            WebElement salvageError = findElementVisibility(By.xpath("//div[text()=' The writeoff value should not be less than the salvage value.']"), 20);
            if (salvageError != null) {
                click(By.xpath("//button[@title='OK']"), "Low Write off error OK", 20);
                getTest().log(LogStatus.PASS, "Error message is displayed as expected when enter write off as lower than the salvage cost");
                logger.info("Error message is displayed as expected when enter write off as lower than the salvage cost");
            } else {
                getTest().log(LogStatus.FAIL, "Error message is not displayed when enter write off value as lower than the salvage cost");
                logger.info("Error message is not displayed when enter write off value as lower than the salvage cost");
                takeScreenshot("ErrorMessage");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Warning popup is not displayed when click add to list button after enter the write off value as lower than the salvage cost");
            logger.info("Warning popup is not displayed when click add to list button after enter the write off value as lower than the salvage cost");
            takeScreenshot("WarningPopup");
        }
    }

    public void selectSingleCheckBox() {
        staticWait(500);
        scrollToWebelement(By.xpath("//div[@id='divmultilevelselect']/div/div"), "Location field");
        click(By.xpath("//table[@id='tblWriteoffItems']//tr[@class='itemAddlist'][1]//td/div"), "1 st record check box", 20);
        productTobeAddInWriteOff = getText(By.xpath("//input[@checked]/../../..//td[4]"), 20);
    }

    public void clickDeleteIconOfAddedProd() {
        click(By.xpath("//table[@id='tbltransferiteminfoList']//tbody//tr//td//a"), "Delete icon of Added prod", 20);
    }

    public void confirmationForAddedProdDelete() {
        WebElement confirmation = findElementVisibility(By.xpath("//div[text()='Are you sure you want to delete these record(s)?']"), 20);
        if (confirmation != null) {
            click(By.xpath("//button[@title='OK']"), "Delete Confirmation OK", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation for product delete is not displayed when click on the delete icon of added product");
            logger.info("Confirmation for product delete is not displayed when click on the delete icon of added product");
            takeScreenshot("DeleteConfirmation");
        }
    }

    public void verifyDeletedRecord() {
        int recordCountAfterDelete = findMultipleElement(By.xpath("//table[@id='tbltransferiteminfoList']//tbody//tr"), 30).size();
        if (recordCountAfterDelete == 0) {
            getTest().log(LogStatus.PASS, "The product is deleted from the table when click on the delete icon");
            logger.info("The product is deleted from the table when click on the delete icon");
        } else {
            getTest().log(LogStatus.FAIL, "The product is not deleted from the table when click on the delete icon");
            logger.info("The product is not deleted from the table when click on the delete icon");
            takeScreenshot("ProductDelete");
        }
    }

    public void clickCheckBoxOfAddedProduct()
    {
        click(By.xpath("//table[@id='tbltransferiteminfoList']//tbody//tr[1]//td[1]/div"),"Added product check box",20);;
    }
    public void saveAddedProduct()
    {
        click(By.cssSelector("a#saveBtn"),"Added product save",20);
    }
    public void verifySaveProdMandatoryError()
    {
        WebElement saveAddedProdError = findElementVisibility(By.xpath("//div[contains(@class,'alert-dismissible')]"),20);
        if(saveAddedProdError != null)
        {
            click(By.cssSelector("#closenotifymessage"),"Save Product Error Ok",20);
            getTest().log(LogStatus.PASS,"The error message is displayed as expected when click on the add to list button without selecting any check box");
            logger.info("The error message is displayed as expected when click on the add to list button without selecting any check box");
        }
        else {
            getTest().log(LogStatus.FAIL,"The error message is not displayed when click on the save button without selecting the check box");
            logger.info("The error message is not displayed when click on the save button without selecting the check box");
            takeScreenshot("SaveWithOutCheckBox");
        }
    }
    public void verifyTheSavedWriteOffProduct()
    {
        List<String> actualProductNames = new ArrayList<>();
        List<WebElement> actualWriteOffProduct = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[3]/span"),30);
        for(WebElement writeOffProduct : actualWriteOffProduct)
        {
            actualProductNames.add(writeOffProduct.getText());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                actualWriteOffProduct = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[1]//span"), 30);
                for (WebElement element : actualWriteOffProduct) {
                    actualProductNames.add(element.getText());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if(actualProductNames.contains(barCodeProduct))
        {
            getTest().log(LogStatus.PASS,"The created product write off is displayed in the product write off list when click on the save button");
            logger.info("The created product write off is displayed in the product write off list when click on the save button");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The created product write off is not displayed in the product write off list when click on the save button");
            logger.info("The created product write off is not displayed in the product write off list when click on the save button");
            takeScreenshot("ProductWriteOffTable");
        }
    }
    public void clickCancelButton()
    {
        click(By.cssSelector("a#cancelBtn"),"Cancel Button",20);
    }
    public void verifyCancelProductWriteOff()
    {
        verifyProductWriteOffScreen("cancel button in Create Product Write Off page");
    }
    public void verifyProductWriteOffScreen(String place)
    {
        WebElement productWriteOffPage = findElementVisibility(By.xpath("//span[text()='Product Write Off']"),20);
        if(productWriteOffPage != null)
        {
            getTest().log(LogStatus.PASS,"Product write off page is displayed as expected when click on "+place);
            logger.info("Product write off page is displayed as expected when click on "+place);
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Product write off page is not displayed when click on "+place);
            logger.info("Product write off page is not displayed when click on "+place);
            takeScreenshot("ProductWriteOffPage");
        }
    }
}
