package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class StartPage {
    private final Common common;
    private final TestData testData;

    //Buttons and text at page
    String buttonSign = "sign-up-button";
    String buttonLogin = "login-button";
    String h1EduEasierAndSafer = "//h1";
    String pCreateEduAndConnect = "//p[.//strong]";
    String pEduEasierForYou = "/html/body/div/main/div/section/div/div[1]/p[2]";
    String pYouCanReadMore = "//p[.//a[@target='_blank']]";
    String linkSunet = "//a[@target='_blank']";

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
        common.explicitWaitClickableElementId(buttonLogin);

        common.findWebElementById(buttonLogin).click();

        //Wait for log in page
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementById(buttonSign));
    }

    private void verifyLabelsSwedish(){
        common.verifyStringByXpath(h1EduEasierAndSafer,
                "eduID är enklare och säkrare inloggning.");
        common.verifyStringByXpath(pCreateEduAndConnect,
                "Skapa ett eduID och koppla det till din identitet för att kunna " +
                "komma åt flera olika tjänster och organisationer inom högskolan.");
        common.verifyStringByXpath(pEduEasierForYou,
                "eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
        common.verifyStringByXpath(pYouCanReadMore,
                "Du kan läsa mer om eduID på Sunet");

        //Verify link to Sunet page works
        common.verifyXpathIsWorkingLink(linkSunet);
    }

    private void verifyLabelsEnglish(){
        common.verifyStringByXpath(h1EduEasierAndSafer,
                "eduID is easier and safer login.");
        common.verifyStringByXpath(pCreateEduAndConnect,
                "Create an eduID and connect it to your identity to gain access to " +
                "services and organisations related to higher education.");
        common.verifyStringByXpath(pEduEasierForYou,
                "eduID is easier for you because you only have to remember one " +
                "password and safer for the Universities becasue it is connecetd to a real individual.");
        common.verifyStringByXpath(pYouCanReadMore,
                "You can read more about eduID at Sunet");

        //Verify link to Sunet page works
        common.verifyXpathIsWorkingLink(linkSunet);
    }
}
