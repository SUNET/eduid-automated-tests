package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.ConfirmIdentity;
import se.sunet.eduid.dashboard.DashBoard;
import se.sunet.eduid.dashboard.DeleteAccount;
import se.sunet.eduid.dashboard.PhoneNumber;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.ConfirmHuman;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_98 {
    private StartPage startPage;
    private Register register;
    private ConfirmHuman confirmHuman;
    private ConfirmedNewAccount confirmedNewAccount;
    private Login login;
    private ConfirmIdentity confirmIdentity;
    private PhoneNumber phoneNumber;
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
        confirmIdentity = new ConfirmIdentity(common);
        phoneNumber = new PhoneNumber(common);
        dashBoard = new DashBoard(common);
        deleteAccount = new DeleteAccount(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void confirmPhoneNumber(){
        dashBoard.pressSettings();
        phoneNumber.confirmNewPhoneNumber(); }

//    @Test( dependsOnMethods = {"confirmPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
