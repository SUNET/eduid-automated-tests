package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.ConfirmIdentityLocators.*;

/**
 * Page object for the identity confirmation flow.
 * Supports verification by: letter (post), Freja eID, eIDAS, Freja without Swedish PNR, and BankID.
 */
public class ConfirmIdentity {

    private final Common   common;
    private final TestData testData;
    private final Identity identity;

    public ConfirmIdentity(Common common, TestData testData, Identity identity) {
        this.common   = common;
        this.testData = testData;
        this.identity = identity;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runConfirmIdentity() {
        common.navigateToIdentity();
        verifyPageTitle();

        if (testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            identity.expandIdentityOptions();
            enterPersonalNumber();
            pressAddButton();
        }

        verifyLabels();
        selectConfirmIdentity();
    }

    // -------------------------------------------------------------------------
    // Navigation & NIN entry
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Identitet | eduID");
    }

    private void enterPersonalNumber() {
        common.verifyPlaceholderBy("ååååmmddnnnn", NIN_INPUT);
        common.findWebElement(NIN_INPUT).sendKeys(testData.getIdentityNumber());
    }

    private void pressAddButton() {
        common.click(common.findWebElement(ADD_NIN_BUTTON));
        common.waitUntilClickable(SHOW_HIDE_BUTTON);
        Common.log.info("Added Swedish personal identity number");
    }

    private void verifyLabels() {
        identity.verifyLabelsSwedish();
        identity.verifyLabelsEnglish();
    }

    // -------------------------------------------------------------------------
    // Identity confirmation — method dispatch
    // -------------------------------------------------------------------------

    public void selectConfirmIdentity() {
        common.timeoutSeconds(1);
        String method = testData.getConfirmIdBy();

        if (method.equalsIgnoreCase("mail")) {
            confirmByLetter();
        } else if (method.equalsIgnoreCase("freja")) {
            confirmByFreja();
        } else if (method.equalsIgnoreCase("eidas")) {
            confirmByEidas();
        } else if (method.equalsIgnoreCase("frejaNoSwedishPnr")) {
            confirmByFrejaNoSwedishPnr();
        } else if (method.equalsIgnoreCase("bankid")) {
            confirmByBankId();
        }
    }

    // -------------------------------------------------------------------------
    // Confirmation methods
    // -------------------------------------------------------------------------

    private void confirmByLetter() {
        Common.log.info("Verifying identity by letter");

        common.timeoutSeconds(3);
        String letterCode = common.getCodeInNewTab(testData.getLetterProofingCodeUrl(), 10);

        verifyLetterSentLabels();

        if (testData.getTestClassName().contains("TC_41")) {
            submitFaultyLetterCode();
        }

        common.findWebElement(LETTER_CODE_INPUT).sendKeys(letterCode);
        common.findWebElement(LETTER_SUBMIT_OK_BUTTON).click();
        common.timeoutMilliSeconds(3800);

        Common.log.info("Identity verified by letter — done");
        common.selectSwedish();
    }

    private void submitFaultyLetterCode() {
        common.findWebElement(LETTER_CODE_INPUT).sendKeys("1qvw3fw2q3");
        common.findWebElement(LETTER_SUBMIT_OK_BUTTON).click();
        common.verifyStatusMessage("Incorrect code");
        common.closeStatusMessage();
        common.click(common.findWebElement(LETTER_PROCEED_BUTTON));
    }

    private void confirmByFreja() {
        Common.log.info("Verifying identity by Freja eID");

        if (testData.getTestClassName().contains("TC_40")) {
            // Verify labels before adding cookie
            common.click(common.findWebElement(FREJA_PROCEED));
            identity.verifyFrejaIdLabelsSwedish();
            common.selectEnglish();
            identity.expandIdentityOptions();
            identity.verifyFrejaIdLabelsEnglish();
            common.findWebElement(FREJA_ACCEPT).click();
            selectAndSubmitUserRefIdp();
            common.verifyStatusMessage("Felaktigt format av identitetsnumret. Var god försök igen.");
            common.addNinCookie();
            common.timeoutMilliSeconds(800);
            identity.expandIdentityOptions();
        } else {
            common.addNinCookie();
        }

        common.click(common.findWebElement(FREJA_PROCEED));
        common.findWebElement(FREJA_ACCEPT).click();
        selectAndSubmitUserRefIdp();
    }

    private void confirmByEidas() {
        Common.log.info("Verifying identity by eIDAS");
        identity.expandIdentityOptions();
        common.click(common.findWebElement(EU_PROCEED));
        common.selectCountry("XA");
        common.submitEidasUser();
        common.submitConsent();
    }

    private void confirmByFrejaNoSwedishPnr() {
        Common.log.info("Verifying identity by Freja (no Swedish PNR)");
        common.click(common.findWebElement(WORLD_PROCEED));

        //Wait and see that we come to Freja eID OpenID Connect - Logga in - page
        common.waitUntilPageTitleContains("Freja eID OpenID Connect - Logga in");
        common.verifyStringOnPage("Logga in på eduID Sweden");
        common.verifyStringOnPage("Öppna Freja-appen och tryck på knappen ”Skanna”.\n" +
                "Skanna QR-koden.");
    }

    private void confirmByBankId() {
        Common.log.info("Verifying identity by BankID");
        identity.expandIdentityOptions();
        common.click(common.findWebElement(BANKID_PROCEED));
        common.waitUntilClickable(BANKID_OTHER_DEVICE).click();
        common.waitUntilClickable(BANKID_ABORT_SCAN).click();
        common.findWebElement(BANKID_CANCEL_AUTH).click();
    }

    // -------------------------------------------------------------------------
    // Letter labels verification
    // -------------------------------------------------------------------------

    private void verifyLetterSentLabels() {
        Common.log.info("Verifying letter-sent labels — Swedish");

        common.verifyString(LETTER_SENT_DATE_XPATH,
                "Ett brev skickades " + common.getDate());
        common.verifyString(LETTER_VALID_DATE_XPATH,
                "Brevet är giltigt till " + common.getDate().plusDays(15));
        common.verifyString(LETTER_INSTRUCTION_XPATH,
                "När du har mottagit brevet, fortsätt genom att klicka på knappen nedan.");
        common.verifyString(LETTER_PROCEED_BUTTON, "FORTSÄTT");

        // Open and verify Swedish pop-up
        Common.log.info("Opening pop-up to verify letter code labels — Swedish");
        common.click(common.findWebElement(LETTER_PROCEED_BUTTON));
        common.verifyString(LETTER_POPUP_HEADING, "Skriv in koden du fått hemskickad");
        common.verifyString(LETTER_POPUP_LABEL, "Kod");
        common.verifyPlaceholderBy("skriv in koden", LETTER_CODE_INPUT);
        common.verifyString(LETTER_SUBMIT_OK_BUTTON, "OK");
        common.click(common.findWebElement(LETTER_POPUP_CLOSE));
        Common.log.info("Closed Swedish letter pop-up");

        // English
        common.selectEnglish();
        Common.log.info("Verifying letter-sent labels — English");

        common.click(common.findWebElement(LETTER_EXPAND_BUTTON));
        common.timeoutMilliSeconds(200);

        common.verifyString(LETTER_SENT_DATE_XPATH,
                "The letter was sent " + common.getDate());
        common.verifyString(LETTER_VALID_DATE_XPATH,
                "The letter is valid to " + common.getDate().plusDays(15));
        common.verifyString(LETTER_INSTRUCTION_XPATH,
                "When you have received the letter, proceed by clicking the button below.");
        common.verifyString(LETTER_PROCEED_BUTTON, "PROCEED");

        Common.log.info("Opening pop-up to verify letter code labels — English");
        common.click(common.findWebElement(LETTER_PROCEED_BUTTON));
        common.verifyString(LETTER_POPUP_HEADING, "Add the code you have received by post");
        common.verifyString(LETTER_POPUP_LABEL, "Code");
        common.verifyPlaceholderBy("enter code", LETTER_CODE_INPUT);
        common.verifyString(LETTER_SUBMIT_OK_BUTTON, "OK");

        Common.log.info("Done verifying letter code pop-up labels — English");
        // Pop-up stays open — caller will enter the code next
    }

    // -------------------------------------------------------------------------
    // Reference IDP helpers
    // -------------------------------------------------------------------------

    public void selectAndSubmitUserRefIdp() {
        common.waitUntilClickable(SELECT_SIMULATED_USER);
        common.findWebElement(ADVANCED_BUTTON).click();
        common.findWebElement(PERSONAL_ID_INPUT).sendKeys(testData.getIdentityNumber());
        common.findWebElement(GIVEN_NAME_INPUT).sendKeys(testData.getGivenName());
        common.findWebElement(SURNAME_INPUT).sendKeys(testData.getSurName());

        Common.log.info("At ref IDP, submitting: {} {} {}",
                testData.getIdentityNumber(), testData.getGivenName(), testData.getSurName());

        common.findWebElement(SUBMIT_BUTTON).click();
        common.timeoutSeconds(5);
    }
}