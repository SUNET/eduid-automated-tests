package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_39 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void logout() {
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void register2(){
        testData.setGenerateUsername(false);
        register.runRegister(); }

    @Test( dependsOnMethods = {"register2"} )
    void confirmHuman2() { confirmHuman.runConfirmHuman(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmHuman2"} )
    void login2(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        testData.setDisplayName("inget namn sparat");
        testData.setGivenName("inget");
        testData.setSurName("namn");
        testData.setIdentityNumber("lägg till personnummer");
        testData.setPhoneNumber("inget telefonnummer sparat");
        testData.setEmail(testData.getUsername());

        dashBoard.pressSettings();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}
