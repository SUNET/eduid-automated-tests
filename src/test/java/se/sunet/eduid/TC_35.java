package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.resetPassword.ConfirmPhoneNumber;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_35 {
    private StartPage startPage;
    private Login login;
    private ConfirmPhoneNumber confirmPhoneNumber;
    private Logout logout;
    private Common common;

    private String username = "ove@idsec.se";
    private String password = "lq2k dvzo 917s";
    private boolean resetPassword = false;
    private boolean registerAccount = false;
    private boolean incorrectPassword = false;
    private String confirmPhoneNumberCode = "mknhKYFl94fJaWaiVk2oG9Tl";

    @BeforeTest
    @Parameters( {"url", "browser", "headless"})
    void initBrowser(@Optional("https://qa.test.swedenconnect.se") String url, @Optional("chrome") String browser,
                     @Optional("true") String headless, final ITestContext testContext){
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        confirmPhoneNumber = new ConfirmPhoneNumber(common);
        logout = new Logout(common);

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
    void confirmPhoneNumber() { confirmPhoneNumber.runConfirmPhoneNumber(false, confirmPhoneNumberCode); }

    @Test( dependsOnMethods = {"confirmPhoneNumber"} )
    void logout() {
        logout.runLogout();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
