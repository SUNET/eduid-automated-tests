package se.sunet.eduid.dashboard;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;

public class Password {
    private Common common;
    private Login login;

    public Password(Common common){
        this.common = common;
        login = new Login(common);
    }

    public void runPassword(){
        verifyPageTitle();
        changePassword();

        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void changePassword(){

        //If a new password is provided, initiate the change flow
        if(!common.getNewPassword().equals("")) {
            common.click(common.findWebElementById("security-change-button"));

            common.switchToPopUpWindow();

            common.timeoutMilliSeconds(500);
            verifyPopupLabels();

            //Accept if true else Abort
            if(common.getButtonValuePopup()) {
                common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span"));

                common.timeoutMilliSeconds(500);
                common.switchToDefaultWindow();

                //Check first if the incorrect password flag is set, then we need to re-set it after login.
                boolean tempIncorrectPassword = common.getIncorrectPassword();
                common.setIncorrectPassword(false);

                //Enter userName and password since we need to login again before pw change
                login.enterUsernamePassword();
                login.signIn();
                common.setIncorrectPassword(tempIncorrectPassword);

                //Heading
                common.timeoutMilliSeconds(500);
                common.verifyStringOnPage("Byt ditt lösenord");

                //Label 1
                common.verifyStringOnPage("SKRIV DITT NUVARANDE LÖSENORD");
                //Label 2
                common.verifyStringOnPage("REKOMMENDERAT LÖSENORD");

                // Link label
                common.verifyStringOnPage("JAG VILL INTE ANVÄNDA DET REKOMMENDERADE LÖSENORDET");

                //Should recommended password be used
                if(!common.getUseRecommendedPw()) {
                    //Select use own password
                    common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span"));
                    verifyOwnPasswordLabels();

                    //Select use own password again since when switch between language it will come back to default change pw page
                    common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span"));

                    //Enter current password
                    common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
                    if (common.getIncorrectPassword()){
                        common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");

                        //Type new password
                        common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                        common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(common.getNewPassword());

                        //Repeat new password
                        common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                        common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(common.getNewPassword());
                    }
                    else {
                        common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(common.getPassword());

                        //Type new password
                        common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                        common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(common.getNewPassword());

                        //Repeat new password
                        common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
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
                    common.click(common.findWebElementById("chpass-button"));

                    // If not the correct password was entered at password change
                    if(common.getIncorrectPassword()) {
                        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
                        common.verifyStringOnPage("Ett fel har " +
                                "uppstått vid ändring av ditt lösenord. Vänligen försök igen eller kontakta supporten om problemet kvarstår.");
                        common.timeoutMilliSeconds(500);
                        //Click abort
                        common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span"));
                    }
                    //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
                    else if(common.getNewPassword().equalsIgnoreCase("test")) {
                        common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span"));
                    }
                    else {
                        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
                        common.verifyStringOnPage("Lösenordet har ändrats");
                        //common.timeoutMilliSeconds(500);
                    }
                } else
                    common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span"));
            }
            else {
                common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[2]/span"));
                common.timeoutMilliSeconds(500);
            }
        }
    }

    private void verifyPopupLabels(){
        //Heading
        common.verifyStringOnPage("Av säkerhetsskäl...");

        //Text
        common.verifyStringOnPage("Du kommer behöva logga in igen med ditt nuvarande lösenord för att kunna skriva in det nya.");

        //Buttons
        common.verifyStringOnPage("ACCEPTERA");
        common.verifyStringOnPage("AVBRYT");
    }

    private void verifyOwnPasswordLabels(){
        //Heading - Swedish
        common.verifyStringOnPage("TÄNK PÅ ATT VÄLJA ETT SÄKERT LÖSENORD:");

        //Text - Swedish
        common.verifyStringOnPage("Använd stora och små bokstäver (inte bara första bokstaven)");
        common.verifyStringOnPage("Lägg till en eller flera siffror någonstans i mitten av lösenordet");
        common.verifyStringOnPage("Använd specialtecken som @ $ \\ + _ %");
        common.verifyStringOnPage("Blanksteg (mellanslag) ignoreras");

        //Heading 2 - Swedish
        common.verifyStringOnPage("SKRIV DITT NYA LÖSENORD");

        //Heading 3 - Swedish
        common.verifyStringOnPage("REPETERA DITT NYA LÖSENORD");

        //Link - Swedish
        common.verifyStringOnPage("REKOMMENDERA ETT LÖSENORD");

        //Switch to English
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

        //Click on not use recommended password
        common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span"));

        //Heading - English
        common.verifyStringOnPage("TIP: CHOOSE A STRONG PASSWORD");

        //Text - English
        common.verifyStringOnPage("Use upper- and lowercase characters, but not at the beginning or end");
        common.verifyStringOnPage("Add digits somewhere, but not at the beginning or end");
        common.verifyStringOnPage("Add special characters, such as @ $ \\ + _ %");
        common.verifyStringOnPage("Spaces are ignored");

        //Heading 2 - English
        common.verifyStringOnPage("ENTER NEW PASSWORD");

        //Heading 3 - English
        common.verifyStringOnPage("REPEAT NEW PASSWORD");

        //Link - English
        common.verifyStringOnPage("SUGGEST A PASSWORD FOR ME");

        //Switch to Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage("Byt lösenord");

        //Text
        common.verifyStringOnPage("Du kan antigen " +
                "använda ett rekommenderat lösenord som vi skapar åt dig eller ett du väljer helt själv.");

        //Add more phone numbers
        common.verifyStringOnPage("BYT LÖSENORD");
    }

    private void verifyLabelsEnglish() {
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

        //Heading
        common.verifyStringOnPage("Change password");

        //Text
        common.verifyStringOnPage("Click the link " +
                "to change your eduID password.");

        //Add more phone numbers
        common.verifyStringOnPage("CHANGE PASSWORD");

        //Click on Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }
}