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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TC_5 {
    private StartPage startPage;
    private Login login;
    private DashBoard dashBoard;
    private PersonalInfo personalInfo;
    private Logout logout;
    private Common common;

    @BeforeTest
    @Parameters( {"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        dashBoard = new DashBoard(common);
        personalInfo = new PersonalInfo(common);
        logout = new Logout(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        common.setLanguage("English");
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() {
        common.setLanguage("Svenska");
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void logout() {
        logout.runLogout();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
