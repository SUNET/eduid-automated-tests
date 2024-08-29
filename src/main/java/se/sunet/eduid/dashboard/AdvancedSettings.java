package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdvancedSettings {
    private final Common common;
    private final TestData testData;

    public AdvancedSettings(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runAdvancedSettings(){
        pressAdvancedSettings();
        verifyPageTitle();
        verifyLabels();
        pressAddSecurityKey();
        pressLadok();

        //TODO investigate why orcid does not work for tc 1
        if(testData.getTestCase().equalsIgnoreCase("TC_1"))
            pressOrcid();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Avancerade Inställningar | eduID");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();
    }

    public void pressAdvancedSettings(){
        common.navigateToAdvancedSettings();

        //Wait for heading "Gör ditt eduID säkrare"
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/h1");
    }

    private void pressAddSecurityKey(){
        //Click on add security key
        common.click(common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]"));

        common.switchToPopUpWindow();

        //Verify text
        common.explicitWaitVisibilityElement("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");


        //Press close, in corner of pop-up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
    }

    private void pressLadok(){
        Common.log.info("Verify Ladok");

        //Activate ladok
        common.timeoutSeconds(2);
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset/form/label/div"));
        common.timeoutSeconds(1);

        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/form/fieldset/label", "Välj lärosäte");
        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/form/fieldset/div/div/div[1]/div",
                "Tillgängliga lärosäten");

        //Extract all table rows in to a list of web elements
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/form/fieldset/div/div/div[1]/div"));

        WebElement elementName = common.findWebElementByXpath("//*[@id=\"ladok-container\"]/form/fieldset/div/div/div[2]");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        //Assert that there are at least two univeritys in the drop down
        Assert.assertTrue(rows.size() > 1,
                "Number of rows with available universitys are too low (are: " +rows.size() +" lower than 2)");

        //click on english
        common.selectEnglish();

        //Activate ladok button
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset/form/label/div"));
        common.timeoutMilliSeconds(200);

        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/form/fieldset/label",
                "Select higher education institution");
        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/form/fieldset/div/div/div[1]/div",
                "Available higher education institutions");

        //Scroll down to bottom of pagem, otherwise we get click exception when drop down not in page
        common.scrollToPageBottom();

        //Expand options
        common.findWebElementByXpath("//*[@id=\"ladok-container\"]/form").click();
        try {
            common.click(common.findWebElementByXpathContainingText("Linnaeus"));
        }catch (NoSuchElementException ex){
            Common.log.info("Failed to click on ESI drop down first time, trying again.");
            common.scrollToPageBottom();
            common.findWebElementByXpathContainingText("Available higher education institutions").click();
            common.timeoutMilliSeconds(500);
            common.findWebElementByXpathContainingText("Linnaeus").click();
        }
        common.timeoutMilliSeconds(500);

        //When Identity is confirmed
        if(testData.isIdentityConfirmed()){
            //Verify status message
            common.verifyStatusMessage("No information for you found in Ladok with that higher education institution.");

            //click on swedish
            common.selectSwedish();

            //Verify status message
            common.verifyStatusMessage("Ingen information om dig hittades i Ladok för detta lärosäte.");
        }
        //When Identity is not confirmed
        else {
            //Verify status message
            common.verifyStatusMessage("You need a verified Swedish national identity number to link your account with ESI.");

            //click on swedish
            common.selectSwedish();

            //Verify status message
            common.verifyStatusMessage("Du behöver verifiera ditt svenska personnummer för att länka ditt konto med ditt ESI.");
        }

        common.closeStatusMessage();
    }

    private void pressOrcid(){
        Common.log.info("Verify ORCID");
        common.explicitWaitClickableElementId("connect-orcid-button");

        common.scrollDown(500);
        common.findWebElementById("connect-orcid-button").click();
        Common.log.info("Clicked on ORCID");

        //Transferred to orcid after click
        //common.timeoutSeconds(8);
        common.timeoutSeconds(5);

        String title = common.getWebDriver().getTitle();
        if (title.equalsIgnoreCase("ORCID") || title.equalsIgnoreCase("Sign in - ORCID"))
            Common.log.info("ORCID page present");
        else {
            Common.log.info("ORCID NOT page present");
            Assert.fail("ORCID NOT page present");
        }

        //Accept cookies
/*
        try {
            common.timeoutSeconds(4);
            common.findWebElementById("onetrust-accept-btn-handler").click();
        }catch (Exception ex){
            Common.log.info("No cookie dialog present");
        }
*/

        //Just go back to end test case by logout
        common.getWebDriver().navigate().back();
        common.timeoutMilliSeconds(500);
        common.getWebDriver().navigate().back();
        //common.timeoutSeconds(5);
        common.explicitWaitPageTitle("Avancerade Inställningar | eduID");
    }

    private void verifyLabels(){
        //Swedish
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Avancerade inställningar");

        common.verifyStringOnPage("Förbättra ditt eduID");
        common.verifyStringOnPage("Öka säkerheten för ditt eduID eller anslut det till andra tjänster.");

        //Security key
        common.verifyStringOnPage("Tvåfaktorsautenticering (2FA)");
        common.verifyStringOnPage("Om möjligt lägg till ett ytterligare sätt att identifiera dig i " +
                "form av en säkerhetsnyckel, utöver användarnamn och lösenord, så att du är säker på att bara du har " +
                "tillgång till ditt eduID. Exempel på säkerhetsnycklar kan en USB-säkerhetsnyckel eller din enhet.");
        common.verifyStringOnPage("Du kan läsa mer om säkerhetsnycklar i hjälpavsnittet: Utökad säkerhet med ditt eduID.");
        common.verifyStringOnPage("Välj ytterligare identifieringsmetod:");
        common.verifyStringById("security-webauthn-button", "SÄKERHETSNYCKEL");
        common.verifyStringOnPage("T.ex. USB säkerhetsnyckel som du använder.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "DEN HÄR ENHETEN");
            common.verifyStringOnPage("T.ex. Touch/ Face ID som stöds på den här enheten.");
        }

        //OrcID
        common.verifyStringOnPage("Länka till ditt ORCID konto");
        common.verifyStringOnPage("Om du är forskare med ett ORCID iD kan du dela det med ditt eduID.");
        common.verifyStringOnPage("ORCID ger en beständig identifierare, ett ORCID iD, som unikt särskiljer " +
                "dig från andra forskare och en mekanism för att koppla dina forskningsresultat och aktiviteter till ditt " +
                "ORCID iD oberoende vilken organisation du är verksam vid.");

        //ESI information
        common.verifyStringOnPage("ESI information");
        common.verifyStringOnPage("Vissa lärosäten har anslutit sig till att låta eduID hämta ut ditt " +
                "ESI - European Student Identifier från Ladok för att kunna få tillgång till vissa tjänster.");
        common.verifyStringOnPage("Länka ditt ESI till ditt eduID-konto");
        common.verifyStringOnPage("Finns ditt lärosäte inte i listan ska du inte använda eduID för att " +
                "logga in i tjänster som kräver ESI. Kontakta då istället ditt lärosäte för mer information.");

/*        common.verifyStringOnPage("Unikt ID");
        common.verifyStringOnPage("Detta unika ID är ett användarnamn för ditt eduID som du kan behöva " +
                "ange för att identifiera ditt konto eller vid teknisk support. Det är en del av vad som kan hänvisas till som EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/div/span/strong", "Unikt ID: ");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));*/

        //click on english
        common.selectEnglish();

        common.verifyPageTitle("Advanced Settings | eduID");

        //English
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Advanced settings");

        common.verifyStringOnPage("Enhance your eduID");
        common.verifyStringOnPage("Increase the security of your eduID or connect it to other services.");

        //Security key
        common.verifyStringOnPage("Two-factor Authentication (2FA)");
        common.verifyStringOnPage("If possible add a security key as a second factor of authentication, " +
                "beyond username and password, to prove you are the owner of your eduID. Examples are USB security keys or your device.");
        common.verifyStringOnPage("You can read more about security keys in the Help section: Improving the security level of eduID.");
        common.verifyStringOnPage("Choose additional identification method:");
        common.verifyStringById("security-webauthn-button", "SECURITY KEY");
        common.verifyStringOnPage("E.g a USB Security Key you are using.");
        if(testData.getBrowser().equalsIgnoreCase("chrome") && testData.getHeadlessExecution().equalsIgnoreCase("false")){
            common.verifyStringById("security-webauthn-platform-button", "THIS DEVICE");
            common.verifyStringOnPage("E.g. Touch/ Face ID supported on this device.");
        }

        //OrcID
        common.verifyStringOnPage("ORCID account");
        common.verifyStringOnPage("If you are a researcher with an ORCID iD you can share it with your eduID.");
        common.verifyStringOnPage("ORCID iD distinguishes you from other researchers and allows linking of " +
                "your research outputs and activities to your identity, regardless of the organisation you are working with.");

        //ESI information
        common.verifyStringOnPage("ESI information");
        common.verifyStringOnPage("Some higher education institutions allow eduID to fetch your " +
                "ESI - European Student identifier from Ladok, which might give you access to certain services.");
        common.verifyStringOnPage("Link your ESI with your eduID account");
        common.verifyStringOnPage("If your institution is not in the list you cannot use your eduID " +
                "to access services requiring ESI, contact your institution for more information.");

/*        common.verifyStringOnPage("Unique ID");
        common.verifyStringOnPage("This identifier is a username for your eduID that you may need to " +
                "provide when accessing other services or requesting support. It is part of what may be referred to as EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/div/span/strong", "Unique ID: ");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));*/

        //click on swedish
        common.selectSwedish();
    }
}
