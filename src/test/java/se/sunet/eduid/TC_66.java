package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import javax.accessibility.AccessibleComponent;

public class TC_66 extends BeforeAndAfter {
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
    void verifySigninPage() {
        //Verify label with registered display name
        common.verifyStringOnPage("Välkommen tillbaka, " +testData.getDisplayName() +"!");

        //Verify placeholder for username
        common.verifyStrings(testData.getUsername(), common.findWebElementByXpath("//*[@id=\"email\"]/input").getAttribute("value"));
    }

    @Test( dependsOnMethods = {"verifySigninPage"} )
    void enterPassword() { login.enterPassword(); }

    @Test( dependsOnMethods = {"enterPassword"} )
    void pressLogin() {
        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"pressLogin"} )
    void dashboard2() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard2"} )
    void logout2() { logout.runLogout(); }
}
