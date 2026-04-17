package se.sunet.eduid.generic;

import org.openqa.selenium.By;

public final class StartPageLocators {

    private StartPageLocators() {}

    // ===== BUTTONS =====
    public static final By SIGN_UP_BUTTON = By.id("sign-up-button");
    public static final By LOGIN_BUTTON   = By.id("login-button");

    // ===== SPLASH CONTENT =====
    public static final By H1_HEADLINE   = By.cssSelector("#eduid-splash-and-children div:first-child h1");

    // P_CREATE_EDU targets the first <p> containing a <strong> — no stable ID available,
    // XPath attribute selector is the most precise option here
    public static final By P_CREATE_EDU  = By.xpath("//p[.//strong]");

    // P_EDU_EASIER is the second paragraph in the first content column
    public static final By P_EDU_EASIER  = By.cssSelector("main section div div:first-child p:nth-child(3)");

    // P_READ_MORE targets the <p> containing an external link
    public static final By P_READ_MORE   = By.xpath("//p[.//a[@target='_blank']]");

    // LINK_SUNET / LINK_HELP — external and second anchor in splash area
    public static final By LINK_SUNET            = By.cssSelector("a[target='_blank']");
    public static final By LINK_HELP             = By.cssSelector("#footer > nav > ul > li:nth-child(1) > a");
    public static final By LINK_SUNET_IN_TEXT    = By.cssSelector("#eduid-splash-and-children > div.landing-content.horizontal-content-margin > p.med-txt > a:nth-child(1)");
    public static final By LINK_HELP_IN_TEXT     = By.cssSelector("#eduid-splash-and-children > div.landing-content.horizontal-content-margin > p.med-txt > a:nth-child(2)");

    // ===== STEPS =====
    public static final By STEP_1        = By.cssSelector("#eduid-splash-and-children div.flex-between.landing-howTo div:nth-child(1)");
    public static final By STEP_2        = By.cssSelector("#eduid-splash-and-children div:nth-child(2) div:nth-child(2)");
    public static final By STEP_3        = By.cssSelector("#eduid-splash-and-children div:nth-child(2) div:nth-child(3)");
    public static final By STEP_4        = By.cssSelector("#eduid-splash-and-children div:nth-child(2) div:nth-child(4)");
}
