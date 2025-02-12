package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_6 extends BeforeAndAfter {
    String userName;

    @Test
    void startPage(){
        //Set local username from properties
        userName = testData.getUsername();
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setUsername(testData.getEppn());
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        testData.setUsername(userName);
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() {
        logout.runLogout();
    }
}
