package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_29 extends BeforeAndAfter {
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
        testData.setVerifySecurityKeyByBankId(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Enter username, password to verify security key first time
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.explicitWaitClickableElementId("mfa-security-key");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void extraSecurityBankId() {
        //Set mfa method to be used to "bankid" at login.
        testData.setMfaMethod("bankid");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with BankID");
    }

    @Test( dependsOnMethods = {"extraSecurityBankId"} )
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
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"verifySamlFailPage"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void login2(){
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login2"} )
    void extraSecurityFreja() {
        //Set mfa method to be used to "bankid" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
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
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
