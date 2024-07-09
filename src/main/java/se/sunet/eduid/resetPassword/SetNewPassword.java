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
        common.verifyStrings("xxxx xxxx xxxx", common.findWebElementById("newPassword").getAttribute("placeholder"));

        //verify the labels in Swedish
        common.verifyStringOnPage("Återställ lösenord: Ange nytt lösenord");
        common.verifyStringOnPage("Ett starkt lösenord har genererats åt dig. För att fortsätta måste " +
                "du kopiera lösenordet till fältet Repetera ditt nya lösenord och klicka på Acceptera lösenord och " +
                "spara det för framtida bruk. Obs: mellanrummen i lösenordet är för att göra det mer läsbart och tas " +
                "automatiskt bort vid inmatning. ");

        common.verifyStringByXpath("//*[@id=\"content\"]/div/label", "Nytt lösenord");
        common.verifyStringByXpath("//*[@id=\"newPassword-wrapper\"]/div/label", "Repetera ditt nya lösenord");

        //Switch to Swedish
        common.selectEnglish();

        common.verifyPageTitle("Reset Password | eduID");

        //verify the labels
        common.verifyStringOnPage("Reset Password: Set new password");
        common.verifyStringOnPage("A strong password has been generated for you. To proceed you will " +
                "need to copy the password in to the Repeat new password field and click Accept Password and save it " +
                "for future use. Note: spaces in the generated password are there for legibility and will be removed " +
                "automatically if entered.");

        common.verifyStringByXpath("//*[@id=\"content\"]/div/label", "New password");
        common.verifyStringByXpath("//*[@id=\"newPassword-wrapper\"]/div/label", "Repeat new password");

        Assert.assertTrue(common.findWebElementById("copy-new-password").getText().isEmpty(), "New password field is empty");

        //Switch to Swedish
        common.selectSwedish();
    }

    private void copyPasteNewPassword(){
        String newPassword = common.getAttributeById("copy-new-password");

        //store the new password
        testData.setPassword(newPassword);
        Common.log.info("New password saved: " + testData.getPassword());

        common.findWebElementById("newPassword").clear();
        common.findWebElementById("newPassword").sendKeys(newPassword);
    }

    private void clickButton(){
        common.verifyStringOnPage("ACCEPTERA LÖSENORD");
        common.click(common.findWebElementById("new-password-button"));

        //Wait for next page
        if(!testData.getSendMobileOneTimePassword().equalsIgnoreCase("already"))
            common.explicitWaitClickableElementId("reset-password-finished");

        /* Abort...
        common.verifyStringOnPage("GÅ TILLBAKA");
        common.findWebElementByXpath("//div/section[2]/div[2]/form/div[2]/button[1]/span").click();
        */
    }
}
