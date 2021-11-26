package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.WebDriverManager;

import java.time.LocalDate;

public class TC_43 extends BeforeAndAfter {
    private final LocalDate localDate = LocalDate.now();

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
    void login(){
        common.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        common.setRegisterAccount(true);

        //Navigate to settings
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        personalInfo.runPersonalInfo();

        common.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber(){
        common.setPhoneNumber("+46701740605");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber"} )
    void confirmIdentity(){
        common.setConfirmIdBy("phone");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentity"} )
    void advancedSettings(){ advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout(){ logout.runLogout(); }

    //Navigate to the support tool
    @Test( dependsOnMethods = {"logout"} )
    void startPageSupport(){
        WebDriverManager.getWebDriver().navigate().to("https://support.dev.eduid.se");
    }

    //Log in to the support tool
    @Test( dependsOnMethods = {"startPageSupport"} )
    void loginSupport(){
        loginSupportTool();
    }

    //Search for newly created account
    @Test( dependsOnMethods = {"loginSupport"} )
    void searchAndVerifyUserData(){
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(common.getEppn());
        common.findWebElementByXpath("//div/form/div/p[2]/button").click();

        registeredData.runRegisteredData();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"searchAndVerifyUserData"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
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

    //Navigate to the support tool again
    @Test( dependsOnMethods = {"login3"} )
    void startPageSupport2(){
        WebDriverManager.getWebDriver().navigate().to("https://support.dev.eduid.se");
    }

    //Log in to the support tool
    @Test( dependsOnMethods = {"startPageSupport2"} )
    void loginSupport2(){
        loginSupportTool();
    }

    //Search for the deleted account and check that terminated timestamp is set with todays date
    @Test( dependsOnMethods = {"loginSupport2"} )
    void searchAndVerifyUserData2(){
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(common.getEppn());
        common.findWebElementByXpath("//div/form/div/p[2]/button").click();

        //Verify Terminated Status contains today's timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[10]/td", String.valueOf(localDate));
    }

    private void loginSupportTool(){
        common.explicitWaitClickableElementId("email");

        common.findWebElementById("email").sendKeys(common.getSupportUsername());
        common.findWebElementByXpath("//*[@id=\"current-password-wrapper\"]/div[2]/input").sendKeys("d72o cqhd hnqc");

        common.findWebElementById("login-form-button").click();
    }
}
