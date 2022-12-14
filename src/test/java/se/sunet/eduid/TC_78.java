package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_78 extends BeforeAndAfter {
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
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
//        testData.setPhoneNumber("+46701740605");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentityEidas(){
        testData.setConfirmIdBy("eidas");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityEidas"} )
    void confirmedIdentityEidas() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentityEidas"} )
    void confirmIdentityFreja(){
        Common.log.info("Verify identity by Freja eID");

        //Expand Swedish options
        common.click(common.findWebElementById("accordion__heading-swedish"));

        //Add nin-cookie to get successfull response from idp
        common.addNinCookie();

        //Expand Freja menu, since collapsed when change of language
        common.scrollToPageBottom();
        common.timeoutMilliSeconds(500);
        common.findWebElementById("accordion__heading-se-freja").click();

        //Select Freja eID
        common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button").click();

        //Click Use Freja eID in pop-up dialog
        common.findWebElementById("eidas-info-modal-accept-button").click();

        //Select and submit user at reference IDP
        confirmIdentity.selectAndSubmitUserRefIdp();

    }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentityFreja() {
        testData.setConfirmIdBy("freja");
        confirmedIdentity.runConfirmIdentity();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentityFreja"} )
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
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}