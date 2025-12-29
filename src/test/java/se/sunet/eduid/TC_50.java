package se.sunet.eduid;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
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
        //testData.setUsername("N9yGFo4f@dev.eduid.sunet.se");
        //testData.setPassword("97x9 n7py wty4");
        testData.setRegisterAccount(false);
        //testData.setUsePasskey(true);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void addPassKey() {
        common.navigateToSecurity();
        common.addMagicCookie();
        common.addNinCookie();
        common.findWebElementById("security-webauthn-platform-button").click();

        common.timeoutSeconds(4);
        common.findWebElementById("describe-webauthn-token-modal").sendKeys("eduID-passkey");
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button").click();
        //testData.setAddSecurityKey(true);
        //testData.setVerifySecurityKeyByFreja(true);
        common.timeoutSeconds(20);
        //securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addPassKey"} )
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

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setRememberMe(false);
        common.rememberMe();

        testData.setResendOTP(true);
        securityKey.virtualAuthenticator();

        testData.setMfaDisabled(true);

        //login.verifyPageTitle();


        login.runLogin();

        //Log in with passkey
        //login.enterPassword();
        //common.click(common.findWebElementById("login-form-button"));
    }
/*
    @Test( dependsOnMethods = {"logout"} )
    void stopBrowser(){
        common.getWebDriver().quit();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"browser", "headless", "language"})
    void startBrowser(String browser, String headless, String language, final ITestContext testContext) throws IOException {
        initBrowser(browser, headless, language, testContext);
        common.navigateToUrl(testData.getBaseUrl());
        testData.setTestSuite(testContext.getCurrentXmlTest().getSuite().getName());
        testData.setTestCase("TC_50");
        //testData.setDisplayName(displayname1);

        securityKey.virtualAuthenticator();
    }

    @Test( dependsOnMethods = {"startBrowser"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.verifyPageTitle();

        login.runLogin();

        //Log in with passkey
        //login.enterPassword();
        //common.click(common.findWebElementById("login-form-button"));
    }*/
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
