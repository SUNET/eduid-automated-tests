package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_47 extends BeforeAndAfter {
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
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        common.setRegisterAccount(true);

        //Navigate to settings
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        personalInfo.runPersonalInfo();

        common.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("frejaID");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void confirmedIdentity() { confirmedIdentity.runConfirmIdentity(); }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void dashboard() { dashBoard.pressSettings(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }
}
