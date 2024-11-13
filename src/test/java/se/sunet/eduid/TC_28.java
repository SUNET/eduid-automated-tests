package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_28 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");

        common.navigateToIdentity();
        confirmIdentity.runConfirmIdentity();
    }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout() {
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        startPage.runStartPage();
    }

    //Reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login3"} )
    void requestNewPassword() {
        requestResetPwEmail.runRequestResetPwEmail();
    }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink();
        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"emailLink"} )
    void NoExtraSecurity() {
        testData.setMfaMethod("no");
        extraSecurity.runExtraSecurity(); }

    @Test( dependsOnMethods = {"NoExtraSecurity"} )
    void setCustomPassword2() {
        //Get default password from properties
        testData.setNewPassword(testData.getPassword());

        testData.setUseRecommendedPw(false);
        password.setPassword();
    }

    @Test( dependsOnMethods = {"setCustomPassword2"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login4(){
        testData.setResetPassword(false);
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard() {
        dashBoard.runDashBoard();
    }
    @Test( dependsOnMethods = {"dashboard"} )
    void delete2() {
        common.navigateToSettings();

        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage4(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void login5(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}