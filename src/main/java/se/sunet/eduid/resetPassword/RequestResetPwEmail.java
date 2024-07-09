package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class RequestResetPwEmail {
    private final Common common;
    private final TestData testData;
    private String sendEmailButton = "//*[@id=\"content\"]/div/button[2]";

    public RequestResetPwEmail(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runRequestResetPwEmail(){
        verifyPageTitle();
        verifyLabels();
        clickSendEmail();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ Lösenord | eduID");
    }

    private void clickSendEmail(){
        common.click(common.findWebElementByXpath(sendEmailButton));

        //Wait for abort button on next page: Reset Password: Verify email address
        common.explicitWaitClickableElementId("response-code-abort-button");
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
        common.verifyPageTitle("Reset Password | eduID");

        //Heading
        common.verifyStringOnPage("Reset Password: Start account recovery process");

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
