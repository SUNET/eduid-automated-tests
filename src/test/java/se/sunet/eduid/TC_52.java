package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

import java.io.IOException;

@Slf4j
public class TC_52 extends BeforeAndAfter {
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
    void confirmIdentityEidas(){
        testData.setConfirmIdBy("eidas");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityEidas"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddExternalSecurityKey(true);
        testData.setVerifySecurityKeyByEidas(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLoginEidas() {
        common.timeoutSeconds(2);
        //Set mfa method to be used at login.
        testData.setMfaMethod("eidas");

        //Login page for extra security select security key mfa method
        //TODO uncomment when label bug is fixed...
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with eidas");


        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLoginEidas"} )
    void verifySecurityKeyLoginEidas2() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();

        Common.log.info("Log in with eidas again to confirm the added security key, since re-auth not allowed at the moment");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLoginEidas2"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    //Verify at dashboard that all security options are checked
    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void dashboard() throws IOException {
        //In this case we have updated the display name from country XA
        testData.setDisplayName("Bernt Olof" +" " +"Larsson");
        testData.setVerifySecurityKeyByEidas(true);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void login3(){
        //Login page for extra security select freja as mfa method
        extraSecurity.selectMfaMethod();
    }

    @Test( dependsOnMethods = {"login3"} )
    void extraLoginEidas() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void login4() {
        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"login4"} )
    void extraLoginEidas2() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas2"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        login.signIn();
    }

    //Reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"verifyAccountDeleted"} )
    void startRestoreDeletedAccount(){
        login.clickRestoreDeletedAccountButton();
    }

    @Test( dependsOnMethods = {"startRestoreDeletedAccount"} )
    void requestNewPassword() {
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
    void extraSecurityEidas() {
        testData.setAddExternalSecurityKey(false);
        testData.setResetPassword(true);

        testData.setMfaMethod("eidas");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"extraSecurityEidas"} )
    void extraLoginEidas3() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas3"} )
    void setCustomPassword2() {
        //Get default password from properties
        testData.setNewPassword(testData.getPassword());

        testData.setUseRecommendedPw(false);
        password.setPassword();
    }

    @Test( dependsOnMethods = {"setCustomPassword2"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login5(){
        testData.setAccountDeleted(false);

        login.enterUsername();
        login.enterPassword();
        login.clickLoginButton();
    }

    @Test( dependsOnMethods = {"login5"} )
    void extraSecurityLoginEidas() {
        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with eIDAS");
    }

    @Test( dependsOnMethods = {"extraSecurityLoginEidas"} )
    void extraLoginEidas5() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas5"} )
    void dashboard2() {
        //Account is verified
        testData.setAccountVerified(true);
        testData.setAddExternalSecurityKey(true);

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void identity() {
        //Verify that identity is still confirmed
        common.navigateToIdentity();

        common.verifyStringOnPage("Ditt eduID är redo att användas");
        common.verifyStringOnPage("Följande identiteter är nu kopplade till ditt eduID");
        common.verifyStringOnPage("Europeisk eIDAS-identitet");
    }

    //Delete the account when confirmed that identity is no longer verified
    @Test( dependsOnMethods = {"identity"} )
    void delete2() {
        common.navigateToAccount();

        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage4(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void extraSecurityEidas3() {
        extraSecurity.selectMfaMethod();
    }

    @Test( dependsOnMethods = {"extraSecurityEidas3"} )
    void extraLoginEidas4() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas4"} )
    void verifyAccountDeleted2(){
        testData.setAccountDeleted(true);

        login.signIn();
    }
}
