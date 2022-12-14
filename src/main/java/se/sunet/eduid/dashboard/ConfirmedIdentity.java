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
        common.verifyPageTitle("Identitet | eduID");
        common.timeoutMilliSeconds(500);
    }


    private void verifyTextAndLabels(){
        common.explicitWaitVisibilityElement("//*[@id=\"text-content\"]/div/h1");

        //Swedish labels
        common.timeoutMilliSeconds(500);
        textAndLabelsSwedish();

        //Change to English
        common.selectEnglish();

        //English labels
        textAndLabelsEnglish();
    }

    private void textAndLabelsSwedish(){
        //Heading
        common.verifyStringOnPage("Koppla din identitet till ditt eduID");

        //Heading 1
        common.verifyStringOnPage("Ditt eduID är redo att användas");

        //Text 1
        common.verifyStringOnPage("Följande identiteter är nu kopplade till ditt eduID");

        //Heading
        common.verifyStringOnPage("Svenskt personnummer");

        //Heading -eIDAS
        if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            common.verifyStringOnPage("Europeisk eIDAS-identitet");
            common.verifyStringOnPage("XA 1939-11-13");
        }
        //Heading -non eIDAS
        else {
            common.verifyStringOnPage("Personnummer");

            //Show full id-number
            common.findWebElementById("show-hide-button").click();

            //Data
            common.verifyStringById("nin-number", testData.getIdentityNumber());
        }
        testData.setIdentityConfirmed(true);
    }

    private void textAndLabelsEnglish(){
        common.verifyPageTitle("Identity | eduID");

        //Heading
        common.verifyStringOnPage("Connect your identity to your eduID");

        //Heading 1
        common.verifyStringOnPage("Your eduID is ready to use");

        //Text 1
        common.verifyStringOnPage("The identities below are now connected to your eduID");

        //Heading -eIDAS
        if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            common.verifyStringOnPage("European eIDAS identity");
            common.verifyStringOnPage("XA 1939-11-13");
        }
        //Heading -non eIDAS
        else {
            common.verifyStringOnPage("Swedish national identity number");

            //Heading
            common.verifyStringOnPage("Id number");

            //Show full id-number
            common.findWebElementById("show-hide-button").click();

            //Data
            common.verifyStringById("nin-number", testData.getIdentityNumber());
        }
    }
}
