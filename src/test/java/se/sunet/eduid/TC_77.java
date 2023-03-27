package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_77 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void runLoginOtherDevice1st() {
        //Set remember me function
        testData.setRememberMe(true);

        testData.setOtherDeviceSubmitCode("true");

        loginOtherDevice.runLoginOtherDevice();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice1st"} )
    void runLoginOtherDevice2nd() {
        common.timeoutSeconds(1);

        loginOtherDevice.submitCode();
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice2nd"} )
    void runLoginOtherDevice3rd() {
        common.timeoutSeconds(1);

        loginOtherDevice.submitCode();

        common.explicitWaitClickableElementId("response-code-cancel-button");
    }

    @Test( dependsOnMethods = {"runLoginOtherDevice3rd"} )
    void verifyStatusMessage(){
        common.verifyStringOnPage("The request is not valid anymore");

        common.selectSwedish();

        common.verifyStringOnPage("Inloggningen är inte giltig längre");

        common.findWebElementById("response-code-cancel-button").click();

        //Wait for the Log in page
        common.explicitWaitClickableElementId("login-form-button");
    }
}
