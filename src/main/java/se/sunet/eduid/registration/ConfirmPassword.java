package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmPassword {
    private final Common common;
    private final TestData testData;

    public ConfirmPassword(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmPassword(){
        verifyPageTitle();
        verityTextAndLabels();
        submitPassword();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Registrera | eduID");
    }


    private void verityTextAndLabels(){
        //Wait for copy to clipboard button
        common.explicitWaitClickableElementId("clipboard");

        common.verifyStringOnPage("Registrera: Bekräfta ditt lösenord");
        common.verifyStringOnPage("Ett lösenord har genererats åt dig. Du kan enkelt kopiera och " +
                "klistra in ditt lösenord genom att klicka på ikonen för att Kopiera till klippbordet.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");

        //Set the password
        testData.setPassword(common.findWebElementById("copy-new-password").getAttribute("value"));

        common.verifyStringByXpath("//*[@id=\"newPassword-wrapper\"]/div/label", "Repetera ditt nya lösenord");
        //common.verifyPlaceholder("newPassword", "xxxx xxxx xxxx");

        common.verifyStringById("new-password-button", "OK");

        //Select English
        common.selectEnglish();

        common.verifyStringOnPage("Register: Confirm your password");
        common.verifyStringOnPage("A password has been generated for you. You can easily copy and paste " +
                "your password by clicking the copy to clipboard icon.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");

        //Verify the password
        common.verifyStrings(common.findWebElementById("copy-new-password").getAttribute("value"), testData.getPassword());

        common.verifyStringByXpath("//*[@id=\"newPassword-wrapper\"]/div/label", "Repeat new password");
        //common.verifyPlaceholder("newPassword", "xxxx xxxx xxxx");

        common.verifyStringById("new-password-button", "OK");

        //Select Swedish
        common.selectSwedish();
    }

    private void submitPassword(){
        //Fill in password
        common.findWebElementById("newPassword").sendKeys(testData.getPassword());

        //Press OK
        common.findWebElementById("new-password-button").click();

        //Wait for the go to eduid link on next page
        common.explicitWaitClickableElementId("finished-button");
    }
}