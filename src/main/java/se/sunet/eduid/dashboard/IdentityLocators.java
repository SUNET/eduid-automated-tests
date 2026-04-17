package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class IdentityLocators {

    private IdentityLocators() {}

    // ===== PAGE STRUCTURE =====
    public static final By PAGE_H1               = By.cssSelector("#content section h1");
    public static final By PAGE_SUBTEXT          = By.cssSelector("#content section div p");
    public static final By CHOOSE_METHOD_HEADING = By.cssSelector("#content article h2");

    // ===== SWEDISH SECTION =====
    public static final By SWEDISH_BUTTON_H3     = By.cssSelector("#swedish-button div h3");
    public static final By SWEDISH_BUTTON_SPAN   = By.cssSelector("#swedish-button div span");
    public static final By SWEDISH_DESCRIPTION   = By.cssSelector("#swedish div p");

    // ===== BANKID SECTION =====
    public static final By BANKID_BUTTON_SPAN    = By.cssSelector("#se-bankID-button div span");
    public static final By BANKID_BUTTON_H3      = By.cssSelector("#se-bankID-button div h3");
    public static final By BANKID_DESC_P1        = By.cssSelector("#se-bankID div p:nth-child(1)");
    public static final By BANKID_DESC_P2        = By.cssSelector("#se-bankID div p:nth-child(2)");
    public static final By BANKID_PROCEED_BUTTON = By.cssSelector("#se-bankID div button");
    public static final By BANKID_DETAILS_BUTTON  = By.id("se-bankID");
    public static final By BANKID_EXPAND_BUTTON   = By.id("se-bankID");

    // ===== FREJA+ SECTION =====
    public static final By FREJA_BUTTON_SPAN     = By.cssSelector("#se-freja-button div span");
    public static final By FREJA_BUTTON_H3       = By.cssSelector("#se-freja-button div h3");
    public static final By FREJA_DESC_P          = By.cssSelector("#se-freja div p");
    public static final By FREJA_PROCEED_BUTTON  = By.cssSelector("#se-freja div button");
    public static final By FREJA_DETAILS_BUTTON  = By.id("se-freja");
    public static final By FREJA_EXPAND_BUTTON   = By.id("se-freja");

    // ===== LETTER SECTION =====
    public static final By LETTER_BUTTON_SPAN    = By.cssSelector("#se-letter-button div span");
    public static final By LETTER_BUTTON_H3      = By.cssSelector("#se-letter-button div h3");
    public static final By LETTER_DESC_P1        = By.cssSelector("#se-letter div p:nth-child(1)");
    public static final By LETTER_PROCEED_BUTTON = By.xpath("//*[@id=\"se-letter\"]/div/button");
    public static final By LETTER_DETAILS_BUTTON = By.id("se-letter");
    public static final By LETTER_EXPAND_BUTTON  = By.id("se-letter");

    // ===== EIDAS SECTION =====
    public static final By EU_BUTTON_H3          = By.cssSelector("#eu-button div h3");
    public static final By EU_BUTTON_SPAN        = By.cssSelector("#eu-button div span");
    public static final By EU_DESC_P1            = By.cssSelector("#eu div p:nth-child(1)");
    public static final By EU_DESC_P2            = By.cssSelector("#eu div p:nth-child(2)");
    public static final By EU_PROCEED_BUTTON     = By.cssSelector("#eu div button");
    public static final By EU_DETAILS_BUTTON     = By.id("eu");
    public static final By EU_EXPAND_BUTTON      = By.id("eu-button");

    // ===== FREJA EID (WORLD) SECTION =====
    public static final By WORLD_BUTTON_H3       = By.cssSelector("#world-button div h3");
    public static final By WORLD_BUTTON_SPAN     = By.cssSelector("#world-button div span");
    public static final By WORLD_DESC_P1         = By.cssSelector("#world div p:nth-child(1)");
    public static final By WORLD_DESC_P2         = By.cssSelector("#world div p:nth-child(2)");
    public static final By WORLD_PROCEED_BUTTON  = By.cssSelector("#world div button");
    public static final By WORLD_DETAILS_BUTTON  = By.id("world");
    public static final By WORLD_EXPAND_BUTTON   = By.id("world-button");

    // ===== FREJA MODAL =====
    public static final By FREJA_MODAL_HEADING   = By.cssSelector("#eidas-info-modal div div div:first-child h4");
    public static final By FREJA_STEP_1          = By.cssSelector("#freja-instructions ol li:nth-child(1)");
    public static final By FREJA_STEP_2          = By.cssSelector("#freja-instructions ol li:nth-child(2)");
    public static final By FREJA_STEP_3          = By.cssSelector("#freja-instructions ol li:nth-child(3)");
    public static final By FREJA_STEP_5          = By.cssSelector("#freja-instructions ol li:nth-child(5)");

    // ===== LETTER CONFIRM POP-UP =====
    public static final By LETTER_POPUP_HEADER   = By.cssSelector("#letter-confirm-modal div div div:first-child h4");
    public static final By LETTER_POPUP_BODY     = By.cssSelector("#letter-confirm-modal div div div:nth-child(2)");

    // ===== IDS =====
    public static final By FREJA_ACCEPT_BUTTON   = By.id("eidas-info-modal-accept-button");
    public static final By FREJA_CLOSE_BUTTON    = By.id("eidas-info-modal-close-button");
    public static final By LETTER_CLOSE_BUTTON   = By.id("letter-confirm-modal-close-button");
    public static final By LETTER_ACCEPT_BUTTON  = By.id("letter-confirm-modal-accept-button");
    public static final By SHOW_HIDE_BUTTON      = By.id("show-hide-button");
    public static final By NIN_INPUT             = By.id("nin");
    public static final By ADD_NIN_BUTTON        = By.id("add-nin-button");
    public static final By NIN_NUMBER_DISPLAY    = By.id("nin-number");
}
