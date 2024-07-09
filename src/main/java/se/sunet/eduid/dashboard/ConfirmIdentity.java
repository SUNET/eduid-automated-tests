package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmIdentity{
    private final Common common;
    private final TestData testData;
    private final Identity identity;

    public ConfirmIdentity(Common common, TestData testData, Identity identity){
        this.common = common;
        this.testData = testData;
        this.identity = identity;
    }

    public void runConfirmIdentity(){
        common.navigateToIdentity();
        verifyPageTitle();
        identity.expandIdentityOptions();
        enterPersonalNumber();
        pressAddButton();
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
        common.explicitWaitClickableElementId("lookup-mobile-proofing-show-hide-button");
    }

    public void selectConfirmIdentity(){
        common.timeoutSeconds(1);

        //First need to collapse to default
        common.findWebElementById("accordion__heading-swedish").click();
        identity.expandIdentityOptions();

        //Select way to confirm the identity. By letter, By phone or Freja Id
        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            Common.log.info("Verify identity by Letter");

            //Click on proceed with letter, switch to pop-up
            common.explicitWaitClickableElement("//*[@id=\"accordion__panel-se-letter\"]/button");
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));
            common.switchToPopUpWindow();

            //Click on accept
            common.click(common.findWebElementById("letter-confirm-modal-accept-button"));

            //Verify labels when letter is sent
            verifyLabelsSentLetter();

            //Press again on the letter button - Add a faulty code
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input")
                    .sendKeys("1qvw3fw2q3");

            //Click OK
            common.findWebElementByXpath("//*[@id=\"letter-confirm-modal-form\"]/div[2]/button").click();

            //Verify response
            common.verifyStatusMessage("Den kod du angett stämmer inte. Var god försök igen");
            common.closeStatusMessage();

            //Fetch the code
            String letterProofingCode =
                    common.getCodeInNewTab("https://dashboard.dev.eduid.se/services/letter-proofing/get-code",
                            10);

            //Press again on the letter button - Add the correct code
            common.timeoutMilliSeconds(300);
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));

            //Wait for close button at pop up before enter the code
            common.explicitWaitClickableElement("//*[@id=\"confirm-user-data-modal\"]/div/div/h5/button");
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input")
                    .sendKeys(letterProofingCode);

            //Click OK
            common.findWebElementByXpath("//*[@id=\"letter-confirm-modal-form\"]/div[2]/button").click();

            common.timeoutMilliSeconds(800);
        }

        //By phone
        else if(testData.getConfirmIdBy().equalsIgnoreCase("phone")) {
            Common.log.info("Verify identity by phone");

            //Click on phone option
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button"));

            //Press accept button
            common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]"));

            common.timeoutMilliSeconds(800);
        }
        //Freja eID
        else if(testData.getConfirmIdBy().equalsIgnoreCase("freja")) {
            Common.log.info("Verify identity by Freja eID");

            //Select Freja eID
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

            //Click Use Freja eID in pop-up dialog
            common.findWebElementById("eidas-info-modal-accept-button").click();

            //Select and submit user at reference IDP
            selectAndSubmitUserRefIdp();

            //Add nin-cookie to get successful response from idp
            common.addNinCookie();

            //Expand Freja menu, since collapsed when change of language
            common.timeoutMilliSeconds(500);

            common.click(common.findWebElementById("accordion__heading-se-freja"));

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

            //Select eIDAS
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-eu\"]/button"));

            //Select country XA in sandbox
            //common.findWebElementById("countryFlag_XA").click();
            common.findWebElementByXpath("//*[@id=\"countrySelectForm\"]/div/div[3]/button").click();

            //Submit IDP identity
            common.findWebElementById("idpSubmitbutton").click();

            //Submit Consent
            common.findWebElementById("buttonNext").click();

            common.timeoutSeconds(2);
        }

        //SVIPE
        else if(testData.getConfirmIdBy().equalsIgnoreCase("svipe")) {
            Common.log.info("Verify identity by SVIPE ID");

            //Click proceed button
            common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-world\"]/button"));

            //Wait and see that we come to Sipe id page
            common.explicitWaitPageTitle("Svipe Login");
        }
    }

    private void verifyLabels() {
        identity.verifyLabelsSwedish();
        identity.verifyLabelsEnglish();
    }

    private void verifyLabelsSentLetter(){
        Common.log.info("Verify Letter sent labels");

        //Verify on the button that letter is sent text exists, with today's date
        common.timeoutMilliSeconds(500);
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[2]", "Ett brev skickades " +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[3]","Brevet är giltigt till " +common.getDate().plusDays(15));

        common.verifyStringOnPage("När du har mottagit brevet, fortsätt genom att klicka på knappen nedan");

        //English
        common.timeoutMilliSeconds(300);
        common.selectEnglish();

        //Expand Letter menu
        common.click(common.findWebElementById("accordion__heading-se-letter"));

        //Verify on the button that letter is sent text exists, with today's date
        common.timeoutMilliSeconds(200);
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[2]", "The letter was sent " +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p[3]","The letter is valid to " +common.getDate().plusDays(15));

        common.verifyStringOnPage("When you have received the letter, proceed by clicking the button below.");

        //Verify text in confirmation pop up
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));
        common.timeoutSeconds(1);
        common.switchToPopUpWindow();
        common.verifyStringOnPage("Add the code you have received by post");
        common.verifyStringOnPage("Code");
        common.verifyStrings("enter code",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input")
                        .getAttribute("placeholder"));
        //common.verifyStringOnPage("*Field cannot be empty");

        //Close
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //English
        common.timeoutMilliSeconds(300);
        common.selectSwedish();

        //Expand Letter menu
        common.click(common.findWebElementById("accordion__heading-se-letter"));
        common.timeoutMilliSeconds(200);

        //Verify text in confirmation pop up
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));
        common.timeoutSeconds(1);
        common.verifyStringOnPage("Skriv in koden du fått hemskickad");
        common.verifyStringOnPage("Kod");
        common.verifyStrings("skriv in koden",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input")
                        .getAttribute("placeholder"));
        //common.verifyStringOnPage("*Fältet kan inte vara tomt");

        //Close
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
    }


    public void selectAndSubmitUserRefIdp(){
        common.explicitWaitClickableElementId("selectSimulatedUser");
        common.selectDropdownScript("selectSimulatedUser", testData.getRefIdpUser());

        common.click(common.findWebElementById("submitButton"));
    }
}