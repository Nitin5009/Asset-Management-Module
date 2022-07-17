package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Drivers;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class MyProductPage extends WebBasePage {

    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    WebDriver driver;
    DeployProductPage deployProduct;
    ProductListingPage productListing;
    ProductTypePage productType;
    ProductContainerPage productContainerPage;
    ProductAssignmentPage productAssignment;
    AttachmentsPage attachmentsPage;
    int beforeSearchListCount;
    String selectedBarCodeType;
    boolean termsAndCondition;
    String requestedProductName;
    String returnRequestedName;
    String containedProductName;
    Dictionary<String, String> barCodeTypes = new Hashtable<>();

    public MyProductPage(WebDriver driver) {
        super(driver, "My Product Page");
        this.deployProduct = new DeployProductPage(driver);
        this.productListing = new ProductListingPage(driver);
        this.productType = new ProductTypePage(driver);
        this.productContainerPage = new ProductContainerPage(driver);
        this.productAssignment = new ProductAssignmentPage(driver);
        this.attachmentsPage = new AttachmentsPage(driver);
        this.driver = driver;
    }

    public void openMyProductPage() {
        WebElement element = findElementVisibility(By.xpath("//ul[@data-p-name='Asset Management ']//a[text()='My Products']"), 20);
        if (element != null) {
            click(By.xpath("//ul[@data-p-name='Asset Management ']//a[text()='My Products']"), "My Products menu", 20);
        } else {
            openMyProductPage();
        }
    }

    public void verifyMyProductPage() {
        WebElement myProductHeading = findElementVisibility(By.xpath("//div[contains(@class,'theme-primary partition')]/span[normalize-space(text())='My Products']"), 30);
        if (myProductHeading != null) {
            getTest().log(LogStatus.PASS, "My product page is displayed when click on the My Product Page menu");
            logger.info("My product page is displayed when click on the My Product Page menu");
        } else {
            getTest().log(LogStatus.FAIL, "My product page is not displayed when click on the My Product Page menu");
            logger.info("My product page is not displayed when click on the My Product Page menu");
            takeScreenshot("MyProductPage");
        }
    }

    public void createNewProduct(boolean uniqueName, String barCodeType, String productTypeName, String quantity, String locationName) {
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
        deployProductPage.clickLocationDropdown();
        deployProductPage.selectLocationValueFromDropdown(locationName);
        productLocationsToAssign.add(getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30));
        deployProductPage.enterQuantity(quantity);
        deployProductPage.enterUnitPrice("1");
        deployProductPage.enterProductCost("1");
        deployProductPage.clickAddListButton();
        deployProductPage.clickSaveButton();
        deployProductPage.handleSuccessPopup();
        deployProductPage.clickNextButton();
        productName = getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td//a[@class='editinfo']"), 20).trim();
        barCodeValue = findElementVisibility(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td//span[contains(@class,'d-block')]"), 20).getText().trim();
        productsToAssign.add(productName);
        barCodeValues.add(barCodeValue);
        barCodeTypes.put(productName, selectedBarCodeType);
        deployProductPage.clickNextButton();
        attachmentsPage.enterAttachmentName();
        if (termsAndCondition) {
            attachmentsPage.selectTermsAndConditionsYes();
        }
        attachmentsPage.uploadAttachment();
        attachmentsPage.clickSaveButton();
//        deployProduct.handleSuccessPopup();
    }

    public void assignProductToUser(int assignCount, boolean halfCount, String processName, String userName) {
        int i = assignCount - 1;
        if (userName.equalsIgnoreCase("companyAdmin")) {
            userName = prop.getProperty("companyAdmin");
        } else {
            userName = prop.getProperty("secondUser");
        }
        productLocationToAssign = productLocationsToAssign.get(i);
        productTypeToAssign = productTypesToAssign;
        productNameToAssign = productNamesToAssign.get(i);
        productToAssign = productsToAssign.get(i);

        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        productAssignment.navigateToProductAssignmentPage();
        productListing.clickAddProductToAssign();
        click(By.cssSelector("div>select#IssuedTo"), "Select User dropdown", 30);
        click(By.xpath("//div//select[@id='IssuedTo']//option[contains(text(),'" + userName + "')]"), userName, 20);
        String[] startTimeArray = getAtribute(By.cssSelector("div>input#StartTime"), "value", 20).split(":");
        String time = String.format("%d", Integer.parseInt(startTimeArray[0])) + ":" + startTimeArray[1];
        assignFromDate = getAtribute(By.cssSelector("div>input#AssignedFrom"), "value", 20) + " " + time;
        String[] endTimeArray = getAtribute(By.cssSelector("div>input#EndTime"), "value", 20).split(":");
        String endTime = String.format("%d", Integer.parseInt(endTimeArray[0])) + ":" + endTimeArray[1];
        assignTillDate = getAtribute(By.cssSelector("div>input#AssignedTill"), "value", 20) + " " + endTime;
        productListing.selectAssignProductLocation();
        productListing.selectAssignProductType();
        productListing.selectAssignProductName();
        productListing.selectAssignProduct();
        productListing.searchAssignProduct();
        if (termsAndCondition) {
            click(By.xpath("//table[@id='tblassestgroupinfodetails']//tbody//tr[1]//td[6]//div"), "Terms and Condition", 20);
        }
        productListing.addAssignProductToList();
        if (halfCount) {
            int totalQuantity = Integer.parseInt(getText(By.xpath("//table[@id='tblassestassignmentinfo']//tbody//tr[1]//td[8]"), 20).trim());
            enter(By.xpath("//input[contains(@class,'txtAssetQuentity')]"), String.valueOf(totalQuantity / 2), "Quantity to assign", 20);
        }
        if (processName.equalsIgnoreCase("Container")) {
            expandContainerProd();
            enterUniqueNameOfContainedProd();
        }
        productListing.saveAssignProduct();
        productListing.navigateToManageProductPage();
    }

    public void createAssignConsumableProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("Consumable");
        createNewProduct(false, "barcode", productTypeName, "1", "");
        assignProductToUser(1, false, "SingleProduct", "normalUser");
    }

    public void createAssignProductForRequestQuantity() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("requestQuantity");
        createNewProduct(false, "barcode", productTypeName, "5", "");
        assignProductToUser(1, true, "SingleProduct", "normalUser");
    }

    public void verifyAssignedProduct() {
        int iteration = 0;
        List<WebElement> actualProductList = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td[1]/span"), 30);
        if (actualProductList.size() != 0) {
            for (WebElement actualProduct : actualProductList) {
                iteration++;
                String actualName = actualProduct.getText().trim();
                if (actualName.equalsIgnoreCase(productToAssign)) {
                    getTest().log(LogStatus.PASS, "Assigned product is displayed in the table");
                    logger.info("Assigned product is displayed in the table");
                    break;
                } else if (actualProductList.size() == iteration) {
                    getTest().log(LogStatus.FAIL, "Assigned product is not displayed in the table");
                    logger.info("Assigned product is not displayed in the table");
                    takeScreenshot("AssignedProd");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Assigned product is not displayed in the table");
            logger.info("Assigned product is not displayed in the table");
            takeScreenshot("AssignedProd");
        }
    }

    public void enterBarCodeValueInSearch(String barCodeValue) {
        enter(By.cssSelector("div>input#Search"), barCodeValue, "Bar Code Search box", 30);
    }

    public void enterAlphaNumericInSearch() {
        enterBarCodeValueInSearch(prop.getProperty("alphaNumericModelName"));
    }

    public void verifyAlphaNumericInSearch() {
        String actualTextInSearch = getAtribute(By.cssSelector("div>input#Search"), "value", 20);
        if (actualTextInSearch.equalsIgnoreCase(prop.getProperty("alphaNumericModelName"))) {
            getTest().log(LogStatus.PASS, "My product page search field accept the alpha numeric value as expected - " + actualTextInSearch);
            logger.info("My product page search field accept the alpha numeric value as expected - " + actualTextInSearch);
        } else {
            getTest().log(LogStatus.FAIL, "My product page search field not accept the alpha numeric value - " + actualTextInSearch);
            logger.info("My product page search field not accept the alpha numeric value - " + actualTextInSearch);
            takeScreenshot("SearchField");
        }
    }

    public void searchBarCodeValue() {
        beforeSearchListCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 30).size();
        enterBarCodeValueInSearch(barCodeValues.get(0));
        productListing.clickSearchButton();
    }

    public void verifySearchedBarCodeValue() {
        String actualBarCode;
        int resultSize = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[6]/img"), 30).size();
        if (resultSize == 1) {
            actualBarCode = getAtribute(By.xpath("//table[@id='tblProjectList']//tbody//tr//td[6]/img"), "data-barcode", 20);
            if (actualBarCode.equalsIgnoreCase(barCodeValues.get(0))) {
                getTest().log(LogStatus.PASS, "Search result for bar code value " + barCodeValues.get(0) + " is displayed as expected");
                logger.info("Search result for bar code value " + barCodeValues.get(0) + " is displayed as expected");
            } else {
                getTest().log(LogStatus.FAIL, "Search result for bar code value " + barCodeValues.get(0) + " is not displayed as expected");
                logger.info("Search result for bar code value " + barCodeValues.get(0) + " is not displayed as expected");
                takeScreenshot("BarCodeSearch");
            }
        } else {
            getTest().log(LogStatus.FAIL, "Search result for bar code value " + barCodeValues.get(0) + " is not displayed as expected");
            logger.info("Search result for bar code value " + barCodeValues.get(0) + " is not displayed as expected");
            takeScreenshot("BarCodeSearch");
        }
    }

    public void resetBarCodeSearch() {
        click(By.cssSelector("a#clear"), "Bar code search reset", 20);
    }

    public void resultOfReset() {
        int afterResetListCount = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 30).size();
        if (beforeSearchListCount == afterResetListCount) {
            getTest().log(LogStatus.PASS, "All records are displayed as expected when click on the reset button");
            logger.info("All records are displayed as expected when click on the reset button");
        } else {
            getTest().log(LogStatus.FAIL, "Search result is not reset and all records are not displayed when click reset button");
            logger.info("Search result is not reset and all records are not displayed when click reset button");
        }
    }

    public List<String> getActualSortedArray() {
        click(By.cssSelector("#ASSET"), "Product header", 20);
        waitForLoad(10);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        staticWait(1500);
        List<WebElement> MainElements = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr[@data-groupindex]//td[1]//span"), 30);
        for (WebElement element : MainElements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                MainElements = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr[@data-groupindex]//td[1]//span"), 30);
                for (WebElement element : MainElements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 10);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }
        return actualSortedArray;
    }

    public void verifyContainerProductSorting(String order) {
        scrollToWebelement(By.xpath("//span[normalize-space(text())='My Products']"), "Page Header");
        click(By.cssSelector("#ASSET"), "Product header", 20);
        waitForLoad(10);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        staticWait(1500);
        int blueLegendCount = findMultipleElement(By.xpath("//a[contains(@class,'arrowwhitecollapse_roles')]"), 30).size();
        for (int i = 1; i <= blueLegendCount; i++) {
            List<String> actualSortedArray = new ArrayList<>();
            String attrValue = getAtribute(By.xpath("(//a[contains(@class,'arrowwhitecollapse_roles')])[1]//parent::td//parent::tr"), "data-groupindex", 20);
            click(By.xpath("(//a[contains(@class,'arrowwhitecollapse_roles')])[1]"), "Expand First Container Product", 20);
            scrollToWebelement(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + attrValue + "]"), "Row Number - " + attrValue);
            List<WebElement> containerProduct = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr[@data-groupid='groupid_" + attrValue + "']/td[1]/span"), 30);
            for (WebElement containerProd : containerProduct) {
                actualSortedArray.add(containerProd.getText());
            }
            verifyOrder(order, actualSortedArray, "sub");
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                blueLegendCount = findMultipleElement(By.xpath("//a[contains(@class,'arrowwhitecollapse_roles')]"), 30).size();
                for (int i = 1; i <= blueLegendCount; i++) {
                    List<String> actualSortedArray = new ArrayList<>();
                    String attrValue = getAtribute(By.xpath("(//a[contains(@class,'arrowwhitecollapse_roles')])[1]//parent::td//parent::tr"), "data-groupindex", 20);
                    click(By.xpath("(//a[contains(@class,'arrowwhitecollapse_roles')])[1]"), "Expand First Container Product", 20);
                    scrollToWebelement(By.xpath("//table[@id='tblProjectList']//tbody//tr[" + attrValue + "]"), "Row Number - " + attrValue);
                    List<WebElement> containerProduct = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr[@data-groupid='groupid_" + attrValue + "']/td[1]/span"), 30);
                    for (WebElement containerProd : containerProduct) {
                        actualSortedArray.add(containerProd.getText());
                    }
                    verifyOrder(order, actualSortedArray, "sub");
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
    }

    public void verifyOrder(String order, List<String> actualSortedArray, String type) {
        String product;
        if (type.equalsIgnoreCase("main")) {
            product = "Products";
        } else {
            product = "Container Products";
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, product + " are sorted in ascending Order when click the Product header for one time");
                logger.info(product + " are sorted in ascending Order when click the Product header for one time");
            } else {
                getTest().log(LogStatus.FAIL, product + " are not sorted in ascending Order when click the Product header for one time");
                logger.info(product + " are not sorted in ascending Order when click the Product header for one time");
                takeScreenshot("ProductSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, product + " are sorted in descending Order when click the Product header for two times");
                logger.info(product + " are sorted in descending Order when click the Product header for two times");
            } else {
                getTest().log(LogStatus.FAIL, product + " are not sorted in descending Order when click the Product header for two times");
                logger.info(product + " are not sorted in descending Order when click the Product header for two times");
                takeScreenshot("ProductSort");
            }
        }
    }

    public void verifyMainProductAscending() {
        List<String> actualArray = getActualSortedArray();
        verifyOrder("ascending", actualArray, "main");
    }

    public void verifyMainProductDescending() {
        List<String> actualArray = getActualSortedArray();
        verifyOrder("descending", actualArray, "main");
    }

    public void verifySubProductAscending() {
        verifyContainerProductSorting("ascending");
    }

    public void verifySubProductDescending() {
        verifyContainerProductSorting("descending");
    }

    public void verifyBarCodeTypes() {
        int iteration = 0;
        String actualBarCodeValue;
        String expectedBarCodeValue;
        for (String productName : productsToAssign) {
            WebElement element = findElementVisibility(By.xpath("//span[text()='" + productName + "']//parent::td//parent::tr//td[6]/img"), 30);
            actualBarCodeValue = element.getAttribute("data-barcode");
            expectedBarCodeValue = barCodeValues.get(iteration);
            if (expectedBarCodeValue.equalsIgnoreCase(actualBarCodeValue)) {
                getTest().log(LogStatus.PASS, "The Bar code for the product " + productName + " is displayed as expected");
                logger.info("The Bar code for the product " + productName + " is displayed as expected");
            } else {
                getTest().log(LogStatus.FAIL, "The Bar code for the product " + productName + " is not displayed as created");
                logger.info("The Bar code for the product " + productName + " is not displayed as created");
                takeScreenshot("BarCodeType");
            }
            iteration++;
        }
    }

    public void createProductType(String toggleName) {
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
        if (toggleName.equalsIgnoreCase("empAccept") || toggleName.equalsIgnoreCase("requestQuantity")) {
            productType.enableEmployeeAcceptanceToggle();
        }
        if (!toggleName.equalsIgnoreCase("requestQuantity")) {
            productType.enableSelfCheckOutToggle();
        }
        if (toggleName.equalsIgnoreCase("SelfTransfer")) {
            productType.enableIsTransferableToggle();
        }
        if (toggleName.equalsIgnoreCase("InsurancePolicy")) {
            productType.enableInsurancePolicyToggle();
        }

        if (toggleName.equalsIgnoreCase("TermsAndCond")) {
            productType.enableTermsAndConditionsToggle();
            termsAndCondition = true;
        }
        productType.enableProductCostToggle();
        productType.clickSaveButton();
        productType.handelSuccessPopup();
    }

    public void createAndAssignProductForEmpAccept() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        String[] barCodeType = {"qrCode", "barCode"};
        createProductType("EmpAccept");
        for (int i = 1; i <= 2; i++) {
            createNewProduct(false, barCodeType[i - 1], productTypeName, "1", "");
            assignProductToUser(i, false, "EmpAccept", "normalUser");
        }
    }

    public void createAndAssignProductTypeWithSelfCheckOut() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("SelfCheckOut");
        createNewProduct(false, "qrCode", productTypeName, "10", "");
        assignProductToUser(1, true, "SelfCheckOut", "normalUser");
    }

    public void createAndAssignProdForContainer() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("Container");
    }

    public void getLatestContainerProductType() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        productType.clickProductType();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        waitForLoader(20);
        List<WebElement> containerStatus = findMultipleElement(By.xpath("//table[@id='tblAsset']//tr//td[6]"), 30);
        int iteration = 0;
        for (WebElement ele : containerStatus) {
            iteration++;
            if (ele.getText().equalsIgnoreCase("Yes")) {
                WebElement txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//span"), 1);
                if (txtBoxStatus == null) {
                    txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//select//option[@selected]"), 1);
                }
                if (txtBoxStatus.getText().equalsIgnoreCase("Active")) {
                    String actualName = getText(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[4]//a"), 5);
                    containerProductType.add(actualName);
                    break;
                }
            }
        }
    }

    public void getNameOfTheContainedProd() {
        int availableQuantity = 0;
        int iteration = 1;
        while (availableQuantity < 1) {
            iteration++;
            deployProduct.clickFullMenuDropDown();
            deployProduct.clickAssetManagement();
            deployProduct.clickManageProduct();
            deployProduct.make100PageSize();
            containedProductName = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + iteration + "]//td/a[@id='ancEditAssetType']/span"), 30);
            if (!containedProductName.equalsIgnoreCase(createdProductName)) {
                click(By.xpath("//table[@id='tablelistingdata']//tbody//tr[" + iteration + "]//td/a[@id='ancEditAssetType']/span"), containedProductName, 30);
                click(By.xpath("//li[@class='nav-item']//a[normalize-space(text())='Related Information']"), "Related Information tab", 40);
                staticWait(1000);
                availableQuantity = Integer.parseInt(getText(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr[1]//td[5]//span"), 30));
            }
        }
    }

    public void selectProductNameForContainer() {
        click(By.xpath("//table[@id='tbContainer']//tbody//tr[1]//td[1]/span[@class='multiselect-native-select']"), "Product name dropdown", 20);
        enter(By.xpath("//select[@name='ddlAssetCatalogId']//parent::span//input[@type='text']"), containedProductName, "Product Name search", 20);
        waitForLoader(20);
        click(By.xpath("//label[normalize-space(text())='" + containedProductName + "']"), "Searched Option", 20);
        waitForLoader(20);
    }

    public void assignContainerProdToUser() {
        productLocationsToAssign.add(productLocationForContainer);
        productTypesToAssign = productTypeForContainer;
        productNamesToAssign.add(createdProductName);
        productsToAssign.add(selectedContainer);
        assignProductToUser(1, false, "Container", "normalUser");
    }

    public void createAndAssignProdForShare() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createNewProduct(true, "barCode", "", "1", "");
        assignProductToUser(1, false, "ProdShare", "normalUser");
    }

    public void createAndAssignProdForTermsAndCondition() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("TermsAndCond");
        createNewProduct(true, "barCode", productTypeName, "1", "");
        assignProductToUser(1, false, "TermsAndCondition", "normalUser");
    }

    public void createAndAssignProdForTermsAndConditionCA() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("TermsAndCond");
        createNewProduct(true, "barCode", productTypeName, "1", "");
        assignProductToUser(1, false, "TermsAndCondition", "companyAdmin");
    }

    public void createAndAssignProdForReturn() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("");
        createNewProduct(false, "barCode", productTypeName, "1", "");
        assignProductToUser(1, false, "RequestForReturn", "NormalUser");
    }

    public void createProductForBarCodeSearch() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("");
        createNewProduct(false, "barCode", productTypeName, "1", "");
    }

    public void verifyRedLegend() {
        for (int i = 0; i <= 1; i++) {
            String num = findElementPresence(By.xpath("//span[text()='" + productsToAssign.get(i) + "']//parent::td//parent::tr"), 20).getAttribute("data-groupindex");
            String script = "return window.getComputedStyle(document.querySelector('tr:nth-child(" + num + ")>td:nth-child(1)'),':after').getPropertyValue('background-color')";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String content = (String) js.executeScript(script);
            if (content.equals("rgb(222, 3, 3)")) {
                getTest().log(LogStatus.PASS, "Red color stripe is displayed as expected for the product " + productsToAssign.get(i) + " when create product under product type with employee acceptance toggle");
                logger.info("Red color stripe is displayed as expected for the product " + productsToAssign.get(i) + "when create product under product type with employee acceptance toggle");
            } else {
                getTest().log(LogStatus.FAIL, "Red color stripe is not displayed for the product " + productsToAssign.get(i) + " when create product under product type with employee acceptance toggle");
                logger.info("Red color stripe is not displayed for the product " + productsToAssign.get(i) + " when create product under product type with employee acceptance toggle");
                takeScreenshot("RedLegend");
            }
        }
    }

    public void clickMoreIcon(String prodName) {
        waitForLoad(30);
        staticWait(1000);
        findElementVisibility(By.xpath("//div[@class='wrapper']/div[contains(@class,'chat-btn-hide')]"), 180);
        findElementVisibility(By.xpath("//table[@id='tblProjectList']//tbody//span[text()='" + prodName + "']/../..//td/span[contains(@class,'mobileaction')]"), 180);
        click(By.xpath("//table[@id='tblProjectList']//tbody//span[text()='" + prodName + "']/../..//td/span[contains(@class,'mobileaction')]"), "More Icon", 60);
        staticWait(500);
    }

    public void clickAcceptIconOfProduct() {
        productsToAssign.add("");
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        click(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr//span[@id='action-list']/a[@data-original-title='Accept']"), "Accept option for" + prodName, 20);
        waitForLoader(20);
        verifyAcceptedProduct(prodName);
    }

    public void verifyAcceptedProduct(String prodName) {
        String num = findElementPresence(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr"), 20).getAttribute("data-groupindex");
        String script = "return window.getComputedStyle(document.querySelector('tr:nth-child(" + num + ")>td:nth-child(1)'),':after').getPropertyValue('background-color')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String content = (String) js.executeScript(script);
        if (content.equals("rgba(0, 0, 0, 0)")) {
            getTest().log(LogStatus.PASS, "The product " + prodName + " is successfully accepted when click on the accept icon");
            logger.info("The product " + prodName + " is successfully accepted when click on the accept icon");
        } else {
            getTest().log(LogStatus.FAIL, "The product " + prodName + " is not accepted when click on the accept icon");
            logger.info("The product " + prodName + " is not accepted when click on the accept icon");
            takeScreenshot("RedLegend");
        }
    }

    public void clickRejectIconOfProduct() {
        String prodName = productsToAssign.get(1);
        clickMoreIcon(prodName);
        click(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr//span[@id='action-list']/a[@data-original-title='Reject']"), "Reject option for" + prodName, 20);
        waitForLoader(20);
        verifyRejectedProduct(prodName);
    }

    public void verifyRejectedProduct(String prodName) {
        WebElement rejectedProd = findElementVisibility(By.xpath("//span[text()='" + prodName + "']"), 10);
        if (rejectedProd == null) {
            getTest().log(LogStatus.PASS, "The product " + prodName + " is successfully rejected when click on the reject icon");
            logger.info("The product " + prodName + " is successfully rejected when click on the reject icon");
        } else {
            getTest().log(LogStatus.FAIL, "The product " + prodName + " is not rejected when click on the reject icon");
            logger.info("The product " + prodName + " is not rejected when click on the reject icon");
            takeScreenshot("EmpReject");
        }
    }

    public void verifyYellowLegend() {
        String prodName = productsToAssign.get(0);
        String num = findElementPresence(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr"), 20).getAttribute("data-groupindex");
        String script = "return window.getComputedStyle(document.querySelector('tr:nth-child(" + num + ")>td:nth-child(1)'),':after').getPropertyValue('background-color')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String content = (String) js.executeScript(script);
        if (content.equals("rgb(251, 198, 72)")) {
            getTest().log(LogStatus.PASS, "Yellow color stripe is displayed as expected for the product " + prodName + " when request for return is created");
            logger.info("Yellow color stripe is displayed as expected for the product " + prodName + " when request for return is created");
        } else {
            getTest().log(LogStatus.FAIL, "Yellow color stripe is not displayed for the product " + prodName + " when request for return is created");
            logger.info("Yellow color stripe is not displayed for the product " + prodName + " when request for return is created");
            takeScreenshot("YellowLegend");
        }
    }

    public void expandContainerProd() {
        click(By.xpath("//table[@id='tblassestassignmentinfo']//tbody//tr[1]//span[@id='cataloguegroup_id']"), "Container prod expand", 20);
    }

    public void enterUniqueNameOfContainedProd() {
        String containedProdName = getText(By.xpath("//table[@id='tblassestassignmentinfo']//tbody//tr[contains(@class,'insertedrowchild')]//td[4]"), 20);
        enter(By.xpath("//table[@id='tblassestassignmentinfo']//tbody//tr[contains(@class,'insertedrowchild')]//td[6]/input[@name='txtcsUniqueName']"), containedProdName.split("")[0], "Contained Prod Unique name", 20);
        click(By.xpath("//div[@class='unique_dynamicdatalist']//li[1]"), "unique name for " + containedProdName, 20);
    }

    public void verifyBlueLegend() {
        String prodName = productsToAssign.get(0);
        String num = findElementPresence(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr"), 20).getAttribute("data-groupindex");
        String script = "return window.getComputedStyle(document.querySelector('tr:nth-child(" + num + ")>td:nth-child(1)'),':after').getPropertyValue('background-color')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String content = (String) js.executeScript(script);
        if (content.equals("rgb(76, 180, 251)")) {
            getTest().log(LogStatus.PASS, "Blue color stripe is displayed as expected for the product " + prodName + " when product under product type with container");
            logger.info("Blue color stripe is displayed as expected for the product " + prodName + " when product under product type with container");
        } else {
            getTest().log(LogStatus.FAIL, "Blue color stripe is not displayed for the product " + prodName + " when product under product type with container");
            logger.info("Blue color stripe is not displayed for the product " + prodName + " when product under product type with container");
            takeScreenshot("BlueLegend");
        }
    }

    public void clickConsumeOption() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        click(By.xpath("//span[text()='" + prodName + "']/../..//td//a[@data-original-title='Consume']"), "Consume button", 20);
    }

    public void verifyConsumePopup() {
        WebElement consumeProductElement = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Consume Product')]"), 30);
        if (consumeProductElement != null) {
            getTest().log(LogStatus.PASS, "Consume Product popup is displayed as expected when click on the consume button");
            logger.info("Consume Product popup is displayed as expected when click on the consume button");
        } else {
            getTest().log(LogStatus.FAIL, "Consume Product popup is not displayed when click on the consume button");
            logger.info("Consume Product popup is not displayed when click on the consume button");
            takeScreenshot("ConsumeProdPopup");
        }
    }

    public void enterAmountOfProduct(String limit) {
        String productQuantity;
        int temp = 1;
        productQuantity = getText(By.xpath("//span[@id='spnOutOF']"), 20).split(" of ")[1];
        if (limit.equalsIgnoreCase("More")) {
            temp = Integer.parseInt(productQuantity) + 1;
        } else if (limit.equalsIgnoreCase("Less")) {
            temp = Integer.parseInt(productQuantity) - 1;
        } else {
            temp = Integer.parseInt(productQuantity);
        }
        productQuantity = String.valueOf(temp);
        enter(By.xpath("//input[@name='NumberOfItems']"), productQuantity, "Product quantity", 20);
    }

    public void verifyQuantityFieldError(String limit) {
        String errorMsgForQuantity = getAtribute(By.xpath("//input[@name='NumberOfItems']"), "class", 10);
        if (limit.equalsIgnoreCase("More")) {
            if (errorMsgForQuantity.equalsIgnoreCase("form-control is-invalid")) {
                getTest().log(LogStatus.PASS, "Error message is displayed as expected when enter quantity more than the available quantity");
                logger.info("Error message is displayed as expected when enter quantity more than the available quantity");
            } else {
                getTest().log(LogStatus.FAIL, "Error message is not displayed when enter quantity more than the available quantity");
                logger.info("Error message is not displayed when enter quantity more than the available quantity");
                takeScreenshot("QuantityError");
            }
        } else {
            if (!errorMsgForQuantity.equalsIgnoreCase("form-control is-invalid")) {
                getTest().log(LogStatus.PASS, "Error message is not displayed as expected when enter consume quantity less than or equal to the available quantity");
                logger.info("Error message is not displayed as expected when enter consume quantity less than or equal to the available quantity");
            } else {
                getTest().log(LogStatus.FAIL, "Error message is displayed when enter consume quantity less than or equal to the available quantity");
                logger.info("Error message is displayed when enter consume quantity less than or equal to the available quantity");
                takeScreenshot("QuantityError");
            }
        }
    }

    public void verifyPredefineValueInPopup(String popupName) {
        String field;
        String fieldText;
        int iteration = 0;
        WebElement prodName = findElementVisibility(By.xpath("//lable[@id='lbl-catalogName']//parent::span"), 20);
        WebElement uniqueName = findElementVisibility(By.xpath("//lable[@id='lbl-uniqeName']//parent::span"), 20);
        WebElement assignedFrom = findElementVisibility(By.xpath("//lable[@id='lbl-assignedFrom']//parent::span"), 20);
        WebElement assignedTill = findElementVisibility(By.xpath("//lable[@id='lbl-assignedTill']//parent::span"), 20);
        WebElement[] preDefinedFields = {prodName, uniqueName, assignedFrom, assignedTill};
        String[] values = {productNamesToAssign.get(0), productsToAssign.get(0), assignFromDate, assignTillDate};
        String[] fieldNames = {"Product Name", "Unique Name", "Assigned From", "Assigned Till"};
        for (WebElement preDefinedField : preDefinedFields) {
            field = preDefinedField.getCssValue("cursor");
            fieldText = preDefinedField.getText();
            if (field.equalsIgnoreCase("not-allowed") && fieldText.equalsIgnoreCase(values[iteration])) {
                String details = fieldNames[iteration] + " field is predefined with value " + values[iteration] + " as expected in " + popupName;
                getTest().log(LogStatus.PASS, details);
                logger.info(details);
            } else {
                String details = fieldNames[iteration] + " field is not predefined with expected value " + values[iteration] + " in " + popupName;
                getTest().log(LogStatus.FAIL, details);
                logger.info(details);
                takeScreenshot("PredefinedField");
            }
            iteration++;
        }
    }

    public void enterMoreConsumeQuantity() {
        enterAmountOfProduct("More");
    }

    public void verifyMoreConsumeQuantityError() {
        verifyQuantityFieldError("More");
    }

    public void enterConsumeReMark(String remarkComment) {
        enter(By.cssSelector("textarea#txtRemarks"), remarkComment, "Consume Remark", 20);
    }

    public void verifyConsumeRemarkAlphaNumeric() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterConsumeReMark(expectedValue);
        String actualValue = getAtribute(By.cssSelector("textarea#txtRemarks"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Remark field in the Consume popup is accepts the alpha numeric value as expected");
            logger.info("The Remark field in the Consume popup is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field in the Consume popup is not accepts the alpha numeric value");
            logger.info("The Remark field in the Consume popup is not accepts the alpha numeric value");
            takeScreenshot("RemarkAlphaNumeric");
        }
    }

    public void verifyPredefineValInConsumePopup() {
        verifyPredefineValueInPopup("Consume Popup");
    }

    public void cancelPopup() {
        click(By.cssSelector("a#btnCancelReq"), "popup Cancel", 20);
    }

    public void verifyConsumePopupClosed() {
        WebElement consumeProductElement = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Consume Product')]"), 10);
        if (consumeProductElement == null) {
            getTest().log(LogStatus.PASS, "Consume Product popup is displayed as expected when click on the consume button");
            logger.info("Consume Product popup is displayed as expected when click on the consume button");
        } else {
            getTest().log(LogStatus.FAIL, "Consume Product popup is not displayed when click on the consume button");
            logger.info("Consume Product popup is not displayed when click on the consume button");
            takeScreenshot("ConsumeProdPopup");
        }
    }

    public void enterConsumeTag() {
        enter(By.cssSelector("#Tag"), "Test Automation", "Consume Tag", 20);
    }

    public void enterSelfCheckOutReMark() {
        enter(By.cssSelector("textarea#txtremarks"), prop.getProperty("attachmentNameAlphaNumeric"), "SelfCheck Out Remark", 20);
    }

    public void savePopup() {
        click(By.cssSelector("a#btnSaveReq"), "Save in Popup", 20);
    }

    public void handleConsumedSuccessMsg() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Product Consumed successfully']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when product consume is saved");
            logger.info("Success message is not displayed when product consume is saved");
            takeScreenshot("ConsumeSuccessMsg");
        }
    }

    public void consumeSaveFunction() {
        int beforeConsumeCount = Integer.parseInt(getText(By.xpath("//table[@id='tblProjectList']//tbody/tr[1]/td[contains(@class,'single-action')]/span/span"), 20));
        clickConsumeOption();
        enterAmountOfProduct("Equal");
        enterConsumeTag();
        enterConsumeReMark("Automation Test");
        int enteredCount = Integer.parseInt(getAtribute(By.xpath("//input[@name='NumberOfItems']"), "value", 20));
        savePopup();
        refreshThePage();
        //handleConsumedSuccessMsg();
        int afterConsumeCount = Integer.parseInt(getText(By.xpath("//table[@id='tblProjectList']//tbody/tr[1]/td[contains(@class,'single-action')]/span/span"), 20));
        if (afterConsumeCount == (beforeConsumeCount - enteredCount)) {
            getTest().log(LogStatus.PASS, "Product count is decreased from " + beforeConsumeCount + " to " + afterConsumeCount + " after consumed");
            logger.info("Product count is decreased from " + beforeConsumeCount + " to " + afterConsumeCount + " after consumed");
        } else {
            getTest().log(LogStatus.FAIL, "Product count is not decreased from " + beforeConsumeCount + " to " + (beforeConsumeCount - enteredCount) + " after consumed");
            logger.info("Product count is not decreased from " + beforeConsumeCount + " to " + (beforeConsumeCount - enteredCount) + " after consumed");
            takeScreenshot("ConsumeProdCount");
        }
    }

    public void clickReplaceRequest() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        click(By.xpath("//span[text()='" + prodName + "']/../..//a[@data-original-title='Replace Request']"), "Replace Request", 20);
    }

    public void verifyReplaceRequestPopup() {
        WebElement consumeProductElement = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Replace Request')]"), 30);
        if (consumeProductElement != null) {
            getTest().log(LogStatus.PASS, "Replace request popup is displayed as expected when click on the Replace request button");
            logger.info("Replace request popup is displayed as expected when click on the Replace request button");
        } else {
            getTest().log(LogStatus.FAIL, "Replace request popup is not displayed when click on the Replace request button");
            logger.info("Replace request popup is not displayed when click on the Replace request button");
            takeScreenshot("ReplaceReqPopup");
        }
    }

    public void verifyPredefinedInReplaceReq() {
        verifyPredefineValueInPopup("Replace Request");
    }

    public void enterRemark(String text) {
        enter(By.cssSelector("#Comment"), text, "Replace Req remark", 20);
    }

    public void verifyReplaceReqRemarkAlphaNumeric() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterRemark(expectedValue);
        String actualValue = getAtribute(By.cssSelector("#Comment"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Remark field in the Replace request popup is accepts the alpha numeric value as expected");
            logger.info("The Remark field in the Replace request popup is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field in the Replace request popup is not accepts the alpha numeric value");
            logger.info("The Remark field in the Replace request popup is not accepts the alpha numeric value");
            takeScreenshot("RemarkAlphaNumeric");
        }
    }

    public void cancelReplaceReq() {
        click(By.cssSelector("a#btnCancelReplacemen"), "Replace Req Cancel", 20);
    }

    public void verifyReplaceRequestPopupClose() {
        WebElement consumeProductElement = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Replace Request')]"), 10);
        if (consumeProductElement == null) {
            getTest().log(LogStatus.PASS, "Replace request popup is closed as expected when click on the close button in the Replace request popup");
            logger.info("Replace request popup is closed as expected when click on the close button in the Replace request popup");
        } else {
            getTest().log(LogStatus.PASS, "Replace request popup is not closed when click on the close button in the Replace request popup");
            logger.info("Replace request popup is not closed when click on the close button in the Replace request popup");
            takeScreenshot("ReplaceReqPopup");
        }
    }

    public void selectReasonForReplace() {
        selectValueWithIndex(By.cssSelector("#AssetReplacementType"), 0, "Reason for replace", 20);
    }

    public void enterNoOfProductReplace() {
        enterAmountOfProduct("Equal");
    }

    public void enterRemark() {
        enterRemark("Automation Testing");
    }

    public void saveReplaceReq() {
        click(By.cssSelector("a#btnSaveReplacement"), "Replace Req save", 20);
    }

    public void closeReplaceReqSuccess() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Replace request sent successfully.']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when replace request is saved");
            logger.info("Success message is not displayed when replace request is saved");
            takeScreenshot("ReplaceReqSuccessMsg");
        }
    }

    public void verifyCreatedRequest() {
        String expectedProductUniqueName = productsToAssign.get(0);
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

    public void verifySelfCheckOutOption() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        WebElement selfCheckOut = findElementVisibility(By.xpath("//span[text()='" + prodName + "']/../..//td//span[text()='Self Check Out']"), 30);
        if (selfCheckOut != null) {
            getTest().log(LogStatus.PASS, "Self check out option is displayed when product type with self checkout option and User also having self check out option");
            logger.info("Self check out option is displayed when product type with self checkout option and User also having self check out option");
        } else {
            getTest().log(LogStatus.FAIL, "Self check out option is not displayed when product type with self checkout option and User also having self check out option");
            logger.info("Self check out option is not displayed when product type with self checkout option and User also having self check out option");
            takeScreenshot("SelfCheckOutDisabled");
        }
    }

    public void clickSelfCheckOutOption() {
        String prodName = productsToAssign.get(0);
        click(By.xpath("//span[text()='" + prodName + "']/../..//td//span[text()='Self Check Out']"), "Self Check out option", 30);
    }

    public void enterMoreProductSelfCheckOut() {
        enterAmountOfProduct("More");
    }

    public void verifyMoreSelfCheckOutQty() {
        verifyQuantityFieldError("More");
    }

    public void verifyPreDefinedValueInSelfCheckOutPage() {
        verifyPredefineValueInPopup("Self Check Out");
    }

    public void verifySelfCheckOutRemarkAlphaNumeric() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterSelfCheckOutReMark();
        String actualValue = getAtribute(By.cssSelector("textarea#txtremarks"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Remark field in the self checkout popup is accepts the alpha numeric value as expected");
            logger.info("The Remark field in the self checkout popup is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field in the self checkout popup is not accepts the alpha numeric value");
            logger.info("The Remark field in the self checkout popup is not accepts the alpha numeric value");
            takeScreenshot("RemarkAlphaNumeric");
        }
    }

    public void verifySelfCheckOutPopupClosed() {
        WebElement consumeProductElement = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Self CheckOut')]"), 30);
        WebElement myProductListing = findElementVisibility(By.xpath("//span[text()='My Products ']"), 30);
        if (consumeProductElement == null && myProductListing != null) {
            getTest().log(LogStatus.PASS, "Self CheckOut popup is closed and my product listing is displayed as expected when click on the Self CheckOut button");
            logger.info("Self CheckOut popup is closed and my product listing is displayed as expected when click on the Self CheckOut button");
        } else {
            getTest().log(LogStatus.FAIL, "Cancel button in the self checkout popup is not working a expected");
            logger.info("Cancel button in the self checkout popup is not working a expected");
            takeScreenshot("ConsumeProdPopup");
        }
    }

    public void handleSelfCheckOutSuccessMsg() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Self Check Out Successfully']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when Self Check Out is saved");
            logger.info("Success message is not displayed when Self Check Out is saved");
            takeScreenshot("SelfCheckOutSuccessMsg");
        }
    }

    public void verifySelfCheckOutFunction() {
        String prodName = productsToAssign.get(0);
        int beforeCheckOutCount = Integer.parseInt(getText(By.xpath("//table[@id='tblProjectList']//tbody/tr[1]/td[contains(@class,'single-action')]/span/span"), 20));
        clickMoreIcon(prodName);
        clickSelfCheckOutOption();
        enterAmountOfProduct("Equal");
        int enteredQuantity = Integer.parseInt(getAtribute(By.cssSelector("div>input#NumberOfItems"), "value", 20));
        enterSelfCheckOutReMark();
        savePopup();
        //handleSelfCheckOutSuccessMsg();
        int afterConsumeCount = Integer.parseInt(getText(By.xpath("//table[@id='tblProjectList']//tbody/tr[1]/td[contains(@class,'single-action')]/span/span"), 20));
        if (afterConsumeCount == (beforeCheckOutCount + enteredQuantity)) {
            getTest().log(LogStatus.PASS, "Product count is increased from " + beforeCheckOutCount + " to " + afterConsumeCount + " after self check out is complete");
            logger.info("Product count is increased from " + beforeCheckOutCount + " to " + afterConsumeCount + " after self check out is complete");
        } else {
            getTest().log(LogStatus.FAIL, "Product count is not increased from " + beforeCheckOutCount + " to " + (afterConsumeCount - 1) + " after self check out is complete");
            logger.info("Product count is not increased from " + beforeCheckOutCount + " to " + (afterConsumeCount - 1) + " after self check out is complete");
            takeScreenshot("SelfCheckOutProdCount");
        }
    }

    public void verifyRequestQuantityOption() {
        WebElement requestQuantityOption = findElementVisibility(By.xpath("//span[text()='Request Quantity']"), 30);
        if (requestQuantityOption != null) {
            getTest().log(LogStatus.PASS, "Request Quantity option is displayed if product type having Employee Acceptance option and  Non unique name");
            logger.info("Request Quantity option is displayed if product type having Employee Acceptance option and  Non unique name");
        } else {
            getTest().log(LogStatus.PASS, "Request Quantity option is not displayed if product type having Employee Acceptance option and  Non unique name");
            logger.info("Request Quantity option is not displayed if product type having Employee Acceptance option and  Non unique name");
            takeScreenshot("RequestQuantityOption");
        }
    }

    public void clickRequestQuantityOption() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        click(By.xpath("//span[text()='" + prodName + "']/../..//td//span[text()='Request Quantity']"), "Request Quantity option", 30);
    }

    public void enterMoreRequestQuantity() {
        enterAmountOfProduct("More");
    }

    public void enterRequestQuantity() {
        enterAmountOfProduct("Equal");
    }

    public void verifyMoreRequestQuantityError() {
        verifyQuantityFieldError("More");
    }

    public void verifyPreDefinedValueInRequestQuantity() {
        verifyPredefineValueInPopup("Request Quantity");
    }

    public void alphaNumericRemarkRequestQuantity() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterRemark(expectedValue);
        String actualValue = getAtribute(By.cssSelector("textarea#Comment"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Remark field in the Request Quantity popup is accepts the alpha numeric value as expected");
            logger.info("The Remark field in the Request Quantity popup is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field in the Request Quantity popup is not accepts the alpha numeric value");
            logger.info("The Remark field in the Request Quantity popup is not accepts the alpha numeric value");
            takeScreenshot("RemarkAlphaNumeric");
        }
    }

    public void verifyRequestQuantityPopupClosed() {
        WebElement requestQuantityPopup = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Request Product')]"), 10);
        WebElement myProductListing = findElementVisibility(By.xpath("//span[text()='My Products ']"), 10);
        if (requestQuantityPopup == null && myProductListing != null) {
            getTest().log(LogStatus.PASS, "Request Quantity popup is closed and my product listing is displayed as expected when click on the cancel button");
            logger.info("Request Quantity popup is closed and my product listing is displayed as expected when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "Cancel button in the Request Quantity popup is not working a expected");
            logger.info("Cancel button in the Request Quantity popup is not working a expected");
            takeScreenshot("ReqQtyPopup");
        }
    }

    public void saveRequestQuantityPopup() {
        requestedProductName = getText(By.cssSelector("lable#lbl-uniqeName"), 30);
        savePopup();
        waitForLoader(20);
    }

    public void handleRequestQuantitySuccessMsg() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Request Quantity Successful']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when request quantity is saved");
            logger.info("Success message is not displayed when request quantity is saved");
            takeScreenshot("RequestQuantitySuccessMsg");
        }
    }

    public void verifyCreatedProdReq() {
        String productNameTemp = requestedProductName;
        int iteration = 0;
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

        for (String actualName : availableCheckOutRequest) {
            iteration++;
            if (actualName.contains(productNameTemp)) {
                getTest().log(LogStatus.PASS, "The requested product " + productNameTemp + " name is displayed in the pending check out list page once the quantity request is saved");
                logger.info("The requested product " + productNameTemp + " name is displayed in the pending check out list page once the quantity request is saved");
                break;
            } else if (availableCheckOutRequest.size() == iteration) {
                getTest().log(LogStatus.FAIL, "The requested product " + productNameTemp + " name is not displayed in the pending check out list page once the quantity request is saved");
                logger.info("The requested product " + productNameTemp + " name is not displayed in the pending check out list page once the quantity request is saved");
                takeScreenshot("ProductInTable");
            }
        }
    }

    public void createAndAssignTransferableProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        createProductType("SelfTransfer");
        createNewProduct(false, "barCode", productTypeName, "1", "");
        assignProductToUser(1, false, "TransferableProduct", "normaUser");
    }

    public void verifyTransferButtonEnable() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        String transferIcon = getAtribute(By.xpath("//span[text()='" + prodName + "']/../..//td//span[text()='Transfer']"), "class", 20);
        if (transferIcon.contains("enable")) {
            getTest().log(LogStatus.PASS, "The transferable icon is enabled if product type has Is transferable field as enabled");
            logger.info("The transferable icon is enabled if product type has Is transferable field as enabled");
        } else {
            getTest().log(LogStatus.FAIL, "The transferable icon is not enabled if product type has Is transferable field as enabled");
            logger.info("The transferable icon is not enabled if product type has Is transferable field as enabled");
            takeScreenshot("TransferableIcon");
        }
    }

    public void clickTransferOption() {
        String prodName = productsToAssign.get(0);
        click(By.xpath("//span[text()='" + prodName + "']/../..//a[@data-original-title='Transfer']"), "Transfer option", 20);
    }

    public void saveTransferButton() {
        click(By.cssSelector("a#btnSaveTransfer"), "Transfer Save", 20);
    }

    public void cancelTransferButton() {
        click(By.cssSelector("a#btnCancelTransfer"), "Transfer Cancel", 20);
    }

    public void verifyMandatoryErrorMessage() {
        String fieldName;
        List<WebElement> asteriskCount = findMultipleElement(By.xpath("//span[@class='mandatory']"), 30);
        for (WebElement asterisk : asteriskCount) {
            fieldName = asterisk.findElement(By.xpath("//parent::span//parent::label")).getText();
            WebElement errorMsg = findElementVisibility(By.xpath("//label[contains(text(),'" + fieldName + "')]//parent::div/span[@class='invalid-feedback']"), 20);
            if (errorMsg != null) {
                getTest().log(LogStatus.PASS, "Error message for " + fieldName.split(":")[0] + " field is displayed as expected as - " + errorMsg.getText().trim());
                logger.info("Error message for " + fieldName.split(":")[0] + " field is displayed as expected as - " + errorMsg.getText().trim());
            } else {
                getTest().log(LogStatus.PASS, "Error message for " + fieldName.split(":")[0] + " field is not displayed");
                logger.info("Error message for " + fieldName.split(":")[0] + " field is not displayed");
            }
        }
    }

    public void selectTransferLocation() {
        click(By.xpath("//div[@data-toggle='dropdown']"), "Location dropdown", 20);
        List<WebElement> locationValues = findMultipleElement(By.xpath("//a[contains(@class,'CompantLocationSelectdd-option')]"), 30);
        for (WebElement locationValue : locationValues) {
            locationValue.click();
            break;
        }
    }

    public void selectUserForTransfer() {
        selectValueWithIndex(By.xpath("//select[@id='AssetUserId']"), 1, "User Name", 20);
    }

    public void verifyTransferPopupClosed() {
        WebElement transferPopup = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Product Transfer')]"), 10);
        WebElement myProductListing = findElementVisibility(By.xpath("//span[text()='My Products ']"), 30);
        if (transferPopup == null && myProductListing != null) {
            getTest().log(LogStatus.PASS, "Product Transfer popup is closed and my product listing is displayed as expected when click on the cancel button");
            logger.info("Product Transfer popup is closed and my product listing is displayed as expected when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "Cancel button in the Product Transfer popup is not working a expected");
            logger.info("Cancel button in the Product Transfer popup is not working a expected");
            takeScreenshot("TransferPopup");
        }
    }

    public void verifyReturnEnable() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        WebElement returnIcon = findElementClickable(By.xpath("//span[text()='" + prodName + "']/../..//td//a[@data-original-title='Return']"), 20);
        if (returnIcon != null) {
            getTest().log(LogStatus.PASS, "The return icon is clickable to the user");
            logger.info("The return icon is clickable to the user");
        } else {
            getTest().log(LogStatus.FAIL, "The return icon is not clickable to the user");
            logger.info("The return icon is not clickable to the user");
            takeScreenshot("ReturnIcon");
        }
    }

    public void clickReturnOption() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        WebElement returnIcon = findElementClickable(By.xpath("//span[text()='" + prodName + "']/../..//td//a[@data-original-title='Return']"), 20);
        if (returnIcon != null) {
            getTest().log(LogStatus.PASS, "The return icon is clickable to the user");
            logger.info("The return icon is clickable to the user");
            click(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//a[@data-original-title='Return']"), "Return option", 20);
        } else {
            getTest().log(LogStatus.FAIL, "The return icon is not clickable to the user");
            logger.info("The return icon is not clickable to the user");
            takeScreenshot("ReturnIcon");
        }
    }

    public void verifyPredefinedInReturnPopup() {
        verifyPredefineValueInPopup("Return");
    }

    public void verifyAlphaNumericReturnRemark() {
        String expectedValue = prop.getProperty("attachmentNameAlphaNumeric");
        enterRemark(expectedValue);
        String actualValue = getAtribute(By.cssSelector("textarea#Comment"), "value", 20);
        if (actualValue.equalsIgnoreCase(expectedValue)) {
            getTest().log(LogStatus.PASS, "The Remark field in the Return popup is accepts the alpha numeric value as expected");
            logger.info("The Remark field in the Return popup is accepts the alpha numeric value as expected");
        } else {
            getTest().log(LogStatus.FAIL, "The Remark field in the Return popup is not accepts the alpha numeric value");
            logger.info("The Remark field in the Return popup is not accepts the alpha numeric value");
            takeScreenshot("RemarkAlphaNumeric");
        }
    }

    public void selectQuantityForReturn() {
        enterAmountOfProduct("Equal");
    }

    public void selectReasonForReturn() {
        selectValueWithIndex(By.cssSelector("#RetrunStatus"), 1, "Reason for return", 20);
    }

    public void saveReturnPopup() {
        returnRequestedName = getText(By.cssSelector("lable#lbl-uniqeName"), 30);
        click(By.cssSelector("a#btnSaveReturn"), "Return popup save", 20);
    }

    public void cancelReturnPopup() {
        click(By.cssSelector("a#btnCancelReturn"), "Cancel popup save", 20);
    }

    public void verifyCreatedReturnReq() {
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

    public void verifyReturnReqPopupClosed() {
        WebElement transferPopup = findElementVisibility(By.xpath("//div[@class='modal-content']//h5[contains(text(),'Return')]"), 30);
        WebElement myProductListing = findElementVisibility(By.xpath("//span[text()='My Products ']"), 30);
        if (transferPopup == null && myProductListing != null) {
            getTest().log(LogStatus.PASS, "Product Return popup is closed and my product listing is displayed as expected when click on the cancel button");
            logger.info("Product Return popup is closed and my product listing is displayed as expected when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "Cancel button in the Product Return popup is not working a expected");
            logger.info("Cancel button in the Product Return popup is not working a expected");
            takeScreenshot("TransferPopup");
        }
    }

    public void handleReturnReqSuccessMsg() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Request Return Successfully']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when return request is saved");
            logger.info("Success message is not displayed when return request is saved");
            takeScreenshot("ReturnRequestSuccessMsg");
        }
    }

    public void verifyTAndCClickable() {
        WebElement TAndC = findElementClickable(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td//a[@data-original-title='Accept Terms & Conditions']"), 20);
        if (TAndC != null) {
            getTest().log(LogStatus.PASS, "The Accept Terms & Condition icon is clickable to the user if product type has Terms and Condition toggle as enabled");
            logger.info("The Accept Terms & Condition icon is clickable to the user if product type has Terms and Condition toggle as enabled");
        } else {
            getTest().log(LogStatus.FAIL, "The Accept Terms & Condition icon is not clickable to the user even if product type has Terms and Condition toggle as enabled");
            logger.info("The Accept Terms & Condition icon is not clickable to the user even if product type has Terms and Condition toggle as enabled");
        }
    }

    public void clickTermsAndConditionIcon() {
        String prodName = productsToAssign.get(0);
        clickMoreIcon(prodName);
        WebElement TAndC = findElementClickable(By.xpath("//span[text()='" + prodName + "']/../..//td//a[@data-original-title='Accept Terms & Conditions']"), 20);
        if (TAndC != null) {
            getTest().log(LogStatus.PASS, "The Accept Terms & Condition icon is clickable to the user if product type has Terms and Condition toggle as enabled");
            logger.info("The Accept Terms & Condition icon is clickable to the user if product type has Terms and Condition toggle as enabled");
            click(By.xpath("//table[@id='tblProjectList']//tbody//tr[1]//td//a[@data-original-title='Accept Terms & Conditions']"), "Terms and Condition option", 20);
        } else {
            getTest().log(LogStatus.FAIL, "The Accept Terms & Condition icon is not clickable to the user even if product type has Terms and Condition toggle as enabled");
            logger.info("The Accept Terms & Condition icon is not clickable to the user even if product type has Terms and Condition toggle as enabled");
        }
    }

    public void clickCheckBoxOfTC() {
        click(By.xpath("//input[contains(@class,'chktermcondition')]//parent::div/label"), "TC Check box", 30);
    }

    public void verifySaveButtonEnable() {
        String tcCheckBox = findElementVisibility(By.cssSelector("a#btnSaveTerms"), 30).getAttribute("class");
        if (!tcCheckBox.contains("disabled-button")) {
            getTest().log(LogStatus.PASS, "The Terms and Condition save button is enabled wen user click the check box");
            logger.info("The Terms and Condition save button is enabled wen user click the check box");
        } else {
            getTest().log(LogStatus.FAIL, "The Terms and Condition save button is not enabled wen user click the check box");
            logger.info("The Terms and Condition save button is not enabled wen user click the check box");
            takeScreenshot("TCSaveButton");
        }
    }

    public void verifyTermsAndCondDownload() {
        String downloadPath = Drivers.path;
        String fileName = getText(By.xpath("//table[@id='tblRecord']//tbody//tr[1]//td[2]"), 30).trim();
        File dir = new File(downloadPath + fileName);
        if (dir.exists()) {
            dir.delete();
        }
        File[] dirContent = new File(downloadPath).listFiles();
        int filesInDirectory = dirContent.length;

        click(By.xpath("//table[@id='tblRecord']//tbody//tr[1]//td[3]/a"), "Download Icon", 20);

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
        }
    }

    public void saveTermsAndCondition() {
        click(By.cssSelector("#btnSaveTerms"), "Terms and Condtion save", 20);
    }

    public void verifyAcceptedTermsAndCondition() {
        String prodName = productsToAssign.get(0);
        waitForLoad(20);
        clickMoreIcon(prodName);
        String termsAndCondition = findElementVisibility(By.xpath("//span[@id='action-list']//span[text()='Accept Terms & Conditions']//parent::a"), 20).getAttribute("class");
        if (termsAndCondition.contains("disabled")) {
            getTest().log(LogStatus.PASS, "The terms and condition is accepted when click on the save button in the Terms and Condition popup");
            logger.info("The terms and condition is accepted when click on the save button in the Terms and Condition popup");
        } else {
            getTest().log(LogStatus.FAIL, "The terms and condition is not accepted when click on the save button in the Terms and Condition popup");
            logger.info("The terms and condition is not accepted when click on the save button in the Terms and Condition popup");
            takeScreenshot("TermsAndConditionAccept");
        }
    }

    public void getCountOfRedProdBasedOnThisMonth() {
        int totalProduct = 0;
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("M").format(new Date());
        int previousMonth = Integer.parseInt(month) - 1;
        deployProduct.make100PageSize();
        waitForLoad(30);
        Object paginationNextButton = "";
        First:
        while (paginationNextButton != null) {
            int redProductByThisMonth = findMultipleElement(By.xpath("//tr[contains(@class,'high-profile-bar')]"), 10).size();
            for (int i = 1; i <= redProductByThisMonth; i++) {
                String productByThisMonth = getText(By.xpath("//tr[contains(@class,'high-profile-bar')][" + i + "]//td[5]/span"), 10);
                if (productByThisMonth.startsWith(month)) {
                    String countInText = getText(By.xpath("//tr[contains(@class,'high-profile-bar')][" + i + "]//td[5]/span/../../td[7]/span/span"), 20);
                    totalProduct = totalProduct + Integer.parseInt(countInText);
                }
                if (productByThisMonth.startsWith(String.valueOf(previousMonth))) {
                    break First;
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 30);
            }
        }
        productAcceptancePendingByUser = String.valueOf(totalProduct);
    }

    public void getRedProductNamesAndCount() {
        List<String> productNames = new ArrayList<>();
        Map<String, String> productCounts = new HashMap<>();
        String month = new SimpleDateFormat("M").format(new Date());
        int previousMonth = Integer.parseInt(month) - 1;
        deployProduct.make100PageSize();
        waitForLoad(30);
        Object paginationNextButton = "";
        First:
        while (paginationNextButton != null) {
            int redProductByThisMonth = findMultipleElement(By.xpath("//tr[contains(@class,'high-profile-bar')]"), 10).size();
            for (int i = 1; i <= redProductByThisMonth; i++) {
                String productByThisMonth = getText(By.xpath("//tr[contains(@class,'high-profile-bar')][" + i + "]//td[5]/span"), 10);
                if (productByThisMonth.startsWith(month)) {
                    String productName = getText(By.xpath("//tr[contains(@class,'high-profile-bar')][" + i + "]//td[1]/span"), 20);
                    String productCount = getText(By.xpath("//tr[contains(@class,'high-profile-bar')][" + i + "]//td[5]/span/../../td[7]/span/span"), 20);
                    productNames.add(productName);
                    productCounts.put(productName, productCount);
                }
                if (productByThisMonth.startsWith(String.valueOf(previousMonth))) {
                    break First;
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next", 30);
            }
        }
        productNamesPendingByUser = productNames;
        productNamesWithCount = productCounts;
    }

    public void getProductNamesForNUWidget() {
        WebElement paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 20);
        while (paginationNext != null) {
            List<WebElement> productCountsList = findMultipleElement(By.xpath("//table[@id='tblProjectList']//tbody//tr"), 20);
            for (WebElement productCount : productCountsList) {
                String prodCount = productCount.findElement(By.xpath("//td[7]/span/span")).getText();
                if (!prodCount.equalsIgnoreCase("0")) {
                    String trClassName = productCount.findElement(By.xpath("//ancestor::tr")).getAttribute("class");
                    if (!trClassName.contains("high-profile-bar")) {
                        String prodName = productCount.findElement(By.xpath("//ancestor::tr/td[1]/span")).getText();
                        productInMyProductList.add(prodName);
                    }
                }
            }
            paginationNext.click();
            paginationNext = findElementVisibility(By.xpath("//a[@class='page-link next']"), 20);
        }
    }

    public void acceptProductBeforeCheckYellowStrip() {
        String trClass = getAtribute(By.xpath("//span[text()='" + productsToAssign.get(0) + "']/ancestor::tr"), "class", 20);
        if (trClass.contains("high-profile-bar")) {
            String prodName = productsToAssign.get(0);
            clickMoreIcon(prodName);
            click(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr//span[@id='action-list']/a[@data-original-title='Accept']"), "Accept option for" + prodName, 20);
            waitForLoader(20);
        }
    }

    public void acceptBeforeReturnProduct() {
        String trClass = getAtribute(By.xpath("//span[text()='" + productsToAssign.get(0) + "']/ancestor::tr"), "class", 20);
        if (trClass.contains("high-profile-bar")) {
            String prodName = productsToAssign.get(0);
            clickMoreIcon(prodName);
            click(By.xpath("//span[text()='" + prodName + "']//parent::td//parent::tr//span[@id='action-list']/a[@data-original-title='Accept']"), "Accept option for" + prodName, 20);
            waitForLoader(20);
        }
    }
}
