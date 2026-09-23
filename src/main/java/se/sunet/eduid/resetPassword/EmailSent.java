package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.EmailSentLocators.*;

/**
 * Page object för sidan "Återställ lösenord: Verifiera e-postadressen".
 * Verifierar att bekräftelsesidan visas korrekt i båda språken
 * efter att återställningsmail har skickats.
 */
public class EmailSent {

    private final Common   common;
    private final TestData testData;

    public EmailSent(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runEmailSent() {
        verifyPageTitle();
        verifyLabels();
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Återställ lösenord | eduID");
    }

    private void verifyLabels() {
        common.timeoutSeconds(1);

        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        String pageBody = common.getPageBody();
        Common.log.info("Verify email confirmation - Swedish");

        if(testData.isResetPasswordNewSession()){
            common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Ange e-postkod");
            common.verifyPageBodyContainsString(pageBody, "Ange e-postadressen som koden skickats till samt koden från mejlet.");
            common.verifyPageBodyContainsString(pageBody, "E-postadress");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifiera e-postadress");
            common.verifyPageBodyContainsString(pageBody, "Ange den sexsiffriga koden som skickades från " +
                    "no-reply@eduid.se till ");
            common.verifyPageBodyContainsString(pageBody,
                    testData.getEmail().toLowerCase() + " för att bekräfta din e-postadress.");
            common.verifyPageBodyContainsString(pageBody,
                    "Om du inte har fått koden kan du avbryta processen och börja om från början.");
        }

        common.verifyStepIndicator(3, "Verifiera e-postadress");
        common.verifyString(ABORT_BUTTON, "AVBRYT");
        common.verifyString(OK_BUTTON, "FORTSÄTT");
    }

    private void verifyLabelsEnglish() {
        Common.log.info("Verify email confirmation - English");

        common.waitUntilPageTitleContains("Reset password | eduID");

        String pageBody = common.getPageBody();

        if(testData.isResetPasswordNewSession()){
            common.verifyPageBodyContainsString(pageBody, "Reset Password: Enter email code");
            common.verifyPageBodyContainsString(pageBody, "Enter the email address that the code was sent to, and the code from the email.");
            common.verifyPageBodyContainsString(pageBody, "Email address");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Reset password: Verify email address");
            common.verifyPageBodyContainsString(pageBody, "Enter the six digit code sent from no-reply@eduid.se to ");
            common.verifyPageBodyContainsString(pageBody, testData.getEmail().toLowerCase() + " to verify your email address.");
            common.verifyPageBodyContainsString(pageBody, "If you haven't received it, cancel and restart the process.");
        }

        common.verifyStepIndicator(3, "Verify email address");
        common.verifyString(ABORT_BUTTON, "CANCEL");
        common.verifyString(OK_BUTTON, "CONTINUE");
    }
}
