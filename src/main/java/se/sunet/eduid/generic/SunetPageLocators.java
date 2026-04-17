package se.sunet.eduid.generic;

import org.openqa.selenium.By;

public final class SunetPageLocators {

    private SunetPageLocators() {}

    public static final By PARAGRAPH_1 = By.cssSelector("#primary-content div div main article div p:nth-child(1)");
    public static final By PARAGRAPH_2 = By.cssSelector("#primary-content div div main article div p:nth-child(2)");
}
