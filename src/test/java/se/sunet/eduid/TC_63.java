package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_63 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void disableRememberMe() {
        //Set remember me function
        testData.setRememberMe(false);
        common.rememberMe();
    }

    @Test( dependsOnMethods = {"disableRememberMe"} )
    void runLoginOtherDevice() {
        testData.setOtherDeviceSubmitCode("timeout");

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void waitForTimeout() {
        common.timeoutSeconds(121);
    }

    @Test( dependsOnMethods = {"waitForTimeout"} )
    void verifyLabels() {
        //Verify labels English
        common.verifyString(By.xpath("//*[@id=\"content\"]/div/div[2]"), "The login attempt was " +
                "aborted or exceeded the time limit. Please try again.");
        common.verifyString(By.id("response-code-cancel-button"), "CANCEL");
        common.verifyString(By.id("refresh-get-new-code"), "RETRY");
    }

    @Test( dependsOnMethods = {"verifyLabels"} )
    void pressCancel() {
        //Press cancel button
        common.click(common.findWebElement(By.id("response-code-cancel-button")));

        //wait for Proceed button at next page
        common.verifyString(By.id("login-other-device-button"), "OTHER DEVICE");
    }
}
