package testcases;

import Action.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class PendingRequest extends WebTestBase {

    @Test(priority = 1)
    public void verifyPendingRequestAndPendingCheckOutRequest() {
        test = getTest("TC_Asset Management_Pending_Request_380_381_382");
        new LoginAction(driver).logoutLogin();
        new CompanySetupAction(driver).changeTimeZoneAndShiftOfAdminUser();
        new CompanySetupAction(driver).changeTimeZoneAndShiftOfSecondUser();
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyPendingCheckOutRequest();
    }

    @Test(priority = 2)
    public void verifySearchClearSortingAndPaginationFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_383_384_385_386_390");
        new LoginAction(driver).logoutLogin();
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifySearchFunctionality();
        pendingRequestAction.verifyClearFunctionality();
        pendingRequestAction.verifySortingAndPaginationOfPendingRequestTab();
    }

    @Test(priority = 3)
    public void verifyAssignAndRejectFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_387_388_389");
        new LoginAction(driver).logoutLogin();
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.assignIconFunctionality();
        pendingRequestAction.rejectIconFunctionality();
    }

    @Test(priority = 4)
    public void verifyReturnRequest() {
        test = getTest("TC_Asset Management_Pending_Request_391_392_393_394_395_396_397_398_409");
        new LoginAction(driver).logoutLogin();
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyFunctionalityOfReturnRequestTab();
        pendingRequestAction.verifySearchFunctionalityInReturnRequest();
        pendingRequestAction.verifyClearFunctionalityInReturnRequest();
        pendingRequestAction.verifyPaginationReturnRequest();
    }

    @Test(priority = 5)
    public void verifyApproveFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_399_400_401_402_403_404");
        LoginAction loginAction = new LoginAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);

        loginAction.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        myProductAction.createAndAssignProductForReturn();
        loginAction.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        pendingRequestAction.returnProductFromSecondUser();
        loginAction.logoutLogin();
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.selectMultipleRowsAndValidationMessage();
        pendingRequestAction.verifyApproveButtonFunctionality();
    }

    @Test(priority = 6)
    public void verifyRejectFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_405_406_407_408");
        LoginAction loginAction = new LoginAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);

        loginAction.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        myProductAction.createAndAssignProductForReturn();
        loginAction.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        pendingRequestAction.returnProductFromSecondUser();
        loginAction.logoutLogin();
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyRejectButtonFunctionality();
    }

    @Test(priority = 7)
    public void verifyReplaceRequestPage() {
        test = getTest("TC_Asset Management_Pending_Request_410_411_425");
        new LoginAction(driver).logoutLogin();
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);

        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyColumnInReplaceRequest();
        //pendingRequestAction.verifySortingOfReplaceRequest(); Disabled as per the review comment
        pendingRequestAction.verifyPaginationOfReplaceRequest();
    }

    @Test(priority = 8)
    public void verifyApproveAndRejectRequestFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_412_413_414_415_416");
        LoginAction loginAction = new LoginAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);

        loginAction.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        myProductAction.createAndAssignProductForReturn();
        loginAction.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        pendingRequestAction.returnProductFromSecondUser();
        loginAction.logoutLogin();
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyApproveRejectCancelFunctionality();
        pendingRequestAction.verifyApproveRejectSaveFunctionality();
    }

    @Test(priority = 9)
    public void verifyRepairFunctionality() {
        test = getTest("TC_Asset Management_Pending_Request_417_418_419_420_421_422_423_424");
        LoginAction loginAction = new LoginAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);

        loginAction.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        myProductAction.createConsumableProduct();
        loginAction.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        myProductAction.verifyReplaceRequestFunction();
        loginAction.logoutLogin();
        pendingRequestAction.goToThePendingRequestPage();
        pendingRequestAction.verifyCancelRepairFunctionality();
        pendingRequestAction.verifySaveRepairFunctionality();
    }

}
