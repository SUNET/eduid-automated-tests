package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class EmailSent {
    private Common common;

    public EmailSent(Common common){
        this.common = common;
    }

    public void runEmailSent(){
        verifyPageTitle();
        verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//div/section[2]/div[2]/div/p/span", "Kontrollera din " +
                "e-postadress " +common.getUsername() +" för att fortsätta. \n Länken är giltig i två timmar.");
        common.verifyStringByXpath("//div/section[2]/div[2]/div/div/p/span[1]", "Om du inte " +
                "fick e-postmeddelandet? Kontrollera skräppost, \n eller");
    }
}
