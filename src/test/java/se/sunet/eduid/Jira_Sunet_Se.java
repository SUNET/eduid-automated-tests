package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Jira_Sunet_Se extends BeforeAndAfter {
    @Test
    void navigateEduId(){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys("eduid");
        common.timeoutMilliSeconds(1500);

        common.findWebElementByXpathContainingText("eduID Sweden").click();

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("eduID login");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
    void loginToJira(){
        login.enterUsernamePassword();
        common.findWebElementById("login-form-button").click();

        // Verify that we see the DIGG OPS que
        common.explicitWaitPageTitle("Service Desk");
        common.verifyStringByXpath("//div[1]/div[2]/div/section/div[2]/div[1]/div[2]/h2/span", "DIGG OPS - 1st line");
    }
}
