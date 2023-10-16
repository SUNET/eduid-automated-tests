package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class RequestResetPwEmail {
    private final Common common;
    private final TestData testData;

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
        common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div/button[2]"));

        //wait for the Send-again button on next page
        common.timeoutSeconds(4);
        common.verifyStringOnPage("Om du har ett eduID-konto, har ett meddelande med instruktioner skickats till");
    }

    private void verifyLabels(){
        //Heading
        common.verifyStringOnPage("Återställ lösenord");

        common.verifyStringOnPage("För att komma igång med kontoåterställningen, klicka på knappen " +
                "nedan för att skicka ett e-postmeddelande till");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());

        //Buttons
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/button[2]", "SKICKA E-POST");
        common.verifyStringById("go-back-button", "TILLBAKA");

        //Switch to english
        common.selectEnglish();

        //Page title
        common.verifyPageTitle("Reset Password | eduID");

        //Heading
        common.verifyStringOnPage("Reset password");

        common.verifyStringOnPage("To start the account recovery process, press the button below to send an email to");
        common.verifyStringOnPage(testData.getUsername().toLowerCase());

        //Buttons
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/button[2]", "SEND EMAIL");
        common.verifyStringById("go-back-button", "GO BACK");

        //Switch to swedish
        common.selectSwedish();
    }
}
