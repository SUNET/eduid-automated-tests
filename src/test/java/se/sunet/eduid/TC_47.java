package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_47 extends BeforeAndAfter {
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
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        personalInfo.runPersonalInfo();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        testData.setPhoneNumber("+46701740605");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        testData.setConfirmIdBy("frejaID");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void confirmedIdentity() { confirmedIdentity.runConfirmIdentity(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void dashboard() { dashBoard.pressSettings(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}