package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_35 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);

        testData.setGenerateUsername(false);
        testData.setUsername("OsvslUDA@dev.eduid.sunet.se");
        testData.setGivenName("Telma");
        testData.setSurName("Olari");


        startPage.runStartPage();

    }

    @Test( dependsOnMethods = {"startPage"})
    void firstInitiateRegistrationAbortAtTerms(){
        testData.setAcceptTerms(true);

        register.enterEmailAndPressRegister();

        try {
            if (common.findWebElementById("value").isDisplayed())
                register.enterCaptchaCode();
        }catch (Exception ex){
            System.out.println("dsfklasf");
        }
        common.selectEnglish();
        register.registerPopUp();

        //Wait for cancel email verification button
        common.explicitWaitClickableElementId("response-code-abort-button");
//        register.runRegister();

        //Cancel email response code at confirm email page
        common.click(common.findWebElementById("response-code-abort-button"));

        //Wait for given name field at register page
        common.explicitWaitVisibilityElementId("given_name");
        }

    @Test( dependsOnMethods = {"firstInitiateRegistrationAbortAtTerms"} )
    void secondInitiateRegisterSameEmailAgain(){
        //Select english, just to get placeholder text in english
        common.selectEnglish();

        //Enter email and press register
        testData.setRegisterAccount(false);
        testData.setGenerateUsername(false);
        register.enterEmailAndPressRegister();
    }
/*
    @Test( dependsOnMethods = {"secondInitiateRegisterSameEmailAgain"} )
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
    void thirdInitiateRegisterSameEmailAgain(){
        //Add the same user credentials a 3rd time
        //register.enterEmailAndPressRegister();
        common.selectSwedish();


        //Enter email and press register
        testData.setRegisterAccount(false);
        testData.setGenerateUsername(false);
        register.enterEmailAndPressRegister();

        //register.runRegister();

        //Accept terms & conditions
        //common.click(common.findWebElementById("accept-button"));
    }

    @Test( dependsOnMethods = {"thirdInitiateRegisterSameEmailAgain"} )
    void verifyMultipleRegisterBlocked(){
        //Verify status message - english
        common.verifyStatusMessage("Too many attempts to create account have been made. Please try again later.");

        //Verify status message - swedish
        common.selectSwedish();
        common.verifyStatusMessage("För många försök att skapa ett konto har gjorts. Försök igen senare.");
    }*/
}
