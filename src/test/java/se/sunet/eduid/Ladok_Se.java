package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Ladok_Se extends BeforeAndAfter {
    @Test
    void selectLogIn(){
        //Wait for page to load
        common.waitUntilPageTitleContains("Ladok - start");

        //Select log in
        common.click(common.findWebElementById("eduIdBtn"));
        common.timeoutMilliSeconds(1500);
    }

    @Test( dependsOnMethods = {"selectLogIn"} )
    void loginToLadok(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElementById("login-form-button"));

        // Verify that we see page that user is not registered in ladok
        common.waitUntilPageTitleContains("Ladok - Användare saknas");
        common.verifyStringOnPage( "Din användare finns inte i Ladok");
    }
}
