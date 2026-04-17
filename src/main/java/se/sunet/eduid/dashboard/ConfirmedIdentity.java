package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.ConfirmedIdentityLocators.*;
import static se.sunet.eduid.generic.LoginOtherDeviceLocators.ABORT_BUTTON;
import static se.sunet.eduid.generic.LoginOtherDeviceLocators.CANCEL_BUTTON;

/**
 * Page object for the Identity page after the user's identity has already been confirmed.
 * Verifies the confirmation state labels in both languages, then delegates to Name.
 */
public class ConfirmedIdentity {

    private final Common   common;
    private final TestData testData;
    private final Name     name;

    public ConfirmedIdentity(Common common, TestData testData, Name name) {
        this.common   = common;
        this.testData = testData;
        this.name     = name;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runConfirmedIdentity() {
        verifyPageTitle();
        verifyTextAndLabels();
        name.runName();
    }

    // -------------------------------------------------------------------------
    // Verification
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilVisible(PAGE_H1);
        common.verifyPageTitle("Identitet | eduID");
        common.timeoutMilliSeconds(500);
    }

    private void verifyTextAndLabels() {
        //Button has different ID when confirmed by eIDAS
        if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            common.waitUntilClickable(REMOVE_EIDAS_ID_BUTTON);
        }
        else {
            common.waitUntilClickable(REMOVE_ID_BUTTON);
        }

        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verifying confirmed identity labels in Swedish");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Identitet");
        common.verifyPageBodyContainsString(pageBody, "Ditt eduID är redo att användas");
        common.verifyPageBodyContainsString(pageBody, "Följande identiteter är nu kopplade till ditt eduID");
        common.verifyPageBodyContainsString(pageBody, "Svenskt personnummer");

        if (isEidas()) {
            common.verifyPageBodyContainsString(pageBody, "Europeisk eIDAS-identitet");
            common.verifyPageBodyContainsString(pageBody, "XA 1939-11-13");
        } else {
            common.verifyPageBodyContainsString(pageBody, "personnummer");
            revealAndVerifyIdNumber();
        }

        testData.setIdentityConfirmed(true);
    }

    private void verifyLabelsEnglish() {
        Common.log.info("Verifying confirmed identity labels in English");
        String pageBody = common.getPageBody();

        common.verifyPageTitle("Identity | eduID");
        common.verifyPageBodyContainsString(pageBody, "Identity");
        common.verifyPageBodyContainsString(pageBody, "Your eduID is ready to use");
        common.verifyPageBodyContainsString(pageBody, "The identities below are now connected to your eduID");

        if (isEidas()) {
            common.verifyPageBodyContainsString(pageBody, "European eIDAS identity");
            common.verifyPageBodyContainsString(pageBody, "XA 1939-11-13");
        } else {
            common.verifyPageBodyContainsString(pageBody, "Swedish national ID number");
            revealAndVerifyIdNumber();
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private boolean isEidas() {
        return testData.getConfirmIdBy().equalsIgnoreCase("eidas");
    }

    private void revealAndVerifyIdNumber() {
        common.findWebElement(SHOW_HIDE_BUTTON).click();
        common.verifyString(ID_NUMBER_DISPLAY, testData.getIdentityNumber());
    }
}
