package se.sunet.eduid.utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.*;
import se.sunet.eduid.registration.ConfirmEmailAddress;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.supportTool.RegisteredData;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.swamid.SwamidData;
import se.sunet.eduid.wcag.AccessibilityBase;
import java.io.IOException;
import java.lang.reflect.Method;

public class BeforeAndAfter {
    public StartPage startPage;
    public Login login;
    public DashBoard dashBoard;
    public Name name;
    public EmailAddresses emailAddresses;
    public Account account;
    public Identity identity;
    public Logout logout;
    public Common common;
    public InitBrowser initBrowser;
    public RequestNewPassword requestNewPassword;
    public EmailSent emailSent;
    public EmailLink emailLink;
    public ExtraSecurity extraSecurity;
    public PasswordChanged passwordChanged;
    public Password password;
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

    public WebDriver webdriver;

    @BeforeTest
    @Parameters({"browser", "headless", "language"})
    public void initBrowser(String browser, String headless, String language, final ITestContext testContext) throws IOException {
        initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language));

        testData.setTestSuite(testContext.getCurrentXmlTest().getSuite().getName());
        testData.setBrowser(browser);
        testData.setHeadlessExecution(headless);

        common = new Common(WebDriverManager.getWebDriver(), testData, testData.getTestSuite());
        startPage = new StartPage(common, testData);
        register = new Register(common, testData);
        login = new Login(common, testData);
        name = new Name(common, testData);
        emailAddresses = new EmailAddresses(common, testData);
        account = new Account(common, testData);
        identity = new Identity(common, testData, name);
        requestNewPassword = new RequestNewPassword(common, testData, register);
        emailSent = new EmailSent(common, testData);
        extraSecurity = new ExtraSecurity(common, testData);
        passwordChanged = new PasswordChanged(common, testData);
        logout = new Logout(common, startPage);
        dashBoard = new DashBoard(common, testData);
        password = new Password(common, testData);
        confirmEmailAddress = new ConfirmEmailAddress(common, testData);
        emailLink = new EmailLink(common, testData, confirmEmailAddress);
        confirmIdentity = new ConfirmIdentity(common, testData, identity);
        confirmedIdentity = new ConfirmedIdentity(common, testData, name);
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
        requestResetPwEmail = new RequestResetPwEmail(common, testData, register);
    }

    @BeforeTest
    public void testCase(final ITestContext testContext){
        common.navigateToUrl(testData.getBaseUrl());

        //testData.setTestSuite(testContext.getSuite().getName());
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
        WebDriverManager.quitWebDriver();
        Common.log.info("End of: " + testData.getTestCase());
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshot(ITestResult result){
        // Change the condition , If the screenshot needs to be taken for other status as well
        if(ITestResult.FAILURE==result.getStatus()){
            Shutterbug.shootPage(WebDriverManager.getWebDriver(), Capture.FULL_SCROLL, 500, true)
                    .withName(testData.getTestCase() +"-" +result.getName())
                    .save("screenshots/");
        }
    }
}