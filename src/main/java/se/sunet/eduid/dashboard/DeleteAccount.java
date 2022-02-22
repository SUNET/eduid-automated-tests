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
            common.selectSwedish();

        verifyLabelsSwedish();
        verifyLabelsEnglish();
        clickDelete();
        clickDeleteInPopUp();
    }

    private void clickDelete(){
        common.click(common.findWebElementById("delete-button"));

        verifyPopUpLabels();
    }

    private void clickDeleteInPopUp(){
        //Press delete
        if(testData.isDeleteButton()) {
            common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button"));

            //Enter userName and password since we need to login again before account is deleted
            common.timeoutSeconds(1);
            Login login = new Login(common, testData);
            login.verifyPageTitle();
            login.enterUsernamePassword();

            common.click(common.findWebElementById("login-form-button"));

            common.timeoutMilliSeconds(1500);
        }
        //Press abort
        else {
            common.closePopupDialog();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/h4", "Ta bort eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p", "Om du väljer att " +
                "ta bort ditt eduID kommer all information du sparat rensas permanent.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"delete-button\"]", "RADERA EDUID");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/h4", "Delete eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p", "Click the link " +
                "to permanently delete your eduID.");

        //Add more phone numbers
        common.verifyStringByXpath("//*[@id=\"delete-button\"]", "DELETE EDUID");

        //Click on Swedish
        common.selectSwedish();
    }

    private void verifyPopUpLabels(){
        //Heading
        common.explicitWaitVisibilityElement("//div/div[1]/h5");
        common.verifyStringOnPage( "Är du säker på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringOnPage("När du tar bort ditt eduID kommer all information du sparat rensas " +
                "permanent. Om du väljer att ta bort ditt eduID kommer du att behöva logga in igen en sista gång.");


        common.closePopupDialog();

        //Select english
        common.selectEnglish();

        common.click(common.findWebElementById("delete-button"));

        //Heading
        common.explicitWaitVisibilityElement("//div/div[1]/h5");
        common.verifyStringOnPage( "Are you sure you want to delete your eduID?");

        //Text
        common.verifyStringOnPage("Deleting your eduID will permanently remove all your saved " +
                "information. After clicking the button you need to use your log in details one final time");


        common.closePopupDialog();

        //Select swedish
        common.selectSwedish();

        common.click(common.findWebElementById("delete-button"));
    }
}