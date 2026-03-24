package se.sunet.eduid.resetPassword;

import com.sun.mail.handlers.text_html;
import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class ExtraSecurity {
    private final Common common;
    private final TestData testData;
    private String continueWithoutExtraSecurityButtonID = "continue-without-security";

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
        if(testData.isAddExternalSecurityKey()){
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
        if(testData.isAddExternalSecurityKey()){
            common.waitUntilPageTitleContains("Log in | eduID");
        }
        else if(testData.isResetPassword()) {
            common.waitUntilPageTitleContains("Reset password | eduID");
        }

        //Extract page body for validation
        String pageBody = common.getPageBody();

        // Wait for the continue without extra security link
        common.explicitWaitClickableElementId(continueWithoutExtraSecurityButtonID);

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
        common.verifyStringById(continueWithoutExtraSecurityButtonID, "continue resetting password");

        //Switch to Swedish
        common.selectSwedish();

        //verify the labels - swedish
        //Extract page body for validation
        pageBody = common.getPageBody();

        // Wait for the continue without extra security link
        common.explicitWaitClickableElementId(continueWithoutExtraSecurityButtonID);

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

        common.verifyPageBodyContainsString(pageBody, "Kan du inte använda säkerhetsnyckel?");
        common.verifyPageBodyContainsString(pageBody, "VISA ANDRA ALTERNATIV");

        common.verifyPageBodyContainsString(pageBody, "Fortsätt utan ytterligare autentisering");
        common.verifyPageBodyContainsString(pageBody, "Din identitet kommer att behöva verifieras efter " +
                "att lösenordet har återställts. ");
        common.verifyStringById(continueWithoutExtraSecurityButtonID, "fortsätt återställa lösenordet");
    }

    public void selectMfaMethod(){
        //Continue without extra security
        if(testData.getMfaMethod().equalsIgnoreCase("no")) {
            Common.log.info("Selecting 'no extra security'");
            //common.click(common.findWebElementById(continueWithoutExtraSecurityButtonID));
            common.scrollToPageBottom();
            common.waitUntilClickable(By.id(continueWithoutExtraSecurityButtonID)).click();
        }

        //IF Freja eID should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("freja")) {
            common.selectSwedish();

            Common.log.info("Selecting Freja+");

            //Expand other options
            common.scrollToPageBottom();
            common.waitUntilClickable(By.xpath("//div[contains(text(), 'Visa andra alternativ')]")).click();
            common.timeoutMilliSeconds(500);

            //Click Freja button
            common.waitUntilClickable(By.xpath("//span[contains(text(), 'Freja+')]")).click();

            //Wait for ref IDP page
            common.waitUntilPageTitleContains("Sweden Connect Reference Identity Provider");
        }
        //If BankID should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("bankid")) {
            common.selectSwedish();

            Common.log.info("Selecting BankID");

            //Expand other options
            common.scrollToPageBottom();
            common.waitUntilClickable(By.xpath("//div[contains(text(), 'Visa andra alternativ')]")).click();
            common.timeoutMilliSeconds(500);

            common.waitUntilClickable(By.xpath("//span[contains(text(), 'BankID')]")).click();

            //Wait for bankID page
            common.waitUntilPageTitleContains("BankID");
        }
        //If eIDAS should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("eidas")) {
            common.selectSwedish();

            Common.log.info("Selecting eIDAS");

            //Expand other options at the re-auth page
            common.timeoutMilliSeconds(500);
            common.scrollToPageBottom();
            common.waitUntilClickable(By.xpath("//div[contains(text(), 'Visa andra alternativ')]")).click();

            common.waitUntilClickable(By.xpath("//span[contains(text(), 'eIDAS')]")).click();

            //Wait for eIDAS connector page
            common.waitUntilPageTitleContains("Foreign eID - Sweden Connect");
        }
        //If external security key should be used
        else if(testData.getMfaMethod().equalsIgnoreCase("securitykey")) {
                Common.log.info("Selecting external security key");
                common.findWebElementById("mfa-security-key").click();

                common.timeoutSeconds(8);
        }
    }
}
