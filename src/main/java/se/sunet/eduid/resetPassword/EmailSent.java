package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class EmailSent {
    private Common common;

    public EmailSent(Common common){
        this.common = common;
    }

    public void runEmailSent(){
        verifyPageTitle();
        verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Reset password - Email");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//div/div[2]/h2", "Reset password");
        common.verifyStringByXpath("//div/div[3]/p", "Reset password message sent. Check your email to continue.");
    }
}
