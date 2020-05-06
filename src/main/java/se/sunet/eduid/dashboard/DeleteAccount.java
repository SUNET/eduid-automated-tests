package se.sunet.eduid.dashboard;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;

public class DeleteAccount {
    private Common common;

    public DeleteAccount(Common common){
        this.common = common;
    }

    public void runDeleteAccount(boolean deleteButton, String username, String password){
        verifyLabelsSwedish();
        verifyLabelsEnglish();
        clickDelete();
        clickDeleteInPopUp(deleteButton, username, password);
    }

    private void clickDelete(){
        common.findWebElementById("delete-button").click();

        common.switchToPopUpWindow();
        verifyPopUpLabels();
    }

    private void clickDeleteInPopUp(boolean deleteButton, String username, String password){
        if(deleteButton) {
            common.click(common.findWebElementByXpath("//*[@id=\"delete-account-modal\"]/div/div[2]/button/span"));

            //Enter userName and password since we need to login again before account is deleted
            Login login = new Login(common);
            login.enterUsernamePassword(username, password, false);
            common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button"));
        }
        else
            common.click(common.findWebElementByXpath("//*[@id=\"delete-account-modal\"]/div/div[3]/button/span"));
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
        common.click(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[1]/a"));

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
        common.explicitWaitVisibilityElement("//*[@id=\"delete-account-modal\"]/div/div[1]/h5/span");
        common.verifyStringByXpath("//*[@id=\"delete-account-modal\"]/div/div[1]/h5/span", "Är du säker " +
                "på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-modal\"]/div/div[2]/p[1]/span", "När du tar " +
                "bort ditt eduID kommer all information du sparat rensas permanent.");

        //Text
        common.verifyStringOnPage("Om du väljer att ta bort ditt eduID kommer du att behöva logga in igen en sista gång.");
    }
}