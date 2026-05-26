package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

@Slf4j
public class TC_8 extends BeforeAndAfter {
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
    void registerInternalSecurityKey() {
        testData.setAddInternalPassKey(true);

        //Add security key
        securityKey.addSecurityKey();

        //Press continue to password button
        common.findWebElement(By.id("finish-signup")).click();
    }

    @Test( dependsOnMethods = {"registerInternalSecurityKey"} )
    void confirmedNewAccount() { confirmedNewAccount.runConfirmedNewAccount(); }

    @Test( dependsOnMethods = {"confirmedNewAccount"} )
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
        testData.setRegisterAccount(false);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage3"} )
    void verifyAccountDeleted(){
        testData.setAccountDeleted(true);

        //Login page for extra security select security key mfa method
        testData.setMfaMethod("securitykey");
        extraSecurity.selectMfaMethod();

        login.signIn();
    }
}
