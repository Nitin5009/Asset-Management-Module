package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;

public class ProductTypePage extends WebBasePage {
    WebDriver driver;
    AddProductPage addProductPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    String pName;
    String changePName;
    String changePNameTwo;
    String itemNameRandomValue;
    String productTypeNameRandomValue;
    String containerNameRandomValue;
    String productCode;
    String productTypeCode;
    String changeProductTypeCode;
    String changeProductTypeTwoCode;
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
    String fileName;
    String proTypeCode;
    String prodTypeCode;
    String prodTypeDescription;
    String statusOfRecord;
    int filesInDirectory;
    List<String> productsToDelete = new ArrayList<>();


    public ProductTypePage(WebDriver driver) {
        super(driver, "Product type Page");
        this.driver = driver;
        this.addProductPage = new AddProductPage(driver);
    }

    public void clickFullMenuDropDown() {
        addProductPage.clickFullMenuDropDown();
    }

    public void clickAssetManagement() {
        addProductPage.clickAssetManagement();
    }

    public void clickProductType() {
        WebElement productTypeOption = findElementVisibility(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Product Type']"), 15);
        if (productTypeOption != null) {
            click(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Product Type']"), "Product type", 20);
        } else {
            clickAssetManagement();
            clickProductType();
        }
    }

    public void verifyProductTypeListingPage() {
        WebElement element = findElementsVisibility(By.xpath("//div[@class='listing']//table[@id='tblAsset']"));
        if (element != null) {
            getTest().log(LogStatus.PASS, "Product type Listing Page is displayed");
            logger.info("Product type Listing Page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Product type Listing Page is not displayed");
            logger.info("Product type Listing Page is not displayed");
            takeScreenshot("Product type Listing");
        }
    }

    public void VerifyBreadCrumbInProductTypePage() {
        WebElement element = findElementsVisibility(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']"));
        if (element.isDisplayed()) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed");
            logger.info("BreadCrumb is displayed");
            clickCompanySetupFromBreadCrumb();
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed");
            logger.info("BreadCrumb is not displayed");
            takeScreenshot("BreadCrumb");
        }
    }

    public void clickCompanySetupFromBreadCrumb() {
        click(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']//li//a[text()='Company Setup']"), "Company Setup from breadcrumb", 20);
        verifyCompanySetupPage();
    }

    public void verifyCompanySetupPage() {
        String actualText = getText(By.xpath("//div[contains(@class,'heme-primary partition')]//span[text()='Announcement']"), 20).trim();
        if (actualText.equals("Announcement")) {
            getTest().log(LogStatus.PASS, "BreadCrumb is Clicked and Company Setup Page is displayed");
            logger.info("BreadCrumb is Clicked and Company Setup Page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not clicked and Company Setup Page is not displayed");
            logger.info("BreadCrumb is not clicked and Company Setup Page is not displayed");
            takeScreenshot("BreadCrumb is not clicked");
        }
    }

    public void enterProductNameInSearchBox() {
        pName = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[4]/a"), 20).trim();
        enter(By.cssSelector("div>input#search"), pName, "Product name", 20);
    }

    public void verifySearchBoxAfterReset() {
        String enteredName = getAtribute(By.cssSelector("div>input#search"), "value", 20);
        clickResetButton();
        waitForVisibilityOfElement(By.cssSelector("div>input#search"), 30);
        String enteredNameAfterReset = getAtribute(By.cssSelector("div>input#search"), "value", 20);
        if (!enteredNameAfterReset.equals(enteredName)) {
            getTest().log(LogStatus.PASS, "Entered text is cleared in the search field text box");
            logger.info("Entered text is cleared in the search field text box");
        } else {
            getTest().log(LogStatus.FAIL, "Entered text is not cleared in the search field text box");
            logger.info("Entered text is not cleared in the search field text box");
            takeScreenshot("Entered text is not cleared");
        }

    }

    public void clickResetButton() {
        findElementClickable(By.xpath("//a//i[@class='fa fa-refresh']"), 80);
        click(By.xpath("//a//i[@class='fa fa-refresh']"), "Reset button", 20);
    }

    public void clickSearchButton() {
        click(By.cssSelector("div>a#Go"), "Search button", 20);
    }

    public void verifySearchedResult() {
        waitForLoader(3);
        String actualProdName = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[4]/a"), 20).trim();
        if (actualProdName.equals(pName)) {
            getTest().log(LogStatus.PASS, "Searched Product is displayed as " + actualProdName);
            logger.info("Searched Product is displayed as " + actualProdName);
        } else {
            getTest().log(LogStatus.FAIL, "Searched Product is not displayed");
            logger.info("Searched Product is displayed");
            takeScreenshot("Searched Product");
        }
    }

    public void clickTheCheckBox() {
        List<WebElement> checkBox = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[1]/div"), 30);
        int iteration = 0;
        for (WebElement ele : checkBox) {
            iteration++;
            String checkBoxStatus = ele.findElement(By.tagName("input")).getAttribute("disabled");
            if (checkBoxStatus == null) {
                click(By.xpath("//table[@id='tblAsset']/tbody/tr[" + iteration + "]/td[1]/div"), "Check box of record - " + iteration, 20);
                break;
            }
        }
    }

    public void verifyDeleteButton() {
        WebElement element = findElementClickable(By.cssSelector("a#DeleteMultiple"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Delete button is active after selecting the checkbox");
            logger.info("Delete button is active after selecting the checkbox");
        } else {
            getTest().log(LogStatus.FAIL, "Delete button is not active after selecting the checkbox");
            logger.info("Delete button is not active after selecting the checkbox");
            takeScreenshot("Delete button is not active");
        }
    }

    public void clickMultiSelectCheckbox() {
        List<WebElement> records = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr//input[@name='DeleteInputs']//parent::div"), 30);
        int iteration = 0;
        for (WebElement ele : records) {
            String checkBox = ele.findElement(By.xpath("//input")).getAttribute("disabled");
            if (checkBox == null) {
                iteration++;
                click(By.xpath("//table[@id='tblAsset']//tbody//tr[" + iteration + "]//input[@name='DeleteInputs']//parent::div"), "Check box of record - " + iteration, 20);
                String productNameToDelete = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[" + iteration + "]/td//a"), 30).trim();
                productsToDelete.add(productNameToDelete);
                if (productsToDelete.size() == 2) {
                    break;
                }
            }
        }
    }

    public void changeStatusToInActive() {
        click(By.xpath("//a[contains(@class,'inactiveicon')]"), "Inactive button", 20);

    }

    public void verifyStatusIsChangedToActive() {
        waitForLoader(20);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[7]//select//option[@selected='selected']"), 50);
        List<String> result = new ArrayList<>();
        for (WebElement element : elements) {
            String status = element.getText().trim();
            if (status.equalsIgnoreCase("Active")) {
                result.add("true");
            } else {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, "Status is changed to Active for all the records");
            logger.info("Status is changed to Active for all the records");
        } else {
            getTest().log(LogStatus.FAIL, "Status is not changed to Active for all the records");
            logger.info("Status is not changed to Active for all the records");
            scrollToWebelement(By.xpath("//table[@id='tblAsset']/tbody/tr[1]/td[7]//select"), "Status Popup");
            takeScreenshot("ChangedStatus");
        }

    }

    public void verifyStatusIsChangedToInActive() {
        waitForLoader(20);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[7]//select//option[@selected='selected']"), 50);
        List<String> result = new ArrayList<>();
        for (WebElement element : elements) {
            String status = element.getText().trim();
            if (status.equalsIgnoreCase("Inactive")) {
                result.add("true");
            } else {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, "Status is changed to Inactive for all the records");
            logger.info("Status is changed to Inactive for all the records");
        } else {
            getTest().log(LogStatus.FAIL, "Status is not changed to Inactive for all the records");
            logger.info("Status is not changed to Inactive for all the records");
            takeScreenshot("ChangedStatus");
        }

    }

    public void changeStatusToActive() {
        click(By.xpath("//a[contains(@class,'white activeicon')]"), "Active button", 20);

    }

    public void clickConfirmInConfirmationPopup() {
        WebElement element = findElementVisibility(By.xpath("//div[contains(@class,'notifybox')]"), 20);
        if (element != null) {
            click(By.xpath("//button[@title='OK']"), "OK", 20);
            waitForLoader(20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation Popup is not displayed");
            logger.info("Confirmation Popup is not displayed");
            takeScreenshot("Confirmation Popup is not displayed");
        }
    }

    public void handelSuccessPopup() {
        waitForLoad(10);
        WebElement element = findElementVisibility(By.xpath("//div[@id='notifymessage']//div[@role='alert']"), 20);
        if (element != null) {
            click(By.cssSelector("button#closenotifymessage"), "Close success message", 60);
            waitForLoader(20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed");
            logger.info("Success message is not displayed");
            takeScreenshot("SuccessMessage");
        }
    }

    public void clickBulkDeleteButton() {
        click(By.cssSelector("a#DeleteMultiple"), "Delete", 20);


    }

    public void verifyAfterDeletingTheAllRecords() {
        for (String str : productsToDelete) {
            List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr/td//a"), 30);
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
    }

    public void clickAddButton() {
        scrollToWebelement(By.xpath("//span[@id='showHideMenuParent']"), "Menu name");
        click(By.cssSelector("a#ancCreateDepartment"), "Add", 20);
    }

    public void enterProductTypeName() {
        itemNameRandomValue = prop.getProperty("productTypeName") + simpleDateFormat.format(new Date());
        enter(By.cssSelector("div>input#AssetType"), itemNameRandomValue, "Product Type Name", 20);
        productTypeNameRandomValue = getAtribute(By.cssSelector("div>input#AssetType"), "value", 20);
    }

    public void selectStatus() {
        selectValueWithIndex(By.cssSelector("select#StatusId"), 0, "Status", 20);
    }

    public void enterProductCode() {
        productCode = dateValue.substring(10, 14);
        enter(By.cssSelector("div>input#AssetTypeCode"), productCode, "product code", 10);
        productTypeCode = getAtribute(By.cssSelector("div>input#AssetTypeCode"), "value", 20);

    }

    public void clickContainerToggle() {
        scrollUpDown("up");
        click(By.xpath("//label[@for='IsContainer']//parent::div//span[@class='slider round']"), "Container toggle", 20);
    }

    public void enterContainerName() {
        itemNameRandomValue = prop.getProperty("containerName") + dateValue;
        enter(By.xpath("//input[contains(@id,'container_name')]"), itemNameRandomValue, "Container Name", 30);
        containerNameRandomValue = getAtribute(By.xpath("//input[contains(@id,'container_name')]"), "value", 20);
    }

    public void uploadProductImage() {
        uploadDoc(By.cssSelector("input#flFile"), filePath + prop.getProperty("testfilejpg"), "Upload Product Type Image", 20);
        fileName = getAtribute(By.cssSelector("input#flFile"), "value", 20);
    }

    public void enterDescription() {
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description250Character"), "description", 10);
        prodTypeDescription = getAtribute(By.cssSelector("textarea#Description"), "value", 20);
    }

    public void enterDescriptionForValidation() {
        enter(By.cssSelector("textarea#Description"), prop.getProperty("description1Character"), "description", 10);
        prodTypeDescription = getAtribute(By.cssSelector("textarea#Description"), "value", 20);
    }

    public void enableAssetToggle() {
        click(By.xpath("//label[@for='IsAsset']//parent::div//span[@class='slider round']"), "Asset Toggle", 20);
    }

    public void enableConsumableToggle() {
        click(By.xpath("//label[@for='IsConsumable']//parent::div//span[@class='slider round']"), "Consumable Toggle", 20);
    }

    public void enableLicenseToggle() {
        click(By.xpath("//label[@for='IsLicense']//parent::div//span[@class='slider round']"), "License Toggle", 20);
    }

    public void enableMaterialSparePartsToggle() {
        click(By.xpath("//label[@for='IsSpare']//parent::div//span[@class='slider round']"), "License Toggle", 20);
    }

    public void enableTimAndAttendanceToggle() {
        click(By.xpath("//label[text()='Time and Attendance:']//parent::div//span[@class='slider round']"), "Time and Attendance toggle", 20);
    }

    public void enableCameraToggle() {
        click(By.xpath("//label[text()='Camera:']//parent::div//span[@class='slider round']"), "Camera toggle", 20);
    }

    public void enableRFIDToggle() {
        click(By.xpath("//label[text()='RFID:']//parent::div//span[@class='slider round']"), "RFID toggle", 20);
    }

    public void enableGPSTrackingToggle() {
        click(By.xpath("//label[text()='GPS Tracking:']//parent::div//span[@class='slider round']"), "GPS Tracking toggle", 20);
    }

    public void enableIsTransferableToggle() {
        click(By.xpath("//label[@for='IsTransferable']//parent::div//span[@class='slider round']"), "Is Transferable toggle", 20);
    }

    public void enableSelfCheckOutToggle() {
        click(By.xpath("//label[@for='IsSelfCheckOut']//parent::div//span[@class='slider round']"), "Self CheckOut toggle", 20);
    }

    public void enableEmployeeAcceptanceToggle() {
        click(By.xpath("//label[@for='IsEmployeeAcceptance']//parent::div//span[@class='slider round']"), "Employee Acceptance toggle", 20);
    }

    public void enableTermsAndConditionsToggle() {
        click(By.xpath("//label[text()='Enable Terms and Conditions:']//parent::div//span[@class='slider round']"), "Terms And Conditions toggle", 20);
    }

    public void enableGPSEnableToggle() {
        click(By.xpath("//label[text()='GPS Enable:']//parent::div//span[@class='slider round']"), "GPS Enable toggle", 20);
    }

    public void enableFleetManagementToggle() {
        click(By.xpath("//label[text()='Fleet Management:']//parent::div//span[@class='slider round']"), "Fleet Management toggle", 20);
    }

    public void enablePenaltyOnReturnOverdueToggle() {
        click(By.xpath("//label[text()='Penalty On Return Overdue:']//parent::div//span[@class='slider round']"), "Penalty On Return Overdue toggle", 20);
    }

    public void entergracePeriod() {
        enter(By.cssSelector("div>input#GracePeriod"), prop.getProperty("gracePeriod"), "Grace Period", 20);
    }

    public void enableInsurancePolicyToggle() {
        click(By.xpath("//label[@for='InsurancePolicy']//parent::div//span[@class='slider round']"), "Insurance Policy toggle", 20);
    }

    public void enableCalibrationToggle() {
        click(By.xpath("//label[@for='IsCalibration']//parent::div//span[@class='slider round']"), "Calibration toggle", 20);
    }

    public void enableProductCostToggle() {
        click(By.xpath("//label[@for='IsItemCost']//parent::div//span[@class='slider round']"), "ProductCost toggle", 20);
    }

    public void enableWarrantyToggle() {
        click(By.xpath("//label[@for='IsWarranty']//parent::div//span[@class='slider round']"), "Warranty toggle", 20);
    }

    public void enableDepreciableToggle() {
        click(By.xpath("//label[@for='IsDepreciable']//parent::div//span[@class='slider round']"), "Depreciable toggle", 20);
    }

    public void enableToggles() {
        enableAssetToggle();
        enableConsumableToggle();
        enableLicenseToggle();
        enableMaterialSparePartsToggle();
        enableTimAndAttendanceToggle();
        enableCameraToggle();
        enableRFIDToggle();
        enableGPSTrackingToggle();
        enableIsTransferableToggle();
        //enableSelfCheckOutToggle();
        enableEmployeeAcceptanceToggle();
        enableTermsAndConditionsToggle();
        enableGPSEnableToggle();
        enableFleetManagementToggle();
        enablePenaltyOnReturnOverdueToggle();
        entergracePeriod();
        enableInsurancePolicyToggle();
        enableCalibrationToggle();
        enableProductCostToggle();
        enableWarrantyToggle();
        enableDepreciableToggle();

    }

    public void createProductTypeWithUploadingImage() {
        clickAddButton();
        enterProductTypeName();
        selectStatus();
        clickContainerToggle();
        enterContainerName();
        enterProductCode();
        uploadProductImage();
        enterDescription();
        enableToggles();
        clickSaveButton();
        handelSuccessPopup();
        verifyProductTypeCode();
    }

    public void createProductTypeWithoutUploadingImage() {
        getProductTypeCode();
        clickAddButton();
        verifyAddProductTypePage();
        enterProductTypeName();
        selectStatus();
        enterDuplicateProTypeCode();
        clickContainerToggle();
        enterContainerName();
        enterDescriptionForValidation();
        enableToggles();
        clickSaveButton();
        handelSuccessPopup();
        verifyProductTypeName();
        verifyUniqueProductTypeCode();
        verifyProductTypeDescription();
        verifyContainerOption();
        clickSaveButton();
        handelSuccessPopup();
    }

    public void clickSaveButton() {
        click(By.cssSelector("a#btnSave"), "Save button", 20);
    }

    public void verifyUploadedImageIsDisplayed() {
        String value = getAtribute(By.xpath("//table[@id='tblAsset']/tbody/tr/td/img"), "src", 20);
        if (!value.equals("/Content/images/imagenotavailable.jpg")) {
            getTest().log(LogStatus.PASS, "Uploaded Image is displayed as expected when product created with uploading image");
            logger.info("Uploaded Image is displayed as expected when product created with uploading image");
        } else {
            getTest().log(LogStatus.FAIL, "\"Image not Available\" is displayed as expected when product created with uploading image");
            logger.info("\"Image not Available\" is displayed as expected when product created with uploading image");
            takeScreenshot("Upload Image not Available");
        }
    }

    public void verifyImageNotDisplayed() {
        String value = getAtribute(By.xpath("//table[@id='tblAsset']/tbody/tr/td/img"), "src", 20);
        if (value.endsWith("imagenotavailable.jpg")) {
            getTest().log(LogStatus.PASS, "\"Image not Available\" is displayed as expected when product created without uploading image");
            logger.info("\"Image not Available\" is displayed as expected when product created without uploading image");
        } else {
            getTest().log(LogStatus.FAIL, "\"Image Available\" is displayed as expected when product created without uploading image");
            logger.info("\"Image Available\" is displayed as expected when product created without uploading image");
            takeScreenshot("Image not Available");
        }
    }

    public void getProductTypeCode() {
        proTypeCode = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[3]"), 20).trim();
    }

    public void enterDuplicateProTypeCode() {
        enter(By.cssSelector("div>input#AssetTypeCode"), proTypeCode, "product code", 10);
        prodTypeCode = getAtribute(By.cssSelector("div>input#AssetTypeCode"), "value", 30);
    }

    public void verifyProductTypeCode() {
        String actualProTypeCode = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[3]/span"), 20).trim();
        if (actualProTypeCode.equals(productTypeCode)) {
            getTest().log(LogStatus.PASS, "Entered Product Type Code is displayed as expected as " + actualProTypeCode);
            logger.info("Entered Product Type Code is displayed as expected as " + actualProTypeCode);
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product Type Code is not displayed");
            logger.info("Entered Product Type Code is not displayed");
            takeScreenshot("Entered Product Type Code is not displayed");
        }
    }

    public void verifyProductTypeName() {
        scrollToWebelement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[4]/a"), "Product Type Name");
        scrollUpDown("up");
        String actualProTypeCode = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[4]/a"), 20).trim();
        if (actualProTypeCode.equals(productTypeNameRandomValue)) {
            getTest().log(LogStatus.PASS, "Entered Product Type Name is displayed as expected as " + actualProTypeCode);
            logger.info("Entered Product Type Name is displayed as expected as " + actualProTypeCode);
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product Type Code is not displayed");
            logger.info("Entered Product Type Code is not displayed");
            takeScreenshot("Product TypeName");
        }
    }

    public void verifyProductTypeDescription() {
        String actualProTypedes = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[5]/span"), 20).trim();
        if (actualProTypedes.equals(prodTypeDescription)) {
            getTest().log(LogStatus.PASS, "Entered Product Type description is displayed as expected as " + actualProTypedes);
            logger.info("Entered Product Type description is displayed as expected as " + actualProTypedes);
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product description  is not displayed");
            logger.info("Entered Product Type description is not displayed");
            takeScreenshot("Product description");
        }
    }

    public void verifyUniqueProductTypeCode() {
        String actualProTypeCode = getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[3]"), 20).trim();
        if (!actualProTypeCode.equals(prodTypeCode)) {
            getTest().log(LogStatus.PASS, "Unique Product Type Code is displayed as expected as " + actualProTypeCode + " if user enters duplicate product Code as " + prodTypeCode);
            logger.info("Unique Product Type Code is displayed as expected as " + actualProTypeCode + " if user enters duplicate product Code as " + prodTypeCode);
        } else {
            getTest().log(LogStatus.FAIL, "Product type code is duplicated");
            logger.info("Product type code is duplicated");
            takeScreenshot("Product Type Code");
        }
    }

    public String getContainerFieldText() {
        return getText(By.xpath("//table[@id='tblAsset']/tbody/tr/td[6]/span"), 20).trim();

    }

    public void verifyContainerOption() {
        String actual = getContainerFieldText();
        staticWait(500);
        openTheCreatedProductType();
        WebElement element = findElementVisibility(By.xpath("//label[@for='IsContainer']//parent::div//span//span[text()='" + actual + "']"), 20);
        if (element.isDisplayed()) {
            String text = element.getText();
            getTest().log(LogStatus.PASS, "Selected option in Container field is displayed as expected as" + text);
            logger.info("Selected option in Container field is displayed as expected as" + text);
        } else {
            getTest().log(LogStatus.FAIL, "Selected option in Container field is not displayed as expected ");
            logger.info("Selected option in Container field is not displayed as expected ");
            takeScreenshot("Container field toggle");
        }

    }

    public void verifyAddProductTypePage() {
        WebElement ele = findElementVisibility(By.xpath("//div[@class='theme-primary partition-full']//span[@class='p-name text-white']"), 20);
        String actualPageName = ele.getText().trim();
        if (actualPageName.equals("Add Product Type")) {
            getTest().log(LogStatus.PASS, "Add Product Type Page is displayed as expected");
            logger.info("Add Product Type Page is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Add Product Type Page is not displayed");
            logger.info("Add Product Type Page is not displayed");
            takeScreenshot("Add Product Type Page");
        }

    }

    public void clickImportProductType() {
        click(By.cssSelector("a#ancImportItemType"), "Import Product Type", 20);
    }

    public void verifyBreadCrumbOnImportProductType() {
        WebElement ele = findElementVisibility(By.cssSelector("div#SiteMapLink"), 20);
        if (ele.isDisplayed()) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed on import Product type page");
            logger.info("BreadCrumb is displayed on import Product type page");
            clickCompanySetupFromBreadCrumb();
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed on import Product type page");
            logger.info("BreadCrumb is not displayed on import Product type page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void uploadImportProductTypeFile() {
        uploadDoc(By.cssSelector("input#flFile"), filePath + prop.getProperty("testfilexls"), "Upload Import Product Type file", 20);
    }

    public void uploadProductTypeFile() {
        uploadDoc(By.cssSelector("input#flFile"), filePath + prop.getProperty("testfilexlsTwo"), "Upload Import Product Type file", 20);
    }

    public void verifyDocumentIsUploaded() {
        String getDocName = getAtribute(By.cssSelector("input[class='form-control disabled']"), "value", 20);
        if (getDocName.equals("ItemTypeSample.xls")) {
            getTest().log(LogStatus.PASS, "Sample document is uploaded");
            logger.info("Sample document is uploaded");
        } else {
            getTest().log(LogStatus.FAIL, "Sample document is not uploaded");
            logger.info("Sample document is not uploaded");
            takeScreenshot("Sample document");
        }
    }

    public void clickImportFromExcelButton() {
        click(By.cssSelector("a#btnImportFile"), "Import from Excel File ", 20);
    }

    public void clickUserGuide() {
        click(By.cssSelector("a[class='ancuserguide']"), "User guide", 20);
    }

    public void verifyUserGuide() {
        WebElement element = findElementVisibility(By.cssSelector("div[class='user-guide-content']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "User guide content is displayed");
            logger.info("User guide content is displayed");
            clickUserGuide();
        } else {
            getTest().log(LogStatus.FAIL, "User guide content is not displayed");
            logger.info("User guide content is not displayed");
            takeScreenshot("User guide content");
        }
    }

    public void clickSelectAllCheckBox() {
        click(By.xpath("//table[@id='tblAsset']/thead/tr//th[@id='nonresize']//div"), "Select All Check Box", 30);
    }

    public void downloadSampleImportExcelFile() {
        String downloadPath = Drivers.path;
        String fileName = prop.getProperty("testfilexls");
        File dir = new File(downloadPath + fileName);
        if (dir.exists()) {
            dir.delete();
        }
        waitForVisibilityOfElement(By.cssSelector("i[class='fa fa-download action-icon']"), 30);
        File[] dir2 = new File(downloadPath).listFiles();
        filesInDirectory = dir2.length;
        click(By.cssSelector("i[class='fa fa-download action-icon']"), "Download sample excel file", 10);
    }

    public void verifyDownloadedFile() {
        String downloadPath = Drivers.path;
        String fileName = prop.getProperty("testfilexls");
        File dir = new File(downloadPath + fileName);
        File dir2 = new File(downloadPath);
        waitTillNewFile(dir2.toString(), filesInDirectory);
        boolean dirContents = dir.exists();
        if (dirContents) {
            getTest().log(LogStatus.PASS, "Downloaded Sample Excel File is Exist");
            logger.info("Downloaded File is Exist");
        } else {
            getTest().log(LogStatus.FAIL, "Downloaded Sample Excel File is not Exist");
            logger.info("Downloaded Sample Excel File is not Exist");
            takeScreenshot("DownloadedFile");
        }

    }

    public void verifyChooseFileButton() {
        WebElement element = findElementClickable(By.cssSelector("label[for='flFile']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Choose file button is clickable");
            logger.info("Choose file button is clickable");
        } else {
            getTest().log(LogStatus.FAIL, "Choose file button is not clickable");
            logger.info("Choose file button is not clickable");
            takeScreenshot("Choose file button");
        }
    }

    public void clickCancelButton() {
        click(By.cssSelector("div>a.btn-danger"), "Cancel", 20);
        verifyProductTypeListingPage();
    }

    public void clearUploadedFile() {
        waitForVisibilityOfElement(By.cssSelector("a.clsattclear"), 50);
        scrollDown();
        scrollUpDown("up");
        click(By.cssSelector("a.clsattclear"), "Clear", 20);
    }

    public void verifyUploadFiledAfterClearingTheUploadedDoc() {
        String getDocName = getAtribute(By.cssSelector("input[class='form-control disabled']"), "value", 20);
        if (!getDocName.equals("ItemTypeSample.xls")) {
            getTest().log(LogStatus.PASS, "Uploaded document is cleared");
            logger.info("Uploaded document is cleared");
        } else {
            getTest().log(LogStatus.FAIL, "Uploaded document is not cleared");
            logger.info("Uploaded document is not cleared");
            takeScreenshot("Uploaded document is not cleared");
        }
    }

    public void verifyImportFileFieldValidation() {
        String actualMsg = getText(By.xpath("//label[@for='Upload_a_File']//parent::div//span[@class='invalid-feedback']"), 20).trim();
        if (actualMsg.equals(Errors.importexcelfieldValidation)) {
            getTest().log(LogStatus.PASS, "Validation message is displayed in upload file field as expected as " + actualMsg);
            logger.info("Validation message is displayed in upload file field as expected as " + actualMsg);
        } else {
            getTest().log(LogStatus.FAIL, "Validation message is not displayed ");
            logger.info("Validation message is not displayed ");
            takeScreenshot("Validation msg on Upload file field");
        }
    }

    public void uploadInvalidFileFormat() {
        uploadDoc(By.cssSelector("input#flFile"), filePath + prop.getProperty("testfilejpg"), "Upload invalid Import Product Type file", 20);
    }

    public void verifyInvalidImportFileValidation() {
        WebElement element = findElementVisibility(By.xpath("//div[@id='notifymessage']/div/span[text()='Please upload valid excel file.']"), 40);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Error message is displayed as expected when upload file other than excel");
            logger.info("Error message is displayed as expected when upload file other than excel");
            click(By.xpath("//div[@id='notifymessage']/div/button"), "Invalid File Error message", 30);
        } else {
            getTest().log(LogStatus.FAIL, "Error message is not displayed when upload file other than excel");
            logger.info("Error message is not displayed when upload file other than excel");
            takeScreenshot("InvalidFile");
        }
    }

    public void verifyUploadProductListing() {
        try {
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
            FileInputStream file = new FileInputStream(new File(filePath + prop.getProperty("testfilexls")));
            HSSFWorkbook wb = new HSSFWorkbook(file);
            HSSFSheet sheet = wb.getSheetAt(0);
            int expctedRowCount = sheet.getLastRowNum();
            int actualrowCount = findMultipleElement(By.xpath("//table[@id='tblExpList']/tbody//tr"), 20).size();

            if (actualrowCount == expctedRowCount) {
                getTest().log(LogStatus.PASS, "Uploaded Product Type List is displayed as expected");
                logger.info("Uploaded Product Type List is displayed as expected ");
            } else {
                getTest().log(LogStatus.FAIL, "Uploaded Product Type List is not displayed");
                logger.info("Uploaded Product Type List is displayed");
                takeScreenshot("Imported product type list");
            }
        } catch (IllegalStateException | IOException e) {
            getTest().log(LogStatus.FAIL, "Error Occurred " + e);
            logger.info("Error Occurred " + e);
        }
    }

    public void changeProductTypeName() {
        itemNameRandomValue = prop.getProperty("productTypeName") + dateValue;
        enter(By.xpath("//table[@id='tblExpList']/tbody/tr[1]/td//input[@class='form-control req']"), itemNameRandomValue, "Change Product Type Name", 20);
        changePName = getAtribute(By.xpath("//table[@id='tblExpList']/tbody/tr[1]/td//input[@class='form-control req']"), "value", 20);
    }

    public void verifyNameChange() {

        if (changePName.equals(itemNameRandomValue)) {
            getTest().log(LogStatus.PASS, "Changed First Product name is displayed as expected as " + changePName);
            logger.info("Changed First Product name is displayed as expected as " + changePName);
        } else {
            getTest().log(LogStatus.FAIL, "Changed First Product name is not displayed");
            logger.info("Changed First Product name is not displayed ");
            takeScreenshot("ChangedFirstName");
        }
    }

    public void changeProductTypeNameTwo() {
        itemNameRandomValue = prop.getProperty("productTypeName") + "D" + dateValue;
        enter(By.xpath("//table[@id='tblExpList']/tbody/tr[2]/td//input[@class='form-control req']"), itemNameRandomValue, "Change Product Type Name", 20);
        changePNameTwo = getAtribute(By.xpath("//table[@id='tblExpList']/tbody/tr[2]/td//input[@class='form-control req']"), "value", 20);
    }

    public void verifySecondNameChange() {

        if (changePNameTwo.equals(itemNameRandomValue)) {
            getTest().log(LogStatus.PASS, "Changed Second Product name is displayed as expected as " + changePNameTwo);
            logger.info("Changed Second Product name is displayed as expected as " + changePNameTwo);
        } else {
            getTest().log(LogStatus.FAIL, "Changed Second Product name is not displayed");
            logger.info("Changed Second Product name is not displayed ");
            takeScreenshot("ChangedSecondName");
        }
    }

    public void changeProductTypeCode() {
        productCode = dateValue.substring(10, 14);
        enter(By.xpath("//table[@id='tblExpList']/tbody/tr[1]/td[3]//input[@class='form-control']"), productCode, "product code", 10);
        changeProductTypeCode = getAtribute(By.xpath("//table[@id='tblExpList']/tbody/tr[1]/td[3]//input[@class='form-control']"), "value", 20);
    }

    public void verifyCodeChange() {

        if (changeProductTypeCode.equals(productCode)) {
            getTest().log(LogStatus.PASS, "Changed First Product Code is displayed as expected as " + changeProductTypeCode);
            logger.info("Changed First Product Code is displayed as expected as " + changeProductTypeCode);
        } else {
            getTest().log(LogStatus.FAIL, "Changed First Product Code is not displayed");
            logger.info("Changed First Product Code is not displayed ");
            takeScreenshot("ChangedFirstCode");
        }
    }

    public void changeProductTypeCodeTwo() {
        productCode = dateValue.substring(11, 14);
        enter(By.xpath("//table[@id='tblExpList']/tbody/tr[2]/td[3]//input[@class='form-control']"), productCode, "product code", 10);
        changeProductTypeTwoCode = getAtribute(By.xpath("//table[@id='tblExpList']/tbody/tr[2]/td[3]//input[@class='form-control']"), "value", 20);
    }

    public void verifySecondCodeChange() {

        if (changeProductTypeTwoCode.equals(productCode)) {
            getTest().log(LogStatus.PASS, "Changed Second Product Code is displayed as expected as " + changeProductTypeTwoCode);
            logger.info("Changed Second Product Code is displayed as expected as " + changeProductTypeTwoCode);
        } else {
            getTest().log(LogStatus.FAIL, "Changed second Product Code is not displayed");
            logger.info("Changed second Product Code is not displayed ");
            takeScreenshot("SecondChangedCode");
        }
    }

    public void clickSubmit() {
        click(By.cssSelector("a#aSubmit"), "Submit", 20);
    }

    public void verifyImportedProductOnProductListingPage() {
        try {
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
            FileInputStream file = new FileInputStream(new File(filePath + prop.getProperty("testfilexlsTwo")));
            HSSFWorkbook wb = new HSSFWorkbook(file);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.getRow(1);
            HSSFRow row_two = sheet.getRow(2);

            HSSFCell cell_one = row.getCell(0);
            String productTypeName_one = cell_one.getStringCellValue();
            HSSFCell cell_two = row_two.getCell(0);
            String productTypeName_two = cell_two.getStringCellValue();

            List<String> actualName = new ArrayList<>();
            List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[4]"), 20);
            int iteration = 0;
            for (WebElement element : elements) {
                iteration++;
                String actName = element.getText().trim();
                if (actName.equals(productTypeName_one) || actName.equals(productTypeName_two)) {
                    actualName.add("true");
                } else if (iteration > elements.size()) {
                    actualName.add("false");
                }
            }
            if (!actualName.contains("false")) {
                getTest().log(LogStatus.PASS, "Imported Product type is displayed on Product Listing Page");
                logger.info("Imported Product type is displayed on Product Listing Page");
            } else {
                getTest().log(LogStatus.FAIL, "Imported Product type is not displayed on Product Listing Page");
                logger.info("Imported Product type is not displayed on Product Listing Page");
                takeScreenshot("ImportedProductType");

            }

        } catch (IllegalStateException | IOException e) {
            getTest().log(LogStatus.FAIL, "Error Occurred " + e);
            logger.info("Error Occurred " + e);
        }
    }

    public void clickCancel() {
        click(By.cssSelector("a#aCancel"), "Cancel", 20);
    }

    public void verifyImportedProductAfterCancel() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblExpList']//tr"), 20);

        if (element == null) {
            getTest().log(LogStatus.PASS, "Uploaded Product Type List is cleared ");
            logger.info("Uploaded Product Type List is cleared");
        } else {
            getTest().log(LogStatus.FAIL, "Uploaded Product Type List is not cleared");
            logger.info("Uploaded Product Type List is not cleared");
            takeScreenshot("Imported product type list");
        }
    }

    public void verifyPaginationFunctionalities() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[" + recordCount + "]//td[4]//a"), 15).trim();
            click(By.cssSelector("a[class='page-link']"), "Second Page", 20);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageCount = Integer.parseInt(secondPage[1]);
            if (secondPageCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed as expected when click on the \"Next\" pagination button");
                logger.info("Second page is not displayed as expected when click on the \"Next\" pagination button");
                takeScreenshot("Second page Pagination");
            }
            click(By.cssSelector("a[class='page-link']"), "First Page", 20);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] firstPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageCount = Integer.parseInt(firstPage[1]);
            if (firstPageCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("First page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed as expected when click on the \"Next\" pagination button");
                logger.info("Second page is not displayed as expected when click on the \"Next\" pagination button");
                takeScreenshot("First page Pagination");
            }
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[" + recordCount + "]//td[4]//a"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[" + recordCount + "]//td[4]//a"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAsset']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[" + recordCount + "]//td[4]//a"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
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

    public void changeStatusForSingleRecord() {
        int indexNumber;
        List<WebElement> statuses = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[7]//select"), 40);
        for (WebElement status : statuses) {
            Select select = new Select(status);
            WebElement option = select.getFirstSelectedOption();
            statusOfRecord = option.getText();
            try {
                if (statusOfRecord.equals("Inactive")) {
                    select.selectByIndex(0);
                    indexNumber = 0;
                } else {
                    select.selectByIndex(1);
                    indexNumber = 1;
                }
                getTest().log(LogStatus.PASS, "Status is selected with index " + indexNumber);
                logger.info("Status is selected with index " + indexNumber);
            } catch (Exception e) {
                getTest().log(LogStatus.FAIL, "Status not selected. Error occurred - " + e);
                logger.info("Status not selected. Error occurred - " + e);
            }
            break;
        }
    }

    public void changeStatus() {
        Select select = new Select(driver.findElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[7]//select")));
        WebElement option = select.getFirstSelectedOption();
        String status = option.getText();
        if (status.equals("Inactive")) {
            click(By.xpath("//a[contains(@class,'activeicon')]"), "Active button", 20);
        } else {
            click(By.xpath("//a[contains(@class,'inactiveicon')]"), "Inactive button", 20);
        }
    }

    public void statusChangeConfirmationPopup() {
        WebElement statusChangeConfirmation = findElementClickable(By.xpath("//div[text()='Are you sure to update this record?']"), 60);
        if (statusChangeConfirmation != null) {
            click(By.xpath("//button[contains(@class,'btn btn-success')]"), "OK", 20);
            waitForLoader(20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation for status change is not displayed");
            logger.info("Confirmation for status change is not displayed");
            takeScreenshot("ConfirmationPopup");
        }
    }

    public void verifyChangedStatus() {
        waitForLoader(20);
        List<WebElement> resultStatus = findMultipleElement(By.xpath("//table[@id='tblAsset']/tbody/tr/td[7]//select"), 30);
        for (WebElement status : resultStatus) {
            Select select = new Select(status);
            WebElement option = select.getFirstSelectedOption();
            String actualStatus = option.getText();
            if (!actualStatus.equalsIgnoreCase(statusOfRecord)) {
                getTest().log(LogStatus.PASS, "The status for the single record is changed as expected when change from the dropdown along with the record");
                logger.info("The status for the single record is changed as expected when change from the dropdown along with the record");
            } else {
                getTest().log(LogStatus.FAIL, "The status for the single record is not changed, when change from the dropdown along with the record");
                logger.info("The status for the single record is not changed, when change from the dropdown along with the record");
                scrollToWebelement(By.xpath("//table[@id='tblAsset']/tbody/tr[1]/td[7]//select"), "Status Popup");
                takeScreenshot("ChangedStatus");
            }
            break;
        }
    }

    public void openTheCreatedProductType() {
        scrollToWebelement(By.cssSelector("table#tblAsset>thead>tr:nth-child(1)"), "10");
        WebElement element = findElementClickable(By.cssSelector("table#tblAsset>tbody>tr>td:nth-child(3)"), 10);
        element.click();
        scrollUpDown("up");
        click(By.xpath("//table[@id='tblAsset']/tbody/tr/td/a[normalize-space(text())='" + productTypeNameRandomValue + "']"), "Created Product", 20);
    }

    public String headerNameSwitch(String headerName) {
        switch (headerName) {
            case "Product Type\n" + "*":
                return "Item_Type_Name";
            case "Status\n" + "*":
                return "Status";
            case "Product Type Code":
                return "Item_Type_Code";
            case "Description":
                return "Description";
            case "Container":
                return "Is_Container";
            case "Compartment":
                return "Container_List";
            case "Asset":
                return "Is_Asset";
            case "Consumable":
                return "Is_Consumable";
            case "License(s)":
                return "Is_License";
            case "Material/Spare Parts":
                return "Is_Material_Spare_Parts";
            case "Is Transferable":
                return "Is_Transferable";
            case "Self CheckOut":
                return "Is_Self_Checkout";
            case "Penalty On Status":
                return "Is_Penalty";
            case "Status":
                return "Penalty_Type";
            case "Penalty On Return Overdue":
                return "Is_Penalty_On_Overdue";
            case "Grace Period":
                return "Grace_Period";
            case "Enable Terms and Conditions":
                return "Is_TC_Enabled";
            case "Employee Acceptance":
                return "Is_Employee_Acceptance";
            case "Insurance Policy":
                return "Is_Insurance_Policy";
            case "Calibration":
                return "Is_Calibration";
            case "Product Cost":
                return "Is_Cost_Enabled";
            case "Warranty":
                return "Is_Warranty";
            case "Depreciable":
                return "Is_Depreciable";
            case "GPS Tracking":
                return "Is_GPS_Tracking";
            case "Fleet Management":
                return "Is_Fleet_Management";

        }
        return "";
    }

    public void ValidateUploadedExcelFile() {
        scrollDown();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblExpList']/thead//th/span"), 30);
        List<String> headerName = new ArrayList<>();
        for (WebElement element : elements) {
            String ele = element.getText().trim();
            headerName.add(headerNameSwitch(ele));
        }
        LinkedHashMap<String, ArrayList<String>> productDetails = new LinkedHashMap<String, ArrayList<String>>();
        int count = findMultipleElement(By.xpath("//table[@id='tblExpList']/tbody//tr"), 30).size();
        productDetails.put(headerName.get(0), new ArrayList<String>());
        productDetails.put(headerName.get(1), new ArrayList<String>());
        productDetails.put(headerName.get(2), new ArrayList<String>());
        productDetails.put(headerName.get(3), new ArrayList<String>());
        productDetails.put(headerName.get(4), new ArrayList<String>());
        productDetails.put(headerName.get(5), new ArrayList<String>());
        productDetails.put(headerName.get(6), new ArrayList<String>());
        productDetails.put(headerName.get(7), new ArrayList<String>());
        productDetails.put(headerName.get(8), new ArrayList<String>());
        productDetails.put(headerName.get(9), new ArrayList<String>());
        productDetails.put(headerName.get(10), new ArrayList<String>());
        productDetails.put(headerName.get(11), new ArrayList<String>());
        productDetails.put(headerName.get(12), new ArrayList<String>());
        productDetails.put(headerName.get(13), new ArrayList<String>());
        productDetails.put(headerName.get(14), new ArrayList<String>());
        productDetails.put(headerName.get(15), new ArrayList<String>());
        productDetails.put(headerName.get(16), new ArrayList<String>());
        productDetails.put(headerName.get(17), new ArrayList<String>());
        productDetails.put(headerName.get(18), new ArrayList<String>());
        productDetails.put(headerName.get(19), new ArrayList<String>());
        productDetails.put(headerName.get(20), new ArrayList<String>());
        productDetails.put(headerName.get(21), new ArrayList<String>());
        productDetails.put(headerName.get(22), new ArrayList<String>());
        productDetails.put(headerName.get(23), new ArrayList<String>());
        productDetails.put(headerName.get(24), new ArrayList<String>());
        for (int i = 1; i <= count; i++) {

            String prodName = getAtribute(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[1]/input"), "value", 20);
            productDetails.get(headerName.get(0)).add(prodName);
            String prodStatus = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[2]//select/option[@selected='selected']"), 20);
            productDetails.get(headerName.get(1)).add(prodStatus);
            String prodCode = getAtribute(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[3]/input"), "value", 20);
            productDetails.get(headerName.get(2)).add(prodCode);
            String prodDes = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[4]/textarea"), 20).trim();
            productDetails.get(headerName.get(3)).add(prodDes);
            String prodContain = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[5]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(4)).add(prodContain);
            String prodCompartment = getAtribute(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[6]//input"), "value", 20);
            productDetails.get(headerName.get(5)).add(prodCompartment);
            String prodAsset = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[7]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(6)).add(prodAsset);
            String prodConsumable = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[8]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(7)).add(prodConsumable);
            String prodLicense = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[9]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(8)).add(prodLicense);
            String prodSpareParts = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[10]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(9)).add(prodSpareParts);
            String prodTransfer = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[11]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(10)).add(prodTransfer);
            String prodCheckOut = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[12]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(11)).add(prodCheckOut);
            String prodPenStatus = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[13]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(12)).add(prodPenStatus);
            scrollToWebelement(By.xpath("//table[@id='tblExpList']//tr[1]//td[14]/span[@class='multiselect-native-select']/div/button"), "Status");
            String prodPOSV = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[14]/span[@class='multiselect-native-select']/div/button/span"), 20);
            if (prodPOSV.equals("Select")) {
                prodPOSV = "";
            }
            productDetails.get(headerName.get(13)).add(prodPOSV);
            String prodReturn = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[15]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(14)).add(prodReturn);
            String prodGracePeriod = getAtribute(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[16]/input"), "value", 20);
            productDetails.get(headerName.get(15)).add(prodGracePeriod);
            String prodTerms = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[17]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(16)).add(prodTerms);
            String prodEmpAcc = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[18]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(17)).add(prodEmpAcc);
            String prodInsPol = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[19]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(18)).add(prodInsPol);
            String prodCal = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[20]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(19)).add(prodCal);
            String prodCost = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[21]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(20)).add(prodCost);
            String prodWarranty = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[22]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(21)).add(prodWarranty);
            String prodDepreciable = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[23]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(22)).add(prodDepreciable);
            String prodGPS = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[24]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(23)).add(prodGPS);
            String prodFleetManage = getText(By.xpath("//table[@id='tblExpList']//tr[" + i + "]//td[25]//input[@checked='checked']//parent::div//parent::div[contains(@class,'float-left')]"), 20).trim();
            productDetails.get(headerName.get(24)).add(prodFleetManage);
        }
        ReadExcel r = new ReadExcel();
        Map<String, ArrayList<String>> compare = r.getValueInDiffColumn(headerName);
        for (Map.Entry<String, ArrayList<String>> expected : compare.entrySet()) {
            for (Map.Entry<String, ArrayList<String>> actual : productDetails.entrySet()) {
                if (expected.getKey().equals(actual.getKey())) {
                    if (expected.getValue().equals(actual.getValue())) {
                        getTest().log(LogStatus.PASS, "Uploaded Product Details displayed as expected for the header " + actual.getKey());
                        logger.info("Uploaded Product Details displayed as expected for the header " + actual.getKey());
                        break;
                    } else {
                        getTest().log(LogStatus.FAIL, "Uploaded Product details is not displayed as correctly.For the Header" + expected.getKey() + ". And expected value is " + expected.getValue() + " But the actual Value is " + actual.getValue());
                        logger.info("Uploaded Product details is not displayed as correctly.For the Header" + expected.getKey() + ". And expected value is " + expected.getValue() + " But the actual Value is " + actual.getValue());
                        break;
                    }
                }
            }
        }
    }
}





