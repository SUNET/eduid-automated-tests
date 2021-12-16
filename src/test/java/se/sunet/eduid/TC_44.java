package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_44 extends BeforeAndAfter {
    @Test
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login(){
        login.verifyPageTitle();
        login.enterUsernamePassword();
        common.findWebElementById("login-form-button").click();

        common.explicitWaitPageTitle("Release check for SWAMID");
    }

    @Test( dependsOnMethods = {"login"} )
    void swamidData(){ swamidData.runSwamidData(true); }
}
