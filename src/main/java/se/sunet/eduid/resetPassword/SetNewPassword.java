package se.sunet.eduid.resetPassword;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class SetNewPassword {
    private final Common common;
    private final TestData testData;

    public SetNewPassword(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runNewPassword(){
        verifyPageTitle();
        verifyLabels();
        copyPasteNewPassword();
        clickButton();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Återställ Lösenord | eduID");
        common.verifyPageTitle("Återställ Lösenord | eduID");
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.selectSwedish();
        }
        //Verify placeholder
        common.verifyStrings("xxxx xxxx xxxx", common.findWebElementById("new-password").getAttribute("placeholder"));

        //verify the labels in Swedish
        common.verifyStringOnPage("Skapa ditt nya lösenord");
        common.verifyStringOnPage("Ett starkt lösenord har " +
                "genererats åt dig. För att fortsätta behöver du skriva in det igen.");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/label", "Nytt lösenord");
        common.verifyStringByXpath("//*[@id=\"new-password-wrapper\"]/div/label", "Repetera ditt nya lösenord");

        //Switch to Swedish
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");

        //verify the labels
        common.verifyStringOnPage("Set your new password");
        common.verifyStringOnPage("A strong password has been " +
                "generated for you. To proceed you will need to repeat the password below .");

        common.verifyStringByXpath("//*[@id=\"reset-pass-display\"]/div/label", "New password");
        common.verifyStringByXpath("//*[@id=\"new-password-wrapper\"]/div/label", "Repeat new password");

        Assert.assertTrue(common.findWebElementById("copy-new-password").getText().isEmpty(), "New password field is empty");

        //Switch to Swedish
        common.selectSwedish();
    }

    private void copyPasteNewPassword(){
        String newPassword = common.getAttributeById("copy-new-password");

        //store the new password
        testData.setPassword(newPassword);
        Common.log.info("New password saved: " + testData.getPassword());

        common.findWebElementById("new-password").clear();
        common.findWebElementById("new-password").sendKeys(newPassword);
    }

    private void clickButton(){
        common.verifyStringOnPage("ACCEPTERA LÖSENORD");
        common.click(common.findWebElementById("new-password-button"));

        //Wait for next page
        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("already"))
            common.explicitWaitClickableElementId("return-login");

        /* Abort...
        common.verifyStringOnPage("GÅ TILLBAKA");
        common.findWebElementByXpath("//div/section[2]/div[2]/form/div[2]/button[1]/span").click();
        */
    }
}
