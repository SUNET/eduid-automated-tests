package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_83 extends BeforeAndAfter {
    @Test
    void startPage() {
        testData.setRegisterAccount(true);
        startPage.runStartPage();
    }

    @Test(dependsOnMethods = {"startPage"})
    void register() {
        register.enterEmailAndPressRegister();
    }


    @Test(dependsOnMethods = {"register"})
    void confirmEmailAddressByCaptcha() {
        //Press toggle switch for use of old captcha
        common.findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div").click();

        //Click on Send captcha button
        common.timeoutSeconds(2);
        common.findWebElementById("send-captcha-button").click();

        //No magic code is set
        common.verifyStatusMessage("Det uppstod ett problem med verifieringen av att du är en människa. " +
                "Var god försök igen.");
    }
}
