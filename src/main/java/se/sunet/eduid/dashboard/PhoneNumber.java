package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PhoneNumber {
    private Common common;

    public PhoneNumber(Common common){
        this.common = common;
    }

    public void runPhoneNumber2(){
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringByXpath("//*[@id=\"settings-content\"]/div[3]/div[1]/h4/span", "Telefonnummer");

        //Text
        common.verifyStringByXpath("//*[@id=\"settings-content\"]/div[3]/div[1]/p/span", "Du kan koppla ett " +
                "eller flera av dina mobiltelefonnummer till ditt eduID-konto och därefter välja vilket av dem som ska vara primär.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"add-more-button\"]/span", "+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

        //Heading
        common.verifyStringByXpath("//*[@id=\"settings-content\"]/div[3]/div[1]/h4/span", "Mobile phone numbers");

        //Text
        common.verifyStringByXpath("//*[@id=\"settings-content\"]/div[3]/div[1]/p/span", "You can connect " +
                "one or more mobile phone numbers to your eduID, but one has to be set as the primary one.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"add-more-button\"]/span", "+ add more");

        //Click on Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }
}