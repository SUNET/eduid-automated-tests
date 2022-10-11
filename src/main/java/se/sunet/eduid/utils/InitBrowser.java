package se.sunet.eduid.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
//import net.lightbody.bmp.BrowserMobProxy;
//import net.lightbody.bmp.client.ClientUtil;
//import net.lightbody.bmp.core.har.Har;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InitBrowser {
    private WebDriver webDriver;
    private static final Logger log = LogManager.getLogger(InitBrowser.class);
//    public BrowserMobProxy proxy;
    public FileOutputStream fileOutputStream;
//    private Har har;

//    public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
//    public static final String AUTOMATE_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
//    public static final String USERNAME = "ovesemart_PnpdD2";
//    public static final String AUTOMATE_KEY = "EvYN352mQDEnTmbBR65R";
//    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public WebDriver initiateBrowser(String browser, String headless, String language){
        if(browser.equalsIgnoreCase("chrome"))
            initChromeDriver(headless, language);
        else if(browser.equalsIgnoreCase("firefox"))
            initFirefoxDriver(headless, language);
//        else if(browser.equalsIgnoreCase("browserstack"))
//            initBrowserStack();
        else if(browser.equalsIgnoreCase("safari"))
            initSafariDriver(headless, language);
        else
            initMobile(browser);

        //Time we will wait before retry functionality will step in
//        webDriver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);

        //If page does not respond within 35sec drop the session.
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));

        return webDriver;
    }

    private void initSafariDriver(String headless, String language){

        webDriver = new SafariDriver();

    }

    private void initChromeDriver(String headless, String language){
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Chrome instance with options
            //WebDriverManager.chromedriver().browserVersion("99").setup();
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("disable-infobars");
            //chromeOptions.addArguments("--lang=" +language);
            //chromeOptions.setExperimentalOption("w3c", false);

            // If execution should be performed headless
            if (headless.equals("true")) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--lang=" +language);
            }

            /* For capture of .har traffic - see TC_1 for example, also comment webdriver.getUrl in Webdrivermanager
            // start the proxy
            proxy = new BrowserMobProxyServer();
            //proxy.setTrustAllServers(true);
            proxy.start(0);


            // get the Selenium proxy object
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            seleniumProxy.setSslProxy("localhost:" + proxy.getPort());
            chromeOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
            chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            chromeOptions.addArguments("--ignore-certificate-errors");
               */

            webDriver = new ChromeDriver(chromeOptions);

            /* For capture of .har traffic - see TC_1 for example, also comment webdriver.getUrl in Webdrivermanager
            // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
            proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
            proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS);
            */

        }
        else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("Platform", "LINUX");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--lang=" +language);

            try {
                webDriver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), chromeOptions);
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
                firefoxOptions.setHeadless(true);

            webDriver = new FirefoxDriver(firefoxOptions);
        } else {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("Platform", "LINUX");
            firefoxOptions.setCapability("marionette", true);
            firefoxOptions.setCapability("language", language);

            try {
                webDriver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), firefoxOptions);
            } catch (Exception ex) {
                log.error("Firefox driver initialization exception: " + ex);
            }
        }
    }
/*
    public void startHarSession(String tc) throws IOException {
        testcase = tc;
        // create a new HAR
        proxy.newHar();

        // open eduid
        webDriver.get("https://dev.eduid.se");

        // get the HAR data
        har = proxy.getHar();
    }

    public void stopHarSession() throws IOException {
        Instant timestamp = Instant.now();
        fileOutputStream = new FileOutputStream("har/" +testcase + "-" +timestamp + ".har");
        har.writeTo(fileOutputStream);

        fileOutputStream.close();
        proxy.stop();
    }
*/
    private void initMobile(String phoneType){
        //Create Chrome instance with options
        WebDriverManager.chromedriver().setup();

        //Create Chrome instance with options
        /*
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 1078);
        deviceMetrics.put("height", 924);
        deviceMetrics.put("pixelRatio", 3.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 8.0.0;" +
                        "Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) " +
        "Chrome/67.0.3396.99 Mobile Safari/537.36");
        */

        /*
        //Iphone X
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
                "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
*/

        Map<String, String> mobileEmulation = new HashMap<>();
        //mobileEmulation.put("orientation", "LANDSCAPE");
        mobileEmulation.put("deviceName", phoneType);
        //Galaxy S5, Nexus 5
//        mobileEmulation.put("orientation", "landscape");
        //mobileEmulation.put("deviceName", "iPhone 7");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        webDriver = new ChromeDriver(chromeOptions);


    }

/*    private void initBrowserStack() {
        Common.log.info("init browser stack with user/passwd: " +USERNAME +" : " +AUTOMATE_KEY);
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //True when browser, false on mobile?
        capabilities.setCapability("browserstack.local", "true");

        *//* Chrome latest
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("browserstack.debug", "true");  // for enabling visual logs
        *//*
        capabilities.setCapability("browser", "safari");
        capabilities.setCapability("browser_version", "latest");
        capabilities.setCapability("os", "OS X");
        capabilities.setCapability("os_version", "Catalina");
*//*
        capabilities.setCapability("browser", "ie");
        capabilities.setCapability("browser_version", "11.0");
        capabilities.setCapability("os", "Windows");
        capabilities.setCapability("os_version", "8.1");
*//*
        *//* Mobile IOS
        capabilities.setCapability("os_version", "12");
        capabilities.setCapability("device", "iPhone 8 Plus");
        capabilities.setCapability("real_mobile", "true");
        *//*

        *//* Mobile Samsung
        capabilities.setCapability("device", "Samsung Galaxy S10e");
        capabilities.setCapability("os_version", "9.0");
        capabilities.setCapability("browserName", "android");
        capabilities.setCapability("realMobile", "true");
*//*
//        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        //browserstackOptions.put("os", "Windows");
        //browserstackOptions.put("osVersion", "10");

//        capabilities.setCapability("bstack:options", browserstackOptions);

        try {
            webDriver = new RemoteWebDriver(new URL(URL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }*/
}