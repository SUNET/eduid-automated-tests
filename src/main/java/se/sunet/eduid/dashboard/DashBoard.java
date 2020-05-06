package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class DashBoard {
    private String default_givenName = "ove";
    private String default_surName = "semart";
    private Common common;

    public DashBoard(Common common){
        this.common = common;
    }

    public void runDashBoard(String givenName_Dashboard, String surName_Dashboard, String language_Dashboard){
        verifyPageTitle();
        verifyUserId();
        verifyFirstName(givenName_Dashboard);
        verifyLastName(surName_Dashboard);
        verifyIdentityNumber();
        verifyPhone();
        verifyEmail();
        if(!language_Dashboard.equals("English"))
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
        common.verifyStringOnPage("ove@idsec.se");
    }

    private void verifyFirstName(String givenName_Dashboard) {
        if(!givenName_Dashboard.equals(""))
            common.verifyStringOnPage(givenName_Dashboard);
        else
            common.verifyStringOnPage(default_givenName);
    }

    private void verifyLastName(String surName_Dashboard) {
        if(!surName_Dashboard.equals(""))
            common.verifyStringOnPage(surName_Dashboard);
        else
            common.verifyStringOnPage(default_surName);
    }

    private void verifyIdentityNumber() {//*[@id="profile-grid"]/div[2]/a/span
        common.verifyStringOnPage("197501100395");
    }

    private void verifyPhone() {
        common.verifyStringOnPage("+46707136728");
    }

    private void verifyEmail() {
        common.verifyStringOnPage("ove@idsec.se");
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
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }

    private void pressSettings(){
        common.click(common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span"));
    }
}
