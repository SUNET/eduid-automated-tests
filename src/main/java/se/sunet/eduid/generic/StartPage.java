package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class StartPage {
    private final Common common;
    private final TestData testData;

    public StartPage(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runStartPage(){
        verifyPageTitle();

        if(testData.getLanguage() != null && testData.getLanguage().equalsIgnoreCase("English"))
            verifyLabelsEnglish();
        else
            verifyLabelsSwedish();
        if(testData.isRegisterAccount())
            registerAccount();
        else
            signIn();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");

        //Verify footer
        common.verifyStringOnPage("©2013-" +common.getDate().toString().substring(0,4));
    }

    private void signIn(){
        //Click on sign in link
        common.click(common.findWebElementById("login-button"));

        //Wait for log in page
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementById("sign-up-button"));
    }

    private void verifyLabelsSwedish(){
        common.verifyStringOnPage("eduID är enklare och säkrare inloggning.");
        common.verifyStringOnPage("Skapa ett eduID och koppla det till din identitet för att kunna " +
                "komma åt flera olika tjänster och organisationer inom högskolan.");
        common.verifyStringOnPage("eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
    }

    private void verifyLabelsEnglish(){
        common.verifyStringOnPage("eduID is easier and safer login.");
        common.verifyStringOnPage("Create an eduID and connect it to your identity to gain access to " +
                "services and organisations related to higher education.");
        common.verifyStringOnPage("eduID is easier for you because you only have to remember one " +
                "password and safer for the Universities becasue it is connecetd to a real individual.");
    }
}
