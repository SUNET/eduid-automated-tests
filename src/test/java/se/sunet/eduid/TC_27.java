package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_27 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void timeoutReqNewPw(){
        //Sleep for OTP send interval timeout
        Common.log.info("Waiting for Request new pw interval...");
        common.timeoutSeconds(295);
    }

    @Test( dependsOnMethods = {"timeoutReqNewPw"} )
    void login(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() {
        requestNewPassword.runRequestNewPassword(); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink(); }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecurity() {
        testData.setSendMobileOneTimePassword("already");
        extraSecurity.runExtraSecurity(); }


    @Test( dependsOnMethods = {"extraSecurity"} )
    void verifyPhoneNumber() {
        verifyPhoneNumber.runVerifyPhoneNumber(); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void verifyStatusMessage() {
        common.verifyStatusMessage("Koden har gått ut. Skicka telefonkoden igen.");

        //Switch to english
        common.timeoutMilliSeconds(900);
        common.selectEnglish();

        common.verifyStatusMessage("Phone code has expired. Please send phone code again.");
    }
}
