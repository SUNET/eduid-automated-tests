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
        //TODO temp comment for test of FTs "other" idp site
        //common.verifyPageTitle("eduID-inloggning");
    }

    public void enterUsernamePassword(){
        //Enter username
        common.findWebElementById("username").clear();
        common.findWebElementById("username").sendKeys(common.getUsername());

        common.findWebElementById("password").clear();
        if(common.getIncorrectPassword())
            common.findWebElementById("password").sendKeys("notTheCorrectPassword");
        else {
            common.findWebElementById("password").sendKeys(common.getPassword());
            Common.log.info("Log in with password: " +common.getPassword());
        }
    }

    public void signIn(){
        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();

        if(common.getIncorrectPassword()) {
            //common.verifyStringByXpath("//*[@id=\"alert_msg\"]", "Ogiltigt användarnamn eller lösenord (1 försök)");
            common.verifyXpathContainsString("//*[@id=\"alert_msg\"]", "användarnamn eller lösenord");
        }
        else {
            //common.timeoutSeconds(1);
            //common.verifyStringOnPage(common.getUsername().toLowerCase());

            //Wait for the "EduId for" label at dashboard
            common.explicitWaitVisibilityElement("//section[1]/div/h1/span");
        }
    }

    private void registerAccount(){
        //Click on sign up button
        common.findWebElementByXpath("//*[@id=\"eduid-idp-menu\"]/div/div[1]/a").click();

        //Wait for next page
        common.explicitWaitClickableElement("//section[1]/div/h1/span");
    }

    private void resetPassword(){
        //Click on forgot password link
        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[2]/a").click();

        //Wait for next page
        common.explicitWaitPageTitle("Återställ lösenord - E-post");
    }
}