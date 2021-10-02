package se.sunet.eduid;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class Mobile_1 {
    private StartPage startPage;
    private Login login;
    private Logout logout;
    private Common common;
    private InitBrowser initBrowser;
    JavascriptExecutor js;

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        logout = new Logout(common);

        js = (JavascriptExecutor) WebDriverManager.getWebDriver();

        Common.log.info("Executing: " + testContext.getName());

//        initBrowser.startHarSession(testContext.getName());
    }

    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        common.timeoutMilliSeconds(700);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        common.timeoutMilliSeconds(700);
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();

        common.timeoutMilliSeconds(700);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        common.timeoutMilliSeconds(700);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void identity() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/span").click();
        common.timeoutMilliSeconds(700);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        common.timeoutMilliSeconds(700);
    }


    @Test( dependsOnMethods = {"identity"} )
    void advancedSettings() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span").click();
        common.timeoutMilliSeconds(700);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        common.timeoutMilliSeconds(700);
    }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout();
        common.timeoutMilliSeconds(700);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        common.timeoutMilliSeconds(700);
    }

    @AfterTest
    void quitBrowser() throws IOException {
//        initBrowser.stopHarSession();
        WebDriverManager.quitWebDriver(); }
}
