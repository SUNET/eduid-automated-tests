package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

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
        storeEppn();
        pressAddSecurityKey();
        pressLadok();
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
        common.explicitWaitVisibilityElement("//*[@id=\"register-security-key-container\"]/div[1]/h3");
    }

    public void storeEppn(){
        testData.setEppn(common.findWebElementByXpath("//*[@id=\"uniqueId-container\"]/div[2]/div").getText());
        Common.log.info("Saved EPPN: " +testData.getEppn());
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

//        else{
            //Press close, in corner of pop-up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
  //      }
    }

    private void pressLadok(){
        //Activate ladok
        common.timeoutSeconds(2);
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset[1]/label/div"));
        common.timeoutSeconds(1);

        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/label", "Välj lärosäte");
        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/div/div/div[1]/div", "Tillgängliga lärosäten");

        //Extract all table rows in to a list of web elements
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/div/div/div[1]/div"));

        WebElement elementName = common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/div/div/div[2]");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        //Assert that there are at least two univeritys in the drop down
        Assert.assertTrue(rows.size() > 1, "Number of rows with available universitys are too low (are: " +rows.size() +" lower than 2)");

        //click on english
        common.selectEnglish();

        //Activate ladok button
        common.click(common.findWebElementByXpath("//*[@id=\"ladok-container\"]/fieldset[1]/label/div"));
        common.timeoutMilliSeconds(200);

        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/label", "Select higher education institution");
        common.verifyStringByXpath("//*[@id=\"ladok-container\"]/fieldset[3]/form/div/div/div[1]/div", "Available higher education institutions");

        //Scroll down to bottom of pagem, otherwise we get click exception when drop down not in page
        common.scrollToPageBottom();

        //Expand options
        common.findWebElementByXpathContainingText("Available higher education institutions").click();
        common.timeoutMilliSeconds(500);

        //Select one of them
        common.findWebElementByXpathContainingText("Linnaeus").click();

        //TODO with a verified account, you will get another message

        //Verify status message
        common.verifyStatusMessage("You need a verified Swedish national identity number to link your account with Ladok.");

        //click on swedish
        common.selectSwedish();

        //Verify status message
        common.verifyStatusMessage("Du behöver verifiera ditt svenska personnummer för att länka ditt konto med Ladok.");
    }

    private void pressOrcid(){
        common.timeoutMilliSeconds(500);
        common.click(common.findWebElementByXpath("//*[@id=\"connect-orcid-button\"]"));

        //Transferred to orcid after click
        common.explicitWaitPageTitle("ORCID");
        common.verifyPageTitle("ORCID");

        //Just go back to end test case by logout
        common.getWebDriver().navigate().back();
        common.explicitWaitPageTitle("Avancerade Inställningar | eduID");
    }

    private void verifyLabels(){
        //Swedish
        common.verifyStringOnPage("Gör ditt eduID säkrare");
        common.verifyStringOnPage("Lägg till ett extra sätt, utöver email och lösenord, för att kunna " +
                "identifiera dig så du är säker på att bara du har tillgång till ditt eduID.");

        common.verifyStringOnPage("Länka till ditt ORCID konto");
        common.verifyStringOnPage("Om du är forskare med ett ORCID iD kan du dela det med ditt eduID.");
        common.verifyStringOnPage("ORCID ger en beständig identifierare, ett ORCID iD, som unikt särskiljer " +
                "dig från andra forskare och en mekanism för att koppla dina forskningsresultat och aktiviteter till ditt " +
                "ORCID iD oberoende vilken organisation du är verksam vid.");

        common.verifyStringOnPage("Ladok information");
        common.verifyStringOnPage("Data från Ladok kan ge dig tillgång till fler tjänster. Vissa " +
                "lärosäten låter eduID hämta data från Ladok.");
        common.verifyStringOnPage("Länka ditt konto till Ladok");
        common.verifyStringOnPage("Att länka ditt eduID-konto med Ladok är nödvändigt om du vill komma " +
                "åt en tjänst som kräver en European Student Identifier.");

        common.verifyStringOnPage("Unikt ID");
        common.verifyStringOnPage("Detta är ett autogenererat unikt id för ditt eduID som du kan behöva " +
                "ange när du ber om teknisk support.");
        common.verifyStringOnPage("eppn");
        common.verifyStringNotEmptyByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]", "//*[@id=\"uniqueId-container\"]/div[2]/label");

        //click on english
        common.selectEnglish();

        common.verifyPageTitle("Advanced Settings | eduID");

        //English
        common.verifyStringOnPage("Make your eduID more secure");
        common.verifyStringOnPage("Add a security key as a second layer of identification, beyond email " +
                "and password, to prove you are the owner of your eduID.");

        common.verifyStringOnPage("ORCID account");
        common.verifyStringOnPage("If you are a researcher with an ORCID iD you can share it with your eduID.");
        common.verifyStringOnPage("ORCID iD distinguishes you from other researchers and allows linking of " +
                "your research outputs and activities to your identity, regardless of the organisation you are working with.");

        common.verifyStringOnPage("Ladok information");
        common.verifyStringOnPage("Data from Ladok might give you access to more services. Some higher " +
                "education institutions allow eduID to fetch data from Ladok.");
        common.verifyStringOnPage("Link your account to Ladok");
        common.verifyStringOnPage("Linking your eduID account with data from Ladok is necessary if you " +
                "want to access a service requiring a European Student Identifier.");

        common.verifyStringOnPage("Unique ID");
        common.verifyStringOnPage("This is an automatically generated unique identifier for your eduID.");
        common.verifyStringOnPage("eppn");
        common.verifyStringNotEmptyByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]", "//*[@id=\"uniqueId-container\"]/div[2]/label");
        common.verifyStringOnPage("You might be asked to share this information if you need technical support.");

        //click on swedish
        common.selectSwedish();
    }
}
