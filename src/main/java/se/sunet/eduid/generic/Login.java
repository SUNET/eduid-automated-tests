package se.sunet.eduid.generic;

import org.testng.Assert;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Login {
    private final Common common;
    private final TestData testData;

    public Login(Common common, TestData testData) {
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
//        common.findWebElementById("username").clear();
//        common.timeoutMilliSeconds(200);
        common.findWebElementById("username").clear();
/*        Common.log.info("Username field : " + common.findWebElementById("username").getText());
        if(common.findWebElementById("username").getText().contains("@")) {
            Common.log.info("Username field already contains an email address: "
                    +common.findWebElementById("username").getText() +" do not type it again");
        }
        else*/
            common.findWebElementById("username").sendKeys(testData.getUsername());

        Common.log.info("Log in with username: " +testData.getUsername());
    }

    public void enterPassword() {
//        common.timeoutMilliSeconds(500);
//        common.findWebElementById("currentPassword").clear();
//        common.timeoutMilliSeconds(200);
        common.findWebElementById("currentPassword").clear();

        common.findWebElementById("currentPassword").sendKeys(testData.getPassword());

        if(!testData.getTestSuite().equalsIgnoreCase("prod"))
            Common.log.info("Log in with password: " +testData.getPassword());
    }

    public void signIn(){
        //Click log in button, if account has not been deleted
        if(!testData.isAccountDeleted()) {
            common.findWebElementById("login-form-button").click();
        }

        if(testData.isIncorrectPassword()) {
            common.timeoutMilliSeconds(500);
            common.verifyStatusMessage("E-postadressen eller lösenordet är felaktigt.");

            common.selectEnglish();
            common.verifyStatusMessage("The email address or password was incorrect.");
            common.selectSwedish();
        }
        else if(testData.isAccountDeleted()) {
            common.verifyStatusMessage("Detta konto har avslutats, men finns kvar några dagar. Gör en " +
                    "lösenordsåterställning för att ångra avslutet.");

            common.verifyStringOnPage("Raderat konto\n" +
                    "Kontot har nyligen raderats och kan inte användas för inloggning. Under en kort tid därefter " +
                    "kan kontot återupptas genom att återställa lösenordet med hjälp av länken nedan.");
            common.verifyStringOnPage("Till återställ lösenord");
            common.verifyStringOnPage("Gå till startsidan genom att klicka eduIDs logo i " +
                    "sidhuvudet för att logga in med ett annat konto, eller skapa ett nytt konto med knappen Registrera.");

            common.selectEnglish();
            common.verifyStatusMessage("This account has been terminated, but is still present. Perform a password " +
                    "reset to cancel termination.");

            common.verifyStringOnPage("Account terminated\n" +
                    "This account has recently been terminated and can not be used to log in. It is possible to " +
                    "re-activate the account shortly afterwards by resetting the password using the link below.");
            common.verifyStringOnPage("Go to reset password page");
            common.verifyStringOnPage("To log in with another account go to the start page by clicking " +
                    "the eduID logo in the header, or create a new account using the Register button.");
        }
        else {
            //Log in successful, wait for copy button at dashboard
            common.explicitWaitClickableElement("//*[@id=\"uniqueId-container\"]/button");
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

        common.explicitWaitClickableElementId("go-back-button");

/*        // If whe have initiated authentication with bankID and aborted since it's not possible to do by automation,
        // then the captcha has already been done in the same req-pw session. Then user will not end up at captcha page
        // after clicking forgot password link but on the send reset-pw email page
        if(testData.getMfaMethod().equalsIgnoreCase("bankid")){
            common.explicitWaitClickableElementId("reset-password-button");
        }
        else {
            //Wait for next page, return to login
            common.explicitWaitClickableElementId("cancel-captcha-button");

            //Add nin cookie
            common.addNinCookie();
            register.enterCaptchaCode();
        }*/
    }

    public void storeEppn(){
        //Wait for copy eppn button
        common.timeoutSeconds(2);
        common.explicitWaitClickableElement("//*[@id=\"uniqueId-container\"]/button");

        testData.setEppn(common.findWebElementById("user-eppn").getDomAttribute("value"));
        if(testData.getEppn().isEmpty()) {
            Assert.fail("Failed to save eppn, saved eppn is: " +testData.getEppn());
        }
        else
            Common.log.info("Saved EPPN: " +testData.getEppn());
    }
}