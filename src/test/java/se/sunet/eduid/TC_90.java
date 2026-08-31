package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_90 extends BeforeAndAfter {
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
        testData.setVerifySecurityKeyByFreja(true);

        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKeyLogin() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Add nin cookie
        common.addNinCookie();

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(4);
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

    //Remove the verified security key
    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void logout(){
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void navigateToFidusTestSkolverketDnp() {
//        testData.setUsername("tBnmBXbE@dev.eduid.sunet.se");
//        testData.setPassword("zfvs qtip dwn2");
//        testData.setEppn("sital-jotof");

        common.navigateToUrl("https://fidustest.skolverket.se/DNP-staging/");

        //Wait for login button (with eID) at skolverket dnp page
        common.waitUntilClickable(By.xpath("//div[2]/div/div/p[3]"));
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
    void loginWithEid() {
        //Click on login button (with eID)
        common.findWebElement(By.xpath("//div[2]/div/div/p[3]/a/button")).click();

        //Wait for idp search field
        common.waitUntilClickable(By.id("searchinput"));
    }

    @Test( dependsOnMethods = {"loginWithEid"} )
    public void navigateToEduId(){
        common.navigateToEduId();
    }

    @Test( dependsOnMethods = {"navigateToEduId"} )
    void loginMfaSecurityKey2() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey2"} )
    void validateSuccessfulLogin(){
        //Wait for handling of personal info link
        common.waitUntilVisible(By.xpath("//div[2]/div/div/p[5]/a"));

        common.verifyString(By.xpath("/html/body/div[2]/div/div/p[1]"),
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }


    @Test( dependsOnMethods = {"validateSuccessfulLogin"} )
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
    void selectUserRefIdp2(){
        //Select and submit user
        common.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp2"} )
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
