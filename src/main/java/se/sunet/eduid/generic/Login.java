package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Login {
    private Common common;

    public Login(Common common){
        this.common = common;
    }

    public void runLogin2(String username, String password, boolean resetPassword, boolean registerAccount, boolean incorrectPassword){
        verifyPageTitle();

        if(resetPassword) {
            resetPassword();
        }
        else if(registerAccount)
            registerAccount();
        else {
            enterUsernamePassword(username, password, incorrectPassword);
            signIn(username, incorrectPassword);

        }
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID-inloggning");
        common.verifyPageTitle("eduID-inloggning");
    }

    public void enterUsernamePassword(String username, String password, boolean incorrectPassword){
        //Enter username
        common.findWebElementById("username").sendKeys(username);

        //Set the recommended password to "", to prepare for change pw test cases
        if(common.getRecommendedPw() == null)
            common.setRecommendedPw("");

        //If we have saved the recommended password, we use it
        if(!common.getRecommendedPw().equals("")) {
            Common.log.info("Log in with stored generated password: " + common.getRecommendedPw());
            common.findWebElementById("password").sendKeys(common.getRecommendedPw());
        }
        else {
            if(incorrectPassword)
                common.findWebElementById("password").sendKeys("notTheCorrectPassword");
            else
                common.findWebElementById("password").sendKeys(password);
        }
    }

    public void signIn(String username, boolean incorrectPassword){
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button"));

        if(incorrectPassword)
            common.verifyStringByXpath("//*[@id=\"alert_msg\"]", "Ogiltigt användarnamn eller lösenord (1 försök)");
        else {
            common.timeoutSeconds(1);
            common.verifyStringOnPage(username);
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
