package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class RequestNewPassword {
    private final Common common;

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

        //Verify the texts after request of new pw
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p/span", "Kontrollera din e-postadress "
                +common.getEmail()+" för att fortsätta. \n Länken är giltig i två timmar.");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/p/span[1]", "Om du inte " +
                "fick e-postmeddelandet? Kontrollera skräppost, \n eller");


        //Switch to english
        common.findWebElementByLinkText("English").click();
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p/span", "Please check your email "
                +common.getEmail() +" to continue. \n Link is valid for 2 hours.");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/p/span[1]", "If you didn’t " +
                "receive the email? Check your junk email, \n or");

        //Switch to english
        common.findWebElementByLinkText("Svenska").click();
    }
}
