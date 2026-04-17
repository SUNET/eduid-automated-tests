package se.sunet.eduid.generic;

import org.openqa.selenium.By;

public final class LoginOtherDeviceLocators {

    private LoginOtherDeviceLocators() {}

    // ===== BUTTONS =====
    public static final By OTHER_DEVICE_BUTTON  = By.id("login-other-device-button");
    public static final By CANCEL_BUTTON        = By.id("response-code-cancel-button");
    public static final By ABORT_BUTTON         = By.id("response-code-abort-button");
    public static final By OK_BUTTON            = By.id("response-code-ok-button");
    public static final By CONTINUE_BUTTON      = By.id("response-code-continue-button");
    public static final By PROCEED_BUTTON       = By.id("proceed-other-device-button");

    // ===== DATA ELEMENTS =====
    public static final By RESPONSE_CODE        = By.id("response_code");
    public static final By LANGUAGE_SELECTOR    = By.id("language-selector");

    // ===== PAGE STRUCTURE =====
    public static final By PAGE_H1              = By.cssSelector("#content div h1");
    public static final By STEP_1               = By.cssSelector("#content div ol li:nth-child(1)");
    public static final By STEP_2               = By.cssSelector("#content div ol li:nth-child(2)");
    public static final By STEP_3               = By.cssSelector("#content div ol li:nth-child(3)");
    public static final By STEP_1_CAPTION       = By.cssSelector("#content div ol li:nth-child(1) figure figcaption");

    // ===== LINKS =====
    public static final By SHORTCUT_LINK        = By.cssSelector("#qr_url a");
}
