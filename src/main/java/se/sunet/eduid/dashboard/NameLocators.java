package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class NameLocators {

    private NameLocators() {}

    // ===== NAME FORM =====
    public static final By CHANGE_BUTTON        = By.cssSelector( "#personal-data div.heading button");
    public static final By DISPLAY_NAME_LABEL   = By.cssSelector("#personal-data div.personal-data-info div:nth-child(3) span strong");
    public static final By SAVE_BUTTON          = By.id("personal-data-button");
    public static final By ADD_PERSONAL_DATA    = By.id("add-personal-data");

    // ===== INPUTS =====
    public static final By GIVEN_NAME_INPUT     = By.id("given_name");
    public static final By SURNAME_INPUT        = By.id("surname");

    // ===== DISPLAY ELEMENTS =====
    public static final By FIRST_NAME_DISPLAY   = By.id("first name");
    public static final By LAST_NAME_DISPLAY    = By.id("last name");
    public static final By DISPLAY_NAME_DISPLAY = By.id("display name");
}
