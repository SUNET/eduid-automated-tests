package se.sunet.eduid.registration;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static se.sunet.eduid.registration.RegisterLocators.*;
import static se.sunet.eduid.utils.Common.log;

/**
 * Page object för registreringssidan.
 *
 * Ansvar:
 *  - Verifiera etiketter på registreringssidan i båda språken.
 *  - Verifiera captcha-sidans etiketter.
 *  - Fylla i formuläret och skicka.
 *  - Verifiera och acceptera/avvisa användarvillkor.
 *  - Generera slumpmässigt användarnamn, identitetsnummer och namn vid behov.
 */
public class Register {

    private final Common   common;
    private final TestData testData;

    public Register(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void runRegister() {
        verifyPageTitle();
        verifyLabels();
        enterEmailAndPressRegister();
        verifyLabelsAtConfirmEmailAddress();
        enterCaptchaCode();
        registerPopUp();
    }

    // -------------------------------------------------------------------------
    // Navigering
    // -------------------------------------------------------------------------

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Registrera | eduID");
    }

    // -------------------------------------------------------------------------
    // Formulärinmatning
    // -------------------------------------------------------------------------

    public void enterEmailAndPressRegister() {
        if (testData.isRegisterAccount()) {
            if (testData.isGenerateUsername()) {
                generateUsername();
                setIdentityNumber();
                setGivenName();
                setSurName();
            }
            testData.setDisplayName(testData.getGivenName() + " " + testData.getSurName());
            log.info("Display name set to: {}", testData.getDisplayName());
        } else {
            log.info("Identity number set to: {}", testData.getIdentityNumber());
            log.info("Using existing display name: {}", testData.getDisplayName());
        }

        log.info("Registering user: {}", testData.getUsername());
        testData.setEmail(testData.getUsername().toLowerCase());

        fillField(GIVEN_NAME_INPUT, testData.getGivenName());
        fillField(SURNAME_INPUT, testData.getSurName());
        fillField(EMAIL_INPUT, testData.getUsername());

        common.waitUntilClickable(REGISTER_BUTTON).click();
    }

    public void enterCaptchaCode() {
        common.addMagicCookie();
        common.findWebElement(CAPTCHA_INPUT).sendKeys("123456");
        common.waitUntilClickable(CAPTCHA_CONTINUE).click();
        log.info("Added captcha code and pressed continue");
    }

    // -------------------------------------------------------------------------
    // Villkors-pop-up
    // -------------------------------------------------------------------------

    public void registerPopUp() {
        // Verifiera på engelska först
        common.timeoutMilliSeconds(1500);
        verifyTermsEnglish();

        // Avvisa och byt till svenska
        common.waitUntilClickable(CANCEL_BUTTON).click();
        common.selectSwedish();

        // Fyll i formuläret igen och öppna pop-upen på svenska
        common.waitUntilClickable(GIVEN_NAME_INPUT).sendKeys(testData.getGivenName());
        common.findWebElement(SURNAME_INPUT).sendKeys(testData.getSurName());
        common.findWebElement(EMAIL_INPUT).sendKeys(testData.getUsername());
        common.waitUntilClickable(REGISTER_BUTTON).click();
        log.info("Pressed register button again");

        verifyTermsSwedish();

        if (testData.isAcceptTerms()) {
            common.click(common.findWebElement(ACCEPT_BUTTON));
        } else {
            common.click(common.findWebElement(CANCEL_BUTTON));
            common.timeoutSeconds(1);
        }
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — registreringssidan
    // -------------------------------------------------------------------------

    private void verifyLabels() {
        verifyLabelsSwedish();
        common.selectEnglish();
        verifyLabelsEnglish();
        // Lämnar engelska aktivt — enterEmailAndPressRegister anropas härnäst
        // och behöver inte ett specifikt språk
    }

    private void verifyLabelsSwedish() {
        log.info("Verifying registration labels — Swedish");
        common.waitUntilClickable(GIVEN_NAME_INPUT);
        String pageBody = common.getPageBody();

        common.verifyPlaceholderBy("förnamn", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("efternamn", SURNAME_INPUT);
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Ange dina personuppgifter");
        common.verifyPageBodyContainsString(pageBody,
                "När du har skapat ditt eduID kan du logga in och koppla det till din identitet.");

        common.verifyString(GIVEN_NAME_LABEL, "Förnamn");
        common.verifyString(SURNAME_LABEL, "Efternamn");
        common.verifyString(EMAIL_LABEL, "E-postadress");
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying registration labels — English");
        common.verifyPageTitle("Register | eduID");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Enter your personal information");
        common.verifyPageBodyContainsString(pageBody,
                "Once you have created an eduID you will be able to log in and connect it to your identity.");

        common.verifyString(GIVEN_NAME_LABEL, "First name");
        common.verifyString(SURNAME_LABEL, "Last name");
        common.verifyString(EMAIL_LABEL, "Email address");

        common.verifyPlaceholderBy("first name", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("last name", SURNAME_INPUT);
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — captcha-sidan
    // -------------------------------------------------------------------------

    private void verifyLabelsAtConfirmEmailAddress() {
        common.waitUntilClickable(CAPTCHA_NEW_IMAGE_BTN);

        // Engelska visas direkt efter registrering — verifiera engelska först
        verifyLabelsAtCaptchaEnglish();
        common.selectSwedish();
        verifyLabelsAtCaptchaSwedish();
        common.selectEnglish();
    }

    private void verifyLabelsAtCaptchaEnglish() {
        common.verifyString(CAPTCHA_PAGE_H1, "Create eduID: Confirm that you are a human");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "As a protection against automated spam, you'll need to confirm that you are a human.");
        common.verifyStringOnPage("Enter the code from the image");
        common.verifyStringOnPage("Generate a new image");
        common.verifyString(CANCEL_CAPTCHA, "CANCEL");
    }

    private void verifyLabelsAtCaptchaSwedish() {
        common.verifyString(CAPTCHA_PAGE_H1, "Skapa eduID: Bekräfta att du är en människa");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "Som ett skydd mot automatisk spam måste du bekräfta att du är en människa.");
        common.verifyStringOnPage("Ange koden från bilden");
        common.verifyStringOnPage("Generera en ny bild");
        common.verifyString(CANCEL_CAPTCHA, "AVBRYT");
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — användarvillkor
    // -------------------------------------------------------------------------

    private void verifyTermsSwedish() {
        log.info("Verifying terms — Swedish");
        common.waitUntilClickable(CANCEL_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Godkänn användarvillkor");
        common.verifyPageBodyContainsString(pageBody,
                "För att skapa ditt eduID måste du acceptera användarvillkoren för eduID.");
        common.verifyPageBodyContainsString(pageBody, "För eduID.se gäller generellt");
        common.verifyPageBodyContainsString(pageBody,
                "att all användning av användarkonton ska följa Sveriges lagar och förordningar,");
        common.verifyPageBodyContainsString(pageBody,
                "att man är sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,");
        common.verifyPageBodyContainsString(pageBody,
                "att användarkonton, lösenord, säkerhetsnycklar och koder är personliga och får endast användas av innehavaren,");
        common.verifyPageBodyContainsString(pageBody,
                "att SUNET:s etiska regler (enligt nedan) reglerar övrig tillåten användning.");
        common.verifyPageBodyContainsString(pageBody, "SUNET bedömer som oetiskt när någon:");
        common.verifyPageBodyContainsString(pageBody,
                "försöker få tillgång till nätverksresurser utan att ha rätt till det");
        common.verifyPageBodyContainsString(pageBody, "försöker dölja sin användaridentitet");
        common.verifyPageBodyContainsString(pageBody,
                "försöker störa eller avbryta den avsedda användningen av nätverken");
        common.verifyPageBodyContainsString(pageBody,
                "uppenbart slösar med tillgängliga resurser (personal, maskinvara eller programvara)");
        common.verifyPageBodyContainsString(pageBody, "gör intrång i andras privatliv");
        common.verifyPageBodyContainsString(pageBody, "försöker förolämpa eller förnedra andra");
        common.verifyPageBodyContainsString(pageBody,
                "Den som överträder, eller misstänks överträda, ovanstående regler kan stängas av från eduID.se. " +
                "Dessutom kan rättsliga åtgärder komma att vidtas.");
    }

    private void verifyTermsEnglish() {
        log.info("Verifying terms — English");
        common.waitUntilClickable(CANCEL_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Accept Terms of Use");
        common.verifyPageBodyContainsString(pageBody,
                "To create your eduID you need to accept the eduID terms of use.");
        common.verifyPageBodyContainsString(pageBody, "The following generally applies:");
        common.verifyPageBodyContainsString(pageBody,
                "that all usage of user accounts follow the laws and by-laws of Sweden,");
        common.verifyPageBodyContainsString(pageBody,
                "that all personal information that you provide, such as name and contact information shall be truthful,");
        common.verifyPageBodyContainsString(pageBody,
                "that user accounts, password, security keys and codes are individual and shall only be used by the intended individual,");
        common.verifyPageBodyContainsString(pageBody,
                "that SUNET's ethical rules (listed below) regulate other permitted usage.");
        common.verifyPageBodyContainsString(pageBody, "SUNET judges unethical behaviour to be when someone:");
        common.verifyPageBodyContainsString(pageBody,
                "attempts to gain access to network resources that they do not have the right to");
        common.verifyPageBodyContainsString(pageBody, "attempts to conceal their user identity");
        common.verifyPageBodyContainsString(pageBody,
                "attempts to interfere or disrupt the intended usage of the network");
        common.verifyPageBodyContainsString(pageBody,
                "clearly wastes available resources (personnel, hardware or software)");
        common.verifyPageBodyContainsString(pageBody,
                "attempts to disrupt or destroy computer-based information");
        common.verifyPageBodyContainsString(pageBody, "infringes on the privacy of others");
        common.verifyPageBodyContainsString(pageBody, "attempts to insult or offend others");
        common.verifyPageBodyContainsString(pageBody,
                "Any person found violating or suspected of violating these rules can be disabled from " +
                "eduID.se for investigation. Furthermore, legal action may be taken.");
    }

    // -------------------------------------------------------------------------
    // Slumpgeneration av testdata
    // -------------------------------------------------------------------------

    private void generateUsername() {
        String chars = "0123456789abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt((int) (chars.length() * Math.random())));
        }
        testData.setUsername(sb + "@dev.eduid.sunet.se");
    }

    private void setIdentityNumber() {
        testData.setIdentityNumber(readRandomLineFromFile(testData.getIdentityNumberFilePath()));
        log.info("Identity number set to: {}", testData.getIdentityNumber());
    }

    private void setGivenName() {
        testData.setGivenName(readRandomLineFromFile(testData.getGivenNameFilePath()));
        log.info("First name set to: {}", testData.getGivenName());
    }

    private void setSurName() {
        testData.setSurName(readRandomLineFromFile(testData.getSurNameFilePath()));
        log.info("Sur name set to: {}", testData.getSurName());
    }

    /**
     * Läser en slumpmässig rad från en fil.
     * Kastar {@link RuntimeException} om filen inte kan läsas — fel i testdata-konfigurationen
     * bör inte tystas ned med en tyst catch.
     */
    private String readRandomLineFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return lines.get(new Random().nextInt(lines.size()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from file: " + filePath, e);
        }
    }

    // -------------------------------------------------------------------------
    // Hjälpare
    // -------------------------------------------------------------------------

    private void fillField(By locator, String value) {
        var field = common.findWebElement(locator);
        field.clear();
        field.sendKeys(value);
    }
}
