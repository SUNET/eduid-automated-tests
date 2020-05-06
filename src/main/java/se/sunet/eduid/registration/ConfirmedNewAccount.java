package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;

public class ConfirmedNewAccount {
    private Common common;

    public ConfirmedNewAccount(Common common){
        this.common = common;
    }

    public void runConfirmedNewAccount(){
        verifyPageTitle();
        verifyLabels();
        clickGoToMyEduID();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void verifyLabels(){
        //Headings
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");

        //Details
        common.verifyStringByXpath("//*[@id=\"register-container\"]/div/h3/span", "You have completed the registration for eduID.");
        common.verifyStringByXpath("//*[@id=\"register-container\"]/div/p/span", "These are your login details for eduID.");

        //Email
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[1]", "EMAIL");
        common.verifyStringByXpath("//*[@id=\"user-email\"]", common.getUsername().toLowerCase());

        //Password
        common.verifyStringByXpath("//*[@id=\"email-display\"]/label[2]", "PASSWORD");
        common.setRecommendedPw(common.findWebElementById("user-password").getText());
    }

    private void clickGoToMyEduID(){
        common.click(common.findWebElementByXpath("//*[@id=\"gotit-button\"]/span"));
    }
}
