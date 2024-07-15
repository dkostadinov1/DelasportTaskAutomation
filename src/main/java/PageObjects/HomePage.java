package PageObjects;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private static final Logger logger = LogManager.getLogger(HomePage.class);

	private WebDriver driver;
	private static final Duration productsPageTimeout = Duration.ofSeconds(5);

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//title[text()='18BETASIA']")
	private WebElement homePageTitle;

	@FindBy(xpath = "//*[@class='btn btn-md btn-accept-cookie']")
	private WebElement acceptCookies;

	@FindBy(xpath = "//*[@class='btn btn-sm btn-link header-login-button header-menu-button']")
	private WebElement loginBtn;

	@FindBy(xpath = "//*[@id='login_form[username]']")
	private WebElement usernameTextField;

	@FindBy(xpath = "//*[@id='login-modal-password-input']")
	private WebElement passwordTextField;

	@FindBy(xpath = "(//*[@class='btn btn-primary btn-block modal-submit-button'])[1]")
	private WebElement submitBtn;

	@FindBy(xpath = "(//button[@class='close'])[10]")
	private WebElement closePopup;

	@FindBy(xpath = "(//*[@class='user-balance-item-amount'])[1]")
	private WebElement userBalance;

	public void waitPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, productsPageTimeout);

		try {
			wait.until(ExpectedConditions.visibilityOf(homePageTitle));
		} catch (TimeoutException timeoutException) {
			logger.warn("Home Page not presented in {} seconds", productsPageTimeout);
		}
	}

	public void acceptCookies() {
		acceptCookies.click();
	}

	public void login(String username, String password) {
		loginBtn.click();
		usernameTextField.sendKeys(username);
		passwordTextField.sendKeys(password);
		submitBtn.click();
	}

	public void closePopup() {
		closePopup.click();
	}

	public double getUserBalance() {
		return Double.parseDouble(userBalance.getText().replace("€", ""));
	}

}
