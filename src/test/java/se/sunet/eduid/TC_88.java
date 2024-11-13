package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_88 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();

        testData.setUsername("E82kBgpL@dev.eduid.sunet.se");
        testData.setPassword("x8l8 lgv3 e6x5");
        testData.setEppn("lopob-porop");
        testData.setIdentityNumber("201311122384");
        testData.setGivenName("Zantra");
        testData.setSurName("Jeppsson");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());
    }

    @Test( dependsOnMethods = {"startPage"} )
    void navigateToFidusTestSkolverketDnp() {
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
    void login(){
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login"} )
    void loginMfaFreja() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Add magic and nin cookie to be redirected to ref.idp
        common.addMagicCookie();
        common.addNinCookie();

        //Login page for extra security select freja mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();
        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp(){
        //Select and submit user
        confirmIdentity.selectAndSubmitUserRefIdp();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void validateSuccessfulLogin(){
        //Wait for handling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
