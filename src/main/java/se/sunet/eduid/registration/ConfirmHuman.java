package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;

public class ConfirmHuman {
    private Common common;

    public ConfirmHuman(Common common){
        this.common = common;
    }

    public void runConfirmHuman(boolean captcha, boolean incorrectMagicCode, boolean generateUsername ){
        verifyPageTitle();
        verifyLabels();
        clickButton(captcha, incorrectMagicCode, generateUsername);
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void verifyLabels() {

        //Heading
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");

        //Label1
        common.verifyStringByXpath("//*[@id=\"register-container\"]/h3/span", "Confirm that you are a human.");


    }

    private void clickButton(boolean sendCaptcha, boolean incorrectMagicCode, boolean generateUsername){
        if(sendCaptcha) {
           //Click on Done button
            common.timeoutMilliSeconds(1000);
            common.click(common.findWebElementById("send-captcha-button"));

            //Verify that status info is displayed
            common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
            if(incorrectMagicCode){
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "There was a problem " +
                        "verifying that you are a human. Please try again");
            }
            else if(!generateUsername){
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "The email address you entered is already in use");
            }
            else {
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Email address successfully registered");

                //Continue with magic url to get to successful registered page
                common.timeoutSeconds(2);
                common.navigateToUrl("https://signup.dev.eduid.se/register/code/mknhKYFl94fJaWaiVk2oG9Tl");
            }
        }
        else {
            common.click(common.findWebElementById("cancel-captcha-button"));
            common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        }
    }
}