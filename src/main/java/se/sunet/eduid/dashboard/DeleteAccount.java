package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class DeleteAccount {
    private final Common common;
    private final TestData testData;
    private final String deletButtonInConfirmationPopup = "delete-account-modal-accept-button";

    public DeleteAccount(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDeleteAccount(){
        common.navigateToAccount();

        verifyLabelsSwedish();
        verifyLabelsEnglish();
        clickDelete();
        clickDeleteInPopUp();
    }

    private void clickDelete(){
        log.info("Click delete link in Account");
        common.explicitWaitClickableElementId("delete-button");
        common.click(common.findWebElementById("delete-button"));

        verifyPopUpLabels();
    }

    private void clickDeleteInPopUp(){
        //Press delete
        if(testData.isDeleteButton()) {
            common.explicitWaitClickableElementId(deletButtonInConfirmationPopup);
            common.findWebElementById(deletButtonInConfirmationPopup).click();

            //Enter userName and password since we need to login again before account is deleted
            common.timeoutMilliSeconds(5000);
        }
        //Press abort
        else {
            common.closePopupDialog();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/h2", "Radera eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/p", "Om du väljer att " +
                "ta bort ditt eduID kommer all information du sparat rensas permanent.");

        //Delete eduid link
        common.verifyStringById("delete-button", "radera eduid");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/h2", "Delete eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/p", "Click the link " +
                "to permanently delete your eduID.");

        //Delete eduid link
        common.verifyStringById("delete-button", "delete eduid");

        //Click on Swedish
        common.selectSwedish();
    }

    private void verifyPopUpLabels(){
        log.info("Verify Delete pop up labels and text - Swedish");

        //Heading
        common.timeoutMilliSeconds(500);

        common.explicitWaitClickableElementId(deletButtonInConfirmationPopup);
        common.verifyStringOnPage( "Är du säker på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringOnPage("När du tar bort ditt eduID kommer all information du sparat rensas " +
                "permanent. Om det har gått lång tid sedan du senast loggade in kan det hända att du behöver logga in igen.");

        //Button text
        common.verifyStringById(deletButtonInConfirmationPopup, "RADERA MITT EDUID");

        common.closePopupDialog();

        //Select english
        common.selectEnglish();

        //common.timeoutMilliSeconds(200);
        common.click(common.findWebElementById("delete-button"));
        //common.timeoutMilliSeconds(400);
        common.explicitWaitClickableElementId(deletButtonInConfirmationPopup);
        common.switchToPopUpWindow();

        log.info("Verify Delete pop up labels and text - English");

        //Heading
        common.verifyStringOnPage( "Are you sure you want to delete your eduID?");

        //Text
        common.verifyStringOnPage("Deleting your eduID will permanently remove all your saved " +
                "information. If it has been a long time since you last logged in, you may need to log in again.");

        //Button text
        common.verifyStringById(deletButtonInConfirmationPopup, "DELETE MY EDUID");

        common.closePopupDialog();

        //Select swedish
        common.timeoutMilliSeconds(200);
        common.selectSwedish();

        common.timeoutMilliSeconds(200);

        log.info("Verified Delete pop up labels and text, clicking on Delete link again in Account");
        common.click(common.findWebElementById("delete-button"));
    }

    public void confirmDeleteAfter5Min(){
        common.switchToPopUpWindow();
        log.info("Verify text and labels in pop-up when delete of account demands one more log in");

        common.securityConfirmPopUp("//*[@id=\"delete-button\"]");

        log.info("Clicked on Accept, to delete account and to be forwarded to log in page");
    }
}