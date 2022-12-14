package se.sunet.eduid;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_26 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setResetPassword(true);
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
        common.getWebDriver().quit();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    void startBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {
        initBrowser(url, browser, headless, language,testsuite);

        testData.setTestSuite(testsuite);
        testData.setTestCase("TC_26");
    }

    @Test( dependsOnMethods = {"startBrowser"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setResetPassword(true);
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

        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("English")) {
            common.selectEnglish();
        }
        common.verifyStatusMessage("You have recently been sent a code. Please wait at least 5 minutes to request a new one.");

    }
}
