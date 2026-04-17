package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.MailReader;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.EmailLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object for the Email Addresses section on the Account page.
 */
public class EmailAddresses {

    private final Common   common;
    private final TestData testData;

    // Used for the confirmation code input field — accessed by ID string in Common API
    private static final String CONFIRM_CODE_INPUT_ID = "email-confirm-modal";

    public EmailAddresses(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runEmailAddresses() {
        common.navigateToAccount();
        verifyPageTitle();

        if (testData.isRemoveNewEmail1()) {
            removeEmail();
        }

        addNewEmail();

        if (!testData.getConfirmNewEmail1().equals("wrongCode")) {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Konto | eduID");
    }

    private void removeEmail() {
        common.click(common.waitUntilClickable(REMOVE_ROW_3_BUTTON));
    }

    // -------------------------------------------------------------------------
    // Add email flow
    // -------------------------------------------------------------------------

    private void addNewEmail() {
        String newEmail = testData.getAddNewEmail1();

        if (!newEmail.isEmpty() && newEmail.contains("@")) {
            addValidEmail(newEmail);
        } else if (!newEmail.isEmpty() && !newEmail.contains("@")) {
            addInvalidEmail(newEmail);
        }
    }

    private void addValidEmail(String email) {
        log.info("Click on add new email link");
        common.click(common.waitUntilClickable(ADD_MORE_BUTTON));

        common.verifyPlaceholder("namn@example.com", "email");
        typeEmailAddress(email);

        common.verifyStringById("cancel-adding-email", "AVBRYT");
        common.verifyStringById("add-email", "LÄGG TILL");

        log.info("Click on add new email button after typing the new email address");
        common.waitUntilClickable(ADD_BUTTON).click();
        common.timeoutMilliSeconds(200);

        // Check if the email already exists in the table
        String existingEmail = common.waitUntilVisible(
                By.cssSelector("#content article:nth-child(2) div div table tbody tr:nth-child(2) td:first-child"))
                .getText();

        if (email.equals(existingEmail)) {
            handleDuplicateEmail(email);
        } else {
            handleNewEmail(email);
        }
    }

    private void handleDuplicateEmail(String email) {
        common.timeoutMilliSeconds(500);
        common.verifyStringByXpath(
                "//div/div/main/div/section/article[2]/div/form/div[1]/div/span",
                "E-postadressen finns redan i listan.");

        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        // Need to add the address again — error message disappears on language switch
        log.info("Click on add new email button, after language change");
        common.click(common.waitUntilClickable(ADD_MORE_BUTTON));
        typeEmailAddress(email);

        common.verifyStringById("cancel-adding-email", "CANCEL");

        common.timeoutMilliSeconds(500);
        common.verifyStringByXpath(
                "//div/div/main/div/section/article[2]/div/form/div[1]/div/span",
                "The email is already in the list.");

        common.timeoutMilliSeconds(200);
        common.selectSwedish();
    }

    private void handleNewEmail(String email) {
        common.timeoutMilliSeconds(1000);

        // Verify the added email and confirm button appear in row 3
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[1]", email);
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button", "BEKRÄFTA");

        String confirmMode = testData.getConfirmNewEmail1();
        if (confirmMode.equals("code") || confirmMode.equals("wrongCode")) {
            handleEmailConfirmation(confirmMode);
        }

        verifyPostConfirmationState(confirmMode);
    }

    private void handleEmailConfirmation(String confirmMode) {
        log.info("Confirm email with code: {}", confirmMode);

        String code = null;
        if (confirmMode.equals("code")) {
            // Wait for the email to arrive
            log.info("Waiting 15 seconds for the email to arrive in inbox");
            common.timeoutSeconds(15);
            code = new MailReader().readEmail("confirmationCode");
            log.info("Confirmation Code: {}", code);
        }

        verifyConfirmEmailPopupLabels();

        common.findWebElementById(CONFIRM_CODE_INPUT_ID).clear();

        if (confirmMode.equals("code")) {
            common.findWebElementById(CONFIRM_CODE_INPUT_ID).sendKeys(code);
            log.info("Confirming email with the correct activation code: {}", code);
        } else {
            // Wrong code — enter bad code, verify error message, reopen and enter a second bad code
            common.findWebElementById(CONFIRM_CODE_INPUT_ID).sendKeys("18587");
            log.info("Attempt to confirm email with incorrect activation code");
            common.verifyStringByXpath(
                    "//*[@id=\"email-confirm-modal-wrapper\"]/div[2]/span",
                    "Den kod du angett stämmer inte. Var god försök igen");

            // .clear() does not work here — close and reopen the pop-up instead
            common.waitUntilClickable(CLOSE_CONFIRM_POPUP).click();
            pressConfirmEmail();
            common.switchToPopUpWindow();
            common.findWebElementById(CONFIRM_CODE_INPUT_ID).sendKeys("e01460442a");
        }

        common.waitUntilClickable(CONFIRM_OK_BUTTON).click();
    }

    private void verifyPostConfirmationState(String confirmMode) {
        common.timeoutSeconds(1);
        if (confirmMode.equals("wrongCode")) {
            log.info("Verify status message when email confirmation code is not correct");
            common.verifyStatusMessage(
                    "Ogiltig kod eller en kod som har gått ut. Var god prova igen eller begär en ny kod");
        } else {
            verifyPrimaryEmailSwap();
        }
    }

    private void verifyPrimaryEmailSwap() {
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");

        // Make the newly added email primary
        log.info("Clicking on email address on second row to make the added email primary");
        common.waitUntilClickable(EMAIL_ROW_3_LABEL).click();
        common.timeoutMilliSeconds(1500);

        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/button", "GÖR PRIMÄR");
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/span", "PRIMÄR");

        // Restore the original primary email
        log.info("Clicking on email address on first row to make the default email primary again");
        common.waitUntilClickable(EMAIL_ROW_2_LABEL).click();
        common.timeoutMilliSeconds(1000);

        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");
    }

    private void addInvalidEmail(String email) {
        common.timeoutSeconds(500);
        common.click(common.waitUntilClickable(ADD_MORE_BUTTON));
        typeEmailAddress(email);

        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "Ogiltig e-postadress");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "En giltig e-postadress");

        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        // Need to add the address again — error message disappears on language switch
        common.click(common.waitUntilClickable(ADD_MORE_BUTTON));
        typeEmailAddress(email);

        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "The entered email is invalid");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "A valid email address");

        common.timeoutMilliSeconds(200);
        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Confirmation pop-up
    // -------------------------------------------------------------------------

    private void verifyConfirmEmailPopupLabels() {
        // English first
        common.selectEnglish();
        pressConfirmEmail();

        log.info("Verify email confirmation pop-up labels - English");
        common.waitUntilVisible(CONFIRM_POPUP_HEADER);
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/dialog/div/div/div/div/h4",
                "Enter the code sent to " + testData.getAddNewEmail1());
        common.verifyStringByXpath(
                "//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Code");
        common.verifyPlaceholder("enter code", CONFIRM_CODE_INPUT_ID);
        common.verifyStringByXpath(
                "//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/button", "Send a new code");

        common.waitUntilClickable(CLOSE_CONFIRM_POPUP).click();
        common.timeoutMilliSeconds(200);

        // Swedish
        common.selectSwedish();
        pressConfirmEmail();

        log.info("Verify email confirmation pop-up labels - Swedish");
        common.waitUntilVisible(CONFIRM_POPUP_HEADER);
        common.verifyStringByXpath(
                "//*[@id=\"add-email-addresses\"]/dialog/div/div/div/div/h4",
                "Skriv in koden som skickats till " + testData.getAddNewEmail1());
        common.verifyStringByXpath(
                "//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Kod");
        common.verifyPlaceholder("skriv in koden", CONFIRM_CODE_INPUT_ID);
        common.verifyStringByXpath(
                "//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/button", "Skicka ny kod");

        log.info("Done! - Verify email confirmation pop-up labels - Swedish");
    }

    private void pressConfirmEmail() {
        log.info("Click on confirm email link");
        common.click(common.waitUntilClickable(CONFIRM_ROW_3_BUTTON));
    }

    // -------------------------------------------------------------------------
    // Page label verification
    // -------------------------------------------------------------------------

    private void verifyLabelsSwedish() {
        common.verifyStringOnPage("E-postadresser");
        common.verifyStringOnPage("Du kan koppla en eller flera e-postadresser till ditt eduID.");
        common.verifyStringOnPage("+ Lägg till fler");
    }

    private void verifyLabelsEnglish() {
        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        common.verifyStringOnPage("Email addresses");
        common.verifyStringOnPage("You can connect one or more email addresses to your eduID.");
        common.verifyStringOnPage("+ Add more");

        common.timeoutMilliSeconds(200);
        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void typeEmailAddress(String email) {
        common.waitUntilClickable(EMAIL_INPUT);
        common.waitUntilVisible(EMAIL_INPUT).clear();
        common.waitUntilVisible(EMAIL_INPUT).sendKeys(email);
        log.info("Adding email address: {}", email);
    }
}
