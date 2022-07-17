package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class DeployProductPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    public static String inputDate;
    public static String modelNameFromPopup;
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    CompanySetupPage companySetup;

    public DeployProductPage(WebDriver driver) {
        super(driver, "Deploy product page");
        this.driver = driver;
        companySetup = new CompanySetupPage(driver);
    }

    public void clickFullMenuDropDown() {
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 60);
        staticWait(1500);
        waitForLoader(300);
        click(By.cssSelector("a#navbarDropdownPortfolio"), "Full Menu", 20);
    }

    public void clickAssetManagement() {
        WebElement assetManagementMenu = findElementVisibility(By.xpath("//a[text()='Asset Management ']"), 15);
        if (assetManagementMenu != null) {
            click(By.xpath("//a[text()='Asset Management ']"), "Asset Management", 10);
        } else {
            driver.navigate().refresh();
            clickFullMenuDropDown();
            clickAssetManagement();
        }
    }

    public void clickManageProduct() {
        WebElement manageProductMenu = findElementVisibility(By.xpath("//div[@id='scrollbar']//a[text()='Manage Product']"), 15);
        if (manageProductMenu != null) {
            click(By.xpath("//div[@id='scrollbar']//a[text()='Manage Product']"), "Manage Product", 20);
        } else {
            clickAssetManagement();
            clickManageProduct();
        }
    }

    public void navigateToDeployTab() {
        click(By.cssSelector("ul#myTab>li:nth-child(2)>a"), "Deploy Tab", 10);
    }

    public void openProduct() {
        String productName = createdProductName;
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//a//span[normalize-space(text())='" + productName + "']"), "Product", 20);
    }

    public void verifyListingColumnHeader() {
        int i = 0;
        List<String> expectedListHeader = new ArrayList<>();
        expectedListHeader.add("Location");
        expectedListHeader.add("Model");
        expectedListHeader.add("Ref. PurchaseOrder");
        expectedListHeader.add("Order Date");
        expectedListHeader.add("Deployment Type");
        expectedListHeader.add("Deployed By");
        expectedListHeader.add("Deployed At");
        expectedListHeader.add("Quantity");
        expectedListHeader.add("Product Cost/Qty");


        List<WebElement> listHeader = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tr//th//span"), 15);
        for (WebElement actual : listHeader) {
            List<String> expectedValues = expectedListHeader;
            for (Object expected : expectedValues) {
                i++;
                if (actual.getText().equals(expected)) {
                    getTest().log(LogStatus.PASS, "The \"" + expected + "\" Header is displayed in the Deploy listing page");
                    logger.info("The \"" + expected + "\" Header is displayed in the Deploy listing page");
                    i = 0;
                    break;
                } else if (i == listHeader.size() && !actual.getText().equals(expected)) {
                    getTest().log(LogStatus.FAIL, "The \"" + expected + "\" Header is not displayed in the Deploy listing page");
                    logger.info("The \"" + expected + "\" Header is not displayed in the Deploy listing page");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void verifySearchBar() {
        WebElement searchBar = findElementVisibility(By.cssSelector("input#assetSearch"), 15);
        if (searchBar != null) {
            getTest().log(LogStatus.PASS, "\"Search Bar\" is displayed in the Deploy product page");
            logger.info("\"Search Bar\" is displayed in the Deploy product page");
        } else {
            getTest().log(LogStatus.FAIL, "\"Search Bar\" is not displayed in the Deploy product page");
            logger.info("\"Search Bar\" is not displayed in the Deploy product page");
            takeScreenshot("SearchBar");
        }
    }

    public void verifyDepolyProductAddButton() {
        WebElement addButton = findElementVisibility(By.cssSelector("#ancDeployItems"), 15);
        if (addButton != null) {
            getTest().log(LogStatus.PASS, "\"Deploy Product Add Button\" is displayed in the Deploy product page");
            logger.info("\"Deploy Product Add Button\" is displayed in the Deploy product page");
        } else {
            getTest().log(LogStatus.FAIL, "\"Deploy Product Add Button\" is not displayed in the Deploy product page");
            logger.info("\"Deploy Product Add Button\" is not displayed in the Deploy product page");
            takeScreenshot("DeployProdAdd");
        }
    }

    public void verifyNextButton() {
        WebElement nextButton = findElementVisibility(By.cssSelector("a#btnNext"), 15);
        if (nextButton != null) {
            getTest().log(LogStatus.PASS, "\"Next Button\" is displayed in the Deploy product page");
            logger.info("\"Next Button\" is displayed in the Deploy product page");
        } else {
            getTest().log(LogStatus.FAIL, "\"Next Button\" is not displayed in the Deploy product page");
            logger.info("\"Next Button\" is not displayed in the Deploy product page");
            takeScreenshot("Next");
        }
    }

    public void verifyPreviousButton() {
        WebElement nextButton = findElementVisibility(By.cssSelector("a#Previous"), 30);
        if (nextButton != null) {
            getTest().log(LogStatus.PASS, "\"Previous Button\" is displayed in the Deploy product page");
            logger.info("\"Previous Button\" is displayed in the Deploy product page");
        } else {
            getTest().log(LogStatus.FAIL, "\"Previous Button\" is not displayed in the Deploy product page");
            logger.info("\"Previous Button\" is not displayed in the Deploy product page");
            takeScreenshot("Previous");
        }
    }

    public void enterInSearchField() {
        enter(By.cssSelector("input#assetSearch"), locationToSearch, "Search Field", 15);
    }

    public void clickSearchButton() {
        click(By.cssSelector("a#aSearchAsset"), "Search Button", 15);
        waitForLoader(20);
    }

    public void verifySearchedProduct() {
        List<WebElement> searchedProduct = findMultipleElement(By.cssSelector("table#deployItemsTable>tbody>tr>td>a"), 20);
        List<String> results = new ArrayList<>();
        for (WebElement locators : searchedProduct) {
            if (locators.getText().equals(locationToSearch)) {
                results.add("true");
            } else {
                results.add("false");
            }
        }
        if (results.contains("false")) {
            getTest().log(LogStatus.FAIL, "Searched location list is not Displayed");
            logger.info("Searched location list is not Displayed");
            takeScreenshot("SearchedLoc");
        } else {
            getTest().log(LogStatus.PASS, "Searched location list is Displayed");
            logger.info("Searched location list is Displayed");
        }
    }

    public void clickClearSearch() {
        WebElement ele = findElementVisibility(By.cssSelector("a#aUN_ClearSearch"), 30);
        if (ele != null) {
            click(By.cssSelector("a#aUN_ClearSearch"), "Clear Search", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Clear Button is not found");
            logger.info("Clear Button is not found");
            takeScreenshot("ClearButton");
        }
    }

    public void verifyClearedSearch() {
        waitForLoader(30);
        String searchField = getAtribute(By.cssSelector("input#assetSearch"), "value", 15);
        if (searchField.equals("")) {
            logger.info("Search field is cleared successfully");
            getTest().log(LogStatus.PASS, "Search field is cleared successfully");
        } else {
            logger.info("Search field is not cleared successfully");
            getTest().log(LogStatus.FAIL, "Search field is not cleared successfully");
            takeScreenshot("ClearedSearch");
        }

    }

    public void clickAddDeployButton() {
        waitForLoader(20);
        findElementClickable(By.cssSelector("a#ancDeployItems"), 60);
        click(By.cssSelector("a#ancDeployItems"), "Add Deploy", 30);
    }

    public void verifyAddDeployProductPage() {
        WebElement addToListButton = findElementVisibility(By.cssSelector("a#btn_AddRow"), 15);
        if (addToListButton != null) {
            getTest().log(LogStatus.PASS, "The \"Add Deploy Product\" page is displayed");
            logger.info("The \"Add Deploy Product\" page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "The \"Add Deploy Product\" page is displayed");
            logger.info("The \"Add Deploy Product\" page is displayed");
            takeScreenshot("DeployProdPage");
        }
    }

    public void clickNextButton() {
        staticWait(1500);
        click(By.cssSelector("a#btnNext"), "Next Button", 15);
    }

    public void verifyNextPage() {
        waitForVisibilityOfElement(By.xpath("//div[@aria-labelledby=\"RelatedInformation-tab\"]//h5//span"), 20);
        WebElement relatedInformationHeader = findElementVisibility(By.xpath("//div[@aria-labelledby='RelatedInformation-tab']//span[text()='Related Information']"), 15);
        if (relatedInformationHeader != null) {
            getTest().log(LogStatus.PASS, "\"Related Information page\" is displayed when click Next button in \"Deploy Product page\"");
            logger.info("\"Related Information page\" is displayed when click Next button in \"Deploy Product page\"");
        } else {
            getTest().log(LogStatus.FAIL, "\"Related Information page\" is not displayed when click Next button in \"Deploy Product page\"");
            logger.info("\"Related Information page\" is not displayed when click Next button in \"Deploy Product page\"");
            takeScreenshot("RelatedInfoPage");
        }
    }

    public void clickPreviousButton() {
        click(By.cssSelector("a#Previous"), "Previous Button", 15);
        click(By.cssSelector("a#Previous"), "Previous Button", 15);
    }

    public void verifyPreviousPage() {
        scrollToWebelement(By.xpath("//div[@id='myTabContent']//span[text()='Create Product']"), "Create Product");
        WebElement relatedInformationHeader = findElementVisibility(By.xpath("//div[@id='myTabContent']//span[text()='Create Product']"), 15);
        if (relatedInformationHeader != null) {
            getTest().log(LogStatus.PASS, "\"Create Product page\" is displayed when click previous button in \"Deploy page\"");
            logger.info("\"Create Product page\" is displayed when click previous button in \"Deploy page\"");
        } else {
            getTest().log(LogStatus.FAIL, "\"Create Product page\" is not displayed when click previous button in \"Deploy page\"");
            logger.info("\"Create Product page\" is not displayed when click previous button in \"Deploy page\"");
            takeScreenshot("CreateProductPage");
        }
    }

    public void selectLocationValueFromDropdown(String locationName) {
        if (locationName.equalsIgnoreCase("")) {
            findElementsVisibilityByClick(By.xpath("//ul[@id='CompantLocationSelect']//li[@class='parentli']//a"));
        } else {
            click(By.xpath("//a[@data-text='" + locationName + "']"), locationName, 20);
        }
    }

    public void verifySelectedLocation(String expectedLocation) {
        String actualSelectedLocation = getText(By.cssSelector("div#divmultilevelselectLocation>div>div>span.CompantLocationSelectselected"), 15);
        if (actualSelectedLocation.equals(expectedLocation)) {
            getTest().log(LogStatus.PASS, "User can able to select the location from dropdown and it is selected");
            logger.info("User can able to select the location from dropdown and it is selected");
        } else {
            getTest().log(LogStatus.FAIL, "Selected Location is not displayed in the dropdown field");
            logger.info("Selected Location is not displayed in the dropdown field");
            takeScreenshot("SelectedLoc");
        }
    }

    public void enterQuantity(String quantity) {
        enter(By.cssSelector("div>input#Quantity"), quantity, "Quantity", 15);
    }

    public void enterUnitPrice(String unitPrice) {
        enter(By.cssSelector("div>input#UnitPrice"), unitPrice, "Unit Price", 15);
    }

    public void enterModel(String modelName) {
        enter(By.cssSelector("div>input#Model"), modelName + dateValue, "Model", 15);
        modelNameFromPopup = getAtribute(By.cssSelector("div>input#Model"), "value", 20);
    }

    public void enterManufacturer(String manufacturer) {
        enter(By.cssSelector("div>input#Manufacturer"), manufacturer, "Manufacturer", 15);
    }

    public void enterVendor(String vendor) {
        enter(By.cssSelector("div>input#Vendor"), vendor, "Vendor", 15);
    }

    public void enterProductCost(String productCost) {
        enter(By.cssSelector("div>input#AssetCost"), productCost, "AssetCost", 15);
    }

    public void enterPurchaseOrder(String purchaseOrder) {
        enter(By.cssSelector("div>input#PurchaseOrder"), purchaseOrder, "PurchaseOrder", 15);
    }

    public void enterInvoiceNumber(String invoiceNumber) {
        enter(By.cssSelector("div>input#InvoiceNumber"), invoiceNumber, "Invoice Number", 15);
    }

    public void enterInsuranceNumber(String insuranceNumber) {
        enter(By.cssSelector("div>input#InsuranceRefNumber"), insuranceNumber, "Insurance Reference Number", 15);
    }

    public void enterInsurarName(String insurerName) {
        enter(By.cssSelector("div>input#InsurerName"), insurerName, "InsurerName", 15);
    }

    public void selectDepreciationRule() {
        selectValueWithIndex(By.cssSelector("div>select#depreciationId"), 1, "Depreciation Rule", 15);
    }

    public void enterProductLife(String year) {
        enter(By.cssSelector("div>input#ItemLife"), year, "Product Life", 15);
    }

    public void enterSalvageCost(String salvageCost) {
        enter(By.cssSelector("div>input#SalvageCost"), salvageCost, "Salvage Cost", 15);
    }

    public void clickAddListButton() {
        click(By.cssSelector("div>a#btn_AddRow"), "Add to List", 15);
    }

    public void clickSaveButton() {
        click(By.cssSelector("a#saveBtnn"), "Save", 15);
    }

    public void handleSuccessPopup() {
        //waitForVisibilityOfElement(By.cssSelector("div.alert-success"), 5);
        click(By.cssSelector("#closenotifymessage"), "Close Popup", 20);
        waitForLoader(20);
    }

    public void verifyCreatedDeployProduct() {
        waitForLoad(20);
        int i = 0;
        List<WebElement> deployedProductList = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td[2]//span"), 15);
        for (WebElement element : deployedProductList) {
            i++;
            if (element.getText().equals(modelNameFromPopup)) {
                locationToSearch = getText(By.xpath("//table[@id='deployItemsTable']//tbody//tr[1]//td//a"), 20);
                getTest().log(LogStatus.PASS, "Created \"Deploy Product\" is displayed in the list");
                logger.info("Created \"Deploy Product\" is displayed in the list");
                break;
            } else if (i == deployedProductList.size() && !element.getText().equals(modelNameFromPopup)) {
                getTest().log(LogStatus.FAIL, "Created \"Deploy Product\" is not displayed in the list");
                logger.info("Created \"Deploy Product\" is not displayed in the list");
                takeScreenshot("DeployedProduct");
            }
        }
    }

    public void verifyBreadCrumb() {
        WebElement breadCrumb = findElementVisibility(By.cssSelector(".breadcrumb"), 15);
        if (breadCrumb != null) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed in the Deploy Product listing page");
            logger.info("BreadCrumb is displayed in the Deploy Product listing page");
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed in the Deploy Product listing page");
            logger.info("BreadCrumb is not displayed in the Deploy Product listing page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void navigateToProductEditMode() {
        click(By.xpath("//table[@id='deployItemsTable']//tbody//tr[1]//td//a"), "Location Name", 15);
    }

    public void verifyEditMode() {
        String locationDisabled = getAtribute(By.cssSelector("#LocationName"), "disabled", 15);
        String saveButton = getAtribute(By.cssSelector("a#saveBtnn"), "class", 15);
        if (locationDisabled.equals("true") && saveButton.equals("btn btn-success")) {
            getTest().log(LogStatus.PASS, "Deploy Product popup is displayed in edit mode");
            logger.info("Deploy Product popup is displayed in edit mode");
        } else {
            getTest().log(LogStatus.FAIL, "Deploy Product popup is not displayed in edit mode");
            logger.info("Deploy Product popup is not displayed in edit mode");
            takeScreenshot("DeployProductPopup");
        }
    }

    public void clearUnitPriceField() {
        enter(By.cssSelector("div>input#UnitPrice"), "", "Unit Price", 15);
    }

    public void verifyMandatoryFieldValidation() {
        int i = 0;
        String actualText;
        String expectedText;
        List<WebElement> errorMessageLocator = findMultipleElement(By.xpath("//div[@class='modal-content']//span[contains(@class,'invalid-feedback')]"), 15);
        String[] expectedValue = {"Location", "Quantity", "Product Cost/Qty", "Unit Price"};
        for (Object expected : expectedValue) {
            WebElement asteriskField = findElementVisibility(By.xpath("//label[text()='" + expected + ":']//span"), 15);
            if (asteriskField != null) {
                getTest().log(LogStatus.PASS, "The Asterisk symbol is displayed for " + expected + " field");
                logger.info("The Asterisk symbol is displayed for " + expected + " field");
            } else {
                getTest().log(LogStatus.FAIL, "The Asterisk symbol is not displayed for " + expected + " field");
                logger.info("The Asterisk symbol is not displayed for " + expected + " field");
                takeScreenshot(expected.toString());
            }
            List<WebElement> expectedElements = errorMessageLocator;
            for (WebElement element : expectedElements) {
                i++;
                actualText = element.getText();
                expectedText = expected.toString();
                if (actualText.indexOf(expectedText) != -1) {
                    getTest().log(LogStatus.PASS, "Error message for \"" + expected + "\" field is displayed as expected");
                    logger.info("Error message for \"" + expected + "\" field is displayed as expected");
                    i = 0;
                    break;
                } else if (i == expectedValue.length && !element.getText().contains(expectedText)) {
                    getTest().log(LogStatus.FAIL, "Error message for \"" + expected + "\" field is not displayed");
                    logger.info("Error message for \"" + expected + "\" field is displayed as expected");
                    takeScreenshot(expectedText);
                }
            }
        }
    }

    public void clickLocationDropdown() {
        clickMultipleTimes(By.cssSelector("div#divmultilevelselectLocation>div>div"), "Location Dropdown", 15);
    }

    public void verifyLocationDropdown() {
        WebElement locationDropdown = findElementVisibility(By.cssSelector("#CompantLocationSelect>li>ol"), 15);
        if (locationDropdown != null) {
            getTest().log(LogStatus.PASS, "Location Dropdown values are displayed as expected");
            logger.info("Location Dropdown values are displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Location Dropdown values are not displayed as expected");
            logger.info("Location Dropdown values are not displayed as expected");
            takeScreenshot("LocationDropDown");
        }
    }

    public void verifySearchFieldInLocationDropdown() {
        WebElement searchField = findElementVisibility(By.cssSelector("#CompantLocationSelect>li.mainelevel"), 15);
        if (searchField != null) {
            getTest().log(LogStatus.PASS, "Search field is displayed in the Location dropdown");
            logger.info("Search field is displayed in the Location dropdown");
        } else {
            getTest().log(LogStatus.FAIL, "Search field is not displayed in the Location dropdown");
            logger.info("Search field is not displayed in the Location dropdown");
            takeScreenshot("SearchInLoc");
        }
    }

    public void enterInLocationSearch(String searchLocation) {
        enter(By.cssSelector("#CompantLocationSelect>li.mainelevel>input"), searchLocation, "Location Search", 15);
    }

    public void verifyLocationSearch(String searchLocation) {
        WebElement searchResult = findElementVisibility(By.xpath("//li//a//span[text()='" + searchLocation + "']"), 15);
        if (searchResult != null) {
            getTest().log(LogStatus.PASS, "Search result for location is displayed as expected");
            logger.info("Search result for location is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Search result for location is not displayed as expected");
            logger.info("Search result for location is not displayed as expected");
            takeScreenshot("LocSearch");
        }
    }

    public void make100PageSize() {
        findElementVisibility(By.cssSelector("select#pageSize"), 30);
        scrollToWebelement(By.cssSelector("select#pageSize"), "Page Size");
        staticWait(3000);
        selectValueWithValue(By.cssSelector("select#pageSize"), "100", "Page Size", 15);
        waitForLoader(20);
        staticWait(3000);
    }

    public void getLocationsFromSetup() {
        waitForVisibilityOfElement(By.xpath("//a[@id='ancEditLocation']"), 15);
        List<WebElement> locationList = findMultipleElement(By.xpath("//a[@id='ancEditLocation']"), 15);
        int i = 0;
        for (WebElement element : locationList) {
            i++;
            waitForVisibilityOfElement(By.xpath("(//a[@id='ancEditLocation'])[" + i + "]//parent::div//parent::div//parent::li//parent::ul"), 15);
            String attributeName = getAtribute(By.xpath("(//a[@id='ancEditLocation'])[" + i + "]//parent::div//parent::div//parent::li//parent::ul"), "class", 15);
            if (!attributeName.equals("parentbasemaster")) {
                parentLocationNameList.add(element.getText());
            } else {
                childLocationNameList.add(element.getText());
            }
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                locationList = findMultipleElement(By.xpath("//a[@id='ancEditLocation']"), 15);
                i = 0;
                for (WebElement element : locationList) {
                    i++;
                    waitForVisibilityOfElement(By.xpath("(//a[@id='ancEditLocation'])[" + i + "]//parent::div//parent::div//parent::li//parent::ul"), 15);
                    String attributeName = getAtribute(By.xpath("(//a[@id='ancEditLocation'])[" + i + "]//parent::div//parent::div//parent::li//parent::ul"), "class", 15);
                    if (!attributeName.equals("parentbasemaster")) {
                        parentLocationNameList.add(element.getText());
                    } else {
                        childLocationNameList.add(element.getText());
                    }
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
    }

    public void verifyLocations(String locationType) {
        int i = 0;
        String text1;
        String text2;
        String locator = (locationType.equals("parent")) ? ".scrollbar>li>a>span" : "ul.child>li>a>div";
        waitForVisibilityOfElement(By.cssSelector(locator), 20);
        int locationCount = findMultipleElement(By.cssSelector(locator), 20).size();
        List<WebElement> locations = findMultipleElement(By.cssSelector(locator), 15);
        for (WebElement actualElement : locations) {
            List<String> expectedLocationNames = (locationType.equals("parent")) ? parentLocationNameList : childLocationNameList;
            for (Object expectedElement : expectedLocationNames) {
                i++;
                text1 = actualElement.getText();
                text2 = expectedElement.toString();
                if (text1.equals(text2)) {
                    getTest().log(LogStatus.PASS, "Expected " + locationType + " location \"" + expectedElement + "\" location is present in both setup and location dropdown");
                    logger.info("Expected " + locationType + " location \"" + expectedElement + "\" location is present in both setup and location dropdown");
                    i = 0;
                    break;
                } else if (i == expectedLocationNames.size()) {
                    getTest().log(LogStatus.FAIL, "Expected " + locationType + " location \"" + expectedElement + "\" location is not present in location dropdown");
                    logger.info("Expected " + locationType + " location \"" + expectedElement + "\" location is not present in location dropdown");
                    takeScreenshot("loc" + text2);
                }
            }
        }
    }

    public void clearLocationSelection() {
        click(By.cssSelector("div#divmultilevelselectLocation>div>div>span>i[title='Clear']"), "Clear Location", 15);
    }

    public void verifyClearedLocation() {
        String actualValue = findElementVisibility(By.cssSelector("[class='CompantLocationSelectselected float-left']"), 15).getText();
        if (actualValue.equals("Select")) {
            getTest().log(LogStatus.PASS, "Selected dropdown value is cleared as expected");
            logger.info("Selected dropdown value is cleared as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Selected dropdown value is not cleared as expected");
            logger.info("Selected dropdown value is not cleared as expected");
            takeScreenshot("ClearedLoc");
        }
    }

    public void verifyQuantityMinimumChar() {
        String text = prop.getProperty("quantityMinimumCharacter");
        enterQuantity(text);
        clickAddListButton();
        WebElement errorMessage = findElementVisibility(By.xpath("//input[@id='Quantity']//parent::div//span[@class='invalid-feedback']"), 20);
        if (errorMessage == null) {
            getTest().log(LogStatus.PASS, "Quantity field is accepts minimum of one character");
            logger.info("Quantity field is accepts minimum of one character");
        } else {
            getTest().log(LogStatus.FAIL, "Quantity field is not accepts minimum of one character");
            logger.info("Quantity field is not accepts minimum of one character");
            takeScreenshot("QuantityMin");
        }
    }

    public void verifyQuantityCharAndSpclChar() {
        String text = prop.getProperty("quantityCharandSpclChar");
        enterQuantity(text);
        String actualText = findElementVisibility(By.cssSelector("div>input#Quantity"), 15).getText();
        if (actualText.equals("")) {
            getTest().log(LogStatus.PASS, "Quantity field is not accepts character and special characters as expected");
            logger.info("Quantity field is not accepts character and special characters as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Quantity field is accepts character and special characters as " + actualText);
            logger.info("Quantity field is accepts character and special characters as " + actualText);
            takeScreenshot("QuantityCharSpclChar");
        }
    }

    public void verifyQuantityMaxChar() {
        String text = prop.getProperty("quantityMaxChar");
        enterQuantity(text);
        String actualText = findElementVisibility(By.cssSelector("div>input#Quantity"), 15).getAttribute("value");
        if (actualText.length() <= 5) {
            getTest().log(LogStatus.PASS, "Quantity field is not accepts more than 5 characters as expected. Accepted Value : " + actualText);
            logger.info("Quantity field is not accepts more than 5 characters as expected. Accepted Value : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Quantity field is accepts more than 5 characters as " + actualText);
            logger.info("Quantity field is accepts more than 5 characters as " + actualText);
            takeScreenshot("QuantityMaxChar");
        }
    }

    public void verifyModelFieldValidation() {
        String modelName = prop.getProperty("alphaNumericModelName");
        enter(By.cssSelector("input#Model"), modelName, "Model Name", 15);
        String actualText = findElementVisibility(By.cssSelector("div>input#Model"), 15).getAttribute("value");
        if (modelName.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Model Name field is accept alpha numeric character as expected. Accepted value : " + actualText);
            logger.info("Model Name field is accept alpha numeric character as expected. Accepted value : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Model Name field is not accept alpha numeric character as expected. It accept the Value \"" + modelName + "\"");
            logger.info("Model Name field is not accept alpha numeric character as expected. It accept the Value \"" + modelName + "\"");
            takeScreenshot("ModelName");
        }
    }

    public void verifyManufacturerFieldAlphaNum() {
        String manufacturerName = prop.getProperty("alphaNumericManufaturerName");
        enterManufacturer(manufacturerName);
        String actualText = findElementVisibility(By.cssSelector("div>input#Manufacturer"), 15).getAttribute("value");
        if (manufacturerName.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Manufacturer Name field is accept the data as expected. Accepted value : " + actualText);
            logger.info("Manufacturer Name field is accept the data as expected. Accepted value : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Manufacturer Name field is not working as expected. It accept the Value \"" + manufacturerName + "\"");
            logger.info("Manufacturer Name field is not working as expected. It accept the Value \"" + manufacturerName + "\"");
            takeScreenshot("ManufacturerName");
        }
    }

    public void verifyManufacturerFieldSpclCharReject() {
        String[] specialCharReject = {"<", "{", "}", "%", "[", "]", "(", ")", "?:", "|'", ";", "=", "&#", "|", "&+"};
        for (String str : specialCharReject) {
            enterManufacturer(str);
            clickAddListButton();
            WebElement manufacturerError = findElementVisibility(By.xpath("//input[@id='Manufacturer']//parent::div//span[@for='Manufacturer']"), 5);
            if (manufacturerError != null) {
                getTest().log(LogStatus.PASS, "Manufacturer Name field displayed the error message as expected for the character \"" + str + "\"");
                logger.info("Manufacturer Name field displayed the error message as expected for the character \"" + str + "\"");
            } else {
                getTest().log(LogStatus.FAIL, "Manufacturer Name field is not displayed the error message as expected for the character \"" + str + "\"");
                logger.info("Manufacturer Name field is not displayed the error message as expected for the character \"" + str + "\"");
                takeScreenshot("ManufacturerSpclCharRej");
            }
        }
    }

    public void verifyManufacturerFieldSpclCharAccept() {
        String[] specialCharAccept = {"@", "#", "$"};
        for (String str : specialCharAccept) {
            enterManufacturer(str);
            clickAddListButton();
            WebElement manufacturerError = findElementVisibility(By.xpath("//input[@id='Manufacturer']//parent::div//span[@for='Manufacturer']"), 5);
            if (manufacturerError == null) {
                getTest().log(LogStatus.PASS, "Manufacturer Name field accepts the value \"" + str + "\" as expected");
                logger.info("Manufacturer Name field accepts the value \"" + str + "\" as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Manufacturer Name field displayed the error message for the special character \"" + str + "\"");
                logger.info("Manufacturer Name field displayed the error message for the special character \"" + str + "\"");
                takeScreenshot("ManufacturerSpclAcc");
            }
        }
    }

    public void verifyVendorFieldValidation() {
        String vendorName = prop.getProperty("alphaNumericVendorName");
        enterVendor(vendorName);
        String actualText = findElementVisibility(By.cssSelector("div>input#Vendor"), 15).getAttribute("value");
        if (vendorName.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Vendor Name field is accept the alpha numeric characters as expected. Accepted value : " + actualText);
            logger.info("Vendor Name field is accept the alpha numeric characters as expected. Accepted value : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Vendor Name field is not accept the alpha numeric characters as expected. It accept the Value \"" + vendorName + "\"");
            logger.info("Vendor Name field is not accept the alpha numeric characters as expected. It accept the Value \"" + vendorName + "\"");
            takeScreenshot("VendorName");
        }
    }

    public void verifyProductCostFieldBehaviour(String condition) {
        boolean productCostField = findElementVisibility(By.cssSelector("div>input#AssetCost"), 15).isEnabled();
        if (condition.equals("enable")) {
            if (productCostField) {
                getTest().log(LogStatus.PASS, "Product Cost field is enabled when Product Cost toggle is set as \"Yes\" in the company setup page");
                logger.info("Product Cost field is enabled when Product Cost toggle is set as \"Yes\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Product Cost field is disabled when Product Cost toggle is set as \"Yes\" in the company setup page");
                logger.info("Product Cost field is disabled when Product Cost toggle is set as \"Yes\" in the company setup page");
                takeScreenshot("ProductCost");
            }
        } else if (condition.equals("disable")) {
            if (!productCostField) {
                getTest().log(LogStatus.PASS, "Product Cost field is disabled when Product Cost toggle is set as \"No\" in the company setup page");
                logger.info("Product Cost field is disabled when product type is set as \"No\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Product Cost field is enabled when Product Cost toggle is set as \"No\" in the company setup page");
                logger.info("Product Cost field is enabled when Product Cost toggle is set as \"No\" in the company setup page");
                takeScreenshot("ProductCost");
            }
        }
    }

    public void verifyInsuranceRefNumFieldBehaviour(String condition) {
        boolean insuranceRefNumField = findElementVisibility(By.cssSelector("div>input#InsuranceRefNumber"), 15).isEnabled();
        if (condition.equals("enable")) {
            if (insuranceRefNumField) {
                getTest().log(LogStatus.PASS, "Insurance Reference Number field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurance Reference Number field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurance Reference Number field is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurance Reference Number is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                takeScreenshot("InsuranceRefNum");
            }
        } else if (condition.equals("disable")) {
            if (!insuranceRefNumField) {
                getTest().log(LogStatus.PASS, "Insurance Reference Number field is disabled when product type is set as \"No\" in the company setup page");
                logger.info("Insurance Reference Number field is disabled when product type is set as \"No\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurance Reference Number field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                logger.info("Insurance Reference Number field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                takeScreenshot("InsuranceRefNum");
            }
        }
    }

    public void verifyInsuranceDateFieldBehaviour(String condition) {
        boolean insuranceDateField = findElementVisibility(By.cssSelector("div>input#txtInsuranceToDate"), 15).isEnabled();
        if (condition.equals("enable")) {
            if (insuranceDateField) {
                getTest().log(LogStatus.PASS, "Insurance Date field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurance Date field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurance Date field is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurance Date is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                takeScreenshot("InsuranceDate");
            }
        } else if (condition.equals("disable")) {
            if (!insuranceDateField) {
                getTest().log(LogStatus.PASS, "Insurance Date field is disabled when product type is set as \"No\" in the company setup page");
                logger.info("Insurance Reference Number field is disabled when product type is set as \"No\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurance Date field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                logger.info("Insurance Date field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                takeScreenshot("InsuranceDate");
            }
        }
    }

    public void verifyInsurerNameFieldBehaviour(String condition) {
        WebElement insurerNameField = findElementVisibility(By.cssSelector("div>input#InsurerName"), 15);
        if (condition.equals("enable")) {
            if (insurerNameField.isEnabled()) {
                getTest().log(LogStatus.PASS, "Insurer Name field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurer Name field is enabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurer Name field is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                logger.info("Insurer Name is disabled when Insurance Policy toggle is set as \"Yes\" in the company setup page");
                takeScreenshot("InsurerName");
            }
        } else if (condition.equals("disable")) {
            if (!insurerNameField.isEnabled()) {
                getTest().log(LogStatus.PASS, "Insurer Name field is disabled when product type is set as \"No\" in the company setup page");
                logger.info("Insurer Name field is disabled when product type is set as \"No\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Insurer Name field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                logger.info("Insurer Name field is enabled when Insurance Policy toggle is set as \"No\" in the company setup page");
                takeScreenshot("InsurerName");
            }
        }
    }

    public void productCostNumbersOnly() {
        String productCost = prop.getProperty("productCostNumeric");
        enterProductCost(productCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#AssetCost"), 15).getAttribute("value");
        if (actualText.equals(productCost)) {
            getTest().log(LogStatus.PASS, "Product Cost field accepts the data with only numbers as expected");
            logger.info("Product Cost field accepts the data with only numbers as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Product Cost field is not working as expected. It accept the Value \"" + productCost + "\"");
            logger.info("Product Cost field is not working as expected. It accept the Value \"" + productCost + "\"");
            takeScreenshot("ProductCost");
        }
    }

    public void productCostCharandSpclChar() {
        String productCost = prop.getProperty("productCostCharSpclChar");
        enterProductCost(productCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#AssetCost"), 15).getAttribute("value");
        if (actualText.equals("")) {
            getTest().log(LogStatus.PASS, "Product Cost field does not accept the data with character and special character as expected");
            logger.info("Product Cost field does not accept the data with character and special character as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Product Cost field accepts the data with character and special character as expected");
            logger.info("Product Cost field accepts the data with character and special character as expected");
            takeScreenshot("ProductCost");
        }
    }

    public void productCostMinimum() {
        String productCost = prop.getProperty("productCostMinimum");
        enterProductCost(productCost);
        clickAddListButton();
        WebElement errorMessageElement = findElementVisibility(By.xpath("//input[@id='AssetCost']//parent::div//span[@class='valid-feedback invalid-feedback']"), 15);
        if (errorMessageElement == null) {
            getTest().log(LogStatus.PASS, "Product Cost field accepts the data with minimum of one digit as expected");
            logger.info("Product Cost field accepts the data with minimum of one digit as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Product Cost field displayed error message for the char data with minimum of one digit as expected. Error message is " + errorMessageElement.getText());
            logger.info("Product Cost field displayed error message for the char data with minimum of one digit as expected Error message is " + errorMessageElement.getText());
            takeScreenshot("ProductCost");
        }
    }

    public void productCostMaximum() {
        String productCost = prop.getProperty("productCostMaximum");
        enterProductCost(productCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#AssetCost"), 15).getAttribute("value");
        if (actualText.equals(productCost)) {
            getTest().log(LogStatus.PASS, "Product Cost field accepts the data with maximum of 10 digit as expected");
            logger.info("Product Cost field accepts the data with maximum of 10 digit as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Product Cost field does not accepts the data with maximum of 10 digit as expected");
            logger.info("Product Cost field does not accepts the data with maximum of 10 digit as expected");
            takeScreenshot("ProductCost");
        }
    }

    public void productCostMoreThanMaximum() {
        String productCost = prop.getProperty("productCostMoreMaximum");
        enterProductCost(productCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#AssetCost"), 15).getAttribute("value");
        if (actualText.length() <= 10) {
            getTest().log(LogStatus.PASS, "Product Cost field does not accepts the data with more than 10 digit as expected. Accepted Data : " + actualText);
            logger.info("Product Cost field does not accepts the data with more than 10 digit as expected. Accepted Data : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Product Cost field accepts the data with more than 10 digits. Entered Data : " + productCost);
            logger.info("Product Cost field accepts the data with more than 10 digits. Entered Data : " + productCost);
            takeScreenshot("ProductCost");
        }
    }

    public void verifyReferenceNumberFieldPesence() {
        WebElement refPurchaseOrder = findElementVisibility(By.cssSelector("div>input#PurchaseOrder"), 15);
        if (refPurchaseOrder != null) {
            getTest().log(LogStatus.PASS, "The logged in user have option to mention the \"Reference Purchase Order Number\"");
            logger.info("The logged in user have option to mention the \"Reference Purchase Order Number\"");
        } else {
            getTest().log(LogStatus.FAIL, "The logged in user doest not have option to mention the \"Reference Purchase Order Number\"");
            logger.info("The logged in user does not have option to mention the \"Reference Purchase Order Number\"");
            takeScreenshot("RefPurchaseOrder");
        }
    }

    public void verifyRefernceNumberAlphaNumeric() {
        String refPurchaseOrder = prop.getProperty("purchaseNumberAlphaNumeric");
        enterPurchaseOrder(refPurchaseOrder);
        String actualText = findElementVisibility(By.cssSelector("div>input#PurchaseOrder"), 15).getAttribute("value");
        if (actualText.equals(refPurchaseOrder)) {
            getTest().log(LogStatus.PASS, "Reference PurchaseOrder field is accept the data with alpha numeric characters as expected. Entered Data : " + refPurchaseOrder);
            logger.info("Reference PurchaseOrder field is accept the data with alpha numeric characters as expected. Entered Data : " + refPurchaseOrder);
        } else {
            getTest().log(LogStatus.FAIL, "Reference PurchaseOrder field is not working as expected. It accept the Value \"" + refPurchaseOrder + "\"");
            logger.info("Reference PurchaseOrder field is not working as expected. It accept the Value \"" + refPurchaseOrder + "\"");
            takeScreenshot("RefPurchaseOrder");
        }
    }

    public void verifyRefernceNumberSpclCharAccept() {
        String[] specialCharAccept = {"@", "#", "$"};
        for (String str : specialCharAccept) {
            enterPurchaseOrder(str);
            clickAddListButton();
            WebElement refPurchaseOrderError = findElementVisibility(By.xpath("//input[@id='PurchaseOrder']//parent::div//span[@for='PurchaseOrder']"), 5);
            if (refPurchaseOrderError == null) {
                getTest().log(LogStatus.PASS, "Reference Purchase Order field accepts the value \"" + str + "\" as expected");
                logger.info("Reference Purchase Order field accepts the value \"" + str + "\" as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Reference Purchase Order field displayed the error message for the char special character \"" + str + "\"");
                logger.info("Reference Purchase Order field displayed the error message for the char special character \"" + str + "\"");
                takeScreenshot("RefPurchaseOrder");
            }
        }
    }

    public void verifyRefernceNumberSpclCharReject() {
        String[] specialCharReject = {"<", "{", "}", "%", "[", "]", "(", ")", "?:", "|'", ";", "=", "&#", "|", "&+"};
        for (String str : specialCharReject) {
            enterPurchaseOrder(str);
            clickAddListButton();
            WebElement refPurchaseOrderError = findElementVisibility(By.xpath("//input[@id='PurchaseOrder']//parent::div//span[@for='PurchaseOrder']"), 5);
            if (refPurchaseOrderError != null) {
                getTest().log(LogStatus.PASS, "Reference Purchase Order field displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Reference Purchase Order field displayed the error message as expected for the char \"" + str + "\"");
            } else {
                getTest().log(LogStatus.FAIL, "Reference Purchase Order field is not displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Reference Purchase Order field is not displayed the error message as expected for the char \"" + str + "\"");
                takeScreenshot("RefPurchaseOrder");
            }
        }
    }

    public String selectDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        DateTimeFormatter monthName = DateTimeFormatter.ofPattern("MM");
        LocalDateTime now = LocalDateTime.now();
        String monthChar = monthName.format(now);
        inputDate = (date.equals("Old")) ? dtf.format(now.minusDays(1)) : (date.equals("Future")) ? dtf.format(now.plusDays(1)) : dtf.format(now);
        String[] inputDateArray = inputDate.split("/");
        String day = inputDateArray[0];
        String month = inputDateArray[1];
        String year = inputDateArray[2];
        click(By.cssSelector(".picker-switch"), "Month & Year popup", 25);
        click(By.cssSelector("[title='Select Year']"), "Year popup", 15);
        click(By.xpath("//span[contains(@class,'year') and text()='" + year + "']"), "Year Value", 15);
        click(By.xpath("//span[contains(@class,'month') and text()='" + month + "']"), "Month Value", 15);
        String dayClass = findElementVisibility(By.xpath("//td[@data-day='" + monthChar + "/" + day + "/" + year + "']"), 15).getAttribute("class");
        if (!dayClass.contains("disabled")) {
            findElementClickable(By.xpath("//td[@data-day='" + monthChar + "/" + day + "/" + year + "']"), 15).click();
        }
        return dayClass;
    }

    public void verifyWithOldDate(String field) {
        selectDate("Old");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDateTime previousDay = LocalDateTime.now().minusDays(1);
        String expectedDate = dtf.format(previousDay);
        String locator = (field.equals("Order")) ? "txtOrderDate" : (field.equals("Invoice")) ? "InvoiceDate" : (field.equals("Insurance")) ? "txtInsuranceToDate" : (field.equals("Warranty")) ? "txtWarrantyDuration" : "";
        String actualText = findElementVisibility(By.cssSelector("input#" + locator), 15).getAttribute("value");
        if (actualText.equals(expectedDate)) {
            getTest().log(LogStatus.PASS, "Old Date was selected as expected for \"" + field + "\" date field");
            logger.info("Old Date was selected as expected for \"" + field + "\" date field");
        } else {
            getTest().log(LogStatus.FAIL, "Not able to select Old date for \"" + field + "\" date field");
            logger.info("Not able to select Old date for \"" + field + "\" date field");
            takeScreenshot(field + "PastDate");
        }
    }

    public void verifyNotClickablePastDate(String field) {
        String dayClass = selectDate("Old");
        if (dayClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "Not able to select Past Date as expected for \"" + field + "\" date field");
            logger.info("Not able to select Past Date as expected for \"" + field + "\" date field");
        } else {
            getTest().log(LogStatus.FAIL, "Past date can be selected for \"" + field + "\" date field");
            logger.info("Past date can be selected for \"" + field + "\" date field");
            takeScreenshot(field + "PastDate");
        }
    }

    public void verifyNotClickableFutureDate(String field) {
        String dayClass = selectDate("Future");
        if (dayClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "Not able to select Future Date as expected for \"" + field + "\" date field");
            logger.info("Not able to select Future Date as expected for \"" + field + "\" date field");
        } else {
            getTest().log(LogStatus.FAIL, "Future date can be selected for \"" + field + "\" date field");
            logger.info("Future date can be selected for \"" + field + "\" date field");
            takeScreenshot(field + "FutureDate");
        }
    }

    public void verifyClickableFutureDate(String field) {
        selectDate("Future");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDateTime futureDay = LocalDateTime.now().plusDays(1);
        String expectedDate = dtf.format(futureDay);
        String locator = (field.equals("Order")) ? "txtOrderDate" : (field.equals("Invoice")) ? "InvoiceDate" : (field.equals("Insurance")) ? "txtInsuranceToDate" : (field.equals("Warranty")) ? "txtWarrantyDuration" : "";
        String actualText = findElementVisibility(By.cssSelector("input#" + locator), 15).getAttribute("value");
        if (actualText.equals(expectedDate)) {
            getTest().log(LogStatus.PASS, "Future Date is selected for \"" + field + "\" date field");
            logger.info("Future Date is selected for \"" + field + "\" date field");
        } else {
            getTest().log(LogStatus.FAIL, "Not able to select future date for \"" + field + "\" date field");
            logger.info("Not able to select future date for \"" + field + "\" date field");
            takeScreenshot(field + "FutureDate");
        }
    }

    public void verifyWithCurrentDate(String field) {
        selectDate("Curent");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        String locator = (field.equals("Order")) ? "txtOrderDate" : (field.equals("Invoice")) ? "InvoiceDate" : (field.equals("Insurance")) ? "txtInsuranceToDate" : (field.equals("Warranty")) ? "txtWarrantyDuration" : "";
        String actualText = findElementVisibility(By.cssSelector("input#" + locator), 15).getAttribute("value");
        if (actualText.equals(currentDate)) {
            getTest().log(LogStatus.PASS, "Current date is selected as expected for \"" + field + "\" date field");
            logger.info("Current date is selected as expected for \"" + field + "\" date field");
        } else {
            getTest().log(LogStatus.FAIL, "Current date is not selected for \"" + field + "\" date field");
            logger.info("Current date is not selected for \"" + field + "\" date field");
            takeScreenshot(field + "CurrentDate");
        }
    }

    public void verifyOrderDateFieldPresence() {
        WebElement orderDateField = findElementVisibility(By.cssSelector("[data-target='#txtOrderDate']"), 15);
        if (orderDateField != null) {
            getTest().log(LogStatus.PASS, "User have option to select the \"Product Order Date\"");
            logger.info("User have option to select the \"Product Order Date\"");
        } else {
            getTest().log(LogStatus.FAIL, "\"Product Order Date\" field is not displayed for the User");
            logger.info("\"Product Order Date\" field is not displayed for the User");
            takeScreenshot("ProductOrderDate");
        }
    }

    public void verifyInvoiceDateFieldPresence() {
        WebElement orderDateField = findElementVisibility(By.cssSelector("[data-target='#InvoiceDate']"), 15);
        if (orderDateField != null) {
            getTest().log(LogStatus.PASS, "User have option to select the \"Invoice Date\"");
            logger.info("User have option to select the \"Invoice Date\"");
        } else {
            getTest().log(LogStatus.FAIL, "\"Invoice Date\" field is not displayed foe the User");
            logger.info("\"Invoice Date\" field is not displayed foe the User");
            takeScreenshot("InvoiceDate");
        }
    }

    public void clickOrderDateField() {
        scrollToWebelement(By.cssSelector("[data-target='#txtOrderDate']"), "Order Date");
        click(By.cssSelector("[data-target='#txtOrderDate']"), "Order Date", 15);
    }

    public void clickInvoiceDateField() {
        scrollToWebelement(By.cssSelector("[data-target='#InvoiceDate']"), "Invoice Date");
        click(By.cssSelector("[data-target='#InvoiceDate']"), "Invoice Date", 15);
    }

    public void clickInsuranceDateField() {
        scrollToWebelement(By.cssSelector("[data-target='#txtInsuranceToDate']"), "Order Date");
        click(By.cssSelector("[data-target='#txtInsuranceToDate']"), "Order Date", 15);
    }

    public void clickWarrantyDateField() {
        scrollToWebelement(By.cssSelector("[data-target='#txtWarrantyDuration']"), "Order Date");
        click(By.cssSelector("[data-target='#txtWarrantyDuration']"), "Order Date", 15);
    }

    public void verifyInvoiceNumberFieldPresence() {
        WebElement element = findElementVisibility(By.cssSelector("div>input#InvoiceNumber"), 15);
        if (element != null) {
            getTest().log(LogStatus.PASS, "User have option to enter the Invoice Number");
            logger.info("User have option to enter the Invoice Number");
        } else {
            getTest().log(LogStatus.FAIL, "Invoice number field is not displayed to the user");
            logger.info("Invoice number field is not displayed to the user");
            takeScreenshot("InvoiceNumber");
        }
    }

    public void verifyInvoiceNumberAlphaNumeric() {
        String invoiceNumber = prop.getProperty("invoiceNumberAlphaNumeric");
        enterInvoiceNumber(invoiceNumber);
        String actualText = findElementVisibility(By.cssSelector("div>input#InvoiceNumber"), 15).getAttribute("value");
        if (invoiceNumber.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Invoice Number field is accept the data with alpha numeric character as expected. Entered Data : " + invoiceNumber);
            logger.info("Invoice Number field is accept the data with alpha numeric character as expected. Entered Data : " + invoiceNumber);
        } else {
            getTest().log(LogStatus.FAIL, "Invoice Number field is not accepting the data with alpha numeric character as expected. Entered Data : " + invoiceNumber);
            logger.info("Invoice Number field is not accepting the data with alpha numeric character as expected. Entered Data : " + invoiceNumber);
            takeScreenshot("InvoiceNumber");
        }
    }

    public void verifyInvoiceNumberSpclCharAccept() {
        String[] specialCharAccept = {"@", "#", "$"};
        for (String str : specialCharAccept) {
            enterInvoiceNumber(str);
            clickAddListButton();
            WebElement refPurchaseOrderError = findElementVisibility(By.xpath("//input[@id='InvoiceNumber']//parent::div//span[@for='PurchaseOrder']"), 5);
            if (refPurchaseOrderError == null) {
                getTest().log(LogStatus.PASS, "Invoice Number field accepts the value \"" + str + "\" as expected");
                logger.info("Invoice Number field accepts the value \"" + str + "\" as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Invoice Number field displayed the error message for the char special character \"" + str + "\"");
                logger.info("Invoice Number field displayed the error message for the char special character \"" + str + "\"");
                takeScreenshot("InvoiceNumber");
            }
        }
    }

    public void verifyInvoiceNumberSpclCharReject() {
        String[] specialCharReject = {"<", "{", "}", "%", "[", "]", "(", ")", "?:", "|'", ";", "=", "&#", "|", "&+"};
        for (String str : specialCharReject) {
            enterInvoiceNumber(str);
            clickAddListButton();
            WebElement refPurchaseOrderError = findElementVisibility(By.xpath("//input[@id='InvoiceNumber']//parent::div//span[@for='InvoiceNumber']"), 5);
            if (refPurchaseOrderError != null) {
                getTest().log(LogStatus.PASS, "Invoice Number field displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Invoice Number field displayed the error message as expected for the char \"" + str + "\"");
            } else {
                getTest().log(LogStatus.FAIL, "Invoice Number field is not displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Invoice Number field is not displayed the error message as expected for the char \"" + str + "\"");
                takeScreenshot("InvoiceNumber");
            }
        }
    }

    public void verifyInsuranceNumberAlphaNumeric() {
        String insuranceNumber = prop.getProperty("insuranceNumberAlphaNumeric");
        enterInsuranceNumber(insuranceNumber);
        String actualText = findElementVisibility(By.cssSelector("div>input#InsuranceRefNumber"), 15).getAttribute("value");
        if (insuranceNumber.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Insurance Ref Number field accepts the data with alpha numeric character as expected. Entered Date : " + insuranceNumber);
            logger.info("Insurance Ref Number field accepts the data with alpha numeric character as expected. Entered Date : " + insuranceNumber);
        } else {
            getTest().log(LogStatus.FAIL, "Insurance Ref Number field is not accepts the data with alpha numeric character as expected. Entered Date : " + insuranceNumber);
            logger.info("Insurance Ref Number field is not accepts the data with alpha numeric character as expected. Entered Date : " + insuranceNumber);
            takeScreenshot("InvoiceNumber");
        }
    }

    public void verifyInsurerNameAlphaNumeric() {
        String insurerName = prop.getProperty("insurerNameAlphaNumeric");
        enterInsurarName(insurerName);
        String actualText = findElementVisibility(By.cssSelector("div>input#InsurerName"), 15).getAttribute("value");
        if (insurerName.equals(actualText)) {
            getTest().log(LogStatus.PASS, "Insurer Name field is accept the data with alpha numeric character");
            logger.info("Insurer Name field is accept the data with alpha numeric character");
        } else {
            getTest().log(LogStatus.FAIL, "Insurer Name field is not working as expected. It accept the Value \"" + insurerName + "\"");
            logger.info("Insurer Name field is not working as expected. It accept the Value \"" + insurerName + "\"");
            takeScreenshot("InsurerName");
        }
    }

    public void verifyDepreciationRuleField(String depreciation) {
        scrollToWebelement(By.cssSelector("div>select#depreciationId"), "Depreciation Rule");
        WebElement depreciationRule = findElementVisibility(By.cssSelector("div>select#depreciationId"), 15);
        if (depreciation.equals("enable")) {
            if (depreciationRule.isEnabled()) {
                getTest().log(LogStatus.PASS, "Depreciation Rule Field is enabled when toggle in product type page is set as \"Yes\"");
                logger.info("Depreciation Rule field is enabled when toggle in product type page is set as \"Yes\" in the company setup page");
            } else {
                getTest().log(LogStatus.FAIL, "Depreciation Rule field is disabled when toggle in product type page is set as \"Yes\"");
                logger.info("Depreciation Rule field is disabled when toggle in product type page is set as \"Yes\"");
                takeScreenshot("DepreciationRule");
            }
        } else if (depreciation.equals("disable")) {
            if (!depreciationRule.isEnabled()) {
                getTest().log(LogStatus.PASS, "Depreciation Rule field is disabled when toggle in product type page is set as \"No\"");
                logger.info("Depreciation Rule field is disabled when toggle in product type page is set as \"No\"");
            } else {
                getTest().log(LogStatus.FAIL, "Depreciation Rule field is enabled when toggle in product type page is set as \"No\"");
                logger.info("Depreciation Rule is enabled when toggle in product type page is set as \"No\"");
                takeScreenshot("DepreciationRule");
            }
        }
    }

    public void verifyDepreciationDropdown() {
        click(By.cssSelector("div>select#depreciationId"), "Depreciation Rule", 15);
        List<WebElement> depreciationRule = findMultipleElement(By.cssSelector("select#depreciationId>option"), 15);
        if (depreciationRule.size() >= 4) {
            getTest().log(LogStatus.PASS, "Depreciation dropdown values are displayed as expected");
            logger.info("Depreciation dropdown values are displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Depreciation dropdown values are not displayed as expected");
            logger.info("Depreciation dropdown values are not displayed as expected");
        }
    }

    public void verifyProductLifeAsteriskSymbol() {
        int beforeDepreciationRule = findMultipleElement(By.xpath("//div[@class='modal-content']//label//span[text()='*']"), 15).size();
        selectDepreciationRule();
        int afterDepreciationRule = findMultipleElement(By.xpath("//div[@class='modal-content']//label//span[text()='*']"), 15).size();
        if (beforeDepreciationRule < afterDepreciationRule) {
            getTest().log(LogStatus.PASS, "Product Life field displays asterisk symbol after selecting depreciation rule");
            logger.info("Product Life field displays asterisk symbol after selecting depreciation rule");
        } else {
            getTest().log(LogStatus.FAIL, "Product Life field is not displayed asterisk symbol after selecting depreciation rule");
            logger.info("Product Life field is not displayed asterisk symbol after selecting depreciation rule");
            takeScreenshot("ProductLife");
        }
    }

    public void verifyProductLifeMandatoryError() {
        clickAddListButton();
        String productLifeError = findElementVisibility(By.xpath("//input[@id='ItemLife']//parent::div//span[@class='invalid-feedback']"), 20).getText();
        if (productLifeError.contains("Product Life")) {
            getTest().log(LogStatus.PASS, "Product Life field is displayed as mandatory after selecting depreciation rule");
            logger.info("Product Life field is displayed as mandatory after selecting depreciation rule");
        } else {
            getTest().log(LogStatus.FAIL, "Product Life field is not displayed as mandatory after selecting depreciation rule");
            logger.info("Product Life field is not displayed as mandatory after selecting depreciation rule");
            takeScreenshot("ProductLife");
        }
    }

    public void verifyProductLifeNumeric() {
        String year = prop.getProperty("productLifeYear");
        enterProductLife(year);
        String actualText = findElementVisibility(By.cssSelector("div>input#ItemLife"), 15).getAttribute("value");
        if (actualText.equals(year)) {
            getTest().log(LogStatus.PASS, "Product Life field accepts the data with only numeric value as expected. Entered Data : " + year);
            logger.info("Product Life field accepts the data with only numeric value as expected. Entered Data : " + year);
        } else {
            getTest().log(LogStatus.FAIL, "Product Life field does not accepts the data with only numeric value as expected. Entered Data : " + year);
            logger.info("Product Life field doed not accepts the data with only numeric value as expected. Entered Data : " + year);
            takeScreenshot("ProductLife");
        }
    }

    public void verifyProductLifeAlphaNumeric() {
        String year = prop.getProperty("productLifeYearAlphaNumeric");
        enterProductLife(year);
        String actualText = findElementVisibility(By.cssSelector("div>input#ItemLife"), 15).getAttribute("value");
        if (!actualText.equals(year)) {
            getTest().log(LogStatus.PASS, "Product Life field is not accepts the data with alpha numeric character as expected. Accepted data : " + actualText);
            logger.info("Product Life field is not accepts the data with alpha numeric character as expected. Accepted data : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Product Life field accepts the data with alpha numeric character as expected. Accepted data : " + actualText);
            logger.info("Product Life field accepts the data with alpha numeric character as expected. Accepted data : " + actualText);
            takeScreenshot("ProductLife");
        }
    }

    public void verifyProductLifeMaximumChar() {
        String year = prop.getProperty("productLifeYearMaximumChar");
        enterProductLife(year);
        String actualText = findElementVisibility(By.cssSelector("div>input#ItemLife"), 15).getAttribute("value");
        if (!actualText.equals(year)) {
            getTest().log(LogStatus.PASS, "Product Life field not accepts the data with more than 3 digits as expected Accepted Data : " + actualText);
            logger.info("Product Life field not accepts the data with more than 3 digits as expected Accepted Data : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Product Life field accepts the data with more than 3 digits as expected Entered Data : " + year);
            logger.info("Product Life field accepts the data with more than 3 digits as expected Entered Data : " + year);
            takeScreenshot("ProductLife");
        }
    }

    public void verifySalvageCostNumeric() {
        String salvageCost = prop.getProperty("salvageCost");
        enterSalvageCost(salvageCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#SalvageCost"), 15).getAttribute("value");
        if (actualText.equals(salvageCost)) {
            getTest().log(LogStatus.PASS, "Salvage Cost field accepts the data with only numeric values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field accepts the data with only numeric values as expected. Entered Data : " + salvageCost);
        } else {
            getTest().log(LogStatus.FAIL, "Salvage Cost field is not accepts the data with only numeric values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field is not accepts the data with only numeric values as expected. Entered Data : " + salvageCost);
            takeScreenshot("SalvageCost");
        }
    }

    public void verifySalvageCostCharandSpclChar() {
        String salvageCost = prop.getProperty("salvageCostCharandSpclChar");
        enterSalvageCost(salvageCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#SalvageCost"), 15).getAttribute("value");
        if (actualText.equals("")) {
            getTest().log(LogStatus.PASS, "Salvage Cost field not accepts the data with character and special character values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field not accepts the data with character and special character values as expected. Entered Data : " + salvageCost);
        } else {
            getTest().log(LogStatus.FAIL, "Salvage Cost field is accepts the data with character and special character values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field is accepts the data with character and special character values as expected. Entered Data : " + salvageCost);
            takeScreenshot("SalvageCost");
        }
    }

    public void verifySalvageCostMaxChar() {
        String salvageCost = prop.getProperty("salvageCostMaxChar");
        enterSalvageCost(salvageCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#SalvageCost"), 15).getAttribute("value");
        if (actualText.equals(salvageCost)) {
            getTest().log(LogStatus.PASS, "Salvage Cost field accepts the data with maximum of 10 digit values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field accepts the data with maximum of 10 digit values as expected. Entered Data : " + salvageCost);
        } else {
            getTest().log(LogStatus.FAIL, "Salvage Cost field is not accepts the data with maximum of 10 digit values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field is not accepts the data with maximum of 10 digit values as expected. Entered Data : " + salvageCost);
            takeScreenshot("SalvageCost");
        }
    }

    public void verifySalvageCostMorethanMaxChar() {
        String salvageCost = prop.getProperty("salvageCostMoreMaxChar");
        enterSalvageCost(salvageCost);
        String actualText = findElementVisibility(By.cssSelector("div>input#SalvageCost"), 15).getAttribute("value");
        if (!actualText.equals(salvageCost)) {
            getTest().log(LogStatus.PASS, "Salvage Cost field is not accepts the data with more than 10 digit values as expected. Accepted Data : " + actualText);
            logger.info("Salvage Cost field accepts the data with more than of 10 digit values as expected. Accepted Data : " + actualText);
        } else {
            getTest().log(LogStatus.FAIL, "Salvage Cost field accepts the data with more than of 10 digit values as expected. Entered Data : " + salvageCost);
            logger.info("Salvage Cost field accepts the data with more than of 10 digit values as expected. Entered Data : " + salvageCost);
            takeScreenshot("SalvageCost");
        }
    }

    public void verifyAddToListButton() {
        WebElement addToList = findElementClickable(By.cssSelector("#btn_AddRow"), 15);
        if (addToList != null) {
            getTest().log(LogStatus.PASS, "Add to list button is present as clickable");
            logger.info("Add to list button is present as clickable");
        } else {
            getTest().log(LogStatus.FAIL, "Add to list button is not clickable");
            logger.info("Add to list button is not clickable");
            takeScreenshot("AddToList");
        }
    }

    public void verifyDeployList() {
        String modelName = modelNameFromPopup;
        waitForVisibilityOfElement(By.xpath("//div[@class='row']//div[1]//div//table[@id='example']//tbody//tr//td[4]"), 15);
        List<WebElement> modelInList = findMultipleElement(By.xpath("//div[@class='row']//div[1]//div//table[@id='example']//tbody//tr//td[4]"), 15);
        for (WebElement element : modelInList) {
            if (element.getText().equals(modelName)) {
                getTest().log(LogStatus.PASS, "Created product is displayed in the \"Deploy Product\" list when click on the add to list button");
                logger.info("Created product is displayed in the \"Deploy Product\" list when click on the add to list button");
                break;
            } else {
                getTest().log(LogStatus.FAIL, "Created product is not displayed in the \"Deploy Product\" list when click on the add to list button");
                logger.info("Created product is not displayed in the \"Deploy Product\" list when click on the add to list button");
                takeScreenshot(modelName);
            }
        }
    }

    public void clickCancelButton() {
        click(By.cssSelector("a#cancelBtn"), "Cancel", 15);
    }

    public void verifyCancelButtonFunctionality() {
        make100PageSize();
        List<WebElement> modelNameLocatorList = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td[2]"), 15);
        int i = 0;
        for (WebElement element : modelNameLocatorList) {
            i++;
            if (!element.getText().equals(modelNameFromPopup) && i == modelNameLocatorList.size()) {
                getTest().log(LogStatus.PASS, "Cancel button is working as expected");
                logger.info("Cancel button is working as expected");
                break;
            } else if (element.getText().equals(modelNameFromPopup)) {
                getTest().log(LogStatus.FAIL, "Cancel button is not working as expected");
                logger.info("Cancel button is not working as expected");
                takeScreenshot("Cancel");
            }
        }
    }

    public void clickCloseButton() {
        WebElement closeButton = findElementPresence(By.xpath("//div[@role='dialog']//div//div/div//button"), 15);
        if (closeButton != null) {
            click(By.xpath("//div[@role='dialog' and(contains(@style,'display: block'))]//button"), "Close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Pop up close button is not visible");
            logger.info("Pop up close button is not visible");
            takeScreenshot("Popup");
        }
    }

    public void verifyCloseButtonFunctionality() {
        WebElement quantityField = findElementVisibility(By.cssSelector("div>input#Quantity"), 10);
        if (quantityField == null) {
            getTest().log(LogStatus.PASS, "Close button is working as expected");
            logger.info("Close button is working as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Close button is not working as expected");
            logger.info("Close button is not working as expected");
            takeScreenshot("Close");
        }
    }

    public void verifyPaginationFunctionalities() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='deployItemsTable']//tbody//tr[" + recordCount + "]//td[2]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='deployItemsTable']//tbody//tr[" + recordCount + "]//td[2]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='deployItemsTable']//tbody//tr[" + recordCount + "]//td[2]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='deployItemsTable']//tbody//tr[" + recordCount + "]//td[2]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
        String selectRecordPage = prop.getProperty("selectRecordPage");
        waitForLoader(20);
        staticWait(2000);
        selectValueWithValue(By.cssSelector("#pageSize"), selectRecordPage, "Page size", 10);
        waitForLoader(30);
        staticWait(2000);
        String selectedOption = getText(By.xpath("//select[@id='pageSize']//option[@selected='selected']"), 30);
        int checkRecord = Integer.parseInt(selectedOption);
        int recordCount = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr"), 20).size();

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
}
