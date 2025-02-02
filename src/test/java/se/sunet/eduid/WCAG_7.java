package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.io.IOException;

public class WCAG_7 extends BeforeAndAfter
{
    @Test
    public void startPage() throws IOException {
        testData.setCounter(7);
    }

    @Test( dependsOnMethods = {"startPage"} )
    void helpPage() throws IOException {
        //Press help
        //common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a").click();
        help.clickHelp();
        common.timeoutSeconds(2);
        help.expandAllOptions();
        common.timeoutSeconds(2);
        accessibilityBase.checkAccessibilityViolations();
    }
}
