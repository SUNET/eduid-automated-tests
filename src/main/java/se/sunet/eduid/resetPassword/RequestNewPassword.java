package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class RequestNewPassword {
    private Common common;

    public RequestNewPassword(Common common){
        this.common = common;
    }

    public void runRequestNewPassword(){
        verifyPageTitle();
        enterEmail();
        pressRestPassword();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void enterEmail(){
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(common.getUsername());
    }

    private void pressRestPassword(){
        common.findWebElementById("reset-password-button").click();

        common.verifyStatusMessage("Återställningslänken har skickats till din epost.");
    }
}
