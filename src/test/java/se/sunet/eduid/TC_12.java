package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_12 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setGivenName("Margareta");
        testData.setSurName("Hultling");
        testData.setDisplayName("Margareta Hultling");
        testData.setUsername("LUCIzvcP@dev.eduid.sunet.se");
        testData.setPassword("grl2 4mtn uokj");

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void emailAddresses() {
        testData.setAddNewEmail1("eduidtest.se1@gmail.com");
        testData.setConfirmNewEmail1("wrongCode");
        emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void logout() {
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setReLogin(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() {
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void emailAddresses2() {
        testData.setAddNewEmail1("");
        testData.setRemoveNewEmail1(true);
        emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses2"} )
    void logout2() {
        logout.runLogout();
    }
}
