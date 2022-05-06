package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_64 extends BeforeAndAfter {
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
        testData.setOtherDeviceSubmitCode("timeout");

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void waitForTimeout() {
        //Wait
        common.selectSwedish();

        //Check timer is present
        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/div/ol/li[3]/div[1]").isDisplayed(),
                "Timer is missing");

        common.timeoutSeconds(121);
    }

    @Test( dependsOnMethods = {"waitForTimeout"} )
    void verifyLabels() {
        //verify labels Swedish
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div[2]", "Inloggningsförsöket " +
                "avbröts eller tog för lång tid. Var vänlig försök igen.");
        common.verifyStringById("response-code-cancel-button", "AVBRYT");
        common.verifyStringById("refresh-get-new-code", "FÖRSÖK IGEN");

    }

    @Test( dependsOnMethods = {"verifyLabels"} )
    void pressRetry() {
        //Press Retry button
        common.click(common.findWebElementById("refresh-get-new-code"));

        //Verify labels again
        common.selectEnglish();
        loginOtherDevice.checkLabels();

        common.timeoutSeconds(4);
        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/div/ol/li[3]/div[1]").isDisplayed(),
                "Timer is missing");
    }
}
