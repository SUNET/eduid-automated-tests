package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class ConfirmIdentityLocators {

    private ConfirmIdentityLocators() {}

    // ===== LETTER FLOW =====
    public static final By LETTER_PROCEED_BUTTON   = By.xpath("//*[@id=\"se-letter\"]/div/button");
    public static final By LETTER_CODE_INPUT       = By.id("letter-confirm-modal");
    public static final By LETTER_SUBMIT_OK_BUTTON = By.cssSelector("#letter-confirm-modal-form div:nth-child(2) button");
    public static final By LETTER_SENT_DATE_XPATH  = By.xpath("//*[@id=\"se-letter\"]/div/p[2]");
    public static final By LETTER_VALID_DATE_XPATH = By.xpath("//*[@id=\"se-letter\"]/div/p[3]");
    public static final By LETTER_INSTRUCTION_XPATH= By.xpath("//*[@id=\"se-letter\"]/div/p[4]");
    public static final By LETTER_POPUP_HEADING    = By.cssSelector("#se-letter div dialog div div div div h4");
    public static final By LETTER_POPUP_LABEL      = By.cssSelector("#letter-confirm-modal-wrapper div label");
    public static final By LETTER_POPUP_CLOSE      = By.cssSelector("#se-letter div dialog div div div div button");
    public static final By LETTER_EXPAND_BUTTON    = By.id("se-letter-button");

    // ===== FREJA / EIDAS REF IDP =====
    public static final By SELECT_SIMULATED_USER   = By.id("selectSimulatedUser");
    public static final By ADVANCED_BUTTON         = By.id("advancedButton");
    public static final By PERSONAL_ID_INPUT       = By.id("personalIdNumber");
    public static final By GIVEN_NAME_INPUT        = By.id("givenName");
    public static final By SURNAME_INPUT           = By.id("surname");
    public static final By SUBMIT_BUTTON           = By.id("submitButton");

    // ===== FREJA PROCEED =====
    public static final By FREJA_PROCEED           = By.cssSelector("#se-freja div button");
    public static final By FREJA_ACCEPT            = By.id("eidas-info-modal-accept-button");

    // ===== EIDAS =====
    public static final By EU_PROCEED              = By.cssSelector("#eu div button");

    // ===== FREJA NO SWEDISH PNR =====
    public static final By WORLD_PROCEED           = By.cssSelector("#world div button");

    // ===== BANKID =====
    public static final By BANKID_PROCEED          = By.cssSelector("#se-bankID div button");
    public static final By BANKID_OTHER_DEVICE     = By.xpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]");
    public static final By BANKID_ABORT_SCAN       = By.xpath("//*[@id=\"app\"]/main/div[1]/dialog/button");
    public static final By BANKID_CANCEL_AUTH      = By.xpath("//*[@id=\"app\"]/main/div[2]/button/span");

    // ===== NIN =====
    public static final By NIN_INPUT               = By.id("nin");
    public static final By ADD_NIN_BUTTON          = By.id("add-nin-button");
    public static final By SHOW_HIDE_BUTTON        = By.id("show-hide-button");
}
