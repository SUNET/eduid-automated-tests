package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class ExtraSecurity {
    private Common common;

    public ExtraSecurity(Common common){
        this.common = common;
    }

    public void runExtraSecurity2(boolean sendMobileOneTimePassword){
        verifyPageTitle();
        verifyLabels();
        confirmPasswordChange(sendMobileOneTimePassword);
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Reset password - Extra security");
    }

    private void verifyLabels(){
        //verify the labels
        common.verifyStringByXpath("//div/div[2]/h2", "Extra security");
        common.verifyStringByXpath("//div/div[2]/p", "Choose an option to enhance the security");
        common.verifyStringByXpath("//div/div[4]/form/div/div/button", "CONTINUE WITH NO EXTRA SECURITY. " +
                "I UNDERSTAND THAT I WILL HAVE TO VERIFY MY ACCOUNT AGAIN.");
    }

    private void confirmPasswordChange(boolean sendMobileOneTimePassword){
        if(sendMobileOneTimePassword) {
            common.click(common.findWebElementByXpath("//div/div[3]/form/div/div/button"));
        }
        else
            common.click(common.findWebElementByXpath("//div/div[4]/form/div/div/button"));
    }
}
