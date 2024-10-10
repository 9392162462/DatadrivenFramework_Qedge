package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;


public class DriverScript extends AppUtil {
String inputpath ="./FileInput/TestData.xlsx";
String outputpath ="./FileOutput/datadrivenResults.xlsx";
ExtentReports reports;
ExtentTest logger;
@Test
public void starttest() throws Throwable
{
	//define path of html
	
	reports = new ExtentReports("./ExtentReports/Login.html");
	//create obejct for excel file util class
	
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in login sheet
	
	int rc =xl.rowCount("Login");
	Reporter.log("No of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger =reports.startTest("Login Test");
		String username = xl.getCellData("Login", i, 0);
		String password = xl.getCellData("Login", i, 1);
		logger.log(LogStatus.INFO, username+"-----"+password);
		//call login method from function libraray class
		
		boolean res =FunctionLibrary.adminLogin(username, password);
		if(res)
		{
			xl.setCellData("Login", i, 2, "Valid username and password", outputpath);
			xl.setCellData("Login", i, 3, "pass", outputpath);
			logger.log(LogStatus.PASS, "Valid username and password");
		}
		else
		{
			//take screen shot
			
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
			xl.setCellData("Login", i, 2, "Invalid username and password", outputpath);
			xl.setCellData("Login", i, 3, "Fail", outputpath);
			logger.log(LogStatus.FAIL, "Invalid username and password");
		}
		reports.endTest(logger);
		reports.flush();
		
	}
}
}









