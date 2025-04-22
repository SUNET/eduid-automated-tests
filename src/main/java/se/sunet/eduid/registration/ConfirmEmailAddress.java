package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;
import static se.sunet.eduid.utils.Common.log;

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
                common.verifyStatusMessage("E-postadressen är redan registrerad. Om du har glömt ditt lösenord kan " +
                        "du gå till inloggningssidan och återställa det.");

                //Verify status message in english
                common.selectEnglish();
                common.verifyStatusMessage("The email address is already registered. If you've forgotten your password, " +
                        "go to the login page and reset it.");

                //Press on login button to prepare Log in and delete the account
                common.click(common.findWebElementById("login"));
            }
            else {
                //Verify labels
                verityTextAndLabels();

                //If not the code fetched above should be used...
                if("987654".equals(testData.getEmailVerificationCode())){
                    Common.log.info("Will use an incorrect emailVerificationCode code: " +testData.getEmailVerificationCode());
                }
                else{
                    //Fetch the email verification code, first url encode the email address
                    String userName = testData.getUsername().replace("@", "%40");
                    testData.setEmailVerificationCode(common.getCodeInNewTab(
                            testData.getEmailVerificationCodeUrl() + userName.toLowerCase(), 6));
                }

                //Enter email verification code
                typeEmailVerificationCode(testData.getEmailVerificationCode());

                //Press OK button
                common.findWebElementById("response-code-ok-button").click();

                //Wait for go to eduid link on next page or error message if not correct code is supplied or too many attempts
                if ("987654".equals(testData.getEmailVerificationCode()) && emailVerificationAttempts == 2) {
                    //verify status message in swedish
                    common.verifyStatusMessage("För många ogiltiga verifieringsförsök. Var god försök igen senare.");

                    //verify status message in english
                    common.selectEnglish();
                    common.verifyStatusMessage("Too many invalid verification attempts. Please try again later.");

                    common.selectSwedish();
                }
                else if ("987654".equals(testData.getEmailVerificationCode())) {
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

                //Wait the copy password on confirm password page
                else {
                    common.explicitWaitClickableElement("//*[@id=\"eduid-splash-and-children\"]/div/button");
                }
            }
        }
        else {
            //Cancel confirm human will send user back to register email page
            common.click(common.findWebElementById("response-code-abort-button"));

            //Wait for text header
            common.verifyStringOnPage("Registrera: Ange dina uppgifter");
        }
    }

    private void verityTextAndLabels(){
        log.info("Verify text and labels for verify of email address - swedish");

        //Wait for cancel button to be present
        common.explicitWaitClickableElementId("response-code-abort-button");

        String pagebody = common.getPageBody();

        common.verifyPageBodyContainsString(pagebody, "Registrera: Verifiera e-postadress");
        common.verifyPageBodyContainsString(pagebody, "Ange den sexsiffriga koden som skickats från no-reply@eduid.se till");
        common.verifyPageBodyContainsString(pagebody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pagebody, "för att verifiera din e-postadress. Du kan också kopiera och klistra " +
                        "in koden från e-posten i inmatningsfältet.");

        common.verifyPageBodyContainsString(pagebody, "Koden går ut om");
        common.verifyStringById("response-code-abort-button", "AVBRYT");

        //Select English
        common.selectEnglish();
        log.info("Verify text and labels for verify of email address - swedish");

        pagebody = common.getPageBody();

        common.verifyPageBodyContainsString(pagebody, "Register: Verify email address");
        common.verifyPageBodyContainsString(pagebody, "Enter the six digit code sent from no-reply@eduid.se to");
        common.verifyPageBodyContainsString(pagebody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pagebody, "to verify your email address. You can also copy and paste the code " +
                "from the email into the input field.");

        common.verifyPageBodyContainsString(pagebody, "The code expires in");
        common.verifyStringById("response-code-abort-button", "CANCEL");

        //Select Swedish
        common.selectSwedish();
    }

    public void typeEmailVerificationCode(String emailVerificationCode){
        common.explicitWaitVisibilityElement("//*[@id=\"eduid-splash-and-children\"]/form/div/input[1]");
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[1]").sendKeys(emailVerificationCode.substring(0, 1));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[2]").sendKeys(emailVerificationCode.substring(1, 2));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[3]").sendKeys(emailVerificationCode.substring(2, 3));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[4]").sendKeys(emailVerificationCode.substring(3, 4));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[5]").sendKeys(emailVerificationCode.substring(4, 5));
        common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/form/div/input[6]").sendKeys(emailVerificationCode.substring(5, 6));
    }
}