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
        testData.setVerifySecurityKeyByFreja(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
       //Enter username, password to verify security key first time
        login.verifyPageTitle();
        login.enterPassword();

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
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with extra security");
    }

    @Test( dependsOnMethods = {"loginMfa"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifySecurityKeyStatus() {
        //Verify the status message
        common.verifyStatusMessage("Felaktigt format av identitetsnumret. Var god försök igen.");

        //Verify status beside the added key dates
        common.verifyStringByXpath(
                "//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[2]", "FREJA+");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath(
                "//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[1]", "BANKID");

        //Verify the status message
        common.verifyStatusMessage("Incorrect format of the identity number. Please try again.");
        common.closeStatusMessage();

        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyStatus"} )
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

        //loginExtraSecurity.selectMfaMethod();
        extraSecurity.selectMfaMethod();
        //common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
