package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_45 extends BeforeAndAfter {
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

    @Test( dependsOnMethods = {"login"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings();
        testData.setEmail(testData.getUsername().toLowerCase());
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
        login.verifyPageTitle();
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

        testData.setRegisterAccount(false);

        common.timeoutSeconds(1);
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"startPage2"} )
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
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}
