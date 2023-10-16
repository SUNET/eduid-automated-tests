package se.sunet.eduid.dashboard;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Password {
    private final Common common;
    private final TestData testData;

    public Password(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPassword(){
        verifyPageTitle();
        changePassword();

        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.timeoutMilliSeconds(400);

        common.explicitWaitPageTitle("Byt Lösenord | eduID");
        common.verifyPageTitle("Byt Lösenord | eduID");

        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.selectSwedish();
        }
    }//utdrag hur dödsfallsregistret

    private void changePassword(){
        //Wait for abort button to be clickable
        common.explicitWaitClickableElement("//*[@id=\"chpass-form\"]/button[1]");

        //Verify recommend password labels
        verifyRecommendedPwLabels();

        //Should recommended password be used
        if(!testData.isUseRecommendedPw()) {
            //Select use own password
            common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button"));

            //Wait for custom password page
            common.explicitWaitClickableElement("//*[@id=\"passwordsview-form\"]/div[1]/label");

            //Verify custom pw labels
            verifyCustomPasswordLabels();

            //Select use own password again since when switch between language it will come back to default change pw page
            common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button"));

            //Enter current password, first clear the input fields
            common.findWebElementById("old").clear();
            common.findWebElementById("old").clear();
            common.findWebElementById("old").clear();
            if (testData.isIncorrectPassword()){
                common.findWebElementById("old").sendKeys("notCorrectPw");

                //Type new password
                common.findWebElementById("custom").sendKeys(testData.getNewPassword());

                //Repeat new password
                common.findWebElementById("repeat").sendKeys(testData.getNewPassword());
            }
            else {
                common.findWebElementById("old").sendKeys(testData.getPassword());

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
            //Enter current password
            common.findWebElementById("old").clear();
            if(testData.isIncorrectPassword())
                common.findWebElementById("old").sendKeys("notCorrectPw");
            else {
                common.findWebElementById("old").sendKeys(testData.getPassword());

                //testData.setPassword(common.findWebElementByXpath("//*[@id=\"suggested\"]/input").getAttribute("value"));
                testData.setPassword(common.findWebElementById("suggested").getAttribute("value"));
                Common.log.info("Stored recommendedPw: " +testData.getPassword());
            }
        }

        Assert.assertFalse(testData.getPassword().isEmpty(), "A new password has not been saved, getPassword is: " +testData.getPassword());

        //Save button or Abort
        if(testData.isButtonValueConfirm()) {
            common.click(common.findWebElementById("chpass-button"));
            common.timeoutMilliSeconds(500);

            // If not the correct password was entered at password change
            if(testData.isIncorrectPassword()) {
                common.verifyStatusMessage("Det gick inte att verifiera ditt gamla lösenord. Vänligen försök igen " +
                        "eller kontakta supporten om problemet kvarstår.");
                common.timeoutMilliSeconds(500);
                //Click abort
                common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
            }
            //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
            else if(testData.getNewPassword().equalsIgnoreCase("test")) {
                common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
            }
            else {
                //Confirmation of password change is no longer presented
                //common.verifyStatusMessage("Lösenordet har ändrats");
                common.timeoutSeconds(2);
            }
        }
        else
            common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[1]"));
    }


    private void verifyCustomPasswordLabels(){
        //Heading - Swedish
        common.verifyStringOnPage("Byt ditt lösenord");
        common.verifyStringOnPage("Skriv ditt nuvarande lösenord");

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
        common.verifyStringOnPage("Rekommendera ett lösenord");

        //Switch to English
        common.selectEnglish();

        //Click on not use recommended password
        common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button"));

        //Heading - English
        common.verifyStringOnPage("Change your current password");
        common.verifyStringOnPage("Current password");

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
        common.verifyStringOnPage("Suggest a password for me");

        //Switch to Swedish
        common.selectSwedish();
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("Byt lösenord");

        //Text
        common.verifyStringOnPage("Du kan antingen " +
                "använda ett rekommenderat lösenord som vi skapar åt dig eller ett du väljer helt själv.");

        //Change password link
        common.verifyStringOnPage("byt lösenord");
    }

    private void verifyLabelsEnglish() {
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Change password");

        //Text
        common.verifyStringOnPage("Click the link " +
                "to change your eduID password.");

        //Change password link
        common.verifyStringOnPage("change password");

        //Click on Swedish
        common.selectSwedish();
    }

    private void verifyRecommendedPwLabels(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/h4", "Byt ditt lösenord");

        //Label 1
        common.verifyStringByXpath("//*[@id=\"old-wrapper\"]/label","Skriv ditt nuvarande lösenord");
        //Label 2
        common.verifyStringByXpath("//*[@id=\"suggested-wrapper\"]/label","Rekommenderat lösenord");

        // Link label
        common.verifyStringById("pwmode-button", "Jag vill inte använda det rekommenderade lösenordet");

        //English
        common.selectEnglish();

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/h4", "Change your current password");

        //Label 1
        common.verifyStringByXpath("//*[@id=\"old-wrapper\"]/label","Current password");
        //Label 2
        common.verifyStringByXpath("//*[@id=\"suggested-wrapper\"]/label","Suggested password");

        // Link label
        common.verifyStringById("pwmode-button", "I don't want a suggested password");

        //Swedish
        common.selectSwedish();
    }
}