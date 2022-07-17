package testcases;

import Action.CompanySetupAction;
import Action.LoginAction;
import Action.ProductTypeAction;
import Action.ProductWriteOffAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductWriteOffTest extends WebTestBase {

    @Test (priority = 1)
    public void verifyProductWriteOffScreen()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_426_427_428");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        CompanySetupAction companySetup = new CompanySetupAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        companySetup.changeTimeZoneAndShiftOfAdminUser();
        companySetup.changeTimeZoneAndShiftOfSecondUser();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.verifyProductWriteOffPage();
        productWriteOffAction.productWriteOffPageAppearance();
        productWriteOffAction.breadCrumbFunction();
    }
    @Test(priority = 8)
    public void verifyFiltersInProductWriteOffScreen()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_429_430_431_432_433");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.expandCollapseAllFunction();
        productWriteOffAction.searchWithLocation();
        productWriteOffAction.searchWithProductName();
        productWriteOffAction.searchWithProductCode();
        productWriteOffAction.searchAndResetFunctionality();
    }
    @Test(priority = 9)
    public void verifyProductWriteOffPaginationFunction()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_434");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.paginationFunction();
    }
    @Test(priority = 2)
    public void verifyBarCodeFieldWriteOffScreen()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_435_436");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productWriteOffAction.createProductTypeAndProduct();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.navigateToCreateWriteOffPage();
        productWriteOffAction.barCodeFieldBehaviour();
        productWriteOffAction.barCodeSearchFunction();
    }
    @Test(priority = 3)
    public void verifyMandatoryFieldValidation()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_438_443_448");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.navigateToCreateWriteOffPage();
        productWriteOffAction.mandatoryFieldValidation();
    }
    @Test(priority = 4)
    public  void verifyCreateWriteOffSearchFilter()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_439_440_441_442_445_446_447");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productWriteOffAction.createProductTypeAndProduct();
        productWriteOffAction.navigateToProductWriteOffPage();
        productWriteOffAction.navigateToCreateWriteOffPage();
        productWriteOffAction.locationFieldBehaviour();
        productWriteOffAction.verifyProductDropdown();
        productWriteOffAction.verifyUniqueNameDropdown();
    }
    @Test (priority = 5)
    public void verifySearchFunction()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_449_450_451_452_453");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productWriteOffAction.createProductTypeAndProduct();
        productWriteOffAction.searchProductForWriteOff();
        productWriteOffAction.verifyProductSearchFunctionWithData();
        productWriteOffAction.verifyAutoSearchFields();
        productWriteOffAction.verifySelectAllCheckBoxFunction();
    }
    @Test (priority = 6)
    public void verifyWriteOffValueAndCancelFunction()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_454_455_456_457_462");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productWriteOffAction.createProductTypeAndProduct();
        productWriteOffAction.searchProductForWriteOff();
        productWriteOffAction.addToListValidation();
        productWriteOffAction.writeOffFieldBehaviour();
        productWriteOffAction.cancelButtonFunction();
    }
    @Test(priority = 7)
    public void verifyProductWriteOffDeleteAndSaveFunction()
    {
        test = getTest("TC_Asset Management_ProductWriteOff_458_459_460_461");
        ProductWriteOffAction productWriteOffAction = new ProductWriteOffAction(driver);
        ProductTypeAction productTypeAction = new ProductTypeAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productWriteOffAction.createProductTypeAndProduct();
        productWriteOffAction.searchProductForWriteOff();
        productWriteOffAction.deleteFunction();
        productWriteOffAction.saveFunction();
    }
}
