package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.DashBoard;
import se.sunet.eduid.dashboard.PersonalInfo;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_4 {
    private StartPage startPage;
    private Login login;
    private DashBoard dashBoard;
    private PersonalInfo personalInfo;
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
    private String givenName = "ove";
    private String surName = "semart";
    private String displayName = "ove semart";
    private String language = "English";

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
        personalInfo = new PersonalInfo(common);
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
    void personalInfo2() {
        personalInfo.runPersonalInfo2(givenName, surName, displayName, language);
    }

    @Test( dependsOnMethods = {"personalInfo2"} )
    void logout2() {
        logout.runLogout2();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
