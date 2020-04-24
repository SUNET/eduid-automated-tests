package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_31 {
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

    private String username = "ove@idsec.se";
    private String password = "lq2k dvzo 917s";
    private boolean resetPassword = true;
    private boolean registerAccount = false;
    private boolean incorrectPassword = false;
    private String magicCode = "mknhKYFl94fJaWaiVk2oG9Tl";
    private boolean sendMobileOneTimePassword = true;
    private boolean resendOTP = true;
    private boolean useCustomPassword = true;
    private String newPasswd = "lq2k dvzo 917s";

    @BeforeTest
    @Parameters( {"url", "browser", "headless"})
    void initBrowser(@Optional("https://qa.test.swedenconnect.se") String url, @Optional("chrome") String browser,
                     @Optional("true") String headless, final ITestContext testContext){
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless), url);

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
        startPage.runStartPage(registerAccount);
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin(username, password, resetPassword, registerAccount, incorrectPassword);
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() { requestNewPassword.runRequestNewPassword(username); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink(magicCode); }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecurity() { extraSecurity.runExtraSecurity(sendMobileOneTimePassword); }

    @Test( dependsOnMethods = {"extraSecurity"} )
    void verifyPhoneNumber() { verifyPhoneNumber.runVerifyPhoneNumber(resendOTP); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void newPassword() { newPassword.runNewPassword(useCustomPassword, newPasswd); }

    @Test( dependsOnMethods = {"newPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(username, newPasswd); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void logout() {
        logout.runLogout();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
