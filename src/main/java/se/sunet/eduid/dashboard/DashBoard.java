package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class DashBoard {
    private final Common common;
    private final TestData testData;

    public DashBoard(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDashBoard(){
        common.navigateToDashboard();
        verifyPageTitle();

        verifyUserId();

        if(testData.getTestCase().equalsIgnoreCase("TC_5"))
            verifyLabelsEnglish();
        else{
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }

    }

    private void verifyPageTitle() {
        //Timeout to save time from retry-functionality
        common.explicitWaitClickableElement("//*[@id=\"language-selector\"]/span/a");
        common.timeoutMilliSeconds(500);

        //TODO temp fix to get swedish language - needed when new accounts created
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska")
                && testData.getLanguage().equalsIgnoreCase("Svenska")) {
            common.selectSwedish();
        }
    }

    private void verifyUserId() {
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/button/span", testData.getUsername().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        common.verifyPageTitle("Start | eduID");

        //Verify navigation label
        common.verifyStringByXpath("//*[@id=\"content\"]/nav/a[1]", "Start");

        //Verify welcome heading
        if(testData.getDisplayName().isEmpty())
            common.verifyStringOnPage("Välkommen, " +testData.getEmail().toLowerCase() +"!");
        else
            common.verifyStringOnPage("Välkommen, " +testData.getDisplayName() +"!");

        //Verify heading sub-text
        common.verifyStringOnPage("Få ut det mesta av eduID genom att granska din information.");

        //Non-verified account text
        if(!testData.isAccountVerified() && testData.getDisplayName().isEmpty()) {
            Common.log.info("Verifying dashboard labels in swedish, Account is not verified");

            common.verifyStringOnPage("Status för verifiering av din identitet");
            common.verifyStringOnPage("Din identitet är inte verifierad.");
            common.verifyStringOnPage("Vänligen se rekommenderade åtgärder nedan.");

            //Heading recommended actions
            common.verifyStringOnPage("Rekommenderade åtgärder för dig");
            common.verifyStringOnPage("För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Name
            recommendedActionName(testData.getLanguage());

            //Recommended actions - Add Number
            recommendedActionAddNumber(testData.getLanguage());

            //Recommended actions - Identity
            recommendedActionIdentity(testData.getLanguage());

            //Recommended actions - Security key
            recommendedActionSecurityKey(testData.getLanguage());
        }

        //Verified account text
        else if(testData.isAccountVerified()){
            Common.log.info("Verifying dashboard labels in swedish, Account is verified");

            common.verifyStringOnPage("Status för verifiering av din identitet");
            common.verifyStringOnPage("Din identitet är verifierad.");
            common.verifyStringOnPage("Ditt eduID är redo att användas.");

            //Heading recommended actions
            common.verifyStringOnPage("Rekommenderade åtgärder för dig");
            common.verifyStringOnPage("För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Security key
            recommendedActionSecurityKey(testData.getLanguage());
        }

        //Account has been verified earlier but after reset pw its not verified anymore
        else{
            Common.log.info("Verifying dashboard labels in swedish, Account has been verified");

            common.verifyStringOnPage("Status för verifiering av din identitet");
            common.verifyStringOnPage("Din identitet är inte längre verifierad efter lösenords återställningen.");
            common.verifyStringOnPage("Vänligen se rekommenderade åtgärder nedan.");

            //Heading recommended actions
            common.verifyStringOnPage("Rekommenderade åtgärder för dig");
            common.verifyStringOnPage("För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Confirm or Add Number depending on whats already registered
            if(testData.getPhoneNumber().isEmpty())
                recommendedActionAddNumber(testData.getLanguage());
            else if(!testData.isRegisterAccount())
                Common.log.info("Do nothing....?!?!?");
            else
                recommendedActionConfirmNumber(testData.getLanguage());

            //Recommended actions - Identity
            recommendedActionIdentity(testData.getLanguage());

            //Recommended actions - Security key
            recommendedActionSecurityKey(testData.getLanguage());
        }
    }

    private void verifyLabelsEnglish() {
        //Select English
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectEnglish();

        common.verifyPageTitle("Start | eduID");

        //Verify site location menu
        common.verifyStringByXpath("//*[@id=\"content\"]/nav/a[1]", "Start");

        //Verify welcome heading
        if(testData.getDisplayName().isEmpty())
            common.verifyStringOnPage("Welcome, " +testData.getEmail().toLowerCase() +"!");
        else
            common.verifyStringOnPage("Welcome, " +testData.getDisplayName() +"!");

        //Verify heading sub-text
        common.verifyStringOnPage("Make the most out of eduID by reviewing your information.");

        //Non-verified accounts
        if(!testData.isAccountVerified() && testData.getDisplayName().isEmpty()) {
            Common.log.info("Verifying dashboard labels in english, Account is not verified");

            //Non-verified account text
            common.verifyStringOnPage("Your identity verification status");

            //Heading recommended actions
            common.verifyStringOnPage("Recommended actions for you");
            common.verifyStringOnPage("To get the most out of eduID we recommend that you follow the below recommendations.");

            //Recommended actions - Name
            recommendedActionName("English");

            //Recommended actions - Add Number
            recommendedActionAddNumber("English");

            //Recommended actions - Identity
            recommendedActionIdentity("English");

            //Recommended actions - Security key
            recommendedActionSecurityKey("English");
        }
        //Verified account text
        else if(testData.isAccountVerified()){
            Common.log.info("Verifying dashboard labels in english, Account is verified");

            common.verifyStringOnPage("Your identity verification status");
            common.verifyStringOnPage("Your identity is verified.");
            common.verifyStringOnPage("Your eduID is ready to use.");

            //Heading recommended actions
            common.verifyStringOnPage("Recommended actions for you");
            common.verifyStringOnPage("To get the most out of eduID we recommend that you follow the below recommendations.");

            //Recommended actions - Security key
            recommendedActionSecurityKey("English");
        }

        //Account has been verified earlier but after reset pw its not verified anymore
        else{
            Common.log.info("Verifying dashboard labels in english, Account has been verified");

            common.verifyStringOnPage("Your identity verification status");
            common.verifyStringOnPage("Your identity is no longer verified after password reset.");

            //Heading recommended actions
            common.verifyStringOnPage("Recommended actions for you");
            common.verifyStringOnPage("To get the most out of eduID we recommend that you follow the below recommendations.");

            //Recommended actions - Confirm or Add Number depending on whats already registered
            if(testData.getPhoneNumber().isEmpty())
                recommendedActionAddNumber("English");
            //TODO - fix me below...
            else if(!testData.isRegisterAccount())
                Common.log.info("Do nothing....?!?!?");
            else
                recommendedActionConfirmNumber("English");

            //Recommended actions - Identity
            recommendedActionIdentity("English");

            //Recommended actions - Security key
            recommendedActionSecurityKey("English");
        }

        //Select swedish
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectSwedish();
    }

    private void recommendedActionIdentity(String language){
        //Recommended actions - Identity
        if(language.equalsIgnoreCase("Svenska")) {
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3", "Verifiera din identitet");
            common.verifyStringOnPage("Din identitet är inte verifierad. Vänligen verifiera din identitet för att få tillgång till fler tjänster.");
            common.verifyStringOnPage("Gå till Identitet");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-verify-identity\"]/a"));
            common.explicitWaitPageTitle("Identitet | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3", "Verify your identity");
            common.verifyStringOnPage("Your identity is not verified. Please verify your identity to get access to more services.");
            common.verifyStringOnPage("Go to Identity");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-verify-identity\"]/a"));
            common.explicitWaitPageTitle("Identity | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
    }

    private void recommendedActionSecurityKey(String language){
       //Recommended actions - Security key
        if(language.equalsIgnoreCase("Svenska")) {
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3",
                    "Lägg till din säkerhetsnyckel");
            common.verifyStringOnPage("Lägg till en säkerhetsnyckel för att aktivera säker återställning av lösenord");
            common.verifyStringOnPage("Gå till Avancerade inställningar");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-security-key\"]/a"));
            common.explicitWaitPageTitle("Avancerade Inställningar | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");

            //FAQ
            common.verifyStringOnPage("Hjälp");

            //Language change
            common.verifyStringOnPage("English");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3",
                    "Add your security key");
            common.verifyStringOnPage("Add your security key to enable safe reset of password");
            common.verifyStringOnPage("Go to Advanced settings");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-security-key\"]/a"));
            common.explicitWaitPageTitle("Advanced Settings | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");

            //FAQ
            common.verifyStringOnPage("Help");

            //Language change
            common.verifyStringOnPage("Svenska");
        }
    }

    private void recommendedActionName(String language){
        //Recommended actions - Add name
        if(language.equalsIgnoreCase("Svenska")) {
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-add-name\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-add-name\"]/div/h3",
                    "Lägg till ditt namn");
            common.verifyStringOnPage("Namn kan användas för att anpassa tjänster som du kommer åt med ditt eduID.");
            common.verifyStringOnPage("Gå till Inställningar");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-add-name\"]/a"));
            common.explicitWaitPageTitle("Inställningar | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-add-name\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-add-name\"]/div/h3",
                    "Add your name");
            common.verifyStringOnPage("Name can be used to personalise services that you access with your eduID.");
            common.verifyStringOnPage("Go to Settings");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-add-name\"]/a"));
            common.explicitWaitPageTitle("Settings | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
    }

    private void recommendedActionAddNumber(String language){
        //Recommended actions - Add phone number
        if(language.equalsIgnoreCase("Svenska")) {
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3",
                    "Lägg till ditt telefonnummer");
            common.verifyStringOnPage("Lägg till ditt telefonnummer för att möjliggöra säker återställning " +
                    "av lösenord och fortsatt verifiering av din identitet.");
            common.verifyStringOnPage("Gå till Inställningar");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-phone\"]/a"));
            common.explicitWaitPageTitle("Inställningar | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3",
                    "Add your phone number");
            common.verifyStringOnPage("Add your phone number to enable safe reset of password and verification of identity.");
            common.verifyStringOnPage("Go to Settings");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-phone\"]/a"));
            common.explicitWaitPageTitle("Settings | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
    }

    private void recommendedActionConfirmNumber(String language){
        //Recommended actions - Add phone number
        if(language.equalsIgnoreCase("Svenska")) {
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3",
                    "Bekräfta ditt telefonnummer");
            common.verifyStringOnPage("Bekräfta ditt telefonnummer för att möjliggöra säker återställning " +
                    "av lösenord och verifiering av identitet.");
            common.verifyStringOnPage("Gå till Inställningar");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-phone\"]/a"));
            common.explicitWaitPageTitle("Inställningar | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3"));
            common.timeoutMilliSeconds(100);
            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-phone\"]/div/h3",
                    "Confirm your phone number");
            common.verifyStringOnPage("Confirm your phone number to enable safe reset of password and verification of identity.");
            common.verifyStringOnPage("Go to Settings");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-phone\"]/a"));
            common.explicitWaitPageTitle("Settings | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
    }
}
