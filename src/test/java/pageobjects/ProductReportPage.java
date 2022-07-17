package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebBasePage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static utils.StaticData.*;

public class ProductReportPage extends WebBasePage {
    WebDriver driver;

    public ProductReportPage(WebDriver driver) {
        super(driver, "Product Report Page");
        this.driver = driver;
    }

    public void clickProductReportMenu() {
        click(By.xpath("//a[text()='Product Report']"), "Product Report Menu", 60);
    }

    public void clickProductCountReportMenu() {
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 300);
        click(By.xpath("//a[@id='aAssetCountReport']"), "Product Count Report menu", 20);
        waitForLoad(20);
        staticWait(3000);
    }

    public void expandLocationField() {
        waitForLoad(60);
        findElementVisibility(By.xpath("//label[text()='Product Name:']//parent::div//span[contains(@class,'text-truncate d-block')]"), 60);
        findElementVisibility(By.xpath("//div[@class='chat_popup chat-btn-hide']"), 20);
        staticWait(1000);
        click(By.xpath("//div[@class='btn form-control']"), "Location Field", 20);
    }

    public void selectLocation(String locationName) {
        if (locationName.equalsIgnoreCase("ProjectManager") || locationName.equalsIgnoreCase("NormalUser") ||
                locationName.equalsIgnoreCase("CompanyAdmin")) {
            locationName = productManagerLocation;
        }
        if (locationName.equalsIgnoreCase("")) {
            click(By.xpath("//ul[@id='CompantLocationSelect']//a[@data-text='" + parentLocationNameList.get(0) + "']"), parentLocationNameList.get(0), 20);
        } else {
            click(By.xpath("//ul[@id='CompantLocationSelect']//a[@data-text='" + locationName + "']"), locationName, 20);
        }
    }

    public void searchProductCountReport() {
        click(By.cssSelector("a#aSearch"), "Product Count Report Search", 20);
        staticWait(2000);
        waitForElementInVisibility(By.xpath("//div[@id='reportViewer_AsyncWait_Wait']"), 60);
    }

    public void getProductNamesUnderSearchedLocation(String locationName) {
        scrollUpDown("down");
        if (locationName.equalsIgnoreCase("")) {
            locationName = parentLocationNameList.get(0);
        }
        driver.switchTo().defaultContent();
        WebElement ele = findElementPresence(By.xpath("//iframe[@id='iReport']"), 1);
        driver.switchTo().frame(ele);
        findElementVisibility(By.xpath("//div[text()='" + locationName + "']"), 60);
        List<WebElement> availableProductInLocation = findMultipleElement(By.xpath("//tr[@valign='top']/td[2]/div"), 30);
        for (WebElement productTypeElement : availableProductInLocation) {
            String productType = productTypeElement.getText().trim();
            if (!productType.equalsIgnoreCase("Location") && !productType.equalsIgnoreCase(locationName) && !productType.equalsIgnoreCase("Total")) {
                productTypeNamesForProductAssignment.add(productType);
            }
        }
        driver.switchTo().defaultContent();
    }

    public void expandStatusDropdown() {
        waitForLoad(120);
        click(By.xpath("//label[text()='Status:']/parent::div//button[contains(@class,'btn-default')]"), "Status Field", 20);
    }

    public void selectStatus(String status) {
        click(By.xpath("//label[text()='Status:']/parent::div//ul[contains(@class,'multiselect-container')]//label[normalize-space(text())='" + status + "']"), status + " status", 20);
    }

    public void enterStartAndEndDateOfMonth() {
        LocalDate todayDate = LocalDate.now();
        String startDate = String.valueOf(todayDate.withDayOfMonth(1));
        String endDate = String.valueOf(todayDate.plusMonths(1).withDayOfMonth(1).minusDays(1));
        String[] fullStartDate = startDate.split("-");
        String startDateToEnter = fullStartDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullStartDate[0].replaceFirst("^0+(?!$)", "");
        String[] fullEndDate = endDate.split("-");
        String EndDateToEnter = fullEndDate[1].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[2].replaceFirst("^0+(?!$)", "") + "/" + fullEndDate[0].replaceFirst("^0+(?!$)", "");
        enter(By.xpath("//label[text()='Deploy Date ']//parent::div//input[@name='startDate']"), startDateToEnter, "Start Date", 20);
        enter(By.xpath("//label[text()='Deploy Date ']//parent::div//input[@name='endDate']"), EndDateToEnter, "Start Date", 20);
    }

    public void getProductCount(String process) {
        scrollUpDown("down");
        driver.switchTo().defaultContent();
        WebElement ele = findElementPresence(By.xpath("//iframe[@id='iReport']"), 1);
        driver.switchTo().frame(ele);
        String actualProductCount = getText(By.xpath("//div[text()='Report Total']/ancestor::tr[3]/td/div/table//tr[2]//td[5]//div"), 20);
        driver.switchTo().defaultContent();
        if (process.equalsIgnoreCase("Status")) {
            totalProductCountBasedOnStatus = actualProductCount;
        } else {
            totalProductCountBasedOnLocation = actualProductCount;
        }
    }
}
