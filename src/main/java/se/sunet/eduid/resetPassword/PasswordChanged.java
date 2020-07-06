package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class PasswordChanged {
    private Common common;

    public PasswordChanged(Common common){
        this.common = common;
    }

    public void runPasswordChanged(){
        //TODO add pagetitle check when its present, missing at the moment 23/4-2020
        //verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
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
        //Return to landing page, click with javascript is needed here...
        common.click(common.findWebElementByXpath("//div/div[2]/p[2]/a"));
        common.verifyPageTitle("eduID");
    }
}