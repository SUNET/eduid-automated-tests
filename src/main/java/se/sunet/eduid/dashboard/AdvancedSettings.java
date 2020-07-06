package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class AdvancedSettings {
    private Common common;

    public AdvancedSettings(Common common){
        this.common = common;
    }

    public void runAdvancedSettings(){
        verifyPageTitle();
        pressAdvancedSettings();
        verifyLabels();
        storeEppn();
        pressAddSecurityKey();
        pressOrcid();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void pressAdvancedSettings(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span").click();
    }

    private void storeEppn(){
        common.setEppn(common.findWebElementByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]").getText());
    }

    private void pressAddSecurityKey(){
        common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]/span").click();

        common.switchToPopUpWindow();

        //Verify text
        common.explicitWaitVisibilityElement("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span", "Ge ett namn till säkerhetsnyckeln");

        //Press abort
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[2]/span").click();

//        common.switchToDefaultWindow();
    }

    private void pressOrcid(){
        common.timeoutMilliSeconds(500);
        //common.explicitWaitClickableElement("//*[@id=\"connect-orcid-button\"]/span");
        common.findWebElementByXpath("//*[@id=\"connect-orcid-button\"]/span").click();

        //Transferred to orcid after click
        common.explicitWaitPageTitle("ORCID");
        common.verifyPageTitle("ORCID");

        //Just go back to end test case by logout
        WebDriverManager.getWebDriver().navigate().back();
        common.timeoutMilliSeconds(200);
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

        common.verifyStringOnPage("Unikt ID");
        common.verifyStringOnPage("Detta är ett autogenererat unikt id för ditt eduID som du kan behöva " +
                "ange när du ber om teknisk support.");
        common.verifyStringOnPage("EPPN");
        common.verifyStringNotEmptyByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]", "//*[@id=\"uniqueId-container\"]/div[2]/label/span");

        //click on english
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //English
        common.verifyStringOnPage("Make your eduID more secure");
        common.verifyStringOnPage("Add a security key as a second layer of identification, beyond email " +
                "and password, to prove you are the owner of your eduID.");

        common.verifyStringOnPage("ORCID account");
        common.verifyStringOnPage("If you are a reseacher with an ORCID iD you can share it with your eduID.");
        common.verifyStringOnPage("ORCID iD distinguishes you from other researchers and allows linking of " +
                "your research outputs and activities to your identity, regardless of the organisation you are working with.");

        common.verifyStringOnPage("Unique ID");
        common.verifyStringOnPage("This is an automatically generated unique identifier for your eduID.");
        common.verifyStringOnPage("EPPN");
        common.verifyStringNotEmptyByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]", "//*[@id=\"uniqueId-container\"]/div[2]/label/span");
        common.verifyStringOnPage("You might be asked to share this information if you need technical support.");

        //click on swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }
}
