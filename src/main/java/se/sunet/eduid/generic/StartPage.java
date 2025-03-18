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
    String  pCreateEduAndConnect = "//p[.//strong]";
    String pEduEasierForYou = "//html/body/div/div/main/div/section/div/div[1]/p[2]";
    String pYouCanReadMore = "//p[.//a[@target='_blank']]";
    String linkSunet = "//a[@target='_blank']";
    String linkHelp = "//*[@id=\"eduid-splash-and-children\"]/div[1]/p[3]/a[2]";

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

    public void verifyLabelsSwedish(){
        common.verifyStringByXpath(h1EduEasierAndSafer,
                "Säkrare och enklare inloggning med eduID");
        common.verifyStringByXpath(pCreateEduAndConnect,
                "Skapa ett eduID och koppla det till din identitet för att få tillgång till tjänster " +
                        "och organisationer som är relaterade till högre utbildning.");
        common.verifyStringByXpath(pEduEasierForYou,
                "eduID är enklare eftersom du bara har en inloggning och säkrare eftersom det är " +
                        "kopplat till en verklig person - dig.");
        common.verifyStringByXpath(pYouCanReadMore,
                "Du kan läsa mer om eduID på Sunet hemsida eller i Hjälp länken i sidfoten. Du kan " +
                        "också registrera dig eller logga in med knapparna nedan!");

        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[1]",
                "Skapa ett grundläggande konto med din e-postadress.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[2]",
                "Bevisa att du är DU.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[3]",
                "Höj din inloggningssäkerhet.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[4]",
                "Höj nivån igen - bevisa att DU loggar in.");

        //Verify link to Sunet and Help page works
        common.verifyXpathIsWorkingLink(linkSunet);
        common.verifyXpathIsWorkingLink(linkHelp);
    }

    private void verifyLabelsEnglish(){
        common.verifyStringByXpath(h1EduEasierAndSafer,
                "Safer and easier login with eduID");
        common.verifyStringByXpath(pCreateEduAndConnect,
                "Create an eduID and connect it to your identity for access to services and " +
                        "organisations related to higher education.");
        common.verifyStringByXpath(pEduEasierForYou,
                "eduID is easier because you only have one login and safer because it's connected to " +
                        "a real individual - you.");
        common.verifyStringByXpath(pYouCanReadMore,
                "You can read more about eduID at Sunet website or in the Help link in the footer. " +
                        "You can also register or log in using the buttons below!");

        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[1]",
                "Create a basic account with your email address.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[2]",
                "Prove that you are YOU.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[3]",
                "Level up your login security.");
        common.verifyStringByXpath("//*[@id=\"eduid-splash-and-children\"]/div[2]/div[4]",
                "Level up again - proving that YOU are logging in.");

        //Verify link to Sunet and Help page works
        common.verifyXpathIsWorkingLink(linkSunet);
        common.verifyXpathIsWorkingLink(linkHelp);
    }
}
