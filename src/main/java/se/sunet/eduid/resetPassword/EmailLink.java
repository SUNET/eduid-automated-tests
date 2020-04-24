package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class EmailLink {
    private Common common;

    public EmailLink(Common common){
        this.common = common;
    }

    public void runEmailLink2(String magicCode){
        clickEmailLink(magicCode);
    }

    private void clickEmailLink(String magicCode){
        common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/email/" +magicCode);

        if(!magicCode.equals("mknhKYFl94fJaWaiVk2oG9Tl")){
            common.verifyStringByXpath("//div/div[2]/h2", "404 Not found");
        }
        common.timeoutSeconds(3);
    }
}
