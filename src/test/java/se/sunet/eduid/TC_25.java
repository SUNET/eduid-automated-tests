package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_25 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void timeoutForOTP(){
        common.timeoutSeconds(26);
    }

    @Test( dependsOnMethods = {"timeoutForOTP"} )
    void login(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() { requestNewPassword.runRequestNewPassword(); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() {
        testData.setMagicCode("notTheCorrectMagicCode");
        emailLink.runEmailLink(); }
}
