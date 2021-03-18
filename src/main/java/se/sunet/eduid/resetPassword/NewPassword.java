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
        common.verifyStringByXpath("//div/div[2]/h2", "Nytt lösenord");
        common.verifyStringByXpath("//div/div[2]/p", "Ändra lösenord för ditt eduID-konto. " +
                "Ett säkert lösenord har genererats åt dig. Du kan acceptera det med knappen \"Acceptera lösenord\" " +
                "eller så kan du välja att använda ett eget lösenord om du klickar på fliken \"Eget lösenord\".");

        common.verifyStringByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[1]", "Ditt genererade lösenord är:");
        common.verifyStringNotEmptyByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[2]/mark", "//div/div[2]/h2");
        common.verifyStringByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[3]",
                "Kopiera och spara det ovanstående lösenordet på en säker plats och klicka \"Acceptera lösenord\".");
    }

    private void acceptPwOrSetCustomPw(){
        if(!common.getUseRecommendedPw()){
            //Click on custom password tab
            common.findWebElementByXpath("//div/div[3]/ul/li[2]/a").click();

            //Verify custom pw labels
            verifyCustomPwLabels();

            //Enter new password
            common.findWebElementById("custom-password").clear();
            common.findWebElementById("custom-password").sendKeys(common.getPassword());
            common.findWebElementById("repeat-password").clear();
            common.findWebElementById("repeat-password").sendKeys(common.getPassword());

            //Click save new passwword button
            common.findWebElementByXpath("//*[@id=\"custom-pw\"]/div/form/div[2]/div/button").click();
        }
        else{
            //Save the recommended password
            common.setPassword(common.findWebElementByXpath("//*[@id=\"generated-pw\"]/div/form/div[1]/div/p[2]/mark").getText());
            Common.log.info("Recommended password saved: " + common.getPassword());

            common.findWebElementByXpath("//*[@id=\"generated-pw\"]/div/form/div[2]/div/button").click();
        }
    }

    private void verifyCustomPwLabels(){
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/p[1]/strong", "Välj ett säkert lösenord.");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/p[2]", "Några tips:");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[1]", "Använd stora och små bokstäver (inte bara första bokstaven)");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[2]", "Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[3]", "Använd specialtecken som @ $ \\ + _ %");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/ul/li[4]", "Blanksteg (mellanslag) ignoreras");

        common.verifyStringByXpath("//*[@id=\"pwd-container\"]/label", "Lösenord");
        common.verifyStringByXpath("//*[@id=\"custom-pw\"]/div/form/div[1]/div[2]/label", "Repetera lösenord");
    }
}
