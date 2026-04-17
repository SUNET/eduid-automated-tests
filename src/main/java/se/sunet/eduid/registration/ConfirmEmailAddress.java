package se.sunet.eduid.registration;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.registration.ConfirmEmailAddressLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object för sidan "Skapa eduID: Verifiera e-postadress".
 *
 * Hanterar tre scenarion:
 *  1. E-postadressen är redan registrerad — verifiera felmeddelande och navigera till inloggning.
 *  2. Felaktig verifieringskod — verifiera felmeddelande och räkna antalet försök.
 *  3. Korrekt kod — vänta på nästa sida.
 *
 * Om {@code testData.isVerifyEmail()} är false avbryts flödet och användaren
 * skickas tillbaka till registreringssidan.
 */
public class ConfirmEmailAddress {

    private final Common   common;
    private final TestData testData;
    private       int      emailVerificationAttempts = 0;

    public ConfirmEmailAddress(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runConfirmEmailAddress() {
        verifyPageTitle();
        submitEmailConfirmationCode();
    }

    // -------------------------------------------------------------------------
    // Navigering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Registrera | eduID");
    }

    // -------------------------------------------------------------------------
    // Verifieringsflöde
    // -------------------------------------------------------------------------

    private void submitEmailConfirmationCode() {
        if (!testData.isVerifyEmail()) {
            cancelAndReturnToRegistration();
            return;
        }

        if (!testData.isGenerateUsername()) {
            handleAlreadyRegisteredEmail();
        } else {
            handleEmailVerification();
        }
    }

    private void cancelAndReturnToRegistration() {
        common.click(common.findWebElement(ABORT_BUTTON));
        common.verifyStringOnPage("Skapa eduID: Ange dina personuppgifter");
    }

    private void handleAlreadyRegisteredEmail() {
        common.verifyStatusMessage("E-postadressen är redan registrerad. Om du har glömt ditt lösenord kan " +
                "du gå till inloggningssidan och återställa det.");
        common.selectEnglish();
        common.verifyStatusMessage("The email address is already registered. If you've forgotten your password, " +
                "go to the login page and reset it.");
        common.click(common.findWebElement(LOGIN_BUTTON));
    }

    private void handleEmailVerification() {
        verifyTextAndLabels();
        fetchCodeIfNeeded();
        typeEmailVerificationCode(testData.getEmailVerificationCode());
        common.findWebElement(OK_BUTTON).click();
        verifyVerificationOutcome();
    }

    private void fetchCodeIfNeeded() {
        if ("987654".equals(testData.getEmailVerificationCode())) {
            Common.log.info("Using intentionally incorrect code: {}", testData.getEmailVerificationCode());
        } else {
            String userNameEncoded = testData.getUsername().replace("@", "%40");
            String code = common.getCodeInNewTab(
                    testData.getEmailVerificationCodeUrl() + userNameEncoded.toLowerCase(), 6);
            testData.setEmailVerificationCode(code);
        }
    }

    private void verifyVerificationOutcome() {
        if ("987654".equals(testData.getEmailVerificationCode()) && emailVerificationAttempts == 2) {
            verifyTooManyAttemptsError();
        } else if ("987654".equals(testData.getEmailVerificationCode())) {
            verifyIncorrectCodeError();
        } else {
            common.waitUntilClickable(NEXT_PAGE_BUTTON);
        }
    }

    private void verifyTooManyAttemptsError() {
        common.verifyStatusMessage("För många ogiltiga verifieringsförsök. Var god försök igen senare.");
        common.selectEnglish();
        common.verifyStatusMessage("Too many invalid verification attempts. Please try again later.");
        common.selectSwedish();
    }

    private void verifyIncorrectCodeError() {
        common.verifyStatusMessage("E-post verifieringen misslyckades. Var god försök igen.");
        common.selectEnglish();
        common.verifyStatusMessage("The email verification failed. Please try again.");
        common.selectSwedish();
        common.closeStatusMessage();
        emailVerificationAttempts++;
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering
    // -------------------------------------------------------------------------

    private void verifyTextAndLabels() {
        log.info("Verifying email verification labels — Swedish");
        common.waitUntilClickable(ABORT_BUTTON);

        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Verifiera e-postadress");
        common.verifyPageBodyContainsString(pageBody,
                "Ange den sexsiffriga koden som skickats från no-reply@eduid.se till");
        common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pageBody,
                "för att verifiera din e-postadress. Du kan också kopiera och klistra in koden från " +
                "e-posten i inmatningsfältet.");
        common.verifyPageBodyContainsString(pageBody, "Koden går ut om");
        common.verifyString(ABORT_BUTTON, "AVBRYT");
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying email verification labels — English");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Verification of email address");
        common.verifyPageBodyContainsString(pageBody, "Enter the six digit code sent from no-reply@eduid.se to");
        common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pageBody,
                "to verify your email address. You can also copy and paste the code from the email into the input field.");
        common.verifyPageBodyContainsString(pageBody, "The code expires in");
        common.verifyString(ABORT_BUTTON, "CANCEL");
    }

    // -------------------------------------------------------------------------
    // Kodinmatning
    // -------------------------------------------------------------------------

    /**
     * Skriver in en sexsiffrig verifieringskod ett tecken i taget
     * i de separata inmatningsfälten.
     */
    public void typeEmailVerificationCode(String code) {
        common.timeoutSeconds(2);
        common.findWebElement(CODE_DIGIT_0).sendKeys(code.substring(0, 1));
        common.waitUntilClickable(CODE_DIGIT_1).sendKeys(code.substring(1, 2));
        common.waitUntilClickable(CODE_DIGIT_2).sendKeys(code.substring(2, 3));
        common.waitUntilClickable(CODE_DIGIT_3).sendKeys(code.substring(3, 4));
        common.waitUntilClickable(CODE_DIGIT_4).sendKeys(code.substring(4, 5));
        common.waitUntilClickable(CODE_DIGIT_5).sendKeys(code.substring(5, 6));
    }
}
