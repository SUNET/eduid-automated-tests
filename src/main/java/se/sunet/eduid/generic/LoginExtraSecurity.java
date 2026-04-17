package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.generic.LoginExtraSecurityLocators.*;

/**
 * Page object for the extra security (MFA) step during login or re-authentication.
 *
 * Two distinct scenarios drive most of the label differences:
 *  - Normal MFA login      (isIdentityConfirmed && !isAddExternalSecurityKey)
 *  - Re-authentication MFA (everything else)
 */
public class LoginExtraSecurity {

    private final Common   common;
    private final TestData testData;

    public LoginExtraSecurity(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    public void runLoginExtraSecurity() {
        verifyPageTitle();
        verifyTexts();
    }

    // -------------------------------------------------------------------------
    // Page verification
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Logga in | eduID");
    }

    private void verifyTexts() {
        common.waitUntilClickable(SECURITY_KEY_BUTTON);

        verifySwedishLabels();
        common.selectEnglish();
        verifyEnglishLabels();
        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Swedish labels
    // -------------------------------------------------------------------------

    private void verifySwedishLabels() {
        common.timeoutSeconds(1);
        String pageBody = common.getPageBody();
        Common.log.info("Verifying text and labels in Swedish");

        if (isNormalMfaLogin()) {
            verifyNormalMfaHeaderSwedish(pageBody);
        } else {
            verifyReAuthMfaHeaderSwedish(pageBody);
        }

        verifyCommonMfaLabelsSwedish(pageBody);
    }

    private void verifyNormalMfaHeaderSwedish(String pageBody) {
        if (!testData.isAddInternalPassKey()) {
            common.verifyPageBodyContainsString(pageBody, "Logga in: med MFA");
        }
        common.verifyPageBodyContainsString(pageBody,
                "Autentisera dig själv med ytterligare en metod för att vara säker på att bara du har " +
                "tillgång till ditt eduID. Om du inte kan använda säkerhetsnyckeln, var vänlig välj annat " +
                "alternativ nedan, t.ex. BankID, Freja+, eIDAS eller Freja eID.");
    }

    private void verifyReAuthMfaHeaderSwedish(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Återautentisering: med MFA");
        common.verifyPageBodyContainsString(pageBody, "Autentisera dig för att fortsätta");
        common.verifyPageBodyContainsString(pageBody, cancelLinkTextSwedish());
        common.verifyPageBodyContainsString(pageBody,
                "Autentisera dig själv med ytterligare en metod för att vara säker på att bara du har " +
                "tillgång till ditt eduID.");

        if (shouldVerifyMultiKeyWarning()) {
            common.verifyPageBodyContainsString(pageBody,
                    "Det rekommenderas starkt att lägga till mer än en säkerhetsnyckel eller passkey/lösennyckel " +
                    "för att försäkra dig om att du kan logga in även om en förloras.");
        }
    }

    private void verifyCommonMfaLabelsSwedish(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");
        common.verifyPageBodyContainsString(pageBody, "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody,
                "T.ex. USB-säkerhetsnyckel, eller lösennyckel (passkey) med enheten som du just nu använder.");
        common.verifyString(SECURITY_KEY_BUTTON, "ANVÄND MIN SÄKERHETSNYCKEL");

        if (shouldVerifyIdentityRequirement()) {
            common.verifyPageBodyContainsString(pageBody,
                    "Kräver att du har verifierat din identitet i eduID med svenskt person- eller " +
                    "samordningsnummer, eIDAS eller Freja eID.");
        }
    }

    // -------------------------------------------------------------------------
    // English labels
    // -------------------------------------------------------------------------

    private void verifyEnglishLabels() {
        String pageBody = common.getPageBody();
        Common.log.info("Verifying text and labels in English");

        if (isNormalMfaLogin()) {
            verifyNormalMfaHeaderEnglish(pageBody);
        } else {
            verifyReAuthMfaHeaderEnglish(pageBody);
        }

        verifyCommonMfaLabelsEnglish(pageBody);
    }

    private void verifyNormalMfaHeaderEnglish(String pageBody) {
        if (!testData.isAddInternalPassKey()) {
            common.verifyPageBodyContainsString(pageBody, "Log in: with MFA");
        }
        common.verifyPageBodyContainsString(pageBody,
                "Choose a second method to authenticate yourself, ensuring only you can access your eduID. " +
                "If you are unable to use the security key, please select other options below, such as " +
                "BankID, Freja+, eIDAS or Freja eID.");
    }

    private void verifyReAuthMfaHeaderEnglish(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Re-authentication: with MFA");
        common.verifyPageBodyContainsString(pageBody, "Authenticate to continue");
        common.verifyPageBodyContainsString(pageBody, cancelLinkTextEnglish());
        common.verifyPageBodyContainsString(pageBody,
                "Choose a second method to authenticate yourself, ensuring only you can access your eduID.");

        // TODO: uncomment when bug is fixed and the multi-key warning text is visible in English again
        // if (shouldVerifyMultiKeyWarning()) {
        //     common.verifyPageBodyContainsString(pageBody,
        //             "It is strongly recommended to add more than one security key or passkey to ensure " +
        //             "you can still sign in to your account if one is lost.");
        // }
    }

    private void verifyCommonMfaLabelsEnglish(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");
        common.verifyPageBodyContainsString(pageBody, "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody,
                "E.g. USB Security Key, or passkey with the device you are currently using.");
        common.verifyString(SECURITY_KEY_BUTTON, "USE MY SECURITY KEY");

        if (shouldVerifyIdentityRequirement()) {
            common.verifyPageBodyContainsString(pageBody,
                    "Requires that you have verified your identity in eduID with a Swedish personal " +
                    "identity number or coordination number, eIDAS or Freja eID.");
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** True when the user is doing a normal MFA login (not re-authentication). */
    private boolean isNormalMfaLogin() {
        return testData.isIdentityConfirmed() && !testData.isAddExternalSecurityKey();
    }

    /**
     * The multi-key warning is only shown during re-auth when identity is not yet
     * confirmed, and not during TC_16 which removes the security key mid-flow.
     */
    private boolean shouldVerifyMultiKeyWarning() {
        return !testData.isIdentityConfirmed()
                && !testData.getTestClassName().contains("TC_16");
    }

    /** The identity requirement notice is only shown when Freja verification is not in use. */
    private boolean shouldVerifyIdentityRequirement() {
        return !testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed();
    }

    private String cancelLinkTextSwedish() {
        return testData.isDeleteButton()
                ? "Om du vill avbryta utan att spara förändringen kan du återvända direkt till sidan Konto."
                : "Om du vill avbryta utan att spara förändringen kan du återvända direkt till sidan Säkerhet.";
    }

    private String cancelLinkTextEnglish() {
        return testData.isDeleteButton()
                ? "If you wish to cancel this process without affecting a change you can return straight to Account page."
                : "If you wish to cancel this process without affecting a change you can return straight to Security page.";
    }
}
