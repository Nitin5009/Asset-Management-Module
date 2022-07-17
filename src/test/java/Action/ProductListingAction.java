package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.AttachmentsPage;
import pageobjects.DeployProductPage;
import pageobjects.ProductListingPage;

public class ProductListingAction {
    WebDriver driver;
    ProductListingPage productListingPage;

    public ProductListingAction(WebDriver driver) {
        this.driver = driver;
        productListingPage = new ProductListingPage(driver);
    }

    public void verifyProductListHeader() {
        productListingPage.verifyHeaders();
    }

    public void disabledCheckBoxOfProduct() {
        productListingPage.createNewProduct();
        productListingPage.navigateToProductAssignmentPage();
        productListingPage.clickAddProductToAssign();
        productListingPage.selectUser();
        productListingPage.selectAssignProductLocation();
        productListingPage.selectAssignProductType();
        productListingPage.selectAssignProductName();
        productListingPage.selectAssignProduct();
        productListingPage.searchAssignProduct();
        productListingPage.clickTermsAndCondition();
        productListingPage.addAssignProductToList();
        productListingPage.saveAssignProduct();
        productListingPage.navigateToManageProductPage();
        productListingPage.disabledCheckBox();
    }

    public void enabledCheckBoxOfProduct() {
        productListingPage.navigateToProductAssignmentPage();
        productListingPage.selectCheckBox();
        productListingPage.clickReturnProduct();
        productListingPage.saveReturnProduct();
        productListingPage.handleProductReturnSuccess();
        productListingPage.navigateToManageProductPage();
        productListingPage.enabledCheckBox();
    }

    public void enabledDeleteButton() {
        productListingPage.clickCheckBox();
        productListingPage.verifyDeleteButton();
    }

    public void uniqueNameGreenStrip() {
        productListingPage.verifyGreenStripe();
    }

    public void verifyPaginationOnProductListPage() {
        productListingPage.paginationOnProductList();
    }

    public void bulkDeleteFunction() {
        productListingPage.bulkDeleteFunctionality();
    }

    public void verifyEditPage() {
        productListingPage.navigateToEditPage();
        productListingPage.verifyProductEditPage();
    }

    public void verifyProductImage() {
        productListingPage.verifyImgNotAvail();
    }

    public void productListSorting() {
        productListingPage.navigateToManageProductPage();
        productListingPage.productTypeNameAscending();
        productListingPage.productTypeNameDescending();
        productListingPage.productNameAscending();
        productListingPage.productNameDescending();
        productListingPage.productStatusAscending();
        productListingPage.productStatusDescending();
    }

    public void searchFunction() {
        productListingPage.selectValueFromLocationDropDown();
        productListingPage.clickSearchButton();
        productListingPage.verifySearchedLocation();
        productListingPage.enterProductNameToSearch();
        productListingPage.clickSearchButton();
        productListingPage.verifySearchedProductName();
        productListingPage.clickResetButton();
        productListingPage.verifyProductNameField();
        productListingPage.selectStatusToSearch();
        productListingPage.clickSearchButton();
        productListingPage.verifySearchedStatus();
    }

    public void expandAllFunction() {
        productListingPage.clickExpandAllButton();
        productListingPage.verifyLocationFilter();
        productListingPage.verifyProductNameFilter();
        productListingPage.verifyStatusFilter();
    }

    public void verifySubStatusPopup() {
        productListingPage.navigateToSubStatusPopup();
        productListingPage.verifyFieldsInSubStatusPopup();
        productListingPage.closeSubStatusPopup();
    }

    public void verifyProductHistoryPage() {
        productListingPage.navigateToProductPageHistory();
        productListingPage.verifyProductAssignmentPage();
    }

    public void verifyAttachmentPage() {
        AttachmentsPage attachment = new AttachmentsPage(driver);
        DeployProductPage deployProduct = new DeployProductPage(driver);
        productListingPage.createNewProduct();
        productListingPage.navigateToAttachmentPage();
        attachment.enterAttachmentName();
        attachment.selectTermsAndConditionsYes();
        attachment.uploadAttachment();
        attachment.verifySaveButtonClickable();
        deployProduct.handleSuccessPopup();
        productListingPage.navigateToViewAttachedFilePage();
        productListingPage.verifyAttachmentPopup();
    }

    public void verifyDepreciationListingScreen() {
        productListingPage.navigateToDepreciationPage();
        productListingPage.verifyDepreciationPage();
        productListingPage.verifyBreadCrumbInDepreciation();
    }

    public void depreciationSearchFunction() {
        productListingPage.navigateToDepreciationPage();
        productListingPage.verifySelectedDate();
        productListingPage.clickSearchButton();
        productListingPage.verifySearchedDepreciationList();
        productListingPage.resetDateSearch();
        productListingPage.verifyClearedDate();
    }

    public void depreciationPagination() {
        productListingPage.navigateToDepreciationPage();
        productListingPage.verifySelectedDate();
        productListingPage.clickSearchButton();
        productListingPage.paginationFunctionality();
    }

    public void verifyImportPageFunctions() {
        productListingPage.navigateToImportProductPage();
        productListingPage.verifyImportPage();
        productListingPage.clickCancelButton();
        productListingPage.verifyProductListingPage();
        productListingPage.navigateToImportProductPage();
        productListingPage.verifyChooseButton();
        productListingPage.downloadAndVerifyImportSample();
    }

    public void importBulkProduct() {
        productListingPage.storeDuplicatePCode();
        productListingPage.navigateToImportProductPage();
        productListingPage.importSampleFile();
        productListingPage.verifyClearButton();
        productListingPage.importSampleFile();
        productListingPage.clickImportButton();
        productListingPage.verifyConfirmationPopup();
        productListingPage.confirmImport();
    }

    public void editFunctionInImportedProduct() {
        productListingPage.selectProductType();
        productListingPage.enterProductName();
        productListingPage.verifyUniqueProductCodeField();
        productListingPage.selectLocation();
        productListingPage.clickSubmitButton();
        productListingPage.verifySuccessMessage();
        productListingPage.handleSuccessPopup();
        productListingPage.verifyUniqueProductCode();
    }

    public void verifyImportCancelFunction() {
        importBulkProduct();
        productListingPage.clickCancelAfterImport();
        productListingPage.verifyProductListingPage();
    }

    public void verifyAddProductScreen() {
        productListingPage.navigateToProductCreatePage();
        productListingPage.verifyAddProductScreen();
        productListingPage.clickUserGuideLink();
        productListingPage.verifyUserGuideScreen();
    }

    public void getBarCodeForProductAssignSearch() {
        productListingPage.navigateToManageProductPage();
        productListingPage.getBarCodeForSearch();
    }

    public void getProductNamedForAssignmentPageValidation() {
        productListingPage.navigateToManageProductPage();
        productListingPage.expandLocationField();
        productListingPage.expandLocationDropdown();
        productListingPage.selectLocationForWidgetSearch("ParentLocation");
        productListingPage.clickSearchButton();
        productListingPage.getProductNamesBasedOnProductType();
    }

}
