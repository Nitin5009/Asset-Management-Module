package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Errors;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.Errors.*;
import static utils.Errors.productTypeRequired;
import static utils.StaticData.createdProductName;

public class AddProductPage extends WebBasePage {
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    public static String pName;
    public static String proCode;
    public static String pType;
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    static String itemNameRandomValue;
    static String productCode;
    WebDriver driver;
    boolean tcToggle = false;
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";

    public AddProductPage(WebDriver driver) {
        super(driver, "Add product Page");
        this.driver = driver;
    }

    public String defaultPCode;

    public void clickFullMenuDropDown() {
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 300);
        findElementClickable(By.cssSelector("a#navbarDropdownPortfolio"), 20);
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

    public void clickAddNewButton() {
        click(By.xpath("//i[@class='fa fa-plus']"), "Add New", 80);
    }

    public void verifyBarCodeDropDown() {
        click(By.cssSelector("div>select#BarcodeTypeId"), "Product Type", 15);
        List<WebElement> barcodeOption = driver.findElements(By.xpath("//select[@id='BarcodeTypeId']//option"));
        int i = 0;
        String[] expectedCodeType = {"Select", "Barcode", "QR"};
        for (String expected : expectedCodeType) {
            for (WebElement element : barcodeOption) {
                i++;
                if (element.getText().equals(expected)) {
                    getTest().log(LogStatus.PASS, "BarCode Dropdown values are displayed as expected");
                    logger.info("BarCode Dropdown values are displayed as expected");
                    i = 0;
                    break;
                } else if (i == barcodeOption.size()) {
                    getTest().log(LogStatus.FAIL, "BarCode Dropdown values are Not Displayed");
                    logger.info("BarCode Dropdown values are not displayed");
                    takeScreenshot("BarCode");
                }

            }

        }
    }

    public boolean getTermsAndCondition(String productTypeName) {
        boolean tcToggle = false;
        clickFullMenuDropDown();
        clickAssetManagement();
        findElementClickable(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Product Type']"), 30);
        click(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Product Type']"), "Product type", 20);
        selectValueWithValue(By.cssSelector("select#pageSize"), "100", "Page Size", 30);
        waitForLoader(20);
        WebElement productType = findElementVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + productTypeName + "']"), 30);
        if (productType == null) {
            WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
            if (paginationNavigator != null) {
                WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
                while (paginationLast != null) {
                    click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                    waitForLoader(20);
                    productType = findElementVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + productTypeName + "']"), 30);
                    if (productType != null) {
                        break;
                    }
                    findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                    paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
                }
            }
        }
        scrollToWebelement(By.cssSelector("a#ancCreateDepartment"), "Add button");
        click(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + productTypeName + "']"), "Product Type", 30);
        WebElement toggleYes = findElementVisibility(By.xpath("//input[@id='IsEnableTermsN']//parent::label//span/span[@class='slider-yes']"), 20);
        if (toggleYes != null) {
            tcToggle = true;
        }
        return tcToggle;
    }

    public void selectProductType() {
        String productTypeName = getText(By.xpath("//select[@id='AssetTypeId']//option[2]"), 30);
        boolean toggle = getTermsAndCondition(productTypeName);
        clickFullMenuDropDown();
        clickAssetManagement();
        clickManageProduct();
        clickAddNewButton();
        selectValueWithIndex(By.cssSelector("div>select#AssetTypeId"), 1, "Product Type", 10);
        findElementVisibility(By.xpath("//div[@id='notifymessage']//div[@role='alert']"), 30);
        if (toggle) {
            click(By.cssSelector("button#closenotifymessage"), "Close success message", 20);
        }
    }

    public void enterProductNameHundredCharacter() {
        enter(By.cssSelector("div>input#Name"), prop.getProperty("productName100Charc"), "Product Name", 10);
    }

    public void enterProductNameOneHundredOneCharacter() {
        enter(By.cssSelector("div>input#Name"), prop.getProperty("productName101Char"), "Product Name", 10);
        click(By.xpath("//label[@for='BarcodeTypeId']"), "Barcode", 10);
    }

    public void enterUniqueItemName() {
        itemNameRandomValue = prop.getProperty("productName") + dateValue;
        enter(By.cssSelector("div>input#Name"), itemNameRandomValue, "Product Name", 10);
        createdProductName = itemNameRandomValue;
    }

    public void enterItemName() {
        itemNameRandomValue = prop.getProperty("productName") + dateValue;
        enter(By.cssSelector("div>input#Name"), "I" + itemNameRandomValue, "Product Name", 10);
        createdProductName = itemNameRandomValue;
    }

    public void enterProductNameWithAlphaNumeric() {
        enter(By.cssSelector("div>input#Name"), prop.getProperty("productNameAlphaNumeric"), "Product Name", 10);

    }

    public void enterProductNameWithSpecialCharacter() {
        enter(By.cssSelector("div>input#Name"), prop.getProperty("ProductNameSpecialCharacter"), "Product Name", 10);
        String[] specialCharReject = {"<", "{", "}", "%", "[", "]", "(", ")", "?:", "|'", ";", "=", "&#", "|", "&+"};
        for (String str : specialCharReject) {
            enter(By.cssSelector("div>input#Name"), str, "Product Name", 10);
            click(By.xpath("//label[@for='BarcodeTypeId']"), "barcode", 10);
            WebElement prodNameError = findElementVisibility(By.xpath("//input[@id='Name']//parent::div//span[contains(@class,'invalid-feedback')]"), 5);
            if (prodNameError != null) {
                getTest().log(LogStatus.PASS, "Product Name field displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Product Name field displayed the error message as expected for the char \"" + str + "\"");
            } else {
                getTest().log(LogStatus.FAIL, "Product Name field is not displayed the error message as expected for the char \"" + str + "\"");
                logger.info("Product Name field is not displayed the error message as expected for the char \"" + str + "\"");
                takeScreenshot("ProductName");
            }
        }
    }

    public void selectBarCodeType() {
        selectValueWithIndex(By.cssSelector("select#BarcodeTypeId"), 1, "Barcode type", 10);
    }

    public void validateStatus() {
        Select select = new Select(driver.findElement(By.xpath("//select[@id='StatusId']")));
        WebElement option = select.getFirstSelectedOption();
        String defaultItem = option.getText();
        if (defaultItem.equals("Active")) {
            getTest().log(LogStatus.PASS, "Product Status is Validated successfully on Add product Page and the default status is " + defaultItem);
            logger.info("Product Status is Validated successfully on Add product Page " + defaultItem);
        } else {
            getTest().log(LogStatus.FAIL, "Status is updated wrongly on Add product Page");
            logger.info("Product Status is updated wrongly on Add product Page");
            takeScreenshot("ProductStatus");
        }
    }

    public void selectStatus() {
        selectValueWithIndex(By.cssSelector("select#StatusId"), 1, "status", 10);
    }

    public void changeProductStatus() {
        String pStatus = prop.getProperty("pStatus");
        selectValueWithText(By.cssSelector("select#StatusId"), pStatus, "status", 10);
        String expectedValue = getAtribute(By.xpath("//select[@id='StatusId']//option[text()='" + pStatus + "']"), "value", 10);
        String actualValue = getAtribute(By.cssSelector("select#StatusId"), "value", 10);
        if (expectedValue.equals(actualValue)) {
            getTest().log(LogStatus.PASS, "Status is changed successfully to " + pStatus);
            logger.info("Status is changed successfully to " + pStatus);
        } else {
            getTest().log(LogStatus.FAIL, "Status is not changed to " + pStatus);
            logger.info("Status is not changed to " + pStatus);
            takeScreenshot("Status");
        }

    }

    public void selectAudit() {
        selectValueWithIndex(By.cssSelector("select#AuditFrequency"), 1, "Audit", 10);
    }

    public void selectImageCapture() {
        selectValueWithIndex(By.cssSelector("select#ImageCaptureFrequency"), 1, "Image frequency", 10);
    }

    public void selectCalibration() {
        selectValueWithIndex(By.cssSelector("select#CalibrationFrequency"), 1, "calibration", 10);
    }

    public void enterProductCodeSixCharacter() {
        enter(By.cssSelector("div>input#ItemCode"), prop.getProperty("productCode6Character"), "product code", 10);
    }

    public void enterProductCode() {
        productCode = dateValue.substring(8, 14);
        defaultPCode = getAtribute(By.cssSelector("input#txtitemtypecode"), "value", 30);
        enter(By.cssSelector("div>input#ItemCode"), productCode, "product code", 10);
    }

    public void enterProductCodeWithAlphaNumeric() {
        enter(By.cssSelector("div>input#ItemCode"), prop.getProperty("productCodeAlphaNumeric"), "product code", 10);
    }

    public void enterProductNameWithOneCharacter() {
        enter(By.cssSelector("div>input#Name"), prop.getProperty("productName1Character"), "product code", 10);
    }

    public void uploadDocument() {
        uploadDoc(By.cssSelector("input#flFileDisplay"), filePath + prop.getProperty("testfilejpg"), "Upload document", 10);
    }

    public void enterDescription() {
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description250Character"), "description", 10);
    }

    public void enterDescriptionTwoHundredFiftyCharacter() {
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description250Character"), "description", 10);
    }

    public void enterDescriptionOneCharacter() {
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description1Character"), "description", 10);
    }

    public void clickTheCheckBox() {
        clickByJavascript(By.cssSelector("input#CreateUniqueNameListing"), "unique name", 10);
    }

    public void enterStockValueForCompany() {
        enter(By.cssSelector("input#for_Company"), prop.getProperty("companyStock"), "For company", 10);
    }

    public void enterStockValueForEmployee() {
        enter(By.cssSelector("input#for_Employee"), prop.getProperty("employeeStock"), "For company", 10);
    }

    public void clickNextButton() {
        click(By.cssSelector("a#btnNext"), "Next button", 10);
        waitForVisibilityOfElement(By.xpath("//div[@aria-labelledby='DeployProduct-tab']//section[@class='page-action']//span"), 30);
        staticWait(2000);
        String deployTab = getText(By.xpath("//div[@aria-labelledby='DeployProduct-tab']//section[@class='page-action']//span"), 60, 60);
        String deployProduct = prop.getProperty("deployTab");
        if (deployProduct.equals(deployTab)) {
            getTest().log(LogStatus.PASS, "Deploy Product Tab page is displayed successfully ");
            logger.info("Deploy Product Tab page is displayed successfully");
        } else {
            getTest().log(LogStatus.FAIL, "Deploy Product Tab page is not displayed successfully ");
            logger.info("Deploy product Tab page is not displayed");
            takeScreenshot("ProductPage");
        }

    }

    public void clickSaveButton() {
        click(By.cssSelector("a#btnSave"), "Save", 10);
    }

    public void goBackToAddProductPage() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[5]"), 15);
        if (element != null) {
            try {
                click(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[5]"), "Product Name", 10);
            } catch (Exception e) {
                clickByJavascript(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[5]"), "Product Name", 10);
            }
        } else {
            getTest().log(LogStatus.FAIL, "ProductPage is not displayed");
            logger.info("ProductPage is not displayed");
            takeScreenshot("ProductPage");
        }
    }

    public void manageProductPageValidation() {
        waitForVisibilityOfElement(By.xpath("//table[@id='tablelistingdata']/tbody/tr[@class='low-bar odd']"), 30);
        String productName = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[5]"), 15);
        createdProductName = productName;
        String productCodeApp = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[4]"), 15);
        if (itemNameRandomValue.equals(productName) && (defaultPCode + productCode).equals(productCodeApp)) {
            getTest().log(LogStatus.PASS, "Added Product is saved in the product list page and product list page is also displayed");
            logger.info("Added Product is saved in the product list page and product list page is also displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Added Product is saved in the product list page and product list page is not displayed");
            logger.info("Added Product is saved in the product list page and product list page is not displayed");
            takeScreenshot("ManageProductPage");
        }
    }

    public void defaultProductCodeValidation() {
        String productCodeApp = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[4]"), 15);
        if ((defaultPCode + productCode).equals(productCodeApp)) {
            getTest().log(LogStatus.PASS, "Entered Product code is displayed in the combination of Product type + Product code as " + (defaultPCode + productCode));
            logger.info("Entered Product code is displayed with the combination of Product type + Product code as " + (defaultPCode + productCode));
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product code is not displayed in the combination of Product type + Product code as ");
            logger.info("Entered Product code is not displayed in the combination of Product type + Product code as ");
            takeScreenshot("ProductCode");
        }
    }

    public void handleSuccessPopup() {
        waitForVisibilityOfElement(By.cssSelector("div.alert-success"), 20);
        click(By.cssSelector("#closenotifymessage"), "Close Popup", 15);
    }

    public void uploadInvalidDoc() {
        scrollDown();
        uploadDoc(By.cssSelector("input#flFileDisplay"), filePath + prop.getProperty("testfiletiff"), "Upload document", 20);
    }

    public void verifyErrorMessageForInvalidDoc() {
        waitForVisibilityOfElement(By.xpath("//div[@id='notifymessage' and @style='display: flex;']"), 20);
        String documentErrorMsg = prop.getProperty("testfiletiff") + Errors.uploadValidDocuments;
        String docErrorMsg = getText(By.xpath("//div[@role='alert']/span"), 10);
        if (documentErrorMsg.equals(docErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error Message is successfully displayed in uploading document field on Add product page");
            logger.info("Error Message is displayed successfully displayed as " + documentErrorMsg);

        } else {
            getTest().log(LogStatus.FAIL, "Error Message is not displayed in uploading document field on Add product page ");
            logger.info("Error Message is not displayed in uploading document field on Add product page");
            takeScreenshot("Document");
        }
        click(By.xpath("//div[@role='alert']/button[@id='closenotifymessage']"), "Close Alert Message", 10);
    }

    public void uploadDocValidation() {
        uploadDoc(By.cssSelector("input#flFileDisplay"), filePath + prop.getProperty("testfilejpg"), "uploaded document", 10);
        String docName = getAtribute(By.xpath("//div[@class='custom-file']//div//input[@type='text']"), "value", 20);
        uploadDoc(By.cssSelector("input#flFileDisplay"), filePath + prop.getProperty("testfilePDF"), "Upload document", 10);
        String doc2Name = getAtribute(By.xpath("//div[@class='custom-file']//div//input[@type='text']"), "value", 20);
        if (!docName.equals("") && !doc2Name.equals("")) {
            if (docName.equals(doc2Name)) {
                getTest().log(LogStatus.FAIL, "Newly uploaded Document is not reflected");
                logger.info("Newly uploaded Document is not reflected");
                takeScreenshot("Upload");
            } else if (doc2Name.equals(prop.getProperty("testfilePDFfileName"))) {
                getTest().log(LogStatus.PASS, "Document upload field is working as expected");
                logger.info("Document upload field is working as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Document upload field is not working as expected");
                logger.info("Document upload field is not working as expected");
                takeScreenshot("Upload");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Not able to get the file Name");
            logger.info("Not able to get the file Name");
            takeScreenshot("FileName");

        }

    }

    public void getProductCode() {

        new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

        String productCode = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[4]//span"), 30);
        proCode = productCode.substring(4, 10);
    }

    public void getProductName() {
        String productName = getText(By.xpath("//a[normalize-space(@id)='ancEditAssetType']"), 10);
        pName = productName;
    }

    public void productNameValidation() {
        String duplicateNameErrorMsg;
        enter(By.cssSelector("div>input#Name"), pName, "Product Name", 10);
        click(By.xpath("//label[@for='BarcodeTypeId']"), "Barcode", 10);
        String nameErrorMsg = Errors.duplicateItemName;
        duplicateNameErrorMsg = getText(By.xpath("//input[@id='Name']//parent::div//span[contains(@class,'invalid-feedback')]"), 20);
        if (duplicateNameErrorMsg.equals(nameErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error Message is successfully displayed in product Name field on Add product page as " + nameErrorMsg);
            logger.info("Error Message is successfully displayed as " + nameErrorMsg);
        } else {
            getTest().log(LogStatus.FAIL, "Error Message is not displayed in product Name field on Add product page");
            logger.info("Error Message is not displayed");
            takeScreenshot("ProductName");
        }

    }

    public void errorMsgValidationOnDescription() {
        waitForVisibilityOfElement(By.cssSelector("textarea#Description"), 30);
        String errorMsg = Errors.descriptionErrorMsg;
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description251Character"), "description", 10);
        click(By.cssSelector("input#for_Employee"), "For company", 10);
        String descriptionErrorMsg = getText(By.xpath("//*[@id='Description']//parent::div//span[contains(@class,'invalid-feedback')]"), 10);
        if (descriptionErrorMsg.equals(errorMsg)) {
            getTest().log(LogStatus.PASS, "Error Message is successfully displayed in description field on Add product page as " + errorMsg);
            logger.info("Error Message is successfully displayed in description field on Add product page as " + errorMsg);
        } else {
            getTest().log(LogStatus.FAIL, "Error Message is Not displayed in description field on Add product page");
            logger.info("Error Message is not displayed in description field on Add product page");
            takeScreenshot("Description");
        }
        driver.navigate().refresh();
    }

    public void productNameErrorMsgValidation() {
        String errorMsgs = productNameReuired;
        scrollToWebelement(By.xpath("//input[@id='Name']//parent::div//span[contains(@class,'invalid-feedback')]"), "Product Name");
        String pNameErrorMsg = getText(By.xpath("//input[@id='Name']//parent::div//span[contains(@class,'invalid-feedback')]"), 15);
        if (errorMsgs.equals(pNameErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error Message is successfully displayed in product name field as " + errorMsgs);
            logger.info("Error Message is successfully displayed in product name field as " + errorMsgs);

        } else {
            getTest().log(LogStatus.FAIL, "Error Message is not displayed in product name field");
            logger.info("Error Message is not displayed in product name field");
            takeScreenshot("ProductName");
        }
    }

    public void productTypeValidation() {
        String errorMsg = productTypeRequired;
        scrollToWebelement(By.xpath("//select[@id='AssetTypeId']"), "Product Type");
        String productTypeErrorMsg = getText(By.xpath("//select[@id='AssetTypeId']//parent::div//span[@class='invalid-feedback']"), 10);
        if (errorMsg.equals(productTypeErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error Message is successfully displayed in product type field as " + errorMsg);
            logger.info("Error Message is successfully displayed in product type field as " + errorMsg);

        } else {
            getTest().log(LogStatus.FAIL, "Error Messgae is not displayed in product type field");
            logger.info("Error Messgae is not displayed in product type field");
            takeScreenshot("ProductType");
        }
        driver.navigate().refresh();
    }

    public void getProductTYpe() {
        String productType = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[3]//span"), 10);
        pType = productType;

    }

    public void selectPtype() {
        selectValueWithText(By.cssSelector("div>select#AssetTypeId"), pType, "product type", 20);
    }

    public void enterDuplicateProductCode() {
        enter(By.cssSelector("div>input#ItemCode"), proCode, "product code", 10);
    }

    public void duplicateProductCodeValidation() {
        WebElement duplicateError = findElementVisibility(By.cssSelector(".alert-dismissible"), 30);
        if (duplicateError != null) {
            String pCodeErrorMsg = getText(By.cssSelector(".alert-dismissible>span"), 20);
            if (pCodeErrorMsg.equals(Errors.duplicateProductCode)) {
                getTest().log(LogStatus.PASS, "Error Message is successfully displayed on product code field as " + pCodeErrorMsg);
                logger.info("Error Message is successfully displayed on product code field as " + pCodeErrorMsg);
            } else {
                handleSuccessPopup();
                getTest().log(LogStatus.FAIL, "Error Message is not displayed as expected for product code field");
                logger.info("Error Message is not Displayed as expected");
                takeScreenshot("ProductCode");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Error message for Duplicate product code is not displayed and product is created");
            logger.info("Error message for Duplicate product code is not displayed and product is created");
            takeScreenshot("ProductCode");
        }
    }

    public void mandatoryFieldValidations() {
        int i = 0;
        click(By.cssSelector("a#btnSave"), "save button", 60);
        List<WebElement> errorMessageLocator = findMultipleElement(By.cssSelector("[class='invalid-feedback']"), 15);
        List<WebElement> expectedErrorMSg = errorMessageLocator;
        String[] expectedValue = {"Product Type", "Product Name", "Barcode Type"};
        for (Object expected : expectedValue) {
            for (WebElement element : expectedErrorMSg) {
                i++;
                String actualText = element.getText();
                String expectedText = expected.toString();
                if (actualText.indexOf(expectedText) != -1) {
                    getTest().log(LogStatus.PASS, "Error message for \"" + expected + "\" field is displayed as expected");
                    logger.info("Error message for \"" + expected + "\" field is displayed as expected");
                    i = 0;
                    break;
                } else if (i == expectedValue.length && !element.getText().contains(expectedText)) {
                    getTest().log(LogStatus.FAIL, "Error message for \"" + expected + "\" field is not displayed");
                    logger.info("Error message for \"" + expected + "\" field is not displayed as expected");
                    takeScreenshot(expected.toString());
                }
            }
        }

    }

    public void checkNotUniqueName() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        RelatedInformationPage relatedInformation = new RelatedInformationPage(driver);
        deployProduct.navigateToDeployTab();
        deployProduct.clickAddDeployButton();
        deployProduct.clickLocationDropdown();
        deployProduct.selectLocationValueFromDropdown("");
        deployProduct.enterModel("Model");
        deployProduct.enterQuantity(prop.getProperty("notUniqueNameQuantity"));
        deployProduct.enterUnitPrice("5");
        deployProduct.enterProductCost("5");
        deployProduct.clickAddListButton();
        deployProduct.clickSaveButton();
        deployProduct.handleSuccessPopup();
        relatedInformation.clickRelatedInformationTab();

        String actualProductCount = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td[4]//span"), 20).trim();
        if (actualProductCount.equals(prop.getProperty("notUniqueNameQuantity"))) {
            getTest().log(LogStatus.PASS, "Product is created with non unique name as expected. And created product quantity is " + actualProductCount);
            logger.info("Product is created with non unique name as expected. And created product quantity is " + actualProductCount);
        } else {
            getTest().log(LogStatus.FAIL, "Product is created with unique name when disable the Unique name toggle");
            logger.info("Product is created with unique name when disable the Unique name toggle");
            takeScreenshot("NotUniqueName");
        }
    }

    public void checkUniqueName() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        RelatedInformationPage relatedInformation = new RelatedInformationPage(driver);
        deployProduct.navigateToDeployTab();
        deployProduct.clickAddDeployButton();
        deployProduct.clickLocationDropdown();
        deployProduct.selectLocationValueFromDropdown("");
        deployProduct.enterQuantity(prop.getProperty("uniqueNameQuantity"));
        deployProduct.enterModel("Model");
        deployProduct.enterUnitPrice("1");
        deployProduct.enterProductCost("1");
        deployProduct.clickAddListButton();
        deployProduct.clickSaveButton();
        deployProduct.handleSuccessPopup();
        relatedInformation.clickRelatedInformationTab();

        int actualProductCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 20).size();
        if (actualProductCount == (Integer.parseInt(prop.getProperty("uniqueNameQuantity")))) {
            getTest().log(LogStatus.PASS, "Product is created with unique name as expected. Total no.of product created with unique name is " + actualProductCount);
            logger.info("Product is created with unique name as expected. Total no.of product created with unique name is " + actualProductCount);
        } else {
            getTest().log(LogStatus.FAIL, "Product is not created with unique name as expected when enable the Unique name toggle");
            logger.info("Product is not created with unique name as expected when enable the Unique name toggle");
            takeScreenshot("UniqueName");
        }
    }

    public void verifymandatoryFieldValidationOnAsteriskSymbolField() {
        int i = 0;
        String actualText;
        String expectedText;
        List<WebElement> errorMessageLocator = findMultipleElement(By.cssSelector("[class='invalid-feedback']"), 15);
        String[] expectedValue = {"Product Type", "Product Name", "Barcode Type"};
        for (Object expected : expectedValue) {
            WebElement asteriskField = findElementPresence(By.xpath("//label[text()='" + expected + ":']//span"), 15);
            if (asteriskField != null) {
                getTest().log(LogStatus.PASS, "The Asterisk symbol is displayed for " + expected + " field");
                logger.info("The Asterisk symbol is displayed for " + expected + " field");
            } else {
                getTest().log(LogStatus.FAIL, "The Asterisk symbol is not displayed for " + expected + " field");
                logger.info("The Asterisk symbol is not displayed for " + expected + " field");
                scrollToWebelement(By.xpath("//label[text()='" + expected + ":']//span"), expected.toString());
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
                } else if (i == expectedValue.length && actualText.indexOf(expectedText) == -1) {
                    getTest().log(LogStatus.FAIL, "Error message for \"" + expected + "\" field is not displayed as expected");
                    logger.info("Error message for \"" + expected + "\" field is not displayed as expected");
                    takeScreenshot(expected.toString());
                }
            }
        }
    }

}
