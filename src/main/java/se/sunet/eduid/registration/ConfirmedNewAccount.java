package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ConfirmedNewAccount {
    private final Common common;
    private final TestData testData;

    public ConfirmedNewAccount(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmedNewAccount(){
        verifyPageTitle();
        verifyLabels();
        clickGoToMyEduID();
    }

    private void verifyPageTitle() {
        //common.explicitWaitPageTitle("Registrera | eduID");
        common.verifyPageTitle("Registrera | eduID");

        //TODO temp fix to get swedish language
        common.timeoutMilliSeconds(500);
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void verifyLabels(){
        //Details
        common.verifyStringOnPage("Registrering av ditt eduID är klar.");
        common.verifyStringOnPage("Detta är dina inloggningsuppgifter. Ett lösenord har genererats. " +
                "Spara lösenordet. Du kan efter du loggat in välja att byta lösenord.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Button
        common.verifyStringById("finished-button", "Gå till eduID");

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");
        testData.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.selectEnglish();

        common.verifyPageTitle("Register | eduID");

        //Details
        common.verifyStringOnPage("You have completed the registration for eduID.");
        common.verifyStringOnPage("These are your login details for eduID. A password has been " +
                "generated for you. Save the password. Once you've logged in you can change your password.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");

        //Button
        common.verifyStringById("finished-button", "Go to eduID");


        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementById("finished-button"));
    }
}
