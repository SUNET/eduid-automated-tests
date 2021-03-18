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
        common.verifyPageTitle("Återställ lösenord - E-post");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//div/div[2]/h2", "Återställ lösenord");
        common.verifyStringByXpath("//div/div[3]/p", "Ett meddelande om lösenordsåterställning " +
                "har skickats. Kolla din e-post för att fortsätta.");
    }
}
