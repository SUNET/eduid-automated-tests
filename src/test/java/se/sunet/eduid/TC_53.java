package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_53 extends BeforeAndAfter {
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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    //First without any personal info saved
    @Test( dependsOnMethods = {"login"} )
    void securityKeyWithoutPersonalInfo() {
        testData.setAddSecurityKey(false);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"securityKeyWithoutPersonalInfo"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addSecurityKey() {
        //Set mfa method to be used to "security key" at login.
        testData.setMfaMethod("freja");

        testData.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }

    @Test( dependsOnMethods = {"addSecurityKey"} )
    void navigateToDashboard() {
        common.timeoutSeconds(2);
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/");
        common.explicitWaitClickableElementId("mfa-security-key");
    }

    @Test( dependsOnMethods = {"navigateToDashboard"} )
    void loginExtraSecurity2() {
        //Set mfa method to be used to "security key" to be able to delete the account.
        testData.setMfaMethod("securitykey");
        loginExtraSecurity.runLoginExtraSecurity();
    }

    @Test( dependsOnMethods = {"loginExtraSecurity2"} )
    void navigateToSettings() {
        //Click on settings
        common.explicitWaitClickableElement("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span");
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
    }

    @Test( dependsOnMethods = {"navigateToSettings"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void loginExtraSecurity(){
        loginExtraSecurity.runLoginExtraSecurity();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"loginExtraSecurity"} )
    void startPage2(){startPage.runStartPage();}

    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.runLogin();
    }
}
