package se.sunet.eduid.dashboard;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.MailReader;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class EmailAddresses_old {
    private final Common common;
    private final TestData testData;
    String emailConfirmationCodeInputField = "email-confirm-modal";
    String confirmationCode;

    public EmailAddresses_old(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailAddresses(){
        common.navigateToAccount();
        verifyPageTitle();
        if(testData.isRemoveNewEmail1())
            removeEmail();
        addNewEmail();
        //Skip labels verification when not submitting a valid confirmation code
        if(!testData.getConfirmNewEmail1().equals("wrongCode")) {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }
    }

    private void verifyPageTitle() {
        common.waitUntilPageTitleContains("Konto | eduID");
    }

    private void removeEmail() {
        //common.explicitWaitClickableElement("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[3]/button");
        common.click(common.waitUntilClickable(By.xpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[3]/button")));
    }

    private void addNewEmail() {
        MailReader mailReader = new MailReader();

        // Try to remove the primary email
        if(!testData.getAddNewEmail1().equals("") && testData.getAddNewEmail1().contains("@")) {
            //common.explicitWaitVisibilityElementId("emails-add-more-button");
            log.info("Click on add new email link");
            common.click(common.findWebElement(By.id("emails-add-more-button")));

            //Verify placeholder
            common.verifyPlaceholder("namn@example.com", "email");

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify Abort button label - Swedish
            common.verifyString(By.id("cancel-adding-email"), "AVBRYT");

            //Verify Add button label - Swedish
            common.verifyString(By.id("add-email"), "LÄGG TILL");

            //Click Add button
            log.info("Click on add new email button after typing the new email address");
            //common.click(common.findWebElement(By.id("add-email"));
            common.findWebElement(By.id("add-email")).click();
            common.timeoutMilliSeconds(200);

            //Try to add already existing email address
            if (testData.getAddNewEmail1().equals(common.findWebElement(By.xpath(
                    "//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[1]")).getText())) {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(500);

                common.verifyString(By.xpath("//div/div/main/div/section/article[2]/div/form/div[1]/div/span"),
                        "E-postadressen finns redan i listan.");

                //Switch to English
                common.timeoutMilliSeconds(200);
                common.selectEnglish();

                //Need to add the address again, since error message disappear when switch between language
                log.info("Click on add new email button, after language change");
                common.click(common.findWebElement(By.id("emails-add-more-button")));

                //Add new email address
                addEmailAddress(testData.getAddNewEmail1());

                //Verify Cancel button label - English
                //common.verifyStrings("CANCEL", common.findWebElement(By.id("cancel-adding-email").getText());
                common.verifyString(By.id("cancel-adding-email"), "CANCEL");

                common.timeoutMilliSeconds(500);
                common.verifyString(By.xpath("//div/div/main/div/section/article[2]/div/form/div[1]/div/span"),
                        "The email is already in the list.");

                //Switch back to Swedish
                common.timeoutMilliSeconds(200);
                common.selectSwedish();
            }
            //Add new email address
            else {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(1000);
                common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[1]"),
                        testData.getAddNewEmail1());
                common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button"),
                        "BEKRÄFTA");

                //Wait 15 sec for the email to arrive in inbox
                log.info("Waiting 15 seconds for the email to arrive in inbox");
                common.timeoutSeconds(15);

                // Confirm email address by code successfully, not successfully by wrongCode
                if(testData.getConfirmNewEmail1().equals("code") || testData.getConfirmNewEmail1().equals("wrongCode")) {
                    log.info("Confirm email with code: " +testData.getConfirmNewEmail1());

                    if(testData.getConfirmNewEmail1().equals("code")) {
                        //Get the confirmationCode
                        confirmationCode = mailReader.readEmail("confirmationCode");
                        log.info("Confirmation Code: " + confirmationCode);
                    }

                    verifyConfirmEmailPopupLabels();

                    // In pop-up enter the confirmation code
                    common.findWebElement(By.id(emailConfirmationCodeInputField)).clear();

                    if (testData.getConfirmNewEmail1().equals("code")) {
                        common.findWebElement(By.id(emailConfirmationCodeInputField)).sendKeys(confirmationCode);
                        log.info("Confirming email with the correct activation code: " +confirmationCode);
                    }
                    if (testData.getConfirmNewEmail1().equals("wrongCode")) {
                        common.findWebElement(By.id(emailConfirmationCodeInputField)).sendKeys("18587");
                        log.info("Attempt to confirm email not correct activation code.");
                        common.verifyString(By.xpath("//*[@id=\"email-confirm-modal-wrapper\"]/div[2]/span"),
                                "Den kod du angett stämmer inte. Var god försök igen");

                        //For some reason the .clear() method does not work here, closing pop-up and open it again
                        common.findWebElement(By.xpath(
                                "//*[@id=\"add-email-addresses\"]/dialog/div/div/div/div/button")).click();

                        //Click on confirm link
                        pressConfirmEmail();

                        //Switch to the new pop-up window
                        common.switchToPopUpWindow();

                        //Send
                        common.findWebElement(By.id("email-confirm-modal")).sendKeys("e01460442a");
                    }

                    // Click OK
                    common.findWebElement(By.xpath("//*[@id=\"email-confirm-modal-form\"]/div[2]/button")).click();
                }

                //Check labels
                common.timeoutSeconds(1);
                if(testData.getConfirmNewEmail1().equals("wrongCode")) {
                    log.info("Verify status message when email confirmation code is not correct");
                    common.verifyStatusMessage("Ogiltig kod eller en kod som har gått ut. Var god prova igen eller begär en ny kod");
                }
                else {
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/span"),
                            "PRIMÄR");
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button"),
                            "GÖR PRIMÄR");

                    //Make email 1 primary
                    log.info("Clicking on email address on second row to make the added email primary");
                    common.findWebElement(By.xpath(
                            "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button")).click();

                    //Verify info label
                    common.timeoutMilliSeconds(1500);

                    //Check labels
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/button"),
                            "GÖR PRIMÄR");
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/span"),
                            "PRIMÄR");

                    //Switch back to default primary email
                    log.info("Clicking on email address on first row to make the default email primary again");

                    common.findWebElement(By.xpath(
                            "//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/button")).click();

                    //Verify info label
                    common.timeoutMilliSeconds(1000);

                    //Check labels
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[2]/td[2]/span"),
                            "PRIMÄR");
                    common.verifyString(By.xpath("//*[@id=\"add-email-addresses\"]/div/div/table/tbody/tr[3]/td[2]/button"),
                            "GÖR PRIMÄR");
                }
            }
        }
        else if(!testData.getAddNewEmail1().contains("") && !testData.getAddNewEmail1().contains("@")){
            common.timeoutSeconds(500);
            common.click(common.findWebElement(By.id("emails-add-more-button")));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed
            common.verifyString(By.xpath("//*[@id=\"email-wrapper\"]/small/span"), "Ogiltig e-postadress");
            common.verifyString(By.xpath("//*[@id=\"email-wrapper\"]/div/span"), "En giltig e-postadress");

            //Click on English
            common.timeoutMilliSeconds(200);
            common.selectEnglish();

            //Need to add the address again, since error message disappear when switch between language
            common.click(common.findWebElement(By.id("emails-add-more-button")));

            //Add new email address
            addEmailAddress(testData.getAddNewEmail1());

            //Verify that correct message is displayed - English
            common.verifyString(By.xpath("//*[@id=\"email-wrapper\"]/small/span"), "The entered email is invalid");
            common.verifyString(By.xpath("//*[@id=\"email-wrapper\"]/div/span"), "A valid email address");

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
        common.verifyStringOnPage("+ Lägg till fler");
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
        common.verifyStringOnPage("+ Add more");

        //Click on Swedish
        common.timeoutMilliSeconds(200);
        common.selectSwedish();
    }

    private void addEmailAddress(String emailAddress){
        common.waitUntilClickable(By.id("email"));

        common.findWebElement(By.id("email")).clear();
        common.findWebElement(By.id("email")).sendKeys(emailAddress);

        log.info("Adding email address: " +emailAddress);
    }

    private void verifyConfirmEmailPopupLabels(){
        //Select English
        common.selectEnglish();

        //Click on confirm link to open pop up
        pressConfirmEmail();

        // In pop-up, verify labels and placeholder
        String headerXpath = "//*[@id=\"add-email-addresses\"]/dialog/div/div/div/div/h4";
        String labelXpath = "//*[@id=\"email-confirm-modal-wrapper\"]/div/label";
        String sendNewCodeXpath = "//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/button";
        String closeButtonXpath = "//*[@id=\"add-email-addresses\"]/dialog/div/div/div/div/button";

        // In pop-up, verify labels and placeholder
        log.info("Verify email confirmation pop-up labels - English");
        common.verifyString(By.xpath(headerXpath), "Enter the code sent to " +testData.getAddNewEmail1());
        common.verifyString(By.xpath(labelXpath), "Code");
        common.verifyPlaceholder("enter code", emailConfirmationCodeInputField);
        common.verifyString(By.xpath(sendNewCodeXpath), "Send a new code");

        //Close confirmation pop-up
        common.findWebElement(By.xpath(closeButtonXpath)).click();

        common.timeoutMilliSeconds(200);
        common.selectSwedish();

        pressConfirmEmail();

        log.info("Verify email confirmation pop-up labels - Swedish");
        common.verifyString(By.xpath(headerXpath), "Skriv in koden som skickats till " +testData.getAddNewEmail1());
        common.verifyString(By.xpath(labelXpath), "Kod");
        common.verifyPlaceholder("skriv in koden", emailConfirmationCodeInputField);
        common.verifyString(By.xpath(sendNewCodeXpath), "Skicka ny kod");

        log.info("Done! - Verify email confirmation pop-up labels - Swedish");
    }

    private void pressConfirmEmail(){
        //Click on confirm link
        log.info("Click on confirm email link");
        common.click(common.findWebElement(By.xpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button")));
    }
}