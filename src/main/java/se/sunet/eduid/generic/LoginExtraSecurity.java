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
        if(testData.isIdentityConfirmed() &! testData.isAddSecurityKey()){
            common.verifyPageBodyContainsString(pageBody, "Logga in: med MFA");
            common.verifyPageBodyContainsString(pageBody, "Autentisera dig själv med ytterligare en metod " +
                    "för att vara säker på att bara du har tillgång till ditt eduID. Om du inte kan använda " +
                    "säkerhetsnyckeln, var vänlig välj annat alternativ nedan, t.ex. BankID eller Freja+.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Återautentisering: med MFA");
            common.verifyPageBodyContainsString(pageBody, "Autentisera dig för att fortsätta");
            if(testData.isDeleteButton()){
                common.verifyPageBodyContainsString(pageBody, "Om du vill avbryta utan att spara " +
                        "förändringen kan du återvända direkt till sidan Konto.");
            }
            else {
                common.verifyPageBodyContainsString(pageBody, "Om du vill avbryta utan att spara förändringen kan " +
                        "du återvända direkt till sidan Säkerhet.");
            }
            common.verifyPageBodyContainsString(pageBody,"Autentisera dig själv med ytterligare en metod för " +
                    "att vara säker på att bara du har tillgång till ditt eduID.");
        }


        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");

        common.timeoutMilliSeconds(500);
        common.verifyPageBodyContainsString(pageBody, "SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody, "T.ex. USB-säkerhetsnyckel, eller lösennyckel " +
                "(passkey) med enheten som du just nu använder.");
        common.verifyStringById("mfa-security-key", "ANVÄND MIN SÄKERHETSNYCKEL");

        //If identity is not confirmed or security key is missing
        if(!testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed())
            common.verifyPageBodyContainsString(pageBody, "Kräver ett bekräftat svenskt person- eller samordningsnummer.");

        common.selectEnglish();

        //English
        //Extract page body for validation
        pageBody = common.getPageBody();

        if(testData.isIdentityConfirmed() &! testData.isAddSecurityKey()){
            common.verifyPageBodyContainsString(pageBody, "Log in: with MFA");
            common.verifyPageBodyContainsString(pageBody, "Choose a second method to authenticate yourself," +
                    " ensuring only you can access your eduID. If you are unable to use the security key, please select" +
                    " another option below, such as BankID or Freja+.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Re-authentication: with MFA");
            common.verifyPageBodyContainsString(pageBody, "Authenticate to continue");
            if(testData.isDeleteButton()){
                common.verifyPageBodyContainsString(pageBody, "If you wish to cancel this process " +
                        "without affecting a change you can return straight to Account page.");
            }
            else {
                common.verifyPageBodyContainsString(pageBody, "If you wish to cancel this process without " +
                        "affecting a change you can return straight to Security page.");
            }
            common.verifyPageBodyContainsString(pageBody, "Choose a second method to authenticate yourself, " +
                    "ensuring only you can access your eduID.");
        }

        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");

        common.verifyPageBodyContainsString(pageBody, "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody, "E.g. USB Security Key, or passkey with the device you are currently using.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");

        //If identity is not confirmed or security key is missing
        if(!testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed())
            common.verifyPageBodyContainsString(pageBody, "Requires a confirmed Swedish national identity or coordination number.");
    }
}