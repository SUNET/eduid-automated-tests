package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.generic.StartPageLocators.*;

/**
 * Page object for the eduID start/splash page.
 *
 * Responsibilities:
 *  - Wait for the page to be ready.
 *  - Verify page title, footer, and all visible labels in both languages.
 *  - Navigate to either registration or login based on the test scenario.
 */
public class StartPage {

    private final Common   common;
    private final TestData testData;

    public StartPage(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runStartPage() {
        common.waitUntilClickable(SIGN_UP_BUTTON);
        verifyPageTitle();
        verifyLabels();

        if (testData.isRegisterAccount()) {
            registerAccount();
        } else {
            signIn();
        }
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    private void signIn() {
        common.click(common.waitUntilClickable(LOGIN_BUTTON));
        common.waitUntilPageTitleContains("Logga in | eduID");
    }

    private void registerAccount() {
        common.click(common.findWebElement(SIGN_UP_BUTTON));
    }

    // -------------------------------------------------------------------------
    // Page verification
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("eduID");
        String currentYear = common.getDate().toString().substring(0, 4);
        common.verifyStringOnPage("©2013-" + currentYear);
    }

    /**
     * Verifies labels in Swedish first, then switches to English and verifies,
     * then restores Swedish — consistent with the pattern used in Login.
     */
    private void verifyLabels() {
        common.selectSwedish();
        verifyLabelsSwedish();

        common.selectEnglish();

        verifyLabelsEnglish();
        common.selectSwedish();
    }

    public void verifyLabelsSwedish() {
        common.verifyString(H1_HEADLINE,
                "Säkrare och enklare inloggning med eduID");
        common.verifyString(P_CREATE_EDU,
                "Skapa ett eduID och koppla det till din identitet för att få tillgång till tjänster " +
                "och organisationer som är relaterade till högre utbildning.");
        common.verifyString(P_EDU_EASIER,
                "eduID är enklare eftersom du bara har en inloggning och säkrare eftersom det är " +
                "kopplat till en verklig person - dig.");
        common.verifyString(P_READ_MORE,
                "Du kan läsa mer om eduID på Sunets webbplats och i vårt hjälpinnehåll, alltid " +
                "tillgängligt i sidfoten. Registrera dig eller logga in med knapparna nedan!");

        verifyStepsSwedish();
        verifyLinks();
    }

    private void verifyLabelsEnglish() {
        common.verifyString(H1_HEADLINE,
                "Safer and easier login with eduID");
        common.verifyString(P_CREATE_EDU,
                "Create an eduID and connect it to your identity for access to services and " +
                "organisations related to higher education.");
        common.verifyString(P_EDU_EASIER,
                "eduID is easier because you only have one login and safer because it's connected to " +
                "a real individual - you.");
        common.verifyString(P_READ_MORE,
                "You can read more about eduID at Sunet's website and in our help content, always" +
                " accessible from the footer. Register or log in using the buttons below!");

        verifyStepsEnglish();
        verifyLinks();
    }

    private void verifyStepsSwedish() {
        common.waitUntilPresence(STEP_1);
        common.verifyString(STEP_1, "Skapa ett grundläggande konto med din e-postadress.");
        common.verifyString(STEP_2, "Bevisa att du är DU.");
        common.verifyString(STEP_3, "Höj din inloggningssäkerhet.");
        common.verifyString(STEP_4, "Höj nivån igen - bevisa att DU loggar in.");
    }

    private void verifyStepsEnglish() {
        common.verifyString(STEP_1, "Create a basic account with your email address.");
        common.verifyString(STEP_2, "Prove that you are YOU.");
        common.verifyString(STEP_3, "Level up your login security.");
        common.verifyString(STEP_4, "Level up again - proving that YOU are logging in.");
    }

    /**
     * Link targets don't change between languages — verified once per language pass
     * so any breakage is caught regardless of which language is active.
     */
    private void verifyLinks() {
        common.verifyLocatorIsWorkingLink(LINK_SUNET);
        common.verifyLocatorIsWorkingLink(LINK_HELP);
        common.verifyLocatorIsWorkingLink(LINK_SUNET_IN_TEXT);
        common.verifyLocatorIsWorkingLink(LINK_HELP_IN_TEXT);
    }
}
