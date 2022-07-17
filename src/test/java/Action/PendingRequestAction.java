package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.*;

public class PendingRequestAction {

    PendingRequestPage pendingRequest;
    DeployProductPage deployProduct;
    ReturnRequestPage returnRequest;
    ReplaceRequestPage replaceRequest;
    WebDriver driver;

    public PendingRequestAction(WebDriver driver) {
        this.pendingRequest = new PendingRequestPage(driver);
        this.deployProduct = new DeployProductPage(driver);
        this.returnRequest = new ReturnRequestPage(driver);
        this.replaceRequest = new ReplaceRequestPage(driver);
        this.driver = driver;
    }

    public void navigateToPendingRequestPage() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        pendingRequest.clickPendingRequestMenu();
    }

    public void navigateToReplaceRequestPage() {
        navigateToPendingRequestPage();
        pendingRequest.sideBarReplaceRequest();
    }

    public void navigateToPendingCheckOutRequestPage() {
        navigateToPendingRequestPage();
        pendingRequest.sideBarPendingCheckOutRequest();
    }

    public void navigateToReturnRequest() {
        navigateToPendingRequestPage();
        pendingRequest.sideBarReturnRequest();
    }

    public void getAcceptPendingProductForWidgetCA() {
        pendingRequest.getPendingProductCount("CompanyAdmin");
    }

    public void getAcceptPendingProductForWidgetPM() {
        pendingRequest.getPendingProductCount("ProjectManager");
    }

    public void getAcceptPendingProductForWidgetNU() {
        pendingRequest.getPendingProductCount("NormalUser");
    }

    public void acceptThePendingProductCA() {
        pendingRequest.AcceptTheReturnRequest("CompanyAdmin");
    }

    public void acceptThePendingProductPM() {
        pendingRequest.AcceptTheReturnRequest("ProjectManager");
    }

    public void acceptThePendingProductNU() {
        pendingRequest.AcceptTheReturnRequest("NormalUser");
    }

    public void goToThePendingRequestPage() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        pendingRequest.clickPendingRequestMenu();
    }

    public void verifyPendingCheckOutRequest() {
        pendingRequest.clickPendingCheckOutRequestTab();
        pendingRequest.VerifyBreadCrumbInProductRequestPage();
    }

    public void verifySearchFunctionality() {
        pendingRequest.countOfPendingRequestRow();
        pendingRequest.verifyProductNameFieldClickable();
        pendingRequest.verifyUniqueNameFieldClickable();
        pendingRequest.verifySearchButtonFunctionality();
    }

    public void verifyClearFunctionality() {
        pendingRequest.verifyTheClearButtonFunctionality();
    }

    public void verifySortingAndPaginationOfPendingRequestTab() {
        //pendingRequest.verifyAscendingDescendingOfUniqueName();
        //pendingRequest.verifyAscendingDescendingOfFromTime();
        //pendingRequest.verifyAscendingDescendingOfToTime();              disabled as per the review comment
        pendingRequest.paginationOnPendingRequestTab();
    }

    public void assignIconFunctionality() {
        pendingRequest.countOfPendingRequestRow();
        pendingRequest.verifyAssignIconIsClick();
        pendingRequest.verifyFunctionalityOfAssignButton();
    }

    public void rejectIconFunctionality() {
        pendingRequest.countOfPendingRequestRow();
        pendingRequest.verifyRejectIconIsClick();
        pendingRequest.rejectReason();
    }

    public void verifyFunctionalityOfReturnRequestTab() {
        returnRequest.verifyReturnRequestLinkClickable();
        returnRequest.verifyReturnRequestScreenDisplayed();
        returnRequest.verifyTheColumnsInReturnRequest();
    }

    public void verifySearchFunctionalityInReturnRequest() {
        returnRequest.getTheSearchStatusAndPenaltyValue();
        returnRequest.searchFieldInAlphaNumericValue();
        returnRequest.searchStatusInDropdownField();
        returnRequest.searchPenaltyStatus();
        returnRequest.verifySearchButtonFunctionality();
    }

    public void verifyClearFunctionalityInReturnRequest() {
        returnRequest.verifyClearButtonFunctionality();
        returnRequest.verifyClearButtonDetails();
    }

    public void verifyPaginationReturnRequest() {
        returnRequest.paginationOnReturnRequestTab();
    }

    public void returnProductFromSecondUser() {
        MyProductPage myProductPage = new MyProductPage(driver);
        myProductPage.clickReturnOption();
        myProductPage.verifyAlphaNumericReturnRemark();
        myProductPage.selectQuantityForReturn();
        myProductPage.selectReasonForReturn();
        myProductPage.saveReturnPopup();
        //myProductPage.handleReturnReqSuccessMsg();
    }

    public void selectMultipleRowsAndValidationMessage() {
        returnRequest.verifyReturnRequestLinkClickable();
        returnRequest.verifyMultipleRowIsSelected();
        returnRequest.verifyApproveAndRejectFunctionality();
    }

    public void verifyApproveButtonFunctionality() {
        returnRequest.verifyApproveCancel();
        returnRequest.verifyApproveSave();
    }

    public void verifyRejectButtonFunctionality() {
        returnRequest.verifyReturnRequestLinkClickable();
        returnRequest.verifyRejectCancel();
        returnRequest.verifyRejectSave();
    }

    public void verifyColumnInReplaceRequest() {
        replaceRequest.verifyReplaceRequestLinkClickable();
        replaceRequest.verifyTheColumnsInReplaceRequest();
    }

    public void verifySortingOfReplaceRequest() {
        replaceRequest.ascendingAndDescendingOfUserName();
        replaceRequest.ascendingAndDescendingOfProductName();
    }

    public void verifyPaginationOfReplaceRequest() {
        replaceRequest.paginationOnReplaceRequestTab();
    }

    public void verifyApproveRejectCancelFunctionality() {
        replaceRequest.verifyReplaceRequestLinkClickable();
        replaceRequest.approveRejectCancelFunctionalityApproved();
        replaceRequest.approveRejectCancelFunctionalityRejected();
    }

    public void verifyApproveRejectSaveFunctionality() {
        replaceRequest.approveRejectSaveFunctionalityApproved();
        replaceRequest.approveRejectSaveFunctionalityRejected();

    }

    public void verifyCancelRepairFunctionality() {
        replaceRequest.verifyReplaceRequestLinkClickable();
        replaceRequest.verifyCancelRepairFunction();
    }

    public void verifySaveRepairFunctionality() {
        replaceRequest.verifySaveRepairFunction();
    }
}
