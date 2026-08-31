package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.dashboard.PasswordLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object for the Change Password page.
 */
public class Password {

    private final Common   common;
    private final TestData testData;

    public Password(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runPassword() {
        navigateToSettings();
        verifyPageTitle();
        setPassword();
    }

    // -------------------------------------------------------------------------
    // Navigation & setup
    // -------------------------------------------------------------------------

    public void navigateToSettings() {
        common.navigateToAccount();
        common.click(common.findWebElement(By.id("security-change-button")));
    }

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Byt lösenord | eduID");
    }

    // -------------------------------------------------------------------------
    // Password flow
    // -------------------------------------------------------------------------

    public void setPassword() {
        //Expand password form
        expandPasswordForm();

        if (testData.isUseRecommendedPw()) {
            copyPasteNewPassword();
        } else {
            selectCustomPasswordAndFill();
        }

        Assert.assertFalse(testData.getPassword().isEmpty(),
                "A new password has not been saved, getPassword is: " + testData.getPassword());

        if (testData.isButtonValueConfirm()) {
            confirmPassword();
        } else {
            abortPassword();
        }
    }

    private void selectCustomPasswordAndFill() {
        common.click(common.findWebElement(CUSTOM_PW_RADIO));
        common.waitUntilClickable(SHOW_CUSTOM_PW_BUTTON);
        verifyCustomPasswordLabels();


        // Re-select after the language switch in label verification
        if(testData.isRegisterAccount() &! testData.isAddExternalSecurityKey() &! testData.isAddInternalPassKey()) {
            common.findWebElement(SHOW_PW_FORM).click();
        }

        common.click(common.findWebElement(CUSTOM_PW_RADIO));
        WebElement pwElement = common.findWebElement(CUSTOM_PW_INPUT);
        pwElement.clear();
        WebElement repeatPwElement = common.findWebElement(REPEAT_PW_INPUT);
        repeatPwElement.clear();

        if (testData.isIncorrectPassword()) {
            pwElement.sendKeys("NotTheSamePassword");
            repeatPwElement.sendKeys(testData.getNewPassword());
            Common.log.info("Incorrect password entered");
        } else {
            pwElement.sendKeys(testData.getNewPassword());
            repeatPwElement.sendKeys(testData.getNewPassword());
            testData.setPassword(testData.getNewPassword());
            Common.log.info("Stored custom password: {}", testData.getPassword());
        }
    }

    private void confirmPassword() {
        common.timeoutMilliSeconds(500);

        if (testData.isUseRecommendedPw()) {
            Common.log.info("Pressing OK to save new recommended password");
            common.click(common.findWebElement(SAVE_REC_PW_BUTTON));

        } else if (testData.isIncorrectPassword()) {
            Common.log.info("Repeated passwords do not match — pressing Cancel");
            common.verifyString(MISMATCH_MSG_XPATH, "Det nya och repeterade lösenordet är olika.");
            common.timeoutMilliSeconds(500);
            common.findWebElement(ABORT_CUSTOM_PW_XPATH).click();

        } else if (testData.getNewPassword().equalsIgnoreCase("test")) {
            Common.log.info("Pressing Cancel — password is too weak");
            String enabled = String.valueOf(common.findWebElement(SAVE_CUSTOM_PW_BUTTON).isEnabled());
            common.verifyStrings("false", enabled);
            common.findWebElement(ABORT_CUSTOM_PW_XPATH).click();

        } else {
            Common.log.info("Pressing OK to save new password");
            common.waitUntilClickable(SAVE_CUSTOM_PW_BUTTON).click();
            Common.log.info("Password changed!");
            common.timeoutSeconds(2);
        }

        common.timeoutMilliSeconds(500);
    }

    private void abortPassword() {
        if (testData.isUseRecommendedPw()) {
            common.click(common.findWebElement(ABORT_REC_PW_BUTTON));
            Common.log.info("Pressed Cancel new password");
        } else {
            common.click(common.findWebElement(ABORT_CUSTOM_PW_XPATH));
        }
    }

    private void copyPasteNewPassword() {
        common.waitUntilClickable(COPY_NEW_PASSWORD);
        testData.setPassword(common.getAttribute(COPY_NEW_PASSWORD));
        log.info("New password saved: {}", testData.getPassword());

        WebElement newPwElement = common.findWebElement(NEW_PASSWORD_INPUT);
        newPwElement.clear();

        newPwElement.sendKeys(testData.getPassword());
    }

    // -------------------------------------------------------------------------
    // Label verification
    // -------------------------------------------------------------------------

    private void verifyCustomPasswordLabels() {
        log.info("Verifying custom password labels — Swedish");

        String pageBody = common.getPageBody();

        //Different heading depending on register or reset password flow
        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, recommendedPwHeadingSwedish());
        }
        else {
            common.verifyPageBodyContainsString(pageBody, customPwHeadingSwedish());
        }

        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, "Skapa ett tillräckligt starkt lösenord för att hålla ditt konto säkert.");
        }
        else {
            //Shortened to handle variations
            common.verifyPageBodyContainsString(pageBody, "När du skapar ditt eget lösenord");
            common.verifyPageBodyContainsString(pageBody, "bör du se till att det är tillräckligt starkt för att hålla");
        }
        common.verifyPageBodyContainsString(pageBody, "Välj ett alternativ");
        common.verifyPageBodyContainsString(pageBody, "Rekommenderat lösenord");
        common.verifyPageBodyContainsString(pageBody, "Skapa ditt eget lösenord");
        Assert.assertTrue(common.findWebElement(CUSTOM_PW_RADIO).isSelected(),
                "Radio button for custom password not selected");
        common.verifyPageBodyContainsString(pageBody, "Tänk på att välja ett säkert lösenord");
        common.verifyPageBodyContainsString(pageBody, "Blanda stora och små bokstäver (inte bara första bokstaven)");
        common.verifyPageBodyContainsString(pageBody, "Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyPageBodyContainsString(pageBody, "Använd specialtecken som @ $ \\ + _ %");
        common.verifyPageBodyContainsString(pageBody, "Blanksteg (mellanslag) ignoreras");
        common.verifyPageBodyContainsString(pageBody, "Skriv ditt nya lösenord");
        common.verifyString(SHOW_CUSTOM_PW_BUTTON, "VISA");
        common.verifyPageBodyContainsString(pageBody, "Repetera ditt nya lösenord");
        common.verifyString(SHOW_REPEAT_PW_BUTTON, "VISA");
        common.verifyString(ABORT_CUSTOM_PW_XPATH, "AVBRYT");

        common.selectEnglish();

        if(testData.isRegisterAccount() &! testData.isAddExternalSecurityKey() &! testData.isAddInternalPassKey()) {
            common.waitUntilClickable(SHOW_PW_FORM).click();
        }

        log.info("Verifying custom password labels — English");
        common.click(common.waitUntilClickable(CUSTOM_PW_RADIO));

        pageBody = common.getPageBody();

        //Different heading depending on register or reset password flow
        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, recommendedPwHeadingEnglish());
        }
        else {
            common.verifyPageBodyContainsString(pageBody, customPwHeadingEnglish());
        }

        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, "Create a strong enough password to keep your account safe.");
        }
        else {
            //Shortened to handle variations
            common.verifyPageBodyContainsString(pageBody, "When creating your own password, make sure ");
            common.verifyPageBodyContainsString(pageBody, " strong enough to keep your account");
        }
        common.verifyPageBodyContainsString(pageBody, "Choose an option");
        common.verifyPageBodyContainsString(pageBody, "Suggested password");
        common.verifyPageBodyContainsString(pageBody, "Create your own password");
        Assert.assertTrue(common.findWebElement(CUSTOM_PW_RADIO).isSelected(),
                "Radio button for custom password not selected");
        common.verifyPageBodyContainsString(pageBody, "Tip: Choose a strong password");
        common.verifyPageBodyContainsString(pageBody, "Use upper- and lowercase characters, but not at the beginning or end");
        common.verifyPageBodyContainsString(pageBody, "Add digits somewhere, but not at the beginning or end");
        common.verifyPageBodyContainsString(pageBody, "Add special characters, such as @ $ \\ + _ %");
        common.verifyPageBodyContainsString(pageBody, "Spaces are ignored");
        common.verifyPageBodyContainsString(pageBody, "Enter new password");
        common.verifyString(SHOW_CUSTOM_PW_BUTTON, "SHOW");
        common.verifyPageBodyContainsString(pageBody, "Repeat new password");
        common.verifyString(ABORT_CUSTOM_PW_XPATH, "CANCEL");

        common.selectSwedish();
    }

    private void verifyRecommendedPwLabels() {
        log.info("Verifying recommended password labels — Swedish");

        String pageBody = common.getPageBody();
        common.verifyPageBodyContainsString(pageBody, recommendedPwHeadingSwedish());
        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, "Ett starkt lösenord har genererats åt dig. " +
                    "Fortsätt genom att kopiera lösenordet till fältet Repetera ditt nya lösenord och klicka på knappen Spara.");
        }
        //This is not the full password sentences, different versions exist if its reset pw flow or change password.
        else {
            common.verifyPageBodyContainsString(pageBody, "Ett starkt lösenord har genererats åt dig. ");
            common.verifyPageBodyContainsString(pageBody,"Repetera ditt nya lösenord och klicka på ");
        }
        common.verifyPageBodyContainsString(pageBody, "Välj ett alternativ");
        common.verifyPageBodyContainsString(pageBody, "Rekommenderat lösenord");
        common.verifyPageBodyContainsString(pageBody, "Skapa ditt eget lösenord");
        Assert.assertTrue(common.findWebElement(SUGGESTED_PW_RADIO).isSelected(),
                "Radio button for suggested password not selected");
        common.verifyString(By.xpath("//*[@id=\"newPassword-wrapper\"]/div/label"), "Repetera ditt nya lösenord");
        common.verifyPlaceholderBy("xxxx xxxx xxxx", NEW_PASSWORD_INPUT);
        //common.verifyString(ABORT_REC_PW_BUTTON, "AVBRYT");
        common.verifyString(SAVE_REC_PW_BUTTON, "SPARA");
        common.verifyPageBodyContainsString(pageBody, "Nytt lösenord");
        common.verifyPageBodyContainsString(pageBody, "Repetera ditt nya lösenord");

        //Verify the registration form step indicator
        if (testData.isRegisterAccount()) {
            common.verifyStepIndicator(4, "Inloggningsmetod");
        }

        common.selectEnglish();
        if(testData.isRegisterAccount()) {
            common.findWebElement(SHOW_PW_FORM).click();
        }
        log.info("Verifying recommended password labels — English");

        pageBody = common.getPageBody();
        common.verifyPageBodyContainsString(pageBody, recommendedPwHeadingEnglish());
        if(testData.isRegisterAccount()){
            common.verifyPageBodyContainsString(pageBody, "A strong password has been generated for you. " +
                    "To proceed copy the password into the Repeat new password field and click the Save button.");
        }
        else {
            //This is not the full password sentences, different versions exist if its reset pw flow or change password.
            common.verifyPageBodyContainsString(pageBody, "A strong password has been generated for you.");
            common.verifyPageBodyContainsString(pageBody,"Repeat new password field and click the Save button");
        }
        common.verifyPageBodyContainsString(pageBody, "Choose an option");
        common.verifyPageBodyContainsString(pageBody, "Suggested password");
        common.verifyPageBodyContainsString(pageBody, "Create your own password");
        Assert.assertTrue(common.findWebElement(SUGGESTED_PW_RADIO).isSelected(),
                "Radio button for suggested password not selected");
        //common.verifyString(ABORT_REC_PW_BUTTON, "CANCEL");
        common.verifyString(SAVE_REC_PW_BUTTON, "SAVE");
        common.verifyPageBodyContainsString(pageBody, "New password");
        common.verifyPageBodyContainsString(pageBody, "Repeat new password");

        //Verify the registration form step indicator
        if (testData.isRegisterAccount()) {
            common.verifyStepIndicator(4, "Sign-in method");
        }

        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Context-dependent heading helpers
    // -------------------------------------------------------------------------

    private String customPwHeadingSwedish() {
        if (testData.isRegisterAccount())  return "Skapa eduID: Skapa ditt eget lösenord";
        if (testData.isResetPassword())    return "Återställ lösenord: Skapa ditt eget lösenord";
        return "Ändra lösenord: Skapa ditt eget lösenord";
    }

    private String customPwHeadingEnglish() {
        if (testData.isRegisterAccount())  return "Create eduID: Set your own password";
        if (testData.isResetPassword())    return "Reset Password: Create your own password";
        return "Change password: Set your own password";
    }

    private String recommendedPwHeadingSwedish() {
        if (testData.isRegisterAccount())  return "Skapa eduID: Lägg till inloggningsmetod";
        if (testData.isResetPassword())    return "Återställ lösenord: Föreslaget lösenord";
        return "Ändra lösenord: Rekommenderat lösenord";
    }

    private String recommendedPwHeadingEnglish() {
        if (testData.isRegisterAccount())  return "Create eduID: Register your sign-in method";
        if (testData.isResetPassword())    return "Reset password: Suggested password";
        return "Change password: Suggested password";
    }

    private void expandPasswordForm() {
        try {
            // Försök hämta texten direkt. Saknas elementet kastas NoSuchElementException här.
            String buttonText = common.findWebElement(SHOW_PW_FORM).getText();

            if (buttonText.contains("VISA FORMULÄR") || buttonText.contains("SHOW FORM")) {
                common.waitUntilClickable(SHOW_PW_FORM).click();
                log.info("Klickade på expandera lösenordsformulär.");
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Hit kommer koden om elementet inte finns på sidan
            log.warn("Knappen för att visa lösenordsformulär hittades inte på sidan.");
        } catch (Exception e) {
            // Hit kommer koden för andra oväntade fel
            log.error("Ett oväntat fel uppstod: " + e.getMessage());
        }
    }



}