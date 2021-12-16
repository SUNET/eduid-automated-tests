package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class PasswordChanged {
    private final Common common;

    public PasswordChanged(Common common){
        this.common = common;
    }

    public void runPasswordChanged(){
        verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        //Verify status message - swedish
        //common.verifyStatusMessage("Klart");

        //verify the labels - swedish
        common.verifyStringByXpath("//div/section[2]/div[2]/div/p", "Lösenordet har uppdaterats");
        common.verifyStringByXpath("//*[@id=\"return-login\"]", "Gå till eduID");

        //Switch language to english
        common.findWebElementByLinkText("English").click();

        //Verify status message - english
        //common.verifyStatusMessage("Success");

        //verify the labels - english
        common.verifyStringByXpath("//div/section[2]/div[2]/div/p", "Password has been updated.");
        common.verifyStringByXpath("//*[@id=\"return-login\"]", "Go to eduID");

        //Switch language to swedish
        common.findWebElementByLinkText("Svenska").click();
    }

    private void clickReturnToLoginLink() {
        //Return to landing page, click with javascript is needed here...
        common.findWebElementById("return-login").click();

        common.verifyPageTitle("eduID");
    }
}