package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_16 extends BeforeAndAfter {
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
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(8);
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


    //Remove the verified security key
    @Test( dependsOnMethods = {"verifySecurityKeyStatus"} )
    void initiateRemoveVerifiedSecurityKey() {
        securityKey.deleteSecurityKey();
    }

    @Test( dependsOnMethods = {"initiateRemoveVerifiedSecurityKey"} )
    void deleteVerifiedSecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Enter username, password to verify security key first time
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.explicitWaitClickableElementId("mfa-security-key");
    }

    @Test( dependsOnMethods = {"deleteVerifiedSecurityKeyLogin"} )
    void loginMfaSecurityKey2() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey2"} )
    void verifySecurityKeyRemoved() {
        Assert.assertFalse(common.getPageBody().contains("test-key1"),
                "Security is still present at page! Should have been removed.");
    }

    //Log out and verify that it is possible to log in again without the security key
    @Test( dependsOnMethods = {"verifySecurityKeyRemoved"} )
    void logout(){
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        testData.setRegisterAccount(false);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.verifyPageTitle();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login2"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){
        common.timeoutSeconds(2);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
