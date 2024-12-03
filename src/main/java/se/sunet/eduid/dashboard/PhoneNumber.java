package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class PhoneNumber {
    private final Common common;
    private final TestData testData;

    public PhoneNumber(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPhoneNumber(){
        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("Telefonnummer");

        //Text
        common.verifyStringOnPage( "Du kan koppla ett eller flera mobiltelefonnummer till ditt eduID.");

        //Add more phone numbers
        common.verifyStringOnPage( "+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Mobile phone numbers");

        //Text
        common.verifyStringOnPage("You can connect one or more mobile phone numbers to your eduID.");

        //Add more phone numbers
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.selectSwedish();
    }

    public void addPhoneNumber() {
        Common.log.info("Add new phone number");

        //Press settings
        common.navigateToAccount();

        //Click add phone number button
        common.click(common.findWebElementById("phone-number-add-more-button"));

        //Verify placeholder
        common.verifyPlaceholder("telefonnummer", "number");

        //Enter phone number, if not set in test case, pick on from phone number list
        common.findWebElementById("number").clear();

        if(testData.isRegisterAccount()){
            //Fetch new number from phone_number.txt
            common.setPhoneNumber();
        }
        common.findWebElementById("number").sendKeys(testData.getPhoneNumber());

        //Click Add
        common.click(common.findWebElementById("add-mobile"));
        common.timeoutMilliSeconds(500);

        //TODO - press cancel button. id=cancel-adding-mobile
    }


    public void confirmNewPhoneNumber(){
        //Common.log.info("Confirm new phone number");

        //Add cookie for back doors
        if(!common.isCookieSet("autotests"))
            common.addMagicCookie();

        //Back door can not handle phone number with +, replacing it.
        String phoneNumber = testData.getPhoneNumber();
        Common.log.info("Confirm phone number: " +phoneNumber);

        if(phoneNumber.contains("+")) {
            phoneNumber = phoneNumber.replace("+", "%2b");
        }
        else if(phoneNumber.startsWith("070")) {
            phoneNumber = phoneNumber.replace("070", "%2b4670");
        }

        Common.log.info("Verify captcha for phone number: " +phoneNumber);
        verifyCaptcha();

        //Fetch the code
        String phoneCode = common.getCodeInNewTab("https://dashboard.dev.eduid.se/services/phone/get-code?eppn="
                +testData.getEppn() +"&phone=" +phoneNumber, 10);

        //Press confirm phone number link
        common.timeoutSeconds(1);
        common.click(common.findWebElementByXpathContainingText("Bekräfta"));
        common.timeoutMilliSeconds(500);

        //Solve captcha
        common.enterCaptcha("123456");

        if(!testData.getTestCase().equalsIgnoreCase("TC_51"))
            verifyLabelsConfirmPhoneNumber();

        common.click(common.findWebElementByXpathContainingText("Bekräfta"));

        //Solve captcha
        common.enterCaptcha("123456");

        //Enter the code
        common.findWebElementById("phone-confirm-modal").clear();
        common.findWebElementById("phone-confirm-modal").sendKeys(phoneCode);

        //Press OK button
        common.click(common.findWebElementByXpath("//*[@id=\"phone-confirm-modal-form\"]/div[2]/button"));
    }

    private void verifyLabelsConfirmPhoneNumber(){
        //Wait for pop-up close button
        common.timeoutMilliSeconds(1000);
        Common.log.info("Verify Captcha text and labels in Swedish");

        //Heading
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Skriv in koden som skickats till +46");

        //Label
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/div/label", "Kod");

        //Verify placeholder
        common.verifyPlaceholder("skriv in koden", "phone-confirm-modal");

        //Resend link
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-form\"]/div[1]/div[2]/a", "Skicka ny kod igen");

        //Close dialog
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        //English
        common.timeoutMilliSeconds(300);
        common.selectEnglish();

        Common.log.info("Verify Captcha text and labels in English");

        //Verify Button text
        common.verifyStringByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]/button",
                "CONFIRM");

        //Press Confirm
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]/button"));

        //Solve captcha
        common.enterCaptcha("123456");

        //Wait for close pop-up button
        common.explicitWaitVisibilityElement("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button");

        //Heading
        common.timeoutMilliSeconds(400);
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Enter the code sent to +46");

        //Label
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/div/label", "Code");

        //Verify placeholder
        common.verifyPlaceholder("enter code", "phone-confirm-modal");

        //Resend link
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-form\"]/div[1]/div[2]/a", "Send a new code");

        //Close dialog
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        //Swedish
        common.timeoutMilliSeconds(300);
        common.selectSwedish();
    }

    private void verifyCaptcha(){
        //We do not need to verify captcha text and labels in all test cases that add a phone number
        if(!testData.getTestCase().equalsIgnoreCase("TC_40") ) {
            //Press confirm beside phoneNumber to open captcha
            common.timeoutMilliSeconds(400);
            Common.log.info("Pressing confirm and wait for button in pop up before validate " +
                    "text strings within captcha window - Swedish");
            common.click(common.findWebElementByXpathContainingText("Bekräfta"));

            //Wait for generate new captcha button
            common.switchToPopUpWindow();
            common.explicitWaitClickableElement("//*[@id=\"phone-captcha-modal-form\"]/div[1]/div[2]/button");

            //Verify labels - Swedish
            //For unknown reason, text field has to be cleared twice.
            common.clearTextField(common.findWebElementById("phone-captcha-modal"));
            common.timeoutMilliSeconds(500);

            common.clearTextField(common.findWebElementById("phone-captcha-modal"));
            common.timeoutMilliSeconds(700);

            //common.verifyStringOnPage("*Fältet kan inte vara tomt");
            Common.log.info("Validate captcha buttons and text strings - Swedish");
            common.findWebElementById("phone-captcha-modal").sendKeys("1");
            common.verifyStringOnPage("För att begära kod, fyll i nedan captcha.");
            common.verifyStringOnPage("Ange koden från bilden");
            common.verifyStringOnPage("Generera en ny bild");
            common.verifyStringByXpath("//*[@id=\"phone-captcha-modal-form\"]/div[2]/button", "FORTSÄTT");

            //Close captcha
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5/button"));

            //Change to english
            common.timeoutMilliSeconds(500);
            common.selectEnglish();

            //Press confirm beside phoneNumber to open captcha
            common.timeoutMilliSeconds(1500);
            Common.log.info("Pressing confirm and wait for button in pop up before validate " +
                    "text strings within captcha window - English");

            common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr[2]/td[2]/button"));
            //common.click(common.findWebElementByXpathContainingText("confirm"));

            //Wait for generate new captcha button
            common.switchToPopUpWindow();
            common.explicitWaitClickableElement("//*[@id=\"phone-captcha-modal-form\"]/div[1]/div[2]/button");

            //Verify labels - English
            //common.verifyStringOnPage("*Field cannot be empty");
            Common.log.info("Validate captcha buttons and text strings - English");
            common.findWebElementById("phone-captcha-modal").sendKeys("2");
            common.verifyStringOnPage("To receive code, complete below captcha.");
            common.verifyStringOnPage("Enter the code from the image");
            common.verifyStringOnPage("Generate a new image");
            common.verifyStringByXpath("//*[@id=\"phone-captcha-modal-form\"]/div[2]/button", "CONTINUE");

            //Generate new captcha code
            common.findWebElementByXpath("//*[@id=\"phone-captcha-modal-form\"]/div[1]/div[2]/button").click();

            //Enter code
            common.findWebElementById("phone-captcha-modal").clear();
            common.findWebElementById("phone-captcha-modal").sendKeys("12345");

            //Press continue
            common.findWebElementByXpath("//*[@id=\"phone-captcha-modal-form\"]/div[2]/button").click();

            //Check status message
            common.verifyStatusMessage("Captcha failed. Please try again.");

            //Change back to swedish
            common.timeoutMilliSeconds(200);
            common.selectSwedish();

            //Check status message
            common.verifyStatusMessage("Captcha inte slutförd. Vänligen försök igen");
            common.closeStatusMessage();
        }

        //Press confirm beside phoneNumber to open captcha
        common.click(common.findWebElementByXpathContainingText("Bekräfta"));

        common.enterCaptcha("123456");
    }
}