package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_68 extends BeforeAndAfter {
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
}
