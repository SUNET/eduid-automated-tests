package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;

public final class ExtraSecurityLocators {

    private ExtraSecurityLocators() {}

    // ===== FLOW BUTTONS =====
    public static final By CONTINUE_WITHOUT_SECURITY = By.id("continue-without-security");
    public static final By SECURITY_KEY_BUTTON       = By.id("mfa-security-key");

    // ===== LANGUAGE DETECTION =====
    // Targets the second footer nav item to detect current language
    public static final By FOOTER_LANG_ITEM          = By.cssSelector("footer nav ul li:nth-child(2)");

    // ===== MFA METHOD BUTTONS =====
    // These buttons are identified by visible text content — no stable ID or CSS selector available
    // since the same elements render different labels per language. XPath text matching is the
    // only reliable strategy here.
    public static final By SHOW_OTHER_OPTIONS_SWE    = By.xpath("//div[contains(text(), 'Visa andra alternativ')]");
    public static final By FREJA_BUTTON_SWE          = By.xpath("//span[contains(text(), 'Freja+')]");
    public static final By BANKID_BUTTON_SWE         = By.xpath("//span[contains(text(), 'BankID')]");
    public static final By EIDAS_BUTTON_SWE          = By.xpath("//span[contains(text(), 'eIDAS')]");
}
