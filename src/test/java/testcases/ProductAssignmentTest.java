package testcases;

import Action.*;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductAssignmentTest extends WebTestBase {


    @Test
    public void verifyTableHeaderAndSortingFunction() {
        test = getTest("Asset Management_Product Assignment_571");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.verifyAssignmentTableHeaders();
        productAssignmentAction.verifyYellowStripOnOveDueProduct();
        productAssignmentAction.productNameHeaderSorting();
        productAssignmentAction.productCodeHeaderSorting();
        productAssignmentAction.locationNameHeaderSorting();
        productAssignmentAction.issuedToHeaderSorting();
        productAssignmentAction.issuedByHeaderSorting();
        productAssignmentAction.assignedFromHeaderSorting();
        productAssignmentAction.assignedTillHeaderSorting();
        productAssignmentAction.returnDateHeaderSorting();
    }

    @Test
    public void verifySearchFunctionality() {
        test = getTest("Asset Management_Product Assignment_572_573_574_575_577");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.searchAndVerifyIssuedTo();
        productAssignmentAction.searchAndVerifyIssuedBy();
        productAssignmentAction.searchAndVerifyProductName();
        productAssignmentAction.searchAndVerifyShowBy();
        productAssignmentAction.verifyMultiSelectSearchAndClearFunctionality();
    }

    @Test
    public void verifyExpandCollapseToolTipPaginationBreadCrumbFunction() {
        test = getTest("Asset Management_Product Assignment_578_579_580");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.verifyExpandAndCollapseFunction();
        productAssignmentAction.verifyToolTipInTheList();
        productAssignmentAction.verifyPaginationFunction();
        productAssignmentAction.verifyBreadCrumbFunction();
    }

    @Test
    public void verifyShareIconFunctionality() {
        test = getTest("Asset Management_Product Assignment_584_585");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.getCompanyAdminLocation();
        companySetupAction.setShiftForProjectManager();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToSharePopup();
        productAssignmentAction.verifySharePopupMandatory();
        productAssignmentAction.verifyLocationField();
        productAssignmentAction.verifySameUserAdding();
        productAssignmentAction.verifyClosedSharingPopup();
        productAssignmentAction.navigateToSharePopup();
        productAssignmentAction.addUserWithEmpId();
        productAssignmentAction.verifySharedProduct();
    }

    @Test
    public void verifyReturnFunctionality() {
        test = getTest("Asset Management_Product Assignment_586_587");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToReturnPopup();
        productAssignmentAction.verifyReturnPopupCancelFunction();
        productAssignmentAction.verifyUniqueNameProductDetailInReturnPopup();
        productAssignmentAction.verifyCommentField();
        productAssignmentAction.saveAndVerifyReturnProduct();
        productAssignmentAction.createAndAssignNonUniqueProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToReturnPopup();
        productAssignmentAction.verifyQuantityMandatoryErrorMessage();
        productAssignmentAction.verifyAlphaNumericInAllFields();
        productAssignmentAction.saveAndVerifyReturnProduct();
    }

    @Test
    public void verifyRequestForReturnFunctionality() {
        test = getTest("Asset Management_Product Assignment_588");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToRequestForReturnPopup();
        productAssignmentAction.verifyQuantityAndPastDateField();
        productAssignmentAction.verifyClosedRequestForReturnPopup();
        productAssignmentAction.createAndAssignNonUniqueProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToRequestForReturnPopup();
        productAssignmentAction.verifyMandatoryErrorForRequestForReturn();
        productAssignmentAction.verifyCommentInRequestForReturnPopup();
        productAssignmentAction.saveRequestForReturnPopup();
        login.logoutLoginSecondUser();
        productAssignmentAction.verifyRaisedRequestForReturn();
    }

    @Test
    public void verifyCreateAssignmentFieldWiseVerification() {
        test = getTest("Asset Management_Product Assignment_589_590_591");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.navigateToCompanySetupPage();
        companySetupAction.getDepartmentNameList();
        companySetupAction.getAllUserNameList();
        companySetupAction.getUserNameBasedONDepartment();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.verifyPredefinedValues();
        productAssignmentAction.verifyUserNameField();
        productAssignmentAction.verifyDepartmentDropdown();
        productAssignmentAction.verifyPastDateInDateField();
        productAssignmentAction.verifyRemarkField();
        productAssignmentAction.verifyNotificationUserFiled();
    }


    @Test
    public void verifyCreateAssignmentButtonPageFunctionality() {
        test = getTest("Asset Management_Product Assignment_592");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.verifyCancelButtonFunction();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.emptyAllTheFields();
        productAssignmentAction.verifyMandatoryErrorMessage();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.selectNormalUserForAssign();
        productAssignmentAction.verifyMandatoryForProdSearchSection();
        productAssignmentAction.verifyQuantityFieldError();
    }


    @Test
    public void verifyBarCodeInProductAssignment() {
        test = getTest("Asset Management_Product Assignment_593");
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.createProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.selectNormalUserForAssign();
        productAssignmentAction.verifyBarCodeSearchField();
    }

    @Test
    public void verifyProductToAssignFields() {
        test = getTest("Asset Management_Product Assignment_594");
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        ProductReportAction productReportAction = new ProductReportAction(driver);
        ProductListingAction productListingAction = new ProductListingAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        companySetupAction.navigateToCompanySetupPage();
        companySetupAction.navigateSideBarLocation();
        companySetupAction.getAllLocationList();
        productReportAction.navigateToProductReportPage();
        productReportAction.getNamesBasedOnLocation();
        productListingAction.getProductNamedForAssignmentPageValidation();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.navigateToProductAssignPopup();
        productAssignmentAction.selectNormalUserForAssign();
        productAssignmentAction.verifyProductAssignmentLocationField();
        productAssignmentAction.verifyProductTypeBasedOnLocation();
        productAssignmentAction.verifyProductNameBasedOnLocationAndProductType();
        productAssignmentAction.createAndAssignProduct();
        login.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        myProductAction.verifyMyProductPage();

    }

    @Test
    public void verifyCommentOptionUnderMoreIcon() {
        test = getTest("Asset Management_Product Assignment_595");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.verifyDisabledCommentButton();
        productAssignmentAction.navigateToReturnPopup();
        productAssignmentAction.enterCommentInReturnPopup();
        productAssignmentAction.saveAndVerifyReturnProduct();
        productAssignmentAction.verifyEnteredComment();
    }

    @Test
    public void verifyNotReturnedOptionUnderMoreIcon() {
        test = getTest("Asset Management_Product Assignment_596");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.SaveAndVerifyNotReturned();
    }

    @Test
    public void verifyRequestAlreadySentOptionUnderMoreIcon() {
        test = getTest("Asset Management_Product Assignment_597");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.createAndAssignProduct();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.saveAndVerifyRequestForReturn();
    }

    @Test
    public void verifyApproveRejectOptionUnderMoreIcon() {
        test = getTest("Asset Management_Product Assignment_598");
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.setShiftForNormalUser();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.createAndAssignProduct();
        login.logoutLoginSecondUser();
        myProductAction.navigateToMyProdPage();
        myProductAction.acceptAndReturnProduct();
        login.logoutLogin();
        productAssignmentAction.navigateToProductAssignmentPage();
        productAssignmentAction.approveAndVerifyProduct();
    }
}
