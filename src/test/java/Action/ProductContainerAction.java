package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.ProductContainerPage;
import pageobjects.ProductListingPage;

public class ProductContainerAction {
    WebDriver driver;
    ProductContainerPage productContainerPage;
    CompanySetupAction companySetupAction;
    ProductListingPage productListingPage;

    public ProductContainerAction(WebDriver driver) {
        this.driver = driver;
        productContainerPage = new ProductContainerPage(driver);
        companySetupAction = new CompanySetupAction(driver);
        productListingPage = new ProductListingPage(driver);
    }

    public void navigateToProductContainerPage() {
        productContainerPage.openProductContainerPage();
    }

    public void verifyProductContainerPage() {
        productContainerPage.verifyProductContainerPage();
    }

    public void verifyBreadCrumb() {
        productContainerPage.verifyBreadCrumbFunction();
    }

    public void searchContainer() {
        productContainerPage.enterProdContainerInSearchField();
        productContainerPage.clickSearchButton();
        productContainerPage.verifySearchedContainer();
    }

    public void searchFieldAlphaNumeric() {
        productContainerPage.enterAlphaNumericInSearchFields();
        productContainerPage.verifyPlaceHolder();
        productContainerPage.verifyAlphaNumericCharacter();
    }

    public void pagination() {
        productContainerPage.verifyPageSize();
        productContainerPage.paginationOnProductContainerList();
    }

    public void navigateToAddContainerPage() {
        productContainerPage.clickAddProductContainerButton();
    }

    public void successPopup()
    {
        productContainerPage.handleSuccessPopup();
    }
    public void verifyCreateContainerPage() {
        productContainerPage.verifyCreateProductContainerPage();
    }

    public void productContainerNameSort() {
        productContainerPage.productContainerAscending();
        productContainerPage.productContainerDescending();
    }

    public void productContainerStatusSort() {
        productContainerPage.containerStatusAscending();
        productContainerPage.containerStatusDescending();
    }

    public void productContainerDateSort() {
        productContainerPage.containerDateAscending();
        productContainerPage.containerDateDescending();
    }

    public void createMultipleContainers()
    {
        productContainerPage.createMultipleContainer();
    }
    public void deleteContainer() {
        productContainerPage.selectSingleData();
        productContainerPage.deleteButton();
        productContainerPage.confirmDeleteContainer();
        productContainerPage.handleDeleteSuccessMsg();
        productContainerPage.verifyDeletedContainer();
        productContainerPage.selectMultiData();
        productContainerPage.deleteButton();
        productContainerPage.confirmDeleteContainer();
        productContainerPage.handleDeleteSuccessMsg();
        productContainerPage.verifyDeletedContainer();
    }

    public void changeStatus() {
        productContainerPage.selectMultiData();
        productContainerPage.changeStatus();
    }

    public void editProductContainer() {
        productContainerPage.navigateToEditPage();
        productContainerPage.changeProduct();
        productContainerPage.saveUpdateContainer();
        productContainerPage.handleContainerUpdateSuccess();
        productContainerPage.verifyContainerUpdate();
    }

    public void getProductTypeWithContainer() {
        productContainerPage.getContainerProductType();
    }

    public void verifyProdDopDown() {
        productContainerPage.verifyProductDropDown();
    }

    public void verifyLocations() {
        companySetupAction.navigateSideBarLocation();
        companySetupAction.getAllLocationList();
        navigateToProductContainerPage();
        navigateToAddContainerPage();
        productContainerPage.verifyLocationDropDown();
    }

    public void verifyProductInContainerDropDown() {
        productContainerPage.deployProductForContainer();
        navigateToProductContainerPage();
        navigateToAddContainerPage();
        productContainerPage.selectProductType();
        productContainerPage.selectLocation();
        productContainerPage.verifyProductNames();

    }

    public void verifyProductsInProductNameDD() {
        productContainerPage.getLocationForProductNameVerification();
        productContainerPage.getTypeLocationBasedProducts();
        navigateToProductContainerPage();
        navigateToAddContainerPage();
        productContainerPage.selectProductType();
        productContainerPage.selectLocation();
        productContainerPage.verifyLocationStatusBasedProducts();
    }

    public void deployProductForCompartment() {
        AddProductAction addProductAction = new AddProductAction(driver);
        productContainerPage.getContainerProductType();
        addProductAction.gotoManageProductPage();
        productContainerPage.deployProductForContainer();
    }

    public void getCompartmentList() {
        productContainerPage.getComportmentInProductType();
    }

    public void verifyCompartment() {
        productContainerPage.selectProductType();
        productContainerPage.selectLocation();
        productContainerPage.selectContainer();
        productContainerPage.selectProductName();
        productContainerPage.verifyComportmentList();
    }

    public void deployProductForAddRowFunction() {
        productContainerPage.deployProductForContainer();
        navigateToProductContainerPage();
        navigateToAddContainerPage();
    }

    public void addRowFunction() {
        productContainerPage.selectProductType();
        productContainerPage.selectLocation();
        productContainerPage.selectContainer();
        productContainerPage.selectProductName();
        productContainerPage.selectCompartmentOne();
        productContainerPage.clickAddRow();
        productContainerPage.verifyAddButtonFunction();
    }

    public void verifyDupProductErrorMsg() {
        productContainerPage.selectDupProductName();
        productContainerPage.verifyDuplicateProdErrorMessage();
    }

    public void verifyDeleteButton() {
        productContainerPage.selectDiffProductName();
        productContainerPage.selectCompartmentTwo();
        productContainerPage.verifyDeletedContainerRow();
    }

    public void verifySaveButton() {
        productContainerPage.saveUpdateContainer();
        productContainerPage.handleContainerCreateSuccess();
        productContainerPage.verifyCreatedContainerGroup();
    }

    public void verifyCancelButton() {
        productContainerPage.cancelContainerGroup();
        productContainerPage.verifyProductContainerPage();
    }
}
