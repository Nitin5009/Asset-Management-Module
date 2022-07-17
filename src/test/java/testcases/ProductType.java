package testcases;


import Action.LoginAction;
import Action.ProductTypeAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductType extends WebTestBase
{
    @Test(priority = 1)
    public void verifyProductTypePage()
    {
        test = getTest("TC_Asset Management_Product_type_147_148");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyProductTypePage();
    }

    @Test(priority = 3)
    public void verifySearchFunctionOnProductTypeListPage()
    {
        test = getTest("TC_Asset Management_Product_type_149_150_151");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifySearchFunctionOnProductTypeListPage();

    }

    @Test(priority = 4)
    public void changeStatusAndDelete()
    {
        test = getTest("TC_Asset Management_Product_type_152_153");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.changeStatusAndDelete();

    }

    @Test(priority = 5)
    public void createProductWithImage()
    {
        test = getTest("TC_Asset Management_Product_type_154");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.createProductWithImage();

    }

    @Test(priority = 6)
    public void createProductWithoutImage()
    {
        test = getTest("TC_Asset Management_Product_type_155_156_157_158_159_160");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.createProductWithoutImage();

    }

    @Test(priority = 7)
    public void verifyBreadCrumbInImportProductTypeField()
    {
        test = getTest("TC_Asset Management_Product_type_162_163");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyBreadCrumbInImportProductTypeField();

    }

    @Test(priority = 8)
    public void downloadSampleExcelFileAndVerify()
    {
        test = getTest("TC_Asset Management_Product_type_164");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.downloadSampleExcelFileAndVerify();

    }

    @Test(priority = 9)
    public void verifyImportProductTypePageAfterClickingCancel()
    {
        test = getTest("TC_Asset Management_Product_type_165_166");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyImportProductTypeUploadField();

    }

    @Test(priority = 10)
    public void verifyUploadDocumentField()
    {
        test = getTest("TC_Asset Management_Product_type_167_168_169");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyUploadDocumentField();

    }

    @Test(priority = 11)
    public void verifyUploadDocumentDetails()
    {
        test = getTest("TC_Asset Management_Product_type_161");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyUploadDocumentDetails();

    }

    @Test(priority = 12)
    public void verifyUploadedDocumentOnImportProductType()
    {
        test = getTest("TC_Asset Management_Product_type_170_173");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyUploadedDocumentOnImportProductType();

    }

    @Test(priority = 2)
    public void verifyUploadedDocumentOnProductListingPage()
    {
        test = getTest("TC_Asset Management_Product_type__171_172");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyUploadedDocumentOnProductListingPage();
    }

    @Test(priority = 13)
    public void verifyPagination()
    {
        test = getTest("TC_Asset Management_Product_type_174");
        ProductTypeAction productTypeAction=new ProductTypeAction(driver);
        new LoginAction(driver).logoutLogin();
        productTypeAction.goToTheProductTypePage();
        productTypeAction.verifyPagination();

    }
}
