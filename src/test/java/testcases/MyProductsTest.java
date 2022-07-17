package testcases;

import Action.*;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class MyProductsTest extends WebTestBase {

    @Test(priority = 1)
    public void verifyRequestQuantityFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_333_334_335_336_337_338");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        PendingRequestAction pendingRequest = new PendingRequestAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetup.changeTimeZoneAndShiftOfAdminUser();
        companySetup.changeTimeZoneAndShiftOfSecondUser();
        companySetup.configUserForSelfCheckOutNo();
        productType.goToTheProductTypePage();
        myProduct.createRequestQuantityProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.acceptEmpAcceptProduct();
        myProduct.verifyRequestQuantityFunction();
        login.logoutLogin();
        pendingRequest.navigateToPendingCheckOutRequestPage();
        myProduct.verifyQuantityRequest();
    }

    @Test(priority = 2)
    public void verifyAcceptancePending() {
        test = getTest("TC_Asset Management_MyProduct_309");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetup.configUserForSelfCheckOutYes();
        productType.goToTheProductTypePage();
        myProduct.createAssignEmpAcceptProdType();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyRedLegend();
        myProduct.acceptEmpAcceptProduct();
        myProduct.rejectEmpAcceptProduct();
    }

    @Test(priority = 3)
    public void verifyRequestedForReturn() {
        test = getTest("TC_Asset Management_MyProduct_313");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        ProductAssignmentAction productAssignment = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createAndAssignProductForReturn();
        productAssignment.navigateToProductAssignmentPage();
        productAssignment.raiseRequestForReturn();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyYellowLegend();
    }

    @Test(priority = 4)
    public void verifyBlueLegendProduct() {
        test = getTest("TC_Asset Management_MyProduct_315");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createProdForContainer();
        myProduct.createContainer();
        myProduct.assignContainerProdToUser();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyBlueLegend();
    }

    @Test(priority = 5)
    public void verifyConsumeFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_316_317_318_319_320_321");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createConsumableProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyConsumeFunction();
    }

    @Test(priority = 6)
    public void verifyReplaceRequestFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_322_323_324_325_326");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        PendingRequestAction pendingRequest = new PendingRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createConsumableProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyReplaceRequestFunction();
        login.logoutLogin();
        pendingRequest.navigateToReplaceRequestPage();
        myProduct.verifyCreatedReplaceRequest();
    }

    @Test(priority = 7)
    public void verifySelfCheckOutFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_327_328_329_330_331_332");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createSelfCheckOutProdType();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifySelfCheckOutFunction();
    }

    @Test(priority = 8)
    public void verifyProductTransferFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_339_340_341_342_343_344");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetup.configUserForSelfTransfer();
        productType.goToTheProductTypePage();
        myProduct.createTransferableProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyTransferFunction();
    }

    @Test(priority = 9)
    public void verifyReturnFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_345_346_347_348_349");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        PendingRequestAction pendingRequest = new PendingRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createAndAssignProductForReturn();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyReturnFunction();
        login.logoutLogin();
        pendingRequest.navigateToReturnRequest();
        myProduct.verifyCreatedReturnRequest();
    }

    @Test(priority = 10)
    public void verifyTermsAndConditionFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_350");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createAndAssignProductForTAndC();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyTermsAndConditionFunction();
    }

    @Test(priority = 11)
    public void verifyMyProductPage() {
        test = getTest("TC_Asset Management_MyProduct_302_303_304_305_306_308");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createAssignEmpAcceptProdType();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyMyProductPage();
        myProduct.verifySearchBarInMyProdPage();
        myProduct.verifyBarCodeSearchAndReset();
        myProduct.verifyQRCodeBarCodeType();
        //myProduct.verifyProductSorting();          Disabled this As per the review comment
    }

    @Test(priority = 12)
    public void verifyCheckOutRequestListFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_352_353_359");
        MyProductAction myProduct = new MyProductAction(driver);
        CheckOutListAction checkOutList = new CheckOutListAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        login.logoutLoginSecondUser();
        checkOutList.navigateToViewCheckOutListPage();
        checkOutList.verifyCheckOutListHeader();
        checkOutList.verifyCheckOutListPagination();
    }

    @Test(priority = 13)
    public void verifyCheckOutListSearchFilter() {
        test = getTest("TC_Asset Management_MyProduct_354_355_356_357_358_378");
        MyProductAction myProduct = new MyProductAction(driver);
        CheckOutListAction checkOutList = new CheckOutListAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        AddCheckOutRequestAction addCheckOutRequest = new AddCheckOutRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createProductForBarCodeSearch();
        login.logoutLoginSecondUser();
        addCheckOutRequest.navigateToAddCheckOutListPage();
        addCheckOutRequest.addCheckOutProduct();
        addCheckOutRequest.verifyProductSaveButtonFunction();
        checkOutList.navigateToViewCheckOutListPage();
        checkOutList.verifyExpandAndCollapseFilter();
        checkOutList.verifyUniqueNameDropdownFunction();
    }

    @Test(priority = 14)
    public void verifyAddCheckOutListPage() {
        test = getTest("TC_Asset Management_MyProduct_360_361");
        MyProductAction myProduct = new MyProductAction(driver);
        AddCheckOutRequestAction addCheckOutRequest = new AddCheckOutRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        login.logoutLoginSecondUser();
        addCheckOutRequest.navigateToAddCheckOutListPage();
        addCheckOutRequest.breadCrumbInAddCheckOutListPage();
    }

    @Test(priority = 15)
    public void verifyAddCheckOutListPageFields() {
        test = getTest("TC_Asset Management_MyProduct_362_363_364_365_366");
        MyProductAction myProduct = new MyProductAction(driver);
        AddCheckOutRequestAction addCheckOutRequest = new AddCheckOutRequestAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createProductForBarCodeSearch();
        login.logoutLoginSecondUser();
        addCheckOutRequest.navigateToAddCheckOutListPage();
        addCheckOutRequest.assignDateFieldsFunction();
        addCheckOutRequest.verifyAssignTimesField();
        addCheckOutRequest.verifyAlphaNumericCondition();
        addCheckOutRequest.searchBarCode();
        addCheckOutRequest.verifyCheckOutListHeader();
    }

    @Test(priority = 16)
    public void verifyAddCheckOutListDeleteFunctionality() {
        test = getTest("TC_Asset Management_MyProduct_367_368_369_370_371_372_373_374_375_376_377_379");
        MyProductAction myProduct = new MyProductAction(driver);
        AddCheckOutRequestAction addCheckOutRequest = new AddCheckOutRequestAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createProductForBarCodeSearch();
        login.logoutLoginSecondUser();
        addCheckOutRequest.navigateToAddCheckOutListPage();
        addCheckOutRequest.addCheckOutProduct();
        addCheckOutRequest.verifyListOfProdTableHeader();
        addCheckOutRequest.verifyCancelButtonFunction();
        addCheckOutRequest.navigateToAddCheckOutListPage();
        addCheckOutRequest.addCheckOutProduct();
        addCheckOutRequest.verifyQuantityFieldFunction();
        addCheckOutRequest.verifyProductDeleteFunction();
    }
}
