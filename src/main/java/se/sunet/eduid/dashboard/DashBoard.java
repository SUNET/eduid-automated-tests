package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.DashBoardLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object för eduID Start / Dashboard-sidan.
 */
public class DashBoard {

    private final Common   common;
    private final TestData testData;

    public DashBoard(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runDashBoard() {
        if (!common.getWebDriver().getCurrentUrl().equalsIgnoreCase(testData.getBaseUrl() + "/profile/")) {
            common.navigateToDashboard();
        }
        verifyPageTitle();
        verifyUserId();

        if (testData.getLanguage().equalsIgnoreCase("English")) {
            verifyLabelsEnglish();
        } else {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }
    }

    // -------------------------------------------------------------------------
    // Verifiering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Start | eduID");
    }

    private void verifyUserId() {
        common.verifyString(USERNAME_DISPLAY, testData.getUsername().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        // Säkerställ att svenska är valt innan verifiering börjar
        common.selectSwedish();
        log.info("Verifying dashboard labels in Swedish");

        common.verifyPageTitle("Start | eduID");
        String pageBody = common.getPageBody();

        if (testData.getTestClassName().equalsIgnoreCase("TC_1")) {
            verifyMenuLabelsSwe();
        }

        verifyWelcomeHeading(pageBody, "Välkommen, ");

        common.verifyString(EPPN_LABEL, "Unikt ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElement(USER_EPPN).getDomAttribute("value"));

        common.verifyPageBodyContainsString(pageBody, "eduID statusöversikt");
        common.verifyPageBodyContainsString(pageBody,
                "Säkerheten och användbarheten av ditt eduID kan förbättras genom nedanstående åtgärder.\n" +
                "Förslag på vad som kan vara lämpligt beroende av organisationen du använder ditt eduID för, " +
                "kan hittas under avsnittet för Tillitsnivåer under Hjälp.");
        common.verifyPageBodyContainsString(pageBody, "Status på slutförda åtgärder markeras med en bockmarkering.");
        common.verifyLocatorIsWorkingLink(HELP_LINK);

        verifyAccountStatusSwedish(pageBody);
        verifyIdentityStatusSwedish(pageBody);
        verifySecurityStatusSwedish(pageBody);
        verifySecurityKeyStatusSwedish(pageBody);

        common.verifyPageBodyContainsString(pageBody,
                "Obs: ytterligare inställningar för språk, e-postadresser, lösenordshantering samt " +
                "länkning till ORCID och ESI kan hanteras under Konto.");
        common.verifyLocatorIsWorkingLink(ACCOUNT_LINK);
    }

    private void verifyLabelsEnglish() {
        if (!testData.getTestClassName().equalsIgnoreCase("TC_4")) {
            common.selectEnglish();
        }

        log.info("Verifying dashboard labels in English");
        common.verifyPageTitle("Start | eduID");
        String pageBody = common.getPageBody();

        if (testData.getTestClassName().equalsIgnoreCase("TC_1")) {
            verifyMenuLabelsEng();
        }

        verifyWelcomeHeading(pageBody, "Welcome, ");

        common.verifyString(EPPN_LABEL, "Unique ID:");
        if (testData.isRegisterAccount()) {
            Assert.assertEquals(
                    common.findWebElement(USER_EPPN).getDomAttribute("value").length(),
                    11, "EPPN seems to be missing or not correct length");
        } else {
            common.verifyStrings(testData.getEppn(), common.findWebElement(USER_EPPN).getDomAttribute("value"));
        }

        common.verifyPageBodyContainsString(pageBody, "eduID status overview");
        common.verifyPageBodyContainsString(pageBody,
                "The strength and usage of your eduID can be improved by following the steps listed below.\n" +
                "Suggestions on what might be required depending on the organisation you are accessing with " +
                "your eduID, can be found in the Assurance levels section in Help.");
        common.verifyPageBodyContainsString(pageBody, "Status of completed steps are indicated with a checkmark.");
        common.verifyLocatorIsWorkingLink(HELP_LINK);

        verifyAccountStatusEnglish(pageBody);
        verifyIdentityStatusEnglish(pageBody);
        verifySecurityStatusEnglish(pageBody);
        verifySecurityKeyStatusEnglish(pageBody);

        common.verifyPageBodyContainsString(pageBody,
                "Note: additional settings such as language, email addresses, password management as well as " +
                "ORCID and ESI affiliation can be edited at Account.");
        common.verifyLocatorIsWorkingLink(ACCOUNT_LINK);

        if (!testData.getTestClassName().equalsIgnoreCase("TC_4")) {
            common.selectSwedish();
        }
    }

    // -------------------------------------------------------------------------
    // Välkomenrubrik — undviker duplikering av displayName/e-post-logik
    // -------------------------------------------------------------------------

    private void verifyWelcomeHeading(String pageBody, String prefix) {
        if (testData.getDisplayName().isEmpty()) {
            common.verifyPageBodyContainsString(pageBody, prefix + testData.getEmail().toLowerCase() + "!");
        } else {
            common.verifyStrings(
                    common.findWebElement(WELCOME_HEADING).getText(),
                    prefix + testData.getDisplayName() + "!");
        }
    }

    // -------------------------------------------------------------------------
    // Status-sektionshjälpare — Svenska
    // -------------------------------------------------------------------------

    private void verifyAccountStatusSwedish(String pageBody) {
        if (testData.isAccountVerified()) {
            common.verifyPageBodyContainsString(pageBody, "Bekräftat konto");
            common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        } else {
            log.info("TODO — konto är inte verifierat");
        }
    }

    private void verifyIdentityStatusSwedish(String pageBody) {
        if (testData.isIdentityConfirmed()) {
            common.verifyPageBodyContainsString(pageBody, "Verifierad identitet");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din verifierade identitet under Identitet");
        } else {
            common.verifyPageBodyContainsString(pageBody, "Verifiera din identitet");
            common.verifyPageBodyContainsString(pageBody, "Koppla din identitet till eduID under Identitet");
        }
        common.verifyLocatorIsWorkingLink(IDENTITY_SECTION_LINK);
    }

    private void verifySecurityStatusSwedish(String pageBody) {
        if (testData.isAddExternalSecurityKey() || testData.isAddInternalPassKey()) {
            common.verifyPageBodyContainsString(pageBody, "Ökad säkerhet");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din tillagda multifaktorautentisering under Säkerhet");
            if (!testData.isIdentityConfirmed()) {
                common.verifyPageBodyContainsString(pageBody,
                        "Det rekommenderas starkt att lägga till mer än en säkerhetsnyckel eller " +
                        "passkey/lösennyckel för att försäkra dig om att du kan logga in även om en förloras.");
            }
        } else {
            common.verifyPageBodyContainsString(pageBody, "Öka säkerheten");
            common.verifyPageBodyContainsString(pageBody, "Lägg till multifaktorautentisering under Säkerhet");
        }
        common.verifyLocatorIsWorkingLink(SECURITY_SECTION_LINK);
    }

    private void verifySecurityKeyStatusSwedish(String pageBody) {
        if (testData.isVerifySecurityKeyByFreja() || testData.isVerifySecurityKeyByEidas()) {
            common.verifyPageBodyContainsString(pageBody, "Verifierad säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din verifierade multifaktorautentisering under Säkerhet");
        } else {
            common.verifyPageBodyContainsString(pageBody, "Verifiera din säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Verifiera din säkerhetsnyckel under Säkerhet");
        }
        common.verifyLocatorIsWorkingLink(SEC_KEY_SECTION_LINK);
    }

    // -------------------------------------------------------------------------
    // Status-sektionshjälpare — Engelska
    // -------------------------------------------------------------------------

    private void verifyAccountStatusEnglish(String pageBody) {
        if (testData.isAccountVerified()) {
            common.verifyPageBodyContainsString(pageBody, "Confirmed account");
            common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        } else {
            log.info("TODO — account is not verified");
        }
    }

    private void verifyIdentityStatusEnglish(String pageBody) {
        if (testData.isIdentityConfirmed()) {
            log.info("Identity is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified identity");
            common.verifyPageBodyContainsString(pageBody, "Read more details about your verified identity at Identity");
        } else {
            common.verifyPageBodyContainsString(pageBody, "Verify your identity");
            common.verifyPageBodyContainsString(pageBody, "Connect your identity to eduID at Identity");
        }
        common.verifyLocatorIsWorkingLink(IDENTITY_SECTION_LINK);
    }

    private void verifySecurityStatusEnglish(String pageBody) {
        if (testData.isAddExternalSecurityKey() || testData.isAddInternalPassKey()) {
            log.info("Security key is added");
            common.verifyPageBodyContainsString(pageBody, "Enhanced security");
            common.verifyPageBodyContainsString(pageBody, "Read more about your added multi-factor authentication at Security");
            if (!testData.isIdentityConfirmed()) {
                common.verifyPageBodyContainsString(pageBody,
                        "It is strongly recommended to add more than one security key or passkey to ensure " +
                        "you can still sign in to your account if one is lost.");
            }
        } else {
            common.verifyPageBodyContainsString(pageBody, "Enhance security");
            common.verifyPageBodyContainsString(pageBody, "Add multi-factor authentication at");
        }
        common.verifyLocatorIsWorkingLink(SECURITY_SECTION_LINK);
    }

    private void verifySecurityKeyStatusEnglish(String pageBody) {
        if (testData.isVerifySecurityKeyByFreja() || testData.isVerifySecurityKeyByEidas()) {
            log.info("Security key is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified security key");
            common.verifyPageBodyContainsString(pageBody, "Read more details about your verified multi-factor authentication at Security");
        } else {
            common.verifyPageBodyContainsString(pageBody, "Verify your security key");
            common.verifyPageBodyContainsString(pageBody, "Verify your security key at Security");
        }
        common.verifyLocatorIsWorkingLink(SEC_KEY_SECTION_LINK);
    }

    // -------------------------------------------------------------------------
    // Menyetikettverifiering
    // -------------------------------------------------------------------------

    void verifyMenuLabelsSwe() {
        log.info("Verifying menu labels in Swedish and checking sub-menu links");
        common.expandNavigationMenu();
        common.timeoutMilliSeconds(100);

        expandMenuSection(NAV_START_CHEVRON, NAV_START_EXPAND);
        common.verifyString(NAV_START_LABEL, "Start");
        common.verifyString(MENU_STATUS_OVERVIEW, "eduID statusöversikt");
        common.verifyLocatorIsWorkingLink(MENU_STATUS_OVERVIEW);

        expandMenuSection(NAV_IDENTITY_CHEVRON, NAV_IDENTITY_EXPAND);
        common.verifyString(NAV_IDENTITY_LABEL, "Identitet");
        common.verifyString(MENU_VERIFY_IDENTITY, "Verifiera identitet");
        common.verifyLocatorIsWorkingLink(MENU_VERIFY_IDENTITY);
        common.verifyString(MENU_NAME, "Namn & visningsnamn");
        common.verifyLocatorIsWorkingLink(MENU_NAME);

        expandMenuSection(NAV_SECURITY_CHEVRON, NAV_SECURITY_EXPAND);
        common.verifyString(NAV_SECURITY_LABEL, "Säkerhet");
        common.verifyString(MENU_MFA, "Lägg till multifaktorautentisering (MFA)");
        common.verifyLocatorIsWorkingLink(MENU_MFA);
        common.verifyString(MENU_SECURITY_KEYS, "Hantera dina säkerhetsnycklar");
        common.verifyLocatorIsWorkingLink(MENU_SECURITY_KEYS);

        expandMenuSection(NAV_ACCOUNT_CHEVRON, NAV_ACCOUNT_EXPAND);
        common.verifyString(NAV_ACCOUNT_LABEL, "Konto");
        common.verifyString(MENU_UNIQUE_ID, "Unikt ID");
        common.verifyLocatorIsWorkingLink(MENU_UNIQUE_ID);
        common.verifyString(MENU_EMAIL, "E-postadresser");
        common.verifyLocatorIsWorkingLink(MENU_EMAIL);
        common.verifyString(MENU_LANGUAGE, "Språk");
        common.verifyLocatorIsWorkingLink(MENU_LANGUAGE);
        common.verifyString(MENU_CHANGE_PASSWORD, "Byt lösenord");
        common.verifyLocatorIsWorkingLink(MENU_CHANGE_PASSWORD);
        common.verifyString(MENU_ORCID, "Länka till ditt ORCID konto");
        common.verifyLocatorIsWorkingLink(MENU_ORCID);
        common.verifyString(MENU_ESI, "ESI information");
        common.verifyLocatorIsWorkingLink(MENU_ESI);
        common.verifyString(MENU_DELETE_ACCOUNT, "Spärra och radera eduID");
        common.verifyLocatorIsWorkingLink(MENU_DELETE_ACCOUNT);

        common.verifyString(LOGOUT_BUTTON, "LOGGA UT");
    }

    void verifyMenuLabelsEng() {
        log.info("Verifying menu labels in English");
        common.expandNavigationMenu();

        expandMenuSection(NAV_START_CHEVRON, NAV_START_EXPAND);
        common.verifyString(NAV_START_LABEL, "Start");
        common.verifyString(MENU_STATUS_OVERVIEW, "eduID status overview");

        expandMenuSection(NAV_IDENTITY_CHEVRON, NAV_IDENTITY_EXPAND);
        common.verifyString(NAV_IDENTITY_LABEL, "Identity");
        common.verifyString(MENU_VERIFY_IDENTITY, "Verify identity");
        common.verifyString(MENU_NAME, "Names & Display Name");

        expandMenuSection(NAV_SECURITY_CHEVRON, NAV_SECURITY_EXPAND);
        common.verifyString(NAV_SECURITY_LABEL, "Security");
        common.verifyString(MENU_MFA, "Add multi-factor Authentication (MFA)");
        common.verifyString(MENU_SECURITY_KEYS, "Manage your security keys");

        expandMenuSection(NAV_ACCOUNT_CHEVRON, NAV_ACCOUNT_EXPAND);
        common.verifyString(NAV_ACCOUNT_LABEL, "Account");
        common.verifyString(MENU_UNIQUE_ID, "Unique ID");
        common.verifyString(MENU_EMAIL, "Email addresses");
        common.verifyString(MENU_LANGUAGE, "Language");
        common.verifyString(MENU_CHANGE_PASSWORD, "Change password");
        common.verifyString(MENU_ORCID, "ORCID account");
        common.verifyString(MENU_ESI, "ESI information");
        common.verifyString(MENU_DELETE_ACCOUNT, "Block and delete eduID");

        common.verifyString(LOGOUT_BUTTON, "LOG OUT");
    }

    /**
     * Expanderar ett navigationsavsnitt om chevron-ikonen indikerar att det är hopfällt.
     */
    private void expandMenuSection(By locator, By buttonLocator) {
        String icon = common.getWebDriver()
                .findElement(locator)
                .getDomAttribute("data-icon");
        if ("chevron-down".equals(icon)) {
            common.findWebElement(buttonLocator).click();
        }
    }
}
