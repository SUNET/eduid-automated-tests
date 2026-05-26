package se.sunet.eduid;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

@Slf4j
public class TC_20 extends BeforeAndAfter {
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
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(true); }
}
