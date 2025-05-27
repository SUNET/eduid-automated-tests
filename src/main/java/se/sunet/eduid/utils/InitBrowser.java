package se.sunet.eduid.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InitBrowser {
    private WebDriver webDriver;
    private static final Logger log = LogManager.getLogger(InitBrowser.class);

    public WebDriver initiateBrowser(String browser, String headless, String language){
        if(browser.equalsIgnoreCase("chrome"))
            initChromeDriver(headless, language);
        else if(browser.equalsIgnoreCase("firefox"))
            initFirefoxDriver(headless, language);
        else if(browser.equalsIgnoreCase("safari"))
            initSafariDriver(headless, language);
        else
            initMobile(browser);

        //Time we will wait before retry functionality will step in
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(11));

        //If page does not respond within 35sec drop the session.
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        return webDriver;
    }

    private void initSafariDriver(String headless, String language){


        // Initialize Safari Driver
        WebDriverManager.safaridriver().setup();

        SafariOptions safariOptions = new SafariOptions();

        // Create an instance of SafariDriver
        webDriver = new SafariDriver();
        webDriver.manage().window().setSize(new Dimension(1200, 800));
    }

    private void initChromeDriver(String headless, String language){
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Chrome instance with options
            WebDriverManager.chromedriver().clearDriverCache().setup();
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("disable-infobars");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-search-engine-choice-screen");

            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");

            // If execution should be performed headless
            if (headless.equals("true")) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--lang=" +language);

                chromeOptions.addArguments("window-size=1920,1080");
                chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

            }
            webDriver = new ChromeDriver(chromeOptions);
        }
        else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("platformName", "LINUX");
            chromeOptions.setCapability("acceptInsecureCerts", true);
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--lang=" + language);
            chromeOptions.addArguments("--accept-lang" + language);
            chromeOptions.addArguments("--disable-search-engine-choice-screen");

            try {
                webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } catch (Exception ex) {
                log.error("Chrome driver initialization exception: " + ex);
            }
        }
    }

    private void initFirefoxDriver(String headless, String language) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Firefox instance with options
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--lang=" +language);

            // If execution should be performed headless
            if (headless.equals("true"))
                firefoxOptions.addArguments("--headless");

            webDriver = new FirefoxDriver(firefoxOptions);
        } else {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("platformName", "LINUX");
            firefoxOptions.addArguments("--ignore-certificate-errors");
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("intl.accept_languages", "sv");
            firefoxOptions.setProfile(profile);

            try {
                webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
            } catch (Exception ex) {
                log.error("Firefox driver initialization exception: " + ex);
            }
        }
    }

    private void initMobile(String phoneType){
        //Create Chrome instance with option
        WebDriverManager.chromedriver().setup();

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", phoneType);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        webDriver = new ChromeDriver(chromeOptions);
    }
}