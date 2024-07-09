package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_18 extends BeforeAndAfter {
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
        testData.setUseRecommendedPw(false);
        testData.setNewPassword("notUsed");
        testData.setButtonValueConfirm(false);
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password"} )
    void logout() {
        logout.runLogout();
    }
}
