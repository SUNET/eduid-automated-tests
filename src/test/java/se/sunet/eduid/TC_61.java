package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_61 extends BeforeAndAfter {
    @Test
    void startPage() {startPage.runStartPage();}

    @Test( dependsOnMethods = {"startPage"} )
    void disableRememberMe() {
        //Set remember me function
        testData.setRememberMe(false);
        common.rememberMe();
    }

    @Test( dependsOnMethods = {"disableRememberMe"} )
    void runLoginOtherDevice() {
        testData.setOtherDeviceSubmitCode("false");

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void verifyCanceledLogin() {
         //wait for Proceed button at next page
        //common.explicitWaitClickableElementId("login-other-device-button");
        common.verifyStringById("login-other-device-button", "OTHER DEVICE");
    }
}
