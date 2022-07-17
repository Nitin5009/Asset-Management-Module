package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Drivers;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.io.File;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;

public class AttachmentsPage extends WebBasePage {
    WebDriver driver;
    private final static String FILE_NAME = System.getProperty("user.dir")+"\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
    public static String termsAndCondition;
    public static int filesInDirectory;
    public static int attachmentCountBeforeDelete;
    public AttachmentsPage(WebDriver driver)
    {
        super(driver,"Attachment page");
        this.driver=driver;
    }

    public void navigateToAttachmentTab()
    {
        WebElement attachmentTab=findElementClickable(By.xpath("//ul[@id='myTab']//li//a[text()='Attachments ']"),20);
        if(attachmentTab!=null) {
            click(By.xpath("//ul[@id='myTab']//li//a[text()='Attachments ']"), "Attachments Tab", 15);
            getTest().log(LogStatus.PASS,"User can able to click \"Attachment Tab\"");
            logger.info("User can able to click \"Attachment Tab\"");
        }else
        {
            getTest().log(LogStatus.FAIL,"User not able to click \"Attachment Tab\"");
            logger.info("User not able to click \"Attachment Tab\"");
            takeScreenshot("AttachmentTab");
        }
    }
    public void enterAttachmentName()
    {
        enter(By.cssSelector("#txtAttachment"),prop.getProperty("attachmentNameAlphaNumeric"),"Attachment Name",20);
    }
    public void attachmentNameAlphaNumeric()
    {
        enterAttachmentName();
        String actualText=getAtribute(By.cssSelector("#txtAttachment"),"value",15);
        if(actualText.equals(prop.getProperty("attachmentNameAlphaNumeric")))
        {
            getTest().log(LogStatus.PASS,"\"Attachment Name\" field accepted the alpha numeric data as expected and Accepted Data : "+actualText);
            logger.info("\"Attachment Name\" field accepted the alpha numeric data as expected and Accepted Data : "+actualText);
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Attachment Name\" field not accepts the alpha numeric data as expected and Entered Data : "+prop.getProperty("attachmentNameAlphaNumeric"));
            logger.info("\"Attachment Name\" field not accepts the alpha numeric data as expected and Entered Data : "+prop.getProperty("attachmentNameAlphaNumeric"));
            takeScreenshot("AttachmentName");
        }
    }
    public void termAnsConditions()
    {
        findElementVisibility(By.cssSelector(".custom-control"),20);
        clickByJavascript(By.cssSelector("div.custom-checkbox>input[name='IsTermsAndCondition']"),"Terms and Conditions",15);
        String check=findElementPresence(By.cssSelector("input.rowTermsCondition"),20).getAttribute("checked");
        if(check.equals("true"))
        {
            getTest().log(LogStatus.PASS,"\"Terms and Conditions\" checkbox is present as clickable");
            logger.info("\"Terms and Conditions\" checkbox is present as clickable");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Terms and Conditions\" checkbox is present as not clickable");
            logger.info("\"Terms and Conditions\" checkbox is present as not clickable");
            takeScreenshot("TermsAndCond");
        }
    }
    public void verifyChooseFileClickable()
    {
        WebElement chooseFile=findElementClickable(By.cssSelector("#divFiles>div>.custom-file>div>.input-group-append>span>label"),20);
        if(chooseFile!=null)
        {
            getTest().log(LogStatus.PASS,"\"Choose File\" is present as clickable");
            logger.info("\"Choose File\" is present as clickable");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Choose File\" is not present as clickable");
            logger.info("\"Choose File\" is not present as clickable");
            takeScreenshot("ChooseFile");
        }
    }
    public void verifyAddMoreFileClickable()
    {
        WebElement addMoreFile=findElementClickable(By.cssSelector("div>#addMore"),20);
        if(addMoreFile!=null)
        {
            clickAddMoreAttachment();
            getTest().log(LogStatus.PASS,"\"Add More Files\" is present as clickable and clicked");
            logger.info("\"Add More Files\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Add More Files\" is not present as clickable");
            logger.info("\"Add More Files\" is not present as clickable");
            takeScreenshot("AddMoreFile");
        }
    }
    public void verifyClearFileClickable()
    {
        boolean actualState=findElementVisibility(By.xpath("//a[contains(@class,'clear actionicons')]"),20).isEnabled();
        if(actualState)
        {
            clickMinusIcon();
            getTest().log(LogStatus.PASS,"\"Clear File\" is present as clickable and clicked");
            logger.info("\"Clear File\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Clear File\" is not present as clickable");
            logger.info("\"Clear File\" is not present as clickable");
            takeScreenshot("ClearFile");
        }
    }
    public void verifySaveButtonClickable()
    {
        WebElement saveButton=findElementClickable(By.cssSelector("a#btnSave"),20);
        if(saveButton!=null)
        {
            clickSaveButton();
            getTest().log(LogStatus.PASS,"\"Save Button\" is present as clickable and clicked");
            logger.info("\"Save Button\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Save Button\" is not present as clickable");
            logger.info("\"Save Button\" is not present as clickable");
            takeScreenshot("Save");
        }
    }
    public void verifyCancelButtonClickable()
    {
        WebElement cancelButton=findElementClickable(By.cssSelector("a#btnCancel"),20);
        if(cancelButton!=null)
        {
            clickCancelButton();
            getTest().log(LogStatus.PASS,"\"Cancel Button\" is present as clickable and clicked");
            logger.info("\"Cancel Button\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Cancel Button\" is not present as clickable");
            logger.info("\"Cancel Button\" is not present as clickable");
            takeScreenshot("Cancel");
        }
    }
    public void verifyPreviousButtonClickable()
    {
        WebElement previousButton=findElementClickable(By.cssSelector("a#Previous"),20);
        if(previousButton!=null)
        {
            clickPreviousButton();
            getTest().log(LogStatus.PASS,"\"Previous Button\" is present as clickable and clicked");
            logger.info("\"Previous Button\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Previous Button\" is not present as clickable");
            logger.info("\"Previous Button\" is not present as clickable");
            takeScreenshot("Previous");
        }
    }
    public void verifyAttachmentsDetails()
    {
        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("M/d/yyyy");
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date=gmtDateFormat.format(new Date());
        String attachmentName=prop.getProperty("attachmentNameAlphaNumeric");
        String fileName=prop.getProperty("testfileDoc");

        String [] expectedArray;
                if(termsAndCondition.equals("No"))
                {
                    expectedArray= new String[]{attachmentName, fileName, termsAndCondition};
                }
                else
                {
                    expectedArray= new String[]{attachmentName, fileName, termsAndCondition,date};
                }
                int i=1;
                for(Object expected:expectedArray)
                {
                    List <WebElement> tableElement=findMultipleElement(By.xpath("//table[@id='example']//tbody//tr[1]//td"),20);
                    String actualTableContent=tableElement.get(i).getText().trim();
                    if(i==4)
                    {
                        String[] actualContentArray=actualTableContent.split(" ");
                        actualTableContent=actualContentArray[0];
                    }
                    if(expected.equals(actualTableContent))
                    {
                        getTest().log(LogStatus.PASS,expected+" data is displayed as expected in the attachment list");
                       logger.info(expected+" data is displayed as expected in the attachment list");
                    }
                    else if (i==tableElement.size() && !expected.equals(actualTableContent))
                    {
                        getTest().log(LogStatus.FAIL,expected+" data is not displayed in the attachment list");
                        logger.info(expected+" data is not displayed in the attachment list");
                        takeScreenshot(expected.toString());
                    }
                    i++;
                }
    }
    public void verifyDownloadIconClickable()
    {
        scrollToWebelement(By.xpath("//table[@id='example']//tbody//tr[1]//td//a[@class='downloadfile']"),"Download");
        WebElement downloadIcon=findElementClickable(By.xpath("//table[@id='example']//tbody//tr[1]//td//a[@class='downloadfile']"),20);
        if(downloadIcon!=null)
        {
            clickDownloadButton();
            getTest().log(LogStatus.PASS,"\"Download Icon\" is present as clickable and clicked");
            logger.info("\"Download Icon\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Download Icon\" is not present as clickable");
            logger.info("\"Download Icon\" is not present as clickable");
            takeScreenshot("DownloadIcon");
        }
    }
    public void verifyDeleteIconClickable()
    {
        WebElement deleteIcon=findElementClickable(By.xpath("//table[@id='example']//tbody//tr[1]//td//a[@class='deletefile']"),20);
        if(deleteIcon!=null)
        {
            clickDeleteIcon();
            getTest().log(LogStatus.PASS,"\"Delete Icon\" is present as clickable and clicked");
            logger.info("\"Delete Icon\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Delete Icon\" is not present as clickable");
            logger.info("\"Delete Icon\" is not present as clickable");
            takeScreenshot("DeleteIcon");
        }
    }
    public void verifyRemoveIconClickable()
    {
        WebElement removeIcon= findElementClickable(By.xpath("//div//a[contains(@class,'actionicons remove')]"),20);
        if(removeIcon!=null)
        {
            clickRemoveAttachmentField();
            getTest().log(LogStatus.PASS,"\"Remove Icon\" is present as clickable and clicked");
            logger.info("\"Remove Icon\" is present as clickable and clicked");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"\"Remove Icon\" is not present as clickable");
            logger.info("\"Remove Icon\" is not present as clickable");
            takeScreenshot("RemoveIcon");
        }
    }
    public void selectTermsAndConditionsYes()
    {
        termsAndCondition=prop.getProperty("termsAndConditionYes");
        clickByJavascript(By.cssSelector("div.custom-checkbox>input[name='IsTermsAndCondition']"),"Terms and Conditions",15);
    }
    public void selectTermsAndConditionsNo()
    {
        termsAndCondition=prop.getProperty("termsAndConditionNo");
    }
    public void uploadAttachment()
    {
        uploadDoc(By.cssSelector("div>input#flFile"), filePath + prop.getProperty("testfileDoc"), "Upload Attachment", 10);
    }
    public void uploadMoreAttachment()
    {
        scrollToWebelement(By.cssSelector("div>input.expenseUpload"),"More Attachments");
        findElementPresence(By.cssSelector("div>input.expenseUpload"), 20).sendKeys(filePath + prop.getProperty("testfileDoc"));
    }
    public void clickAddMoreAttachment()
    {
            click(By.cssSelector("a#addMore"), "Add More", 15);
    }
    public void clickRemoveAttachmentField()
    {
        click(By.xpath("//div//a[contains(@class,'actionicons remove')]"),"Remove Attachment",20);
    }
    public void clickMinusIcon()
    {
        click(By.xpath("//a[contains(@class,'clear actionicons')]"),"Clear Button",20);
    }
    public void clickCancelButton()
    {
        click(By.cssSelector("a#btnCancel"),"Cancel",15);
    }

    public void clickSaveButton()
    {
        click(By.cssSelector("a#btnSave"),"Save",15);
        waitForLoader(20);
    }
    public void clickPreviousButton()
    {
        click(By.cssSelector("a#Previous"),"Previous",15);
    }
    public void clickDeleteIcon()
    {
        attachmentCountBeforeDelete=findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"),20).size();
        click(By.xpath("//table[@id='example']//tbody//tr[1]//td//a[@class='deletefile']"),"Delete",15);
    }
    public void confirmAttchmentDelete()
    {
        waitForVisibilityOfElement(By.cssSelector("div.notifybox"),20);
        click(By.cssSelector("div.modal-confirm-footer>button.btn-success"),"Ok",20);
    }
    public void clickDownloadButton()
    {
        String downloadPath = Drivers.path;
        String fileName = prop.getProperty("testfileDoc");
        File dir = new File(downloadPath + fileName);
        if (dir.exists()) {
            dir.delete();
        }
        File[] dir2 = new File(downloadPath).listFiles();
        filesInDirectory=dir2.length;
        click(By.xpath("//table[@id='example']//tbody//tr[1]//td//a[@class='downloadfile']"),"Download",20);
    }
    public void verifyPlusIconFunctionality()
    {
        int attachmentNameFieldCountBefore=findMultipleElement(By.cssSelector("#txtAttachment"),15).size();
        verifyAddMoreFileClickable();
        int attachmentNameFieldCountAfter=findMultipleElement(By.cssSelector("#txtAttachment"),15).size();
        if(attachmentNameFieldCountBefore<attachmentNameFieldCountAfter)
        {
            getTest().log(LogStatus.PASS,"Field for add more attachment is displayed when click on the \"Add More Attachment\" icon");
            logger.info("Field for add more attachment is displayed when click on the \"Add More Attachment\" icon");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Field for add more attachment is not displayed when click on the \"Add More Attachment\" icon");
            logger.info("Field for add more attachment is not displayed when click on the \"Add More Attachment\" icon");
            takeScreenshot("MoreAttachment");
        }
    }
    public void verifyCrossIconFunctionality()
    {
        int attachmentNameFieldCountBefore=findMultipleElement(By.cssSelector("#txtAttachment"),15).size();
        verifyRemoveIconClickable();
        int attachmentNameFieldCountAfter=findMultipleElement(By.cssSelector("#txtAttachment"),15).size();
        if(attachmentNameFieldCountBefore>attachmentNameFieldCountAfter)
        {
            getTest().log(LogStatus.PASS,"Additional attachment field is not displayed as expected when click on the \"Remove More Attachment\" icon");
            logger.info("Additional attachment field is not displayed as expected when click on the \"Remove More Attachment\" icon");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Additional attachment field is not displayed as expected when click on the \"Remove More Attachment\" icon");
           logger.info("Additional attachment field is not displayed as expected when click on the \"Remove More Attachment\" icon");
           takeScreenshot("AdditionAttachment");
        }
    }
    public void verifyMinusIconFunctionality()
    {
        verifyClearFileClickable();
        String attachedFileNameAfter=getAtribute(By.xpath("//input[contains(@class,'expenseUpload')]/following-sibling::div//input"),"value",30);
        if(attachedFileNameAfter.equals(""))
        {
            getTest().log(LogStatus.PASS,"Already attached file is removed as expected when click \"Clear Attachment\" icon");
            logger.info("Already attached file is removed as expected when click \"Clear Attachment\" icon");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Already attached file is not removed as expected when click \"Clear Attachment\" icon");
           logger.info("Already attached file is not removed as expected when click \"Clear Attachment\" icon");
           takeScreenshot("ClearAttachment");
        }
    }
    public void verifyManageProductPage()
    {
        WebElement manageProductPageHeader=findElementVisibility(By.xpath("//div[@class='theme-primary partition']//span[text()='Product List']"),20);
        if(manageProductPageHeader.isDisplayed())
        {
            getTest().log(LogStatus.PASS,"Manage Product page is displayed as expected when click \"Cancel\" button");
            logger.info("Manage Product page is displayed as expected when click \"Cancel\" button");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Manage Product page is not displayed as expected when click \"Cancel\" button");
            logger.info("Manage Product page is not displayed as expected when click \"Cancel\" button");
            takeScreenshot("ManageProductPage");
        }
    }
    public void verifyPreviousButtonFunctionality()
    {
        waitForVisibilityOfElement(By.xpath("//div[@aria-labelledby='RelatedInformation-tab']//span[text()='Related Information']"),20);
        WebElement relatedInformationPage=findElementVisibility(By.xpath("//div[@aria-labelledby='RelatedInformation-tab']//span[text()='Related Information']"),20);
        if(relatedInformationPage.isDisplayed())
        {
            getTest().log(LogStatus.PASS,"Related Information page is displayed as expected when click \"Previous\" button");
           logger.info("Related Information page is displayed as expected when click \"Previous\" button");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Related Information page is not displayed as expected when click \"Previous\" button");
           logger.info("Related Information page is not displayed as expected when click \"Previous\" button");
           takeScreenshot("RelatedInformation");
        }
    }
    public void verifyDownloadedFile()
    {
        String downloadPath = Drivers.path;
        String fileName = prop.getProperty("testfileDoc");
        File dir = new File(downloadPath + fileName);
        File dir2 = new File(downloadPath);
        waitTillNewFile(dir2.toString(), filesInDirectory);
        boolean dirContents = dir.exists();
        if (dirContents) {
            getTest().log(LogStatus.PASS, "File is downloaded and exist in the folder");
            logger.info("File is downloaded and exist in the folder");
        } else {
            getTest().log(LogStatus.FAIL, "File is not exist in the folder");
            logger.info("File is not exist in the folder");
            takeScreenshot("FileDownload");
        }
    }
    public void verifyDeleteFunctionality()
    {
        int attachmentCountAfterDelete=findMultipleElement(By.xpath("//table[@id='example']//tbody//tr"),20).size();
        int expectedCount=attachmentCountBeforeDelete-1;
        if(expectedCount==attachmentCountAfterDelete)
        {
            getTest().log(LogStatus.PASS,"Attachment is removed from the list as expected when click on the \"Delete\" icon");
            logger.info("Attachment is removed from the list as expected when click on the \"Delete\" icon");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"Attachment is not removed from the list as expected when click on the \"Delete\" icon");
            logger.info("Attachment is not removed from the list as expected when click on the \"Delete\" icon");
            takeScreenshot("Delete");
        }
    }
}
