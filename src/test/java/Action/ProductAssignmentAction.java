package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.DeployProductPage;
import pageobjects.MyProductPage;
import pageobjects.ProductAssignmentPage;

import static utils.StaticData.*;

public class ProductAssignmentAction {

    ProductAssignmentPage productAssignmentPage;
    DeployProductPage deployProductPage;
    ProductTypeAction productTypeAction;
    MyProductPage myProductPage;
    ProductReportAction productReportAction;

    public ProductAssignmentAction(WebDriver driver) {
        this.productAssignmentPage = new ProductAssignmentPage(driver);
        this.deployProductPage = new DeployProductPage(driver);
        this.productTypeAction = new ProductTypeAction(driver);
        this.myProductPage = new MyProductPage(driver);
        this.productReportAction = new ProductReportAction(driver);
    }

    public void navigateToProductAssignmentPage() {
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        productAssignmentPage.navigateToProductAssignmentPage();
    }

    public void raiseRequestForReturn() {
        productAssignmentPage.clickCheckBoxOfProduct();
        productAssignmentPage.clickRequestForReturnOption();
        productAssignmentPage.enterRequestForReturnComment();
        productAssignmentPage.saveRequestForReturn();
        productAssignmentPage.handleRequestSentSuccess();
    }

    public void productNameHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Product Name", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Product Name", "Descending");
    }

    public void productCodeHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Product Code", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Product Code", "Descending");
    }

    public void locationNameHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Location Name", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Location Name", "Descending");
    }

    public void issuedToHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Issued To", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Issued To", "Descending");
    }

    public void issuedByHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Issued By", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Issued By", "Descending");
    }

    public void assignedFromHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Assigned From", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Assigned From", "Descending");
    }

    public void assignedTillHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Assigned Till", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Assigned Till", "Descending");
    }

    public void returnDateHeaderSorting() {
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Return Date", "Ascending");
        productAssignmentPage.verifyProductAssignmentHeaderSorting("Return Date", "Descending");
    }

    public void verifyAssignmentTableHeaders() {
        productAssignmentPage.verifyTableHeaders();
    }

    public void searchAndVerifyIssuedTo() {
        productAssignmentPage.expandIssuedToField();
        productAssignmentPage.enterIssuedToUserName();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifySearchedResult("Issued To");
        productAssignmentPage.clickClearSearchButton();
    }

    public void searchAndVerifyIssuedBy() {
        productAssignmentPage.expandIssuedByField();
        productAssignmentPage.enterIssuedByUserName();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifySearchedResult("Issued By");
        productAssignmentPage.clickClearSearchButton();
    }

    public void searchAndVerifyProductName() {
        productAssignmentPage.expandProductNameField();
        productAssignmentPage.enterProductName();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifySearchedResult("Product Name");
        productAssignmentPage.clickClearSearchButton();
    }

    public void searchAndVerifyShowBy() {
        productAssignmentPage.expandShowByField();
        productAssignmentPage.selectReturnOverDueOption();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifyShowBySearchResult();
        productAssignmentPage.clickClearSearchButton();
    }

    public void verifyMultiSelectSearchAndClearFunctionality() {
        productAssignmentPage.expandIssuedToField();
        productAssignmentPage.enterIssuedToUserName();
        productAssignmentPage.expandProductNameField();
        productAssignmentPage.enterProductName();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifyComboSearchResult();
        productAssignmentPage.clickClearSearchButton();
        productAssignmentPage.verifyClearedSearch();
    }

    public void verifyExpandAndCollapseFunction() {
        productAssignmentPage.upperSideExpandCollapseFilter();
        productAssignmentPage.bottomSideExpandCollapseFilter();
    }

    public void verifyToolTipInTheList() {
        productAssignmentPage.verifyToolTipInList();
    }

    public void verifyBreadCrumbFunction() {
        productAssignmentPage.verifyBreadCrumbInProductAssignmentPage();
    }

    public void verifyPaginationFunction() {
        productAssignmentPage.selectRecordPagination();
        productAssignmentPage.verifyPaginationFunctionality();
    }

    public void createProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", "");
    }

    public void createAndAssignProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", productManagerLocation);
        myProductPage.assignProductToUser(1, false, "", "normalUser");
    }

    public void createAndSelfAssignProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("empAccept");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", productManagerLocation);
        myProductPage.assignProductToUser(1, false, "", "companyAdmin");
    }

    public void createAndAssignNonUniqueProduct() {
        productNamesToAssign.clear();
        productsToAssign.clear();
        barCodeValues.clear();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(false, "barcode", productTypeName, "10", productManagerLocation);
        myProductPage.assignProductToUser(1, false, "", "normalUser");
    }

    public void navigateToSharePopup() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.verifyMoreIconBehaviorForUniqueProd();
        productAssignmentPage.verifySharingPopup();
    }

    public void verifySharePopupMandatory() {
        productAssignmentPage.clickSaveButton();
        productAssignmentPage.verifyMandatoryErrorPopup();
        productAssignmentPage.verifyAsteriskAndMessage();
    }

    public void verifyLocationField() {
        productAssignmentPage.clickLocationField();
        productAssignmentPage.verifyLocationDropdownList();
        productAssignmentPage.clickAndVerifyLocationValue();
        productAssignmentPage.verifyLocationFieldClearFunction();
    }

    public void verifySameUserAdding() {
        productAssignmentPage.clickLocationField();
        productAssignmentPage.selectCompanyAdminLocation();
        productAssignmentPage.enterCompanyAdminUserName();
        productAssignmentPage.clickAddButton();
        productAssignmentPage.verifySelectedNameIsDisplayed();
        productAssignmentPage.enterCompanyAdminUserName();
        productAssignmentPage.clickAddButton();
        productAssignmentPage.verifySameUserIssue();
    }

    public void addUserWithEmpId() {
        productAssignmentPage.clickLocationField();
        productAssignmentPage.selectProductManagerLocation();
        productAssignmentPage.enterProjectManagerID();
        productAssignmentPage.clickAddButton();
        productAssignmentPage.verifySelectedNameIsDisplayed();
    }

    public void verifyClosedSharingPopup() {
        productAssignmentPage.clickCancelButton();
        productAssignmentPage.verifySharingPopupClosed();
    }

    public void verifySharedProduct() {
        productAssignmentPage.clickSaveButton();
        productAssignmentPage.verifySharedProduct();
    }

    public void verifyYellowStripOnOveDueProduct() {
        productAssignmentPage.expandShowByField();
        productAssignmentPage.selectReturnOverDueOption();
        productAssignmentPage.clickSearchButton();
        productAssignmentPage.verifyYellowStripForOverDueProduct();
        productAssignmentPage.clickClearSearchButton();
    }

    public void navigateToReturnPopup() {
        productAssignmentPage.clickCheckBoxOfProduct();
        productAssignmentPage.clickReturnIconInTableTop();
        productAssignmentPage.verifyReturnPopup();
    }

    public void verifyReturnPopupCancelFunction() {
        productAssignmentPage.cancelReturnPopup();
        productAssignmentPage.verifyClosedReturnedPopup();
    }

    public void verifyUniqueNameProductDetailInReturnPopup() {
        productAssignmentPage.getAndStoreProductDetails("UniqueName");
        productAssignmentPage.clickReturnIconInTableTop();
        productAssignmentPage.verifyTheDetailsInPopup("UniqueName");
    }

    public void verifyCommentField() {
        productAssignmentPage.enterMoreCharInCommentField();
        productAssignmentPage.verifyCommentFieldLength();
    }

    public void saveAndVerifyReturnProduct() {
        productAssignmentPage.clickBulkSaveButton();
        productAssignmentPage.verifyReturnedProduct();
    }

    public void verifyQuantityMandatoryErrorMessage() {
        productAssignmentPage.enterEmptyStringInQuantity();
        productAssignmentPage.clickBulkSaveButton();
        productAssignmentPage.verifyMandatoryErrorMessage();
    }

    public void verifyAlphaNumericInAllFields() {
        productAssignmentPage.enterValidQuantity();
        productAssignmentPage.enterAlphaNumericInTitle();
        productAssignmentPage.verifyAlphaNumericInTitleField();
        productAssignmentPage.enterAlphaNumericInComment();
        productAssignmentPage.verifyAlphaNumericInCommentField();
    }

    public void navigateToRequestForReturnPopup() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.clickRequestForReturnUnderProduct();
        productAssignmentPage.verifyRequestForReturnPopup("Displayed");
    }

    public void verifyClosedRequestForReturnPopup() {
        productAssignmentPage.closeRequestForReturnPopup();
        productAssignmentPage.verifyRequestForReturnPopup("Closed");
    }

    public void verifyMandatoryErrorForRequestForReturn() {
        productAssignmentPage.enterQuantityInRequestForReturn("");
        productAssignmentPage.saveRequestForReturnViaMoreIcon();
        productAssignmentPage.verifyMandatoryErrorRequestForReturn();
    }

    public void verifyQuantityAndPastDateField() {
        productAssignmentPage.verifyQuantityFieldForUniqueNameInRequestForQuantity();
        productAssignmentPage.clickDateField();
        productAssignmentPage.verifyPastDateSelectionInReturnUntilField();
        productAssignmentPage.clickDateField();
    }

    public void verifyCommentInRequestForReturnPopup() {
        productAssignmentPage.enterRequestForReturnCommentViaMoreIcon();
        productAssignmentPage.verifyCommentInRequestForReturnViaMoreIcon();
    }

    public void saveRequestForReturnPopup() {
        productAssignmentPage.enterQuantityInRequestForReturn("Same");
        productAssignmentPage.saveRequestForReturnViaMoreIcon();
    }

    public void verifyRaisedRequestForReturn() {
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        myProductPage.openMyProductPage();
        myProductPage.acceptProductBeforeCheckYellowStrip();
        myProductPage.verifyYellowLegend();
    }

    public void navigateToProductAssignPopup() {
        productAssignmentPage.clickAddProductToAssign();
    }

    public void verifyPredefinedValues() {
        productAssignmentPage.verifyPreDefinedAssignedFromTIllDates();
        productAssignmentPage.verifyPreDefinedStartAndEndTime();
    }

    public void verifyDepartmentDropdown() {
        productAssignmentPage.verifySearchBoxInDepartmentDropdown();
        productAssignmentPage.verifyDepartmentNamesInDD();
        productAssignmentPage.selectMultipleDepartmentFromList();
        productAssignmentPage.verifySelectedDepartmentName();
        productAssignmentPage.clearSelectedDepartment();
        productAssignmentPage.expandDepartmentButton();
    }

    public void verifyUserNameField() {
        productAssignmentPage.expandSelectUserField();
        productAssignmentPage.verifyAllSelectUserDropDownValues();
        productAssignmentPage.expandDepartmentButton();
        productAssignmentPage.selectSingleDepartmentFromList();
        productAssignmentPage.expandSelectUserField();
        productAssignmentPage.verifySelectUserDropDownValues();
        productAssignmentPage.expandDepartmentButton();
        productAssignmentPage.clearSelectedDepartment();
    }

    public void verifyPastDateInDateField() {
        productAssignmentPage.clickDateField("AssignedFrom");
        productAssignmentPage.verifyPastDateSelection("AssignedFrom");
        productAssignmentPage.clickDateField("AssignedTill");
        productAssignmentPage.verifyPastDateSelection("AssignedTill");
    }

    public void verifyRemarkField() {
        productAssignmentPage.enterRemark("");
        productAssignmentPage.verifyProductAssignmentRemark("", "Alpha Numeric");
    }

    public void verifyNotificationUserFiled() {
        productAssignmentPage.expandNotificationUserField();
        productAssignmentPage.verifySearchBoxInNotificationUserDropdown();
        productAssignmentPage.verifyAllUserNamesUnderNotificationUser();
        productAssignmentPage.selectAndVerifyNotificationUserName();
        productAssignmentPage.expandNotificationUserField();
    }

    public void verifyCancelButtonFunction() {
        productAssignmentPage.cancelProductAssignment();
        productAssignmentPage.verifyProductAssignmentListingPage();
    }

    public void emptyAllTheFields() {
        productAssignmentPage.enterEmptyDateField("AssignedFrom");
        productAssignmentPage.enterEmptyDateField("AssignedTill");
        productAssignmentPage.enterEmptyDateField("StartTime");
        productAssignmentPage.enterEmptyDateField("EndTime");
    }

    public void verifyMandatoryErrorMessage() {
        productAssignmentPage.clickAssignSaveButton();
        productAssignmentPage.closeErrorAlertPopup();
        productAssignmentPage.verifySelectUserErrorMessage();
        productAssignmentPage.verifyAssignedFromErrorMessage();
        productAssignmentPage.verifyAssignedTillErrorMessage();
        productAssignmentPage.verifyStartTimeErrorMessage();
        productAssignmentPage.verifyEndTImeErrorMessage();
    }

    public void verifyMandatoryForProdSearchSection() {
        productAssignmentPage.selectLocation("");
        productAssignmentPage.clickAssignSaveButton();
        productAssignmentPage.closePleaseAddProdPopup();
        productAssignmentPage.verifyErrorMessageForProductType();
        productAssignmentPage.verifyErrorMessageForProductName();
        productAssignmentPage.verifyErrorMessageForProducts();
    }

    public void verifyQuantityFieldError() {
        productAssignmentPage.selectProductType("");
        productAssignmentPage.selectProductName("");
        productAssignmentPage.enterProducts();
        productAssignmentPage.clickProductSearchButton();
        productAssignmentPage.enterEmptyToQuantityField();
        productAssignmentPage.clickAssignSaveButton();
        productAssignmentPage.closePleaseAddProdPopup();
        productAssignmentPage.verifyQuantityFieldError();
    }

    public void verifyBarCodeSearchField() {
        productAssignmentPage.clickBarCodeSearchIcon();
        productAssignmentPage.closeErrorPopup();
        productAssignmentPage.verifyBarCodeMandatoryError();
        productAssignmentPage.enterBarCodeInSearchField("123");
        productAssignmentPage.clickBarCodeSearchIcon();
        productAssignmentPage.verifyAndCloseUnAssociatedIssue();
        productAssignmentPage.enterBarCodeInSearchField("");
        productAssignmentPage.clickBarCodeSearchIcon();
        productAssignmentPage.verifyBarCodeSearchResult();
    }

    public void selectNormalUserForAssign() {
        productAssignmentPage.selectUserToAssign("NormalUser");
    }

    public void notSelectUserForAssign() {
        productAssignmentPage.selectUserToAssign("SelfAssignment");
    }

    public void verifyProductAssignmentLocationField() {
        productAssignmentPage.expandLocationField();
        productAssignmentPage.getAndVerifyActualLocationNames();
        productAssignmentPage.verifySearchFieldUnderLocationDropdown();
        productAssignmentPage.enterLocationInSearchField();
        productAssignmentPage.verifySearchedLocation();
        productAssignmentPage.clickClearButtonInLocationSearch();
        productAssignmentPage.verifyClearedSearchInLocation();
    }

    public void verifyProductTypeBasedOnLocation() {
        productAssignmentPage.enterLocationInSearchField();
        productAssignmentPage.selectLocationForProductAssignment("");
        productAssignmentPage.expandProductTypeField();
        productAssignmentPage.verifyProductTypesInTheDropDown("");
    }

    public void verifyProductNameBasedOnLocationAndProductType() {
        productAssignmentPage.expandProductTypeField();
        productAssignmentPage.selectProductType("");
        productAssignmentPage.verifyProductInProductField("");
    }


    public void verifyDisabledCommentButton() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.verifyCommentButtonStatus();
        productAssignmentPage.closeMoreOptionPopup();
    }

    public void enterCommentInReturnPopup() {
        productAssignmentPage.enterCommentToVerifyCommentButton();
    }

    public void verifyEnteredComment() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.verifyEnabledCommentButton();
    }

    public void SaveAndVerifyNotReturned() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.navigateToNotReturnedPopup();
        productAssignmentPage.enterTitleInReturnPopup();
        productAssignmentPage.enterCommentInReturnPopup();
        productAssignmentPage.saveReturnPopup();
        productAssignmentPage.verifyReturnedProduct();
    }

    public void saveAndVerifyRequestForReturn() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.navigateToRequestForReturnPopup();
        productAssignmentPage.saveRequestForReturnViaMoreIcon();
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.verifyRequestAlreadySentButton();
    }

    public void approveAndVerifyProduct() {
        productAssignmentPage.clickMoreIcon();
        productAssignmentPage.verifyApproveRejectOption();
        productAssignmentPage.clickApproveRejectForRetunredProd();
        productAssignmentPage.enableCheckBoxOfReturnedProduct();
        productAssignmentPage.clickSaveInAcceptRejectReturnPopup();
        productAssignmentPage.verifyReturnedProduct();
    }

    public void selectValuesForTermsAndCondition() {
        productAssignmentPage.selectLocation("SelfAssignment");
        productAssignmentPage.selectProductType("SelfAssignment");
        productAssignmentPage.selectProductName("SelfAssignment");
        productAssignmentPage.enterProducts();
        productAssignmentPage.clickProductSearchButton();
    }

    public void expandDepartmentDD() {
        productAssignmentPage.expandDepartmentButton();
    }
}
