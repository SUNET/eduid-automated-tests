package se.sunet.eduid.dashboard;

import org.openqa.selenium.WebElement;
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
        common.navigateToAccount();
        verifyPageTitle();
        if(testData.isRemoveNewEmail1())
            removeEmail();
        addNewEmail();
        //Skip labels verification when not submittning a valid confirmation code
        if(!testData.getConfirmNewEmail1().equals("wrongCode")) {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Konto | eduID");
    }

    private void removeEmail() {
        common.explicitWaitClickableElement("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[3]/button");
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[3]/button"));
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

            //Try to add already existing email address
            if (testData.getAddNewEmail1().equals(common.findWebElementByXpath(
                    "//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[1]").getText())) {
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
            //Add new email address
            else {
                //Verify info messages - swedish
                common.timeoutMilliSeconds(1000);
                common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[1]", testData.getAddNewEmail1());
                common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button", "BEKRÄFTA");

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
                    WebElement emailConfirmationCodeInputField = common.findWebElementById("email-confirm-modal");
                    emailConfirmationCodeInputField.clear();
                    if (testData.getConfirmNewEmail1().equals("code")) {
                        emailConfirmationCodeInputField.sendKeys(confirmationCode);
                        Common.log.info("Confirmation email correct activation code: " +confirmationCode);
                    }
                    if (testData.getConfirmNewEmail1().equals("wrongCode")) {
                        emailConfirmationCodeInputField.sendKeys("18587");
                        Common.log.info("Attempt to confirm email not correct activation code.");
                        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/small/span",
                                "Den kod du angett stämmer inte. Var god försök igen");

                        //For some reason the .clear() method does not work here, closing pop-up and open it again
                        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5/button").click();

                        //Click on confirm link
                        pressConfirmEmail();

                        //Switch to the new pop-up window
                        common.switchToPopUpWindow();

                        common.findWebElementById("email-confirm-modal").sendKeys("e01460442a");
                    }

                    // Click OK
                    common.click(common.findWebElementByXpath("//*[@id=\"email-confirm-modal-form\"]/div[2]/button"));

                    //Switch back to original window handle after submitting username, password
                    common.switchToDefaultWindow();
                }
                /*
                Obsolete, we do not provide any url in the confirmation emails 2024-04-11
                else if (testData.getConfirmNewEmail1().equals("url")) {
                    //Get the confirmationUrl
                    String confirmationUrl = mailReader.readEmail("confirmationUrl");
                    Common.log.info("Confirmation Url: " + confirmationUrl);
                    //common.navigateToUrl(confirmationUrl);
                    common.navigateToUrl("https://dashboard.dev.eduid.se/services/email/verify?code=2cd31df7-7ce1-4179-b43d-4282a52b09e6&email=eduidtest.se1@gmail.com");
                }
                */

                //Check labels
                common.timeoutSeconds(1);
                if(testData.getConfirmNewEmail1().equals("wrongCode")) {
                    Common.log.info("Verify status message when email confirmation code is not correct");
                    common.verifyStatusMessage("Ogiltig kod eller en kod som har gått ut. Var god prova igen eller begär en ny kod");

                    //For unknown reason page is frozen when submitting wrong code, refresh it before change of language
/*                    common.getWebDriver().navigate().refresh();
                    common.timeoutSeconds(4);

                    common.selectEnglish();

                    common.verifyStatusMessage("The code is invalid or has expired, please try again or request a new code");
                    common.selectSwedish();*/

                }
                else {
                    common.timeoutMilliSeconds(1000);
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");

                    //Make email 1 primary
                    common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[2]/button", "GÖR PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/span", "PRIMÄR");

                    //Switch back to default primary email
                    common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[2]/button"));

                    //Verify info label
                    common.timeoutMilliSeconds(1000);

                    //Check labels
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[2]/td[2]/span", "PRIMÄR");
                    common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button", "GÖR PRIMÄR");
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
        // In pop-up, verify labels and placeholder
        Common.log.info("Verify email confirmation pop-up - Swedish");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Skriv in koden som skickats till " +testData.getAddNewEmail1());
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Kod");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/button",
                "Skicka ny kod igen");

        //Close confirmation pop-up
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        common.timeoutMilliSeconds(200);
        common.selectEnglish();

        //Click on confirm link
        pressConfirmEmail();

        //Switch to the new pop-up window
        common.switchToPopUpWindow();

        // In pop-up, verify labels and placeholder
        Common.log.info("Verify email confirmation pop-up - English");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Enter the code sent to " +testData.getAddNewEmail1());
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-wrapper\"]/div/label", "Code");
        common.verifyStringByXpath("//*[@id=\"email-confirm-modal-form\"]/div[1]/div[2]/button",
                "Send a new code");

        //Close confirmation pop-up
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        common.timeoutMilliSeconds(200);
        common.selectSwedish();

        pressConfirmEmail();
    }

    private void pressConfirmEmail(){
        //Click on confirm link
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[2]/div/div/table/tbody/tr[3]/td[2]/button"));
    }
}