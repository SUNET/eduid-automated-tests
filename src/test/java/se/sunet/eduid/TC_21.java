package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_21 extends BeforeAndAfter {
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
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void password() {
        testData.setIncorrectPassword(true);
        testData.setNewPassword("Test?=59(GG1234%€#\\");
        password.runPassword(); }

    @Test( dependsOnMethods = {"password"} )
    void logout() {
        logout.runLogout();
    }
}
