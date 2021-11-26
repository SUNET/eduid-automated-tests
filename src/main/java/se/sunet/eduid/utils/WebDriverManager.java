package se.sunet.eduid.utils;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        if(webDriver.get() == null)
            Common.log.info("Driver is null in getWebdriver");
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver, String url) {
        webDriver.set(driver);
        webDriver.get().get(url);
    }

    public static void quitWebDriver() {
        try{
            webDriver.get().quit();
        }catch(Exception ex){
            Common.log.warn("Quit browser exception: " +ex);
        }finally {
            webDriver.get().quit();
        }
    }
}
