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
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        common.timeoutSeconds(1);

        //Verify the texts after request of new pw
        common.verifyStringOnPage("Ett e-postmeddelande med instruktioner har skickats till");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("Länken i e-postmeddelandet är giltig i två timmar.");

        common.verifyStringOnPage("Om du inte fick e-postmeddelandet, kontrollera din skräppost eller " +
                "skicka e-postmeddelandet igen.");

        //Button text
        common.verifyStringById("go-back-button", "TILLBAKA");
        common.verifyStringById("send-email-button", "SKICKA E-POST IGEN");

        //Switch to english
        common.selectEnglish();
        common.verifyStringOnPage("An e-mail with instructions has been sent to");
        common.verifyStringOnPage(testData.getEmail() +".");
        common.verifyStringOnPage("The link in the e-mail is valid for two hours.");

        common.verifyStringOnPage("If you didn’t receive the email, check your junk email, or resend the email.");

        //Button text
        common.verifyStringById("go-back-button", "GO BACK");
        common.verifyStringById("send-email-button", "RESEND E-MAIL");


        //Switch to swedish
        common.selectSwedish();
    }
}
