package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_62 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void disableRememberMe() {
        common.explicitWaitClickableElement("//*[@id=\"content\"]/fieldset/label/div");

        //Set remember me function
        testData.setRememberMe(false);
        common.rememberMe();
    }

    @Test( dependsOnMethods = {"disableRememberMe"} )
    void runLoginOtherDevice() {
        testData.setOtherDeviceSubmitCode("true");
        testData.setOtherDeviceFillCode(true);

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void verifyStatusMessage(){
        //TODO Here we should get a proper error message, 2022-05-03 only a new QR is generated #922
        common.verifyStatusMessage("An error occurred. Please try again later.");
    }
}
