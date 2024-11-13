package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

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
    void personalInfo() {
        testData.setGivenName("Bernt Olof");
        testData.setSurName("Larsson");
        testData.setDisplayName("Bernt Olof Larsson");
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void logout() {
        logout.runLogout();
    }
}
