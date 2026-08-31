package se.sunet.eduid.resetPassword;

import se.sunet.eduid.registration.ConfirmEmailAddress;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.EmailLinkLocators.*;

/**
 * Page object för hämtning och inmatning av e-postkod vid lösenordsåterställning.
 *
 * Om magic cookie inte är satt verifieras att ingen kod är tillgänglig (Bad Request).
 * Annars hämtas koden via ny flik, fylls i och formuläret skickas.
 */
public class EmailLink {

    private final Common               common;
    private final TestData             testData;
    private final ConfirmEmailAddress  confirmEmailAddress;

    public EmailLink(Common common, TestData testData, ConfirmEmailAddress confirmEmailAddress) {
        this.common               = common;
        this.testData             = testData;
        this.confirmEmailAddress  = confirmEmailAddress;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runEmailLink() {
        verifyEmailAddress();
    }

    // -------------------------------------------------------------------------
    // Kodverifiering
    // -------------------------------------------------------------------------

    private void verifyEmailAddress() {
        if (!common.isCookieSet("autotests")) {
            verifyNoCodeWithoutCookie();
        } else {
            fetchAndSubmitCode();
        }
        common.timeoutSeconds(3);
    }

    private void verifyNoCodeWithoutCookie() {
        common.navigateToUrl(testData.getEmailResetPwCodeUrl() + testData.getEppn());
        common.verifyStringOnPage("Bad Request");
    }

    private void fetchAndSubmitCode() {
        common.addMagicCookie();

        String code = common.getCodeInNewTab(
                testData.getEmailResetPwCodeUrl() + testData.getEppn(), 6);
        testData.setEmailCode(code);

        Common.log.info("Filling in the code ({}) and pressing OK", code);
        confirmEmailAddress.typeEmailVerificationCode(code);
        common.findWebElement(OK_BUTTON).click();

        common.waitUntilPageTitleContains("Återställ lösenord | eduID");
    }
}
