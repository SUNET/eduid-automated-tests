package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.DashBoard;
import se.sunet.eduid.dashboard.EmailAddresses;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_12 {
    private StartPage startPage;
    private Login login;
    private DashBoard dashBoard;
    private EmailAddresses emailAddresses;
    private Logout logout;
    private Common common;

    private String username = "ove@idsec.se";
    private String password = "lq2k dvzo 917s";
    private boolean resetPassword = false;
    private boolean registerAccount = false;
    private boolean incorrectPassword = false;
    private String givenName_Dashboard = "";
    private String surName_Dashboard = "";
    private String language_Dashboard = "";
    private boolean removePrimary = false;
    private boolean removeNewEmail1 = false;
    private String addNewEmail1 = "eduidtest.se1@gmail.com";
    private String confirmNewEmail1 = "wrongCode";

    @BeforeTest
    @Parameters( {"url", "browser", "headless"})
    void initBrowser(@Optional("https://qa.test.swedenconnect.se") String url, @Optional("chrome") String browser,
                     @Optional("true") String headless, final ITestContext testContext){
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        dashBoard = new DashBoard(common);
        emailAddresses = new EmailAddresses(common);
        logout = new Logout(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage2(){
        startPage.runStartPage2(registerAccount);
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.runLogin2(username, password, resetPassword, registerAccount, incorrectPassword);
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() {
        dashBoard.runDashBoard2(givenName_Dashboard, surName_Dashboard, language_Dashboard);
   }

    @Test( dependsOnMethods = {"dashboard2"} )
    void emailAddresses2() { emailAddresses.runEmailAddresses2(removePrimary, removeNewEmail1, addNewEmail1, confirmNewEmail1); }

    @Test( dependsOnMethods = {"emailAddresses2"} )
    void logout2() {
        logout.runLogout2();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
