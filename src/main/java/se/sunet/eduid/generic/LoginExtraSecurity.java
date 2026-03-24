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
        common.timeoutSeconds(2);
        String pageBody = common.getPageBody();

        Common.log.info("Verify text and labels in Swedish");

        //Swedish
        if(testData.isIdentityConfirmed() &! testData.isAddExternalSecurityKey()){
            //Text not visible when logging in with passkey
            if(!testData.isAddInternalPassKey()) {
                common.verifyPageBodyContainsString(pageBody, "Logga in: med MFA");
            }
            common.verifyPageBodyContainsString(pageBody, "Autentisera dig själv med ytterligare en metod " +
                    "för att vara säker på att bara du har tillgång till ditt eduID. Om du inte kan använda " +
                    "säkerhetsnyckeln, var vänlig välj annat alternativ nedan, t.ex. BankID, Freja+, eIDAS eller Freja eID.");
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
            System.out.println(testData.isAddExternalSecurityKey() +" " + testData.isAddInternalPassKey() +" " + testData.isIdentityConfirmed());
            //This text is only visible when security key is added
            //if((testData.isAddExternalSecurityKey() || testData.isAddInternalPassKey()) &!testData.isIdentityConfirmed()) {
            //Since test case 16 removes the security key the extra text will not be present
            if(!testData.isIdentityConfirmed() &!testData.getTestClassName().contains("TC_16")) {
                common.verifyPageBodyContainsString(pageBody, "Det rekommenderas starkt att lägga till mer än " +
                        "en säkerhetsnyckel eller passkey/lösennyckel för att försäkra dig om att du kan logga in även om en förloras.");
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
            common.verifyPageBodyContainsString(pageBody, "Kräver att du har verifierat din identitet i " +
                    "eduID med svenskt person- eller samordningsnummer, eIDAS eller Freja eID.");

        common.selectEnglish();
        Common.log.info("Verify text and labels in English");

        //English
        //Extract page body for validation
        pageBody = common.getPageBody();

        if(testData.isIdentityConfirmed() &! testData.isAddExternalSecurityKey()){
            //Text not visible when logging in with passkey
            if(!testData.isAddInternalPassKey()) {
                common.verifyPageBodyContainsString(pageBody, "Log in: with MFA");
            }
            common.verifyPageBodyContainsString(pageBody, "Choose a second method to authenticate yourself," +
                    " ensuring only you can access your eduID. If you are unable to use the security key, please select" +
                    " other options below, such as BankID, Freja+, eIDAS or Freja eID.");
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

            //This text is only visible when security key is added
            //TODO uncomment when bug is fixed and text is visible again
/*                        //Since test case 16 removes the security key the extra text will not be present
            if(!testData.isIdentityConfirmed() &! testData.getTestClassName().contains("TC_16")) {
                common.verifyPageBodyContainsString(pageBody, "It is strongly recommended to add more than" +
                        "one security key or passkey to ensure you can still sign in to your account if one is lost.");
            }*/
        }

        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");

        common.verifyPageBodyContainsString(pageBody, "SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody, "E.g. USB Security Key, or passkey with the device you are currently using.");
        common.verifyStringById("mfa-security-key", "USE MY SECURITY KEY");

        //If identity is not confirmed or security key is missing
        if(!testData.isVerifySecurityKeyByFreja() && !testData.isIdentityConfirmed())
            common.verifyPageBodyContainsString(pageBody, "Requires that you have verified your identity " +
                    "in eduID with a Swedish personal identity number or coordination number, eIDAS or Freja eID.");
        }
    }
}