package se.sunet.eduid.dashboard;

import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class DeleteAccount {
    private final Common common;
    private final TestData testData;

    public DeleteAccount(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDeleteAccount(){
        //TODO temp fix to get swedish language//*[@id="language-selector"]/span
        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
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
            common.timeoutSeconds(2);
            common.click(common.findWebElementById("delete-account-modal-accept-button"));

            //Enter userName and password since we need to login again before account is deleted
            common.timeoutSeconds(1);
            Login login = new Login(common, testData);
            login.enterPassword();

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
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div[1]/h3", "Radera eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p", "Om du väljer att " +
                "ta bort ditt eduID kommer all information du sparat rensas permanent.");

        //Delete eduid link
        common.verifyStringByXpath("//*[@id=\"delete-button\"]", "radera eduid");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div[1]/h3", "Delete eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"delete-account-container\"]/div/p", "Click the link " +
                "to permanently delete your eduID.");

        //Delete eduid link
        common.verifyStringByXpath("//*[@id=\"delete-button\"]", "delete eduid");

        //Click on Swedish
        common.selectSwedish();
    }

    private void verifyPopUpLabels(){
        //Heading
        common.timeoutSeconds(2);

        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage( "Är du säker på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringOnPage("När du tar bort ditt eduID kommer all information du sparat rensas " +
                "permanent. Om du väljer att ta bort ditt eduID kommer du att behöva logga in igen en sista gång.");

        common.closePopupDialog();

        //Select english
        common.selectEnglish();

        common.timeoutSeconds(2);
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

        common.timeoutSeconds(2);
        common.click(common.findWebElementById("delete-button"));
    }
}