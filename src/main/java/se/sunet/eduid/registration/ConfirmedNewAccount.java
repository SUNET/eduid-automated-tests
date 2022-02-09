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
        //Headings
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID är enklare och säkrare inloggning.");

        //Details
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h3", "Registrering av ditt eduID är klar.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Detta är dina inloggningsuppgifter för eduID.");

        //TODO bug - email and password labels should be in swedish #245
        //Email
        //common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Epost");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Email");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "Password");
        testData.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.selectEnglish();

        //Headings
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1", "eduID is easier and safer login.");

        //Details
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h3", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Email");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", testData.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "Password");

        //Switch language to Swedish
        common.selectSwedish();
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementByXpath("//*[@id=\"gotit-button\"]"));
    }
}
