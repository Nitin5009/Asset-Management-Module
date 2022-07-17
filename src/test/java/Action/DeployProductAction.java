package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.DeployProductPage;
import utils.PropertiesLoader;

import java.util.Properties;

public class DeployProductAction {
    WebDriver driver;
    DeployProductPage deployProduct;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public DeployProductAction(WebDriver driver) {
        this.driver = driver;
        this.deployProduct = new DeployProductPage(driver);
        this.deployProduct = new DeployProductPage(driver);
    }

    public void navigateToDeployProductPage() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        deployProduct.clickManageProduct();
        deployProduct.openProduct();
        deployProduct.navigateToDeployTab();
    }

    public void navigateToCreateProductPage() {
        deployProduct.clickAddDeployButton();
    }

    public void verifyDeployListing() {
        deployProduct.verifyListingColumnHeader();
    }

    public void verifySearchandAddElementPresence() {
        deployProduct.verifySearchBar();
        deployProduct.verifyDepolyProductAddButton();
    }

    public void verifyPreviousandNextElementPresence() {
        deployProduct.verifyPreviousButton();
        deployProduct.verifyNextButton();
    }

    public void searchingFunctionality() {
        deployProduct.enterInSearchField();
        deployProduct.clickSearchButton();
        deployProduct.verifySearchedProduct();
    }

    public void searchFieldClearFunctionality() {
        deployProduct.clickClearSearch();
        deployProduct.verifyClearedSearch();
    }

    public void addDeployPageVerification() {
        deployProduct.verifyAddDeployProductPage();
    }

    public void nextButtonFunctionality() {
        deployProduct.clickNextButton();
        deployProduct.verifyNextPage();
    }

    public void previousButtonFunctionality() {
        deployProduct.clickPreviousButton();
        deployProduct.verifyPreviousPage();
    }

    public void verifyAddedDeployProduct() {
        createProductToDeploy();
        saveButtonFunctionality();
        deployProduct.make100PageSize();
        deployProduct.verifyCreatedDeployProduct();
    }

    public void breadCrumbValidation() {
        deployProduct.verifyBreadCrumb();
    }

    public void editPageVerification() {
        deployProduct.navigateToProductEditMode();
        deployProduct.verifyEditMode();
    }

    public void errorMessageValidation() {
        deployProduct.clearUnitPriceField();
        deployProduct.clickAddListButton();
        deployProduct.verifyMandatoryFieldValidation();
    }

    public void locationDropdownVerification() {
        deployProduct.clickLocationDropdown();
        deployProduct.verifyLocationDropdown();
    }

    public void locationDropdownSearchFuctionality() {
        deployProduct.verifySearchFieldInLocationDropdown();
        deployProduct.enterInLocationSearch(prop.getProperty("dropdownLocationToSearch"));
        deployProduct.verifyLocationSearch(prop.getProperty("dropdownLocationToSearch"));
    }

    public void selectAndClearLocation() {
        deployProduct.selectLocationValueFromDropdown("");
        deployProduct.verifySelectedLocation(prop.getProperty("dropdownLocationToSearch"));
        deployProduct.clearLocationSelection();
        deployProduct.verifyClearedLocation();
    }

    public void verifyLocationDropdownValues() {
        deployProduct.clickLocationDropdown();
        deployProduct.verifyLocations("parent");
        deployProduct.verifyLocations("child");
    }

    public void verifyQuantityField() {
        deployProduct.verifyQuantityMinimumChar();
        deployProduct.verifyQuantityCharAndSpclChar();
        deployProduct.verifyQuantityMaxChar();
    }

    public void verifyModelandVendorNameField() {
        deployProduct.verifyModelFieldValidation();
        deployProduct.verifyVendorFieldValidation();
    }

    public void verifyManufacturerNameField() {
        deployProduct.verifyManufacturerFieldAlphaNum();
        deployProduct.verifyManufacturerFieldSpclCharAccept();
        deployProduct.verifyManufacturerFieldSpclCharReject();
    }

    public void verifyProductCostFieldBehaviour(String condition) {
        deployProduct.verifyProductCostFieldBehaviour(condition);
        deployProduct.clickCloseButton();
    }

    public void verifyInsurenceRefNumFieldBehaviour(String condition) {
        deployProduct.verifyInsuranceRefNumFieldBehaviour(condition);
    }

    public void verifyInsurenceDateFieldBehaviour(String condition) {
        deployProduct.verifyInsuranceDateFieldBehaviour(condition);
        deployProduct.clickCloseButton();
    }

    public void verifyInsurerNameFieldBehaviour(String condition) {
        deployProduct.verifyInsurerNameFieldBehaviour(condition);
        deployProduct.clickCloseButton();
    }

    public void verifyProductCostField() {
        deployProduct.productCostNumbersOnly();
        deployProduct.productCostCharandSpclChar();
        deployProduct.productCostMinimum();
        deployProduct.productCostMaximum();
        deployProduct.productCostMoreThanMaximum();
        deployProduct.clickCloseButton();
    }

    public void verifyReferenceField() {
        deployProduct.verifyReferenceNumberFieldPesence();
        deployProduct.verifyRefernceNumberAlphaNumeric();
        deployProduct.verifyRefernceNumberSpclCharAccept();
        deployProduct.verifyRefernceNumberSpclCharReject();
    }

    public void verifyDateField() {
        deployProduct.verifyOrderDateFieldPresence();
        deployProduct.verifyInvoiceDateFieldPresence();
    }

    public void verifyProductDateAsCurrentDate() {
        deployProduct.clickOrderDateField();
        deployProduct.verifyWithCurrentDate("Order");
    }

    public void verifyProductDateAsOldDate() {
        deployProduct.clickOrderDateField();
        deployProduct.verifyWithOldDate("Order");
    }

    public void verifyProductDateFutureDate() {
        deployProduct.clickOrderDateField();
        deployProduct.verifyNotClickableFutureDate("Order");
    }

    public void verifyInvoiceNumberFieldFunctionality() {
        deployProduct.verifyInvoiceNumberFieldPresence();
        deployProduct.verifyInvoiceNumberAlphaNumeric();
        deployProduct.verifyInvoiceNumberSpclCharAccept();
        deployProduct.verifyInvoiceNumberSpclCharReject();
    }

    public void verifyInvoiceDateAsCurrentDate() {
        deployProduct.clickInvoiceDateField();
        deployProduct.verifyWithCurrentDate("Invoice");
    }

    public void verifyInvoiceDateAsOldDate() {
        deployProduct.clickInvoiceDateField();
        deployProduct.verifyWithOldDate("Invoice");
    }

    public void verifyInvoiceDateFutureDate() {
        deployProduct.clickInvoiceDateField();
        deployProduct.verifyNotClickableFutureDate("Invoice");
    }

    public void verifyInsuranceNumandInsurerNameField() {
        deployProduct.verifyInsuranceNumberAlphaNumeric();
        deployProduct.verifyInsurerNameAlphaNumeric();
    }

    public void verifyInsuranceValidDateFunctionality() {
        deployProduct.clickInsuranceDateField();
        deployProduct.verifyWithCurrentDate("Insurance");
        deployProduct.clickInsuranceDateField();
        deployProduct.verifyClickableFutureDate("Insurance");
    }

    public void verifyCurrentWarrantyDate() {
        deployProduct.clickWarrantyDateField();
        deployProduct.verifyWithCurrentDate("Warranty");
    }

    public void verifyPastWarrantyDate() {
        deployProduct.clickWarrantyDateField();
        deployProduct.verifyNotClickablePastDate("Warranty");
    }

    public void verifyFutureWarrantyDate() {
        deployProduct.verifyClickableFutureDate("Warranty");
    }

    public void verifyDepreciationRuleFieldBehaviour(String behaviour) {
        deployProduct.verifyDepreciationRuleField(behaviour);
        deployProduct.clickCloseButton();
    }

    public void verifyDepreciationDropdown() {
        deployProduct.verifyDepreciationDropdown();
    }

    public void verifyProducLifeField() {
        deployProduct.verifyProductLifeAsteriskSymbol();
        deployProduct.verifyProductLifeMandatoryError();
        deployProduct.verifyProductLifeNumeric();
        deployProduct.verifyProductLifeAlphaNumeric();
        deployProduct.verifyProductLifeMaximumChar();
    }

    public void verifySalvageCostField() {
        deployProduct.verifySalvageCostNumeric();
        deployProduct.verifySalvageCostCharandSpclChar();
        deployProduct.verifySalvageCostMaxChar();
        deployProduct.verifySalvageCostMorethanMaxChar();
    }

    public void cancelButtonFunctionality() {
        deployProduct.clickAddListButton();
        deployProduct.clickCancelButton();
        deployProduct.verifyCancelButtonFunctionality();
    }

    public void saveButtonFunctionality() {
        deployProduct.clickAddListButton();
        deployProduct.clickSaveButton();
        deployProduct.handleSuccessPopup();
        deployProduct.verifyCreatedDeployProduct();
    }

    public void closeButtonFunctionality() {
        deployProduct.clickCloseButton();
        deployProduct.verifyCloseButtonFunctionality();
    }

    public void createProductToDeploy() {
        deployProduct.clickLocationDropdown();
        deployProduct.selectLocationValueFromDropdown("");
        deployProduct.enterQuantity(prop.getProperty("quantities"));
        deployProduct.enterUnitPrice(prop.getProperty("unitPrice"));
        deployProduct.enterModel(prop.getProperty("modelNameToCreate"));
        deployProduct.enterManufacturer(prop.getProperty("manufacturerName"));
        deployProduct.enterVendor(prop.getProperty("vendorName"));
        deployProduct.enterProductCost(prop.getProperty("productCost"));
        deployProduct.enterPurchaseOrder(prop.getProperty("purchaseOrder"));
        deployProduct.clickOrderDateField();
        deployProduct.selectDate(prop.getProperty("currentDate"));
        deployProduct.enterInvoiceNumber(prop.getProperty("invoiceNumber"));
        deployProduct.clickInvoiceDateField();
        deployProduct.selectDate(prop.getProperty("currentDate"));
//        deployProduct.enterInsuranceNumber(prop.getProperty("insuranceNumber"));
//        deployProduct.enterInsurarName(prop.getProperty("insurarName"));
        deployProduct.clickInsuranceDateField();
        deployProduct.selectDate(prop.getProperty("futureDate"));
        deployProduct.clickWarrantyDateField();
        deployProduct.selectDate(prop.getProperty("futureDate"));
        deployProduct.selectDepreciationRule();
        deployProduct.enterProductLife(prop.getProperty("productLifeYear"));
        deployProduct.enterSalvageCost(prop.getProperty("salvageCost"));
    }

    public void addToListFunctionality() {
        deployProduct.verifyAddToListButton();
        deployProduct.clickAddListButton();
        deployProduct.verifyDeployList();
    }

    public void verifyPagination() {
        deployProduct.selectRecordPagination();
        deployProduct.verifyPaginationFunctionalities();
    }

}
