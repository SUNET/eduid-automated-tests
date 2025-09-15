package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_15 extends BeforeAndAfter {
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
    void confirmPassword() {
        testData.setUseRecommendedPw(false);

        //Get default password from properties
        testData.setNewPassword(testData.getPassword());
        password.setPassword();
    }

    @Test( dependsOnMethods = {"confirmPassword"} )
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
    void initiateRemoveNonVerifiedSecurityKey() {
        securityKey.deleteSecurityKey();
    }

    @Test( dependsOnMethods = {"initiateRemoveNonVerifiedSecurityKey"} )
    void deleteNonVerifiedSecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(8);
    }

    @Test( dependsOnMethods = {"deleteNonVerifiedSecurityKeyLogin"} )
    void removeNonVerifiedSecurityKey() {
        Assert.assertFalse(common.getPageBody().contains("test-key1"),
                "Security key is still present at page! Should have been removed.");
    }

    //Log out and verify that it is possible to log in again without the security key
    @Test( dependsOnMethods = {"removeNonVerifiedSecurityKey"} )
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
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
