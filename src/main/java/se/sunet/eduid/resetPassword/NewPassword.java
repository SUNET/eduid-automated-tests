package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class NewPassword {
    private Common common;

    public NewPassword(Common common){
        this.common = common;
    }

    public void runNewPassword(){
        //TODO add pagetitle check when its present, missing at the moment 23/4-2020
        //verifyPageTitle();
        verifyLabels();
        acceptPwOrSetCustomPw();
    }


    private void verifyPageTitle() {
        common.verifyPageTitle("");
    }

    private void verifyLabels(){
        //verify the labels
        common.verifyStringByXpath("//div/div[2]/h2", "New password");
        common.verifyStringByXpath("//div/div[2]/p", "Please choose a new password for your eduID account. " +
                "A strong password has been generated for you. You can accept the generated password by clicking \"Change password\" " +
                "or you can opt to choose your own password by clicking \"Custom Password\".");

        common.verifyStringByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[1]", "Your generated password is:");
        common.verifyStringNotEmptyByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[2]/mark", "//div/div[2]/h2");
        common.verifyStringByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[3]", "Copy and save the above password somewhere safe and click \"Accept password\".");
    }

    private void acceptPwOrSetCustomPw(){
        if(!common.getUseRecommendedPw()){
            //Click on custom password tab
            common.click(common.findWebElementByXpath("//div/div[3]/ul/li[2]/a"));

            //Verify custom pw labels
            verifyCustomPwLabels();

            //Enter new password
            common.findWebElementById("custom-password").sendKeys(common.getPassword());
            common.findWebElementById("repeat-password").sendKeys(common.getPassword());

            //Click save new passwword button
            common.click(common.findWebElementByXpath("//*[@id=\"custom-pw\"]/div/form/div[2]/div/button"));
        }
        else{
            //Save the recommended password
            common.setPassword(common.findWebElementByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[2]/mark").getText());
            Common.log.info("Recommended password saved: " + common.getPassword());

            common.click(common.findWebElementByXpath("//*[@id=\"generated-pw\"]/div/form/div[2]/div/button"));
        }
    }

    private void verifyCustomPwLabels(){
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/p[1]/strong", "Choose a strong password");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/p[2]", "Some tips:");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[1]", "Use upper- and lowercase characters (preferably not in the beginning or end)");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[2]", "Add digits somewhere else than at the end of the password");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[3]", "Add special characters, such as @ $ \\ + _ %");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[4]", "Spaces are ignored");

        common.verifyStringByXpath("//*[@id=\"pwd-container\"]/label", "Password");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/form/div[1]/div[2]/label", "Repeat password");
    }
}
