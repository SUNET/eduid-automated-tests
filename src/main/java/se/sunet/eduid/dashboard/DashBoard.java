package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class DashBoard {
    private Common common;

    public DashBoard(Common common){
        this.common = common;
    }

    public void runDashBoard(){
        verifyPageTitle();
        verifyUserId();
        verifyDisplayName();
        verifyGivenName();
        verifySurName();
        verifyIdentityNumber();
        verifyPhone();
        verifyEmail();
        if(!common.getLanguage().equals("English"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
        pressSettings();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void verifyUserId() {
        common.verifyStringOnPage(common.getUsername());
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
        common.verifyStringOnPage(common.getPersonalNumber());
    }

    private void verifyPhone() {
        common.verifyStringOnPage(common.getPhoneNumber());
    }

    private void verifyEmail() {
        common.verifyStringOnPage(common.getEmail());
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
        common.verifyStringOnPage("NAMN");

        //Personal number
        common.verifyStringOnPage("PERSONNUMMER");

        //Phone number
        common.verifyStringOnPage( "TELEFONNUMMER");

        //Email
        common.verifyStringOnPage( "E-POSTADRESS");

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
        common.verifyStringOnPage( "NAME");

        //Personal number
        common.verifyStringOnPage( "ID NUMBER");

        //Phone number
        common.verifyStringOnPage( "PHONE NUMBER");

        //Email
        common.verifyStringOnPage("EMAIL ADDRESS");

        //FAQ
        common.verifyStringOnPage( "Help");

        //Logout
        common.verifyStringOnPage("Logout");

        //Language change
        common.verifyStringOnPage("Svenska");

        //Click on Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    public void pressSettings(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();
    }
}
