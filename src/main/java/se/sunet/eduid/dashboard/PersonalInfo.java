package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;

public class PersonalInfo {
    private Common common;

    public PersonalInfo(Common common){
        this.common = common;
    }

    public void runPersonalInfo(){
        verifyPageTitle();
        //If new account
        if(common.getRegisterAccount()) {
            //Click add data
            common.findWebElementById("add-personal-data").click();
            updatePersonalInfo();
        }
        else
            verifyAndUpdatePersonalInfo();
        selectLanguage();
        if(common.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID dashboard");
        common.explicitWaitVisibilityElement("//div/footer/nav/ul/li[2]");

        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language - needed when new accounts created
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")
                && common.getLanguage().equalsIgnoreCase("Svenska") && common.getRegisterAccount()) {
            common.findWebElementByLinkText("Svenska").click();
        }
    }

    private void verifyAndUpdatePersonalInfo() {
        // Old account, If given name shall be updated else verify the default value
        if(common.getGivenName().equals(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[1]/p").getText()) &&
                common.getSurName().equals(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[2]/p").getText()) &&
                    common.getDisplayName().equals(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[3]/p").getText())) {

            //Verify current names
            common.verifyStrings(common.getGivenName(), common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[1]/p").getText());
            common.verifyStrings(common.getSurName(), common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[2]/p").getText());
            common.verifyStrings(common.getDisplayName(), common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[3]/p").getText());
        }
        else{
            //Click on change
            common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button/span").click();

            updatePersonalInfo();
        }
    }

    private void updatePersonalInfo(){
        common.findWebElementById("given_name").clear();
        common.findWebElementById("given_name").sendKeys(common.getGivenName());
        common.verifyStrings(common.getGivenName(), common.getAttributeById("given_name"));

        common.findWebElementById("surname").clear();
        common.findWebElementById("surname").sendKeys(common.getSurName());
        common.verifyStrings(common.getSurName(), common.getAttributeById("surname"));

        common.findWebElementById("display_name").clear();
        common.findWebElementById("display_name").sendKeys(common.getDisplayName());
        common.verifyStrings(common.getDisplayName(), common.getAttributeById("display_name"));

        //Display text
        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen " +
                "om du verifierar ditt eduID med ditt personummer.");

        //Display name text
        common.verifyStringOnPage( "vissa tjänster visar detta " +
                "i stället för förnamn och efternamn.");

        //If new account
        if(common.getRegisterAccount()) {
            common.findWebElementById("Svenska").click();
        }

        pressAddButton();
    }

    private void selectLanguage() {
        //Change to Swedish
        if((common.getLanguage().equalsIgnoreCase("Svenska") && !common.getLanguage().equalsIgnoreCase(
                common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[4]/p").getText()))) {
            //Click on change
            common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button/span").click();

            //Select new language - Swedish
            common.findWebElementById("Svenska").click();

            //Verify button text
            common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "SAVE");

            pressAddButton();
        }
        //Change to English
        else if(common.getLanguage().equalsIgnoreCase("English") && !common.getLanguage().equalsIgnoreCase(
                common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[2]/div[4]/p").getText())){
            //Click on change
            common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button/span").click();

            //Select new language - English
            common.findWebElementById("English").click();

            //Verify button text
            common.verifyStringByXpath("//*[@id=\"personal-data-button\"]/span", "SPARA");

            pressAddButton();
        }
    }

    private void pressAddButton(){
        // If any value updated we need to save it and verify that the info message appears

        //Click Add button
        common.explicitWaitClickableElementId("personal-data-button");
        common.findWebElementById("personal-data-button").click();
        common.timeoutMilliSeconds(500);
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringOnPage( "Namn & språk");

        //Text
        common.verifyStringOnPage("Den här informationen kan komma att användas för att anpassa tjänster " +
                "som du når med ditt eduID.");

        //Given name
        common.verifyStringOnPage("Förnamn");

        //Sur name
        common.verifyStringOnPage( "Efternamn");

        //Display name
        common.verifyStringOnPage( "Visningsnamn");

        //Language
        common.verifyStringOnPage( "Språk");
    }

    private void verifyLabelsEnglish() {
        //Heading
        common.verifyStringOnPage("Name & language");

        //Text
        common.verifyStringOnPage("This information may be used to personalise services that you access with your eduID.");

        //Given name
        common.verifyStringOnPage("First name");

        //Sur name
        common.verifyStringOnPage("Last name");

        //Display name
        common.verifyStringOnPage( "Display name");

        //Language
        common.verifyStringOnPage("Language");
    }
}
