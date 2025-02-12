package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_11 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setUsername("GvY0RfP0@dev.eduid.sunet.se");
        testData.setPassword("g307 hjz7 zaqr");
        testData.setEppn("mopab-gotas");
        testData.setIdentityNumber("198904082396");
        testData.setGivenName("Jenny");
        testData.setSurName("Huling");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityEidas(){
        testData.setConfirmIdBy("eidas");
        testData.setLoaLevel("eIDAS Low");

        common.navigateToIdentity();
        confirmIdentity.runConfirmIdentity();
    }

    @Test( dependsOnMethods = {"confirmIdentityEidas"} )
    void verifySamlFailPage() {
        common.explicitWaitClickableElementId("dashboard-button");
        common.verifyStringOnPage("Ett fel uppstod under åtkomst till tjänsten.");

        //Select to navigate to dashboard
        common.findWebElementById("dashboard-button").click();
        common.timeoutSeconds(5);
    }

    @Test( dependsOnMethods = {"verifySamlFailPage"} )
    void logout() { logout.runLogout(); }
}