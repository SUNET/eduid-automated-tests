package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TC_35 {
    private StartPage startPage;
    private Register register;
    private Common common;

    @BeforeTest
    @Parameters( {"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        register = new Register(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){
        common.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        common.setAcceptTerms(false);
        register.runRegister(); }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
