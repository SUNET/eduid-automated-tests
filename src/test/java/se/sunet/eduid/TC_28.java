package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_28 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void timeoutForOTP(){
        //Sleep for OTP send interval timeout
        Common.log.info("Waiting for OTP timeout interval...");
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
    void emailLink() { emailLink.runEmailLink(); }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecurity() {
        testData.setSendMobileOneTimePassword("no");
        extraSecurity.runExtraSecurity(); }

    @Test( dependsOnMethods = {"extraSecurity"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
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
    void initPwChange2() {
        initPwChange.runInitPwChange();
    }

    @Test( dependsOnMethods = {"initPwChange2"} )
    void loginPwChange2(){
        //Check first if the incorrect password flag is set, then we need to re-set it after login.
        boolean tempIncorrectPassword = testData.isIncorrectPassword();
        testData.setIncorrectPassword(false);

        //Enter userName and password since we need to login again before pw change
        login.enterPassword();
        login.signIn();

        testData.setIncorrectPassword(tempIncorrectPassword);
    }

    @Test( dependsOnMethods = {"loginPwChange2"} )
    void password2() {
        testData.setNewPassword("lq2k dvzo 917s");
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout() {
        logout.runLogout();
    }
}
