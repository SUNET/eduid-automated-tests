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
        common.verifyStatusMessage("Om det finns en användare med den epostadressen, skickas ett mail med instruktioner.");
        //common.verifyStringOnPage(testData.getEmail() +".");
        //common.verifyStringOnPage("Länken i e-postmeddelandet är giltig i två timmar.");

        //common.verifyStringOnPage("Om du inte fick e-postmeddelandet, kontrollera din skräppost eller " +
        //        "skicka e-postmeddelandet igen.");

        //Button text
        common.verifyStringById("go-back-button", "TILLBAKA");
        common.verifyStringById("reset-password-button", "SKICKA E-POST");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");
        common.verifyStatusMessage("If the address is registered, a message with instructions to reset the password has been sent.");
        //common.verifyStringOnPage(testData.getEmail() +".");
        //common.verifyStringOnPage("The link in the e-mail is valid for two hours.");

        //common.verifyStringOnPage("If you didn’t receive the email, check your junk email, or resend the email.");

        //Button text
        common.verifyStringById("go-back-button", "GO BACK");
        common.verifyStringById("reset-password-button", "SEND EMAIL");


        //Switch to swedish
        common.selectSwedish();
    }
}
