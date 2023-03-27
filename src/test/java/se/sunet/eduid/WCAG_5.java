package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class WCAG_5 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(5);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void loginPage() throws IOException {

    }

    @Test( dependsOnMethods = {"loginPage"} )
    void dashBoard() throws IOException {
        login.runLogin();

        common.timeoutSeconds(1);
        common.navigateToIdentity();

        //Collapse swedish menu first
        common.findWebElementById("accordion__heading-swedish").click();

        //Expand all menus
        identity.expandIdentityOptions();

        common.timeoutSeconds(1);
        accessibilityBase.checkAccessibilityViolations();
    }
}
