package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_7 extends BeforeAndAfter {
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
    void emailAddresses() {
        testData.setAddNewEmail1("eduidtest.se1@gmail.com");
        testData.setConfirmNewEmail1("code");
        emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void logout() {
        logout.runLogout();
    }
}
