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
        common.verifyStringOnPage("Om du har ett eduID-konto, har ett meddelande med instruktioner skickats till ");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("Länken i e-postmeddelandet är giltig i två timmar.");

        common.verifyStringOnPage("Om du inte fick e-postmeddelandet, kontrollera din skräppost innan " +
                "du skickar e-postmeddelandet igen efter fem minuter, enligt timern intill Skicka E-post igen knappen.");

        //Button text
        common.verifyStringById("go-back-button", "TILLBAKA");
        common.verifyStringById("send-email-button", "SKICKA E-POST IGEN");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");
        common.verifyStringOnPage("If you have an eduID account, an email with instructions has been sent to");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("The link in the email is valid for two hours.");

        common.verifyStringOnPage("If you didn’t receive the email, check your junk email before " +
                "resending it after five minutes, according to the timer next to the Resend button.");

        //Button text
        common.verifyStringById("go-back-button", "GO BACK");
        common.verifyStringById("send-email-button", "RESEND EMAIL");


        //Switch to swedish
        common.selectSwedish();
    }
}
