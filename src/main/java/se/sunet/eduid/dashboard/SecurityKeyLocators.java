package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class SecurityKeyLocators {

    private SecurityKeyLocators() {}

    // ===== IDs =====
    public static final By SECURITY_KEY_NAME_INPUT    = By.id("describe-webauthn-token-modal");
    public static final By ADD_EXTERNAL_KEY_BUTTON    = By.id("security-webauthn-button");
    public static final By ADD_INTERNAL_KEY_BUTTON    = By.id("security-webauthn-platform-button");
    public static final By REMOVE_KEY_BUTTON          = By.id("remove-webauthn");
    public static final By CLOSE_VERIFY_MODAL         = By.id("verify-webauthn-token-modal-close-button");
    public static final By VERIFY_BANKID_BUTTON       = By.id("verify-webauthn-token-modal-continue-bankID-button");
    public static final By VERIFY_FREJA_BUTTON        = By.id("verify-webauthn-token-modal-continue-frejaID-button");
    public static final By VERIFY_EIDAS_BUTTON        = By.id("verify-webauthn-token-modal-continue-eidas-button");
    public static final By VERIFY_FREJA_EID_BUTTON    = By.id("verify-webauthn-token-modal-continue-frejaeid-button");

    // ===== Improved locators =====

    public static final By CLOSE_POPUP_BUTTON =
            By.cssSelector("dialog button");

    public static final By CONFIRM_NAME_OK_BUTTON =
            By.cssSelector("#describe-webauthn-token-modal-form button");

    public static final By SECURITY_PAGE_HEADING =
            By.cssSelector("#content section p");

    public static final By TOGGLE_SWITCH =
            By.cssSelector("#content fieldset label div");

    public static final By VERIFIED_STATUS_LABEL =
            By.xpath("//*[@id=\"manage-security-keys\"]/figure/div/div[3]/span/strong");

    // ===== Popup labels =====

    public static final By POPUP_HEADING_XPATH =
            By.cssSelector("dialog h4");

    public static final By POPUP_NOTE_XPATH =
            By.cssSelector("#describe-webauthn-token-modal-form p");

    public static final By POPUP_LABEL_XPATH =
            By.cssSelector("#describe-webauthn-token-modal-wrapper label");

    public static final By POPUP_MAX_CHARS_XPATH =
            By.cssSelector("#describe-webauthn-token-modal-wrapper span");

    public static final By BANKID_CONFIRM_BUTTON =
            By.cssSelector("#manage-security-keys figure span > button:nth-of-type(1)");

    public static final By FREJA_CONFIRM_BUTTON =
            By.cssSelector("#manage-security-keys figure span > button:nth-of-type(2)");

    public static final By EIDAS_CONFIRM_BUTTON =
            By.cssSelector("#manage-security-keys figure span > button:nth-of-type(3)");

    public static final By FREJA_EID_CONFIRM_BUTTON =
            By.cssSelector("#manage-security-keys figure span > button:nth-of-type(4)");
}
/*public final class SecurityKeyLocators {

    private SecurityKeyLocators() {}

    // IDs
    public static final String SECURITY_KEY_NAME_INPUT    = "describe-webauthn-token-modal";
    public static final String ADD_EXTERNAL_KEY_BUTTON    = "security-webauthn-button";
    public static final String ADD_INTERNAL_KEY_BUTTON    = "security-webauthn-platform-button";
    public static final String REMOVE_KEY_BUTTON          = "remove-webauthn";
    public static final String CLOSE_VERIFY_MODAL         = "verify-webauthn-token-modal-close-button";
    public static final String VERIFY_BANKID_BUTTON       = "verify-webauthn-token-modal-continue-bankID-button";
    public static final String VERIFY_FREJA_BUTTON        = "verify-webauthn-token-modal-continue-frejaID-button";
    public static final String VERIFY_EIDAS_BUTTON        = "verify-webauthn-token-modal-continue-eidas-button";
    public static final String VERIFY_FREJA_EID_BUTTON    = "verify-webauthn-token-modal-continue-frejaeid-button";

    // XPaths
    public static final String CLOSE_POPUP_BUTTON         = "//*[@id=\"content\"]/dialog[1]/div/div/div/div/button";
    public static final String CONFIRM_NAME_OK_BUTTON     = "//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button";
    public static final String SECURITY_PAGE_HEADING      = "//*[@id=\"content\"]/section/div/p";
    public static final String TOGGLE_SWITCH              = "//*[@id=\"content\"]/article[2]/form/fieldset/label/div";
    public static final String VERIFIED_STATUS_LABEL      = "//*[@id=\"manage-security-keys\"]/figure/div/div[3]/span/strong";

    // Pop-up dialog labels
    public static final String POPUP_HEADING_XPATH        = "//*[@id=\"content\"]/dialog[1]/div/div/div/div/h4";
    public static final String POPUP_NOTE_XPATH           = "//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p";
    public static final String POPUP_LABEL_XPATH          = "//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label";
    public static final String POPUP_MAX_CHARS_XPATH      = "//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span";
}*/
