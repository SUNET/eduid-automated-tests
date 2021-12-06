package se.sunet.eduid.dashboard;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class DeleteAccount {
    private final Common common;
    private final TestData testData;

    public DeleteAccount(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDeleteAccount(){
        //TODO temp fix to get swedish language
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
            common.findWebElementByLinkText("Svenska").click();

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
        if(testData.isDeleteButton()) {
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]/span").click();

            //Enter userName and password since we need to login again before account is deleted
            common.timeoutSeconds(1);
            Login login = new Login(common, testData);
            login.enterUsernamePassword();

            common.findWebElementById("login-form-button").click();

            common.timeoutMilliSeconds(1500);
        }
        //Press abort
        else {
            common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/div/button").click();
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