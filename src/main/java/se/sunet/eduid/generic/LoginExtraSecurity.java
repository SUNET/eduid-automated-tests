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
        common.explicitWaitPageTitle("Logga in | eduID");

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
            Common.log.info("Selecting security key for mfa login");
            common.findWebElementById("mfa-security-key").click();
        }
        else if(testData.getMfaMethod().equalsIgnoreCase("freja")){
            Common.log.info("Selecting Freja eID+ for mfa login");
            common.click(common.findWebElementById("mfa-freja"));
        }
    }

    private void verifyTexts(){
        //Swedish
        common.verifyStringOnPage("Logga in: Extra säkerhet");
        common.verifyStringOnPage("Du måste välja en ytterligare metod att autentisera dig själv med. " +
                "Detta säkerställer att bara du kan komma åt ditt eduID.");
        common.verifyStringOnPage("T.ex. USB säkerhetsnyckel, Touch ID eller Face ID.");
        common.verifyStringById("mfa-security-key", "ANVÄND MIN SÄKERHETSNYCKEL");
        common.verifyStringOnPage("Svenskt eID");
        common.verifyStringOnPage("ANVÄND MITT\n Freja+");
        common.verifyStringOnPage("ANVÄND MITT\n BankID");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Kom ihåg mig på den här enheten");

        common.selectEnglish();

        //English
        common.verifyStringOnPage("Log in: Extra security");
        common.verifyStringOnPage("You need to choose a second method to authenticate yourself. " +
                "This ensures that only you can access your eduID.");
        common.verifyStringOnPage("E.g. USB Security Key, Touch ID or Face ID.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");
        common.verifyStringOnPage("USE MY\n Freja+");
        common.verifyStringOnPage("USE MY\n BankID");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Remember me on this device");
    }
}