package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_98 extends BeforeAndAfter {
    @Test
    void startPage(){ startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void confirmPhoneNumber() {
        common.navigateToSettings();
        confirmPhoneNumber.runConfirmPhoneNumber();
    }
}
