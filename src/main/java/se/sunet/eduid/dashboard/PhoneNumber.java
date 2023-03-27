package se.sunet.eduid.dashboard;

import org.testng.Assert;
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
        //Press settings
        common.navigateToSettings();

        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();

        //Click add phone number button
        common.click(common.findWebElementById("phone-number-add-more-button"));

        //Verify placeholder
        common.verifyPlaceholder("Telefonnummer", "number");

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
        //Add cookie for back doors
        if(!common.isCookieSet("autotests"))
            common.addMagicCookie();

        //Back door can not handle phone number with +, replacing it.
        String phoneNumber = testData.getPhoneNumber();
        Common.log.info("Adding phone number: " +phoneNumber);

        if(phoneNumber.contains("+")) {
            phoneNumber = phoneNumber.replace("+", "%2b");
        }
        else if(phoneNumber.startsWith("070")) {
            phoneNumber = phoneNumber.replace("070", "%2b4670");
        }

        //Fetch eppen - click on advanced settings
        common.navigateToAdvancedSettings();

        //Store eppen
        String eppen = common.findWebElementById("user-eppn").getText();

        //Fetch the code
        common.navigateToUrl("https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +eppen +"&phone=" +phoneNumber);
        Common.log.info("Fetching phone code: " +"https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +eppen +"&phone=" +phoneNumber);

        String phoneCode = common.findWebElementByXpath("/html/body").getText();
        if(phoneCode.contains("Bad Request"))
            Assert.fail("Got Bad request instead of a phone code");
        else
            Common.log.info("Phone code: " +phoneCode);

        //Navigate back to settings page
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/settings/personaldata");

        //Press confirm phone number link
        common.timeoutSeconds(2);
        common.click(common.findWebElementByXpathContainingText("Bekräfta"));

        if(!testData.getTestCase().equalsIgnoreCase("TC_51"))
            verifyLabelsConfirmPhoneNumber();

        common.click(common.findWebElementByXpathContainingText("Bekräfta"));

        //Enter the code
        common.findWebElementById("phone-confirm-modal").clear();
        common.findWebElementById("phone-confirm-modal").sendKeys(phoneCode);

        //Press OK button
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));
    }

    private void verifyLabelsConfirmPhoneNumber(){
        //Wait for pop-up close button
        common.timeoutMilliSeconds(1000);

        //Heading
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Skriv in koden som skickats till +46");

        //Label
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/div/label", "Kod");

        //Verify placeholder
        common.verifyPlaceholder("Kod", "phone-confirm-modal");

        //Resend link
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div/a", "Skicka ny kod igen");

        //Close dialog
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        //English
        common.selectEnglish();

        //Verify Button text
        common.verifyStringByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]/button",
                "CONFIRM");

        //Press Confirm
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr/td[2]/button"));

        //Wait for close pop-up button
        common.explicitWaitVisibilityElement("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button");

        //Heading
        common.verifyXpathContainsString("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5",
                "Enter the code sent to +46");

        //Label
        common.verifyStringByXpath("//*[@id=\"phone-confirm-modal-wrapper\"]/div/label", "Code");

        //Verify placeholder
        common.verifyPlaceholder("Code", "phone-confirm-modal");

        //Resend link
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[2]/div/a", "Send a new code");

        //Close dialog
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button").click();

        //Swedish
        common.selectSwedish();
    }
}