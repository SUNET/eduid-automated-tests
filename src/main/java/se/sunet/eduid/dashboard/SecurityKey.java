package se.sunet.eduid.dashboard;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class SecurityKey {
    private final Common common;
    private final TestData testData;

    public SecurityKey(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runSecurityKey(){
        if(testData.isVerifySecurityKey()){
            verifySecurityKey();
        }
        else {
            pressAdvancedSettings();

            verifySecurityLabels();
            //If we shall add extra security key
    //        if(testData.isAddSecurityKey())
                virtualAuthenticator();

            addSecurityKey();
        }
    }

    private void pressAdvancedSettings(){
        //common.navigateToAdvancedSettings();
        common.navigateToSecurity();

        //Wait for heading "Gör ditt eduID säkrare"
        common.explicitWaitVisibilityElement("//*[@id=\"register-security-key-container\"]/h2");

        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();


    }

    //Not used any more, keeping code for example
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
        Common.log.info("Virtual authentication made with USB");
    }

    private void addSecurityKey(){
        //Click on add security key and verify labels
        verifyAddSecurityKeyLabels();

        //Add a security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.timeoutMilliSeconds(500);
        common.switchToPopUpWindow();

        //Enter name of key and click OK
        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").sendKeys("test-key1");
        common.click(common.findWebElementByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button"));
        common.timeoutMilliSeconds(500);

        //Verify that extra key can be added.
        if(testData.isAddSecurityKey()) {
            //Verify headings
            verifySecurityKeyHeaders();
        }
        //Verify that without personal info added, no extra key can be added.
        //TODO can probably be remoed
        else if(!testData.isAddSecurityKey()) {
            common.verifyStatusMessage("Du behöver lägga till ditt namn i Inställningar innan du kan lägga till en säkerhetsnyckel");

            //Close the status message
            common.closeStatusMessage();
        }
    }

    private void verifySecurityKeyHeaders(){
        Common.log.info("Verify security key headers - swedish");
        //Security key Toggle information
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/fieldset/form/label/legend",
                "Använd alltid tvåfaktorsautenticering (2FA) vid inloggning till eduID\n" +
                        "Om andra externa tjänster än eduID kräver tvåfaktorsautenticering (2FA) vid inloggning, " +
                        "kommer du ändå behöva använda din säkerhetsnyckel då, även när denna inställning är avslagen.");

        //Verify headings
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/h4", "Hantera dina säkerhetsnycklar");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[1]", "Namn");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[2]", "Skapad den");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[3]", "Senast använd");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[4]", "Verifiera");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[5]", "Ta bort");

        //verify data
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[1]", "test-key1");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[2]", common.getDate().toString());
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[3]", "Aldrig använd");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button[1]", "FREJA+");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button[2]", "BANKID");

        //English
        common.selectEnglish();

        Common.log.info("Verify security key headers - english");

        //Security key Toggle information
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/fieldset/form/label/legend",
                "Always use a second factor (2FA) to log in to eduID\n" +
                        "If external services other than eduID require a second factor during login, you will then " +
                        "still need to use your security key even when this setting is toggled off.");

        //Verify headings
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/h4", "Manage your security keys");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[1]", "Name");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[2]", "Created on");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[3]", "Used on");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[4]", "Verify");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[5]", "Remove");

        //verify data
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[1]", "test-key1");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[2]", common.getDate().toString());
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[3]", "Never used");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button[1]", "FREJA+");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button[2]", "BANKID");

        //Swedish
        common.selectSwedish();
    }

    private void verifyAddSecurityKeyLabels(){
        common.selectSwedish();

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                "Obs: beskrivningen är endast för att hjälpa dig skilja på dina tillagda nycklar.");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
        common.verifyStrings("beskriv din säkerhetsnyckel",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //This option with using your device is not visible in firefox
        try {
            if (common.findWebElementById("security-webauthn-platform-button").isDisplayed()) {

                //Verify extra security on This device labels
                common.click(common.findWebElementById("security-webauthn-platform-button"));
                common.timeoutMilliSeconds(500);

                //Labels and placeholder
                common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Ge ett namn till din säkerhetsnyckel");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[1]/p",
                        "Note: this is only for your own use to be able to distinguish between your added keys.");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
                common.verifyStrings("beskriv din säkerhetsnyckel",
                        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

                //Close pop up
                common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
                common.timeoutMilliSeconds(500);
            }
        }catch (Exception ex) {
            Common.log.info("Use this device as extra security not visible");
        }


        //English
        common.timeoutMilliSeconds(500);

        //For unknown reason, chrome sometimes freezes at this point when change of language. A page refresh resolves the problem.
        try {
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a"));
        }catch (TimeoutException ex){
            Common.log.info("Got timeout exception when trying to change language to english, reloading the page");
            common.getWebDriver().navigate().refresh();
            common.timeoutSeconds(1);
            Common.log.info("Page refreshed, selecting english again");

            common.selectEnglish();
        }

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Add a name for your security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 characters");
        common.verifyStrings("describe your security key",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //This option with using your device is not visible in firefox
        try{
            if(common.findWebElementById("security-webauthn-platform-button").isDisplayed()){

                //Verify extra security on This device labels
                common.click(common.findWebElementById("security-webauthn-platform-button"));
                common.timeoutMilliSeconds(500);

                //Labels and placeholder
                common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Add a name for your security key");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Security key");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 characters");
                common.verifyStrings("describe your security key",
                        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

                //Close pop up
                common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
            }
        }catch (Exception ex){
            common.log.info("Use this device as extra security not visible");
        }

        //English
        common.timeoutMilliSeconds(2500);
        common.selectSwedish();
    }

    private void verifySecurityKey(){
        Common.log.info("Start verify security key pop up labels and text");

        //Click on Verify for the added security key - Selecting Freja
        common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button"));

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button");

        //Click on Accept
        Common.log.info("Start verify security key pop up - pressed Accept button");
    }

    public void deleteSecurityKey(){
        Common.log.info("Delete security key - start verify security key pop up labels and text");

        //Click on Remove button for the added security key
        common.click(common.findWebElementById("remove-webauthn"));

        //Verify the security pop up and click accept
        common.securityConfirmPopUp("//*[@id=\"remove-webauthn\"]");

        //Click on Accept
        Common.log.info("Delete security key - start verify security key pop up - pressed Accept button");
    }


    public void verifySecurityLabels(){
        Common.log.info("Start verify security page labels - swedish");

        //Extract page body for validation
        String pageBody = common.getPageBody();

        //Swedish
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Säkerhet");

        common.verifyPageBodyContainsString(pageBody,"Säkerhet");
        common.verifyPageBodyContainsString(pageBody,"Öka säkerheten för ditt eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Tvåfaktorsautenticering (2FA)");
        common.verifyPageBodyContainsString(pageBody,"Om möjligt lägg till ett ytterligare sätt att identifiera dig i form " +
                "av en säkerhetsnyckel, utöver användarnamn och lösenord, så att du är säker på att bara du har tillgång " +
                "till ditt eduID. Exempel på säkerhetsnycklar kan vara en USB-säkerhetsnyckel eller din enhet.");
        common.verifyPageBodyContainsString(pageBody,"Du kan läsa mer om säkerhetsnycklar i hjälpavsnittet: Utökad säkerhet med ditt eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"register-security-key-container\"]/p[2]/a");

        common.verifyPageBodyContainsString(pageBody,"Lägg till en ny säkerhetsnyckel:");
        common.verifyStringById("security-webauthn-button", "FYSISK SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody,"T.ex. USB säkerhetsnyckel som du använder.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "DEN HÄR ENHETEN");
            common.verifyPageBodyContainsString(pageBody,"Enheten som du just nu använder");
        }

        //click on english
        common.selectEnglish();

        Common.log.info("Start verify security page labels - english");

        common.verifyPageTitle("Security | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //English
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Security");

        common.verifyPageBodyContainsString(pageBody,"Security");
        common.verifyPageBodyContainsString(pageBody,"Enhance the security of your eduID.");

        //Security key
        common.verifyPageBodyContainsString(pageBody,"Two-factor Authentication (2FA)");
        common.verifyPageBodyContainsString(pageBody,"If possible add a security key as a second factor of authentication, " +
                "beyond username and password, to prove you are the owner of your eduID. Examples are USB security keys or your device.");
        common.verifyPageBodyContainsString(pageBody,"You can read more about security keys in the Help section: Improving the security level of eduID.");

        //Verify internal link to help pages works
        common.verifyXpathIsWorkingLink("//*[@id=\"register-security-key-container\"]/p[2]/a");

        common.verifyPageBodyContainsString(pageBody,"Add a new security key:");
        common.verifyStringById("security-webauthn-button", "EXTERNAL SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody,"E.g a USB Security Key you are using.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "THIS DEVICE");
            common.verifyPageBodyContainsString(pageBody,"The device you are currently using.");
        }
    }
}
