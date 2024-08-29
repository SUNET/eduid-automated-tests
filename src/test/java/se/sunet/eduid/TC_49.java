package se.sunet.eduid;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import java.io.IOException;

public class TC_49 extends BeforeAndAfter {
    private String username1, password1;

    //Register first account
    @Test
    void registerFirstAccount(){
    }

    @Test( dependsOnMethods = {"registerFirstAccount"} )
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        register.runRegister();
    }

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

        //Store user credentials for the first user
        username1 = testData.getUsername();
        password1 = testData.getPassword();
    }

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
    void confirmIdentityPhone(){
        testData.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityPhone"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout() { logout.runLogout(); }

    //Register the second account

    @Test( dependsOnMethods = {"logout"} )
    void registerSecondAccount(){
    }

    @Test( dependsOnMethods = {"registerSecondAccount"} )
    void startPage2(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void register2(){
        //Special in order not to generate a new identity number
        testData.setRegisterAccount(false);
        register.runRegister();
        testData.setRegisterAccount(true);
    }

    @Test( dependsOnMethods = {"register2"} )
    void confirmEmailAddress2() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress2"} )
    void setRecommendedPassword2() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword2"} )
    void confirmedNewAccount2() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount2"} )
    void login2(){
        testData.setRegisterAccount(false);
        login.verifyPageTitle();

        //Disable Remember me
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div"));
        common.timeoutSeconds(1);

        login.enterUsername();
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login2"} )
    void personalInfo2() {
        //Navigate to settings
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();

        //Set identity to not confirmed
        testData.setIdentityConfirmed(false);

        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo2"} )
    void addPhoneNumber2(){
        //Setting register to false in order to use the previous phone number
        testData.setRegisterAccount(false);
        phoneNumber.addPhoneNumber();
        testData.setRegisterAccount(true);
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber2"} )
    void confirmIdentityPhone2(){
        testData.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityPhone2"} )
    void confirmedIdentity2() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    //Delete the second account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity2"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login3"} )
    void stopBrowser(){
        common.getWebDriver().quit();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    void startBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {
        initBrowser(url, browser, headless, language,testsuite);
        testData.setTestSuite(testsuite);
        testData.setTestCase("TC_49");
    }

    //Log in to first account, verify that phone number and identity needs to be confirmed
    @Test( dependsOnMethods = {"startBrowser"} )
    void checkFirstAccount(){
    }

    @Test( dependsOnMethods = {"checkFirstAccount"} )
    void startPage4(){
        testData.setIncorrectPassword(false);
        testData.setRegisterAccount(false);

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage4"} )
    void login4(){
        testData.setUsername(username1);
        testData.setPassword(password1);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard() {
        //Account is no longer verified

        //Set some user data that will be verified in dashboard
        testData.setDisplayName("Cookie Magic Cookie");

        //Setting Account verified to false to check the correct account verification text at dashboard.
        testData.setAccountVerified(false);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void verifyPhoneNotConfirmed(){
        //Navigate to settings
        common.navigateToSettings();

        //Verify that phone number is not confirmed
        String currentButtonText = common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button").getText();
        common.verifyStrings("BEKRÄFTA", currentButtonText);

    }

    @Test( dependsOnMethods = {"verifyPhoneNotConfirmed"} )
    void verifyIdentityNotConfirmed(){
        //Navigate to Identity
        common.navigateToIdentity();

        //verify that Identity is not confirmed
        common.verifyStringOnPage("Koppla din identitet till ditt eduID");
        common.verifyStringOnPage("Verifiera att du har tillgång till ditt person- eller samordningsnummer.");
    }

    //Delete the first account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"verifyIdentityNotConfirmed"} )
    void navigateToSettings2() { common.navigateToSettings(); }

    @Test( dependsOnMethods = {"navigateToSettings2"} )
    void delete2() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete2"} )
    void startPage5(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage5"} )
    void login5(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }
}
