package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;

public final class RequestNewPasswordLocators {

    private RequestNewPasswordLocators() {}

    // ===== FORM =====
    public static final By EMAIL_INPUT           = By.id("email");
    public static final By EMAIL_LABEL_XPATH     = By.cssSelector("#email-wrapper div label");
    public static final By RESET_PASSWORD_BUTTON = By.id("reset-password-button");
    public static final By GO_BACK_BUTTON        = By.id("go-back-button");

    // ===== CAPTCHA / FLOW =====
    public static final By CANCEL_CAPTCHA_BUTTON = By.id("cancel-captcha-button");
    public static final By ABORT_BUTTON          = By.id("response-code-abort-button");
}
