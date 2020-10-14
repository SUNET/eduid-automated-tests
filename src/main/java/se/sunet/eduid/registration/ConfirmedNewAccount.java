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
        setUserData();
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
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h3/span", "Registrering av ditt eduID är klar.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p/span", "Detta är dina inloggningsuppgifter för eduID.");

        //TODO bug - email and password labels should be in swedish #245
        //Email
        //common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Epost");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Email");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", common.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "Password");
        common.setPassword(common.findWebElementById("user-password").getText());

        //Switch language to English
        common.findWebElementByLinkText("English").click();

        //Headings
        common.verifyStringByXpath("//*[@id=\"root\"]/section[1]/div/h1/span", "eduID is easier and safer login.");

        //Details
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h3/span", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p/span", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "Email");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", common.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "Password");

        //Switch language to Swedish
        common.findWebElementByLinkText("Svenska").click();
    }

    private void setUserData(){
        common.setDisplayName("inget namn sparat");
        common.setGivenName("inget");
        common.setSurName("namn");
        common.setPersonalNumber("lägg till personnummer");
        common.setPhoneNumber("inget telefonnummer sparat");
        common.setEmail(common.getUsername());
    }

    private void clickGoToMyEduID(){
        common.findWebElementByXpath("//*[@id=\"gotit-button\"]/span").click();
    }
}
