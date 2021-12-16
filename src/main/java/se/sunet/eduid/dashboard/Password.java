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

        common.explicitWaitPageTitle("eduID dashboard");
        common.verifyPageTitle("eduID dashboard");

        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")) {
            common.findWebElementByLinkText("Svenska").click();
        }
    }

    private void changePassword(){
        //Wait for abort button to be clickable
        common.explicitWaitClickableElement("//*[@id=\"chpass-form\"]/button[2]");

        //Verify recommend password labels
        verifyRecommendedPwLabels();

        //Should recommended password be used
        if(!testData.isUseRecommendedPw()) {
            //Select use own password
            common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button").click();

            //Wait for custom password page
            common.explicitWaitClickableElement("//*[@id=\"passwordsview-form\"]/div[1]/label");

            //Verify custom pw labels
            verifyCustomPasswordLabels();

            //Select use own password again since when switch between language it will come back to default change pw page
            common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button").click();

            //Enter current password, first clear the input fields
            common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
            if (testData.isIncorrectPassword()){
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");

                //Type new password
                //common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(testData.getNewPassword());

                //Repeat new password
                //common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(testData.getNewPassword());
            }
            else {
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(testData.getPassword());

                //Type new password
                //common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(testData.getNewPassword());

                //Repeat new password
                //common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(testData.getNewPassword());

                //Store the new password
                testData.setPassword(testData.getNewPassword());
                Common.log.info("Stored custom password: " +testData.getPassword());
            }
        }
        else if(testData.isUseRecommendedPw()){
            //Enter current password
            common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
            if(testData.isIncorrectPassword())
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");
            else {
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(testData.getPassword());

                testData.setPassword(common.findWebElementByXpath("//*[@id=\"suggested-password-field\"]/input").getAttribute("value"));
                Common.log.info("Stored recommendedPw: " +testData.getPassword());
            }
        }

        Assert.assertFalse(testData.getPassword().isEmpty(), "A new password has not been saved, getPassword is: " +testData.getPassword());

        //Save button or Abort
        if(testData.isButtonValueConfirm()) {
            common.findWebElementById("chpass-button").click();

            // If not the correct password was entered at password change
            if(testData.isIncorrectPassword()) {
                common.verifyStatusMessage("Ett fel har uppstått vid ändring av ditt lösenord. Vänligen försök " +
                        "igen eller kontakta supporten om problemet kvarstår.");
                common.timeoutMilliSeconds(500);
                //Click abort
                common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]").click();
            }
            //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
            else if(testData.getNewPassword().equalsIgnoreCase("test")) {
                common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]").click();
            }
            else {
                common.verifyStatusMessage("Lösenordet har ändrats");
            }
        }
        else
            common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]").click();
    }


    private void verifyCustomPasswordLabels(){
        //Heading - Swedish
        common.verifyStringOnPage("Byt ditt lösenord");
        common.verifyStringOnPage("Skriv ditt nuvarande lösenord");

        //Text - Swedish
        common.verifyStringOnPage("Tänk på att välja ett säkert lösenord");
        common.verifyStringOnPage("Blanda stora och små bokstäver (inte bara första bokstaven)");
        common.verifyStringOnPage("Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyStringOnPage("Använd specialtecken som@ $ + _ %");
        common.verifyStringOnPage("Blanksteg (mellanslag) ignoreras");

        //Heading 2 - Swedish
        common.verifyStringOnPage("Skriv ditt nya lösenord");

        //Heading 3 - Swedish
        common.verifyStringOnPage("Repetera ditt nya lösenord");

        //Link - Swedish
        common.verifyStringOnPage("REKOMMENDERA ETT LÖSENORD");

        //Switch to English
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Click on not use recommended password
        common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button").click();

        //Heading - English
        common.verifyStringOnPage("Change your current password");
        common.verifyStringOnPage("Current password");

        //Text - English
        common.verifyStringOnPage("Tip: Choose a strong password");
        common.verifyStringOnPage("Use upper- and lowercase characters, but not at the beginning or end");
        common.verifyStringOnPage("Add digits somewhere, but not at the beginning or end");
        common.verifyStringOnPage("Add special characters, such as @ $ + _ %");
        common.verifyStringOnPage("Spaces are ignored");

        //Heading 2 - English
        common.verifyStringOnPage("Enter new password");

        //Heading 3 - English
        common.verifyStringOnPage("Repeat new password");

        //Link - English
        common.verifyStringOnPage("SUGGEST A PASSWORD FOR ME");

        //Switch to Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("Byt lösenord");

        //Text
        common.verifyStringOnPage("Du kan antingen " +
                "använda ett rekommenderat lösenord som vi skapar åt dig eller ett du väljer helt själv.");

        //Add more phone numbers
        common.verifyStringOnPage("BYT LÖSENORD");
    }

    private void verifyLabelsEnglish() {
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Heading
        common.verifyStringOnPage("Change password");

        //Text
        common.verifyStringOnPage("Click the link " +
                "to change your eduID password.");

        //Add more phone numbers
        common.verifyStringOnPage("CHANGE PASSWORD");

        //Click on Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyRecommendedPwLabels(){
        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Byt ditt lösenord");

        //Label 1
        common.verifyStringByXpath("//*[@id=\"old-password-field\"]/label","Skriv ditt nuvarande lösenord");
        //Label 2
        common.verifyStringByXpath("//*[@id=\"suggested-password-field\"]/label","Rekommenderat lösenord");

        // Link label
        common.verifyStringByXpath("//*[@id=\"password-suggestion\"]/div/button", "JAG VILL INTE ANVÄNDA DET REKOMMENDERADE LÖSENORDET");

        //English
        common.findWebElementByLinkText("English").click();

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Change your current password");

        //Label 1
        common.verifyStringByXpath("//*[@id=\"old-password-field\"]/label","Current password");
        //Label 2
        common.verifyStringByXpath("//*[@id=\"suggested-password-field\"]/label","Suggested password");

        // Link label
        common.verifyStringByXpath("//*[@id=\"password-suggestion\"]/div/button", "I DON'T WANT A SUGGESTED PASSWORD");

        //English
        common.findWebElementByLinkText("Svenska").click();
        common.timeoutMilliSeconds(500);
    }
}