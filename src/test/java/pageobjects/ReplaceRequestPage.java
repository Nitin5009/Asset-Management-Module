package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class ReplaceRequestPage extends WebBasePage {
    WebDriver driver;
    ReturnRequestPage returnRequestPage;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public ReplaceRequestPage(WebDriver driver) {
        super(driver, "Product type Page");
        this.driver = driver;
        this.returnRequestPage = new ReturnRequestPage(driver);
    }

    public void verifyReplaceRequestLinkClickable() {
        WebElement replaceRequest = findElementVisibility(By.id("aReplaceRequest"), 10);
        if (replaceRequest.isEnabled()) {
            replaceRequest.click();
            getTest().log(LogStatus.PASS, "Replace request link is clickable");
            logger.info("Replace request link is clickable");
        } else {
            getTest().log(LogStatus.FAIL, "Replace request link is not clickable");
            logger.info("Replace request link is not clickable");
        }
    }

    public void verifyTheColumnsInReplaceRequest() {
        String actualText;
        int iteration = 0;
        String[] expectedColumns = {"User Name", "Product", "Unique Name", "Reason", "Comment", "Approved/Rejected Comment", "Requested At", "Status", "Approve/Reject Request", "Repair"};
        List<WebElement> columnElement = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']/thead/tr/th/span"), 30);
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

    public void ascendingAndDescendingOfUserName() {
        verifySortingOfUserNameColumn("ascending");
        verifySortingOfUserNameColumn("descending");
    }

    public void ascendingAndDescendingOfProductName() {
        verifySortingOfProductColumn("ascending");
        verifySortingOfProductColumn("descending");
    }

    public void verifySortingOfUserNameColumn(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        if(order.equalsIgnoreCase("descending")){
        scrollUpDown("up");}
        click(By.xpath("//span[@id='USER_NAME']"), "Unique Name", 30);
        selectValueWithValue(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//select[@id='pageSize']"), "100", "Page Size", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td[1]"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td[1]"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList =new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "UserName is sorted in ascending Order ");
                logger.info("UserName are sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, "UserName is not sorted in ascending Order");
                logger.info("UserName is not sorted in ascending Order ");
                takeScreenshot("UserNameStatusSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "UserName is sorted in descending Order");
                logger.info("Status is sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Status are not sorted in descending Order ");
                logger.info("UserName is not sorted in descending Order");
                takeScreenshot("UserNameStatusSort");
            }
        }
    }

    public void verifySortingOfProductColumn(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        if(order.equalsIgnoreCase("descending"))
        {
            scrollUpDown("up");
        }
        click(By.xpath("//span[@id='NAME']"), "Product name", 20);
        selectValueWithValue(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//select[@id='pageSize']"), "100", "Page Size", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td[2]"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td[2]"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product is sorted in ascending Order ");
                logger.info("Product is sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Product is not sorted in ascending Order");
                logger.info("Product is not sorted in ascending Order ");
                takeScreenshot("ProductStatusSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product is sorted in descending Order");
                logger.info("Product is sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Product is not sorted in descending Order ");
                logger.info("Product is not sorted in descending Order");
                takeScreenshot("ProductStatusSort");
            }
        }
    }

    public void paginationOnReplaceRequestTab() {
        String lastRecord;
        int recordCount;
        int defaultStartRecordCount;
        int defaultEndCount;
        int totalRecordCount;
        String[] defaultPaginationText;
        String[] nextPaginationText;
        String[] previousPaginationText;
        String[] lastPagePaginationText;
        String[] firstPagePaginationText;
        defaultPaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            int secondPageEndRecordCount = Integer.parseInt(secondPaginationText[3]);
            int pageSize = Integer.parseInt(getText(By.cssSelector("#pageSize>option[selected]"), 30));
            if (secondPageStartRecordCount == defaultEndCount + 1 && secondPageEndRecordCount <= 2 * pageSize) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed when click on the \"2\" pagination button");
                logger.info("Second page is not displayed when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            secondPaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"1\" pagination button");
                logger.info("First page is displayed as expected when click on the \"1\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed when click on the \"1\" pagination button");
                logger.info("First page is not displayed when click on the \"1\" pagination button");
                takeScreenshot("Pagination1");
            }

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            nextPaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int nextPageStartRecordCount = Integer.parseInt(nextPaginationText[1]);
            if (nextPageStartRecordCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Next page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Next page is not displayed when click on the \"Next\" pagination button");
                logger.info("Next page is not displayed when click on the \"Next\" pagination button");
                takeScreenshot("PaginationNext");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link previous' and text()='Prev']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            previousPaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int previousPageEndCount = Integer.parseInt(previousPaginationText[3]);
            if (previousPageEndCount == nextPageStartRecordCount - 1) {
                getTest().log(LogStatus.PASS, "Previous page is displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is displayed as expected when click on the \"Previous\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Previous page is not displayed when click on the \"Previous\" pagination button");
                logger.info("Previous page is not displayed when click on the \"Previous\" pagination button");
                takeScreenshot("PaginationPrevious");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link last' and text()='Last ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            lastPagePaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int lastPageEndCount = Integer.parseInt(lastPagePaginationText[3]);
            if (lastPageEndCount == totalRecordCount) {
                getTest().log(LogStatus.PASS, "Last page is displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is displayed as expected when click on the \"Last\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Last page is not displayed when click on the \"Last\" pagination button");
                logger.info("Last page is not displayed when click on the \"Last\" pagination button");
                takeScreenshot("PaginationLast");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link  first' and text()='First ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            firstPagePaginationText = getText(By.xpath("//div[@class='listing mt-2']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            firstPageStartRecordCount = Integer.parseInt(firstPagePaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is displayed as expected when click on the \"First\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed when click on the \"First\" pagination button");
                logger.info("First page is not displayed when click on the \"First\" pagination button");
                takeScreenshot("PaginationFirst");
            }
        } else {
            getTest().log(LogStatus.PASS, "Pagination section is not displayed since the record count is " + totalRecordCount);
            logger.info("Pagination section is not displayed since the record count is " + totalRecordCount);
        }
    }

    public void approveRejectCancelFunctionalityApproved()
    {
        approveRejectCancelFunctionality("Approved");
    }
    public void approveRejectCancelFunctionalityRejected()
    {
        approveRejectCancelFunctionality("Rejected");
    }
    public void approveRejectCancelFunctionality(String status) {
        verifyApproveOrRejectButtonClickable();
        verifyPopupForApproveOrReject();
        verifyTheStatusOfPopup(status);
        cancelReplaceRequest();
    }

    public void approveRejectSaveFunctionalityApproved()
    {
        approveRejectSaveFunctionality("Approved");
    }
    public void approveRejectSaveFunctionalityRejected()
    {
        approveRejectSaveFunctionality("Rejected");
    }
    public void approveRejectSaveFunctionality(String status) {
        verifyApproveOrRejectButtonClickable();
        verifyPopupForApproveOrReject();
        verifyTheStatusOfPopup(status);
        verifyRemarkField();
        saveReplaceRequest();
        returnRequestPage.verifyValidationMessage("Product Replace Request has been " + status + ".");
        verifyStatus(status);
    }

    public void verifyApproveOrRejectButtonClickable() {
        WebElement request = findElementVisibility(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td/a/span[text()='Active']/../..//following::td[1]"), 10);
        if (request != null) {
            userName = getText(By.xpath("//table[@id='divRequestionItemsData']/tbody/tr/td/a/span[text()='Active']/../../../td[3]"), 10);
            request.click();
            getTest().log(LogStatus.PASS, "Approve or reject request is clicked");
            logger.info("Approve or reject request is clicked");
        } else {
            getTest().log(LogStatus.FAIL, "Approve or reject request is not clicked");
            logger.info("Approve or reject request is not clicked");
        }
    }

    public void verifyPopupForApproveOrReject() {
        WebElement popup = findElementPresence(By.xpath("//div[@class='modal-content']/div/h5[@id='ui-id-8']"), 10);
        if (popup != null) {
            getTest().log(LogStatus.PASS, "Approve or reject request popup is displayed");
            logger.info("Approve or reject request popup is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Approve or reject request popup is not displayed");
            logger.info("Approve or reject request popup is not displayed");
        }
    }

    public void verifyTheStatusOfPopup(String status) {
        countOfDropdownValues(By.id("ReplaceRequestStatus"), "Status", 10);
        selectStatus(status);
    }

    public void selectStatus(String status) {
        selectValueWithText(By.id("ReplaceRequestStatus"), status, "Status", 10);
    }

    public void verifyRemarkField() {
        WebElement remarkField = findElementsVisibility(By.id("txtComment"));
        if (remarkField != null) {
            enter(By.id("txtComment"), prop.getProperty("productNameAlphaNumeric"), "Comment", 10);
            String remark = getAtribute(By.id("txtComment"), "value", 10);
            if (remark.equals("New123")) {
                getTest().log(LogStatus.PASS, "Alpha numeric value is entered");
                logger.info("Alpha numeric value is entered");
            } else {
                getTest().log(LogStatus.PASS, "Alpha numeric value is not entered");
                logger.info("Alpha numeric value is not entered");
            }
        }
    }

    public void saveReplaceRequest() {
        click(By.id("btnSaveApproval"), "Save", 10);
    }

    public void cancelReplaceRequest() {
        click(By.id("btnCancelApproval"), "Cancel", 10);
    }
    public void verifyClosedRepairPopup()
    {
        WebElement repairPopup = findElementVisibility(By.id("ui-id-8"), 10);
        if(repairPopup == null)
        {
            getTest().log(LogStatus.PASS,"The repair popup is closed when click cancel button");
            logger.info("The repair popup is closed when click cancel button");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The repair popup is not closed when click cancel button");
            logger.info("The repair popup is not closed when click cancel button");
            takeScreenshot("RepairClose");
        }
    }
    public void verifyStatus(String status) {
        String actualText = getText(By.xpath("//td[normalize-space(text())='"+userName+"']//parent::tr//td/a/span"), 10);
        if (actualText.equals(status)) {
            getTest().log(LogStatus.PASS, "Active status changed to " + status + " status");
            logger.info("Active status changed to \"+status+\" status");
        } else {
            getTest().log(LogStatus.FAIL, "Active status not changed to " + status + " status");
            logger.info("Active status not changed to \"+status+\" status");
        }
    }

    public void verifyCancelRepairFunction() {
        verifyRepairClickable();
        verifyRepairPopup();
        cancelReplaceRequest();
        verifyClosedRepairPopup();
    }

    public void verifySaveRepairFunction() {
        verifyRepairClickable();
        VerifyLocationIsClickable();
        selectMaintenanceType();
        verifySelectedDate();
        verifyRemarkFieldInRepair();
        saveReplaceRequest();
    }

    public void verifyRepairClickable() {
        scrollToWebelement(By.xpath("//td/a/i[@class='fa fa-wrench action-icon text-dark']"),"Repair Icon");
        scrollToWebelement(By.xpath("//span[text()='Replace Request']"),"Replace Request header");
       WebElement repair = findElementVisibility(By.xpath("//td/a/i[@class='fa fa-wrench action-icon text-dark']"),10);
        if (repair != null) {
            click(By.xpath("//td/a/i[@class='fa fa-wrench action-icon text-dark']"),"Repair",10);
        } else {
            getTest().log(LogStatus.FAIL, "Repair icon is not clickable");
            logger.info("Repair icon is not clickable");
            takeScreenshot("RepairIconNotClickable");
        }
    }

    public void verifyRepairPopup() {
        WebElement repairPopup = findElementVisibility(By.id("ui-id-8"), 10);
        if (repairPopup != null) {
            getTest().log(LogStatus.PASS, "Repair popup is displayed");
            logger.info("Repair popup is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Repair popup is not displayed");
            logger.info("Repair popup is not displayed");
        }
    }

    public void VerifyLocationIsClickable() {
        WebElement location = findElementPresence(By.xpath("//div[@id='divmultilevelselect']/div/div/span"), 10);
        if (location != null) {
            location.click();
            click(By.xpath("//ul[@id='CompantLocationSelect']//li[@class='parentli'][1]//a/span"), "Location Value", 10);
            getTest().log(LogStatus.PASS, "Location is selected");
            logger.info("Location is selected");
        } else {
            getTest().log(LogStatus.FAIL, "Location is not selected");
            logger.info("Location is not selected");
        }

    }

    public void selectMaintenanceType() {
        selectValueWithText(By.id("MaintenanceType"), "Repair", "Maintenance", 10);
    }

    public void verifyRemarkFieldInRepair() {
        WebElement remarkField = findElementsVisibility(By.id("Comment"));
        if (remarkField != null) {
            enter(By.xpath("//textarea[@id='Comment']"), prop.getProperty("productNameAlphaNumeric"), "Comment", 10);
            String remark = getAtribute(By.xpath("//textarea[@id='Comment']"), "value",10);
            if (remark.equals("New123")) {
                getTest().log(LogStatus.PASS, "Alpha numeric value is entered");
                logger.info("Alpha numeric value is entered");
            } else {
                getTest().log(LogStatus.PASS, "Alpha numeric value is not entered");
                logger.info("Alpha numeric value is not entered");
            }
        }
    }

    public String selectDepreciationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        DateTimeFormatter monthName = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter monthDigit = DateTimeFormatter.ofPattern("M");
        DateTimeFormatter dayDigit = DateTimeFormatter.ofPattern("d");
        LocalDateTime now = LocalDateTime.now();
        String monthChar = monthName.format(now);
       String monthInDigit = monthDigit.format(now);
       String dayInDigit = dayDigit.format(now.plusDays(1));
        String[] inputDateArray = dtf.format(now.plusDays(1)).split("/");
        String day = inputDateArray[0];
        String month = inputDateArray[1];
        String year = inputDateArray[2];

        click(By.xpath("//div[@data-target='#txtExpectedReturnDate']"), "Date Field", 20);
        click(By.xpath("//th[@title='Select Month']"), "Month switch", 25);
        click(By.xpath("//th[@title='Select Year']"), "Year switch", 15);
        click(By.xpath("//span[contains(@class,'year') and text()='" + year + "']"), "Year Value", 15);
        click(By.xpath("//span[contains(@class,'month') and text()='" + month + "']"), "Month Value", 15);
        click(By.xpath("//td[@data-day='" + monthChar + "/" + day + "/" + year + "']"), "Day Value", 20);
        return monthInDigit + "/" + dayInDigit + "/" + year;
    }

    public void verifySelectedDate() {
        String expectedDate = selectDepreciationDate();
        waitForLoader(20);
        String actualDate = getAtribute(By.cssSelector("input#txtExpectedReturnDate"), "value", 30);
        if (actualDate.contains(expectedDate)) {
            getTest().log(LogStatus.PASS, "User can able to select date from the calendar");
            logger.info("User can able to select date from the calendar");
        } else {
            getTest().log(LogStatus.FAIL, "User not able to select date from the calendar");
            logger.info("User not able to select date from the calendar");
            takeScreenshot("Date");
        }
    }
}
