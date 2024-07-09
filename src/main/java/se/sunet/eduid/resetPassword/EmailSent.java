package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class EmailSent {
    private final Common common;
    private final TestData testData;

    public EmailSent(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailSent(){
        verifyPageTitle();
        verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ Lösenord | eduID");
    }

    private void verifyLabels(){
        common.timeoutSeconds(1);

        //Verify the texts after request of new pw
        common.verifyStringOnPage("Återställ lösenord: Verifiera e-postadressen");
        common.verifyStringOnPage("Om du har ett eduID-konto har koden skickats till ");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("E-postkoden är giltig i två timmar.");

        common.verifyStringOnPage("Om du inte har fått koden kan du avbryta processen och börja om från början.");

        //Button text
        common.verifyStringById("response-code-abort-button", "AVBRYT");
        common.verifyStringById("response-code-ok-button", "OK");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");
        common.verifyStringOnPage("Reset Password: Verify email address");
        common.verifyStringOnPage("If you have an eduID account, the code has been sent to ");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("The email code is valid for two hours.");

        common.verifyStringOnPage(
                "If you haven't receive the code, please cancel the process and restart from the beginning.");

        //Button text
        common.verifyStringById("response-code-abort-button", "CANCEL");
        common.verifyStringById("response-code-ok-button", "OK");


        //Switch to swedish
        common.selectSwedish();
    }
}
