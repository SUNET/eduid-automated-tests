package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class StartPage {
    private final Common common;

    public StartPage(Common common){
        this.common = common;
    }

    public void runStartPage(){
        verifyPageTitle();

        if(common.getLanguage().equalsIgnoreCase("English"))
            verifyLabelsEnglish();
        else
            verifyLabelsSwedish();
        if(common.getRegisterAccount())
            registerAccount();
        else
            signIn();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID");
        common.verifyPageTitle("eduID");
    }

    private void signIn(){
        //Click on sign in link
        common.findWebElementByXpath("//*[@id=\"login\"]/a").click();

        //Wait for next page
        common.explicitWaitPageTitle("eduID login");
    }

    private void registerAccount(){
        //Click on sign up button
        common.findWebElementById("register").click();
    }

    private void verifyLabelsSwedish(){
        common.verifyStringByXpath("//section[1]/div/h1", "eduID är enklare och säkrare inloggning.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[1]", "Skapa ett eduID och " +
                "koppla det till ditt svenska personnummer för att kunna komma åt flera olika tjänster och organisationer inom högskolan.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[2]", "eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
        common.verifyStringByXpath("//*[@id=\"login\"]", "Om du redan har ett eduID kan du logga in här");
    }

    private void verifyLabelsEnglish(){
        common.verifyStringByXpath("//section[1]/div/h1", "eduID is easier and safer login.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[1]", "Create an eduID and connect it to your Swedish national identity " +
                "number to gain access to services and organisation related to higher education.");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[2]", "eduID is easier for you because you only have to remember one " +
                "password and safer for the Universities becasue it is connecetd to a real individual.");
        common.verifyStringByXpath("//*[@id=\"login\"]", "If you already have eduID you can log in here.");
    }
}
