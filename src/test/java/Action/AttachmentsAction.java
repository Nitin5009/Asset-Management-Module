package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.AttachmentsPage;
import pageobjects.DeployProductPage;

public class AttachmentsAction {

    WebDriver driver;
    AttachmentsPage attachment;
    DeployProductPage deployProduct;
    public AttachmentsAction(WebDriver driver)
    {
        this.driver=driver;
        this.attachment=new AttachmentsPage(driver);
        this.deployProduct=new DeployProductPage(driver);
    }
    public void navigateToAttachmentPage()
    {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        deployProduct.clickManageProduct();
        deployProduct.openProduct();
        attachment.navigateToAttachmentTab();
    }
    public void addRemoveFieldClickable()
    {
        attachment.verifyPlusIconFunctionality();
        attachment.verifyCrossIconFunctionality();
        attachment.verifyAddMoreFileClickable();
        attachment.uploadMoreAttachment();
        attachment.verifyMinusIconFunctionality();
    }
    public void nameCheckBoxUpload()
    {
        attachment.attachmentNameAlphaNumeric();
        attachment.termAnsConditions();
        attachment.verifyChooseFileClickable();
    }
    public void addAttachmentToList()
    {
        attachment.enterAttachmentName();
        attachment.selectTermsAndConditionsNo();
        attachment.uploadAttachment();
        attachment.verifySaveButtonClickable();
        deployProduct.handleSuccessPopup();
    }
    public void verifyAttachmentList()
    {
        deployProduct.openProduct();
        attachment.navigateToAttachmentTab();
        attachment.verifyAttachmentsDetails();
    }
    public void cancelButtonFunctionality()
    {
        attachment.navigateToAttachmentTab();
        attachment.verifyCancelButtonClickable();
        attachment.verifyManageProductPage();
    }
    public void previousButtonFunctionality()
    {
        attachment.enterAttachmentName();
        attachment.selectTermsAndConditionsYes();
        attachment.uploadAttachment();
        attachment.verifyPreviousButtonClickable();
        attachment.verifyPreviousButtonFunctionality();
    }
    public void downloadFunctionality()
    {
        deployProduct.openProduct();
        attachment.navigateToAttachmentTab();
        attachment.verifyDownloadIconClickable();
        attachment.verifyDownloadedFile();
    }
    public void deleteFunctionality()
    {
        attachment.verifyDeleteIconClickable();
        attachment.confirmAttchmentDelete();
        deployProduct.handleSuccessPopup();
        attachment.verifyDeleteFunctionality();
    }
}
