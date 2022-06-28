package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmedIdentity {
    private final Common common;
    private final TestData testData;

    public ConfirmedIdentity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmIdentity(){
        verifyPageTitle();
        verifyTextAndLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");
        common.timeoutMilliSeconds(500);
    }


    private void verifyTextAndLabels(){
        common.explicitWaitVisibilityElement("//*[@id=\"text-content\"]/div/h1");

        //Swedish labels
        textAndLabelsSwedish();

        //Change to English
        common.selectEnglish();
        common.timeoutMilliSeconds(500);

        //English labels
        textAndLabelsEnglish();
    }

    private void textAndLabelsSwedish(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/h1", "Koppla din identitet till ditt eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/div/p", "För att kunna " +
                "använda eduID måste du bevisa din identitet. Lägg till ditt personnummer och bekräfta det i verkliga livet.");

        //Heading 1
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/h4", "Ditt eduID är redo att användas");

        //Text 1
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/p", "Personnumret " +
                "nedan är nu kopplat till din eduID.");

        //Heading
        //common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/div/label", "Personnummer");

        //Show full id-number
        //common.findWebElementById("show-hide-button").click();

        //Data
//        common.verifyStringById("nin-number", testData.getIdentityNumber());

        //Heading 2
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/h4", "Förbättra din identifiering");

        //Text 2
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/p", "Lägg till ett " +
                "telefonnummer eller en säkerhetsnyckel för att behålla din identitet om du återställer ditt lösenord.");
    }

    private void textAndLabelsEnglish(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/h1", "Connect your identity to your eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div/div/p", "To be able to use eduID " +
                "you have to prove your identity. Add your national id number and verify it in real life.");

        //Heading 1
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/h4", "Your eduID is ready to use");

        //Text 1
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[1]/p", "The Swedish national " +
                "identity number below is now connected to your eduID.");

        //Heading
//        common.verifyStringOnPage("Id number");

        //Show full id-number
//        common.findWebElementById("show-hide-button").click();

        //Data
//        common.verifyStringById("nin-number", testData.getIdentityNumber());

        //Heading 2
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/h4", "Improve your identification");

        //Text 2
        common.verifyStringByXpath("//*[@id=\"text-content\"]/ol/li[2]/p", "Add a phone number " +
                "or a security key to your eduID to keep your identity at password reset.");
    }
}
