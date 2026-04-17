package se.sunet.eduid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_89 extends BeforeAndAfter {
    @Test
    void navigateToFidusTestSkolverketDnp() {
        testData.setUsername("tBnmBXbE@dev.eduid.sunet.se");
        testData.setPassword("zfvs qtip dwn2");
        testData.setEppn("sital-jotof");

        common.navigateToUrl("https://fidustest.skolverket.se/DNP-staging/");

        //Wait for login button (with eID) at skolverket dnp page
        common.waitUntilClickable(By.xpath("//div[2]/div/div/p[3]"));
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
    void loginWithEid() {
        //Click on login button (with eID)
        common.findWebElementByXpath("//div[2]/div/div/p[3]/a/button").click();

        //Wait for idp search field
        common.waitUntilClickable(By.id("searchinput"));
    }

    @Test( dependsOnMethods = {"loginWithEid"} )
    public void navigateToEduId(){
        common.navigateToEduId();
    }

    @Test( dependsOnMethods = {"navigateToEduId"} )
    void login(){
        //We need the magic cookie and the nin-cookie for log in with extra security options
        common.addMagicCookie();
        common.addNinCookie();

        login.enterUsername();
        login.enterPassword();

        //Click log in button
        login.clickLoginButton();
    }

    @Test( dependsOnMethods = {"login"} )
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
        common.waitUntilPageTitleContains("BankID");

        //Navigate back
        common.getWebDriver().navigate().back();

        common.waitUntilPageTitleContains("eduID");
    }
}
