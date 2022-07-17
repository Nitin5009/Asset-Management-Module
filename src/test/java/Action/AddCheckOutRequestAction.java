package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.*;

public class AddCheckOutRequestAction {

    AddCheckOutRequestPage addCheckOutRequest;
    CheckOutRequestPage checkOutRequest;
    ProductListingPage productListing;
    DeployProductPage deployProduct;
    MyProductPage myProduct;
    public AddCheckOutRequestAction(WebDriver driver)
    {
        this.addCheckOutRequest = new AddCheckOutRequestPage(driver);
        this.checkOutRequest = new CheckOutRequestPage(driver);
        this.productListing = new ProductListingPage(driver);
        this.deployProduct=new DeployProductPage(driver);
        this.myProduct=new MyProductPage(driver);
    }

    public void navigateToAddCheckOutListPage()
    {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        myProduct.openMyProductPage();
        addCheckOutRequest.navigateToAddCheckOutList();
    }
    public void breadCrumbInAddCheckOutListPage()
    {
        addCheckOutRequest.clickCheckOutInBreadCrumb();
        addCheckOutRequest.verifyCheckOutListPage();
        navigateToAddCheckOutListPage();
        checkOutRequest.clickProductBreadCrumbProductMenu();
        checkOutRequest.verifyProductPage();
        navigateToAddCheckOutListPage();
        checkOutRequest.clickHomeBreadCrumbProductMenu();
        checkOutRequest.verifyHomePage();
    }
    public void assignDateFieldsFunction()
    {
        addCheckOutRequest.verifyAssignFromDate();
        addCheckOutRequest.verifyAssignTillDate();
    }
    public void verifyAssignTimesField()
    {
        addCheckOutRequest.verifyAssignStartTime();
        addCheckOutRequest.verifyAssignEndTime();
    }
    public void verifyCheckOutListHeader()
    {
        addCheckOutRequest.verifyCheckOutTableHeader();
    }
    public void verifyAlphaNumericCondition()
    {
        addCheckOutRequest.alphaNumericCheckOutComment();
        addCheckOutRequest.verifyAlphaNumericBarCodeValue();
    }
    public void searchBarCode()
    {
        addCheckOutRequest.enterValidBarCode();
        addCheckOutRequest.clickBarCodeSearch();
        addCheckOutRequest.verifyBarCodeSearchResult();
    }
    public void verifyProductAddFunctions()
    {
         addCheckOutRequest.verifyLocationField();
         addCheckOutRequest.verifyProductTypeField();
         addCheckOutRequest.verifyProductNameField();
         addCheckOutRequest.verifyProductField();
         productListing.searchAssignProduct();
         addCheckOutRequest.verifySearchedCheckOutProduct();
    }
    public void addCheckOutProduct()
    {
        verifyProductAddFunctions();
        productListing.addAssignProductToList();
        addCheckOutRequest.verifyListOfProductTable();
    }
    public void verifyListOfProdTableHeader()
    {
        addCheckOutRequest.verifyListOfProdHeaders();
    }
    public void verifyQuantityFieldFunction()
    {
        addCheckOutRequest.verifyRequestQuantityField();
    }
    public void verifyProductDeleteFunction()
    {
        addCheckOutRequest.clickDeleteIcon();
        addCheckOutRequest.confirmationForDelete();
        addCheckOutRequest.verifyDeletedProduct();
    }
    public void verifyProductSaveButtonFunction()
    {
        addCheckOutRequest.saveProductCheckOutRequest();
        addCheckOutRequest.verifySavedCheckOutRequest();
    }
    public void verifyCancelButtonFunction()
    {
        addCheckOutRequest.clickCancelButton();
        addCheckOutRequest.verifyCheckOutListScreen();
    }
}
