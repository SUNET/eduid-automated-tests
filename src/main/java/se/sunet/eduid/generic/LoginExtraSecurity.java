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
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    private void verifyTexts(){
        //Extract page body for validation, wait for security key button
        common.explicitWaitClickableElementId("mfa-security-key");
        String pageBody = common.getPageBody();

        //Swedish
        common.verifyPageBodyContainsString(pageBody, "Logga in: Säkerhet");
        common.verifyPageBodyContainsString(pageBody,"Autentisera dig själv med ytterligare en metod för " +
                "att vara säker på att bara du har tillgång till ditt eduID.");

        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");

        common.timeoutMilliSeconds(500);
        common.verifyPageBodyContainsString(pageBody, "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody, "T.ex. USB-säkerhetsnyckel eller enheten som du just nu använder.");
        common.verifyStringById("mfa-security-key", "ANVÄND MIN SÄKERHETSNYCKEL");

        //If identity is not confirmed or security key is missing
        if(!testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed())
            common.verifyPageBodyContainsString(pageBody, "Kräver ett bekräftat svenskt person- eller samordningsnummer.");

        common.selectEnglish();

        //English
        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Log in: Security");
        common.verifyPageBodyContainsString(pageBody,"Choose a second method to authenticate yourself, ensuring only you can access your eduID.");

        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");

        common.verifyPageBodyContainsString(pageBody, "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody, "E.g. USB Security Key or the device you are currently using.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");

        //If identity is not confirmed or security key is missing
        if(!testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed())
            common.verifyPageBodyContainsString(pageBody, "Requires a confirmed Swedish national identity or coordination number.");
    }
}