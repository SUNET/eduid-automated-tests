package se.sunet.eduid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_88 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setUsername("E82kBgpL@dev.eduid.sunet.se");
        testData.setPassword("x8l8 lgv3 e6x5");
        testData.setEppn("lopob-porop");
        testData.setIdentityNumber("201311122384");
        testData.setGivenName("Zantra");
        testData.setSurName("Jeppsson");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void navigateToFidusTestSkolverketDnp() {
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
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        login.clickLoginButton();

        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"login"} )
    void loginMfaFreja() {
        //Set mfa method to be used to "freja" at login.
        testData.setMfaMethod("freja");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Add magic and nin cookie to be redirected to ref.idp
        common.addNinCookie();

        //Add magic cookie
        common.addMagicCookie();

        //Login page for extra security select freja mfa method
        extraSecurity.selectMfaMethod();
        Common.log.info("Log in with Freja");
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp(){
        //Select and submit user
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void validateSuccessfulLogin(){
        //Wait for handling of personal info link
        common.waitUntilVisible(By.xpath("//div[2]/div/div/p[5]/a"));

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
