package se.sunet.eduid.dashboard;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.RetryAndScreenShot;
import se.sunet.eduid.utils.WebDriverManager;

public class DeleteAccount {
    private Common common;

    public DeleteAccount(Common common){
        this.common = common;
    }

    public void runDeleteAccount(){
        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a"));

        verifyLabelsSwedish();
        verifyLabelsEnglish();
        clickDelete();
        clickDeleteInPopUp();
    }

    private void clickDelete(){
        common.findWebElementById("delete-button").click();

        verifyPopUpLabels();
    }

    private void clickDeleteInPopUp(){
        //Press delete
        if(common.getDeleteButton()) {
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span").click();

            //Enter userName and password since we need to login again before account is deleted
            Login login = new Login(common);
            login.enterUsernamePassword();

            common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();

        }
        //Press abort
        else {
            common.findWebElementByXpath("//div/div[3]/button[2]/span").click();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/h4/span", "Ta bort eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p/span", "Om du väljer att " +
                "ta bort ditt eduID kommer all information du sparat rensas permanent.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"delete-button\"]/span", "RADERA EDUID");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a").click();

        //Heading
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/h4/span", "Delete eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p/span", "Click the link " +
                "to permanently delete your eduID.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"delete-button\"]/span", "DELETE EDUID");

        //Click on Swedish
        common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();
    }

    private void verifyPopUpLabels(){
        //Heading
        common.explicitWaitVisibilityElement("//div/div[1]/h5/span");
        common.verifyStringOnPage( "Är du säker på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringOnPage("När du tar bort ditt eduID kommer all information du sparat rensas " +
                "permanent. Om du väljer att ta bort ditt eduID kommer du att behöva logga in igen en sista gång.");
    }
}