package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;

public final class DeleteAccountLocators {

    private DeleteAccountLocators() {}

    public static final By DELETE_BUTTON         = By.id("delete-button");
    public static final By CONFIRM_DELETE_BUTTON = By.id("delete-account-modal-accept-button");
    public static final By CLOSE_MODAL_BUTTON    = By.id("delete-account-modal-close-button");

    public static final By HEADING_XPATH =
            By.cssSelector("#delete-account h2");

    public static final By TEXT_XPATH =
            By.cssSelector("#delete-account p");
}
/*public final class DeleteAccountLocators {
    private DeleteAccountLocators() {}

    public static final String DELETE_BUTTON         = "delete-button";
    public static final String CONFIRM_DELETE_BUTTON = "delete-account-modal-accept-button";
    public static final String CLOSE_MODAL_BUTTON    = "delete-account-modal-close-button";
    public static final String HEADING_XPATH         = "//*[@id=\"delete-account\"]/div/h2";
    public static final String TEXT_XPATH            = "//*[@id=\"delete-account\"]/p";
}*/
