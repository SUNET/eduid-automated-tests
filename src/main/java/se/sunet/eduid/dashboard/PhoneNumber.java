package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PhoneNumber {
    private Common common;

    public PhoneNumber(Common common){
        this.common = common;
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
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Heading
        common.verifyStringOnPage("Mobile phone numbers");

        //Text
        common.verifyStringOnPage("You can connect " +
                "one or more mobile phone numbers to your eduID, but one has to be set as the primary one.");

        //Add more phone numbers
        common.verifyStringOnPage("+ add more");

        //Click on Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    public void addPhoneNumber(){
        //Press settings
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));

        //Click add phone number button
        common.click(common.findWebElementByXpath("/html/body/div/section[2]/div[2]/div/div/div[3]/div[2]/button"));

        //Enter phone number
        common.findWebElementByXpath("//*[@id=\"number\"]/input").clear();
        common.findWebElementByXpath("//*[@id=\"number\"]/input").sendKeys(common.getPhoneNumber());

        //Click Add
        common.click(common.findWebElementById("mobile-button"));

        //Check status info
        //common.timeoutMilliSeconds(500);
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Telefonnummer sparades");
    }

    public void confirmNewPhoneNumber(){
        //Add cookie for back doors
        common.addMagicCookie();

        //Back door can not handle phone number with +, replacing it.
        String phoneNumber;
        if(common.getPhoneNumber().contains("+"))
            phoneNumber = common.getPhoneNumber().replace("+", "%2b");
        else
            phoneNumber = common.getPhoneNumber();

        //Fetch eppen - click on advanced settings
        common.click(common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span"));

        //Store eppen
        String eppen = common.findWebElementByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]").getText();

        //Fetch the code
        common.navigateToUrl("https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +eppen +"&phone=" +phoneNumber);

        String phoneCode = common.findWebElementByXpath("/html/body").getText();
        common.log.info("Phone code: " +phoneCode);

        //Navigate back to settings page
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/settings/personaldata");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));

        //Press confirm phone number link
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span"));

        //Enter the code
        common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").clear();
        common.findWebElementByXpath("//*[@id=\"phoneConfirmDialogControl\"]/input").sendKeys(phoneCode);

        //Press OK button
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]/span"));

        //Check status info
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Telefonnummer har bekräftats");
    }
}