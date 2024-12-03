package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class WCAG_4 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(4);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void loginPage() throws IOException {

    }

    @Test( dependsOnMethods = {"loginPage"} )
    void dashBoard() throws IOException {
        login.runLogin();

        common.timeoutSeconds(1);
        common.navigateToAccount();
        common.timeoutSeconds(1);
        accessibilityBase.checkAccessibilityViolations();
    }
}
