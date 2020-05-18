package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_25 {
    private StartPage startPage;
    private Login login;
    private RequestNewPassword requestNewPassword;
    private EmailSent emailSent;
    private EmailLink emailLink;
    private ExtraSecurity extraSecurity;
    private VerifyPhoneNumber verifyPhoneNumber;
    private NewPassword newPassword;
    private PasswordChanged passwordChanged;
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
        requestNewPassword = new RequestNewPassword(common);
        emailSent = new EmailSent(common);
        emailLink = new EmailLink(common);
        extraSecurity = new ExtraSecurity(common);
        verifyPhoneNumber = new VerifyPhoneNumber(common);
        newPassword = new NewPassword(common);
        passwordChanged = new PasswordChanged(common);
        logout = new Logout(common);

        System.out.println("Executing: " +testContext.getName());
    }

    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        common.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() { requestNewPassword.runRequestNewPassword(); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink(); }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecurity() { extraSecurity.runExtraSecurity(); }

    @Test( dependsOnMethods = {"extraSecurity"} )
    void verifyPhoneNumber() { verifyPhoneNumber.runVerifyPhoneNumber(); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void newPassword() { newPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void logout() {
        logout.runLogout();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
