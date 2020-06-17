package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class StartPage {
    private Common common;

    public StartPage(Common common){
        this.common = common;
    }

    public void runStartPage(){
        verifyPageTitle();

        if(!common.getLanguage().equalsIgnoreCase("English"))
            verifyLabelsSwedish();
        else
            verifyLabelsEnglish();
        if(common.getRegisterAccount())
            registerAccount();
        else
            signIn();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void signIn(){
        common.findWebElementByXpath("//*[@id=\"login\"]/a").click();
    }

    private void registerAccount(){
        //Click on sign up button
        common.findWebElementById("register").click();
    }

    private void verifyLabelsSwedish(){
        common.verifyStringOnPage("eduID är enklare och säkrare inloggning.");
        common.verifyStringOnPage("Skapa ett eduID och koppla det till ditt svenska personnummer för " +
                "att kunna komma åt flera olika tjänster och organisationer inom högskolan.");
        common.verifyStringOnPage("eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
        common.verifyStringOnPage("Om du redan har ett eduID kan du logga in här");
    }

    private void verifyLabelsEnglish(){
        common.verifyStringOnPage("eduID is easier and safer login.");
        common.verifyStringOnPage("Create an eduID and connect it to your Swedish national identity " +
                "number to gain access to services and organisation related to higher education.");
        common.verifyStringOnPage("eduID is easier for you because you only have to remember one " +
                "password and safer for the Universities becasue it is connecetd to a real individual.");
        common.verifyStringOnPage("If you already have eduID you can log in here.");
    }
}
