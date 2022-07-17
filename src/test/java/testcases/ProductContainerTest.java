package testcases;

import Action.AddProductAction;
import Action.LoginAction;
import Action.ProductContainerAction;
import Action.ProductTypeAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductContainerTest extends WebTestBase {

    @Test
    public void containerPageBreadCrumbSearch() {
        test = getTest("TC_Asset Management_ProductContainerTest_251_252_253_254_256");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.verifyProductContainerPage();
        productContainer.verifyBreadCrumb();
        productContainer.navigateToProductContainerPage();
        productContainer.searchFieldAlphaNumeric();
        productContainer.searchContainer();
    }

    @Test
    public void containerPagination() {
        test = getTest("TC_Asset Management_ProductContainerTest_255");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.pagination();
    }

    @Test
    public void containerSort() {
        test = getTest("TC_Asset Management_ProductContainerTest_258");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.createMultipleContainers();
        productContainer.productContainerNameSort();
        productContainer.productContainerStatusSort();
        productContainer.productContainerDateSort();
    }

    @Test
    public void statusDeleteEditPage() {
        test = getTest("TC_Asset Management_ProductContainerTest_259_260_261_262");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.changeStatus();
        productContainer.changeStatus();
        productContainer.editProductContainer();
        productContainer.deleteContainer();
    }

    @Test
    public void createProdType() {
        test = getTest("TC_Asset Management_ProductContainerTest_257_263");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.navigateToAddContainerPage();
        productContainer.verifyCreateContainerPage();
        productType.goToTheProductTypePage();
        productContainer.getProductTypeWithContainer();
        productContainer.navigateToProductContainerPage();
        productContainer.navigateToAddContainerPage();
        productContainer.verifyProdDopDown();
    }

    @Test
    public void verifyLocationList() {
        test = getTest("TC_Asset Management_ProductContainerTest_264");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productContainer.navigateToProductContainerPage();
        productContainer.verifyLocations();
    }

    @Test
    public void productInContainerDD() {
        test = getTest("TC_Asset Management_ProductContainerTest_265");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        AddProductAction addProductAction = new AddProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        productContainer.getProductTypeWithContainer();
        addProductAction.gotoManageProductPage();
        productContainer.verifyProductInContainerDropDown();
    }

    @Test
    public void verifyProductsInProdNameDropdown() {
        test = getTest("TC_Asset Management_ProductContainerTest_266");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        AddProductAction addProductAction = new AddProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        addProductAction.gotoManageProductPage();
        productContainer.verifyProductsInProductNameDD();
    }

    @Test
    public void compartmentVerification() {
        test = getTest("TC_Asset Management_ProductContainerTest_267");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        productContainer.getCompartmentList();
        productType.goToTheProductTypePage();
        productContainer.deployProductForCompartment();
        productContainer.navigateToProductContainerPage();
        productContainer.navigateToAddContainerPage();
        productContainer.verifyCompartment();
    }

    @Test
    public void compartmentGroupRow() {
        test = getTest("TC_Asset Management_ProductContainerTest_268_269_270_271_272");
        ProductContainerAction productContainer = new ProductContainerAction(driver);
        AddProductAction addProductAction = new AddProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        productContainer.getProductTypeWithContainer();
        addProductAction.gotoManageProductPage();
        productContainer.deployProductForAddRowFunction();
        productContainer.addRowFunction();
        productContainer.verifyDupProductErrorMsg();
        productContainer.verifyDeleteButton();
        productContainer.verifySaveButton();
        productContainer.navigateToAddContainerPage();
        productContainer.verifyCancelButton();
    }
}
