package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_29 extends BeforeAndAfter {
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
        testData.setAddExternalSecurityKey(true);
        testData.setVerifySecurityKeyByBankId(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Set mfa method to be used to "bankid" at login.
        testData.setMfaMethod("bankid");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with BankID");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void verifyBankId() {
        common.verifyBankIdTextAndLabels();
    }

    @Test( dependsOnMethods = {"verifyBankId"} )
    void verifySamlFailPage() {
        common.waitUntilClickable(By.id("dashboard-button"));
        common.verifyStringOnPage("Ett fel uppstod under åtkomst till tjänsten.");

        //Select to navigate to dashboard
        common.findWebElement(By.id("dashboard-button")).click();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"verifySamlFailPage"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void extraSecurityFreja() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"extraSecurityFreja"} )
    void selectIdRefIdp() {
        common.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void extraSecurityFreja2() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"extraSecurityFreja2"} )
    void selectIdRefIdp2() {
        common.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp2"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        login.signIn();
    }
}
