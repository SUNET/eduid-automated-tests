package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class PersonalInfo {
    private final Common common;
    private final TestData testData;

    public PersonalInfo(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPersonalInfo(){
        common.navigateToSettings();
        verifyPageTitle();
        //If new account
        if(testData.isRegisterAccount()) {
            //Click add data
            common.timeoutMilliSeconds(500);
            common.click(common.findWebElementById("add-personal-data"));

            updatePersonalInfo();
        }
        else
            verifyAndUpdatePersonalInfo();
        selectLanguage();
        if(testData.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyPageTitle() {
        //wait for delete button to be clickable
        //common.explicitWaitClickableElementId("delete-button");

        //TODO temp fix to get swedish language - needed when new accounts created
/*        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")
                && testData.getLanguage().equalsIgnoreCase("Svenska") && testData.isRegisterAccount()) {
            common.selectSwedish();
        }*/
    }

    private void verifyAndUpdatePersonalInfo() {
        // Old account, If given name shall be updated else verify the default value
        if(testData.getGivenName().equalsIgnoreCase(common.findWebElementById("first name").getText()) &&
                testData.getSurName().equalsIgnoreCase(common.findWebElementById("last name").getText())) {

            //Verify current names
            common.verifyStringById("first name", testData.getGivenName());
            common.verifyStringById("last name", testData.getSurName());
        }
        else{
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button"));
            updatePersonalInfo();
        }
    }

    private void updatePersonalInfo(){
        //Verify placeholder
        common.verifyPlaceholder("Förnamn", "given_name");
        common.verifyPlaceholder("Efternamn", "surname");

        //Verify fine text
        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen " +
                "om du verifierar ditt eduID med ditt personummer.");

        //English
        common.selectEnglish();

        //Click add or change link, depending on register new account or change of existing data
        if(testData.isRegisterAccount())
            common.click(common.findWebElementById("add-personal-data"));
        else
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button"));

        //Verify placeholder
        common.verifyPlaceholder("First name", "given_name");
        common.verifyPlaceholder("Last name", "surname");

        //Verify fine text
        common.verifyStringOnPage("First and last name will be replaced with your legal name if you " +
                "verify your eduID with your personal id number.");

        //Swedish
        common.selectSwedish();

        //Click add or change link, depending on register new account or change of existing data
        if(testData.isRegisterAccount())
            common.click(common.findWebElementById("add-personal-data"));
        else
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button"));

        //Note! for some unknown reason I need to clear and fill in givenname twice
        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElementById("given_name"));
        common.findWebElementById("given_name").sendKeys(testData.getGivenName());

        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElementById("given_name"));
        common.findWebElementById("given_name").sendKeys(testData.getGivenName());

        common.verifyStrings(testData.getGivenName(), common.getAttributeById("given_name"));


        //Note! for some unknown reason I need to clear and fill in surname twice
        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElementById("surname"));
        common.findWebElementById("surname").sendKeys(testData.getSurName());

        common.timeoutMilliSeconds(500);
        common.clearTextField(common.findWebElementById("surname"));
        common.findWebElementById("surname").sendKeys(testData.getSurName());

        common.verifyStrings(testData.getSurName(), common.getAttributeById("surname"));

        //Display text
        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen " +
                "om du verifierar ditt eduID med ditt personummer.");

        //If new account, select Swedish
        if(testData.isRegisterAccount()) {
            common.click(common.findWebElementByXpath("//*[@id=\"personaldata-view-form\"]/fieldset[2]/div/label[2]/input"));
        }

        pressSaveButton();
    }

    private void selectLanguage() {
        //Change to Swedish
        if((testData.getLanguage().equalsIgnoreCase("Svenska") && !testData.getLanguage().equalsIgnoreCase(
                common.findWebElementById("language").getText()))) {
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button"));

            //Verify button text, before change
            common.verifyStringById("personal-data-button", "SAVE");

            //Select new language - Swedish
            //common.click(common.findWebElementByXpath("//*[@id=\"personaldata-view-form\"]/fieldset[2]/div/label[2]/input"));
            common.click(common.findWebElementById("Svenska"));

            pressSaveButton();

            common.timeoutSeconds(1);
        }
        //Change to English
        else if(testData.getLanguage().equalsIgnoreCase("English") && !testData.getLanguage().equalsIgnoreCase(
                common.findWebElementById("language").getText())){
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button"));

            //Verify button text, before change
            common.verifyStringById("personal-data-button", "SPARA");

            //Select new language - English
            //common.click(common.findWebElementByXpath("//*[@id=\"personaldata-view-form\"]/fieldset[2]/div/label[1]/input"));
            common.click(common.findWebElementById("English"));

            pressSaveButton();

            common.timeoutSeconds(1);
        }
    }

    private void pressSaveButton(){
        // If any value updated we need to save it and verify that the info message appears

        //Click Save button
        common.explicitWaitClickableElementId("personal-data-button");
        common.click(common.findWebElementById("personal-data-button"));
        common.timeoutMilliSeconds(500);
    }

    private void verifyLabelsSwedish() {
        //Page title
        common.verifyPageTitle("Inställningar | eduID");

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Inställningar");

        //Heading
        common.verifyStringOnPage( "Namn & språk");

        //Text
        common.verifyStringOnPage("Den här informationen kan komma att användas för att anpassa tjänster " +
                "som du når med ditt eduID.");

        //Given name
        common.verifyStringOnPage("Förnamn");

        //Sur name
        common.verifyStringOnPage( "Efternamn");

        //Language
        common.verifyStringOnPage( "Språk");
    }

    private void verifyLabelsEnglish() {
        //Page title
        common.verifyPageTitle("Settings | eduID");

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Settings");

        //Heading
        common.verifyStringOnPage("Name & language");

        //Text
        common.verifyStringOnPage("This information may be used to personalise services that you access with your eduID.");

        //Given name
        common.verifyStringOnPage("First name");

        //Sur name
        common.verifyStringOnPage("Last name");

        //Language
        common.verifyStringOnPage("Language");
    }
}