package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class RequestNewPassword {
    private Common common;

    public RequestNewPassword(Common common){
        this.common = common;
    }

    public void runRequestNewPassword(String username){
        verifyPageTitle();
        enterEmail(username);
        pressRestPassword();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Reset password - Email");
    }

    private void enterEmail(String username){
        common.findWebElementById("email").sendKeys(username);
    }

    private void pressRestPassword(){
        common.findWebElementByXpath("//div/div[3]/form/div[2]/div/button").click();
    }
}
