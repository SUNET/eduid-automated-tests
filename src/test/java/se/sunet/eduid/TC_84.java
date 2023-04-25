package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_84 extends BeforeAndAfter {
    @Test
    void startPage() {
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test(dependsOnMethods = {"startPage"})
    void register() {
        common.addMagicCookie();
        register.enterEmailAndPressRegister();
    }


    @Test(dependsOnMethods = {"register"})
    void confirmEmailAddressByCaptcha() {
        //Press toggle switch for use of old captcha
        common.findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div").click();

        //Click on Send captcha button
        common.timeoutSeconds(2);
        common.findWebElementById("send-captcha-button").click();

        //No magic code is set, just wait for the Accept Terms button on next page
        common.explicitWaitClickableElementId("accept-button");
    }
}
