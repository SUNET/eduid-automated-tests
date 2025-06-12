package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

import java.io.IOException;

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
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddSecurityKey(true);
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
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void selectCountry(){
        //Select country XA
        common.findWebElementById("countryFlag_XA").click();

        //Wait for idp button on next page
        common.explicitWaitClickableElementId("idpSubmitbutton");
    }

    @Test( dependsOnMethods = {"selectCountry"} )
    void submitEidasUser(){
        //Select loa substantial level and submit
        common.findWebElementByXpath("//*[@id=\"eidasDiv\"]/div/button").click();
        common.findWebElementByXpath("//*[@id=\"bs-select-3-1\"]").click();

        common.findWebElementById("idpSubmitbutton").click();

        //Wait for consent button on next page
        common.explicitWaitClickableElementId("buttonNext");
    }

    @Test( dependsOnMethods = {"submitEidasUser"} )
    void submitConsent(){
        //Select country XA
        common.findWebElementById("buttonNext").click();
    }

    @Test( dependsOnMethods = {"submitConsent"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    //Verify at dashboard that all security options are checked
    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void dashboard() throws IOException {
        //In this case we have updated the display name from country XA
        testData.setDisplayName("Bernt Olof" +" " +"Larsson");
        testData.setVerifySecurityKeyByFreja(true);
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
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login3"} )
    void extraSecurityFreja2() {
        //Set mfa method to be used to "freja" at login, since eidas is not an option to enhance the security key
        testData.setMfaMethod("freja");

        //Login page for extra security select freja as mfa method
        extraSecurity.selectMfaMethod();
    }

    @Test( dependsOnMethods = {"extraSecurityFreja2"} )
    void selectUserRefIdp2() {
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp2"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        login.signIn();
    }
}
