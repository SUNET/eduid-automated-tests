package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_65 extends BeforeAndAfter {
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
    void clickLoginShortcut() {
        loginOtherDevice.clickLoginShortcut();
    }

    @Test( dependsOnMethods = {"clickLoginShortcut"} )
    void confirmLogin() {
        loginOtherDevice.verifyConfirmLoginLabels();
    }

    @Test( dependsOnMethods = {"confirmLogin"} )
    void waitForTimeout() {
        common.timeoutSeconds(3);

        //Check timer is present
        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/div[2]/span[2]").isDisplayed(),
                "Timer is missing");

        common.timeoutSeconds(121);
    }

    @Test( dependsOnMethods = {"waitForTimeout"} )
    void verifyLabels() {
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "The code has expired, please close this browser window.");

        common.selectSwedish();
        common.timeoutSeconds(121);

        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Koden har gått ut och du bör stänga det här fönstret");
    }
}