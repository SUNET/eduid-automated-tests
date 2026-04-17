package se.sunet.eduid.registration;

import org.openqa.selenium.By;

public final class ConfirmedNewAccountLocators {

    private ConfirmedNewAccountLocators() {}

    // ===== NAVIGATION =====
    public static final By FINISHED_BUTTON       = By.id("finished-button");

    // ===== DISPLAYED CREDENTIALS =====
    public static final By USER_EMAIL_DISPLAY    = By.id("user-email");
    public static final By USER_PASSWORD_DISPLAY = By.id("user-password");
}
