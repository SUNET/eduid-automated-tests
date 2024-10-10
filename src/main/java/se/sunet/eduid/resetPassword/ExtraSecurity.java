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
        sendOtp();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord | eduID");;
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("English")) {
            common.selectEnglish();
        }

        //verify the labels - English
        common.timeoutSeconds(1);
        common.verifyPageTitle("Reset password | eduID");

        //Extract page body for validation
        String pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Reset password: Verification method");
        common.verifyPageBodyContainsString(pageBody, "Select an extra security option to maintain identity confirmation " +
                "during the password reset process, or continue without extra security, with identity confirmation " +
                "required after the password reset.");
//        if(!testData.getPhoneNumber().isEmpty())
//            common.verifyPageBodyContainsString(pageBody, "SEND SMS TO **********" + testData.getPhoneNumber().substring(10,12));

        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja") &!
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {

//            common.verifyPageBodyContainsString(pageBody, "Already received the code?  enter code");

        }
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja ") ||
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid ")){
            common.verifyPageBodyContainsString(pageBody, "USE MY FREJA+");
            common.verifyPageBodyContainsString(pageBody, "USE MY BANKID");
        }

        common.verifyPageBodyContainsString(pageBody, "Your identity will require confirmation after the password has been " +
                "reset. Continue resetting password");
        common.verifyPageBodyContainsString(pageBody, "Continue without extra security option");

        //Switch to Swedish
        common.selectSwedish();

        //verify the labels - swedish
        //Extract page body for validation
        pageBody = common.getPageBody();

        common.verifyPageBodyContainsString(pageBody, "Återställ lösenord: Verifieringsmetod");
        common.verifyPageBodyContainsString(pageBody, "Välj ett extra säkerhetsalternativ för att bekräfta din identitet " +
                "under lösenordsåterställningsprocessen, eller fortsätt utan extra säkerhet, med krav på " +
                "identitetsbekräftelse efter lösenordsåterställningen.");
//        if(!testData.getPhoneNumber().isEmpty())
//            common.verifyPageBodyContainsString(pageBody, "SKICKA SMS TILL **********" + testData.getPhoneNumber().substring(10,12));

        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja") &!
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {

//            common.verifyPageBodyContainsString(pageBody, "Redan fått en kod?  skriv in koden");
        }

        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja ") ||
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid ")){
            common.verifyPageBodyContainsString(pageBody, "ANVÄND MITT FREJA+");
            common.verifyPageBodyContainsString(pageBody, "ANVÄND MITT BANKID");
        }

        common.verifyPageBodyContainsString(pageBody, "Din identitet kommer att behöva verifieras efter att lösenordet har " +
                "återställts. Fortsätt återställa lösenordet");
        common.verifyPageBodyContainsString(pageBody, "Fortsätt utan extra säkerhetsalternativ");
    }

    private void sendOtp(){
        //Continue with extra security
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("yes")) {
            Common.log.info("Selecting Phone for password reset");

            String otpText = common.findWebElementById("extra-security-phone").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementById("extra-security-phone"));

            //Verify the status message
            common.verifyStatusMessage("En kod har skickats till din telefon.");

            //Verify that we can fill in the code and are at the right page
            common.explicitWaitClickableElementId("phone");
        }
        //Continue without extra security
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {
            Common.log.info("Selecting 'no extra security' for password reset");
            common.click(common.findWebElementById("continue-without-security"));
        }
        //If multiple phone numbers are added select the in order that should be used
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("2")) {
            Common.log.info("Selected 2nd option: " +common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button").getText());
            String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button"));
        }
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("3")) {
            Common.log.info("Selected 3rd option: " +common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button").getText());
            String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button"));
        }
        //IF Freja eID should be used
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja")) {
            Common.log.info("Selecting Freja+ for password reset");

            //Verify button text
            common.verifyStringById("extra-security-freja", "ANVÄND MITT FREJA+");

            //Click Freja button
            common.findWebElementById("extra-security-freja").click();
        }
        //IF BankID should be used
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid")) {
            Common.log.info("Selecting BankID for password reset");

            //Verify button text
            common.verifyStringById("extra-security-bankid", "ANVÄND MITT BANKID");

            //Click BankID button
            common.findWebElementById("extra-security-bankid").click();
        }
        //TODO below can probably be removed
        //Already have OTP
        else {
            Common.log.info("Selecting Already have OTP for password reset");
            common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/p[2]/a").click();

            common.explicitWaitClickableElementId("phone");

            common.verifyStringOnPage("Skriv in koden som skickats till ");
            common.verifyStringOnPage("Skicka ny kod igen");
        }
    }
}
