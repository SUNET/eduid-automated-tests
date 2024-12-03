package se.sunet.eduid;

import org.testng.annotations.*;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_2 extends BeforeAndAfter {
    @Test
    void startPage(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void updateUserNames() {
        common.navigateToIdentity();

        testData.setGivenName("palle");
        testData.setSurName("kuling");
        testData.setDisplayName("palle kuling");
        name.runName();
    }

    @Test( dependsOnMethods = {"updateUserNames"} )
    void logout() {
        logout.runLogout();
    }
}
