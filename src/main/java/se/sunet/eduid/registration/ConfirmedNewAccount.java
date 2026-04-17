package se.sunet.eduid.registration;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.registration.ConfirmedNewAccountLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object för registreringssidans slutsida "Skapa eduID: Slutfört".
 * Verifierar inloggningsuppgifter och navigerar till eduID-startsidan.
 */
public class ConfirmedNewAccount {

    private final Common   common;
    private final TestData testData;

    public ConfirmedNewAccount(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runConfirmedNewAccount() {
        verifyPageTitle();
        verifyLabels();
        clickGoToMyEduID();
    }

    // -------------------------------------------------------------------------
    // Navigering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Registrera | eduID");
        common.waitUntilClickable(FINISHED_BUTTON);
    }

    private void clickGoToMyEduID() {
        common.click(common.findWebElement(FINISHED_BUTTON));
        common.timeoutSeconds(1);
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
        log.info("Verifying new account labels — Swedish");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Slutfört");
        common.verifyPageBodyContainsString(pageBody, "Här är dina inloggningsuppgifter för eduID. Kom ihåg eller spara lösenordet på " +
                "ett säkert sätt! Obs: mellanrummen i lösenordet är för att göra det mer läsbart och tas automatiskt " +
                "bort vid inmatning. Du kan efter att du har loggat in välja att byta lösenord.");
        common.verifyPageBodyContainsString(pageBody,  "E-postadress");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());
        common.verifyString(FINISHED_BUTTON, "Gå till eduID för att logga in");

        if (testData.isUseRecommendedPw()) {
            common.verifyPageBodyContainsString(pageBody, "Lösenord");
            common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
        }
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying new account labels — English");
        common.verifyPageTitle("Register | eduID");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Completed");
        common.verifyPageBodyContainsString(pageBody, "These are your login details for eduID. Remember or save the password securely! " +
                "Note: spaces in the password are there for legibility and will be removed automatically if entered. " +
                "Once you've logged in it is possible to change your password.");
        common.verifyPageBodyContainsString(pageBody, "Email address");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());
        common.verifyString(FINISHED_BUTTON, "Go to eduID to login");

        if (testData.isUseRecommendedPw()) {
            common.verifyPageBodyContainsString(pageBody, "Password");
            common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
        }
    }
}
