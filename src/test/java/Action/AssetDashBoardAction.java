package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.*;

import static utils.StaticData.*;

public class AssetDashBoardAction {

    WebDriver driver;
    AssetDashBoardPage assetDashBoardPage;
    ProductTransferPage productTransferPage;
    AddProductAction addProductAction;
    InTransitPage inTransitPage;
    MyProductPage myProductPage;
    ProductTypeAction productTypeAction;
    ProductAssignmentPage productAssignmentPage;
    ProductListingPage productListingPage;

    public AssetDashBoardAction(WebDriver driver) {
        this.driver = driver;
        this.assetDashBoardPage = new AssetDashBoardPage(driver);
        this.productTransferPage = new ProductTransferPage(driver);
        this.addProductAction = new AddProductAction(driver);
        this.inTransitPage = new InTransitPage(driver);
        this.myProductPage = new MyProductPage(driver);
        this.productTypeAction = new ProductTypeAction(driver);
        this.productAssignmentPage = new ProductAssignmentPage(driver);
        this.productListingPage = new ProductListingPage(driver);
    }

    public void navigateToAssetDashBoardPage() {
        assetDashBoardPage.navigateToManageProductMenu();
        assetDashBoardPage.clickAssetDashBoardMenu();
    }

    public void navigateToProductAssignmentPage() {
        assetDashBoardPage.navigateToManageProductMenu();
        productAssignmentPage.navigateToProductAssignmentPage();
    }

    public void navigateToInTransitPage() {
        inTransitPage.navigateToInTransitPage();
    }

    public void manageWidgetProcess() {
        assetDashBoardPage.navigateToManageWidgetPopup();
        assetDashBoardPage.enableThreeProdCheckBox();
        assetDashBoardPage.clickSaveButton();
        assetDashBoardPage.verifyWidgetRemoveFunction();
        assetDashBoardPage.navigateToManageWidgetPopup();
        assetDashBoardPage.enableAllProdCheckBox();
        assetDashBoardPage.clickSaveButton();
        assetDashBoardPage.verifyWidgetAddFunction();
        assetDashBoardPage.verifyMaximumWidgets();
    }

    public void zoomInZoomOutFunction() {
        assetDashBoardPage.verifyWidgetZoomIn();
        assetDashBoardPage.verifyWidgetZoomOut();
    }

    public void verifyMenuUnderProductByStatusWidget() {
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyTheMenuInWidget("ProductByStatus");
    }

    public void verifyMenuUnderProductByLocationWidget() {
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyTheMenuInWidget("ProductByLocation");
    }

    public void verifyMenuUnderPendingByUserWidget() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyTheMenuInWidget("PendingByUser");
    }

    public void verifyMenuUnderPendingByManagerWidget() {
        assetDashBoardPage.scrollToWidget("PendingByManager");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyTheMenuInWidget("PendingByManager");
    }

    public void verifyMenuUnderReturnOverDueWidget() {
        assetDashBoardPage.scrollToWidget("ReturnOverDue");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyTheMenuInWidget("ReturnOverDue");
    }

    public void verifyMenuInInTransitWidgets() {
        assetDashBoardPage.scrollToWidget("InTransit");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyTheMenuInWidget("InTransit");
    }

    public void verifyStartToOfThisYearProductByStatus() {
        assetDashBoardPage.clickYearOption("ProductByStatus");
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyStartAndToDate("ProductByStatus", "ThisYear");
    }

    public void verifyStartToOfThisMonthProductByStatus() {
        assetDashBoardPage.clickMonthOption("ProductByStatus", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyStartAndToDate("ProductByStatus", "thisMonth");
    }

    public void verifyStartToOfLastMonthProductByStatus() {
        assetDashBoardPage.clickMonthOption("ProductByStatus", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyStartAndToDate("ProductByStatus", "lastMonth");
    }

    public void verifySpecificDateFieldProductByStatus() {
        assetDashBoardPage.clickSpecificDateFiled("ProductByStatus", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("ProductByStatus", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekProductByStatus() {
        assetDashBoardPage.clickWeekOption("ProductByStatus", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyStartAndToDate("ProductByStatus", "thisWeek");
    }

    public void verifyStartToOfLastWeekProductByStatus() {
        assetDashBoardPage.clickWeekOption("ProductByStatus", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("ProductByStatus");
        assetDashBoardPage.verifyStartAndToDate("ProductByStatus", "lastWeek");
    }


    public void verifyStartToOfThisYearProductByLocation() {
        assetDashBoardPage.clickYearOption("ProductByLocation");
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyStartAndToDate("ProductByLocation", "ThisYear");
    }

    public void verifyStartToOfThisMonthProductByLocation() {
        assetDashBoardPage.clickMonthOption("ProductByLocation", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyStartAndToDate("ProductByLocation", "thisMonth");
    }

    public void verifyStartToOfLastMonthProductByLocation() {
        assetDashBoardPage.clickMonthOption("ProductByLocation", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyStartAndToDate("ProductByLocation", "lastMonth");
    }

    public void verifySpecificDateFieldProductByLocation() {
        assetDashBoardPage.clickSpecificDateFiled("ProductByLocation", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("ProductByLocation", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekProductByLocation() {
        assetDashBoardPage.clickWeekOption("ProductByLocation", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyStartAndToDate("ProductByLocation", "thisWeek");
    }

    public void verifyStartToOfLastWeekProductByLocation() {
        assetDashBoardPage.clickWeekOption("ProductByLocation", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("ProductByLocation");
        assetDashBoardPage.verifyStartAndToDate("ProductByLocation", "lastWeek");
    }


    public void verifyStartToOfThisYearProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickYearOption("PendingByUser");
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyStartAndToDate("PendingByUser", "ThisYear");
    }

    public void verifyStartToOfThisMonthProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickMonthOption("PendingByUser", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyStartAndToDate("PendingByUser", "thisMonth");
    }

    public void verifyStartToOfLastMonthProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickMonthOption("PendingByUser", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyStartAndToDate("PendingByUser", "lastMonth");
    }

    public void verifySpecificDateFieldProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickSpecificDateFiled("PendingByUser", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("PendingByUser", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickWeekOption("PendingByUser", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyStartAndToDate("PendingByUser", "thisWeek");
    }

    public void verifyStartToOfLastWeekProductPendingByUser() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.clickWeekOption("PendingByUser", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("PendingByUser");
        assetDashBoardPage.verifyStartAndToDate("PendingByUser", "lastWeek");
    }


    public void verifyStartToOfThisYearProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickYearOption("PendingByManager");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyStartAndToDate("PendingByManager", "ThisYear");
    }

    public void verifyStartToOfThisMonthProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickMonthOption("PendingByManager", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyStartAndToDate("PendingByManager", "thisMonth");
    }

    public void verifyStartToOfLastMonthProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickMonthOption("PendingByManager", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyStartAndToDate("PendingByManager", "lastMonth");
    }

    public void verifySpecificDateFieldProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickSpecificDateFiled("PendingByManager", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("PendingByManager", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickWeekOption("PendingByManager", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyStartAndToDate("PendingByManager", "thisWeek");
    }

    public void verifyStartToOfLastWeekProductPendingByManager() {
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.clickWeekOption("PendingByManager", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("PendingByManager");
        assetDashBoardPage.verifyStartAndToDate("PendingByManager", "lastWeek");
    }


    public void verifyStartToOfThisYearInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickYearOption("InTransit");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyStartAndToDate("InTransit", "ThisYear");
    }

    public void verifyStartToOfThisMonthInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickMonthOption("InTransit", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyStartAndToDate("InTransit", "thisMonth");
    }

    public void verifyStartToOfLastMonthInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickMonthOption("InTransit", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyStartAndToDate("InTransit", "lastMonth");
    }

    public void verifySpecificDateFieldInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickSpecificDateFiled("InTransit", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("InTransit", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickWeekOption("InTransit", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyStartAndToDate("InTransit", "thisWeek");
    }

    public void verifyStartToOfLastWeekInTransitStatus() {
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.clickWeekOption("InTransit", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("InTransit");
        assetDashBoardPage.verifyStartAndToDate("InTransit", "lastWeek");
    }


    public void verifyStartToOfThisYearProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickYearOption("ReturnOverDue");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyStartAndToDate("ReturnOverDue", "ThisYear");
    }

    public void verifyStartToOfThisMonthProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickMonthOption("ReturnOverDue", "ThisMonth");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyStartAndToDate("ReturnOverDue", "thisMonth");
    }

    public void verifyStartToOfLastMonthProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickMonthOption("ReturnOverDue", "LastMonth");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyStartAndToDate("ReturnOverDue", "lastMonth");
    }

    public void verifySpecificDateFieldProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickSpecificDateFiled("ReturnOverDue", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("ReturnOverDue", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
    }

    public void verifyStartToOfThisWeekProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickWeekOption("ReturnOverDue", "ThisWeek");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyStartAndToDate("ReturnOverDue", "thisWeek");
    }

    public void verifyStartToOfLastWeekProductReturnOverDue() {
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.clickWeekOption("ReturnOverDue", "LastWeek");
        assetDashBoardPage.clickFilterMenuIcon("ReturnOverDue");
        assetDashBoardPage.verifyStartAndToDate("ReturnOverDue", "lastWeek");
    }

    public void verifySpecificDateFieldGlobal() {
        assetDashBoardPage.clickFilterMenuIcon("Global");
        assetDashBoardPage.clickSpecificDateFiled("Global", "FromDate");
        assetDashBoardPage.verifySpecificDateField("Start Date");
        assetDashBoardPage.clickSpecificDateFiled("Global", "ToDate");
        assetDashBoardPage.verifySpecificDateField("To Date");
        assetDashBoardPage.clickGoButton();
        assetDashBoardPage.verifySelectedOptionReflectionInChart();
    }

    public void acceptInTransitProduct() {
        inTransitPage.navigateToInTransitPage();
        inTransitPage.clickAcceptorRejectButton();
        inTransitPage.selectAcceptOption();
        inTransitPage.saveAcceptorReject();
    }

    public void createAndTransferProduct() {
        inTransitPage.rejectAllProdBeforeVerifyWidget();
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", "");
        assetDashBoardPage.storeProductDetailsToStaticData();
        productTransferPage.clickProductTransfer();
        productTransferPage.saveTheProductToAnotherLocationAndVerify("Widget");
    }

    public void getInTransitProductCount() {
        addProductAction.gotoManageProductPage();
        productTransferPage.navigateToInTransitPage();
        inTransitPage.getProductCountInLocation();
    }

    public void verifyInTransitWidgetOfCA() {
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Company Admin");
        acceptInTransitProduct();
        inTransitPage.getProductCountInLocation();
        navigateToAssetDashBoardPage();
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Company Admin");
    }

    public void verifyInTransitWidgetCumulativeCountOfPM() {
        assetDashBoardPage.verifyLocationForPMInTransit("Project Manager");
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Project Manager");
        acceptInTransitProduct();
        inTransitPage.getProductCountInLocation();
        navigateToAssetDashBoardPage();
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Project Manager");
    }

    public void verifyInTransitWidgetCumulativeCountOfNU() {
        assetDashBoardPage.verifyLocationForPMInTransit("Normal User");
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Normal User");
        acceptInTransitProduct();
        inTransitPage.getProductCountInLocation();
        navigateToAssetDashBoardPage();
        assetDashBoardPage.verifyInTransitProductCumulativeCount("Normal User");
    }

    public void createAndAssignPendingUserProd() {
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", productManagerLocation);
        myProductPage.assignProductToUser(1, false, "", "normalUser");
    }

    public void createAndAssignPendingManagerProd() {
        productTypeAction.goToTheProductTypePage();
        myProductPage.createProductType("requestQuantity");
        myProductPage.createNewProduct(true, "barcode", productTypeName, "10", productManagerLocation);
        myProductPage.assignProductToUser(1, false, "", "normalUser");
    }

    public void getRedProductCountCA() {
        assetDashBoardPage.navigateToManageProductMenu();
        myProductPage.openMyProductPage();
        myProductPage.getCountOfRedProdBasedOnThisMonth();
    }

    public void getRedProductCountWithLocation(String User) {
        assetDashBoardPage.navigateToManageProductMenu();
        myProductPage.openMyProductPage();
        myProductPage.getRedProductNamesAndCount();
    }

    public void getRedProductNamesForPMUser() {
        getRedProductCountWithLocation("ProjectManager");
    }

    public void verifyPendingByUserWidgetForPM() {
        assetDashBoardPage.verifyProdAcceptancePendingUserCumulativeCount("ProjectManager");
    }

    public void verifyPendingByUserWidgetForNU() {
        assetDashBoardPage.verifyProdAcceptancePendingUserCumulativeCount("NormalUser");
    }

    public void getRedProductNamesForNUUser() {
        getRedProductCountWithLocation("NormalUser");
    }

    public void getProductCountBasedOnLocation() {
        assetDashBoardPage.navigateToManageProductPage();
        productListingPage.verifyAndAddProductCountWithLocation();
    }

    public void getProductNotToDisplayInWidgetPM() {
        assetDashBoardPage.navigateToManageProductPage();
        productListingPage.verifyAndAddProductNameWithLocation("ProductManager", "AllLocation");
    }

    public void getProductShouldToDisplayInWidgetPM() {
        assetDashBoardPage.navigateToManageProductPage();
        productListingPage.verifyAndAddProductNameWithLocation("ProductManager", "ShiftLocation");
    }

    public void getProductsNotToDisplayInWidgetNU() {
        assetDashBoardPage.navigateToManageProductPage();
        productListingPage.verifyAndAddProductNameWithLocation("NormalUser", "AllLocation");
    }

    public void getProductsToDisplayInWidgetNU() {
        assetDashBoardPage.navigateToManageProductPage();
        productListingPage.verifyAndAddProductNameWithLocation("NormalUser", "ShiftLocation");
    }

    public void verifyCumulativePendingByUserCA() {
        navigateToAssetDashBoardPage();
        assetDashBoardPage.verifyProdAcceptancePendingUserCumulativeCountCA();
    }

    public void acceptAssignedProductCA() {
        assetDashBoardPage.navigateToManageProductMenu();
        myProductPage.openMyProductPage();
        myProductPage.clickAcceptIconOfProduct();
    }

    public void getOverDueProductCountForCA() {
        productAssignmentPage.getCountOfAllReturnOverDueProd();
    }

    public void getOverDueProductCountBySameShift() {
        productAssignmentPage.getCountOfShiftReturnOverDueProd("projectManager", "same");
    }

    public void getOverDueProductCountForNU() {
        productAssignmentPage.getCountOfReturnOverDueProdForNU("same");
    }

    public void getOverDueProductCountByAllShift() {
        productAssignmentPage.getCountOfShiftReturnOverDueProd("projectManager", "all");
    }

    public void verifyReturnOverDueWidgetCumulativeCountCA() {
        assetDashBoardPage.verifyReturnedOverDueProductCount("same", "companyAdmin");
    }

    public void verifyReturnOverDueWidgetCumulativeCountPMSameShift() {
        assetDashBoardPage.verifyReturnedOverDueProductCount("same", "projectManager");
    }

    public void verifyReturnOverDueWidgetCumulativeCountNUSameShift() {
        assetDashBoardPage.verifyReturnedOverDueProductCount("same", "normalUser");
    }

    public void verifyReturnOverDueWidgetCumulativeCountPMAllShift() {
        assetDashBoardPage.verifyReturnedOverDueProductCount("all", "projectManager");
    }

    public void verifyReturnOverDueWidgetCumulativeCountNUAllShift() {
        assetDashBoardPage.verifyReturnedOverDueProductCount("all", "normalUser");
    }

    public void returnProductForWidgetFromProductAssignment() {
        productAssignmentPage.returnProductFromProductAssignmentPage();
    }

    public void returnProductForWidgetFromProductAssignmentByPMShift() {
        productAssignmentPage.returnProductFromProductAssignmentPageByShift("ProjectManager");
    }

    public void returnProductFromMyProductPage() {
        assetDashBoardPage.navigateToManageProductMenu();
        myProductPage.openMyProductPage();
        myProductPage.clickAcceptIconOfProduct();
        myProductPage.clickReturnOption();
        myProductPage.selectReasonForReturn();
        myProductPage.saveReturnPopup();
    }

    public void verifyProductInPendingByManagerWidgetCA() {
        assetDashBoardPage.verifyPendingByManagerCumulativeCount("Company Admin");
    }

    public void verifyProductInPendingByManagerWidgetPM() {
        assetDashBoardPage.verifyPendingByManagerCumulativeCount("Project Manager");
    }

    public void verifyProductInPendingByManagerWidgetNU() {
        assetDashBoardPage.verifyPendingByManagerCumulativeCount("Normal User");
    }

    public void verifyOtherLocationProdInPendingByManagerWidgetPM() {
        assetDashBoardPage.verifyAllLocationProductInPendingByManager("ProjectManager");
    }

    public void verifyOtherLocationProdInPendingByManagerWidgetNU() {
        assetDashBoardPage.verifyAllLocationProductInPendingByManager("NormalUser");
    }

    public void verifyProductRemovePendingByManagerWidgetCA() {
        assetDashBoardPage.verifyProdRemoveInPendingByManager("Company Admin");
    }

    public void verifyProductRemovePendingByManagerWidgetPM() {
        assetDashBoardPage.verifyProdRemoveInPendingByManager("Project Manger");
    }

    public void verifyProductRemovePendingByManagerWidgetNU() {
        assetDashBoardPage.verifyProdRemoveInPendingByManager("NormalUser");
    }

    public void verifyProductByStatusCA() {
        assetDashBoardPage.verifyProductByStatusWidget("Company Admin", "Status");
    }

    public void verifyProductByStatusPM() {
        assetDashBoardPage.verifyProductByStatusWidget("Project Manager", "Status");
    }

    public void verifyProductByStatusNU() {
        assetDashBoardPage.verifyProductByStatusWidget("NormalUser", "Status");
    }

    public void verifyProductByLocationCA() {
        assetDashBoardPage.verifyProductByStatusWidget("Company Admin", "Location");
    }

    public void verifyProductByLocationPM() {
        assetDashBoardPage.verifyProductByStatusWidget("Project Manager", "Location");
    }

    public void verifyProductByLocationNU() {
        assetDashBoardPage.verifyProductByStatusWidget("NormalUser", "Location");
    }
}
