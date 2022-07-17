package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.*;
import static utils.StaticData.*;

public class WebTestBase {

    public WebDriver driver;
    public ExtentTest test;
    public static Logger logger;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();

    @AfterSuite(alwaysRun = true)
    public void close() {
        closeTest();
        closeReport();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        String url = System.getProperty("url");
        String browser = System.getProperty("browser");
        if (url == null) {
            url = prop.getProperty("url");
        }
        if (browser == null) {
            browser = "chrome";
        }
        System.out.println(url);
        driver = new Drivers().getWebDriver(browser);
        driver.get(url);
        driver.manage().window().maximize();

    }

    @BeforeMethod()
    public void beforeMethod(Method method) {
        logger = Logger.getLogger(method.getDeclaringClass().getSimpleName() + "-" + method.getName());
//        test = getTest(method.getDeclaringClass().getSimpleName() + "-" + method.getName(), method.getName());
        System.out.println("Method Name - " + method.getDeclaringClass().getSimpleName() + "-" + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void reportWrapUp(ITestResult result, Method method) {

        if (!result.isSuccess()) {

            String fileName = method.getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String imagePath = System.getProperty("user.dir") + "\\reports\\" + fileName;
            // generate screenshot as a file object
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                // copy file object to designated location
                FileUtils.copyFile(scrFile, new File(imagePath + ".png"));
                System.out.println(imagePath + ".png");
                getTest().log(LogStatus.FAIL, "Method - " + method.getName() + " failed , see the screenshot - " + imagePath + ".png");
                logger.info("Method - " + method.getName() + " failed , see the screenshot - " + imagePath + ".png");
            } catch (Exception e) {
                getTest().log(LogStatus.FAIL, method.getName() + " Failed - " + e);
                Assert.fail("Error while taking screenshot - " + e);
            }

        }
        closeTest(test);
        clearStaticData();
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    public void clearStaticData() {
        createdProductName = null;
        selectedContainer = null;
        locationToSearch = null;
        productNameToAssign = null;
        productTypeToAssign = null;
        productLocationToAssign = null;
        productToAssign = null;
        productNameToSearch = null;
        productStatusToSearch = null;
        duplicateProductCode = null;
        duplicateProductType = null;
        productTypeForContainer = null;
        productLocationForContainer = null;
        productTitleName = null;
        productTypeStatus = null;
        activeRecordName = null;
        uniqueName = null;
        productCode = null;
        productName = null;
        location = null;
        toLocation = null;
        assignFromDate = null;
        assignTillDate = null;
        randNum = new Random();
        productTypeName = null;
        barCodeValues = new ArrayList<>();
        productTypesToAssign = null;
        productNamesToAssign = new ArrayList<>();
        productLocationsToAssign = new ArrayList<>();
        productsToAssign = new ArrayList<>();
        containerProductType = new ArrayList<>();
        searchUniqueName = null;
        searchStatus = null;
        rowName = null;
        userName = null;
        productAcceptancePendingByUser = null;
        productNamesPendingByUser = new ArrayList<>();
        productNamesWithCount = new HashMap<>();
        productCountForPendingByUserWidget = null;
        allReturnOverDueProductCount = null;
        shiftReturnOverDueProductCount = null;
        productInMyProductList = new ArrayList<>();
        productCountForLocationWidget = 0;
        inTransitProductCount = null;
        prodType = null;
        productsAcceptancePendingByMangerCA = new ArrayList<>();
        productsAcceptancePendingByMangerPM = new ArrayList<>();
        productsAcceptancePendingByMangerNU = new ArrayList<>();
        pendingByManagerOtherLocation = new ArrayList<>();
        productManagerLocation = null;
        normalUserLocation = null;
        userCount = 0;
        validBarcode = null;
        locationName = null;
        companyAdminLocationName = null;
        departmentNameList = new ArrayList<>();
        usersInTheDepartment = new ArrayList<>();
        allUserNameList = new ArrayList<>();
        departmentNameToVerify = null;
        barCodeNameToProductAssignSearch = null;
        parentLocationNameList = new ArrayList<>();
        childLocationNameList = new ArrayList<>();
        productTypeNamesForProductAssignment = new ArrayList<>();
        productTypeToGetProductName = null;
        productNameToGetProducts = null;
        productNamesForProductAssignment = new ArrayList<>();
        totalProductCountBasedOnStatus = null;
        totalProductCountBasedOnLocation = null;
        currentTimeToTest = null;
    }

}
