package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.MailReader;

public class EmailAddresses {
    private Common common;

    public EmailAddresses(Common common){
        this.common = common;
    }

    public void runEmailAddresses(boolean removePrimary, boolean removeNewEmail1, String email1, String confirm1){
        verifyPageTitle();
        removeEmail(removePrimary, removeNewEmail1);
        addNewEmail(email1, confirm1);
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void removeEmail(boolean removePrimary, boolean removeNewEmail1) {

        // Try to removePrimary the primary email
        if(removePrimary) {
            common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[3]").click();

            //Verify info bar message - swedish
            verifyUpdatedInfoBar("Du måste ha minst en e-postadress knuten till ditt konto");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

            //Verify info bar message - english
            verifyUpdatedInfoBar("You must have at least one email address");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
        }

        // RemoveNewEmail1 the primary email
        if(removeNewEmail1) {
            common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[3]").click();

            //Verify info bar message - swedish
            verifyUpdatedInfoBar("E-postadress borttagen");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

            //Verify info bar message - english
            verifyUpdatedInfoBar("Successfully removed email address");
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
        }
    }

    private void addNewEmail(String email1, String confirm1) {
        MailReader mailReader = new MailReader();

        // Try to remove the primary email
        if(!email1.equals("") && email1.contains("@")) {
            //explicitWaitVisibilityElementId("add-more-button");
            common.findWebElementById("add-more-button").click();

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(email1);

            //Verify Add button label - Swedish
            common.verifyStrings("LÄGG TILL", common.findWebElementById("email-button").getText());

            //Click Add button
            common.findWebElementById("email-button").click();

            if (email1.equals(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr/td[1]").getText())) {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("Formulärfel. Se nedan för specifikt felmeddelande");
                common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "E-postadressen är redan tagen. Var god använd en annan.");
                common.verifyStringByXpath("//*[@id=\"email\"]/small/span[2]", "En giltig e-postadress");

                //Switch to English
                common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

                //Verify info messages - english
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("Check the form below for errors.");

                //Need to add the address again, since error message disappear when switch between language
                common.click(common.findWebElementByXpath("//*[@id=\"add-more-button\"]/span"));

                //Add new email address
                common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
                common. findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(email1);

                //Verify Add button label - English
                common.verifyStrings("ADD", common.findWebElementById("email-button").getText());
                common.verifyStringByXpath("//*[@id=\"email\"]/small/span", "A valid email address");

                //Click Add button
                common.click(common.findWebElementById("email-button"));

                common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "That email address is already in use, please choose another");

                //Switch back to Swedish
                common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
            }
            else {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);
                verifyUpdatedInfoBar("E-postadressen sparad");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[1]", email1);
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "BEKRÄFTA");

                //Switch to English
                common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

                //Verify info messages - english
                verifyUpdatedInfoBar("The email address was saved");
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[1]", email1);
                common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "CONFIRM");

                //Switch to Swedish
                common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));

                //Wait 5 sec for the email to arrive in inbox
                common.timeoutSeconds(5);

                // Confirm email address by code successfully, not successfully by emptyCode or wrongCode
                if(confirm1.equals("code") || confirm1.equals("emptyCode") || confirm1.equals("wrongCode")) {
                    String confirmationCode = null;

                    if(confirm1.equals("code")) {
                        //Get the confirmationCode
                        confirmationCode = mailReader.readEmail("confirmationCode");
                        //log.info("Confirmation Code: " + confirmationCode);
                    }
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span"));

                    //Switch to the new pop-up window
                    common.switchToPopUpWindow();

                    // In pop-up enter the confirmation code
                    common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").clear();
                    if (confirm1.equals("code"))
                        common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").sendKeys(confirmationCode);
                    if (confirm1.equals("wrongCode"))
                        common.findWebElementByXpath("//*[@id=\"emailConfirmDialogControl\"]/input").sendKeys("notTheCorrectCode");

                    //verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span",
                    //       "Ett meddelande har skickats till " +email1 +" med vidare instruktioner");

                    // Click OK
                    common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]/span"));

                    //Switch back to original window handle after submitting username, password
                    common.switchToDefaultWindow();
                } else if (confirm1.equals("url")) {
                    //Get the confirmationCode
                    String confirmationCode = mailReader.readEmail("confirmationUrl");
                    //log.info("Confirmation Url: " + confirmationCode);
                    common.navigateToUrl(confirmationCode);
                }

                //Check labels
                common.timeoutSeconds(1);
                if (confirm1.equals("emptyCode") || confirm1.equals("wrongCode"))
                    verifyUpdatedInfoBar("Ogiltig bekräftelsekod. Var god prova igen eller begär en ny bekräftelsekod");
                else {
                    if(confirm1.equals("code"))
                        verifyUpdatedInfoBar("E-postadressen bekräftad");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "GÖR PRIMÄR");

                    //Make email 1 primary
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span"));

                    //Verify info label
                    common.timeoutMilliSeconds(500);
                    verifyUpdatedInfoBar("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button/span", "GÖR PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/span/span", "PRIMÄR");

                    //Switch back to default primary email
                    common.click(common.findWebElementByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/button/span"));

                    //Verify info label
                    common.timeoutMilliSeconds(500);
                    verifyUpdatedInfoBar("Din primära e-postadress har ändrats");

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[1]/td[2]/span/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"email-display\"]/div[1]/table/tbody/tr[2]/td[2]/button/span", "GÖR PRIMÄR");
                }
            }
        }
        else if(!email1.contains("") && !email1.contains("@")){
            common.timeoutSeconds(500);
            common.click(common.findWebElementById("add-more-button"));

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(email1);

            //Verify that correct message is displayed
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "Ogiltig e-postadress");
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[2]", "En giltig e-postadress");

            //Click on English
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

            //Need to add the address again, since error message disappear when switch between language
            common.explicitWaitClickableElement("//*[@id=\"add-more-button\"]/span");
            common.click(common.findWebElementByXpath("//*[@id=\"add-more-button\"]/span"));

            //Add new email address
            common.findWebElementByXpath("//*[@id=\"email\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(email1);

            //Verify that correct message is displayed - English
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span[1]/span", "The entered value does not look like an email");
            common.verifyStringByXpath("//*[@id=\"email\"]/small/span", "A valid email address");

            //Switch back to Swedish
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
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
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

        //Heading
        common.verifyStringOnPage("Email addresses");

        //Text
        common.verifyStringOnPage("You can " +
                "connect one or more email addresses with your eduID account and select one to be your primary email address.");

        //Add more addresses
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }

    private void verifyUpdatedInfoBar(String message){
        //Verify the saved info label
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
        common.verifyStringOnPage(message);
    }
}
