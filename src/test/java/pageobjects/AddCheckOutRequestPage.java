package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class AddCheckOutRequestPage extends WebBasePage {

    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    MyProductPage myProduct;
    CheckOutRequestPage checkOutRequest;
    ProductTransferPage productTransfer;

    public AddCheckOutRequestPage(WebDriver driver) {
        super(driver, "Add Check out Request Page");
        this.myProduct = new MyProductPage(driver);
        this.productTransfer = new ProductTransferPage(driver);
        this.checkOutRequest = new CheckOutRequestPage(driver);
    }

    public void navigateToAddCheckOutList() {
        click(By.cssSelector("a#ancaddgroupRequisitions"), "Add Check out list", 20);
    }

    public void clickCheckOutInBreadCrumb() {
        click(By.xpath("//div[@id='SiteMapLink']//a[text()='Check Out List']"), "BreadCrumb Check Out List menu", 20);
    }

    public void verifyCheckOutListPage() {
        WebElement myProductHeading = findElementVisibility(By.xpath("//div[contains(@class,'theme-primary partition')]/span[normalize-space(text())='Check Out List']"), 30);
        if (myProductHeading != null) {
            getTest().log(LogStatus.PASS, "Check Out List page is displayed when click on the Check Out List menu in the add check out page breadcrumb");
            logger.info("Check Out List page is displayed when click on the Check Out List menu in the add check out page breadcrumb");
        } else {
            getTest().log(LogStatus.FAIL, "Check Out List page is not displayed when click on the Check Out List menu in the add check out page breadcrumb");
            logger.info("Check Out List page is not displayed when click on the Check Out List menu in the add check out page breadcrumb");
            takeScreenshot("CheckOutListPage");
        }
    }

    public String selectAssignDates(String field) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        DateTimeFormatter monthName = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter monthDigit = DateTimeFormatter.ofPattern("M");
        DateTimeFormatter dayDigit = DateTimeFormatter.ofPattern("d");
        LocalDateTime now = LocalDateTime.now();
        String monthChar = monthName.format(now);
        String monthInDigit = monthDigit.format(now);
        String dayInDigit = dayDigit.format(now);
        String[] inputDateArray = dtf.format(now.plusYears(1)).split("/");
        String day = inputDateArray[0];
        String month = inputDateArray[1];
        String year = inputDateArray[2];

        String locator = (field.equalsIgnoreCase("AssignFrom")) ? "AssignedFrom" : "AssignedTill";
        //click(By.cssSelector("div>input#"+locator), locator+" Date Field", 20);
        click(By.xpath("//div/input[@id='" + locator + "']/../div/span"), locator + " Date Field", 20);
        click(By.xpath("//th[@title='Select Month']"), "Month switch", 30);
        click(By.xpath("//th[@title='Select Year']"), "Year switch", 15);
        click(By.xpath("//span[contains(@class,'year') and text()='" + year + "']"), "Year Value", 15);
        click(By.xpath("//span[contains(@class,'month') and text()='" + month + "']"), "Month Value", 15);
        click(By.xpath("//td[@data-day='" + monthChar + "/" + day + "/" + year + "']"), "Day Value", 20);
        return monthInDigit + "/" + dayInDigit + "/" + year;
    }

    public void verifyAssignDateField(String field) {
        String expectedDate = selectAssignDates(field);
        waitForLoader(20);
        String locator = (field.equalsIgnoreCase("AssignFrom")) ? "AssignedFrom" : "AssignedTill";
        String actualDate = getAtribute(By.cssSelector("div>input#" + locator), "value", 30);
        if (actualDate.equals(expectedDate)) {
            getTest().log(LogStatus.PASS, "User can able to select date from the calendar for the " + field);
            logger.info("User can able to select date from the calendar for the " + field);
        } else {
            getTest().log(LogStatus.FAIL, "User not able to select date from the calendar for the " + field);
            logger.info("User not able to select date from the calendar for the " + field);
            takeScreenshot("Date");
        }
    }

    public void verifyAssignFromDate() {
        verifyAssignDateField("AssignFrom");
    }

    public void verifyAssignTillDate() {
        verifyAssignDateField("AssignTill");
    }

    public String selectAssignTimes(String locator) {
        DecimalFormat formatter = new DecimalFormat("00");
        String hValue;
        String minValue;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm ");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        hValue = currentTime.split(":")[0];
        minValue = currentTime.split(":")[1];
        if (locator.equalsIgnoreCase("EndTime")) {
            hValue = dtf.format(now.plusHours(1)).split(":")[0];
        }

        click(By.xpath("//div[@data-target='#" + locator + "']"), locator, 25);
        click(By.cssSelector(".timepicker-hour"), "Time Picker", 25);
        click(By.xpath("//td[@class='hour' and text()='" + hValue + "']"), "Time - " + hValue, 15);
        click(By.cssSelector(".timepicker-minute"), "Minute Picker", 15);
        int minute = Integer.parseInt(minValue.split("")[1]) * 5;
        String minuteValue = formatter.format(minute);
        click(By.xpath("//td[@class='minute' and text()='" + minuteValue + "']"), "Minute - " + minuteValue, 15);
        String timePeriod = getText(By.xpath("//button[@title='Toggle Period']"), 20);
        click(By.xpath("//div[@data-target='#" + locator + "']"), locator, 20);
        return hValue + ":" + minuteValue + " " + timePeriod.toLowerCase();
    }

    public void verifySelectedTime(String locator) {
        String expectedTime = selectAssignTimes(locator);
        String actualTime = getAtribute(By.xpath("//input[@id='" + locator + "']"), "value", 20);
        if (actualTime.equalsIgnoreCase(expectedTime)) {
            getTest().log(LogStatus.PASS, "User can able to select time for the the Assign " + locator + " and the selected time is displayed");
            logger.info("User can able to select time for the the Assign " + locator + " and the selected time is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "User not able to select time for the the Assign " + locator + " or the selected time is displayed");
            logger.info("User not able to select time for the the Assign " + locator + " or the selected time is displayed");
            takeScreenshot("TimeSelection");
        }
    }

    public void verifyAssignStartTime() {
        verifySelectedTime("StartTime");
    }

    public void verifyAssignEndTime() {
        verifySelectedTime("EndTime");
    }

    public void alphaNumericCheckOutComment() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        myProduct.enterRemark(expectedValue);
        String actualValue = getAtribute(By.cssSelector("#Comment"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The CheckOut Comment field in the CheckOut list page is accepts the alpha numeric value as expected");
            logger.info("The CheckOut Comment field in the CheckOut list page is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The CheckOut Comment field in the CheckOut list page is not accepts the alpha numeric value");
            logger.info("The CheckOut Comment field in the CheckOut list page is not accepts the alpha numeric value");
            takeScreenshot("CheckOutComment");
        }
    }

    public void enterInBarCode(String value) {
        enter(By.cssSelector("input#txtBarcode"), value, "Barcode Name", 20);
    }

    public void verifyAlphaNumericBarCodeValue() {
        verifyAlphaNumericBarCodeValue("Add checkout request page");
    }

    public void verifyAlphaNumericBarCodeValue(String pageName) {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterInBarCode(expectedValue);
        String actualValue = getAtribute(By.cssSelector("input#txtBarcode"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Barcode field in the " + pageName + " is accepts the alpha numeric value as expected");
            logger.info("The Barcode field in the " + pageName + " is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Barcode field in the " + pageName + " is not accepts the alpha numeric value");
            logger.info("The Barcode field in the " + pageName + " is not accepts the alpha numeric value");
            takeScreenshot("Barcode");
        }
    }

    public void clickBarCodeSearch() {
        click(By.cssSelector("a#Searchassest_barcode"), "Barcode Search", 20);
    }

    public void enterValidBarCode() {
        enterInBarCode(barCodeValues.get(0));
    }

    public void verifyBarCodeSearchResult() {
        waitForLoader(20);
        scrollToWebelement(By.cssSelector("input#txtBarcode"), "Barcode search field");
        WebElement actualSearchResult = findElementVisibility(By.xpath("//table[@id='tblassestgroupinfo']//tbody//tr[1]/td[2]"), 20);
        if (actualSearchResult != null) {
            if (actualSearchResult.getText().contains(productNamesToAssign.get(0))) {
                getTest().log(LogStatus.PASS, "The search result for entered bard code - " + barCodeValues.get(0) + " is displayed as expected");
                logger.info("The search result for entered bard code - " + barCodeValues.get(0) + " is displayed as expected");
            } else {
                getTest().log(LogStatus.FAIL, "The search result for entered bard code - " + barCodeValues.get(0) + " is not displayed accurately");
                logger.info("The search result for entered bard code - " + barCodeValues.get(0) + " is not displayed accurately");
                takeScreenshot("BarCodeSearch");
            }
        } else {
            getTest().log(LogStatus.FAIL, "The search result for entered bard code is not displayed accurately");
            logger.info("The search result for entered bard code is not displayed accurately");
            takeScreenshot("BarCodeSearch");
        }
    }

    public void verifyLocationField() {
        location = productLocationsToAssign.get(0);
        productTransfer.selectLocation();
    }

    public void verifyProductTypeField() {
        prodType = productTypesToAssign;
        productTransfer.selectProductType();
    }

    public void verifyProductNameField() {
        productName = productNamesToAssign.get(0);
        productTransfer.selectProductName();
    }

    public void verifyProductField() {
        uniqueName = productsToAssign.get(0);
        productTransfer.enterUniqueName();
        productTransfer.verifyUniqueNameDropDown();
        productTransfer.selectUniqueName();
    }

    public void verifySearchedCheckOutProduct() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblassestgroupinfo']/tbody/tr[1]//td[2]"), 60);
        if (element != null) {
            String actualUniqueName = getText(By.xpath("//table[@id='tblassestgroupinfo']/tbody/tr[1]//td[2]"), 30, 30).trim();
            if (actualUniqueName.contains(uniqueName)) {
                getTest().log(LogStatus.PASS, "Searched Product is displayed as expected");
                logger.info("Searched Product is displayed as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Searched Product is not displayed ");
                logger.info("Searched Product is not displayed ");
                takeScreenshot("Result Table");
            }
        }
    }

    public void verifyCheckOutTableHeader() {
        scrollToWebelement(By.cssSelector("input#txtBarcode"), "Barcode Search Field");
        String actualHeader;
        int iteration = 0;
        List<WebElement> checkOutTableHeaders = findMultipleElement(By.xpath("//table[@id='tblassestgroupinfo']//th/span"), 30);
        for (WebElement checkOutTableHeader : checkOutTableHeaders) {
            actualHeader = checkOutTableHeader.getText().trim();
            String[] expectedHeaders = {"S.No", "Product Name", "Terms and Conditions", "Action"};
            for (String expectedHeader : expectedHeaders) {
                iteration++;
                if (actualHeader.equalsIgnoreCase(expectedHeader)) {
                    getTest().log(LogStatus.PASS, "The header name " + expectedHeader + " is displayed in the check out product searched table as expected");
                    logger.info("The header name " + expectedHeader + " is displayed in the check out product searched table as expected");
                    iteration = 0;
                    break;
                } else if (iteration == expectedHeaders.length) {
                    getTest().log(LogStatus.FAIL, "The header name " + expectedHeader + " is not displayed in the check out product searched table as expected");
                    logger.info("The header name " + expectedHeader + " is not displayed in the check out product searched table as expected");
                    takeScreenshot("CheckOutTableHeader");
                }
            }
        }
    }

    public void verifyTermsAndConditionOption() {
        WebElement termsAndCondition = findElementVisibility(By.xpath("//input[contains(@class,'assertermscondition')]//parent::a"), 20);
        if (termsAndCondition != null) {
            getTest().log(LogStatus.PASS, "Terms and condition check box is displayed if product type has enabled the terms and conditions toggle");
            logger.info("Terms and condition check box is displayed if product type has enabled the terms and conditions toggle");
            click(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr[1]//td[6]//div"), "Terms and Condition", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Terms and condition check box is not displayed even the product type has enabled the terms and conditions toggle");
            logger.info("Terms and condition check box is not displayed even the product type has enabled the terms and conditions toggle");
            takeScreenshot("CheckOutSearchT&C");
        }
    }

    public void verifyListOfProductTable() {
        String lisOfProduct = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='heading-border']/h5/span"), 20);
        if (lisOfProduct.equalsIgnoreCase("List Of Product")) {
            getTest().log(LogStatus.PASS, "The Lis of Product table is displayed as expected when click action icon");
            logger.info("The Lis of Product table is displayed as expected when click action icon");
        } else {
            getTest().log(LogStatus.FAIL, "The Lis of Product table is not displayed when click action icon");
            logger.info("The Lis of Product table is not displayed when click action icon");
            takeScreenshot("ListOfProdTable");
        }
    }

    public void verifyListOfProdHeaders() {
        String actualHeader;
        int iteration = 0;
        waitForLoader(30);
        List<WebElement> ListOfProdTableHeaders = findMultipleElement(By.xpath("//table[@id='tblRequestDataItem']//th/span"), 30);
        for (WebElement ListOfProdTableHeader : ListOfProdTableHeaders) {
            actualHeader = ListOfProdTableHeader.getText().trim();
            String[] expectedHeaders = {"#", "Product Name", "Terms and Conditions", "Available Quantity", "Pending Available Quantity", "Request Quantity", "Compartment", "Delete"};
            for (String expectedHeader : expectedHeaders) {
                iteration++;
                if (actualHeader.equalsIgnoreCase(expectedHeader)) {
                    getTest().log(LogStatus.PASS, "The header name " + actualHeader + " is displayed in the list of product table as expected");
                    logger.info("The header name " + actualHeader + " is displayed in the list of product table as expected");
                    iteration = 0;
                    break;
                } else if (iteration == expectedHeaders.length) {
                    getTest().log(LogStatus.FAIL, "The header name " + actualHeader + " is not displayed in the list of product table as expected");
                    logger.info("The header name " + actualHeader + " is not displayed in the list of product table as expected");
                    takeScreenshot("LisOfProdTableHeader");
                }
            }
        }
    }

    public void enterRequestQuantity(String value) {
        enter(By.cssSelector("td>input.txtAssetQuentity"), value, "Product Quantity", 20);
    }

    public void verifyRequestQuantityField() {
        String quantity = getAtribute(By.cssSelector("td>input.txtAssetQuentity"), "value", 20);
        int quantityNeedToEnter = Integer.parseInt(quantity) - 1;
        enterRequestQuantity(String.valueOf(quantityNeedToEnter));
        String actualQuantity = getAtribute(By.cssSelector("td>input.txtAssetQuentity"), "value", 20);
        if (actualQuantity.equalsIgnoreCase(String.valueOf(quantityNeedToEnter))) {
            getTest().log(LogStatus.PASS, "Quantity field in the list of product table accepts the numeric value as expected");
            logger.info("Quantity field in the list of product table accepts the numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Quantity field in the list of product table is not accepts the numeric value");
            logger.info("Quantity field in the list of product table is not accepts the numeric value");
            takeScreenshot("QuantityNumeric");
        }
    }

    public void clickDeleteIcon() {
        click(By.cssSelector("td>a.ancremoveData"), "Delete", 20);
    }

    public void confirmationForDelete() {
        WebElement confirmation = findElementVisibility(By.xpath("//div[text()='Are you sure you want to delete this record?']"), 20);
        if (confirmation != null) {
            staticWait(2000);
            click(By.xpath("//button[@title='OK']"), "Confirmation Ok", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation for product delete is not displayed when click on the delete icon in the Lis of Product table");
            logger.info("Confirmation for product delete is not displayed when click on the delete icon in the Lis of Product table");
        }
    }

    public void verifyDeletedProduct() {
        int productTypeOptionCount = findMultipleElement(By.xpath("//select[@id='AssetTypeFilter']/option"), 5).size();
        if (productTypeOptionCount == 1) {
            getTest().log(LogStatus.PASS, "Search table is displayed as expected when click delete icon in the list of product table");
            logger.info("Search table is displayed as expected when click delete icon in the list of product table");
        } else {
            getTest().log(LogStatus.PASS, "Search table is not displayed when click delete icon in the list of product table");
            logger.info("Search table is not displayed when click delete icon in the list of product table");
            takeScreenshot("SearchTable");
        }
    }

    public void clickCancelButton() {
        click(By.cssSelector("a#ancCancelgroupRequisition"), "Check out cancel", 20);
    }

    public void verifyCheckOutListScreen() {
        WebElement myProductHeading = findElementVisibility(By.xpath("//div[contains(@class,'theme-primary partition')]/span[normalize-space(text())='Check Out List']"), 30);
        if (myProductHeading != null) {
            getTest().log(LogStatus.PASS, "Check Out List page is displayed when click on the cancel button in the search product screen");
            logger.info("Check Out List page is displayed when click on the cancel button in the search product screen");
        } else {
            getTest().log(LogStatus.FAIL, "Check Out List page is not displayed when click on the cancel button in the search product screen");
            logger.info("Check Out List page is not displayed when click on the cancel button in the search product screen");
            takeScreenshot("CheckOutListScreen");
        }
    }

    public void saveProductCheckOutRequest() {
        click(By.cssSelector("a#ancsaverequest"), "Check out request save", 20);
    }

    public void verifySavedCheckOutRequest() {
        checkOutRequest.verifyProductCheckOutReq(productNamesToAssign.get(0));
        refreshThePage();
    }
}
