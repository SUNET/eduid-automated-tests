package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_86 extends BeforeAndAfter {
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
    void account() { account.runAccount(); }

    @Test( dependsOnMethods = {"account"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setReLogin(true);
        testData.setIncorrectPassword(true);

        login.runLogin();

        testData.setIncorrectPassword(false);
    }

    //Init reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"verifyAccountDeleted"} )
    void resetPassword(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"resetPassword"} )
    void requestNewPassword() {
        requestResetPwEmail.runRequestResetPwEmail();
    }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() {
        emailLink.runEmailLink();
        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecurityBankId() {
        testData.setMfaMethod("bankid");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"extraSecurityBankId"} )
    void verifyBankId() {
        common.verifyBankIdTextAndLabels();
    }

    //Reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"verifySamlFailPage"} )
    void login6(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login6"} )
    void requestNewPassword2() {
        requestResetPwEmail.runRequestResetPwEmail();
    }

    @Test( dependsOnMethods = {"requestNewPassword2"} )
    void emailSent2() {
        testData.setResetPasswordNewSession(true);
        emailSent.runEmailSent();
    }

    @Test( dependsOnMethods = {"emailSent2"} )
    void emailLink2() { emailLink.runEmailLink();
        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"emailLink2"} )
    void extraSecurityFreja() {
        testData.setMfaMethod("freja");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"extraSecurityFreja"} )
    void selectIdRefIdp() {
        common.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void setRecommendedPassword2() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword2"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login4(){
        testData.setReLogin(true);
        testData.setResetPassword(false);

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard() {
        //Account is verified
        testData.setAccountVerified(true);

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void identity() {
        //Verify that identity is still confirmed
        common.navigateToIdentity();

        String pageBody = common.getPageBody();
        common.verifyPageBodyContainsString(pageBody, "Ditt eduID är redo att användas");
        common.verifyPageBodyContainsString(pageBody, "Följande identiteter är nu kopplade till ditt eduID");
        common.verifyPageBodyContainsString(pageBody, "Svenskt personnummer");
    }

    //Delete account when confirmed that identity is no longer verified
    @Test( dependsOnMethods = {"identity"} )
    void delete2() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage4(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void login5(){
        testData.setIncorrectPassword(true);

        login.runLogin();
    }
}