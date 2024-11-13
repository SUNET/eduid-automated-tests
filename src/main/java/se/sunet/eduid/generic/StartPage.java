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
        common.explicitWaitClickableElementId("login-button");
        common.findWebElementById("login-button").click();

        //Wait for log in page
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementById("sign-up-button"));
    }

    private void verifyLabelsSwedish(){
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/h1",
                "eduID är enklare och säkrare inloggning.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[1]/strong",
                "Skapa ett eduID och koppla det till din identitet för att kunna " +
                "komma åt flera olika tjänster och organisationer inom högskolan.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[2]",
                "eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[3]",
                "Du kan läsa mer om eduID på Sunet");

        //Verify link to Sunet page works
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[3]/a");
    }

    private void verifyLabelsEnglish(){
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/h1",
                "eduID is easier and safer login.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[1]/strong",
                "Create an eduID and connect it to your identity to gain access to " +
                "services and organisations related to higher education.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[2]",
                "eduID is easier for you because you only have to remember one " +
                "password and safer for the Universities becasue it is connecetd to a real individual.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[3]",
                "You can read more about eduID at Sunet");

        //Verify link to Sunet page works
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/div[1]/p[3]/a");
    }
}
