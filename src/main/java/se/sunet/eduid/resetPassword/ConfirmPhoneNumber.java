package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class ConfirmPhoneNumber {
    private Common common;

    public ConfirmPhoneNumber(Common common){
        this.common = common;
    }

    public void runConfirmPhoneNumber(boolean codeSentTooEarly, String confirmPhoneNumberCode){
        verifyPageTitle();
        getConfirmationCode(codeSentTooEarly, confirmPhoneNumberCode);
        confirmPhoneNumber(confirmPhoneNumberCode);
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void getConfirmationCode(boolean codeSentTooEarly, String confirmPhoneNumberCode){
        //Press settings tab
        common.click(common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/h5/span"));

        //Press the Confirm phone number link
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span"));

        common.switchToPopUpWindow();
        verifyLabels();

        //Click on re-send OTP in order to get it for the first time. To not hit the 5min limitation of sending the code,
        // it will not be sent for the cancel and if the magic is provided. Because the code will be sent for 1st use case where
        // incorrect code is entered for phone number confirmation.
        if(!confirmPhoneNumberCode.equalsIgnoreCase("cancel") && confirmPhoneNumberCode.equals("mknhKYFl94fJaWaiVk2oG9Tl")) {
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a"));

            common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");

            //Pop up will now be closed and info that code has been sent is displayed
            if(codeSentTooEarly)
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Vi kan bara skicka en kod var 5:e minut, var god vänta innan du ber om en ny kod.");
            else
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "En ny bekräftelsekod har skickats till dig");
        }
    }

    private void confirmPhoneNumber(String confirmPhoneNumberCode){
        //If we should click on the OK button or Cancel in confirm pop-up
        if(!confirmPhoneNumberCode.equalsIgnoreCase("cancel")) {
            //Press the Confirm phone number link
            common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span"));

            common.switchToPopUpWindow();

            common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").sendKeys(confirmPhoneNumberCode);

            //Send the code by click OK
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));

            //Verify that the confirmation info label shows
            common.explicitWaitClickableElement("//*[@id=\"content\"]/div[1]/div/span");
            if(confirmPhoneNumberCode.equals("mknhKYFl94fJaWaiVk2oG9Tl"))
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Telefonnummer har bekräftats");
            else
                common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/div/span", "Telefonummret du angav kan inte hittas");

        }
        else {
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[2]/span"));
        }
    }

    private void verifyLabels(){
        common.explicitWaitClickableElement("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a");
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/span", "Skriv in koden som skickats till +46");
        common.verifyStringByXpath("//*[@id=\"phoneConfirmDialogControl\"]/label/span", "ANGE DIN BEKRÄFTELSEKOD");
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div[2]/a", "Skicka bekräftelsekoden igen");
    }
}