package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PersonalInfo {
    private Common common;
    private boolean pressAddButton = false;

    public PersonalInfo(Common common){
        this.common = common;
    }

    public void runPersonalInfo(){
        verifyPageTitle();
        verifyAndUpdatePersonalInfo();
        selectLanguage();
        if(pressAddButton)
            pressAddButton();
        if(common.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void verifyAndUpdatePersonalInfo() {
        // If given name shall be updated else verify the default value
        if(common.getGivenName().equals(common.getAttributeByXpath("//*[@id=\"given_name\"]/input")))
            common.verifyStrings(common.getGivenName(), common.getAttributeByXpath("//*[@id=\"given_name\"]/input"));
        else{
            common.findWebElementByXpath("//*[@id=\"given_name\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"given_name\"]/input").sendKeys(common.getGivenName());
            common.verifyStrings(common.getGivenName(), common.getAttributeByXpath("//*[@id=\"given_name\"]/input"));

            pressAddButton = true;
        }

        // If sur name shall be updated else verify the default value
        if(common.getSurName().equals(common.getAttributeByXpath("//*[@id=\"surname\"]/input")))
            common.verifyStrings(common.getSurName(), common.getAttributeByXpath("//*[@id=\"surname\"]/input"));
        else{
            common.findWebElementByXpath("//*[@id=\"surname\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"surname\"]/input").sendKeys(common.getSurName());
            common.verifyStrings(common.getSurName(), common.getAttributeByXpath("//*[@id=\"surname\"]/input"));

            pressAddButton = true;
        }

        // If display name shall be updated else verify the default value
        if(common.getDisplayName().equals(common.getAttributeByXpath("//*[@id=\"display_name\"]/input")))
            common.verifyStrings(common.getDisplayName(), common.getAttributeByXpath("//*[@id=\"display_name\"]/input"));
        else {
            common.findWebElementByXpath("//*[@id=\"display_name\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"display_name\"]/input").sendKeys(common.getDisplayName());
            common.verifyStrings(common.getDisplayName(), common.getAttributeByXpath("//*[@id=\"display_name\"]/input"));

            pressAddButton = true;
        }
    }

    private void selectLanguage() {
        if(common.getLanguage().equalsIgnoreCase("Svenska") && !common.findWebElementById("Svenska").isSelected()) {
            //Select new language - Swedish
            common.findWebElementById("Svenska").click();

            pressAddButton = true;
        }
        else if(common.getLanguage().equalsIgnoreCase("English") && !common.findWebElementById("English").isSelected()){
            //Select new language - English
            common.findWebElementById("English").click();

            pressAddButton = true;
        }
    }

    private void pressAddButton(){
        // If any value updated we need to save it and verify that the info message appears
        if(pressAddButton) {
            //Click Add button
            common.findWebElementById("personal-data-button").click();
            common.timeoutMilliSeconds(500);

            if(common.getLanguage().equalsIgnoreCase("Svenska")) {
                common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "SPARA");
            }
            else{
                common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "SAVE");
            }
            verifyUpdatedInfoBar(common.getLanguage());
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage( "Personlig information");

        //Text
        common.verifyStringOnPage("Den här informationen skickas till tjänsteleverantörer när du loggar " +
                "in med eduID för att kunna anpassa dessa tjänster för dig.");

        //Given name
        common.verifyStringOnPage("Förnamn");

        //Sur name
        common.verifyStringOnPage( "Efternamn");

        //Display name
        common.verifyStringOnPage( "Visningsnamn");

        //Display name text
        common.verifyStringOnPage( "vissa tjänster visar detta " +
                "i stället för förnamn och efternamn.");

        //Language
        common.verifyStringOnPage( "Språk");
    }

    private void verifyLabelsEnglish() {
        //Heading
        common.verifyStringOnPage("Name & language");

        //Text
        common.verifyStringOnPage("Your name and preferred language will be used to personalise some services that you access with eduID.");

        //Given name
        common.verifyStringOnPage("First name");

        //Sur name
        common.verifyStringOnPage("Last name");

        //Display name
        common.verifyStringOnPage( "Display name");

        //Display name text
        common.verifyStringOnPage("some services will show this " +
                "instead of your first and last name.");

        //Language
        common.verifyStringOnPage("Language");
    }

    private void verifyUpdatedInfoBar(String language){
        //Verify the saved info message, using timeout to get better flow...
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        if(!language.equals("English"))
            common.verifyStringByXpath( "//*[@id=\"panel\"]/div[1]/div/span", "Personlig information sparad");
        else
            common.verifyStringByXpath( "//*[@id=\"panel\"]/div[1]/div/span", "Personal information updated");
    }
}
