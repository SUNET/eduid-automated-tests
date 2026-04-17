package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.DeleteAccountLocators.*;
import static se.sunet.eduid.generic.LoginPageLocators.LOGIN_BUTTON;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object för borttagning av konto på kontosidan.
 *
 * Flöde:
 *  1. Navigera till kontosidan.
 *  2. Verifiera etiketter på engelska, därefter svenska (varje språk öppnar sin
 *     egen pop-up och stänger den efter verifiering).
 *  3. Öppna pop-upen en sista gång och klicka antingen Ta bort eller Stäng.
 */
public class DeleteAccount {

    private final Common   common;
    private final TestData testData;

    // Säkerhetsnyckelknapp att vänta på om konto har säkerhetsnyckel tillagd
    private static final String SECURITY_KEY_BUTTON = "mfa-security-key";

    public DeleteAccount(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runDeleteAccount() {
        common.navigateToAccount();
        verifyLabelsEnglish();
        verifyLabelsSwedish();
        clickDeleteInPopUp();
    }

    public void confirmDeleteAfter5Min() {
        log.info("Verifying pop-up text and labels when account deletion requires re-login");

        common.securityConfirmPopUpBy(DELETE_BUTTON, "", "");

        log.info("Clicked Accept — forwarded to login page for account deletion");

        // Vänta på nästa sida beroende på om säkerhetsnyckel finns
        if (testData.isAddExternalSecurityKey() || testData.isAddInternalPassKey()) {
            common.waitUntilClickable(By.id(SECURITY_KEY_BUTTON));
        } else {
            common.waitUntilClickable(LOGIN_BUTTON);
        }
    }

    // -------------------------------------------------------------------------
    // Flöde
    // -------------------------------------------------------------------------

    private void clickDeleteInPopUp() {
        if (testData.isDeleteButton()) {
            common.waitUntilClickable(CONFIRM_DELETE_BUTTON).click();
            common.timeoutMilliSeconds(5000);
            log.info("Pressed Delete button in pop-up");
        } else {
            common.findWebElement(CLOSE_MODAL_BUTTON).click();
            log.info("Pressed Close button in pop-up");
        }
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — sida
    // -------------------------------------------------------------------------

    private void verifyLabelsSwedish() {
        common.selectSwedish();
        log.info("Verifying Delete labels and text — Swedish");

        common.verifyString(HEADING_XPATH, "Spärra och radera eduID");
        common.verifyString(TEXT_XPATH,
                "Klicka på länken för att radera ditt eduID. Det spärrar all åtkomst till kontot om du inte väljer " +
                "att byta lösenord inom en vecka, sedan rensas all information du sparat permanent.");
        common.verifyString(DELETE_BUTTON, "Radera eduID");

        common.waitUntilClickable(DELETE_BUTTON).click();
        verifyPopUpLabelsSwedish();
    }

    private void verifyLabelsEnglish() {
        common.waitUntilClickable(DELETE_BUTTON);

        common.selectEnglish();
        log.info("Verifying Delete labels and text — English");

        //For some reason language is often not changed at this point, change it again...
        common.selectEnglish();


        common.verifyString(HEADING_XPATH, "Block and delete eduID");
        common.verifyString(TEXT_XPATH,
                "Click the link to delete your eduID. It will block any access to the account unless you change " +
                "your password within one week, after which it will be removed permanently.");
        common.verifyString(DELETE_BUTTON, "Delete eduID");

        common.waitUntilClickable(DELETE_BUTTON).click();
        verifyPopUpLabelsEnglish();
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — pop-up
    // -------------------------------------------------------------------------

    private void verifyPopUpLabelsSwedish() {
        log.info("Verifying Delete pop-up labels and text — Swedish");

        common.waitUntilClickable(CONFIRM_DELETE_BUTTON);
        common.verifyStringOnPage("Är du säker på att du vill ta bort ditt eduID?");
        common.verifyStringOnPage(
                "När du tar bort ditt eduID kommer all information du sparat rensas permanent. Om det har gått " +
                "lång tid sedan du senast loggade in kan det hända att du behöver logga in igen.");
        common.verifyString(CONFIRM_DELETE_BUTTON, "RADERA MITT EDUID");
    }

    private void verifyPopUpLabelsEnglish() {
        common.waitUntilClickable(CONFIRM_DELETE_BUTTON);
        log.info("Verifying Delete pop-up labels and text — English");

        common.selectEnglish();
        common.verifyStringOnPage("Are you sure you want to delete your eduID?");
        common.verifyStringOnPage(
                "Deleting your eduID will permanently remove all your saved information. If it has been a long " +
                "time since you last logged in, you may need to log in again.");
        common.verifyString(CONFIRM_DELETE_BUTTON, "DELETE MY EDUID");

        common.findWebElement(CLOSE_MODAL_BUTTON).click();
    }
}
