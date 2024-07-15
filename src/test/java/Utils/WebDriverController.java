package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;



import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverController {

    private WebDriver driver;

    public WebDriver start(String browser) {
        switch(browser) {
            case "chrome":
        	    WebDriverManager.chromedriver().setup();
        	    ChromeOptions cOptions = new ChromeOptions();
        	    cOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        	    driver = new ChromeDriver(cOptions);
        	    break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fOptions = new FirefoxOptions();
                fOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
                driver = new FirefoxDriver(fOptions);
                break;       
        }

        return driver;
    }
}