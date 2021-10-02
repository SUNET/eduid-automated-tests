package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_38 extends BeforeAndAfter {
    @Test
    void startPage(){
        common.setRegisterAccount(true);
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
        common.setRegisterAccount(false);
        login.runLogin(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        common.setDisplayName("lägg till namn");
        common.setGivenName("lägg till");
        common.setSurName("namn");
        common.setIdentityNumber("lägg till personnummer");
        common.setPhoneNumber("lägg till telefonnummer");
        common.setEmail(common.getUsername());

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }
}
