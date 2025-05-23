package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_4 extends BeforeAndAfter {
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
    void setLanguageEnglish() {
        testData.setLanguage("English");
        account.runAccount();
    }

    @Test( dependsOnMethods = {"setLanguageEnglish"} )
    void logout() {
        logout.runLogout();
    }
}
