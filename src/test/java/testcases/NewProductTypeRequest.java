package testcases;

import Action.LoginAction;
import Action.NewProductTypeRequestAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class NewProductTypeRequest extends WebTestBase {

    @Test
    public void productTypeRequestHeaderAndCheckbox() {
        test = getTest("TC_Asset Management_NewProductTypeRequest_273_282_300_281");
        LoginAction login = new LoginAction(driver);
        NewProductTypeRequestAction request = new NewProductTypeRequestAction(driver);
        login.logoutLogin();
        request.navigateToProductTypeRequestPage();
        request.verifyNewProductListHeaders();
        request.verifyCheckAllCheckbox();
        request.verifyDeleteFunctionality();
        request.verifySortingTheColumn();
    }

    @Test
    public void productTypeRequestSearchingFilter() {
        test = getTest("TC_Asset Management_NewProductTypeRequest_274_275_276_277_278_279_280");
        LoginAction login = new LoginAction(driver);
        NewProductTypeRequestAction request = new NewProductTypeRequestAction(driver);
        login.logoutLogin();
        request.navigateToProductTypeRequestPage();
        request.verifySearchFunctionInTitle();
        request.verifySearchFunctionInStatus();
        request.verifyFilterInBothTitleAndStatus();
    }

    @Test
    public void productTypeRequestViewDetailSection() {
        test = getTest("TC_Asset Management_NewProductTypeRequest_283_284_285_286_287_288");
        NewProductTypeRequestAction request = new NewProductTypeRequestAction(driver);
        LoginAction login = new LoginAction(driver);
        login.logoutLogin();
        request.navigateToProductTypeRequestPage();
        request.verifyDetailViewIconAndPage();
        request.verifyActiveRecordDetailsPageButtons();
        request.approveProductTypeFunctionality();
        request.rejectProductTypeFunctionality();
    }

    @Test
    public void postNewCommentOnViewDetailsPage() {
        test = getTest("TC_Asset Management_NewProductTypeRequest_289_290_291_292_293");
        NewProductTypeRequestAction request = new NewProductTypeRequestAction(driver);
        LoginAction login = new LoginAction(driver);
        login.logoutLogin();
        request.navigateToProductTypeRequestPage();
        request.verifyValidationMessageForPostNewComment();
        request.verifyMaxCharCommentFields();
        request.verifyAfterPostComment();
    }

    @Test
    public void requestNewProductType() {
        test = getTest("TC_Asset Management_NewProductTypeRequest_294_295_296_297_298_299_301");
        NewProductTypeRequestAction request = new NewProductTypeRequestAction(driver);
        LoginAction login = new LoginAction(driver);
        login.logoutLogin();
        request.navigateToProductTypeRequestPage();
        request.verifyProductListPageAndValidationMessage();
        request.verifyMaxCharCreateProdFields();
        request.verifyAfterCreateTheNewProductRequest();
        request.verifyUserGuideLink();
    }
}
