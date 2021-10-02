package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class Password {
    private Common common;

    public Password(Common common){
        this.common = common;
    }

    public void runPassword(){
        verifyPageTitle();
        changePassword();

        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID dashboard");
        common.verifyPageTitle("eduID dashboard");
    }

    private void changePassword(){
        //Wait for abort button to be clickable
        common.explicitWaitClickableElement("//*[@id=\"chpass-form\"]/button[2]/span");

        //Heading
        common.verifyStringOnPage("Byt ditt lösenord");

        //Label 1
        common.verifyStringOnPage("Skriv ditt nuvarande lösenord");
        //Label 2
        common.verifyStringOnPage("Rekommenderat lösenord");

        // Link label
        common.verifyStringOnPage("JAG VILL INTE ANVÄNDA DET REKOMMENDERADE LÖSENORDET");

        //Should recommended password be used
        if(!common.getUseRecommendedPw()) {
            //Select use own password
            common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span").click();
            verifyOwnPasswordLabels();

            //Select use own password again since when switch between language it will come back to default change pw page
            common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span").click();

            //Enter current password, first clear the input fields
            common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
            if (common.getIncorrectPassword()){
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");

                //Type new password
                //common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(common.getNewPassword());

                //Repeat new password
                //common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(common.getNewPassword());
            }
            else {
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(common.getPassword());

                //Type new password
                //common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(common.getNewPassword());

                //Repeat new password
                //common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(common.getNewPassword());

                //Store the new password
                common.setPassword(common.getNewPassword());
                Common.log.info("Stored custom password: " +common.getPassword());
            }
        }
        else if(common.getUseRecommendedPw()){
            //Enter current password
            common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
            if(common.getIncorrectPassword())
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");
            else {
                common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(common.getPassword());

                common.setPassword(common.findWebElementByXpath("//*[@id=\"suggested-password-field\"]/input").getAttribute("value"));
                Common.log.info("Stored recommendedPw: " +common.getPassword());
            }
        }

        //Save button or Abort
        if(common.getButtonValueConfirm()) {
            common.findWebElementById("chpass-button").click();

            // If not the correct password was entered at password change
            if(common.getIncorrectPassword()) {
                common.verifyStatusMessage("Ett fel har uppstått vid ändring av ditt lösenord. Vänligen försök " +
                        "igen eller kontakta supporten om problemet kvarstår.");
                common.timeoutMilliSeconds(500);
                //Click abort
                common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span").click();
            }
            //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
            else if(common.getNewPassword().equalsIgnoreCase("test")) {
                common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span").click();
            }
            else {
                common.verifyStatusMessage("Lösenordet har ändrats");
            }
        }
        else
            common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span").click();

    }


    private void verifyOwnPasswordLabels(){
        //Heading - Swedish
        common.verifyStringOnPage("Byt ditt lösenord");
        common.verifyStringOnPage("Skriv ditt nuvarande lösenord");

        //Text - Swedish
        common.verifyStringOnPage("Tänk på att välja ett säkert lösenord:");
        common.verifyStringOnPage("Använd stora och små bokstäver (inte bara första bokstaven)");
        common.verifyStringOnPage("Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyStringOnPage("Använd specialtecken som @ $ \\ + _ %");
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
        common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span").click();

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
}