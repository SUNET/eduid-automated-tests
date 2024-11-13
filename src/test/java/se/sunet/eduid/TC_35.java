package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_35 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void initiateRegistrationAbortAtTerms(){
        testData.setAcceptTerms(false);
        register.runRegister(); }

    @Test( dependsOnMethods = {"initiateRegistrationAbortAtTerms"} )
    void initiateRegisterSameEmailAgain(){
        //Select english, just to get placeholder text in english
        common.selectEnglish();

        //Enter email and press register
        testData.setRegisterAccount(false);
        testData.setGenerateUsername(false);
        register.enterEmailAndPressRegister();
    }

    @Test( dependsOnMethods = {"initiateRegisterSameEmailAgain"} )
    void acceptTerms(){
        //Accept terms & conditions
        common.click(common.findWebElementById("accept-button"));

        //Wait for cancel email verification button
        common.explicitWaitClickableElementId("response-code-abort-button");
    }

    @Test( dependsOnMethods = {"acceptTerms"} )
    void cancelVerifyEmail(){
        //Cancel email response code at confirm email page
        common.click(common.findWebElementById("response-code-abort-button"));

        //Wait for given name field at register page
        common.explicitWaitVisibilityElementId("given_name");
    }

    @Test( dependsOnMethods = {"cancelVerifyEmail"} )
    void initiateRegisterSameEmailAgain3rdTime(){
        //Add the same user credentials a 3rd time
        //register.enterEmailAndPressRegister();
        common.selectSwedish();

        //register.runRegister();

        //Accept terms & conditions
        //common.click(common.findWebElementById("accept-button"));
    }

    @Test( dependsOnMethods = {"cancelVerifyEmail"} )
    void verifyMultipleRegisterBlocked(){
        //Verify status message - english
        common.verifyStatusMessage("Too many attempts to create account have been made. Please try again later.");

        //Verify status message - swedish
        common.selectSwedish();
        common.verifyStatusMessage("För många försök att skapa ett konto har gjorts. Försök igen senare.");
    }
}
