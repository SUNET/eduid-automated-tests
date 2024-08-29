package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_54 extends BeforeAndAfter {
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
        //Use the 2nd added mobile number
        testData.setSendMobileOneTimePassword("3");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"extraSecurity"} )
    void verifyPhoneNumber() { verifyPhoneNumber.runVerifyPhoneNumber(); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login2(){
        testData.setResetPassword(false);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void changeToDefaultPassword(){}

    @Test( dependsOnMethods = {"changeToDefaultPassword"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void password2() {
        testData.setUseRecommendedPw(false);
        testData.setNewPassword("lq2k dvzo 917s");
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout() {
        logout.runLogout();
    }
}
