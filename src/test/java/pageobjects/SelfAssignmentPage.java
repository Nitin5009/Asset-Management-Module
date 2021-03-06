package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Drivers;
import utils.PropertiesLoader;
import utils.WebBasePage;

import static pageobjects.AttachmentsPage.filesInDirectory;
import static utils.StaticData.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;

public class SelfAssignmentPage extends WebBasePage {
    WebDriver driver;
    AddProductPage addProductPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());

    public SelfAssignmentPage(WebDriver driver) {
        super(driver, "Self Assignment Page");
        this.driver = driver;
        this.addProductPage = new AddProductPage(driver);
    }

    //Click the Full menu drop down
    public void clickFullMenuDropDown() {
        addProductPage.clickFullMenuDropDown();
    }

    //Verify Asset management menu is present in the full menu drop down  and click the menu
    public void clickAssetManagement() {
        addProductPage.clickAssetManagement();
    }

    //click My product
    public void clickMyProduct() {
        click(By.xpath("(//a[text()='My Products'])[2]"), "My products", 10);
    }

    //click Self Assignment button
    public void clickSelfAssignment() {
        click(By.xpath("//span[text()='Self Assignment']"), "Self Assignment", 10);
        currentTimeToTest = new SimpleDateFormat("hh:mm a").format(new Date());
    }

    //click Product Assignment
    public void clickProductAssignment() {
        //click(By.xpath("//a[text()='Product Assignment']"),"Product Assignment",10);
        scrollToWebelement(By.xpath("//a[text()='Product Assignment']"), "New Product Type Request");
        WebElement element = findElementVisibility(By.xpath("//div[@id='scrollbar']//a[text()='Product Assignment']"), 40);
        if (element != null) {
            click(By.xpath("//div[@id='scrollbar']//a[text()='Product Assignment']"), "Product Assignment", 20);
        } else {
            clickFullMenuDropDown();
            clickAssetManagement();
        }
    }

    //click assign product
    public void clickAssignProduct() {
        click(By.xpath("//a[@id='ancCreateJob' and text()='Assign Product']"), "Assign Product", 10);
    }

    public void verifyAutoGeneratedUserName() {
        String username = getText(By.xpath("//select[@id='IssuedTo']/option[@selected='selected']"), 10);
        if (username.contains(prop.getProperty("companyAdmin"))) {
            getTest().log(LogStatus.PASS, "Username is auto selected in the issued to field when self assignment page is opened");
            logger.info("Username is auto selected in the issued to field when self assignment page is opened");
        } else {
            getTest().log(LogStatus.FAIL, "Username is not selected in the issued to field when self assignment page is opened");
            logger.info("Username is not selected in the issued to field when self assignment page is opened");
            takeScreenshot("IssuedToUserName");
        }
    }
    // verify On the Assign product page following fields should be pre filled - Date of assign, Date of assign till, start time, end time

    public void verifyPreFilledData() {
        WebElement dateOfAssign;
        WebElement dateOfAssignTill;
        WebElement startTime;
        WebElement endTime;
        String preFilledDateOfAssign;
        String preFilledDateOfAssignTill;
        String preFilledStartTime;
        String preFilledEndTime;

        //Date of Assign
        dateOfAssign = findElementPresence(By.id("AssignedFrom"), 10);
        preFilledDateOfAssign = dateOfAssign.getAttribute("Value");
        if (preFilledDateOfAssign != null) {
            getTest().log(LogStatus.PASS, "Date of Assign '" + preFilledDateOfAssign + "' value is pre filled in the page");
            logger.info("Date of Assign '" + preFilledDateOfAssign + "' value is pre filled in the page");
        } else {
            getTest().log(LogStatus.FAIL, "Date of Assign value is not  pre filled in the page");
            logger.info("Date of Assign value is not pre filled in the page");
        }
        //Date of Assign till
        dateOfAssignTill = findElementPresence(By.id("AssignedTill"), 10);
        preFilledDateOfAssignTill = dateOfAssignTill.getAttribute("Value");
        if (preFilledDateOfAssignTill != null) {
            getTest().log(LogStatus.PASS, "Date of Assign Till '" + preFilledDateOfAssignTill + "' value is pre filled in the page");
            logger.info("Date of Assign Till '" + preFilledDateOfAssignTill + "' value is pre filled in the page");
        } else {
            getTest().log(LogStatus.FAIL, "Date of Assign Till value is not  pre filled in the page");
            logger.info("Date of Assign Till value is not pre filled in the page");
        }
        //Start time
        startTime = findElementPresence(By.id("StartTime"), 10);
        preFilledStartTime = startTime.getAttribute("Value");
        if (preFilledStartTime != null) {
            getTest().log(LogStatus.PASS, "Start time '" + preFilledStartTime + "' value is pre filled in the page");
            logger.info("Start time '" + preFilledStartTime + "' value is pre filled in the page");
        } else {
            getTest().log(LogStatus.FAIL, "Start time value is not  pre filled in the page");
            logger.info("Start time value is not pre filled in the page");
        }
        //End time
        endTime = findElementPresence(By.id("EndTime"), 10);
        preFilledEndTime = endTime.getAttribute("Value");
        if (preFilledEndTime != null) {
            getTest().log(LogStatus.PASS, "End time '" + preFilledEndTime + "' value is pre filled in the page");
            logger.info("End time '" + preFilledEndTime + "' value is pre filled in the page");
        } else {
            getTest().log(LogStatus.FAIL, "End time  value is not pre filled in the page");
            logger.info("End time value is not pre filled in the page");
        }
    }

    //Verify department field dropdown is clickable or not
    public void verifyDepartmentFieldDropdown() {
        WebElement department = findElementClickable(By.xpath("//select[@id='Department']/../div/button/span"), 10);
        if (department.isEnabled()) {
            click(By.xpath("//select[@id='Department']/../div/button/span"), "Department dropdown is clickable", 10);
            if (findElementVisibility(By.xpath("//ul[@class='multiselect-container dropdown-menu show']"), 10) != null) {
                getTest().log(LogStatus.PASS, "Dropdown value is displayed when click the dropdown");
                logger.info("Dropdown value is displayed when click the dropdown");
                click(By.xpath("//select[@id='Department']/../div/button/span"), "Department dropdown is clickable", 10);

            }
        } else {
            getTest().log(LogStatus.FAIL, "Dropdown value is not displayed when click the dropdown");
            logger.info("Dropdown value is not displayed when click the dropdown");
        }
    }

    //Verify Assign to field is selected user or not.If not selected select the user.
    public void verifyAssignToUser() {
        //String assignToValue=getAtribute(By.id("rdo_0"),"value",10);
        String assignToValue = findElementPresence(By.id("rdo_0"), 10).getAttribute("value");
        if (assignToValue.equals("User")) {
            getTest().log(LogStatus.PASS, "User is selected in the page");
            logger.info("Dropdown value is displayed when click the dropdown");
        } else {
            click(By.id("rdo_0"), "User", 10);
        }
    }

    //Get the count of select user
    public void getTheCount() {
        List<WebElement> user = findMultipleElement(By.id("IssuedTo"), 10);
        userCount = user.size();
    }

    //Select Multiple department
    public void selectMultipleDepartment() {
        click(By.xpath("//select[@id='Department']/../div/button/span"), "Department", 10);
        click(By.xpath("//label[text()=' Human Resources']"), "Select first Department", 10);
        click(By.xpath("//label[text()=' Sales']"), "Select second departments", 10);
    }

    //Verify user field should be reflecting users present according to the selected departments
    public void verifyUserCount() {
        List<WebElement> user = findMultipleElement(By.id("IssuedTo"), 10);
        if (userCount != user.size()) {
            getTest().log(LogStatus.PASS, "user field should be reflecting users present according to the selected departments");
            logger.info("user field should be reflecting users present according to the selected departments");
        } else {
            getTest().log(LogStatus.FAIL, "user field should not be reflecting users present according to the selected departments");
            logger.info("user field should not be reflecting users present according to the selected departments");

        }
    }

    //Select PastDateInAssignField
    public void enterAssignField() {
        click(By.id("//input[@id='AssignedFrom']"), "AssignField", 10);
    }

    //SelectAssignFieldTill()
    public void enterAssignTill() {
        click(By.id("//input[@id='AssignedTill']"), "AssignField", 10);
    }

    //Enter 150 Alpha numeric characters in remark field
    public void enterRemarkField() {
        enter(By.id("Remarks"), prop.getProperty("remarkAlphaCharacters150"), "Remarks", 10);
    }

    //Select multiple Notification user
    public void selectMultipleNotificationUser() {
        click(By.xpath("//select[@id='NotificationUser']/../div/button/span"), "Notification user", 10);
        click(By.xpath("//label[text()=' Oliver Noah(Utility Locate Technician)']"), "Select first Notification user", 10);
        click(By.xpath("//label[text()=' Harry jasnon(Sales)']"), "Select second Notification User", 10);
    }

    //Click cancel button
    public void clickCancelButton() {
        click(By.xpath("//a[@href='/AssetAssignment/Index' and text()='Cancel']"), "Cancel", 10);
    }

    //Verify Product assignment page
    public void verifyProductAssignment() {
        WebElement productAssignmentPage = findElementVisibility(By.xpath("//span[text()='Product Assignment']"), 10);
        if (productAssignmentPage != null) {
            getTest().log(LogStatus.PASS, "Product Assignment page is displayed when click on the Self Assignment button");
            logger.info("Product Assignment page is displayed when click on the Self Assignment button");
        } else {
            getTest().log(LogStatus.FAIL, "Product Assignment page is not displayed when click on the Self Assignment button");
            logger.info("Product Assignment page is not displayed when click on the Self Assignment button");
        }
    }

    //verify Assign product page
    public void verifyAssignProduct() {
        WebElement assignProduct = findElementsVisibility(By.xpath("//li[text()='Assign Product']"));
        if (assignProduct != null) {
            getTest().log(LogStatus.PASS, " Assign product page is displayed when click Assign product button");
            logger.info("Assign product page is displayed when click Assign product button");
        } else {
            getTest().log(LogStatus.FAIL, "Assign product page is not displayed when click Assign product button");
            logger.info("Assign product page is not displayed when click Assign product button");
        }
    }

    //Click Save button
    public void clickSaveButton() {
        click(By.id("btnSave"), "Save button", 10);
    }

    //Verify validation message
    public void verifyValidationMessage() {
        WebElement validationMessage = findElementsVisibility(By.xpath("//div[text()='Please correct the highlighted errors.']"));
        if (validationMessage != null) {
            click(By.xpath("//button[text()=' OK']"), "Ok", 10);
            WebElement verifyValidationMessage = findElementVisibility((By.xpath("//div[@id='ForDepartmentWise']/div/span[2]")), 10);
            if (verifyValidationMessage != null) {
                getTest().log(LogStatus.PASS, "Validation message is displayed when click save button");
                logger.info("Validation message is displayed when click save button");
            } else {
                getTest().log(LogStatus.PASS, "Validation message is not displayed when click save button");
                logger.info("Validation message is not displayed when click save button");
            }
        }
    }

    //Select the user
    public void selectTheUser() {
        selectValueWithText(By.id(""), "Safiya Smith (N/A)", "User", 10);
    }

    //Click search icon
    public void BarCodeSearch() {
        click(By.id("Searchassest_barcode"), "Search", 10);
    }

    //verify validation Message for search icon
    public void verifyValidationMessageSearch() {
        WebElement searchMessage = findElementVisibility(By.xpath("//span[text()='Please correct the highlighted errors.']"), 10);
        if (searchMessage != null) {
            getTest().log(LogStatus.PASS, "Validation message is Displayed when click search icon");
            logger.info("Validation message is Displayed when click search icon");
            click(By.xpath("//button[@id='closenotifymessage']"), "Close the validation message", 10);
        } else {
            getTest().log(LogStatus.FAIL, "Validation message is not Displayed when click search icon");
            logger.info("Validation message is not Displayed when click search icon");
        }
    }

    //Enter the invalid barcode number
    public void enterBarcodeNumber() {
        String proCode = dateValue.substring(9, 14);
        enter(By.id("txtBarcode"), "proCode", "Invalid Barcode", 10);
    }

    //Get the valid barcode
    public void getValidBarcode() {
        validBarcode = getText(By.xpath("//div[@class='text-center pt-0 pb-2 w-100']"), 10);
    }

    //Enter valid barcode
    public void enterValidBarcode() {
        enter(By.id("txtBarcode"), validBarcode, "Valid Barcode", 10);
    }

    public void navigateManageProduct() {
        addProductPage.clickManageProduct();
    }

    public void navigateManageRelatedInformation() {
        click(By.xpath("//table[@id='tablelistingdata']//td[5]//span"), "Product name", 30);
        click(By.xpath("//li[@class='nav-item']//a[normalize-space(text())='Related Information']"), "Related Information tab", 40);
        staticWait(1000);

    }

    //Verify alert message
    public void verifyAlertMessage() {
        WebElement alertMessage = findElementVisibility(By.xpath("//div[text()='Product is not associated with the system']"), 10);
        if (alertMessage != null) {
            getTest().log(LogStatus.PASS, "Alert message should be displayed stating 'Product is not associated with the system'");
            logger.info("Alert message should be displayed stating 'Product is not associated with the system'");
            click(By.xpath("//button[text()=' OK']"), "Close the Alert message", 10);
        } else {
            getTest().log(LogStatus.FAIL, "Alert message should not be displayed stating 'Product is not associated with the system'");
            logger.info("Alert message should not be displayed stating 'Product is not associated with the system'");
        }
    }

    public void selectLocation() {
        click(By.xpath("//div[@class='btn form-control']"), "Click location", 10);
    }

    public void verifySearchInLocation() {
        WebElement search = findElementVisibility(By.xpath("//li[@class='mainelevel']/input"), 10);
        if (search != null) {
            getTest().log(LogStatus.PASS, "Search field is displayed in the inside of Location");
            logger.info("Search field is displayed in the inside of Location");
        } else {
            getTest().log(LogStatus.FAIL, "Search field is not displayed in the inside of Location");
            logger.info("Search field is not displayed in the inside of Location");
        }
    }

    public void getLocation() {
        locationName = getText(By.xpath("//ol[@class='scrollbar']//li[2]//span"), 10);
    }

    public void enterLocationInSearchField() {
        enter(By.xpath("//li[@class='mainelevel']/input"), locationName, "Location name", 10);
    }

    public void clearTheLocation() {
        click(By.xpath("//i[@title='Clear']"), "Clear", 10);
    }

    public void verifyClearLocation() {
        String clearedLocationText;
        clearedLocationText = getText(By.xpath("//span[@class='CompantLocationSelectselected float-left text-truncate w-80']"), 10);
        if (clearedLocationText == null) {
            getTest().log(LogStatus.PASS, "Entered Location is cleared when click the clear button");
            logger.info("Entered Location is cleared when click the clear button");
        } else {
            getTest().log(LogStatus.PASS, "Entered Location is not cleared when click the clear button");
            logger.info("Entered Location is not cleared when click the clear button");
        }
    }

    public void selectProductType() {
        click(By.id("AssetTypeFilter"), "Product Type", 10);
        click(By.xpath("//select[@id='AssetTypeFilter']/option[2]"), "Select product type", 10);
    }

    public void selectProductName() {
        click(By.id("AssetCatalogFilter"), "Product Name", 10);
        click(By.xpath("//select[@id='AssetCatalogFilter']/option[2]"), "Select product name", 10);
    }

    public void enterProducts() {
        enter(By.id("txtAssetItems"), "I", "Product name", 10);
        click(By.xpath("//div[@class='unique_dynamicdatalist']//li[1]"), "Select product name", 10);
    }

    public void search() {
        click(By.id("Searchassest"), "Search", 10);
    }

    public void enterMoreQuantity() {
        String availableQuantity;
        int quantity;
        String moreQuantity;
        availableQuantity = getText(By.xpath("//td[7]"), 10);
        quantity = Integer.parseInt(availableQuantity) + 1;
        moreQuantity = String.valueOf(quantity);
        enter(By.xpath("//td[9]/input"), moreQuantity, "More than available quantity", 10);
    }

    public void enterQuantity() {
        String availableQuantity;
        availableQuantity = getText(By.xpath("//td[7]"), 10);
        enter(By.xpath("//td[9]/input"), availableQuantity, "Available quantity", 10);
    }

    public void verifyValidationMessageForQuantity() {
        WebElement validationMessage = findElementVisibility(By.xpath("//div[@class='-body alert alert-warning p-3 mb-0']"), 10);
        if (validationMessage != null) {
            getTest().log(LogStatus.PASS, "Validation message is displayed when enter more than available quantity ");
            logger.info("Validation message is displayed when enter more than available quantity ");
            click(By.xpath("//i[@class='fa fa-check']"), "Close the popup", 10);
        } else {
            getTest().log(LogStatus.FAIL, "Validation message is not displayed when enter more than available quantity ");
            logger.info("Validation message is not displayed when enter more than available quantity ");
        }
    }

    public void clickTermsAndCondition() {
        WebElement termsAndCondition = findElementVisibility(By.xpath("//label[contains(text(),'I agree to the Terms and Conditions')]"),15);
        if (termsAndCondition != null) {
            getTest().log(LogStatus.PASS, "Terms and condition link is displayed");
            logger.info("Terms and condition link is displayed");
            click(By.xpath("//label[contains(text(),'I agree to the Terms and Conditions')]"), "Terms and condition", 10);
        } else {
            getTest().log(LogStatus.FAIL, "Terms and condition link is not displayed");
            logger.info("Terms and condition link is not displayed");
        }
    }

    public void downloadTermAndConditionDocument() {
        String downloadPath = Drivers.path;
        File[] dir2 = new File(downloadPath).listFiles();
        filesInDirectory = dir2.length;
        click(By.cssSelector("a.downloadtermfile"), "Terms And Condition Download", 20);
    }

    public void downloadAndVerifyTermsAndCondition() {
        String downloadPath = Drivers.path;
        File[] dir2 = new File(downloadPath).listFiles();
        int filesAfterDownload = dir2.length;

        if (filesAfterDownload == filesInDirectory + 1) {
            getTest().log(LogStatus.PASS, "The Terms and Condition file is downloaded and exist in the folder");
            logger.info("The Terms and Condition file is downloaded and exist in the folder");
        } else {
            getTest().log(LogStatus.PASS, "The Terms and Condition file is not downloaded");
            logger.info("The Terms and Condition file is not downloaded");
            takeScreenshot("FileDownload");
        }
    }

    public void clickAddItem() {
        click(By.xpath("//i[@title='Add to item']"), "Add item", 10);
    }

    public void cancelProductAssignment() {
        click(By.xpath("//div[contains(@class,'text-right')]//a[contains(@class,'btn-danger') and text()='Cancel']"), "Product Assignment Cancel", 20);
        waitForLoader(60);
    }

    public void createTermsAndConditionProduct() {
        MyProductPage myProductPage = new MyProductPage(driver);
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        myProductPage.createProductType("TermsAndCond");
        myProductPage.createNewProduct(true, "barCode", productTypeName, "1", "");
    }

}
