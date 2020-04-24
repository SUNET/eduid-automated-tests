package se.sunet.eduid.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class InitBrowser {
    private WebDriver webDriver;
    private static final Logger log = LogManager.getLogger(InitBrowser.class);

    public WebDriver initiateBrowser(String browser, String headless){
        if(browser.equalsIgnoreCase("chrome"))
            initChromeDriver(headless);
        else
            initFirefoxDriver(headless);

        //Time we will wait before retry functionality will step in
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //If page does not respond within 35sec drop the session.
        webDriver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);

        return webDriver;
    }
    
    private void initChromeDriver(String headless){
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Chrome instance with options
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("disable-infobars");
            chromeOptions.addArguments("--lang=sv");

            // Below did not help selecting the certificate for Spain
            chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

            // If execution should be performed headless
            if (headless.equals("true")) {
                chromeOptions.addArguments("--headless");
            }

            webDriver = new ChromeDriver(chromeOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("Platform", "LINUX");
            chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--lang=sv");

            try {
                webDriver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), chromeOptions);
            } catch (Exception ex) {
                log.error("Chrome driver initialization exception: " + ex);
            }
        }
    }

    private void initFirefoxDriver(String headless) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Firefox instance with options
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--lang=sv");

            // If execution should be performed headless
            if (headless.equals("true"))
                firefoxOptions.setHeadless(true);

            webDriver = new FirefoxDriver(firefoxOptions);
        } else {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("Platform", "LINUX");
            firefoxOptions.setCapability("marionette", true);
            firefoxOptions.setCapability("language", "sv");

            try {
                webDriver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), firefoxOptions);
            } catch (Exception ex) {
                log.error("Firefox driver initialization exception: " + ex);
            }
        }
    }
}