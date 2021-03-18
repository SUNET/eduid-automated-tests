package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class ExtraSecurity {
    private Common common;

    public ExtraSecurity(Common common){
        this.common = common;
    }

    public void runExtraSecurity(){
        verifyPageTitle();
        verifyLabels();
        confirmPasswordChange();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord - Extra säkerhet");
    }

    private void verifyLabels(){
        //verify the labels
        common.verifyStringByXpath("//div/div[2]/h2", "Extra säkerhet");
        common.verifyStringByXpath("//div/div[2]/p", "Välj en metod för att öka säkerheten");
        common.verifyStringByXpath("//div/div[4]/form/div/div/button", "FORTSÄTT UTAN EXTRA " +
                "SÄKERHET. JAG FÖRSTÅR ATT DET BETYDER ATT JAG MÅSTE BEKRÄFTA MITT KONTO IGEN.");
    }

    private void confirmPasswordChange(){
        if(common.getSendMobileOneTimePassword()) {
            common.findWebElementByXpath("//div/div[3]/form/div/div/button").click();
        }
        else
            common.findWebElementByXpath("//div/div[4]/form/div/div/button").click();
    }
}
