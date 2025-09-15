package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_60 extends BeforeAndAfter {
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
        testData.setOtherDeviceSubmitCode("not used");

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
    void login(){
        common.selectSwedish();

        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));
    }

    @Test( dependsOnMethods = {"login"} )
    void extractCode(){
        loginOtherDevice.extractCode();
    }

    @Test( dependsOnMethods = {"extractCode"} )
    void closeTab(){
        webdriver.close();
        common.switchToDefaultWindow();
    }

    @Test( dependsOnMethods = {"closeTab"} )
    void enterAndSubmitCode(){
        confirmEmailAddress.typeEmailVerificationCode(testData.getOtherDeviceCode());

        testData.setOtherDeviceSubmitCode("true");
        testData.setOtherDeviceFillCode(true);
        loginOtherDevice.submitCode();
    }

    @Test( dependsOnMethods = {"enterAndSubmitCode"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }
}
