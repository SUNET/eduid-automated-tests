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
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void pressIdentity(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/span").click();
    }

    private void verifyLabels() {
        //English

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
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));

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
    }
}
