package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_39 extends BeforeAndAfter {
    @Test
    void startPage(){
        common.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void logout() {
        logout.runLogout();
    }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2(){
        common.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void register2(){
        common.setGenerateUsername(false);
        register.runRegister(); }

    @Test( dependsOnMethods = {"register2"} )
    void confirmHuman2() { confirmHuman.runConfirmHuman(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmHuman2"} )
    void login2(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        common.setDisplayName("inget namn sparat");
        common.setGivenName("inget");
        common.setSurName("namn");
        common.setIdentityNumber("lägg till personnummer");
        common.setPhoneNumber("inget telefonnummer sparat");
        common.setEmail(common.getUsername());

        dashBoard.pressSettings();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }
}
