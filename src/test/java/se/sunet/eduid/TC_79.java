package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_79 extends BeforeAndAfter {
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

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin();

//        common.explicitWaitClickableElement("//*[@id=\"header\"]/nav/button");
    }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityFrejaNoSwedishPnr(){
        //Set phone number to empty, so no phone labels are verified
        testData.setPhoneNumber("");

        testData.setConfirmIdBy("frejaNoSwedishPnr");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityFrejaNoSwedishPnr"} )
    void navigateBackToEduId() {
        common.getWebDriver().navigate().back();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"navigateBackToEduId"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){
        testData.setRegisterAccount(false);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
