package se.sunet.eduid.resetPassword;

import se.sunet.eduid.utils.Common;

public class PasswordChanged {
    private final Common common;

    public PasswordChanged(Common common){
        this.common = common;
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
        common.verifyStringOnPage("Detta är ditt nya lösenord för eduID. Spara lösenordet! När du har " +
                "loggat in är det möjligt att byta ditt lösenord.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "E-postadress");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Lösenord");

        common.verifyStringById("reset-password-finished", "Gå till eduID för att logga in");

        //Switch language to english
        common.selectEnglish();

        //verify the labels - english
        common.verifyStringOnPage("Reset Password: Completed");
        common.verifyStringOnPage("This is your new password for eduID. Save the password! Once you've " +
                "logged in it is possible to change your password.");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[1]/label", "Email address");
        common.verifyStringByXpath("//*[@id=\"email-display\"]/fieldset[2]/label", "Password");

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