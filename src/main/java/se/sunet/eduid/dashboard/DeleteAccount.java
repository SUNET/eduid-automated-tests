package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class DeleteAccount {
    private final Common common;
    private final TestData testData;
    private final String deleteButtonInConfirmationPopup = "delete-account-modal-accept-button";
    private final String closeButtonInConfirmationPopup = "delete-account-modal-close-button";

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
            common.explicitWaitClickableElementId(deleteButtonInConfirmationPopup);
            common.findWebElementById(deleteButtonInConfirmationPopup).click();

            //Enter userName and password since we need to login again before account is deleted
            common.timeoutMilliSeconds(5000);
        }
        //Press abort
        else {
            common.findWebElementById(closeButtonInConfirmationPopup).click();
        }
    }

    private void verifyLabelsSwedish() {
        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/h2", "Spärra och radera eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/p", "Klicka på länken för " +
                "att radera ditt eduID. Det spärrar all åtkomst till kontot om du inte väljer att byta lösenord inom " +
                "en vecka, sedan rensas all information du sparat permanent.");

        //Delete eduid link
        common.verifyStringById("delete-button", "radera eduid");
    }

    private void verifyLabelsEnglish() {
        //Click on English
        common.selectEnglish();

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/h2", "Block and delete eduID");

        //Text
        common.verifyStringByXpath("//*[@id=\"content\"]/article[7]/p", "Click the link to " +
                "delete your eduID. It will block any access to the account unless you change your password within one " +
                "week, after which it will be removed permanently.");

        //Delete eduid link
        common.verifyStringById("delete-button", "delete eduid");

        //Click on Swedish
        common.selectSwedish();
    }

    private void verifyPopUpLabels(){
        log.info("Verify Delete pop up labels and text - Swedish");

        //Heading
        common.timeoutMilliSeconds(500);

        common.explicitWaitClickableElementId(deleteButtonInConfirmationPopup);
        common.verifyStringOnPage( "Är du säker på att du vill ta bort ditt eduID?");

        //Text
        common.verifyStringOnPage("När du tar bort ditt eduID kommer all information du sparat rensas " +
                "permanent. Om det har gått lång tid sedan du senast loggade in kan det hända att du behöver logga in igen.");

        //Button text
        common.verifyStringById(deleteButtonInConfirmationPopup, "RADERA MITT EDUID");

        //close pop up dialog
        common.findWebElementById(closeButtonInConfirmationPopup).click();

        //Select english
        common.selectEnglish();

        //common.timeoutMilliSeconds(200);
        common.click(common.findWebElementById("delete-button"));
        //common.timeoutMilliSeconds(400);
        common.explicitWaitClickableElementId(deleteButtonInConfirmationPopup);
        common.switchToPopUpWindow();

        log.info("Verify Delete pop up labels and text - English");

        //Heading
        common.verifyStringOnPage( "Are you sure you want to delete your eduID?");

        //Text
        common.verifyStringOnPage("Deleting your eduID will permanently remove all your saved " +
                "information. If it has been a long time since you last logged in, you may need to log in again.");

        //Button text
        common.verifyStringById(deleteButtonInConfirmationPopup, "DELETE MY EDUID");

        //close pop up dialog
        common.findWebElementById(closeButtonInConfirmationPopup).click();

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

        common.securityConfirmPopUp("//*[@id=\"delete-button\"]",
                "",
                "");

        log.info("Clicked on Accept, to delete account and to be forwarded to log in page");
    }
}