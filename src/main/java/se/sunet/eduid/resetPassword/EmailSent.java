package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class EmailSent {
    private final Common common;
    private final TestData testData;

    public EmailSent(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runEmailSent(){
        verifyPageTitle();
        verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        //Verify the texts after request of new pw
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "En länk har skickats " +
                "till din e-postadress " +testData.getEmail()+". Länken är giltig i två timmar.");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/p", "Har du inte fått " +
                "e-postmeddelandet? Kontrollera din skräppost, ellerskicka länken igen");


        //Switch to english
        common.selectEnglish();
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "A link has been sent to your email "
                +testData.getEmail() +". Link is valid for 2 hours.");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/p", "If you didn't " +
                "receive the email? Check your junk email, orresend link");

        //Switch to english
        common.selectSwedish();
    }
}
