package se.sunet.eduid.registration;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.CommonOperations;

public class ConfirmHuman extends CommonOperations {
    @Test
    private void verifyPageTitle() {
        verifyPageTitle("eduID");
    }

    @Test(dependsOnMethods = {"verifyPageTitle"})
    private void verifyLabels() {

        //Heading
        verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");

        //Label1
        //explicitWaitVisibilityElement("//*[@id=\"register-container\"]/h3/span");
        verifyStringByXpath("//*[@id=\"register-container\"]/h3/span", "Confirm that you are a human.");


    }

    @Test(dependsOnMethods = {"verifyLabels"})
    @Parameters( {"sendCaptcha"} )
    private void clickButton(@Optional("true") String sendCaptcha){
        if(sendCaptcha.equalsIgnoreCase("true")) {
            click(findWebElementById("send-captcha-button"));
            timeoutMilliSeconds(400);

            //Verify that status info is displayed
            verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "There was a problem " +
                    "verifying that you are a human. Please try again");

            timeoutSeconds(3);//devtestuser1@dev.eduid.sunet.com
            navigateToUrl("https://signup.dev.eduid.se/register/code/mknhKYFl94fJaWaiVk2oG9Tl");
        }
        else {
            click(findWebElementById("cancel-captcha-button"));
            verifyStringByXpath("//*[@id=\"register-container\"]/label", "EMAIL ADDRESS");
        }
    }
}
