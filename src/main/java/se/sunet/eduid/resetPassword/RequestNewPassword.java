package se.sunet.eduid.resetPassword;

import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class RequestNewPassword {
    private final Common common;
    private final TestData testData;
    private final Register register;

    public RequestNewPassword(Common common, TestData testData, Register register){
        this.common = common;
        this.testData = testData;
        this.register = register;
    }

    public void runRequestNewPassword(){
        verifyPageTitle();
        verifyLabels();
        enterEmail();
        pressResetPassword();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Återställ lösenord | eduID");
    }

    private void enterEmail(){
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());
    }

    public void pressResetPassword(){
        common.click(common.findWebElementById("reset-password-button"));

        // If whe have initiated authentication with bankID and aborted since it's not possible to do by automation,
        // then the captcha has already been done in the same req-pw session. Then user will not end up at captcha page
        // after clicking forgot password link but on the send reset-pw email page
        if(testData.getMfaMethod().equalsIgnoreCase("bankid")){
            common.explicitWaitClickableElementId("reset-password-button");
        }
        else {
            //Wait for next page, return to login
            common.explicitWaitClickableElementId("cancel-captcha-button");

            //Add nin cookie
            common.addNinCookie();
            register.enterCaptchaCode();
        }

        //If non-existing user is submitted
        if(testData.isIncorrectPassword()){
            common.verifyStatusMessage("Användaren hittades inte. Vänligen försök igen.");

            common.selectEnglish();

            common.verifyStatusMessage("User not found. Please try again.");
        }
        else {
            //wait for the Send-again button on next page
            common.explicitWaitClickableElementId("response-code-abort-button");
        }
    }

    private void verifyLabels(){
        //Heading
        common.verifyStringOnPage("Återställ lösenord: Ange e-postadressen");

        common.verifyStringOnPage("Om det finns en användare med den epostadressen, skickas ett mail " +
                "med instruktioner från no-reply@eduid.se.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress");

        //Verify placeholder
        common.verifyPlaceholder("namn@example.com", "email");

        //Buttons
        common.verifyStringById("reset-password-button", "SKICKA E-POST");
        common.verifyStringById("go-back-button", "TILLBAKA");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset password | eduID");

        //Heading
        common.verifyStringOnPage("Reset password: Enter the email address");

        common.verifyStringOnPage("Once entered, if the address is registered, a message with " +
                "instructions to reset the password will be sent from no-reply@eduid.se.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address");

        //Verify placeholder
        common.verifyPlaceholder("name@example.com", "email");

        //Buttons
        common.verifyStringById("reset-password-button", "SEND EMAIL");
        common.verifyStringById("go-back-button", "GO BACK");

        //Switch to swedish
        common.selectSwedish();
    }
}
