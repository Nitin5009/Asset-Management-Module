package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.ProductTransferPage;

public class ProductTransferAction {
    WebDriver driver;
    ProductTransferPage productTransferPage;
    AddProductAction addProductAction;

    public ProductTransferAction(WebDriver driver) {
        this.driver = driver;
        productTransferPage = new ProductTransferPage(driver);
        addProductAction = new AddProductAction(driver);
    }

    public void goToTheProductTransferPage() {
        addProductAction.gotoManageProductPage();
        productTransferPage.clickProductTransfer();
    }

    public void createNewProduct() {
        addProductAction.gotoManageProductPage();
        productTransferPage.createNewProduct();
        productTransferPage.clickProductTransfer();
    }

    public void verifyProductTransferPageAndBreadCrumbInThePage() {
        productTransferPage.verifyProductTransferPage();
        productTransferPage.verifyUserGuide();
        productTransferPage.verifyBreadCrumbInProductTransferPage();
    }

    public void verifyAlphaNumericValueInBarCodeField() {
        productTransferPage.enterAlphaNumericBarCodeNumber();
    }

    public void verifyBarCodeAndSearchFieldFunctionalities() {

        createNewProduct();
        productTransferPage.enterBarCodeNumber();
        productTransferPage.clickBarCodeSearchButton();
        productTransferPage.verifySearchedBarCodeProduct();
        productTransferPage.selectLocation();
        productTransferPage.selectProductType();
        productTransferPage.selectProductName();
        productTransferPage.enterUniqueName();
        productTransferPage.verifyUniqueNameDropDown();
        productTransferPage.selectUniqueName();
        productTransferPage.clickSearchButton();
        productTransferPage.verifySearchedProduct();
        productTransferPage.verifyHeaderForSearchedResultTable();
    }

    public void enterAlphaNumericValues() {
        createNewProduct();
        productTransferPage.enterBarCodeNumber();
        productTransferPage.clickBarCodeSearchButton();
        productTransferPage.enterAlphaNumericInProductCode();
        productTransferPage.verifyAlphaNumericIsAllowedInProdCodeField();
        productTransferPage.verifyEnteredProductCodeInResultTable();
        productTransferPage.enterAlphaNumericInProductName();
        productTransferPage.verifyAlphaNumericIsAllowedInProdNameField();
        productTransferPage.verifyEnteredProductNameInResultTable();
        productTransferPage.enterAlphaNumericInUniqueName();
        productTransferPage.verifyAlphaNumericIsAllowedInUniqueNameField();
        productTransferPage.verifyEnteredUniqueNameInResultTable();
        productTransferPage.verifySearchBoxAfterReset();
    }

    public void verifyMultipleCheckBoxAndTransferQuantityField() {

        addProductAction.gotoManageProductPage();
        productTransferPage.createNewProductWithoutUniqueName();
        productTransferPage.clickProductTransfer();
        productTransferPage.searchProduct();
        productTransferPage.clickSelectAllCheckBox();
        productTransferPage.verifyAfterSelectAllCheckBoxClicked();
        productTransferPage.enterAndVerifyCharacterInTransferQuantity();
        productTransferPage.verifyTransferQuantityField();

    }

    public void VerifyProductIsAddedOnProductMoveToListTable() {
        createNewProduct();
        productTransferPage.searchProduct();
        productTransferPage.clickCheckBox();
        productTransferPage.AddProductToProductMoveToList();
        productTransferPage.selectToLocation("");
        productTransferPage.verifyHeaderForProductMoveToListTable();
        productTransferPage.clickCancelButton();
        productTransferPage.verifyResultTableAfterCancel();
    }

    public void verifyProductMoveToListAndDeleteAndPagination() {
        createNewProduct();
        productTransferPage.searchProduct();
        productTransferPage.verifyPaginationFunctionalities();
        productTransferPage.clickSelectAllCheckBox();
        productTransferPage.moveAllTheProductToList();
        productTransferPage.clickDeleteAndVerifyProductMoveToListTable();

    }

    public void verifyAfterProductIsMovedToTheLocation() {
        createNewProduct();
        productTransferPage.saveTheProductToAnotherLocationAndVerify("");
        addProductAction.gotoManageProductPage();
        productTransferPage.navigateToInTransitPage();
        productTransferPage.clickExpandAllOnInTransitPage();
        productTransferPage.selectLocationOnly();
        productTransferPage.enterProductNameOrCode();
        productTransferPage.clickSearch();
        productTransferPage.verifyRequestIsCreated();

    }

}
