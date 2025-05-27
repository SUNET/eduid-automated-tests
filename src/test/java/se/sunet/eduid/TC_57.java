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
        //Set mfa method to be used at login.
        testData.setMfaMethod("securitykey");
        common.addNinCookie();

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

    @Test( dependsOnMethods = {"logout"} )
    void navigateToSwamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void loginSecurityKey() {
        common.timeoutSeconds(2);

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security Key");
    }

    @Test( dependsOnMethods = {"loginSecurityKey"} )
    void swamidData(){
        swamidData.runSwamidData(true); }
}
