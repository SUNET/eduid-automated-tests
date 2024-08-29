package se.sunet.eduid.utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.*;
import se.sunet.eduid.registration.ConfirmEmailAddress;
//import se.sunet.eduid.registration.ConfirmPassword;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.supportTool.RegisteredData;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.swamid.SwamidData;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
//import com.browserstack.local.Local;
import se.sunet.eduid.wcag.AccessibilityBase;

public class BeforeAndAfter {
    public StartPage startPage;
    public Login login;
    public DashBoard dashBoard;
    public PersonalInfo personalInfo;
    public EmailAddresses emailAddresses;
    public PhoneNumber phoneNumber;
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
    public PasswordChanged passwordChanged;
    public Password password;
    public ConfirmPhoneNumber confirmPhoneNumber;
    public ConfirmEmailAddress confirmEmailAddress;
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
    public Sunet sunet;
    public LoginOtherDevice loginOtherDevice;
    public AccessibilityBase accessibilityBase;
    public RequestResetPwEmail requestResetPwEmail;
    public TestData testData = new TestData();
    List<String> failedTests;

//    Local bsLocal;
    public WebDriver webdriver;

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language", "testsuite"})
    public void initBrowser(String url, String browser, String headless, String language, String testsuite) throws IOException {

        initBrowser = new InitBrowser();
        webdriver = initBrowser.initiateBrowser(browser, headless, language);
        common = new Common(webdriver, testsuite, testData);
        webdriver.get(url);
        testData.setBrowser(browser);
        testData.setHeadlessExecution(headless);

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
        passwordChanged = new PasswordChanged(common, testData);
        logout = new Logout(common);
        dashBoard = new DashBoard(common, testData);
        password = new Password(common, testData);
        confirmPhoneNumber = new ConfirmPhoneNumber(common, testData);
        confirmEmailAddress = new ConfirmEmailAddress(common, testData);
        register = new Register(common, testData);
        confirmIdentity = new ConfirmIdentity(common, testData, identity);
        confirmedIdentity = new ConfirmedIdentity(common, testData);
        deleteAccount = new DeleteAccount(common, testData);
        confirmedNewAccount = new ConfirmedNewAccount(common, testData);
        registeredData = new RegisteredData(common, testData);
        swamid = new Swamid(common);
        swamidData = new SwamidData(common, testData);
        securityKey = new SecurityKey(common, testData);
        help = new Help(common);
        loginExtraSecurity = new LoginExtraSecurity(common, testData);
        sunet = new Sunet(common, testData);
        loginOtherDevice = new LoginOtherDevice(common, testData);
        accessibilityBase = new AccessibilityBase(common, testData);
        requestResetPwEmail = new RequestResetPwEmail(common, testData);

//        initBrowser.startHarSession(testContext.getName());
    }

    @BeforeTest
    public void testCase(final ITestContext testContext){
        testData.setTestSuite(testContext.getSuite().getName());
        testData.setTestCase(testContext.getName());
        Common.log.info("\n\nStart executing: " +testData.getTestCase() + " - "
                +testContext.getCurrentXmlTest().getParameter("testDescription"));
    }

    @BeforeMethod
    public void testCaseAndMethod(Method method){
        testData.setTestMethod(method.getName());
        Common.log.info(testData.getTestCase() +" - "+testData.getTestMethod());
    }

    @AfterTest
    public void quitBrowser() throws IOException {
        try {
            webdriver.quit();
            Common.log.info("End of: " +testData.getTestCase());
        } catch (Exception ex) {
            Common.log.info("Failed to quit the browser normally");
        }
        finally {
            webdriver.quit();
            Common.log.info("End of: " +testData.getTestCase() +" - quit by finally!");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshot(ITestResult result){
        // Change the condition , If the screenshot needs to be taken for other status as well
        if(ITestResult.FAILURE==result.getStatus()){
            Shutterbug.shootPage(webdriver, Capture.FULL_SCROLL, 500, true)
                    .withName(testData.getTestCase() +"-" +result.getName())
                    .save("screenshots/");

//            failedTests.add(testData.getTestCase() +" - "+method.getName());
        }
    }

//    @BeforeSuite
    public void beforeSuite(){
        failedTests = new ArrayList<>();
    }

//    @AfterSuite
    public void printFailedTests(){
        Common.log.info("Failing testcases: " +failedTests.size());
        for (String failedTest : failedTests) {
            Common.log.info(failedTest);
        }
    }
}