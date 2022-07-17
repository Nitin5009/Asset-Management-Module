package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.CompanySetupPage;
import pageobjects.DeployProductPage;

public class CompanySetupAction {
    WebDriver driver;
    CompanySetupPage companySetup;

    public CompanySetupAction(WebDriver driver) {
        this.driver = driver;
        companySetup = new CompanySetupPage(driver);
    }

    public void navigateToCompanySetupPage() {
        companySetup.clickFullMenu();
        companySetup.clickCompanySetupMenu();
        companySetup.clickCompanySetupSubMenu();
    }

    public void navigateSideBarLocation() {
        companySetup.sideBarLocation();
    }

    public void getAllLocationList() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        deployProduct.make100PageSize();
        deployProduct.getLocationsFromSetup();
    }

    public void createChildLocation() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        companySetup.clickAddLocationButton();
        companySetup.selectParentLocation();
        companySetup.enterLocationName();
        companySetup.enterAddressLine1();
        companySetup.enterCity();
        companySetup.selectCountry();
        companySetup.saveLocation();
        deployProduct.handleSuccessPopup();
    }

    public void changeProductCostToggle(boolean productCost) {
        companySetup.sideBarProductType();
        companySetup.openEditProductType();
        companySetup.clickProductCostToggle(productCost);
    }

    public void changeInsuranceRefNumToggle(boolean policy) {
        companySetup.sideBarProductType();
        companySetup.openEditProductType();
        companySetup.clickInsurancePolicyToggle(policy);
    }

    public void changeDepreciationToggle(boolean depreciation) {
        companySetup.sideBarProductType();
        companySetup.openEditProductType();
        companySetup.clickDepreciationToggle(depreciation);
    }

    public void navigateToUserPage() {
        companySetup.clickFullMenu();
        companySetup.clickCompanySetupMenu();
        companySetup.clickUserMenu();
    }

    public void configUserForSelfCheckOutYes() {
        navigateToUserPage();
        companySetup.openSecondUser();
        companySetup.navigateToUserConfigurationMenu();
        companySetup.expandProductManagementMenu();
        companySetup.selfCheckOutYes();
    }

    public void configCASelfCheckOutYes() {
        navigateToUserPage();
        companySetup.openAdminUser();
        companySetup.navigateToUserConfigurationMenu();
        companySetup.expandProductManagementMenu();
        companySetup.selfCheckOutYes();
    }

    public void configUserForSelfCheckOutNo() {
        navigateToUserPage();
        companySetup.openSecondUser();
        companySetup.navigateToUserConfigurationMenu();
        companySetup.expandProductManagementMenu();
        companySetup.selfCheckOutNo();
    }

    public void configUserForSelfTransfer() {
        navigateToUserPage();
        companySetup.openSecondUser();
        companySetup.navigateToUserConfigurationMenu();
        companySetup.expandProductManagementMenu();
        companySetup.selfTransferYes();
    }

    public void changeTimeZoneAndShiftOfSecondUser() {
        navigateToUserPage();
        companySetup.openSecondUser();
        companySetup.changeTimeZone();
        companySetup.selectLocationTenShift();
        companySetup.saveTimeZone();
        companySetup.handleUserUpdateSuccess();
    }

    public void changeTimeZoneAndShiftOfAdminUser() {
        navigateToUserPage();
        companySetup.openAdminUser();
        companySetup.changeTimeZone();
        companySetup.selectLocationTenShift();
        companySetup.saveTimeZone();
        companySetup.handleUserUpdateSuccess();
    }

    public void setShiftForProjectManager() {
        navigateToUserPage();
        companySetup.openProjectManager();
        companySetup.selectPMLocationShift();
        companySetup.saveTimeZone();
        companySetup.handleUserUpdateSuccess();
    }

    public void setShiftForNormalUser() {
        navigateToUserPage();
        companySetup.openSecondUser();
        companySetup.selectPMLocationShift();
        companySetup.saveTimeZone();
        companySetup.handleUserUpdateSuccess();
    }

    public void getCompanyAdminLocation() {
        navigateToUserPage();
        companySetup.expandUserNameSearchField();
        companySetup.enterUserInSearchField();
        companySetup.clickSearchButton();
        companySetup.openCompanyAdmin();
        companySetup.getCompanyUserLocation();
    }

    public void getDepartmentNameList() {
        companySetup.sideBarDepartment();
        companySetup.getAllDepartmentList();
    }

    public void getUserNameBasedONDepartment() {
        navigateToUserPage();
        companySetup.expandDepartmentDropdown();
        companySetup.selectDepartment("");
        companySetup.clickSearchButton();
        companySetup.getUserNameBasedOnDepartment("BasedOnDept");
    }

    public void getAllUserNameList() {
        navigateToUserPage();
        companySetup.getAllNormalUserNames();
    }
}
