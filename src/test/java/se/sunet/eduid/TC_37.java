package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_37 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void confirmEmailAddress() {
        //Set incorrect email verification code
        testData.setEmailVerificationCode("987654");
        confirmEmailAddress.runConfirmEmailAddress(); }
}
