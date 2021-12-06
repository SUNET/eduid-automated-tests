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
        common.verifyStringByXpath("//div/section[2]/div[2]/div/p/span", "Kontrollera din " +
                "e-postadress " +testData.getUsername() +" för att fortsätta. \n Länken är giltig i två timmar.");
        common.verifyStringByXpath("//div/section[2]/div[2]/div/div/p/span[1]", "Om du inte " +
                "fick e-postmeddelandet? Kontrollera skräppost, \n eller");
    }
}
