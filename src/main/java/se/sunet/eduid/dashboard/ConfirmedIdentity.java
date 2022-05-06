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

        //TODO temp fix to get swedish language
//        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
//            common.selectSwedish();
        common.timeoutMilliSeconds(500);
    }


    private void verifyTextAndLabels(){
        common.explicitWaitVisibilityElement("//*[@id=\"text-content\"]/h3");

        //Swedish labels
        textAndLabelsSwedish();

        //Change to English
        common.selectEnglish();

        //English labels
        textAndLabelsEnglish();
    }

    private void textAndLabelsSwedish(){
        common.timeoutSeconds(2);

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h3", "Ditt eduID är redo att användas");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p[1]", "Personnumret " +
                "nedan är nu kopplat till din eduID.");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/label", "Personnummer");

        //Show full id-number
        common.findWebElementById("show-hide-button").click();

        //Data
        common.verifyStringById("nin-number", testData.getIdentityNumber());

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p[2]", "Lägg till ett " +
                "telefonnummer eller en säkerhetsnyckel för att behålla din identitet om du återställer ditt lösenord.");
    }

    private void textAndLabelsEnglish(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h3", "Your eduID is ready to use");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p[1]", "The Swedish national " +
                "identity number below is now connected to your eduID.");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/label", "Id number");

        //Show full id-number
        common.findWebElementById("show-hide-button").click();

        //Data
        common.verifyStringById("nin-number", testData.getIdentityNumber());

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p[2]", "Add a phone number " +
                "or a security key to your eduID to keep your identity at password reset.");
    }
}
