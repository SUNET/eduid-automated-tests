package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.DashBoard;
import se.sunet.eduid.dashboard.DeleteAccount;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.ConfirmHuman;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.RetryAndScreenShot;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TC_38 {
    private StartPage startPage;
    private Register register;
    private ConfirmHuman confirmHuman;
    private ConfirmedNewAccount confirmedNewAccount;
    private Login login;
    private DashBoard dashBoard;
    private DeleteAccount deleteAccount;
    private Common common;


    @BeforeTest
    @Parameters( {"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        register = new Register(common);
        confirmedNewAccount = new ConfirmedNewAccount(common);
        confirmHuman = new ConfirmHuman(common);
        login = new Login(common);
        dashBoard = new DashBoard(common);
        deleteAccount = new DeleteAccount(common);

        Common.log.info("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){
        common.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() {
        confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        common.setDisplayName("inget namn sparat");
        common.setGivenName("inget");
        common.setSurName("namn");
        common.setPersonalNumber("lägg till personnummer");
        common.setPhoneNumber("inget telefonnummer sparat");
        common.setEmail(common.getUsername());

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
