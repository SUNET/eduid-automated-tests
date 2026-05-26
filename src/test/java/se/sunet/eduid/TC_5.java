package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

@Slf4j
public class TC_5 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){ register.runRegister();}

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress"} )
    void registerExternalSecurityKey() {
        testData.setAddExternalSecurityKey(true);

        //Add security key
        securityKey.addSecurityKey();

        //Press continue to password button
        common.findWebElement(By.id("continue-to-password")).click();
    }

    @Test( dependsOnMethods = {"registerExternalSecurityKey"} )
    void confirmPassword() {
        testData.setUseRecommendedPw(false);

        //Get default password from properties
        testData.setNewPassword(testData.getPassword());
        password.setPassword();
    }

    @Test( dependsOnMethods = {"confirmPassword"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
    void login(){
        testData.setRegisterAccount(false);

        login.enterPassword();
        login.enterUsername();
        login.clickLoginButton();
    }

    @Test( dependsOnMethods = {"login"} )
    void loginExtraSecuritySecurityKey() {
        //Login page for extra security select security key mfa method
        testData.setMfaMethod("securitykey");
        extraSecurity.selectMfaMethod();

        Common.log.info("Log in with securitykey");
    }

    @Test( dependsOnMethods = {"loginExtraSecuritySecurityKey"} )
    void dashboard() {
        login.storeEppn();

        dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void confirmSecurityKey() {
        common.navigateToSecurity();
        common.findWebElement(By.cssSelector("#manage-security-keys > figure > div > div:nth-child(3) > span > button:nth-child(2)")).click();

        common.addNinCookie();
    }

    @Test( dependsOnMethods = {"confirmSecurityKey"} )
    void selectUserRefIdp(){
        //Select and submit user
        common.refIdpEnterAndSubmitUser();
    }

    @Test( dependsOnMethods = {"selectUserRefIdp"} )
    void verifiedSecurityKeyStatus() {
        securityKey.verifiedSecurityKey();
    }

    @Test( dependsOnMethods = {"verifiedSecurityKeyStatus"} )
    void dashboard2() {
        testData.setIdentityConfirmed(true);
        testData.setVerifySecurityKeyByFreja(true);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard2"} )
    void delete() {
        testData.setDeleteButton(true);
        deleteAccount.runDeleteAccount();
    }

    @Test( dependsOnMethods = {"delete"} )
    void startPage3(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        //Login page for extra security select security key mfa method
        extraSecurity.selectMfaMethod();

        login.signIn();
    }
}
