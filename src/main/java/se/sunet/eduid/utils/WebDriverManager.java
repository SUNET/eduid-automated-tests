package se.sunet.eduid.utils;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        if(webDriver.get() == null)
            System.out.println("Driver is null in getWebdriver");
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
            //log.warn("Catching exception in quit of browser \n" +ex);
        }finally {
            webDriver.get().quit();
        }
    }
}
