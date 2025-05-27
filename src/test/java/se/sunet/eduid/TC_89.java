package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_89 extends BeforeAndAfter {
/*    @Test
    void navigateToFidusTestSkolverketDnp() {
        testData.setUsername("tBnmBXbE@dev.eduid.sunet.se");
        testData.setPassword("zfvs qtip dwn2");
        testData.setEppn("sital-jotof");

        common.navigateToUrl("https://fidustest.skolverket.se/DNP-staging/");

        //Wait for login button (with eID) at skolverket dnp page
        common.explicitWaitClickableElement("//div[2]/div/div/p[3]");
    }*/
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
    void loginWithEid() {
        //Click on login button (with eID)
        common.findWebElementByXpath("//div[2]/div/div/p[3]/a/button").click();

        //Wait for idp search field
        common.explicitWaitClickableElementId("searchinput");
    }

    @Test( dependsOnMethods = {"loginWithEid"} )
    public void navigateEduId(){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys("eduid staging");
        common.timeoutMilliSeconds(3500);

        //Select eduid staging
        common.click(common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/li/a"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(2000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
/*    void login(){
        //We need the magic cookie and the nin-cookie for log in with extra security options
        common.addMagicCookie();
        common.addNinCookie();

        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login"} )*/
    void loginMfaBankId() {
        common.timeoutSeconds(2);

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("bankid");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with BankID");
    }

    @Test( dependsOnMethods = {"loginMfaBankId"} )
    void verifyBankIdLoginPage(){
        //Wait and verify that we come to the BankID log in page i.e. the saml bankid idp.
        common.explicitWaitPageTitle("BankID");

        //Navigate back
        common.getWebDriver().navigate().back();

        common.explicitWaitPageTitle("eduID");
    }
}
