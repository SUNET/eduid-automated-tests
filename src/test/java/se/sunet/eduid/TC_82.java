package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_82 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        testData.setMagicCode("notTheCorrectMagicCode");
        register.runRegister(); }

    //Enter incorrect code three times
    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress1stAttempt() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress1stAttempt"} )
    void confirmEmailAddress2ndAttempt() { confirmEmailAddress.runConfirmEmailAddress(); }

    @Test( dependsOnMethods = {"confirmEmailAddress2ndAttempt"} )
    void confirmEmailAddress3rdAttempt() { confirmEmailAddress.runConfirmEmailAddress(); }
}
