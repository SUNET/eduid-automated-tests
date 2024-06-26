package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_57 extends BeforeAndAfter {
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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void storeEppn(){
        advancedSettings.pressAdvancedSettings();
        common.timeoutSeconds(1);
        advancedSettings.storeEppn();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"storeEppn"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentityMail(){
        testData.setConfirmIdBy("mail");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityMail"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKey() {
        //Click on Verify for the added security key
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));
    }

    @Test( dependsOnMethods = {"verifySecurityKey"} )
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
    void loginMfaSecurityKey() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifySecurityKeyStatus() {
        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIERAD");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIED");
        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyStatus"} )
    void logout(){
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void navigateToSwamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login2(){
        //Add magic cookie
        common.addMagicCookie();

        login.verifyPageTitle();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login2"} )
    void loginMfaFreja() {
        common.timeoutSeconds(2);

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.selectMfaMethod();
        Common.log.info("Log in with Freja eID+");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp2(){
        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp2"} )
    void swamidData(){
        swamidData.runSwamidData(true); }

    @Test( dependsOnMethods = {"swamidData"} )
    void navigateToEduid(){
        common.navigateToUrl("https://dev.eduid.se");

        common.click(common.findWebElementById("login-button"));
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"navigateToEduid"} )
    void loginMfaFreja2() {
        //Login page for extra security select security key mfa method
        loginExtraSecurity.selectMfaMethod();
        Common.log.info("Log in with Freja eID+");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaFreja2"} )
    void selectUserRefIdp3(){
        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp3"} )
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
    void loginMfaFreja3(){
        loginExtraSecurity.runLoginExtraSecurity();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"loginMfaFreja3"} )
    void selectUserRefIdp4(){
        common.explicitWaitClickableElementId("submitButton");
        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);

        //Wait for register button at start page
        common.explicitWaitClickableElementId("sign-up-button");
    }

    @Test( dependsOnMethods = {"selectUserRefIdp4"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
