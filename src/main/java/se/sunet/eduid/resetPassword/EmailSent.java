package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class EmailSent {
    private final Common common;
    private final TestData testData;
    String pageBody;

    public EmailSent(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailSent(){
        verifyPageTitle();
        verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord | eduID");
    }

    private void verifyLabels(){
        common.timeoutSeconds(1);
        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify the texts after request of new pw
        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifiera e-postadressen");
        common.verifyPageBodyContainsString(pageBody, "Om du har ett eduID-konto har koden skickats till ");
        common.verifyPageBodyContainsString(pageBody, testData.getEmail() +".");
        common.verifyPageBodyContainsString(pageBody, "E-postkoden är giltig i två timmar.");

        common.verifyPageBodyContainsString(pageBody, "Om du inte har fått koden kan du avbryta processen och börja om från början.");

        //Button text
        common.verifyStringById("response-code-abort-button", "AVBRYT");
        common.verifyStringById("response-code-ok-button", "OK");

        //Switch to english
        common.selectEnglish();

        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageTitle("Reset password | eduID");
        common.verifyPageBodyContainsString(pageBody, "Reset password: Verify email address");
        common.verifyPageBodyContainsString(pageBody, "If you have an eduID account, the code has been sent to ");
        common.verifyPageBodyContainsString(pageBody, testData.getEmail() +".");
        common.verifyPageBodyContainsString(pageBody, "The email code is valid for two hours.");

        common.verifyPageBodyContainsString(pageBody,
                "If you haven't receive the code, please cancel the process and restart from the beginning.");

        //Button text
        common.verifyStringById("response-code-abort-button", "CANCEL");
        common.verifyStringById("response-code-ok-button", "OK");


        //Switch to swedish
        common.selectSwedish();
    }
}
