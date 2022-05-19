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
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("English")) {
            common.selectEnglish();
        }

        //verify the labels - English
        common.timeoutSeconds(1);
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/h1", "Select an extra security option");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[1]", "A password reset " +
                "using an extra security option will keep your identity confirmed.");
        common.verifyStringOnPage("SEND SMS TO **********" + testData.getPhoneNumber().substring(10,12));
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[2]", "Already received sms?  enter code");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/h4", "Continue without extra " +
                "security option");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[3]", "Your account will " +
                "require confirmation after the password has been reset. Continue reset password");

        //Switch to Swedish
        common.selectSwedish();

        //verify the labels - swedish
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/h1", "Välj ett extra säkerhetsalternativ");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[1]", "Genom att " +
                "återställa lösenordet med ett extra säkerhetsalternativ så kommer kontot att förbli verifierat.");
        common.verifyStringOnPage("SKICKA SMS TILL **********" + testData.getPhoneNumber().substring(10,12));
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[2]", "Redan fått sms?  skriv in koden");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/h4", "Fortsätt utan extra " +
                "säkerhetsalternativ");
        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p[3]", "Ditt konto kommer " +
                "att behöva verifieras efter att lösenordet har återställts. Fortsätt återställa lösenordet");
    }

    private void sendOtp(){
        //Continue with extra security
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("yes")) {
            String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[1]/button").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementById("extra-security-phone"));
        }
        //Continue without extra security
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("no")) {
            common.click(common.findWebElementById("continue-without-security"));
        }
        //If multiple phone numbers are added select the in order that should be used
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("2")) {
            Common.log.info("Selected 2nd option: " +common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button").getText());
            String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[2]/button"));
        }
        else if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("3")) {//*[@id="reset-pass-display"]/p[2]
            Common.log.info("Selected 3rd option: " +common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button").getText());
            String otpText = common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button").getText();
            testData.setOtpPhoneNumber(otpText.substring(otpText.length()-2));
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/div[3]/button"));
        }
        //Already have OTP
        else {
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/p[2]/a"));
        }
    }
}
