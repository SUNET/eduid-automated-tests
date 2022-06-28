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
            common.selectSwedish();
        }
        common.timeoutMilliSeconds(500);
    }

    public void selectMfaMethod(){
        common.timeoutSeconds(2);

        //selectMfa method
        if(testData.getMfaMethod().equalsIgnoreCase("securitykey")) {
            common.findWebElementById("mfa-security-key").click();
            Common.log.info("Selecting security key for mfa login");
        }
        else if(testData.getMfaMethod().equalsIgnoreCase("freja")){
            common.click(common.findWebElementById("mfa-freja"));
            Common.log.info("Selecting Freja eID+ for mfa login");
        }
    }

    private void verifyTexts(){
        //Swedish
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Logga in: Extra nivå av säkerhet");
//        common.verifyStringByXpath("//*[@id=\"content\"]/div/div[1]/p", "Använd eduID för att komma " +
//                "åt SWAMID Entity Category Release Check");
        common.verifyStringOnPage("Du måste välja en andra " +
                "metod att autentisera dig själv. Detta hjälper att garantera att bara du kan komma åt ditt eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div[2]/div[1]/div/p", "T.ex. USB säkerhetsnyckel, Touch ID eller Face ID.");
        common.verifyStringById("mfa-security-key", "ANVÄND MIN SÄKERHETSNYCKEL");
        common.verifyStringOnPage("Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span", "Freja eID+");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Kom ihåg mig på den här enheten");

        common.selectEnglish();

        //English
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Log in: Extra level of security");
//        common.verifyStringByXpath("//*[@id=\"content\"]/div/div[1]/p", "Use eduID to access " +
//                "SWAMID Entity Category Release Check");
        common.verifyStringOnPage("You need to choose a " +
                "second method to authenticate yourself. This helps guarantee that only you can access your eduID.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div[2]/div[1]/div/p", "E.g. USB Security Key, Touch ID or Face ID.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");
        common.verifyStringOnPage("Freja eID+");
        common.verifyStringByXpath("//*[@id=\"mfa-freja\"]/span", "Freja eID+");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Remember me on this device");
    }
}