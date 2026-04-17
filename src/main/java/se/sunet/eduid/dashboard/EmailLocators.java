package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class EmailLocators {

    private EmailLocators() {}

    // ===== ADD EMAIL FORM =====
    public static final By ADD_MORE_BUTTON      = By.id("emails-add-more-button");
    public static final By EMAIL_INPUT          = By.id("email");
    public static final By ADD_BUTTON          = By.id("add-email");
    public static final By CANCEL_BUTTON        = By.id("cancel-adding-email");

    // ===== CONFIRM CODE POP-UP =====
    public static final By CONFIRM_CODE_INPUT   = By.id("email-confirm-modal");
    public static final By CONFIRM_OK_BUTTON    = By.cssSelector("#email-confirm-modal-form div:nth-child(2) button");
    public static final By CLOSE_CONFIRM_POPUP  = By.cssSelector("#add-email-addresses dialog div div div div button");
    public static final By CONFIRM_POPUP_HEADER = By.cssSelector("#add-email-addresses dialog div div div div h4");
    public static final By CONFIRM_POPUP_LABEL  = By.cssSelector("#email-confirm-modal-wrapper div label");
    public static final By SEND_NEW_CODE_BUTTON = By.cssSelector("#email-confirm-modal-form div:first-child div:nth-child(2) button");

    // ===== EMAIL TABLE ROWS =====
    public static final By EMAIL_ROW_2_LABEL    = By.cssSelector("#add-email-addresses div div table tbody tr:nth-child(2) td:nth-child(2) span");
    public static final By EMAIL_ROW_3_LABEL    = By.cssSelector("#add-email-addresses div div table tbody tr:nth-child(3) td:nth-child(2) button");
    public static final By CONFIRM_ROW_3_BUTTON = By.cssSelector("#content article:nth-child(2) div div table tbody tr:nth-child(3) td:nth-child(2) button");
    public static final By REMOVE_ROW_3_BUTTON  = By.cssSelector("#content article:nth-child(2) div div table tbody tr:nth-child(3) td:nth-child(3) button");
}
