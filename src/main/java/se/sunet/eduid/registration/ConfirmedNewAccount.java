package se.sunet.eduid.registration;

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

        if(testData.isAddInternalPassKey()){
            common.verifyPageBodyContainsString(pageBody,"Ditt eduID-konto har skapats och du kan logga in " +
                    "med dina tillagda uppgifter.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Ditt eduID-konto har skapats och du kan logga in" +
                    " med dina tillagda uppgifter.");

            if (testData.isUseRecommendedPw()) {
                common.verifyPageBodyContainsString(pageBody, "Lösenord");
                common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
            }
        }

        common.verifyPageBodyContainsString(pageBody,  "E-postadress");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());
        if(testData.isSwamidSp()){
            common.verifyString(FINISHED_BUTTON, "Fortsätt till SWAMID Entity Category Release Check");
        }
        else {
            common.verifyString(FINISHED_BUTTON, "Gå till eduID för att logga in");
        }

        common.verifyPageBodyContainsString(pageBody, "Obs: Logga in i eduID.se närsomhelst för att" +
                " hantera dina kontoinställningar, t.ex. lägga till nycklar, byta lösenord, uppdatera namn och " +
                "verifiera din identitet. Läs mer om eduID i hjälp-innehållet som nås i sidfoten.");

        common.verifyStepIndicator(5, "Slutfört");
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying new account labels — English");
        common.waitUntilPageTitleContains("Register | eduID");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Completed");

        if(testData.isAddInternalPassKey()){
            common.verifyPageBodyContainsString(pageBody,"Your eduID account has been created and you can" +
                    " continue using it with the details you have provided.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Your eduID account has been created and " +
                    "you can continue using it with the details you have provided.");

            if (testData.isUseRecommendedPw()) {
                common.verifyPageBodyContainsString(pageBody, "Password");
                common.verifyString(USER_PASSWORD_DISPLAY, testData.getPassword());
            }
        }
        common.verifyPageBodyContainsString(pageBody, "Email address");
        common.verifyString(USER_EMAIL_DISPLAY, testData.getUsername().toLowerCase());
        if(testData.isSwamidSp()){
            common.verifyString(FINISHED_BUTTON, "Continue to SWAMID Entity Category Release Check");
        }
        else {
            common.verifyString(FINISHED_BUTTON, "Go to eduID to login");
        }

        common.verifyPageBodyContainsString(pageBody, "Note: Sign in to eduID.se anytime to manage" +
                " your account settings, e.g. add more keys, change password, update name and verify your " +
                "identity. Read more about eduID in the help content accessible in the footer.");

        common.verifyStepIndicator(5, "Completed");
    }
}
