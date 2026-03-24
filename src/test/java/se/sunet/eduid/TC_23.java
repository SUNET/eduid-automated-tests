package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_23 extends BeforeAndAfter {
    String origPassword;

    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        //testData.setGivenName("Karl");
        //testData.setSurName("Karlsson");
        //testData.setDisplayName("Karl Karlsson");
        //testData.setUsername("Npjt4zv9@dev.eduid.sunet.se");
        //testData.setEmail("Npjt4zv9@dev.eduid.sunet.se");
        //testData.setPassword("et69 avnj a115");


        origPassword = testData.getPassword();

        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() { requestNewPassword.runRequestNewPassword(); }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink(); }

    @Test( dependsOnMethods = {"emailLink"} )
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login2(){
        testData.setResetPassword(false);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void changeToDefaultPassword(){}

    @Test( dependsOnMethods = {"changeToDefaultPassword"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void password2() throws IOException {
        testData.isIncorrectPassword();
        testData.setUseRecommendedPw(false);
        testData.setProperties(testData.getTestSuite());
        testData.setNewPassword(origPassword);

        password.runPassword();
    }

    @Test( dependsOnMethods = {"password2"} )
    void logout() {
        logout.runLogout();
    }
}
