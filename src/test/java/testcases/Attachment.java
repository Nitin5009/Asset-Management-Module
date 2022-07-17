package testcases;

import Action.AttachmentsAction;
import Action.LoginAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class Attachment extends WebTestBase {

    @Test(priority = 1)
    public void verifyElementClickableOne()
    {
        AttachmentsAction attachmentsAction=new AttachmentsAction(driver);
        test = getTest("TC_Asset Management_Attachment_138_139_140");
        new LoginAction(driver).logoutLogin();
        attachmentsAction.navigateToAttachmentPage();
        attachmentsAction.addRemoveFieldClickable();
    }
    @Test(priority = 2)
    public void verifyElementClickableTwo()
    {
        AttachmentsAction attachmentsAction=new AttachmentsAction(driver);
        test = getTest("TC_Asset Management_Attachment_134_135_136_137");
        new LoginAction(driver).logoutLogin();
        attachmentsAction.navigateToAttachmentPage();
        attachmentsAction.nameCheckBoxUpload();
    }
   @Test(priority = 3)
    public void verifyAttachmentListDetails()
    {
        AttachmentsAction attachmentsAction=new AttachmentsAction(driver);
        test = getTest("TC_Asset Management_Attachment_141_144");
        new LoginAction(driver).logoutLogin();
        attachmentsAction.navigateToAttachmentPage();
        attachmentsAction.addAttachmentToList();
        attachmentsAction.verifyAttachmentList();
    }
     @Test(priority = 4)
    public void verifyDownloadAndDeleteFunctions()
    {
        AttachmentsAction attachmentsAction=new AttachmentsAction(driver);
        test = getTest("TC_Asset Management_Attachment_145_146");
        new LoginAction(driver).logoutLogin();
        attachmentsAction.navigateToAttachmentPage();
        attachmentsAction.addAttachmentToList();
        attachmentsAction.downloadFunctionality();
        attachmentsAction.deleteFunctionality();
    }
    @Test (priority = 5)
    public void verifyPreviousAndCancel()
    {
        AttachmentsAction attachmentsAction = new AttachmentsAction(driver);
        test = getTest("TC_Asset Management_Attachment_142_143");
        new LoginAction(driver).logoutLogin();
        attachmentsAction.navigateToAttachmentPage();
        attachmentsAction.previousButtonFunctionality();
        attachmentsAction.cancelButtonFunctionality();
    }
}
