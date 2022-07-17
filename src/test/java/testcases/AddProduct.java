package testcases;

import Action.AddProductAction;
import Action.LoginAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class AddProduct extends WebTestBase
{
    @Test()
    public void addProductUniqueName()
    {
        test = getTest("TC_Asset Management_AddProduct_15_21_27_28");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.addProductUniqueName();
        addProductAction.deployTab();
    }
    @Test()
    public void verifyUniqueName()
    {
        test = getTest("TC_Asset Management_AddProduct_22");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.addProductUniqueName();
        addProductAction.verifyUniqueName();
    }
    @Test()
    public void verifyNotUnique()
    {
        test = getTest("TC_Asset Management_AddProduct_23");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.addProduct();
        addProductAction.verifyNotUniqueName();
    }
    @Test()
    public void changeStatusWhileCreatingProduct()
    {
        test = getTest("TC_Asset Management_AddProduct_1_3");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.changeStatusWhileCreatingProduct();

    }
    @Test()
    public void duplicateProductNameValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_7");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.duplicateNameValidation();
    }
    @Test()
    public  void productNameValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_4_6_8_9");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.validationsOnProductName();
    }
    @Test()
    public void productNameFieldValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_5");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.productNameFieldValidation();
    }
    @Test()
    public void barCode()
    {
        test = getTest("TC_Asset Management_AddProduct_19_20");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.dropDownValidation();
    }
    @Test()
    public void productStatusFieldValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_2");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.productStatusValidation();
    }
    @Test()
    public void duplicateCodeValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_12_13");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.duplicateProductCodeValidation();
    }
    @Test()
    public void productCodeValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_11_14");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.validationOnProductCode();
    }

    @Test()
    public void documentValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_29_30");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.documentValidation();
    }
   @Test()
    public void descriptionFieldErrorMessageValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_17");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.errorMsgValidationOnDescription();
    }
    @Test()
    public void descriptionFieldValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_16_18");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.descriptionFieldValidation();
    }
    @Test()
    public void errorMsgValidationOnAddProductPage()
    {
        test = getTest("TC_Asset Management_AddProduct_25_26");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.errorMessageValidation();
    }
    @Test()
    public void mandatoryFieldValidation()
    {
        test = getTest("TC_Asset Management_AddProduct_10_24");
        AddProductAction addProductAction=new AddProductAction(driver);
        new LoginAction(driver).logoutLogin();
        addProductAction.gotoManageProductPage();
        addProductAction.addNewButton();
        addProductAction.mandatoryFieldValidation();
    }
}
