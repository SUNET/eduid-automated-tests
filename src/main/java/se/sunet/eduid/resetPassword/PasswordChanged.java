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
        common.verifyPageTitle("Återställ Lösenord | eduID");;
    }

    private void verifyLabels(){
        //verify the labels - swedish
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "Lösenordet har uppdaterats");
        common.verifyStringByXpath("//*[@id=\"return-login\"]", "Gå till eduID");

        //Switch language to english
        common.selectEnglish();

        //verify the labels - english
        common.verifyPageTitle("Reset Password | eduID");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "Password has been updated.");
        common.verifyStringByXpath("//*[@id=\"return-login\"]", "Go to eduID");

        //Switch language to swedish
        common.selectSwedish();
    }

    private void clickReturnToLoginLink() {
        //Return to landing page, click with javascript is needed here...
        common.click(common.findWebElementById("return-login"));

        common.verifyPageTitle("eduID");
    }
}