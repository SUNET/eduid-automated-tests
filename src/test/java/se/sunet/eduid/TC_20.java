package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

@Slf4j
public class TC_20 extends BeforeAndAfter {
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
        testData.setSwamidSp(true);
        register.runRegister();}

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void registerInternalSecurityKey() {
        testData.setAddInternalPassKey(true);

        //Add security key
        securityKey.addSecurityKey();
    }

    @Test( dependsOnMethods = {"registerInternalSecurityKey"} )
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
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

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
