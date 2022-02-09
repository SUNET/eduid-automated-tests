package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Ladok_Se extends BeforeAndAfter {
    @Test
    void selectLogIn(){
        //Wait for page to load
        common.explicitWaitPageTitle("Ladok - start");

        //Select log in
        common.click(common.findWebElementByXpath("//div/p/a"));
    }

    @Test( dependsOnMethods = {"selectLogIn"} )
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
    void loginToLadok(){
        login.verifyPageTitle();
        login.enterUsernamePassword();
        common.click(common.findWebElementById("login-form-button"));

        // Verify that we see page that user is not registered in ladok
        common.explicitWaitPageTitle("Ladok - Användare saknas");
        common.verifyStringOnPage( "Din användare finns inte i Ladok");
    }
}
