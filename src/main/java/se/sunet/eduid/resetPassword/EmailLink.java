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
        common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/email/" +common.getMagicCode());

        if(!common.getMagicCode().equals("mknhKYFl94fJaWaiVk2oG9Tl")){
            common.verifyStringByXpath("//div/div[2]/h2", "404 Not found");
        }
        common.timeoutSeconds(3);
    }
}
