package se.sunet.eduid;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_26 extends BeforeAndAfter {
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
    void verifyPhoneNumber() {
        verifyPhoneNumber.verifyLabels(); }


    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void stopBrowser(){
        WebDriverManager.quitWebDriver();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"url", "browser", "headless", "language"})
    void startBrowser(String url, String browser, String headless, String language) throws IOException {
        initBrowser(url, browser, headless, language);
    }

    @Test( dependsOnMethods = {"startBrowser"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        common.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void requestNewPassword2() {
        common.timeoutSeconds(28);
        requestNewPassword.runRequestNewPassword(); }

    @Test( dependsOnMethods = {"requestNewPassword2"} )
    void emailSent2() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent2"} )
    void emailLink2() { emailLink.runEmailLink(); }

    @Test( dependsOnMethods = {"emailLink2"} )
    void extraSecurity2() { extraSecurity.runExtraSecurity(); }

    @Test( dependsOnMethods = {"extraSecurity2"} )
    void verifyPhoneNumber2() {
        common.verifyStatusMessage("Vi kan bara skicka en kod var 5:e minut, var god vänta innan du ber om en ny kod.");

        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("English")) {
            common.findWebElementByLinkText("English").click();
        }
        common.verifyStatusMessage("You have recently been sent a verification code. Please wait at least 5 minutes to request a new one.");

    }
}
