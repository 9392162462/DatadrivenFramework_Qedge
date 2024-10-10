package config;

	import java.time.Duration;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.support.PageFactory;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;

	import applicationLayer.Com.Page.LoginPage;
	import applicationLayer.Com.Page.LogoutPage;

	public class AppUtil1 {
	public static WebDriver driver;
	@BeforeTest
	public static void setUp()
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://webapp.qedgetech.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		LoginPage login =PageFactory.initElements(driver, LoginPage.class);
		login.adminLogin("admin", "master");
	}
	@AfterTest
	public static void tearDown()
	{
		LogoutPage logout =PageFactory.initElements(driver, LogoutPage.class);
		logout.adminLogout();
		driver.quit();
	}
	}
