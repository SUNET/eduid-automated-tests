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

    public void runPasswordChanged2(String username, String newPassword){
        //TODO add pagetitle check when its present, missing at the moment 23/4-2020
        //verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
        login(username, newPassword);
        verifyLogin(username);
    }

//    @Test
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

    private void login(String username, String newPassword){
        //Login again
        common.click(common.findWebElementByXpath("//*[@id=\"login\"]/a"));
        login.enterUsernamePassword(username, newPassword, false);
        login.signIn(username, false);
    }

    private void verifyLogin(String username){
        common.explicitWaitPageTitle("eduID");

        //Just verify that log in succeeded
        common.verifyStringOnPage(username);
    }
}