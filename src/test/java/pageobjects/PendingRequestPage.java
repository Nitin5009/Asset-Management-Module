package pageobjects;

import Action.ProductTypeAction;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class PendingRequestPage extends WebBasePage {

    DeployProductPage deployProduct;
    ProductTransferPage productTransferPage;
    ProductTypeAction productTypeAction;
    ProductListingPage productListing;
    MyProductPage myProductPage;
    WebDriver driver;
    String pattern = "M/d/YYYY";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime now = LocalDateTime.now();
    String inputDateArray = dtf.format(now.plusDays(2));

    public PendingRequestPage(WebDriver driver) {
        super(driver, "Pending Request page");
        this.deployProduct = new DeployProductPage(driver);
        this.productTransferPage = new ProductTransferPage(driver);
        this.productTypeAction = new ProductTypeAction(driver);
        this.productListing = new ProductListingPage(driver);
        this.myProductPage = new MyProductPage(driver);
        this.driver = driver;
    }

    public void clickPendingRequestMenu() {
        WebElement pendingReqMenu = findElementPresence(By.xpath("//a[text()='Pending Requests']"), 20);
        if (pendingReqMenu != null) {
            scrollToWebelement(By.xpath("//a[text()='Pending Requests']"), "Pending Request menu");
            click(By.xpath("//a[text()='Pending Requests']"), "Pending Request menu", 20);
        } else {
            deployProduct.clickFullMenuDropDown();
            deployProduct.clickAssetManagement();
            clickPendingRequestMenu();
        }
    }

    public void sideBarReplaceRequest() {
        click(By.cssSelector("a#aReplaceRequest"), "Replace Request menu", 20);
    }

    public void sideBarPendingCheckOutRequest() {
        click(By.cssSelector("a#aPendingRequest"), "Pending checkout Request menu", 20);
    }

    public void sideBarReturnRequest() {
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 20);
        click(By.cssSelector("a#aReturnRequest"), "Return Request menu", 20);
    }

    public void verifyPendingRequestPage() {
        String actualText = getText(By.xpath("//span[@class='p-name text-white' and text()='Pending Request']"), 20);
        if (actualText.equals("Pending Request")) {
            getTest().log(LogStatus.PASS, "Pending request Page is displayed when click Pending request sub menu");
            logger.info("Pending request Page is displayed when click Pending request sub menu");
        } else {
            getTest().log(LogStatus.FAIL, "Pending request Page is not displayed when click Pending request sub menu");
            logger.info("Pending request Page is not displayed when click Pending request sub menu");
            takeScreenshot("Pending request");
        }
    }

    public void clickPendingCheckOutRequestTab() {
        WebElement pendingCheckOutRequest = findElementVisibility(By.xpath("//a[@id='aPendingRequest' and normalize-space(text())='Pending Check Out Request']"), 20);
        if (pendingCheckOutRequest != null) {
            sideBarPendingCheckOutRequest();
            verifyPendingCheckOutRequestTab();
        } else {
            clickPendingRequestMenu();
            clickPendingCheckOutRequestTab();
        }
    }

    public void verifyPendingCheckOutRequestTab() {
        String actualText = getText(By.xpath("//div[@class='theme-primary partition-full']/span[text()='Pending Check Out Request']"), 20);
        if (actualText.equals("Pending Check Out Request")) {
            getTest().log(LogStatus.PASS, "Pending check out request Page is displayed when click Pending check out request sub menu");
            logger.info("Pending check out request Page is displayed when click Pending check out request sub menu");
        } else {
            getTest().log(LogStatus.FAIL, "Pending check out request Page is not displayed when click Pending check out request sub menu");
            logger.info("Pending check out request Page is not displayed when click Pending check out request sub menu");
            takeScreenshot("Pending check out request");
        }
    }

    public void VerifyBreadCrumbInProductRequestPage() {
        WebElement element = findElementsVisibility(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']"));
        if (element.isDisplayed()) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed");
            logger.info("BreadCrumb is displayed");
            clickHomeFromBreadCrumb();
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed");
            logger.info("BreadCrumb is not displayed");
            takeScreenshot("BreadCrumb");
        }
    }

    public void clickHomeFromBreadCrumb() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-250)");
        click(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']//li//a[text()='Home']"), "Home from breadcrumb", 20);
        verifyHomePage();
    }

    public void verifyHomePage() {
        String actualText = getText(By.xpath("//div[contains(@class,'heme-primary partition')]/span[@class='p-name text-white p-0' and normalize-space(text()='Dashboard')]"), 20).trim();
        if (actualText.equals("Dashboard")) {
            getTest().log(LogStatus.PASS, "Home Page is displayed when clicked BreadCrumb");
            logger.info("Home Page is displayed when clicked BreadCrumb");
        } else {
            getTest().log(LogStatus.FAIL, "Home Page is not displayed when clicked BreadCrumb");
            logger.info("HomePage is not displayed when clicked BreadCrumb");
            takeScreenshot("BreadCrumb is not clicked");
        }
    }

    public void verifyProductNameFieldClickable() {
        WebElement productName = findElementClickable(By.id(("catalogId")), 10);
        if (productName.isEnabled()) {
            //productName.click();
            getTest().log(LogStatus.PASS, "Product name field is clickable");
            logger.info("Product name field is clickable");
            selectProductNameDropDown();
        } else {
            getTest().log(LogStatus.FAIL, "Product name field is not clickable");
            logger.info("Product name field is not clickable");
        }
    }

    public void countOfPendingRequestRow() {
        staticWait(2000);
        List<WebElement> row = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td[6]//i[contains(@class,'text-success')]"), 10);
        int countOfRow = row.size();
        if (countOfRow == 0) {
            createThePendingRequest();
        } else {
            String textInTable = findMultipleElement((By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[1]/span")), 10).get(0).getText();
            uniqueName = textInTable.substring(textInTable.indexOf("[") + 1, textInTable.indexOf("]"));
            productName = uniqueName.substring(0, uniqueName.indexOf('_'));
        }
    }

    public void createThePendingRequest() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("Consumable");
        myProductPage.createNewProduct(false, "barcode", productTypeName, "1", "");
        productLocationToAssign = productLocationsToAssign.get(0);
        productTypeToAssign = productTypesToAssign;
        productNameToAssign = productNamesToAssign.get(0);
        productToAssign = productsToAssign.get(0);
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        myProductPage.openMyProductPage();
        addCheckOutRequest();
        productListing.selectAssignProductLocation();
        productListing.selectAssignProductType();
        productListing.selectAssignProductName();
        productListing.selectAssignProduct();
        productListing.searchAssignProduct();
        productListing.addAssignProductToList();
        saveTheProducts();
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        clickPendingRequestMenu();
    }

    public void addCheckOutRequest() {
        click(By.id("ancaddgroupRequisitions"), "Add check out request", 20);
    }

    public void selectDate() {
        enter(By.id("AssignedTill"), inputDateArray, "End date", 10);
    }

    public void addIcon() {
        try {
            click(By.xpath("//label[text()='I agree to the Terms and Conditions']"), "Terms and conditions", 10);
            click(By.xpath("//td[@id='lastrow1']/a"), "icon", 10);
        } catch (Exception e) {
            click(By.xpath("//td[@id='lastrow1']/a"), "icon", 10);
        }

    }

    public void saveTheProducts() {

        click(By.id("ancsaverequest"), "Save", 10);
    }

    public void selectProductNameDropDown() {
        try {
            selectValueWithText(By.id("catalogId"), productName, "Product Name", 10);
            getTest().log(LogStatus.PASS, "Select the product name in the dropdown menu");
            logger.info("Select the product name in the dropdown menu");
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Not select the product name in the dropdown menu");
            logger.info("Not select the product name in the dropdown menu");
        }
    }

    public void verifyUniqueNameFieldClickable() {
        WebElement uniqueName = findElementClickable(By.xpath(("//span[text()='Select Unique Name']")), 15);
        if (uniqueName.isEnabled()) {
            uniqueName.click();
            getTest().log(LogStatus.PASS, "Unique name field is clickable");
            logger.info("Unique name field is clickable");
            selectUniqueName();
        } else {
            getTest().log(LogStatus.FAIL, "Unique name field is not clickable");
            logger.info("Unique name field is not clickable");
        }
    }

    public void selectUniqueName() {
        try {
            enter(By.xpath("//input[contains(@class,'multiselect-search')]"), uniqueName, "Unique Name", 20);
            click(By.xpath("//label[normalize-space(text())='" + uniqueName + "']"), "Unique Name", 10);
            getTest().log(LogStatus.PASS, "Unique name is selected");
            logger.info("Unique name is selected");
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Unique name is not selected");
            logger.info("Unique name is not selected");
        }
    }


    public void verifySearchButtonFunctionality() {
        click(By.id("Go"), "Search", 10);
        verifySelectedDataIsPresent();
    }

    public void verifySelectedDataIsPresent() {
        waitForLoad(30);
        String actualUniqueName = getText((By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td/span")), 10);
        if (actualUniqueName.contains(uniqueName)) {
            getTest().log(LogStatus.PASS, "Accurate result is displayed when search button is clicked");
            logger.info("Accurate result is displayed when search button is clicked");
        } else {
            getTest().log(LogStatus.FAIL, "Accurate result is not displayed when search button is clicked");
            logger.info("Accurate result is not displayed when search button is clicked");
            takeScreenshot("UniqueNameSearch");
        }
    }

    public void verifyTheClearButtonFunctionality() {
        scrollUpDown("up");
        staticWait(2000);
        click(By.xpath("//a[@title='Clear' and text()='Clear']"), "Clear", 10);
        verifySelectedDetailsClear();
    }

    public void verifySelectedDetailsClear() {
        String productName = getText((By.xpath("//select[@id='catalogId']/option")), 10);
        String uniqueName = getText(By.xpath("//span[text()='Select Unique Name']"), 10);
        if ((productName.equals("Select Product Name")) && (uniqueName.equals("Select Unique Name"))) {
            getTest().log(LogStatus.PASS, "All details is cleared");
            logger.info("All details is cleared");
        } else {
            getTest().log(LogStatus.FAIL, "All details is not cleared");
            logger.info("All details is not cleared");
            takeScreenshot("ClearSearch");
        }
    }

    public void verifyAscendingDescendingOfUniqueName() {
        sortingTheProductName("ascending");
        sortingTheProductName("descending");
    }

    public void verifyAscendingDescendingOfFromTime() {
        sortingTheFromTime("ascending");
        sortingTheFromTime("descending");
    }

    public void verifyAscendingDescendingOfToTime() {
        sortingTheToTime("ascending");
        sortingTheToTime("descending");
    }

    public void sortingTheProductName(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 40);
        scrollToWebelement((By.xpath("//span[text()='Pending Check Out Request']")), "Pending check out request");
        waitForLoad(20);
        click(By.xpath("//span[@id='ASSETGROUP' and normalize-space(text()='Unique Name')]"), "Unique Name", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[1]/span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[1]/span"), 30);
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
                getTest().log(LogStatus.PASS, "Unique name's are sorted in ascending Order ");
                logger.info("Unique name's are sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Unique name's are not sorted in ascending Order");
                logger.info("Unique name's are not sorted in ascending Order ");
                takeScreenshot("UniqueNameSorting");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Unique name's are sorted in descending Order");
                logger.info("Unique name's are sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Unique are not sorted in descending Order ");
                logger.info("Unique name's are not sorted in descending Order");
                takeScreenshot("UniqueNameSorting");
            }
        }
    }

    public void sortingTheFromTime(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        scrollToWebelement(By.xpath("//span[text()='Pending Check Out Request']"), "Pending check out request");
        click(By.xpath("//span[@id='FROMTIME' and normalize-space(text()='From Time')]"), "From time", 20);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[2]/span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[2]/span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = actualSortedArray.stream().sorted().collect(Collectors.toList());
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "From time is sorted in ascending Order ");
                logger.info("Status are sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, "From time is not sorted in ascending Order");
                logger.info("From time is not sorted in ascending Order ");
                takeScreenshot("FromTimeSorting");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = actualSortedArray.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "From time is sorted in descending Order");
                logger.info("From time is sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, "From time is not sorted in descending Order ");
                logger.info("From time is not sorted in descending Order");
                takeScreenshot("FromTimeSorting");
            }
        }

    }

    public void sortingTheToTime(String order) {
        List<String> actualSortedArray = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        scrollToWebelement((By.xpath("//span[text()='Pending Check Out Request']")), "pending check out request");
        click(By.xpath("//span[@id='TOTIME' and normalize-space(text()='To Time')]"), "To time", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[3]/span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[3]/span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = actualSortedArray.stream().sorted().collect(Collectors.toList());
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "To time is sorted in ascending Order ");
                logger.info("To time is sorted in ascending Order");
            } else {
                getTest().log(LogStatus.FAIL, "To time is not sorted in ascending Order");
                logger.info("To time is not sorted in ascending Order ");
                takeScreenshot("ToTimeSorting");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = actualSortedArray.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "To time is sorted in descending Order");
                logger.info("To time is sorted in descending Order");
            } else {
                getTest().log(LogStatus.FAIL, "Status are not sorted in descending Order ");
                logger.info("To time is not sorted in descending Order");
                takeScreenshot("ToTimeSorting");
            }
        }
    }

    public void paginationOnPendingRequestTab() {
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
        defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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

    public void verifyAssignIconIsClick() {
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        WebElement assign = findElementVisibility((By.xpath("//table[@id='tblRelatedInfoListing']//tr[1]//td[6]//i")), 20);
        if (assign.getAttribute("class").contains("text-success")) {
            //scrollDown();
            click(By.xpath("//table[@id='tblRelatedInfoListing']//tr[1]//td[6]//i"), "Assign", 20);
            getTest().log(LogStatus.PASS, "Assign icon is clickable");
            logger.info("Assign icon is clickable");
            verifyProductAssignmentPage();
        } else {
            getTest().log(LogStatus.FAIL, "Assign icon is not clickable");
            logger.info("Assign icon is not clickable");
        }
    }

    public void verifyProductAssignmentPage() {
        String productAssignPage = getText(By.xpath("//div[@class='theme-primary partition-full']/span"), 10);
        if (productAssignPage.equals("Product Assignment")) {
            getTest().log(LogStatus.PASS, "Product Assignment page is displayed");
            logger.info("Product Assignment page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Product Assignment page is not displayed");
            logger.info("Product Assignment page is not displayed");
        }
    }

    public void verifyFunctionalityOfAssignButton() {
        enterTheDetailsInProductAssignScreen();
        verifyProductScreen();

    }

    public void enterTheDetailsInProductAssignScreen() {
        enter(By.xpath("//td[@class='text-center']/input[@type='text' and @class='txtitems form-control']"), "1", "Quantity to be assigned", 10);
        click(By.id("btnSave"), "Save", 10);
    }

    public void verifyProductScreen() {
        WebElement element = findElementVisibility(By.xpath("//span[text()='Pending Check Out Request']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Pending Request screen is displayed");
            logger.info("Pending Request screen is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Pending Request screen is displayed");
            logger.info("Pending Request screen is displayed");
            takeScreenshot("MyProductRequestScreen");
        }
    }

    public void verifyRejectIconIsClick() {
        try {
            //scrollDown();
            findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
            click(By.xpath("//table[@id='tblRelatedInfoListing']//tr[1]//td[7]//i"), "Reject", 10);
            getTest().log(LogStatus.PASS, "Reject icon is clickable");
            logger.info("Reject icon is clickable");
            verifyPopUpMessage();
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Reject icon is not clickable");
            logger.info("Reject icon is not clickable");
            takeScreenshot("RejectIconNotClickable");
        }
    }

    public void verifyPopUpMessage() {
        alertAccept("Are you sure want to reject requisition.");
    }

    public void rejectReason() {
        enter(By.id("RequsitionRejectComment"), "Reason", "Reject reason", 10);
        click(By.id("btnSaveReqReject"), "Save", 10);
    }

    public void verifyCreatedReplaceRequest(String expectedProductUniqueName) {
        List<String> actualNamesList = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr/td[3]"), 30);
        for (WebElement element : elements) {
            actualNamesList.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='divRequestionItemsData']//tbody//tr/td[3]"), 30);
                for (WebElement element : elements) {
                    actualNamesList.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }

        if (actualNamesList.contains(expectedProductUniqueName)) {
            getTest().log(LogStatus.PASS, "Created Replace request " + expectedProductUniqueName + " is displayed in the Replace Request page");
            logger.info("Created Replace request " + expectedProductUniqueName + " is displayed in the Replace Request page");
        } else {
            getTest().log(LogStatus.FAIL, "Created Replace request " + expectedProductUniqueName + " is not displayed in the Replace Request page");
            logger.info("Created Replace request " + expectedProductUniqueName + " is not displayed in the Replace Request page");
            takeScreenshot("ReplaceRequestNotDisplayed");
        }
    }

    public void verifyProductQuantityReq(String productName) {
        List<String> availableCheckOutRequest = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td[1]/span"), 30);
        for (WebElement element : elements) {
            availableCheckOutRequest.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td[1]/span"), 30);
                for (WebElement element : elements) {
                    availableCheckOutRequest.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }

        if (availableCheckOutRequest.contains(productName)) {
            getTest().log(LogStatus.PASS, "The requested product " + productName + " name is displayed in the pending check out list page once the quantity request is saved");
            logger.info("The requested product " + productName + " name is displayed in the pending check out list page once the quantity request is saved");
        } else {
            getTest().log(LogStatus.FAIL, "The requested product " + productName + " name is not displayed in the pending check out list page once the quantity request is saved");
            logger.info("The requested product " + productName + " name is not displayed in the pending check out list page once the quantity request is saved");
            takeScreenshot("ProductInTable");
        }
    }

    public void verifyCreatedReturnReq(String returnRequestedName) {
        List<String> availableCheckOutRequest = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td[4]"), 30);
        for (WebElement element : elements) {
            availableCheckOutRequest.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr//td[4]"), 30);
                for (WebElement element : elements) {
                    availableCheckOutRequest.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }

        if (availableCheckOutRequest.contains(returnRequestedName)) {
            getTest().log(LogStatus.PASS, "The requested product " + returnRequestedName + " name is displayed in the return request list page once the return request is saved");
            logger.info("The requested product " + returnRequestedName + " name is displayed in the return request list page once the return request is saved");
        } else {
            getTest().log(LogStatus.FAIL, "The requested product " + returnRequestedName + " name is not displayed in the return request list page once the return request is saved");
            logger.info("The requested product " + returnRequestedName + " name is not displayed in the return request list page once the return request is saved");
            takeScreenshot("ReturnReq");
        }
    }

    public void getPendingProductCount(String userType) {
        String returnedStatus = "";
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        String monthNumber = sdf.format(new Date());
        int previousMonth = Integer.parseInt(monthNumber) - 1;
        staticWait(3000);
        selectValueWithValue(By.xpath("//div[@id='divRequestionItemsData']//select[@id='pageSize']"), "100", "Page Size", 15);
        waitForLoad(20);
        scrollUpDown("up");
        Object paginationNext = "";
        First:
        while (paginationNext != null) {
            List<WebElement> pendingRequestProductList = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 20);
            for (int i = 1; i <= pendingRequestProductList.size(); i++) {
                String returnedDate = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td[15]"), 5);
                if (returnedDate.startsWith(monthNumber)) {
                    returnedStatus = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td/a/span"), 5, 60);
                    if (returnedStatus.equalsIgnoreCase("Active")) {
                        String productName = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td[4]"), 20);
                        if (userType.equalsIgnoreCase("normalUser")) {
                            productsAcceptancePendingByMangerNU.add(productName);
                        } else if (userType.equalsIgnoreCase("ProjectManager")) {
                            productsAcceptancePendingByMangerPM.add(productName);
                        } else {
                            productsAcceptancePendingByMangerCA.add(productName);
                        }
                    }
                } else if (returnedDate.startsWith(String.valueOf(previousMonth))) {
                    break First;
                }
            }
            paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 20);
            if (paginationNext != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
            }
        }
    }

    public void getPendingProductCountForNU() {
        String returnedStatus = "";
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        String monthNumber = sdf.format(new Date());
        int previousMonth = Integer.parseInt(monthNumber) - 1;
        staticWait(3000);
        selectValueWithValue(By.xpath("//div[@id='divRequestionItemsData']//select[@id='pageSize']"), "100", "Page Size", 15);
        waitForLoad(20);
        scrollUpDown("up");
        Object paginationNext = "";
        First:
        while (paginationNext != null) {
            List<WebElement> pendingRequestProductList = findMultipleElement(By.xpath("//table[@id='tblReturnRequest']//tbody//tr"), 20);
            for (int i = 1; i <= pendingRequestProductList.size(); i++) {
                String returnedDate = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td[15]"), 5);
                if (returnedDate.startsWith(monthNumber)) {
                    returnedStatus = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td/a/span"), 5);
                    if (returnedStatus.equalsIgnoreCase("Active")) {
                        String productName = getText(By.xpath("//table[@id='tblReturnRequest']//tbody//tr[" + i + "]//td[4]"), 20);
                        productsAcceptancePendingByMangerNU.add(productName);
                    }
                } else if (returnedDate.startsWith(String.valueOf(previousMonth))) {
                    break First;
                }
            }
            paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 20);
            if (paginationNext != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
            }
        }
    }

    public void clickCheckBoxOfFirstReturnReqProd(String productName) {
        click(By.xpath("//td[text()='" + productName + "']//parent::tr//td[1]/div"), productName + " check box", 20);
    }

    public void clickApproveButton() {
        click(By.xpath("//a[@id='ancApprove']"), "Approve", 20);
    }

    public void clickSaveButtonInApprovePopup() {
        click(By.xpath("//h5[text()='Approve']//ancestor::div//a[@id='btnUpdateReturnStatus']"), "Approve Save", 20);
    }

    public void AcceptTheReturnRequest(String userType) {
        waitForLoader(120);
        staticWait(3000);
        if (userType.equalsIgnoreCase("CompanyAdmin")) {
            clickCheckBoxOfFirstReturnReqProd(productsAcceptancePendingByMangerCA.get(0));
        } else if (userType.equalsIgnoreCase("ProjectManager")) {
            clickCheckBoxOfFirstReturnReqProd(productsAcceptancePendingByMangerPM.get(0));
        } else {
            clickCheckBoxOfFirstReturnReqProd(productsAcceptancePendingByMangerNU.get(0));
        }
        staticWait(2000);
        clickApproveButton();
        clickSaveButtonInApprovePopup();
    }
}
