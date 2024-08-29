package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class PasswordChanged {
    private final Common common;
    private final TestData testData;

    public PasswordChanged(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runPasswordChanged(){
        verifyPageTitle();
        verifyLabels();
        clickReturnToLoginLink();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Återställ Lösenord | eduID");;
    }

    private void verifyLabels(){
        //verify the labels - swedish
        common.verifyStringOnPage("Återställ lösenord: Slutförd");
        common.verifyStringOnPage("Ditt lösenord är nu uppdaterat. Se till att förvara ditt lösenord på " +
                "ett säkert sätt för framtida användning. När du har loggat in är det möjligt att ändra ditt lösenord.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password only visible when the recommended password is used
        if(testData.isUseRecommendedPw()) {
            common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");
            common.verifyStringById("user-password", testData.getPassword());
        }

        common.verifyStringById("reset-password-finished", "Gå till eduID för att logga in");

        //Switch language to english
        common.selectEnglish();

        //verify the labels - english
        common.verifyStringOnPage("Reset Password: Completed");
        common.verifyStringOnPage("You have successfully updated your password. Make sure to store " +
                "your password securely for future use. Once you've logged in it is possible to change your password.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringById("user-email", testData.getUsername().toLowerCase());

        //Password only visible when the recommended password is used
        if(testData.isUseRecommendedPw()) {
            common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");
            common.verifyStringById("user-password", testData.getPassword());
        }

        common.verifyStringById("reset-password-finished", "Go to eduID to login");

        //Switch language to swedish
        common.selectSwedish();
    }

    private void clickReturnToLoginLink() {
        //Return to landing page, click with javascript is needed here...
        common.click(common.findWebElementById("reset-password-finished"));

        //Page is redirected a few times so wait for the correct page title
        common.explicitWaitPageTitle("Logga in | eduID");
    }
}