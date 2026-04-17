package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

import java.io.IOException;

public class TC_51 extends BeforeAndAfter {
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
    void confirmIdentityMail(){
        testData.setConfirmIdBy("mail");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityMail"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddExternalSecurityKey(true);
        testData.setVerifySecurityKeyByFrejaeID(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "freja" at login, since eidas is not an option to enhance the security key
        testData.setMfaMethod("freja");
        extraSecurity.selectMfaMethod();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifyFrejaEidPage(){
        //Wait and see that we come to Freja eID OpenID Connect - Logga in - page
        common.waitUntilPageTitleContains("Freja eID OpenID Connect - Logga in");
        common.verifyStringOnPage("Logga in på eduID Sweden");
        common.verifyStringOnPage("Öppna Freja-appen och tryck på knappen ”Skanna”.\n" +
                "Skanna QR-koden.");
    }

    @Test( dependsOnMethods = {"verifyFrejaEidPage"} )
    void navigateBackToEduId() {
        common.getWebDriver().navigate().back();
    }

    @Test( dependsOnMethods = {"navigateBackToEduId"} )
    void verifiedSecurityKeyStatus() {
        Assert.assertFalse(common.getPageBody().contains("VERIFIED") || common.getPageBody().contains("VERIFIERAD"),
                "Security key is verified! Should have status unused");
    }

    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void extraSecurityFreja() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"extraSecurityFreja"} )
    void selectIdRefIdp() {
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void extraSecurityFreja2() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"extraSecurityFreja2"} )
    void selectIdRefIdp2() {
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp2"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        login.signIn();
    }
}
