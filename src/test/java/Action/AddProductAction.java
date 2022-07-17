package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.AddProductPage;

public class AddProductAction
{
    WebDriver driver;
    AddProductPage addProductPage;
    public AddProductAction(WebDriver driver)
    {
        this.driver=driver;
        addProductPage = new AddProductPage(driver);
    }
    public void gotoManageProductPage()
    {
        addProductPage.clickFullMenuDropDown();
        addProductPage.clickAssetManagement();
        addProductPage.clickManageProduct();
    }

    public void addNewButton()
    {
        addProductPage.clickAddNewButton();
    }
    public void duplicateNameValidation()
    {
        addProductPage.getProductName();
        addProductPage.clickAddNewButton();
        addProductPage.productNameValidation();

    }
    public void duplicateProductCodeValidation()
    {
        addProductPage.getProductCode();
        addProductPage.getProductTYpe();
        addProductPage.clickAddNewButton();
        addProductPage.selectPtype();
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.enterDuplicateProductCode();
        addProductPage.clickSaveButton();
        addProductPage.duplicateProductCodeValidation();

    }
    public void dropDownValidation()
    {
        addProductPage.verifyBarCodeDropDown();
    }
    public void productStatusValidation()
    {
        addProductPage.validateStatus();
    }
    public void validationsOnProductName()
    {
        addProductPage.enterProductNameHundredCharacter();
        addProductPage.enterProductNameWithAlphaNumeric();
        addProductPage.enterProductNameWithOneCharacter();
        addProductPage.enterProductNameWithSpecialCharacter();
    }
    public void productNameFieldValidation()
    {
        addProductPage.enterProductNameOneHundredOneCharacter();
    }

    public void validationOnProductCode()
    {
        addProductPage.enterProductCodeWithAlphaNumeric();
        addProductPage.enterProductCodeSixCharacter();
    }
    public void documentValidation()
    {
        addProductPage.selectProductType();
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.uploadInvalidDoc();
        addProductPage.clickSaveButton();
        addProductPage.verifyErrorMessageForInvalidDoc();
        addProductPage.uploadDocValidation();
    }
    public void errorMsgValidationOnDescription()
    {
        addProductPage.errorMsgValidationOnDescription();
    }
    public void descriptionFieldValidation()
    {
        addProductPage.enterDescriptionOneCharacter();
        addProductPage.enterDescriptionTwoHundredFiftyCharacter();

    }
    public void errorMessageValidation()
    {
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.clickSaveButton();
        addProductPage.productTypeValidation();
        addProductPage.selectProductType();
        addProductPage.selectBarCodeType();
        addProductPage.clickSaveButton();
        addProductPage.productNameErrorMsgValidation();
    }
    public void mandatoryFieldValidation()
    {
        addProductPage.mandatoryFieldValidations();
        addProductPage.verifymandatoryFieldValidationOnAsteriskSymbolField();
    }
    public void changeStatusWhileCreatingProduct()
    {
        addProductPage.selectProductType();
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.changeProductStatus();
    }
    public void addProductUniqueName()
    {
        addProductPage.selectProductType();
        addProductPage.enterUniqueItemName();
        addProductPage.selectBarCodeType();
        addProductPage.selectStatus();
        addProductPage.selectAudit();
        addProductPage.selectImageCapture();
        addProductPage.selectCalibration();
        addProductPage.enterProductCode();
        addProductPage.uploadDocument();
        addProductPage.enterDescription();
        addProductPage.clickTheCheckBox();
        addProductPage.enterStockValueForCompany();
        addProductPage.enterStockValueForEmployee();
        addProductPage.clickSaveButton();
        addProductPage.handleSuccessPopup();
        addProductPage.manageProductPageValidation();
        addProductPage.defaultProductCodeValidation();
        addProductPage.goBackToAddProductPage();
    }
    public void addProduct()
    {
        addProductPage.selectProductType();
        addProductPage.enterItemName();
        addProductPage.selectBarCodeType();
        addProductPage.selectStatus();
        addProductPage.selectAudit();
        addProductPage.selectImageCapture();
        addProductPage.selectCalibration();
        addProductPage.enterProductCode();
        addProductPage.uploadDocument();
        addProductPage.enterDescription();
        addProductPage.enterStockValueForCompany();
        addProductPage.enterStockValueForEmployee();
        addProductPage.clickSaveButton();
        addProductPage.handleSuccessPopup();
        addProductPage.goBackToAddProductPage();
    }
        public void verifyNotUniqueName()
        {
            addProductPage.checkNotUniqueName();
        }
        public void verifyUniqueName()
        {
            addProductPage.checkUniqueName();
        }

    public void deployTab()
    {
        addProductPage.clickNextButton();
    }


}
