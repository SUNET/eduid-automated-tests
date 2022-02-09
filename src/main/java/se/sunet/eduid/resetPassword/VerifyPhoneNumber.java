package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class VerifyPhoneNumber {
    private final Common common;
    private String otp;
    private final TestData testData;

    public VerifyPhoneNumber(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runVerifyPhoneNumber(){
        verifyPageTitle();

        //Skip verification of labels when adding a faulty otp, only check status message
        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("already"))
            verifyLabels();

        enterOtp();
        clickButton();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    public void verifyLabels(){
        //verify status message - swedish
        common.verifyStatusMessage("En engångskod har skickats till din telefon.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//div/section[2]/div[2]/div/p", "Skriv in koden som skickats till");

        //Verify phone number OTP has been sent to
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p/b", testData.getOtpPhoneNumber());

        common.verifyStringByXpath("//div/section[2]/div[2]/div/form/div/div/label", "Bekräftelsekod");
        common.verifyStringByXpath("//*[@id=\"resend-phone\"]", "Skicka bekräftelsekoden igen");

        //Switch to english
        common.selectEnglish();

        //verify status message - english
        //common.verifyStatusMessage("One time verification code has been sent to your phone.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//div/section[2]/div[2]/div/p", "Enter the code sent to ");
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p/b", testData.getOtpPhoneNumber());
        common.verifyStringByXpath("//div/section[2]/div[2]/div/form/div/div/label", "Confirmation code");
        common.verifyStringByXpath("//*[@id=\"resend-phone\"]", "Send a new confirmation code");
    }

    private void enterOtp(){
        //Shall we resent the otp?
        if(testData.isResendOTP()){
            //Close status message
            common.closeStatusMessage();

            //Wait for countdown timer to be displayed
            common.timeoutSeconds(4);

            //Timeout for the resend link to be enabled, in this case we can not use the wait for visibility in findbyId
            Common.log.info("Waiting (5min) for resent OTP link to be enabled");
            while(common.getWebDriver().findElement(By.id("count-down-time-phone")).isDisplayed()){
                common.timeoutSeconds(5);
            }

            //Click re-send otp
            common.click(common.findWebElementById("resend-phone"));

            common.verifyStatusMessage("One time verification code has been sent to your phone.");
            common.timeoutSeconds(2);
        }
        //Fetch the OTP, but not if we set that we already have the otp code, then set it to "incorrect1"
        if(testData.getSendMobileOneTimePassword().equalsIgnoreCase("already")) {
            otp = "incorrect1";
        }
        else {
            fetchOtp();

            //Click on Already received sms - enter code
            common.click(common.findWebElementByXpath("//*[@id=\"reset-pass-display\"]/p[2]/a"));

            common.explicitWaitClickableElementId("phone");

            //Switch to swedish if we need to
            if (common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
                common.selectSwedish();
            }
        }
        //Enter otp
        common.findWebElementById("phone").clear();
        common.findWebElementById("phone").sendKeys(otp);
    }

    private void clickButton(){
        common.explicitWaitClickableElementId("save-phone-button");
        common.click(common.findWebElementById("save-phone-button"));

        //Wait for next page
        common.explicitWaitClickableElementId("copy-new-password");
    }

    private void fetchOtp(){
        //Fetch the code
        common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-phone-code?eppn=" +testData.getEppn());
        otp = common.findWebElementByXpath("/html/body").getText();

        Common.log.info("OTP: " +otp);

        //Back
        common.navigateToUrl("https://www.dev.eduid.se/reset-password/phone-code-sent/" +testData.getEmailCode());

        common.timeoutSeconds(2);
    }
}
