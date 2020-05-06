package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PersonalInfo {
    private Common common;

    public PersonalInfo(Common common){
        this.common = common;
    }

    public void runPersonalInfo(String givenName, String surName, String displayName, String language){
        verifyPageTitle();
        verifyAndUpdatePersonalInfo(givenName, surName, displayName, language);
        selectLanguage(language);
        if(!language.equals("English"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void verifyAndUpdatePersonalInfo(String givenName, String surName, String displayName, String language) {
        boolean pressAddButton = false;

        // If given name shall be updated else verify the default value
        if(givenName.equals(common.getAttributeByXpath("//*[@id=\"given_name\"]/input")))
            common.verifyStrings(givenName, common.getAttributeByXpath("//*[@id=\"given_name\"]/input"));
        else if(!givenName.equals("")) {
            common.findWebElementByXpath("//*[@id=\"given_name\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"given_name\"]/input").sendKeys(givenName);
            common.verifyStrings(givenName, common.getAttributeByXpath("//*[@id=\"given_name\"]/input"));

            pressAddButton = true;
        }
        else
            common.verifyStrings(givenName, common.getAttributeByXpath("//*[@id=\"given_name\"]/input"));

        // If sur name shall be updated else verify the default value
        if(surName.equals(common.getAttributeByXpath("//*[@id=\"surname\"]/input")))
            common.verifyStrings(surName, common.getAttributeByXpath("//*[@id=\"surname\"]/input"));
        else if(!surName.equals("")) {
            common.findWebElementByXpath("//*[@id=\"surname\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"surname\"]/input").sendKeys(surName);
            common.verifyStrings(surName, common.getAttributeByXpath("//*[@id=\"surname\"]/input"));

            pressAddButton = true;
        }
        else
            common.verifyStrings(surName, common.getAttributeByXpath("//*[@id=\"surname\"]/input"));

        // If display name shall be updated else verify the default value
        if(displayName.equals(common.getAttributeByXpath("//*[@id=\"display_name\"]/input")))
            common.verifyStrings(displayName, common.getAttributeByXpath("//*[@id=\"display_name\"]/input"));
        else if(!displayName.equals("")) {
            common.findWebElementByXpath("//*[@id=\"display_name\"]/input").clear();
            common.findWebElementByXpath("//*[@id=\"display_name\"]/input").sendKeys(displayName);
            common.verifyStrings(displayName, common.getAttributeByXpath("//*[@id=\"display_name\"]/input"));

            pressAddButton = true;
        }
        else
            common.verifyStrings(displayName,  common.getAttributeByXpath("//*[@id=\"display_name\"]/input"));

        // If any value updated we need to save it and verify that the info message appears
        if(pressAddButton) {
            //Click Add button
            common.click(common.findWebElementByXpath("//*[@id=\"personal-data-button\"]/span"));
            verifyUpdatedInfoBar(language);
        }
    }

    private void selectLanguage(String language) {
        //language = lang;
        if(language.equalsIgnoreCase("Svenska")) {
            //Select new language - Swedish
            common.findWebElementByXpath("//*[@id=\"language\"]/select/option[3]").click();

            //Click Add button
            common.findWebElementByXpath("//*[@id=\"personal-data-button\"]/span").click();

            common.timeoutMilliSeconds(500);
            common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "LÄGG TILL");

            //Verify the saved info message - Swedish
            verifyUpdatedInfoBar(language);
        }
        else if(language.equalsIgnoreCase("English")){
            //Verify the label
            common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "LÄGG TILL");

            //Select new language - English
            common.findWebElementByXpath("//*[@id=\"language\"]/select/option[2]").click();

            //Click Add button
            common.findWebElementByXpath("//*[@id=\"personal-data-button\"]/span").click();

            //Verify the label
            //explicitWaitClickableElement("//*[@id=\"personal-data-button\"]/span");
            common.timeoutMilliSeconds(500);
            common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "ADD");

            //Verify the saved info message - English
            verifyUpdatedInfoBar(language);

            //Verify labels in english
            verifyLabelsEnglish();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage( "Personlig information");

        //Text
        common.verifyStringOnPage("Den här informationen skickas till tjänsteleverantörer när du loggar " +
                "in med eduID för att kunna anpassa dessa tjänster för dig.");

        //Given name
        common.verifyStringOnPage("FÖRNAMN");

        //Sur name
        common.verifyStringOnPage( "EFTERNAMN");

        //Display name
        common.verifyStringOnPage( "VISNINGSNAMN");

        //Display name text
        common.verifyStringOnPage( "Vissa tjänster visar detta " +
                "i stället för förnamn och efternamn.");

        //Language
        common.verifyStringOnPage( "SPRÅK");
    }

    private void verifyLabelsEnglish() {
        //Heading
        common.verifyStringOnPage("Name & language");

        //Text
        common.verifyStringOnPage("Your name and preferred language will be used to personalise some services that you access with eduID.");

        //Given name
        common.verifyStringOnPage("FIRST NAME");

        //Sur name
        common.verifyStringOnPage("LAST NAME");

        //Display name
        common.verifyStringOnPage( "DISPLAY NAME");

        //Display name text
        common.verifyStringOnPage("Some services will show this " +
                "instead of your first and last name.");

        //Language
        common.verifyStringOnPage("LANGUAGE");
    }

    private void verifyUpdatedInfoBar(String language){
        //Verify the saved info message
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div[1]/div/span");
        if(!language.equals("English"))
            common.verifyStringOnPage( "Personlig information sparad");
        else
            common.verifyStringOnPage( "Personal information updated");
    }
}
