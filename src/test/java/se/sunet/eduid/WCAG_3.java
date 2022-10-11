package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class WCAG_3 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(3);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void loginPage() throws IOException {

    }

    @Test( dependsOnMethods = {"loginPage"} )
    void dashBoard() throws IOException {
        login.runLogin();

        common.timeoutSeconds(1);
        common.timeoutSeconds(1);
        accessibilityBase.checkAccessibilityViolations();
    }
}
