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
            common.findWebElementByLinkText("Svenska").click();

        //Click add phone number button
        common.findWebElementByXpath("//div/section[2]/div[2]/div/div/div[2]/div[2]/button").click();

        //Enter phone number
        common.findWebElementById("number").clear();
        common.findWebElementById("number").sendKeys(common.getPhoneNumber());

        //Click Add
        common.findWebElementById("mobile-button").click();

        //Check status info
        //common.timeoutMilliSeconds(500);
        common.verifyStatusMessage("Telefonnummer sparades");
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
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span").click();

        //Store eppen
        String eppen = common.findWebElementByXpath("//*[@id=\"uniqueId-container\"]/div[2]/p[1]").getText();

        //Fetch the code
        common.navigateToUrl("https://dashboard.dev.eduid.se/services/phone/get-code?eppn=" +eppen +"&phone=" +phoneNumber);

        String phoneCode = common.findWebElementByXpath("/html/body").getText();
        Common.log.info("Phone code: " +phoneCode);

        //Navigate back to settings page
        common.navigateToUrl("https://dashboard.dev.eduid.se/profile/settings/personaldata");

        //TODO temp fix to get swedish language
//        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
//            common.findWebElementByLinkText("Svenska").click();

        //Press confirm phone number link
        common.explicitWaitClickableElement("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span");

        common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr/td[2]/button/span").click();

        //Enter the code
        common.findWebElementById("phoneConfirmDialogControl").clear();
        common.findWebElementById("phoneConfirmDialogControl").sendKeys(phoneCode);

        //Press OK button
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]/span").click();

        //Check status info
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.findWebElementByLinkText("Svenska").click();

        //common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Telefonnummer har bekräftats");
        common.verifyStatusMessage("Telefonnummer har bekräftats");

        //Hide the info message
        common.findWebElementByXpath("//*[@id=\"panel\"]/div[1]/div/button/span").click();
    }
}