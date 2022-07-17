package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class ProductTransferPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    static String barcodeValue;
    static String alphaNumericValue;
    static String getUniqueName;
    static String proCode;


    public ProductTransferPage(WebDriver driver) {
        super(driver, "Product transfer Page");
        this.driver = driver;
    }

    public void clickProductTransfer() {
        staticWait(2000);
        click(By.cssSelector("a#ancItemTransfer"), "Product transfer", 20);
    }

    public void verifyProductTransferPage() {
        String pageNAme = getText(By.cssSelector("span[class='p-name text-white']"), 20).trim();
        if (pageNAme.equals("Product Assignment")) {

            getTest().log(LogStatus.PASS, "Product transfer Page is displayed");
            logger.info("Product transfer Page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Product transfer page is not displayed");
            logger.info("Product transfer Page is not displayed");
            takeScreenshot("Product type Listing");
        }
    }

    public void verifyBreadCrumbInProductTransferPage() {
        WebElement element = findElementsVisibility(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']"));
        if (element.isDisplayed()) {
            getTest().log(LogStatus.PASS, "BreadCrumb is displayed in Product Transfer Page");
            logger.info("BreadCrumb is displayed in Product Transfer Page");
            clickItemFromBreadCrumb();
            verifyItemPage();

        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed in Product Transfer Page");
            logger.info("BreadCrumb is not displayed in Product Transfer Page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void clickItemFromBreadCrumb() {
        click(By.xpath("//div[@id='SiteMapLink']//ol[@class='breadcrumb']//li//a[text()='Product']"), "Product", 20);

    }

    public void verifyItemPage() {
        WebElement element = findElementsVisibility(By.xpath("//table[@id='tablelistingdata']/tbody/tr/td/span"));
        if (element != null) {
            getTest().log(LogStatus.PASS, "Item Page is displayed");
            logger.info("Item Page is displayed");
        } else {
            getTest().log(LogStatus.FAIL, "Item Page is not displayed");
            logger.info("Item Page is not displayed");
            takeScreenshot("Item");
        }
    }

    public void getBarCodeNumber() {
        barcodeValue = getText(By.xpath("//table[@id='tblRelatedInfoListing']/tbody/tr/td[6]//div[contains(@class,'form-control')]/span"), 20).trim();
    }

    public void enterBarCodeNumber() {
        enter(By.cssSelector("input#txtBarcode"), barcodeValue, "Barcode number", 20);
    }

    public void enterAlphaNumericBarCodeNumber() {
        alphaNumericValue = prop.getProperty("barCode") + dateValue.substring(10, 14);
        enter(By.cssSelector("input#txtBarcode"), alphaNumericValue, "Barcode number", 20);
        String getBarcodevalue = getAtribute(By.cssSelector("input#txtBarcode"), "value", 30);
        if (getBarcodevalue.equals(alphaNumericValue)) {
            getTest().log(LogStatus.PASS, "AlphaNumeric value is allowed to enter in Barcode field");
            logger.info("AlphaNumeric value is allowed to enter in Barcode field");
        } else {
            getTest().log(LogStatus.FAIL, "AlphaNumeric value is not allowed to enter in Barcode field");
            logger.info("AlphaNumeric value is not allowed to enter in Barcode field");
            takeScreenshot("AlphaNumericBarCode");
        }

    }

    public void clickBarCodeSearchButton() {
        click(By.cssSelector("a#Searchassest_barcode"), "Search Button", 20);
    }

    public void verifySearchedBarCodeProduct() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']"), 60);
        if (element != null) {
            String actualUniqueName = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']/td[4]"), 30).trim();
            if (actualUniqueName.equals(uniqueName)) {
                getTest().log(LogStatus.PASS, "Searched Product is displayed as expected");
                logger.info("Searched Product is displayed as expected");

            } else {
                getTest().log(LogStatus.FAIL, "Searched Product is not displayed ");
                logger.info("Searched Product is not displayed ");
                takeScreenshot("SearchedProd");
            }
        }
    }

    public void selectLocation() {
        WebElement element = findElementVisibility(By.xpath("//label[text()='Location:']//parent::div//div[@type='button']"), 20);
        if (element != null) {
            click(By.xpath("//label[text()='Location:']//parent::div//div[@type='button']"), "Location", 20);
            click(By.xpath("//ul[@id='CompantLocationSelect']//li/a//span[text()='" + location + "']"), "Location value - " + location, 30);
            getTest().log(LogStatus.PASS, "Location dropdown is clicked and location is selected ");
            logger.info("Location dropdown is clicked and location is selected");
        } else {
            getTest().log(LogStatus.FAIL, "Location dropdown is not clicked and location is not selected ");
            logger.info("Location dropdown is not clicked and location is not selected");
            takeScreenshot("SelectLocation");
        }
    }

    public void selectProductType() {
        selectValueWithText(By.cssSelector("div>select#AssetTypeFilter"), prodType, "Product type", 30);
    }

    public void selectProductName() {
        findElementPresence(By.xpath("//select[@id='AssetCatalogFilter']//option[text()='" + productName + "']"), 60);
        selectValueWithText(By.cssSelector("div>select#AssetCatalogFilter"), productName, "Product type", 30);
    }


    public void enterUniqueName() {
        enter(By.cssSelector("input#txtAssetItems"), productName, "Products", 20);
    }

    public void verifyUniqueNameDropDown() {
        waitForLoader(2);
        WebElement element = findElementVisibility(By.xpath("//div[@class='unique_dynamicdatalist']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "UniqueName dropDown is displayed as expected");
            logger.info("UniqueName dropDown is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "UniqueName dropDown is not displayed");
            logger.info("UniqueName dropDown is not displayed");
            takeScreenshot("UniqueName dropdown");
        }
    }

    public void selectUniqueName() {

        findElementClickable(By.xpath("//div[@class='unique_dynamicdatalist']/ul/li[text()='" + uniqueName + "']"), 240);
        clickByJavascript(By.xpath("//div[@class='unique_dynamicdatalist']/ul/li[text()='" + uniqueName + "']"), "Select Unique Name", 30);
    }

    public void verifySearchedProduct() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']"), 60);
        if (element != null) {
            String actualUniqueName = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']/td[4]"), 30, 30).trim();
            if (actualUniqueName.equals(uniqueName)) {
                getTest().log(LogStatus.PASS, "Searched Product is displayed as expected");
                logger.info("Searched Product is displayed as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Searched Product is not displayed ");
                logger.info("Searched Product is not displayed ");
                takeScreenshot("Result Table");
            }
        }
    }

    public void verifyHeaderForSearchedResultTable() {
        List<String> headerName = new ArrayList<>();
        headerName.add("Product Code");
        headerName.add("Product Name");
        headerName.add("Unique Name");
        headerName.add("Location");
        headerName.add("Total Quantity");
        headerName.add("Available Quantity");
        headerName.add("Transfer Quantity");
        waitForVisibilityOfElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']"), 30);

        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']/thead/tr/th/span"), 20);
        for (String expectedHeader : headerName) {
            int i = 0;

            for (WebElement element : elements) {
                i++;
                String actualHeaderName = element.getText().trim();
                if (expectedHeader.equals(actualHeaderName)) {
                    getTest().log(LogStatus.PASS, "The " + expectedHeader + " Header is displayed in the Product transfer Page searched result table");
                    logger.info("Pass - The " + expectedHeader + " Header is displayed in the Product transfer Page searched result table");
                    break;
                } else if (i == elements.size()) {
                    getTest().log(LogStatus.FAIL, "The " + expectedHeader + " Header is not displayed in the Product transfer Page searched result table");
                    logger.info("Pass - The " + expectedHeader + " Header is not displayed in the Product transfer Page searched result table");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void enterAlphaNumericInProductCode() {
        scrollToWebelement(By.xpath("//div[@id='divassestsearch']//label[text()='Location:']"), "Product Code");
        enter(By.cssSelector("td>input#itemCodeAutoSearch"), productCode, "Barcode number", 20);
    }

    public void verifyEnteredProductCodeInResultTable() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[2]"), 30);
        if (element != null) {
            String getProCode = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[2]"), 30).trim();
            if (productCode.equals(getProCode)) {
                getTest().log(LogStatus.PASS, "Entered Product code is displayed in the Result table as expected");
                logger.info("Entered Product code is displayed in the Result table as expected");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product code is not displayed in the Result table");
            logger.info("Entered Product code is not displayed in the Result table ");
            takeScreenshot("ProdCodeNotList");
        }
    }


    public void verifyAlphaNumericIsAllowedInProdCodeField() {
        String getProductCodeValue = getAtribute(By.cssSelector("td>input#itemCodeAutoSearch"), "value", 30);
        if (getProductCodeValue.equals(productCode)) {
            getTest().log(LogStatus.PASS, "AlphaNumeric value is allowed to enter Product Code field");
            logger.info("AlphaNumeric value is allowed to enter Product Name Code");
        } else {
            getTest().log(LogStatus.FAIL, "AlphaNumeric value is not allowed to enter Product Code field");
            logger.info("AlphaNumeric value is not allowed to enter Product Code field");
            takeScreenshot("AlphaNumericProdCode");
        }


    }

    public void enterAlphaNumericInProductName() {
        enter(By.cssSelector("td>input#itemNameAutoSearch"), productName, "Barcode number", 20);

    }

    public void verifyAlphaNumericIsAllowedInProdNameField() {

        String getProductNameValue = getAtribute(By.cssSelector("td>input#itemNameAutoSearch"), "value", 30);
        if (getProductNameValue.equals(productName)) {
            getTest().log(LogStatus.PASS, "AlphaNumeric value is allowed to enter Product Name field");
            logger.info("AlphaNumeric value is allowed to enter Product Name field");
        } else {
            getTest().log(LogStatus.FAIL, "AlphaNumeric value is not allowed to enter in Product Name field");
            logger.info("AlphaNumeric value is not allowed to enter in Product Name field");
            takeScreenshot("AlphaNumericProdName");
        }


    }

    public void verifyEnteredProductNameInResultTable() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[3]"), 30);
        if (element != null) {
            String getproductName = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[3]"), 30).trim();
            if (productName.equals(getproductName)) {
                getTest().log(LogStatus.PASS, "Entered Product Name is displayed in the Result table as expected");
                logger.info("Entered Product Name is displayed in the Result table as expected");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Entered Product Name is not displayed in the Result table");
            logger.info("Entered Product Name is not displayed in the Result table ");
            takeScreenshot("ProdNameNotList");
        }
    }

    public void enterAlphaNumericInUniqueName() {
        enter(By.cssSelector("td>input#uniqueNameAutoSearch"), uniqueName, "Barcode number", 20);
    }

    public void verifyAlphaNumericIsAllowedInUniqueNameField() {
        String getUniqueValue = getAtribute(By.cssSelector("td>input#uniqueNameAutoSearch"), "value", 30);
        if (getUniqueValue.equals(uniqueName)) {
            getTest().log(LogStatus.PASS, "AlphaNumeric value is allowed to enter in UniqueName field");
            logger.info("AlphaNumeric value is allowed to enter in UniqueName field");

        } else {
            getTest().log(LogStatus.FAIL, "AlphaNumeric value is not allowed to enter in UniqueName field");
            logger.info("AlphaNumeric value is not allowed to enter in UniqueName field");
            takeScreenshot("AlphaNumericUniqueName");
        }


    }

    public void verifyEnteredUniqueNameInResultTable() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[4]"), 30);
        if (element != null) {
            String getUname = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[4]"), 30).trim();
            if (uniqueName.equals(getUname)) {
                getTest().log(LogStatus.PASS, "Entered UniqueName is displayed in the Result table as expected");
                logger.info("Entered UniqueName is displayed in the Result table as expected");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Entered UniqueName is not displayed in the Result table");
            logger.info("Entered UniqueName is not displayed in the Result table ");
            takeScreenshot("UniqueNameNotList");
        }
    }


    public void createNewProduct() {
        AddProductPage addProductPage = new AddProductPage(driver);
        DeployProductPage deployProductPage = new DeployProductPage(driver);
        addProductPage.clickAddNewButton();
        addProductPage.selectProductType();
        Select select = new Select(driver.findElement(By.cssSelector("div>select#AssetTypeId")));
        WebElement option = select.getFirstSelectedOption();
        prodType = option.getText();
        addProductPage.enterItemName();
        productName = getAtribute(By.cssSelector("div>input#Name"), "value", 20);
        addProductPage.selectBarCodeType();
        char random = "ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray()[randNum.nextInt("ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray().length)];
        proCode = random + dateValue.substring(9, 14);
        enter(By.cssSelector("div>input#ItemCode"), proCode, "product code", 10);
        productCode = getAtribute(By.cssSelector("div>input#ItemCode"), "value", 30);
        addProductPage.clickTheCheckBox();
        goToDeployPage();
        staticWait(5000);
        deployProductPage.clickAddDeployButton();
        deployProductPage.clickLocationDropdown();
        deployProductPage.selectLocationValueFromDropdown("");
        location = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30);
        deployProductPage.enterQuantity("25");
        deployProductPage.enterUnitPrice("1");
        deployProductPage.enterProductCost("20");
        deployProductPage.clickAddListButton();
        deployProductPage.clickSaveButton();
        deployProductPage.handleSuccessPopup();
        deployProductPage.clickNextButton();
        uniqueName = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td//a[@class='editinfo']"), 20).trim();
        getBarCodeNumber();
        click(By.xpath("//div[contains(@class,'juda-menu-container')]//a[normalize-space(text())='Manage Product']"), "Manage Product", 30);
    }

    public void createNewProductWithoutUniqueName() {
        AddProductPage addProductPage = new AddProductPage(driver);
        DeployProductPage deployProductPage = new DeployProductPage(driver);
        addProductPage.clickAddNewButton();
        addProductPage.selectProductType();
        Select select = new Select(driver.findElement(By.cssSelector("div>select#AssetTypeId")));
        WebElement option = select.getFirstSelectedOption();
        prodType = option.getText();
        addProductPage.enterItemName();
        productName = getAtribute(By.cssSelector("div>input#Name"), "value", 20);
        addProductPage.selectBarCodeType();
        char random = "ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray()[randNum.nextInt("ABCDEFGHIJKLMNOPQRSTUVWZYZ".toCharArray().length)];
        proCode = random + dateValue.substring(9, 14);
        enter(By.cssSelector("div>input#ItemCode"), proCode, "product code", 10);
        productCode = getAtribute(By.cssSelector("div>input#ItemCode"), "value", 30);
        goToDeployPage();
        staticWait(5000);
        deployProductPage.clickAddDeployButton();
        deployProductPage.clickLocationDropdown();
        deployProductPage.selectLocationValueFromDropdown("");
        location = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30);
        deployProductPage.enterQuantity("25");
        deployProductPage.enterUnitPrice("1");
        deployProductPage.enterProductCost("20");
        deployProductPage.clickAddListButton();
        deployProductPage.clickSaveButton();
        deployProductPage.handleSuccessPopup();
        deployProductPage.clickNextButton();
        uniqueName = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td//a[@class='editinfo']"), 20).trim();
        getBarCodeNumber();
        click(By.xpath("//div[contains(@class,'juda-menu-container')]//a[normalize-space(text())='Manage Product']"), "Manage Product", 30);
    }

    public void clickSearchButton() {
        click(By.cssSelector("a#Searchassest"), "Search", 30);
        waitForLoad(20);
    }

    public void clickResetButton() {
        click(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr/td/a[@title='Clear']"), "Reset", 30);
    }

    public void verifySearchBoxAfterReset() {
        clickResetButton();
        waitForVisibilityOfElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[4]"), 30);
        String element = getAtribute(By.cssSelector("td>input#uniqueNameAutoSearch"), "value", 20);
        if (element.equals("")) {
            getTest().log(LogStatus.PASS, "Entered text is cleared in the search field text box");
            logger.info("Entered text is cleared in the search field text box");

        } else {
            getTest().log(LogStatus.FAIL, "Entered text is not cleared in the search field text box");
            logger.info("Entered text is not cleared in the search field text box");
            takeScreenshot("Entered text is not cleared");
        }

    }

    public void clickSelectAllCheckBox() {
        scrollUpDown("up");
        click(By.xpath("//table[@id='tblTransferMultipleItems']/thead/tr/th[1]/div"), "Select All", 30);
    }

    public void verifyAfterSelectAllCheckBoxClicked() {
        int row = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr/td[@class='uniqueName']"), 30).size();
        int checkBoxCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr/td/div/input[@checked='checked']"), 30).size();
        if (row == checkBoxCount) {
            getTest().log(LogStatus.PASS, "All the rows in the table are selected");
            logger.info("All the rows in the table are selected");
        } else {
            getTest().log(LogStatus.FAIL, "All the rows in the table are not selected");
            logger.info("All the rows in the table are not selected");
            takeScreenshot("Select all check box");
        }
    }

    public void enterAndVerifyCharacterInTransferQuantity() {
        enter(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), prop.getProperty("productName"), "Transfer Quantity", 30);
        String getTQuantity = getAtribute(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), "value", 30);
        if (!getTQuantity.equals(prop.getProperty("productName"))) {
            getTest().log(LogStatus.PASS, "Characters are not allowed to enter in transfer quantity field");
            logger.info("Characters is not allowed to enter in transfer field");
            enter(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), "1", "Transfer Quantity", 30);
            String getTransQuantity = getAtribute(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), "value", 30);
            if (getTransQuantity.equals("1")) {
                getTest().log(LogStatus.PASS, "Numeric value is allowed in the Transfer quantity field");
                logger.info("Numeric value is allowed in the Transfer quantity field");
            }

        } else {
            getTest().log(LogStatus.FAIL, "Characters are allowed to enter in transfer quantity field");
            logger.info("Characters are allowed to enter in transfer quantity field");
            takeScreenshot("CharacterTransferQuantity");
        }
    }

    public void verifyTransferQuantityField() {
        String transQuantity;
        String getTransferQuantity;
        String transferQuantity;
        String alertMsg;
        String getAvailableQuantity = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[7]//span/span"), 30).trim();
        int availableQuantity = Integer.parseInt(getAvailableQuantity);
        int tQuantity = availableQuantity + 1;
        transferQuantity = String.valueOf(tQuantity);
        enter(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), transferQuantity, "Transfer Quantity", 30);
        alertMsg = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        if (alertMsg.equals("Transfer quantity should not be greater than available quantity.")) {
            getTest().log(LogStatus.PASS, "Alert message is displayed as expected as" + alertMsg);
            logger.info("Alert message is displayed as expected as " + alertMsg);
        } else {
            getTest().log(LogStatus.FAIL, "Alert message is not displayed ");
            logger.info("Alert message is not displayed ");
            takeScreenshot("AlertMessage");
        }
        transQuantity = String.valueOf(availableQuantity);
        enter(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), transQuantity, "Transfer Quantity", 30);
        getTransferQuantity = getAtribute(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[3]/td[8]/input"), "value", 30);
        if (getTransferQuantity.equals(transQuantity)) {
            getTest().log(LogStatus.PASS, "Entered Transfer Quantity is displayed as expected as" + getTransferQuantity);
            logger.info("Entered Transfer Quantity is displayed as expected as" + getTransferQuantity);
        } else {
            getTest().log(LogStatus.FAIL, "Entered Transfer Quantity is displayed ");
            logger.info("Entered Transfer Quantity is displayed");
            takeScreenshot("EnteredQuantity");
        }

    }

    public void verifyPaginationFunctionalities() {
        String lastRecord;
        int recordCount;
        int defaultStartRecordCount;
        int defaultEndCount;
        int totalRecordCount;
        String[] defaultPaginationText;
        String[] secondPage;
        String[] firstPage;
        String[] nextPaginationText;
        String[] previousPaginationText;
        String[] lastPagePaginationText;
        String[] firstPagePaginationText;
        scrollToWebelement(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), "Pagination text");
        defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist']"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[ @class='itemAddlist'][" + recordCount + "]//td[@class='uniqueName']"), 15).trim();
            click(By.cssSelector("a[class='page-link']"), "Second Page", 20);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + lastRecord + "]//td[@class='uniqueName']"), 30);
            secondPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + lastRecord + "]//td[@class='uniqueName']"), 30);
            firstPage = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + lastRecord + "]//td[@class='uniqueName']"), 30);
            nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist']"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[ @class='itemAddlist'][" + recordCount + "]//td[@class='uniqueName']"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist']"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[ @class='itemAddlist'][" + recordCount + "]//td[@class='uniqueName']"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + lastRecord + "]//td[@class='uniqueName']"), 30);
            lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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
            recordCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist']"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + recordCount + "]//td[@class='uniqueName']"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']//tbody//tr[@class='itemAddlist'][" + lastRecord + "]//td[@class='uniqueName']"), 30);
            firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
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

    public void searchProduct() {
        selectLocation();
        selectProductType();
        selectProductName();
        clickSearchButton();
    }


    public void clickCheckBox() {
        scrollToWebelement(By.xpath("//table[@id='tblTransferMultipleItems']/thead"), "Table Header");
        getUniqueName = getText(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']/td[@class='uniqueName']"), 20).trim();
        click(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']/td/div"), "Check Box", 20);
    }

    public void AddProductToProductMoveToList() {
        clickAddListButton();
        scrollDown();
        click(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr[@class='itemAddlist']/td/div"), "Check Box", 20);
        WebElement element = findElementVisibility(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[4]"), 20);
        if (element != null) {
            String getActualUniqueName = element.getText().trim();
            if (getUniqueName.equals(getActualUniqueName)) {
                getTest().log(LogStatus.PASS, "Added product is displayed in Product Move To List section");
                logger.info("Added product is displayed in Product Move To List section");
            } else {
                getTest().log(LogStatus.FAIL, "Added product is not moved in Product Move To List section");
                logger.info("Added product is not moved in Product Move To List section");
                takeScreenshot("Product Move To List");
            }
        }

    }

    public void clickDeleteAndVerifyProductMoveToListTable() {
        String actualUName;
        clickCheckBox();
        String getUName = getText(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[4]"), 20);
        click(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[9]/a"), "Delete", 30);
        driver.switchTo().alert().accept();
        actualUName = getText(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[4]"), 20);
        if (!actualUName.equals(getUName)) {
            WebElement element = findElementVisibility(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[4]"), 20);
            if (element != null) {
                getTest().log(LogStatus.PASS, "Product is deleted from the Product move to List table");
                logger.info("Product is deleted from the Product move to List table");
            } else {
                getTest().log(LogStatus.FAIL, "Product is deleted from the Product move to List table");
                logger.info("Product is deleted from the Product move to List table");
                takeScreenshot("Delete");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Added product is not moved in Product Move To List section");
            logger.info("Added product is not moved in Product Move To List section");
            takeScreenshot("Product Move To List");
        }
    }

    public void clickAddListButton() {
        WebElement element = findElementVisibility(By.cssSelector("a#btn_AddRow"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Add to List Button is enabled");
            logger.info("Add to List Button is enabled");
            click(By.cssSelector("a#btn_AddRow"), "Add to List", 30);
        } else {
            getTest().log(LogStatus.FAIL, "Add to List Button is not enabled");
            logger.info("Add to List Button is not enabled");
            takeScreenshot("Add to list button");
        }
    }

    public void selectToLocation(String process) {
        WebElement element = findElementVisibility(By.xpath("//div[@class='btn form-control']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "Location dropdown is enabled");
            logger.info("Location dropdown is enabled");
            click(By.xpath("//div[@class='btn form-control']"), "Location dropdown", 20);
            List<WebElement> elements = findMultipleElement(By.xpath("//ul[@id='CompantLocationSelecttransferItem']//li//ol//li//a"), 20);
            for (WebElement ele : elements) {
                String loca = ele.getAttribute("data-text").trim();
                if (process.equalsIgnoreCase("Widget")) {
                    if (loca.equals(productManagerLocation)) {
                        ele.click();
                        break;
                    }
                } else {
                    if (!loca.equals(location)) {
                        ele.click();
                        break;
                    }
                }
            }
        }
        toLocation = getText(By.xpath("//div[@id='TransferItemsData']//span[contains(@class,'CompantLocationSelect')]"), 20).trim();
    }


    public void verifyHeaderForProductMoveToListTable() {
        String actualHeaderName;
        List<String> headerName = new ArrayList<>();
        headerName.add("Product Code");
        headerName.add("Product Name");
        headerName.add("Unique Name");
        headerName.add("Location");
        headerName.add("Total Quantity");
        headerName.add("Available Quantity");
        headerName.add("Transfer Quantity");
        headerName.add("Action");
        waitForElementInVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr[@class='itemAddlist']"), 30);

        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tbltransferiteminfoList']/thead/tr/th/span"), 20);
        for (String expectedHeader : headerName) {
            int i = 0;
            for (WebElement element : elements) {
                i++;
                actualHeaderName = element.getText().trim();
                if (expectedHeader.equals(actualHeaderName)) {
                    getTest().log(LogStatus.PASS, "The " + expectedHeader + " Header is displayed in the Product transfer Page searched Product move to list table");
                    logger.info("Pass - The " + expectedHeader + " Header is displayed in the Product transfer Page searched Product move to list table");
                    break;
                } else if (i == elements.size()) {
                    getTest().log(LogStatus.FAIL, "The " + expectedHeader + " Header is not displayed in the Product transfer Page searched Product move to list table");
                    logger.info("Pass - The " + expectedHeader + " Header is not displayed in the Product transfer Page searched Product move to list table");
                    takeScreenshot("HeaderName");
                }
            }
        }
    }

    public void moveAllTheProductToList() {

        int row = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr/td[@class='uniqueName']"), 30).size();
        int checkBoxCount = findMultipleElement(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr/td/div/input[@checked='checked']"), 30).size();
        if (row == checkBoxCount) {
            clickAddListButton();
            scrollDown();
            int rowCount = findMultipleElement(By.xpath("//table[@id='tbltransferiteminfoList']/tbody/tr/td[4]"), 20).size();
            if (row == rowCount) {
                getTest().log(LogStatus.PASS, "All the products are moved to the Product move to list table");
                logger.info("All the products are moved to the Product move to list table");
            } else {
                getTest().log(LogStatus.FAIL, "All the products are not moved to the Product move to list table");
                logger.info("All the products are not moved to the Product move to list table");
                takeScreenshot("Product move to list");
            }
        }

    }

    public void saveTheProductToAnotherLocationAndVerify(String process) {
        searchProduct();
        clickCheckBox();
        AddProductToProductMoveToList();
        selectToLocation(process);
        click(By.cssSelector("a#saveBtn"), "Save", 20);
        staticWait(3000);
    }

    public void navigateToInTransitPage() {
        click(By.cssSelector("a#ancInTransitItems"), "In Transit", 20);

    }

    public void clickExpandAllOnInTransitPage() {
        click(By.xpath("//div[@class='bottom_filter_button']/a"), "Expand All", 20);
    }

    public void selectLocationOnly() {
        click(By.cssSelector(" span.CompantLocationSelectselected"), "Location", 20);
        click(By.xpath("//ol//span[text()='" + toLocation + "']"), "To Location", 30);

    }

    public void enterProductNameOrCode() {
        enter(By.cssSelector("input#search"), productName, "Product Name", 30);
    }

    public void clickSearch() {
        click(By.cssSelector("a#Go"), "Search", 30);
        waitForLoader(30);
    }

    public void verifyRequestIsCreated() {
        waitForLoad(30);
        String actualLocation = getText(By.xpath("//table[@id='tblProjectList']/tbody/tr/td[3]/span"), 60, 30).trim();
        String actualProductName = getText(By.xpath("//table[@id='tblProjectList']/tbody/tr/td[1]/span"), 60, 30).trim();
        if (actualProductName.equals(productName)) {
            if (actualLocation.equals(toLocation)) {
                getTest().log(LogStatus.PASS, "Product transfer to the location \"" + actualLocation + "\" request is created on IN-Transit Page");
                logger.info("Product transfer to the location \"" + actualLocation + "\" request is created on IN-Transit Page");
            } else {
                getTest().log(LogStatus.FAIL, "Product transfer to the location \"" + actualLocation + "\" request is not created on IN-Transit Page");
                logger.info("Product transfer to the location \"" + actualLocation + "\" request is not created on IN-Transit Page");
                takeScreenshot("Request on IN-Transit");
            }

        } else {
            getTest().log(LogStatus.FAIL, "Searched product is not displayed in the table");
            logger.info("Searched product is not displayed in the table");
            takeScreenshot("Searched Product");
        }

    }

    public void clickCancelButton() {
        click(By.cssSelector("a#cancelBtn"), "Cancel", 20);
    }

    public void verifyResultTableAfterCancel() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblTransferMultipleItems']/tbody/tr"), 30);
        if (element == null) {
            getTest().log(LogStatus.PASS, "All details is cleared in Product Transfer Page");
            logger.info("All details is cleared in Product Transfer Page");
        } else {
            getTest().log(LogStatus.FAIL, "All details is not cleared in Product Transfer Page");
            logger.info("All details is not cleared in Product Transfer Page");
            takeScreenshot("AllDetailsNotClear");
        }
    }

    public void clickUserGuide() {
        click(By.cssSelector("a[class='ancuserguide']"), "User guide", 20);
    }

    public void verifyUserGuide() {
        clickUserGuide();
        WebElement element = findElementVisibility(By.cssSelector("div[class='user-guide-content']"), 20);
        if (element != null) {
            getTest().log(LogStatus.PASS, "User guide content is displayed on Product transfer page");
            logger.info("User guide content is displayed on Product transfer page");
            clickUserGuide();
        } else {
            getTest().log(LogStatus.FAIL, "User guide content is not displayed on Product transfer page");
            logger.info("User guide content is not displayed on Product transfer page");
            takeScreenshot("UserGuideContent");
        }
    }

    public void goToDeployPage() {
        click(By.cssSelector("a#btnNext"), "Next button", 10);
        WebElement ele = findElementVisibility(By.xpath("//div[@role='alert']"), 20);
        if (ele != null) {
            getTest().log(LogStatus.FAIL, "Error Message is displayed as " + ele.getText().trim());
            logger.info("Error Message is displayed as " + ele.getText().trim());
            takeScreenshot("Product Code Error message");
            click(By.xpath("//div[@role='alert']//button[@type='button']"), "Close", 20);
            click(By.xpath("//div[@id='juda-menu']//a[normalize-space(text())='Manage Product']"), "Manage product", 20);
            openProductCreatedProduct();
            click(By.cssSelector("a#btnNext"), "Next", 20);
        }
    }

    public void openProductCreatedProduct() {
        click(By.xpath("//table[@id='tablelistingdata']/tbody/tr/td[5]/a/span[normalize-space(text())='" + productName + "']"), "open the Product", 20);
    }

    public void manageProduct() {
        findElementClickable(By.xpath("//div[@id='juda-menu']//ul//li//a[normalize-space(text())='Manage Product']"), 20);
        click(By.xpath("//div[@id='juda-menu']//ul//li//a[normalize-space(text())='Manage Product']"), "Manage Product", 20);
    }

    public void expandProductName() {
        click(By.xpath("//span[normalize-space(text())='Product Name/Code']"), "Expand Product Name", 20);
    }
}
