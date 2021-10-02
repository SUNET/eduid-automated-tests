package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class VerifyPhoneNumber {
    private Common common;
    private String otp;

    public VerifyPhoneNumber(Common common){
        this.common = common;
    }

    //TODO update class with resend otp e.g.

    public void runVerifyPhoneNumber(){
        verifyPageTitle();
        verifyLabels();
        enterOtp();
        clickButton();
    }


    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    public void verifyLabels(){
        //verify status message - swedish
        common.verifyStatusMessage("En engångsverifieringskod har skickats till din telefon.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//div/section[2]/div[2]/div/p/span", "Skriv in koden som skickats till");
        common.verifyStringByXpath("//div/section[2]/div[2]/div/form/div/div/label/span", "Bekräftelsekod");
        common.verifyStringByXpath("//*[@id=\"resend-phone\"]", "Skicka bekräftelsekoden igen");

        //Switch to english
        common.findWebElementByLinkText("English").click();

        //verify status message - english
        common.verifyStatusMessage("One time verification code has been sent to your phone.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//div/section[2]/div[2]/div/p/span", "Enter the code sent to ");
        common.verifyStringByXpath("//div/section[2]/div[2]/div/form/div/div/label/span", "Confirmation code");
        common.verifyStringByXpath("//*[@id=\"resend-phone\"]", "Send a new confirmation code");
    }

    private void enterOtp(){
        //Shall we resent the otp?
        if(common.getResendOTP()){
            //Close status message
            common.closeStatusMessage();

            //Wait for countdown timer to be displayed
            common.timeoutSeconds(4);

            //Timeout for the resend link to be enabled
            Common.log.info("Waiting (5min) for resent OTP link to be enabled");
            while(common.findWebElementById("count-down-time-phone").isDisplayed()){
                common.timeoutSeconds(5);
            }

            //Click re-send otp
            common.findWebElementById("resend-phone").click();

            common.verifyStatusMessage("One time verification code has been sent to your phone.");
            common.timeoutSeconds(2);
        }
        //Fetch the OTP
        fetchOtp();

        //common.timeoutMilliSeconds(400);
        common.explicitWaitClickableElement("//div/section[2]/div[2]/div/p[2]/a/span");

        //Click on Already have otp
        common.findWebElementByXpath("//div/section[2]/div[2]/div/p[2]/a/span").click();
        common.explicitWaitClickableElementId("phone");

        //Switch to swedish if we need to
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.findWebElementByLinkText("Svenska").click();
        }

        //Enter otp
        common.findWebElementById("phone").clear();
        common.findWebElementById("phone").sendKeys(otp);
    }

    private void clickButton(){
        common.explicitWaitClickableElementId("save-phone-button");
        common.findWebElementById("save-phone-button").click();

        //Wait for next page
        common.explicitWaitClickableElementId("copy-new-password");
    }

    private void fetchOtp(){
        //Fetch the code
        common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-phone-code?eppn=" +common.getEppn());
        otp = common.findWebElementByXpath("/html/body").getText();

        Common.log.info("OTP: " +otp);

        //Back
        WebDriverManager.getWebDriver().navigate().to("https://www.dev.eduid.se/reset-password/phone-code-sent/" +common.getEmailCode());
    }
}
