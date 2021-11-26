package se.sunet.eduid;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Mobile_1 extends BeforeAndAfter {
    JavascriptExecutor js;
    @Test
    void startPage() {
        common.takeScreenshot("startpage");
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        common.takeScreenshot("login");
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        common.takeScreenshot("dashboard");
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
        common.takeScreenshot("personalInfo");
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void identity() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/span").click();
        common.takeScreenshot("identity");
    }

    @Test( dependsOnMethods = {"identity"} )
    void advancedSettings() {
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span").click();
        common.takeScreenshot("advancedSettings");
    }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }
}
