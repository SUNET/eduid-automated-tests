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
        register.runRegister(); }

    //Enter incorrect code three times
    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress1stAttempt() {
        //Set incorrect email verification code
        testData.setEmailVerificationCode("987654");
        confirmEmailAddress.runConfirmEmailAddress();
    }

    @Test( dependsOnMethods = {"confirmEmailAddress1stAttempt"} )
    void confirmEmailAddress2ndAttempt() {
        //Set incorrect email verification code
        testData.setEmailVerificationCode("987654");
        confirmEmailAddress.runConfirmEmailAddress();
    }

    @Test( dependsOnMethods = {"confirmEmailAddress2ndAttempt"} )
    void confirmEmailAddress3rdAttempt() {
        //Set incorrect email verification code
        testData.setEmailVerificationCode("987654");
        confirmEmailAddress.runConfirmEmailAddress();
    }
}
