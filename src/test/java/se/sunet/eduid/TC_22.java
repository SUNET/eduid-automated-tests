package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_22 extends BeforeAndAfter {
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
        initPwChange.runInitPwChange();
    }

    @Test( dependsOnMethods = {"initPwChange"} )
    void loginPwChange(){
        //Check first if the incorrect password flag is set, then we need to re-set it after login.
        boolean tempIncorrectPassword = testData.isIncorrectPassword();
        testData.setIncorrectPassword(false);

        //Enter userName and password since we need to login again before pw change
        login.runLogin();

        testData.setIncorrectPassword(tempIncorrectPassword);
    }

    @Test( dependsOnMethods = {"loginPwChange"} )
    void password() {
        testData.setNewPassword("test");
        password.runPassword(); }

    @Test( dependsOnMethods = {"password"} )
    void logout() {
        logout.runLogout();
    }
}
