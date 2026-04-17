package se.sunet.eduid;

import org.testng.annotations.*;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_2 extends BeforeAndAfter {
    @Test
    void startPage(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setGivenName("Bruno");
        testData.setSurName("Carlgren");
        testData.setDisplayName("Bruno Carlgren");
        testData.setUsername("dQq6aHgJ@dev.eduid.sunet.se");
        testData.setPassword("7dta qlh1 ar00");

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        dashBoard.runDashBoard();
   }

    @Test( dependsOnMethods = {"dashboard"} )
    void updateUserNames() {
        common.navigateToIdentity();

        testData.setGivenName("palle");
        testData.setSurName("kuling");
        testData.setDisplayName("palle kuling");
        name.runName();
    }

    @Test( dependsOnMethods = {"updateUserNames"} )
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
    void dashboard2() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard2"} )
    void setNewUserNames2() {
        common.navigateToIdentity();

        testData.setGivenName("Bruno");
        testData.setSurName("Carlgren");
        testData.setDisplayName("Bruno Carlgren");

        name.runName();
    }

    @Test( dependsOnMethods = {"setNewUserNames2"} )
    void logout2() {
        logout.runLogout();
    }
}
