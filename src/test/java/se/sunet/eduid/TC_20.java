package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

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
    void password2() throws IOException {
        testData.setUseRecommendedPw(false);
        testData.setProperties(testData.getTestSuite());
        testData.setNewPassword(testData.getPassword());

        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout2() {
        logout.runLogout();
    }
}
