package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.AddProductPage;
import pageobjects.AddProductTypePage;

public class AddProductTypeAction {
    WebDriver driver;
    AddProductTypePage addProductTypePage;
    AddProductPage addProductPage;
    CompanySetupAction companySetupAction;


    public AddProductTypeAction(WebDriver driver) {
        this.driver = driver;
        addProductTypePage = new AddProductTypePage(driver);
        addProductPage = new AddProductPage(driver);
        companySetupAction = new CompanySetupAction(driver);

    }

    public void navigateToProductType() {
        addProductTypePage.clickFullMenuDropDown();
        addProductTypePage.clickAssetManagement();
        addProductTypePage.clickProductType();
    }

    public void verifyProductListingPageAndProductTypeField() {
        addProductTypePage.productTypeListingPage();
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.verifyTextFieldClickable();
        addProductTypePage.enterMyProductTypeField();
        addProductTypePage.verifyProductTypeNameField("MaximumLength");
        addProductTypePage.enterAlphaNumericProductType();
        addProductTypePage.verifyProductTypeNameField("AlphaNumeric");

    }

    public void verifyActiveProductTypeStatus() {
        addProductTypePage.enterProductTypeName();
        addProductTypePage.verifyStatusDropdown();
        addProductTypePage.selectProductTypeStatus("Active");
        addProductTypePage.selectProductType();
        addProductTypePage.clickSaveButton();
        addProductTypePage.handleSuccessPopup();
        addProductTypePage.verifyProductListingStatus("Active");
    }

    public void verifyInActiveProductTypeStatus() {
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.selectProductTypeStatus("Inactive");
        addProductTypePage.selectProductType();
        addProductTypePage.clickSaveButton();
        addProductTypePage.verifyProductListingStatus("Inactive");
    }

    public void verifyProductTypeCode() {
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.selectProductType();
        addProductTypePage.getProductType();
        addProductTypePage.enterProductTypeCode();
        addProductTypePage.clickSaveButton();
        addProductTypePage.handleSuccessPopup();
        addProductTypePage.verifyProductTypeCode();
        addProductTypePage.getProductTypeCode();
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.enterDuplicateProductTypeCode();
        addProductTypePage.selectProductType();
        addProductTypePage.clickSaveButton();
        addProductTypePage.handleSuccessPopup();
        addProductTypePage.verifyProductTypeIsDiffer();
    }

    public void createProductTypeWithAssetType() {
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.selectProductType();
        addProductTypePage.clickSaveButton();
        addProductTypePage.handleSuccessPopup();
        addProductTypePage.verifyCreatedProductType();
    }

    public void createProductTypeWithOutAssetType() {
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.clickSaveButton();
        addProductTypePage.verifyNotCreatedProductType();
    }

    public void verifyImageAndDescriptionField() {
        navigateToProductType();
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.enterProductTypeName();
        addProductTypePage.selectProductType();
        addProductTypePage.uploadImage();
        addProductTypePage.enterMoreCharProductDescription();
    }

    public void verifyContainerAndCompartmentField() {
        addProductTypePage.verifyContainerYesField();
        addProductTypePage.verifyMinimumOneRowForContainer();
        addProductTypePage.verifyContainerNoField();
        addProductTypePage.switchToYesToggle();
        addProductTypePage.clickSaveButton();
        addProductTypePage.verifyValidationMessage("Compartment name is required");
        addProductTypePage.clickPlusButton();
        addProductTypePage.verifyNewRowAdded();
        addProductTypePage.clickDelete("2");
        addProductTypePage.verifyDeletedRow();
        addProductTypePage.clickDelete("1");
        addProductTypePage.verifyToggleButton();
    }

    public void verifyProductTypeCategory() {
        addProductTypePage.clickProductTypeAddButton();
        addProductTypePage.verifyYesNoToggleAsset();
        addProductTypePage.selectAssetYesToggle();
        addProductTypePage.selectAssetNoToggle();
        addProductTypePage.verifyYesNoToggleLicence();
        addProductTypePage.verifyYesNoToggleConsume();
        addProductTypePage.selectYesToggleConsume();
        addProductTypePage.verifyYesNoToggleSpareTool();
    }

    public void verifyActionBasedProperties() {
        addProductTypePage.selectYesToggleEmployee();
        addProductTypePage.verifyYesNoToggleTransferField();
        addProductTypePage.verifyYesNoToggleSelfCheckOut();
        addProductTypePage.verifyYesNoTermsAndConditions();
        addProductTypePage.selectYesToggleTermsAndCondition();
        addProductTypePage.verifyYesNoGPSEnable();
        addProductTypePage.verifyYesNoFleetManagement();
        addProductTypePage.verifyYesNoPenaltyOnStatus();
        addProductTypePage.verifyYesNoPenaltyOnOverdue();
    }

    public void verifyDeploymentProperties() {
        addProductTypePage.verifyYesNoToggleInsurancePolicy();
        addProductTypePage.verifyYesNoToggleInsurancePolicy();
        addProductTypePage.verifyYesNoProductCost();
        addProductTypePage.verifyYesNoDepreciable();
        addProductTypePage.verifyYesNoCalibration();
        addProductTypePage.verifyYesNoWarranty();
    }

    public void verifyHardwareIntegrationType() {
        addProductTypePage.verifyYesNoTimeAndAttendance();
        addProductTypePage.verifyYesNoCamera();
        addProductTypePage.verifyYesNoRFID();
        addProductTypePage.verifyYesNoGPSTracking();
    }

    public void verifyProductCreateAndAssign() {
        addProductTypePage.createProductType();
    }

    public void verifyProductCreationAndAssign() {
        addProductTypePage.createProductTypes();
    }

    public void verifyDisableConsuming() {
        addProductTypePage.clickAction();
        addProductTypePage.verifyConsumeDisable();
    }

    public void createEmployeesAcceptanceProduct() {
        addProductTypePage.createEmployeesAcceptance();
    }

    public void verifyAcceptButtonEnable() {
        addProductTypePage.clickAction();
        addProductTypePage.verifyAcceptVisible("Enable");
    }

    public void verifyAcceptButtonDisable() {
        addProductTypePage.clickAction();
        addProductTypePage.verifyAcceptVisible("Disable");
    }

    public void uploadTermAndConditionDocument() {
        addProductPage.clickAddNewButton();
        addProductPage.selectProductType();
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.uploadInvalidDoc();
        addProductPage.clickSaveButton();
        addProductPage.uploadDocValidation();
    }

    public void configUserForSelfCheckOutEnable() {
        companySetupAction.configUserForSelfCheckOutYes();
    }

    public void verifyNotAcceptedProdInTransit() {
        addProductTypePage.verifyNotAcceptedProductInTransit();
    }

    public void createConsumableProduct() {
        addProductTypePage.createAndVerifyConsumeProduct();
    }

    public void createInsurancePolicyProduct() {
        addProductTypePage.createInsurancePolicy();
    }
}
