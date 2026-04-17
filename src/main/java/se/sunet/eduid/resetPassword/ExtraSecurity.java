package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.ExtraSecurityLocators.*;

/**
 * Page object för sidan "Återställ lösenord: Verifieringsmetod".
 * Verifierar etiketter i båda språken och väljer sedan MFA-metod.
 *
 * Notera: `com.sun.mail.handlers.text_html` var ett felaktigt import
 * i originalfilen och är borttaget.
 */
public class ExtraSecurity {

    private final Common   common;
    private final TestData testData;

    public ExtraSecurity(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runExtraSecurity() {
        verifyPageTitle();
        verifyLabels();
        selectMfaMethod();
    }

    // -------------------------------------------------------------------------
    // Sidtitel
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        if (testData.isAddExternalSecurityKey()) {
            common.verifyPageTitle("Logga in | eduID");
        } else if (testData.isResetPassword()) {
            common.verifyPageTitle("Återställ lösenord | eduID");
        }
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        // Säkerställ engelska om footer-länken innehåller "English"
        if (common.findWebElement(FOOTER_LANG_ITEM).getText().contains("English")) {
            common.selectEnglish();
        }

        verifyLabelsEnglish();
        common.selectSwedish();
        verifyLabelsSwedish();
    }

    private void verifyLabelsEnglish() {
        common.timeoutSeconds(1);

        if (testData.isAddExternalSecurityKey()) {
            common.waitUntilPageTitleContains("Log in | eduID");
        } else if (testData.isResetPassword()) {
            common.waitUntilPageTitleContains("Reset password | eduID");
        }

        String pageBody = common.getPageBody();
        common.waitUntilClickable(CONTINUE_WITHOUT_SECURITY);

        common.verifyPageBodyContainsString(pageBody, "Reset password: Verification method");
        common.verifyPageBodyContainsString(pageBody, descriptionTextEnglish());
        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");
        common.verifyPageBodyContainsString(pageBody, "Continue without additional authentication");
        common.verifyPageBodyContainsString(pageBody,
                "Your identity will require confirmation after the password has been reset. ");
        common.verifyString(CONTINUE_WITHOUT_SECURITY, "Continue resetting password");
    }

    private void verifyLabelsSwedish() {
        String pageBody = common.getPageBody();
        common.waitUntilClickable(CONTINUE_WITHOUT_SECURITY);

        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifieringsmetod");
        common.verifyPageBodyContainsString(pageBody, descriptionTextSwedish());
        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");
        common.verifyPageBodyContainsString(pageBody, "Fortsätt utan ytterligare autentisering");
        common.verifyPageBodyContainsString(pageBody,
                "Din identitet kommer att behöva verifieras efter att lösenordet har återställts. ");
        common.verifyString(CONTINUE_WITHOUT_SECURITY, "Fortsätt återställa lösenordet");
    }

    // -------------------------------------------------------------------------
    // Kontextberoende beskrivningstexter
    // -------------------------------------------------------------------------

    private String descriptionTextEnglish() {
        if (testData.isResetPassword()) {
            return "Choose a second method to authenticate yourself, ensuring only you can access your eduID. " +
                   "If you are unable to use the security key, please select from other options below, " +
                   "such as BankID or Freja+.";
        }
        return "Select an security option to maintain identity confirmation during the password reset process, " +
               "or continue without security, with identity confirmation required after the password reset.";
    }

    private String descriptionTextSwedish() {
        if (testData.isResetPassword()) {
            return "Autentisera dig själv med ytterligare en metod för att vara säker på att bara du har tillgång " +
                   "till ditt eduID. Om du inte kan använda säkerhetsnyckeln, var vänlig välj annat alternativ " +
                   "nedan, t.ex. BankID eller Freja+.";
        }
        return "Välj ett säkerhetsalternativ för att bekräfta din identitet under lösenordsåterställningsprocessen, " +
               "eller fortsätt utan säkerhet, med krav på identitetsbekräftelse efter lösenordsåterställningen.";
    }

    // -------------------------------------------------------------------------
    // Val av MFA-metod
    // -------------------------------------------------------------------------

    public void selectMfaMethod() {
        String method = testData.getMfaMethod();

        if (method.equalsIgnoreCase("no")) {
            selectNoExtraSecurity();
        } else if (method.equalsIgnoreCase("freja")) {
            selectFreja();
        } else if (method.equalsIgnoreCase("bankid")) {
            selectBankId();
        } else if (method.equalsIgnoreCase("eidas")) {
            selectEidas();
        } else if (method.equalsIgnoreCase("securitykey")) {
            selectSecurityKey();
        }
    }

    private void selectNoExtraSecurity() {
        Common.log.info("Selecting 'no extra security'");
        common.scrollToPageBottom();
        common.waitUntilClickable(CONTINUE_WITHOUT_SECURITY).click();
    }

    private void selectFreja() {
        Common.log.info("Selecting Freja+");
        common.selectSwedish();
        common.scrollToPageBottom();
        common.waitUntilClickable(SHOW_OTHER_OPTIONS_SWE).click();
        common.timeoutMilliSeconds(500);
        common.waitUntilClickable(FREJA_BUTTON_SWE).click();
        common.waitUntilPageTitleContains("Sweden Connect Reference Identity Provider");
    }

    private void selectBankId() {
        Common.log.info("Selecting BankID");
        common.selectSwedish();
        common.scrollToPageBottom();
        common.waitUntilClickable(SHOW_OTHER_OPTIONS_SWE).click();
        common.timeoutMilliSeconds(500);
        common.waitUntilClickable(BANKID_BUTTON_SWE).click();
        common.waitUntilPageTitleContains("BankID");
    }

    private void selectEidas() {
        Common.log.info("Selecting eIDAS");
        common.selectSwedish();
        common.timeoutMilliSeconds(500);
        common.scrollToPageBottom();
        common.waitUntilClickable(SHOW_OTHER_OPTIONS_SWE).click();
        common.waitUntilClickable(EIDAS_BUTTON_SWE).click();
        common.waitUntilPageTitleContains("Foreign eID - Sweden Connect");
    }

    private void selectSecurityKey() {
        Common.log.info("Selecting external security key");
        common.findWebElement(SECURITY_KEY_BUTTON).click();
        common.timeoutSeconds(8);
    }
}
