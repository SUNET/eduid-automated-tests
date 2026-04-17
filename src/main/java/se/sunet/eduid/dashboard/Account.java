package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.util.List;

import static se.sunet.eduid.dashboard.AccountLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object for the Account page.
 */
public class Account {

    private final Common   common;
    private final TestData testData;

    public Account(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runAccount() {
        common.navigateToAccount();

        if (testData.getLanguage() != null) {
            selectLanguage();
        } else {
            verifyPageTitle();
            verifyLabels();
        }

        if (testData.getTestClassName().contains("TC_1")) {
            pressLadok();
            pressOrcid();
        }
    }

    // -------------------------------------------------------------------------
    // Navigation & setup
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Konto | eduID");
        // Ensure Swedish is active if the language selector shows "Svenska" as the switch option
        if (common.findWebElement(LANGUAGE_SELECTOR).getText().contains("Svenska")) {
            common.selectSwedish();
        }
    }

    // -------------------------------------------------------------------------
    // Ladok / ESI
    // -------------------------------------------------------------------------

    private void pressLadok() {
        log.info("Verifying Ladok/ESI");
        common.scrollToPageBottom();
        common.findWebElement(LADOK_TOGGLE).click();

        verifyLadokLabelsSwedish();
        common.selectEnglish();
        verifyLadokLabelsEnglish();

        selectInstitutionAndVerify();
        common.closeStatusMessage();
    }

    private void verifyLadokLabelsSwedish() {
        log.info("Verify Ladok/ESI labels in Swedish");
        common.waitUntilVisible(LADOK_SELECT_LABEL);
        common.verifyString(LADOK_SELECT_LABEL, "Välj lärosäte");
        common.verifyString(LADOK_DROPDOWN_HEADING, "Tillgängliga lärosäten");
        verifyInstitutionListNotEmpty();
    }

    private void verifyLadokLabelsEnglish() {
        log.info("Verify Ladok/ESI labels in English");
        common.click(common.findWebElement(LADOK_ACTIVATE_BUTTON));
        common.waitUntilVisible(LADOK_SELECT_LABEL);
        common.verifyString(LADOK_SELECT_LABEL, "Select higher education institution");
        common.verifyString(LADOK_DROPDOWN_HEADING, "Available higher education institutions");
    }

    private void verifyInstitutionListNotEmpty() {
        common.findWebElement(LADOK_DROPDOWN_HEADING).click();
        WebElement institutionList = common.findWebElement(LADOK_DROPDOWN_LIST);
        List<WebElement> rows = institutionList.findElements(By.xpath("*"));
        Assert.assertTrue(rows.size() > 1,
                "Too few institutions in dropdown (found: " + rows.size() + ", expected >1)");
    }

    private void selectInstitutionAndVerify() {
        common.scrollToPageBottom();
        common.findWebElement(LADOK_DROPDOWN_HEADING).click();
        common.click(common.findWebElementByXpathContainingText("Linnaeus"));
        verifyLadokStatusMessage();
    }

    private void verifyLadokStatusMessage() {
        if (testData.isIdentityConfirmed()) {
            common.verifyStatusMessage("No information for you found in Ladok with that higher education institution.");
            common.selectSwedish();
            common.verifyStatusMessage("Ingen information om dig hittades i Ladok för detta lärosäte.");
        } else {
            common.verifyStatusMessage("You need a verified Swedish national identity number to link your account with ESI.");
            common.selectSwedish();
            common.verifyStatusMessage("Du behöver verifiera ditt svenska personnummer för att länka ditt konto med ditt ESI.");
        }
    }

    // -------------------------------------------------------------------------
    // ORCID
    // -------------------------------------------------------------------------

    private void pressOrcid() {
        log.info("Verifying ORCID");
        common.waitUntilClickable(CONNECT_ORCID_BUTTON);
        common.click(common.findWebElement(CONNECT_ORCID_BUTTON));
        log.info("Clicked on ORCID button");

        common.timeoutSeconds(2);
        String title = common.getWebDriver().getTitle();
        if (title.equalsIgnoreCase("ORCID") || title.equalsIgnoreCase("Sign in - ORCID")) {
            log.info("ORCID page present");
        } else {
            log.info("ORCID page NOT present — title is: {}", title);
            Assert.fail("ORCID page NOT present — title is: " + title);
        }

        common.getWebDriver().navigate().back();
        common.waitUntilPageTitleContains("Konto | eduID");
    }

    // -------------------------------------------------------------------------
    // Label verification
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        log.info("Verifying Account page labels — Swedish");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Konto");
        common.verifyPageBodyContainsString(pageBody,
                "Uppdatera dina eduID-kontoinställningar, byt lösenord eller ta bort ditt eduID.");
        common.verifyPageBodyContainsString(pageBody, "Unikt ID");
        common.verifyPageBodyContainsString(pageBody,
                "Detta unika ID är ett användarnamn för ditt eduID som du kan behöva ange för att identifiera ditt " +
                "konto eller vid teknisk support. Det är en del av vad som kan hänvisas till som EPPN.");
        common.verifyString(EPPN_LABEL, "Unikt ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElement(USER_EPPN).getDomAttribute("value"));
        common.verifyString(EPPN_COPY_BUTTON, "KOPIERA");
        common.verifyPageBodyContainsString(pageBody, "Länka till ditt ORCID konto");
        common.verifyPageBodyContainsString(pageBody,
                "Om du är forskare med ett ORCID iD kan du dela det med ditt eduID.");
        common.verifyPageBodyContainsString(pageBody,
                "ORCID ger en beständig identifierare, ett ORCID iD, som unikt särskiljer dig från andra forskare " +
                "och en mekanism för att koppla dina forskningsresultat och aktiviteter till ditt ORCID iD oberoende " +
                "vilken organisation du är verksam vid.");
        common.verifyPageBodyContainsString(pageBody, "ESI information");
        common.verifyPageBodyContainsString(pageBody,
                "Vissa lärosäten har anslutit sig till att låta eduID hämta ut ditt ESI - European Student Identifier " +
                "från Ladok för att kunna få tillgång till vissa tjänster.");
        common.verifyPageBodyContainsString(pageBody, "Länka ditt ESI till ditt eduID-konto");
        common.verifyPageBodyContainsString(pageBody,
                "Finns ditt lärosäte inte i listan ska du inte använda eduID för att logga in i tjänster som kräver " +
                "ESI. Kontakta då istället ditt lärosäte för mer information.");
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying Account page labels — English");
        common.verifyPageTitle("Account | eduID");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Account");
        common.verifyPageBodyContainsString(pageBody,
                "Update your eduID account settings, change password or delete your eduID.");
        common.verifyPageBodyContainsString(pageBody, "Unique ID");
        common.verifyPageBodyContainsString(pageBody,
                "This identifier is a username for your eduID that you may need to provide when accessing other " +
                "services or requesting support. It is part of what may be referred to as EPPN.");
        common.verifyString(EPPN_LABEL, "Unique ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElement(USER_EPPN).getDomAttribute("value"));
        common.verifyString(EPPN_COPY_BUTTON, "COPY");
        common.verifyPageBodyContainsString(pageBody, "ORCID account");
        common.verifyPageBodyContainsString(pageBody,
                "If you are a researcher with an ORCID iD you can share it with your eduID.");
        common.verifyPageBodyContainsString(pageBody,
                "ORCID iD distinguishes you from other researchers and allows linking of your research outputs and " +
                "activities to your identity, regardless of the organisation you are working with.");
        common.verifyPageBodyContainsString(pageBody, "ESI information");
        common.verifyPageBodyContainsString(pageBody,
                "Some higher education institutions allow eduID to fetch your ESI - European Student identifier from " +
                "Ladok, which might give you access to certain services.");
        common.verifyPageBodyContainsString(pageBody, "Link your ESI with your eduID account");
        common.verifyPageBodyContainsString(pageBody,
                "If your institution is not in the list you cannot use your eduID to access services requiring ESI, " +
                "contact your institution for more information.");
    }

    // -------------------------------------------------------------------------
    // Language selection
    // -------------------------------------------------------------------------

    private void selectLanguage() {
        log.info("Setting application language to: {}", testData.getLanguage());
        if (testData.getLanguage().equalsIgnoreCase("Svenska")) {
            common.verifyString(LANG_SWEDISH_LABEL, "Svenska");
            common.click(common.findWebElement(LANG_SWEDISH_LABEL));
            common.timeoutSeconds(1);
        } else if (testData.getLanguage().equalsIgnoreCase("English")) {
            common.verifyString(LANG_ENGLISH_LABEL, "English");
            common.click(common.findWebElement(LANG_ENGLISH_LABEL));
            common.timeoutSeconds(1);
        }
    }
}
