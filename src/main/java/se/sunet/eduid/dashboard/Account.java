package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.util.List;

public class Account {
    private final Common common;
    private final TestData testData;

    public Account(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runAccount(){
        common.navigateToAccount();

        //TODO change language
        if(testData.getLanguage() != null) {
            selectLanguage();
        }
        else {
            verifyPageTitle();
            verifyLabels();
        }

        //TODO investigate why orcid does not work for tc 1
        if(testData.getTestCase().equalsIgnoreCase("TC_1")){
            pressLadok();
            pressOrcid();
        }
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Konto | eduID");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void pressLadok(){
        Common.log.info("Verify Ladok");

        //Activate ladok
        //common.timeoutSeconds(2);
        common.scrollToPageBottom();
        common.findWebElementByXpath("//*[@id=\"ladok\"]/form/fieldset/label/div").click();
        common.timeoutSeconds(1);

        String availableUniversitys = "//*[@id=\"content\"]/article[6]/form[2]/fieldset/div/div/div[1]/div";

        common.verifyStringByXpath("//*[@id=\"content\"]/article[6]/form[2]/fieldset/label", "Välj lärosäte");
        common.verifyStringByXpath(availableUniversitys, "Tillgängliga lärosäten");

        //Extract all table rows in to a list of web elements
        common.click(common.findWebElementByXpathContainingText("Tillgängliga lärosäten"));
        common.timeoutSeconds(1);

        WebElement elementName = common.findWebElementByXpath("//*[@id=\"react-select-3-listbox\"]/div");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        //Assert that there are at least two univeritys in the drop down
        Assert.assertTrue(rows.size() > 1,
                "Number of rows with available universitys are too low (are: " +rows.size() +" lower than 2)");

        //click on english
        common.selectEnglish();

        //Activate ladok button
        common.click(common.findWebElementById("ladok-connection"));
        common.timeoutMilliSeconds(200);

        common.verifyStringByXpath("//*[@id=\"content\"]/article[6]/form[2]/fieldset/label",
                "Select higher education institution");
        common.verifyStringByXpath(availableUniversitys, "Available higher education institutions");

        //Scroll down to bottom of page, otherwise we get click exception when drop down not in page
        common.scrollToPageBottom();

        //Expand options
        common.findWebElementByXpath("//*[@id=\"content\"]/article[6]/form[2]/fieldset/div/div[1]/div[1]/div").click();
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

        //common.scrollDown(800);
        common.click(common.findWebElementById("connect-orcid-button"));
        Common.log.info("Clicked on ORCID");

        //Transferred to orcid after click
        common.timeoutSeconds(2);

        String title = common.getWebDriver().getTitle();
        if (title.equalsIgnoreCase("ORCID") || title.equalsIgnoreCase("Sign in - ORCID"))
            Common.log.info("ORCID page present");
        else {
            Common.log.info("ORCID NOT page present, page title is: " +common.getWebDriver().getTitle());
            Assert.fail("ORCID NOT page present, page title is: " +common.getWebDriver().getTitle());
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
        common.explicitWaitPageTitle("Konto | eduID");
    }

    private void verifyLabels(){
        //Extract page body for validation
        String pageBody = common.getPageBody();

        Common.log.info("Verify Account page labels - Swedish");

        //Swedish
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Konto");

        //Swedish
        //Verify site location menu, beside Start link

        common.verifySiteLocation("Konto");

        common.verifyPageBodyContainsString(pageBody,"Konto");
        common.verifyPageBodyContainsString(pageBody,"Uppdatera dina eduID-kontoinställningar, byt lösenord eller ta bort ditt eduID.");

        //EPPN
        common.verifyPageBodyContainsString(pageBody,"Unikt ID");
        common.verifyPageBodyContainsString(pageBody,"Detta unika ID är ett användarnamn för ditt eduID " +
                "som du kan behöva ange för att identifiera ditt konto eller vid teknisk support. Det är en del av vad " +
                "som kan hänvisas till som EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/label/strong", "Unikt ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/button", "KOPIERA");

        //OrcID
        common.verifyPageBodyContainsString(pageBody,"Länka till ditt ORCID konto");
        common.verifyPageBodyContainsString(pageBody,"Om du är forskare med ett ORCID iD kan du dela det med ditt eduID.");
        common.verifyPageBodyContainsString(pageBody,"ORCID ger en beständig identifierare, ett ORCID iD, som unikt särskiljer " +
                "dig från andra forskare och en mekanism för att koppla dina forskningsresultat och aktiviteter till ditt " +
                "ORCID iD oberoende vilken organisation du är verksam vid.");

        //ESI information
        common.verifyPageBodyContainsString(pageBody,"ESI information");
        common.verifyPageBodyContainsString(pageBody,"Vissa lärosäten har anslutit sig till att låta eduID hämta ut ditt " +
                "ESI - European Student Identifier från Ladok för att kunna få tillgång till vissa tjänster.");
        common.verifyPageBodyContainsString(pageBody,"Länka ditt ESI till ditt eduID-konto");
        common.verifyPageBodyContainsString(pageBody,"Finns ditt lärosäte inte i listan ska du inte använda eduID för att " +
                "logga in i tjänster som kräver ESI. Kontakta då istället ditt lärosäte för mer information.");

        //click on english
        common.selectEnglish();

        Common.log.info("Verify Account page labels - English");

        common.verifyPageTitle("Account | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();


        //English
        //Verify site location menu, beside Start link
        common.verifySiteLocation("Account");

        common.verifyPageBodyContainsString(pageBody,"Account");
        common.verifyPageBodyContainsString(pageBody,"Update your eduID account settings, change password or delete your eduID.");

        //EPPN
        common.verifyPageBodyContainsString(pageBody,"Unique ID");
        common.verifyPageBodyContainsString(pageBody,"This identifier is a username for your eduID that " +
                "you may need to provide when accessing other services or requesting support. It is part of what may be " +
                "referred to as EPPN.");
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/label/strong", "Unique ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/button", "COPY");

        //OrcID
        common.verifyPageBodyContainsString(pageBody,"ORCID account");
        common.verifyPageBodyContainsString(pageBody,"If you are a researcher with an ORCID iD you can share it with your eduID.");
        common.verifyPageBodyContainsString(pageBody,"ORCID iD distinguishes you from other researchers and allows linking of " +
                "your research outputs and activities to your identity, regardless of the organisation you are working with.");

        //ESI information
        common.verifyPageBodyContainsString(pageBody,"ESI information");
        common.verifyPageBodyContainsString(pageBody,"Some higher education institutions allow eduID to fetch your " +
                "ESI - European Student identifier from Ladok, which might give you access to certain services.");
        common.verifyPageBodyContainsString(pageBody,"Link your ESI with your eduID account");
        common.verifyPageBodyContainsString(pageBody,"If your institution is not in the list you cannot use your eduID " +
                "to access services requiring ESI, contact your institution for more information.");

        //click on swedish
        common.selectSwedish();
    }

    private void selectLanguage() {
        Common.log.info("Setting application language in settings to: " +testData.getLanguage());
        //Click on change
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button").click();

        //Change to Swedish
        if(testData.getLanguage().equalsIgnoreCase("Svenska")){
            //Verify button text, before change
            common.verifyStringByXpath("//*[@id=\"personaldata-view-form\"]/fieldset/article/div/label[2]/span", "Svenska");

            //Select new language - Swedish
            common.click(common.findWebElementById("Svenska"));

            //pressSaveButton();

            common.timeoutSeconds(1);
        }
        //Change to English
        else if(testData.getLanguage().equalsIgnoreCase("English")){
            //Verify button text, before change
            common.verifyStringByXpath("//*[@id=\"personaldata-view-form\"]/fieldset/article/div/label[1]/span", "English");

            //Select new language - English
            //common.click(common.findWebElementByXpath("//*[@id=\"personaldata-view-form\"]/fieldset[2]/div/label[1]/input"));
            common.click(common.findWebElementById("English"));

            //pressSaveButton();

            common.timeoutSeconds(1);
        }
    }
}
