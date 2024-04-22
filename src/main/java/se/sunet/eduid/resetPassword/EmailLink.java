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
        clickEmailLink();
    }

    private void clickEmailLink(){
        if(!testData.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
            common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getMagicCode());

            common.verifyStringOnPage("Bad Request");
        }
        else{
            //Add cookie for back doors
            common.addMagicCookie();

            //common.logMagicCookie();

            //Navigate to get the code
            common.timeoutSeconds(2);
            common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getEppn());

            Common.log.info("Get reset email code: https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getEppn());

            //Fetch the code
            testData.setEmailCode(common.findWebElementByXpath("/html/body").getText());

            if(testData.getEmailCode().contains("Bad Request")){
                Common.log.info("Got email code: " +testData.getEmailCode() +", trying to fetch it again");
                common.timeoutSeconds(2);
                common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +testData.getEppn());

                //Fetch the code
                common.timeoutSeconds(1);
                testData.setEmailCode(common.findWebElementByXpath("/html/body").getText());
            }

            Common.log.info("Email code: " +testData.getEmailCode());

            if(testData.getHeadlessExecution().equalsIgnoreCase("true")){
                //Simulate that clicking on link with code in email. There is no link in email anymore but problems with
                // headless execution. //TODO fix this to use else code below instead
                common.navigateToUrl("https://www.dev.eduid.se/reset-password/email-code/" +testData.getEmailCode());
            }
            else{
                //Navigate back
                common.getWebDriver().navigate().back();
                common.timeoutMilliSeconds(500);

                //Fill in the code and press OK
                Common.log.info("Fill in code and press ok");
                ConfirmEmailAddress confirmEmailAddress = new ConfirmEmailAddress(common, testData);
                confirmEmailAddress.typeEmailVerificationCode(testData.getEmailCode());

                common.findWebElementById("response-code-ok-button").click();
            }
            common.explicitWaitPageTitle("Återställ Lösenord | eduID");
        }
        common.timeoutSeconds(3);
    }
}