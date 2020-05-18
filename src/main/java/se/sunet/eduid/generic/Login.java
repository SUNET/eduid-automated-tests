package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Login {
    private Common common;

    public Login(Common common){
        this.common = common;
    }

    public void runLogin(){
        verifyPageTitle();

        if(common.getResetPassword()) {
            resetPassword();
        }
        else if(common.getRegisterAccount())
            registerAccount();
        else {
            enterUsernamePassword();
            signIn();
        }
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID-inloggning");
        common.verifyPageTitle("eduID-inloggning");
    }

    public void enterUsernamePassword(){
        //Enter username
        common.findWebElementById("username").sendKeys(common.getUsername());

        if(common.getIncorrectPassword())
            common.findWebElementById("password").sendKeys("notTheCorrectPassword");
        else
            common.findWebElementById("password").sendKeys(common.getPassword());
    }

    public void signIn(){
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button"));

        if(common.getIncorrectPassword())
            common.verifyStringByXpath("//*[@id=\"alert_msg\"]", "Ogiltigt användarnamn eller lösenord (1 försök)");
        else {
            common.timeoutSeconds(1);
            common.verifyStringOnPage(common.getUsername().toLowerCase());
        }
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementByXpath("//*[@id=\"eduid-idp-menu\"]/div/div[1]/a"));
    }

    private void resetPassword(){
        //Click on forgot password link
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[2]/a"));
    }
}