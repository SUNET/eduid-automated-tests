package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.resetPassword.PasswordChangedLocators.*;

/**
 * Page object för sidan "Återställ lösenord: Slutförd".
 * Verifierar att lösenordsåterställningen visas korrekt i båda språken
 * och navigerar sedan tillbaka till inloggningssidan.
 */
public class PasswordChanged {

    private final Common   common;
    private final TestData testData;

    public PasswordChanged(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runPasswordChanged() {
        verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
    }

    // -------------------------------------------------------------------------
    // Navigering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord | eduID");
    }

    private void clickReturnToLoginLink() {
        common.click(common.findWebElement(FINISHED_BUTTON));
        Common.log.info("Clicked the Go To eduID login link");
        common.waitUntilPageTitleContains("Logga in | eduID");
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        common.waitUntilClickable(FINISHED_BUTTON);

        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verifying text and labels — Swedish");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Slutförd");
        common.verifyStringOnPage("Ditt lösenord är nu uppdaterat. Se till att förvara ditt lösenord på " +
                "ett säkert sätt för framtida användning. När du har loggat in är det möjligt att ändra ditt lösenord.");
        common.verifyPageBodyContainsString(pageBody, "E-postadress");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());

        if (testData.isUseRecommendedPw()) {
            common.verifyPageBodyContainsString(pageBody, "Lösenord");
            common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
        }

        common.verifyString(FINISHED_BUTTON, "Gå till eduID för att logga in");
    }

    private void verifyLabelsEnglish() {
        Common.log.info("Verifying text and labels — English");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody,"Reset password: Completed");
        common.verifyPageBodyContainsString(pageBody,"You have successfully updated your password. Make sure to store " +
                "your password securely for future use. Once you've logged in it is possible to change your password.");
        common.verifyPageBodyContainsString(pageBody,"Email address");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());

        if (testData.isUseRecommendedPw()) {
            common.verifyPageBodyContainsString(pageBody, "Password");
            common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
        }

        common.verifyString(FINISHED_BUTTON, "Go to eduID to login");
    }
}
