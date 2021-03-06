package se.sunet.eduid.resetPassword;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class ConfirmPhoneNumber {
    private Common common;

    public ConfirmPhoneNumber(Common common){
        this.common = common;
    }

    public void runConfirmPhoneNumber(){
        verifyPageTitle();
        getConfirmationCode();
        confirmPhoneNumber();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void getConfirmationCode(){
        //Press settings tab
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();

        //Press the Confirm phone number link
        common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span").click();

        common.switchToPopUpWindow();
        verifyLabels();

        //Click on re-send OTP in order to get it for the first time. To not hit the 5min limitation of sending the code,
        // it will not be sent for the cancel/incorrect code and if the magic is provided. Because the code will be sent for 1st use case where
        // incorrect code is entered for phone number confirmation. The confirmPhoneNumberCode is the same as the magic
        if(!common.getMagicCode().equalsIgnoreCase("cancel") && common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
            common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a").click();

            //Wait for status information to appear
            common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");

            //Pop up will now be closed and info that code has been sent is displayed
            //TODO maybe we should have test case when code is sent too earyl as well... later on
            boolean codeSentTooEarly = false;
            if(codeSentTooEarly)
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Vi kan bara " +
                        "skicka en kod var 5:e minut, var god vänta innan du ber om en ny kod.");
            else
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "En ny bekräftelsekod har skickats till dig");
        }
        //If OTP should not be ordered, close the pop-up
        else {
            common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/div/button").click();
            common.timeoutMilliSeconds(500);
        }
    }

    private void confirmPhoneNumber(){
        //If we should click on the OK button or Cancel in confirm pop-up
        if(!common.getMagicCode().equalsIgnoreCase("cancel")) {

            //Press the Confirm phone number link
            common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span").click();

            common.switchToPopUpWindow();

            //Add cookie for back doors
            common.addMagicCookie();

            //Back door can not handle phone number with +, replacing it.
            String phoneNumber;
            if(common.getPhoneNumber().contains("+"))
                phoneNumber = common.getPhoneNumber().replace("+", "%2b");
            else
                phoneNumber = common.getPhoneNumber();

            //Fetch the code
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +common.getEppn() +"&phone=" +phoneNumber);

            String phoneCode = common.findWebElementByXpath("/html/body").getText();
            Common.log.info("Phone code: " +phoneCode);

            WebDriverManager.getWebDriver().navigate().back();

            //Press the Confirm phone number link - again
            common.explicitWaitClickableElement("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span");
            common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span").click();

            common.switchToPopUpWindow();

            //Enter code in pop-up window, if correct code should be sent, else send not correct code. First a too long code
            //to check the information text, then send incorrect but code within limits 10-12 digits.
            common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").clear();
            if(common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").sendKeys(phoneCode);
            else {
                //Type a too long code, press OK
                common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").sendKeys(common.getMagicCode());

                //Verify that the ok button is not enabled when typed code is not 10 characters long
                Assert.assertTrue(!common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]").isEnabled(),
                        "The button should not be enabled, until 10 character code is entered");

                //Clear the text field and send another incorrect code
                common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").sendKeys("codeBlue10");
            }
            //Send the code by click OK
            common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]").click();

            //Verify that the confirmation info label shows
            common.explicitWaitClickableElement("//*[@id=\"panel\"]/div[1]/div/span");
            if(common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Telefonnummer har bekräftats");
            else
                common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Telefonumret du angav kan inte hittas");
                //common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Den " +
                //        "bekräftelsekod du angett stämmer inte. Var god försök igen");

        }
    }

    private void verifyLabels(){
        common.explicitWaitClickableElement("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a");
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span", "Skriv in koden som skickats till +46");
        common.verifyStringByXpath("//*[@id=\"phoneConfirmDialogControl\"]/div/label/span", "Bekräftelsekod");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a", "Skicka bekräftelsekoden igen");
    }
}