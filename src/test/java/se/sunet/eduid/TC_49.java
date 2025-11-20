package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import java.io.IOException;

public class TC_49 extends BeforeAndAfter {
    private String username1, password1, displayname1;

    @Test
    void loginFirstAccount(){
    }

    @Test( dependsOnMethods = {"loginFirstAccount"} )
    void startPage(){
        startPage.runStartPage();

        testData.setUsername("E5rm9Tle@dev.eduid.sunet.se");
        testData.setPassword("kyrz jg9x xrn2");
        testData.setEppn("salam-zamoz");
        testData.setIdentityNumber("199104142386");
        testData.setGivenName("Ingela Ester Louisa");
        testData.setSurName("Åkerberg");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        //testData.setRegisterAccount(false);
        login.runLogin();

        //Store user credentials for the first user
        username1 = testData.getUsername();
        password1 = testData.getPassword();
        displayname1 = testData.getDisplayName();
    }

    @Test( dependsOnMethods = {"login"} )
    void confirmIdentityFreja(){
        testData.setConfirmIdBy("freja");

        common.addMagicCookie();
        common.navigateToIdentity();
        confirmIdentity.runConfirmIdentity();

    }

    @Test( dependsOnMethods = {"confirmIdentityFreja"} )
    void confirmedIdentity() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    @Test( dependsOnMethods = {"confirmedIdentity"} )
    void logout() { logout.runLogout(); }


    @Test( dependsOnMethods = {"logout"} )
    void loginSecondAccount(){
    }

    @Test( dependsOnMethods = {"loginSecondAccount"} )
    void startPage2(){
        startPage.runStartPage();

        testData.setUsername("QHIA91DH@dev.eduid.sunet.se");
        testData.setPassword("96v7 36e3 qm24");
        testData.setEppn("vanal-fanib");
        testData.setEmail("QHIA91DH@dev.eduid.sunet.se");
    }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){
        //Disable Remember me
        testData.setRememberMe(false);
        common.rememberMe();
        common.timeoutSeconds(1);

        login.enterUsername();
        login.enterPassword();
        login.signIn();
    }

    @Test( dependsOnMethods = {"login2"} )
    void confirmIdentityFreja2(){
        testData.setConfirmIdBy("freja");

        common.navigateToIdentity();
        confirmIdentity.runConfirmIdentity();
    }

    @Test( dependsOnMethods = {"confirmIdentityFreja2"} )
    void confirmedIdentity2() {
        confirmedIdentity.runConfirmedIdentity();

        testData.setRegisterAccount(false);
    }

    //Delete the second account, so it will be removed after 2 weeks by script
    @Test( dependsOnMethods = {"confirmedIdentity2"} )
    void stopBrowser(){
        common.getWebDriver().quit();
    }

    @Test( dependsOnMethods = {"stopBrowser"} )
    @Parameters({"browser", "headless", "language"})
    void startBrowser(String browser, String headless, String language, final ITestContext testContext) throws IOException {
        initBrowser(browser, headless, language, testContext);
        common.navigateToUrl(testData.getBaseUrl());
        testData.setTestSuite(testContext.getCurrentXmlTest().getSuite().getName());
        testData.setTestCase("TC_49");
        testData.setDisplayName(displayname1);
    }

    //Log in to first account, verify that identity needs to be confirmed
    @Test( dependsOnMethods = {"startBrowser"} )
    void checkFirstAccountNotVerifiedIdentity(){
    }

    @Test( dependsOnMethods = {"checkFirstAccountNotVerifiedIdentity"} )
    void startPage4(){
        testData.setIncorrectPassword(false);
        testData.setRegisterAccount(false);

        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage4"} )
    void login4(){
        testData.setUsername(username1);
        testData.setPassword(password1);

        testData.setRememberMe(true);
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login4"} )
    void dashboard() {
        //Identity is no longer verified
        testData.setAccountVerified(true);
        testData.setIdentityConfirmed(false);
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void verifyIdentityNotConfirmed(){
        //Navigate to Identity
        common.navigateToIdentity();

        //verify that Identity is not confirmed
        common.verifyStringOnPage("Koppla din identitet till ditt eduID");
        common.verifyStringOnPage("Verifiera att du har tillgång till ditt person- eller samordningsnummer.");
    }

    @Test( dependsOnMethods = {"verifyIdentityNotConfirmed"} )
    void logout2() { logout.runLogout(); }
}
