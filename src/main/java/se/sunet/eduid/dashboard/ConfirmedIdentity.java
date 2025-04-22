package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmedIdentity {
    private final Common common;
    private final TestData testData;
    private final Name name;
    private String pageBody;

    public ConfirmedIdentity(Common common, TestData testData, Name name) {
        this.common = common;
        this.testData = testData;
        this.name = name;
    }

    public void runConfirmedIdentity(){
        verifyPageTitle();
        verifyTextAndLabels();

        name.runName();
    }

    private void verifyPageTitle() {
        //Wait for heading to be loaded
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/h1");

        common.verifyPageTitle("Identitet | eduID");
        common.timeoutMilliSeconds(500);
    }

    private void verifyTextAndLabels(){
        //Wait for the remove identity button, use xpath since different id depending on method of identification
        common.explicitWaitClickableElement("//div/div/main/div/section/article[1]/figure/button");

        //Swedish labels
        textAndLabelsSwedish();

        //Change to English
        common.selectEnglish();

        //English labels
        textAndLabelsEnglish();

        //Change to Swedish
        common.selectSwedish();
    }

    private void textAndLabelsSwedish(){
        Common.log.info("Verify confirmed identity labels in Swedish");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Heading
        common.verifyPageBodyContainsString(pageBody,"Identitet");

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
            common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/figure/div[3]/div/div", testData.getIdentityNumber());
        }
        testData.setIdentityConfirmed(true);
    }

    private void textAndLabelsEnglish(){
        Common.log.info("Verify confirmed identity labels in English");

        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageTitle("Identity | eduID");

        //Heading
        common.verifyPageBodyContainsString(pageBody,"Identity");

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
            common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/figure/div[3]/div/div", testData.getIdentityNumber());
        }
    }
}
