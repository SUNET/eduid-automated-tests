package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_5 extends BeforeAndAfter {
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
        testData.setLanguage("English");
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void setLanguage() {
        testData.setLanguage("Svenska");
        account.runAccount();
    }

    @Test( dependsOnMethods = {"setLanguage"} )
    void logout() {
        logout.runLogout();
    }
}
