package se.sunet.eduid.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
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

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
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
    public TestData testData = new TestData();

    Local bsLocal;
    public WebDriver webdriver;

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    public void initBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {
        //Create browser stack access tunnel
//        createBrowserStackAccessTunnel();

        initBrowser = new InitBrowser();
        webdriver = initBrowser.initiateBrowser(browser, headless, language);
        common = new Common(webdriver, testsuite, testData);
        webdriver.get(url);

        startPage = new StartPage(common, testData);
        login = new Login(common, testData);
        personalInfo = new PersonalInfo(common, testData);
        emailAddresses = new EmailAddresses(common, testData);
        phoneNumber = new PhoneNumber(common, testData);
        advancedSettings = new AdvancedSettings(common, testData);
        identity = new Identity(common, testData);
        requestNewPassword = new RequestNewPassword(common, testData);
        emailSent = new EmailSent(common, testData);
        emailLink = new EmailLink(common, testData);
        extraSecurity = new ExtraSecurity(common, testData);
        verifyPhoneNumber = new VerifyPhoneNumber(common, testData);
        setNewPassword = new SetNewPassword(common, testData);
        passwordChanged = new PasswordChanged(common);
        logout = new Logout(common);
        dashBoard = new DashBoard(common, testData);
        password = new Password(common, testData);
        initPwChange = new InitPwChange(common, testData);
        confirmPhoneNumber = new ConfirmPhoneNumber(common, testData);
        confirmHuman = new ConfirmHuman(common, testData);
        register = new Register(common, testData);
        confirmIdentity = new ConfirmIdentity(common, testData);
        confirmedIdentity = new ConfirmedIdentity(common, testData);
        deleteAccount = new DeleteAccount(common, testData);
        confirmedNewAccount = new ConfirmedNewAccount(common, testData);
        registeredData = new RegisteredData(common, testData);
        swamid = new Swamid(common);
        swamidData = new SwamidData(common, testData);
        securityKey = new SecurityKey(common, testData);
        help = new Help(common);
        loginExtraSecurity = new LoginExtraSecurity(common, testData);

//        initBrowser.startHarSession(testContext.getName());
    }

    @BeforeTest
    public void testCase(final ITestContext testContext){
        testData.setTestSuite(testContext.getSuite().getName());
        testData.setTestCase(testContext.getName());
        Common.log.info("Start executing: " +testData.getTestCase() + " - " +testContext.getCurrentXmlTest().getParameter("testDescription"));

    }

    @BeforeMethod
    public void testCaseAndMethod(Method method) throws IOException {
        Common.log.info(testData.getTestCase() +" - "+method.getName());
    }

    @AfterTest
    public void quitBrowser() throws IOException {
        //Browserstack test result
//        testResult();

//        initBrowser.stopHarSession();

        webdriver.quit();
        Common.log.info("End of: " +testData.getTestCase());

        //Delete browserstack access tunnel
//        deleteBrowserStackAccessTunnel();
    }
}

/* Below code when using browser stack!

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
    */


