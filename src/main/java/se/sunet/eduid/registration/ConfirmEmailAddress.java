package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmEmailAddress {
    private final Common common;
    private final TestData testData;
    int emailVerificationAttempts = 0;

    public ConfirmEmailAddress(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmEmailAddress(){
        verifyPageTitle();
        submitEmailConfirmationCode();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Registrera | eduID");
    }

    private void submitEmailConfirmationCode(){
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
                testData.setEmailVerificationCode(common.getCodeInNewTab(
                        "https://signup.dev.eduid.se/services/signup/get-code/?email=" + userName.toLowerCase()));

                //If not the code fetched above should be used...
                if (!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                    testData.setEmailVerificationCode("987654");
                    Common.log.info("Will use an incorrect emailVerificationCode code: " +testData.getEmailVerificationCode());
                }

                //Enter email verification code
                typeEmailVerificationCode();

                //Wait for go to eduid link on next page or error message if not correct code is supplied or too many attempts
                if (!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl") && emailVerificationAttempts == 2) {
                    //verify status message in swedish
                    common.verifyStatusMessage("För många ogiltiga verifieringsförsök. Var god försök igen.");

                    //verify status message in english
                    common.selectEnglish();
                    common.verifyStatusMessage("Too many invalid verification attempts. Please try again.");

                    common.selectSwedish();
                }
                else if (!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
                    //verify status message in swedish
                    common.verifyStatusMessage("E-post verifieringen misslyckades. Var god försök igen.");

                    //verify status message in english
                    common.selectEnglish();
                    common.verifyStatusMessage("The email verification failed. Please try again.");

                    common.selectSwedish();

                    //Close status message
                    common.closeStatusMessage();

                    emailVerificationAttempts ++;
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

    private void verityTextAndLabels(){
        //Wait for cancel button to be present
        common.explicitWaitClickableElementId("response-code-abort-button");

        common.verifyStringOnPage("Verifiering av e-postadress");
        common.verifyStringOnPage("Ange den sexsiffriga koden som skickats till");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
        common.verifyStringOnPage("för att verifiera din e-postadress. Du kan också kopiera och klistra " +
                        "in koden från e-posten i inmatningsfältet.");

        common.verifyStringOnPage("Koden går ut om");
        common.verifyStringById("response-code-abort-button", "AVBRYT");

        //Select English
        common.selectEnglish();

        common.verifyStringOnPage("Verification of email address");
        common.verifyStringOnPage("Enter the six digit code sent to");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
        common.verifyStringOnPage("to verify your email address. You can also copy and paste the code " +
                "from the email into the input field.");

        common.verifyStringOnPage("The code expires in");
        common.verifyStringById("response-code-abort-button", "CANCEL");

        //Select Swedish
        common.selectSwedish();
    }

    private void typeEmailVerificationCode(){
        String emailVerificationCode = testData.getEmailVerificationCode();
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[1]").sendKeys(emailVerificationCode.substring(0, 1));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[2]").sendKeys(emailVerificationCode.substring(1, 2));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[3]").sendKeys(emailVerificationCode.substring(2, 3));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[4]").sendKeys(emailVerificationCode.substring(3, 4));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[5]").sendKeys(emailVerificationCode.substring(4, 5));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[6]").sendKeys(emailVerificationCode.substring(5, 6));
    }
}