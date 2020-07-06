package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;

public class ConfirmedNewAccount {
    private Common common;

    public ConfirmedNewAccount(Common common){
        this.common = common;
    }

    public void runConfirmedNewAccount(){
        verifyPageTitle();
        verifyLabels();
        clickGoToMyEduID();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }

    private void verifyLabels(){
        //Headings
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1/span", "eduID är enklare och säkrare inloggning.");

        //Details
        //TODO bug - email address should be printed and not the {email}
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h3/span", "Registrering av {email} är klar");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p/span", "Detta är dina inloggningsuppgifter för eduID.");

        //TODO bug - email and password labels should be in swedish
        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "EMAIL");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", common.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "PASSWORD");
        common.setPassword(common.findWebElementById("user-password").getText());
        //English
        /*
        //Headings
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");

        //Details
        common.verifyStringByXpath("//*[@id=\"register-container\"]/div/h3/span", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"register-container\"]/div/p/span", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "EMAIL");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", common.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "PASSWORD");
        common.setRecommendedPw(common.findWebElementById("user-password").getText());
        */
    }

    private void clickGoToMyEduID(){
        common.findWebElementByXpath("//*[@id=\"gotit-button\"]/span").click();
    }
}
