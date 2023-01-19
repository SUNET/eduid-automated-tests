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
/*        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            verifyNotificationDot();
        verifyUserId();
        verifyDisplayName();
        verifyGivenName();
        verifySurName();

 */
        //Ignore check of ID number when not present
/*        if(!testData.getIdentityNumber().equalsIgnoreCase("lägg till personnummer"))
            verifyIdentityNumber();
        verifyPhone();
        verifyEmail();*/
//        if(testData.getLanguage().equals("Svenska"))
//            verifyLabelsSwedish();

 /*
        else
            verifyLabelsEnglish();

        pressSettings();

  */
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

    private void verifyNotificationDot(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/li[2]/a/div/div").isDisplayed();
    }

    private void verifyUserId() {
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
    }

    private void verifyDisplayName() {
        common.verifyXpathContainsString("//*[@id=\"profile-grid\"]/div[1]", testData.getDisplayName()); }

    private void verifyGivenName() {
        common.verifyXpathContainsString("//*[@id=\"profile-grid\"]/div[1]",testData.getGivenName()); }

    private void verifySurName() {
        common.verifyXpathContainsString("//*[@id=\"profile-grid\"]/div[1]",testData.getSurName());
    }

    private void verifyIdentityNumber() {
        //Check text on link for hide/show full identityNumber
        checkShowHideText("VISA", "SHOW");

        //Click on Show button to display complete personal nummber
        common.click(common.findWebElementByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button"));
        common.verifyStringOnPage(testData.getIdentityNumber());

        //Check text on link for hide/show full identityNumber
        checkShowHideText("DÖLJ", "HIDE");
    }

    private void verifyPhone() {
        common.verifyStringOnPage(testData.getPhoneNumber());
    }

    private void verifyEmail() {
        common.verifyStringOnPage(testData.getEmail().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        common.verifyPageTitle("Start | eduID");

        //Verify welcome heading
        common.verifyStringOnPage("Välkommen, " +testData.getDisplayName() +"!");

        //Verify heading sub-text
        common.verifyStringOnPage("Få ut det mesta av eduID genom att granska din information.");

        //Non-verified account text
        common.verifyStringOnPage("Nuvarande framsteg i din identitets verifiering");

        //Verify icons - eduID created
        common.timeoutSeconds(3);
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/strong", "Skapa eduID");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-check");
        //Common.log.info("PELLE_:: " +common.getAttributeByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/svg"));

        //Verify icons - eduID identity verified
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[2]/strong", "Verifiera din identitet");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[2]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-exclamation");

        //Verify icons - eduID created
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[3]/strong", "För att slutföra det sista steget behöver du verifiera ett svenskt personnummer");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[3]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-exclamation");

        //Heading recommended actions
        common.verifyStringOnPage("Rekommenderade åtgärder för dig");
        common.verifyStringOnPage("För att få ut det mesta av eduID rekommenderar vi att du följer nedanstående rekommendationer.");


        //Recommended actions - Identity
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-raa-5\"]/span[2]/h6"));
        common.timeoutMilliSeconds(100);
        common.verifyStringByXpath("//*[@id=\"accordion__heading-raa-5\"]/span[2]/h6", "Verifiera din identitet");
        common.verifyStringOnPage("Din identitet är inte verifierad. Vänligen verifiera din identitet för att få tillgång till fler tjänster.");
        common.verifyStringOnPage("Gå till Identitet");
        common.findWebElementByXpath("//*[@id=\"accordion__panel-raa-33\"]/a").click();
        common.explicitWaitPageTitle("Identitet | eduID");
        common.getWebDriver().navigate().back();

        //Recommended actions - Security key
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-raa-15\"]/span[2]/h6"));
        common.timeoutMilliSeconds(100);
        common.verifyStringByXpath("//*[@id=\"accordion__heading-raa-15\"]/span[2]/h6", "Lägg till din säkerhetsnyckel");
        common.verifyStringOnPage("Lägg till en säkerhetsnyckel för att aktivera säker återställning av lösenord");
        common.verifyStringOnPage("Gå till Avancerade inställningar");
        common.findWebElementByXpath("//*[@id=\"accordion__panel-raa-55\"]/a").click();
        common.explicitWaitPageTitle("Identitet | eduID");
        common.getWebDriver().navigate().back();

        //FAQ
        common.verifyStringOnPage("Hjälp");

        //Language change
        common.verifyStringOnPage("English");
    }

    private void verifyLabelsEnglish() {
        common.verifyPageTitle("Start | eduID");

        //Verify site location menu
        common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/a", "Start");

        //Verify welcome heading
        common.verifyStringOnPage("Welcome, " +testData.getDisplayName() +"!");

        //Verify heading sub-text
        common.verifyStringOnPage("Make the most out of eduID by reviewing your information.");

        //Non-verified account text
        common.verifyStringOnPage("Your identity verification progress");

        //Verify icons - eduID created
        common.timeoutSeconds(3);
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/strong", "Create eduID");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-check");
        //Common.log.info("PELLE_:: " +common.getAttributeByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[1]/svg"));

        //Verify icons - eduID identity verified
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[2]/strong", "Verify your identity");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[2]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-exclamation");

        //Verify icons - eduID created
        common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[3]/strong", "To complete the last step, you need to verify a Swedish national ID number");
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[3]/span[3]/svg").getAttribute("data-icon").equalsIgnoreCase("circle-exclamation");

        //Heading recommended actions
        common.verifyStringOnPage("Recommended actions for you");
        common.verifyStringOnPage("To get the most out of eduID we recommend that you follow the below recommendations.");


        //Recommended actions - Identity            "//input[contains(@id, '-fromDate')]"
        common.click(common.findWebElementByXpath("//[contains(@id, 'accordion__heading-raa-5\"]/span[2]/h6"));
        common.timeoutMilliSeconds(100);
        common.verifyStringByXpath("//*[@id=\"accordion__heading-raa-5\"]/span[2]/h6", "Verify your identity");
        common.verifyStringOnPage("Your identity is not verified. Please verify your identity to get access to more services.");
        common.verifyStringOnPage("Go to Identity");
        common.findWebElementByXpath("//*[@id=\"accordion__panel-raa-33\"]/a").click();
        common.explicitWaitPageTitle("Identity | eduID");
        common.getWebDriver().navigate().back();

        //Recommended actions - Security key
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__heading-raa-15\"]/span[2]/h6"));
        common.timeoutMilliSeconds(100);
        common.verifyStringByXpath("//*[@id=\"accordion__heading-raa-15\"]/span[2]/h6", "Add your security key");
        common.verifyStringOnPage("Add your security key to enable safe reset of password");
        common.verifyStringOnPage("Go to Advanced settings");
        common.findWebElementByXpath("//*[@id=\"accordion__panel-raa-55\"]/a").click();
        common.explicitWaitPageTitle("Advanced Settings | eduID");
        common.getWebDriver().navigate().back();

        //FAQ
        common.verifyStringOnPage("Hjälp");

        //Language change
        common.verifyStringOnPage("English");
    }

    private void checkShowHideText(String textSwedish, String textEnglish){
        if(testData.getLanguage().equalsIgnoreCase("Svenska"))
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button", textSwedish);
        else
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button", textEnglish);
    }

    public void pressSettings(){
        common.navigateToSettings();

        //wait for one "add more" button to be clickable
        common.explicitWaitClickableElementId("emails-add-more-button");
    }
}
