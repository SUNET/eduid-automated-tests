package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_54 extends BeforeAndAfter {
    //TODO this test case is not completed, need to know if user should be able to register a new
    //TODO account when already registered with eidas. since limitation of thest users in the XA/XB test countries
    @Test
    void swamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void createEduIDAccount(){
        common.findWebElement(By.id("register")).click();
    }

    @Test( dependsOnMethods = {"createEduIDAccount"} )
    void register(){
        testData.setRegisterAccount(true);
        testData.setRegisterWithEidas(true);
        register.runRegister();}

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void registerExternalSecurityKey() {
        testData.setAddExternalSecurityKey(true);

        //Add security key
        securityKey.addSecurityKey();
    }

    @Test( dependsOnMethods = {"registerExternalSecurityKey"} )
    void registerPassword() {
        testData.setUseRecommendedPw(false);

        //Get default password from properties
        testData.setNewPassword(testData.getPassword());
        password.setPassword();
    }

    @Test( dependsOnMethods = {"registerPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(true); }


    @Test( dependsOnMethods = {"swamidData"} )
    void navigateToEduid(){
        common.navigateToUrl(testData.getBaseUrl());

        common.waitUntilClickable(By.id("login-button"));
        common.findWebElement(By.id("login-button")).click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"navigateToEduid"} )
    void loginMfaFreja() {
        //Set mfa method to be used to "eidas" at login.
        testData.setMfaMethod("eidas");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();
        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){
        testData.setRegisterAccount(false);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        //Select and submit user
        common.selectAndSubmitUserRefIdp();

        login.signIn();
    }
}
