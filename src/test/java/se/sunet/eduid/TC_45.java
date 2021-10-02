package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_45 extends BeforeAndAfter {
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

    @Test( dependsOnMethods = {"login"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings();
    common.setEmail(common.getUsername().toLowerCase());
    }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void navigateToSwamid(){
        common.navigateToUrl("https://release-check.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login2(){
        login.enterUsernamePassword();
        common.findWebElementById("login-form-button").click();

        common.explicitWaitPageTitle("Release check for SWAMID");
    }

    @Test( dependsOnMethods = {"login2"} )
    void swamidData(){ swamidData.runSwamidData(false); }

    @Test( dependsOnMethods = {"swamidData"} )
    void startPage2() {
        common.navigateToUrl("https://dev.eduid.se");

        //Click on sign in link
        common.findWebElementByXpath("//*[@id=\"login\"]/a").click();

        common.setRegisterAccount(false);
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"startPage2"} )
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
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }
}
