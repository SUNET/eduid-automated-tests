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
        if(testData.isRegisterWithBankId()){
            log.info("Registering with BankID");

            generateUsername();
            setIdentityNumber();
            setGivenName();
            setSurName();
            common.findWebElement(BANKID_BUTTON).click();
        }
        else if(testData.isRegisterWithFreja()){
            log.info("Registering with Freja");

            //Add magic cookie to be redirected to the reference IDP instead of Freja
            common.addMagicCookie();

            generateUsername();
            setIdentityNumber();
            setGivenName();
            setSurName();

            //Add nin cookie to get a positive response from freja
            common.addNinCookie();

            common.findWebElement(FREJA_BUTTON).click();

            common.selectAndSubmitUserRefIdp();

            verifyEidLabels();
        }
        else if(testData.isRegisterWithEidas()){
            log.info("Registering with eIDAS");
            common.findWebElement(EIDAS_BUTTON).click();

            //Select XA as country in eIDAS connector
            common.selectCountry("XA");
            common.submitEidasUser();
            common.submitConsent();
            common.submitConsent();
        }
        else if(testData.isRegisterWithFrejaEid()){
            log.info("Registering with Freja EID");
            common.findWebElement(FREJA_EID_BUTTON).click();
        }

        if(!testData.isRegisterWithBankId()) {
            log.info("Not registering with BankID (or eID), enter email instead.");
            enterEmailAndPressRegister();

            if(testData.isRegisterWithFreja() || testData.isRegisterWithEidas() || testData.isRegisterWithFrejaEid()) {
                verifyTermsSwedish();
                common.selectEnglish();
            }

            if(!testData.isRegisterWithFreja()) {
                verifyLabelsAtConfirmEmailAddress();
                enterCaptchaCode();
            }
            registerPopUp();
        }
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
            if (testData.isGenerateUsername()
                    &!testData.isRegisterWithBankId()
                    &!testData.isRegisterWithFreja()
                    &!testData.isRegisterWithEidas()
                    &!testData.isRegisterWithFrejaEid()) {
                generateUsername();
                setIdentityNumber();
                setGivenName();
                setSurName();
            }
            else if (!testData.isGenerateUsername()){
                log.info("Using existing username: {}", testData.getUsername());
            }
            else {
                generateUsername();
            }
            testData.setDisplayName(testData.getGivenName() + " " + testData.getSurName());
            log.info("Display name set to: {}", testData.getDisplayName());
        } else {
            log.info("Identity number set to: {}", testData.getIdentityNumber());
            log.info("Using existing display name: {}", testData.getDisplayName());
        }

        log.info("Registering user: {}", testData.getUsername());
        testData.setEmail(testData.getUsername().toLowerCase());

        if(!testData.isRegisterWithBankId() &!testData.isRegisterWithFreja() &!testData.isRegisterWithEidas() &!testData.isRegisterWithFrejaEid()) {
            fillField(GIVEN_NAME_INPUT, testData.getGivenName());
            fillField(SURNAME_INPUT, testData.getSurName());
        }
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
        common.waitUntilClickable(CANCEL_BUTTON);
        verifyTermsEnglish();

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
        common.waitUntilClickable(EXPAND_REGISTRATION_FORM_BUTTON);

        // Verifiera svensk text och expandera formuläret (Klick 1)
        verifyLabelsSwedish(common.getPageBody());

        // Växla till engelska
        common.selectEnglish();

        // Verifiera engelsk text och stäng/klicka formuläret igen baserat på dess tillstånd (Klick 2)
        verifyLabelsEnglish(common.getPageBody());
    }

    private void verifyLabelsSwedish(String pageBody) {
        log.info("Verifying registration labels — Swedish");

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Lägg till inloggningsmetod");
        common.verifyPageBodyContainsString(pageBody,
                "Det snabbaste sättet att registrera ditt eduID är med digitalt ID, eller så kan du " +
                        "använda namn och e-postadress.");
        common.verifyPageBodyContainsString(pageBody, "Med digitalt ID");
        common.verifyPageBodyContainsString(pageBody, "Använd BankID, Freja, eIDAS eller Freja eID " +
                "för att registrera dig. Ditt namn och din identitet verifieras automatiskt.");
        common.verifyPageBodyContainsString(pageBody, "Läs mer om hur du använder digitalt ID med eduID Hjälp.");
        common.verifyString(BANKID_BUTTON, "BANKID");
        common.verifyString(FREJA_BUTTON, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "EU-medborgare");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med ditt lands elektroniska ID m.h.a. eIDAS.");
        common.verifyString(EIDAS_BUTTON, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "De flesta länder");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med pass eller nationellt ID-kort m.h.a. Freja eID+.");
        common.verifyString(FREJA_EID_BUTTON, "FREJA EID");
        common.verifyPageBodyContainsString(pageBody, "eller ange dina uppgifter på annat sätt");

        // Återställt exakt enligt originalet: Verifiera "VISA FORMULÄR " och klicka
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "VISA FORMULÄR ");
        common.findWebElement(EXPAND_REGISTRATION_FORM_BUTTON).click();

        common.verifyPageBodyContainsString(pageBody, "Med namn och e-post");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "DÖLJ FORMULÄR ");
        common.verifyPageBodyContainsString(pageBody, "Ange en e-postadress som du har säker tillgång " +
                "till eftersom den behöver bekräftas med en kod som skickas dit. När du har skapat ett eduID kan du" +
                " logga in och verifiera din identitet i efterhand.");

        common.verifyPlaceholderBy("förnamn", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("efternamn", SURNAME_INPUT);
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);

        common.verifyString(GIVEN_NAME_LABEL, "Förnamn");
        common.verifyString(SURNAME_LABEL, "Efternamn");
        common.verifyString(EMAIL_LABEL, "E-postadress");

        common.verifyStepIndicator(1, "Registreringsmetod");
    }

    private void verifyLabelsEnglish(String pageBody) {
        log.info("Verifying registration labels — English");
        common.waitUntilPageTitleContains("Register | eduID");

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Choose registration method");
        common.verifyPageBodyContainsString(pageBody,
                "The fastest way to register is with a digital ID, or you can register with your " +
                        "name and email address.");
        common.verifyPageBodyContainsString(pageBody, "With a digital ID");
        common.verifyPageBodyContainsString(pageBody, "Use BankID, Freja, eIDAS or Freja eID to " +
                "register. Your name and identity will be verified automatically.");
        common.verifyPageBodyContainsString(pageBody, "Read more about how to register with a " +
                "digital ID in eduID Help.");
        common.verifyString(BANKID_BUTTON, "BANKID");
        common.verifyString(FREJA_BUTTON, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "EU citizen");
        common.verifyPageBodyContainsString(pageBody, "Verify with your country's electronic ID using eIDAS.");
        common.verifyString(EIDAS_BUTTON, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "Most countries");
        common.verifyPageBodyContainsString(pageBody, "Verify with a passport or national ID card using Freja eID+.");
        common.verifyString(FREJA_EID_BUTTON, "FREJA EID");
        common.verifyPageBodyContainsString(pageBody, "or register another way");

        // Återställt exakt enligt originalet: Verifiera "SHOW FORM " och klicka igen
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "SHOW FORM ");
        common.findWebElement(EXPAND_REGISTRATION_FORM_BUTTON).click();

        common.verifyPageBodyContainsString(pageBody, "With name and email");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "HIDE FORM ");
        common.verifyPageBodyContainsString(pageBody, "Use an email address you have access to, as it " +
                "will need to be confirmed by a received code. Once you have created an eduID you will be able to " +
                "log in and verify your identity afterwards.");

        common.verifyString(GIVEN_NAME_LABEL, "First name");
        common.verifyString(SURNAME_LABEL, "Last name");
        common.verifyString(EMAIL_LABEL, "Email address");

        common.verifyPlaceholderBy("first name", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("last name", SURNAME_INPUT);
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);

        common.verifyStepIndicator(1, "Registration method");
    }


    // -------------------------------------------------------------------------
    // Etikett-verifiering — captcha-sidan
    // -------------------------------------------------------------------------

    private void verifyLabelsAtConfirmEmailAddress() {
        common.waitUntilClickable(CAPTCHA_NEW_IMAGE_BTN);

        verifyLabelsAtCaptchaEnglish();
        common.selectSwedish();
        verifyLabelsAtCaptchaSwedish();
        common.selectEnglish();
    }

    private void verifyLabelsAtCaptchaEnglish() {
        common.verifyString(CAPTCHA_PAGE_H1, "Create eduID: Confirm/Accept");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "Confirm that you are human as protection against automated spam.");
        common.verifyStringOnPage("Enter the code from the image");
        common.verifyStringOnPage("Generate a new image");
        common.verifyString(CANCEL_CAPTCHA, "CANCEL");

        common.verifyStepIndicator(2, "Confirm/Accept");
    }

    private void verifyLabelsAtCaptchaSwedish() {
        common.verifyString(CAPTCHA_PAGE_H1, "Skapa eduID: Bekräfta/Godkänn");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "Bekräfta att du inte är en robot m.h.a. CAPTCHA, för att undvika automatiskt spam.");
        common.verifyStringOnPage("Ange koden från bilden");
        common.verifyStringOnPage("Generera en ny bild");
        common.verifyString(CANCEL_CAPTCHA, "AVBRYT");

        common.verifyStepIndicator(2, "Bekräfta/Godkänn");
    }

    private void verifyEidLabels(){
        log.info("Verifying identity labels — Swedish");
        common.waitUntilPageTitleContains("Registrera | eduID");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Lägg till inloggningsmetod");
        common.verifyPageBodyContainsString(pageBody, "Din identitet har verifierats");
        common.verifyPageBodyContainsString(pageBody, "Din identitet har verifierats och ditt namn har sparats. För att slutföra registreringen ange din e-postadress nedan.");
        common.verifyPageBodyContainsString(pageBody, "Freja identitet");
        common.verifyStrings(testData.getGivenName(), common.getAttribute(GIVEN_NAME_INPUT));
        common.verifyStrings(testData.getSurName(), common.getAttribute(SURNAME_INPUT));
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);
        common.verifyString(REGISTER_BUTTON, "FORTSÄTT");

        common.selectEnglish();
        log.info("Verifying identity labels — English");

        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Choose registration method");
        common.verifyPageBodyContainsString(pageBody, "Your identity has been verified");
        common.verifyPageBodyContainsString(pageBody, "Your identity has been verified and your name has been saved. To complete the registration, please enter your email address below.");
        common.verifyPageBodyContainsString(pageBody, "Freja identity");
        common.verifyStrings(testData.getGivenName(), common.getAttribute(GIVEN_NAME_INPUT));
        common.verifyStrings(testData.getSurName(), common.getAttribute(SURNAME_INPUT));
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);
        common.verifyString(REGISTER_BUTTON, "CONTINUE");

        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — användarvillkor
    // -------------------------------------------------------------------------

    private void verifyTermsSwedish() {
        log.info("Verifying terms — Swedish");
        common.waitUntilClickable(CANCEL_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Bekräfta/Godkänn");
        common.verifyPageBodyContainsString(pageBody, "För att skapa ditt eduID måste du acceptera eduIDs användarvillkor.");
        common.verifyPageBodyContainsString(pageBody, "Du kan bli ombedd att acceptera villkoren på nytt om du inte har använt tjänsten sedan en tid, eller närsomhelst om villkoren har ändrats.");
        common.verifyPageBodyContainsString(pageBody, "För eduID.se gäller generellt");
        common.verifyPageBodyContainsString(pageBody, "att all användning av användarkonton ska följa Sveriges lagar och förordningar,");
        common.verifyPageBodyContainsString(pageBody, "att man är sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,");
        common.verifyPageBodyContainsString(pageBody, "att användarkonton, lösenord, säkerhetsnycklar och koder är personliga och får endast användas av innehavaren,");
        common.verifyPageBodyContainsString(pageBody, "att SUNET:s etiska regler (enligt nedan) reglerar övrig tillåten användning.");
        common.verifyPageBodyContainsString(pageBody, "SUNET bedömer som oetiskt när någon:");
        common.verifyPageBodyContainsString(pageBody, "försöker få tillgång till nätverksresurser utan att ha rätt till det");
        common.verifyPageBodyContainsString(pageBody, "försöker dölja sin användaridentitet");
        common.verifyPageBodyContainsString(pageBody, "försöker störa eller avbryta den avsedda användningen av nätverken");
        common.verifyPageBodyContainsString(pageBody, "uppenbart slösar med tillgängliga resurser (personal, maskinvara eller programvara)");
        common.verifyPageBodyContainsString(pageBody, "gör intrång i andras privatliv");
        common.verifyPageBodyContainsString(pageBody, "försöker förolämpa eller förnedra andra");
        common.verifyPageBodyContainsString(pageBody, "Den som överträder, eller misstänks överträda, ovanstående regler kan stängas av från eduID.se. Dessutom kan rättsliga åtgärder komma att vidtas.");
    }

    private void verifyTermsEnglish() {
        log.info("Verifying terms — English");
        common.waitUntilClickable(CANCEL_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Confirm/Accept");
        common.verifyPageBodyContainsString(pageBody, "To create your eduID account you need to accept the eduID terms of use.");
        common.verifyPageBodyContainsString(pageBody, "You may be asked to accept the terms again if you haven't used the service for a period of time, or any time the terms have changed.");
        common.verifyPageBodyContainsString(pageBody, "The following generally applies:");
        common.verifyPageBodyContainsString(pageBody, "that all usage of user accounts follow the laws and by-laws of Sweden,");
        common.verifyPageBodyContainsString(pageBody, "that all personal information that you provide, such as name and contact information shall be truthful,");
        common.verifyPageBodyContainsString(pageBody, "that user accounts, password, security keys and codes are individual and shall only be used by the intended individual,");
        common.verifyPageBodyContainsString(pageBody, "that SUNET's ethical rules (listed below) regulate other permitted usage.");
        common.verifyPageBodyContainsString(pageBody, "SUNET judges unethical behaviour to be when someone:");
        common.verifyPageBodyContainsString(pageBody, "attempts to gain access to network resources that they do not have the right to");
        common.verifyPageBodyContainsString(pageBody, "attempts to conceal their user identity");
        common.verifyPageBodyContainsString(pageBody, "attempts to interfere or disrupt the intended usage of the network");
        common.verifyPageBodyContainsString(pageBody, "clearly wastes available resources (personnel, hardware or software)");
        common.verifyPageBodyContainsString(pageBody, "attempts to disrupt or destroy computer-based information");
        common.verifyPageBodyContainsString(pageBody, "infringes on the privacy of others");
        common.verifyPageBodyContainsString(pageBody, "attempts to insult or offend others");
        common.verifyPageBodyContainsString(pageBody, "Any person found violating or suspected of violating these rules can be disabled from eduID.se for investigation. Furthermore, legal action may be taken.");
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
/*
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

*/
/**
 * Page object för registreringssidan.
 *
 * Ansvar:
 *  - Verifiera etiketter på registreringssidan i båda språken.
 *  - Verifiera captcha-sidans etiketter.
 *  - Fylla i formuläret och skicka.
 *  - Verifiera och acceptera/avvisa användarvillkor.
 *  - Generera slumpmässigt användarnamn, identitetsnummer och namn vid behov.
 *//*

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
        if(testData.isRegisterWithBankId()){
            log.info("Registering with BankID");

            generateUsername();
            setIdentityNumber();
            setGivenName();
            setSurName();
            common.findWebElement(BANKID_BUTTON).click();
        }
        else if(testData.isRegisterWithFreja()){
            log.info("Registering with Freja");

            //Add magic cookie to be redirected to the reference IDP instead of Freja
            common.addMagicCookie();

            generateUsername();
            setIdentityNumber();
            setGivenName();
            setSurName();

            //Add nin cookie to get a positive response from freja
            common.addNinCookie();

            common.findWebElement(FREJA_BUTTON).click();

            common.selectAndSubmitUserRefIdp();

            verifyEidLabels();
        }
        else if(testData.isRegisterWithEidas()){
            log.info("Registering with eIDAS");
            common.findWebElement(EIDAS_BUTTON).click();

            //Select XA as country in eIDAS connector
            common.selectCountry("XA");
            common.submitEidasUser();
            common.submitConsent();
            common.submitConsent();

            //TODO how to handle accounts that already are registered with an eIDAS eid?
        }
        else if(testData.isRegisterWithFrejaEid()){
            log.info("Registering with Freja EID");
            common.findWebElement(FREJA_EID_BUTTON).click();


        }

        if(!testData.isRegisterWithBankId()) {
            log.info("Not registering with BankID (or eID), enter email instead.");
            enterEmailAndPressRegister();

            if(testData.isRegisterWithFreja() || testData.isRegisterWithEidas() || testData.isRegisterWithFrejaEid()) {
                verifyTermsSwedish();
                common.selectEnglish();
            }

            if(!testData.isRegisterWithFreja()) {
                verifyLabelsAtConfirmEmailAddress();
                enterCaptchaCode();
            }
            registerPopUp();
        }
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
            if (testData.isGenerateUsername()
                    &!testData.isRegisterWithBankId()
                    &!testData.isRegisterWithFreja()
                    &!testData.isRegisterWithEidas()
                    &!testData.isRegisterWithFrejaEid()) {
                generateUsername();
                setIdentityNumber();
                setGivenName();
                setSurName();
            }
            else if (!testData.isGenerateUsername()){
                log.info("Using existing username: {}", testData.getUsername());
            }
            else {
                //The user name needs to be generated when register with eID
                generateUsername();
                //TODO verify names and birth date from eID
            }
            testData.setDisplayName(testData.getGivenName() + " " + testData.getSurName());
            log.info("Display name set to: {}", testData.getDisplayName());
        } else {
            log.info("Identity number set to: {}", testData.getIdentityNumber());
            log.info("Using existing display name: {}", testData.getDisplayName());
        }

        log.info("Registering user: {}", testData.getUsername());
        testData.setEmail(testData.getUsername().toLowerCase());

        if(!testData.isRegisterWithBankId() &!testData.isRegisterWithFreja() &!testData.isRegisterWithEidas() &!testData.isRegisterWithFrejaEid()) {
            fillField(GIVEN_NAME_INPUT, testData.getGivenName());
            fillField(SURNAME_INPUT, testData.getSurName());
        }
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
        common.waitUntilClickable(EXPAND_REGISTRATION_FORM_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Lägg till inloggningsmetod");
        common.verifyPageBodyContainsString(pageBody,
                "Det snabbaste sättet att registrera ditt eduID är med digitalt ID, eller så kan du " +
                        "använda namn och e-postadress.");
        common.verifyPageBodyContainsString(pageBody, "Med digitalt ID");
        common.verifyPageBodyContainsString(pageBody, "Använd BankID, Freja, eIDAS eller Freja eID " +
                "för att registrera dig. Ditt namn och din identitet verifieras automatiskt.");
        common.verifyPageBodyContainsString(pageBody, "Läs mer om hur du använder digitalt ID med eduID Hjälp.");
        common.verifyPageBodyContainsString(pageBody, "Svenskt eID");
        common.verifyPageBodyContainsString(pageBody, "Kräver ett svenskt person- eller samordningsnummer.");
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/section[1]/p[2]/a");
        common.verifyString(BANKID_BUTTON, "BANKID");
        common.verifyString(FREJA_BUTTON, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "EU-medborgare");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med ditt lands elektroniska ID m.h.a. eIDAS.");
        common.verifyString(EIDAS_BUTTON, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "De flesta länder");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med pass eller nationellt ID-kort m.h.a. Freja eID+.");
        common.verifyString(FREJA_EID_BUTTON, "FREJA EID");
        common.verifyPageBodyContainsString(pageBody, "eller ange dina uppgifter på annat sätt");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "VISA FORMULÄR ");

        //Expand the registration form for username
        common.findWebElement(EXPAND_REGISTRATION_FORM_BUTTON).click();
        common.verifyPageBodyContainsString(pageBody, "Med namn och e-post");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "DÖLJ FORMULÄR ");
        common.verifyPageBodyContainsString(pageBody, "Ange en e-postadress som du har säker tillgång " +
                "till eftersom den behöver bekräftas med en kod som skickas dit. När du har skapat ett eduID kan du" +
                " logga in och verifiera din identitet i efterhand.");

        common.verifyPlaceholderBy("förnamn", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("efternamn", SURNAME_INPUT);
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);

        common.verifyString(GIVEN_NAME_LABEL, "Förnamn");
        common.verifyString(SURNAME_LABEL, "Efternamn");
        common.verifyString(EMAIL_LABEL, "E-postadress");

        //Verify the registration form step indicator
        common.verifyStepIndicator(1, "Registreringsmetod");
    }

    private void verifyLabelsEnglish() {
        log.info("Verifying registration labels — English");
        common.waitUntilPageTitleContains("Register | eduID");

        common.waitUntilClickable(EXPAND_REGISTRATION_FORM_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Choose registration method");
        common.verifyPageBodyContainsString(pageBody,
                "The fastest way to register is with a digital ID, or you can register with your " +
                        "name and email address.");
        common.verifyPageBodyContainsString(pageBody, "With a digital ID");
        common.verifyPageBodyContainsString(pageBody, "Use BankID, Freja, eIDAS or Freja eID to " +
                "register. Your name and identity will be verified automatically.");
        common.verifyPageBodyContainsString(pageBody, "Read more about how to register with a " +
                "digital ID in eduID Help.");
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/section[1]/p[2]/a");
        common.verifyPageBodyContainsString(pageBody, "Swedish ID");
        common.verifyPageBodyContainsString(pageBody, "Requires a Swedish personal identity number or coordination number.");
        common.verifyString(BANKID_BUTTON, "BANKID");
        common.verifyString(FREJA_BUTTON, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "EU citizen");
        common.verifyPageBodyContainsString(pageBody, "Verify with your country's electronic ID using eIDAS.");
        common.verifyString(EIDAS_BUTTON, "EIDAS");
        common.verifyPageBodyContainsString(pageBody, "Most countries");
        common.verifyPageBodyContainsString(pageBody, "Verify with a passport or national ID card using Freja eID+.");
        common.verifyString(FREJA_EID_BUTTON, "FREJA EID");
        common.verifyPageBodyContainsString(pageBody, "or register another way");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "SHOW FORM ");

        //Expand the registration form for username
        common.findWebElement(EXPAND_REGISTRATION_FORM_BUTTON).click();
        common.verifyPageBodyContainsString(pageBody, "With name and email");
        common.verifyString(EXPAND_REGISTRATION_FORM_BUTTON, "HIDE FORM ");
        common.verifyPageBodyContainsString(pageBody, "Use an email address you have access to, as it " +
                "will need to be confirmed by a received code. Once you have created an eduID you will be able to " +
                "log in and verify your identity afterwards.");

        common.verifyString(GIVEN_NAME_LABEL, "First name");
        common.verifyString(SURNAME_LABEL, "Last name");
        common.verifyString(EMAIL_LABEL, "Email address");

        common.verifyPlaceholderBy("first name", GIVEN_NAME_INPUT);
        common.verifyPlaceholderBy("last name", SURNAME_INPUT);
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);

        //Verify the registration form step indicator
        common.verifyStepIndicator(1, "Registration method");
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
        common.verifyString(CAPTCHA_PAGE_H1, "Create eduID: Confirm/Accept");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "Confirm that you are human as protection against automated spam.");
        common.verifyStringOnPage("Enter the code from the image");
        common.verifyStringOnPage("Generate a new image");
        common.verifyString(CANCEL_CAPTCHA, "CANCEL");

        //Verify the registration form step indicator
        common.verifyStepIndicator(2, "Confirm/Accept");
    }

    private void verifyLabelsAtCaptchaSwedish() {
        common.verifyString(CAPTCHA_PAGE_H1, "Skapa eduID: Bekräfta/Godkänn");
        common.verifyString(CAPTCHA_PAGE_DESC,
                "Bekräfta att du inte är en robot m.h.a. CAPTCHA, för att undvika automatiskt spam.");
        common.verifyStringOnPage("Ange koden från bilden");
        common.verifyStringOnPage("Generera en ny bild");
        common.verifyString(CANCEL_CAPTCHA, "AVBRYT");

        //Verify the registration form step indicator
        common.verifyStepIndicator(2, "Bekräfta/Godkänn");
    }

    private void verifyEidLabels(){
        log.info("Verifying identity labels — Swedish");
        common.waitUntilPageTitleContains("Registrera | eduID");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Lägg till inloggningsmetod");
        common.verifyPageBodyContainsString(pageBody, "Din identitet har verifierats");
        common.verifyPageBodyContainsString(pageBody, "Din identitet har verifierats och ditt namn har sparats. För att slutföra registreringen ange din e-postadress nedan.");
        common.verifyPageBodyContainsString(pageBody, "Freja identitet");
        common.verifyStrings(testData.getGivenName(), common.getAttribute(GIVEN_NAME_INPUT));
        common.verifyStrings(testData.getSurName(), common.getAttribute(SURNAME_INPUT));
        common.verifyPlaceholderBy("namn@example.com", EMAIL_INPUT);
        common.verifyString(REGISTER_BUTTON, "FORTSÄTT");

        common.selectEnglish();

        log.info("Verifying identity labels — English");

        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Choose registration method");
        common.verifyPageBodyContainsString(pageBody, "Your identity has been verified");
        common.verifyPageBodyContainsString(pageBody, "Your identity has been verified and your name has been saved. To complete the registration, please enter your email address below.");
        common.verifyPageBodyContainsString(pageBody, "Freja identity");
        common.verifyStrings(testData.getGivenName(), common.getAttribute(GIVEN_NAME_INPUT));
        common.verifyStrings(testData.getSurName(), common.getAttribute(SURNAME_INPUT));
        common.verifyPlaceholderBy("name@example.com", EMAIL_INPUT);
        common.verifyString(REGISTER_BUTTON, "CONTINUE");

        common.selectSwedish();
    }

    // -------------------------------------------------------------------------
    // Etikett-verifiering — användarvillkor
    // -------------------------------------------------------------------------

    private void verifyTermsSwedish() {
        log.info("Verifying terms — Swedish");
        common.waitUntilClickable(CANCEL_BUTTON);
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Skapa eduID: Bekräfta/Godkänn");
        common.verifyPageBodyContainsString(pageBody,
                "För att skapa ditt eduID måste du acceptera eduIDs användarvillkor.");
        common.verifyPageBodyContainsString(pageBody, "Du kan bli ombedd att acceptera villkoren " +
                "på nytt om du inte har använt tjänsten sedan en tid, eller närsomhelst om villkoren har ändrats.");
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

        common.verifyPageBodyContainsString(pageBody, "Create eduID: Confirm/Accept");
        common.verifyPageBodyContainsString(pageBody,
                "To create your eduID account you need to accept the eduID terms of use.");
        common.verifyPageBodyContainsString(pageBody, "You may be asked to accept the terms again " +
                "if you haven't used the service for a period of time, or any time the terms have changed.");
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

    */
/**
     * Läser en slumpmässig rad från en fil.
     * Kastar {@link RuntimeException} om filen inte kan läsas — fel i testdata-konfigurationen
     * bör inte tystas ned med en tyst catch.
     *//*

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
*/
