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
        common.navigateToSettings();
        verifyPageTitle();
        removeEmail();
        addNewEmail();
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Inställningar | eduID");
    }

    private void removeEmail() {
        common.timeoutMilliSeconds(500);

        // Try to removePrimary the primary email
/*        if(testData.isRemovePrimary()) {
            common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr/td[3]/button"));

            //Verify info bar message - swedish
            common.verifyStatusMessage("Du måste ha minst en e-postadress knuten till ditt konto");

            //Verify info bar message - english
            common.selectEnglish();
            common.verifyStatusMessage("You must have at least one email address");

            common.selectSwedish();
        }*/

        // RemoveNewEmail1, in this case the second email address in the list
        if(testData.isRemoveNewEmail1()) {
            common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[3]/button"));
        }
    }

    private void addNewEmail() {
        MailReader mailReader = new MailReader();

        // Try to remove the primary email
        if(!testData.getAddNewEmail1().equals("") && testData.getAddNewEmail1().contains("@")) {
            //common.explicitWaitVisibilityElementId("emails-add-more-button");
            common.click(common.findWebElementById("emails-add-more-button"));

            //Verify placeholder
            common.verifyPlaceholder("namn@example.com", "email");

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify Abort button label - Swedish
            common.verifyStrings("AVBRYT", common.findWebElementById("cancel-adding-email").getText());

            //Verify Add button label - Swedish
            common.verifyStrings("LÄGG TILL", common.findWebElementById("add-email").getText());

            //Click Add button
            common.click(common.findWebElementById("add-email"));
            common.timeoutMilliSeconds(200);

            if (testData.getAddNewEmail1().equals(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[1]").getText())) {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);

                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "E-postadressen finns redan i listan.");

                //Switch to English
                common.timeoutMilliSeconds(200);
                common.selectEnglish();

                //Need to add the address again, since error message disappear when switch between language
                common.click(common.findWebElementById("emails-add-more-button"));

                //Add new email address
                addEmailAddress(testData.getAddNewEmail1());

                //Verify Cancel button label - English
                common.verifyStrings("CANCEL", common.findWebElementById("cancel-adding-email").getText());

                common.timeoutMilliSeconds(500);
                common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "The email is already in the list.");

                //Switch back to Swedish
                common.timeoutMilliSeconds(200);
                common.selectSwedish();
            }
            else {
                //Verify info messages - swedish
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[3]/td[1]", testData.getAddNewEmail1());
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[3]/td[2]/button", "BEKRÄFTA");

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
                    pressConfirmEmail();

                    verifyConfirmEmailPopupLabels();

                    // In pop-up enter the confirmation code
                    common.findWebElementById("email-confirm-modal").clear();
                    if (testData.getConfirmNewEmail1().equals("code")) {
                        common.findWebElementById("email-confirm-modal").sendKeys(confirmationCode);
                        Common.log.info("Confirmation email code: " +confirmationCode);
                    }
                    if (testData.getConfirmNewEmail1().equals("wrongCode"))
                        common.findWebElementById("email-confirm-modal").sendKeys("18587024-e4e3-4fdd-a8fc-77544c1c8409");

                    //verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span",
                    //       "Ett meddelande har skickats till " +email1 +" med vidare instruktioner");


                    // Click OK
                    common.click(common.findWebElementByXpath("//*[@id=\"email-confirm-modal-form\"]/div[2]/button"));

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
                    common.verifyStatusMessage("Ogiltig kod eller en kod som har gått ut. Var god prova igen eller begär en ny kod");
                else {
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");

                    //Make email 1 primary
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[2]/td[2]/button", "GÖR PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[2]/span", "PRIMÄR");

                    //Switch back to default primary email
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[2]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");
                }
            }
        }
        else if(!testData.getAddNewEmail1().contains("") && !testData.getAddNewEmail1().contains("@")){
            common.timeoutSeconds(500);
            common.click(common.findWebElementById("emails-add-more-button"));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "Ogiltig e-postadress");
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "En giltig e-postadress");

            //Click on English
            common.timeoutMilliSeconds(200);
            common.selectEnglish();

            //Need to add the address again, since error message disappear when switch between language
            common.click(common.findWebElementById("emails-add-more-button"));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed - English
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/small/span", "The entered email is invalid");
            common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "A valid email address");

            //Switch back to Swedish
            common.timeoutMilliSeconds(200);
            common.selectSwedish();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("E-postadresser");

        //Text
        common.verifyStringOnPage("Du kan koppla en eller flera e-postadresser till ditt eduID.");

        //Add more addresses
        common.verifyStringOnPage("+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Email addresses");

        //Text
        common.verifyStringOnPage("You can connect one or more email addresses to your eduID.");

        //Add more addresses
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.timeoutMilliSeconds(200);
        common.selectSwedish();
    }

    private void addEmailAddress(String emailAddress){
        common.explicitWaitClickableElementId("email");

        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(emailAddress);

        Common.log.info("Adding email address: " +emailAddress);
    }

    private void verifyConfirmEmailPopupLabels(){
        // In pop-up, verify labels and place holder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Klicka på länken eller skriv in koden som skickats till "
                        +testData.getAddNewEmail1() +" här");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Kod");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/span",
                "koden är formaterad som fem grupper med tecken och nummer, åtskilda av bindestreck");
        common.verifyPlaceholder("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", "email-confirm-modal");

        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/a",
                "Skicka ny kod igen");

        //Close confirmation pop-up
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        //Click on confirm link
        pressConfirmEmail();

        //Switch to the new pop-up window
        common.switchToPopUpWindow();

        // In pop-up, verify labels and place holder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Click the link or enter the code sent to "
                        +testData.getAddNewEmail1() +" here");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Code");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/span",
                "the code is formatted as five groups of characters and numbers, separated by hyphens");
        common.verifyPlaceholder("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", "email-confirm-modal");

        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/a",
                "Send a new code");

        //Close confirmation pop-up
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        common.timeoutMilliSeconds(200);
        common.selectSwedish();

        pressConfirmEmail();
    }

    private void pressConfirmEmail(){
        //Click on confirm link
        common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div/table/tbody/tr[3]/td[2]/button"));
    }
}