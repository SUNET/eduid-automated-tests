package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

import java.io.IOException;

@Slf4j
public class TC_31 extends BeforeAndAfter {
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
    void confirmedIdentityMail() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentityMail"} )
    void addSecurityKey() {
        testData.setAddExternalSecurityKey(true);
        testData.setVerifySecurityKeyByEidas(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "freja" at login, since eidas is not an option to enhance the security key
        testData.setMfaMethod("freja");
        extraSecurity.selectMfaMethod();

        Common.log.info("At this point Freja needs to be used for the re-authentication (nin) and eIDAS for the verification of security key");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void selectCountry(){
        //Select country XA
        common.selectCountry("XA");
    }

    @Test( dependsOnMethods = {"selectCountry"} )
    void submitEidasUser(){
        //Select loa substantial level and submit
        common.submitEidasUser();
    }

    @Test( dependsOnMethods = {"submitEidasUser"} )
    void submitConsent(){
        //Consent
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"submitConsent"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    //Verify at dashboard that all security options are checked
    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void dashboard() {
        //In this case we have updated the display name from country XA
        testData.setDisplayName("Bernt Olof" +" " +"Larsson");
        testData.setVerifySecurityKeyByFreja(true);
        dashBoard.runDashBoard();
    }

    //Delete the added security key and use eIDAS for re-authentication
    //Remove the verified security key
    @Test( dependsOnMethods = {"dashboard"} )
    void initiateRemoveVerifiedSecurityKey() {
        common.navigateToSecurity();
        securityKey.deleteSecurityKey();
    }

    @Test( dependsOnMethods = {"initiateRemoveVerifiedSecurityKey"} )
    void deleteVerifiedSecurityKeyLogin() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("eidas");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with eIDAS");
    }

    @Test( dependsOnMethods = {"deleteVerifiedSecurityKeyLogin"} )
    void extraLoginEidas() {
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    @Test( dependsOnMethods = {"extraLoginEidas"} )
    void verifySecurityKeyRemoved() {
        Assert.assertFalse(common.getPageBody().contains("test-key1"),
                "Security is still present at page! Should have been removed.");

        common.navigateToAccount();
    }


    @Test( dependsOnMethods = {"verifySecurityKeyRemoved"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void login4() {
        testData.setIncorrectPassword(true);

        login.enterPassword();
        login.clickLoginButton();
    }
}
