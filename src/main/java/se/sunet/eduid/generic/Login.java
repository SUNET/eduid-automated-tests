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

    public void runLogin() {
        verifyPageTitle();

        if (testData.isResetPassword()) {
            resetPassword();
        }
        else if (testData.isRegisterAccount()) {
            registerAccount();
        }
        //Log in with passkey
        else if (testData.isAddSecurityKey()) {
            common.findWebElementById("pass-key").click();
        }
        else {
            if(!testData.isReLogin()) {
                enterUsername();
            }
            enterPassword();
            signIn();
        }
    }

    public void verifyPageTitle() {
        common.explicitWaitPageTitle("Logga in | eduID");
        verifyTextAndLabels();
    }

    public void enterUsername(){
        //Enter username
        common.findWebElementById("username").clear();
        common.findWebElementById("username").sendKeys(testData.getUsername());

        Common.log.info("Log in with username: " +testData.getUsername());
    }

    public void enterPassword() {
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

    private void verifyTextAndLabels(){
        //Wait for username, passwd log in button
        common.explicitWaitClickableElementId("login-form-button");

        //Store page body in swedish
        String pageBody = common.getPageBody();
        Common.log.info("Verify text and labels in swedish");

        if(testData.isReLogin() && testData.isRememberMe()) {
            common.verifyPageBodyContainsString(pageBody, "Logga in: med lösenord");
            common.verifyPageBodyContainsString(pageBody, "Välkommen tillbaka, " +testData.getDisplayName());
            common.verifyPageBodyContainsString(pageBody, "Inte du?");
            common.verifyXpathIsWorkingLink("//*[@id=\"wrong-person-button\"]");
        }
        else if(testData.isDeleteButton()){
            common.verifyPageBodyContainsString(pageBody, "Återautentisering: med lösenord");
            common.verifyPageBodyContainsString(pageBody, "Autentisera dig för att fortsätta");
            common.verifyPageBodyContainsString(pageBody, "Efteråt omdirigeras du till sidan för att radera konto.");
            common.verifyPageBodyContainsString(pageBody, "Om du vill avbryta utan att spara förändringen " +
                    "kan du återvända direkt till sidan Konto.");
            common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/section[1]/div[2]/div[2]/span/a");
        }
        else{
            //Passkey
            common.verifyPageBodyContainsString(pageBody, "Logga in: med passkey eller lösenord");
            common.verifyPageBodyContainsString(pageBody, "Snabbare och enklare autentisering");
            common.verifyPageBodyContainsString(pageBody, "Du kan logga in säkert med din passkey mha " +
                    "fingeravtryck, ansiktsigenkänning eller andra skärmlåsmetoder.");

            //Button text
            common.verifyStringById("pass-key", "LOGGA IN MED PASSKEY");

            //Username password
            common.verifyPageBodyContainsString(pageBody, "eller andra inloggnings-alternativ?");

            //Verify placeholder
            common.verifyPlaceholder("e-post eller unikt ID", "username");
        }

        //Username password
        common.verifyPageBodyContainsString(pageBody, "Användarnamn");
        common.verifyPageBodyContainsString(pageBody, "Lösenord");
        common.verifyStringByXpath("//*[@id=\"currentPassword-wrapper\"]/div[2]/button", "VISA");
        if(!testData.isDeleteButton()) {
            common.verifyStringByXpath("//*[@id=\"link-forgot-password\"]", "Glömt ditt lösenord?");

            //Other device
            common.verifyPageBodyContainsString(pageBody, "Kom ihåg mig på den här enheten");
            if(testData.isRememberMe()) {
                common.verifyPageBodyContainsString(pageBody, "Om denna stängs av kommer du till inloggning med " +
                        "användarnamn och lösenord istället.");
            }
            else if(!testData.isMfaDisabled()) {
                common.verifyPageBodyContainsString(pageBody, "Genom att tillåta eduID att komma ihåg dig på " +
                        "den här enheten kan inloggningen göras enklare och säkrare");
            }

            //Verify placeholder
            common.verifyPlaceholder("ange lösenord", "currentPassword");

            //Button text
            common.verifyStringById("login-form-button", "LOGGA IN");

            //Button text
            common.verifyStringById("login-other-device-button", "ANNAN ENHET");

            //Button text
            common.verifyStringById("login-abort-button", "AVBRYT");
        }


        //Store page body in english
        common.selectEnglish();
        pageBody = common.getPageBody();
        Common.log.info("Verify text and labels in english");

        if(testData.isReLogin() && testData.isRememberMe()) {
            common.verifyPageBodyContainsString(pageBody, "Log in: with Password");
            common.verifyPageBodyContainsString(pageBody, "Welcome back, " +testData.getDisplayName());
            common.verifyPageBodyContainsString(pageBody, "Not you?");
            common.verifyXpathIsWorkingLink("//*[@id=\"wrong-person-button\"]");
        }
        else if(testData.isDeleteButton()){
            common.verifyPageBodyContainsString(pageBody, "Re-authentication: with Password");
            common.verifyPageBodyContainsString(pageBody, "Authenticate to continue");
            common.verifyPageBodyContainsString(pageBody, "Afterward, you will be redirected to the page to delete account.");
            common.verifyPageBodyContainsString(pageBody, "If you wish to cancel this process without " +
                    "affecting a change you can return straight to Account page.");
            common.verifyXpathIsWorkingLink("//*[@id=\"content\"]/section[1]/div[2]/div[2]/span/a");
        }
        else{
            //Pass key
            common.verifyPageBodyContainsString(pageBody, "Log in: with Passkey or Password");
            common.verifyPageBodyContainsString(pageBody, "Faster and safer way to authenticate");
            common.verifyPageBodyContainsString(pageBody, "You can log in securely with your passkey using " +
                    "your fingerprint, face recognition or other screen-lock methods.");

            //Button text
            common.verifyStringById("pass-key", "LOG IN WITH PASSKEY");

            //Username password
            common.verifyPageBodyContainsString(pageBody, "or other log in options?");

            //Verify placeholder
            common.verifyPlaceholder("email or unique ID", "username");
        }

        //Username password
        common.verifyPageBodyContainsString(pageBody, "Username");
        common.verifyPageBodyContainsString(pageBody, "Password");
        common.verifyStringByXpath("//*[@id=\"currentPassword-wrapper\"]/div[2]/button", "SHOW");
        if(!testData.isDeleteButton()) {
            common.verifyStringByXpath("//*[@id=\"link-forgot-password\"]", "Forgot your password?");

            //Other device
            common.verifyPageBodyContainsString(pageBody, "Remember me on this device");

            if(testData.isRememberMe()) {
                common.verifyPageBodyContainsString(pageBody, "Turning this off will enable login with username " +
                        "and password instead.");
            }
            else if(!testData.isMfaDisabled()) {
                common.verifyPageBodyContainsString(pageBody, "Allowing eduID to remember you on this device " +
                        "makes logging in easier and more secure");
            }

            //Verify placeholder
            common.verifyPlaceholder("enter password", "currentPassword");

            //Button text
            common.verifyStringById("login-form-button", "LOG IN");

            //Button text
            common.verifyStringById("login-other-device-button", "OTHER DEVICE");

            //Button text
            common.verifyStringById("login-abort-button", "CANCEL");
        }
        common.selectSwedish();
    }
}