package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_32 {
    private StartPage startPage;
    private Login login;
    private RequestNewPassword requestNewPassword;
    private EmailSent emailSent;
    private EmailLink emailLink;
    private Common common;

    private String username = "ove@idsec.se";
    private String password = "lq2k dvzo 917s";
    private boolean resetPassword = true;
    private boolean registerAccount = false;
    private boolean incorrectPassword = false;
    private String magicCode = "notTheCorrectMagicCode";

    @BeforeTest
    @Parameters( {"url", "browser", "headless"})
    void initBrowser(@Optional("https://qa.test.swedenconnect.se") String url, @Optional("chrome") String browser,
                     @Optional("true") String headless, final ITestContext testContext){
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        requestNewPassword = new RequestNewPassword(common);
        emailSent = new EmailSent(common);
        emailLink = new EmailLink(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){
        startPage.runStartPage(registerAccount);
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin(username, password, resetPassword, registerAccount, incorrectPassword);
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() { requestNewPassword.runRequestNewPassword(username); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink(magicCode); }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
