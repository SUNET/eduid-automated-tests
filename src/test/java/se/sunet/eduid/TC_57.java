package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_57 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setUsername("x2lSCHs1@dev.eduid.sunet.se");
        testData.setPassword("0our 2s65 q0pl");
        testData.setEppn("jutap-dizid");
        testData.setIdentityNumber("199706212389");
        testData.setGivenName("Eleonara");
        testData.setSurName("Lagerfeldt");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void navigateToSwamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login(){
        //Add nin cookie and magic cookie
        common.addNinCookie();
        common.addMagicCookie();

        login.verifyPageTitle();

        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login"} )
    void loginMfaFreja() {
        common.timeoutSeconds(2);

        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("freja");

        //This account has confirmed identity
        testData.setIdentityConfirmed(true);

        //Login page for extra security select security key mfa method
        loginExtraSecurity.runLoginExtraSecurity();
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with Freja eID+");
    }

    @Test( dependsOnMethods = {"loginMfaFreja"} )
    void selectUserRefIdp2(){
        confirmIdentity.selectAndSubmitUserRefIdp();
        common.timeoutSeconds(3);
    }

    @Test( dependsOnMethods = {"selectUserRefIdp2"} )
    void swamidData(){
        swamidData.runSwamidData(true); }
}
