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
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with extra security");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifyFailedSecurityKey() {
        //Verify the status message
        common.verifyStatusMessage("Felaktigt format av identitetsnumret. Var god försök igen.");

        common.explicitWaitClickableElement("//*[@id=\"manage-security-keys\"]//button[1]");

        //Verify status beside the added key dates
        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]/figure/div/div[3]/span/button[1]", "BANKID");

        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]//button[2]", "FREJA+");

        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]//button[3]", "EIDAS");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]/figure/div/div[3]/span/button[1]", "BANKID");

        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]//button[2]", "FREJA+");

        common.verifyStringByXpath(
                "//*[@id=\"manage-security-keys\"]//button[3]", "EIDAS");

        //Verify the status message
        common.verifyStatusMessage("Incorrect format of the identity number. Please try again.");
        common.closeStatusMessage();

        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"verifyFailedSecurityKey"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void loginExtraSecurity(){
        extraSecurity.selectMfaMethod();
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        login.signIn();
    }
}
