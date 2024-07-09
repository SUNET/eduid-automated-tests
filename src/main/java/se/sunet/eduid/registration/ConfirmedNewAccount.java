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
        common.verifyStringOnPage("Registrera: Slutförd");
        common.verifyStringOnPage("Här är dina inloggningsuppgifter med ett genererat lösenord. Spara " +
                "lösenordet! Obs: mellanrummen i lösenordet är för att göra det mer läsbart och tas automatiskt bort " +
                "vid inmatning. Du kan efter att du har loggat in välja att byta lösenord.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Button
        common.verifyStringById("finished-button", "Gå till eduID för att logga in");

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");
        common.verifyStringById("user-password", testData.getPassword());
        //testData.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.selectEnglish();

        common.verifyPageTitle("Register | eduID");

        //Details
        common.verifyStringOnPage("Register: Completed");
        common.verifyStringOnPage("These are your login details for eduID. Save the password! Note: " +
                "spaces in the generated password are there for legibility and will be removed automatically if entered." +
                " Once you've logged in it is possible to change your password.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");
        common.verifyStringById("user-password", testData.getPassword());

        //Button
        common.verifyStringById("finished-button", "Go to eduID to login");


        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementById("finished-button"));
        common.timeoutSeconds(1);
    }
}
