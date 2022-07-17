package testcases;

import Action.*;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class SelfAssignmentTest extends WebTestBase {
    @Test(priority = 1)
    public void verifyProductAssignmentPage() {
        test = getTest("TC_Asset Management_Self_Assignment_562_563_564_565_566_567");
        SelfAssignmentAction selfAssignment = new SelfAssignmentAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.navigateToCompanySetupPage();
        companySetupAction.getDepartmentNameList();
        companySetupAction.getAllUserNameList();
        companySetupAction.getUserNameBasedONDepartment();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        productAssignmentAction.verifyCancelButtonFunction();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        selfAssignment.verifyPrePopulatedUserName();
        productAssignmentAction.verifyPredefinedValues();
        productAssignmentAction.expandDepartmentDD();
        productAssignmentAction.verifyDepartmentDropdown();
        productAssignmentAction.verifyPastDateInDateField();
        productAssignmentAction.verifyRemarkField();
        productAssignmentAction.verifyNotificationUserFiled();
        productAssignmentAction.emptyAllTheFields();
        productAssignmentAction.verifyMandatoryErrorMessage();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        productAssignmentAction.verifyMandatoryForProdSearchSection();
        productAssignmentAction.verifyQuantityFieldError();
    }

    @Test(priority = 2)
    public void verifyValidInvalidBarcode() {
        test = getTest("TC_Asset Management_Self_Assignment_568");
        SelfAssignmentAction selfAssignment = new SelfAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        productAssignmentAction.createProduct();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        productAssignmentAction.notSelectUserForAssign();
        productAssignmentAction.verifyBarCodeSearchField();
    }

    @Test(priority = 3)
    public void verifyLocationProductTypeAndName() {
        test = getTest("TC_Asset Management_Self_Assignment_569");
        SelfAssignmentAction selfAssignment = new SelfAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        ProductReportAction productReportAction = new ProductReportAction(driver);
        ProductListingAction productListingAction = new ProductListingAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configCASelfCheckOutYes();
        companySetupAction.navigateToCompanySetupPage();
        companySetupAction.navigateSideBarLocation();
        companySetupAction.getAllLocationList();
        productReportAction.navigateToProductReportPage();
        productReportAction.getNamesBasedOnLocation();
        productListingAction.getProductNamedForAssignmentPageValidation();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        productAssignmentAction.notSelectUserForAssign();
        productAssignmentAction.verifyProductAssignmentLocationField();
        productAssignmentAction.verifyProductTypeBasedOnLocation();
        productAssignmentAction.verifyProductNameBasedOnLocationAndProductType();
        productAssignmentAction.createAndSelfAssignProduct();
        myProductAction.navigateToMyProdPage();
        myProductAction.verifyMyProductPage();
    }

    @Test(priority = 4)
    public void verifyTermsAndConditionField() {
        test = getTest("TC_Asset Management_Self_Assignment_570");
        SelfAssignmentAction selfAssignment = new SelfAssignmentAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        MyProductAction myProductAction = new MyProductAction(driver);
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        ProductAssignmentAction productAssignmentAction = new ProductAssignmentAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configCASelfCheckOutYes();
        addProductTypeAction.navigateToProductType();
        myProductAction.createAndAssignsProductForTAndCForCA();
        addProductTypeAction.navigateToProductType();
        selfAssignment.createTermsAndConditionProduct();
        selfAssignment.navigateToMyProduct();
        selfAssignment.verifySelfAssignmentPage();
        productAssignmentAction.selectValuesForTermsAndCondition();
        selfAssignment.verifyTermsAndCondition();

    }
}
