package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;

public final class RequestResetPwEmailLocators {

    private RequestResetPwEmailLocators() {}

    // SEND_EMAIL_BUTTON — second button inside the content div (first is Go Back)
    public static final By SEND_EMAIL_BUTTON = By.cssSelector("#content div button:nth-of-type(2)");

    public static final By GO_BACK_BUTTON    = By.id("go-back-button");
}
