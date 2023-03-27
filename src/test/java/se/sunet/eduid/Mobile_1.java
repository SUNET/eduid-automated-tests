package se.sunet.eduid;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Mobile_1 extends BeforeAndAfter {
    JavascriptExecutor js;
    @Test
    void startPage() {
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("startpage");
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("login");
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("dashboard");
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() {
        common.navigateToSettings();
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("personalInfo");
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void identity() {
        common.navigateToIdentity();
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("identity");
    }

    @Test( dependsOnMethods = {"identity"} )
    void advancedSettings() {
        common.navigateToAdvancedSettings();
        common.timeoutSeconds(2);
        common.takeFullPageScreenshot("advancedSettings");
    }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }
}
