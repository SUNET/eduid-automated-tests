package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

@Slf4j
public class TC_48 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setAddSecurityKey(false);
        testData.setReLogin(true);

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard2"} )
    void logout2() { logout.runLogout(); }
}
