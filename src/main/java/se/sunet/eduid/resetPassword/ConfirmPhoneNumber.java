package se.sunet.eduid.resetPassword;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class ConfirmPhoneNumber {
    private final Common common;
    private final TestData testData;

    public ConfirmPhoneNumber(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmPhoneNumber(){
        common.navigateToSettings();
        verifyPageTitle();
        getConfirmationCode();
        confirmPhoneNumber();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Inställningar | eduID");
        common.verifyPageTitle("Inställningar | eduID");
    }

    private void getConfirmationCode(){
        //Press the Confirm phone number link
        pressConfirmPhoneNumber();

        common.switchToPopUpWindow();
        verifyLabels();

        //Click on re-send OTP in order to get it for the first time. To not hit the 5min limitation of sending the code,
        // it will not be sent for the cancel/incorrect code and if the magic is provided. Because the code will be sent for 1st use case where
        // incorrect code is entered for phone number confirmation. The confirmPhoneNumberCode is the same as the magic
        if(!testData.getMagicCode().equalsIgnoreCase("cancel") && testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
            log.info("Requesting an otp phone code");
            common.timeoutMilliSeconds(500);
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div/a"));
            common.timeoutMilliSeconds(500);

            //Pop up will now be closed and info that code has been sent is displayed
            //TODO maybe we should have test case when code is sent too early as well... later on
            boolean codeSentTooEarly = false;
            if(codeSentTooEarly) {
                common.verifyStatusMessage("Vi kan bara " +
                        "skicka en kod var 5:e minut, var god vänta innan du ber om en ny kod.");
            }

            //Close pop-up again
            common.closePopupDialog();
        }
        //If OTP should not be ordered, close the pop-up
        else {
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
            common.timeoutMilliSeconds(500);
        }
    }

    private void confirmPhoneNumber(){
        //If we should click on the OK button or Cancel in confirm pop-up
        if(!testData.getMagicCode().equalsIgnoreCase("cancel")) {

            //Press the Confirm phone number link
            pressConfirmPhoneNumber();

            common.switchToPopUpWindow();

            //Add cookie for back doors
            common.addMagicCookie();

            //Back door can not handle phone number with +, replacing it.
            String phoneNumber;
            if(testData.getPhoneNumber().contains("+"))
                phoneNumber = testData.getPhoneNumber().replace("+", "%2b");
            else
                phoneNumber = testData.getPhoneNumber();

            //Fetch the code
            common.timeoutSeconds(2);
            log.info("Navigate to get phone code: " +"https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +testData.getEppn() +"&phone=" +phoneNumber);
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +testData.getEppn() +"&phone=" +phoneNumber);

            String phoneCode = common.findWebElementByXpath("/html/body").getText();
            log.info("Phone code: " +phoneCode);

            common.getWebDriver().navigate().back();

            //Press the Confirm phone number link - again
            pressConfirmPhoneNumber();

            common.switchToPopUpWindow();

            //Enter code in pop-up window, if correct code should be sent, else send not correct code. First a too long code
            //to check the information text, then send incorrect but code within limits 10-12 digits.
            common.findWebElementById("phone-confirm-modal").clear();
            if(testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.findWebElementById("phone-confirm-modal").sendKeys(phoneCode);
            else {
                //Type a too long code, press OK
                common.findWebElementById("phone-confirm-modal").sendKeys(testData.getMagicCode());

                //Verify that the ok button is not enabled when typed code is not 10 characters long
                Assert.assertTrue(!common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button").isEnabled(),
                        "The button should not be enabled, until 10 character code is entered");

                common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/small/span", "Ogiltig kod");

                //Close the confirm pop-up
                common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();
                common.timeoutMilliSeconds(500);

                //Open confirm pop-up again
                pressConfirmPhoneNumber();

                //Clear the text field and send another incorrect code
                common.findWebElementById("phone-confirm-modal").clear();
                common.findWebElementById("phone-confirm-modal").sendKeys("2f4g6h7j8j");
            }
            //Send the code by click OK
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button"));

            //Verify that the confirmation info label shows
            if(testData.getMagicCode().equalsIgnoreCase("notTheCorrectCode"))
                common.verifyStatusMessage("Telefonumret du angav kan inte hittas");
            else if(!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.verifyStatusMessage("Den kod du angett stämmer inte. Var god försök igen");
        }
    }

    private void verifyLabels(){
        common.explicitWaitClickableElement("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div/a");
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Skriv in koden som skickats till +46");
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/div/label",
                "Kod");
        common.verifyPlaceholder("Kod", "phone-confirm-modal");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div/a",
                "Skicka ny kod igen");
    }

    private void pressConfirmPhoneNumber(){
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]/button"));
    }
}