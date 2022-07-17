package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class AddProductTypePage extends WebBasePage {
    WebDriver driver;
    AddProductPage addProductPage;
    MyProductPage myproduct;
    ProductTypePage productType;
    boolean termsAndCondition;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    String itemNameRandomValue;
    String productTypeCode;
    String selectedBarCodeType;
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";

    public AddProductTypePage(WebDriver driver) {
        super(driver, "Add product type Page");
        this.driver = driver;
        this.addProductPage = new AddProductPage(driver);
        this.myproduct = new MyProductPage(driver);
        this.productType = new ProductTypePage(driver);

    }

    //Click the Full menu drop down
    public void clickFullMenuDropDown() {
        addProductPage.clickFullMenuDropDown();
    }

    //Verify Asset management menu is present in the full menu drop down  and click the menu
    public void clickAssetManagement() {
        addProductPage.clickAssetManagement();
    }

    public void verifyNotAcceptedProductInTransit() {
        InTransitPage inTransitPage = new InTransitPage(driver);
        clickFullMenuDropDown();
        clickAssetManagement();
        addProductPage.clickManageProduct();
        inTransitPage.clickInTransitLink();
        inTransitPage.verifyNotAcceptedProduct();
    }


    //Verify Product type menu is display and click the product type
    public void clickProductType() {
        WebElement productType = findElementVisibility(By.xpath("//div[@id='scrollbar']//a[text()='Product Type' and contains(@href,'/Asset/')]"), 15);
        if (productType != null) {
            click(By.xpath("//div[@id='scrollbar']//a[text()='Product Type' and contains(@href,'/Asset/')]"), "Product Type", 10);
        } else {
            clickAssetManagement();
            clickProductType();
        }
    }

    //Verify the Product type listing page is display or not
    public void productTypeListingPage() {
        WebElement productTypePage = findElementVisibility(By.xpath("//span[text()='Product Type']//following::div[@id='asset']"), 10);
        if (productTypePage != null) {
            getTest().log(LogStatus.PASS, "Product type listing is displayed when click on the Product type menu");
            logger.info("Product type listing is displayed when click on the Product type menu");
        } else {
            getTest().log(LogStatus.FAIL, "Product type listing is not displayed when click on the Product type menu");
            logger.info("Product type listing is not displayed when click on the Product type menu");
            takeScreenshot("ProductTypePage");
        }
    }

    //Click the Add button in Product type page
    public void clickProductTypeAddButton() {
        scrollUpDown("up");
        click(By.xpath("//i[@class='fa fa-plus']"), "Add button", 15);
    }

    //Verify the product type text field is clickable
    public void verifyTextFieldClickable() {
        WebElement productTypeTextField = findElementClickable(By.id("AssetType"), 15);
        if (productTypeTextField != null) {
            getTest().log(LogStatus.PASS, "The Product Type Name field is clickable");
            logger.info("The Product Type Name field is clickable");
            click(By.id("AssetType"), "Product Type Name text field", 10);
        } else {
            getTest().log(LogStatus.FAIL, "The Product Type Name field is not clickable");
            logger.info("The Product Type Name field is not clickable");
        }
    }

    //Enter Maximum 200 character in product type field
    public void enterMyProductTypeField() {
        enter(By.id("AssetType"), prop.getProperty("productName201Charc"), "My Product Type", 10);
    }

    public void verifyProductTypeNameField(String validationType) {
        String expectedString;
        if (validationType.equalsIgnoreCase("AlphaNumeric")) {
            expectedString = prop.getProperty("productNameAlphaNumeric");
        } else {
            expectedString = prop.getProperty("productName200Charc");
        }
        String actualText = getAtribute(By.xpath("//input[@id='AssetType']"), "value", 20);
        if (actualText.equalsIgnoreCase(expectedString)) {
            getTest().log(LogStatus.PASS, "The Product Type Name field accepts only 200 character as expected");
            logger.info("The Product Type Name field accepts only 200 character as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Product Type Name field accepts more than 200 character");
            logger.info("The Product Type Name field accepts more than 200 character");
            takeScreenshot("ProductTypeName");
        }
    }

    //Enter Alpha numeric character in product type field
    public void enterAlphaNumericProductType() {
        enter(By.id("AssetType"), prop.getProperty("productNameAlphaNumeric"), "Alpha numeric character in product type", 15);
    }

    //Enter product type name
    public void enterProductTypeName() {
        char random = "ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray()[randNum.nextInt("ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray().length)];
        itemNameRandomValue = prop.getProperty("productTypeName") + random + dateValue;
        enter(By.id("AssetType"), itemNameRandomValue, "Product type", 10);
    }

    public void verifyCreatedProductType() {
        waitForLoader(60);
        scrollUpDown("up");
        WebElement productType = findElementVisibility(By.xpath("//table[@id='tblAsset']//tbody//tr//td//a[normalize-space(text())='" + itemNameRandomValue + "']"), 20);
        if (productType != null) {
            getTest().log(LogStatus.PASS, "The product type is created when we enable any one of toggle under asset category");
            logger.info("The product type is created when we enable any one of toggle under asset category");
        } else {
            getTest().log(LogStatus.FAIL, "The product type is not created when we enable any one of toggle under asset category");
            logger.info("The product type is not created when we enable any one of toggle under asset category");
            takeScreenshot("ProductTypeCreateNotCreate");
        }
    }

    public void verifyNotCreatedProductType() {
        WebElement assetTypeError = findElementVisibility(By.xpath("//div[text()='Atleast one Product category must be selected']"), 20);
        if (assetTypeError != null) {
            getTest().log(LogStatus.PASS, "The product type is not created when we not enable any one of toggle under asset category");
            logger.info("The product type is not created when we not enable any one of toggle under asset category");
        } else {
            getTest().log(LogStatus.FAIL, "The product type is created even if we not enable any one of toggle under asset category");
            logger.info("The product type is created even if we not enable any one of toggle under asset category");
            takeScreenshot("ProductTypeCreateNotCreate");
        }
    }

    //Verify Status field  dropdown
    public void verifyStatusDropdown() {
        List<WebElement> StatusElement;
        String actualStatus;
        int iteration = 0;
        String[] expectedStatus = {"Active", "Inactive"};
        StatusElement = findMultipleElement(By.xpath("//select[@id='StatusId']/option"), 30);
        for (String expectedProductStatus : expectedStatus) {
            for (WebElement ele : StatusElement) {
                iteration++;
                actualStatus = ele.getText().trim();
                if (expectedProductStatus.equals(actualStatus)) {
                    getTest().log(LogStatus.PASS, "The expected status \"" + actualStatus + "\" is displayed in the Product status dropdown");
                    logger.info("The expected status \"" + actualStatus + "\" is displayed in the Product status dropdown");
                    iteration = 0;
                    break;
                } else if (iteration == StatusElement.size()) {
                    getTest().log(LogStatus.FAIL, "The expected status \"" + actualStatus + "\" is not displayed as expected");
                    logger.info("The expectedstatus \"" + actualStatus + "\" is not displayed as expected");
                    takeScreenshot("StatusHeader");
                }
            }

        }

    }

    //Select Product type status--Active and In active
    public void selectProductTypeStatus(String status) {
        selectValueWithText(By.id("StatusId"), status, "Status", 10);
    }

    //Select product categories
    public void selectProductType() {
        click(By.xpath("//input[@id='IsAssetN']/../span"), "Yes Asset Toggle", 10);
    }

    //Click save button
    public void clickSaveButton() {
        click(By.id("btnSave"), "Save", 10);
    }

    public void handleSuccessPopup() {
        WebElement element = findElementVisibility(By.xpath("//div[contains(@class,'alert-success')]"), 40);
        if (element != null) {
            String successMsg = getText(By.xpath("//div[contains(@class,'alert-success')]/span"), 30);
            getTest().log(LogStatus.PASS, "Success Message is displayed as " + successMsg);
            logger.info("Success Message is displayed as " + successMsg);
            click(By.xpath("//div[contains(@class,'alert-success')]//button"), "Close Success Message", 30);
        } else {
            getTest().log(LogStatus.FAIL, "Success Message is not displayed");
            logger.info("Success Message is not displayed");
            takeScreenshot("SuccessPopup");
        }
    }

    //verify the status of product when save the Product details
    public void verifyProductListingStatus(String expectedStatus) {
        scrollUpDown("up");
        String actualStatus = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[1]/td[7]//select/option[@selected]"), 20);
        if (actualStatus.equals(expectedStatus)) {
            getTest().log(LogStatus.PASS, "The expected status \"" + actualStatus + "\" is displayed when product type is created with " + actualStatus + " status");
            logger.info("The expected status \"" + actualStatus + "\" is displayed when product type is created with " + actualStatus + " status");

        } else {
            getTest().log(LogStatus.FAIL, "The expected status \"" + actualStatus + "\" is not displayed when product type is created with " + actualStatus + " status");
            logger.info("The expected status \"" + actualStatus + "\" is not displayed when product type is created with " + actualStatus + " status");
            takeScreenshot("StatusHeader");
        }
    }

    //Get the product type code
    public void getProductType() {
        productTypeCode = getAtribute(By.id("AssetTypeCode"), "value", 10);
    }

    //verify the product type code is generated
    public void verifyProductTypeCode() {
        String expectedText = getText(By.xpath("//table[@id='tblAsset']//tbody/tr[1]/td/a[contains(text(),'" + itemNameRandomValue + "')]/../../td[3]/span"), 10);
        if (!expectedText.equals(productTypeCode)) {
            getTest().log(LogStatus.PASS, "Automatic product type code '" + expectedText + "' is generated");
            logger.info("Automatic product type code '" + expectedText + "' is generated");
        } else {
            getTest().log(LogStatus.FAIL, "Automatic product type code '" + expectedText + "' is not generated");
            logger.info("Automatic product type code '" + expectedText + "' is not generated");
            takeScreenshot("ProductTypeCode");
        }
    }

    //Verify user should not be able to enter the more than 4 characters
    public void enterProductTypeCode() {
        String code;
        enter(By.id("AssetTypeCode"), prop.getProperty("productTypeCode5Charc"), "ProductTypeCode", 10);
        code = getAtribute(By.id("AssetTypeCode"), "maxlength", 10);
        if (code != "5") {
            getTest().log(LogStatus.PASS, "Entered more than 4 characters but it allowed only 4 characters");
            logger.info("Entered more than 4 characters but it allowed only 4 characters");
        } else {
            getTest().log(LogStatus.PASS, "Entered more than 4 characters but it allowed more than 4 characters");
            logger.info("Entered more than 4 characters but it allowed more than 4 characters");
        }

    }

    //Verify upload only one image in the product image field
    public void uploadImage() {
//        uploadDoc(By.cssSelector("input#flFile"), filePath + prop.getProperty("testfilejpg"), "uploaded document", 10);
        uploadDoc(By.cssSelector("input#flProductTypeFile"), filePath + prop.getProperty("testfilejpg"), "uploaded document", 10);
        //Verify upload image
        if (findElementsVisibility(By.xpath("//img[contains(@src,'imagenotavailable.jpg')]")) == null) {
            getTest().log(LogStatus.PASS, "User should be allowed one image in the field");
            logger.info("User should be allowed one image in the field");
        } else {
            getTest().log(LogStatus.FAIL, "User should not be allowed one image in the field");
            logger.info("User should not be allowed one image in the field");
        }
    }

    //Verify not allowed to enter more than 250 characters
    public void enterMoreCharProductDescription() {
        enter(By.id("Description"), prop.getProperty("description251Character"), "Enter more than 251 characters in Description", 10);
        clickSaveButton();
        if (findElementsVisibility(By.xpath("//span[@for='Description']")) != null) {
            getTest().log(LogStatus.PASS, "Validation message is displayed as expected when enter more than 250 characters");
            logger.info("Validation message is displayed as expected when enter more than 250 characters");
        } else {
            getTest().log(LogStatus.FAIL, "Validation message is not displayed when enter more than 250 characters");
            logger.info("Validation message is not displayed when enter more than 250 characters");
            enter(By.id("Description"), prop.getProperty("description250Character"), "Enter more than 250 characters in Description", 10);

        }
    }

    // Verify container field is display when toggle yes
    public void verifyContainerYesField() {
        WebElement field;
        scrollUpDown("up");
        click(By.xpath("//input[@id='IsContainerN']/../span"), "Toggle yes", 10);
        field = findElementVisibility(By.xpath("//span[text()='Compartments']"), 10);
        if (field != null) {

            getTest().log(LogStatus.PASS, "Container field is displayed when Toggle the Yes button");
            logger.info("Container field is displayed when Toggle the Yes button");
        } else {
            getTest().log(LogStatus.FAIL, "Container field is not displayed when Toggle the Yes button");
            logger.info("Container field is not displayed when Toggle the Yes button");
            takeScreenshot("ContainerField");
        }
    }

    // Verify container field is display when toggle No
    public void verifyContainerNoField() {
        WebElement field;
        click(By.xpath("//input[@id='IsContainerN']/../span"), "Toggle no", 10);
        field = findElementVisibility(By.xpath("//span[text()='Compartments']"), 10);
        if (field == null) {

            getTest().log(LogStatus.PASS, "Container field is not displayed when Toggle the No button");
            logger.info("Container field is not displayed when Toggle the No button");
        } else {
            getTest().log(LogStatus.FAIL, "Container field is displayed when Toggle the No button");
            logger.info("Container field is displayed when Toggle the No button");
            takeScreenshot("ContainerField");
        }
    }

    public void switchToYesToggle() {
        click(By.xpath("//input[@id='IsContainerN']/../span"), "Toggle yes", 10);
    }

    public void verifyMinimumOneRowForContainer() {
        int actualCount;
        List<WebElement> containerCount = findMultipleElement(By.xpath("//table[@id='tblContainerList']/tbody/tr"), 10);
        actualCount = containerCount.size();
        if (actualCount == 1) {
            getTest().log(LogStatus.PASS, "Minimum 1 row of compartment field is required while selecting 'Yes' toggle button of container field ");
            logger.info("Minimum 1 row of compartment field is required while selecting 'Yes' toggle button of container field");
        } else {
            getTest().log(LogStatus.FAIL, "Minimum 1 row of compartment field is not required while selecting 'Yes' toggle button of container field");
            logger.info("Minimum 1 row of compartment field is not required while selecting 'Yes' toggle button of container field");
        }
    }

    //Verify validation message is displayed when without enter container field and save the product type
    public void verifyValidationMessage(String expectedValidationMessage) {
        String actualValidationMessage;
        actualValidationMessage = getText(By.xpath("//span[@class='invalid-feedback']"), 10);
        if (expectedValidationMessage.equals(actualValidationMessage)) {
            getTest().log(LogStatus.PASS, "Validation message is displayed when not entering the compartment field");
            logger.info("Validation message is displayed when not entering the compartment field");
        } else {
            getTest().log(LogStatus.FAIL, "Validation message is not displayed when not entering the compartment field");
            logger.info("Validation message is not displayed when not entering the compartment field");
        }

    }

    //Verify container field + button
    public void clickPlusButton() {
        staticWait(200);
        scrollUpDown("Up");
        click(By.xpath("//tr[@id='datarow']/td/a/i[@class='fa fa-plus']"), "+ button", 10);
    }

    //Verify The new row of compartments field should be added when we click on '+' button
    public void verifyNewRowAdded() {
        int actualCount;
        List<WebElement> containerCount = driver.findElements(By.xpath("//table[@id='tblContainerList']/tbody/tr"));
        actualCount = containerCount.size();
        if (actualCount == 2) {
            getTest().log(LogStatus.PASS, "New row is added when click Plus button");
            logger.info("New row is added when click Plus button");
        } else {
            getTest().log(LogStatus.FAIL, "New row is not added when click Plus button");
            logger.info("New row is added when click Plus button");
        }
    }

    //Click delete button
    public void clickDelete(String count) {
        click(By.xpath("//tr[" + count + "]//td//i[@class='recyclebin fa fa-trash']"), "Delete", 10);
    }

    //Verify the row of compartment field should be deleted while clicking on delete button
    public void verifyDeletedRow() {
        int actualCount;
        List<WebElement> containerCount = driver.findElements(By.xpath("//table[@id='tblContainerList']/tbody/tr"));
        actualCount = containerCount.size();
        if (actualCount == 1) {
            getTest().log(LogStatus.PASS, "Row of compartment field should be deleted while clicking on delete button");
            logger.info("Row of compartment field should be deleted while clicking on delete buttnn");
        } else {
            getTest().log(LogStatus.FAIL, "Row of compartment field should not be deleted while clicking on delete button");
            logger.info("Row of compartment field should not be deleted while clicking on delete button");
        }
    }

    //Verify No toggle button of container field should be selected when we delete all the rows of compartment field
    public void verifyToggleButton() {
        if (findElementsVisibility(By.xpath("//input[@id='IsContainerN']/..//span[text()='No']")) != null) {
            getTest().log(LogStatus.PASS, "No toggle button of container field should be displayed when we delete all the rows of compartment field");
            logger.info("No toggle button of container field should be displayed when we delete all the rows of compartment field");
        } else {
            getTest().log(LogStatus.FAIL, "No toggle button of container field should not be displayed when we delete all the rows of compartment field");
            logger.info("No toggle button of container field should not be displayed when we delete all the rows of compartment field");
        }
    }

    //Verify Yes and No toggle button for Asset is clickable
    public void verifyYesNoToggleAsset() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsAssetN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Asset No toggle button is clickable");
            logger.info("Asset No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsAssetN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Asset Yes toggle button is clickable");
                logger.info("Asset Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Yes toggle button is not clickable");
                logger.info("Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "No toggle button is clickable");
            logger.info("No toggle button is clickable");
        }
    }

    //Select yes toggle button for Asset product category
    public void selectAssetYesToggle() {
        click(By.xpath("//input[@id='IsAssetN']/..//span[text()='No']"), "Select asset Yes", 10);
    }

    //Select no toggle button for Asset product category
    public void selectAssetNoToggle() {
        click(By.xpath("//input[@id='IsAssetN']/..//span[text()='Yes']"), "Select asset No", 10);
    }

    //Verify Yes and No toggle button for Licence is clickable
    public void verifyYesNoToggleLicence() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsLicenseN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "License No toggle button is clickable");
            logger.info("License No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsLicenseN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "License Yes toggle button is clickable");
                logger.info("License Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "License Yes toggle button is not clickable");
                logger.info("License Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "License No toggle button is clickable");
            logger.info("License No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for Consume is clickable
    public void verifyYesNoToggleConsume() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsConsumableN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Consume No toggle button is clickable");
            logger.info("Consume No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsConsumableN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Consume Yes toggle button is clickable");
                logger.info("Consume Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Consume Yes toggle button is not clickable");
                logger.info("Consume Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Consume No toggle button is clickable");
            logger.info("Consume No toggle button is clickable");
        }
    }

    //Select yes toggle button for Consume product category
    public void selectYesToggleConsume() {
        click(By.xpath("//input[@id='IsConsumableN']/..//span[text()='No']"), "Select Consume Yes", 10);
    }

    //Verify Yes and No toggle button for Spare/Tool is clickable
    public void verifyYesNoToggleSpareTool() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsSpareN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Spare Tool No toggle button is clickable");
            logger.info("Spare Tool No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsSpareN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Spare Tool Yes toggle button is clickable");
                logger.info("Spare Tool Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Spare Tool Yes toggle button is not clickable");
                logger.info("Spare Tool Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Spare Tool No toggle button is clickable");
            logger.info("Spare Tool No toggle button is clickable");
        }
    }

    //Select yes toggle button for employee acceptance
    public void selectYesToggleEmployee() {
        click(By.xpath("//input[@id='IsEmployeeAcceptanceN']/..//span[text()='No']"), "Employe Select Yes", 10);
    }

    //Verify Yes and No toggle button for Insurance Policy is clickable
    public void verifyYesNoToggleInsurancePolicy() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='InsurancePolicyN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Insurance Policy No toggle button is clickable");
            logger.info("Insurance Policy No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='InsurancePolicyN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Insurance Policy Yes toggle button is clickable");
                logger.info("Insurance Policy Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Insurance Policy Yes toggle button is not clickable");
                logger.info("Insurance Policy Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Insurance Policy No toggle button is clickable");
            logger.info("Insurance Policy No toggle button is clickable");
        }
    }

    //Select yes toggle button for Insurance policy
    public void selectYesToggleInsurancePolicy() {
        click(By.xpath("//input[@id='IsEmployeeAcceptanceN']/..//span[text()='No']"), "Select Insurance Policy Yes", 10);
    }

    //Verify Yes and No toggle button for Transfer field is clickable
    public void verifyYesNoToggleTransferField() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsTransferableN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Transfer field No toggle button is clickable");
            logger.info("Transfer field No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsTransferableN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Transfer field Yes toggle button is clickable");
                logger.info("Transfer field Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Transfer field Yes toggle button is not clickable");
                logger.info("Transfer field Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Transfer field No toggle button is clickable");
            logger.info("Transfer field No toggle button is clickable");
        }
    }

    //Select yes toggle button for transfer field
    public void selectYesToggleTransfer() {
        click(By.xpath("//input[@id='IsTransferableN']/..//span[text()='No']"), "Select transfer filed Yes", 10);
    }

    //Verify Yes and No toggle button for Self Check Out is clickable
    public void verifyYesNoToggleSelfCheckOut() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsSelfCheckOutN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Self CheckOut No toggle button is clickable");
            logger.info("Self CheckOut No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsSelfCheckOutN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Self CheckOut Yes toggle button is clickable");
                logger.info("Self CheckOut Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Self CheckOut Yes toggle button is not clickable");
                logger.info("Self CheckOut Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Self CheckOut No toggle button is clickable");
            logger.info("Self CheckOut No toggle button is clickable");
        }
    }

    //Select yes toggle button for SelfCheckout
    public void selectYesSelfCheckout() {
        click(By.xpath("//input[@id='IsTransferableN']/..//span[text()='No']"), "Select self checkout Yes", 10);
    }

    //Verify Yes and No toggle button for Terms and conditions is clickable
    public void verifyYesNoTermsAndConditions() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsEnableTermsN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Terms and conditions No toggle button is clickable");
            logger.info("Terms and conditions No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsEnableTermsN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Terms and conditions Yes toggle button is clickable");
                logger.info("Terms and conditions Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Terms and conditions Yes toggle button is not clickable");
                logger.info("Terms and conditions Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Terms and conditions No toggle button is clickable");
            logger.info("Terms and conditions No toggle button is clickable");
        }
    }

    //Select yes toggle button for terms and condition
    public void selectYesToggleTermsAndCondition() {
        click(By.xpath("//input[@id='IsEnableTermsN']/..//span[text()='No']"), "Select Terms and conditions  Yes", 10);
    }

    //Verify Yes and No toggle button for product cost is clickable
    public void verifyYesNoProductCost() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsItemCostN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "product cost No toggle button is clickable");
            logger.info("product cost No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsItemCostN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "product cost Yes toggle button is clickable");
                logger.info("product cost Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "product cost Yes toggle button is not clickable");
                logger.info("product cost Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "product cost No toggle button is clickable");
            logger.info("product cost No toggle button is clickable");
        }
    }

    //Select yes toggle button for Product cost
    public void selectYesToggleProductCost() {
        click(By.xpath("//input[@id='IsItemCostN']/..//span[text()='No']"), "Select product cost Yes", 10);
    }

    //Verify Yes and No toggle button for depreciable is clickable
    public void verifyYesNoDepreciable() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsDepreciableN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Depreciable No toggle button is clickable");
            logger.info("Depreciable No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsDepreciableN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Depreciable Yes toggle button is clickable");
                logger.info("Depreciable Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Depreciable Yes toggle button is not clickable");
                logger.info("Depreciable Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Depreciable No toggle button is clickable");
            logger.info("Depreciable No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for calibration is clickable
    public void verifyYesNoCalibration() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsCalibrationN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Calibration No toggle button is clickable");
            logger.info("Calibration No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsCalibrationN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Calibration Yes toggle button is clickable");
                logger.info("Calibration Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Calibration Yes toggle button is not clickable");
                logger.info("Calibration Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Calibration No toggle button is clickable");
            logger.info("Calibration No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for time and attendance is clickable
    public void verifyYesNoTimeAndAttendance() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsTimeAttendanceN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Time and attendance No toggle button is clickable");
            logger.info("Time and attendance No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsTimeAttendanceN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Time and attendance Yes toggle button is clickable");
                logger.info("Time and attendance Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Time and attendance Yes toggle button is not clickable");
                logger.info("Time and attendance Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Time and attendance No toggle button is clickable");
            logger.info("Time and attendance No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for Camera is clickable
    public void verifyYesNoCamera() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsCameraN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Camera No toggle button is clickable");
            logger.info("Camera No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsCameraN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Camera Yes toggle button is clickable");
                logger.info("Camera Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Camera Yes toggle button is not clickable");
                logger.info("Camera Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Camera No toggle button is clickable");
            logger.info("Camera No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for RFID is clickable
    public void verifyYesNoRFID() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsRFIDN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "RFID No toggle button is clickable");
            logger.info("RFID No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsRFIDN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "RFID Yes toggle button is clickable");
                logger.info("RFID Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "RFID Yes toggle button is not clickable");
                logger.info("RFID Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "RFID No toggle button is clickable");
            logger.info("RFID No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for GPS Tracking is clickable
    public void verifyYesNoGPSTracking() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsGPSN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "GPS Tracking No toggle button is clickable");
            logger.info("GPS Tracking No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsGPSN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "GPS Tracking Yes toggle button is clickable");
                logger.info("GPS Tracking Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "GPS Tracking Yes toggle button is not clickable");
                logger.info("GPS Tracking Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "GPS Tracking No toggle button is clickable");
            logger.info("GPS Tracking No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for GPS enable is clickable
    public void verifyYesNoGPSEnable() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsGPSTrackingN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "GPS enable No toggle button is clickable");
            logger.info("GPS enable No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsGPSTrackingN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "GPS enable Yes toggle button is clickable");
                logger.info("GPS enable Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "GPS enable Yes toggle button is not clickable");
                logger.info("GPS enable Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "GPS enable No toggle button is clickable");
            logger.info("GPS enable No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for fleet management is clickable
    public void verifyYesNoFleetManagement() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsFleetManagementN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Fleet management No toggle button is clickable");
            logger.info("Fleet management No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsFleetManagementN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Fleet management Yes toggle button is clickable");
                logger.info("Fleet management Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Fleet management Yes toggle button is not clickable");
                logger.info("Fleet management Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Fleet management No toggle button is clickable");
            logger.info("Fleet management No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for Penalty on status is clickable
    public void verifyYesNoPenaltyOnStatus() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsPenaltyN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Penalty on status No toggle button is clickable");
            logger.info("Penalty on status No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsPenaltyN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Penalty on status Yes toggle button is clickable");
                logger.info("Penalty on status Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Penalty on status Yes toggle button is not clickable");
                logger.info("Penalty on status Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "Penalty on status No toggle button is clickable");
            logger.info("Penalty on status No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for warranty is clickable
    public void verifyYesNoWarranty() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsWarrantyN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "Warranty No toggle button is clickable");
            logger.info("Warranty No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsWarrantyN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Warranty Yes toggle button is clickable");
                logger.info("Warranty Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Warranty Yes toggle button is not clickable");
                logger.info("Warranty Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "No toggle button is clickable");
            logger.info("No toggle button is clickable");
        }
    }

    //Verify Yes and No toggle button for warranty is clickable
    public void verifyYesNoPenaltyOnOverdue() {
        WebElement toggleNo;
        WebElement toggleYes;
        toggleNo = findElementClickable(By.xpath("//input[@id='IsPenaltyOnReturnOverdueN']/..//span[text()='No']"), 10);
        if (toggleNo.isEnabled()) {
            toggleNo.click();
            getTest().log(LogStatus.PASS, "No toggle button is clickable");
            logger.info("No toggle button is clickable");
            toggleYes = findElementClickable(By.xpath("//input[@id='IsPenaltyOnReturnOverdueN']/..//span[text()='Yes']"), 10);
            if (toggleYes.isEnabled()) {
                toggleYes.click();
                getTest().log(LogStatus.PASS, "Yes toggle button is clickable");
                logger.info("Yes toggle button is clickable");
            } else {
                getTest().log(LogStatus.PASS, "Yes toggle button is not clickable");
                logger.info("Yes toggle button is not clickable");
            }

        } else {
            getTest().log(LogStatus.PASS, "No toggle button is clickable");
            logger.info("No toggle button is clickable");
        }
    }

    public void getProductTypeCode() {
        productTypeCode = getText(By.xpath("//table[@id='tblAsset']//tbody/tr[2]/td[3]/span"), 10);

    }

    public void enterDuplicateProductTypeCode() {
        enter(By.id("AssetTypeCode"), productTypeCode, "ProductTypeCode", 10);

    }

    public void verifyProductTypeIsDiffer() {
        String actualProductTypeCode = getText(By.xpath("//table[@id='tblAsset']//td[3]/span"), 10);
        if (!actualProductTypeCode.equals(productTypeCode)) {
            getTest().log(LogStatus.PASS, "Product type code is increased in the listing screen when enter existing code");
            logger.info("Product type code is increased in the listing screen when enter existing code");
        } else {
            getTest().log(LogStatus.PASS, "Duplicate product type code is accepted");
            logger.info("Duplicate product type code is accepted");
        }
    }

    public void verifyConsumeDisable() {
        String consumeOptionClass = getAtribute(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[8]//a[@data-original-title='Consume']"), "class", 10);
        if (consumeOptionClass.contains("disabled")) {
            getTest().log(LogStatus.PASS, "Consume is disable when click Toggle is no in product category ");
            logger.info("Consume is disable when click Toggle is no in product category ");
        } else {
            getTest().log(LogStatus.FAIL, "Consume is not disable when click Toggle is no in product category ");
            logger.info("Consume is not disable when click Toggle is no in product category ");
            takeScreenshot("DisabledConsume");
        }
    }

    public void verifyAcceptButton() {
        WebElement accept = findElementVisibility((By.xpath("//a/span[text()='Accept']")), 10);
        if (accept != null) {
            getTest().log(LogStatus.PASS, "Accept is visible when click Toggle is Yes in Employee Acceptance ");
            logger.info("Accept is visible when click Toggle is Yes in Employee Acceptance ");
        } else {
            getTest().log(LogStatus.FAIL, "Accept is not visible when click Toggle is Yes in Employee Acceptance  ");
            logger.info("Accept is not visible when click Toggle is Yes in Employee Acceptance  ");
        }
    }

    public void createProductTypeForDisableConsumable(String toggleName) {
        productType.clickAddButton();
        productType.enterProductTypeName();
        productTypeName = getAtribute(By.cssSelector("div>input#AssetType"), "value", 20);
        productType.selectStatus();
        productType.enterProductCode();
        if (toggleName.equalsIgnoreCase("container")) {
            productType.clickContainerToggle();
            productType.enterContainerName();
        }
        if (toggleName.equalsIgnoreCase("consumable")) {
            productType.enableConsumableToggle();
        } else {

            productType.enableAssetToggle();
        }
        productType.enterDescription();
        productType.enableProductCostToggle();
        productType.clickSaveButton();
        productType.handelSuccessPopup();
    }

    public void createProductEmployeeAcceptance(String toggleName) {
        productType.clickAddButton();
        productType.enterProductTypeName();
        productTypeName = getAtribute(By.cssSelector("div>input#AssetType"), "value", 20);
        productType.selectStatus();
        productType.enterProductCode();
        if (toggleName.equalsIgnoreCase("container")) {
            productType.clickContainerToggle();
            productType.enterContainerName();
        }
        if (toggleName.equalsIgnoreCase("Employee")) {
            productType.enableEmployeeAcceptanceToggle();
        }

        productType.enableAssetToggle();
        productType.enableProductCostToggle();
        productType.enterDescription();
        productType.clickSaveButton();
        productType.handelSuccessPopup();
    }

    public void createProductType() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductTypeForDisableConsumable("Asset");
        myproduct.createNewProduct(false, "barcode", productTypeName, "1", "");
        myproduct.assignProductToUser(1, false, "SingleProduct", "normalUser");
    }

    public void createProductTypes() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductTypeForDisableConsumable("Asset");
        myproduct.createNewProduct(false, "barcode", productTypeName, "1", "");
        myproduct.assignProductToUser(1, false, "SingleProduct", "normalUser");
    }

    public void clickAction() {
        // click(By.xpath("//table[@id='tblProjectList']//tbody/tr/td/span[text()='"+itemNameRandomValue+"']/../../td/span[@class='actions mobileaction']"),"Action",10);
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 300);
        click(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[8]//span[@class='actions mobileaction']"), "", 10);
    }


    public void createEmployeesAcceptance() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductEmployeeAcceptance("Employee");
        myproduct.createNewProduct(false, "barcode", productTypeName, "1", "");
        myproduct.assignProductToUser(1, false, "SingleProduct", "normalUser");
    }

    public void verifyAcceptVisible(String condition) {
        boolean conditionCheck;
        if (condition.equalsIgnoreCase("Enable")) {
            WebElement acceptButton = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[8]//a[@data-original-title='Accept']"), 10);
            conditionCheck = !acceptButton.getAttribute("class").contains("disabled");
        } else {
            WebElement acceptButton = findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[8]//a[@data-original-title='Accept']"), 10);
            conditionCheck = acceptButton.getAttribute("class").contains("disabled");
        }
        if (conditionCheck) {
            getTest().log(LogStatus.PASS, "Accept button is " + condition + " if product type created with employee acceptance Toggle as " + condition + " in product category ");
            logger.info("Accept button is " + condition + " if product type created with employee acceptance Toggle as " + condition + " in product category ");
        } else {
            getTest().log(LogStatus.PASS, "Accept button is not " + condition + " if product type created with employee acceptance Toggle as " + condition + " in product category ");
            logger.info("Accept button is not " + condition + " if product type created with employee acceptance Toggle as " + condition + " in product category ");
            takeScreenshot("AcceptButton");
        }
    }

    public void verifySelfCheckOutFunction() {
        myproduct.verifySelfCheckOutFunction();
    }

    public void createAndVerifyConsumeProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        myproduct.createProductType("Consumable");
        verifyConsumeProduct();
    }

    public void verifyConsumeProduct() {
        if (findElementsVisibility(By.xpath("//a[@class='update ancEditAssetType' and contains(text(),'" + productTypeName + "')]")) != null) {
            getTest().log(LogStatus.PASS, "Consume type product should be created");
            logger.info("Consume type product should be created");
        } else {
            getTest().log(LogStatus.FAIL, "Consume type product should not be created");
            logger.info("Consume type product should not be created");
        }
    }

    public void createInsurancePolicy() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        myproduct.createProductType("InsurancePolicy");
        createNewProductAndVerifyInsurnceField(true, "barcode", productTypeName, "10", "");

    }

    public void createNewProductAndVerifyInsurnceField(boolean uniqueName, String barCodeType, String productTypeName, String quantity, String locationName) {
        String productName;
        String barCodeValue;
        AddProductPage addProductPage = new AddProductPage(driver);
        DeployProductPage deployProductPage = new DeployProductPage(driver);
        addProductPage.clickFullMenuDropDown();
        addProductPage.clickAssetManagement();
        addProductPage.clickManageProduct();
        addProductPage.clickAddNewButton();
        if (productTypeName.equalsIgnoreCase("")) {
            selectValueWithIndex(By.cssSelector("div>select#AssetTypeId"), 1, "Product Type", 10);
        } else {
            selectValueWithText(By.cssSelector("div>select#AssetTypeId"), productTypeName, "Product Type", 10);
        }
        if (termsAndCondition) {
            click(By.cssSelector("button#closenotifymessage"), "Terms and Condition Warning close", 20);
        }
        Select select = new Select(driver.findElement(By.cssSelector("div>select#AssetTypeId")));
        WebElement option = select.getFirstSelectedOption();
        productTypesToAssign = option.getText();
        addProductPage.enterItemName();
        productNamesToAssign.add(getAtribute(By.cssSelector("div>input#Name"), "value", 20));
        if (barCodeType.equalsIgnoreCase("barcode")) {
            selectValueWithIndex(By.cssSelector("select#BarcodeTypeId"), 1, "Barcode type - BarCode", 10);
            selectedBarCodeType = "BarCode";
        } else {
            selectValueWithIndex(By.cssSelector("select#BarcodeTypeId"), 2, "Barcode type - QRCode", 10);
            selectedBarCodeType = "QRCode";
        }
        if (uniqueName) {
            addProductPage.clickTheCheckBox();
        }
        addProductPage.clickNextButton();
        staticWait(5000);
        deployProductPage.clickAddDeployButton();
        if (findElementsVisibility(By.id("InsurerName")).isEnabled()) {
            getTest().log(LogStatus.PASS, "Insurance policy field is enable");
            logger.info("Insurance policy field is enable");
        } else {
            getTest().log(LogStatus.FAIL, "Insurance policy field is not enable");
            logger.info("Insurance policy field is not enable");
            takeScreenshot("InsuranceFieldEnable");
        }

        if (findElementsVisibility(By.id("AssetCost")).isEnabled()) {
            getTest().log(LogStatus.PASS, "Product cost field is enable");
            logger.info("Product cost field is enable");
        } else {
            getTest().log(LogStatus.FAIL, "Product cost field is not enable");
            logger.info("Product cost field is not enable");
            takeScreenshot("CostFieldEnable");
        }
        deployProductPage.clickLocationDropdown();
        deployProductPage.selectLocationValueFromDropdown(locationName);
        productLocationsToAssign.add(getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30));
        deployProductPage.enterQuantity(quantity);
        deployProductPage.enterUnitPrice("1");
        deployProductPage.enterProductCost("1");
        deployProductPage.clickAddListButton();
        deployProductPage.clickSaveButton();
        deployProductPage.handleSuccessPopup();


    }
}
