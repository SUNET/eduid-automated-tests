package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_87 extends BeforeAndAfter {
/*
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        testData.setIdentityNumber("");
        register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin();

        common.explicitWaitClickableElement("//*[@id=\"header-nav\"]/button/span");
    }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        testData.setRegisterAccount(true);

        //Navigate to settings
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void storeEppn(){
        advancedSettings.pressAdvancedSettings();
        common.timeoutSeconds(1);
        advancedSettings.storeEppn();
        common.timeoutSeconds(1);
    }
    @Test( dependsOnMethods = {"storeEppn"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout() { logout.runLogout(); }
*/

//    @Test( dependsOnMethods = {"logout"} )
    @Test
    void navigateToFidusTestSkolverketDnp() {
        testData.setUsername("ySxlpB9S@dev.eduid.sunet.se");
        testData.setPassword("vjla oauz 1qzm");
        testData.setEppn("kavav-motiv");

        common.navigateToUrl("https://fidustest.skolverket.se/DNP-staging/");

        //Wait for login button (without eID) at skolverket dnp page
        common.explicitWaitClickableElement("//div[2]/div/div/p[2]");
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
    void loginWithoutEid() {
        //Click on login button (without eID)
        common.findWebElementByXpath("//div[2]/div/div/p[2]/a").click();

        //Wait for idp search field
        common.explicitWaitClickableElementId("searchinput");
    }

    @Test( dependsOnMethods = {"loginWithoutEid"} )
    public void navigateEduId(){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys("eduid staging");
        common.timeoutMilliSeconds(3500);

        //Select eduid staging
        common.click(common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/a[1]"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(2000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
    void login2(){
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login2"} )
    void validateSuccessfulLogin(){
        //Wait for handeling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
