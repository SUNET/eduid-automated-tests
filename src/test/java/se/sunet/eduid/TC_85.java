package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_85 extends BeforeAndAfter {
    @Test
    void startPage(){
     startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setPassword("ycb1 al8x ucrc");
        testData.setUsername("dKdmCkae@dev.eduid.sunet.se");
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
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp(){
        //Click on Verify for the added security key - Selecting Freja
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));

        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void logInSomething(){
        common.navigateToUrl("https://ds.fidus.skolverket.se/ds/?entityID=https%3A%2F%2Fidpproxy.dev.eduid.se%2Fsp&return=https%3A%2F%2Fidpproxy.dev.eduid.se%2FSaml2SP%2Fdisco");

        common.explicitWaitVisibilityElementId("searchinput");
        common.findWebElementById("searchinput").sendKeys("eduid staging");
        //testData.setRegisterAccount(true);
        //startPage.runStartPage();
        common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/a/li/div/div[1]").click();

        common.timeoutSeconds(5);
    }

    @Test( dependsOnMethods = {"logInSomething"} )
    void login2(){
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login2"} )
    void loginMfaSecurityKey2() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        common.timeoutSeconds(5);

        //Add cookie for back doors
        if(!common.isCookieSet("magiccookie"))
            common.addMagicCookie();

        //Add nin cookie
        common.addNinCookie();
/*        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);

        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);

        authenticator.setUserVerified(true);*/
        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Security key");
    }

//    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void selectUserRefIdp2(){
        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
    }

//    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifySecurityKeyStatus2() {
        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIERAD");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/span", "VERIFIED");
        common.selectSwedish();
    }

}
