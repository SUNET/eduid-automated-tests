package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_88 extends BeforeAndAfter {
    @Test
    void navigateToFidusTestSkolverketDnp() {
        testData.setUsername("tBnmBXbE@dev.eduid.sunet.se");
        testData.setPassword("zfvs qtip dwn2");
        testData.setEppn("sital-jotof");

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

        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login2"} )
    void loginMfaFreja() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("freja");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.explicitWaitClickableElementId("submitButton");
        common.selectDropdownScript("selectSimulatedUser", "Ulla Alm (198611062384)");

        common.findWebElementById("submitButton").click();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void validateSuccessfulLogin(){
        //Wait for handeling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }

    /*
    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void verifyFrejaLoginPage(){
        //Wait and verify that we come to the Freja log in page
        common.explicitWaitPageTitle("Freja eID IDP");

        //Navigate back
        common.getWebDriver().navigate().back();

        common.explicitWaitPageTitle("eduID");
    }

    @Test( dependsOnMethods = {"verifyFrejaLoginPage"} )
    void verifyBankIdLoginPage(){
        //Click on bankID
        common.findWebElementById("mfa-bankid").click();

        //Wait and verify that we come to the Freja log in page
        common.explicitWaitPageTitle("BankID");

        //Navigate back
        common.getWebDriver().navigate().back();

        common.explicitWaitPageTitle("eduID");
    }

    @Test( dependsOnMethods = {"verifyBankIdLoginPage"} )
    void loginMfaSecurityKey() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("securitykey");

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        Common.log.info("Log in with Security key");

        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"loginMfaSecurityKey"} )
    void validateSuccessfulLogin(){
        //Wait for handeling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }*/
}
