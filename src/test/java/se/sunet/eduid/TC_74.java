package se.sunet.eduid;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_74 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void runLoginOtherDevice() {
        testData.setOtherDeviceSubmitCode("not used");
        testData.setRememberMe(true);

        loginOtherDevice.runLoginOtherDevice();

        //Check timer is present
        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/div/ol/li[3]/div/div[2]/span[2]").isDisplayed(),
                "Timer is missing");
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void clickLoginShortcut() {
        common.timeoutSeconds(1);

        loginOtherDevice.clickLoginShortcut();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"clickLoginShortcut"} )
    void confirmLoginCancel() {
        loginOtherDevice.verifyConfirmLoginLabels();

        //Check timer is present
        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/div[2]/span[2]").isDisplayed(),
                "Timer is missing");

        //Click login button
        common.findWebElementById("cancel-other-device-button").click();
        common.timeoutMilliSeconds(300);
    }

    @Test( dependsOnMethods = {"confirmLoginCancel"} )
    void verifyCanceledLogin() {
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Log in on another device");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Request cancelled. You should close this browser window.");

        common.selectSwedish();
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Logga in på en annan enhet");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Avbrutet. Du kan stänga det här fönstret.");
    }
}
