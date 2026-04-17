package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.NameLocators.*;

/**
 * Page object for the Name & Display name section on the Identity page.
 */
public class Name {

    private final Common   common;
    private final TestData testData;

    public Name(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runName() {
        verifyOrUpdateName();
        verifyLabels();
    }

    // -------------------------------------------------------------------------
    // Name verification / update
    // -------------------------------------------------------------------------

    private void verifyOrUpdateName() {
        boolean namesMatch =
                testData.getGivenName().equalsIgnoreCase(common.findWebElement(FIRST_NAME_DISPLAY).getText()) &&
                testData.getSurName().equalsIgnoreCase(common.findWebElement(LAST_NAME_DISPLAY).getText());

        if (namesMatch || testData.isIdentityConfirmed()) {
            verifyCurrentNames();
        } else {
            common.click(common.findWebElement(CHANGE_BUTTON));
            updatePersonalInfo();
        }
    }

    private void verifyCurrentNames() {
        String method = testData.getConfirmIdBy();
        if (method != null && method.equalsIgnoreCase("mail")) {
            common.verifyString(FIRST_NAME_DISPLAY, "Magic Cookie");
            common.verifyString(LAST_NAME_DISPLAY, "Testsson");
            common.verifyString(DISPLAY_NAME_DISPLAY, "Cookie Testsson");
            testData.setDisplayName("Cookie Testsson");
        } else if (method != null && method.equalsIgnoreCase("eidas")) {
            common.verifyString(FIRST_NAME_DISPLAY, "Bernt Olof");
            common.verifyString(LAST_NAME_DISPLAY, "Larsson");
            common.verifyString(DISPLAY_NAME_DISPLAY, "Bernt Olof Larsson");
        } else {
            common.verifyString(FIRST_NAME_DISPLAY, testData.getGivenName());
            common.verifyString(LAST_NAME_DISPLAY, testData.getSurName());
            if (testData.isIdentityConfirmed()) {
                common.verifyString(DISPLAY_NAME_DISPLAY, testData.getDisplayName());
            }
        }
    }

    private void updatePersonalInfo() {
        Common.log.info("Updating name to: {} {}", testData.getGivenName(), testData.getSurName());

        verifyNameFormLabelsSwedish();

        common.selectEnglish();
        clickChangeOrAdd();
        verifyNameFormLabelsEnglish();

        common.selectSwedish();
        clickChangeOrAdd();

        // Double-fill workaround — clearTextField + sendKeys needs two passes for reliable input
        fillField(GIVEN_NAME_INPUT, testData.getGivenName());
        common.verifyStrings(testData.getGivenName(), common.getAttribute(GIVEN_NAME_INPUT));

        fillField(SURNAME_INPUT, testData.getSurName());
        common.verifyStrings(testData.getSurName(), common.getAttribute(SURNAME_INPUT));

        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen om du verifierar ditt eduID med ditt personummer.");

        if (testData.isRegisterAccount()) {
            common.click(common.findWebElementById("Svenska"));
        }

        common.click(common.waitUntilClickable(SAVE_BUTTON));
        common.timeoutMilliSeconds(500);
        Common.log.info("Saved updated name");
    }

    /** Fills a text field twice — known workaround for intermittent input issues in this form. */
    private void fillField(By locator, String value) {
        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElement(locator));
        common.findWebElement(locator).sendKeys(value);
        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElement(locator));
        common.findWebElement(locator).sendKeys(value);
    }

    private void clickChangeOrAdd() {
        if (testData.isRegisterAccount()) {
            common.click(common.findWebElement(ADD_PERSONAL_DATA));
        } else {
            common.click(common.findWebElement(CHANGE_BUTTON));
        }
    }

    private void verifyNameFormLabelsSwedish() {
        common.verifyPlaceholderBy("Förnamn", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("Efternamn", SURNAME_INPUT);
        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen om du verifierar ditt eduID med ditt personummer.");
    }

    private void verifyNameFormLabelsEnglish() {
        common.verifyPlaceholderBy("First name", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("Last name", SURNAME_INPUT);
        common.verifyStringOnPage("First and last name will be replaced with your legal name if you verify your eduID with your personal id number.");
    }

    // -------------------------------------------------------------------------
    // Page label verification
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        String language = testData.getLanguage();
        if (language == null || language.equals("Svenska")) {
            verifyLabelsSwedish();
        } else {
            verifyLabelsEnglish();
        }
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verifying name labels in Swedish");
        common.waitUntilPageTitleContains("Identitet | eduID");

        String pageBody = common.getPageBody();
        common.verifyPageBodyContainsString(pageBody, "Namn & visningsnamn");
        common.verifyPageBodyContainsString(pageBody,
                "Den här informationen kan komma att användas för att anpassa tjänster som du når med ditt eduID.");
        common.verifyPageBodyContainsString(pageBody, "Förnamn");
        common.verifyPageBodyContainsString(pageBody, "Efternamn");
        if (testData.isIdentityConfirmed()) {
            common.verifyString(DISPLAY_NAME_LABEL, "Visningsnamn");
        }
    }

    private void verifyLabelsEnglish() {
        Common.log.info("Verifying name labels in English");
        common.waitUntilPageTitleContains("Identity | eduID");

        String pageBody = common.getPageBody();
        common.verifyPageBodyContainsString(pageBody, "Names & display Name");
        common.verifyPageBodyContainsString(pageBody,
                "This information may be used to personalise services that you access with your eduID.");
        common.verifyPageBodyContainsString(pageBody, "First name");
        common.verifyPageBodyContainsString(pageBody, "Last name");
        if (testData.isIdentityConfirmed()) {
            common.verifyString(DISPLAY_NAME_LABEL, "Display name");
        }
    }
}
