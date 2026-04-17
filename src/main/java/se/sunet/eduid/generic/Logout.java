package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;

import static se.sunet.eduid.generic.LogoutPageLocators.*;
import static se.sunet.eduid.generic.StartPageLocators.SIGN_UP_BUTTON;

/**
 * Page object for the logout flow.
 *
 * After logout the user lands on the start page, so label verification
 * is delegated to StartPage — no duplication of start-page assertions here.
 */
public class Logout {

    private final Common    common;
    private final StartPage startPage;

    public Logout(Common common, StartPage startPage) {
        this.common    = common;
        this.startPage = startPage;
    }

    public void runLogout() {
        logout();
        verifyReturnToStartPage();
    }

    private void logout() {
        common.expandNavigationMenu();
        common.click(common.waitUntilClickable(LOGOUT_BUTTON));
    }

    private void verifyReturnToStartPage() {
        // Wait for start page to be ready before delegating label checks.
        common.waitUntilClickable(SIGN_UP_BUTTON);
        startPage.verifyLabelsSwedish();
    }
}
