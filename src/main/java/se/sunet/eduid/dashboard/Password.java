package se.sunet.eduid.dashboard;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Password {
    private final Common common;
    private final TestData testData;
    private final String abortRecPwButton = "//*[@id=\"new-password-form\"]/div[2]/button[1]";

    public Password(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPassword(){
        navigateToSettingss();
        verifyPageTitle();
        changePassword();
    }

    private void navigateToSettingss(){
        common.navigateToSettings();

        common.click(common.findWebElementById("security-change-button"));
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Byt Lösenord | eduID");

        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.selectSwedish();
        }
    }

    private void changePassword(){
        //Wait for abort button to be clickable
        common.explicitWaitClickableElement(abortRecPwButton);

        //Verify recommend password labels
        verifyRecommendedPwLabels();

        //Should recommended password be used
        if(!testData.isUseRecommendedPw()) {
            //Select use own password
            common.click(common.findWebElementById("custom-pw"));

            //Wait for show password 'VISA' at custom password page
            common.explicitWaitClickableElement("//*[@id=\"custom-wrapper\"]/div[2]/button");

            //Verify custom pw labels
            verifyCustomPasswordLabels();

            //Select use own password again since when switch between language it will come back to default change pw page
            common.click(common.findWebElementById("custom-pw"));

            //Enter current password, first clear the input fields
            common.findWebElementById("custom").clear();
            common.findWebElementById("repeat").clear();

            if (testData.isIncorrectPassword()){
                //Type new password
                common.findWebElementById("custom").sendKeys("NotTheSamePassword");

                //Repeat new password
                common.findWebElementById("repeat").sendKeys(testData.getNewPassword());
            }
            else {
                //Type new password
                common.findWebElementById("custom").sendKeys(testData.getNewPassword());

                //Repeat new password
                common.findWebElementById("repeat").sendKeys(testData.getNewPassword());

                //Store the new password
                testData.setPassword(testData.getNewPassword());
                Common.log.info("Stored custom password: " +testData.getPassword());
            }
        }
        else if(testData.isUseRecommendedPw()){
            copyPasteNewPassword();
        }

        Assert.assertFalse(testData.getPassword().isEmpty(),
                "A new password has not been saved, getPassword is: " +testData.getPassword());

        //Save button or Abort
        if(testData.isButtonValueConfirm()) {
            //The Save button has different ID depending on if recommended or own password is used.
            Common.log.info("Pressing OK to save new password");
            if(testData.isUseRecommendedPw())
                common.click(common.findWebElementById("new-password-button"));
            else
                common.click(common.findWebElementById("chpass-button"));

            common.timeoutMilliSeconds(500);

            // If not the correct password was entered at password change
            if(testData.isIncorrectPassword()) {
                common.verifyStringByXpath("//*[@id=\"repeat-wrapper\"]/small/span",
                        "Det nya och repeterade lösenordet är olika.");
                common.timeoutMilliSeconds(500);
                //Click abort
                common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
            }
            //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
            else if(testData.getNewPassword().equalsIgnoreCase("test")) {
                Common.log.info("Pressing Cancel abort new password");
                common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
            }
            else {
                //Confirmation of password change is no longer presented
                Common.log.info("Password changed!");
                common.timeoutSeconds(2);
            }
        }
        else {
            if(testData.isUseRecommendedPw())
                common.click(common.findWebElementByXpath(abortRecPwButton));
            else
                common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
        }
    }


    private void verifyCustomPasswordLabels(){
        //Heading - Swedish
        common.verifyStringOnPage("Ändra lösenord: Skapa ditt eget lösenord");
        common.verifyStringOnPage("När du skapar ditt eget lösenord, bör du se till att det är " +
                "tillräckligt starkt för att hålla ditt konto säkert.");

        //Text - Swedish
        common.verifyStringOnPage("Tänk på att välja ett säkert lösenord");
        common.verifyStringOnPage("Blanda stora och små bokstäver (inte bara första bokstaven)");
        common.verifyStringOnPage("Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyStringOnPage("Använd specialtecken som @ $ \\ + _ %");
        common.verifyStringOnPage("Blanksteg (mellanslag) ignoreras");

        //Heading 2 - Swedish
        common.verifyStringOnPage("Skriv ditt nya lösenord");

        //Heading 3 - Swedish
        common.verifyStringOnPage("Repetera ditt nya lösenord");

        //Link - Swedish
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/fieldset/div/label[1]/span",
                "Rekommenderat Lösenord");

        //Switch to English
        common.selectEnglish();

        //Click on use own password
        common.click(common.findWebElementById("custom-pw"));

        //Heading - English
        common.verifyStringOnPage("Change password: Set your own password");
        common.verifyStringOnPage("When creating your own password, make sure it's strong enough to " +
                "keep your account safe.");

        //Text - English
        common.verifyStringOnPage("Tip: Choose a strong password");
        common.verifyStringOnPage("Use upper- and lowercase characters, but not at the beginning or end");
        common.verifyStringOnPage("Add digits somewhere, but not at the beginning or end");
        common.verifyStringOnPage("Add special characters, such as @ $ \\ + _ %");
        common.verifyStringOnPage("Spaces are ignored");

        //Heading 2 - English
        common.verifyStringOnPage("Enter new password");

        //Heading 3 - English
        common.verifyStringOnPage("Repeat new password");

        //Link - English
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/fieldset/div/label[1]/span",
                "Suggested Password");

        //Switch to Swedish
        common.selectSwedish();
    }

    private void verifyRecommendedPwLabels(){
        //Heading
        common.verifyStringOnPage("Ändra lösenord: Rekommenderat lösenord");
        common.verifyStringOnPage("Ett starkt lösenord har genererats åt dig. För att fortsätta måste " +
                "du kopiera lösenordet till fältet Repetera ditt nya lösenord och klicka på Spara knappen för att " +
                "lagra det för framtida bruk.");

        //Label 1
        //common.verifyStringByXpath("//*[@id=\"old-wrapper\"]/label","Skriv ditt nuvarande lösenord");

        //Heading 2 - Swedish
        common.verifyStringOnPage("Nytt lösenord");

        //Heading 3 - Swedish
        common.verifyStringOnPage("Repetera ditt nya lösenord");

        // Link label
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/fieldset/div/label[2]/span",
                "Skapa Ditt Eget Lösenord");

        //English
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage( "Change password: Suggested password");
        common.verifyStringOnPage("A strong password has been generated for you. To proceed you will " +
                "need to copy the password in to the Repeat new password field and click the Save button to store it " +
                "for future use.");

        //Label 2
        common.verifyStringOnPage("New password");

        //Label 2
        common.verifyStringOnPage("Repeat new password");

        // Link label
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/fieldset/div/label[2]/span",
                "Set Your Own Password");

        //Swedish
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
}