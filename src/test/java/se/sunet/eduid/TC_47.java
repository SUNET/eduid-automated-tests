package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_47 extends BeforeAndAfter {
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
    void setRecommendedPassword() { password.setPassword(); }

    @Test( dependsOnMethods = {"setRecommendedPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");
        confirmIdentity.runConfirmIdentity(); }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void deleteIdentityConfirmation() {
        common.selectEnglish();

        //Click remove identity button
        common.findWebElementById("remove-identity-nin").click();

        //Verify text and labels in remove identity pop-up
        common.verifyStringByXpath("//*[@id=\"remove-identity-verification\"]/div/div/div[1]/h4",
                "Disconnect your identity");
        common.verifyStringByXpath("//*[@id=\"remove-identity-verification\"]/div/div/div[2]",
                "Are you sure you want to disconnect your identity from your eduID account?");
        common.verifyStringById("remove-identity-verification-accept-button", "CONFIRM");

        //Close pop up
        common.click(common.findWebElementById("remove-identity-verification-close-button"));

        //Select Swedish
        common.selectSwedish();

        //Click remove identity button
        common.findWebElementById("remove-identity-nin").click();

        //Verify text and labels in remove identity pop-up
        common.verifyStringByXpath("//*[@id=\"remove-identity-verification\"]/div/div/div[1]/h4",
                "Frånkoppla din identitet");
        common.verifyStringByXpath("//*[@id=\"remove-identity-verification\"]/div/div/div[2]",
                "Är du säker på att du vill ta bort koppling till din identitet från ditt eduID konto?");
        common.verifyStringById("remove-identity-verification-accept-button", "BEKRÄFTA");

        //Press confirm delete
        common.click(common.findWebElementById("remove-identity-verification-accept-button"));

        common.explicitWaitClickableElementId("accordion__heading-swedish");
    }


    //Delete the account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"deleteIdentityConfirmation"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();

        //Verify the extra pop-up when logged in +5minutes
        deleteAccount.confirmDeleteAfter5Min();
    }

    @Test( dependsOnMethods = {"delete"} )
    void login2(){
        login.verifyPageTitle();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();

        common.timeoutSeconds(5);
    }

    @Test( dependsOnMethods = {"login2"} )
    void startPage2(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void verifyAccountDeleted(){
        testData.setIncorrectPassword(true);
        login.verifyPageTitle();
        login.enterPassword();
        login.signIn();

        testData.setIncorrectPassword(false);
    }
}
