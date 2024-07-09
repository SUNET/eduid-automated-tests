package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_24 extends BeforeAndAfter {
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
        testData.setResendOTP(true);
        verifyPhoneNumber.runVerifyPhoneNumber(); }

    @Test( dependsOnMethods = {"verifyPhoneNumber"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
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
        testData.setNewPassword("lq2k dvzo 917s");
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout() {
        logout.runLogout();
    }
}
