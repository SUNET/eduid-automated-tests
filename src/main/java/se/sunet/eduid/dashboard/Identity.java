package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.IdentityLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object for the Identity page.
 * Verifies all option sections (BankID, Freja+, letter, eIDAS, Freja eID) in both languages.
 */
public class Identity {

    private final Common   common;
    private final TestData testData;
    private final Name     name;

    public Identity(Common common, TestData testData, Name name) {
        this.common   = common;
        this.testData = testData;
        this.name     = name;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runIdentity() {
        common.navigateToIdentity();
        verifyPageTitle();

        if (testData.getTestSuite().equalsIgnoreCase("prod")) {
            common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Ditt eduID är redo att användas");
        } else {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }

        name.runName();
    }

    // -------------------------------------------------------------------------
    // Page title
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Identitet | eduID");
    }

    // -------------------------------------------------------------------------
    // Swedish label verification
    // -------------------------------------------------------------------------

    public void verifyLabelsSwedish() {
        log.info("Verifying Identity labels in Swedish");
        expandIdentityOptions();

        common.verifyString(PAGE_H1, "Identitet");
        common.verifyString(PAGE_SUBTEXT,
                "För att använda vissa tjänster behöver din identitet verifieras. Koppla din identitet till " +
                "ditt eduID för att få mest användning av det.");
        common.verifyString(CHOOSE_METHOD_HEADING, "Välj din huvudsakliga identifieringsmetod");
        common.verifyString(SWEDISH_BUTTON_H3, "Svenskt personnummer eller samordningsnummer");
        common.verifyString(SWEDISH_BUTTON_SPAN, "Med digitalt ID / Via post");
        common.verifyString(SWEDISH_DESCRIPTION,
                "Verifiera att du har tillgång till ditt person- eller samordningsnummer.");

        verifyBankIdSectionSwedish();
        verifyFrejaSectionSwedish();
        verifyLetterSectionSwedish();

        if (testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            verifyMailTextsInPopUpSwedish();
        }

        expandIdentityOptions();
        verifyEidasSectionSwedish();
        verifyWorldFrejaSwedish();

        log.info("Verifying Identity labels in Swedish — done");
    }

    // -------------------------------------------------------------------------
    // English label verification
    // -------------------------------------------------------------------------

    public void verifyLabelsEnglish() {
        common.selectEnglish();
        log.info("Verifying Identity labels in English");
        expandIdentityOptions();

        common.verifyString(PAGE_H1, "Identity");
        common.verifyString(PAGE_SUBTEXT,
                "Some services need to know your real life identity. Connect your identity to your eduID to get the most benefit from it.");
        common.verifyString(CHOOSE_METHOD_HEADING, "Choose your principal identification method");
        common.verifyString(SWEDISH_BUTTON_H3, "Swedish personal ID or coordination number");
        common.verifyString(SWEDISH_BUTTON_SPAN, "With a digital ID / By post");

        verifyBankIdSectionEnglish();
        verifyFrejaSectionEnglish();
        verifyLetterSectionEnglish();

        if (testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            verifyMailTextsInPopUpEnglish();
        }

        verifyEidasSectionEnglish();
        verifyWorldFrejaEnglish();

        common.timeoutSeconds(1);
        log.info("Verifying Identity labels in English — done");

        common.selectSwedish();
        expandIdentityOptions();
    }

    // -------------------------------------------------------------------------
    // Section helpers — Swedish
    // -------------------------------------------------------------------------

    private void verifyBankIdSectionSwedish() {
        log.info("Verify BankID labels - Swedish");
        common.verifyString(BANKID_BUTTON_SPAN, "För dig som kan använda BankID");
        common.verifyString(BANKID_BUTTON_H3, "MED BANKID");
        common.verifyString(BANKID_DESC_P1,
                "För att använda det här alternativet behöver du först skapa ett digitalt ID i BankID appen.");
        common.verifyString(BANKID_DESC_P2,
                "Knappen nedan tar dig till en extern identifieringssida, där du genom att identifiera dig med " +
                "Bank ID verifierar din identitet mot eduID.");
        common.verifyString(BANKID_PROCEED_BUTTON, "FORTSÄTT");
    }

    private void verifyFrejaSectionSwedish() {
        log.info("Verify Freja labels - Swedish");
        common.verifyString(FREJA_BUTTON_SPAN,
                "För dig som har eller kan skapa Freja+ genom att använda appen eller besöka ett ombud");
        common.verifyString(FREJA_BUTTON_H3, "MED FREJA+");
        common.verifyString(FREJA_DESC_P,
                "För att använda det här alternativet behöver du först skapa ett digitalt ID i Freja appen.");
        //common.verifyLocatorIsWorkingLink(FREJA_DESC_P + "/a");
        common.verifyString(FREJA_PROCEED_BUTTON, "FORTSÄTT");
    }

    private void verifyLetterSectionSwedish() {
        log.info("Verify Letter labels - Swedish");
        common.verifyString(LETTER_BUTTON_SPAN, "För dig som har en svensk folkbokföringsadress");
        common.verifyString(LETTER_BUTTON_H3, "VIA POST");
        common.verifyString(LETTER_DESC_P1,
                "Ett brev skickas till dig med en kod. Av säkerhetsskäl är koden giltig i två veckor.");
        common.verifyString(LETTER_PROCEED_BUTTON, "FORTSÄTT");
    }

    private void verifyEidasSectionSwedish() {
        log.info("Verify eIDAS labels - Swedish");
        common.verifyString(EU_BUTTON_H3, "EU-medborgare");
        common.verifyString(EU_BUTTON_SPAN, "Med eIDAS elektronisk identifiering");
        common.verifyString(EU_DESC_P1,
                "Om du har ett elektroniskt ID från ett eIDAS-anslutet land kan du använda det för att bekräfta " +
                "din identitet i eduID.");
        common.verifyString(EU_DESC_P2,
                "Knappen Fortsätt tar dig till en extern sida där du kan logga in med ditt elektroniska ID för " +
                "att koppla din identitet till eduID.");
        common.verifyString(EU_PROCEED_BUTTON, "FORTSÄTT");
    }

    private void verifyWorldFrejaSwedish() {
        log.info("Verify Freja eID labels - Swedish");
        common.verifyString(WORLD_BUTTON_H3, "De flesta länder");
        common.verifyString(WORLD_BUTTON_SPAN, "Med Freja eID identitetsverifiering");
        common.verifyString(WORLD_DESC_P1, "Om du har ett Freja eID kan du koppla det till ditt eduID.");
        //common.verifyXpathIsWorkingLink(WORLD_DESC_P1 + "/a");
        common.verifyString(WORLD_DESC_P2,
                "Knappen nedan tar dig till en extern identifieringssida, där du genom att identifiera dig med " +
                "Freja eID verifierar din identitet mot eduID.");
        common.verifyString(WORLD_PROCEED_BUTTON, "FORTSÄTT");
    }

    // -------------------------------------------------------------------------
    // Section helpers — English
    // -------------------------------------------------------------------------

    private void verifyBankIdSectionEnglish() {
        log.info("Verify BankID labels - English");
        common.verifyString(BANKID_BUTTON_SPAN, "If you are able to use BankID");
        common.verifyString(BANKID_BUTTON_H3, "WITH A BANKID");
        common.timeoutMilliSeconds(500);
        common.verifyString(BANKID_DESC_P1,
                "To use this option you will need to first create a digital ID in the BankID app.");
        common.verifyString(BANKID_DESC_P2,
                "The button below will take you to an external identification site, where you by identifying " +
                "yourself with BankID will verify your identity towards eduID.");
        common.verifyString(BANKID_PROCEED_BUTTON, "PROCEED");
    }

    private void verifyFrejaSectionEnglish() {
        log.info("Verify Freja labels - English");
        common.verifyString(FREJA_BUTTON_SPAN,
                "If you are able to create a Freja+ by using the app or visiting an authorised agent");
        common.verifyString(FREJA_BUTTON_H3, "WITH FREJA+");
        common.verifyString(FREJA_DESC_P,
                "To use this option you will need to first create a digital ID in the Freja app.");
        //common.verifyXpathIsWorkingLink(FREJA_DESC_P + "/a");
        common.verifyString(FREJA_PROCEED_BUTTON, "PROCEED");
    }

    private void verifyLetterSectionEnglish() {
        log.info("Verify Letter labels - English");
        common.verifyString(LETTER_BUTTON_SPAN, "If you are registered at your Swedish address");
        common.verifyString(LETTER_BUTTON_H3, "BY POST");
        common.verifyString(LETTER_DESC_P1,
                "You will receive a letter which contains a code that for security reasons expires in two weeks.");
        common.verifyString(LETTER_PROCEED_BUTTON, "PROCEED");
    }

    private void verifyEidasSectionEnglish() {
        log.info("Verify eIDAS labels - English");
        common.verifyString(EU_BUTTON_H3, "EU citizen");
        common.verifyString(EU_BUTTON_SPAN, "With eIDAS electronic identification");
        common.verifyString(EU_DESC_P1,
                "If you have an electronic ID from a country connected to eIDAS, you can connect it to your eduID.");
        common.verifyString(EU_DESC_P2,
                "The button below will take you to an external site where you log in with your electronic ID to " +
                "connect your identity to eduID.");
        common.verifyString(EU_PROCEED_BUTTON, "PROCEED");
    }

    private void verifyWorldFrejaEnglish() {
        log.info("Verify Freja eID labels - English");
        common.verifyString(WORLD_BUTTON_H3, "Most countries");
        common.verifyString(WORLD_BUTTON_SPAN, "With Freja eID identity verification");
        common.verifyString(WORLD_DESC_P1,
                "If you have a Freja eID you can connect it to your eduID.");
        common.verifyString(WORLD_DESC_P2,
                "The button below will take you to an external identification site, where by identifying yourself " +
                "with Freja eID, you will verify your identity towards eduID.");
        //common.verifyXpathIsWorkingLink(WORLD_DESC_P1 + "/a");
        common.verifyString(WORLD_PROCEED_BUTTON, "PROCEED");
    }

    // -------------------------------------------------------------------------
    // Freja modal label verification
    // -------------------------------------------------------------------------

    public void verifyFrejaIdLabelsSwedish() {
        log.info("Verifying Freja eID pop-up labels — Swedish");
        common.verifyString(FREJA_MODAL_HEADING, "Med Freja-appen kan du skapa ett digitalt ID-kort");
        common.verifyString(FREJA_STEP_1, "Installera appen");
        common.verifyString(FREJA_STEP_2, "Skapa ett Freja+ konto (godkänd svensk e-legitimation)");
        common.verifyString(FREJA_STEP_3, "Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("Tips: Du kan hitta närmsta ombud i appen");
        common.verifyString(FREJA_STEP_5,
                "Freja+ är nu redo att användas med ditt eduID, fortsätt genom att klicka på knappen nedan");
        common.verifyStringOnPage("Vad är Freja+?");
        common.verifyString(FREJA_ACCEPT_BUTTON, "ANVÄND MITT FREJA+");
        common.click(common.findWebElement(FREJA_CLOSE_BUTTON));
    }

    public void verifyFrejaIdLabelsEnglish() {
        log.info("Verifying Freja eID pop-up labels — English");
        common.click(common.findWebElement(FREJA_PROCEED_BUTTON));

        common.verifyString(FREJA_MODAL_HEADING, "Use Freja+ and visit a local authorised agent");
        common.verifyString(FREJA_STEP_1, "Install the app");
        common.verifyString(FREJA_STEP_2,
                "Create a Freja+ account (awarded the \"Svensk e-legitimation\" quality mark)");
        common.verifyString(FREJA_STEP_3, "The app will generate a QR-code");
        common.verifyStringOnPage("Find a local authorised agent, show them a valid ID together with the QR-code and they will be able to verify your identity");
        common.verifyStringOnPage("Tip: Use the app to find your nearest agent");
        common.verifyString(FREJA_STEP_5,
                "Freja+ is now ready to be used with your eduID, proceed by clicking the button below");
        common.verifyStringOnPage("What is Freja+?");
        common.verifyString(FREJA_ACCEPT_BUTTON, "USE MY FREJA+");
    }

    // -------------------------------------------------------------------------
    // Letter pop-up label verification
    // -------------------------------------------------------------------------

    public void verifyMailTextsInPopUpSwedish() {
        log.info("Verifying letter pop-up labels — Swedish");
        common.findWebElement(LETTER_PROCEED_BUTTON).click();
        common.waitUntilVisible(LETTER_POPUP_HEADER);
        common.verifyString(LETTER_POPUP_HEADER, "Skriv in koden du fått hemskickad");
        common.verifyString(LETTER_POPUP_BODY,
                "Om du accepterar att få ett brev hem måste du skriva in koden här för att bevisa att " +
                "personnumret är ditt. Av säkerhetsskäl går koden ut om två veckor.");
        common.findWebElement(LETTER_CLOSE_BUTTON).click();
        log.info("Done verifying letter pop-up labels — Swedish");
    }

    public void verifyMailTextsInPopUpEnglish() {
        log.info("Verifying letter pop-up labels — English");
        common.findWebElement(LETTER_PROCEED_BUTTON).click();
        common.waitUntilVisible(LETTER_POPUP_HEADER);
        common.verifyString(LETTER_POPUP_HEADER, "Use a code sent by post to your address");
        common.verifyString(LETTER_POPUP_BODY,
                "The letter will contain a code that you enter here to verify your identity. The code sent to you " +
                "will expire in 2 weeks starting from now");
        common.click(common.findWebElement(LETTER_ACCEPT_BUTTON));
        log.info("Done verifying letter pop-up labels — English");
    }

    // -------------------------------------------------------------------------
    // Expand identity options
    // -------------------------------------------------------------------------

    public void expandIdentityOptions() {
        expandIfClosed(By.id("swedish"), By.id("swedish"));
        common.waitUntilClickable(BANKID_DETAILS_BUTTON);
        expandIfClosed(BANKID_DETAILS_BUTTON, BANKID_EXPAND_BUTTON);
        expandIfClosed(FREJA_DETAILS_BUTTON, FREJA_EXPAND_BUTTON);
        expandIfClosed(LETTER_DETAILS_BUTTON, LETTER_EXPAND_BUTTON);
        expandIfClosed(EU_DETAILS_BUTTON, EU_EXPAND_BUTTON);
        expandIfClosed(WORLD_DETAILS_BUTTON, WORLD_EXPAND_BUTTON);
    }

    /**
     * Clicks the button element to expand a collapsible section if it is not already open.
     * Some sections use the same ID for both the container and the toggle button, while others
     * use a separate "-button" suffixed ID for the toggle.
     */
    private void expandIfClosed(By locator, By buttonId) {
        if (common.findWebElement(locator).getDomAttribute("open") == null) {
            common.findWebElement(buttonId).click();
        }
    }
}
