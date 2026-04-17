package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.RequestResetPwEmailLocators.*;

/**
 * Page object för sidan "Återställ lösenord: Starta processen".
 * Verifierar etiketter i båda språken, klickar sedan på Skicka e-post.
 */
public class RequestResetPwEmail {

    private final Common   common;
    private final TestData testData;
    private final Register register;

    public RequestResetPwEmail(Common common, TestData testData, Register register) {
        this.common   = common;
        this.testData = testData;
        this.register = register;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runRequestResetPwEmail() {
        verifyPageTitle();
        verifyLabels();
        clickSendEmail();
    }

    // -------------------------------------------------------------------------
    // Navigering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord | eduID");
    }

    private void clickSendEmail() {
        common.click(common.waitUntilClickable(SEND_EMAIL_BUTTON));
        Common.log.info("Clicked send email button");

        // Om BankID-autentisering redan påbörjats och avbrutits i samma session
        // hoppas captcha-steget över och användaren hamnar direkt på nästa sida
        if (testData.getMfaMethod().equalsIgnoreCase("bankid")) {
            common.waitUntilClickable(By.id("response-code-abort-button"));
        } else {
            common.waitUntilClickable(By.id("cancel-captcha-button"));
            common.addNinCookie();
            register.enterCaptchaCode();
        }

        common.waitUntilClickable(By.id("response-code-abort-button"));
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
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody,
                "Återställ lösenord: Starta processen för återställning av konto");
        common.verifyPageBodyContainsString(pageBody,
                "Klicka på knappen nedan för att skicka ett e-postmeddelande till");
        common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pageBody,
                "Om du väljer att avbryta, klicka på knappen Tillbaka för att återgå till inloggningssidan.");

        common.verifyString(SEND_EMAIL_BUTTON, "SKICKA E-POST");
        common.verifyString(GO_BACK_BUTTON, "TILLBAKA");
    }

    private void verifyLabelsEnglish() {
        common.verifyPageTitle("Reset password | eduID");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Reset password: Start account recovery process");
        common.verifyPageBodyContainsString(pageBody, "Click the button below to send an e-mail to");
        common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        common.verifyPageBodyContainsString(pageBody,
                "If you decide to cancel, simply click the Go Back button to return to the login page.");

        common.verifyString(SEND_EMAIL_BUTTON, "SEND EMAIL");
        common.verifyString(GO_BACK_BUTTON, "GO BACK");
    }
}
