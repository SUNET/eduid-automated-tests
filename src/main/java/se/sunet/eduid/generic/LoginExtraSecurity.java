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

            common.timeoutSeconds(2);
        }
        else if(testData.getMfaMethod().equalsIgnoreCase("freja")){
            Common.log.info("Selecting Freja eID+ for mfa login");
            common.click(common.findWebElementById("mfa-freja"));
        }
    }

    private void verifyTexts(){
        //Extract page body for validation
        common.timeoutMilliSeconds(300);
        String pageBody = common.getPageBody();

        //Swedish
        common.verifyPageBodyContainsString(pageBody, "Logga in: Extra säkerhet");
        common.verifyPageBodyContainsString(pageBody, "Du måste välja en ytterligare metod att autentisera dig själv med. " +
                "Detta säkerställer att bara du kan komma åt ditt eduID.");
        common.verifyPageBodyContainsString(pageBody, "T.ex. USB säkerhetsnyckel eller enheten som du just nu använder.");
        common.verifyStringById("mfa-security-key", "ANVÄND MIN SÄKERHETSNYCKEL");

        common.verifyPageBodyContainsString(pageBody, "Svenskt eID");
        if(!testData.isVerifySecurityKey())
            common.verifyPageBodyContainsString(pageBody, "Kräver ett bekräftat svenskt personnummer.");
        common.verifyPageBodyContainsString(pageBody, "ANVÄND MITT\n Freja+");
        common.verifyPageBodyContainsString(pageBody, "ANVÄND MITT\n BankID");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Kom ihåg mig på den här enheten");

        common.selectEnglish();

        //English
        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Log in: Extra security");
        common.verifyPageBodyContainsString(pageBody, "You need to choose a second method to authenticate yourself. " +
                "This ensures that only you can access your eduID.");
        common.verifyPageBodyContainsString(pageBody, "E.g. USB Security Key or the device you are currently using.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");

        common.verifyPageBodyContainsString(pageBody, "Swedish eID");
        if(!testData.isVerifySecurityKey())
            common.verifyPageBodyContainsString(pageBody, "Requires a confirmed Swedish national identity number.");
        common.verifyPageBodyContainsString(pageBody, "USE MY\n Freja+");
        common.verifyPageBodyContainsString(pageBody, "USE MY\n BankID");

        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Remember me on this device");
    }
}