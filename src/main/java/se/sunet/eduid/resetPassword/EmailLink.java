package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

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
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/email/" +common.getMagicCode());

            common.verifyStringByXpath("//div/div[2]/h2", "404 Not found");
        }
        else{
            //Add cookie for back doors
            common.addMagicCookie();

            //Fetch the code
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/get-email-code?eppn=nunif-mados");
            String emailCode = common.findWebElementByXpath("/html/body").getText();
            common.log.info("Email code: " +emailCode);

            //Simulate that clicking on link with code in email.
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/email/" +emailCode);
        }
        common.timeoutSeconds(3);
    }
}