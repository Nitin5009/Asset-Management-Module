package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.ProductTypePage;

public class ProductTypeAction {
    WebDriver driver;
    ProductTypePage productTypePage;

    public ProductTypeAction(WebDriver driver) {
        this.driver = driver;
        productTypePage = new ProductTypePage(driver);
    }

    public void goToTheProductTypePage() {
        productTypePage.clickFullMenuDropDown();
        productTypePage.clickAssetManagement();
        productTypePage.clickProductType();
    }

    public void verifyProductTypePage() {
        productTypePage.verifyProductTypeListingPage();
        productTypePage.VerifyBreadCrumbInProductTypePage();
    }

    public void verifySearchFunctionOnProductTypeListPage() {
        productTypePage.clickTheCheckBox();
        productTypePage.verifyDeleteButton();
        productTypePage.enterProductNameInSearchBox();
        productTypePage.clickSearchButton();
        productTypePage.verifySearchedResult();
        productTypePage.verifySearchBoxAfterReset();
    }

    public void changeStatusAndDelete() {
        productTypePage.clickTheCheckBox();
        productTypePage.changeStatus();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.handelSuccessPopup();
        productTypePage.changeStatusForSingleRecord();
        productTypePage.statusChangeConfirmationPopup();
        productTypePage.handelSuccessPopup();
        productTypePage.verifyChangedStatus();
        productTypePage.clickSelectAllCheckBox();
        productTypePage.changeStatusToInActive();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.handelSuccessPopup();
        productTypePage.verifyStatusIsChangedToInActive();
        productTypePage.clickSelectAllCheckBox();
        productTypePage.changeStatusToActive();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.handelSuccessPopup();
        productTypePage.verifyStatusIsChangedToActive();
        productTypePage.clickMultiSelectCheckbox();
        productTypePage.verifyDeleteButton();
        productTypePage.clickBulkDeleteButton();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.handelSuccessPopup();
        productTypePage.verifyAfterDeletingTheAllRecords();
    }

    public void createProductWithImage() {
        productTypePage.createProductTypeWithUploadingImage();
        productTypePage.verifyUploadedImageIsDisplayed();
    }

    public void createProductWithoutImage() {
        productTypePage.createProductTypeWithoutUploadingImage();
        productTypePage.verifyImageNotDisplayed();
    }

    public void verifyBreadCrumbInImportProductTypeField() {
        productTypePage.clickImportProductType();
        productTypePage.clickUserGuide();
        productTypePage.verifyUserGuide();
        productTypePage.verifyBreadCrumbOnImportProductType();
    }

    public void downloadSampleExcelFileAndVerify() {
        productTypePage.clickImportProductType();
        productTypePage.downloadSampleImportExcelFile();
        productTypePage.verifyDownloadedFile();
    }

    public void verifyImportProductTypeUploadField() {
        productTypePage.clickImportProductType();
        productTypePage.verifyChooseFileButton();
        productTypePage.clickCancelButton();
    }

    public void verifyUploadDocumentField() {
        productTypePage.clickImportProductType();
        productTypePage.uploadImportProductTypeFile();
        productTypePage.verifyDocumentIsUploaded();
        productTypePage.clearUploadedFile();
        productTypePage.verifyUploadFiledAfterClearingTheUploadedDoc();
        productTypePage.clickImportFromExcelButton();
        productTypePage.verifyImportFileFieldValidation();
        productTypePage.uploadInvalidFileFormat();
        productTypePage.clickImportFromExcelButton();
        productTypePage.verifyInvalidImportFileValidation();
    }

    public void verifyUploadDocumentDetails() {
        productTypePage.clickImportProductType();
        productTypePage.uploadImportProductTypeFile();
        productTypePage.clickImportFromExcelButton();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.ValidateUploadedExcelFile();
    }

    public void verifyUploadedDocumentOnImportProductType() {
        productTypePage.clickImportProductType();
        productTypePage.uploadImportProductTypeFile();
        productTypePage.clickImportFromExcelButton();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.verifyUploadProductListing();
        productTypePage.clickCancel();
        productTypePage.verifyImportedProductAfterCancel();

    }

    public void verifyUploadedDocumentOnProductListingPage() {
        productTypePage.clickImportProductType();
        productTypePage.uploadProductTypeFile();
        productTypePage.clickImportFromExcelButton();
        productTypePage.clickConfirmInConfirmationPopup();
        productTypePage.changeProductTypeName();
        productTypePage.verifyNameChange();
        productTypePage.changeProductTypeNameTwo();
        productTypePage.verifySecondNameChange();
        productTypePage.changeProductTypeCode();
        productTypePage.verifyCodeChange();
        productTypePage.changeProductTypeCodeTwo();
        productTypePage.verifySecondCodeChange();
        productTypePage.clickSubmit();
        productTypePage.handelSuccessPopup();
        productTypePage.verifyImportedProductOnProductListingPage();

    }

    public void verifyPagination() {
        productTypePage.verifyPaginationFunctionalities();
    }


}
