package se.sunet.eduid.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.*;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.*;
import se.sunet.eduid.registration.ConfirmHuman;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.supportTool.RegisteredData;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.swamid.SwamidData;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.browserstack.local.Local;

public class BeforeAndAfter {
    public StartPage startPage;
    public Login login;
    public DashBoard dashBoard;
    public PersonalInfo personalInfo;
    public EmailAddresses emailAddresses;
    public PhoneNumber phoneNumber;
    public InitPwChange initPwChange;
    public AdvancedSettings advancedSettings;
    public Identity identity;
    public Logout logout;
    public Common common;
    public InitBrowser initBrowser;
    public RequestNewPassword requestNewPassword;
    public EmailSent emailSent;
    public EmailLink emailLink;
    public ExtraSecurity extraSecurity;
    public VerifyPhoneNumber verifyPhoneNumber;
    public SetNewPassword setNewPassword;
    public PasswordChanged passwordChanged;
    public Password password;
    public ConfirmPhoneNumber confirmPhoneNumber;
    public ConfirmHuman confirmHuman;
    public Register register;
    public ConfirmIdentity confirmIdentity;
    public ConfirmedIdentity confirmedIdentity;
    public DeleteAccount deleteAccount;
    public ConfirmedNewAccount confirmedNewAccount;
    public RegisteredData registeredData;
    public Swamid swamid;
    public SwamidData swamidData;
    public SecurityKey securityKey;
    public Help help;
    public LoginExtraSecurity loginExtraSecurity;

    Local bsLocal;

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    public void initBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {
        //Create browser stack access tunnel
//        createBrowserStackAccessTunnel();

        initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver(), testsuite);
        startPage = new StartPage(common);
        login = new Login(common);
        dashBoard = new DashBoard(common);
        personalInfo = new PersonalInfo(common);
        emailAddresses = new EmailAddresses(common);
        phoneNumber = new PhoneNumber(common);
        initPwChange = new InitPwChange(common);
        advancedSettings = new AdvancedSettings(common);
        identity = new Identity(common);
        logout = new Logout(common);
        requestNewPassword = new RequestNewPassword(common);
        emailSent = new EmailSent(common);
        emailLink = new EmailLink(common);
        extraSecurity = new ExtraSecurity(common);
        verifyPhoneNumber = new VerifyPhoneNumber(common);
        setNewPassword = new SetNewPassword(common);
        passwordChanged = new PasswordChanged(common);
        logout = new Logout(common);
        dashBoard = new DashBoard(common);
        password = new Password(common);
        initPwChange = new InitPwChange(common);
        confirmPhoneNumber = new ConfirmPhoneNumber(common);
        confirmHuman = new ConfirmHuman(common);
        register = new Register(common);
        confirmIdentity = new ConfirmIdentity(common);
        confirmedIdentity = new ConfirmedIdentity(common);
        deleteAccount = new DeleteAccount(common);
        confirmedNewAccount = new ConfirmedNewAccount(common);
        registeredData = new RegisteredData(common);
        swamid = new Swamid(common);
        swamidData = new SwamidData(common);
        securityKey = new SecurityKey(common);
        help = new Help(common);
        loginExtraSecurity = new LoginExtraSecurity(common);

//        initBrowser.startHarSession(testContext.getName());
    }

    @BeforeTest
    public void testCase(final ITestContext testContext){
        common.setTestSuite(testContext.getSuite().getName());
        common.setTestCase(testContext.getName());
        Common.log.info("Start executing: " +common.getTestCase() + " - " +testContext.getCurrentXmlTest().getParameter("testDescription"));
    }

    @BeforeMethod
    public void testCaseAndMethod(Method method) throws IOException {
        Common.log.info(common.getTestCase() +" - "+method.getName());
    }

    @AfterTest
    public void quitBrowser() throws IOException {
        //Browserstack test result
//        testResult();

//        initBrowser.stopHarSession();
        WebDriverManager.quitWebDriver();
        Common.log.info("End of: " +common.getTestCase());

        //Delete browserstack access tunnel
//        deleteBrowserStackAccessTunnel();
    }



    private void createBrowserStackAccessTunnel(){
        // Creates an instance of Local
        bsLocal = new Local();
        Common.log.info("Create browser stack tunnel ");

        // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
        HashMap<String, String> bsLocalArgs = new HashMap<String, String>();

        bsLocalArgs.put("key", "EvYN352mQDEnTmbBR65R");

        // Starts the Local instance with the required arguments
        try {
            bsLocal.start(bsLocalArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if BrowserStack local instance is running
        try {
            System.out.println("Is browserstack up and running? " +bsLocal.isRunning());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteBrowserStackAccessTunnel(){
        // Creates an instance of Local
        bsLocal = new Local();

        // Stop the Local instance after your test run is completed, i.e after driver.quit
        try {
            Common.log.info("Delete browser stack tunnel ");
            bsLocal.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testResult(){
        JavascriptExecutor jse = (JavascriptExecutor)WebDriverManager.getWebDriver();
// To mark the test as passed
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"<reason>\"}}");
// To mark the test as failed
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"<reason>\"}}");

    }
}
