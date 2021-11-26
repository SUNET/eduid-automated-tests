package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_11 extends BeforeAndAfter {
    @Test
    void timeoutForOTP(){
        //Sleep for OTP send interval timeout
        Common.log.info("Waiting for OTP timeout interval...");
        common.timeoutSeconds(605);
    }

    @Test( dependsOnMethods = {"timeoutForOTP"} )
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
    void extraSecurity() {
        common.setSendMobileOneTimePassword("yes");
        extraSecurity.runExtraSecurity(); }

    @Test( dependsOnMethods = {"extraSecurity"} )
    void verifyPhoneNumber() {
        common.setSendMobileOneTimePassword("already");
        verifyPhoneNumber.runVerifyPhoneNumber(); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void verifyStatusMessage() {
        common.verifyStatusMessage("Felaktig telefonkod.");

        //Switch to english
        common.findWebElementByLinkText("English").click();

        common.verifyStatusMessage("Incorrect phone code.");
    }
}
