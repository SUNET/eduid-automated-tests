package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.WebDriverManager;

public class TC_72 extends BeforeAndAfter {
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

        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"login2"} )
    void closeTab(){
        WebDriverManager.getWebDriver().close();
        common.switchToDefaultWindow();
    }

    @Test( dependsOnMethods = {"closeTab"} )
    void pressCancel(){
        common.timeoutMilliSeconds(500);

        //No code submitted in this case, just pressing cancel button
        testData.setOtherDeviceSubmitCode("false");
        loginOtherDevice.submitCode();
    }
//TODO here we should end up at log in page after pressing cancel above
//    @Test( dependsOnMethods = {"pressCancel"} )
    void dashboard() { dashBoard.runDashBoard(); }

//    @Test( dependsOnMethods = {"dashboard"} )
    void logout2() { logout.runLogout(); }
}
