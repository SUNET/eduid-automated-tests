package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Identity {
    private final Common common;
    private final TestData testData;

    public Identity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runIdentity(){
        pressIdentity();
        verifyPageTitle();
        verifyIdentity();

        if(testData.getTestSuite().equalsIgnoreCase("prod"))
            common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Ditt eduID är redo att användas");
        else
            verifyLabels();
    }

    private void pressIdentity(){
        common.navigateToIdentity();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language
        //if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
          //  common.selectSwedish();
    }

    private void verifyIdentity(){
        //Click on show/hide full identityNumber
        common.click(common.findWebElementById("show-hide-button"));

        common.verifyStringById("nin-number", testData.getIdentityNumber());
    }

    private void verifyLabels() {
        //Swedish

        //Show/hide identityNumber
        common.verifyStringById("show-hide-button", "DÖLJ");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/h1", "Koppla din identitet till ditt eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/div/p", "För att kunna " +
                "använda eduID måste du bevisa din identitet. Lägg till ditt personnummer och bekräfta det i verkliga livet.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/h4", "Lägg till ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/div/label", "Personnummer");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/h4", "Bekräfta ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/p", "Välj ett sätt att bekräfta att " +
                "du har tillgång till det angivna personnumret. Om en av metoderna inte fungerar får du prova en annan.");

        //Button text - letter
        common.verifyStringOnPage("FÖR DIG SOM HAR EN SVENSK FOLKBOKFÖRINGSADRESS");

        common.verifyStringOnPage("VIA POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p", "Brevet innehåller " +
                "en bekräftelsekod som av säkerhetsskäl går ut efter två veckor.");

        //Button text - phone
        common.verifyStringOnPage("FÖR DIG SOM HAR ETT SVENSKT TELEFONABONNEMANG I DITT EGET NAMN");

        common.verifyStringOnPage("VIA TELEFON");

        //Button text - phone - Fine text
        common.verifyStringOnPage("Registret med " +
                "telefonnummer uppdateras av mobiloperatörerna och innehåller inte nödvändigtvis alla nummer.");

        //Button text - Freja
        common.verifyStringOnPage("FÖR DIG SOM HAR ELLER KAN SKAPA FREJA EID+ GENOM ATT BESÖKA ETT OMBUD I SVERIGE");

        common.verifyStringOnPage("MED DIGITALT ID-KORT");

        //Button text - Freja - Fine text
         common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p", "För att använda det " +
                 "här alternativet behöver du först skapa ett digitalt ID-kort i Freja eID+ appen.");

        //English
        //Click on english
        common.selectEnglish();

        //Verify identity
        verifyIdentity();

        //Show/hide identityNumber
        common.verifyStringById("show-hide-button", "HIDE");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/h1", "Connect your identity to your eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/div/p", "To be able to use " +
                "eduID you have to prove your identity. Add your national id number and verify it in real life.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/h4", "Add your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/div/label", "Id number");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/h4", "Verify your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/p", "Choose a method to verify " +
                "that you have access to the added id number. If you are unable to use a method you need to try another.");


        //Button text - letter
        common.verifyStringOnPage("FOR YOU OFFICIALLY REGISTERED AT AN ADDRESS IN SWEDEN");

        common.verifyStringOnPage("BY POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p", "The letter will contain " +
                "a code that for security reasons expires in two weeks.");

        //Button text - phone
        common.verifyStringOnPage("FOR YOU WITH A SWEDISH PHONE NUMBER REGISTERED IN YOUR NAME");

        common.verifyStringOnPage("BY PHONE");

        //Button text - phone - Fine text
        common.verifyStringOnPage("The phone number registry " +
                "is maintained by phone operators at their convenience and may not include all registered phone numbers.");

        //Button text - Freja
        common.verifyStringOnPage("FOR YOU ABLE TO CREATE A FREJA EID+ BY VISITING ONE OF THE AUTHORISED AGENTS");

        common.verifyStringOnPage("WITH A DIGITAL ID-CARD");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p", "To use this option " +
                "you first need to create a digital ID-card in the Freja eID+ app.");

        //Click on Freja
        common.click(common.findWebElementById("eidas-show-modal"));


        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");

        //Verify Pop-up labels
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
                "Use Freja eID+ and visit a local authorised agent");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Install the app");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Create a Freja eID+ account (awarded the \"Svensk e-legitimation\" quality mark)");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]",
                "The app will generate a QR-code");

        common.verifyStringOnPage("Find a local authorised agent, show them a valid ID together with the QR-code and " +
                        "they will be able to verify your identity");

        common.verifyStringOnPage("Tip: Use the app to find your nearest agent");

        common.verifyStringOnPage("Freja eID+ is now ready to be used with your eduID");

        //Press cancel
        common.click(common.findWebElementById("eidas-info-modal-close-button"));

        //Click on swedish
        common.selectSwedish();
    }

    private void verifyFrejaIdLabels(){
        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Swedish
        common.explicitWaitVisibilityElement("//*[@id=\"eidas-modal\"]/div/div[1]/h5");
        common.verifyStringOnPage("Med Freja eID appen kan du skapa ett digitalt ID-kort");
        common.verifyStringOnPage("Installera appen");
        common.verifyStringOnPage("Skapa ett Freja eID+ konto (godkänd svensk e-legitimation)");
        common.verifyStringOnPage("Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("TIPS: DU KAN HITTA NÄRMSTA OMBUD I APPEN");
        common.verifyStringOnPage("Freja eID+ är redo att användas med ditt eduID");
    }


    private void verifyPhoneLabels(){
        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage("Kolla om ditt telefonnummer är kopplat till ditt personnummer.");
        common.verifyStringOnPage("Du kan acceptera en sökning i telefonregistret för att se om ditt " +
                "telefonnummer finns lagrat tillsammans med ditt personnummer. Om dina uppgifter inte lagts till i " +
                "registret av din mobiloperatör kommer vi inte kunna bevisa din identitet via telefonnummer.");

        /*
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5/span");
        common.verifyStringOnPage("Lägg till ditt telefonnummer för att fortsätta");
        common.verifyStringOnPage("Lägg till ditt telefonnummer i Inställningar. Glöm inte att också " +
                "bekräfta att det är ditt! Du kan bara bevisa din identitet med ett bekräftat telefonnummer!");
                */
    }

    private void verifyMailLabels(){
        //Switch to pop up and verify its text
        common.switchToPopUpWindow();

        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/span", "Få en bekräftelsekod via post");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[2]/span", "Om du accepterar " +
                "att få ett brev hem måste du skriva in bekräftelsekoden här för att bevisa att personnumret är ditt. " +
                "Av säkerhetsskäl går koden ut om två veckor.");
    }
}