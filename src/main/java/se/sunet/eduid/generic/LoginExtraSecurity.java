package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class LoginExtraSecurity {
    private final Common common;

    public LoginExtraSecurity(Common common){
        this.common = common;
    }

    public void runLoginExtraSecurity(){
        verifyPageTitle();
        verifyTexts();

        selectMfaMethod();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID login");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")){
            common.findWebElementByLinkText("Svenska").click();
        }
        common.timeoutMilliSeconds(500);
    }

    public void selectMfaMethod(){
        //selectMfa
        if(common.getMfaMethod().equalsIgnoreCase("securitykey")) {
            common.findWebElementById("mfa-security-key").click();
            Common.log.info("Selecting security key for mfa login");
        }
        else {
            common.findWebElementByXpath("//*[@id=\"mfa-freja\"]/span/span").click();
            Common.log.info("Selecting Freja eID+ for mfa login");
        }
    }

    private void verifyTexts(){
        //Swedish
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h2/span", "Logga in: Extra nivå av säkerhet");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p/span", "Du måste välja en andra " +
                "metod att autentisera dig själv. Detta hjälper att garantera att bara du kan komma åt ditt eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[1]/div/p/span", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"mfa-security-key\"]/span", "ANVÄND MIN SÄKERHETSNYCKEL");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[2]/div/p/span", "Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span/span", "Freja eID+");

        common.findWebElementByLinkText("English").click();

        //English
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h2/span", "Log in: Extra level of security");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p/span", "You need to choose a " +
                "second method to authenticate yourself. This helps guarantee that only you can access your eduID");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[1]/div/p/span", "Security key");
        common.verifyStringByXpath("//*[@id=\"mfa-security-key\"]/span", "USE MY SECURITY KEY");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[2]/div/p/span", "Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span/span", "Freja eID+");
    }
}