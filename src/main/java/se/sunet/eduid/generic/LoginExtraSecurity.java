package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class LoginExtraSecurity {
    private final Common common;
    private final TestData testData;

    public LoginExtraSecurity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
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
        if(testData.getMfaMethod().equalsIgnoreCase("securitykey")) {
            common.findWebElementById("mfa-security-key").click();
            Common.log.info("Selecting security key for mfa login");
        }
        else {
            common.findWebElementByXpath("//*[@id=\"mfa-freja\"]/span").click();
            Common.log.info("Selecting Freja eID+ for mfa login");
        }
    }

    private void verifyTexts(){
        //Swedish
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h2", "Logga in: Extra nivå av säkerhet");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "Du måste välja en andra " +
                "metod att autentisera dig själv. Detta hjälper att garantera att bara du kan komma åt ditt eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[1]/div/p", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"mfa-security-key\"]", "ANVÄND MIN SÄKERHETSNYCKEL");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[2]/div/p", "Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span", "Freja eID+");

        common.findWebElementByLinkText("English").click();

        //English
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h2", "Log in: Extra level of security");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/p", "You need to choose a " +
                "second method to authenticate yourself. This helps guarantee that only you can access your eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[1]/div/p", "Security key");
        common.verifyStringByXpath("//*[@id=\"mfa-security-key\"]", "USE MY SECURITY KEY");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/div[2]/div/p", "Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span", "Freja eID+");
    }
}