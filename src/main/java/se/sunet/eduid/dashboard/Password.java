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

    public void runPassword(String newPassword, boolean buttonValuePopup, boolean useRecommendedPw, boolean buttonValueConfirm,
                             String username, String password, boolean incorrectPassword){
        verifyPageTitle();
        changePassword(newPassword, buttonValuePopup, useRecommendedPw, buttonValueConfirm, username, password, incorrectPassword);

        verifyLabelsSwedish();
        verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void changePassword(String newPassword, boolean buttonValuePopup, boolean useRecommendedPw, boolean buttonValueConfirm,
         String username, String password, boolean incorrectPassword){

        //If a new password is provided, initiate the change flow
        if(!newPassword.equals("")) {
            common.click(common.findWebElementById("security-change-button"));

            common.switchToPopUpWindow();

            common.timeoutMilliSeconds(500);
            verifyPopupLabels();

            //Accept if true else Abort
            if(buttonValuePopup) {
                common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span"));

                common.timeoutMilliSeconds(500);
                common.switchToDefaultWindow();

                //Enter userName and password since we need to login again before pw change
                login.enterUsernamePassword(username, password, false);
                login.signIn(username, false);

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
                if(!useRecommendedPw) {
                    //Select use own password
                    common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span"));
                    verifyOwnPasswordLabels();

                    //Select use own password again since when switch between language it will come back to default change pw page
                    common.click(common.findWebElementByXpath("//*[@id=\"password-suggestion\"]/div/button/span"));

                    //Type new password
                    common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").clear();
                    common.findWebElementByXpath("//*[@id=\"custom-password-field\"]/input").sendKeys(newPassword);

                    //Repeat new password
                    common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").clear();
                    common.findWebElementByXpath("//*[@id=\"repeat-password-field\"]/input").sendKeys(newPassword);
                }
                else if(useRecommendedPw){
                    common.setRecommendedPw(common.findWebElementByXpath("//*[@id=\"suggested-password-field\"]/input").getAttribute("value"));
                    Common.log.info("Stored recommendedPw: " +common.getRecommendedPw());
                }

                if((!common.getRecommendedPw().equals("") && useRecommendedPw || common.getRecommendedPw().equals("") && !useRecommendedPw)) {
                    //Enter current password
                    common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
                    if(incorrectPassword)
                        common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys("notCorrectPw");
                    else
                        common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(password);
                }
                else if(!common.getRecommendedPw().equals("") && !useRecommendedPw){
                    common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").clear();
                    common.findWebElementByXpath("//*[@id=\"old-password-field\"]/input").sendKeys(common.getRecommendedPw());
                    common.setRecommendedPw("");
                }

                //Save button or Abort
                if(buttonValueConfirm) {
                    common.click(common.findWebElementById("chpass-button"));

                    // If not the correct password was entered at password change
                    if(incorrectPassword) {
                        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
                        common.verifyStringOnPage("Ett fel har " +
                                "uppstått vid ändring av ditt lösenord. Vänligen försök igen eller kontakta supporten om problemet kvarstår.");
                        common.timeoutMilliSeconds(500);
                        //Click abort
                        common.click(common.findWebElementByXpath("//*[@id=\"chpass-form\"]/button[2]/span"));
                    }
                    //If too weak password is chosen, click abort. Inside save button if-statement to test if its possible to click it
                    else if(newPassword.equalsIgnoreCase("test")) {
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