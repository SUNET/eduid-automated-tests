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
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityEidas(){
        testData.setConfirmIdBy("eidas");

        common.navigateToIdentity();
        confirmIdentity.selectConfirmIdentity();
    }


    @Test( dependsOnMethods = {"confirmIdentityEidas"} )
    void confirmedIdentityEidas() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentityEidas"} )
    void confirmIdentityFreja(){
        Common.log.info("Verify identity by Freja eID");
        //Note! This is a special method since both Freja and eIDAS will be used. If only
        // one method to confirm identity, use standard above.

        //Expand Swedish options
        common.click(common.findWebElementById("swedish-button"));

        //Add nin-cookie to get successful response from idp
        common.addNinCookie();

        //Expand Freja menu, since collapsed when change of language
        common.click(common.findWebElementById("se-freja-button"));

        //Select Freja eID by pressing Continue
        common.click(common.findWebElementByXpath("//*[@id=\"se-freja\"]/div/button"));

        //Click Use Freja eID in pop-up dialog
        common.findWebElementById("eidas-info-modal-accept-button").click();

        //Select and submit user at reference IDP
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentityFreja() {
        testData.setConfirmIdBy("freja");
        confirmedIdentity.runConfirmedIdentity();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentityFreja"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}