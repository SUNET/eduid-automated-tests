package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_42 extends BeforeAndAfter {
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

        //common.timeoutSeconds(4);
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
    void startPage2(){
        common.navigateToUrl(testData.getBaseUrl());

        startPage.runStartPage();
    }

    //TODO - continue here, we never get to the login page when account is deleted with security key

    //Init reset password and verify that the Identity still is verified
    @Test( dependsOnMethods = {"startPage2"} )
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
    void extraSecuritySecurityKey() {
        //testData.setMfaMethod("securitykey");
        extraSecurity.runExtraSecurity();
    }

    //TODO continue here
    @Test( dependsOnMethods = {"extraSecuritySecurityKey"} )
    void verifyBankId() {
        common.explicitWaitPageTitle("BankID");

        Common.log.info("Verify BankID labels - Swedish");

        //Verify texts
        //Wait for cancel button
        common.explicitWaitClickableElement("//*[@id=\"app\"]/main/div[2]/button");

        common.verifyStringOnPage(testData.getBankIdTextSwe());
        common.verifyStringOnPage("Vill du använda BankID på den här enheten eller på en annan enhet?");

        //Verify button texts
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[1]", "BankID på den här enheten");
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]", "Mobilt BankID på annan enhet");

        //Cancel button
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[2]/button", "Avbryt");

        //English link
        common.verifyStringByXpath("//*[@id=\"app\"]/div/button", "English");

        //Select english
        common.findWebElementByXpath("//*[@id=\"app\"]/div/button").click();

        Common.log.info("Verify BankID labels - English");

        //Verify texts
        common.verifyStringOnPage(testData.getBankIdTextEng());
        common.verifyStringOnPage("Do you want to use your BankID on this device or on another device?");

        //Verify button texts
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[1]", "BankID on this device");
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]", "Mobile BankID on other device");

        //Cancel button
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[2]/button", "Cancel");

        //English link
        common.verifyStringByXpath("//*[@id=\"app\"]/div/button", "Svenska");

        //Select swedish
        common.findWebElementByXpath("//*[@id=\"app\"]/div/button").click();

        Common.log.info("Verify BankID labels on other device - Swedish");

        //Select BankID on other device
        common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]").click();

        //Verify pop-up texts - swedish
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/dialog/ol/li[1]", "Starta BankID-appen");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[2]", "Tryck på QR-kodsknappen i BankID-appen");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[3]", "Ge BankID-appen tillåtelse att använda kameran");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[4]", "Rikta kameran mot QR-koden som visas här");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[5]", "Följ instruktionerna i appen");
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/dialog/button", "Stäng");

        //Close pop-up
        common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/dialog/button").click();

        //Select english
        common.findWebElementByXpath("//*[@id=\"app\"]/div/button").click();

        //Select BankID on other device
        common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]").click();

        Common.log.info("Verify BankID labels on other device - English");

        //Verify pop-up texts - english
        //Wait for pop-up close button
        common.explicitWaitClickableElement("//*[@id=\"app\"]/main/div[1]/dialog/button");

        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/dialog/ol/li[1]", "Start the BankID app");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[2]", "Press the QR code button in the BankID app");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[3]", "Allow the BankID app to use your camera");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[4]", "Point the camera at the QR code displayed here");
        common.verifyStringByXpath(
                "//*[@id=\"app\"]/main/div[1]/dialog/ol/li[5]", "Follow the instructions in the app");
        common.verifyStringByXpath("//*[@id=\"app\"]/main/div[1]/dialog/button", "Close");

        //Close pop-up
        common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/dialog/button").click();

        //Press cancel
        common.findWebElementByXpath("//*[@id=\"app\"]/main/div[2]/button").click();
    }

    @Test( dependsOnMethods = {"verifyBankId"} )
    void verifySamlFailPage() {
        common.explicitWaitClickableElementId("dashboard-button");
        common.verifyStringOnPage("Ett fel uppstod under åtkomst till tjänsten.");

        //Select to navigate to dashboard
        common.findWebElementById("dashboard-button").click();
        common.timeoutSeconds(5);
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
    void emailSent2() { emailSent.runEmailSent(); }

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
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void setRecommendedPassword2() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword2"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login4(){
        testData.setResetPassword(false);
        login.enterPassword();
        login.signIn();
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
    }
}