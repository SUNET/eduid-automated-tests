package se.sunet.eduid.generic;

import org.openqa.selenium.By;

public final class LoginPageLocators {

    private LoginPageLocators() {}

    // ===== FORM INPUTS =====
    public static final By USERNAME_INPUT       = By.id("username");
    public static final By PASSWORD_INPUT       = By.id("currentPassword");

    // ===== BUTTONS =====
    public static final By LOGIN_BUTTON         = By.id("login-form-button");
    public static final By PASSKEY_BUTTON       = By.id("pass-key");
    public static final By FORGOT_PASSWORD_LINK = By.id("link-forgot-password");
    public static final By OTHER_DEVICE_BUTTON  = By.id("login-other-device-button");
    public static final By ABORT_BUTTON         = By.id("login-abort-button");

    // ===== USER DATA =====
    public static final By USER_EPPN            = By.id("user-eppn");
    public static final By EPPN_COPY_BUTTON     = By.cssSelector("#uniqueId-container button");

    // ===== NAVIGATION =====
    public static final By REGISTER_LINK        = By.cssSelector("#eduid-idp-menu div div:first-child a");
    public static final By REGISTER_CONFIRM     = By.cssSelector("section:first-of-type div h1 span");

    // ===== PASSWORD CONTROLS =====
    public static final By SHOW_PASSWORD_BUTTON = By.cssSelector("#currentPassword-wrapper div:nth-child(2) button");

    // ===== DELETED ACCOUNT =====
    public static final By DELETED_RESTORE_BTN  = By.cssSelector("#content section div button");

    // ===== PASSKEY SECTION =====
    public static final By PASSKEY_HELP_LINK    = By.cssSelector("#content section.passkey-option div div.text-wrapper div div p.help-text a");

    // ===== DELETE FLOW =====
    public static final By RETURN_TO_ACCOUNT_LINK = By.cssSelector("#content section.intro div.status-box div.text-wrapper span a");
}
