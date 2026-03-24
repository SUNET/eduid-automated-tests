package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_7 extends BeforeAndAfter {
    String userName;

    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setGivenName("Mikaela");
        testData.setSurName("Djerf");
        testData.setDisplayName("Mikaela Djerf");
        testData.setUsername("C6jqJGk6@dev.eduid.sunet.se");
        testData.setPassword("u2dy 9ugs o9vq");

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

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        //Set local username from properties
        userName = testData.getUsername();
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        //Click on not you, otherwise last username is pre-filled
        common.findWebElementById("wrong-person-button").click();

        testData.setUsername("eduidtest.se1@gmail.com");
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() {
        testData.setUsername(userName);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void emailAddresses2() {
        testData.setRemoveNewEmail1(true);
        testData.setAddNewEmail1("");
        emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses2"} )
    void logout2() {
        logout.runLogout();
    }
}
