package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_91 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }
//TODO here a new second account should be created and not "user does already exist"
    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void loginPage(){
        //Press register button at login page
        common.findWebElementById("register").click();
    }

    @Test( dependsOnMethods = {"loginPage"} )
    void register2(){
        register.runRegister(); }

    @Test( dependsOnMethods = {"register2"} )
    void confirmEmailAddress2() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"register2"} )
    void setRecommendedPassword2() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword2"} )
    void confirmedNewAccount2() { confirmedNewAccount.runConfirmedNewAccount(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedNewAccount2"} )
    void login2(){
        testData.setRegisterAccount(false);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard() {
        //Set some user data that will be verified in dashboard
        testData.setDisplayName("inget namn sparat");
        testData.setGivenName("inget");
        testData.setSurName("namn");
        testData.setIdentityNumber("lägg till personnummer");
        testData.setPhoneNumber("inget telefonnummer sparat");
        testData.setEmail(testData.getUsername());

        common.navigateToAccount();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
