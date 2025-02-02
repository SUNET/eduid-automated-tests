package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_70 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void disableRememberMe() {
        //Set remember me function
        testData.setRememberMe(false);
        common.rememberMe();
    }

    @Test( dependsOnMethods = {"disableRememberMe"} )
    void runLoginOtherDevice1st() {
        testData.setOtherDeviceSubmitCode("true");
        testData.setOtherDeviceFillCode(true);

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice1st"} )
    void runLoginOtherDevice2nd() {
        common.timeoutSeconds(1);

        loginOtherDevice.enterCode("9", "8", "7", "6", "5", "4");
        loginOtherDevice.submitCode();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice2nd"} )
    void runLoginOtherDevice3rd() {
        common.timeoutSeconds(1);

        loginOtherDevice.enterCode("3", "2", "1", "9", "8", "7");
        loginOtherDevice.submitCode();

        common.explicitWaitClickableElementId("response-code-cancel-button");
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice3rd"} )
    void verifyStatusMessage(){
        common.verifyStringOnPage("The request is not valid anymore");

        common.selectSwedish();

        common.verifyStringOnPage("Inloggningen är inte giltig längre");

        //Click Abort button
        common.findWebElementById("response-code-cancel-button").click();

        //Wait for the Log in page
        common.explicitWaitClickableElementId("login-form-button");
    }
}
