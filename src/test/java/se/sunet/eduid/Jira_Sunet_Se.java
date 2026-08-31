package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Jira_Sunet_Se extends BeforeAndAfter {
    @Test
    public void navigateToEduId(){
        common.navigateToEduId();
    }

    @Test( dependsOnMethods = {"navigateToEduId"} )
    void loginToJira(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElement(By.id("login-form-button")));

        // Verify that we see the DIGG OPS que
        common.waitUntilPageTitleContains("Service Desk");
        common.verifyStringOnPage( "DIGG OPS - 1st line");
    }
}
