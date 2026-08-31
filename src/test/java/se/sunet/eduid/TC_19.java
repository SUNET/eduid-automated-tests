package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_19 extends BeforeAndAfter {
    String origPassword;
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setGivenName("Stella Ann");
        testData.setSurName("Magnum");
        testData.setDisplayName("Stella Ann Magnum");
        testData.setUsername("ydlxcFqm@dev.eduid.sunet.se");
        testData.setPassword("h0s9 lz91 fp61");
        origPassword = testData.getPassword();

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void setRecommendedPassword() {
        password.runPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
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
        testData.setAddExternalSecurityKey(false);
        testData.setReLogin(true);

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void password2() throws IOException {
        testData.setProperties(testData.getTestSuite());
        testData.setNewPassword(origPassword);

        testData.setUseRecommendedPw(false);
        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout2() {
        logout.runLogout();
    }
}
