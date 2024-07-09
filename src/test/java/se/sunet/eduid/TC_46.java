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
    void confirmPassword() { confirmPassword.runConfirmPassword(); }

    @Test( dependsOnMethods = {"confirmPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    //First without any personal info saved
    @Test( dependsOnMethods = {"login"} )
    void securityKeyWithoutPersonalInfo() {
        testData.setAddSecurityKey(false);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"securityKeyWithoutPersonalInfo"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
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
        testData.setVerifySecurityKey(true);

        securityKey.runSecurityKey();
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
        common.timeoutSeconds(4);

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with extra security");

        //Wait for the Verify link for the added security key - Selecting Freja
        try{
            common.explicitWaitVisibilityElement("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button");
        }catch (Exception ex){
            Common.log.info("At page: " +common.getWebDriver().getTitle());
            Common.log.info("We are not logged in, call log in again");
            loginExtraSecurity.selectMfaMethod();
        }

    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp(){
        common.timeoutSeconds(3);

        //Click on Verify for the added security key - Selecting Freja
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));

        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
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
    void timeoutBeforeDelete() {
        //Timeout to make sure last log in was +5min ago
        common.timeoutSeconds(30);
    }

    @Test( dependsOnMethods = {"timeoutBeforeDelete"} )
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
    void loginExtraSecurity(){

        loginExtraSecurity.selectMfaMethod();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login4(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
