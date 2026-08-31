package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_76 extends BeforeAndAfter {
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
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice"} )
    void clickDoneThreeTimes() {
        common.timeoutMilliSeconds(500);

        //Press Done button
        testData.setOtherDeviceSubmitCode("true");
        loginOtherDevice.submitCode();

        common.timeoutMilliSeconds(500);

        //Press Done button
        testData.setOtherDeviceSubmitCode("true");
        loginOtherDevice.submitCode();

        common.timeoutMilliSeconds(500);

        //Press Done button
        testData.setOtherDeviceSubmitCode("true");
        loginOtherDevice.submitCode();
    }

    @Test( dependsOnMethods = {"clickDoneThreeTimes"} )
    void verifyAbortedMessage() {
        common.verifyString(By.xpath("//*[@id=\"content\"]/div/p"), "Inloggningen är inte giltig längre");

        common.selectEnglish();
        common.verifyString(By.xpath("//*[@id=\"content\"]/div/p"), "The request is not valid anymore");
    }

    @Test( dependsOnMethods = {"verifyAbortedMessage"} )
    void pressCancel(){
        //Press cancel button
        testData.setOtherDeviceSubmitCode("false");
        loginOtherDevice.submitCode();

        //wait for the Log in page to be loaded
        common.verifyString(By.id("login-other-device-button"), "OTHER DEVICE");
    }
}
