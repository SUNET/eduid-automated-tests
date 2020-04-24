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

public class TC_28 {
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
    private boolean resendOTP = false;
    private boolean useCustomPassword = false;
    private String newPasswd = "Test?=59(GG1234%€#\\";

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
    void startPage2(){
        startPage.runStartPage2(registerAccount);
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.runLogin2(username, password, resetPassword, registerAccount, incorrectPassword);
    }

    @Test( dependsOnMethods = {"login2"} )
    void requestNewPassword2() { requestNewPassword.runRequestNewPassword2(username); }

    @Test( dependsOnMethods = {"requestNewPassword2"} )
    void emailSent2() { emailSent.runEmailSent2(); }

    @Test( dependsOnMethods = {"emailSent2"} )
    void emailLink2() { emailLink.runEmailLink2(magicCode); }

    @Test( dependsOnMethods = {"emailLink2"} )
    void extraSecurity2() { extraSecurity.runExtraSecurity2(sendMobileOneTimePassword); }

    @Test( dependsOnMethods = {"extraSecurity2"} )
    void verifyPhoneNumber2() { verifyPhoneNumber.runVerifyPhoneNumber2(resendOTP); }

    @Test( dependsOnMethods = {"verifyPhoneNumber2"} )
    void newPassword2() { newPassword.runNewPassword2(useCustomPassword, newPasswd); }

    @Test( dependsOnMethods = {"newPassword2"} )
    void passwordChanged2() { passwordChanged.runPasswordChanged2(username, newPasswd); }

    @Test( dependsOnMethods = {"passwordChanged2"} )
    void logout2() {
        logout.runLogout2();
    }

    @AfterTest
    void quitBrowser(){
        WebDriverManager.quitWebDriver();
    }
}
