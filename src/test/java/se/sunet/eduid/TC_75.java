package se.sunet.eduid;

import org.springframework.context.annotation.ScopeMetadata;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_75 extends BeforeAndAfter {
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
    void clickLoginShortcut() {
        common.timeoutSeconds(1);
        loginOtherDevice.clickLoginShortcut();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"clickLoginShortcut"} )
    void confirmLogin() {
        loginOtherDevice.verifyConfirmLoginLabels();

        //Click login button
        common.findWebElementById("proceed-other-device-button").click();
        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"confirmLogin"} )
    void login2(){
        common.selectSwedish();

        login.enterPassword();

        //Click Cancel button
        common.click(common.findWebElementById("login-abort-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"login2"} )
    void verifyStatusMessage(){
        common.verifyStatusMessage("Tillfälligt fel, var god försök igen senare.");

        common.selectEnglish();
        common.verifyStatusMessage("An error occurred. Please try again later.");
    }

    @Test( dependsOnMethods = {"verifyStatusMessage"} )
    void closeTab(){
        webdriver.close();
        common.switchToDefaultWindow();
    }

    @Test( dependsOnMethods = {"closeTab"} )
    void pressCancel(){
        common.timeoutMilliSeconds(500);

        //Press cancel button
        testData.setOtherDeviceSubmitCode("false");
        loginOtherDevice.submitCode();

        //wait for Proceed button at next page
        common.explicitWaitClickableElementId("login-other-device-button");
        common.verifyStringById("login-other-device-button", "OTHER DEVICE");
    }
}
