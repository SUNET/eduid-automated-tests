package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmHuman {
    private final Common common;
    private final TestData testData;

    public ConfirmHuman(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmHuman(){
        verifyPageTitle();
        verifyLabels();
        clickButton();
    }

    //TODO all must be adapted when language switch is available....

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID signup");
    }

    private void verifyLabels() {
        //Swedish
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID är enklare och säkrare inloggning.");

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h3", "eduID måste " +
                "verifiera att du är en människa och inte en maskin.");

        //Switch language to English
        common.findWebElementByLinkText("English").click();

        //Heading
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID is easier and safer login.");

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h3", "Confirm that you are a human.");

        //Switch language to Swedish
        common.findWebElementByLinkText("Svenska").click();

    }

    private void clickButton(){
        if(testData.isSendCaptcha()) {
            //Add cookie for back doors and click on proceed button
            if(testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                common.addMagicCookie();
                common.timeoutMilliSeconds(400);
                //common.logMagicCookie();
            }

            //Click on Done button
            common.findWebElementById("send-captcha-button").click();

            //Common.log.info("Magic code: " +common.getMagicCode());

            //Verify that status info is displayed
            common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div");

            //Evaluate response according to below criterias
            if(!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
                common.verifyStatusMessage("Vi kunde inte verifiera att du är människa. Var vänlig försök igen.");
            }
            else if(!testData.isGenerateUsername()){
                //TODO bug with language
                common.verifyStatusMessage("E-postadressen används redan");

                //Press on login button to prepare Log in and delete the account
                common.findWebElementById("login").click();
            }
            else {
                common.verifyStatusMessage("E-postadressen har registrerats");
                //TODO switch to english...
                //Verify text on page
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/h3", "Kontot skapades");
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/div/p",
                        "Slutför skapandet av ditt eduID genom att klicka länken skickad till:");
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/div/h3", testData.getUsername());

                //Verify text in english
                common.findWebElementByLinkText("English").click();
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/h3", "A link has been sent to your email address.");
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/div/p",
                        "Complete registration by clicking the link sent to:");
                common.verifyStringByXpath("//div[1]/section[2]/div[2]/div/h3", testData.getUsername());

                //Continue with magic url to get to successful registered page
                common.timeoutSeconds(2);

                //Fetch the registration code
                common.navigateToUrl("https://signup.dev.eduid.se/services/signup/get-code/?email=" +testData.getUsername());
                //Common.log.info("URL: " +"https://signup.dev.eduid.se/services/signup/get-code/?email=" +common.getUsername());
                String registrationCode = common.findWebElementByXpath("/html/body").getText();
                Common.log.info("Sign up code: " +registrationCode);

                //Simulate that clicking on link with code in email.
                common.navigateToUrl("https://signup.dev.eduid.se/register/code/" +registrationCode);
            }
        }
        else {
            common.findWebElementById("cancel-captcha-button").click();
            common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID är enklare och säkrare inloggning.");
        }
    }
}