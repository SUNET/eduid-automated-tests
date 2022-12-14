package se.sunet.eduid.resetPassword;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.TestData;

import java.io.IOException;

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
        common.verifyPageTitle("Återställ Lösenord | eduID");
    }

    public void verifyLabels(){
        //verify status message - swedish
        common.verifyStatusMessage("En kod har skickats till din telefon.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p", "Skriv in koden som skickats till ");

        //Verify phone number OTP has been sent to
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p", testData.getOtpPhoneNumber());

        common.verifyStringByXpath("//*[@id=\"phone-wrapper\"]/div/label", "Kod");
        common.verifyStringById("resend-phone", "Skicka ny kod igen");

        //Verify Placholder
        common.verifyPlaceholder("Kod", "phone");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");

        //verify status message - english
        common.verifyStatusMessage("A code has been sent to your phone.");

        //Verify labels - swedish
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p", "Enter the code sent to ");
        common.verifyXpathContainsString("//*[@id=\"reset-pass-display\"]/p", testData.getOtpPhoneNumber());
        common.verifyStringByXpath("//*[@id=\"phone-wrapper\"]/div/label", "Code");
        common.verifyStringById("resend-phone", "Send a new code");

        //Verify Placholder
        common.verifyPlaceholder("Code", "phone");
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
            //Get the otp
            otp = common.getCodeInNewTab("https://idp.dev.eduid.se/services/reset-password/get-phone-code?eppn=" +testData.getEppn());
            //fetchOtp();

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

  /*  private void fetchOtp() {
        //Store current window handle
        common.switchToPopUpWindow();

        // Opens a new window and switches to new window, to continue with same session
        common.getWebDriver().switchTo().newWindow(WindowType.TAB);

        //Navigate to page with otp
        common.navigateToUrl("https://idp.dev.eduid.se/services/reset-password/get-phone-code?eppn=" +testData.getEppn());
        otp = common.findWebElementByXpath("/html/body").getText();

        Common.log.info("OTP: " +otp);
        common.timeoutMilliSeconds(500);

        //Close the tab or window
        common.getWebDriver().close();
        common.timeoutMilliSeconds(500);

        //Switch back to the old tab
        common.switchToDefaultWindow();
        common.timeoutMilliSeconds(500);
    }*/
}
