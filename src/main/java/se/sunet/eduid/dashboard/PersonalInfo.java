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
        common.explicitWaitPageTitle("eduID dashboard");
        common.explicitWaitVisibilityElement("//div/footer/nav/ul/li[2]");

        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language - needed when new accounts created
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")
                && testData.getLanguage().equalsIgnoreCase("Svenska") && testData.isRegisterAccount()) {
            common.selectSwedish();
        }
    }

    private void verifyAndUpdatePersonalInfo() {
        // Old account, If given name shall be updated else verify the default value
        if(testData.getGivenName().equalsIgnoreCase(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[1]/div").getText()) &&
                testData.getSurName().equalsIgnoreCase(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[2]/div").getText()) &&
                testData.getDisplayName().equalsIgnoreCase(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[3]/div").getText())) {

            //Verify current names
            common.verifyXpathContainsString("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[1]/div", testData.getGivenName());
            common.verifyXpathContainsString("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[2]/div", testData.getSurName());
            common.verifyXpathContainsString("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[3]/div", testData.getDisplayName());
        }
        else{
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button"));
            updatePersonalInfo();
        }
    }

    private void updatePersonalInfo(){
        //Verify placeholder
        common.verifyStrings("Förnamn", common.findWebElementById("given_name").getAttribute("placeholder"));
        common.verifyStrings("Efternamn", common.findWebElementById("surname").getAttribute("placeholder"));
        common.verifyStrings("Visningsnamn", common.findWebElementById("display_name").getAttribute("placeholder"));

        //English
        common.selectEnglish();

        //Click add or change link, depending on register new account or change of existing data
        if(testData.isRegisterAccount())
            common.click(common.findWebElementById("add-personal-data"));
        else
            common.click(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button"));

        //Verify placeholder
        common.verifyStrings("First name", common.findWebElementById("given_name").getAttribute("placeholder"));
        common.verifyStrings("Last name", common.findWebElementById("surname").getAttribute("placeholder"));
        common.verifyStrings("Display name", common.findWebElementById("display_name").getAttribute("placeholder"));

        //Swedish
        common.selectSwedish();

        //Click add or change link, depending on register new account or change of existing data
        if(testData.isRegisterAccount())
            common.click(common.findWebElementById("add-personal-data"));
        else
            common.click(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article/div/div[1]/button"));

        common.findWebElementById("given_name").clear();
        common.findWebElementById("given_name").sendKeys(testData.getGivenName());
        common.verifyStrings(testData.getGivenName(), common.getAttributeById("given_name"));

        common.findWebElementById("surname").clear();
        common.findWebElementById("surname").sendKeys(testData.getSurName());
        common.verifyStrings(testData.getSurName(), common.getAttributeById("surname"));

        common.findWebElementById("display_name").clear();
        common.findWebElementById("display_name").sendKeys(testData.getDisplayName());
        common.verifyStrings(testData.getDisplayName(), common.getAttributeById("display_name"));

        //Display text
        common.verifyStringOnPage("För- och efternamn kommer att ersättas med de från folkbokföringen " +
                "om du verifierar ditt eduID med ditt personummer.");

        //Display name text
        common.verifyStringOnPage( "vissa tjänster visar detta " +
                "i stället för förnamn och efternamn.");

        //If new account
        if(testData.isRegisterAccount()) {
            common.click(common.findWebElementById("Svenska"));
        }

        pressAddButton();
    }

    private void selectLanguage() {
        //Change to Swedish
        if((testData.getLanguage().equalsIgnoreCase("Svenska") && !testData.getLanguage().equalsIgnoreCase(
                common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[4]/div").getText()))) {
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[1]/button"));

            //Select new language - Swedish
            common.click(common.findWebElementById("Svenska"));

            //Verify button text
            common.verifyStringById("personal-data-button", "SAVE");

            pressAddButton();

            common.timeoutSeconds(1);
        }
        //Change to English
        else if(testData.getLanguage().equalsIgnoreCase("English") && !testData.getLanguage().equalsIgnoreCase(
                common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[2]/div[4]/div").getText())){
            //Click on change
            common.click(common.findWebElementByXpath("//*[@id=\"text-content\"]/div/article[1]/div/div[1]/button"));

            //Select new language - English
            common.click(common.findWebElementById("English"));

            //Verify button text
            common.verifyStringById("personal-data-button", "SPARA");

            pressAddButton();

            common.timeoutSeconds(1);
        }
    }

    private void pressAddButton(){
        // If any value updated we need to save it and verify that the info message appears

        //Click Add button
        common.explicitWaitClickableElementId("personal-data-button");
        common.click(common.findWebElementById("personal-data-button"));
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
