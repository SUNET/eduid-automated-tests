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
    void personalInfo() {
        testData.setGivenName("palle");
        testData.setSurName("kuling");
        testData.setDisplayName("palle kuling");
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void logout() {
        logout.runLogout();
    }
}
