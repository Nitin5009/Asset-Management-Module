package testcases;

import Action.LoginAction;
import org.testng.annotations.Test;
import pageobjects.DeployProductPage;
import pageobjects.InTransitPage;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class InTransit extends WebTestBase {

    @Test(priority = 1)
    public void navigateToInTransit() {
        test = getTest("TC_Asset Management_InTransit_list_237_238");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        login.logoutLogin();
        inTransit.navigateToInTransitPage();
        inTransit.verifyInTransitPage();
        inTransit.verifyBreadcrumbs();
    }

    @Test(priority = 2)
    public void searchAlphanumericProductName() {
        test = getTest("TC_Asset Management_InTransit_list_239_240_241_242");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        login.logoutLogin();
        inTransit.navigateToInTransitPage();
        inTransit.expandAllOrCollapseAll();
        inTransit.verifyExpandAllLocation();
        inTransit.verifyExpandAllProductName();
        inTransit.enterProductName();
        inTransit.searchOption();
        inTransit.verifySearchedProductName();
        inTransit.resetButton();
        inTransit.expandAllOrCollapseAll();
        inTransit.clickLocationField();
        inTransit.selectLocation();
        inTransit.searchOption();
        inTransit.verifySelectedLocation();
        inTransit.checkSearchFiledIsEmpty();
        inTransit.checkSearchBarIsEmptyInLocationField();
    }

    @Test(priority = 3)
    public void checkInTransitPageHeaders() {
        test = getTest("TC_Asset Management_InTransit_list_243");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        login.logoutLogin();
        inTransit.navigateToInTransitPage();
        inTransit.verifyInTransitPageHeaders();
    }

    @Test(priority = 6)
    public void acceptOrReject() {
        test = getTest("TC_Asset Management_InTransit_list_244_245_246_247_248");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        login.logoutLogin();
        inTransit.navigateToInTransitPage();
        inTransit.getStatusBeforeAcceptOrReject();
        inTransit.clickInTransitLink();
        inTransit.clickAcceptorRejectButton();
        inTransit.selectAcceptOption();
        inTransit.saveAcceptorReject();
        inTransit.navigateToSubStatusPopup();
        inTransit.verifyTransferReqActiveStatus();
        inTransit.navigateToInTransitPage();
        inTransit.getStatusBeforeAcceptOrReject();
        inTransit.clickInTransitLink();
        inTransit.clickAcceptorRejectButton();
        inTransit.selectRejectOption();
        inTransit.saveAcceptorReject();
        inTransit.navigateToSubStatusPopup();
        inTransit.verifyTransferReqRejectStatus();
    }

    @Test(priority = 4)
    public void sortedLocations() {
        test = getTest("TC_Asset Management_InTransit_list_249");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        DeployProductPage deployProduct = new DeployProductPage(driver);
        login.logoutLogin();
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        deployProduct.clickManageProduct();
        inTransit.addMultipleInTransitReq();
        deployProduct.handleSuccessPopup();
        inTransit.navigateToInTransitPage();
        inTransit.productLocationAscendingOrder();
        inTransit.productLocationDescendingOrder();
    }

    @Test(priority = 5)
    public void verifyPagination() {
        test = getTest("TC_Asset Management_InTransit_list_250");
        LoginAction login = new LoginAction(driver);
        InTransitPage inTransit = new InTransitPage(driver);
        login.logoutLogin();
        inTransit.navigateToInTransitPage();
        inTransit.verifyPaginationFunctionality();
        inTransit.selectRecordPagination();
    }
}
