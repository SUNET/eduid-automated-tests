package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_44 extends BeforeAndAfter {
    @Test
    void swamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElement(By.id("login-form-button")));

        common.waitUntilPageTitleContains("Releasecheck för Swamid");
    }

    @Test( dependsOnMethods = {"login"} )
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(true); }
}
