package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertTrue;
import static utils.StaticData.*;
import static reporting.ComplexReportFactory.getTest;

public class ReturnRequestPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public ReturnRequestPage(WebDriver driver) {
        super(driver, "Product type Page");
        this.driver = driver;
    }

    public void verifyReturnRequestLinkClickable() {
        WebElement returnRequest = findElementClickable(By.id("aReturnRequest"), 10);
        if (returnRequest.isEnabled()) {
            returnRequest.click();
            getTest().log(LogStatus.PASS, "Return request link is clickable");
            logger.info("Return request link is clickable");
        } else {
            getTest().log(LogStatus.FAIL, "Return request link is not clickable");
            logger.info("Return request link is not clickable");
            takeScreenshot("RequestRequestLink");
        }
    }

    public void verifyReturnRequestScreenDisplayed() {
        String screenName = getText(By.xpath("//div[@id='ReturnRequestHeading']/span[@class='p-name text-white']"), 10);
        if (screenName.equals("Return Request")) {
            getTest().log(LogStatus.PASS, "Return request page is displayed");
            logger.info("Return request page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Return request page is not displayed");
            logger.info("Return request page is not displayed");
            takeScreenshot("ReturnPage");
        }
    }

    public void verifyTheColumnsInReturnRequest() {
        String actualText;
        int iteration = 0;
        String[] expectedColumns = {"User Name", "Product", "Unique Name", "Reason", "Comment", "Approval Comment", "Status", "Quantity", "Product Cost", "Book Value", "Over due days", "Penalty Status", "Penalty", "Return Date"};
        List<WebElement> columnElement = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']/thead/tr/th/span"), 30);
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

    public void getTheSearchStatusAndPenaltyValue() {
        searchUniqueName = getText(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td[4]"), 20);
        searchStatus = getText(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td[8]/a/span"), 20);

    }

    public void searchFieldInAlphaNumericValue() {
        WebElement searchField = findElementVisibility(By.id("Search"), 10);
        if (searchField.isEnabled()) {
            searchField.sendKeys(searchUniqueName);
            String actualText = getAtribute(By.id("Search"),"value", 20);
           if(actualText.equals(searchUniqueName)){
               getTest().log(LogStatus.PASS, "Alpha numeric value is entered");
               logger.info("Alpha numeric value is entered");
           }

        } else {
            getTest().log(LogStatus.FAIL, "Alpha numeric value is not entered");
            logger.info("Alpha numeric value is not entered");
            takeScreenshot("AlphaNumericValue");
        }
    }

    public void searchStatusInDropdownField() {
        WebElement dropdown = findElementClickable(By.id("ddlSearchStatus"), 10);
        if (dropdown.isEnabled()) {
            selectValueWithText(By.id("ddlSearchStatus"), searchStatus, "status", 10);
            getTest().log(LogStatus.PASS, "Status is selected in the dropdown");
            logger.info("Status is selected in the dropdown");
        } else {
            getTest().log(LogStatus.FAIL, "Status is not selected in the dropdown");
            logger.info("Status is not selected in the dropdown");
            takeScreenshot("StatusDropdown");
        }
    }

    public void searchPenaltyStatus() {
        WebElement penaltyStatus = findElementPresence(By.id("ddlPenaltyOnStatus"), 10);
        if (penaltyStatus.isEnabled()) {
            selectValueWithText(By.id("ddlPenaltyOnStatus"), "Yes", "Penalty status", 10);
            selectValueWithText(By.id("ddlPenaltyOnStatus"), "Select", "Penalty status", 10);
            getTest().log(LogStatus.PASS, "Penalty Status is selected in the dropdown");
            logger.info("PenaltyStatus is selected in the dropdown");
        } else {
            getTest().log(LogStatus.FAIL, "Penalty Status is not selected in the dropdown");
            logger.info("Penalty Status is not selected in the dropdown");
            takeScreenshot("PenaltyStatus");
        }
    }

    public void verifySearchButtonFunctionality() {
        WebElement searchButton = findElementClickable(By.id("btnSearch"), 10);
        if (searchButton != null) {
            searchButton.click();
            getTest().log(LogStatus.PASS, "Search button is clicked");
            logger.info("Search button is clicked");
            selectedResultIsDisplayed();
        } else {
            getTest().log(LogStatus.FAIL, "Search button is not clicked");
            logger.info("Search button is not clicked");
            takeScreenshot("SearchButton");
        }
    }

    public void selectedResultIsDisplayed() {
        String actualText = getText(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td[4]"), 20);
        if (actualText.equals(searchUniqueName)) {
            staticWait(3000);
            getTest().log(LogStatus.PASS, "Selected result is displayed");
            logger.info("Selected result is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Selected result is not displayed");
            logger.info("Selected result is not displayed");
            takeScreenshot("SearchResult");
        }
    }

    public void verifyClearButtonFunctionality() {
        WebElement clearButton = findElementClickable(By.xpath("//a/span[text()='Clear']"), 20);
        if (clearButton != null) {
            click(By.xpath("//a/span[text()='Clear']"),"Clear",20);
            getTest().log(LogStatus.PASS, "Clear button is clicked");
            logger.info("Clear button is clicked");
        } else {
            getTest().log(LogStatus.FAIL, "Clear button is not clicked");
            logger.info("Clear button is not clicked");
            takeScreenshot("ClearButton");
        }
    }

    public void verifyClearButtonDetails() {
        String actualText = getText(By.id("ddlSearchStatus"), 10);
        if (!actualText.equals("Select")) {
            getTest().log(LogStatus.PASS, "All details is cleared when click Clear button");
            logger.info("All details is cleared when click Clear button");
        } else {
            getTest().log(LogStatus.FAIL, "All details is not cleared when click Clear button");
            logger.info("All details is not cleared when click Clear button");
            takeScreenshot("ClearButton");
        }
    }

    public void verifyMultipleRowIsSelected() {
        int iteration = 0;
        click(By.xpath("//th[@id='nonresize']/div"), "Checkbox", 30);
        List<WebElement> checked = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td/div"), 20);
        if(checked.size()!=0) {
            for (WebElement ele : checked) {
                iteration++;
                String checkBox=findElementPresence(By.xpath("//table[@id='tblReturnRequest']/tbody/tr["+iteration+"]//input[contains(@class,'custom-control-input')]"),30).getAttribute("disabled");
                if (checkBox==null) {
                    String checkedAttribute = findElementPresence(By.xpath("//table[@id='tblReturnRequest']/tbody/tr[" + iteration + "]/td/div/input"),30).getAttribute("checked");
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
        click(By.xpath("//th[@id='nonresize']/div"), "Checkbox", 20);
    }

    public void verifyApproveAndRejectFunctionality() {
        clickApproveAndRejectButton("Approve");
        verifyValidationMessage("Please select at least one row to update Return Status.");
        clickApproveAndRejectButton("Reject");
        verifyValidationMessage("Please select at least one row to update Return Status.");
    }

    public void clickApproveAndRejectButton(String button) {
        click(By.xpath("//div[@id='ReturnRequestHeading']/span/a/span[text()='" + button + "']"), button, 20);

    }

    public void verifyValidationMessage(String message) {
        String errorMessage = findElementPresence(By.xpath("//div[contains(@class,'alert-dismissible')]/span"), 20).getText();
        Assert.assertEquals(errorMessage, message);
        click(By.xpath("//div[contains(@class,'alert-dismissible')]/button"), "Validation message Close", 20);
    }

    public void verifyApproveCancel() {
        selectTheRow();
        clickApproveAndRejectButton("Approve");
        verifyPopup("Approve");
        verifyCancelInPopup("Approve");
    }

    public void verifyApproveSave() {
        clickApproveAndRejectButton("Approve");
        verifyPopup("Approve");
        verifyAcceptAlphanumericValueInPopup();
        verifySaveInPopup();
        verifyProductStatus("Approve");
    }

    public void verifyRejectCancel() {
        selectTheRow();
        clickApproveAndRejectButton("Reject");
        verifyPopup("Reject");
        verifyCancelInPopup("Reject");
    }

    public void verifyRejectSave() {
        clickApproveAndRejectButton("Reject");
        verifyPopup("Reject");
        verifyAcceptAlphanumericValueInPopup();
        verifySaveInPopup();
        verifyProductStatus("Reject");
    }

    public void selectTheRow() {
        waitForLoad(30);
        click(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td/a/span[text()='Active']/../../../td/div"), "Select one row", 20);
        rowName = getText(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td/a/span[text()='Active']/../../../td[4]"), 20);
    }


    public void verifyPopup(String button) {
        WebElement approve = findElementVisibility(By.xpath("//div[@aria-describedby='divPop']//div[@class='modal-content']//h5[text()='"+button+"']"), 40);
        if (approve!=null) {
            getTest().log(LogStatus.PASS, "New " + button + " pop up is opened");
            logger.info("New " + button + " pop up is opened");
        } else {
            getTest().log(LogStatus.FAIL, "New " + button + " pop up is not opened");
            logger.info("New " + button + " pop up is not opened");
            takeScreenshot(button+"Popup");
        }
    }

    public void verifyCancelInPopup(String button) {
        WebElement cancel = findElementVisibility(By.xpath("//div[@class='modal-content']//div[@class='form-group']//a[text()='Cancel']"), 20);
        if (cancel != null) {
            cancel.click();
            getTest().log(LogStatus.PASS, "Cancel button is clicked");
            logger.info("Cancel button is clicked");
            WebElement approve = findElementsVisibility(By.xpath("//div[@class='modal-content']//h5[text()='"+button+"']"));
            if (approve == null) {
                getTest().log(LogStatus.PASS, "New "+button+" pop up is closed");
                logger.info("New "+button+" pop up is closed");
            } else {
                getTest().log(LogStatus.FAIL, "New "+button+" pop up is not closed");
                logger.info("New "+button+" pop up is not closed");
                takeScreenshot(button+"Popup");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Cancel button is not clicked");
            logger.info("Cancel button is not clicked");
            takeScreenshot("CancelButton");
        }
    }

    public void verifyAcceptAlphanumericValueInPopup() {
        WebElement commentField = findElementsVisibility(By.xpath("//div[@class='modal-content']//div[@class='form-group']//textarea[@id='ddlComment']"));
        if (commentField != null) {
            commentField.click();
            enter(By.xpath("//div[@class='modal-content']//div[@class='form-group']//textarea[@id='ddlComment']"), prop.getProperty("productNameAlphaNumeric"), "Comment", 10);
            String comment = getAtribute(By.xpath("//div[@class='modal-content']//div[@class='form-group']//textarea[@id='ddlComment']"), "value", 10);
            if (comment.equals("New123")) {
                getTest().log(LogStatus.PASS, "Alpha numeric value is entered");
                logger.info("Alpha numeric value is entered");
            } else {
                getTest().log(LogStatus.FAIL, "Alpha numeric value is not entered");
                logger.info("Alpha numeric value is not entered");
                takeScreenshot("AlphaNumericComment");
            }
        }

    }

    public void verifySaveInPopup() {
        click(By.xpath("//div[@class='modal-content']//div[@class='form-group']//a[text()='Save']"), "Save", 10);
    }

    public void verifyProductStatus(String status) {
        waitForLoader(30);
        String expected = (status.equalsIgnoreCase("Approve"))?"Approved":"Rejected";
        String text = getText(By.xpath("//table[@id='tblReturnRequest']/tbody/tr/td[text()='" + rowName + "']/../td[8]/a/span"), 10);
        if (text.equals(expected)) {
            getTest().log(LogStatus.PASS, "Product status is changed after save the "+status+" popup");
            logger.info("Product status is changed after save the "+status+" popup");
        } else {
            getTest().log(LogStatus.FAIL, "Product status is not changed after save the "+status+" popup");
            logger.info("Product status is not changed after save the "+status+" popup");
        }
    }


    public void paginationOnReturnRequestTab() {
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
        scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
        defaultPaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            String[] secondPaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 30).split(" ");

            int secondPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            int secondPageEndRecordCount = Integer.parseInt(secondPaginationText[3]);
            int pageSize = Integer.parseInt(getText(By.xpath("//div[@id='divRequestionItemsData']//select[@id='pageSize']/option[1]"), 40));
            staticWait(1000);
            if (secondPageStartRecordCount == defaultEndCount + 1 && secondPageEndRecordCount <= 2 * pageSize) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed when click on the \"2\" pagination button");
                logger.info("Second page is not displayed when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 40);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            secondPaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            staticWait(3000);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"1\" pagination button");
                logger.info("First page is displayed as expected when click on the \"1\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed when click on the \"1\" pagination button");
                logger.info("First page is not displayed when click on the \"1\" pagination button");
                takeScreenshot("Pagination1");
            }

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 40);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            nextPaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 40);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            previousPaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 40);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            lastPagePaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int lastPageEndCount = Integer.parseInt(lastPagePaginationText[3]);
            if (lastPageEndCount == totalRecordCount) {
                getTest().log(LogStatus.PASS, "Last page is displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is displayed as expected when click on the \"Last\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Last page is not displayed when click on the \"Last\" pagination button");
                logger.info("Last page is not displayed when click on the \"Last\" pagination button");
                takeScreenshot("PaginationLast");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link  first' and text()='First ']"), 40);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + recordCount + "]//td[15]"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(30);
            waitForElementInVisibility(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            scrollToWebelement(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"),"Pagination");
            firstPagePaginationText = getText(By.xpath("//div[@id='divRequestionItemsData']//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
}
