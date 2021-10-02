package se.sunet.eduid.resetPassword;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class EmailLink {
    private Common common;

    public EmailLink(Common common){
        this.common = common;
    }

    public void runEmailLink(){
        clickEmailLink();
    }

    private void clickEmailLink(){
        if(!common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
            common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +common.getMagicCode());

            common.verifyStringOnPage("Bad Request");
        }
        else{
            //Add cookie for back doors
            common.addMagicCookie();

            //common.logMagicCookie();

            //Navigate to get the code
            common.timeoutSeconds(2);
            common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +common.getEppn());

            //Common.log.info("Current url: " + WebDriverManager.getWebDriver().getCurrentUrl());

            //Fetch the code
            common.setEmailCode(common.findWebElementByXpath("/html/body").getText());

            if(common.getEmailCode().contains("Bad Request")){
                Common.log.info("Got email code: " +common.getEmailCode() +", trying to fetch it again");
                common.timeoutSeconds(2);
                common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-email-code?eppn=" +common.getEppn());

                //Fetch the code
                common.timeoutSeconds(1);
                common.setEmailCode(common.findWebElementByXpath("/html/body").getText());
            }

            Common.log.info("Email code: " +common.getEmailCode());

            //Simulate that clicking on link with code in email.
            common.navigateToUrl("https://www.dev.eduid.se/reset-password/email-code/" +common.getEmailCode());

            common.explicitWaitPageTitle("eduID login");
        }
        common.timeoutSeconds(3);
    }
}