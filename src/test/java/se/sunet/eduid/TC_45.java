package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_45 extends BeforeAndAfter {
/*
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
    void confirmedNewAccount() {
        confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin();

        testData.setEmail(testData.getUsername().toLowerCase());
    }

    @Test( dependsOnMethods = {"login"} )
    void logout() { logout.runLogout(); }
*/

//    @Test( dependsOnMethods = {"logout"} )
    @Test
    void navigateToSwamid(){
        testData.setUsername("9c1qsEFn@dev.eduid.sunet.se");
        testData.setPassword("bt14 fyw8 079c");
        testData.setEppn("purul-kakid");
        testData.setIdentityNumber("199001222398");
        testData.setGivenName("Erika");
        testData.setSurName("Lööf");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());

        common.navigateToUrl("https://release-check.qa.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login2(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));

        common.explicitWaitPageTitle("Release check for SWAMID");
    }

    @Test( dependsOnMethods = {"login2"} )
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(false); }
/*
    @Test( dependsOnMethods = {"swamidData"} )
    void startPage2() {
        common.navigateToUrl("https://dev.eduid.se");

        //Click on sign in link
        common.click(common.findWebElementById("login-button"));

        testData.setRegisterAccount(false);

        common.timeoutSeconds(1);
    }

    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"startPage2"} )
    void dashboard() {
         //Setting register account to true to just check that the eppn is present on dashboard (eppn value is unknown at this point).
        testData.setRegisterAccount(true);

        //Setting Account verified to false to check the correct account verification text at dashboard.
        testData.setAccountVerified(false);

        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
        common.timeoutSeconds(2);
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){
        testData.setRegisterAccount(false);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();
    }*/
}
