package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import utils.Drivers;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static reporting.ComplexReportFactory.getTest;
import static utils.Errors.productImportConfirmation;
import static utils.StaticData.*;

public class ProductListingPage extends WebBasePage {

    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String testFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
    List<String> productsToDelete = new ArrayList<>();
    String pattern = "yyyyMMddHHmm";
    String patternTwo = "ddHHmmss";
    List<String> importedPNames = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    DeployProductPage deployProductPage;

    public ProductListingPage(WebDriver driver) {
        super(driver, "Product Listing page");
        this.driver = driver;
        this.deployProductPage = new DeployProductPage(driver);
    }

    public void verifyHeaders() {
        List<WebElement> headerElement = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tr//th//span"), 30);
        String actualText;
        int iteration = 0;
        for (WebElement ele : headerElement) {
            actualText = ele.getText().trim();
            String[] expectedHeaders = {"Image", "Product Type Name", "Product Code", "Product Name", "Status", "Total Number of Quantity", "Available Quantity", "Actions"};
            for (String expectedHeader : expectedHeaders) {
                iteration++;
                if (actualText.equals(expectedHeader)) {
                    getTest().log(LogStatus.PASS, "The expected header name \"" + actualText + "\" is displayed in the table");
                    logger.info("The expected header name \"" + actualText + "\" is displayed in the table");
                    iteration = 0;
                    break;
                } else if (iteration == expectedHeaders.length) {
                    getTest().log(LogStatus.FAIL, "The expected header name \"" + actualText + "\" is not displayed");
                    logger.info("The expected header name \"" + actualText + "\" is not displayed");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void createNewProduct() {
        AddProductPage addProductPage = new AddProductPage(driver);
        DeployProductPage deployProductPage = new DeployProductPage(driver);
        addProductPage.clickAddNewButton();
        addProductPage.selectProductType();
        Select select = new Select(driver.findElement(By.cssSelector("div>select#AssetTypeId")));
        WebElement option = select.getFirstSelectedOption();
        productTypeToAssign = option.getText();
        addProductPage.enterItemName();
        productNameToAssign = getAtribute(By.cssSelector("div>input#Name"), "value", 20);
        addProductPage.selectBarCodeType();
        addProductPage.clickTheCheckBox();
        addProductPage.clickNextButton();
        staticWait(5000);
        deployProductPage.clickAddDeployButton();
        deployProductPage.clickLocationDropdown();
        deployProductPage.selectLocationValueFromDropdown("");
        productLocationToAssign = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30);
        deployProductPage.enterQuantity("1");
        deployProductPage.enterUnitPrice("1");
        deployProductPage.enterProductCost("200");
        deployProductPage.clickAddListButton();
        deployProductPage.clickSaveButton();
        deployProductPage.handleSuccessPopup();
        deployProductPage.clickNextButton();
        staticWait(500);
        productToAssign = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td//a[@class='editinfo']"), 20).trim();
    }

    public void navigateToAttachmentPage() {
        click(By.xpath("//ul[@id='myTab']//a[text()=' Attachments ']"), "Attachments Tab", 20);
    }

    public void navigateToProductAssignmentPage() {
        click(By.xpath("//div[@class='menu-box']//a[normalize-space(text())='Product Assignment']"), "Product Assignment Menu", 20);
    }

    public void clickAddProductToAssign() {
        click(By.xpath("//a[@id='ancCreateJob']"), "Add Product to Assign", 20);
    }

    public void selectUser() {
        selectValueWithIndex(By.cssSelector("div>select#IssuedTo"), 1, "User", 20);
    }

    public void selectAssignProductLocation() {
        scrollToWebelement(By.xpath("//div[@data-toggle='dropdown']"), "Location Dropdown");
        click(By.xpath("//div[@data-toggle='dropdown']"), "Location Dropdown", 20);
        click(By.xpath("//a[@data-text='" + productLocationToAssign + "']"), "Location Value", 25);
    }

    public void selectAssignProductType() {
        selectValueWithText(By.cssSelector("div>select#AssetTypeFilter"), productTypeToAssign, "Product Type", 20);
    }

    public void selectAssignProductName() {
        scrollToWebelement(By.cssSelector("div>select#AssetCatalogFilter"), "Product Name");
        findElementPresence(By.xpath("//select[@id='AssetCatalogFilter']//option[text()='" + productNameToAssign + "']"), 40);
        selectValueWithText(By.cssSelector("div>select#AssetCatalogFilter"), productNameToAssign, "Product Name", 20);
    }

    public void selectAssignProduct() {
        String[] ch = productToAssign.split("");
        enter(By.cssSelector("input#txtAssetItems"), ch[0], "Products", 20);
        click(By.xpath("//div[@class='unique_dynamicdatalist']//li[text()='" + productToAssign + "']"), "Product", 20);
    }

    public void searchAssignProduct() {
        click(By.cssSelector("a#Searchassest"), "Search Button", 20);
    }

    public void clickTermsAndCondition() {
        WebElement termsAndCondition = findElementVisibility(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr[1]//td[6]//span[text()='N/A']"), 20);
        if (termsAndCondition == null) {
            click(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr[1]//td[6]//div"), "Terms and Condition", 20);
        }
    }

    public void addAssignProductToList() {
        scrollToWebelement(By.cssSelector("td#lastrow1>a.ancaddtodata"), "Add Assign Button");
        click(By.cssSelector("td#lastrow1>a.ancaddtodata"), "Add To Item", 20);
        waitForLoader(20);
    }

    public void saveAssignProduct() {
        click(By.cssSelector("a#btnSave"), "Save Button", 20);
    }

    public void navigateToManageProductPage() {
        DeployProductPage deployProductPage = new DeployProductPage(driver);
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        deployProductPage.clickManageProduct();
    }

    public void disabledCheckBox() {
        WebElement element = findElementPresence(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td[1]//input[@disabled]"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Check box of the assigned product is not clickable as expected");
            logger.info("Check box of the assigned product is not clickable as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Check box of the assigned product is displayed as clickable");
            logger.info("Check box of the assigned product is displayed as clickable");
            takeScreenshot("CheckBox");
        }
    }

    public void enabledCheckBox() {
        WebElement element = findElementPresence(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td[1]//input[@disabled]"), 10);
        if (element == null) {
            getTest().log(LogStatus.PASS, "Check box of the assigned product is displayed clickable as expected");
            logger.info("Check box of the assigned product is displayed clickable as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Check box of the assigned product is not displayed as clickable");
            logger.info("Check box of the assigned product is not displayed as clickable");
            takeScreenshot("CheckBox");
        }
    }

    public void openAssignProduct() {
        click(By.xpath("//table[@id='example']//tbody//tr//td[normalize-space(text())='" + productToAssign + "']"), "Product", 20);
    }

    public void selectCheckBox() {
        click(By.xpath("//table[@id='example']//td[normalize-space(text())='" + productNameToAssign + "']/../td//div"), "Check Box", 20);
    }

    public void clickReturnProduct() {
        click(By.xpath("//a[text()='Return Product']"), "Return Product", 20);
    }

    public void saveReturnProduct() {
        click(By.cssSelector("#btnBulkReturnItem"), "Save", 30);
    }

    public void handleProductReturnSuccess() {
        findElementVisibility(By.xpath("//div[@id='notifymessage']//div/span[text()='Item returned successfully.']"), 60);
        click(By.xpath("//div[@id='notifymessage']//div/button"), "Success Message Close", 20);
    }

    public void clickCheckBox() {
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//input[@name='DeleteInputs']//parent::div"), "Delete Check Box", 20);
    }

    public void verifyDeleteButton() {
        String deleteButton = getAtribute(By.cssSelector("#DeleteMultiple"), "class", 30);
        if (!deleteButton.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The Delete button is enabled when select the check box");
            logger.info("The Delete button is enabled when select the check box");
        } else {
            getTest().log(LogStatus.FAIL, "The Delete button is not enabled when select the check box");
            logger.info("The Delete button is not enabled when select the check box");
            takeScreenshot("DeleteButton");
        }
    }

    public void verifyGreenStripe() {
        String script = "return window.getComputedStyle(document.querySelector('tr:nth-child(2)>td.text-center:nth-child(1)'),':after').getPropertyValue('background-color')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String content = (String) js.executeScript(script);
        if (content.equals("rgb(1, 151, 4)")) {
            getTest().log(LogStatus.PASS, "Green color stripe is displayed as expected when create product with unique name");
            logger.info("Green color stripe is displayed as expected when create product with unique name");
        } else {
            getTest().log(LogStatus.FAIL, "Green color stripe is not displayed as expected when create product with unique name");
            logger.info("Green color stripe is not displayed as expected when create product with unique name");
            takeScreenshot("GreenStrip");
        }
    }

    public void paginationOnProductList() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[5]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[5]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[5]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + recordCount + "]//td[5]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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

    public void bulkDeleteFunctionality() {
        int recordCheckBox = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//input[@name='DeleteInputs']//parent::div"), 30).size();
        if (recordCheckBox != 0) {
            selectBulkRecordToDelete();
            clickDeleteIcon();
            confirmationOfDelete();
            handleSuccessPopup();
            verifyDeletedProducts();
        } else {
            getTest().log(LogStatus.PASS, "There is no enough data to perform bulk delete function");
            logger.info("There is no enough data to perform bulk delete function");
        }
    }

    public void selectBulkRecordToDelete() {
        List<WebElement> records = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//input[@name='DeleteInputs']//parent::div"), 30);
        int iteration = 1;
        for (WebElement ele : records) {
            if (ele.isEnabled()) {
                iteration++;
                ele.click();
                String productNameToDelete = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + iteration + "]/td//a[@id='ancEditAssetType']//span"), 30).trim();
                productsToDelete.add(productNameToDelete);
                if (productsToDelete.size() == 2) {
                    break;
                }
            }
        }
    }

    public void clickDeleteIcon() {
        click(By.cssSelector("#DeleteMultiple"), "Delete Icon", 20);
    }

    public void confirmationOfDelete() {
        findElementClickable(By.xpath("//div[contains(@class,'modal-confirm-footer')]/button[contains(@class,'success')]"), 30);
        click(By.xpath("//div[contains(@class,'modal-confirm-footer')]/button[contains(@class,'success')]"), "Confirm OK", 40);
        waitForLoader(20);
    }

    public void verifyDeletedProducts() {
        for (String str : productsToDelete) {
            List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr/td//a[@id='ancEditAssetType']//span"), 30);
            int iteration = 0;
            for (WebElement element : elements) {
                iteration++;
                if (!str.equals(element.getText().trim()) && elements.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Deleted Product - " + str + " is not displayed in the list as expected");
                    logger.info("Deleted Product - " + str + " is not displayed in the list as expected");
                } else if (elements.size() == iteration) {
                    getTest().log(LogStatus.FAIL, "Deleted Product - " + str + " is not removed from the list");
                    logger.info("Deleted Product - " + str + " is not removed from the list");
                    takeScreenshot("DeletedProduct");
                }
            }
        }
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

    public void navigateToEditPage() {
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]/td//a[@id='ancEditAssetType']//span"), "Product Name", 30);
    }

    public void verifyProductEditPage() {
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("Update")) {
            getTest().log(LogStatus.PASS, "The Product edit page is displayed when click on the product name");
            logger.info("The Product edit page is displayed when click on the product name");
        } else {
            getTest().log(LogStatus.FAIL, "The Product page is not displayed when click on the product name");
            logger.info("The Product edit page is not displayed when click on the product name");
            takeScreenshot("ProductEditPage");
        }
    }

    public void verifyImgNotAvail() {
        String imageSrc = getAtribute(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td//img"), "src", 20);
        if (imageSrc.endsWith("imagenotavailable.jpg")) {
            getTest().log(LogStatus.PASS, "\"Image not Available\" is displayed as expected when product created without uploading image");
            logger.info("\"Image not Available\" is displayed as expected when product created without uploading image");
        } else {
            getTest().log(LogStatus.FAIL, "\"Image not Available\" is not displayed when product created without uploading image");
            logger.info("\"Image not Available\" is not displayed when product created without uploading image");
            takeScreenshot("ImgNotAvailable");
        }
    }

    public void productTypeNameAscending() {
        verifyProductTypeNameOrder("ascending");
    }

    public void productTypeNameDescending() {
        verifyProductTypeNameOrder("descending");
    }

    public void verifyProductTypeNameOrder(String order) {
        click(By.cssSelector("#ASSETTYPE"), "Product Type Name", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[3]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[3]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Type Names are sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Type Names are sorted in ascending Order when click the Product type header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Product Type Names are not sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Type Names are not sorted in ascending Order when click the Product type header for one time");
                takeScreenshot("ProductTypeSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Type Names are sorted in descending Order when click the Product type header for two times");
                logger.info("Product Type Names are sorted in descending Order when click the Product type header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Product Type Names are not sorted in descending Order when click the Product type header for two times");
                logger.info("Product Type Names are not sorted in descending Order when click the Product type header for two times");
                takeScreenshot("ProductTypeSort");
            }
        }
    }

    public void productNameAscending() {
        verifyProductNameOrder("ascending");
    }

    public void productNameDescending() {
        verifyProductNameOrder("descending");
    }

    public void verifyProductNameOrder(String order) {
        click(By.cssSelector("#NAME"), "Product Type Name", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[5]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[5]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Names are sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Names are sorted in ascending Order when click the Product type header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Product Names are not sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Names are not sorted in ascending Order when click the Product type header for one time");
                takeScreenshot("ProductNameSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Names are sorted in descending Order when click the Product type header for two times");
                logger.info("Product Names are sorted in descending Order when click the Product type header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Product Names are not sorted in descending Order when click the Product type header for two times");
                logger.info("Product Names are not sorted in descending Order when click the Product type header for two times");
                takeScreenshot("ProductNameSort");
            }
        }
    }

    public void productStatusAscending() {
        verifyProductStatusOrder("ascending");
    }

    public void productStatusDescending() {
        verifyProductStatusOrder("descending");
    }

    public void verifyProductStatusOrder(String order) {
        click(By.cssSelector("#STATUS"), "Product Type Name", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[6]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[6]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Status are sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Status are sorted in ascending Order when click the Product type header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Product Status are not sorted in ascending Order when click the Product type header for one time");
                logger.info("Product Status are not sorted in ascending Order when click the Product type header for one time");
                takeScreenshot("ProductStatusSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Status are sorted in descending Order when click the Product type header for two times");
                logger.info("Product Status are sorted in descending Order when click the Product type header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Product Status are not sorted in descending Order when click the Product type header for two times");
                logger.info("Product Status are not sorted in descending Order when click the Product type header for two times");
                takeScreenshot("ProductStatusSort");
            }
        }
    }

    public void selectValueFromLocationDropDown() {
        click(By.xpath("//div[contains(@class,'CompantLocationdd')]/div"), "Location Dropdown", 20);
        locationToSearch = findMultipleElement(By.xpath("//a[contains(@class,'CompantLocationdd-option')]//span"), 30).get(0).getText();
        scrollUpDown("up");
        findMultipleElement(By.xpath("//a[contains(@class,'CompantLocationdd-option')]"), 30).get(0).click();
    }

    public void clickSearchButton() {
        click(By.cssSelector("#Go"), "Search Button", 20);
        staticWait(2000);
    }

    public void clickResetButton() {
        findElementClickable(By.cssSelector("a.clearsearchtext"), 30);
        click(By.cssSelector("a.clearsearchtext"), "Clear Button", 20);
    }

    public void verifySearchedLocation() {
        int searchedCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//a[@id='ancEditAssetType']//span"), 30).size();
        if (searchedCount != 0) {
            click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td[5]//span"), "Searched Product", 20);
            click(By.xpath("//ul[@id='myTab']//a[text()=' Deploy Product']"), "Deploy tab", 20);
            List<WebElement> locationList = findMultipleElement(By.xpath("//table[@id='deployItemsTable']//tbody//tr//td[1]//a"), 30);
            int iteration = 0;
            for (WebElement ele : locationList) {
                iteration++;
                if (ele.getText().equals(locationToSearch)) {
                    getTest().log(LogStatus.PASS, "Searched Location are displayed as expected");
                    logger.info("Searched Location are displayed as expected");
                    break;
                } else if (iteration == locationList.size()) {
                    getTest().log(LogStatus.FAIL, "Searched Location are not displayed");
                    logger.info("Searched Location are not displayed");
                    takeScreenshot("SearchedLocation");
                }
            }
            navigateToManageProductPage();
        } else {
            getTest().log(LogStatus.FAIL, "Searched Location are not displayed");
            logger.info("Searched Location are not displayed");
            takeScreenshot("SearchedLocation");
        }
    }

    public void enterProductNameToSearch() {
        click(By.xpath("//span[text()='Product Name/Code ']"), "Produt Name Search field", 30);
        productNameToSearch = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[5]//span"), 30).trim();
        enter(By.cssSelector("input#search"), productNameToSearch, "Product Name To Search", 20);
    }

    public void verifySearchedProductName() {
        waitForLoad(20);
        String searchedProductName = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[5]//span"), 30, 30).trim();
        if (productNameToSearch.equals(searchedProductName)) {
            getTest().log(LogStatus.PASS, "Searched Product is displayed as expected when search with product name / code");
            logger.info("Searched Product is displayed as expected when search with product name / code");
        } else {
            getTest().log(LogStatus.FAIL, "Searched Product is not displayed when search with product name / code");
            logger.info("Searched Product is not displayed when search with product name / code");
            takeScreenshot("SearchedProduct");
        }
    }

    public void verifyProductNameField() {
        click(By.xpath("//span[text()='Product Name/Code ']"), "Produt Name Search field", 30);
        String actualFiled = getAtribute(By.cssSelector("input#search"), "value", 30);
        if (actualFiled.equals("")) {
            getTest().log(LogStatus.PASS, "Entered product name is cleared as expected when click on the reset button");
            logger.info("Entered product name is cleared as expected when click on the reset button");
        } else {
            getTest().log(LogStatus.FAIL, "Entered product name is not cleared when click on the reset button");
            logger.info("Entered product name is not cleared when click on the reset button");
            takeScreenshot("ProductNameField");
        }
        click(By.xpath("//span[text()='Product Name/Code ']"), "Produt Name Search field", 30);
    }

    public void selectStatusToSearch() {
        click(By.xpath("//span[text()='Status']"), "Status Search Field", 20);
        productStatusToSearch = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[6]//span"), 30).trim();
        scrollDown();
        click(By.xpath("//label[text()='" + productStatusToSearch + "']"), productStatusToSearch + "Status", 20);
        scrollUpDown("up");
    }

    public void verifySearchedStatus() {
        List<String> actualSearchedStatus = new ArrayList<>();
        List<String> result = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        waitForLoader(30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[6]//span"), 30);
        for (WebElement element : elements) {
            actualSearchedStatus.add(element.getText().trim());
        }
        WebElement paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 30);
        if (paginationNext != null) {
            paginationNext = findElementClickable(By.xpath("//a[@class='page-link next']"), 30);
            click((By.xpath("//a[@class='page-link next']")), "Pagination Next", 20);
            while (paginationNext != null) {
                elements = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[6]//span"), 30);
                for (WebElement element : elements) {
                    actualSearchedStatus.add(element.getText().trim());
                }
                click((By.xpath("//a[@class='page-link next']")), "Pagination Next", 20);
                paginationNext = findElementClickable(By.xpath("//a[@class='page-link next']"), 20);
            }
        }
        for (String str : actualSearchedStatus) {
            if (str.equals(productStatusToSearch)) {
                result.add("true");
            } else {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, "Searched status are displayed as expected when search with status field");
            logger.info("Searched status are displayed as expected when search with status field");
        } else {
            getTest().log(LogStatus.FAIL, "Searched status are not displayed when search with status field");
            logger.info("Searched status are not displayed when search with status field");
            takeScreenshot("SearchedStatus");
        }
    }

    public void clickExpandAllButton() {
        click(By.xpath("//div[@class='bottom_filter_button']//a"), "Expand All", 20);
    }

    public void verifyLocationFilter() {
        verifyExpandedFilters("location");
    }

    public void verifyProductNameFilter() {
        verifyExpandedFilters("productName");
    }

    public void verifyStatusFilter() {
        verifyExpandedFilters("status");
    }

    public void verifyExpandedFilters(String filterName) {
        WebElement expectedFilter = null;
        String fieldName = null;
        WebElement expandedLocationFilter = findElementVisibility(By.xpath("//div[contains(@class,'CompantLocationdd')]/div"), 30);
        WebElement expandedProductNameFilter = findElementVisibility(By.cssSelector("input#search"), 30);
        WebElement expandedStatusFilter = findElementVisibility(By.xpath("//div[@class='form-group filterscroll']"), 30);
        switch (filterName) {
            case "location":
                expectedFilter = expandedLocationFilter;
                fieldName = "Location Dropdown";
                break;
            case "productName":
                expectedFilter = expandedProductNameFilter;
                fieldName = "Product Name /Code text box";
                break;
            case "status":
                expectedFilter = expandedStatusFilter;
                fieldName = "Status selection";
                break;
        }
        if (expectedFilter != null) {
            getTest().log(LogStatus.PASS, fieldName + " field is displayed as expected when click expand all button");
            logger.info(fieldName + " field is displayed as expected when click expand all button");
        } else {
            getTest().log(LogStatus.FAIL, "Location dropdown field is not displayed when click expand all button");
            logger.info("Location dropdown field is not displayed when click expand all button");
            takeScreenshot("LocationDropdown");
        }
    }

    public void clickMoreIcon() {
        staticWait(500);
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        findElementVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//span[@class='actions mobileaction']"), 180);
        scrollUpDown("up");
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//span[@class='actions mobileaction']"), "More Icon", 60);
    }

    public void navigateToSubStatusPopup() {
        clickMoreIcon();
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//a[@data-original-title='Sub Status']"), "Sub-status Menu", 60);
        staticWait(1500);
        waitForLoad(20);
    }

    public void verifyFieldsInSubStatusPopup() {
        WebElement locationDropDown = findElementVisibility(By.xpath("//div[contains(@class,'CompantLocationSelectSubStatusdd')]/div"), 30);
        if (locationDropDown != null) {
            getTest().log(LogStatus.PASS, "Location drop down is displayed in the sub status popup");
            logger.info("Location drop down is displayed in the sub status popup");
        } else {
            getTest().log(LogStatus.FAIL, "Location drop down is not displayed in the sub status popup");
            logger.info("Location drop down is not displayed in the sub status popup");
            takeScreenshot("LocationDropdown");
        }
        List<WebElement> headerNames = findMultipleElement(By.xpath("//table[@id='tblsubstatuslisting']//th//span"), 40);
        List<String> expectedHeader = Arrays.asList("S.No", "Status", "Location", "Count");
        for (WebElement ele : headerNames) {
            int iteration = 0;
            for (String str : expectedHeader) {
                iteration++;
                if (str.equals(ele.getText().trim())) {
                    getTest().log(LogStatus.PASS, "Expected Header Name " + ele.getText().trim() + " is displayed in the popup");
                    logger.info("Expected Header Name " + ele.getText().trim() + " is displayed in the popup");
                    break;
                } else if (iteration == expectedHeader.size()) {
                    getTest().log(LogStatus.FAIL, "Expected Header Name " + ele.getText().trim() + " is not displayed in the popup");
                    logger.info("Expected Header Name " + ele.getText().trim() + " is not displayed in the popup");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void closeSubStatusPopup() {
        click(By.xpath("//div[@aria-describedby='divDialogSubStatus']//button[@class='close']"), "Sub-staus close", 30);
    }

    public void navigateToProductPageHistory() {
        clickMoreIcon();
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//a[contains(@class,'detailItemHistory')]"), "Product Page History option", 30);
    }

    public void verifyProductAssignmentPage() {
        WebElement productAssignment = findElementVisibility(By.xpath("//span[text()='Product Assignment']"), 50);
        if (productAssignment != null) {
            getTest().log(LogStatus.PASS, "Product Assignment page is displayed as expected when click \"Product Page History Option\"");
            logger.info("Product Assignment page is displayed as expected when click \"Product Page History Option\"");
        } else {
            getTest().log(LogStatus.FAIL, "Product Assignment page is not displayed when click \"Product Page History Option\"");
            logger.info("Product Assignment page is not displayed when click \"Product Page History Option\"");
            takeScreenshot("ProductAssignmentPage");
        }
    }

    public void navigateToViewAttachedFilePage() {
        clickMoreIcon();
        click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//a[contains(@class,'viewFile')]"), "View Attachment option", 30);
    }

    public void verifyAttachmentPopup() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='example']"), 60);
        if (element != null) {
            int recordsCount = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"), 40).size();
            if (recordsCount != 0) {
                int downloadIcon = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//a[@class='downloadfile']"), 30).size();
                int deleteIcon = findMultipleElement(By.xpath("//table[@id='example']//tbody//tr//a[@class='deletefile']"), 30).size();
                if (downloadIcon == recordsCount && deleteIcon == recordsCount) {
                    getTest().log(LogStatus.PASS, "Download icon and Delete icon is displayed as expected for each file in the table");
                    logger.info("Download icon and Delete icon is displayed as expected for each file in the table");
                    verifyAttachmentDownload();
                } else {
                    getTest().log(LogStatus.FAIL, "Download icon and Delete icon is not displayed as expected");
                    logger.info("Download icon and Delete icon is not displayed as expected");
                    takeScreenshot("DownloadIcon");
                }
            } else {
                getTest().log(LogStatus.PASS, "Attachment popup is displayed but there is no record");
                logger.info("Attachment popup is displayed but there is no record");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Attachment popup is not displayed when click Attachment option");
            logger.info("Attachment popup is not displayed when click Attachment option");
            takeScreenshot("AttachmentPopup");
        }
    }

    public void verifyAttachmentDownload() {
        String downloadPath = Drivers.path;
        String fileName = getText(By.xpath("//table[@id='example']//tbody//tr[1]//td[3]"), 30);
        File dir = new File(downloadPath + fileName);
        if (dir.exists()) {
            dir.delete();
        }
        File[] dirContent = new File(downloadPath).listFiles();
        int filesInDirectory = dirContent.length;

        click(By.xpath("//table[@id='example']//tbody//tr[1]//a[@class='downloadfile']"), "Download Icon", 20);

        dir = new File(downloadPath + fileName);
        File dir2 = new File(downloadPath);
        waitTillNewFile(dir2.toString(), filesInDirectory);
        boolean dirContents = dir.exists();
        if (dirContents) {
            getTest().log(LogStatus.PASS, "Downloaded File \"" + fileName + "\"is Exist");
            logger.info("Downloaded File \"" + fileName + "\"is Exist");
        } else {
            getTest().log(LogStatus.FAIL, "Downloaded File \"" + fileName + "\" is not Exist");
            logger.info("Downloaded File \"" + fileName + "\" is not Exist");
            takeScreenshot("DownloadedFile");
        }
    }

    public void navigateToDepreciationPage() {
        click(By.cssSelector("a#ancDepreciation"), "Depreciation menu", 20);
    }

    public void verifyDepreciationPage() {
        WebElement depreciationPage = findElementVisibility(By.xpath("//span[text()='Depreciation List']"), 40);
        if (depreciationPage != null) {
            getTest().log(LogStatus.PASS, "Depreciation page is displayed as expected when click on the \"Depreciation Menu\"");
            logger.info("Depreciation page is displayed as expected when click on the \"Depreciation Menu\"");
        } else {
            getTest().log(LogStatus.FAIL, "Depreciation page is not displayed when click on the \"Depreciation Menu\"");
            logger.info("Depreciation page is not displayed when click on the \"Depreciation Menu\"");
            takeScreenshot("DepreciationPage");
        }
    }

    public void verifyBreadCrumbInDepreciation() {
        WebElement breadCrumb = findElementVisibility(By.xpath("//div[@id='SiteMapLink']//ol"), 40);
        if (breadCrumb != null) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed in the depreciation list page");
            logger.info("BreadCrumb is displayed in the depreciation list page");
            click(By.xpath("//div[@id='SiteMapLink']//ol//a"), "BreadCrumb Home", 20);
            WebElement homePage = findElementVisibility(By.xpath("//div[@class='row']//span[normalize-space(text())='Dashboard']"), 40);
            if (homePage != null) {
                getTest().log(LogStatus.PASS, "Home page is displayed as expected when click \"Home\" in the breadcrumb");
                logger.info("Home page is displayed as expected when click \"Home\" in the breadcrumb");
            } else {
                getTest().log(LogStatus.FAIL, "Home page is not displayed aswhen click \"Home\" in the breadcrumb");
                logger.info("Home page is not displayed when click \"Home\" in the breadcrumb");
                takeScreenshot("HomePage");
            }
        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed in the depreciation list page");
            logger.info("BreadCrumb is not displayed in the depreciation list page");
            takeScreenshot("BreadCrumb");
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
        String dayInDigit = dayDigit.format(now);
        String[] inputDateArray = dtf.format(now.plusYears(1)).split("/");
        String day = inputDateArray[0];
        String month = inputDateArray[1];
        String year = inputDateArray[2];

        click(By.xpath("//div[@data-target='#depreciationDate']"), "Date Field", 20);
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
        String actualDate = getAtribute(By.cssSelector("input#depreciationDate"), "value", 30);
        if (actualDate.equals(expectedDate)) {
            getTest().log(LogStatus.PASS, "User can able to select date from the calendar");
            logger.info("User can able to select date from the calendar");
        } else {
            getTest().log(LogStatus.FAIL, "User not able to select date from the calendar");
            logger.info("User not able to select date from the calendar");
            takeScreenshot("Date");
        }
    }

    public void verifySearchedDepreciationList() {
        waitForLoad(20);
        String[] selectedDate = getAtribute(By.cssSelector("input#depreciationDate"), "value", 30, 30).split("/");
        String expectedYear = selectedDate[2];
        int expectedMonthNum = Integer.parseInt(selectedDate[0]);
        String expectedMonth = Month.of(expectedMonthNum).name();
        String expectedHeaderName = expectedMonth + "-" + expectedYear;
        waitForLoader(20);
        findElementPresence(By.xpath("//table[contains(@class,'table table-bordered')]//tr[2]//th[21]//span"), 40);
        String[] depreciation = getText(By.xpath("//table[contains(@class,'table table-bordered')]//tr[2]//th[21]//span"), 30).split(" ");
        String actualHeaderName = depreciation[3];
        if (expectedHeaderName.equalsIgnoreCase(actualHeaderName)) {
            getTest().log(LogStatus.PASS, "The depreciation list provide the accurate result based on the selected date when click search button");
            logger.info("The depreciation list provide the accurate result based on the selected date when click search button");
        } else {
            getTest().log(LogStatus.FAIL, "The depreciation list not provides the accurate result based on the selected date when click search button");
            logger.info("The depreciation list not provides the accurate result based on the selected date when click search button");
            takeScreenshot("DepreciationList");
        }
    }

    public void resetDateSearch() {
        click(By.cssSelector("a#clearSearch"), "Reset Date Search", 40);
    }

    public void verifyClearedDate() {
        String actualText = getAtribute(By.cssSelector("input#depreciationDate"), "value", 30);
        if (actualText.equals("")) {
            getTest().log(LogStatus.PASS, "Selected date is cleared as expected when click reset button");
            logger.info("Selected date is cleared as expected when click reset button");
        } else {
            getTest().log(LogStatus.FAIL, "Selected date is not cleared as expected when click reset button");
            logger.info("Selected date is not cleared as expected when click reset button");
            takeScreenshot("DateField");
        }
    }

    public void paginationFunctionality() {
        int depreciationCount = findMultipleElement(By.xpath("//table[contains(@class,'table table-bordered')]//tbody//tr//td//span[@data-relatedinfoid]"), 40).size();
        if (depreciationCount != 0) {
            verifyDepreciationPaginationFunction();
        } else {
            getTest().log(LogStatus.PASS, "There is no enough data to perform pagination on depreciation search result");
            logger.info("There is no enough data to perform pagination on depreciation search result");
        }
    }

    public void verifyDepreciationPaginationFunction() {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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
            recordCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + recordCount + "]//td[4]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
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

    public void navigateToImportProductPage() {
        click(By.cssSelector("a#ancImportItem"), "Import menu", 20);
    }

    public void verifyImportPage() {
        WebElement importPage = findElementVisibility(By.xpath("//span[text()='Import Product']"), 40);
        if (importPage != null) {
            getTest().log(LogStatus.PASS, "Import Product page is displayed as expected when click on the Import icon");
            logger.info("Import Product page is displayed as expected when click on the Import icon");
        } else {
            getTest().log(LogStatus.FAIL, "Import Product page is not displayed as expected when click on the Import icon");
            logger.info("Import Product page is not displayed as expected when click on the Import icon");
            takeScreenshot("ImportPage");
        }
    }

    public void downloadAndVerifyImportSample() {
        String downloadPath = Drivers.path;
        String fileName = prop.getProperty("importProductSampleFile");
        File dir = new File(downloadPath + fileName);
        if (dir.exists()) {
            dir.delete();
        }
        File[] dirContent = new File(downloadPath).listFiles();
        int filesInDirectory = dirContent.length;

        click(By.xpath("//div[@class='d-flex align-items-center']//a//i[contains(@class,'fa-download')]"), "Download Icon", 20);

        dir = new File(downloadPath + fileName);
        File dir2 = new File(downloadPath);
        waitTillNewFile(dir2.toString(), filesInDirectory);
        boolean dirContents = dir.exists();
        if (dirContents) {
            getTest().log(LogStatus.PASS, "Downloaded sample import file \"" + fileName + "\" is Exist in the folder");
            logger.info("Downloaded sample import file \"" + fileName + "\" is Exist in the folder");
        } else {
            getTest().log(LogStatus.FAIL, "Downloaded sample import file \"" + fileName + "\" is not Exist in the folder");
            logger.info("Downloaded sample import file \"" + fileName + "\" is not Exist in the folder");
            takeScreenshot("DownloadedFile");
        }
    }

    public void storeDuplicatePCode() {
        String productType = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td[3]//span"), 30);
        String productCode = getText(By.xpath("//table[@id='tablelistingdata']/tbody/tr[2]/td[4]//span"), 30);
        int endIndex = productCode.length();
        int startIndex = endIndex - 6;
        duplicateProductType = productType;
        duplicateProductCode = productCode.substring(startIndex, endIndex);
    }

    public void importSampleFile() {
        enter(By.cssSelector("input#flFile"), testFilePath + prop.getProperty("importProductSampleFile"), "Import Sample File", 20);
    }

    public void clickImportButton() {
        click(By.cssSelector("a#btnImportFile"), "Import Button", 20);
    }

    public void confirmImport() {
        click(By.xpath("//div[contains(@class,'modal-confirm-footer')]/button[@title='OK']"), "Confirm Import", 60);
    }

    public void verifyConfirmationPopup() {
        String actualConfirmation = getText(By.xpath("//div[contains(@class,'notifybox')]/div"), 30);
        if (actualConfirmation.equals(productImportConfirmation)) {
            getTest().log(LogStatus.PASS, "Confirmation popup is displayed as expected when click on import from excel file button");
            logger.info("Confirmation popup is displayed as expected when click on import from excel file button");
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation popup is not displayed when click on import from excel file button");
            logger.info("Confirmation popup is not displayed when click on import from excel file button");
            takeScreenshot("ConfirmationPopup");
        }
    }

    public void selectProductType() {
        int rowCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td[1]//select"), 30).size();
        for (int i = 1; i <= rowCount; i++) {
            selectValueWithText(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[1]//select"), duplicateProductType, "Imported Product Type", 20);
            Select select = new Select(driver.findElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[1]//select")));
            WebElement option = select.getFirstSelectedOption();
            String defaultItem = option.getText();
            if (!defaultItem.equals("Select")) {
                getTest().log(LogStatus.PASS, "User can able to Select the product type from the dropdown for the imported product in the row - " + i);
                logger.info("User can able to Select the product type from the dropdown for the imported product in the row - " + i);
            } else {
                getTest().log(LogStatus.FAIL, "User not able to Select the product type from the dropdown for the imported product in the row - " + i);
                logger.info("User not able to Select the product type from the dropdown for the imported product in the row " + i);
                takeScreenshot("ProductTypeDropdown");
            }
        }
    }

    public void enterProductName() {
        int rowCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td[1]//select"), 30).size();
        for (int i = 1; i <= rowCount; i++) {
            enter(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td//input[@id='Item_Name']"), "P" + i + dateValue, "Imported Product Name", 20);
            String prodName = getAtribute(By.xpath("//table[@id='tblExpList']//tbody//tr[" + i + "]//td//input[@id='Item_Name']"), "value", 20);
            importedPNames.add(prodName);
        }
    }

    public void verifyUniqueProductCodeField() {
        int rowCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td[1]//select"), 30).size();
        for (int i = 1; i <= rowCount; i++) {
            enter(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[4]//input"), duplicateProductCode, "Prodcut Code", 30);
            String actualProductCode = getAtribute(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[4]//input"), "value", 20);
            if (actualProductCode.equals(duplicateProductCode)) {
                getTest().log(LogStatus.PASS, "User can able to enter the Product Code in the list for the imported product in row - " + i);
                logger.info("User can able to enter the Product Code in the list for the imported product in row - " + i);
            } else {
                getTest().log(LogStatus.FAIL, "User not able to enter the Product Code in the list for the imported product in row - " + i);
                logger.info("User not able to enter the Product Code in the list for the imported product in row - " + i);
                takeScreenshot("ProductCodeField");
            }
        }
    }

    public void verifyUniqueProductCode() {
        int j = 1;
        for (String productNames : importedPNames) {
            String productCode = getText(By.xpath("//span[normalize-space(text())='" + productNames + "']//../../..//td[4]/span"), 30);
            String actualProductCode = productCode.substring(4, 10);
            int number = Integer.parseInt(duplicateProductCode.substring(3, 6)) + j;
            j++;
            String temp = String.format("%03d", number);
            String expectedProductCOde = duplicateProductCode.substring(0, 3) + temp;
            if (actualProductCode.equalsIgnoreCase(expectedProductCOde)) {
                getTest().log(LogStatus.PASS, "Imported product is added to the list with unique product code " + productCode + " as expected");
                logger.info("Imported product is added to the list with unique product code " + productCode + " as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Imported product is not added to the list with unique product code");
                logger.info("Imported product is not added to the list with unique product code");
                takeScreenshot("UniqueCodeField");
            }
        }
    }

    public void selectLocation() {
        int rowCount = findMultipleElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr//td[1]//select"), 30).size();
        for (int i = 1; i <= rowCount; i++) {
            selectValueWithIndex(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[11]//select"), 1, "Imported Product Location", 20);
            Select select = new Select(driver.findElement(By.xpath("//table[contains(@class,'table-bordered')]//tbody//tr[" + i + "]//td[11]//select")));
            WebElement option = select.getFirstSelectedOption();
            String defaultItem = option.getText();
            if (!defaultItem.equals("Select")) {
                getTest().log(LogStatus.PASS, "User can able to Select the location from the dropdown for the imported product in the row - " + i);
                logger.info("User can able to Select the location from the dropdown for the imported product in the row - " + i);
            } else {
                getTest().log(LogStatus.FAIL, "User not able to Select the location from the dropdown for the imported product in the row - " + i);
                logger.info("User not able to Select the location from the dropdown for the imported product in the row - " + i);
                takeScreenshot("LocationDropdown");
            }
        }
    }

    public void clickCancelButton() {
        click(By.xpath("//a[@class='btn btn-danger']"), "Cancel Button", 20);
    }

    public void verifyProductListingPage() {
        WebElement productListPage = findElementVisibility(By.xpath("//span[text()='Product List']"), 40);
        if (productListPage != null) {
            getTest().log(LogStatus.PASS, "Product listing page is displayed as expected when click on the cancel button");
            logger.info("Product listing page is displayed as expected when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "Product listing page is not displayed as expected when click on the cancel button");
            logger.info("Product listing page is not displayed as expected when click on the cancel button");
            takeScreenshot("ProductListingScreen");
        }
    }

    public void verifyChooseButton() {
        WebElement chooseButton = findElementClickable(By.xpath("//label[@for='flFile']"), 30);
        if (chooseButton != null) {
            getTest().log(LogStatus.PASS, "Choose file button is displayed as clickable");
            logger.info("Choose file button is displayed as clickable");
        } else {
            getTest().log(LogStatus.FAIL, "Choose file button is not displayed as clickable");
            logger.info("Choose file button is not displayed as clickable");
            takeScreenshot("ChooseFileButton");
        }
    }

    public void verifyClearButton() {
        String beforeReset = getAtribute(By.xpath("//div[contains(@class,'bootstrap-filestyle')]/input"), "value", 30);
        click(By.xpath("//a[contains(@class,'clsattclear')]"), "Clear Button", 20);
        String afterReset = getAtribute(By.xpath("//div[contains(@class,'bootstrap-filestyle')]/input"), "value", 30);
        if (!beforeReset.equals(afterReset)) {
            getTest().log(LogStatus.PASS, "Selected file is removed as expected when click on the clear button");
            logger.info("Selected file is removed as expected when click on the clear button");
        } else {
            getTest().log(LogStatus.FAIL, "Selected file is not removed as expected when click on the clear button");
            logger.info("Selected file is not removed as expected when click on the clear button");
            takeScreenshot("ClearImportFile");
        }
    }

    public void clickCancelAfterImport() {
        click(By.xpath("//div[@class='search-btm-btn']//a[contains(@class,'btn btn-danger')]"), "Cancel After Import", 50);
    }

    public void clickSubmitButton() {
        click(By.cssSelector("a#aSubmit"), "Submit Button", 20);
    }

    public void verifySuccessMessage() {
        WebElement successMessage = findElementVisibility(By.xpath("//span[text()='File has been successfully imported.']"), 40);
        if (successMessage != null) {
            getTest().log(LogStatus.PASS, "Success message is displayed as expected when click on the submit button");
            logger.info("Success message is displayed as expected when click on the submit button");
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed as expected when click on the submit button");
            logger.info("Success message is not displayed as expected when click on the submit button");
            takeScreenshot("SuccessMessagePopup");
        }
    }

    public void navigateToProductCreatePage() {
        click(By.cssSelector("a#ancCreateJob"), "Create New", 20);
    }

    public void verifyAddProductScreen() {
        WebElement createProduct = findElementVisibility(By.xpath("//div[contains(@class,'heading-border')]//span[text()='Create Product']"), 40);
        if (createProduct != null) {
            getTest().log(LogStatus.PASS, "Create product page is displayed as expected when click on the add new button");
            logger.info("Create product page is displayed as expected when click on the add new button");
        } else {
            getTest().log(LogStatus.FAIL, "Create product page is not displayed as expected when click on the add new button");
            logger.info("Create product page is not displayed as expected when click on the add new button");
            takeScreenshot("CreateProductPage");
        }
    }

    public void clickUserGuideLink() {
        click(By.cssSelector("a.ancuserguide"), "User Guide Link", 20);
    }

    public void verifyUserGuideScreen() {
        WebElement userGuide = findElementVisibility(By.xpath("//div[@class='user-guide-content']"), 30);
        if (userGuide != null) {
            getTest().log(LogStatus.PASS, "User Guide screen is displayed as expected when click on the User Guide Link");
            logger.info("User Guide screen is displayed as expected when click on the User Guide Link");
        } else {
            getTest().log(LogStatus.FAIL, "User Guide screen is not displayed when click on the User Guide Link");
            logger.info("User Guide screen is not displayed when click on the User Guide Link");
            takeScreenshot("UserGuideScreen");
        }
    }

    public void expandLocationField() {
        click(By.xpath("//span[text()='Location']"), "Location Field", 20);
    }

    public void expandLocationDropdown() {
        click(By.xpath("//div[@id='divmultilevelselect']/div/div"), "Location drop down", 20);
    }

    public void selectLocationForWidgetSearch(String locationName) {
        if (locationName.equalsIgnoreCase("parentLocation")) {
            productManagerLocation = parentLocationNameList.get(0);
        }
        click(By.xpath("//a[@data-text='" + productManagerLocation + "']"), "Location " + productManagerLocation, 30);
    }

    public void expandProductNameField() {
        click(By.xpath("//span[text()='Product Name/Code ']"), "Product Name field", 20);
    }

    public void enterProductName(String productName) {
        enter(By.cssSelector("input#search"), productName, "Product Name To Search", 20);
    }

    public void getProductCountForNUWidget() {
        int productCountForWidget = 0;
        expandLocationField();
        expandLocationDropdown();
        selectLocationForWidgetSearch("");
        for (String prodName : productInMyProductList) {
            expandProductNameField();
            enterProductName(prodName);
            clickSearchButton();
            waitForLoader(30);
            WebElement searchedProduct = findElementVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[8]/span/span"), 20);
            if (searchedProduct != null) {
                String productCountInText = searchedProduct.getText();
                productCountForWidget = productCountForWidget + Integer.parseInt(productCountInText);
            }
        }
        productCountForLocationWidget = productCountForWidget;
    }

    public void verifyAndAddProductCountWithLocation() {
        int productCountForWidget = 0;
        expandLocationField();
        expandLocationDropdown();
        selectLocationForWidgetSearch("");
        for (String prodName : productNamesPendingByUser) {
            expandProductNameField();
            enterProductName(prodName.split("_")[0]);
            clickSearchButton();
            waitForLoader(30);
            WebElement searchedProduct = findElementVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr[@class]"), 10);
            if (searchedProduct != null) {
                String productCountInText = productNamesWithCount.get(prodName);
                productCountForWidget = productCountForWidget + Integer.parseInt(productCountInText);
            }
        }
        productCountForPendingByUserWidget = String.valueOf(productCountForWidget);
    }

    public void verifyAndAddProductNameWithLocation(String userType, String listToBeShared) {
        List<String> actualProductList = null;
        List<String> productsHasSameShift = new ArrayList<>();
        List<String> productsHasDifferentShift = new ArrayList<>();
        if (userType.equalsIgnoreCase("ProductManager")) {
            actualProductList = productsAcceptancePendingByMangerPM;
        } else if (userType.equalsIgnoreCase("NormalUser")) {
            actualProductList = productsAcceptancePendingByMangerNU;
        }
        expandLocationField();
        expandLocationDropdown();
        selectLocationForWidgetSearch("");
        for (String prodName : actualProductList) {
            expandProductNameField();
            enterProductName(prodName.split("_")[0]);
            clickSearchButton();
            waitForLoader(30);
            WebElement searchedProduct = findElementVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr[@class]"), 5);
            if (searchedProduct != null) {
                productsHasSameShift.add(prodName);
            } else {
                productsHasDifferentShift.add(prodName);
            }
        }
        if (listToBeShared.equalsIgnoreCase("AllLocation")) {
            productsAcceptancePendingByMangerPM.clear();
            productsAcceptancePendingByMangerNU.clear();
            productsAcceptancePendingByMangerPM = productsHasDifferentShift;
            productsAcceptancePendingByMangerNU = productsHasDifferentShift;
        } else {
            productsAcceptancePendingByMangerPM.clear();
            productsAcceptancePendingByMangerNU.clear();
            productsAcceptancePendingByMangerNU = productsHasSameShift;
            productsAcceptancePendingByMangerPM = productsHasSameShift;
        }
    }

    public void getBarCodeForSearch() {
        deployProductPage.make100PageSize();
        waitForLoader(60);
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 120);
        List<WebElement> productInListLocator = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td[8]/span/span"), 20);
        First:
        for (WebElement product : productInListLocator) {
            String productCount = product.getText().trim();
            if (!productCount.equalsIgnoreCase("0") && !productCount.startsWith("-")) {
                product.findElement(By.xpath("//ancestor::tr//td[5]")).click();
                click(By.xpath("//ul[@id='myTab']//a[@id='tab-timeline' and text()=' Related Information ']"), "Related Information tab", 20);
                waitForLoader(60);
                staticWait(3000);
                List<WebElement> relatedInfoProd = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//td[5]//span/span"), 20);
                for (WebElement relatedProd : relatedInfoProd) {
                    if (!relatedProd.getText().equalsIgnoreCase("0")) {
                        barCodeNameToProductAssignSearch = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td[6]//span[contains(@class,'d-block')]"), 60, 60);
                        break First;
                    }
                }
            }
        }
    }

    public void getProductNamesBasedOnProductType() {
        deployProductPage.make100PageSize();
        waitForLoader(60);
        Object paginationNextButton = "";
        while (paginationNextButton != null) {
            List<WebElement> productNamesLocators = findMultipleElement(By.xpath("//span[text()='" + productTypeNamesForProductAssignment.get(0) + "']//ancestor::tr//a[@class='active-disabled']//ancestor::tr//td[8]/span/span"), 20);
            for (WebElement productNamesLocator : productNamesLocators) {
                String availableProduct = productNamesLocator.getText();
                if (!availableProduct.equalsIgnoreCase("0")) {
                    String productNamesToBeCheck = productNamesLocator.findElement(By.xpath("//ancestor::tr//td[5]/a/span")).getText().trim();
                    productNamesForProductAssignment.add(productNamesToBeCheck);
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 20);
            }
        }
        if (productNamesForProductAssignment.size() == 0) {
            getTest().log(LogStatus.FAIL, "No product is displayed in the manage product page when search with product type as " + productTypeNamesForProductAssignment.get(0) + " but for the same product type has some product count in product report page. So ProductName and Products fields in the product assignment page can't be validated");
            logger.info("No product is displayed in the manage product page when search with product type as " + productTypeNamesForProductAssignment.get(0) + " but for the same product type has some product count in product report page. So ProductName and Products fields in the product assignment page can't be validated");
        }
    }
}
