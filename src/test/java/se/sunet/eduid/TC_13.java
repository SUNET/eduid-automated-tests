package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_13 extends BeforeAndAfter {
    @Test
    void swamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void createEduIDAccount(){
        common.findWebElement(By.id("register")).click();
    }

    @Test( dependsOnMethods = {"createEduIDAccount"} )
    void register(){
        testData.setRegisterAccount(true);
        testData.setSwamidSp(true);
        register.runRegister();}

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
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(true); }
}
