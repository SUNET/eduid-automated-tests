package se.sunet.eduid.resetPassword;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;

public class PasswordChanged {
    private Common common;
    private Login login;

    public PasswordChanged(Common common){
        this.common = common;
        login = new Login(common);
    }

    public void runPasswordChanged(){
        //TODO add pagetitle check when its present, missing at the moment 23/4-2020
        //verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
        login();
        verifyLogin();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("");
    }

    private void verifyLabels(){
        //verify the labels
        common.verifyStringByXpath("//div/div[2]/p[1]", "Password has been updated successfully.");
        common.verifyStringByXpath("//div/div[2]/p[2]/a", "Return to login page");
    }

    private void clickReturnToLoginLink() {
        //Return to landing page
        common.click(common.findWebElementByXpath("//div/div[2]/p[2]/a"));
        common.verifyPageTitle("eduID");
    }

    //TODO move this two methods below into the test case instead....
    private void login(){
        //Login again
        common.click(common.findWebElementByXpath("//*[@id=\"login\"]/a"));
        login.enterUsernamePassword();
        login.signIn();
    }

    private void verifyLogin(){
        common.explicitWaitPageTitle("eduID");

        //Just verify that log in succeeded
        common.verifyStringOnPage(common.getUsername());
    }
}