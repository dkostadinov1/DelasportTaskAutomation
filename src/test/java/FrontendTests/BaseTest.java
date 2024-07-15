package FrontendTests;

import org.testng.annotations.AfterMethod;

import java.io.InputStream;

import java.time.Duration;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.yaml.snakeyaml.Yaml;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import PageObjects.HomePage;
import Utils.WebDriverController;

public abstract class BaseTest {

	private static Logger logger = LogManager.getLogger(BaseTest.class);

	private static String SYSTEM_ENV_PARAM = "env";
	private static String SYSTEM_BROWSER_PARAM = "browser";
	private static String envYamlFilePath = "config/env.yaml";

	protected static Duration defaultTimeout = Duration.ofSeconds(5);

	protected HomePage homePage;

	protected static String loginUsername;
	protected static String loginPassword;
	protected static String url;

	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	protected RemoteWebDriver driver;

	@SuppressWarnings("unchecked")
	@BeforeSuite
	public void setUpSuite() {
		String browser = System.getProperty(SYSTEM_BROWSER_PARAM) != null ? System.getProperty(SYSTEM_BROWSER_PARAM)
				: "chrome";
		String env = System.getProperty(SYSTEM_ENV_PARAM) != null ? System.getProperty(SYSTEM_ENV_PARAM) : "dev";
		logger.info("Setting browser to {}, env to {}", browser, env);
		logger.info("Starting browser");

		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(envYamlFilePath);

		Map<String, Object> environments = (Map<String, Object>) yaml.load(inputStream);
		Map<String, String> selectedEnv = (Map<String, String>) environments.get(env);

		url = selectedEnv.get("url");
		loginUsername = selectedEnv.get("username");
		loginPassword = selectedEnv.get("password");

		driver = (RemoteWebDriver) new WebDriverController().start(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(defaultTimeout);

		homePage = new HomePage(driver);

	}

	public void initializeReport() {

		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/extentSparkReport.html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Automation Test Execution Report");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

	}

	@BeforeTest
	public void startReport() {
		initializeReport();
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		test = extent.createTest(methodName, "Adding products to cart");
		test.assignCategory("SmokeTest");
	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

	@BeforeMethod
	public void setUpMethod() {
		driver.get(url);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		logger.info("Quitting browser");
	}

	public void loginToBabibet() {
		homePage.login(loginUsername, loginPassword);
	}

	public void acceptCookies() {
		homePage.acceptCookies();
	}

	public void closePopup() {
		homePage.closePopup();
	}

	public double getUserBalance() {
		return homePage.getUserBalance();
	}

}
