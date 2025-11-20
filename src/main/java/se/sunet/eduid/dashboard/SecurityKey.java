package se.sunet.eduid.dashboard;

import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class SecurityKey {
    private final Common common;
    private final TestData testData;
    String securityKeyInputFieldId = "describe-webauthn-token-modal";
    String securityKeyName = "test-key1";

    public SecurityKey(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runSecurityKey(){
        pressAdvancedSettings();

        verifySecurityLabels();

        virtualAuthenticator();

        addSecurityKey();
    }

    private void pressAdvancedSettings(){
        common.navigateToSecurity();

        //Wait for heading "Öka säkerheten för ditt eduID"
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/div/p");
    }

    private void addSecurityKey(){
        //Click on add security key and verify labels
        verifyAddSecurityKeyLabels();

        //Enter name of key and click OK
        common.findWebElementById(securityKeyInputFieldId).sendKeys(securityKeyName);
        common.click(common.findWebElementByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button"));
        common.timeoutMilliSeconds(500);

        //If security key should be verified, it can be done directly when added.
        if(testData.isVerifySecurityKeyByFreja()){
            log.info("Security key added and user selecting Freja for immediate verification of it, clicking on Freja button");

            //Verify the security key confirmation pop up labels
            verifyTheVerifySecurityKeyPopupLabels();

            //Click on Freja button
            common.click(common.findWebElementById("verify-webauthn-token-modal-continue-frejaID-button"));

            //Verify the security key extra login pop up and click accept
            common.securityConfirmPopUp("//*[@id=\"manage-security-keys\"]/figure/div[3]/span/button[2]",
                    "Obs: använd säkerhetsnyckeln test-key1 vid inloggningen. Efter inloggning omdirigeras du till " +
                            "FREJA för att verifiera din säkerhetsnyckel.",

                    "Note: please use the security key test-key1 during the login process. After logging in, you will" +
                            " be redirected to FREJA page to verify your security key.");
        }
        else if(testData.isVerifySecurityKeyByBankId()){
            log.info("Security key added and user selecting BankID for immediate verification of it, clicking on BankID button");

            verifyTheVerifySecurityKeyPopupLabels();

            //Click on BankID button
            common.click(common.findWebElementById("verify-webauthn-token-modal-continue-bankID-button"));

            //Ignore verification of the security pop up labels and just click continue
            common.securityConfirmPopUp("//*[@id=\"manage-security-keys\"]/figure/div[3]/span/button[1]",
                    "Obs: använd säkerhetsnyckeln test-key1 vid inloggningen. Efter inloggning omdirigeras " +
                            "du till BANKID för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key test-key1 during the login process. After logging in," +
                            " you will be redirected to BANKID page to verify your security key.");
        }
        else if(testData.isVerifySecurityKeyByEidas()){
            log.info("Security key added and user selecting eIDAS for immediate verification of it, clicking on eIDAS button");

            verifyTheVerifySecurityKeyPopupLabels();

            //Click on eIDAS button
            common.click(common.findWebElementById("verify-webauthn-token-modal-continue-eidas-button"));

            //Verify the security pop up and click accept
            common.securityConfirmPopUp("//*[@id=\"manage-security-keys\"]/figure/div[3]/span/button[3]",
                    "Obs: använd säkerhetsnyckeln test-key1 vid inloggningen. Efter inloggning omdirigeras " +
                            "du till EIDAS för att verifiera din säkerhetsnyckel.",
                    "Note: please use the security key test-key1 during the login process. After logging in," +
                            " you will be redirected to EIDAS page to verify your security key.");
        }

        //If security key should not be verified when added, close the pop-up
        else{
            common.findWebElementById("verify-webauthn-token-modal-close-button").click();
            log.info("Security key added but user selected not to verify it now, closing modal.");

            //If user does not select to immediately verify the added security key, then verify the added non-verified labels
            verifyAddedSecurityKeyHeadersSwe();
            verifyAddedSecurityKeyHeadersEng();
        }
    }

    private void verifyAddedSecurityKeyHeadersSwe() {
        log.info("Verify added non-verified security key headers - swedish");

        //Wait for toggle switch
        common.explicitWaitClickableElement("//*[@id=\"content\"]/article[2]/form/fieldset/label/div");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Använd alltid säkerhetsnyckel för att logga in");
        common.verifyPageBodyContainsString(pageBody, "Om denna stängs av behöver du bara använda din " +
                "säkerhetsnyckel när andra tjänster kräver extra verifiering vid inloggning.");

        //Verify headings
        common.verifyPageBodyContainsString(pageBody, "Hantera dina säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Namn: " +securityKeyName);
        common.verifyPageBodyContainsString(pageBody, "Skapad: " + common.getDate().toString());
        common.verifyPageBodyContainsString(pageBody, "Använd: Aldrig använd");
        common.verifyPageBodyContainsString(pageBody, "Verifiera med:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        //common.verifyXpathIsWorkingLink("//*[@id=\"register-webauthn-tokens-area\"]/figure/div[3]/span/button[1]");
        common.verifyPageBodyContainsString(pageBody, "BANKID");
        common.verifyPageBodyContainsString(pageBody, "EIDAS");
        common.explicitWaitClickableElementId("remove-webauthn"); //Remove button
    }

    private void verifyAddedSecurityKeyHeadersEng(){
        //English
        common.selectEnglish();

        log.info("Verify added non-verified security key headers - english");
        String pageBody = common.getPageBody();

        //Security key Toggle information
        common.verifyPageBodyContainsString(pageBody,
                "Always use a security key to log in\n" +
                        "Turning this off you only need to use your security key for services that require extra login verification.");

        //Verify headings
        common.verifyPageBodyContainsString(pageBody, "Manage your security keys");
        common.verifyPageBodyContainsString(pageBody, "Name: " +securityKeyName);
        common.verifyPageBodyContainsString(pageBody, "Created: " +common.getDate().toString());
        common.verifyPageBodyContainsString(pageBody, "Used: Never used");
        common.verifyPageBodyContainsString(pageBody, "Verify with:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "BANKID");
        common.verifyPageBodyContainsString(pageBody, "EIDAS");
        common.explicitWaitClickableElementId("remove-webauthn"); //Remove button

        //Swedish
        common.selectSwedish();
    }

    private void verifyAddSecurityKeyLabels(){
        String closePopupButton = "//*[@id=\"content\"]/dialog[1]/div/div/div/div/button";
        log.info("Verify add security key headers in pop-up - english");

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.explicitWaitClickableElement(closePopupButton);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"content\"]/dialog[1]/div/div/div/div/h4", "Add a name for your security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Note: this is only for your own use to be able to distinguish between your added keys.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 characters");
        common.verifyPlaceholder("describe your security key", securityKeyInputFieldId);

        //Close pop up
        common.click(common.findWebElementByXpath(closePopupButton));


        //Select swedish
        log.info("Verify add security key headers in pop-up - swedish");
        common.selectSwedish();

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.explicitWaitClickableElement(closePopupButton);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"content\"]/dialog[1]/div/div/div/div/h4", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
        common.verifyPlaceholder("beskriv din säkerhetsnyckel", securityKeyInputFieldId);
    }

    public void deleteSecurityKey(){
        log.info("Delete security key - start with verify security key pop up labels and text");

        //Click on Remove button for the added security key
        common.click(common.findWebElementById("remove-webauthn"));

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"remove-webauthn\"]",
                "Obs: Din säkerhetsnyckel " +securityKeyName +" kommer att tas bort efter inloggningen.",
                "Note: Your security key " +securityKeyName +" will be removed after you log in.");

        //Click on Accept
        log.info("Delete security key - start verify security key pop up - pressed Accept button");
    }


    public void verifySecurityLabels(){
        log.info("Start verify security page labels - swedish");

        //Extract page body for validation
        String pageBody = common.getPageBody();

        //Swedish
        common.verifyPageBodyContainsString(pageBody,"Säkerhet");
        common.verifyPageBodyContainsString(pageBody,"Öka och hantera säkerheten för ditt eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Lägg till multifaktorautentisering (MFA)");
        common.verifyPageBodyContainsString(pageBody,"Om möjligt lägg till ett ytterligare sätt att " +
                "identifiera dig i form av en säkerhetsnyckel, utöver användarnamn och lösenord, för att vara säker på " +
                "att bara du har tillgång till ditt eduID. Exempelvis en separat USB-säkerhetsnyckel som du kan skaffa," +
                " eller inbyggda passkey/lösennyckel-funktioner i din enhet som använder biometri eller pinkod. Lägg " +
                "gärna till flera säkerhetsnycklar.");
        common.verifyPageBodyContainsString(pageBody,"Obs: Tillagda säkerhetsnycklar är personliga och ska " +
                "inte delas med andra. Det är för att säkerställa att endast du som kontohavare har tillgång till ditt konto.");
        common.verifyPageBodyContainsString(pageBody,"Du kan läsa mer om säkerhetsnycklar som stöds i " +
                "hjälpavsnittet: Utökad säkerhet med ditt eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article[1]/p[3]/a");

        common.verifyPageBodyContainsString(pageBody,"Lägg till en ny säkerhetsnyckel:");
        common.verifyStringById("security-webauthn-button", "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody,"Din externa USB-säkerhetsnyckel");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "DEN HÄR ENHETEN");
            common.verifyPageBodyContainsString(pageBody,"Inbyggd passkey i din mobil eller laptop");
        }

        //click on english
        common.selectEnglish();

        log.info("Start verify security page labels - english");

        common.verifyPageTitle("Security | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //English
        common.verifyPageBodyContainsString(pageBody,"Security");
        common.verifyPageBodyContainsString(pageBody,"Enhance and manage the security of your eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Add multi-factor Authentication (MFA)");
        common.verifyPageBodyContainsString(pageBody,"If possible add a security key as a second factor " +
                "of authentication, beyond username and password, to prove you are the owner of your eduID. Examples " +
                "are separate physical USB security keys that you can get, or built-in passkey features on your device," +
                " such as biometrics or pins. It is recommended to add more than one security key.");
        common.verifyPageBodyContainsString(pageBody,"Note: Added security keys are personal and not to be " +
                "shared with others. This is to ensure that access to your account is limited solely to you, the account holder.");
        common.verifyPageBodyContainsString(pageBody,"You can read more about supported security keys in " +
                "the Help section: Improving the security level of eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article[1]/p[3]/a");

        common.verifyPageBodyContainsString(pageBody,"Add a new security key:");
        common.verifyStringById("security-webauthn-button", "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody,"Your external USB security key");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "THIS DEVICE");
            common.verifyPageBodyContainsString(pageBody,"Internal passkey on your phone or laptop");
        }
        log.info("Done verify security page labels - english");
    }

    public void verifiedSecurityKey(){
        //Wait for the remove security key icon
        common.explicitWaitClickableElementId("remove-webauthn");

        //Verify the added security key status
        common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/figure/div[3]/span/strong", "VERIFIERAD");

        common.selectEnglish();

        //Verify status beside the added key dates
        common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/figure/div[3]/span/strong", "VERIFIED");
        common.selectSwedish();
    }

    private void verifyTheVerifySecurityKeyPopupLabels(){
        log.info("Confirm security key popup labels - swedish");

        //Wait for bankid button
        common.explicitWaitClickableElementId("verify-webauthn-token-modal-continue-bankID-button");

        common.verifyStringOnPage("Verifiera din tillagda säkerhetsnyckel");

        common.verifyStringOnPage("Verifiera din säkerhetsnyckel med hjälp av knapparna för BankID, " +
                "Freja+ eller eIDAS nedan.");
        common.verifyStringOnPage("Obs: dina tillagda säkerhetsnycklar kan även verifieras senare i " +
                "inställningarna under \"Hantera dina säkerhetsnycklar\".");

        common.verifyStringById("verify-webauthn-token-modal-continue-bankID-button", "BANKID");
        common.verifyStringById("verify-webauthn-token-modal-continue-frejaID-button", "FREJA+");
        common.verifyStringById("verify-webauthn-token-modal-continue-eidas-button", "EIDAS");
    }


    public void virtualAuthenticator(){
        //Add cookie for back doors, if not already set
        if(!common.isCookieSet("autotests"))
            common.addMagicCookie();

        //Virtual authenticatior emulating authenticator devices in chrome
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
/*        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserConsenting(true)
                .setIsUserVerified(true);*/

        if(testData.isMfaUserConsentingAuthentication()){
            options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                    .setHasUserVerification(true)
                    .setIsUserConsenting(true)
                    .setIsUserVerified(true);
        }
        else if(testData.isMfaUserDeclinedConsentAuthentication()){
            options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                    .setHasUserVerification(true)
                    .setIsUserConsenting(false)
                    .setIsUserVerified(true);
        }
        else {
            options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                    .setHasUserVerification(true)
                    .setIsUserVerified(true);
        }


        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);
        authenticator.setUserVerified(true);
        log.info("Virtual authentication made with USB");
    }
}
