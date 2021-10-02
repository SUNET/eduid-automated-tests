package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class DashBoard {
    private Common common;

    public DashBoard(Common common){
        this.common = common;
    }

    public void runDashBoard(){
        verifyPageTitle();
        verifyNotificationDot();
        verifyUserId();
        verifyDisplayName();
        verifyGivenName();
        verifySurName();
        //Ignore check of ID number when not present
        if(!common.getIdentityNumber().equalsIgnoreCase("lägg till personnummer"))
            verifyIdentityNumber();
        verifyPhone();
        verifyEmail();
        if(common.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
        pressSettings();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID dashboard");
        common.verifyPageTitle("eduID dashboard");

        //Timeout to save time from retry-functionality
        common.timeoutMilliSeconds(200);
        //TODO temp fix to get swedish language - needed when new accounts created
       if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")
                && common.getLanguage().equalsIgnoreCase("Svenska")) {
            common.findWebElementByLinkText("Svenska").click();
        }



    }

    private void verifyNotificationDot(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/div/div/div").isDisplayed();
    }

    private void verifyUserId() {
        common.verifyStringOnPage(common.getUsername().toLowerCase());
    }

    private void verifyDisplayName() {
        common.verifyStringOnPage(common.getDisplayName());
    }

    private void verifyGivenName() {
        common.verifyStringOnPage(common.getGivenName());
    }

    private void verifySurName() {
        common.verifyStringOnPage(common.getSurName());
    }

    private void verifyIdentityNumber() {
        //Check text on link for hide/show full identityNumber
        checkShowHideText("VISA", "SHOW");

        //Click on Show button to display complete personal nummber
        common.findWebElementByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span").click();
        common.verifyStringOnPage(common.getIdentityNumber());

        //Check text on link for hide/show full identityNumber
        checkShowHideText("DÖLJ", "HIDE");
    }

    private void verifyPhone() {
        common.verifyStringOnPage(common.getPhoneNumber());
    }

    private void verifyEmail() {
        common.verifyStringOnPage(common.getEmail().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        //Welcome label
        common.verifyStringOnPage( "eduID för");

        //Profile not verified label
        //common.verifyStringByXpath("//*[@id=\"profile-prompt-link\"]/h2/span", "Glöm inte att koppla din identitet till ditt eduID");

        //Profile
        common.verifyStringOnPage("Profil");

        //Settings
        common.verifyStringOnPage("Inställningar");

        //Name
        common.verifyStringOnPage("Namn");

        //Personal number
        common.verifyStringOnPage("Personnummer");

        //Phone number
        common.verifyStringOnPage( "Telefonnummer");

        //Email
        common.verifyStringOnPage( "E-postadress");

        //FAQ
        common.verifyStringOnPage("Hjälp");

        //Logout
        common.verifyStringOnPage("Logga ut");

        //Language change
        common.verifyStringOnPage("English");
    }

    private void verifyLabelsEnglish() {
        //Welcome label
        common.verifyStringOnPage( "eduID for");

        //Profile not verified label
        //common.verifyStringOnPage("Don't forget to connect your identity to eduID");

        //Profile
        common.verifyStringOnPage("Profile");

        //Settings
        common.verifyStringOnPage( "Settings");

        //Name
        common.verifyStringOnPage( "Name");

        //Personal number
        common.verifyStringOnPage( "Id number");

        //Phone number
        common.verifyStringOnPage( "Phone number");

        //Email
        common.verifyStringOnPage("Email address");

        //FAQ
        common.verifyStringOnPage( "Help");

        //Logout
        common.verifyStringOnPage("Logout");

        //Language change
        common.verifyStringOnPage("Svenska");

        //Click on Swedish
//        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void checkShowHideText(String textSwedish, String textEnglish){
        if(common.getLanguage().equalsIgnoreCase("Svenska"))
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span", textSwedish);
        else
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span", textEnglish);
    }

    public void pressSettings(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
    }
}
