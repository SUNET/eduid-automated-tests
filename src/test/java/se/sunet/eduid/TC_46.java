package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.*;
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

public class TC_46 {
    private StartPage startPage;
    private Register register;
    private ConfirmHuman confirmHuman;
    private ConfirmedNewAccount confirmedNewAccount;
    private Login login;
    private PersonalInfo personalInfo;
    private DeleteAccount deleteAccount;
    private Common common;
    private SecurityKey securityKey;

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
        deleteAccount = new DeleteAccount(common);
        personalInfo = new PersonalInfo(common);
        securityKey = new SecurityKey(common);

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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    //First without any personal info saved
    @Test( dependsOnMethods = {"login"} )
    void securityKey1() {
        common.setAddSecurityKey(false);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"securityKey1"} )
    void personalInfo() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        personalInfo.runPersonalInfo(); }

    @Test( dependsOnMethods = {"personalInfo"} )
    void securityKey2() {
        common.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"securityKey2"} )
    void delete() {
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/");
        common.timeoutSeconds(2);
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
