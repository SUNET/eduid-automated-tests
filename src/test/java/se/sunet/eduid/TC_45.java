package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.AdvancedSettings;
import se.sunet.eduid.dashboard.DashBoard;
import se.sunet.eduid.dashboard.DeleteAccount;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.ConfirmHuman;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_45 {
    private StartPage startPage;
    private Register register;
    private ConfirmHuman confirmHuman;
    private ConfirmedNewAccount confirmedNewAccount;
    private Login login;
    private DashBoard dashBoard;
    private DeleteAccount deleteAccount;
    private Logout logout;
    private Common common;
    private Swamid swamid;
    private AdvancedSettings advancedSettings;

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
        logout = new Logout(common);
        swamid = new Swamid(common);
        advancedSettings = new AdvancedSettings(common);

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

    @Test( dependsOnMethods = {"login"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings();
    common.setEmail(common.getUsername().toLowerCase());
    }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void navigateToSwamid(){
        common.navigateToUrl("https://sp.swamid.se/Shibboleth.sso/DS/swamid-test?target=https://sp.swamid.se/secure/");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid(false);
    }

    @Test( dependsOnMethods = {"swamid"} )
    void startPage2() {
        common.navigateToUrl("https://dev.eduid.se");

        startPage.runStartPage();
        common.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){ login.runLogin(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"login2"} )
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
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
