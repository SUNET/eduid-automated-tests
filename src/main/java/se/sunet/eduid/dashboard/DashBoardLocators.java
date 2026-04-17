package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class DashBoardLocators {

    private DashBoardLocators() {}

    // ===== PAGE ELEMENTS =====
    public static final By USERNAME_DISPLAY =
            By.cssSelector("#header nav button span");

    public static final By WELCOME_HEADING =
            By.cssSelector("#eduid-splash-and-children h1 strong");

    public static final By EPPN_LABEL =
            By.cssSelector("#uniqueId-container label strong");

    public static final By HELP_LINK =
            By.cssSelector("#eduid-splash-and-children article p a");

    public static final By IDENTITY_SECTION_LINK =
            By.cssSelector("#eduid-splash-and-children section a[href*='identity']");

    public static final By SECURITY_SECTION_LINK =
            By.cssSelector("#eduid-splash-and-children section a[href*='security']");

    public static final By SEC_KEY_SECTION_LINK =
            By.cssSelector("#eduid-splash-and-children section a[href*='security-keys']");

    public static final By ACCOUNT_LINK =
            By.cssSelector("#eduid-splash-and-children article a[href*='account']");

    // ===== NAVIGATION MENU =====
    public static final By NAV_START_LABEL =
            By.cssSelector("a[href^='/profile/']");

    public static final By NAV_IDENTITY_LABEL =
            By.cssSelector("a[href^='/profile/identity']");

    public static final By NAV_SECURITY_LABEL =
            By.cssSelector("a[href^='/profile/security']");

    public static final By NAV_ACCOUNT_LABEL =
            By.cssSelector("a[href^='/profile/account']");

    // ===== CHEVRONS (EXPAND ICONS) =====
    public static final By NAV_START_CHEVRON =
            By.cssSelector("#header > nav > div > ul > li:nth-child(1) > div > button > svg");

    public static final By NAV_IDENTITY_CHEVRON =
            By.cssSelector("#header > nav > div > ul > li:nth-child(2) > div > button > svg");

    public static final By NAV_SECURITY_CHEVRON =
            By.cssSelector("#header > nav > div > ul > li:nth-child(3) > div > button > svg");

    public static final By NAV_ACCOUNT_CHEVRON =
            By.cssSelector("#header > nav > div > ul > li:nth-child(4) > div > button > svg");

    // ===== EXPAND BUTTONS =====
    public static final By NAV_START_EXPAND =
            By.xpath("//*[@id=\"header\"]/nav/div/ul/li[1]/div/button");

    public static final By NAV_IDENTITY_EXPAND =
            By.xpath("//*[@id=\"header\"]/nav/div/ul/li[2]/div/button");

    public static final By NAV_SECURITY_EXPAND =
            By.xpath("//*[@id=\"header\"]/nav/div/ul/li[3]/div/button");

    public static final By NAV_ACCOUNT_EXPAND =
            By.xpath("//*[@id=\"header\"]/nav/div/ul/li[4]/div/button");

    // ===== SUB MENU =====
    public static final By MENU_STATUS_OVERVIEW =
            By.cssSelector("a[href*='#status-overview']");

    public static final By MENU_VERIFY_IDENTITY =
            By.cssSelector("a[href*='#verify-identity']");

    public static final By MENU_NAME =
            By.cssSelector("a[href*='#personal-data']");

    public static final By MENU_MFA =
            By.cssSelector("a[href*='#add-two-factor']");

    public static final By MENU_SECURITY_KEYS =
            By.cssSelector("a[href*='#manage-security-keys']");

    public static final By MENU_UNIQUE_ID =
            By.cssSelector("a[href*='#unique-id']");

    public static final By MENU_EMAIL =
            By.cssSelector("a[href*='#add-email-addresses']");

    public static final By MENU_LANGUAGE =
            By.cssSelector("a[href*='#language']");

    public static final By MENU_CHANGE_PASSWORD =
            By.cssSelector("a[href*='#change-password']");

    public static final By MENU_ORCID =
            By.cssSelector("a[href*='#orcid']");

    public static final By MENU_ESI =
            By.cssSelector("a[href*='#ladok']");

    public static final By MENU_DELETE_ACCOUNT =
            By.cssSelector("a[href*='#delete-account']");

    // ===== IDS =====
    public static final By LOGOUT_BUTTON =
            By.id("logout");

    public static final By USER_EPPN =
            By.id("user-eppn");
}
