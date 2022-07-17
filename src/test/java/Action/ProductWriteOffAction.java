package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.ProductListingPage;
import pageobjects.ProductWriteOffPage;

public class ProductWriteOffAction {

    ProductWriteOffPage productWriteOffPage;
    ProductListingPage productListingPage;
    public ProductWriteOffAction(WebDriver driver)
    {
        this.productWriteOffPage = new ProductWriteOffPage(driver);
        this.productListingPage = new ProductListingPage(driver);
    }

    public void navigateToProductWriteOffPage()
    {
        productWriteOffPage.openProductWriteOffPage();
    }
    public void navigateToCreateWriteOffPage()
    {
        productWriteOffPage.navigateToCreateProductWriteOffPage();
    }
    public void verifyProductWriteOffPage()
    {
        productWriteOffPage.verifyProductWriteOffPage();
    }
    public void productWriteOffPageAppearance()
    {
        productWriteOffPage.verifySearchFilterPresence();
        productWriteOffPage.findAddNewButtonPresence();
        productWriteOffPage.verifyProductWriteOffHeaders();
    }
    public void breadCrumbFunction()
    {
        productWriteOffPage.verifyProductInPWOBreadCrumb();
        navigateToProductWriteOffPage();
        productWriteOffPage.verifyHomeInPWOBreadCrumb();
    }
    public void searchWithLocation()
    {
        productWriteOffPage.expandLocationSearchField();
        productWriteOffPage.getLocationToSearch();
        productWriteOffPage.selectLocationToSearch();
        productListingPage.clickSearchButton();
        productWriteOffPage.verifyLocationSearch();
        productListingPage.clickResetButton();
    }
    public void searchWithProductName()
    {
        productWriteOffPage.expandProductNameCodeField();
        productWriteOffPage.getProductNameToSearch();
        productWriteOffPage.enterProductNameInSearchField();
        productListingPage.clickSearchButton();
        productWriteOffPage.verifyProductNameSearch();
        productListingPage.clickResetButton();
    }
    public void searchWithProductCode()
    {
        productWriteOffPage.expandProductNameCodeField();
        productWriteOffPage.getProductCodeToSearch();
        productWriteOffPage.enterProductCodeInSearchField();
        productListingPage.clickSearchButton();
        productWriteOffPage.verifyProductCodeSearch();
        productListingPage.clickResetButton();
    }
    public void searchAndResetFunctionality()
    {
        productWriteOffPage.clickExpandCollapseButton();
        productWriteOffPage.getLocationToSearch();
        productWriteOffPage.getProductCodeToSearch();
        productWriteOffPage.expandLocationSearchField();
        productWriteOffPage.selectLocationToSearch();
        productWriteOffPage.enterProductCodeInSearchField();
        productListingPage.clickSearchButton();
        productListingPage.clickResetButton();
        productWriteOffPage.verifyResetButtonFunction();
    }
    public void expandCollapseAllFunction()
    {
        productWriteOffPage.clickExpandCollapseButton();
        productWriteOffPage.verifyExpandedMenu();
    }
    public void paginationFunction()
    {
        productWriteOffPage.productWriteOffPagination();
    }
    public void barCodeFieldBehaviour()
    {
        productWriteOffPage.clickBarCodeSearch();
        productWriteOffPage.verifyBarCodeValidationMsg();
        productWriteOffPage.verifyAlphaNumericValue();
    }
    public void createProductTypeAndProduct()
    {
        productWriteOffPage.createProduct();
    }
    public void barCodeSearchFunction()
    {
        productWriteOffPage.verifyBarCodeSearch();
        productWriteOffPage.verifySearchedBarCodeProduct();
    }
    public void mandatoryFieldValidation()
    {
        productWriteOffPage.clickSearchButton();
        productWriteOffPage.closeWarningPopup();
        productWriteOffPage.verifyMandatoryErrorMessage();
    }
    public void locationFieldBehaviour()
    {

        productWriteOffPage.getLocations();
        navigateToProductWriteOffPage();
        navigateToCreateWriteOffPage();
        productWriteOffPage.clickLocationField();
        productWriteOffPage.verifyLocations();
        productWriteOffPage.findLocationSearchField();
        productWriteOffPage.clickLocationField();
        productWriteOffPage.selectLocation();
        productWriteOffPage.verifyLocationIsSelected();
        productWriteOffPage.resetSelectedLocation();
        productWriteOffPage.verifyLocationReset();
    }
    public void verifyProductDropdown()
    {
        productWriteOffPage.getProductBasedOnTheProductType();
        navigateToProductWriteOffPage();
        navigateToCreateWriteOffPage();
        productWriteOffPage.selectLocation();
        productWriteOffPage.selectProductType();
        productWriteOffPage.clickProductDropDown();
        productWriteOffPage.verifyProductDropdown();
        productWriteOffPage.selectProduct();
    }
    public void verifyUniqueNameDropdown()
    {
        productWriteOffPage.enterProductUniqueName();
        productWriteOffPage.verifyProductUniqueNameDropdown();
    }
    public void verifyProductSearchFunctionWithData()
    {
        productWriteOffPage.verifyProductSearchResult();
    }
    public void verifyAutoSearchFields()
    {
        productWriteOffPage.verifyAlphaNumericPCode();
        productWriteOffPage.verifyAlphaNumericPName();
        productWriteOffPage.verifyAlphaNumericUniqueName();
        productWriteOffPage.clickResetInNewTable();
        productWriteOffPage.verifyResetFields();
        productWriteOffPage.enterDataInAutoSearchField();
        productWriteOffPage.verifyAutoSearchedResult();
    }
    public void searchProductForWriteOff()
    {
        productWriteOffPage.getProductBasedOnTheProductType();
        navigateToProductWriteOffPage();
        navigateToCreateWriteOffPage();
        productWriteOffPage.selectLocation();
        productWriteOffPage.selectProductType();
        productWriteOffPage.selectProduct();
        productWriteOffPage.clickSearchButton();
    }
    public void verifySelectAllCheckBoxFunction()
    {
        productWriteOffPage.clickSelectAllCheckBoxAfterSearch();
        productWriteOffPage.verifyAllSelectedCheckBoxAfterSearch();
    }
    public void writeOffFieldBehaviour()
    {
        productWriteOffPage.selectSingleCheckBox();
        productWriteOffPage.verifyNumericWriteOff();
        productWriteOffPage.verifyAlphabetWriteOff();
        productWriteOffPage.enterWriteOffLowSalvage();
        productWriteOffPage.clickAddToListButton();
        productWriteOffPage.verifyLowSalvageError();
    }

    public void addToListValidation()
    {
        productWriteOffPage.enterWriteOffHighSalvage();
        productWriteOffPage.clickAddToListButton();
        productWriteOffPage.verifyAddToListValidation();
    }
    public void deleteFunction()
    {
        productWriteOffPage.selectSingleCheckBox();
        productWriteOffPage.enterWriteOffHighSalvage();
        productWriteOffPage.clickAddToListButton();
        productWriteOffPage.clickDeleteIconOfAddedProd();
        productWriteOffPage.confirmationForAddedProdDelete();
        productWriteOffPage.verifyDeletedRecord();
    }
    public void cancelButtonFunction()
    {
        productWriteOffPage.clickCancelButton();
        productWriteOffPage.verifyCancelProductWriteOff();
    }
    public void saveFunction()
    {
        productWriteOffPage.clickAddToListButton();
        productWriteOffPage.verifySaveProdMandatoryError();
        productWriteOffPage.selectSingleCheckBox();
        productWriteOffPage.enterWriteOffHighSalvage();
        productWriteOffPage.clickAddToListButton();
        productWriteOffPage.verifyProductIsAddedToWriteOffList();
        productWriteOffPage.clickCheckBoxOfAddedProduct();
        productWriteOffPage.saveAddedProduct();
        productWriteOffPage.verifyTheSavedWriteOffProduct();
    }
}
