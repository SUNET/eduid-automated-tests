package se.sunet.eduid.resetPassword;

import se.sunet.eduid.registration.ConfirmEmailAddress;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class EmailLink {
    private final Common common;
    private final TestData testData;
    private final ConfirmEmailAddress confirmEmailAddress;

    public EmailLink(Common common, TestData testData, ConfirmEmailAddress confirmEmailAddress) {
        this.common = common;
        this.testData = testData;
        this.confirmEmailAddress = confirmEmailAddress;
    }

    public void runEmailLink(){
        verifyEmailAddress();
    }

    private void verifyEmailAddress(){
        //Verify that there is no reset pw code when magic cookie is not set
        if(!common.isCookieSet("autotests")){
            common.navigateToUrl(testData.getEmailResetPwCodeUrl() +testData.getEppn());
            common.verifyStringOnPage("Bad Request");
        }
        else{
            //Add cookie for back doors
            common.addMagicCookie();

            //Navigate to get the code
            testData.setEmailCode(common.getCodeInNewTab(
                    testData.getEmailResetPwCodeUrl() +testData.getEppn(), 6));

                //Fill in the code and press OK
                Common.log.info("Filling in the code (" +testData.getEmailCode() +") and pressing ok");
                confirmEmailAddress.typeEmailVerificationCode(testData.getEmailCode());

                common.findWebElementById("response-code-ok-button").click();

            common.explicitWaitPageTitle("Återställ lösenord | eduID");
        }
        common.timeoutSeconds(3);
    }
}