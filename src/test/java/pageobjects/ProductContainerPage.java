package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.WebBasePage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;
import static utils.Errors.duplicateProdErr;
import static utils.StaticData.*;

public class ProductContainerPage extends WebBasePage {

    WebDriver driver;
    DeployProductPage deployProduct;
    AttachmentsPage attachmentsPage;
    AddProductPage addProduct;
    ProductListingPage productListing;
    String containerToSearch;
    List<String> deletedContainers = new ArrayList<>();
    List<String> deployedProductNames = new ArrayList<>();
    List<String> expectedCompartmentList = new ArrayList<>();
    List<String> productsNameToValidate = new ArrayList<>();
    String beforeEditProduct;
    String afterEditProduct;
    String selectedProduct;

    public ProductContainerPage(WebDriver driver) {
        super(driver, "Product Container Page");
        this.driver = driver;
        this.deployProduct = new DeployProductPage(driver);
        this.addProduct = new AddProductPage(driver);
        this.productListing = new ProductListingPage(driver);
        this.attachmentsPage = new AttachmentsPage(driver);
    }

    public void openProductContainerPage() {
        deployProduct.clickFullMenuDropDown();
        deployProduct.clickAssetManagement();
        WebElement element = findElementVisibility(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Products Container List']"), 40);
        if (element != null) {
            click(By.xpath("//ul[contains(@class,'submenu clschild')]//li//a[text()='Products Container List']"), "Prod Container Sub menu", 20);
        } else {
            openProductContainerPage();
        }
    }

    public void verifyProductContainerPage() {
        WebElement prodContainerPage = findElementVisibility(By.xpath("//span[@id='titleName' and text()='Product Container']"), 30);
        if (prodContainerPage != null) {
            getTest().log(LogStatus.PASS, "Product Container List page is displayed as expected when click on the Product Container List sub menu");
            logger.info("Product Container List page is displayed as expected when click on the Product Container List sub menu");
        } else {
            getTest().log(LogStatus.FAIL, "Product Container List page is not displayed when click on the Product Container List sub menu");
            logger.info("Product Container List page is not displayed when click on the Product Container List sub menu");
            takeScreenshot("ProdContainerListPage");
        }
    }

    public void verifyBreadCrumbFunction() {
        WebElement breadCrumb = findElementVisibility(By.xpath("//div[@id='SiteMapLink']//ol"), 40);
        if (breadCrumb != null) {
            click(By.xpath("//div[@id='SiteMapLink']//ol//li//a[text()='Company Setup']"), "BreadCrumb Company Setup", 20);
            verifyCompanySetupPage();
            openProductContainerPage();
            click(By.xpath("//div[@id='SiteMapLink']//ol//li//a[@class='home']"), "BreadCrumb Home", 20);
            verifyHomePage();

        } else {
            getTest().log(LogStatus.FAIL, "BreadCrumb is not displayed in the Product Container list page");
            logger.info("BreadCrumb is not displayed in the Product Container list page");
            takeScreenshot("BreadCrumb");
        }
    }

    public void verifyCompanySetupPage() {
        WebElement companySetup = findElementVisibility(By.xpath("//span[@id='titleName' and text()='Announcement']"), 30);
        if (companySetup != null) {
            getTest().log(LogStatus.PASS, "Company setup page is displayed when click breadcrumb company set in product container list page");
            logger.info("Company setup page is displayed when click breadcrumb company set in product container list page");
        } else {
            getTest().log(LogStatus.FAIL, "Company setup page is not displayed when click breadcrumb company set in product container list page");
            logger.info("Company setup page is not displayed when click breadcrumb company set in product container list page");
            takeScreenshot("CompanySetupPage");
        }
    }

    public void verifyHomePage() {
        WebElement homePage = findElementVisibility(By.xpath("//div[@class='row']//span[normalize-space(text())='Dashboard']"), 40);
        if (homePage != null) {
            getTest().log(LogStatus.PASS, "Home page  is displayed when click breadcrumb company set in product container list page");
            logger.info("Home page  is displayed when click breadcrumb company set in product container list page");
        } else {
            getTest().log(LogStatus.FAIL, "Home page is not displayed when click breadcrumb company set in product container list page");
            logger.info("Home page is not displayed when click breadcrumb company set in product container list page");
            takeScreenshot("HomePage");
        }
    }

    public void enterProdContainerInSearchField() {
        containerToSearch = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a"), 30).trim();
        enter(By.cssSelector("div>input#search"), containerToSearch, "Search Field", 20);
    }

    public void clickSearchButton() {
        productListing.clickSearchButton();
    }

    public void verifySearchedContainer() {
        String searchedContainer = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a"), 30, 30).trim();
        if (searchedContainer.equals(containerToSearch)) {
            getTest().log(LogStatus.PASS, "Searched container " + containerToSearch + " is displayed as expected when enter and then click search button");
            logger.info("Searched container " + containerToSearch + " is displayed as expected when enter and then click search button");
        } else {
            getTest().log(LogStatus.FAIL, "Searched container " + containerToSearch + " is not displayed  when enter and then click search button");
            logger.info("Searched container " + containerToSearch + " is not displayed  when enter and then click search button");
            takeScreenshot("SearchResult");
        }
    }

    public void enterAlphaNumericInSearchFields() {
        enter(By.cssSelector("div>input#search"), "A1b2C3", "Search Field", 20);
    }

    public void verifyAlphaNumericCharacter() {
        String actualText = getAtribute(By.cssSelector("div>input#search"), "value", 30);
        if (actualText.equals("A1b2C3")) {
            getTest().log(LogStatus.PASS, "Container Search field accepts the Alpha numeric character as expected");
            logger.info("Container Search field accepts the Alpha numeric character as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Container Search field does not accepts the Alpha numeric character");
            logger.info("Container Search field does not accepts the Alpha numeric character");
            takeScreenshot("ContainerSearch");
        }
    }

    public void verifyPageSize() {
        WebElement option;
        int selectedValue;
        int recordsCount;
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "20", "Page Size", 20);
        waitForLoader(10);
        Select select = new Select(driver.findElement(By.xpath("//select[@id='pageSize']")));
        option = select.getFirstSelectedOption();
        selectedValue = Integer.parseInt(option.getText());
        recordsCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 30).size();
        if (selectedValue >= recordsCount) {
            getTest().log(LogStatus.PASS, "Selected records are displayed as expected in product container list");
            logger.info("Selected records are displayed as expected in product container list");
            selectValueWithValue(By.xpath("//select[@id='pageSize']"), "10", "Page Size", 20);
            waitForLoader(10);
        } else {
            getTest().log(LogStatus.FAIL, "Selected records are not displayed in product container list");
            logger.info("Selected records are not displayed in product container list");
            takeScreenshot("PageSize");
        }
    }

    public void paginationOnProductContainerList() {
        String lastRecord;
        int recordCount;
        int defaultStartRecordCount;
        int defaultEndCount;
        int totalRecordCount;
        String[] defaultPaginationText;
        String[] nextPaginationText;
        String[] previousPaginationText;
        String[] lastPagePaginationText;
        String[] firstPagePaginationText;
        defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            int secondPageEndRecordCount = Integer.parseInt(secondPaginationText[3]);
            int pageSize = Integer.parseInt(getText(By.cssSelector("#pageSize>option[selected]"), 30));
            if (secondPageStartRecordCount == defaultEndCount + 1 && secondPageEndRecordCount <= 2 * pageSize) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed when click on the \"2\" pagination button");
                logger.info("Second page is not displayed when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"1\" pagination button");
                logger.info("First page is displayed as expected when click on the \"1\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed when click on the \"1\" pagination button");
                logger.info("First page is not displayed when click on the \"1\" pagination button");
                takeScreenshot("Pagination1");
            }

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int nextPageStartRecordCount = Integer.parseInt(nextPaginationText[1]);
            if (nextPageStartRecordCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Next page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Next page is not displayed when click on the \"Next\" pagination button");
                logger.info("Next page is not displayed when click on the \"Next\" pagination button");
                takeScreenshot("PaginationNext");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link previous' and text()='Prev']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int previousPageEndCount = Integer.parseInt(previousPaginationText[3]);
            if (previousPageEndCount == nextPageStartRecordCount - 1) {
                getTest().log(LogStatus.PASS, "Previous page is displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is displayed as expected when click on the \"Previous\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Previous page is not displayed when click on the \"Previous\" pagination button");
                logger.info("Previous page is not displayed when click on the \"Previous\" pagination button");
                takeScreenshot("PaginationPrevious");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link last' and text()='Last ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int lastPageEndCount = Integer.parseInt(lastPagePaginationText[3]);
            if (lastPageEndCount == totalRecordCount) {
                getTest().log(LogStatus.PASS, "Last page is displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is displayed as expected when click on the \"Last\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Last page is not displayed when click on the \"Last\" pagination button");
                logger.info("Last page is not displayed when click on the \"Last\" pagination button");
                takeScreenshot("PaginationLast");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link  first' and text()='First ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + recordCount + "]//td//a"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + lastRecord + "']"), 30);
            firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            firstPageStartRecordCount = Integer.parseInt(firstPagePaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is displayed as expected when click on the \"First\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed when click on the \"First\" pagination button");
                logger.info("First page is not displayed when click on the \"First\" pagination button");
                takeScreenshot("PaginationFirst");
            }
        } else {
            getTest().log(LogStatus.PASS, "Pagination section is not displayed since the record count is " + totalRecordCount);
            logger.info("Pagination section is not displayed since the record count is " + totalRecordCount);
        }
    }

    public void verifyPlaceHolder() {
        enterAlphaNumericInSearchFields();
        String afterEnterText = getAtribute(By.cssSelector("div>input#search"), "value", 30);
        if (!afterEnterText.equals("")) {
            getTest().log(LogStatus.PASS, "Place holder value is removed and entered data is displayed in the search field");
            logger.info("Place holder value is removed and entered data is displayed in the search field");
        } else {
            getTest().log(LogStatus.FAIL, "Place holder value is not removed and entered data is not displayed in the search field");
            logger.info("Place holder value is not removed and entered data is not displayed in the search field");
            takeScreenshot("PlaceHolder");
        }
    }

    public void clickAddProductContainerButton() {
        staticWait(3000);
        click(By.cssSelector("a#ancCreateDepartment"), "Add Container icon", 60);
    }

    public void handleSuccessPopup() {
        deployProduct.handleSuccessPopup();
    }

    public void verifyCreateProductContainerPage() {
        WebElement createContainerPage = findElementVisibility(By.xpath("//div[@id='General']//span[text()='Add Product Container List']"), 30);
        if (createContainerPage != null) {
            getTest().log(LogStatus.PASS, "Create Container page is displayed as expected when click on the add icon");
            logger.info("Create Container page is displayed as expected when click on the add icon");
        } else {
            getTest().log(LogStatus.FAIL, "Create Container page is not displayed when click on the add icon");
            logger.info("Create Container page is not displayed when click on the add icon");
            takeScreenshot("ContainerCreatePage");
        }
    }

    public void productContainerAscending() {
        verifyProductContainerOrder("ascending");
    }

    public void productContainerDescending() {
        verifyProductContainerOrder("descending");
    }

    public void verifyProductContainerOrder(String order) {
        scrollUpDown("up");
        click(By.cssSelector("#NAME"), "Product Container Name", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td[2]//a"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 10);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 10);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td[2]//a"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Container Names are sorted in ascending Order when click the Product Container header for one time");
                logger.info("Product Container Names are sorted in ascending Order when click the Product Container header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Product Container Names are not sorted in ascending Order when click the Product Container header for one time");
                logger.info("Product Container Names are not sorted in ascending Order when click the Product Container header for one time");
                takeScreenshot("ProductContainerNameSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Product Container Names are sorted in descending Order when click the Product Container header for two times");
                logger.info("Product Container Names are sorted in descending Order when click the Product Container header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Product Container Names are not sorted in descending Order when click the Product Container header for two times");
                logger.info("Product Container Names are not sorted in descending Order when click the Product Container header for two times");
                takeScreenshot("ProductContainerNameSort");
            }
        }
    }

    public void containerStatusAscending() {
        verifyProductStatusOrder("ascending");
    }

    public void containerStatusDescending() {
        verifyProductStatusOrder("descending");
    }

    public void verifyProductStatusOrder(String order) {
        scrollUpDown("up");
        click(By.cssSelector("span#STATUS"), "Container Status Header", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//select[contains(@class,'UpdateStatusConatiner')]//option[@selected]"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 10);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//select[contains(@class,'UpdateStatusConatiner')]//option[@selected]"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Container Status are sorted in ascending Order when click the Container Status header for one time");
                logger.info("Container Status are sorted in ascending Order when click the Container Status header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Container Status are not sorted in ascending Order when click the Container Status header for one time");
                logger.info("Container Status are not sorted in ascending Order when click the Container Status header for one time");
                takeScreenshot("ContainerStatusSort");
            }
        }
        if (order.equals("descending")) {
            List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
            expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(expectedSortedList);
            if (expectedSortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Container Status are sorted in descending Order when click the Container Status header for two times");
                logger.info("Container Status are sorted in descending Order when click the Container Status header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Container Status are not sorted in descending Order when click the Container Status header for two times");
                logger.info("Container Status are not sorted in descending Order when click the Container Status header for two times");
                takeScreenshot("ContainerStatusSort");
            }
        }
    }

    public void containerDateAscending() {
        verifyProductDateOrder("ascending");
    }

    public void containerDateDescending() {
        verifyProductDateOrder("descending");
    }

    public List<String> dateSort(List<String> expectedSortedList) {
        Collections.sort(expectedSortedList, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("M/dd/yyyy hh:mm:ss a");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return expectedSortedList;
    }

    public void verifyProductDateOrder(String order) {
        scrollUpDown("up");
        click(By.cssSelector("#CREATED_AT"), "Container Created AT Header", 20);
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<String> actualSortedArray = new ArrayList<>();
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td[7]//span"), 30);
        for (WebElement element : elements) {
            actualSortedArray.add(element.getAttribute("title"));
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 10);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td[7]//span"), 30);
                for (WebElement element : elements) {
                    actualSortedArray.add(element.getAttribute("title"));
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
        if (order.equals("ascending")) {
            List<String> duplicateList = new ArrayList<>(actualSortedArray);
            List<String> sortedList = dateSort(duplicateList);
            if (sortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Container Created Date are sorted in ascending Order when click the Container Created Date header for one time");
                logger.info("Container Created Date are sorted in ascending Order when click the Container Created Date header for one time");
            } else {
                getTest().log(LogStatus.FAIL, "Container Created Date are not sorted in ascending Order when click the Container Created Date header for one time");
                logger.info("Container Created Date are not sorted in ascending Order when click the Container Created Date header for one time");
                takeScreenshot("ContainerDateSort");
            }
        }
        if (order.equals("descending")) {
            List<String> duplicateList = new ArrayList<>(actualSortedArray);
            List<String> sortedList = dateSort(duplicateList);
            Collections.reverse(sortedList);
            if (sortedList.equals(actualSortedArray)) {
                getTest().log(LogStatus.PASS, "Container Created Date are sorted in descending Order when click the Container Status Created Date for two times");
                logger.info("Container Created Date are sorted in descending Order when click the Container Created Date header for two times");
            } else {
                getTest().log(LogStatus.FAIL, "Container Created Date are not sorted in descending Order when click the Container Created Date header for two times");
                logger.info("Container Created Date are not sorted in descending Order when click the Container Created Date header for two times");
                takeScreenshot("ContainerDateSort");
            }
        }
    }

    public void createMultipleContainer() {
        String temp;
        for (int i = 1; i <= 4; i++) {
            clickAddProductContainerButton();
            selectValueWithIndex(By.xpath("//select[@id='AssetTypeId']"), 1, "Product Type", 20);
            click(By.xpath("//div[@id='divmultilevelselectLocation']/div/div"), "Location Dropdown", 20);
            click(By.xpath("//div[@id='divmultilevelselectLocation']/div/ul//li/a"), "Location Value", 20);
            findElementPresence(By.xpath("//select[@id='ParentRelatedInfoId']//option[2]"), 30);
            selectValueWithIndex(By.xpath("//select[@id='ParentRelatedInfoId']"), 1, "Container Name", 20);
            click(By.xpath("//ul[contains(@class,'multiselect-container')]/parent::div"), "Product Name Dropdown", 20);
            List<WebElement> productNames = findMultipleElement(By.xpath("//ul[contains(@class,'multiselect-container')]/parent::div//li//a//label"), 30);
            for (WebElement ele : productNames) {
                temp = ele.getText().trim();
                if (!temp.equalsIgnoreCase("Select")) {
                    ele.click();
                    break;
                }
            }
            selectValueWithIndex(By.xpath("//select[contains(@class,'CompartmentId')]"), 1, "Compartment", 20);
            saveUpdateContainer();
            handleContainerCreateSuccess();
        }
    }

    public void selectSingleData() {
        deletedContainers = clickCheckBox(1);
    }

    public void selectMultiData() {
        deletedContainers = clickCheckBox(2);
    }

    public List<String> clickCheckBox(int count) {
        waitForLoader(20);
        List<String> containerToDelete = new ArrayList<>();
        List<WebElement> checkBoxList = findMultipleElement(By.xpath("//input[@name='DeleteInputs']//parent::div"), 30);
        if (checkBoxList.size() != 0) {
            int iteration = 0;
            for (WebElement ele : checkBoxList) {
                String checkBox = ele.findElement(By.xpath("//input")).getAttribute("disabled");
                if (checkBox == null) {
                    iteration++;
                    click(By.xpath("//table[@id='tblAssetGroup']//tbody//tr[" + iteration + "]//td/div[contains(@class,'checkbox')]"), "Check box for record - " + iteration, 20);
                    containerToDelete.add(getText(By.xpath("//table[@id='tblAssetGroup']//tr[" + iteration + "]//a"), 30).trim());
                    if (iteration == count) {
                        break;
                    }
                }
            }
        } else {
            getTest().log(LogStatus.PASS, "There is no data to check the check box");
            logger.info("There is no data to check the check box");
        }
        return containerToDelete;
    }

    public void deleteButton() {
        ProductListingPage productListing = new ProductListingPage(driver);
        productListing.verifyDeleteButton();
        productListing.clickDeleteIcon();
    }

    public void handleDeleteSuccessMsg() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Product Container has been successfully deleted.']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when status is changed");
            logger.info("Success message is not displayed when status is changed");
            takeScreenshot("StatusSuccessMsg");
        }
    }

    public void verifyDeletedContainer() {
        List<WebElement> containerList = findMultipleElement(By.xpath("//table[@id='tblAssetGroup']//tr//a"), 30);
        for (String str : deletedContainers) {
            int iteration = 0;
            for (WebElement ele : containerList) {
                iteration++;
                String actualText = ele.getText().trim();
                if (!actualText.equals(str) && iteration == containerList.size()) {
                    getTest().log(LogStatus.PASS, "Deleted Container " + str + " is not displayed as expected in the list");
                    logger.info("Deleted Container " + str + " is not displayed as expected in the list");
                    break;
                } else if (iteration == containerList.size()) {
                    getTest().log(LogStatus.FAIL, "Deleted Container " + str + " is not removed from the list");
                    logger.info("Deleted Container " + str + " is not removed from the list");
                    takeScreenshot("DeletedContainer");
                }
            }
        }
    }

    public void changeStatus() {
        scrollToWebelement(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td//select//option[@selected]"), "Status");
        String currentStatus = getText(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td//select//option[@selected]"), 30).trim();
        if (currentStatus.equals("Active")) {
            clickInActiveStatus();
            statusConfirmation();
            handleStatusChangeSuccess();
            verifyStatusChange("Inactive");
        } else {
            clickActiveStatus();
            statusConfirmation();
            handleStatusChangeSuccess();
            verifyStatusChange("Active");
        }
    }

    public void clickActiveStatus() {
        click(By.xpath("//a[@id='ancActInact']//span[text()=' Active']"), "Active Status", 20);
    }

    public void clickInActiveStatus() {
        click(By.xpath("//a[@id='ancActInact']//span[text()='  Inactive']"), "Inactive Status", 20);
    }

    public void statusConfirmation() {
        WebElement confirmation = findElementVisibility(By.xpath("//div[contains(@class,'alert-warning') and contains(text(),'status')]"), 30);
        if (confirmation != null) {
            click(By.xpath("//div[contains(@class,'modal-confirm-footer')]//button[@title='OK']"), "Status Confirmation", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Status change confirmation is not displayed");
            logger.info("Status change confirmation is not displayed");
            takeScreenshot("StatusConfirmation");
        }
    }

    public void handleStatusChangeSuccess() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Status has been successfully updated.']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when status is changed");
            logger.info("Success message is not displayed when status is changed");
            takeScreenshot("StatusSuccessMsg");
        }
        waitForLoader(20);
    }

    public void verifyStatusChange(String expectedStatus) {
        waitForLoader(20);
        scrollToWebelement(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td//select//option[@selected]"), "Status");
        String actualStatus = getText(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td//select//option[@selected]"), 30).trim();
        if (actualStatus.equals(expectedStatus)) {
            getTest().log(LogStatus.PASS, "Status is changed for the record as " + expectedStatus);
            logger.info("Status is changed for the record as " + expectedStatus);
        } else {
            getTest().log(LogStatus.FAIL, "Status is not changed for the record as " + expectedStatus);
            logger.info("Status is not changed for the record as " + expectedStatus);
            takeScreenshot("StatusChange");
        }
    }

    public void navigateToEditPage() {
        staticWait(1000);
        beforeEditProduct = getText(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td[4]//span"), 30).trim();
        click(By.xpath("//table[@id='tblAssetGroup']//tr[1]//a"), "Container", 30);
    }

    public void changeProduct() {
        click(By.xpath("//select[@name='ddlAssetCatalogId']//parent::span"), "Product Dropdown", 20);
        List<WebElement> products = findMultipleElement(By.xpath("//select[@name='ddlAssetCatalogId']//parent::span//ul//li//label"), 30);
        for (WebElement ele : products) {
            if (!ele.getText().equals(beforeEditProduct)) {
                afterEditProduct = ele.getText();
                ele.click();
                break;
            }
        }
    }

    public void saveUpdateContainer() {
        click(By.cssSelector("a#btnSubmit"), "Save", 20);
    }

    public void handleContainerUpdateSuccess() {
        WebElement successMsg = findElementVisibility(By.xpath("//span[text()='Product Container has been successfully updated.']"), 30);
        if (successMsg != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when click save button after update the container");
            logger.info("Success message is not displayed when click save button after update the container");
            takeScreenshot("ContainerUpdateSuccessMsg");
        }
    }

    public void verifyContainerUpdate() {
        afterEditProduct = getText(By.xpath("//table[@id='tblAssetGroup']//tr[1]//td[4]//span"), 30).trim();
        if (!beforeEditProduct.equals(afterEditProduct)) {
            getTest().log(LogStatus.PASS, "Container group is successfully updated by changing the product");
            logger.info("Container group is successfully updated by changing the product");
        } else {
            getTest().log(LogStatus.FAIL, "Container group is not updated by changing the product");
            logger.info("Container group is not updated by changing the product");
            takeScreenshot("ContainerUpdate");
        }
    }

    public void getContainerProductType() {
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        waitForLoader(20);
        List<WebElement> containerStatus = findMultipleElement(By.xpath("//table[@id='tblAsset']//tr//td[6]"), 30);
        int iteration = 0;
        for (WebElement ele : containerStatus) {
            iteration++;
            if (ele.getText().equalsIgnoreCase("Yes")) {
                WebElement txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//span"), 1);
                if (txtBoxStatus == null) {
                    txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[7]//select//option[@selected]"), 1);
                }
                if (txtBoxStatus.getText().equalsIgnoreCase("Active")) {
                    String actualName = getText(By.xpath("//table[@id='tblAsset']//tr[" + iteration + "]//td[4]//a"), 5);
                    containerProductType.add(actualName);
                }
            }
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 10);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                List<WebElement> containerStatusOne = findMultipleElement(By.xpath("//table[@id='tblAsset']//tr//td[6]"), 30);
                int iterationOne = 0;
                for (WebElement ele : containerStatusOne) {
                    iterationOne++;
                    if (ele.getText().equalsIgnoreCase("Yes")) {
                        WebElement txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iterationOne + "]//td[7]//span"), 1);
                        if (txtBoxStatus == null) {
                            txtBoxStatus = findElementVisibility(By.xpath("//table[@id='tblAsset']//tr[" + iterationOne + "]//td[7]//select//option[@selected]"), 1);
                        }
                        if (txtBoxStatus.getText().equalsIgnoreCase("Active")) {
                            String actualName = getText(By.xpath("//table[@id='tblAsset']//tr[" + iterationOne + "]//td[4]//a"), 5);
                            containerProductType.add(actualName);
                        }
                    }
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
    }

    public void verifyProductDropDown() {
        List<WebElement> dropDownOptions = findMultipleElement(By.xpath("//select[@id='AssetTypeId']//option"), 30);
        for (String expectedProduct : containerProductType) {
            int iteration = 0;
            for (WebElement actualProduct : dropDownOptions) {
                iteration++;
                if (expectedProduct.equalsIgnoreCase(actualProduct.getText().trim())) {
                    getTest().log(LogStatus.PASS, "The Product \"" + expectedProduct + "\" has container and displayed in the product dropdown");
                    logger.info("The Product \"" + expectedProduct + "\" has container and displayed in the product dropdown");
                    break;
                } else if (iteration == dropDownOptions.size()) {
                    getTest().log(LogStatus.FAIL, "The Product \"" + expectedProduct + "\" has container but not displayed in the product dropdown");
                    logger.info("The Product \"" + expectedProduct + "\" has container but not displayed in the product dropdown");
                }
            }
        }
    }

    public void verifyLocationDropDown() {
        deployProduct.clickLocationDropdown();
        deployProduct.verifyLocations("parent");
        deployProduct.verifyLocations("child");
    }

    public void deployProductForContainer() {
        addProduct.clickAddNewButton();
        boolean toggle = addProduct.getTermsAndCondition(containerProductType.get(0));
        addProduct.clickFullMenuDropDown();
        addProduct.clickAssetManagement();
        addProduct.clickManageProduct();
        addProduct.clickAddNewButton();
        selectValueWithText(By.cssSelector("div>select#AssetTypeId"), containerProductType.get(0), "Product Type", 20);
        if (toggle) {
            click(By.xpath("//span[contains(text(),'term and conditions')]//parent::div//button"), "Terms and Condition close", 40);
        }
        Select select = new Select(driver.findElement(By.cssSelector("div>select#AssetTypeId")));
        WebElement option = select.getFirstSelectedOption();
        productTypeForContainer = option.getText();
        addProduct.enterItemName();
        createdProductName = getAtribute(By.cssSelector("div>input#Name"), "value", 20);
        addProduct.selectBarCodeType();
        addProduct.clickTheCheckBox();
        addProduct.clickNextButton();
        staticWait(3000);
        deployProduct.clickAddDeployButton();
        deployProduct.clickLocationDropdown();
        deployProduct.selectLocationValueFromDropdown("");
        productLocationForContainer = getText(By.xpath("//span[contains(@class,'CompantLocationSelectselected')]"), 30);
        deployProduct.enterQuantity("10");
        deployProduct.enterUnitPrice("1");
        deployProduct.enterProductCost("1");
        deployProduct.enterSalvageCost("10");
        deployProduct.clickAddListButton();
        deployProduct.clickSaveButton();
        deployProduct.handleSuccessPopup();
        deployProduct.clickNextButton();
        staticWait(4000);
        List<WebElement> productNameLocators = findMultipleElement(By.xpath("//table[@id='tblRelatedInfoListing']//tbody//tr//a[@class='editinfo']"), 30);
        for (WebElement ele : productNameLocators) {
            try {
                deployedProductNames.add(ele.getText().trim());
            } catch (Exception e) {
                getTest().log(LogStatus.FAIL, "Error occurred - " + e);
                logger.info("Error occurred - " + e);
                takeScreenshot("getText");
            }
        }
        deployProduct.clickNextButton();
        attachmentsPage.enterAttachmentName();
        if (toggle) {
            attachmentsPage.selectTermsAndConditionsYes();
        }
        attachmentsPage.uploadAttachment();
        attachmentsPage.clickSaveButton();
        deployProduct.handleSuccessPopup();
    }

    public void selectProductType() {
        selectValueWithText(By.cssSelector("select#AssetTypeId"), productTypeForContainer, "Product Type", 30);
    }

    public void selectLocation() {
        click(By.xpath("//div[contains(@class,'locationddl')]//div[@type='button']"), "Product Location Dropdown", 20);
        click(By.xpath("//ul[@id='CompantLocationSelect']//span[text()='" + productLocationForContainer + "']"), "Product Location Value", 20);
    }

    public void selectContainer() {
        findElementPresence(By.xpath("//select[@id='ParentRelatedInfoId']//option[text()='" + deployedProductNames.get(0) + "']"), 60);
        selectValueWithIndex(By.cssSelector("select#ParentRelatedInfoId"), 1, "Container Dropdown", 20);
        Select select = new Select(driver.findElement(By.cssSelector("select#ParentRelatedInfoId")));
        WebElement option = select.getFirstSelectedOption();
        selectedContainer = option.getText();
    }

    public void selectProductName() {
        String value;
        click(By.xpath("//table[@id='tbContainer']//tbody//tr[1]//td[1]/span[@class='multiselect-native-select']"), "Product name dropdown", 20);
        List<WebElement> productNameList = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr[1]//td[1]//label"), 30);
        for (WebElement ele : productNameList) {
            value = ele.getText().trim();
            if (!value.equals("Select")) {
                ele.click();
                selectedProduct = value;
                break;
            }
        }
    }

    public void selectDupProductName() {
        String value;
        click(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//td[1]/span[@class='multiselect-native-select']"), "Product name dropdown", 20);
        List<WebElement> productNameList = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//td[1]//label"), 30);
        for (WebElement ele : productNameList) {
            value = ele.getText().trim();
            if (!value.equals("Select") && value.equals(selectedProduct)) {
                ele.click();
                break;
            }
        }
    }

    public void selectDiffProductName() {
        String value;
        String selectedValue = getText(By.xpath("//table[@id='tbContainer']//tbody//tr[1]//td[1]/span[@class='multiselect-native-select']//button/span"), 30);
        click(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//td[1]/span[@class='multiselect-native-select']"), "Product Dropdown", 20);
        List<WebElement> productNameList = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//td[1]//label"), 30);
        for (WebElement ele : productNameList) {
            value = ele.getText();
            if (!value.equals("Select") && !ele.getText().trim().equals(selectedValue)) {
                ele.click();
                break;
            }
        }
    }

    public void selectCompartmentOne() {
        selectValueWithIndex(By.xpath("//table[@id='tbContainer']//tbody//tr[1]//select[contains(@id,'CompartmentId')]"), 1, "Product container", 20);
    }

    public void selectCompartmentTwo() {
        selectValueWithIndex(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//select[contains(@id,'CompartmentId')]"), 1, "Product container", 20);
    }

    public void clickAddRow() {
        click(By.cssSelector("#ancAddRow"), "Add Row", 20);
    }

    public void verifyAddButtonFunction() {
        int containerGroupCount = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr"), 30).size();
        if (containerGroupCount > 1) {
            getTest().log(LogStatus.PASS, "New Row added to the container group list as expected when click add row button");
            logger.info("New Row added to the container group list as expected when click add row button");
        } else {
            getTest().log(LogStatus.FAIL, "New Row is not added to the container group list when click add row button");
            logger.info("New Row is not added to the container group list when click add row button");
            takeScreenshot("AddRow");
        }
    }

    public void verifyDuplicateProdErrorMessage() {
        String actualErrorMessage = getText(By.xpath("//div[@id='notifymessage']//div/span"), 30);
        if (actualErrorMessage.equals(duplicateProdErr)) {
            getTest().log(LogStatus.PASS, "Error message is displayed as expected when add container with duplicate product");
            logger.info("Error message is displayed as expected when add container with duplicate product");
            click(By.xpath("//div[@id='notifymessage']//div/button"), "Close Error", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Error message is not displayed when add container with duplicate product");
            logger.info("Error message is not displayed when add container with duplicate product");
            takeScreenshot("DuplicateProdError");
        }
    }

    public void verifyProductNames() {
        List<String> actualProductNameList = new ArrayList<>();
        List<WebElement> actualProductsList = findMultipleElement(By.xpath("//select[@id='ParentRelatedInfoId']//option"), 30);
        for (WebElement ele : actualProductsList) {
            actualProductNameList.add(ele.getText());
        }

        for (String expectedProduct : deployedProductNames) {
            int iteration = 0;
            for (String actualProduct : actualProductNameList) {
                iteration++;
                if (expectedProduct.equals(actualProduct)) {
                    getTest().log(LogStatus.PASS, "The product \"" + actualProduct + "\" is displayed as expected when selecting the product type as " + productTypeForContainer + " and location as " + productLocationForContainer);
                    logger.info("The product \"" + actualProduct + "\" is displayed as expected when selecting the product type as " + productTypeForContainer + " and location as " + productLocationForContainer);
                    break;
                } else if (iteration == actualProductNameList.size()) {
                    getTest().log(LogStatus.FAIL, "The product \"" + actualProduct + "\" is not displayed when selecting the product type as " + productTypeForContainer + " and location as " + productLocationForContainer);
                    logger.info("The product \"" + actualProduct + "\" is not displayed when selecting the product type as " + productTypeForContainer + " and location as " + productLocationForContainer);
                    takeScreenshot("TypeLocationBaseProduct");
                }
            }
        }
    }

    public void getComportmentInProductType() {
        click(By.xpath("//span[text()='Yes']//parent::td//parent::tr//a[@class='update ancEditAssetType']"), "Product Type Name", 20);
        productTypeForContainer = getAtribute(By.cssSelector("div>input#AssetType"), "value", 20);
        List<WebElement> productContainerLocators = findMultipleElement(By.xpath("//table[@id='tblContainerList']//tbody//tr//td//input[contains(@id,'container_name')]"), 30);
        for (WebElement ele : productContainerLocators) {
            expectedCompartmentList.add(ele.getAttribute("value"));
        }
    }

    public void verifyComportmentList() {
        String actualCompartment;
        List<WebElement> actualCompartmentLocators = findMultipleElement(By.xpath("//select[contains(@id,'CompartmentId')]//option"), 30);
        for (String expectedCompartment : expectedCompartmentList) {
            int iteration = 0;
            for (WebElement actualCompartmentLocator : actualCompartmentLocators) {
                iteration++;
                actualCompartment = actualCompartmentLocator.getText().trim();
                if (expectedCompartment.equalsIgnoreCase(actualCompartment)) {
                    getTest().log(LogStatus.PASS, "The compartment \"" + expectedCompartment + "\" is displayed in product type page as well as compartment dropdown as expected");
                    logger.info("The compartment \"" + expectedCompartment + "\" is displayed in product type page as well as compartment dropdown as expected");
                    break;
                } else if (iteration == actualCompartmentLocators.size()) {
                    getTest().log(LogStatus.FAIL, "The compartment \"" + expectedCompartment + "\" is not displayed in the compartment dropdown");
                    logger.info("The compartment \"" + expectedCompartment + "\" is not displayed in the compartment dropdown");
                    takeScreenshot("Compartment");
                }
            }
        }
    }

    public void clickContainerDeleteIcon() {
        click(By.xpath("//table[@id='tbContainer']//tbody//tr[2]//a[@data-original-title='Delete']"), "Delete Icon", 20);
    }

    public void confirmDeleteContainer() {
        WebElement confirmMsg = findElementVisibility(By.xpath("//div[contains(@class,'alert-warning')]"), 30);
        if (confirmMsg != null) {
            click(By.xpath("//div[contains(@class,'modal-confirm-footer')]//button[@title='OK']"), "Confirm Ok", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Container delete confirmation is not displayed");
            logger.info("Container delete confirmation is not displayed");
            takeScreenshot("DeleteConfirmation");
        }
    }

    public void verifyDeletedContainerRow() {
        int afterDelete;
        int beforeDelete;
        beforeDelete = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr"), 30).size();
        clickContainerDeleteIcon();
        confirmDeleteContainer();
        afterDelete = findMultipleElement(By.xpath("//table[@id='tbContainer']//tbody//tr"), 30).size();
        if (afterDelete < beforeDelete) {
            getTest().log(LogStatus.PASS, "Deleted container row is removed from the list as expected");
            logger.info("Deleted container row is removed from the list as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Deleted container row is removed from the list");
            logger.info("Deleted container row is removed from the list");
            takeScreenshot("DeleteContainer");
        }
    }

    public void handleContainerCreateSuccess() {
        WebElement successMessage = findElementVisibility(By.xpath("//span[text()='Product Container has been successfully added.']"), 60);
        if (successMessage != null) {
            click(By.cssSelector("button#closenotifymessage"), "Success message close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message is not displayed when click save button");
            logger.info("Success message is not displayed when click save button");
            takeScreenshot("StatusSuccessMsg");
        }
    }

    public void verifyCreatedContainerGroup() {
        WebElement actualContainerName = findElementVisibility(By.xpath("//table[@id='tblAssetGroup']//tbody//tr//td//a[normalize-space(text())='" + selectedContainer + "']"), 30);
        if (actualContainerName != null) {
            getTest().log(LogStatus.PASS, "Created container group is displayed in the list as expected");
            logger.info("Created container group is displayed in the list as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Created container group is not displayed in the list");
            logger.info("Created container group is not displayed in the list");
            takeScreenshot("CreateContainerGroup");
        }
    }

    public void cancelContainerGroup() {
        click(By.xpath("//a[@id='gobacktolistscreen']"), "Cancel", 20);
    }

    public void getLocationForProductNameVerification() {
        int searchCount;
        click(By.xpath("//div[@id='accordionEx']//a[@class='collapsed']/span[text()='Location']"), "Location Filter Option", 20);
        click(By.xpath("//div[contains(@class,'CompantLocationdd-container')]//div"), "Location Search Dropdown", 20);
        List<WebElement> locationList = findMultipleElement(By.xpath("//a[contains(@class,'CompantLocationdd-option')]"), 20);
        for (WebElement ele : locationList) {
            ele.click();
            productLocationForContainer = getText(By.xpath("//span[contains(@class,'CompantLocationselected')]"), 20);
            clickSearchButton();
            searchCount = findMultipleElement(By.xpath("//table[@id='tablelistingdata']//tbody//tr"), 30).size();
            if (searchCount <= 1) {
                click(By.xpath("//div[contains(@class,'CompantLocationdd-container')]//div"), "Location Search Dropdown", 20);
            } else {
                break;
            }
        }

    }

    public void getTypeLocationBasedProducts() {
        selectValueWithValue(By.cssSelector("#pageSize"), "100", "PageSize", 20);
        List<WebElement> productsToValidateLocators = findMultipleElement(By.xpath("//span[text()='Active']//parent::a//parent::td//parent::tr//a[@id='ancEditAssetType']//span"), 30);
        productTypeForContainer = getText(By.xpath("//table[@id='tablelistingdata']//tbody//tr[2]//td[3]//span"), 20);
        for (WebElement ele : productsToValidateLocators) {
            productsNameToValidate.add(ele.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                productsToValidateLocators = findMultipleElement(By.xpath("//span[@title='" + productTypeForContainer + "']//parent::td//parent::tr//td//a[@id='ancEditAssetType']//span"), 30);
                for (WebElement ele : productsToValidateLocators) {
                    productsNameToValidate.add(ele.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 5);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 5);
            }
        }
    }

    public void verifyLocationStatusBasedProducts() {
        String actualProduct;
        click(By.xpath("//select[contains(@class,'ddlAssetCatalogId')]//parent::span"), "Product Name Dropdown", 20);
        List<WebElement> actualBasedProductLocators = findMultipleElement(By.xpath("//select[contains(@class,'ddlAssetCatalogId')]//parent::span//a//label"), 30);
        for (String expectedBasedProduct : productsNameToValidate) {
            int iteration = 0;
            for (WebElement actualBasedProductLocator : actualBasedProductLocators) {
                iteration++;
                actualProduct = actualBasedProductLocator.getText().trim();
                if (actualProduct.equals(expectedBasedProduct)) {
                    getTest().log(LogStatus.PASS, "The Product \"" + actualProduct + "\" is displayed in the product name dropdown as expected when select deployed location as " + productLocationForContainer);
                    logger.info("The Product \"" + actualProduct + "\" is displayed in the product name dropdown as expected when select deployed location as " + productLocationForContainer);
                    break;
                } else if (iteration == actualBasedProductLocators.size()) {
                    getTest().log(LogStatus.FAIL, "The Product \"" + actualProduct + "\" is not displayed in the product name dropdown when select deployed location as " + productLocationForContainer);
                    logger.info("The Product \"" + actualProduct + "\" is not displayed in the product name dropdown when select deployed location as " + productLocationForContainer);
                    takeScreenshot("LocationBasedProducts");
                }
            }
        }
    }
}
