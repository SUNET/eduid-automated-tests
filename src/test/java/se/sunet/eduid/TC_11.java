package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_11 extends BeforeAndAfter {
    @Test
    void timeoutForOTP(){
        //Sleep for OTP send interval timeout
        Common.log.info("Waiting for OTP timeout interval...");
        //common.timeoutSeconds(305);
    }

    @Test( dependsOnMethods = {"timeoutForOTP"} )
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
    void extraSecurity() {
        testData.setSendMobileOneTimePassword("yes");

        extraSecurity.runExtraSecurity(); }
}
