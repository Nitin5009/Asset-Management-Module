package testcases;

import Action.*;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class AddProductTypeTest extends WebTestBase {
    @Test(priority = 1)
    public void verifyAddProductTypeNameAndStatus() {
        test = getTest("TC_Asset Management_AddProductType_517_518_519_520_521_522");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.verifyProductListingPageAndProductTypeField();
        addProductTypeAction.verifyActiveProductTypeStatus();
        addProductTypeAction.verifyInActiveProductTypeStatus();

    }

    @Test(priority = 2)
    public void verifyAddProductTypeCodeImageDescriptionAndContainer() {
        test = getTest("TC_Asset Management_AddProductType_523_524_525_526_527_528_529_530_531_532_533_534");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.verifyProductTypeCode();
        addProductTypeAction.verifyImageAndDescriptionField();
        addProductTypeAction.verifyContainerAndCompartmentField();

    }

    @Test(priority = 3)
    public void verifyProductTypeBasedOnAssetToggle() {
        test = getTest("TC_Asset Management_AddProductType_536_537");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.createProductTypeWithAssetType();
        addProductTypeAction.createProductTypeWithOutAssetType();
    }

    @Test(priority = 4)
    public void verifyToggle() {
        test = getTest("TC_Asset Management_AddProductType_535_538_539_542_545_547_551_553_556_558_559_560_561");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.verifyProductTypeCategory();
        addProductTypeAction.verifyActionBasedProperties();
        addProductTypeAction.verifyDeploymentProperties();
        addProductTypeAction.verifyHardwareIntegrationType();
    }

    @Test(priority = 5)
    public void verifyDisableConsumable() {
        test = getTest("TC_Asset Management_AddProductType_541");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        MyProductAction myProduct = new MyProductAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);
        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.verifyProductCreationAndAssign();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        addProductTypeAction.verifyDisableConsuming();
    }

    @Test(priority = 6)
    public void verifyEmployeeAcceptance() {
        test = getTest("TC_Asset Management_AddProductType_543_544");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        MyProductAction myProduct = new MyProductAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
        addProductTypeAction.navigateToProductType();
        myProduct.createRequestQuantityProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        addProductTypeAction.verifyAcceptButtonEnable();
        login.logoutLogin();
        addProductTypeAction.verifyNotAcceptedProdInTransit();
        companySetupAction.configUserForSelfCheckOutYes();
        addProductTypeAction.navigateToProductType();
        myProduct.createSelfCheckOutProdType();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        addProductTypeAction.verifyAcceptButtonDisable();
        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();

    }


    @Test(priority = 7)
    public void verifyTermsAndConditionDocument() {
        test = getTest("TC_Asset Management_AddProductType_550_552_554_555");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductTypeAction.navigateToProductType();
        addProductTypeAction.verifyProductCreationAndAssign();
        addProductTypeAction.uploadTermAndConditionDocument();
    }

    @Test(priority = 8)
    public void verifySelfCheckout() {
        test = getTest("TC_Asset Management_AddProductType_552");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        CompanySetupAction companySetupAction = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutYes();
        productType.goToTheProductTypePage();
        myProduct.createSelfCheckOutProdType();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifySelfCheckOutFunction();
        login.logoutLogin();
        companySetupAction.configUserForSelfCheckOutNo();
    }

    @Test(priority = 9)
    public void verifyConsumableInsurenceAndTransferProduct() {
        test = getTest("TC_Asset Management_AddProductType_540,546,548,549,557");
        AddProductTypeAction addProductTypeAction = new AddProductTypeAction(driver);
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        addProductTypeAction.createConsumableProduct();

        productType.goToTheProductTypePage();
        addProductTypeAction.createInsurancePolicyProduct();
        companySetup.configUserForSelfTransfer();
        companySetup.configUserForSelfCheckOutYes();
        productType.goToTheProductTypePage();
        myProduct.createTransferableProduct();
        login.logoutLoginSecondUser();
        myProduct.navigateToMyProdPage();
        myProduct.verifyTransferFunction();

    }

    @Test(priority = 10)
    public void verifyTermsAndConditionForAssign() {
        test = getTest("TC_Asset Management_AddProductType_554");
        MyProductAction myProduct = new MyProductAction(driver);
        ProductTypeAction productType = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productType.goToTheProductTypePage();
        myProduct.createAndAssignProductForTAndC();
    }
}

