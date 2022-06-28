package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.AccessibilityBase;
import se.sunet.eduid.utils.FrameworkBase;

import java.io.IOException;

public class WCAG_1 extends FrameworkBase
{
    AccessibilityBase a11y;

    @Test
    public void eduidAccessibility() throws IOException {
        webDriver().get("https://dev.eduid.se");

        webDriver().findElement(By.xpath("/html/body/section[2]/div/p[3]/a")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a11y.checkAccessibilityViolations();
    }
}
