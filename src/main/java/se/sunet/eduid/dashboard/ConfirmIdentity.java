package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class ConfirmIdentity{
    private Common common;

    public ConfirmIdentity(Common common){
        this.common = common;
    }

    public void runConfirmIdentity(){
        verifyPageTitle();
        pressIdentity();
        verifyLabels();
        enterPersonalNumber();
        pressAddButton();
        selectConfirmIdentity();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");

        //TODO temp fix to get swedish language
//        if(common.findWebElementByXpath("/html").getText().contains("Svenska"))
//            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void pressIdentity(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/span").click();
    }

    private void enterPersonalNumber(){
        common.findWebElementByXpath("//*[@id=\"nin\"]/input").sendKeys(common.getPersonalNumber());
    }

    private void pressAddButton(){
        common.findWebElementByXpath("//*[@id=\"nin-form\"]/button/span").click();
    }

    private void selectConfirmIdentity(){
        //Select way to confirm the identity. By mail, By phone or Freja Id
        if(common.getConfirmIdBy().equalsIgnoreCase("mail"))
            common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button").click();

        //TODO at the moment "nothing" visual happens when clicking on mail. But that will be improved later on.

        //By phone
        else if(common.getConfirmIdBy().equalsIgnoreCase("phone")) {
            common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button").click();
            //Switch to pop up
            common.switchToPopUpWindow();

            //Verify labels in pop up
            //TODO metod verifyPhoneLables has different message when the phone number NOT has been confirmed
            verifyPhoneLabels();

            //Press accept button
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span").click();

            //Verify status label
            common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
            common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Personnumret är bekräftat");

            //Verify text
            common.verifyStringOnPage("Ditt eduID är redo att användas");
            common.verifyStringOnPage("För att kunna använda eduID måste du bevisa din identitet. Lägg " +
                    "till ditt personnumer och bekräfta det i verkliga livet.");

        }
        //Freja ID
        else {
            //Select Freja ID
            common.addMagicCookie();
            common.findWebElementByXpath("//*[@id=\"eidas-show-modal\"]").click();

            //Switch to the Freja Id pop up
            common.switchToPopUpWindow();

            //Verify the labels
            verifyFrejaIdLabels();

            //Press use Freja ID
            common.findWebElementByXpath("//*[@id=\"freja-links\"]/a[1]/span").click();
        }
    }

    private void verifyLabels() {
        //Swedish

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Koppla din identitet till ditt eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "För att kunna använda eduID " +
                "måste du bevisa din identitet. Lägg till ditt personnumer och bekräfta det i verkliga livet.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3/span", "1. Lägg till ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"nin\"]/label/span", "PERSONNUMMER");
        common.verifyStringByXpath("//*[@id=\"nin\"]/small/span", "Personnummer med 12 siffror");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/h3/span", "2. Bekräfta ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/p/span", "Välj ett sätt att bekräfta att " +
                "du har tillgång till det angivna personnumret. Om en av metoderna inte fungerar får du prova en annan.");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p/span", "Brevet innehåller en " +
                "bekräftelsekod som av säkehetsskäl går ut efter två veckor.");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p/span", "Registret med telefonnummer " +
                "uppdateras av mobiloperatörerna och har inget krav på att innehålla alla nummer.");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p/span", "För att använda " +
                "det här alternativet så måste du först skaffa ett digitalt ID-kort i Freja eID appen.");


        //English

        //Click on english
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Connect your identity to your eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "To be able to use " +
                "eduID you have to prove your identity. Add your national id number and verify it in real life.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3/span", "1. Add your id number");
        common.verifyStringByXpath("//*[@id=\"nin\"]/label/span", "ID NUMBER");
        common.verifyStringByXpath("//*[@id=\"nin\"]/small/span", "National identity number with 12 digits");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/h3/span", "2. Verify your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/p/span", "Choose a method to verify " +
                "that you have access to the added id number. If you are unable to use a method you need to try another.");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p/span", "The letter will contain " +
                "a code that for security reasons expires in two weeks.");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p/span", "The phone number registry " +
                "is maintained by phone operators at their convenience and may not include all registered phone numbers.");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p/span", "To use this option you " +
                "will need to first create a digital ID-card in the Freja eID app.");


        //Click on swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyFrejaIdLabels(){
        //TODO freja labels in english

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
}
