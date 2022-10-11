package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class WCAG_2 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(2);
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void loginPage() throws IOException {
        common.timeoutSeconds(1);
        accessibilityBase.checkAccessibilityViolations();
    }
}
