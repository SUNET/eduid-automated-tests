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
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void setCustomPassword() {
        //Use custom password and not recommended
        testData.setUseRecommendedPw(false);

        //Get default password from properties
        testData.setNewPassword(testData.getPassword());

        password.setPassword();
    }

    @Test( dependsOnMethods = {"setCustomPassword"} )
    void confirmedNewAccount() {
        confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        testData.setEmail(testData.getUsername());

        //Account is a new register
        testData.setRegisterAccount(true);

        //Setting Account verified to false to check the correct account verification text at dashboard.
        testData.setAccountVerified(false);
        dashBoard.runDashBoard();
        testData.setRegisterAccount(false);
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setReLogin(true);
        testData.setIncorrectPassword(true);

        login.runLogin();
    }
}
