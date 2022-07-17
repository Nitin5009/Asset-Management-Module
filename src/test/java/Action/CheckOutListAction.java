package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.CheckOutRequestPage;
import pageobjects.DeployProductPage;
import pageobjects.MyProductPage;
import pageobjects.ProductListingPage;

public class CheckOutListAction {

    CheckOutRequestPage checkOutRequest;
    ProductListingPage productListing;
    DeployProductPage deployProduct;
    MyProductPage myProduct;

    public CheckOutListAction(WebDriver driver)
    {
        this.checkOutRequest = new CheckOutRequestPage(driver);
        this.productListing = new ProductListingPage(driver);
        this.deployProduct=new DeployProductPage(driver);
        this.myProduct=new MyProductPage(driver);
    }
    public void navigateToViewCheckOutListPage()
    {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        myProduct.openMyProductPage();
        checkOutRequest.navigateToCheckOutRequestList();

    }
    public void verifyCheckOutListHeader()
    {
        checkOutRequest.verifyCheckOutListHeader();
        checkOutRequest.verifyBreadCrumbPresenceInCheckOutList();
        checkOutRequest.clickProductBreadCrumbProductMenu();
        checkOutRequest.verifyProductPage();
        navigateToViewCheckOutListPage();
        checkOutRequest.clickHomeBreadCrumbProductMenu();
        checkOutRequest.verifyHomePage();
    }
    public void verifyExpandAndCollapseFilter()
    {
        checkOutRequest.clickExpandCollapseIcon();
        checkOutRequest.verifyExpandMenu();
        checkOutRequest.clickExpandCollapseIcon();
        checkOutRequest.verifyCollapseMenu();
    }
    public void verifyUniqueNameDropdownFunction()
    {
        checkOutRequest.clickUniqueNameDropdown();
        checkOutRequest.clickUniqueProduct();
        checkOutRequest.uniqueNameSearchFunction();
    }
    public void verifyCheckOutListPagination()
    {
        navigateToViewCheckOutListPage();
        checkOutRequest.verifyCheckOutListPagination();
    }
}
