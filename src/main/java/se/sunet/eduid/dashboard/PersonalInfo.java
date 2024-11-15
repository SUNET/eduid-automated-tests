package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class PersonalInfo {
    private final Common common;
    private final TestData testData;
    String pageBody;

    public PersonalInfo(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPersonalInfo(){
        common.timeoutMilliSeconds(4500);
        common.navigateToSettings();

        //If new account
        if(testData.isRegisterAccount()) {
            //Click add data
            //common.timeoutMilliSeconds(500);
            //common.click(common.findWebElementById("add-personal-data"));
//TODO add test case when personal data is changed...
//            updatePersonalInfo();
        }
        else
            verifyAndUpdatePersonalInfo();

        //TODO change language
        if(testData.getLanguage() != null)
            selectLanguage();
        if(testData.getLanguage() == null || testData.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyAndUpdatePersonalInfo() {
        // Old account, If given name shall be updated else verify the default value
        if(testData.getGivenName().equalsIgnoreCase(common.findWebElementById("first name").getText()) &&
                testData.getSurName().equalsIgnoreCase(common.findWebElementById("last name").getText())
        || testData.isIdentityConfirmed()) {

            //Verify current names
            common.verifyStringById("first name", testData.getGivenName());
            common.verifyStringById("last name", testData.getSurName());

            if(testData.isIdentityConfirmed()) {
                common.verifyStringById("display name", testData.getDisplayName());
            }
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
            common.click(common.findWebElementById("Svenska"));
        }

        pressSaveButton();
    }

    private void selectLanguage() {
        //Click on change
        //common.findWebElementByXpath("//*[@id=\"content\"]/article[1]/div[1]/button").click();

        //Change to Swedish
        if(testData.getLanguage().equalsIgnoreCase("Svenska")){
            //Verify button text, before change
            common.verifyStringByXpath("//*[@id=\"personaldata-view-form\"]/fieldset/article/div/label[2]/span", "Svenska");

            //Select new language - Swedish
            common.click(common.findWebElementById("Svenska"));

            //pressSaveButton();

            common.timeoutSeconds(1);
        }
        //Change to English
        else if(testData.getLanguage().equalsIgnoreCase("English")){
            //Verify button text, before change
            common.verifyStringByXpath("//*[@id=\"personaldata-view-form\"]/fieldset/article/div/label[1]/span", "English");

            //Select new language - English
            //common.click(common.findWebElementByXpath("//*[@id=\"personaldata-view-form\"]/fieldset[2]/div/label[1]/input"));
            common.click(common.findWebElementById("English"));

            //pressSaveButton();

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
        common.explicitWaitPageTitle("Inställningar | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Inställningar");

        //Heading
        common.verifyPageBodyContainsString(pageBody,  "Namn & Visningsnamn");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Den här informationen kan komma att användas för att anpassa tjänster " +
                "som du når med ditt eduID.");

        //Given name
        common.verifyPageBodyContainsString(pageBody, "Förnamn");

        //Sur name
        common.verifyPageBodyContainsString(pageBody,  "Efternamn");

        //Display name
        if(testData.isIdentityConfirmed()) {
            common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[2]/div[3]/span/strong", "Visningsnamn");
        }
    }

    private void verifyLabelsEnglish() {
        //Page title
        common.explicitWaitPageTitle("Settings | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Settings");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Names & Display Name");

        //Text
        common.verifyPageBodyContainsString(pageBody, "This information may be used to personalise services that you access with your eduID.");

        //Given name
        common.verifyPageBodyContainsString(pageBody, "First name");

        //Sur name
        common.verifyPageBodyContainsString(pageBody, "Last name");

        //Display name
        if(testData.isIdentityConfirmed()) {
            common.verifyStringByXpath("//*[@id=\"content\"]/article[1]/div[2]/div[3]/span/strong", "Display name");
        }
    }
}