package se.sunet.eduid;

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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void storeEppn(){
        advancedSettings.pressAdvancedSettings();
        common.timeoutSeconds(1);
        advancedSettings.storeEppn();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"storeEppn"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentityMail(){
        testData.setConfirmIdBy("mail");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityMail"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void addSecurityKey() {
        testData.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void verifySecurityKey() {
        //Click on Verify for the added security key
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));
    }

    @Test( dependsOnMethods = {"verifySecurityKey"} )
    void verifySecurityKeyLogin() {
        //Add nin cookie
        common.addNinCookie();

        //Enter username, password to verify security key first time
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.explicitWaitClickableElementId("mfa-security-key");
    }

    @Test( dependsOnMethods = {"verifySecurityKeyLogin"} )
    void loginMfaSecurityKey() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifySecurityKeyStatus() {
        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIERAD");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIED");
        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"verifySecurityKeyStatus"} )
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
        common.explicitWaitClickableElement("//div[2]/div/div/p[3]");
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
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
        common.click(common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/a[1]"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(2000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
    void login2(){
        //We need the magic cookie and the nin-cookie for log in with extra security options
        common.addMagicCookie();
        common.addNinCookie();

        login.verifyPageTitle();

        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login2"} )
    void loginMfaSecurityKey2() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey2"} )
    void validateSuccessfulLogin(){
        //Wait for handeling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
