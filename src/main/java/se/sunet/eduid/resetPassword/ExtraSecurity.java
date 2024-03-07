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
        common.verifyPageTitle("Återställ Lösenord | eduID");;
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("English")) {
            common.selectEnglish();
        }

        //verify the labels - English
        common.timeoutSeconds(1);
        common.verifyPageTitle("Reset Password | eduID");

        common.verifyStringOnPage("Select an extra security option");
        common.verifyStringOnPage("A password reset using an extra security option will keep your identity confirmed.");
        if(!testData.getPhoneNumber().isEmpty())
            common.verifyStringOnPage("SEND SMS TO **********" + testData.getPhoneNumber().substring(10,12));

        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja") &!
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {

            common.verifyStringOnPage("Already received the code?  enter code");

        }
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja ") ||
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid ")){
            common.verifyStringOnPage("USE MY FREJA+");
            common.verifyStringOnPage("USE MY BANKID");
        }

        common.verifyStringOnPage("Continue without extra security option");
        common.verifyStringOnPage("Your identity will require confirmation after the password has been " +
                "reset. Continue resetting password");

        //Switch to Swedish
        common.selectSwedish();

        //verify the labels - swedish
        common.verifyStringOnPage("Välj ett extra säkerhetsalternativ");
        common.verifyStringOnPage("Genom att återställa lösenordet med ett extra säkerhetsalternativ " +
                "kommer kontot att förbli verifierat.");
        if(!testData.getPhoneNumber().isEmpty())
            common.verifyStringOnPage("SKICKA SMS TILL **********" + testData.getPhoneNumber().substring(10,12));

        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja") &!
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {

            common.verifyStringOnPage("Redan fått en kod?  skriv in koden");
        }

        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("freja ") ||
                testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid ")){
            common.verifyStringOnPage("ANVÄND MITT FREJA+");
            common.verifyStringOnPage("ANVÄND MITT BANKID");
        }

        common.verifyStringOnPage("Fortsätt utan extra säkerhetsalternativ");
        common.verifyStringOnPage("Din identitet kommer att behöva verifieras efter att lösenordet har " +
                "återställts. Fortsätt återställa lösenordet");
    }

    private void sendOtp(){
        //Continue with extra security
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("yes")) {
            //String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[1]/button").getText();
            String otpText = common.findWebElementById("extra-security-phone").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementById("extra-security-phone"));
            Common.log.info("Selecting Phone for password reset");
        }
        //Continue without extra security
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {
            common.click(common.findWebElementById("continue-without-security"));
            Common.log.info("Selecting 'no extra security' for password reset");
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
            //Verify button text
            common.verifyStringById("extra-security-freja", "ANVÄND MITT FREJA+");
            Common.log.info("Selecting Freja+ for password reset");

            //Click Freja button
            common.findWebElementById("extra-security-freja").click();
        }
        //IF BankID should be used
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("bankid")) {
            //Verify button text
            common.verifyStringById("extra-security-bankid", "ANVÄND MITT BANKID");
            Common.log.info("Selecting BankID for password reset");

            //Click BankID button
            common.findWebElementById("extra-security-bankid").click();
        }
        //Already have OTP
        else {
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/p[2]/a"));

            Common.log.info("Already have OTP for password reset");
        }
    }
}
