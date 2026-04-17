package se.sunet.eduid.generic;

import org.openqa.selenium.By;

public final class HelpPageLocators {

    private HelpPageLocators() {}

    public static final By HELP_FOOTER_LINK = By.cssSelector("#footer nav ul li:first-child a");
    public static final By HELP_H1          = By.cssSelector("#content section h1");
}
