package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class ConfirmedIdentity {
    private Common common;

    public ConfirmedIdentity(Common common){
        this.common = common;
    }

    public void runConfirmIdentity(){
        verifyPageTitle();
        verifyTextAndLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
            common.findWebElementByLinkText("Svenska").click();
        common.timeoutMilliSeconds(500);
    }


    private void verifyTextAndLabels(){
        common.explicitWaitVisibilityElement("//*[@id=\"text-content\"]/div[1]/h4/span");

        //Swedish labels
        textAndLabelsSwedish();

        //Change to English
        common.findWebElementByLinkText("English").click();

        //English labels
        textAndLabelsEnglish();
    }

    private void textAndLabelsSwedish(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID för\n" +common.getUsername().toLowerCase());

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Ditt eduID är redo att användas");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "Personnumret " +
                "nedan är nu kopplat till detta eduID. Använd ditt eduID för att logga in till olika tjänster inom högskolan.");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/label/span", "Personnummer");

        //Show full id-number
        common.findWebElementByXpath("//*[@id=\"text-content\"]/div[2]/div/button/span").click();

        //Data
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/div/p", common.getIdentityNumber());

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p/span", "Lägg till ett " +
                "telefonnummer eller en säkerhetsnyckel för att behålla din identitet om du återställer ditt lösenord.");
    }

    private void textAndLabelsEnglish(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID for\n" +common.getUsername().toLowerCase());

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4/span", "Your eduID is ready to use");

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p/span", "The below id number " +
                "is now connected to this eduID. Use your eduID to log in to services related to higher education.");

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/label/span", "Id number");

        //Data
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[2]/div/p", common.getIdentityNumber());

        //Text
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p/span", "Add a phone number " +
                "or a security key to your eduID to keep your identity at password reset.");
    }
}
