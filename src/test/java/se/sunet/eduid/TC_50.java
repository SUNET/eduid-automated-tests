package se.sunet.eduid;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;

import java.io.IOException;

public class TC_50 extends BeforeAndAfter {
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

        //Set mfa method to be used at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with security key");
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

    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void logout(){
        logout.runLogout();
    }
/*
//TODO log in with the passkey...

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        //startPage.runStartPage();
        try {
            quitBrowser();

            initBrowser(testData.getBrowser(), testData.getHeadlessExecution(), testData.getLanguage());;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.verifyPageTitle();

        //Log in with passkey
        //login.enterPassword();
        //common.click(common.findWebElementById("login-form-button"));
    }
/*
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
    }*/
}
