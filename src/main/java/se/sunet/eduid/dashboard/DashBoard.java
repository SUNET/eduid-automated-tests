package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class DashBoard {
    private final Common common;
    private final TestData testData;

    public DashBoard(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDashBoard(){
        verifyPageTitle();
        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            verifyNotificationDot();
        verifyUserId();
        verifyDisplayName();
        verifyGivenName();
        verifySurName();
        //Ignore check of ID number when not present
        if(!testData.getIdentityNumber().equalsIgnoreCase("lägg till personnummer"))
            verifyIdentityNumber();
        verifyPhone();
        verifyEmail();
        if(testData.getLanguage().equals("Svenska"))
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
                && testData.getLanguage().equalsIgnoreCase("Svenska")) {
            common.findWebElementByLinkText("Svenska").click();
        }
    }

    private void verifyNotificationDot(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[2]/li/div/div/div").isDisplayed();
    }

    private void verifyUserId() {
        common.verifyStringOnPage(testData.getUsername().toLowerCase());
    }

    private void verifyDisplayName() { common.verifyStringOnPage(testData.getDisplayName()); }

    private void verifyGivenName() { common.verifyStringOnPage(testData.getGivenName()); }

    private void verifySurName() {
        common.verifyStringOnPage(testData.getSurName());
    }

    private void verifyIdentityNumber() {
        //Check text on link for hide/show full identityNumber
        checkShowHideText("VISA", "SHOW");

        //Click on Show button to display complete personal nummber
        common.findWebElementByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span").click();
        common.verifyStringOnPage(testData.getIdentityNumber());

        //Check text on link for hide/show full identityNumber
        checkShowHideText("DÖLJ", "HIDE");
    }

    private void verifyPhone() {
        common.verifyStringOnPage(testData.getPhoneNumber());
    }

    private void verifyEmail() {
        common.verifyStringOnPage(testData.getEmail().toLowerCase());
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
    }

    private void checkShowHideText(String textSwedish, String textEnglish){
        if(testData.getLanguage().equalsIgnoreCase("Svenska"))
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span", textSwedish);
        else
            common.verifyStringByXpath("//*[@id=\"profile-grid\"]/div[2]/div/button/span", textEnglish);
    }

    public void pressSettings(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[3]/li/span").click();

        //wait for one "add more" button to be clickable
        common.explicitWaitClickableElementId("add-more-button");
    }
}
