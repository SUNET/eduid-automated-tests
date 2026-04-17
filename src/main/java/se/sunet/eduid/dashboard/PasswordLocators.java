package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class PasswordLocators {

    private PasswordLocators() {}

    public static final By ABORT_REC_PW_BUTTON   = By.id("new-password-cancel-button");
    public static final By SAVE_REC_PW_BUTTON    = By.id("new-password-button");
    public static final By CUSTOM_PW_RADIO       = By.id("custom-pw");
    public static final By SUGGESTED_PW_RADIO    = By.id("suggested-pw");
    public static final By CUSTOM_PW_INPUT       = By.id("custom");
    public static final By REPEAT_PW_INPUT       = By.id("repeat");
    public static final By NEW_PASSWORD_INPUT    = By.id("newPassword");
    public static final By COPY_NEW_PASSWORD     = By.id("copy-new-password");
    public static final By SAVE_CUSTOM_PW_BUTTON = By.id("chpass-button");

    // Improved (removed index-based XPath)
    public static final By SHOW_CUSTOM_PW_BUTTON =
            By.cssSelector("#custom-wrapper button");

    public static final By SHOW_REPEAT_PW_BUTTON =
            By.cssSelector("#repeat-wrapper button");

    public static final By ABORT_CUSTOM_PW_XPATH =
            By.cssSelector("#chpass-form button");

    public static final By MISMATCH_MSG_XPATH =
            By.cssSelector("#repeat-wrapper span");
}
/*public final class PasswordLocators {

    private PasswordLocators() {}

    public static final String ABORT_REC_PW_BUTTON   = "new-password-cancel-button";
    public static final String SAVE_REC_PW_BUTTON    = "new-password-button";
    public static final String CUSTOM_PW_RADIO       = "custom-pw";
    public static final String SUGGESTED_PW_RADIO    = "suggested-pw";
    public static final String CUSTOM_PW_INPUT       = "custom";
    public static final String REPEAT_PW_INPUT       = "repeat";
    public static final String NEW_PASSWORD_INPUT    = "newPassword";
    public static final String COPY_NEW_PASSWORD     = "copy-new-password";
    public static final String SAVE_CUSTOM_PW_BUTTON = "chpass-button";

    public static final String SHOW_CUSTOM_PW_BUTTON = "//*[@id=\"custom-wrapper\"]/div[2]/button";
    public static final String SHOW_REPEAT_PW_BUTTON = "//*[@id=\"repeat-wrapper\"]/div[2]/button";
    public static final String ABORT_CUSTOM_PW_XPATH = "//*[@id=\"chpass-form\"]/button[1]";
    public static final String MISMATCH_MSG_XPATH    = "//*[@id=\"repeat-wrapper\"]/div[3]/span";
}*/
