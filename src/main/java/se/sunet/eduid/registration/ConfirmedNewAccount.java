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
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Registrering av ditt eduID är klar.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Detta är dina inloggningsuppgifter för eduID.");

        //TODO bug - email and password labels should be in swedish #998
        //Email
        //common.verifyStringByXpath("//*[@id="email-display"]/fieldset[1]/label", "Epost");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Button
        common.verifyStringById("gotit-button", "KLART");

        //Password
        // TODO  - should be "lösenord"
        //common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "Lösenord");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");
        testData.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.selectEnglish();

        //Details
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");

        //Button
        common.verifyStringById("gotit-button", "GO TO MY EDUID");


        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementByXpath("//*[@id=\"gotit-button\"]"));
    }
}
