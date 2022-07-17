package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.DeployProductPage;
import pageobjects.ProductReportPage;

public class ProductReportAction {

    ProductReportPage productReportPage;
    DeployProductPage deployProductPage;
    WebDriver driver;

    public ProductReportAction(WebDriver driver) {
        this.driver = driver;
        this.productReportPage = new ProductReportPage(driver);
        this.deployProductPage = new DeployProductPage(driver);
    }

    public void navigateToProductReportPage() {
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
        productReportPage.clickProductReportMenu();
        productReportPage.clickProductCountReportMenu();
    }

    public void getNamesBasedOnLocation() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("");
        productReportPage.searchProductCountReport();
        productReportPage.getProductNamesUnderSearchedLocation("");
    }

    public void getActiveStatusProductCountForCA() {
        productReportPage.expandStatusDropdown();
        productReportPage.selectStatus("Active");
        productReportPage.expandStatusDropdown();
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Status");

    }

    public void getActiveStatusProductCountForPM() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("ProjectManager");
        productReportPage.expandStatusDropdown();
        productReportPage.selectStatus("Active");
        productReportPage.expandStatusDropdown();
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Status");
    }

    public void getActiveStatusProductCountForNU() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("NormalUser");
        productReportPage.expandStatusDropdown();
        productReportPage.selectStatus("Active");
        productReportPage.expandStatusDropdown();
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Status");
    }

    public void getProductCountBasedOnLocationNU() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("NormalUser");
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Location");
    }

    public void getProductCountBasedOnLocationPM() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("ProjectManager");
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Location");
    }

    public void getProductCountBasedOnLocationCA() {
        productReportPage.expandLocationField();
        productReportPage.selectLocation("CompanyAdmin");
        productReportPage.enterStartAndEndDateOfMonth();
        productReportPage.searchProductCountReport();
        productReportPage.getProductCount("Location");
    }
}
