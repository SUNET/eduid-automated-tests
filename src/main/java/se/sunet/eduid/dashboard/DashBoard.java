package se.sunet.eduid.dashboard;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class DashBoard {
    private final Common common;
    private final TestData testData;
    String pageBody;

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
        //TODO temp fix to get swedish language - needed when new accounts created
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska")
                && testData.getLanguage().equalsIgnoreCase("Svenska")) {
            common.selectSwedish();
        }

        common.verifyPageTitle("Start | eduID");
    }

    private void verifyUserId() {
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/button/span", testData.getUsername().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify navigation label
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));

        //Verify welcome heading
        if (testData.getDisplayName().isEmpty())
            common.verifyPageBodyContainsString(pageBody, "Välkommen, " + testData.getEmail().toLowerCase() + "!");
        else {
            //Ignore case-sensitive since when double name all names are shown with capital letter
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Välkommen, " + testData.getDisplayName() + "!");
        }

        //Verify heading sub-text
        common.verifyPageBodyContainsString(pageBody, "Få ut det mesta av eduID genom att granska din information.");

        //EPPN
        common.verifyPageBodyContainsString(pageBody, "Unikt ID");
        common.verifyPageBodyContainsString(pageBody, "Detta unika ID är ett användarnamn för ditt eduID som du kan behöva " +
                "ange för att identifiera ditt konto eller vid teknisk support. Det är en del av vad som kan hänvisas till som EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/div/span/strong", "Unikt ID: ");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));

        //Non-verified account text
        if(!testData.isAccountVerified() && testData.isRegisterAccount()) {
            Common.log.info("Verifying dashboard labels in swedish, Account is not verified");

            common.verifyPageBodyContainsString(pageBody, "Status för verifiering av din identitet");
            common.verifyPageBodyContainsString(pageBody, "Din identitet är inte verifierad.");
            common.verifyPageBodyContainsString(pageBody, "Vänligen se rekommenderade åtgärder nedan.");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Rekommenderade åtgärder för dig");
            common.verifyPageBodyContainsString(pageBody, "För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Identity
            recommendedActionIdentity("Svenska");

            //Recommended actions - Security key
            recommendedActionSecurityKey("Svenska");
        }

        //Verified account text
        else if(testData.isAccountVerified()){
            Common.log.info("Verifying dashboard labels in swedish, Account is verified");

            common.verifyPageBodyContainsString(pageBody, "Status för verifiering av din identitet");
            common.verifyPageBodyContainsString(pageBody, "Din identitet är verifierad.");
            common.verifyPageBodyContainsString(pageBody, "Ditt eduID är redo att användas.");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Rekommenderade åtgärder för dig");
            common.verifyPageBodyContainsString(pageBody, "För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Security key
            recommendedActionSecurityKey("Svenska");
        }

        //Account has been verified earlier but after reset pw its not verified anymore
        else{
            Common.log.info("Verifying dashboard labels in swedish, Account has previously been verified");

            common.verifyPageBodyContainsString(pageBody, "Status för verifiering av din identitet");
            common.verifyPageBodyContainsString(pageBody, "Din identitet är inte längre verifierad efter lösenordsåterställningen.");
            common.verifyPageBodyContainsString(pageBody, "Vänligen se rekommenderade åtgärder nedan.");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Rekommenderade åtgärder för dig");
            common.verifyPageBodyContainsString(pageBody, "För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");

            //Recommended actions - Identity
            recommendedActionIdentity("Svenska");

            //Recommended actions - Security key
            recommendedActionSecurityKey("Svenska");
        }
    }

    private void verifyLabelsEnglish() {
        //Select English
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectEnglish();

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));

        //Verify welcome heading
        if(testData.getDisplayName().isEmpty()) {
            common.verifyPageBodyContainsString(pageBody, "Welcome, " + testData.getEmail().toLowerCase() + "!");
        }
        else {
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Welcome, " + testData.getDisplayName() + "!");
        }

        //Verify heading sub-text
        common.verifyPageBodyContainsString(pageBody, "Make the most out of eduID by reviewing your information.");

        //EPPN
        common.verifyPageBodyContainsString(pageBody, "Unique ID");
        common.verifyPageBodyContainsString(pageBody, "This identifier is a username for your eduID that you may need to " +
                "provide when accessing other services or requesting support. It is part of what may be referred to as EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/div/span/strong", "Unique ID: ");
        if(testData.isRegisterAccount()) {
            //Just check that eppn is 11 characters long
            Assert.assertTrue(common.findWebElementById("user-eppn").getAttribute("value").length() == 11,
                    "EPPN seems to be missing or not correct length");
        }
        else
            common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));

        //Non-verified accounts
        if(!testData.isAccountVerified() && testData.isRegisterAccount()) {
            Common.log.info("Verifying dashboard labels in english, Account is not verified");

            //Non-verified account text
            common.verifyPageBodyContainsString(pageBody, "Your identity verification status");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Recommended actions for you");
            common.verifyPageBodyContainsString(pageBody, "To get the most out of eduID we recommend that you follow the below recommendations.");

            //Recommended actions - Identity
            recommendedActionIdentity("English");

            //Recommended actions - Security key
            recommendedActionSecurityKey("English");
        }
        //Verified account text
        else if(testData.isAccountVerified()){
            Common.log.info("Verifying dashboard labels in english, Account is verified");

            common.verifyPageBodyContainsString(pageBody, "Your identity verification status");
            common.verifyPageBodyContainsString(pageBody, "Your identity is verified.");
            common.verifyPageBodyContainsString(pageBody, "Your eduID is ready to use.");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Recommended actions for you");
            common.verifyPageBodyContainsString(pageBody, "To get the most out of eduID we recommend that you follow the below recommendations.");

            //Recommended actions - Security key
            recommendedActionSecurityKey("English");
        }

        //Account has been verified earlier but after reset pw its not verified anymore
        else{
            Common.log.info("Verifying dashboard labels in english, Account has been verified");

            common.verifyPageBodyContainsString(pageBody, "Your identity verification status");
            common.verifyPageBodyContainsString(pageBody, "Your identity is no longer verified after password reset.");

            //Heading recommended actions
            common.verifyPageBodyContainsString(pageBody, "Recommended actions for you");
            common.verifyPageBodyContainsString(pageBody, "To get the most out of eduID we recommend that you follow the below recommendations.");

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
            common.timeoutMilliSeconds(200);

            //Extract page body for validation
            pageBody = common.getPageBody();

            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3", "Verifiera din identitet");

            //Check if account is new and has not been verified before
            if(testData.isRegisterAccount()){
                common.verifyPageBodyContainsString(pageBody, "Din identitet är inte verifierad.");
            }
            else {
                common.verifyPageBodyContainsString(pageBody, "Din identitet är inte längre verifierad efter lösenordsåterställningen.");
            }
            common.verifyPageBodyContainsString(pageBody, "Vänligen se rekommenderade åtgärder nedan.");
            common.verifyPageBodyContainsString(pageBody, "Gå till Identitet");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-verify-identity\"]/a"));
            common.explicitWaitPageTitle("Identitet | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3"));
            common.timeoutMilliSeconds(200);

            //Extract page body for validation
            pageBody = common.getPageBody();

            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-verify-identity\"]/div/h3", "Verify your identity");

            //Check if account is new and has not been verified before
            if(testData.isRegisterAccount()){
                common.verifyPageBodyContainsString(pageBody, "Your identity is not verified.");
            }
            else {
                common.verifyPageBodyContainsString(pageBody, "Your identity is no longer verified after password reset.");
            }
            common.verifyPageBodyContainsString(pageBody, "Please see the recommended actions below.");
            common.verifyPageBodyContainsString(pageBody, "Go to Identity");
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
            common.timeoutMilliSeconds(200);

            //Extract page body for validation
            pageBody = common.getPageBody();

            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3",
                    "Lägg till din säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Lägg till en säkerhetsnyckel för att aktivera säker återställning av lösenord");
            common.verifyPageBodyContainsString(pageBody, "Gå till Avancerade inställningar");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-security-key\"]/a"));
            common.explicitWaitPageTitle("Avancerade Inställningar | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");

            //FAQ
            common.verifyPageBodyContainsString(pageBody, "Hjälp");

            //Language change
            common.verifyPageBodyContainsString(pageBody, "English");
        }
        else{
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3"));
            common.timeoutMilliSeconds(200);

            //Extract page body for validation
            pageBody = common.getPageBody();

            common.verifyStringByXpath("//*[@id=\"accordion__heading-recommendation-security-key\"]/div/h3",
                    "Add your security key");
            common.verifyPageBodyContainsString(pageBody, "Add your security key to enable safe reset of password");
            common.verifyPageBodyContainsString(pageBody, "Go to Advanced settings");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-recommendation-security-key\"]/a"));
            common.explicitWaitPageTitle("Advanced Settings | eduID");

            common.getWebDriver().navigate().back();
            common.explicitWaitPageTitle("Start | eduID");

            //FAQ
            common.verifyPageBodyContainsString(pageBody, "Help");

            //Language change
            common.verifyPageBodyContainsString(pageBody, "Svenska");
        }
    }
}
