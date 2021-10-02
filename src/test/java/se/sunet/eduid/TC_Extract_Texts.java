package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_Extract_Texts {
    private StartPage startPage;
    private Login login;
    private DashBoard dashBoard;
    private PersonalInfo personalInfo;
    private EmailAddresses emailAddresses;
    private PhoneNumber phoneNumber;
    private Password password;
    private AdvancedSettings advancedSettings;
    private Identity identity;
    private Logout logout;
    private Common common;
    private InitBrowser initBrowser;

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        startPage = new StartPage(common);
        login = new Login(common);
        dashBoard = new DashBoard(common);
        personalInfo = new PersonalInfo(common);
        emailAddresses = new EmailAddresses(common);
        phoneNumber = new PhoneNumber(common);
        password = new Password(common);
        advancedSettings = new AdvancedSettings(common);
        identity = new Identity(common);
        logout = new Logout(common);

        Common.log.info("Executing: " + testContext.getName());

//        initBrowser.startHarSession(testContext.getName());
    }

    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin();
        Common.log.info(WebDriverManager.getWebDriver().findElement(By.tagName("body")).getAttribute("innerText"));
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard();
        Common.log.info(WebDriverManager.getWebDriver().findElement(By.tagName("body")).getAttribute("innerText"));
    }
/*
    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() { personalInfo.runPersonalInfo(); }

    @Test( dependsOnMethods = {"personalInfo"} )
    void emailAddresses() { emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void phoneNumber() { phoneNumber.runPhoneNumber(); }

    @Test( dependsOnMethods = {"phoneNumber"} )
    void password() { password.runPassword(); }

    @Test( dependsOnMethods = {"password"} )
    void identity() { identity.runIdentity(); }

    @Test( dependsOnMethods = {"identity"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }

    @AfterTest
    void quitBrowser() throws IOException {
//        initBrowser.stopHarSession();
        WebDriverManager.quitWebDriver(); }

        */
}
