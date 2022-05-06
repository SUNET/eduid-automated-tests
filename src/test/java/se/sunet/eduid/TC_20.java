package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_20 extends BeforeAndAfter {
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
        login.enterPassword();
        login.signIn();

        testData.setIncorrectPassword(tempIncorrectPassword);
    }

    @Test( dependsOnMethods = {"loginPwChange"} )
    void password() {
        testData.setNewPassword("Test?=59(GG1234%€#\\");
        password.runPassword(); }

    @Test( dependsOnMethods = {"password"} )
    void logout() {
        logout.runLogout();
    }

    //Log back in with stored pw and then change back to default.

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void initPwChange2() {
        initPwChange.runInitPwChange();
    }

    @Test( dependsOnMethods = {"initPwChange2"} )
    void loginPwChange2(){
        //Check first if the incorrect password flag is set, then we need to re-set it after login.
        boolean tempIncorrectPassword = testData.isIncorrectPassword();
        testData.setIncorrectPassword(false);

        //Enter userName and password since we need to login again before pw change
        login.enterPassword();
        login.signIn();

        testData.setIncorrectPassword(tempIncorrectPassword);
    }

    @Test( dependsOnMethods = {"loginPwChange2"} )
    void password2() {
        testData.setNewPassword("lq2k dvzo 917s");
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout2() {
        logout.runLogout();
    }
}
