package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class TC_26 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setResetPassword(true);
        testData.setUsername("nonExistingUser@dev.eduid.se");

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void requestNewPassword() {
        //Setting incorrect password to verify status message when user can not be found
        testData.setIncorrectPassword(true);

        requestNewPassword.runRequestNewPassword();
    }
}
