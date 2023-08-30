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
        pressResetPassword();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ Lösenord | eduID");
    }

    private void enterEmail(){
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());
    }

    public void pressResetPassword(){
        common.click(common.findWebElementById("reset-password-button"));

        //wait for the Send-again button on next page
        common.timeoutSeconds(1);
        common.explicitWaitClickableElementId("go-back-button");
    }

    private void verifyLabels(){
        //Heading
        common.verifyStringOnPage("Återställ lösenord");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "Ange din e-postadress registrerad till ditt konto.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress");

        //Verify placeholder
        common.verifyPlaceholder("namn@example.com", "email");

        //Buttons
        common.verifyStringById("reset-password-button", "SKICKA E-POST");
        common.verifyStringById("go-back-button", "TILLBAKA");

        //Switch to english
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");

        //Heading
        common.verifyStringOnPage("Reset password");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/p", "Enter the email address registered to your eduID account.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address");

        //Verify placeholder
        common.verifyPlaceholder("name@example.com", "email");

        //Buttons
        common.verifyStringById("reset-password-button", "SEND EMAIL");
        common.verifyStringById("go-back-button", "GO BACK");

        //Switch to swedish
        common.selectSwedish();
    }
}
