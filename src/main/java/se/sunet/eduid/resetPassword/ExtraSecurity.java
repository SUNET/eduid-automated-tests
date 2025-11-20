package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ExtraSecurity {
    private final Common common;
    private final TestData testData;

    public ExtraSecurity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runExtraSecurity(){
        verifyPageTitle();
        verifyLabels();
        selectMfaMethod();
    }

    private void verifyPageTitle() {
        if(testData.isAddSecurityKey()){
            common.verifyPageTitle("Logga in | eduID");
        }
        else if(testData.isResetPassword()) {
            common.verifyPageTitle("Återställ lösenord | eduID");
        }
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("English")) {
            common.selectEnglish();
        }

        //verify the labels - English
        common.timeoutSeconds(1);
        if(testData.isAddSecurityKey()){
            common.verifyPageTitle("Log in | eduID");
        }
        else if(testData.isResetPassword()) {
            common.verifyPageTitle("Reset password | eduID");
        }

        //Extract page body for validation
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Reset password: Verification method");
        if(testData.isResetPassword()){
            common.verifyPageBodyContainsString(pageBody,
                    "Choose a second method to authenticate yourself, " +
                    "ensuring only you can access your eduID. If you are unable to use the security key, please select " +
                    "from other options below, such as BankID or Freja+.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Select an security option to maintain identity confirmation " +
                    "during the password reset process, or continue without security, with identity confirmation " +
                    "required after the password reset.");
        }

        common.verifyPageBodyContainsString(pageBody, "Having issues using a security key?");
        common.verifyPageBodyContainsString(pageBody, "SHOW OTHER OPTIONS");

        common.verifyPageBodyContainsString(pageBody, "Continue without additional authentication");
        common.verifyPageBodyContainsString(pageBody, "Your identity will require confirmation after the " +
                "password has been reset. ");
        common.verifyStringById("continue-without-security", "continue resetting password");

        //Switch to Swedish
        common.selectSwedish();

        //verify the labels - swedish
        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifieringsmetod");
        if(testData.isResetPassword()){
            common.verifyPageBodyContainsString(pageBody,
                    "Autentisera dig själv med ytterligare en metod " +
                    "för att vara säker på att bara du har tillgång till ditt eduID. Om du inte kan använda säkerhetsnyckeln, " +
                    "var vänlig välj annat alternativ nedan, t.ex. BankID eller Freja+.");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Välj ett säkerhetsalternativ för att bekräfta din identitet " +
                    "under lösenordsåterställningsprocessen, eller fortsätt utan säkerhet, med krav på " +
                    "identitetsbekräftelse efter lösenordsåterställningen.");
        }

        //common.verifyPageBodyContainsString(pageBody, "Fortsätt utan extra säkerhet");
        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");

        common.verifyPageBodyContainsString(pageBody, "Fortsätt utan ytterligare autentisering");
        common.verifyPageBodyContainsString(pageBody, "Din identitet kommer att behöva verifieras efter " +
                "att lösenordet har återställts. ");
        common.verifyStringById("continue-without-security", "fortsätt återställa lösenordet");
    }

    public void selectMfaMethod(){
        //Continue without extra security
        if(testData.getMfaMethod().equalsIgnoreCase("no")) {
            Common.log.info("Selecting 'no extra security'");
            common.click(common.findWebElementById("continue-without-security"));
        }

        //IF Freja eID should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("freja")) {
            common.selectSwedish();

            Common.log.info("Selecting Freja+");

            //Expand other options
            common.findWebElementByXpath("//div[contains(text(), 'Visa andra alternativ')]").click();
            common.timeoutMilliSeconds(500);

            //Click Freja button - Freja might be displayed out window display, using javascript click for this.
            common.click(common.findWebElementByXpath("//span[contains(text(), 'Freja+')]"));

            //Wait for ref IDP page
            common.explicitWaitPageTitle("Sweden Connect Reference Identity Provider");
        }
        //If BankID should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("bankid")) {
            common.selectSwedish();

            Common.log.info("Selecting BankID");

            //Expand other options
            common.findWebElementByXpath("//div[contains(text(), 'Visa andra alternativ')]").click();
            common.timeoutMilliSeconds(500);

            common.findWebElementByXpath("//span[contains(text(), 'BankID')]").click();

            //Wait for bankID page
            common.explicitWaitPageTitle("BankID");
        }
        //If eIDAS should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("eidas")) {
            common.selectSwedish();

            Common.log.info("Selecting eIDAS");

            //Expand other options
            common.findWebElementByXpath("//div[contains(text(), 'Visa andra alternativ')]").click();
            common.timeoutMilliSeconds(500);

            common.findWebElementByXpath("//span[contains(text(), 'EIDAS')]").click();

            //Wait for eIDAS page
            common.explicitWaitPageTitle("BankID");
        }
        //If external security key should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("securitykey")) {
                Common.log.info("Selecting external security key");
                common.findWebElementById("mfa-security-key").click();

                common.timeoutSeconds(8);
        }
    }
}
