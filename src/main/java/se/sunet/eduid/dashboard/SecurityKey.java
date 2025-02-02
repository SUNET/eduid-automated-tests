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

    public SecurityKey(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runSecurityKey(){
/*        if(testData.isVerifySecurityKeyByFreja()){
            verifySecurityKeyByFreja();
        }
        else if (testData.isVerifySecurityKeyByBankId()) {
            verifySecurityKeyByBankId();
        }
        else {*/

/*        if (testData.isVerifySecurityKeyByBankId() &! testData.isAddSecurityKey()) {
            verifySecurityKeyByBankId();
        }
        else if (testData.isVerifySecurityKeyByFreja() &! testData.isAddSecurityKey()) {
            verifySecurityKeyByBankId();
        }
        else if (testData.isAddSecurityKey()){*/
            pressAdvancedSettings();

            verifySecurityLabels();
            //If we shall add extra security key
    //        if(testData.isAddSecurityKey())
                virtualAuthenticator();

            addSecurityKey();
//        }
    }

    private void pressAdvancedSettings(){
        common.navigateToSecurity();

        //Wait for heading "Öka säkerheten för ditt eduID"
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/div/p");

        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();


    }

    private void addSecurityKey(){
        //Click on add security key and verify labels
        verifyAddSecurityKeyLabels();

        //Enter name of key and click OK
        //common.findWebElementById("security-webauthn-button").click();
        common.findWebElementById(securityKeyInputFieldId).sendKeys("test-key1");
        common.click(common.findWebElementByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button"));
        common.timeoutMilliSeconds(500);

        //If security key should be verified, it can be done directly when added.
        if(testData.isVerifySecurityKeyByFreja()){
            log.info("Security key added but user selecting Freja for immediate verification of it, clicking on Freja button");

            verifyTheVerifySecurityKeyPopupLabels();

            //Click on Freja button
            common.click(common.findWebElementById("verify-webauthn-token-modal-continue-frejaID-button"));

            //Verify the security pop up and click accept
            common.securityConfirmPopUp("//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[1]");
        }
        else if(testData.isVerifySecurityKeyByBankId()){
            log.info("Security key added but user selecting BankID for immediate verification of it, clicking on BankID button");

            verifyTheVerifySecurityKeyPopupLabels();

            //Click on BankID button
            common.click(common.findWebElementById("verify-webauthn-token-modal-continue-bankID-button"));

            //Ignore verification of the security pop up lables and just click continue
            common.securityConfirmPopUp("");
        }

        //If security key should not be verified when added, close the pop-up
        else{
            common.findWebElementById("verify-webauthn-token-modal-close-button").click();
            log.info("Security key added but user selected not to verify it now, closing modal.");
        }

        //Verify that extra key can be added.
        //TODO - only when verify security key and not add change negation
        //if(testData.isAddSecurityKey()) {
/*        if(testData){
            //Verify headings
            verifySecurityKeyHeaders();
        }*/

        //Verify that without personal info added, no extra key can be added.
        //TODO can probably be remoed
/*        else if(!testData.isAddSecurityKey()) {
            common.verifyStatusMessage("Du behöver lägga till ditt namn i Inställningar innan du kan lägga till en säkerhetsnyckel");

            //Close the status message
            common.closeStatusMessage();
        }*/
    }

    private void verifySecurityKeyHeaders(){
        log.info("Verify added security key headers - swedish");

        //Wait for toggle switch
        common.explicitWaitClickableElement("//*[@id=\"content\"]/article[2]/form/fieldset/label/div");

        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody,"Använd alltid tvåfaktorsautentisering (2FA) vid " +
                "inloggning till eduID");
        common.verifyPageBodyContainsString(pageBody,"Om andra externa tjänster än eduID kräver " +
                "tvåfaktorsautentisering (2FA) vid inloggning, kommer du ändå behöva använda din säkerhetsnyckel då, " +
                "även när denna inställning är avslagen.");

        //Verify headings
        common.verifyPageBodyContainsString(pageBody,"Hantera dina säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody,"Namn: test-key1");
        common.verifyPageBodyContainsString(pageBody,"Skapad: " +common.getDate().toString());
        common.verifyPageBodyContainsString(pageBody,"Använd: Aldrig använd");
        common.verifyPageBodyContainsString(pageBody,"Verifiera med:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        //common.verifyXpathIsWorkingLink("//*[@id=\"register-webauthn-tokens-area\"]/figure/div[3]/span/button[1]");
        common.verifyPageBodyContainsString(pageBody,"BANKID");
        common.explicitWaitClickableElementId("remove-webauthn"); //Remove button

        //English
        common.selectEnglish();

        log.info("Verify added security key headers - english");
        pageBody = common.getPageBody();

        //Security key Toggle information
        common.verifyPageBodyContainsString(pageBody,
                "Always use a second factor (2FA) to log in to eduID\n" +
                        "If external services other than eduID require a second factor during login, you will then " +
                        "still need to use your security key even when this setting is toggled off.");

        //Verify headings
        common.verifyPageBodyContainsString(pageBody, "Manage your security keys");
        common.verifyPageBodyContainsString(pageBody, "Name: test-key1");
        common.verifyPageBodyContainsString(pageBody, "Created: " +common.getDate().toString());
        common.verifyPageBodyContainsString(pageBody, "Used: Never used");
        common.verifyPageBodyContainsString(pageBody, "Verify with:");
        common.verifyPageBodyContainsString(pageBody, "FREJA+");
        common.verifyPageBodyContainsString(pageBody, "BANKID");
        common.explicitWaitClickableElementId("remove-webauthn"); //Remove button

        //Swedish
        common.selectSwedish();
    }

    private void verifyAddSecurityKeyLabels(){
        String closePopupButton = "//*[@id=\"confirm-user-data-modal\"]/div/div/h5/button";
        log.info("Verify add security key headers in pop-up - english");

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.explicitWaitClickableElement(closePopupButton);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Add a name for your security key");
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
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
        common.verifyPlaceholder("beskriv din säkerhetsnyckel", securityKeyInputFieldId);

        //Close pop up
        //common.click(common.findWebElementByXpath(closePopupButton));
    }

    private void verifySecurityKeyByFreja(){
        log.info("Start verify security key - selecting Freja");

        //Click on Verify for the added security key - Selecting Freja
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[1]"));

        //Click on Freja button
//        common.click(common.findWebElementById("verify-webauthn-token-modal-continue-frejaID-button"));

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[1]");

        //Click on Accept
        //Common.log.info("mmmmm verify security key pop up - pressed Freja");
    }

    private void verifySecurityKeyByBankId(){
        log.info("Start verify security key - selecting BankID");

        //Click on Verify for the added security key - Selecting BankID
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[2]"));

        //Click on BankID button
//        common.click(common.findWebElementById("verify-webauthn-token-modal-continue-bankID-button"));

        //Handle the extra log in needed for security key verification
        //common.securityConfirmPopUp("//*[@id=\"verify-webauthn-token-modal-continue-bankID-button\"]");

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"content\"]/article[2]/figure/div[3]/span/button[2]");

        //Click on Accept
        //Common.log.info("mmmm verify security key pop up - pressed BankID");
    }

    public void deleteSecurityKey(){
        log.info("Delete security key - start verify security key pop up labels and text");

        //Click on Remove button for the added security key
        common.click(common.findWebElementById("remove-webauthn"));

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"remove-webauthn\"]");

        //Click on Accept
        log.info("Delete security key - start verify security key pop up - pressed Accept button");
    }


    public void verifySecurityLabels(){
        log.info("Start verify security page labels - swedish");

        //Extract page body for validation
        String pageBody = common.getPageBody();

        //Swedish
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Säkerhet");

        common.verifyPageBodyContainsString(pageBody,"Säkerhet");
        common.verifyPageBodyContainsString(pageBody,"Ökad säkerhet för ditt eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Tvåfaktorsautentisering (2FA)");
        common.verifyPageBodyContainsString(pageBody,"Om möjligt lägg till ett ytterligare sätt att identifiera dig i form " +
                "av en säkerhetsnyckel, utöver användarnamn och lösenord, så att du är säker på att bara du har tillgång " +
                "till ditt eduID. Exempel på säkerhetsnycklar kan vara en USB-säkerhetsnyckel eller din enhet.");
        common.verifyPageBodyContainsString(pageBody,"Du kan läsa mer om säkerhetsnycklar i hjälpavsnittet: Utökad säkerhet med ditt eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article/p[2]/a");

        common.verifyPageBodyContainsString(pageBody,"Lägg till en ny säkerhetsnyckel:");
        common.verifyStringById("security-webauthn-button", "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody,"T.ex. USB-säkerhetsnyckel som du använder.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "DEN HÄR ENHETEN");
            common.verifyPageBodyContainsString(pageBody,"Enheten som du just nu använder");
        }

        //click on english
        common.selectEnglish();

        log.info("Start verify security page labels - english");

        common.verifyPageTitle("Security | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //English
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Security");

        common.verifyPageBodyContainsString(pageBody,"Security");
        common.verifyPageBodyContainsString(pageBody,"Enhanced security of your eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Two-factor Authentication (2FA)");
        common.verifyPageBodyContainsString(pageBody,"If possible add a security key as a second factor of authentication, " +
                "beyond username and password, to prove you are the owner of your eduID. Examples are USB security keys or your device.");
        common.verifyPageBodyContainsString(pageBody,"You can read more about security keys in the Help section: Improving the security level of eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/article/p[2]/a");

        common.verifyPageBodyContainsString(pageBody,"Add a new security key:");
        common.verifyStringById("security-webauthn-button", "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody,"E.g a USB Security Key you are using.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "THIS DEVICE");
            common.verifyPageBodyContainsString(pageBody,"The device you are currently using.");
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
        //Wait for bankid button
        common.explicitWaitClickableElementId("verify-webauthn-token-modal-continue-bankID-button");

        common.verifyStringOnPage("Verifiera din tillagda säkerhetsnyckel");

        common.verifyStringOnPage("Klicka på knappen för BankID eller Freja+ för att verifiera din säkerhetsnyckel.");
        common.verifyStringOnPage("Obs: dina tillagda säkerhetsnycklar kan även verifieras från " +
                "tabellen \"Hantera dina säkerhetsnycklar\".");

        common.verifyStringById("verify-webauthn-token-modal-continue-bankID-button", "BANKID");
        common.verifyStringById("verify-webauthn-token-modal-continue-frejaID-button", "FREJA+");
    }


    public void virtualAuthenticator(){
        //Add cookie for back doors, if not set
        if(!common.isCookieSet("autotests"))
            common.addMagicCookie();
/*

        //Virtual authenticatior emulating authenticator devices in chrome
        // Create a DevTools session
        DevTools devTools = ((ChromeDriver) common.getWebDriver()).getDevTools();
        devTools.createSession();

        // Enable security domain
        devTools.send(Security.enable());

        // Set up a virtual authenticator (USB)
        JsonObject options = new JsonObject();
        options.addProperty("transport", "usb");
        options.addProperty("hasUserVerification", true);
        options.addProperty("isUserVerified", true);

        JsonObject setUserVerifiedParams = new JsonObject();
        setUserVerifiedParams.add("options", options);

        // Execute the CDP command to set user verified
        //TODO send the command to devtools in some other way...
        //devTools.executeCdpCommand("Security.setUserVerified", setUserVerifiedParams);

        System.out.println("Virtual USB authentication completed successfully");
*/


        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);

        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);
        authenticator.setUserVerified(true);
        log.info("Virtual authentication made with USB");
    }
}
