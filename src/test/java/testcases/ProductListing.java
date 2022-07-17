package testcases;

import Action.AddProductAction;
import Action.LoginAction;
import Action.ProductListingAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductListing extends WebTestBase {

    @Test(priority = 1)
    public void productListHeaderAndCheckBox() {
        test = getTest("TC_Asset Management_ProductListing_175_176_177_178_182");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyProductListHeader();
        productListing.disabledCheckBoxOfProduct();
        productListing.enabledCheckBoxOfProduct();
        productListing.enabledDeleteButton();
        productListing.uniqueNameGreenStrip();
        productListing.verifyProductImage();
    }

    @Test(priority = 2)
    public void productListPaginationAndDelete() {
        test = getTest("TC_Asset Management_ProductListing_179_180");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.bulkDeleteFunction();
        productListing.verifyPaginationOnProductListPage();
    }

    @Test(priority = 3)
    public void verifyProductSortAndEditPage() {
        test = getTest("TC_Asset Management_ProductListing_181_183");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyEditPage();
        productListing.productListSorting();
    }

    @Test(priority = 4)
    public void verifyExpandAndSearchFunction() {
        test = getTest("TC_Asset Management_ProductListing_184_185_186_187");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.expandAllFunction();
        productListing.searchFunction();
    }

    @Test(priority = 5)
    public void verifyMoreActions() {
        test = getTest("TC_Asset Management_ProductListing_188_189_190");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifySubStatusPopup();
        productListing.verifyProductHistoryPage();
        addProduct.gotoManageProductPage();
        productListing.verifyAttachmentPage();
    }

    @Test(priority = 6)
    public void verifyDepreciationPageFunctions() {
        test = getTest("TC_Asset Management_ProductListing_191_192_193_194_195");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyDepreciationListingScreen();
        addProduct.gotoManageProductPage();
        productListing.depreciationSearchFunction();
    }

    @Test(priority = 7)
    public void verifyDepreciationPaginationFunction() {
        test = getTest("TC_Asset Management_ProductListing_196");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.depreciationPagination();
    }

    @Test(priority = 8)
    public void importProductPageFunctionOne() {
        test = getTest("TC_Asset Management_ProductListing_197_198_199_200");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyImportPageFunctions();
    }

    @Test(priority = 9)
    public void importProductPageFunctionTwo() {
        test = getTest("TC_Asset Management_ProductListing_201_202_203_204_205_206");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.importBulkProduct();
        productListing.editFunctionInImportedProduct();
    }

    @Test(priority = 10)
    public void importProductPageFunctionThree() {
        test = getTest("TC_Asset Management_ProductListing_207");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyImportCancelFunction();
    }

    @Test(priority = 11)
    public void verifyAddProductScreenFunction() {
        test = getTest("TC_Asset Management_ProductListing_208_209");

        ProductListingAction productListing = new ProductListingAction(driver);
        AddProductAction addProduct = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProduct.gotoManageProductPage();
        productListing.verifyAddProductScreen();
    }
}
