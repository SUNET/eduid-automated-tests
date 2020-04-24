package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class StartPage {
    private Common common;

    public StartPage(Common common){
        this.common = common;
    }

    public void runStartPage(boolean registerAccount){
        verifyPageTitle();

        if(registerAccount)
            registerAccount();
        else
            signIn();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void signIn(){
        common.click(common.findWebElementByXpath("//*[@id=\"login\"]/a"));
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[2]/a"));
    }
}
