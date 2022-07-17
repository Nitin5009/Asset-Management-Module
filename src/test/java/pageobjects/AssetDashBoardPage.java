package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import static reporting.ComplexReportFactory.getTest;
import static utils.StaticData.*;

public class AssetDashBoardPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String pattern = "M/D/YYYY";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    DeployProductPage deployProductPage;

    public AssetDashBoardPage(WebDriver driver) {
        super(driver, "Asset DashBoard Page");
        this.driver = driver;
        deployProductPage = new DeployProductPage(driver);
    }

    public void navigateToManageProductMenu() {
        deployProductPage.clickFullMenuDropDown();
        deployProductPage.clickAssetManagement();
    }

    public void navigateToManageProductPage() {
        navigateToManageProductMenu();
        deployProductPage.clickManageProduct();
    }

    public void storeProductDetailsToStaticData() {
        location = productLocationsToAssign.get(0);
        prodType = productTypesToAssign;
        productName = productNamesToAssign.get(0);
        waitForLoader(30);
    }

    public void clickAssetDashBoardMenu() {
        WebElement assetDashBoardMenu = findElementClickable(By.xpath("//ul[@data-p-name='Asset Management ']//li[@class='menuitem']//a[text()='Asset Dashboard']"), 30);
        if (assetDashBoardMenu != null) {
            getTest().log(LogStatus.PASS, "Asset Dashboard option is present as clickable");
            logger.info("Asset Dashboard option is present as clickable");
            assetDashBoardMenu.click();
        } else {
            getTest().log(LogStatus.FAIL, "Asset Dashboard option is not present as clickable");
            logger.info("Asset Dashboard option is not present as clickable");
            takeScreenshot("AssetDashBoardMenu");
        }
    }

    public void verifyMaximumWidgets() {
        int widgetsCount = findMultipleElement(By.xpath("//div[contains(@class,'graph-widget-box')]"), 30).size();
        if (widgetsCount == 6) {
            getTest().log(LogStatus.PASS, "Maximum 6 widgets is displayed as expected");
            logger.info("Maximum 6 widgets is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Maximum of " + widgetsCount + " widgets only displayed in the asset dashboard");
            logger.info("Maximum of " + widgetsCount + " widgets only displayed in the asset dashboard");
            takeScreenshot("AssetDashBoard");
        }
    }

    public void navigateToManageWidgetPopup() {
        staticWait(3000);
        scrollToWebelement(By.cssSelector("a#hlkAddMoreReports"), "Add More Button");
        click(By.cssSelector("a#hlkAddMoreReports"), "Add More Button", 20);
    }

    public void uncheckTheCheckBox(int count) {
        int checkedCount = findMultipleElement(By.xpath("//table[@id='tblReportDashboard']//tbody//tr//td/div/input[@checked]"), 20).size();
        if (checkedCount != 6) {
            enableAllProdCheckBox();
        }
        for (int i = 1; i <= count; i++) {
            click(By.xpath("//table[@id='tblReportDashboard']//tbody//tr[" + i + "]//td[1]//div"), "Check Box of Widget No - " + i, 30);
        }
    }

    public void enableThreeProdCheckBox() {
        uncheckTheCheckBox(3);
    }

    public void enableAllProdCheckBox() {
        click(By.xpath("//table[@id='tblReportDashboard']//thead//th/div"), "Global Check box", 20);
    }

    public void clickSaveButton() {
        click(By.xpath("//button[@class='btn btn-success']"), "Save Button", 20);
    }

    public void verifyManagedWidgetWindows(String action) {
        int expectedWidgetCount;
        String expectedAction;
        if (action.equalsIgnoreCase("add")) {
            expectedWidgetCount = 6;
            expectedAction = "add";
        } else {
            expectedWidgetCount = 3;
            expectedAction = "remove";
        }
        int managedWidgetCount = findMultipleElement(By.xpath("//div[contains(@class,'graph-widget-box')]"), 30).size();
        if (managedWidgetCount == expectedWidgetCount) {
            getTest().log(LogStatus.PASS, "User can able to " + expectedAction + " the widget from the asset dashboard");
            logger.info("User can able to " + expectedAction + " the widget from the asset dashboard");
        } else {
            getTest().log(LogStatus.FAIL, "User can not able to " + expectedAction + " the widget from the asset dashboard");
            logger.info("User can not able to " + expectedAction + " the widget from the asset dashboard");
            takeScreenshot("WidgetManage");
        }
    }

    public void verifyWidgetRemoveFunction() {
        verifyManagedWidgetWindows("remove");
    }

    public void verifyWidgetAddFunction() {
        verifyManagedWidgetWindows("add");
    }

    public void clickZoomInZoomOutIcon() {
        click(By.xpath("//div[@title='Products by Status']//span[@class='icon_expand']"), "Zoom In / Zoom Out Icon of Product By Status", 20);
    }

    public void verifyWidgetPosition(String zoomPosition) {
        String expectedWindowPosition;
        String message;
        if (zoomPosition.equalsIgnoreCase("zoomIn")) {
            expectedWindowPosition = "1";
            message = "zoomed in";
        } else {
            expectedWindowPosition = "0";
            message = "zoomed out";
        }
        String actualWindowPosition = getAtribute(By.xpath("//div[@title='Products by Status']/span[@class='icon_expand']/a"), "zoom", 20);
        if (actualWindowPosition.equals(expectedWindowPosition)) {
            getTest().log(LogStatus.PASS, "The Widget got " + message + " when click on the the Zoom in / Zoom out icon");
            logger.info("The Widget got " + message + " when click on the the Zoom in / Zoom out icon");
        } else {
            getTest().log(LogStatus.FAIL, "The Widget is not " + message + " when click on the the Zoom in / Zoom out icon");
            logger.info("The Widget is not " + message + " when click on the the Zoom in / Zoom out icon");
            takeScreenshot("WidgetZoomOut");
        }
    }

    public void verifyWidgetZoomIn() {
        clickZoomInZoomOutIcon();
        verifyWidgetPosition("zoomIn");
    }

    public void verifyWidgetZoomOut() {
        clickZoomInZoomOutIcon();
        verifyWidgetPosition("zoomOut");
    }

    public void clickFilterMenuIcon(String filterName) {
        String id = "";
        String name = "";
        if (filterName.equalsIgnoreCase("Global")) {
            id = "aDateLable_global";
            name = "Global";
        } else if (filterName.equalsIgnoreCase("ProductByStatus")) {
            id = "aDateLable_StatuswiseAsset";
            name = "Product By Status";
        } else if (filterName.equalsIgnoreCase("ProductByLocation")) {
            id = "aDateLable_LocationwiseAsset";
            name = "Product By Location";
        } else if (filterName.equalsIgnoreCase("PendingByUser")) {
            id = "aDateLable_NotAcceptedAsset";
            name = "Pending By User";
        } else if (filterName.equalsIgnoreCase("PendingByManager")) {
            id = "aDateLable_PendingReturnRequestAsset";
            name = "Pending By Manager";
        } else if (filterName.equalsIgnoreCase("ReturnOverDue")) {
            id = "aDateLable_ReturnDueAsset";
            name = "Product Status By Over Due";
        } else if (filterName.equalsIgnoreCase("InTransit")) {
            id = "aDateLable_InTransitAsset";
            name = "Product By InTransit";
        }
        click(By.xpath("//a[@id='" + id + "']"), name + " Filter ", 20);
    }

    public void scrollToWidget(String widgetName) {
        scrollUpDown("down");
    }

    public void clickMonthOption(String widgetName, String fieldName) {
        String id = "";
        String name = "";
        String fieldId = "";
        if (fieldName.equalsIgnoreCase("ThisMonth")) {
            fieldId = "aMonth";
        } else {
            fieldId = "aLastMonth";
        }
        if (widgetName.equalsIgnoreCase("Global")) {
            id = "DivTimer_global";
            name = "Global";
        } else if (widgetName.equalsIgnoreCase("ProductByStatus")) {
            id = "DivTimer_StatuswiseAsset";
            name = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("ProductByLocation")) {
            id = "DivTimer_LocationwiseAsset";
            name = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("PendingByUser")) {
            id = "DivTimer_NotAcceptedAsset";
            name = "Pending By User";
        } else if (widgetName.equalsIgnoreCase("PendingByManager")) {
            id = "DivTimer_PendingReturnRequestAsset";
            name = "Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("ReturnOverDue")) {
            id = "DivTimer_ReturnDueAsset";
            name = "Product Status By Over Due";
        } else if (widgetName.equalsIgnoreCase("InTransit")) {
            id = "DivTimer_InTransitAsset";
            name = "Product By InTransit";
        }
        click(By.xpath("//div[@id='" + id + "']//a[@id='" + fieldId + "']"), name + " " + fieldId + " Month", 20);
    }

    public void clickWeekOption(String widgetName, String fieldName) {
        String id = "";
        String name = "";
        String fieldId = "";
        if (fieldName.equalsIgnoreCase("ThisWeek")) {
            fieldId = "aWeek";
        } else {
            fieldId = "aLastWeek";
        }
        if (widgetName.equalsIgnoreCase("Global")) {
            id = "DivTimer_global";
            name = "Global";
        } else if (widgetName.equalsIgnoreCase("ProductByStatus")) {
            id = "DivTimer_StatuswiseAsset";
            name = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("ProductByLocation")) {
            id = "DivTimer_LocationwiseAsset";
            name = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("PendingByUser")) {
            id = "DivTimer_NotAcceptedAsset";
            name = "Pending By User";
        } else if (widgetName.equalsIgnoreCase("PendingByManager")) {
            id = "DivTimer_PendingReturnRequestAsset";
            name = "Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("ReturnOverDue")) {
            id = "DivTimer_ReturnDueAsset";
            name = "Product Status By Over Due";
        } else if (widgetName.equalsIgnoreCase("InTransit")) {
            id = "DivTimer_InTransitAsset";
            name = "Product By InTransit";
        }
        click(By.xpath("//div[@id='" + id + "']//a[@id='" + fieldId + "']"), name + " " + fieldId, 20);
    }

    public void clickYearOption(String widgetName) {
        String id = "";
        String name = "";
        String fieldId = "";
        if (widgetName.equalsIgnoreCase("Global")) {
            id = "DivTimer_global";
            name = "Global";
        } else if (widgetName.equalsIgnoreCase("ProductByStatus")) {
            id = "DivTimer_StatuswiseAsset";
            name = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("ProductByLocation")) {
            id = "DivTimer_LocationwiseAsset";
            name = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("PendingByUser")) {
            id = "DivTimer_NotAcceptedAsset";
            name = "Pending By User";
        } else if (widgetName.equalsIgnoreCase("PendingByManager")) {
            id = "DivTimer_PendingReturnRequestAsset";
            name = "Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("ReturnOverDue")) {
            id = "DivTimer_ReturnDueAsset";
            name = "Product Status By Over Due";
        } else if (widgetName.equalsIgnoreCase("InTransit")) {
            id = "DivTimer_InTransitAsset";
            name = "Product By InTransit";
        }
        click(By.xpath("//div[@id='" + id + "']//a[@id='aAll']"), name + " " + fieldId + " Month", 20);
    }

    public void clickGoButton() {
        click(By.xpath("//input[@id='btnsearch_global']"), "Global Go", 20);
    }

    public void clickSpecificDateFiled(String widgetName, String fieldName) {
        String id = "";
        String name = "";
        String fieldId = "";
        String fieldMsg = "";
        if (fieldName.equalsIgnoreCase("FromDate")) {
            fieldId = "txtFromDate_";
            fieldMsg = "From Date";
        } else {
            fieldId = "txtToDate_";
            fieldMsg = "To Date";
        }
        if (widgetName.equalsIgnoreCase("Global")) {
            id = "global";
            name = "Global";
        } else if (widgetName.equalsIgnoreCase("ProductByStatus")) {
            id = "StatuswiseAsset";
            name = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("ProductByLocation")) {
            id = "LocationwiseAsset";
            name = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("PendingByUser")) {
            id = "NotAcceptedAsset";
            name = "Pending By User";
        } else if (widgetName.equalsIgnoreCase("PendingByManager")) {
            id = "PendingReturnRequestAsset";
            name = "Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("ReturnOverDue")) {
            id = "ReturnDueAsset";
            name = "Product Status By Over Due";
        } else if (widgetName.equalsIgnoreCase("InTransit")) {
            id = "InTransitAsset";
            name = "Product By InTransit";
        }
        click(By.xpath("//input[@id='" + fieldId + id + "']"), name + " " + fieldMsg, 20);
    }

    public void verifyTheMenuInWidget(String widgetName) {
        String locationId = "";
        String fieldName = "";
        if (widgetName.equalsIgnoreCase("PendingByManager")) {
            locationId = "aDateLable_PendingReturnRequestAsset";
            fieldName = "Product Acceptance Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("ReturnOverDue")) {
            locationId = "aDateLable_ReturnDueAsset";
            fieldName = "Status by Return due and return over due";
        } else if (widgetName.equalsIgnoreCase("InTransit")) {
            locationId = "aDateLable_InTransitAsset";
            fieldName = "Status by In Transit";
        } else if (widgetName.equalsIgnoreCase("ProductByStatus")) {
            locationId = "aDateLable_StatuswiseAsset";
            fieldName = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("ProductByLocation")) {
            locationId = "aDateLable_LocationwiseAsset";
            fieldName = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("PendingByUser")) {
            locationId = "aDateLable_NotAcceptedAsset";
            fieldName = "Product Acceptance pending by User";
        }
        int iteration = 0;
        String actualName = "";
        List<WebElement> WidgetMenuLocators = findMultipleElement(By.xpath("//a[@id='" + locationId + "']//parent::div//div//table//tr[1]//td//li/a"), 30);
        for (WebElement widgetMenuItem : WidgetMenuLocators) {
            String[] expectedMenuItems = {"Today", "Yesterday", "This Week", "This Year", "Last Week", "This Month", "Last Month"};
            for (int i = 0; i < WidgetMenuLocators.size(); i++) {
                iteration++;
                actualName = widgetMenuItem.getText().trim();
                if (actualName.equalsIgnoreCase(expectedMenuItems[i])) {
                    getTest().log(LogStatus.PASS, expectedMenuItems[i] + " is displayed as expected in the " + fieldName + " widget menu");
                    logger.info(expectedMenuItems[i] + " is displayed as expected in the " + fieldName + " widget menu");
                    iteration = 0;
                    break;
                } else if (iteration == WidgetMenuLocators.size()) {
                    getTest().log(LogStatus.FAIL, expectedMenuItems[i] + " is not displayed in the " + fieldName + " widget menu");
                    logger.info(expectedMenuItems[i] + " is not displayed in the " + fieldName + " widget menu");
                    takeScreenshot("WidgetMenu");
                }
            }
        }
    }

    public void verifyStartAndToDate(String widgetName, String dayMonthYear) {
        String startDateLocationId = "";
        String toDateLocationId = "";
        String fieldName = "";
        String expectedStartDate = "";
        String expectedToDate = "";
        if (widgetName.equalsIgnoreCase("pendingByManager")) {
            startDateLocationId = "txtFromDate_PendingReturnRequestAsset";
            toDateLocationId = "txtToDate_PendingReturnRequestAsset";
            fieldName = "Product Acceptance Pending By Manager";
        } else if (widgetName.equalsIgnoreCase("returnOverDue")) {
            startDateLocationId = "txtFromDate_ReturnDueAsset";
            toDateLocationId = "txtToDate_ReturnDueAsset";
            fieldName = "Status by Return due and return over due";
        } else if (widgetName.equalsIgnoreCase("inTransit")) {
            startDateLocationId = "txtFromDate_InTransitAsset";
            toDateLocationId = "txtToDate_InTransitAsset";
            fieldName = "Status by In Transit";
        } else if (widgetName.equalsIgnoreCase("productByStatus")) {
            startDateLocationId = "txtFromDate_StatuswiseAsset";
            toDateLocationId = "txtToDate_StatuswiseAsset";
            fieldName = "Product By Status";
        } else if (widgetName.equalsIgnoreCase("productByLocation")) {
            startDateLocationId = "txtFromDate_LocationwiseAsset";
            toDateLocationId = "txtToDate_LocationwiseAsset";
            fieldName = "Product By Location";
        } else if (widgetName.equalsIgnoreCase("pendingByUser")) {
            startDateLocationId = "txtFromDate_NotAcceptedAsset";
            toDateLocationId = "txtToDate_NotAcceptedAsset";
            fieldName = "Product Acceptance pending by User";
        } else if (widgetName.equalsIgnoreCase("Global")) {
            startDateLocationId = "txtFromDate_global";
            toDateLocationId = "txtToDate_global";
            fieldName = "Global Menu";
        }
        String actualStartDate = getAtribute(By.xpath("//input[@id='" + startDateLocationId + "']"), "value", 30);
        String actualToDate = getAtribute(By.xpath("//input[@id='" + toDateLocationId + "']"), "value", 30);
        LocalDate todayDate = LocalDate.now();
        if (dayMonthYear.contains("Week")) {
            String startDate = "";
            String endDate = "";
            LocalDate monday = todayDate.with(previousOrSame(MONDAY));
            LocalDate sunday = todayDate.with(nextOrSame(SUNDAY));
            if (dayMonthYear.equalsIgnoreCase("lastWeek")) {
                startDate = String.valueOf(monday.plusDays(-7));
                endDate = String.valueOf(sunday.plusDays(-7));
            } else {
                startDate = String.valueOf(monday);
                endDate = String.valueOf(sunday);
            }
            String[] fullStartDate = startDate.split("-");
            expectedStartDate = fullStartDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[0].replaceFirst("^0+(?!$)", "");
            String[] fullEndDate = endDate.split("-");
            expectedToDate = fullEndDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[0].replaceFirst("^0+(?!$)", "");
        }
        if (dayMonthYear.contains("Month")) {
            String startDate = "";
            String endDate = "";
            if (dayMonthYear.equalsIgnoreCase("lastMonth")) {
                startDate = String.valueOf(todayDate.withDayOfMonth(1).plusMonths(-1));
                endDate = String.valueOf(todayDate.withDayOfMonth(1).plusDays(-1));
            } else {
                startDate = String.valueOf(todayDate.withDayOfMonth(1));
                endDate = String.valueOf(todayDate.plusMonths(1).withDayOfMonth(1).minusDays(1));
            }
            String[] fullStartDate = startDate.split("-");
            expectedStartDate = fullStartDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[0].replaceFirst("^0+(?!$)", "");
            String[] fullEndDate = endDate.split("-");
            expectedToDate = fullEndDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[0].replaceFirst("^0+(?!$)", "");
        }
        if (dayMonthYear.contains("Year")) {
            if (dayMonthYear.equalsIgnoreCase("thisYear")) {
                String startDate = String.valueOf(todayDate.withDayOfMonth(1).withDayOfYear(1));
                String endDate = String.valueOf(LocalDate.now().withDayOfMonth(1).withDayOfYear(1).plusYears(1).plusDays(-1));
                String[] fullStartDate = startDate.split("-");
                expectedStartDate = fullStartDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[0].replaceFirst("^0+(?!$)", "");
                String[] fullEndDate = endDate.split("-");
                expectedToDate = fullEndDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[0].replaceFirst("^0+(?!$)", "");
            }
        }
        if (actualStartDate.equalsIgnoreCase(expectedStartDate) && actualToDate.equalsIgnoreCase(expectedToDate)) {
            getTest().log(LogStatus.PASS, "The start date and End date of " + dayMonthYear + " is displayed as expected when click on " + dayMonthYear + " menu of " + fieldName);
            logger.info("The start date and End date of " + dayMonthYear + " is displayed as expected when click on " + dayMonthYear + " menu of " + fieldName);
        } else {
            getTest().log(LogStatus.FAIL, "The start date or End date of " + dayMonthYear + " is not displayed correctly when click on " + dayMonthYear + " menu of " + fieldName);
            logger.info("The start date or End date of " + dayMonthYear + " is not displayed correctly when click on " + dayMonthYear + " menu of " + fieldName);
            takeScreenshot("StartAndEndDate");
        }
    }

    public void verifySpecificDateField(String dateField) {
        String selectedDate = deployProductPage.selectDate("Old");
        if (!selectedDate.contains("disabled")) {
            getTest().log(LogStatus.PASS, "User can able to select the " + dateField);
            logger.info("User can able to select the " + dateField);
        } else {
            getTest().log(LogStatus.FAIL, "User not able to select the " + dateField);
            logger.info("User not able to select the " + dateField);
            takeScreenshot("DateField");
        }
    }

    public void verifySelectedOptionReflectionInChart() {
        int iteration = 0;
        String expectedString = getText(By.xpath("//a[@id='aDateLable_global']//*[@class='text-white ']"), 30);
        List<WebElement> selectedOptionLocator = findMultipleElement(By.xpath("//div[contains(@class,'dashbord-graph-widget')]//a[contains(@id,'aDateLable')]/*[@class='text-white ']"), 30);
        String[] widgetName = {"Products By Status", "Products By Location", "Pending Products By User",
                "Pending Products by Manager", "Products by Return Over Due", "Products by In-Transit"};
        for (WebElement selectedOption : selectedOptionLocator) {
            String selectedOptionInGraph = selectedOption.getText();
            if (selectedOptionInGraph.equalsIgnoreCase(expectedString)) {
                getTest().log(LogStatus.PASS, "The " + expectedString + " option is selected in the global filter and this value is reflected in the filter of " + widgetName[iteration]);
                logger.info("The " + expectedString + " option is selected in the global filter and this value is reflected in the filter of " + widgetName[iteration]);
            } else {
                getTest().log(LogStatus.FAIL, "The " + expectedString + " option is selected in the global filter but this selected value is not reflected in the filter of " + widgetName[iteration]);
                logger.info("The " + expectedString + " option is selected in the global filter but this selected value is not reflected in the filter of " + widgetName[iteration]);
                takeScreenshot("FilterSelectionInGlobal");
            }
            iteration++;
        }
    }

    public void verifyInTransitProductCumulativeCount(String userType) {
        scrollToWebelement(By.xpath("//div[@title='Status by IN-TRANSIT ']"), "In Transit Widget");
        if (inTransitProductCount != null) {
            WebElement prodWithCount = findElementVisibility(By.xpath("(//div[@id='chartInTransitAssetList']//*[text()='" + inTransitProductCount + "'])[1]"), 30);
            if (prodWithCount != null) {
                getTest().log(LogStatus.PASS, "Cumulative count of the product based on the " + userType + " locations are displayed as correctly in the In-Transit Widget");
                logger.info("Cumulative count of the product based on the " + userType + " locations are displayed as correctly in the In-Transit Widget");
            } else {
                getTest().log(LogStatus.FAIL, "Cumulative count of the product based on the " + userType + " locations are not displayed correctly in the In-Transit Widget");
                logger.info("Cumulative count of the product based on the " + userType + " locations are not displayed correctly in the In-Transit Widget");
                takeScreenshot("CumulativeCount");
            }
        } else {
            WebElement prodWithCount = findElementVisibility(By.xpath("//div[@dAction='InTransitAsset']//span[text()='No record(s) found']"), 30);
            if (prodWithCount != null) {
                getTest().log(LogStatus.PASS, "Cumulative count of the product based on the " + userType + " locations are changed when product accepted in the In-Transit page as correctly");
                logger.info("Cumulative count of the product based on the " + userType + " locations are changed when product accepted in the In-Transit page as correctly");
            } else {
                getTest().log(LogStatus.PASS, "Cumulative count of the product based on the " + userType + " locations are not changed when product accepted in the In-Transit page");
                logger.info("Cumulative count of the product based on the " + userType + " locations are not changed when product accepted in the In-Transit page");
                takeScreenshot("CumulativeCountChange");
            }
        }
    }

    public void verifyLocationForPMInTransit(String userType) {
        scrollToWebelement(By.xpath("//div[@title='Status by IN-TRANSIT ']"), "In Transit Widget");
        List<WebElement> locationList = findMultipleElement(By.xpath("//div[@id='chartInTransitAssetList']//*[contains(@class,'legend-item')]/*[@style='pointer-events: none;']"), 30);
        String expectedLocationName = locationList.get(0).getText().trim();
        if (locationList.size() == 1 && expectedLocationName.contains(toLocation)) {
            getTest().log(LogStatus.PASS, toLocation + " location is displayed with cumulative count of in transit product in In transit widget of " + userType);
            logger.info(toLocation + " location is displayed with cumulative count of in transit product in In transit widget of " + userType);
        } else {
            getTest().log(LogStatus.FAIL, "Location list in In transit widget of PM user is displaying the location which are not a " + userType + " shift location");
            logger.info("Location list in In transit widget of PM user is displaying the location which are not a " + userType + " shift location");
            takeScreenshot("PMLocationList");
        }
    }

    public void verifyPendingByManagerCumulativeCount(String userType) {
        List<String> productsAcceptancePendingByManger = userType.equals("Company Admin") ? productsAcceptancePendingByMangerCA : userType.equals("project Manager") ? productsAcceptancePendingByMangerPM : productsAcceptancePendingByMangerNU;
        scrollToWebelement(By.xpath("//div[@title='Products Acceptance Pending by Manager']"), "Pending By Manager Widget");
        int iteration = 0;
        List<WebElement> productsInWidget = findMultipleElement(By.xpath("//div[@action='PendingReturnRequestAsset']//table//tbody//tr//td[2]"), 30);
        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productName : productsInWidget) {
            actualProductNames.add(productName.getText().trim());
        }
        if (actualProductNames.size() == productsAcceptancePendingByManger.size()) {
            for (String expectedProductName : productsAcceptancePendingByManger) {
                for (String actualProductName : actualProductNames) {
                    iteration++;
                    if (expectedProductName.equalsIgnoreCase(actualProductName)) {
                        getTest().log(LogStatus.PASS, "The product " + expectedProductName + " is displayed as Active in pending request page and the same product is displayed in the Product Acceptance pending by Manager Widget of " + userType);
                        logger.info("The product " + expectedProductName + " is displayed as Active in pending request page and the same product is displayed in the Product Acceptance pending by Manager Widget of" + userType);
                        iteration = 0;
                        break;
                    } else if (iteration == actualProductNames.size()) {
                        getTest().log(LogStatus.FAIL, "The product " + expectedProductName + " is displayed as Active in pending request page but the same product is not displayed in the Product Acceptance pending by Manager Widget of" + userType);
                        logger.info("The product " + expectedProductName + " is displayed as Active in pending request page but the same product is not displayed in the Product Acceptance pending by Manager Widget of" + userType);
                        takeScreenshot("PendingProductByManager");
                        iteration = 0;
                        break;
                    }
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "The no of product with active status in pending request page is " + productsAcceptancePendingByManger.size() + " but the " + userType + " user can see the " + actualProductNames.size() + " no of product in the Product Pending for Acceptance By manager widget");
            logger.info("The no of product with active status in pending request page is " + productsAcceptancePendingByManger.size() + " but the " + userType + " user can see the " + actualProductNames.size() + " no of product in the Product Pending for Acceptance By manager widget");
            takeScreenshot("PendingProductByManager");
        }
    }

    public void verifyProdRemoveInPendingByManager(String userType) {
        List<String> productsAcceptancePendingByManger = userType.equalsIgnoreCase("company Admin") ? productsAcceptancePendingByMangerCA : userType.equalsIgnoreCase("project Manager") ? productsAcceptancePendingByMangerPM : productsAcceptancePendingByMangerNU;
        scrollToWebelement(By.xpath("//div[@title='Products Acceptance Pending by Manager']"), "Pending By Manager Widget");
        List<WebElement> productsInWidget = findMultipleElement(By.xpath("//div[@action='PendingReturnRequestAsset']//table//tbody//tr//td[2]"), 30);
        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productName : productsInWidget) {
            actualProductNames.add(productName.getText().trim());
        }
        if (!actualProductNames.contains(productsAcceptancePendingByManger.get(0))) {
            getTest().log(LogStatus.PASS, "The Product " + productsAcceptancePendingByManger.get(0) + " is removed from the Pending by Manger widget of " + userType + " when the request for the product is accepted");
            logger.info("The Product " + productsAcceptancePendingByManger.get(0) + " is removed from the Pending by Manger widget of " + userType + " when the request for the product is accepted");
        } else {
            getTest().log(LogStatus.FAIL, "The Product " + productsAcceptancePendingByManger.get(0) + " is not removed from the Pending by Manger widget of " + userType + " even the request for the product is accepted");
            logger.info("The Product " + productsAcceptancePendingByManger.get(0) + " is not removed from the Pending by Manger widget od " + userType + " even the request for the product is accepted");
            takeScreenshot("ProdRemoveInPendingByManager");
        }
    }

    public void verifyProdAcceptancePendingUserCumulativeCountCA() {
        String userName = prop.getProperty("secondUser");
        findElementVisibility(By.xpath("//div[@id='notacceptedAsset']"), 30);
        WebElement prodWithCount = findElementVisibility(By.xpath("(//div[@id='notacceptedAsset']//*[text()='" + userName + "(" + productAcceptancePendingByUser + ")'])[1]"), 15);

        if (prodWithCount != null) {
            getTest().log(LogStatus.PASS, "Cumulative count of the product based on the product acceptance pending by user for the " + userName + " user is displayed correctly");
            logger.info("Cumulative count of the product based on the product acceptance pending by user for the " + userName + " user is displayed correctly");
        } else {
            getTest().log(LogStatus.FAIL, "The actual cumulative count in my product page of " + userName + " is " + productAcceptancePendingByUser + " but that count is not displayed in dashboard");
            logger.info("The actual cumulative count in my product page of " + userName + " is " + productAcceptancePendingByUser + " but that count is not displayed in dashboard");
            takeScreenshot("CumulativeCountCA");
        }
    }

    public void verifyReturnedOverDueProductCount(String condition, String userType) {
        WebElement actualReturnedProductCount;
        scrollToWebelement(By.xpath("//div[@title='Status by Return Due and Return Overdue ']"), "Return Overdue Widget");
        String expectedCount = "";
        if (userType.equalsIgnoreCase("projectManager") || userType.equalsIgnoreCase("normalUser")) {
            expectedCount = shiftReturnOverDueProductCount;
        } else {
            expectedCount = allReturnOverDueProductCount;
        }
        if (expectedCount.equalsIgnoreCase("0")) {
            actualReturnedProductCount = findElementVisibility(By.xpath("//div[@action='ReturnDueAsset']//span[text()='No record(s) found']"), 10);
        } else {
            actualReturnedProductCount = findElementVisibility(By.xpath("(//div[@id='chartReturnDueList']//*[text()='Return Over Due(" + expectedCount + ")'])[1]"), 5);
        }
        if (condition.equalsIgnoreCase("same")) {
            if (actualReturnedProductCount != null) {
                getTest().log(LogStatus.PASS, "The total returned over due product in the product assignment page is " + expectedCount + " and the same count is displayed in the Return Over due widget of " + userType + " user who has same shift location with product");
                logger.info("The total returned over due product in the product assignment page is " + expectedCount + " and the same count is displayed in the Return Over due widget of " + userType + " user who has same shift location with product");
            } else {
                getTest().log(LogStatus.FAIL, "The total returned over due product in the product assignment page is " + expectedCount + " and the count is not displayed in the Return Over due widget of " + userType + " user who has same shift location with product");
                logger.info("The total returned over due product in the product assignment page is " + expectedCount + " and the count is not displayed in the Return Over due widget of " + userType + " user who has same shift location with product");
                takeScreenshot("ReturnOverDueProductCount");
            }
        } else {
            if (actualReturnedProductCount == null) {
                getTest().log(LogStatus.PASS, "The total returned over due product of all location in the product assignment page is " + expectedCount + " and the same count is not displayed in the Return Over due widget of " + userType + " user as expected");
                logger.info("The total returned over due product of all location in the product assignment page is " + expectedCount + " and the same count is not displayed in the Return Over due widget of " + userType + " user as expected");
            } else {
                getTest().log(LogStatus.FAIL, "The total returned over due product of all location in the product assignment page is " + expectedCount + " and the product count in the Return Over due widget of " + userType + " user are same");
                logger.info("The total returned over due product of all location in the product assignment page is " + expectedCount + " and the product count in the Return Over due widget of " + userType + " user are same");
                takeScreenshot("ReturnOverDueProductCount");
            }
        }

    }

    public void verifyProdAcceptancePendingUserCumulativeCount(String userType) {
        String userName = prop.getProperty("secondUser");
        findElementVisibility(By.xpath("//div[@id='notacceptedAsset']"), 30);
        WebElement prodWithCount = findElementVisibility(By.xpath("(//div[@id='notacceptedAsset']//*[text()='" + userName + "(" + productCountForPendingByUserWidget + ")'])[1]"), 15);

        if (prodWithCount != null) {
            getTest().log(LogStatus.PASS, "Cumulative count of the product based on the product acceptance pending by user for the " + userName + " user is displayed correctly in the Pending by user widget of " + userType + " dashboard");
            logger.info("Cumulative count of the product based on the product acceptance pending by user for the " + userName + " user is displayed correctly in the Pending by user widget of " + userType + "dashboard");
        } else {
            getTest().log(LogStatus.FAIL, "The actual cumulative count of not accepted products in my product page with shift location of " + userName + " is " + productCountForPendingByUserWidget + " but that count is not displayed in the Pending by user widget of " + userType + " dashboard");
            logger.info("The actual cumulative count of not accepted products in my product page with shift location of " + userName + " is " + productCountForPendingByUserWidget + " but that count is not displayed in the Pending by user widget of " + userType + " dashboard");
            takeScreenshot("CumulativeCount" + userType);
        }
    }

    public void verifyAllLocationProductInPendingByManager(String userType) {
        scrollToWebelement(By.xpath("//div[@title='Products Acceptance Pending by Manager']"), "Pending By Manager Widget");
        int iteration = 0;
        List<String> locationShouldNotDisplay = (userType.equalsIgnoreCase("ProductManager")) ? productsAcceptancePendingByMangerPM : productsAcceptancePendingByMangerNU;
        List<WebElement> actualProductsInTheWidget = findMultipleElement(By.xpath("//div[@action='PendingReturnRequestAsset']//table/tbody/tr/td[2]"), 20);
        for (String productOne : locationShouldNotDisplay) {
            for (WebElement productTwo : actualProductsInTheWidget) {
                iteration++;
                if (!productOne.equalsIgnoreCase(productTwo.getText().trim()) && iteration == actualProductsInTheWidget.size()) {
                    getTest().log(LogStatus.PASS, "Since the " + userType + " shift location and the " + productOne + " product location are different, So Product " + productOne + " is not displayed in the Pending by Manger widget of " + userType + " as expected.");
                    logger.info("Since the " + userType + " shift location and the " + productOne + " product location are different, So Product " + productOne + " is not displayed in the Pending by Manger widget of " + userType + " as expected.");
                    iteration = 0;
                    break;
                } else if (productOne.equalsIgnoreCase(productTwo.getText().trim())) {
                    getTest().log(LogStatus.FAIL, "The " + userType + " shift location and the " + productOne + " product location are different, even though The Product " + productOne + " is displayed in the Pending by Manger widget of " + userType + ".");
                    logger.info("The " + userType + " shift location and the " + productOne + " product location are different, even though The Product " + productOne + " is displayed in the Pending by Manger widget of " + userType + ".");
                    takeScreenshot("PendingByManager" + userType);
                    iteration = 0;
                    break;
                }
            }
        }
    }

    public void verifyProductByStatusWidget(String userType, String process) {
        String id = "";
        String prefix = "";
        String expectedCount = "";
        if (process.equalsIgnoreCase("Status")) {
            expectedCount = totalProductCountBasedOnStatus;
            id = "chartStatus";
            prefix = "Active";
        } else {
            expectedCount = totalProductCountBasedOnLocation;
            id = "chartLocation";
            prefix = userType.equalsIgnoreCase("normalUser") ? normalUserLocation : productManagerLocation;
        }
        WebElement activeStatusProductCount = findElementVisibility(By.xpath("//div[@id='" + id + "']//*[contains(@class,'legend-item')]//*[text()='" + prefix + "(" + expectedCount + ")']"), 20);
        if (activeStatusProductCount != null) {
            getTest().log(LogStatus.PASS, "The total product count based on the " + process + " is displayed as " + expectedCount + " in the product report and it is displayed in the Product By " + process + " widget of " + userType);
            logger.info("The total product count based on the " + process + " is displayed as " + expectedCount + " in the product report and it is displayed in the Product By " + process + " widget of " + userType);
        } else {
            getTest().log(LogStatus.PASS, "The total product count based on the " + process + " is displayed as " + expectedCount + " in the product report but it is not displayed in the Product By " + process + " widget of " + userType);
            logger.info("The total product count based on the " + process + " is displayed as " + expectedCount + " in the product report but it is not displayed in the Product By " + process + " widget of " + userType);
            takeScreenshot("TotalProdCount");
        }
    }
}
