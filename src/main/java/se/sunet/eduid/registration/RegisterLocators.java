package se.sunet.eduid.registration;

import org.openqa.selenium.By;

public final class RegisterLocators {

    private RegisterLocators() {}

    // ===== REGISTRATION FORM =====
    public static final By GIVEN_NAME_INPUT      = By.id("given_name");
    public static final By SURNAME_INPUT         = By.id("surname");
    public static final By EMAIL_INPUT           = By.id("email");
    public static final By REGISTER_BUTTON       = By.id("register-button");
    public static final By ACCEPT_BUTTON         = By.id("accept-button");
    public static final By CANCEL_BUTTON         = By.id("cancel-button");

    // ===== CAPTCHA =====
    public static final By CAPTCHA_INPUT         = By.id("value");
    public static final By CAPTCHA_CONTINUE      = By.id("captcha-continue-button");
    public static final By CANCEL_CAPTCHA        = By.id("cancel-captcha-button");

    // ===== FIELD LABELS =====
    // Scoped to each wrapper div to avoid matching other labels on the page
    public static final By GIVEN_NAME_LABEL      = By.cssSelector("#given_name-wrapper div label");
    public static final By SURNAME_LABEL         = By.cssSelector("#surname-wrapper div label");
    public static final By EMAIL_LABEL           = By.cssSelector("#email-wrapper div label");

    // ===== CAPTCHA PAGE =====
    public static final By CAPTCHA_PAGE_H1       = By.cssSelector("#content h1");
    public static final By CAPTCHA_PAGE_DESC     = By.cssSelector("#content > div > p");
    public static final By CAPTCHA_NEW_IMAGE_BTN = By.cssSelector("#content figure button");

    // ===== TERMS PAGE =====
    // Used to wait for the terms modal to be ready — cancel button is the stable anchor
    public static final By TERMS_HEADING_XPATH   = By.id("cancel-button");
}
