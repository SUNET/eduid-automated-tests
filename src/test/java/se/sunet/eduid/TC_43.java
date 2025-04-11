package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.time.LocalDate;

public class TC_43 extends BeforeAndAfter {
    private final LocalDate localDate = LocalDate.now();

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
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityEidas(){
        testData.setConfirmIdBy("eidas");
        confirmIdentity.runConfirmIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmIdentityEidas"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
        common.selectSwedish();
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout(){ logout.runLogout(); }

    //Log in to the support tool
    @Test( dependsOnMethods = {"startPageSupport"} )
    void loginSupport(){
        loginSupportTool();
    }

    //Navigate to the support tool
    @Test( dependsOnMethods = {"logout"} )
    void startPageSupport(){
        common.navigateToUrl("https://support.eduid.docker");
    }

    //Search for newly created account
    @Test( dependsOnMethods = {"loginSupport"} )
    void searchAndVerifyUserData(){
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(testData.getEppn());
        common.click(common.findWebElementByXpath("//div/form/div/p[2]/button"));

        registeredData.runRegisteredData();
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"searchAndVerifyUserData"} )
    void startPage2(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        //Click on not you, otherwise last username is pre-filled
        common.findWebElementById("wrong-person-button").click();

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login2"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount(); }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }

    //Navigate to the support tool again
    @Test( dependsOnMethods = {"verifyAccountDeleted"} )
    void startPageSupport2(){
        common.navigateToUrl("https://support.eduid.docker");
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
        common.findWebElementByXpath("//div/form/div/p[1]/input").sendKeys(testData.getEppn());
        common.click(common.findWebElementByXpath("//div/form/div/p[2]/button"));

        //Verify Terminated Status contains today's timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[10]/td", String.valueOf(localDate));
    }

    private void loginSupportTool(){
        common.explicitWaitClickableElementId("email");

        //Click on not you, otherwise last username is pre-filled
        common.findWebElementById("wrong-person-button").click();

        //Enter support username and password
        common.findWebElementById("username").sendKeys(testData.getSupportUsername());
        common.findWebElementById("current-password").sendKeys("v8ic uz53 wd85");

        //eppn: dalum-zifuj

        common.click(common.findWebElementById("login-form-button"));
    }
}
