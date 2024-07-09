package se.sunet.eduid.resetPassword;

import se.sunet.eduid.registration.ConfirmEmailAddress;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class EmailLink {
    private final Common common;
    private final TestData testData;

    public EmailLink(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailLink(){
        verifyEmailAddress();
    }

    private void verifyEmailAddress(){
        if(!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
            common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getMagicCode());

            common.verifyStringOnPage("Bad Request");
        }
        else{
            //Add cookie for back doors
            common.addMagicCookie();

            //Navigate to get the code
            testData.setEmailCode(common.getCodeInNewTab(
                    "https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getEppn(), 6));

                //Fill in the code and press OK
                Common.log.info("Filling in the code (" +testData.getEmailCode() +") and pressing ok");
                ConfirmEmailAddress confirmEmailAddress = new ConfirmEmailAddress(common, testData);
                confirmEmailAddress.typeEmailVerificationCode(testData.getEmailCode());

                common.findWebElementById("response-code-ok-button").click();

            common.explicitWaitPageTitle("Återställ Lösenord | eduID");
        }
        common.timeoutSeconds(3);
    }

}