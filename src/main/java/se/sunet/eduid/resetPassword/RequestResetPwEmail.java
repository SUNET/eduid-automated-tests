package se.sunet.eduid.resetPassword;

import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class RequestResetPwEmail {
    private final Common common;
    private final TestData testData;
    private final Register register;
    private String sendEmailButton = "//*[@id=\"content\"]/div/button[2]";

    public RequestResetPwEmail(Common common, TestData testData, Register register) {
        this.common = common;
        this.testData = testData;
        this.register = register;
    }

    public void runRequestResetPwEmail(){
        verifyPageTitle();
        verifyLabels();
        clickSendEmail();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord | eduID");
    }

    private void clickSendEmail(){
        common.explicitWaitClickableElement(sendEmailButton);
        common.click(common.findWebElementByXpath(sendEmailButton));
        Common.log.info("Clicked send email button");

        //Wait for abort button on next page: Reset Password: Verify email address
                //TODO tempfix to be removed when bug fixed
                //common.timeoutSeconds(18);
                //Common.log.info("special temp fix, closing status message and sending email once again");
                //common.closeStatusMessage();
/*        if(!common.getPageBody().contains("Återställ lösenord: Verifiera e-postadressen")) {
            common.findWebElementById("reset-password-button").click();
            Common.log.info("Reset email button again...");
        }*/

        // If whe have initiated authentication with bankID and aborted since it's not possible to do by automation,
        // then the captcha has already been done in the same req-pw session. Then user will not end up at captcha page
        // after clicking forgot password link but on the send reset-pw email page
        if(testData.getMfaMethod().equalsIgnoreCase("bankid")){
            common.explicitWaitClickableElementId("response-code-abort-button");
        }
        else {
            //Wait for next page, return to login
            common.explicitWaitClickableElementId("cancel-captcha-button");

            //Add nin cookie
            common.addNinCookie();
            register.enterCaptchaCode();
        }

/*        //Wait for next page, return to login
        common.explicitWaitClickableElementId("cancel-captcha-button");

        //Add nin cookie
        common.addNinCookie();
        register.enterCaptchaCode();*/


        //wait for the Send-again button on next page
        common.explicitWaitClickableElementId("response-code-abort-button");

        //common.explicitWaitClickableElementId("response-code-abort-button");
    }

    private void verifyLabels(){
        //Heading
        common.verifyStringOnPage("Återställ lösenord: Starta processen för återställning av konto");

        common.verifyStringOnPage("Klicka på knappen nedan för att skicka ett e-postmeddelande till");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
        common.verifyStringOnPage("Om du väljer att avbryta, klicka på knappen Gå tillbaka för att återgå till inloggningssidan.");

        //Buttons
        common.verifyStringByXpath(sendEmailButton, "SKICKA E-POST");
        common.verifyStringById("go-back-button", "TILLBAKA");

        //Switch to english
        common.selectEnglish();

        //Page title
        common.verifyPageTitle("Reset password | eduID");

        //Heading
        common.verifyStringOnPage("Reset password: Start account recovery process");

        common.verifyStringOnPage("Click the button below to send an e-mail to");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
        common.verifyStringOnPage("If you decide to cancel, simply click the Go Back button to return to the login page.");

        //Buttons
        common.verifyStringByXpath(sendEmailButton, "SEND EMAIL");
        common.verifyStringById("go-back-button", "GO BACK");

        //Switch to swedish
        common.selectSwedish();
    }
}
