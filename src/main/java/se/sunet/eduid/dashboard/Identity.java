package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class Identity {
    private final Common common;

    public Identity(Common common){
        this.common = common;
    }

    public void runIdentity(){
        pressIdentity();
        verifyPageTitle();
        verifyIdentity();

        if(common.getTestSuite().equalsIgnoreCase("prod"))
            common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Ditt eduID är redo att användas");
        else
            verifyLabels();
    }

    private void pressIdentity(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/span").click();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
            common.findWebElementByLinkText("Svenska").click();
    }

    private void verifyIdentity(){
        //Click on show/hide full identityNumber
        common.findWebElementByXpath("//*[@id=\"text-content\"]/div[2]/div/button[1]/span").click();
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/div/p", common.getIdentityNumber());
    }

    private void verifyLabels() {
        //Swedish

        //Show/hide identityNumber
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/div/button[1]/span", "DÖLJ");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Koppla din identitet till ditt eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "För att kunna " +
                "använda eduID måste du bevisa din identitet. Lägg till ditt personnummer och bekräfta det i verkliga livet.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3/span", "1. Lägg till ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/label/span", "Personnummer");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[3]/h3/span", "2. Bekräfta ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[3]/p/span", "Välj ett sätt att bekräfta att " +
                "du har tillgång till det angivna personnumret. Om en av metoderna inte fungerar får du prova en annan.");

        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/span",
                "FÖR DIG SOM HAR TILLGÅNG TILL DIN FOLKBOKFÖRINGSADDRESS");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[2]/span", "VIA POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p/span", "Brevet innehåller " +
                "en bekräftelsekod som av säkerhetsskäl går ut efter två veckor.");

        //Button text - phone
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[1]/span",
                "FÖR DIG SOM HAR ETT SVENSKT TELEFONABONNEMANG I DITT EGET NAMN");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[2]/span",
                "VIA TELEFON");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p/span", "Registret med " +
                "telefonnummer uppdateras av mobiloperatörerna och innehåller inte nödvändigtvis alla nummer.");

        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[1]/span",
                "FÖR DIG SOM HAR ELLER KAN SKAPA FREJA EID GENOM ATT BESÖKA ETT OMBUD I SVERIGE");

        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[2]/span", "MED DIGITALT ID-KORT");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p/span", "För att använda " +
                "det här alternativet måste du först skaffa ett digitalt ID-kort i Freja eID appen.");

        //English

        //Click on english
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Show/hide identityNumber
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/div/button[1]/span", "HIDE");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Connect your identity to your eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "To be able to use " +
                "eduID you have to prove your identity. Add your national id number and verify it in real life.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3/span", "1. Add your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/label/span", "Id number");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[3]/h3/span", "2. Verify your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[3]/p/span", "Choose a method to verify " +
                "that you have access to the added id number. If you are unable to use a method you need to try another.");


        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/span",
                "FOR YOU REGISTERED AT YOUR CURRENT ADDRESS");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[2]/span", "BY POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p/span", "The letter will contain " +
                "a code that for security reasons expires in two weeks.");

        //Button text - phone
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[1]/span",
                "FOR YOU WITH A SWEDISH PHONE NUMBER REGISTERED IN YOUR NAME");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[2]/span",
                "BY PHONE");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p/span", "The phone number registry " +
                "is maintained by phone operators at their convenience and may not include all registered phone numbers.");

        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[1]/span",
                "FOR YOU ABLE TO CREATE A FREJA EID BY VISITING ONE OF THE AUTHORISED AGENTS");

        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[2]/span", "WITH A DIGITAL ID-CARD");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p/span", "To use this option you " +
                "will need to first create a digital ID-card in the Freja eID app.");

        //Click on Freja
        common.findWebElementById("eidas-show-modal").click();


        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5/span");

        //Verify Pop-up labels
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/span",
                "Use Freja eID+ and pass a local authorised agent");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]/span", "Install the app");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]/span",
                "Create a Freja eID Plus account (awarded the ‘Svensk e-legitimation’ quality mark)");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]/span", "The app will generate a QR-code");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[4]/span",
                "Find a local authorised agent, show them a valid ID together with the QR-code and " +
                        "they will be able to verify your identity");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/label/span",
                "Tip: Use the app to find your nearest agent");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[5]/span",
                "Freja eID is now ready to be used with your eduID");

        //Press cancel
        common.findWebElementById("eidas-hide-modal").click();
        common.timeoutMilliSeconds(500);

        //Click on swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyFrejaIdLabels(){
        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Swedish
        common.explicitWaitVisibilityElement("//*[@id=\"eidas-modal\"]/div/div[1]/h5/span");
        common.verifyStringOnPage("Med Freja eID appen kan du skapa ett digitalt ID-kort");
        common.verifyStringOnPage("Installera appen");
        common.verifyStringOnPage("Skapa ett Freja eID Plus konto (godkänd svensk e-legitimation)");
        common.verifyStringOnPage("Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("TIPS: DU KAN HITTA NÄRMSTA OMBUD I APPEN");
        common.verifyStringOnPage("Freja eID är redo att användas med ditt eduID");
    }


    private void verifyPhoneLabels(){
        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5/span");
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

        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5/span");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/span", "Få en bekräftelsekod via post");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[2]/span", "Om du accepterar " +
                "att få ett brev hem måste du skriva in bekräftelsekoden här för att bevisa att personnumret är ditt. " +
                "Av säkerhetsskäl går koden ut om två veckor.");
    }
}