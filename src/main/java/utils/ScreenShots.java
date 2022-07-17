package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

import static reporting.ComplexReportFactory.getTest;


public class ScreenShots {

    public void takeScreenShot(WebDriver driver , String methodname){
        String imagpath=System.getProperty("user.dir")+"\\src\\reports\\"+methodname;
        // generate screenshot as a file object
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            // copy file object to designated location
//            org.apache.commons.io.FileUtils.copyFile(scrFile, new File(imagpath+".png"));
            System.out.println(imagpath+".png");
        }catch(Exception e){

        }
        getTest().log(LogStatus.FAIL, getTest().addScreenCapture(imagpath+".png"));
    }
}
