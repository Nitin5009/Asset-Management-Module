package testcases;

import Action.*;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class AssetDashBoardTest extends WebTestBase {

    @Test
    public void inTransitWidgetCAFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_477_478_479");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        assetDashBoardAction.navigateToInTransitPage();
        assetDashBoardAction.createAndTransferProduct();
        assetDashBoardAction.getInTransitProductCount();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyInTransitWidgetOfCA();
    }

    @Test
    public void pendingByUserWidgetCAFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_486_487");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        assetDashBoardAction.createAndAssignPendingUserProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.getRedProductCountCA();
        login.logoutLogin();
        assetDashBoardAction.verifyCumulativePendingByUserCA();
        login.logoutLoginSecondUser();
        assetDashBoardAction.acceptAssignedProductCA();
        assetDashBoardAction.getRedProductCountCA();
        login.logoutLogin();
        assetDashBoardAction.verifyCumulativePendingByUserCA();
    }

    @Test
    public void returnOverDueWidgetCAFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_493,494,495,496");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.getOverDueProductCountForCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountCA();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.returnProductForWidgetFromProductAssignment();
        assetDashBoardAction.getOverDueProductCountForCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountCA();
    }

    @Test
    public void pendingByManagerWidgetCAFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_508_509_510");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        companySetupAction.configUserForSelfCheckOutNo();
        assetDashBoardAction.createAndAssignPendingManagerProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.returnProductFromMyProductPage();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.getAcceptPendingProductForWidgetCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductInPendingByManagerWidgetCA();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.acceptThePendingProductCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductRemovePendingByManagerWidgetCA();
    }

    @Test
    public void verifyInTransitWidgetForProjectManager() {
        test = getTest("Asset Management_Asset DashBoard_480_481_482_483");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        assetDashBoardAction.navigateToInTransitPage();
        assetDashBoardAction.createAndTransferProduct();
        assetDashBoardAction.getInTransitProductCount();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyInTransitWidgetCumulativeCountOfPM();
    }

    @Test
    public void verifyPendingByUserWidgetForProjectManager() {
        test = getTest("Asset Management_Asset DashBoard_488_489_490");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        companySetupAction.setShiftForProjectManager();
        assetDashBoardAction.createAndAssignPendingUserProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.getRedProductNamesForPMUser();
        login.logoutLogin();
        assetDashBoardAction.getProductCountBasedOnLocation();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyPendingByUserWidgetForPM();
        login.logoutLoginSecondUser();
        assetDashBoardAction.acceptAssignedProductCA();
        assetDashBoardAction.getRedProductNamesForPMUser();
        login.logoutLogin();
        assetDashBoardAction.getProductCountBasedOnLocation();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyPendingByUserWidgetForPM();
    }


    @Test
    public void returnOverDueWidgetPMFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_493,494,495,496");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.getOverDueProductCountByAllShift();
        assetDashBoardAction.getOverDueProductCountBySameShift();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountPMAllShift();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountPMSameShift();
        login.logoutLogin();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.returnProductForWidgetFromProductAssignmentByPMShift();
        assetDashBoardAction.getOverDueProductCountBySameShift();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountPMSameShift();
    }


    @Test
    public void pendingByManagerWidgetPMFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_511_512_513");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        companySetupAction.setShiftForNormalUser();
        assetDashBoardAction.createAndAssignPendingManagerProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.returnProductFromMyProductPage();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.getAcceptPendingProductForWidgetPM();
        assetDashBoardAction.getProductNotToDisplayInWidgetPM();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyOtherLocationProdInPendingByManagerWidgetPM();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.getAcceptPendingProductForWidgetPM();
        assetDashBoardAction.getProductShouldToDisplayInWidgetPM();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyOtherLocationProdInPendingByManagerWidgetPM();
        assetDashBoardAction.verifyProductInPendingByManagerWidgetPM();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.acceptThePendingProductPM();
        pendingRequestAction.getAcceptPendingProductForWidgetPM();
        assetDashBoardAction.getProductShouldToDisplayInWidgetPM();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductRemovePendingByManagerWidgetPM();
    }


    @Test
    public void verifyInTransitWidgetFunctionalityOfNU() {
        test = getTest("Asset Management_Asset DashBoard_484_485");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        assetDashBoardAction.navigateToInTransitPage();
        assetDashBoardAction.createAndTransferProduct();
        assetDashBoardAction.getInTransitProductCount();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyInTransitWidgetCumulativeCountOfNU();
    }

    @Test
    public void verifyPendingByUserWidgetForNormalUser() {
        test = getTest("Asset Management_Asset DashBoard_491_492");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        companySetupAction.setShiftForNormalUser();
        assetDashBoardAction.createAndAssignPendingUserProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.getRedProductNamesForNUUser();
        login.logoutLogin();
        assetDashBoardAction.getProductCountBasedOnLocation();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyPendingByUserWidgetForNU();
    }


    @Test
    public void returnOverDueWidgetNUFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_500_501");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.getOverDueProductCountByAllShift();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountNUAllShift();
        login.logoutLogin();
        assetDashBoardAction.navigateToProductAssignmentPage();
        assetDashBoardAction.getOverDueProductCountForNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyReturnOverDueWidgetCumulativeCountNUSameShift();
    }


    @Test
    public void pendingByManagerWidgetNUFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_514_515_516");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        PendingRequestAction pendingRequestAction = new PendingRequestAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        assetDashBoardAction.createAndAssignPendingManagerProd();
        login.logoutLoginSecondUser();
        assetDashBoardAction.returnProductFromMyProductPage();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.getAcceptPendingProductForWidgetNU();
        assetDashBoardAction.getProductsNotToDisplayInWidgetNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyOtherLocationProdInPendingByManagerWidgetNU();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.getAcceptPendingProductForWidgetNU();
        assetDashBoardAction.getProductsToDisplayInWidgetNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductInPendingByManagerWidgetNU();
        login.logoutLogin();
        pendingRequestAction.navigateToReturnRequest();
        pendingRequestAction.acceptThePendingProductNU();
        pendingRequestAction.getAcceptPendingProductForWidgetNU();
        assetDashBoardAction.getProductsToDisplayInWidgetNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductRemovePendingByManagerWidgetNU();
    }

    @Test
    public void verifyAssetDashBoardFunctionality() {
        test = getTest("Asset Management_Asset DashBoard_463_464_465_466");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.manageWidgetProcess();
        assetDashBoardAction.zoomInZoomOutFunction();
    }


    @Test
    public void verifyFiltersInProductByStatusWidget() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuUnderProductByStatusWidget();
        assetDashBoardAction.verifyStartToOfThisYearProductByStatus();
        assetDashBoardAction.verifyStartToOfThisMonthProductByStatus();
        assetDashBoardAction.verifyStartToOfLastMonthProductByStatus();
        assetDashBoardAction.verifySpecificDateFieldProductByStatus();
        assetDashBoardAction.verifyStartToOfThisWeekProductByStatus();
        assetDashBoardAction.verifyStartToOfLastWeekProductByStatus();
    }

    @Test
    public void verifyFiltersInProductByLocationWidget() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuUnderProductByLocationWidget();
        assetDashBoardAction.verifyStartToOfThisYearProductByLocation();
        assetDashBoardAction.verifyStartToOfThisMonthProductByLocation();
        assetDashBoardAction.verifyStartToOfLastMonthProductByLocation();
        assetDashBoardAction.verifySpecificDateFieldProductByLocation();
        assetDashBoardAction.verifyStartToOfThisWeekProductByLocation();
        assetDashBoardAction.verifyStartToOfLastWeekProductByLocation();
    }


    @Test
    public void verifyFiltersInProductPendingByUserWidget() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuUnderPendingByUserWidget();
        assetDashBoardAction.verifyStartToOfThisYearProductPendingByUser();
        assetDashBoardAction.verifyStartToOfThisMonthProductPendingByUser();
        assetDashBoardAction.verifyStartToOfLastMonthProductPendingByUser();
        assetDashBoardAction.verifySpecificDateFieldProductPendingByUser();
        assetDashBoardAction.verifyStartToOfThisWeekProductPendingByUser();
        assetDashBoardAction.verifyStartToOfLastWeekProductPendingByUser();
    }


    @Test
    public void verifyFiltersInProductPendingByManagerWidget() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuUnderPendingByManagerWidget();
        assetDashBoardAction.verifyStartToOfThisYearProductPendingByManager();
        assetDashBoardAction.verifyStartToOfThisMonthProductPendingByManager();
        assetDashBoardAction.verifyStartToOfLastMonthProductPendingByManager();
        assetDashBoardAction.verifySpecificDateFieldProductPendingByManager();
        assetDashBoardAction.verifyStartToOfThisWeekProductPendingByManager();
        assetDashBoardAction.verifyStartToOfLastWeekProductPendingByManager();
    }


    @Test
    public void verifyFiltersInProductInTransitStatus() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuInInTransitWidgets();
        assetDashBoardAction.verifyStartToOfThisYearInTransitStatus();
        assetDashBoardAction.verifyStartToOfThisMonthInTransitStatus();
        assetDashBoardAction.verifyStartToOfLastMonthInTransitStatus();
        assetDashBoardAction.verifySpecificDateFieldInTransitStatus();
        assetDashBoardAction.verifyStartToOfThisWeekInTransitStatus();
        assetDashBoardAction.verifyStartToOfLastWeekInTransitStatus();
    }


    @Test
    public void verifyFiltersInProductReturnOverDue() {
        test = getTest("Asset Management_Asset DashBoard_467_468_469_470_471_472_473_474");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyMenuUnderReturnOverDueWidget();
        assetDashBoardAction.verifyStartToOfThisYearProductReturnOverDue();
        assetDashBoardAction.verifyStartToOfThisMonthProductReturnOverDue();
        assetDashBoardAction.verifyStartToOfLastMonthProductReturnOverDue();
        assetDashBoardAction.verifySpecificDateFieldProductReturnOverDue();
        assetDashBoardAction.verifyStartToOfThisWeekProductReturnOverDue();
        assetDashBoardAction.verifyStartToOfLastWeekProductReturnOverDue();
    }

    @Test
    public void verifySpecificDateGlobalFilter() {
        test = getTest("Asset Management_Asset DashBoard_475");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifySpecificDateFieldGlobal();
    }

    @Test
    public void verifyProductByStatusAndProductByLocationWidgetCA() {
        test = getTest("Asset Management_Asset DashBoard_502_505");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        ProductReportAction productReportAction = new ProductReportAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        productReportAction.navigateToProductReportPage();
        productReportAction.getActiveStatusProductCountForCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByStatusCA();
        productReportAction.navigateToProductReportPage();
        productReportAction.getProductCountBasedOnLocationCA();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByLocationCA();
    }

    @Test
    public void verifyProductByStatusAndProductByLocationWidgetPM() {
        test = getTest("Asset Management_Asset DashBoard_503_506");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        ProductReportAction productReportAction = new ProductReportAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForProjectManager();
        productReportAction.navigateToProductReportPage();
        productReportAction.getActiveStatusProductCountForPM();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByStatusPM();
        login.logoutLogin();
        productReportAction.navigateToProductReportPage();
        productReportAction.getProductCountBasedOnLocationPM();
        login.logoutLoginProjectManager();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByLocationPM();
    }

    @Test
    public void verifyProductByStatusAndProductByLocationWidgetNU() {
        test = getTest("Asset Management_Asset DashBoard_504_507");
        AssetDashBoardAction assetDashBoardAction = new AssetDashBoardAction(driver);
        ProductReportAction productReportAction = new ProductReportAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        productReportAction.navigateToProductReportPage();
        productReportAction.getActiveStatusProductCountForNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByStatusNU();
        login.logoutLogin();
        productReportAction.navigateToProductReportPage();
        productReportAction.getProductCountBasedOnLocationNU();
        login.logoutLoginSecondUser();
        assetDashBoardAction.navigateToAssetDashBoardPage();
        assetDashBoardAction.verifyProductByLocationNU();
    }
}
