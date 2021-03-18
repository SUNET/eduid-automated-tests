package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class VerifyPhoneNumber {
    private Common common;

    public VerifyPhoneNumber(Common common){
        this.common = common;
    }

    public void runVerifyPhoneNumber(){
        verifyPageTitle();
        verifyLabels();
        continueOrResendOTP();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ lösenord - Bekräfta telefonnummer");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//div/div[2]/h2", "Bekräfta telefonnummer");
        common.verifyStringByXpath("//div/div[2]/p", "Ange bekräftelsekoden som har skickats med SMS");
        common.verifyStringByXpath("//div/div[3]/form/div[3]/a", "Skicka en ny kod eller byt säkerhetsmetod");
    }

    private void continueOrResendOTP(){
        if(common.getResendOTP()) {
            common.setResendOTP(false);
            common.findWebElementByXpath("//div/div[3]/form/div[3]/a").click();

            //Testng cannot execute same class twice in *.xml file. Therefore call necessary methods separately
            //to test the re-send OTP functionality.
            ExtraSecurity security = new ExtraSecurity(common);
            common.setSendMobileOneTimePassword(true);
            security.runExtraSecurity();

            //To continue the flow and enter the OTP with magic-code
            common.setSendMobileOneTimePassword(false);
            continueOrResendOTP();
        }
        else {
            //Fetch the code
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/security/reset-password/get-phone-code?eppn=nunif-mados");

            String phoneCode = common.findWebElementByXpath("/html/body").getText();

            WebDriverManager.getWebDriver().navigate().back();

            common.explicitWaitVisibilityElementId("phone_code");
            common.findWebElementById("phone_code").clear();
            common.findWebElementById("phone_code").sendKeys(phoneCode);

            common.findWebElementByXpath("//div/div[3]/form/div[2]/div/button").click();
        }
    }
}
