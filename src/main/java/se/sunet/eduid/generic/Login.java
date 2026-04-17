package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.generic.LoginPageLocators.*;

/**
 * Page object for the eduID login page.
 *
 * Responsibilities:
 *  - Navigate the login flow based on the resolved LoginScenario.
 *  - Verify page text/labels in both Swedish and English.
 *  - Store the EPPN after a successful login.
 *
 * This class intentionally contains no TestNG/JUnit assertions.
 * Any assertion failures are propagated as exceptions to the test layer.
 */
public class Login {

    private final Common   common;
    private final TestData testData;

    public Login(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runLogin() {
        verifyPageTitle();

        switch (LoginScenario.resolve(testData)) {
            case RESET_PASSWORD   -> navigateToResetPassword();
            case REGISTER_ACCOUNT -> navigateToRegister();
            case PASSKEY          -> loginWithPasskey();
            case RE_LOGIN         -> loginWithPasswordOnly();
            case STANDARD         -> loginWithUsernameAndPassword();
        }
    }

    public void verifyPageTitle() {
        common.waitUntilPageTitleContains("Logga in | eduID");
        verifyTextAndLabels();
    }

    public void enterUsername() {
        WebElement field = common.findWebElement(USERNAME_INPUT);
        field.clear();
        field.sendKeys(testData.getUsername());
        Common.log.info("Logging in with username: {}", testData.getUsername());
    }

    public void enterPassword() {
        WebElement field = common.findWebElement(PASSWORD_INPUT);
        field.clear();
        field.sendKeys(testData.getPassword());
        Common.log.info("Logging in with password: {}", testData.getPassword());
    }

    public void clickLoginButton() {
        common.findWebElement(LOGIN_BUTTON).click();
    }

    /**
     * Completes sign-in and verifies the outcome based on the current test scenario.
     * Throws {@link IllegalStateException} if EPPN cannot be stored after a successful login.
     */
    public void signIn() {
        if (testData.isAccountDeleted()) {
            verifyDeletedAccountPage();
            return;
        }

        clickLoginButton();

        if (testData.isIncorrectPassword()) {
            verifyIncorrectPasswordError();
        } else {
            storeEppn();
        }
    }

    public void storeEppn() {
        common.waitUntilClickable(EPPN_COPY_BUTTON);

        String eppn = common.findWebElement(USER_EPPN).getDomAttribute("value");
        if (eppn == null || eppn.isEmpty()) {
            throw new IllegalStateException("EPPN was empty after login — login may have failed.");
        }

        testData.setEppn(eppn);
        Common.log.info("Saved EPPN: {}", eppn);
    }

    public void clickRestoreDeletedAccountButton() {
        common.findWebElement(DELETED_RESTORE_BTN).click();
    }

    // -------------------------------------------------------------------------
    // Login strategies
    // -------------------------------------------------------------------------

    private void navigateToResetPassword() {
        common.click(common.findWebElement(FORGOT_PASSWORD_LINK));
        common.waitUntilClickable(By.id("go-back-button"));
    }

    private void navigateToRegister() {
        common.click(common.findWebElement(REGISTER_LINK));
        common.waitUntilClickable(REGISTER_CONFIRM);
    }

    private void loginWithPasskey() {
        common.findWebElement(PASSKEY_BUTTON).click();
        Common.log.info("Clicked 'Login with passkey'");
    }

    private void loginWithPasswordOnly() {
        enterPassword();
        signIn();
    }

    private void loginWithUsernameAndPassword() {
        enterUsername();
        enterPassword();
        signIn();
    }

    // -------------------------------------------------------------------------
    // Sign-in outcome verification
    // -------------------------------------------------------------------------

    private void verifyIncorrectPasswordError() {
        common.timeoutMilliSeconds(500);

        common.verifyStatusMessage("E-postadressen eller lösenordet är felaktigt.");
        common.selectEnglish();
        common.verifyStatusMessage("The email address or password was incorrect.");
        common.selectSwedish();
    }

    private void verifyDeletedAccountPage() {
        common.waitUntilClickable(DELETED_RESTORE_BTN);

        verifyDeletedAccountSwedish();
        common.selectEnglish();
        verifyDeletedAccountEnglish();
        common.selectSwedish();
    }

    private void verifyDeletedAccountSwedish() {
        common.verifyStatusMessage(
            "Detta konto har avslutats, men finns kvar några dagar. Gör en " +
            "lösenordsåterställning för att ångra avslutet."
        );
        common.verifyStringOnPage("Raderat konto");
        common.verifyStringOnPage(
            "Kontot har nyligen raderats och kan inte användas för inloggning." +
            " Under en kort tid därefter kan kontot återupptas genom att återställa lösenordet med hjälp av länken nedan."
        );
        common.verifyStringOnPage("Till återställ lösenord");
        common.verifyStringOnPage(
            "Gå till startsidan genom att klicka på eduIDs logo i " +
            "sidhuvudet för att logga in med ett annat konto, eller skapa ett nytt konto med knappen Skapa eduID."
        );
    }

    private void verifyDeletedAccountEnglish() {
        common.verifyStatusMessage(
            "This account has been terminated, but is still present. Perform a password " +
            "reset to cancel termination."
        );
        common.verifyStringOnPage("Account terminated");
        common.verifyStringOnPage(
            "This account has recently been terminated and can not be used " +
            "to log in. It is possible to re-activate the account shortly afterwards by resetting the password using the link below."
        );
        common.verifyStringOnPage("Go to reset password page");
        common.verifyStringOnPage(
            "To log in with another account go to the start page by clicking " +
            "the eduID logo in the header, or create a new account using the Create eduID button."
        );
    }

    // -------------------------------------------------------------------------
    // Label verification
    // -------------------------------------------------------------------------

    private void verifyTextAndLabels() {
        common.waitUntilClickable(LOGIN_BUTTON);

        verifySwedishLabels();
        common.selectEnglish();
        verifyEnglishLabels();
        common.selectSwedish();
    }

    private void verifySwedishLabels() {
        String body = common.getPageBody();
        Common.log.info("Verifying text and labels in Swedish");

        if (testData.isReLogin() && testData.isRememberMe()) {
            verifyReLoginLabelsSwedish(body);
        } else if (testData.isDeleteButton()) {
            verifyDeleteButtonLabelsSwedish(body);
        } else {
            verifyStandardLabelsSwedish(body);
        }

        verifyCommonLabelsSwedish(body);
    }

    private void verifyEnglishLabels() {
        String body = common.getPageBody();
        Common.log.info("Verifying text and labels in English");

        if (testData.isReLogin() && testData.isRememberMe()) {
            verifyReLoginLabelsEnglish(body);
        } else if (testData.isDeleteButton()) {
            verifyDeleteButtonLabelsEnglish(body);
        } else {
            verifyStandardLabelsEnglish(body);
        }

        verifyCommonLabelsEnglish(body);
    }

    // --- Swedish scenario labels ---

    private void verifyReLoginLabelsSwedish(String body) {
        common.verifyPageBodyContainsString(body, "Logga in: med lösenord");
        common.verifyPageBodyContainsString(body, "Välkommen tillbaka, " + testData.getDisplayName());
        common.verifyPageBodyContainsString(body, "Inte du?");
    }

    private void verifyDeleteButtonLabelsSwedish(String body) {
        common.verifyPageBodyContainsString(body, "Återautentisering: med lösenord");
        common.verifyPageBodyContainsString(body, "Autentisera dig för att fortsätta");
        common.verifyPageBodyContainsString(body, "Efteråt omdirigeras du till sidan för att radera konto.");
        common.verifyPageBodyContainsString(body,
            "Om du vill avbryta utan att spara förändringen kan du återvända direkt till sidan Konto.");
        common.verifyLocatorIsWorkingLink(RETURN_TO_ACCOUNT_LINK);
    }

    private void verifyStandardLabelsSwedish(String body) {
        common.verifyPageBodyContainsString(body, "Logga in: med lösenord eller passkey");
        common.verifyPageBodyContainsString(body, "Snabbare och enklare autentisering");
        common.verifyPageBodyContainsString(body,
            "Om du har lagt till en passkey för eduid.se kan du logga in säkert mha " +
            "fingeravtryck, ansiktsigenkänning, PIN-kod eller andra skärmlåsmetoder.");
        common.verifyPageBodyContainsString(body,
            "Läs mer om att logga in m.h.a. passkeys i avsnittet \"Användning av eduID\" i Hjälp.");
        common.verifyLocatorIsWorkingLink(PASSKEY_HELP_LINK);
        common.verifyString(PASSKEY_BUTTON, "LOGGA IN MED PASSKEY");
        common.verifyPlaceholderBy("e-post eller unikt ID", USERNAME_INPUT);
    }

    private void verifyCommonLabelsSwedish(String body) {
        common.verifyPageBodyContainsString(body, "Användarnamn");
        common.verifyPageBodyContainsString(body, "Lösenord");
        common.verifyString(SHOW_PASSWORD_BUTTON, "VISA");

        if (testData.isDeleteButton()) return;

        common.verifyStringByXpath("//*[@id=\"link-forgot-password\"]", "Glömt ditt lösenord?");
        common.verifyPageBodyContainsString(body, "Kom ihåg mig på den här enheten");
        verifyRememberMeTextSwedish(body);
        common.verifyPlaceholderBy("ange lösenord", PASSWORD_INPUT);
        common.verifyString(LOGIN_BUTTON, "LOGGA IN");
        common.verifyString(OTHER_DEVICE_BUTTON, "ANNAN ENHET");
        common.verifyString(ABORT_BUTTON, "AVBRYT");
    }

    private void verifyRememberMeTextSwedish(String body) {
        if (testData.isRememberMe()) {
            common.verifyPageBodyContainsString(body,
                "Om denna stängs av kommer du till inloggning med användarnamn och lösenord istället.");
        } else if (!testData.isMfaDisabled()) {
            common.verifyPageBodyContainsString(body,
                "Genom att tillåta eduID att komma ihåg dig på den här enheten kan inloggningen göras enklare och säkrare");
        }
    }

    // --- English scenario labels ---

    private void verifyReLoginLabelsEnglish(String body) {
        common.verifyPageBodyContainsString(body, "Log in: with Password");
        common.verifyPageBodyContainsString(body, "Welcome back, " + testData.getDisplayName());
        common.verifyPageBodyContainsString(body, "Not you?");
    }

    private void verifyDeleteButtonLabelsEnglish(String body) {
        common.verifyPageBodyContainsString(body, "Re-authentication: with Password");
        common.verifyPageBodyContainsString(body, "Authenticate to continue");
        common.verifyPageBodyContainsString(body, "Afterward, you will be redirected to the page to delete account.");
        common.verifyPageBodyContainsString(body,
            "If you wish to cancel this process without affecting a change you can return straight to Account page.");
        common.verifyLocatorIsWorkingLink(RETURN_TO_ACCOUNT_LINK);
    }

    private void verifyStandardLabelsEnglish(String body) {
        common.verifyPageBodyContainsString(body, "Log in: with Password or Passkey");
        common.verifyPageBodyContainsString(body, "Faster and safer way to authenticate");
        common.verifyPageBodyContainsString(body,
            "If you have registered a passkey for eduID.se you can log in securely using your " +
            "fingerprint, face recognition, PIN code or other screen-lock methods.");
        common.verifyPageBodyContainsString(body,
            "Read more about logging in using passkeys in the \"Using eduID\" section in eduID Help.");
        common.verifyLocatorIsWorkingLink(PASSKEY_HELP_LINK);
        common.verifyString(PASSKEY_BUTTON, "LOG IN WITH PASSKEY");
        common.verifyPlaceholderBy("email or unique ID", USERNAME_INPUT);
    }

    private void verifyCommonLabelsEnglish(String body) {
        common.verifyPageBodyContainsString(body, "Username");
        common.verifyPageBodyContainsString(body, "Password");
        common.verifyString(SHOW_PASSWORD_BUTTON, "SHOW");

        if (testData.isDeleteButton()) return;

        common.verifyStringByXpath("//*[@id=\"link-forgot-password\"]", "Forgot your password?");
        common.verifyPageBodyContainsString(body, "Remember me on this device");
        verifyRememberMeTextEnglish(body);
        common.verifyPlaceholderBy("enter password", PASSWORD_INPUT);
        common.verifyString(LOGIN_BUTTON, "LOG IN");
        common.verifyString(OTHER_DEVICE_BUTTON, "OTHER DEVICE");
        common.verifyString(ABORT_BUTTON, "CANCEL");
    }

    private void verifyRememberMeTextEnglish(String body) {
        if (testData.isRememberMe()) {
            common.verifyPageBodyContainsString(body,
                "Turning this off will enable login with username and password instead.");
        } else if (!testData.isMfaDisabled()) {
            common.verifyPageBodyContainsString(body,
                "Allowing eduID to remember you on this device makes logging in easier and more secure");
        }
    }
}