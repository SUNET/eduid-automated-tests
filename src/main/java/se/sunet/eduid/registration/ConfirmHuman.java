package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;

public class ConfirmHuman {
    private Common common;

    public ConfirmHuman(Common common){
        this.common = common;
    }

    public void runConfirmHuman(){
        verifyPageTitle();
        verifyLabels();
        clickButton();
    }

    //TODO all must be adapted when language switch is available....

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void verifyLabels() {
        //Swedish
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1/span", "eduID är enklare och säkrare inloggning.");

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h3/span", "eduID måste " +
                "verifiera att du är en människa och inte en maskin.");

        //Switch language to English
        common.findWebElementByLinkText("English").click();

        //Heading
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1/span", "eduID is easier and safer login.");

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h3/span", "Confirm that you are a human.");

        //Switch language to Swedish
        common.findWebElementByLinkText("Svenska").click();

    }

    private void clickButton(){
        if(common.getSendCaptcha()) {
            //Add cookie for back doors
            if(common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.addMagicCookie();

           //Click on Done button
            common.findWebElementById("send-captcha-button").click();

            //Verify that status info is displayed
            common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");

            if(!common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span","Vi kunde inte " +
                        "verifiera att du är människa. Var vänlig försök igen.");
                //common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "There was a problem " +
                        //"verifying that you are a human. Please try again");
            }
            else if(!common.getGenerateUsername()){
                //TODO bug with language
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "E-postadressen används redan");
                //common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "The email address you entered is already in use");

                //Press on login button to prepare Log in and delete the account
                common.findWebElementById("login").click();
            }
            else {
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span","E-postadressen har registrerats");
                //common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Email address successfully registered");

                //Continue with magic url to get to successful registered page
                common.timeoutSeconds(2);

                //Fetch the registration code
                common.navigateToUrl("https://signup.dev.eduid.se/services/signup/get-code/?email=" +common.getUsername());
                Common.log.info("URL: " +"https://signup.dev.eduid.se/services/signup/get-code/?email=" +common.getUsername());
                String registrationCode = common.findWebElementByXpath("/html/body").getText();
                Common.log.info("Sign up code: " +registrationCode);

                //Simulate that clicking on link with code in email.
                common.navigateToUrl("https://signup.dev.eduid.se/register/code/" +registrationCode);
            }
        }
        else {
            common.findWebElementById("cancel-captcha-button").click();
            common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1/span", "eduID är enklare och säkrare inloggning.");
        }
    }
}