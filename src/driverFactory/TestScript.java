package driverFactory;
	

import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import applicationLayer.Com.Page.CustomerPage;
import config.AppUtil1;
import utilities.ExcelFileUtil;

	public class TestScript extends AppUtil1 {
	String inputpath ="./FileInput/CustomerData.xlsx";
	String outputpath ="./FileOutput/CustomerResults.xlsx";
	String TCSheet ="Customer";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable
	{
		report = new ExtentReports("./ExtentReports/Customer.html");
		//create object for excelfileutil class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//count no of rows in TCSheet
		int rc =xl.rowCount(TCSheet);
		Reporter.log("No of rows are::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			logger = report.startTest("Customer");
			logger.assignAuthor("HARI");
			//read cell from sheet
			String cname =xl.getCellData(TCSheet, i, 0);
			String Address =xl.getCellData(TCSheet, i, 1);
			String city =xl.getCellData(TCSheet, i, 2);
			String country =xl.getCellData(TCSheet, i, 3);
			String cperson =xl.getCellData(TCSheet, i, 4);
			String pnumber =xl.getCellData(TCSheet, i, 5);
			String email =xl.getCellData(TCSheet, i, 6);
			String mnumber =xl.getCellData(TCSheet, i, 7);
			String notes =xl.getCellData(TCSheet, i, 8);
			logger.log(LogStatus.INFO, cname+"    "+Address+"    "+city+"   "+country+"    "+cperson+"    "+pnumber+"     "+email+"     "+mnumber+"     "+notes);
			//SearchContext driver = null;
			CustomerPage customeradd =PageFactory.initElements(driver, CustomerPage.class);
			boolean res =customeradd.addCustomer(cname, Address, city, country, cperson, pnumber, email, mnumber, notes);
			if(res)
			{
				xl.setCellData(TCSheet, i, 9, "Pass", outputpath);
				logger.log(LogStatus.PASS, "Customer Add Success");
			}
			else
			{
				xl.setCellData(TCSheet, i, 9, "Fail", outputpath);
				logger.log(LogStatus.FAIL, "Customer Add UnSuccess");
			}
			report.endTest(logger);
			report.flush();
		}
		
	}
	}

