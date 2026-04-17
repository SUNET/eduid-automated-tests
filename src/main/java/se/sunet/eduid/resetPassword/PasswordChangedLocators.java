package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;

public final class PasswordChangedLocators {

    private PasswordChangedLocators() {}

    // ===== NAVIGATION =====
    public static final By FINISHED_BUTTON       = By.id("reset-password-finished");

    // ===== DISPLAYED CREDENTIALS =====
    public static final By USER_EMAIL_DISPLAY    = By.id("user-email");
    public static final By USER_PASSWORD_DISPLAY = By.id("user-password");

}
