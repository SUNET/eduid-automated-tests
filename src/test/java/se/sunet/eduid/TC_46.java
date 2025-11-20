package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_46 extends BeforeAndAfter {
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
    void addSecurityKey() {
        testData.setAddSecurityKey(true);
        testData.setVerifySecurityKeyByFreja(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() {
        testData.setMfaUserDeclinedConsentAuthentication(true);
        startPage.runStartPage();
    }
    //TODO - test case not completed press abort at the security log when mfa, maybe it can be done by setting user has NOT verified in virtural auth

/*    @Test( dependsOnMethods = {"startPage"} )
    void login2(){ login.runLogin(); }*/

    /*
    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void extraSecuritySecurityKey2() {
        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with securitykey");
    }

    @Test( dependsOnMethods = {"extraSecuritySecurityKey2"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void extraSecuritySecurityKey3() {
        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with securitykey");
    }


    @Test( dependsOnMethods = {"extraSecuritySecurityKey3"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        login.signIn();
    }

    @Test( dependsOnMethods = {"verifyAccountDeleted"} )
    void clickReqPwLink(){
        //Navigate to the reset password page by click on link on deleted account information page
        common.findWebElementByXpath("//*[@id=\"content\"]/section/div/button").click();
    }

    //Init reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"clickReqPwLink"} )
    void requestNewPassword() {
        common.selectSwedish();
        requestNewPassword.runRequestNewPassword();
    }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() {
        emailLink.runEmailLink();
    }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecuritySecurityKey() {
        //securityKey.virtualAuthenticator();

        //Set add security key just to pass label verification at log in extra security
        //testData.setAddSecurityKey(false);
        testData.setResetPassword(true);

        //extraSecurity.runExtraSecurity();

        extraSecurity.selectMfaMethod();
    }*/

    //TODO continue here
    /*
    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void setRecommendedPassword2() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword2"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login4(){
        testData.setResetPassword(false);
        login.enterPassword();
        login.signIn();
    }*/
/*
    @Test( dependsOnMethods = {"extraSecuritySecurityKey"} )
    void dashboard() {
        //Account is verified
        testData.setAccountVerified(true);

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void identity() {
        //Verify that identity is still confirmed
        common.navigateToIdentity();

        common.verifyStringOnPage("Ditt eduID är redo att användas");
        common.verifyStringOnPage("Följande identiteter är nu kopplade till ditt eduID");
        common.verifyStringOnPage("Svenskt personnummer");
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
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }*/
}