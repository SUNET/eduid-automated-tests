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
        common.verifyStringOnPage( "Du kan koppla ett " +
                "eller flera av dina mobiltelefonnummer till ditt eduID-konto och därefter välja vilket av dem som ska vara primär.");

        //Add more phone numbers
        common.verifyStringOnPage( "+ lägg till fler");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Mobile phone numbers");

        //Text
        common.verifyStringOnPage("You can connect " +
                "one or more mobile phone numbers to your eduID, but one has to be set as the primary one.");

        //Add more phone numbers
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.selectSwedish();
    }

    public void addPhoneNumber(){
        //Press settings
        common.navigateToSettings();

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();

        //Click add phone number button
        common.click(common.findWebElementByXpath("//div/section[2]/div/div/div/div[2]/div[2]/button"));

        //Verify placeholder
        common.verifyStrings("Telefonnummer", common.findWebElementByXpath("//*[@id=\"number\"]").getAttribute("placeholder"));

        //Check message when phone number is not valid
        checkMessageFaultyNumber();

        //Enter phone number
        common.findWebElementById("number").clear();
        common.findWebElementById("number").sendKeys(testData.getPhoneNumber());

        //Click Add
        common.click(common.findWebElementById("mobile-button"));

        //Check status info
        common.verifyStatusMessage("Telefonnummer sparades");
    }

    public void confirmNewPhoneNumber(){
        //Add cookie for back doors
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
        String eppen = common.findWebElementByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]").getText();

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

        //Enter the code
        common.findWebElementById("phoneConfirmDialogControl").clear();
        common.findWebElementById("phoneConfirmDialogControl").sendKeys(phoneCode);

        //Press OK button
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));

        //Check status info
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();

        //Verify status message
        common.verifyStatusMessage("Telefonnummer har bekräftats");

        //Hide the info message
        common.closeStatusMessage();
    }

    private void checkMessageFaultyNumber(){
        //Enter a phone number on incorrect format to check message
        common.findWebElementById("number").clear();
        common.findWebElementById("number").sendKeys("1223456789");

        //Click outside to trigger validation of phone number
        common.click(common.findWebElementByXpath("//*[@id=\"add-more-button\"]"));

        common.verifyStringByXpath("//*[@id=\"number-wrapper\"]/small/span",
                "Ogiltigt telefonnummer. Skriv ett svensk nummer eller ett internationellt nummer " +
                        "som börjar med '+' följt av 6-20 siffror.");
    }
}