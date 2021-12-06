package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_38 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() {
        confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        testData.setDisplayName("lägg till namn");
        testData.setGivenName("lägg till");
        testData.setSurName("namn");
        testData.setIdentityNumber("lägg till personnummer");
        testData.setPhoneNumber("lägg till telefonnummer");
        testData.setEmail(testData.getUsername());

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}
