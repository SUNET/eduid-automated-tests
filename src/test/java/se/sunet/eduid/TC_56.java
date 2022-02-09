package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_56 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void confirmIdentity(){
        testData.setConfirmIdBy("mail");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void confirmedIdentity() { confirmedIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKey() {
        common.addMagicCookie();
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));
    }

    @Test( dependsOnMethods = {"verifySecurityKey"} )
    void verifySecurityKeyLogin() {
        //Enter username, password to verify security key first time
        login.verifyPageTitle();
        login.enterUsernamePassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.explicitWaitClickableElementId("mfa-security-key");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void loginMfa() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with extra security");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfa"} )
    void selectUserRefIdp(){
        //Add nin cookie
        //common.addNinCookie();

        //Select and submit user
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.click(common.findWebElementById("submitButton"));
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifySecurityKeyStatus() {
        common.verifyStatusMessage("Identiteten motsvarar inte den som är bekräftad för detta eduID");

        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button", "VERIFIERA");

        common.selectEnglish();
        common.verifyStatusMessage("The identity does not match the one verified for this eduID");
        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button", "VERIFY KEY");
        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyStatus"} )
    void navigateToSettings() {
        common.navigateToSettings();
    }

    @Test( dependsOnMethods = {"navigateToSettings"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void loginExtraSecurity(){
        loginExtraSecurity.runLoginExtraSecurity();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage2(){startPage.runStartPage();}

    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}
