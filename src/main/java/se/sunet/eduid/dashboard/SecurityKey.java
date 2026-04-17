package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.util.Objects;

import static se.sunet.eduid.dashboard.SecurityKeyLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object for the Security / security key management page.
 */
public class SecurityKey {

    private final Common   common;
    private final TestData testData;

    private static final String KEY_NAME = "test-key1";

    public SecurityKey(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runSecurityKey() {
        navigateToSecurityPage();
        verifySecurityLabels();
        addSecurityKey();
    }

    public void deleteSecurityKey() {
        log.info("Delete security key — verifying pop-up labels");
        common.click(common.findWebElement(REMOVE_KEY_BUTTON));
        common.securityConfirmPopUp(
                "//*[@id=\"remove-webauthn\"]",
                "Obs: Din säkerhetsnyckel " + KEY_NAME + " kommer att tas bort efter inloggningen.",
                "Note: Your security key " + KEY_NAME + " will be removed after you log in.");
        log.info("Delete security key — pressed Accept");
    }

    public void verifiedSecurityKey() {
        common.waitUntilClickable(REMOVE_KEY_BUTTON);
        common.verifyString(VERIFIED_STATUS_LABEL, "VERIFIERAD");
        common.selectEnglish();
        common.verifyString(VERIFIED_STATUS_LABEL, "VERIFIED");
        common.selectSwedish();
    }

    public void virtualAuthenticator() {
        if (!common.isCookieSet("autotests")) {
            common.addMagicCookie();
        }
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);
        VirtualAuthenticator authenticator =
                ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);
        authenticator.setUserVerified(true);
        log.info("Virtual authenticator created with USB");
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    private void navigateToSecurityPage() {
        common.navigateToSecurity();
        common.waitUntilVisible(SECURITY_PAGE_HEADING);
    }

    // -------------------------------------------------------------------------
    // Add security key flow
    // -------------------------------------------------------------------------

    private void addSecurityKey() {
        if (testData.isAddInternalPassKey()) {
            common.createVirtualWebAuthn();
        } else if (testData.isAddExternalSecurityKey()) {
            virtualAuthenticator();
            common.selectSwedish();
            common.findWebElement(ADD_EXTERNAL_KEY_BUTTON).click();
        }

        if (testData.getTestClassName().contains("TC_50")) {
            verifyAddSecurityKeyLabelsSwedish(ADD_EXTERNAL_KEY_BUTTON);
            common.selectEnglish();
            verifyAddSecurityKeyLabelsEnglish(ADD_EXTERNAL_KEY_BUTTON);
            common.click(common.findWebElement(CLOSE_POPUP_BUTTON));

            verifyAddSecurityKeyLabelsEnglish(ADD_INTERNAL_KEY_BUTTON);
            common.selectSwedish();
            verifyAddSecurityKeyLabelsSwedish(ADD_INTERNAL_KEY_BUTTON);

            common.click(common.findWebElement(ADD_INTERNAL_KEY_BUTTON));
        }

        // Name the key and confirm
        common.findWebElement(SECURITY_KEY_NAME_INPUT).sendKeys(KEY_NAME);
        common.click(common.findWebElement(CONFIRM_NAME_OK_BUTTON));
        log.info("Added security key '{}' and clicked OK", KEY_NAME);
        common.timeoutMilliSeconds(500);

        verifyAfterKeyAdded();
    }

    private void verifyAfterKeyAdded() {
        if (testData.isVerifySecurityKeyByFreja()) {
            verifyAndProceedWithMethod("Freja",
                    VERIFY_FREJA_BUTTON,
                    FREJA_CONFIRM_BUTTON,
                    "Obs: använd säkerhetsnyckeln " + KEY_NAME + " vid inloggningen. Efter inloggning omdirigeras du till FREJA för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key " + KEY_NAME + " during the login process. After logging in, you will be redirected to FREJA page to verify your security key.");

        } else if (testData.isVerifySecurityKeyByBankId()) {
            verifyAndProceedWithMethod("BankID",
                    VERIFY_BANKID_BUTTON,
                    BANKID_CONFIRM_BUTTON,
                    "Obs: använd säkerhetsnyckeln " + KEY_NAME + " vid inloggningen. Efter inloggning omdirigeras du till BANKID för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key " + KEY_NAME + " during the login process. After logging in, you will be redirected to BANKID page to verify your security key.");

        } else if (testData.isVerifySecurityKeyByEidas()) {
            verifyAndProceedWithMethod("eIDAS",
                    VERIFY_EIDAS_BUTTON,
                    EIDAS_CONFIRM_BUTTON,
                    "Obs: använd säkerhetsnyckeln " + KEY_NAME + " vid inloggningen. Efter inloggning omdirigeras du till EIDAS för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key " + KEY_NAME + " during the login process. After logging in, you will be redirected to EIDAS page to verify your security key.");

            common.timeoutSeconds(3);
            if (Objects.requireNonNull(common.getWebDriver().getTitle()).equalsIgnoreCase("Säkerhet | eduID")) {
                log.info("Still on security page — clicking eIDAS again: {}", testData.getMfaMethod());
                common.findWebElementByXpath("//*[@id=\"manage-security-keys\"]/figure/div/div[3]/span/button[3]").click();
            }

        } else if (testData.isVerifySecurityKeyByFrejaeID()) {
            common.addNinCookie();
            verifyAndProceedWithMethod("Freja eID",
                    VERIFY_FREJA_EID_BUTTON,
                    FREJA_EID_CONFIRM_BUTTON,
                    "Obs: använd säkerhetsnyckeln " + KEY_NAME + " vid inloggningen. Efter inloggning omdirigeras du till FREJA för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key " + KEY_NAME + " during the login process. After logging in, you will be redirected to FREJA page to verify your security key.");

        } else {
            // No immediate verification — close the modal and verify unverified-state labels
            common.findWebElement(CLOSE_VERIFY_MODAL).click();
            log.info("Security key added — user chose not to verify immediately");
            verifyAddedKeyLabelsSwedish();
            verifyAddedKeyLabelsEnglish();
        }
    }

    /**
     * Shared pattern for all "verify with X" flows: show popup labels, click the method button,
     * then handle the security confirm popup.
     */
    private void verifyAndProceedWithMethod(String methodName, By verifyButton,
                                             By confirmPopup, String swe, String eng) {
        log.info("User selecting {} for immediate security key verification", methodName);
        verifySecurityKeyConfirmPopupLabels();
        common.waitUntilClickable(verifyButton).click();
        common.securityConfirmPopUpBy(confirmPopup, swe, eng);
    }

    // -------------------------------------------------------------------------
    // Label verification
    // -------------------------------------------------------------------------

    public void verifySecurityLabels() {
        log.info("Verifying security page labels — Swedish");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Säkerhet");
        common.verifyPageBodyContainsString(pageBody, "Öka och hantera säkerheten för ditt eduID.");
        common.verifyPageBodyContainsString(pageBody, "Lägg till multifaktorautentisering (MFA)");
        common.verifyPageBodyContainsString(pageBody,
                "Om möjligt lägg till ett ytterligare sätt att identifiera dig i form av en säkerhetsnyckel, " +
                "utöver användarnamn och lösenord, för att vara säker på att bara du har tillgång till ditt eduID. " +
                "Exempelvis en separat USB-säkerhetsnyckel som du kan skaffa, eller inbyggda passkey/lösennyckel-" +
                "funktioner i din enhet som använder biometri eller pinkod.");
        common.verifyPageBodyContainsString(pageBody,
                "Obs: Tillagda säkerhetsnycklar är personliga och ska inte delas med andra. Det är för att " +
                "säkerställa att endast du som kontohavare har tillgång till ditt konto.");
        common.verifyPageBodyContainsString(pageBody,
                "Du kan läsa mer om säkerhetsnycklar som stöds i hjälpavsnittet: Utökad säkerhet med ditt eduID.");
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article[1]/p[3]/a");
        common.verifyPageBodyContainsString(pageBody, "Lägg till en ny säkerhetsnyckel:");
        common.verifyString(ADD_EXTERNAL_KEY_BUTTON, "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody, "Din externa USB-säkerhetsnyckel");
        if (isNonHeadlessChrome()) {
            common.verifyString(ADD_INTERNAL_KEY_BUTTON, "DEN HÄR ENHETEN");
            common.verifyPageBodyContainsString(pageBody, "Inbyggd passkey i din mobil eller laptop");
        }

        common.selectEnglish();
        log.info("Verifying security page labels — English");

        common.verifyPageTitle("Security | eduID");
        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Security");
        common.verifyPageBodyContainsString(pageBody, "Enhance and manage the security of your eduID.");
        common.verifyPageBodyContainsString(pageBody, "Add multi-factor Authentication (MFA)");
        common.verifyPageBodyContainsString(pageBody,
                "If possible add a security key as a second factor of authentication, beyond username and password, " +
                "to prove you are the owner of your eduID. Examples are separate physical USB security keys that you " +
                "can get, or built-in passkey features on your device, such as biometrics or pins.");
        common.verifyPageBodyContainsString(pageBody,
                "Note: Added security keys are personal and not to be shared with others. This is to ensure that " +
                "access to your account is limited solely to you, the account holder.");
        common.verifyPageBodyContainsString(pageBody,
                "You can read more about supported security keys in the Help section: Improving the security level of eduID.");
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article[1]/p[3]/a");
        common.verifyPageBodyContainsString(pageBody, "Add a new security key:");
        common.verifyString(ADD_EXTERNAL_KEY_BUTTON, "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody, "Your external USB security key");
        if (isNonHeadlessChrome()) {
            common.verifyString(ADD_INTERNAL_KEY_BUTTON, "THIS DEVICE");
            common.verifyPageBodyContainsString(pageBody, "Internal passkey on your phone or laptop");
        }

        log.info("Done verifying security page labels — English");
        common.selectSwedish();
    }

    private void verifyAddedKeyLabelsSwedish() {
        log.info("Verifying added (unverified) security key labels — Swedish");
        common.waitUntilClickable(TOGGLE_SWITCH);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Använd alltid säkerhetsnyckel för att logga in");
        common.verifyPageBodyContainsString(pageBody,
                "Om denna stängs av behöver du bara använda din säkerhetsnyckel när andra tjänster kräver " +
                "extra verifiering vid inloggning.");
        common.verifyPageBodyContainsString(pageBody, "Hantera dina säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Namn: " + KEY_NAME);
        common.verifyPageBodyContainsString(pageBody, "Skapad: " + common.getDate());
        common.verifyPageBodyContainsString(pageBody, "Använd: Aldrig använd");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "BANKID");
        common.verifyPageBodyContainsString(pageBody, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "FREJA EID");
        common.waitUntilClickable(REMOVE_KEY_BUTTON);
    }

    private void verifyAddedKeyLabelsEnglish() {
        common.selectEnglish();
        log.info("Verifying added (unverified) security key labels — English");
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody,
                "Always use a security key to log in\n" +
                "Turning this off you only need to use your security key for services that require extra login verification.");
        common.verifyPageBodyContainsString(pageBody, "Manage your security keys");
        common.verifyPageBodyContainsString(pageBody, "Name: " + KEY_NAME);
        common.verifyPageBodyContainsString(pageBody, "Created: " + common.getDate());
        common.verifyPageBodyContainsString(pageBody, "Used: Never used");
        common.verifyPageBodyContainsString(pageBody, "Verify with:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "BANKID");
        common.verifyPageBodyContainsString(pageBody, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "FREJA EID");
        common.waitUntilClickable(REMOVE_KEY_BUTTON);

        common.selectSwedish();
    }

    private void verifyAddSecurityKeyLabelsSwedish(By buttonId) {
        log.info("Verifying add security key pop-up labels for button '{}' — Swedish", buttonId);
        //common.selectSwedish();
        common.click(common.findWebElement(buttonId));
        common.waitUntilClickable(CLOSE_POPUP_BUTTON);

        common.verifyString(POPUP_HEADING_XPATH, "Ge ett namn till din säkerhetsnyckel");
        common.verifyString(POPUP_NOTE_XPATH,
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyString(POPUP_LABEL_XPATH, "Säkerhetsnyckel");
        common.verifyString(POPUP_MAX_CHARS_XPATH, "max 50 tecken");
        common.verifyPlaceholderBy("beskriv din säkerhetsnyckel", SECURITY_KEY_NAME_INPUT);

        common.click(common.findWebElement(CLOSE_POPUP_BUTTON));
    }

    private void verifyAddSecurityKeyLabelsEnglish(By buttonId) {
        log.info("Verifying add security key pop-up labels for button '{}' — English", buttonId);
        common.click(common.findWebElement(buttonId));
        common.waitUntilClickable(CLOSE_POPUP_BUTTON);

        common.verifyString(POPUP_HEADING_XPATH, "Add a name for your security key");
        common.verifyString(POPUP_NOTE_XPATH,
                "Note: this is only for your own use to be able to distinguish between your added keys.");
        common.verifyString(POPUP_LABEL_XPATH, "Security key");
        common.verifyString(POPUP_MAX_CHARS_XPATH, "max 50 characters");
        common.verifyPlaceholderBy("describe your security key", SECURITY_KEY_NAME_INPUT);
    }

    private void verifySecurityKeyConfirmPopupLabels() {
        log.info("Verifying security key verification pop-up labels — Swedish");
        common.waitUntilClickable(VERIFY_BANKID_BUTTON);

        common.verifyStringOnPage("Verifiera din tillagda säkerhetsnyckel");
        common.verifyStringOnPage("\nVerifiera din säkerhetsnyckel med hjälp av knapparna för BankID, Freja+, eIDAS eller Freja eID nedan.");
        common.verifyStringOnPage("Obs: dina tillagda säkerhetsnycklar kan även verifieras senare i inställningarna under \"Hantera dina säkerhetsnycklar\".");

        common.verifyString(VERIFY_BANKID_BUTTON, "BANKID");
        common.verifyString(VERIFY_FREJA_BUTTON, "FREJA+");
        common.verifyString(VERIFY_EIDAS_BUTTON, "EIDAS");
        common.verifyString(VERIFY_FREJA_EID_BUTTON, "FREJA EID");
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private boolean isNonHeadlessChrome() {
        return testData.getBrowser().equalsIgnoreCase("chrome")
                && testData.getHeadlessExecution().equalsIgnoreCase("false");
    }
}
