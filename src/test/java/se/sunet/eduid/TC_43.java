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
import se.sunet.eduid.supportTool.RegisteredData;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;
import java.time.LocalDate;

public class TC_43 {
    private StartPage startPage;
    private Register register;
    private ConfirmHuman confirmHuman;
    private ConfirmedNewAccount confirmedNewAccount;
    private Login login;
    private ConfirmIdentity confirmIdentity;
    private PhoneNumber phoneNumber;
    private DashBoard dashBoard;
    private DeleteAccount deleteAccount;
    private AdvancedSettings advancedSettings;
    private Logout logout;
    private RegisteredData registeredData;
    private Common common;
    private LocalDate localDate = LocalDate.now();

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
        logout = new Logout(common);
        advancedSettings = new AdvancedSettings(common);
        registeredData = new RegisteredData(common);

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

    @Test( dependsOnMethods = {"login"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void advancedSettings(){ advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout(){ logout.runLogout(); }

    //Navigate to the support tool
    @Test( dependsOnMethods = {"logout"} )
    void startPageSupport(){
        WebDriverManager.getWebDriver().navigate().to("https://support.dev.eduid.se");
    }

    //Log in to the support tool
    @Test( dependsOnMethods = {"startPageSupport"} )
    void loginSupport(){
        common.findWebElementById("username").sendKeys(common.getSupportUsername());
        common.findWebElementById("password").sendKeys("d72o cqhd hnqc");

        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();
    }

    //Search for newly created account
    @Test( dependsOnMethods = {"loginSupport"} )
    void searchAndVerifyUserData(){
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(common.getEppn());
        common.findWebElementByXpath("//div/form/div/p[2]/button").click();

        registeredData.runRegisteredData();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"searchAndVerifyUserData"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard() { dashBoard.pressSettings(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }

    //Navigate to the support tool again
    @Test( dependsOnMethods = {"login3"} )
    void startPageSupport2(){
        WebDriverManager.getWebDriver().navigate().to("https://support.dev.eduid.se");
    }

    //Log in to the support tool
    @Test( dependsOnMethods = {"startPageSupport2"} )
    void loginSupport2(){
        common.findWebElementById("username").sendKeys(common.getSupportUsername());
        common.findWebElementById("password").sendKeys("d72o cqhd hnqc");

        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();
    }

    //Search for the deleted account and check that terminated timestamp is set with todays date
    @Test( dependsOnMethods = {"loginSupport2"} )
    void searchAndVerifyUserData2(){
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(common.getEppn());
        common.findWebElementByXpath("//div/form/div/p[2]/button").click();

        //Verify Terminated Status contains todays timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[10]/td", String.valueOf(localDate));
    }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
