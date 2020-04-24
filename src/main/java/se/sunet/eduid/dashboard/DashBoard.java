package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class DashBoard {
    private String default_givenName = "ove";
    private String default_surName = "semart";
    private Common common;

    public DashBoard(Common common){
        this.common = common;
    }

    public void runDashBoard2(String givenName_Dashboard, String surName_Dashboard, String language_Dashboard){
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

    private void verifyIdentityNumber() {
        common.verifyStringByXpath("//div/div/div/div[2]/div[2]/div/div[2]/div[2]/div/a", "197501100395");
    }

    private void verifyPhone() {
        common.verifyStringByXpath("//div/div/div/div[2]/div[2]/div/div[2]/div[3]/div/p", "+46707136728");
    }

    private void verifyEmail() {
        common.verifyStringByXpath("//div/div/div/div[2]/div[2]/div/div[2]/div[4]/div/p", "ove@idsec.se");
    }

    private void verifyLabelsSwedish() {
        //Welcome label
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "eduID för");

        //Profile not verified label
        common.verifyStringByXpath("//*[@id=\"profile-prompt-link\"]/h2/span", "Glöm inte att koppla din identitet till ditt eduID");

        //Profile
        common.verifyStringByXpath("//*[@id=\"dashboard-nav\"]/ul/a[1]/li/h5/span", "Profil");

        //Settings
        common.verifyStringByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/h5/span", "Inställningar");

        //Name
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[1]/label/span", "NAMN");

        //Personal number
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[2]/label/span", "PERSONNUMMER");

        //Phone number
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[3]/label/span", "TELEFONNUMMER");

        //Email
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[4]/label/span", "E-POSTADRESS");

        //FAQ
        common.verifyStringByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a/span", "Hjälp");

        //Logout
        common.verifyStringByXpath("//*[@id=\"logout\"]/span", "Logga ut");

        //Language change
        common.verifyStringByXpath("//*[@id=\"language-selector\"]/p[1]/a", "English");
    }

    private void verifyLabelsEnglish() {
        //Welcome label
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "eduID for");

        //Profile not verified label
        common.verifyStringByXpath("//*[@id=\"profile-prompt-link\"]/h2/span", "Don't forget to connect your identity to eduID");

        //Profile
        common.verifyStringByXpath("//*[@id=\"dashboard-nav\"]/ul/a[1]/li/h5/span", "Profile");

        //Settings
        common.verifyStringByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/h5/span", "Settings");

        //Name
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[1]/label/span", "NAME");

        //Personal number
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[2]/label/span", "NATIONAL ID NUMBER");

        //Phone number
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[3]/label/span", "PHONE NUMBER");

        //Email
        common.verifyStringByXpath("//*[@id=\"profile-detail-grid\"]/div[4]/label/span", "EMAIL ADDRESS");

        //FAQ
        common.verifyStringByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a/span", "Help");

        //Logout
        common.verifyStringByXpath("//*[@id=\"logout\"]/span", "Logout");

        //Language change
        common.verifyStringByXpath("//*[@id=\"language-selector\"]/p[2]/a", "Svenska");

        //Click on Swedish
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));
    }

    private void pressSettings(){
        common.click(common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/h5/span"));
    }
}
