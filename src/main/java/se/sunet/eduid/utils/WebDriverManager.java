package se.sunet.eduid.utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        if(webDriver.get() == null) {
            Common.log.error("Driver is null in getWebdriver");
            Assert.fail("Driver is null in getWebdriver");
        }
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static void quitWebDriver() {
        try{
            webDriver.get().quit();
        }catch(Exception ex){
            Common.log.warn("Catching exception in quit of browser \n" +ex);
        }finally {
            webDriver.get().quit();
        }
    }
}
