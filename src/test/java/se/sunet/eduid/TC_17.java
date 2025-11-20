package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_17 extends BeforeAndAfter {
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

        //Turn security off for logging in
        common.explicitWaitClickableElement("//*[@id=\"content\"]/article[2]/form/fieldset/label/div");
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/form/fieldset/label/div"));

        testData.setMfaDisabled(true);
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void initiateTurnOffMfa() {
        common.securityConfirmPopUp("//*[@id=\"content\"]/article[2]/form/fieldset/label/div",
                "",
                "");
    }


    @Test( dependsOnMethods = {"initiateTurnOffMfa"} )
    void turnOffNonVerifiedSecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"turnOffNonVerifiedSecurityKeyLogin"} )
    //Log out and verify that it is possible to log in again without the security key (needs the remember me to be turned off)
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
        testData.setAddSecurityKey(false);

        //Turn off the remember me function at startpage for this
        testData.setRememberMe(false);
        common.rememberMe();

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void loginExtraSecurity(){
        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with securitykey");
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage3(){
        common.timeoutSeconds(2);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);

        login.runLogin();
    }
}
