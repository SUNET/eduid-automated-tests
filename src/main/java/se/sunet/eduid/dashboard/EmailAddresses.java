package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.MailReader;
import se.sunet.eduid.utils.TestData;

public class EmailAddresses {
    private final Common common;
    private final TestData testData;

    public EmailAddresses(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailAddresses(){
        verifyPageTitle();
        removeEmail();
        addNewEmail();
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");
    }

    private void removeEmail() {
        common.timeoutMilliSeconds(500);

        // Try to removePrimary the primary email
        if(testData.isRemovePrimary()) {
            common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[3]/button"));

            //Verify info bar message - swedish
            common.verifyStatusMessage("Du måste ha minst en e-postadress knuten till ditt konto");

            //Verify info bar message - english
            common.selectEnglish();
            common.verifyStatusMessage("You must have at least one email address");

            common.selectSwedish();
        }

        // RemoveNewEmail1 the primary email
        if(testData.isRemoveNewEmail1()) {
            common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[3]/button"));

            //Verify info bar message - swedish
            common.timeoutMilliSeconds(500);
            common.verifyStatusMessage("E-postadress borttagen");

            //Verify info bar message - english
            common.selectEnglish();
            common.verifyStatusMessage("Successfully removed email address");

            //Switch back to swedish
            common.selectSwedish();
        }
    }

    private void addNewEmail() {
        MailReader mailReader = new MailReader();

        // Try to remove the primary email
        if(!testData.getAddNewEmail1().equals("") && testData.getAddNewEmail1().contains("@")) {
            common.explicitWaitVisibilityElementId("add-more-button");
            common.click(common.findWebElementById("add-more-button"));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify Add button label - Swedish
            common.verifyStrings("LÄGG TILL", common.findWebElementById("email-button").getText());

            //Click Add button
            common.click(common.findWebElementById("email-button"));

            //Verify placeholder
            common.verifyStrings("namn@example.com", common.findWebElementByXpath("//*[@id=\"email\"]").getAttribute("placeholder"));

            if (testData.getAddNewEmail1().equals(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[1]").getText())) {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);

                common.verifyStatusMessage("Formulärfel. Se nedan för specifikt felmeddelande");

                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "E-postadressen är redan tagen. Var god använd en annan.");
                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "en giltig e-postadress");

                //Switch to English
                common.selectEnglish();

                //Verify info messages - english
                common.timeoutMilliSeconds(500);
                common.verifyStatusMessage("Check the form below for errors.");

                //Need to add the address again, since error message disappear when switch between language
                common.click(common.findWebElementByXpath("//*[@id=\"add-more-button\"]"));

                //Add new email address
                addEmailAddress(testData.getAddNewEmail1());

                //Verify Add button label - English
                common.verifyStrings("ADD", common.findWebElementById("email-button").getText());
                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "a valid email address");

                //Click Add button
                common.click(common.findWebElementById("email-button"));

                common.timeoutMilliSeconds(500);
                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "That email address is already in use, please choose another");

                //Switch back to Swedish
                common.selectSwedish();
            }
            else {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);

                common.verifyStatusMessage("E-postadressen sparad");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[1]", testData.getAddNewEmail1());
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button", "BEKRÄFTA");

                //Wait 5 sec for the email to arrive in inbox
                common.timeoutSeconds(5);

                // Confirm email address by code successfully, not successfully by wrongCode
                if(testData.getConfirmNewEmail1().equals("code") || testData.getConfirmNewEmail1().equals("wrongCode")) {
                    Common.log.info("Confirm email with code: " +testData.getConfirmNewEmail1());
                    String confirmationCode = null;

                    if(testData.getConfirmNewEmail1().equals("code")) {
                        //Get the confirmationCode
                        confirmationCode = mailReader.readEmail("confirmationCode");
                        Common.log.info("Confirmation Code: " + confirmationCode);
                    }
                    //Click on confirm link
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button"));

                    //Switch to the new pop-up window
                    common.switchToPopUpWindow();

                    // In pop-up, verify place holder
                    common.verifyStrings("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]").getAttribute("placeholder"));

                    // In pop-up enter the confirmation code
                    common.findWebElementById("emailConfirmDialogControl").clear();
                    if (testData.getConfirmNewEmail1().equals("code")) {
                        common.findWebElementById("emailConfirmDialogControl").sendKeys(confirmationCode);
                        Common.log.info("Confirmation email code: " +confirmationCode);
                    }
                    if (testData.getConfirmNewEmail1().equals("wrongCode"))
                        common.findWebElementById("emailConfirmDialogControl").sendKeys("18587024-e4e3-4fdd-a8fc-77544c1c8409");

                    //verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span",
                    //       "Ett meddelande har skickats till " +email1 +" med vidare instruktioner");


                    // Click OK
                    common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));

                    //Switch back to original window handle after submitting username, password
                    common.switchToDefaultWindow();
                } else if (testData.getConfirmNewEmail1().equals("url")) {
                    //Get the confirmationUrl
                    String confirmationUrl = mailReader.readEmail("confirmationUrl");
                    Common.log.info("Confirmation Url: " + confirmationUrl);
                    common.navigateToUrl(confirmationUrl);
                }

                //Check labels
                common.timeoutSeconds(1);
                if(testData.getConfirmNewEmail1().equals("wrongCode"))
                    common.verifyStatusMessage("Ogiltig bekräftelsekod. Var god prova igen eller begär en ny bekräftelsekod");
                else {
                    if(testData.getConfirmNewEmail1().equals("code"))
                        common.verifyStatusMessage("E-postadressen bekräftad");
                        //verifyUpdatedInfoBar("E-postadressen bekräftad");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/label", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button", "GÖR PRIMÄR");

                    //Make email 1 primary
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);
                    common.verifyStatusMessage("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button", "GÖR PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/label", "PRIMÄR");

                    //Switch back to default primary email
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);
                    common.verifyStatusMessage("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/label", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button", "GÖR PRIMÄR");
                }
            }
        }
        else if(!testData.getAddNewEmail1().contains("") && !testData.getAddNewEmail1().contains("@")){
            common.timeoutSeconds(500);
            common.click(common.findWebElementById("add-more-button"));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "Ogiltig e-postadress");
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "En giltig e-postadress");

            //Click on English
            common.selectEnglish();

            //Need to add the address again, since error message disappear when switch between language
            common.click(common.findWebElementByXpath("//*[@id=\"add-more-button\"]"));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed - English
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "The entered email is invalid");
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "A valid email address");

            //Switch back to Swedish
            common.selectSwedish();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("E-postadresser");

        //Text
        common.verifyStringOnPage("Du kan koppla en eller flera av dina e-postadresser till ditt " +
                "eduID-konto och därefter välja vilken av dem som ska vara primär.");

        //Add more addresses
        common.verifyStringOnPage("+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Email addresses");

        //Text
        common.verifyStringOnPage("You can " +
                "connect one or more email addresses with your eduID account and select one to be your primary email address.");

        //Add more addresses
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.selectSwedish();
    }

    private void addEmailAddress(String emailAddress){
        common.explicitWaitClickableElementId("email");

        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(emailAddress);

        Common.log.info("Adding email address: " +emailAddress);
    }
}