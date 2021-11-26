package se.sunet.eduid;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.WebDriverManager;
import java.io.IOException;

public class TC_49 extends BeforeAndAfter {
    private String username1, password1;

    //Register first account
    @Test
    void registerFirstAccount(){
    }

    @Test( dependsOnMethods = {"registerFirstAccount"} )
    void startPage(){
        common.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        register.runRegister();
    }

    @Test( dependsOnMethods = {"register"} )
    void confirmHuman() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        common.setRegisterAccount(false);
        login.runLogin();

        //Store user credentials for the first user
        username1 = common.getUsername();
        password1 = common.getPassword();
    }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        common.setRegisterAccount(true);

        //Navigate to settings
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();

        common.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        common.setPhoneNumber("+46701740606");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void confirmedIdentity() { confirmedIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout() { logout.runLogout(); }

    //Register the second account

    @Test( dependsOnMethods = {"logout"} )
    void registerSecondAccount(){
    }

    @Test( dependsOnMethods = {"registerSecondAccount"} )
    void startPage2(){
        common.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void register2(){ register.runRegister(); }

    @Test( dependsOnMethods = {"register2"} )
    void confirmHuman2() { confirmHuman.runConfirmHuman(); }

    @Test( dependsOnMethods = {"confirmHuman2"} )
    void confirmedNewAccount2() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount2"} )
    void login2(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login2"} )
    void personalInfo2() {
        //Navigate to settings
        common.setRegisterAccount(true);

        //Navigate to settings
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();

        common.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo2"} )
    void addPhoneNumber2(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber2"} )
    void confirmIdentity2(){
        common.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity2"} )
    void confirmedIdentity2() { confirmedIdentity.runConfirmIdentity(); }


    //Delete the second account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity2"} )
    void dashboard() { dashBoard.pressSettings(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login3"} )
    void stopBrowser(){
        WebDriverManager.quitWebDriver();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    void startBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {
        initBrowser(url, browser, headless, language,testsuite);
        common.setTestSuite(testsuite);
        common.setTestCase("TC_49");
    }

    //Log in to first account, verify that phonenumber and identity needs to be confirmed
    @Test( dependsOnMethods = {"startBrowser"} )
    void checkFirstAccount(){
    }

    @Test( dependsOnMethods = {"checkFirstAccount"} )
    void startPage4(){
        common.setIncorrectPassword(false);
        common.setRegisterAccount(false);

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage4"} )
    void login4(){
        common.setUsername(username1);
        common.setPassword(password1);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login4"} )
    void verifyPhoneNotConfirmed(){
        //Navigate to settings
        dashBoard.pressSettings();

        //Verify that phonenumber is not confirmed
        String currentButtonText = common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span").getText();
        common.verifyStrings("BEKRÄFTA", currentButtonText);

    }

    @Test( dependsOnMethods = {"verifyPhoneNotConfirmed"} )
    void verifyIdentityNotConfirmed(){
        //Navigate to Identity
        confirmIdentity.pressIdentity();

        //verify that Identity is not confirmed
        common.verifyStringOnPage("Koppla din identitet till ditt eduID");
        common.verifyStringOnPage("Lägg till ditt personnummer");
        common.verifyStringOnPage("Bekräfta ditt personnummer");
    }
}
