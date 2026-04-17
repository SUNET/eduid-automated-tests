package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class AccountLocators {

    private AccountLocators() {}

    // ===== EPPN =====
    public static final By EPPN_LABEL       = By.cssSelector("#uniqueId-container label strong");
    public static final By EPPN_COPY_BUTTON = By.cssSelector("#uniqueId-container button");
    public static final By USER_EPPN        = By.id("user-eppn");

    // ===== ORCID =====
    public static final By CONNECT_ORCID_BUTTON = By.id("connect-orcid-button");

    // ===== LADOK =====
    private static final String LADOK_ROOT = "#ladok";

    public static final By LADOK_TOGGLE =
            By.cssSelector(LADOK_ROOT + " fieldset label div");

    public static final By LADOK_ACTIVATE_BUTTON =
            By.id("ladok-connection");

    public static final By LADOK_SELECT_LABEL =
            By.cssSelector(LADOK_ROOT + " form span");

    public static final By LADOK_DROPDOWN_HEADING =
            By.xpath("//*[@id=\"ladok\"]//form//fieldset//div//div//div[1]/div");

    public static final By LADOK_DROPDOWN_LIST =
            By.xpath("//*[@id=\"ladok\"]/form//fieldset//div/div");

    // ===== LANGUAGE =====
    public static final By LANGUAGE_SELECTOR =
            By.cssSelector("#language-selector a");

    public static final By LANG_SWEDISH_LABEL =
            By.xpath("//label[.//span[text()='Svenska']]");

    public static final By LANG_ENGLISH_LABEL =
            By.xpath("//label[.//span[text()='English']]");
}