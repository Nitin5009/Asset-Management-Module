package testcases;

import Action.CompanySetupAction;
import Action.DeployProductAction;
import Action.LoginAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class DeployProduct extends WebTestBase {

    @Test (priority = 1)
    public void verifyElementsPresence()
    {
        DeployProductAction deployProduct= new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_31_32_33_34");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.verifyDeployListing();
        deployProduct.verifySearchandAddElementPresence();
        deployProduct.verifyPreviousandNextElementPresence();
    }
    @Test(priority = 4)
    public void verifySearchFieldFunctionality()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_35_36_37");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.searchingFunctionality();
        deployProduct.searchFieldClearFunctionality();
    }
    @Test(priority = 2)
    public void previousAndNextButtonVerification()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_39_40_43");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.breadCrumbValidation();
        deployProduct.nextButtonFunctionality();
        deployProduct.previousButtonFunctionality();
    }
    @Test(priority = 3)
    public void verifyDeployedProduct()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_38_41");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.addDeployPageVerification();
        deployProduct.verifyAddedDeployProduct();
    }
    @Test(priority = 5)
    public void productEditPageValidation()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_44_53");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.editPageVerification();
    }

    @Test(priority = 6)
    public void mandatoryFieldValidation()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_45_111");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.errorMessageValidation();
    }
    @Test(priority = 7)
    public void verifyLocationDropdown()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_46_47_48_50_52_20");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.locationDropdownVerification();
        deployProduct.locationDropdownSearchFuctionality();
        deployProduct.selectAndClearLocation();
    }
    @Test(priority = 8)
    public void verifyLocationDropdownValues()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        CompanySetupAction companySetup=new CompanySetupAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_49_51");
        new LoginAction(driver).logoutLogin();
        companySetup.navigateToCompanySetupPage();
        companySetup.navigateSideBarLocation();
        companySetup.createChildLocation();
        companySetup.getAllLocationList();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyLocationDropdownValues();
    }
    @Test(priority = 9)
    public void verifyQuantityFieldFunctionality()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_54_55_56_57");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyQuantityField();
    }
    @Test(priority = 10)
    public  void verifyModelAndVendorManufacturerFields()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_58_59_60_61");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyModelandVendorNameField();
        deployProduct.verifyManufacturerNameField();
    }
    @Test(priority = 11)
    public void verifyProductCostField()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        CompanySetupAction companySetup=new CompanySetupAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_62_63_64_65_66_67_68");
        new LoginAction(driver).logoutLogin();
        companySetup.navigateToCompanySetupPage();
        companySetup.changeProductCostToggle(true);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyProductCostFieldBehaviour("enable");
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyProductCostField();
        companySetup.navigateToCompanySetupPage();
        companySetup.changeProductCostToggle(false);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyProductCostFieldBehaviour("disable");
    }

   @Test(priority = 12)
    public void verifyReferencePurchaseOrder()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_69_70_71");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyReferenceField();
    }
    @Test(priority = 13)
    public void verifyProductOrderDateField()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_72_73_74_78");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyDateField();
        deployProduct.verifyProductDateAsCurrentDate();
        deployProduct.verifyProductDateAsOldDate();
        deployProduct.verifyProductDateFutureDate();
    }
    @Test(priority = 14)
    public void verifyInvoiceNumberField()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_75_76_77");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInvoiceNumberFieldFunctionality();
    }
    @Test(priority = 15)
    public void verifyInvoiceDateField()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_79_80");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInvoiceDateAsCurrentDate();
        deployProduct.verifyInvoiceDateAsOldDate();
        deployProduct.verifyInvoiceDateFutureDate();
    }
    @Test(priority = 16)
    public void verifyInsuranceFields()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        CompanySetupAction companySetup=new CompanySetupAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_81_82_83_84_85_86");
        new LoginAction(driver).logoutLogin();
        companySetup.navigateToCompanySetupPage();
        companySetup.changeInsuranceRefNumToggle(false);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsurenceRefNumFieldBehaviour("disable");
        deployProduct.verifyInsurerNameFieldBehaviour("disable");
        companySetup.navigateToCompanySetupPage();
        companySetup.changeInsuranceRefNumToggle(true);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsurenceRefNumFieldBehaviour("enable");
        deployProduct.verifyInsurerNameFieldBehaviour("enable");
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsuranceNumandInsurerNameField();
    }
    @Test(priority = 17)
    public void verifyInsuranceDateField() {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_87_88_89_90_91");
        new LoginAction(driver).logoutLogin();
        companySetup.navigateToCompanySetupPage();
        companySetup.changeInsuranceRefNumToggle(false);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsurenceDateFieldBehaviour("disable");
        companySetup.navigateToCompanySetupPage();
        companySetup.changeInsuranceRefNumToggle(true);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsurenceDateFieldBehaviour("enable");
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyInsuranceValidDateFunctionality();
    }
    @Test(priority = 18)
    public void verifyWarrantyDateField()
    {
        DeployProductAction deployProduct=new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_92_93_94");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyCurrentWarrantyDate();
        deployProduct.verifyPastWarrantyDate();
        deployProduct.verifyFutureWarrantyDate();
    }
    @Test(priority = 19)
    public void verifyInsuranceDateFieldBehaviour() {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_95_96_97_98_99");
        new LoginAction(driver).logoutLogin();
        companySetup.navigateToCompanySetupPage();
        companySetup.changeDepreciationToggle(false);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyDepreciationRuleFieldBehaviour("disable");
        companySetup.navigateToCompanySetupPage();
        companySetup.changeDepreciationToggle(true);
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyDepreciationRuleFieldBehaviour("enable");
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyDepreciationDropdown();
    }
    @Test(priority = 20)
    public void verifyProductLifeField() {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_100_101_102_103_104");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifyProducLifeField();
    }
    @Test(priority = 21)
    public void verifySalvageCostField() {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_105_106_107_108");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.verifySalvageCostField();
    }
    @Test(priority = 22)
    public void verifyAddToListFunctionality()
    {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_109_110");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.createProductToDeploy();
        deployProduct.addToListFunctionality();
    }
    @Test(priority = 23)
    public void popupButtonsFunctionality()
    {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_112_113_114");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.navigateToCreateProductPage();
        deployProduct.closeButtonFunctionality();
        deployProduct.navigateToCreateProductPage();
        deployProduct.createProductToDeploy();
        deployProduct.cancelButtonFunctionality();
        deployProduct.navigateToCreateProductPage();
        deployProduct.createProductToDeploy();
        deployProduct.saveButtonFunctionality();
    }
    @Test(priority = 24)
    public void verifyPaginationFunctionality() {
        DeployProductAction deployProduct = new DeployProductAction(driver);
        test = getTest("TC_Asset Management_DeployProduct_42");
        new LoginAction(driver).logoutLogin();
        deployProduct.navigateToDeployProductPage();
        deployProduct.verifyPagination();
    }
}
