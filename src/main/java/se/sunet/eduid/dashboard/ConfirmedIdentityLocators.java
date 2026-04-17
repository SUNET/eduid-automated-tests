package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class ConfirmedIdentityLocators {

    private ConfirmedIdentityLocators() {}

    public static final By PAGE_H1                 = By.cssSelector("#content section h1");
    public static final By REMOVE_ID_BUTTON        = By.id("remove-identity-nin");
    public static final By REMOVE_EIDAS_ID_BUTTON  = By.id("remove-identity-eidas");
    public static final By ID_NUMBER_DISPLAY       = By.cssSelector("#verify-identity figure div:nth-child(3) div div");
    public static final By SHOW_HIDE_BUTTON        = By.id("show-hide-button");
}
