package se.sunet.eduid.generic;

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
        else if(testData.isOtherDevice())
            loginOtherDevice();
        else {
            enterUsernamePassword();
            signIn();
        }
    }

    public void verifyPageTitle() {
        common.explicitWaitPageTitle("eduID login");
        //TODO temp comment for test of FTs "other" idp site
        //common.verifyPageTitle("eduID-inloggning");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska")){
            verifyPlaceholder("name@example.com", "enter password");
            common.selectSwedish();
        }

        common.timeoutMilliSeconds(500);
    }

    public void enterUsernamePassword(){
        //Verify placeholder
        verifyPlaceholder("namn@example.com", "ange lösenord");

        //Enter username
        common.explicitWaitClickableElementId("email");
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());

        Common.log.info("Log in with username: " +testData.getUsername());

        common.findWebElementById("current-password").clear();

        common.findWebElementById("current-password").sendKeys(testData.getPassword());

        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            Common.log.info("Log in with password: " +testData.getPassword());
    }

    public void signIn(){
        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        if(testData.isIncorrectPassword()) {
            common.timeoutMilliSeconds(500);
            common.verifyXpathContainsString("//div/section[2]/div[1]/div/span", "E-postadressen eller lösenordet är felaktigt.");

            common.selectEnglish();
            common.verifyXpathContainsString("//div/section[2]/div[1]/div/span", "The email address or password was incorrect.");
            common.selectSwedish();
        }
        else {
            //Wait for the "EduId for" label at dashboard
            common.timeoutMilliSeconds(800);
            common.explicitWaitVisibilityElement("//*[@id=\"root\"]/section[1]/div/h1");
        }
        common.timeoutMilliSeconds(500);
    }

    private void registerAccount(){
        //Click on sign up button
        common.click(common.findWebElementByXpath("//*[@id=\"eduid-idp-menu\"]/div/div[1]/a"));

        //Wait for next page
        common.explicitWaitClickableElement("//section[1]/div/h1/span");
    }

    private void resetPassword(){
        //Click on forgot password link
        common.explicitWaitVisibilityElementId("link-forgot-password");
        common.click(common.findWebElementById("link-forgot-password"));

        //Wait for next page, return to login
        common.explicitWaitClickableElementId("return-login");
    }

    private void verifyPlaceholder(String email, String password){
        //Verify placeholder
        common.verifyStrings(email, common.findWebElementByXpath("//*[@id=\"email\"]").getAttribute("placeholder"));
        common.verifyStrings(password, common.findWebElementByXpath("//*[@id=\"current-password\"]").getAttribute("placeholder"));
    }

    private void loginOtherDevice(){
        //Click Other Device button
        common.click(common.findWebElementById("login-other-device-button"));

        //Wait for heading on next page
        common.explicitWaitClickableElement("//*[@id=\"content\"]/div/h3");
    }
}