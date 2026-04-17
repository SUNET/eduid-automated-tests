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
        common.verifyPageTitle("Återställ lösenord | eduID");
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

        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifiera e-postadressen");
        common.verifyPageBodyContainsString(pageBody, "Om du har ett eduID-konto, har koden skickats till ");
        common.verifyPageBodyContainsString(pageBody,
                testData.getEmail().toLowerCase() + ". från no-reply@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "E-postkoden är giltig i två timmar.");
        common.verifyPageBodyContainsString(pageBody,
                "Om du inte har fått koden kan du avbryta processen och börja om från början.");

        common.verifyString(ABORT_BUTTON, "AVBRYT");
        common.verifyString(OK_BUTTON, "OK");
    }

    private void verifyLabelsEnglish() {
        common.verifyPageTitle("Reset password | eduID");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Reset password: Verify email address");
        common.verifyPageBodyContainsString(pageBody, "If you have an eduID account, the code has been sent to ");
        common.verifyPageBodyContainsString(pageBody,
                testData.getEmail().toLowerCase() + ". from no-reply@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "The email code is valid for two hours.");
        common.verifyPageBodyContainsString(pageBody,
                "If you haven't receive the code, please cancel the process and restart from the beginning.");

        common.verifyString(ABORT_BUTTON, "CANCEL");
        common.verifyString(OK_BUTTON, "OK");
    }
}
