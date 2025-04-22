package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmIdentity{
    private final Common common;
    private final TestData testData;
    private final Identity identity;
    String letterCodeConfirmationInputFieldId = "letter-confirm-modal";
    String letterProceedButton = "//*[@id=\"accordion__panel-se-letter\"]/button";
    String letterSubmitCodeOkButton = "//*[@id=\"letter-confirm-modal-form\"]/div[2]/button";

    public ConfirmIdentity(Common common, TestData testData, Identity identity){
        this.common = common;
        this.testData = testData;
        this.identity = identity;
    }

    public void runConfirmIdentity(){
        common.navigateToIdentity();
        verifyPageTitle();

        //Check and add personal id number when confirming by mail
        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")){
            identity.expandIdentityOptions();

            enterPersonalNumber();
            pressAddButton();
        }
        verifyLabels();
        selectConfirmIdentity();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Identitet | eduID");
    }

    private void enterPersonalNumber(){
        //Verify placeholder
        common.verifyPlaceholder("ååååmmddnnnn", "nin");

        common.findWebElementById("nin").sendKeys(testData.getIdentityNumber());
    }

    private void pressAddButton(){
        common.click(common.findWebElementById("add-nin-button"));

        //Wait for the nin to be added and show/hide button is visible
        common.explicitWaitClickableElementId("letter-proofing-show-hide-button");

        Common.log.info("Added swedish personal id number");
    }

    public void selectConfirmIdentity(){
        common.timeoutSeconds(1);

        //Select way to confirm the identity. By letter or Freja Id
        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            Common.log.info("Verify identity by Letter");

            //Fetch the code
            String letterProofingCode =
                    common.getCodeInNewTab(testData.getLetterProofingCodeUrl(), 10);

            //Verify labels when letter is sent
            verifyLabelsSentLetter();

            //Press again on the letter button - Add a faulty code. Not needed for all test cases
            if(testData.getTestCase().equalsIgnoreCase("TC_41")) {
                common.findWebElementByXpath(letterCodeConfirmationInputFieldId).sendKeys("1qvw3fw2q3");

                //Click OK
                common.findWebElementByXpath(letterSubmitCodeOkButton).click();

                //Verify response
                //common.verifyStatusMessage("Den kod du angett stämmer inte. Var god försök igen");
                common.verifyStatusMessage("Incorrect code");
                common.closeStatusMessage();

                //Press again on the letter proceed button to open send letter pop-up
                common.click(common.findWebElementByXpath(letterProceedButton));
            }

            //Wait for close button at pop up before enter the code
            common.findWebElementById(letterCodeConfirmationInputFieldId).sendKeys(letterProofingCode);

            //Click OK
            common.findWebElementByXpath(letterSubmitCodeOkButton).click();

            common.timeoutMilliSeconds(3800);

            Common.log.info("Verify identity by Letter - Done");

            //Select Swedish
            common.selectSwedish();
        }

        //Freja eID
        else if(testData.getConfirmIdBy().equalsIgnoreCase("freja")) {
            Common.log.info("Verify identity by Freja eID");

            //Verify that nin cookie has to be used is not necessary for all test cases, check it only in tc 40
            if(testData.getTestCase().equalsIgnoreCase("TC_40")) {
                //Select Freja eID
                common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

                //Verify labels and text in pop-up
                identity.verifyFrejaIdLabelsSwedish();
                common.selectEnglish();

                identity.expandIdentityOptions();
                identity.verifyFrejaIdLabelsEnglish();

                //Click Use Freja eID in pop-up dialog
                common.findWebElementById("eidas-info-modal-accept-button").click();

                //Select and submit user at reference IDP
                selectAndSubmitUserRefIdp();

                //Nin cookie required
                common.verifyStatusMessage("Felaktigt format av identitetsnumret. Var god försök igen.");

                //Add nin-cookie to get successful response from idp
                common.addNinCookie();

                //Expand Freja menu, since collapsed when change of language
                common.timeoutMilliSeconds(800);

                identity.expandIdentityOptions();
            }
            else {
                //Add nin-cookie to get successful response from idp
                common.addNinCookie();
            }

            //Select Freja eID
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

            //Click Use Freja eID in pop-up dialog
            common.findWebElementById("eidas-info-modal-accept-button").click();

            //Select and submit user at reference IDP
            selectAndSubmitUserRefIdp();
        }

        //eIDAS
        else if(testData.getConfirmIdBy().equalsIgnoreCase("eidas")) {
            Common.log.info("Verify identity by eIDAS");

            identity.expandIdentityOptions();

            //Select eIDAS
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-eu\"]/button"));

            //Select country XA in sandbox
            common.findWebElementByXpath("//*[@id=\"countrySelectForm\"]/div/div[3]/button").click();

            //Set LoA to substantial
            common.click(common.findWebElementByXpath("//*[@id=\"authnForm\"]/table/tbody/tr[3]/td/div/div/button"));
            common.click(common.findWebElementByXpath(
                    "//*[@id=\"authnForm\"]//span[contains(text(),'" +testData.getLoaLevel() +"')]"));

            //Submit IDP identity
            common.findWebElementById("idpSubmitbutton").click();

            //Submit Consent
            common.findWebElementById("buttonNext").click();

            common.timeoutSeconds(2);
        }

        //Freja no Swedish Pnr
        else if(testData.getConfirmIdBy().equalsIgnoreCase("frejaNoSwedishPnr")) {
            Common.log.info("Verify identity by Freja with no Swedish personal identity number");

            //Click proceed button
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-world\"]/button"));

            //Wait and see that we come to Freja eID OpenID Connect - Logga in - page
            common.explicitWaitPageTitle("Freja eID OpenID Connect - Logga in");
            common.verifyStringOnPage("För att gå vidare skanna QR-koden");
        }

        //BankID
        else if(testData.getConfirmIdBy().equalsIgnoreCase("bankid")) {
            Common.log.info("Verify identity by BankID");

            identity.expandIdentityOptions();

            //Click proceed with bankid button
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-bankID\"]/button"));

            //Wait and press BankId on other device
            common.explicitWaitClickableElement("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]");
            common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/div[1]/button[2]").click();

            //Wait and press abort scanning the qr code
            common.explicitWaitClickableElement("//*[@id=\"app\"]/main/div[1]/dialog/button");
            common.findWebElementByXpath("//*[@id=\"app\"]/main/div[1]/dialog/button").click();

            //Cancel the authention
            common.findWebElementByXpath("//*[@id=\"app\"]/main/div[2]/button/span").click();
        }
    }

    private void verifyLabels() {
        identity.verifyLabelsSwedish();
        identity.verifyLabelsEnglish();
    }

    private void verifyLabelsSentLetter(){
        Common.log.info("Verify Letter sent labels - Swedish");

        //Verify on the button that letter is sent text exists, with today's date
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[2]",
                "Ett brev skickades " +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[3]",
                "Brevet är giltigt till " +common.getDate().plusDays(15));

        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[4]",
                "När du har mottagit brevet, fortsätt genom att klicka på knappen nedan.");

        //Verify Proceed button text
        common.verifyStringByXpath(letterProceedButton, "FORTSÄTT");

        //Verify text in confirmation pop up
        Common.log.info("Open pop-up to confirm with code received in letter - Swedish");
        common.click(common.findWebElementByXpath(letterProceedButton));

        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/dialog/div/div/div/div/h5",
                "Skriv in koden du fått hemskickad");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal-wrapper\"]/div/label", "Kod");

        common.verifyPlaceholder("skriv in koden", letterCodeConfirmationInputFieldId);

        //Verify OK button text
        common.verifyStringByXpath(letterSubmitCodeOkButton, "OK");

        //Close
        common.click(common.findWebElementByXpath(
                "//*[@id=\"accordion__panel-se-letter\"]/dialog/div/div/div/div/button"));
        Common.log.info("Closed pop-up to confirm with code received in letter - Swedish");

        //Select english
        common.selectEnglish();

        Common.log.info("Verify Letter sent labels - English");

        //Expand letter validation menu
        common.click(common.findWebElementById("accordion__heading-se-letter"));

        //Verify on the button that letter is sent text exists, with today's date
        common.timeoutMilliSeconds(200);
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[2]",
                "The letter was sent " +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[3]",
                "The letter is valid to " +common.getDate().plusDays(15));

        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[4]",
                "When you have received the letter, proceed by clicking the button below.");

        //Verify Proceed button text
        common.verifyStringByXpath(letterProceedButton, "PROCEED");

        //Verify text in confirmation pop up
        Common.log.info("Open pop-up to confirm with code received in letter - English");
        common.click(common.findWebElementByXpath(letterProceedButton));

        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/dialog/div/div/div/div/h5",
                "Add the code you have received by post");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal-wrapper\"]/div/label", "Code");
        common.verifyPlaceholder("enter code", letterCodeConfirmationInputFieldId);

        //Verify OK button text
        common.verifyStringByXpath(letterSubmitCodeOkButton, "OK");
    }


    public void selectAndSubmitUserRefIdp(){
        common.explicitWaitClickableElementId("selectSimulatedUser");

        //Click advanced options
        common.findWebElementById("advancedButton").click();

        //Enter First name, family name and id number
        common.findWebElementById("personalIdNumber").sendKeys(testData.getIdentityNumber());
        common.findWebElementById("givenName").sendKeys(testData.getGivenName());
        common.findWebElementById("surname").sendKeys(testData.getSurName());

        Common.log.info("At ref IDP, submit: "+testData.getIdentityNumber() );
        Common.log.info("At ref IDP, submit: "+testData.getGivenName() );
        Common.log.info("At ref IDP, submit: "+testData.getSurName() );


        //Submit
        common.findWebElementById("submitButton").click();

        //Redirect back to eduID takes time, adding some timeout
        common.timeoutSeconds(18);
    }
}