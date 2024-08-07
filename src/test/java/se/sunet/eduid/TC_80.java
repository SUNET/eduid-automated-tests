package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_80 extends BeforeAndAfter {
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
    void confirmPassword() { confirmPassword.runConfirmPassword(); }

    @Test( dependsOnMethods = {"confirmPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);
        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
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
    void extraSecurityFreja() {
        testData.setSendMobileOneTimePassword("freja");
        extraSecurity.runExtraSecurity();
    }

    @Test( dependsOnMethods = {"extraSecurityFreja"} )
    void selectIdRefIdp() {
        //Set another user to be selected at rep. idp to get ID locked response at eduID
        testData.setRefIdpUser("Frida Kranstege (197705232382)");
        confirmIdentity.selectAndSubmitUserRefIdp();
    }

    @Test( dependsOnMethods = {"selectIdRefIdp"} )
    void newPassword() { setNewPassword.runNewPassword(); }

    @Test( dependsOnMethods = {"newPassword"} )
    void passwordChanged() { passwordChanged.runPasswordChanged(); }

    @Test( dependsOnMethods = {"passwordChanged"} )
    void login4(){
        testData.setResetPassword(false);
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login4"} )
    void identity() {
        //Verify that identity is still confirmed
        common.navigateToIdentity();

        common.verifyStringOnPage("Ditt eduID är redo att användas");
        common.verifyStringOnPage("Följande identiteter är nu kopplade till ditt eduID");
        common.verifyStringOnPage("Svenskt personnummer");
    }

    @Test( dependsOnMethods = {"identity"} )
    void verifyConfirmedPhone() {
        //Verify that phone number is still confirmed
        common.navigateToSettings();

        common.verifyStringByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]", "PRIMÄR");
    }

    //Delete account when confirmed that identity is no longer verified
 /*   @Test( dependsOnMethods = {"verifyConfirmedPhone"} )
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
    }*/
}