package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_69 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setMfaDisabled(true);

        //Disable Remember me
        testData.setRememberMe(false);
        common.rememberMe();

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard2"} )
    void logout2() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout2"} )
    void startPage3() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        //Enable Remember me
        testData.setRememberMe(true);
        common.rememberMe();

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login3"} )
    void dashboard3() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard3"} )
    void logout3() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout3"} )
    void startPage4() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void verifySignInPage() {
        common.timeoutMilliSeconds(500);
        //Verify label with registered display name
        common.verifyStringOnPage("Välkommen tillbaka, " +testData.getDisplayName() +"!");

        //Verify placeholder for username
        common.verifyStrings(testData.getUsername(), common.findWebElementById("username").getDomAttribute("value"));
    }

    @Test( dependsOnMethods = {"verifySignInPage"} )
    void enterPassword() { login.enterPassword(); }

    @Test( dependsOnMethods = {"enterPassword"} )
    void pressLogin() {
        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"pressLogin"} )
    void dashboard4() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard4"} )
    void logout4() { logout.runLogout(); }
}
