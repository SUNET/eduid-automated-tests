package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class InitPwChange {
    private Common common;

    public InitPwChange(Common common){
        this.common = common;
    }

    public void runInitPwChange(){

        //Press change password
        common.findWebElementById("security-change-button").click();

        common.switchToPopUpWindow();

        common.timeoutMilliSeconds(500);
        verifyPopupLabels();

        //Accept if true else Abort
        if(common.getButtonValuePopup()) {
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span").click();

            common.timeoutMilliSeconds(500);
            common.switchToDefaultWindow();
        }
        else {
            //Close pop-up window
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/div/button").click();
            common.timeoutMilliSeconds(500);
        }
    }


    private void verifyPopupLabels(){
        //Heading
        common.verifyStringOnPage("Av säkerhetsskäl...");

        //Text
        common.verifyStringOnPage("Du kommer behöva logga in igen med ditt nuvarande lösenord för att kunna skriva in det nya.");

        //Buttons
        common.verifyStringOnPage("ACCEPTERA");
    }
}
