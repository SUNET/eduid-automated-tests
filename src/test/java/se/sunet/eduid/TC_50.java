package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_50 extends BeforeAndAfter {
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
    void addPassKey() {
        testData.setAddInternalPassKey(true);
        testData.setVerifySecurityKeyByFreja(true);

        securityKey.runSecurityKey();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"addPassKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used at login, before Freja can be used for confirmation
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        common.enableVirtualAuthenticator();
        extraSecurity.selectMfaMethod();
        common.disableVirtualAuthenticator();

        Common.log.info("Log in with security key");
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
    void logout(){
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void loginPasskey(){
        //Used for the extra security log in
        testData.setIdentityConfirmed(true);

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        common.enableVirtualAuthenticator();
        extraSecurity.selectMfaMethod();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"loginPasskey"} )
    void dashboard() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout2(){
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout2"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void loginPasskeyDefaultLoginPage(){
        // Disable remember me, to get the login page with both passkey and username passwd option
        testData.setRememberMe(false);
        common.rememberMe();

        //Disable to verify to the text for remember me correctly
        testData.setMfaDisabled(true);

        testData.setUsePasskey(true);
        login.runLogin();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"loginPasskeyDefaultLoginPage"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void delete() {
        testData.setDeleteButton(true);
        common.enableVirtualAuthenticator();
        deleteAccount.runDeleteAccount();
        common.disableVirtualAuthenticator();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage4(){
        common.timeoutSeconds(2);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage4"} )
    void loginPasskeyDefaultLoginPage2(){
        // Disable remember me, to get the login page with both passkey and username passwd option
        testData.setRememberMe(false);
        common.rememberMe();

        //Disable to verify to verify texts correctly
        testData.setDeleteButton(false);

        testData.setUsePasskey(true);
        login.runLogin();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"loginPasskeyDefaultLoginPage2"} )
    void verifyAccountDeleted(){
        common.timeoutSeconds(3);

        testData.setAccountDeleted(true);
        login.signIn();
    }

    @Test( dependsOnMethods = {"verifyAccountDeleted"} )
    void startRestoreDeletedAccount(){
        login.clickRestoreDeletedAccountButton();
    }

    @Test( dependsOnMethods = {"startRestoreDeletedAccount"} )
    void requestNewPassword(){
        common.selectSwedish();
        requestNewPassword.runRequestNewPassword();
    }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() {
        emailLink.runEmailLink();
        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"emailLink"} )
    void extraSecuritySecurityKey() {
        //Set add security key just to pass label verification at log in extra security
        testData.setResetPassword(true);

        common.enableVirtualAuthenticator();
        extraSecurity.selectMfaMethod();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"extraSecuritySecurityKey"} )
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
        testData.setAddExternalSecurityKey(false);
        testData.setReLogin(true);

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard3() {
        //Account is verified
        testData.setAccountVerified(true);

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard3"} )
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
        common.navigateToAccount();

        testData.setDeleteButton(true);
        common.enableVirtualAuthenticator();
        deleteAccount.runDeleteAccount();
        common.disableVirtualAuthenticator();
    }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage5(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage5"} )
    void extraSecuritySecurityKey3() {
        //Login page for extra security select security key mfa method
        common.enableVirtualAuthenticator();
        extraSecurity.selectMfaMethod();
        common.disableVirtualAuthenticator();

        Common.log.info("Log in with securitykey");
    }

    @Test( dependsOnMethods = {"extraSecuritySecurityKey3"} )
    void verifyAccountDeleted2(){
        testData.setAccountDeleted(true);

        login.signIn();
    }
}
