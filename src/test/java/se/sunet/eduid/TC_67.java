package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_67 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifySignInPage() {
        //Verify label with registered display name
        common.explicitWaitClickableElementId("login-form-button");
        common.verifyStringOnPage("Välkommen tillbaka, " +testData.getDisplayName() +"!");

        //Verify placeholder for username
        common.verifyStrings(testData.getUsername(), common.findWebElementById("email").getAttribute("value"));
    }

    @Test( dependsOnMethods = {"verifySignInPage"} )
    void pressNotYou() {
        common.findWebElementById("wrong-person-button").click();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"pressNotYou"} )
    void enterUsernamePassword() {
        testData.setUsername("yUHivJGG@dev.eduid.sunet.se");
        testData.setPassword("ty3v 3w39 596v");
        login.enterUsername();
        login.enterPassword();
    }

    @Test( dependsOnMethods = {"enterUsernamePassword"} )
    void pressLogin() {
        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"pressLogin"} )
    void dashboard2() {
        testData.setDisplayName("Rutger Jönåker");
//        testData.setPhoneNumber("+46701740605");
//        testData.setEmail("yUHivJGG@dev.eduid.sunet.se");
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void logout2() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout2"} )
    void startPage3() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifySignInPage2() {
        //Verify label with registered display name
        common.timeoutMilliSeconds(500);
        common.verifyStringOnPage("Välkommen tillbaka, Rutger Jönåker!");

        //Verify placeholder for username
        common.verifyStrings(testData.getUsername().toLowerCase(), common.findWebElementById("email").getAttribute("value"));
    }
}
