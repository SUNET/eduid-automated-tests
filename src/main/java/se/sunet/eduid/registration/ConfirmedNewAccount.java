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
        common.explicitWaitPageTitle("eduID signup");
        common.verifyPageTitle("eduID signup");

        //TODO temp fix to get swedish language
        common.timeoutMilliSeconds(500);
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void verifyLabels(){
        //Details
        common.verifyStringByXpath("//*[@id=\"panel\"]/form/h1", "Registrering av ditt eduID är klar.");
        common.verifyStringByXpath("//*[@id=\"panel\"]/form/p", "Detta är dina inloggningsuppgifter för eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Button
        common.verifyStringById("gotit-button", "Gå till eduID");

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");
        testData.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.selectEnglish();

        //Details
        common.verifyStringByXpath("//*[@id=\"panel\"]/form/h1", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"panel\"]/form/p", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");

        //Button
        common.verifyStringById("gotit-button", "Go to my eduID");


        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementByXpath("//*[@id=\"gotit-button\"]"));
    }
}
