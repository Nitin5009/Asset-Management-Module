package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.DeployProductPage;
import pageobjects.MyProductPage;
import pageobjects.ProductAssignmentPage;
import pageobjects.ProductContainerPage;

public class MyProductAction {

    MyProductPage myProduct;
    ProductAssignmentPage productAssignment;
    DeployProductPage deployProduct;
    ProductContainerPage productContainer;

    public MyProductAction(WebDriver driver) {
        this.myProduct = new MyProductPage(driver);
        this.productAssignment = new ProductAssignmentPage(driver);
        this.deployProduct = new DeployProductPage(driver);
        this.productContainer = new ProductContainerPage(driver);
    }

    public void navigateToMyProdPage() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        myProduct.openMyProductPage();
    }

    public void createSelfCheckOutProdType() {
        myProduct.createAndAssignProductTypeWithSelfCheckOut();
    }

    public void createTransferableProduct() {
        myProduct.createAndAssignTransferableProduct();
    }

    public void createProdForContainer() {
        myProduct.createAndAssignProdForContainer();
    }

    public void assignContainerProdToUser() {
        myProduct.assignContainerProdToUser();
    }

    public void createAssignEmpAcceptProdType() {
        myProduct.createAndAssignProductForEmpAccept();
    }

    public void createAndAssignProductForReturn() {
        myProduct.createAndAssignProdForReturn();
    }

    public void createProductForBarCodeSearch() {
        myProduct.createProductForBarCodeSearch();
    }

    public void createAndAssignProductForTAndC() {
        myProduct.createAndAssignProdForTermsAndCondition();
    }

    public void createAndAssignsProductForTAndCForCA() {
        myProduct.createAndAssignProdForTermsAndConditionCA();
    }

    public void createConsumableProduct() {
        myProduct.createAssignConsumableProduct();
    }

    public void createRequestQuantityProduct() {
        myProduct.createAssignProductForRequestQuantity();
    }

    public void verifyMyProductPage() {
        myProduct.verifyAssignedProduct();
    }

    public void verifySearchBarInMyProdPage() {
        myProduct.enterAlphaNumericInSearch();
        myProduct.verifyAlphaNumericInSearch();
    }

    public void verifyBarCodeSearchAndReset() {
        myProduct.searchBarCodeValue();
        myProduct.verifySearchedBarCodeValue();
        myProduct.resetBarCodeSearch();
        myProduct.resultOfReset();
    }

    public void verifyProductSorting() {
        myProduct.verifyMainProductAscending();
        myProduct.verifyMainProductDescending();
        myProduct.verifySubProductAscending();
        myProduct.verifySubProductDescending();
    }

    public void verifyQRCodeBarCodeType() {
        myProduct.verifyBarCodeTypes();
    }

    public void verifyRedLegend() {
        myProduct.verifyRedLegend();
    }

    public void acceptEmpAcceptProduct() {
        myProduct.clickAcceptIconOfProduct();
    }

    public void rejectEmpAcceptProduct() {
        myProduct.clickRejectIconOfProduct();
    }

    public void verifyYellowLegend() {
        myProduct.verifyYellowLegend();
    }

    public void verifyBlueLegend() {
        myProduct.verifyBlueLegend();
    }

    public void createContainer() {
        myProduct.getLatestContainerProductType();
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        deployProduct.clickManageProduct();
        productContainer.deployProductForContainer();
        myProduct.getNameOfTheContainedProd();
        productContainer.openProductContainerPage();
        productContainer.clickAddProductContainerButton();
        productContainer.selectLocation();
        productContainer.selectProductType();
        productContainer.selectContainer();
        myProduct.selectProductNameForContainer();
        productContainer.selectCompartmentOne();
        productContainer.saveUpdateContainer();
        productContainer.handleContainerCreateSuccess();
    }

    public void verifyConsumeFunction() {
        myProduct.clickConsumeOption();
        myProduct.verifyConsumePopup();
        myProduct.enterMoreConsumeQuantity();
        myProduct.verifyMoreConsumeQuantityError();
        myProduct.verifyConsumeRemarkAlphaNumeric();
        myProduct.verifyPredefineValInConsumePopup();
        myProduct.cancelPopup();
        myProduct.verifyConsumePopupClosed();
        myProduct.consumeSaveFunction();
    }

    public void verifyReplaceRequestFunction() {
        myProduct.clickReplaceRequest();
        myProduct.verifyReplaceRequestPopup();
        myProduct.verifyPredefinedInReplaceReq();
        myProduct.verifyReplaceReqRemarkAlphaNumeric();
        myProduct.cancelReplaceReq();
        myProduct.verifyReplaceRequestPopupClose();
        myProduct.clickReplaceRequest();
        myProduct.selectReasonForReplace();
        myProduct.enterNoOfProductReplace();
        myProduct.enterRemark();
        myProduct.saveReplaceReq();
        //myProduct.closeReplaceReqSuccess();
    }

    public void verifyCreatedReplaceRequest() {
        myProduct.verifyCreatedRequest();
    }

    public void verifySelfCheckOutFunction() {
        myProduct.verifySelfCheckOutOption();
        myProduct.clickSelfCheckOutOption();
        myProduct.enterMoreProductSelfCheckOut();
        myProduct.verifyMoreSelfCheckOutQty();
        myProduct.verifyPreDefinedValueInSelfCheckOutPage();
        myProduct.verifySelfCheckOutRemarkAlphaNumeric();
        myProduct.cancelPopup();
        myProduct.verifySelfCheckOutPopupClosed();
        myProduct.verifySelfCheckOutFunction();
    }

    public void verifyRequestQuantityFunction() {
        myProduct.clickRequestQuantityOption();
        myProduct.enterMoreRequestQuantity();
        myProduct.savePopup();
        myProduct.verifyMoreRequestQuantityError();
        myProduct.verifyPreDefinedValueInRequestQuantity();
        myProduct.alphaNumericRemarkRequestQuantity();
        myProduct.cancelPopup();
        myProduct.verifyRequestQuantityPopupClosed();
        myProduct.clickRequestQuantityOption();
        myProduct.enterRequestQuantity();
        myProduct.alphaNumericRemarkRequestQuantity();
        myProduct.saveRequestQuantityPopup();
        //myProduct.handleRequestQuantitySuccessMsg();
    }

    public void verifyQuantityRequest() {
        myProduct.verifyCreatedProdReq();
    }

    public void verifyTransferFunction() {
        myProduct.verifyTransferButtonEnable();
        myProduct.clickTransferOption();
        myProduct.saveTransferButton();
        myProduct.verifyMandatoryErrorMessage();
        myProduct.selectTransferLocation();
        myProduct.selectUserForTransfer();
        myProduct.cancelTransferButton();
        myProduct.verifyTransferPopupClosed();
    }

    public void verifyReturnFunction() {
        myProduct.clickReturnOption();
        myProduct.verifyPredefinedInReturnPopup();
        myProduct.verifyAlphaNumericReturnRemark();
        myProduct.cancelReturnPopup();
        myProduct.verifyReturnReqPopupClosed();
        myProduct.clickReturnOption();
        myProduct.verifyAlphaNumericReturnRemark();
        myProduct.selectQuantityForReturn();
        myProduct.selectReasonForReturn();
        myProduct.saveReturnPopup();
        //myProduct.handleReturnReqSuccessMsg();
    }

    public void verifyCreatedReturnRequest() {
        myProduct.verifyCreatedReturnReq();
    }

    public void verifyTermsAndConditionFunction() {
        myProduct.clickTermsAndConditionIcon();
        myProduct.clickCheckBoxOfTC();
        myProduct.verifySaveButtonEnable();
        myProduct.verifyTermsAndCondDownload();
        myProduct.saveTermsAndCondition();
        myProduct.verifyAcceptedTermsAndCondition();
    }

    public void acceptAndReturnProduct() {
        myProduct.acceptBeforeReturnProduct();
        myProduct.clickReturnOption();
        myProduct.saveReturnPopup();
    }
}
