package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Name {
    private final Common common;
    private final TestData testData;
    String pageBody;
    String changeButton = "//*[@id=\"content\"]/article[2]/div[1]/button";

    public Name(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runName(){
        verifyAndUpdateName();

        if(testData.getLanguage() == null || testData.getLanguage().equals("Svenska"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
    }

    private void verifyAndUpdateName() {
        // Old account, If given name shall be updated else verify the default value
        if(testData.getGivenName().equalsIgnoreCase(common.findWebElementById("first name").getText()) &&
                testData.getSurName().equalsIgnoreCase(common.findWebElementById("last name").getText())
        || testData.isIdentityConfirmed()) {

            //Verify current names
            if(testData.getConfirmIdBy() != null && testData.getConfirmIdBy().equalsIgnoreCase("mail")){
                common.verifyStringById("first name", "Magic Cookie");
                common.verifyStringById("last name", "Testsson");
                common.verifyStringById("display name", "Cookie Testsson");

                testData.setDisplayName("Cookie Testsson");
            }
            else if(testData.getConfirmIdBy() != null && testData.getConfirmIdBy().equalsIgnoreCase("eidas")){
                common.verifyStringById("first name", "Bernt Olof");
                common.verifyStringById("last name", "Larsson");
                common.verifyStringById("display name", "Bernt Olof Larsson");

                //testData.setDisplayName("Bernt Olof Larsson");
            }
            else{
                common.verifyStringById("first name", testData.getGivenName());
                common.verifyStringById("last name", testData.getSurName());

                if(testData.isIdentityConfirmed()) {
                    common.verifyStringById("display name", testData.getDisplayName());
                }
            }
        }
        else{
            //Click on change
            common.click(common.findWebElementByXpath(changeButton));
            updatePersonalInfo();
        }
    }

    private void updatePersonalInfo(){
        Common.log.info("Update of name to: " + testData.getGivenName() + " " + testData.getSurName());

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
            common.click(common.findWebElementByXpath(changeButton));

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
            common.click(common.findWebElementByXpath(changeButton));

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
        Common.log.info("Pressed save for the updated name");
    }

    private void pressSaveButton(){
        // If any value updated we need to save it and verify that the info message appears

        //Click Save button
        common.explicitWaitClickableElementId("personal-data-button");
        common.click(common.findWebElementById("personal-data-button"));
        common.timeoutMilliSeconds(500);
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verify name labels in Swedish");

        //Page title
        common.explicitWaitPageTitle("Identitet | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identitet");

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
            common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div[2]/div[3]/span/strong", "Visningsnamn");
        }
    }

    private void verifyLabelsEnglish() {
        Common.log.info("Verify name labels in English");

        //Page title
        common.explicitWaitPageTitle("Identity | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identity");

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
            common.verifyStringByXpath("//*[@id=\"content\"]/article[2]/div[2]/div[3]/span/strong", "Display name");
        }
    }
}