package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_35 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        testData.setAcceptTerms(false);
        register.runRegister(); }

    @Test( dependsOnMethods = {"register"} )
    void registerSameEmailAgain(){
        //Select english, just to get placeholder text in english
        common.selectEnglish();

        //Enter email and press register
        register.enterEmailAndPressRegister();
    }

    @Test( dependsOnMethods = {"registerSameEmailAgain"} )
    void verifyMultipleRegisterBlocked(){
        //Accept terms & conditions
        common.click(common.findWebElementById("accept-button"));

        //Cancel email response code at confirm email page
        common.click(common.findWebElementById("response-code-abort-button"));

        //Add the same email adress again
        register.enterEmailAndPressRegister();

        //Accept terms & conditions
        common.click(common.findWebElementById("accept-button"));

        //Verify status message - english
        common.verifyStatusMessage("Too many attempts to create account have been made. Please try again later.");

        //Verify status message - swedish
        common.selectSwedish();
        common.verifyStatusMessage("För många försök att skapa ett konto har gjorts. Försök igen senare.");
    }
}
