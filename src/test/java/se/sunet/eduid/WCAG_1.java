package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.wcag.AccessibilityBase;
import se.sunet.eduid.wcag.FrameworkBase;

import java.io.IOException;

public class WCAG_1 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(1);
        common.timeoutSeconds(1);
        accessibilityBase.checkAccessibilityViolations();
    }
}