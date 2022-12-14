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
//        verifyLabels();
//        clickButton();

//        verityTextAndLabels();
        getAndSubmitSignupCode();
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

    private void getAndSubmitSignupCode(){
        //Old naming of variable, should be renamed to continue sign up with otp code
        if(testData.isVerifyEmail()) {
            //Evaluate response according to below criterias
            if(!testData.isGenerateUsername()) {
                //Verify status message in swedish
                common.verifyStatusMessage("E-postadressen används redan.");

                //Verify status message in english
                common.selectEnglish();
                common.verifyStatusMessage("The email address is already in use.");

                //Press on login button to prepare Log in and delete the account
                common.click(common.findWebElementById("login"));
            }
            else {
                //Verify labels
                verityTextAndLabels();

                //Fetch the email verification code, first url encode the email address
                String userName = testData.getUsername().replace("@", "%40");
                String emailVerificationCode = common.getCodeInNewTab("https://signup.dev.eduid.se/services/signup/get-code/?email=" + userName.toLowerCase());

                //If not the code fetched above should be used...
                if (!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                    emailVerificationCode = "987654";
                    Common.log.info("Will use an incorrect emailVerificationCode code: " +emailVerificationCode);
                }

                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[1]").sendKeys(emailVerificationCode.substring(0, 1));
                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[2]").sendKeys(emailVerificationCode.substring(1, 2));
                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[3]").sendKeys(emailVerificationCode.substring(2, 3));
                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[4]").sendKeys(emailVerificationCode.substring(3, 4));
                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[5]").sendKeys(emailVerificationCode.substring(4, 5));
                common.findWebElementByXpath("//*[@id=\"content\"]/div[3]/form/div/input[6]").sendKeys(emailVerificationCode.substring(5, 6));

                //Wait for go to eduid link on next page or error message if not correct code is sypplied
                if (!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                    //verify status message in swedish
                    common.verifyStatusMessage("E-post verifieringen misslyckades. Var god försök igen.");

                    //verify status message in english
                    common.selectEnglish();
                    common.verifyStatusMessage("The email verification failed. Please try again.");
                }
                //Wait for go to eduid link on next page
                else {
                    common.explicitWaitClickableElementId("finished-button");
                }
            }
        }
        else {
            //Cancel confirm human will send user back to register email page
            common.click(common.findWebElementById("response-code-abort-button"));

            //Wait for text header
            common.verifyStringOnPage("Registrera din e-postadress för att skapa ditt eduID.");
        }
    }

    //TODO this need clean up, some code need to be re-used for some testcases
    private void clickButton(){
        if(testData.isVerifyEmail()) {
            //Add cookie for back doors and click on proceed button
            if(testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                common.addMagicCookie();
                common.timeoutMilliSeconds(600);
            }

            //Click on Send captcha button
            common.findWebElementById("send-captcha-button").click();
            common.timeoutSeconds(2);

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

                //Fetch the registration code, firts url encode the email address
                String userName = testData.getUsername().replace("@", "%40");
                common.navigateToUrl("https://signup.dev.eduid.se/services/signup/get-code/?email=" +userName.toLowerCase());
                //Common.log.info("Fetching sign up code: https://signup.dev.eduid.se/services/signup/get-code/?email=" +userName.toLowerCase());

                String registrationCode = common.findWebElementByXpath("/html/body").getText();
                Common.log.info("Sign up code: " +registrationCode);

                //Simulate that clicking on link with code in email.
                //common.navigateToUrl("https://signup.dev.eduid.se/register/code/" +registrationCode);
                common.navigateToUrl("https://signup.dev.eduid.se/register/" +registrationCode);

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

    private void verityTextAndLabels(){
        //Wait for cancel button to be present
        common.explicitWaitClickableElementId("response-code-abort-button");

        common.verifyStringOnPage("Verifiering av e-postadress");
        common.verifyStringOnPage("Ange den sexsiffriga koden som skickades till");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
        common.verifyStringOnPage("för att verifiera din e-postadress");

        common.verifyStringOnPage("Koden går ut om");
        common.verifyStringById("response-code-abort-button", "AVBRYT");

        //TODO switch to english here and verify text and labels when its possible.

    }

    private void negativeScenarios(){
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
    }
}