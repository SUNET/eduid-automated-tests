package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class VerifyPhoneNumber {
    private Common common;

    public VerifyPhoneNumber(Common common){
        this.common = common;
    }

    public void runVerifyPhoneNumber(boolean resendOTP){
        verifyPageTitle();
        verifyLabels();
        continueOrResendOTP(resendOTP);
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Reset password - Verify phone number");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//div/div[2]/h2", "Verify phone number");
        common.verifyStringByXpath("//div/div[2]/p", "Enter the code you received via SMS");
        common.verifyStringByXpath("//div/div[3]/form/div[3]/a", "Resend code or try another way");
    }

    private void continueOrResendOTP(boolean resendOTP){
        if(resendOTP) {
            common.click(common.findWebElementByXpath("//div/div[3]/form/div[3]/a"));

            //Testng cannot execute same class twice in *.xml file. Therefore call neccessary methods separately
            //to test the re-send OTP functionality.
            ExtraSecurity security = new ExtraSecurity(common);
            security.runExtraSecurity(true);

            //To continue the flow and enter the OTP with magic-code
            continueOrResendOTP(false);
        }
        else {
            common.findWebElementById("phone_code").sendKeys("mknhKYFl94fJaWaiVk2oG9Tl");
            common.click(common.findWebElementByXpath("//div/div[3]/form/div[2]/div/button"));
        }
    }
}
