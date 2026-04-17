package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.RequestNewPasswordLocators.*;

/**
 * Page object för sidan "Återställ lösenord: Ange e-postadressen".
 * Verifierar etiketter, fyller i e-postadressen och skickar formuläret.
 */
public class RequestNewPassword {

    private final Common   common;
    private final TestData testData;
    private final Register register;

    public RequestNewPassword(Common common, TestData testData, Register register) {
        this.common   = common;
        this.testData = testData;
        this.register = register;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runRequestNewPassword() {
        verifyPageTitle();
        verifyLabels();
        enterEmail();
        pressResetPassword();
    }

    // -------------------------------------------------------------------------
    // Navigering & inmatning
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Återställ lösenord | eduID");
    }

    private void enterEmail() {
        WebElement emailField = common.findWebElement(EMAIL_INPUT);
        emailField.clear();
        emailField.sendKeys(testData.getUsername());
    }

    public void pressResetPassword() {
        common.click(common.findWebElement(RESET_PASSWORD_BUTTON));

        // Om BankID-autentisering redan påbörjats och avbrutits i samma session
        // hoppas captcha-steget över
        if (testData.getMfaMethod().equalsIgnoreCase("bankid")) {
            common.waitUntilClickable(RESET_PASSWORD_BUTTON);
        } else {
            common.waitUntilClickable(CANCEL_CAPTCHA_BUTTON);
            common.addNinCookie();
            register.enterCaptchaCode();
        }

        if (testData.isIncorrectPassword()) {
            verifyUserNotFoundError();
        } else {
            common.waitUntilClickable(ABORT_BUTTON);
        }
    }

    private void verifyUserNotFoundError() {
        common.verifyStatusMessage("Användaren hittades inte. Vänligen försök igen.");
        common.selectEnglish();
        common.verifyStatusMessage("User not found. Please try again.");
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        common.verifyStringOnPage("Återställ lösenord: Ange e-postadressen");
        common.verifyStringOnPage("Om det finns en användare med den epostadressen, skickas ett mail " +
                "med instruktioner från no-reply@eduid.se.");
        common.verifyString(EMAIL_LABEL_XPATH, "E-postadress");
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);
        common.verifyString(RESET_PASSWORD_BUTTON, "SKICKA E-POST");
        common.verifyString(GO_BACK_BUTTON, "TILLBAKA");
    }

    private void verifyLabelsEnglish() {
        common.verifyPageTitle("Reset password | eduID");
        common.verifyStringOnPage("Reset password: Enter the email address");
        common.verifyStringOnPage("Once entered, if the address is registered, a message with " +
                "instructions to reset the password will be sent from no-reply@eduid.se.");
        common.verifyString(EMAIL_LABEL_XPATH, "Email address");
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);
        common.verifyString(RESET_PASSWORD_BUTTON, "SEND EMAIL");
        common.verifyString(GO_BACK_BUTTON, "GO BACK");
    }
}
