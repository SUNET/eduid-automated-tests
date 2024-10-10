package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmedIdentity {
    private final Common common;
    private final TestData testData;
    private String pageBody;

    public ConfirmedIdentity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmIdentity(){
        verifyPageTitle();
        verifyTextAndLabels();
    }

    private void verifyPageTitle() {
        //Wait for heading to be loaded
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/h1");

        common.verifyPageTitle("Identitet | eduID");
        common.timeoutMilliSeconds(500);
    }

    private void verifyTextAndLabels(){
        //Wait for heading: Following identietes are now...
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/article/h2");

        //Swedish labels
        common.timeoutMilliSeconds(500);
        textAndLabelsSwedish();

        //Change to English
        common.selectEnglish();

        //English labels
        textAndLabelsEnglish();
    }

    private void textAndLabelsSwedish(){
        //Extract page body for validation
        pageBody = common.getPageBody();

        //Heading
        common.verifyPageBodyContainsString(pageBody,"Koppla din identitet till ditt eduID");

        //Heading 1
        common.verifyPageBodyContainsString(pageBody,"Ditt eduID är redo att användas");

        //Text 1
        common.verifyPageBodyContainsString(pageBody,"Följande identiteter är nu kopplade till ditt eduID");

        //Heading
        common.verifyPageBodyContainsString(pageBody,"Svenskt personnummer");

        //Heading -eIDAS
        if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            common.verifyPageBodyContainsString(pageBody,"Europeisk eIDAS-identitet");
            common.verifyPageBodyContainsString(pageBody,"XA 1939-11-13");
        }
        //Heading -non eIDAS
        else {
            common.verifyPageBodyContainsString(pageBody,"personnummer");

            //Show full id-number
            common.findWebElementById("undefined-show-hide-button").click();

            //Data
            common.verifyStringById("nin-number", testData.getIdentityNumber());
        }
        testData.setIdentityConfirmed(true);
    }

    private void textAndLabelsEnglish(){
        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageTitle("Identity | eduID");

        //Heading
        common.verifyPageBodyContainsString(pageBody,"Connect your identity to your eduID");

        //Heading 1
        common.verifyPageBodyContainsString(pageBody,"Your eduID is ready to use");

        //Text 1
        common.verifyPageBodyContainsString(pageBody,"The identities below are now connected to your eduID");

        //Heading -eIDAS
        if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            common.verifyPageBodyContainsString(pageBody,"European eIDAS identity");
            common.verifyPageBodyContainsString(pageBody,"XA 1939-11-13");
        }
        //Heading -non eIDAS
        else {
            common.verifyPageBodyContainsString(pageBody,"Swedish national ID number");

            //Heading
            //common.verifyStringOnPage("National ID number");

            //Show full id-number
            common.findWebElementById("undefined-show-hide-button").click();

            //Data
            common.verifyStringById("nin-number", testData.getIdentityNumber());
        }
    }
}
