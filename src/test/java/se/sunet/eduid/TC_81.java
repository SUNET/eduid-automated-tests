package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_81 extends BeforeAndAfter {
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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        //Account is not verified
        testData.setAccountVerified(false);

        //Set some user data that will be verified in dashboard
        testData.setDisplayName("");
        testData.setPhoneNumber("");

        //Setting register account to true to just check that the eppn is present on dashboard (eppn value is unknown at this point).
        testData.setRegisterAccount(true);

        //Setting Account verified to false to check the correct account verification text at dashboard.
        testData.setAccountVerified(false);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() {
        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    //Delete the account
    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void navigateToSettings() { common.navigateToSettings(); }

    @Test( dependsOnMethods = {"navigateToSettings"} )
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

        testData.setIncorrectPassword(false);
    }

    //Reset password and verify that the Identity is verified
    @Test( dependsOnMethods = {"login2"} )
    void login3(){
        testData.setResetPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login3"} )
    void requestNewPassword() {
        requestResetPwEmail.runRequestResetPwEmail();
    }

    @Test( dependsOnMethods = {"requestNewPassword"} )
    void emailSent() { emailSent.runEmailSent(); }

    @Test( dependsOnMethods = {"emailSent"} )
    void emailLink() { emailLink.runEmailLink();
        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"emailLink"} )
    void resetWithoutExtraSecurity() {
        testData.setSendMobileOneTimePassword("no");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"resetWithoutExtraSecurity"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void login4(){
        testData.setResetPassword(false);
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard2() {
        //Account is no longer verified

        //Set some user data that will be verified in dashboard
        testData.setDisplayName("Ulla Alm");

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void identity() {
        //Verify that identity is not confirmed
        common.navigateToIdentity();

        common.verifyStringOnPage("Koppla din identitet till ditt eduID");
        common.verifyStringOnPage("För att använda vissa tjänster behöver din identitet verifieras. " +
                "Koppla din identitet till ditt eduID för att få mest användning");
        common.verifyStringOnPage("Välj din huvudsakliga identifieringsmetod");
    }

    @Test( dependsOnMethods = {"identity"} )
    void navigateToSettings2() {
        //Verify that phone number is still confirmed
        common.navigateToSettings();

        //common.verifyStringByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]", "PRIMÄR");
    }

    //Delete account when confirmed that identity is no longer verified
    @Test( dependsOnMethods = {"navigateToSettings2"} )
    void delete2() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage4(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void login5(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}