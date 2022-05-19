package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class InitPwChange {
    private final Common common;
    private final TestData testData;

    public InitPwChange(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runInitPwChange(){

        //Press change password
        common.click(common.findWebElementById("security-change-button"));

        common.switchToPopUpWindow();

        common.timeoutMilliSeconds(500);
        verifyPopupLabels();

        //Accept if true else Abort
        if(testData.isButtonValuePopup()) {
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]").click();

            common.timeoutMilliSeconds(500);
            common.switchToDefaultWindow();
        }
        else {
            //Close pop-up window
            common.closePopupDialog();
            common.timeoutMilliSeconds(500);
            common.switchToDefaultWindow();
        }
    }


    private void verifyPopupLabels(){
        //Heading
        common.verifyStringOnPage("Av säkerhetsskäl...");

        //Text
        common.verifyStringOnPage("Du kommer behöva logga in igen med ditt nuvarande lösenord för att kunna skriva in det nya.");

        //Buttons
        common.verifyStringOnPage("GODKÄNN");
    }
}
