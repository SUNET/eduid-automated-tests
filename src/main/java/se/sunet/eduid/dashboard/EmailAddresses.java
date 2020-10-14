package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.MailReader;

public class EmailAddresses {
    private Common common;

    public EmailAddresses(Common common){
        this.common = common;
    }

    public void runEmailAddresses(){
        verifyPageTitle();
        removeEmail();
        addNewEmail();
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void removeEmail() {

        // Try to removePrimary the primary email
        if(common.getRemovePrimary()) {
            common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[3]").click();

            //Verify info bar message - swedish
            verifyUpdatedInfoBar("Du måste ha minst en e-postadress knuten till ditt konto");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

            //Verify info bar message - english
            verifyUpdatedInfoBar("You must have at least one email address");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
        }

        // RemoveNewEmail1 the primary email
        if(common.getRemoveNewEmail1()) {
            common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[3]").click();

            //Verify info bar message - swedish
            common.timeoutMilliSeconds(500);
            verifyUpdatedInfoBar("E-postadress borttagen");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

            //Verify info bar message - english
            verifyUpdatedInfoBar("Successfully removed email address");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
        }
    }

    private void addNewEmail() {
        MailReader mailReader = new MailReader();

        // Try to remove the primary email
        if(!common.getAddNewEmail1().equals("") && common.getAddNewEmail1().contains("@")) {
            common.explicitWaitVisibilityElementId("add-more-button");
            common.findWebElementById("add-more-button").click();

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getAddNewEmail1());

            //Verify Add button label - Swedish
            common.verifyStrings("LÄGG TILL", common.findWebElementById("email-button").getText());

            //Click Add button
            common.findWebElementById("email-button").click();

            if (common.getAddNewEmail1().equals(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[1]").getText())) {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("Formulärfel. Se nedan för specifikt felmeddelande");
                common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "E-postadressen är redan tagen. Var god använd en annan.");
                common.verifyStringByXpath("//*[@id=\"email\"]/div/span/span", "en giltig e-postadress");

                //Switch to English
                common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

                //Verify info messages - english
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("Check the form below for errors.");

                //Need to add the address again, since error message disappear when switch between language
                common.findWebElementByXpath("//*[@id=\"add-more-button\"]/span").click();

                //Add new email address
                common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getAddNewEmail1());

                //Verify Add button label - English
                common.verifyStrings("ADD", common.findWebElementById("email-button").getText());
                common.verifyStringByXpath("//*[@id=\"email\"]/div/span/span", "a valid email address");

                //Click Add button
                common.findWebElementById("email-button").click();

                common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "That email address is already in use, please choose another");

                //Switch back to Swedish
                common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
            }
            else {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("E-postadressen sparad");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[1]", common.getAddNewEmail1());
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "BEKRÄFTA");

                //Switch to English
                common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

                //Verify info messages - english
                verifyUpdatedInfoBar("The email address was saved");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[1]", common.getAddNewEmail1());
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "CONFIRM");

                //Switch to Swedish
                common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();

                //Wait 5 sec for the email to arrive in inbox
                common.timeoutSeconds(5);

                // Confirm email address by code successfully, not successfully by wrongCode
                if(common.getConfirmNewEmail1().equals("code") || common.getConfirmNewEmail1().equals("wrongCode")) {
                    Common.log.info("Confirm email with code: " +common.getConfirmNewEmail1());
                    String confirmationCode = null;

                    if(common.getConfirmNewEmail1().equals("code")) {
                        //Get the confirmationCode
                        confirmationCode = mailReader.readEmail("confirmationCode");
                        Common.log.info("Confirmation Code: " + confirmationCode);
                    }
                    common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span").click();

                    //Switch to the new pop-up window
                    common.switchToPopUpWindow();

                    // In pop-up enter the confirmation code
                    common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").clear();
                    if (common.getConfirmNewEmail1().equals("code"))
                        common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").sendKeys(confirmationCode);
                    if (common.getConfirmNewEmail1().equals("wrongCode"))
                        common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").sendKeys("18587024-e4e3-4fdd-a8fc-77544c1c8409");

                    //verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span",
                    //       "Ett meddelande har skickats till " +email1 +" med vidare instruktioner");


                    // Click OK
                    common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]/span").click();

                    //Switch back to original window handle after submitting username, password
                    common.switchToDefaultWindow();
                } else if (common.getConfirmNewEmail1().equals("url")) {
                    //Get the confirmationCode
                    String confirmationCode = mailReader.readEmail("confirmationUrl");
                    //log.info("Confirmation Url: " + confirmationCode);
                    common.navigateToUrl(confirmationCode);
                }

                //Check labels
                common.timeoutSeconds(1);
                if(common.getConfirmNewEmail1().equals("wrongCode"))
                    verifyUpdatedInfoBar("Ogiltig bekräftelsekod. Var god prova igen eller begär en ny bekräftelsekod");
                else {
                    if(common.getConfirmNewEmail1().equals("code"))
                        verifyUpdatedInfoBar("E-postadressen bekräftad");//*[@id="email-display"]/div[1]/table/tbody/tr[1]/td[2]/label/span
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/label/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "GÖR PRIMÄR");

                    //Make email 1 primary
                    common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span").click();

                    //Verify info label
                    common.timeoutMilliSeconds(500);
                    verifyUpdatedInfoBar("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button/span", "GÖR PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/label/span", "PRIMÄR");

                    //Switch back to default primary email
                    common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button/span").click();

                    //Verify info label
                    common.timeoutMilliSeconds(500);
                    verifyUpdatedInfoBar("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/label/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "GÖR PRIMÄR");
                }
            }
        }
        else if(!common.getAddNewEmail1().contains("") && !common.getAddNewEmail1().contains("@")){
            common.timeoutSeconds(500);
            common.findWebElementById("add-more-button").click();

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getAddNewEmail1());

            //Verify that correct message is displayed
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "Ogiltig e-postadress");
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[2]", "En giltig e-postadress");

            //Click on English
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

            //Need to add the address again, since error message disappear when switch between language
            common.explicitWaitClickableElement("//*[@id=\"add-more-button\"]/span");
            common.findWebElementByXpath("//*[@id=\"add-more-button\"]/span").click();

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getAddNewEmail1());

            //Verify that correct message is displayed - English
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "The entered value does not look like an email");
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span", "A valid email address");

            //Switch back to Swedish
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("E-postadresser");

        //Text
        common.verifyStringOnPage("Du kan " +
                "koppla en eller flera av dina e-postadresser till ditt eduID-konto och därefter välja vilken av dem som " +
                "ska vara primär.");

        //Add more addresses
        common.verifyStringOnPage("+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Heading
        common.verifyStringOnPage("Email addresses");

        //Text
        common.verifyStringOnPage("You can " +
                "connect one or more email addresses with your eduID account and select one to be your primary email address.");

        //Add more addresses
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyUpdatedInfoBar(String message){
        //Verify the saved info label
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", message);
    }
}
