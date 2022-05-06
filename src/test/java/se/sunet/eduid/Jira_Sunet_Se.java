package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Jira_Sunet_Se extends BeforeAndAfter {
    @Test
    void navigateEduId(){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys("eduid");
        common.timeoutMilliSeconds(1500);

        common.click(common.findWebElementByXpathContainingText("eduID Sweden"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("eduID login");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
    void loginToJira(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));

        // Verify that we see the DIGG OPS que
        common.explicitWaitPageTitle("Service Desk");
        common.verifyStringOnPage( "DIGG OPS - 1st line");
    }
}
