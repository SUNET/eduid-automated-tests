package se.sunet.eduid.resetPassword;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;

public class SetNewPassword {
    private final Common common;

    public SetNewPassword(Common common){
        this.common = common;
    }

    public void runNewPassword(){
        verifyPageTitle();
        verifyLabels();
        copyPasteNewPassword();
        clickButton();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID login");
    }

    private void verifyLabels(){
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.findWebElementByLinkText("Svenska").click();
        }

        //verify the labels in Swedish
        common.verifyStringByXpath("//div/section[2]/div[2]/p[1]/span", "Skapa ditt nya lösenord");
        common.verifyStringByXpath("//div/section[2]/div[2]/p[2]/span", "Ett säkert lösenord " +
                "har genererats till dig. För att komma vidare behöver du kopiera det till Repetera ditt nya lösenord " +
                "och klicka Acceptera lösenord.");

        common.verifyStringByXpath("//div/section[2]/div[2]/div/label/span", "Nytt lösenord");
        common.verifyStringByXpath("//div/section[2]/div[2]/form/div[1]/div/label/span[1]", "Repetera ditt nya lösenord");


        //Switch to Swedish
        common.findWebElementByLinkText("English").click();


        //verify the labels
        common.verifyStringByXpath("//div/section[2]/div[2]/p[1]/span", "Set your new password");
        common.verifyStringByXpath("//div/section[2]/div[2]/p[2]/span", "A strong password has " +
                "been generated for you. To proceed you will need to copy the password in to the Repeat new password " +
                "field and click Accept Password.");

        common.verifyStringByXpath("//div/section[2]/div[2]/div/label/span", "New password");
        common.verifyStringByXpath("//div/section[2]/div[2]/form/div[1]/div/label/span[1]", "Repeat new password");

        Assert.assertTrue(common.findWebElementById("copy-new-password").getText().isEmpty(), "New password field is empty");

        //Switch to Swedish
        common.findWebElementByLinkText("Svenska").click();
    }

    private void copyPasteNewPassword(){
        String newPassword = common.getAttributeById("copy-new-password");

        //store the new password
        common.setPassword(newPassword);
        Common.log.info("New password saved: " + common.getPassword());

        common.findWebElementById("new-password").clear();
        common.findWebElementById("new-password").sendKeys(newPassword);
    }

    private void clickButton(){
        common.verifyStringOnPage("ACCEPTERA LÖSENORD");
        common.findWebElementById("new-password-button").click();

        //Wait for next page
        if(!common.getSendMobileOneTimePassword().equalsIgnoreCase("already"))
            common.explicitWaitClickableElementId("return-login");

        /* Abort...
        common.verifyStringOnPage("GÅ TILLBAKA");
        common.findWebElementByXpath("//div/section[2]/div[2]/form/div[2]/button[1]/span").click();
        */
    }
}
