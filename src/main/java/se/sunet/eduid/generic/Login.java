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
        common.explicitWaitPageTitle("eduID login");
        //TODO temp comment for test of FTs "other" idp site
        //common.verifyPageTitle("eduID-inloggning");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")){
            common.findWebElementByLinkText("Svenska").click();
        }

        common.timeoutMilliSeconds(500);
    }

    public void enterUsernamePassword(){
        //Enter username
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(common.getUsername());
        //common.findWebElementByXpath("//div/section[2]/div[2]/div/form/div[1]/input").sendKeys(common.getUsername());

        Common.log.info("Log in with username: " +common.getUsername());

        common.findWebElementByXpath("//div/section[2]/div[2]/div/form/div[2]/div[2]/input").clear();

        common.findWebElementByXpath("//*[@id=\"current-password-wrapper\"]/div[2]/input").sendKeys(common.getPassword());
        Common.log.info("Log in with password: " +common.getPassword());

    }

    public void signIn(){
        common.findWebElementById("login-form-button").click();

        if(common.getIncorrectPassword()) {
            common.timeoutMilliSeconds(500);
            common.verifyXpathContainsString("//div/section[2]/div[1]/div/span", "E-postadressen eller lösenordet är felaktigt.");
        }
        else {
            //Wait for the "EduId for" label at dashboard
            common.timeoutMilliSeconds(500);
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
        common.explicitWaitVisibilityElementId("link-forgot-password");
        common.findWebElementById("link-forgot-password").click();

        //Wait for next page, heading
        common.explicitWaitClickableElement("//div/section[2]/div[2]/form/button/span");
    }
}