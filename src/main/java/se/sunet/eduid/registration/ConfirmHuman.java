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
        common.verifyPageTitle("Registrera | eduID");
    }

    private void verifyLabels() {
        //Swedish
        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h1", "eduID måste " +
                "verifiera att du är en människa och inte en maskin.");

        //Switch language to English
        common.selectEnglish();

        common.verifyPageTitle("Register | eduID");

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h1", "Confirm that you are a human.");

        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickButton(){
        if(testData.isSendCaptcha()) {
            //Add cookie for back doors and click on proceed button
            if(testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                common.addMagicCookie();
                common.timeoutMilliSeconds(600);
                //common.logMagicCookie();
            }

            //Click on Send captcha button
            common.findWebElementById("send-captcha-button").click();
            common.timeoutSeconds(2);

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
                common.click(common.findWebElementById("login"));
            }
            else {
                //Verify text on page
                common.verifyStringOnPage("Kontot skapades");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/p",
                        "Slutför skapandet av ditt eduID genom att följa länken som skickats till:");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/h4", testData.getUsername());

                //Verify text in english
                common.selectEnglish();
                common.verifyStringOnPage("A link has been sent to your email address.");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/p",
                        "Complete registration by clicking the link sent to:");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/h4", testData.getUsername());

                //Continue with magic url to get to successful registered page
                common.timeoutSeconds(2);

                //Fetch the registration code
                common.navigateToUrl("https://signup.dev.eduid.se/services/signup/get-code/?email=" +testData.getUsername());

                String registrationCode = common.findWebElementByXpath("/html/body").getText();
                Common.log.info("Sign up code: " +registrationCode);

                //Simulate that clicking on link with code in email.
                common.navigateToUrl("https://signup.dev.eduid.se/register/code/" +registrationCode);

                //Wait for Go To eduID link on next page
                common.explicitWaitClickableElementId("finished-button");
            }
        }
        else {
            //Cancel confirm human will send user back to register email page
            common.click(common.findWebElementById("cancel-captcha-button"));

            //Wait for text header
            common.verifyStringOnPage("Registrera din e-postadress för att skapa ditt eduID.");
        }
    }
}