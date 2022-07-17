package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebBasePage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.productToAssign;

public class CheckOutRequestPage extends WebBasePage {

    ProductListingPage productListing;
    MyProductPage myProduct;
    String productToBeSearch;
    int beforeSearchCount;
    int beforeClearSearch;
    public CheckOutRequestPage(WebDriver driver)
    {
        super(driver,"CheckOut Request page");
        this.productListing = new ProductListingPage(driver);
        this.myProduct = new MyProductPage(driver);
    }
    public void navigateToAddCheckOutPage()
    {
        click(By.cssSelector("a#ancaddgroupRequisitions"),"Add Checkout request page",20);
    }
    public void navigateToCheckOutRequestList(){
        click(By.cssSelector("a#ancMyCheckOutRequest"),"Checkout request List page",20);
    }
    public void clickAssignedTIllField()
    {
        click(By.cssSelector("div>input#AssignedTill"),"Assigned Till Date",20);
    }
    public void changeEndDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String currentDate=dtf.format(now);
        click(By.xpath("//td[@data-day='"+currentDate+"']"),"Next day",20);
    }
    public void enterCommentForCheckOut()
    {
        myProduct.enterRemark("Automation Testing");
    }
    public void addProductForCheckOut()
    {
        productListing.selectAssignProductLocation();
        productListing.selectAssignProductType();
        productListing.selectAssignProductName();
        productListing.selectAssignProduct();
        productListing.searchAssignProduct();
        productListing.clickTermsAndCondition();
        productListing.addAssignProductToList();
        productListing.saveAssignProduct();
    }
    public void  verifyCreatedCheckOutProduct()
    {
        List<String> availableCheckOutRequest = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr//td[1]/span"), 30);
        for (WebElement element : elements) {
            availableCheckOutRequest.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr//td[1]/span"), 30);
                for (WebElement element : elements) {
                    availableCheckOutRequest.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }

        if(availableCheckOutRequest.contains(productToAssign))
        {
            getTest().log(LogStatus.PASS,"The requested product "+productToAssign+" name is displayed in the check out request list page once the check out request is saved");
            logger.info("The requested product "+productToAssign+" name is displayed in the check out request list page once the check out request is saved");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The requested product "+productToAssign+" name is not displayed in the check out request list page once the check out request is saved");
            logger.info("The requested product "+productToAssign+" name is not displayed in the check out request list page once the check out request is saved");
            takeScreenshot("CheckOutReq");
        }
    }
    public void verifyCheckOutListHeader()
    {
        String actualHeader;
        int iteration = 0;
        String[] expectedHeaders = {"Products","Assigned","Created By","Created At","From","To","Approve/Rejected By","Approve/Rejected At","Reason","Status"};
        List<WebElement> checkOutListHeaders = findMultipleElement(By.xpath("//table[@id='pkgListtable']//th/span"),30);
        for (String expectedHeader : expectedHeaders) {
            for (WebElement ele : checkOutListHeaders) {
                iteration++;
                actualHeader = ele.getText().trim();
                if (expectedHeader.equalsIgnoreCase(actualHeader)) {
                    getTest().log(LogStatus.PASS,"The header name "+expectedHeader+" is displayed in the check out request list page as expected");
                    logger.info("The header name "+expectedHeader+" is displayed in the check out request list page as expected");
                    iteration = 0;
                    break;
                } else if (iteration == checkOutListHeaders.size()) {
                    getTest().log(LogStatus.FAIL,"The header name "+expectedHeader+" is not displayed in the check out request list page");
                    logger.info("The header name "+expectedHeader+" is not displayed in the check out request list page");
                    takeScreenshot("CheckOutListHeader");
                }
            }

        }
    }
    public void verifyBreadCrumbPresenceInCheckOutList()
    {
        verifyBreadCrumbPresence("Add Checkout List Page");
    }
    public void verifyBreadCrumbPresence(String pageName)
    {
        WebElement breadCrumb = findElementVisibility(By.xpath("//div[@id='SiteMapLink']/ol"),30);
        if(breadCrumb != null)
        {
            getTest().log(LogStatus.PASS,"The bread crumb is displayed in the "+pageName);
            logger.info("The bread crumb is displayed in the "+pageName);
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The bread crumb is not displayed in the "+pageName);
            logger.info("The bread crumb is not displayed in the "+pageName);
            takeScreenshot("CheckOutPageBreadCrumb");
        }
    }
    public void clickProductBreadCrumbProductMenu()
    {
        click(By.xpath("//div[@id='SiteMapLink']//a[text()='Product']"),"BreadCrumb Product menu",20);
    }
    public void verifyProductPage()
    {
        verifyProductPage("Add Checkout list page");
    }
    public void verifyProductPage(String pageName)
    {
        WebElement productPageElement = findElementVisibility(By.xpath("//div[@class='theme-primary partition']/span[text()='Product List']"),30);
        if(productPageElement != null) {
            getTest().log(LogStatus.PASS,"Product List page is displayed as expected when click product menu in the "+pageName+"'s breadcrumb");
            logger.info("Product List page is displayed as expected when click product menu in the "+pageName+"'s breadcrumb");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Product List page is not displayed when click product menu in the "+pageName+"'s breadcrumb");
            logger.info("Product List page is not displayed when click product menu in the "+pageName+"'s breadcrumb");
            takeScreenshot("BreadCrumbProductPage");
        }
    }
    public void clickHomeBreadCrumbProductMenu()
    {
        click(By.xpath("//div[@id='SiteMapLink']//a[@class='home']"),"Breadcrumb Home",20);
    }
    public void verifyHomePage()
    {
        verifyHomePage("Add Checkout List Page");
    }
    public void verifyHomePage(String pageName)
    {
        waitForLoader(30);
        WebElement homePage = findElementVisibility(By.xpath("//div[@class='row']//span[normalize-space(text())='Dashboard']"), 40);
        if (homePage != null) {
            getTest().log(LogStatus.PASS, "Home page  is displayed when click home menu in the "+pageName+"'s breadcrumb");
            logger.info("Home page  is displayed when click home menu in the "+pageName+"'s breadcrumb");
        } else {
            getTest().log(LogStatus.FAIL, "Home page is not displayed when click home menu in the "+pageName+"'s breadcrumb");
            logger.info("Home page is not displayed when click home menu in the "+pageName+"'s breadcrumb");
            takeScreenshot("HomePage");
        }
    }
    public void clickExpandCollapseIcon()
    {
        scrollUpDown("up");
        click(By.cssSelector("span.ancExpandAllCollapseAll"),"Expand /  Collapse icon",20);
    }
    public void verifyExpandCollapseMenu(String condition)
    {
        staticWait(1000);
        WebElement productTypeSearchBox = findElementVisibility(By.cssSelector("input.searchcustomfilter"),10);
        scrollToWebelement(By.xpath("//span[text()='Unique Name']"),"Unique Name search box");
        WebElement uniqueNameDropdown = findElementVisibility(By.xpath("//button[@title='Select Unique Name']/span"),10);
        boolean conditions = condition.equals("expand")? productTypeSearchBox!=null && uniqueNameDropdown!=null:productTypeSearchBox==null && uniqueNameDropdown==null;
        if (conditions) {
            getTest().log(LogStatus.PASS, "Product Type field and Unique Name field are "+condition+"ed as expected when click expand / collapse icon");
            logger.info("Product Type field and Unique Name field are "+condition+"ed as expected when click expand / collapse icon");
        } else {
            getTest().log(LogStatus.FAIL, "Product Type field and Unique Name field are not "+condition+"ed when click expand / collapse icon");
            logger.info("Product Type field and Unique Name field are not "+condition+"ed when click expand / collapse icon");
            takeScreenshot("ExpandCollapseButton");
        }
    }
    public void verifyExpandMenu()
    {
        verifyExpandCollapseMenu("expand");
    }
    public void verifyCollapseMenu()
    {
        verifyExpandCollapseMenu("collaps");
    }
    public void clickUniqueNameDropdown()
    {
        scrollUpDown("up");
        click(By.xpath("//div[@class='card']//span[text()='Product Name']"),"Product Name dropdown",20);
    }
    public void clickUniqueProduct()
    {
        WebElement productToSearch = findElementVisibility(By.xpath("//table[@id='pkgListtable']//tbody//tr[1]/td[1]/span"),20);
        if(productToSearch != null) {
            productToBeSearch = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[1]/td[1]/span"), 30);
            enter(By.xpath("//div[contains(@class,'customsearchbox')]/input"),productToBeSearch,"Product search field",30);;
            click(By.xpath("//label[normalize-space(text())='" + productToBeSearch + "']"), "Product Unique Name", 20);
            waitForLoader(20);
        }
    }
    public void uniqueNameSearchFunction()
    {
        if(productToBeSearch!= null) {
            clickSearchButton();
            verifySearchedUniqueProduct();
            clickResetButton();
            verifyClearedSearch();
        }
    }
    public void clickSearchButton()
    {
        beforeSearchCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"),30).size();
        productListing.clickSearchButton();
    }
    public  void clickResetButton()
    {
        beforeClearSearch=findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"),30).size();
        productListing.clickResetButton();
    }
    public void verifySearchedUniqueProduct() {
        String actualProductName = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[1]/td[1]/span"), 30);
        if (productToBeSearch.equalsIgnoreCase(actualProductName)) {
            getTest().log(LogStatus.PASS, "Search result for selected product name is displayed accurately");
            logger.info("Search result for selected product name is displayed accurately");
        } else {
            getTest().log(LogStatus.FAIL, "Search result for selected product name is not displayed accurately");
            logger.info("Search result for selected product name is not displayed accurately");
            takeScreenshot("ProductNameSearch");
        }
    }
    public void verifyClearedSearch()
    {
        int afterClearSearch=findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"),30).size();
        if(beforeSearchCount != 1)
        {
            if(beforeClearSearch <= afterClearSearch)
            {
                getTest().log(LogStatus.PASS,"Searched result is cleared as expected when click on the reset button");
                logger.info("Searched result is cleared as expected when click on the reset button");
            }
            else
            {
                getTest().log(LogStatus.FAIL,"Searched result is not cleared when click on the reset button");
                logger.info("Searched result is not cleared when click on the reset button");
                takeScreenshot("CheckOutReset");
            }
        }
    }
    public void verifyCheckOutListPagination()
    {
        String[] defaultPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
        int defaultStartRecordCount = Integer.parseInt(defaultPaginationText[1]);
        int defaultEndCount = Integer.parseInt(defaultPaginationText[3]);
        int totalRecordCount = Integer.parseInt(defaultPaginationText[5]);
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            int recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            String lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='2']"), "Pagination 2", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='pkgListtable']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] secondPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int secondPageStartRecordCount = Integer.parseInt(secondPaginationText[1]);
            int secondPageEndRecordCount = Integer.parseInt(secondPaginationText[3]);
            int pageSize = Integer.parseInt(getText(By.cssSelector("#pageSize>option[selected]"), 30));
            if (secondPageStartRecordCount == defaultEndCount + 1 && secondPageEndRecordCount <= 2 * pageSize) {
                getTest().log(LogStatus.PASS, "Second page is displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is displayed as expected when click on the \"2\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Second page is not displayed as expected when click on the \"2\" pagination button");
                logger.info("Second page is not displayed as expected when click on the \"2\" pagination button");
                takeScreenshot("Pagination2");
            }
            recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link' and text()='1']"), "Pagination 1", 40);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);

            waitForVisibilityOfElement(By.xpath("//a[@class='page-link next' and text()='Next']"), 60);
            recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] nextPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int nextPageStartRecordCount = Integer.parseInt(nextPaginationText[1]);
            if (nextPageStartRecordCount == defaultEndCount + 1) {
                getTest().log(LogStatus.PASS, "Next page is displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is displayed as expected when click on the \"Next\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Next page is not displayed as expected when click on the \"Next\" pagination button");
                logger.info("Next page is not displayed as expected when click on the \"Next\" pagination button");
                takeScreenshot("PaginationNext");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link previous' and text()='Prev']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link previous' and text()='Prev']"), " Pagination Previous", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] previousPaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int previousPageEndCount = Integer.parseInt(previousPaginationText[3]);
            if (previousPageEndCount == nextPageStartRecordCount - 1) {
                getTest().log(LogStatus.PASS, "Previous page is displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is displayed as expected when click on the \"Previous\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Previous page is not displayed as expected when click on the \"Previous\" pagination button");
                logger.info("Previous page is not displayed as expected when click on the \"Previous\" pagination button");
                takeScreenshot("PaginationPrevious");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link last' and text()='Last ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link last' and text()='Last ']"), "Pagination Last", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] lastPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int lastPageEndCount = Integer.parseInt(lastPagePaginationText[3]);
            if (lastPageEndCount == totalRecordCount) {
                getTest().log(LogStatus.PASS, "Last page is displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is displayed as expected when click on the \"Last\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "Last page is not displayed as expected when click on the \"Last\" pagination button");
                logger.info("Last page is not displayed as expected when click on the \"Last\" pagination button");
                takeScreenshot("PaginationLast");
            }
            waitForVisibilityOfElement(By.xpath("//a[@class='page-link  first' and text()='First ']"), 20);
            recordCount = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr"), 15).size();
            lastRecord = getText(By.xpath("//table[@id='pkgListtable']//tbody//tr[" + recordCount + "]//td[1]//span"), 15).trim();
            click(By.xpath("//a[@class='page-link  first' and text()='First ']"), "Pagination First", 30);
            waitForLoader(20);
            waitForElementInVisibility(By.xpath("//table[@id='tablelistingdata']//tbody//tr//td//span[normalize-space(text())='" + lastRecord + "']"), 30);
            String[] firstPagePaginationText = getText(By.xpath("//div[@class='nu-paging']//ul//li//span[contains(@class,'ml')]"), 20).split(" ");
            int firstPageStartRecordCount = Integer.parseInt(firstPagePaginationText[1]);
            if (firstPageStartRecordCount == defaultStartRecordCount) {
                getTest().log(LogStatus.PASS, "First page is displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is displayed as expected when click on the \"First\" pagination button");
            } else {
                getTest().log(LogStatus.FAIL, "First page is not displayed as expected when click on the \"First\" pagination button");
                logger.info("First page is not displayed as expected when click on the \"First\" pagination button");
                takeScreenshot("PaginationFirst");
            }
        } else {
            getTest().log(LogStatus.PASS, "Pagination section is not displayed since the record count is " + totalRecordCount);
            logger.info("Pagination section is not displayed since the record count is " + totalRecordCount);
        }
    }
    public void verifyProductCheckOutReq(String productName)
    {
        List<String> availableCheckOutRequest = new ArrayList<>();
        selectValueWithValue(By.xpath("//select[@id='pageSize']"), "100", "Page Size", 30);
        List<WebElement> elements = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr//td[1]/span"), 30);
        for (WebElement element : elements) {
            availableCheckOutRequest.add(element.getText().trim());
        }
        WebElement paginationNavigator = findElementVisibility(By.xpath("//div[@class='nu-paging']//li//ul"), 15);
        if (paginationNavigator != null) {
            WebElement paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 30);
            while (paginationLast != null) {
                click(By.xpath("//a[@class='page-link next' and text()='Next']"), "Pagination Next", 15);
                waitForLoader(20);
                elements = findMultipleElement(By.xpath("//table[@id='pkgListtable']//tbody//tr//td[1]/span"), 30);
                for (WebElement element : elements) {
                    availableCheckOutRequest.add(element.getText().trim());
                }
                findElementVisibility(By.xpath("//a[@class='page-link last']"), 30);
                paginationLast = findElementClickable(By.xpath("//a[@class='page-link last']"), 10);
            }
        }

        if(availableCheckOutRequest.contains(productName))
        {
            getTest().log(LogStatus.PASS,"The requested product "+productName+" name is displayed in the pending check out list page once the quantity request is saved");
            logger.info("The requested product "+productName+" name is displayed in the pending check out list page once the quantity request is saved");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The requested product "+productName+" name is not displayed in the pending check out list page once the quantity request is saved");
            logger.info("The requested product "+productName+" name is not displayed in the pending check out list page once the quantity request is saved");
            takeScreenshot("ProductInTable");
        }
    }
}
