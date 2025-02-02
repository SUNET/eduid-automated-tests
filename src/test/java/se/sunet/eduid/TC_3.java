package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_3 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        testData.setGivenName("palle");
        testData.setSurName("kuling");
        testData.setDisplayName("palle kuling");
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void setNewUserNames() throws IOException {
        common.navigateToIdentity();

        testData.setProperties(testData.getTestSuite());

        name.runName();
    }

    @Test( dependsOnMethods = {"setNewUserNames"} )
    void logout() {
        logout.runLogout();
    }
}
