package testcases;

import Action.LoginAction;
import Action.ProductTransferAction;

import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ProductTransfer extends WebTestBase {

    @Test
    public void verifyProductTypePage() {
        test = getTest("TC_Asset Management_Product_transfer_210_211");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.goToTheProductTransferPage();
        productTransferAction.verifyProductTransferPageAndBreadCrumbInThePage();
    }

    @Test
    public void verifyAndEnterAlphaNumericOnBarcodeField() {
        test = getTest("TC_Asset Management_Product_transfer_212");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.goToTheProductTransferPage();
        productTransferAction.verifyAlphaNumericValueInBarCodeField();
    }

    @Test
    public void verifyBarCodeAndSearchFieldFunctionalities() {
        test = getTest("TC_Asset Management_Product_transfer_213_214_215_216_217_218_219_220");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.verifyBarCodeAndSearchFieldFunctionalities();
    }

    @Test
    public void enterAlphaNumericValues() {
        test = getTest("TC_Asset Management_Product_transfer_220_221_222_223_224");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.enterAlphaNumericValues();
    }

    @Test
    public void VerifyProductIsAddedOnProductMoveToListTable() {
        test = getTest("TC_Asset Management_Product_transfer_229_230_231_235");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.VerifyProductIsAddedOnProductMoveToListTable();
    }

    @Test
    public void verifyMultipleCheckBox() {
        test = getTest("TC_Asset Management_Product_transfer_225_226_227");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.verifyMultipleCheckBoxAndTransferQuantityField();
    }

    @Test
    public void verifyProductMoveToListAndDeleteAndPagination() {
        test = getTest("TC_Asset Management_Product_transfer_228_232_233");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.verifyProductMoveToListAndDeleteAndPagination();
    }

    @Test
    public void verifyAfterProductIsMovedToTheLocation() {
        test = getTest("TC_Asset Management_Product_transfer_234");
        ProductTransferAction productTransferAction = new ProductTransferAction(driver);
        new LoginAction(driver).logoutLogin();
        productTransferAction.verifyAfterProductIsMovedToTheLocation();
    }
}
