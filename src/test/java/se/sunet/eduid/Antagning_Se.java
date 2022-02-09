package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class Antagning_Se extends BeforeAndAfter {
    @Test
    void selectLogIn(){
        //Wait for page to load
        common.explicitWaitPageTitle("Antagning.se - Anmälan till högskola och universitet");

        //Select log in
        common.click(common.findWebElementByXpathContainingText("Logga in"));
    }

    @Test( dependsOnMethods = {"selectLogIn"} )
    void logInWithEduId(){
        //Wait for Heading Logga in
        common.explicitWaitVisibilityElement("//main/section/h1");

        //Click logga in med eduid
        common.click(common.findWebElementByXpathContainingText("Logga in med eduID"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("eduID login");
    }

    @Test( dependsOnMethods = {"logInWithEduId"} )
    void loginToAntagning(){
        login.verifyPageTitle();
        login.enterUsernamePassword();
        common.click(common.findWebElementById("login-form-button"));

        // Verify that we are logged in
        common.explicitWaitClickableElement("//main/section/a");
        common.verifyStringOnPage("Du är inloggad");
    }

    @Test( dependsOnMethods = {"loginToAntagning"} )
    void logout(){
        //click profile
        common.click(common.findWebElementByXpath("//*[@id=\"header\"]/div/div[1]/button"));

        //Click log out
        common.click(common.findWebElementByXpath("//*[@id=\"tab-mypages-logout\"]/button"));
    }
}
