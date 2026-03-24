package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_4 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setGivenName("Haans");
        testData.setSurName("Vall");
        testData.setDisplayName("Haans Vall");
        testData.setUsername("BzZjzKty@dev.eduid.sunet.se");
        testData.setPassword("hlpe 80ax 3ihe");

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void setLanguageEnglish() {
        testData.setLanguage("English");
        account.runAccount();
    }

    @Test( dependsOnMethods = {"setLanguageEnglish"} )
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
    void setLanguage2() {
        testData.setLanguage("Svenska");
        account.runAccount();
    }

    @Test( dependsOnMethods = {"setLanguage2"} )
    void logout2() {
        logout.runLogout();
    }
}
