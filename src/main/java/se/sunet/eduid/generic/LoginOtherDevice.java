package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import se.sunet.eduid.registration.ConfirmEmailAddress;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.generic.LoginOtherDeviceLocators.*;

/**
 * Page object for the "login using another device" flow.
 */
public class LoginOtherDevice {

    private final Common               common;
    private final TestData             testData;
    private final ConfirmEmailAddress  confirmEmailAddress;

    public LoginOtherDevice(Common common, TestData testData, ConfirmEmailAddress confirmEmailAddress) {
        this.common               = common;
        this.testData             = testData;
        this.confirmEmailAddress  = confirmEmailAddress;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runLoginOtherDevice() {
        selectLoginOtherDevice();
        checkLabels();

        if (testData.isOtherDeviceFillCode()) {
            confirmEmailAddress.typeEmailVerificationCode("123456");
        }
        submitCode();
    }

    public void checkLabels() {
        ensureSwedishSelected();
        verifyStepsSwedish();
        common.selectEnglish();
        verifyStepsEnglish();
        common.selectSwedish();
    }

    public void verifyConfirmLoginLabels() {
        verifyConfirmLoginSwedish();
        common.selectEnglish();
        verifyConfirmLoginEnglish();
        common.selectSwedish();
    }

    public void submitCode() {
        if (testData.getOtherDeviceSubmitCode().equalsIgnoreCase("true")) {
            By buttonId = testData.isOtherDeviceFillCode() ? OK_BUTTON : CONTINUE_BUTTON;
            common.click(common.findWebElement(buttonId));
        } else if (testData.getOtherDeviceSubmitCode().equalsIgnoreCase("false")) {
            cancel();
        }
    }

    public void clickLoginShortcut() {
        //Get the code in separate tab to simulate click on link in other device
        common.navigateToUrlNewWindow(common.waitUntilClickable(SHORTCUT_LINK).getText());

        common.switchToPopUpWindow();

        //Wait for the log in button
        common.waitUntilClickable(PROCEED_BUTTON);
    }

    public void extractCode() {
        String code = common.findWebElement(RESPONSE_CODE).getText();
        testData.setOtherDeviceCode(code);
        Common.log.info("Code stored: {}", code);
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    private void selectLoginOtherDevice() {
        common.click(common.findWebElement(OTHER_DEVICE_BUTTON));

        By waitForButton = testData.isRememberMe() ? CANCEL_BUTTON : ABORT_BUTTON;
        common.waitUntilClickable(waitForButton);
    }

    private void cancel() {
        if (testData.isRememberMe()) {
            common.timeoutSeconds(2);
            common.click(common.findWebElement(CANCEL_BUTTON));
        } else {
            common.click(common.findWebElement(ABORT_BUTTON));
        }
    }

    // -------------------------------------------------------------------------
    // Label verification — steps
    // -------------------------------------------------------------------------

    private void ensureSwedishSelected() {
        if (common.findWebElement(LANGUAGE_SELECTOR).getText().equalsIgnoreCase("svenska")) {
            common.selectSwedish();
        }
    }

    private void verifyStepsSwedish() {
        common.verifyString(PAGE_H1, "Logga in med hjälp av en annan enhet");
        common.verifyLocatorContainsString(STEP_1, "Skanna QR-koden med din andra enhet");
        common.verifyString(STEP_2, "Logga in på den andra enheten");
        common.verifyLocatorContainsString(STEP_3, rememberMeStep3Swedish());
    }

    private void verifyStepsEnglish() {
        common.verifyString(PAGE_H1, "Log in using another device");
        common.verifyLocatorContainsString(STEP_1, "Scan this QR-code with your other device");
        common.verifyString(STEP_2, "Log in on the other device");
        common.verifyLocatorContainsString(STEP_3, rememberMeStep3English());
    }

    private String rememberMeStep3Swedish() {
        return testData.isRememberMe()
                ? "Klicka på \"fortsätt\" här när du har loggat in på den andra enheten"
                : "Skriv in den sexsiffriga koden som visas i den andra enheten i fältet nedan";
    }

    private String rememberMeStep3English() {
        return testData.isRememberMe()
                ? "Click \"continue\" once you have logged in on the other device"
                : "Enter the six-digit response code shown on the other device in the form below";
    }

    // -------------------------------------------------------------------------
    // Label verification — confirm login
    // -------------------------------------------------------------------------

    private void verifyConfirmLoginSwedish() {
        if (testData.isRememberMe()) {
            common.verifyLocatorContainsString(STEP_1,
                    "Du loggar in som " + testData.getDisplayName() +
                    " (" + testData.getUsername() + ") på den andra enheten");
        } else {
            common.verifyLocatorContainsString(STEP_1,
                    "Notera att du använder den här enheten för att logga in på enheten nedan");
            common.verifyStringOnPage("IP adress");
            common.verifyStringOnPage("Beskrivning");
        }
        common.verifyLocatorContainsString(STEP_2, "Logga in den här enheten");
        common.verifyLocatorContainsString(STEP_1_CAPTION, "ID# ");
    }

    private void verifyConfirmLoginEnglish() {
        if (testData.isRememberMe()) {
            common.verifyLocatorContainsString(STEP_1,
                    "You are logging in as " + testData.getDisplayName() +
                    " (" + testData.getUsername() + ") on the other device");
        } else {
            common.verifyLocatorContainsString(STEP_1,
                    "Note that you are using this device to log in on the device below");
            common.verifyStringOnPage("IP address");
            common.verifyStringOnPage("Description");
        }
        common.verifyLocatorContainsString(STEP_2, "Log in this device");
        common.verifyLocatorContainsString(STEP_1_CAPTION, "ID# ");
    }
}