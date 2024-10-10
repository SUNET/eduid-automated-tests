package se.sunet.eduid.generic;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Login {
    private final Common common;
    private final TestData testData;

    public Login(Common common, TestData testData){
        this.testData = testData;
        this.common = common;
    }

    public void runLogin(){
        verifyPageTitle();

        if(testData.isResetPassword()) {
            resetPassword();
        }
        else if(testData.isRegisterAccount())
            registerAccount();
        else {
            enterUsername();
            enterPassword();
            signIn();
        }
    }

    public void verifyPageTitle() {
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    public void enterUsername(){
        //Verify placeholder
        common.verifyPlaceholder("e-post eller användarnamn", "username");
        common.verifyPlaceholder("ange lösenord", "currentPassword");

        //Enter username
        common.findWebElementById("username").clear();
        common.timeoutMilliSeconds(200);
        common.findWebElementById("username").clear();
        common.findWebElementById("username").sendKeys(testData.getUsername());

        Common.log.info("Log in with username: " +testData.getUsername());
    }

    public void enterPassword() {
        common.timeoutMilliSeconds(500);
        common.findWebElementById("currentPassword").clear();
        common.timeoutMilliSeconds(200);
        common.findWebElementById("currentPassword").clear();

        common.findWebElementById("currentPassword").sendKeys(testData.getPassword());

        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            Common.log.info("Log in with password: " +testData.getPassword());
    }

    public void signIn(){
        //Click log in button
        common.findWebElementById("login-form-button").click();

        if(testData.isIncorrectPassword()) {
            common.timeoutMilliSeconds(500);
            common.verifyStatusMessage("E-postadressen eller lösenordet är felaktigt.");

            common.selectEnglish();
            common.verifyStatusMessage("The email address or password was incorrect.");
            common.selectSwedish();
        }
        else {
            //Check if the temporary information about removal of phone number is presented
            if(testData.getTestCase().equalsIgnoreCase("TC_11")) {
                common.explicitWaitClickableElementId("continue-button");

                common.verifyStringOnPage("Vi upphör med support av telefonnummer");

                common.findWebElementById("continue-button").click();
                Common.log.info("The extra info page about removal of phone number present, click on continue");
            }

            //Wait for the username label at dashboard upper right corner
            common.timeoutSeconds(4);

            //Log in successful
            common.explicitWaitClickableElementId("clipboard");
            storeEppn();

        }
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementByXpath("//*[@id=\"eduid-idp-menu\"]/div/div[1]/a"));

        //Wait for next page
        common.explicitWaitClickableElement("//section[1]/div/h1/span");
    }

    private void resetPassword(){
        //Click on forgot password link
        common.click(common.findWebElementById("link-forgot-password"));

        //Wait for next page, return to login
        common.explicitWaitClickableElementId("go-back-button");
    }

    public void storeEppn(){
        //Wait for copy eppn button
        common.timeoutSeconds(2);
        common.explicitWaitClickableElementId("clipboard");

        testData.setEppn(common.findWebElementById("user-eppn").getAttribute("value"));
        if(testData.getEppn().isEmpty()) {
            Assert.fail("Failed to save eppn, saved eppn is: " +testData.getEppn());
        }
        else
            Common.log.info("Saved EPPN: " +testData.getEppn());
    }
}