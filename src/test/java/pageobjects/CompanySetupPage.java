package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class CompanySetupPage extends WebBasePage {

    WebDriver driver;
    public static String productType;
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    public CompanySetupPage(WebDriver driver) {
        super(driver, "Company setup page");
        this.driver = driver;
    }

    public void clickFullMenu() {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        deployProduct.clickFullMenuDropDown();
    }

    public void clickCompanySetupMenu() {
        WebElement companySetup = findElementVisibility(By.cssSelector("[data-name='COMPANY']>a"), 15);
        if (companySetup != null) {
            click(By.cssSelector("[data-name='COMPANY']>a"), "Company Setup menu", 15);
        } else {
            driver.navigate().refresh();
            clickFullMenu();
            clickCompanySetupMenu();
        }
    }

    public void clickCompanySetupSubMenu() {
        WebElement companySetupSubMenu = findElementVisibility(By.xpath("//ul[contains(@class,'submenu')]//a[text()='Company Setup']"), 15);
        if (companySetupSubMenu != null) {
            click(By.xpath("//ul[contains(@class,'submenu')]//a[text()='Company Setup']"), "Company Setup Sub Menu", 15);
        } else {
            clickCompanySetupMenu();
            clickCompanySetupSubMenu();
        }
    }

    public void clickAddLocationButton() {
        scrollToWebelement(By.cssSelector("span#showHideMenuParent"), "Add Location");
        click(By.cssSelector("a#ancCreateLocation"), "Add Location", 15);
    }

    public void selectParentLocation() {
        click(By.cssSelector("div#divmultilevelselect>div>div"), "Parent Location Dropdown", 15);
        click(By.xpath("//ul[@id='CompantLocationSelect']//li[@class='parentli'][1]//span"), "Parent Location Value", 15);
    }

    public void enterLocationName() {
        enter(By.cssSelector("input#LocationName"), prop.getProperty("childLocationName") + dateValue, "Location Name", 15);
    }

    public void enterAddressLine1() {
        enter(By.cssSelector("input#Address1"), "Address Line1", "Address Line1", 15);
    }

    public void enterCity() {
        enter(By.cssSelector("input#City"), "City", "City", 15);
    }

    public void selectCountry() {
        selectValueWithText(By.cssSelector("select#ddlCountry"), "India", "Country", 15);
    }

    public void saveLocation() {
        click(By.cssSelector("a#btnSave"), "Save", 15);
    }

    public void sideBarProductType() {
        findElementVisibility(By.cssSelector(".customsearchbox>input"), 15);
        enter(By.cssSelector(".customsearchbox>input"), "Product Type", "Menu Search", 15);
        click(By.xpath("//ul[@id='upper']//li//a[text()='Product Type']"), "Side Bar Product Type", 15);
        waitForLoader(20);
    }

    public void sideBarLocation() {
        enter(By.cssSelector(".customsearchbox>input"), "Location", "Menu Search", 15);
        click(By.xpath("//ul[@id='upper']//li//a[text()='Location']"), "Side Bar Location", 15);
        waitForLoader(20);
    }

    public void openEditProductType() {
        productType = getText(By.xpath("//table[@id='tblAsset']//tbody//tr[1]//td[4]//a"), 15).trim();
        click(By.xpath("//table[@id='tblAsset']//tbody//tr[1]//td[4]//a"), "Edit Product Type", 15);
        waitForLoader(20);
    }

    public void saveProductTypeChange() {
        click(By.cssSelector("a#btnSave"), "Save", 15);
    }

    public void confirmationPopup() {
        findElementsVisibility(By.cssSelector("div.modal-confirm-footer"));
        click(By.cssSelector("div.modal-confirm-footer>button"), "Confirmation Ok", 15);
    }

    public void clickProductCostToggle(boolean cost) {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        scrollToWebelement(By.xpath("//input[@id='IsItemCostN']//parent::label//span[@class='slider round']"), "ProductCost Toggle");
        boolean option = findElementPresence(By.id("IsItemCostN"), 15).isSelected();
        if (cost) {
            if (!option) {
                click(By.xpath("//input[@id='IsItemCostN']//parent::label//span[@class='slider round']"), "Product Cost Yes", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        } else {
            if (option) {
                boolean depreciationOption = findElementPresence(By.id("IsDepreciableN"), 15).isSelected();
                if (depreciationOption) {
                    click(By.xpath("//span[text()='Deployment Properties:']//following::div[@class='form-group'][9]//div//span[@class='slider round']"), "Product Cost No", 15);
                }
                click(By.xpath("//input[@id='IsItemCostN']//parent::label//span[@class='slider round']"), "Product Cost No", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        }
    }

    public void clickInsurancePolicyToggle(boolean policy) {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        scrollToWebelement(By.xpath("//input[@id='InsurancePolicyN']//parent::label//span[@class='slider round']"), "ProductCost Toggle");
        boolean option = findElementPresence(By.id("InsurancePolicyN"), 15).isSelected();
        if (policy) {
            if (!option) {
                click(By.xpath("//input[@id='InsurancePolicyN']//parent::label//span[@class='slider round']"), "Product Cost Yes", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        } else {
            if (option) {
                click(By.xpath("//input[@id='InsurancePolicyN']//parent::label//span[@class='slider round']"), "Product Cost No", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        }
    }

    public void clickDepreciationToggle(boolean depriciation) {
        DeployProductPage deployProduct = new DeployProductPage(driver);
        scrollToWebelement(By.xpath("//input[@id='IsDepreciableN']//parent::label//span[@class='slider round']"), "ProductCost Toggle");
        boolean option = findElementPresence(By.id("IsDepreciableN"), 15).isSelected();
        if (depriciation) {
            if (!option) {
                boolean productCost = findElementPresence(By.id("IsItemCostN"), 15).isSelected();
                if (!productCost) {
                    click(By.xpath("//input[@id='IsItemCostN']//parent::label//span[@class='slider round']"), "Product Cost Yes", 15);
                }
                click(By.xpath("//span[text()='Deployment Properties:']//following::div[@class='form-group'][9]//div//span[@class='slider round']"), "Product Cost Yes", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        } else {
            if (option) {
                click(By.xpath("//span[text()='Deployment Properties:']//following::div[@class='form-group'][9]//div//span[@class='slider round']"), "Product Cost No", 15);
                saveProductTypeChange();
                waitForLoader(20);
                deployProduct.handleSuccessPopup();
            }
        }
    }

    public void clickUserMenu() {
        WebElement element = findElementVisibility(By.xpath("//a[text()='User']"), 10);
        if (element == null) {
            clickCompanySetupMenu();
        }
        click(By.xpath("//a[text()='User']"), "User menu", 30);
    }

    public void openUser(String userName) {
        waitForLoader(60);
        click(By.xpath("//a[normalize-space(text())='" + userName + "']"), userName, 20);
    }

    public void openSecondUser() {
        openUser(prop.getProperty("secondUser"));
    }

    public void make100PageSize() {
        findElementVisibility(By.cssSelector("select#pageSize"), 30);
        scrollToWebelement(By.cssSelector("select#pageSize"), "Page Size");
        staticWait(3000);
        selectValueWithValue(By.cssSelector("select#pageSize"), "100", "Page Size", 15);
        waitForLoader(20);
        staticWait(3000);
    }

    public void openProjectManager() {
        make100PageSize();
        openUser(prop.getProperty("projectManager"));
    }

    public void navigateToUserConfigurationMenu() {
        click(By.xpath("//ul[@id='myTab']//a[normalize-space(text())='Configuration']"), "User Configuration menu", 40);
    }

    public void expandProductManagementMenu() {
        click(By.xpath("//a[@class='card-link collapsed']//span[text()='Product Management']"), "Product management popup", 30);
    }

    public void selfCheckOutYes() {
        String checkOutOption = findElementPresence(By.xpath("//span[text()='Self CheckOut']/../..//div//input[@checked]"), 20).getAttribute("value");
        if (!checkOutOption.equalsIgnoreCase("Yes")) {
            click(By.xpath("//span[text()='Self CheckOut']/../..//div//label[normalize-space(text())='Yes']"), "Self check out Yes", 20);
            SaveUserConfig();
            handleUserConfigSuccess();
        }
    }

    public void selfCheckOutNo() {
        String checkOutOption = findElementPresence(By.xpath("//span[text()='Self CheckOut']/../..//div//input[@checked]"), 20).getAttribute("value");
        if (!checkOutOption.equalsIgnoreCase("No")) {
            click(By.xpath("//span[text()='Self CheckOut']/../..//div//label[normalize-space(text())='No']"), "Self check out No", 20);
            SaveUserConfig();
            handleUserConfigSuccess();
        }
    }

    public void selfTransferYes() {
        String checkOutOption = findElementPresence(By.xpath("//span[text()='Self Transfer']/../..//div//input[@checked]"), 20).getAttribute("value");
        if (!checkOutOption.equalsIgnoreCase("Yes")) {
            click(By.xpath("//span[text()='Self Transfer']/../..//div//label[normalize-space(text())='Yes']"), "Self transfer Yes", 20);
            SaveUserConfig();
            handleUserConfigSuccess();
        }
    }

    public void SaveUserConfig() {
        click(By.xpath("//div[@class='divKeysConfig collapse show']//a[@id='hlkSave']"), "User Config save", 20);
    }

    public void handleUserConfigSuccess() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Record(s) has been successfully saved.']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when click save button after update the user config");
            logger.info("Success message is not displayed when click save button after update the user config");
            takeScreenshot("UserConfigSuccessMsg");
        }
    }

    public void changeTimeZone() {
        selectValueWithText(By.cssSelector("select#ddlTimeZone"), prop.getProperty("timeZone"), "Time Zone Dropdown", 20);
    }

    public void openAdminUser() {
        make100PageSize();
        click(By.xpath("//a[@id='ancEdituser']/span"), "Admin User Name", 20);
    }

    public void saveTimeZone() {
        click(By.xpath("//button[@id='btnSave']"), "Save Button", 20);
        waitForLoad(30);
    }

    public void handleUserUpdateSuccess() {
        waitForLoader(30);
        try {
            findElementVisibility(By.cssSelector("#closenotifymessage"), 20).click();
        } catch (Exception e) {
        }
    }

    public void selectLocationTenShift() {
        selectShift("Location10");
    }

    public void selectPMLocationShift() {
        productManagerLocation = "Auto Child Location";
        selectShift(productManagerLocation);
    }

    public void selectShift(String shiftName) {
        click(By.xpath("//select[@id='Clientdetail_shift_id']"), "Shift Dropdown field", 20);
        click(By.xpath("//select[@id='Clientdetail_shift_id']//option[contains(text(),'" + shiftName + "')]"), shiftName, 30);
    }

    public void expandUserNameSearchField() {
        click(By.xpath("//span[@id='searchbyname' and contains(text(),'Name')]"), "User Name Search", 20);
    }

    public void enterUserInSearchField() {
        enter(By.xpath("//input[@id='txtsearchByUserName']"), prop.getProperty("companyAdmin"), "User Name Search", 20);
    }

    public void clickSearchButton() {
        click(By.xpath("//a[@id='Go']"), "Search", 20);
    }

    public void openCompanyAdmin() {
        click(By.xpath("//span[@title='" + prop.getProperty("companyAdmin") + "']"), prop.getProperty("companyAdmin"), 20);
    }

    public void getCompanyUserLocation() {
        WebElement locationName = findElementVisibility(By.xpath("//select[@id='Clientdetail_shift_id']"), 20);
        Select select = new Select(locationName);
        String tempName = select.getFirstSelectedOption().getText().trim();
        companyAdminLocationName = tempName.substring(tempName.indexOf("(") + 1, tempName.indexOf(")"));
    }

    public void sideBarDepartment() {
        findElementVisibility(By.cssSelector(".customsearchbox>input"), 15);
        enter(By.cssSelector(".customsearchbox>input"), "Department", "Menu Search", 15);
        click(By.xpath("//ul[@id='upper']//li//a[text()='Department']"), "Side Bar Department", 15);
        waitForLoader(20);
    }

    public void getAllDepartmentList() {
        int iteration = 0;
        Object paginationNextButton = "";
        while (paginationNextButton != null) {
            waitForLoader(60);
            List<WebElement> departmentLocators = findMultipleElement(By.xpath("//table[@id='tblDepartment']//tbody//tr//td[2]/a"), 20);
            for (WebElement department : departmentLocators) {
                iteration++;
                String departmentName = getText(By.xpath("//table[@id='tblDepartment']//tbody//tr[" + iteration + "]//td[2]/a"), 20, 60);
                departmentNameList.add(departmentName);
            }
            waitForLoader(60);
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 120);
                staticWait(3000);
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next Button", 10);
                iteration = 0;
            }
        }
    }

    public void expandDepartmentDropdown() {
        click(By.xpath("//span[@id='departmentId']"), "Department dropdown", 20);
    }

    public void selectDepartment(String department) {
        if (department.equalsIgnoreCase("")) {
            List<WebElement> departmentFromTable = findMultipleElement(By.xpath("//table[@id='tableResponsive']//tbody//tr//td[8]"), 20);
            for (WebElement departmentName : departmentFromTable) {
                if (!departmentName.getText().equalsIgnoreCase("--")) {
                    departmentNameToVerify = departmentName.getText().trim();
                    enter(By.xpath("//span[@id='departmentId']//ancestor::div[1]//parent::div//input[contains(@class,'searchcustomfilter')]"), departmentNameToVerify, "DepartmentSearch", 20);
                    click(By.xpath("//label[text()='" + departmentNameToVerify + "']"), departmentNameToVerify, 20);
                    break;
                }
            }
        } else {
            click(By.xpath("//label[text()='" + department + "']"), department, 10);
        }
    }

    public void getUserNameBasedOnDepartment(String process) {
        String userName = "";
        int iteration = 0;
        Object paginationNextButton = "";
        while (paginationNextButton != null) {
            List<WebElement> userNameLocators = findMultipleElement(By.xpath("//table[@id='tableResponsive']//tbody//tr//td[2]/a"), 20);
            for (WebElement userNameLocator : userNameLocators) {
                userName = userNameLocator.getText().trim();
                if (process.equalsIgnoreCase("AllUser")) {
                    allUserNameList.add(userName);
                } else {
                    usersInTheDepartment.add(userName);
                }
            }
            paginationNextButton = findElementVisibility(By.xpath("//a[@class='page-link next']"), 5);
            if (paginationNextButton != null) {
                findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 120);
                click(By.xpath("//a[@class='page-link next']"), "Pagination Next Button", 10);
                iteration = 0;
            }
        }
    }

    public void getAllNormalUserNames() {
        getUserNameBasedOnDepartment("AllUser");
    }
}
