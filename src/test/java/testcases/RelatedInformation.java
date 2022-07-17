package testcases;

import Action.LoginAction;
import Action.RelatedInformationAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class RelatedInformation extends WebTestBase {
    @Test
    public void checkRelatedPageDetail(){
        test = getTest("TC_Asset Management_RelatedInformation_115");
        new LoginAction(driver).logoutLogin();
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.checkRelatedPageDetail();
    }

    @Test
    public void validationOnSearchField(){
        test = getTest("TC_Asset Management_RelatedInformation_116_117_118_119");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.searchProduct();
        relatedInfo.verifyResetTable();

    }

   @Test()
    public void ValidationOfBreadCrumbs(){
        test = getTest("TC_Asset Management_RelatedInformation_120");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.checkBreadCrumb();


    }
    @Test()
    public void barCodePrintValidation(){
        test = getTest("TC_Asset Management_RelatedInformation_121");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.barCodePrint();
        relatedInfo.verifyBarCodePrintPopupPage();

    }
    @Test
    public void downloadBulkBarCodeAndVerifyDownloadedFile(){
        test = getTest("TC_Asset Management_RelatedInformation_122");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.verifyDownloadBulkBarCode();

    }
  @Test
    public void checkAndEditProductUniqueNameOrCode(){
        test = getTest("TC_Asset Management_RelatedInformation_123");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.clickProductUniqueNameOrCode();
        relatedInfo.editUniqueProductDetails();

    }
    @Test
    public void downloadBarImageAndVerifyDownloadedFile(){
        test = getTest("TC_Asset Management_RelatedInformation_124");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.verifyDownloadedBarImage();


    }
    @Test
    public void verifyCalibrationCommentPopup(){
        test = getTest("TC_Asset Management_RelatedInformation_125_126");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.checkCalibrationCommentPopup();
        relatedInfo.enterTheDetailsInCalibrationPopup();

    }
    @Test
    public void verifyAuditCommentpopup(){
        test = getTest("TC_Asset Management_RelatedInformation_127_128");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.checkAuditCommentPopup();
        relatedInfo.enterTheDetailsInAuditCommentPopup();


    }
    @Test
    public void editAndChangeStatus(){
        test = getTest("TC_Asset Management_RelatedInformation_129_132");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.statusSection();
        relatedInfo.selectStatusDropDown();

    }
    @Test
    public void viewHistoryPageValidations()
    {
        test = getTest("TC_Asset Management_RelatedInformation_130_131");
       RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.viewHistoryPageValidations();

    }
    @Test
    public void verifyPagination(){
        test = getTest("TC_Asset Management_RelatedInformation_133");
        RelatedInformationAction relatedInfo=new RelatedInformationAction(driver);
        new LoginAction(driver).logoutLogin();
        relatedInfo.navigateToRelatedProduct();
        relatedInfo.RelatedInformationTab();
        relatedInfo.paginationFunctionality();
    }

}
