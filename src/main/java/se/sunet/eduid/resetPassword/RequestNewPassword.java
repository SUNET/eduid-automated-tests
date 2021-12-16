package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class RequestNewPassword {
    private final Common common;
    private final TestData testData;

    public RequestNewPassword(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runRequestNewPassword(){
        verifyPageTitle();
        verifyLabels();
        enterEmail();
        pressRestPassword();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void enterEmail(){
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());
    }

    private void pressRestPassword(){
        common.findWebElementById("reset-password-button").click();

        //wait the texts on next page
        common.explicitWaitVisibilityElement("//*[@id=\"reset-pass-display\"]/p");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//*[@id=\"content\"]/p", "Ange din e-postadress registrerad till ditt konto");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress\n*");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "en giltig e-postadress");

        //Verify placeholder
        common.verifyStrings("namn@example.com", common.findWebElementByXpath("//*[@id=\"email\"]").getAttribute("placeholder"));

        //Switch to english
        common.findWebElementByLinkText("English").click();
        common.verifyStringByXpath("//*[@id=\"content\"]/p", "Enter your email address registered to your account");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address\n*");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/span", "a valid email address");

        //Switch to english
        common.findWebElementByLinkText("Svenska").click();
    }
}
