package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_17 extends BeforeAndAfter {
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
    void initPwChange() {
        testData.setButtonValuePopup(false);
        initPwChange.runInitPwChange();
    }

    @Test( dependsOnMethods = {"initPwChange"} )
    void logout() {
        logout.runLogout();
    }
}
