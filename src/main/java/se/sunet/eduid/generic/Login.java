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
            common.findWebElementByLinkText("Svenska").click();
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

        common.findWebElementByXpath("//div/section[2]/div[2]/div/form/div[2]/div[2]/input").clear();

        common.findWebElementByXpath("//*[@id=\"current-password-wrapper\"]/div[2]/input").sendKeys(testData.getPassword());

        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            Common.log.info("Log in with password: " +testData.getPassword());
    }

    public void signIn(){
        //Click log in button
        common.findWebElementById("login-form-button").click();

        if(testData.isIncorrectPassword()) {
            common.timeoutMilliSeconds(500);
            common.verifyXpathContainsString("//div/section[2]/div[1]/div/span", "E-postadressen eller lösenordet är felaktigt.");

            common.findWebElementByLinkText("English").click();
            common.verifyXpathContainsString("//div/section[2]/div[1]/div/span", "The email address or password was incorrect.");
            common.findWebElementByLinkText("Svenska").click();
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
        common.findWebElementByXpath("//*[@id=\"eduid-idp-menu\"]/div/div[1]/a").click();

        //Wait for next page
        common.explicitWaitClickableElement("//section[1]/div/h1/span");
    }

    private void resetPassword(){
        //Click on forgot password link
        common.explicitWaitVisibilityElementId("link-forgot-password");
        common.findWebElementById("link-forgot-password").click();

        //Wait for next page, return to login
        common.explicitWaitClickableElementId("return-login");
    }

    private void verifyPlaceholder(String email, String password){
        //Verify placeholder
        common.verifyStrings(email, common.findWebElementByXpath("//*[@id=\"email\"]").getAttribute("placeholder"));
        common.verifyStrings(password, common.findWebElementByXpath("//*[@id=\"current-password\"]").getAttribute("placeholder"));
    }
}