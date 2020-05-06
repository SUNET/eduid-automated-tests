package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PhoneNumber {
    private Common common;

    public PhoneNumber(Common common){
        this.common = common;
    }

    public void runPhoneNumber(){
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("Telefonnummer");

        //Text
        common.verifyStringOnPage( "Du kan koppla ett " +
                "eller flera av dina mobiltelefonnummer till ditt eduID-konto och därefter välja vilket av dem som ska vara primär.");

        //Add more phone numbers
        common.verifyStringOnPage( "+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

        //Heading
        common.verifyStringOnPage("Mobile phone numbers");

        //Text
        common.verifyStringOnPage("You can connect " +
                "one or more mobile phone numbers to your eduID, but one has to be set as the primary one.");

        //Add more phone numbers
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }
}