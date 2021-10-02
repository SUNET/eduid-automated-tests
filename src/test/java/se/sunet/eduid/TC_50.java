package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_50 extends BeforeAndAfter {
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
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login1(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login1"} )
    void personalInfo() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        personalInfo.runPersonalInfo(); }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("frejaID");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void addSecurityKey() {
        common.setAddSecurityKey(true);
        securityKey.runSecurityKey();
    }
/*
    @Test( dependsOnMethods = {"addSecurityKey"} )
    void logout() {
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/");
        common.timeoutSeconds(2);

        //common.findWebElementById("logout").click();
        logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void navigateSwamidMfaCheck() {
        common.navigateToUrl("https://mfa-check.swamid.se");
        common.timeoutSeconds(2);
     }

    @Test( dependsOnMethods = {"navigateSwamidMfaCheck"} )
    void swamidTest(){
        common.findWebElementByLinkText("SWAMID Test").click();
        common.findWebElementByLinkText("Log in via SWAMID Test federation").click();
    }


    @Test( dependsOnMethods = {"swamidTest"} )
    void navigateToEduId(){
        swamid.navigateEduId("eduid");
    }

    @Test( dependsOnMethods = {"navigateToEduId"} )
    void login(){
        Login login = new Login(common);
        login.enterUsernamePassword();

        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();
    }

    @Test( dependsOnMethods = {"login"} )
    void selectFrejaEid(){
        common.timeoutSeconds(2);
        common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div[1]/h2/span", "Two-factor authentication");

        //Select Freja
        common.findWebElementByXpath("//*[@id=\"mfa-try-another-way\"]/div[2]/button[2]/span").click();

        common.explicitWaitPageTitle("Freja eID IDP");
    }

    @Test( dependsOnMethods = {"selectFrejaEid"} )
    void verifyFreja(){
        common.verifyPageTitle("Freja eID IDP");

        common.verifyStringOnPage("Logga in med personnummer");
    }

*/
/*
    @Test( dependsOnMethods = {"addSecurityKey"} )
    void delete() {
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/");
        common.timeoutSeconds(2);
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        common.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login3(){
        common.setIncorrectPassword(true);
        login.runLogin();
    }
*/
}
