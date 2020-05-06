package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_39 {
    private StartPage startPage;
    private Register register;
    private Common common;

    private boolean acceptTerms = false;
    private boolean registerAccount = true;
    private boolean incorrectMagicCode = false;
    private boolean generateUsername = true;

    @BeforeTest
    @Parameters( {"url", "browser", "headless"})
    void initBrowser(@Optional("https://qa.test.swedenconnect.se") String url, @Optional("chrome") String browser,
                     @Optional("true") String headless, final ITestContext testContext){
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        register = new Register(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){ startPage.runStartPage(registerAccount); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(acceptTerms, incorrectMagicCode, generateUsername); }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
