package se.sunet.eduid.registration;

import org.openqa.selenium.By;

public final class ConfirmEmailAddressLocators {

    private ConfirmEmailAddressLocators() {}

    // ===== ACTION BUTTONS =====
    public static final By OK_BUTTON        = By.id("response-code-ok-button");
    public static final By ABORT_BUTTON     = By.id("response-code-abort-button");
    public static final By LOGIN_BUTTON     = By.id("login");

    // ===== NEXT PAGE =====
    // Targets the copy-password button that appears after successful email verification
    public static final By NEXT_PAGE_BUTTON = By.cssSelector("#eduid-splash-and-children button");

    // ===== SIX-DIGIT CODE INPUT FIELDS =====
    // Each digit has its own input field identified by index
    public static final By CODE_DIGIT_0     = By.id("v[0]");
    public static final By CODE_DIGIT_1     = By.id("v[1]");
    public static final By CODE_DIGIT_2     = By.id("v[2]");
    public static final By CODE_DIGIT_3     = By.id("v[3]");
    public static final By CODE_DIGIT_4     = By.id("v[4]");
    public static final By CODE_DIGIT_5     = By.id("v[5]");
}
