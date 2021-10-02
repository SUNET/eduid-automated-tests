package se.sunet.eduid.utils;

import org.testng.*;
import org.testng.annotations.*;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.Help;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.registration.ConfirmHuman;
import se.sunet.eduid.registration.ConfirmedNewAccount;
import se.sunet.eduid.registration.Register;
import se.sunet.eduid.resetPassword.*;
import se.sunet.eduid.supportTool.RegisteredData;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.swamid.SwamidData;

import java.io.IOException;
import java.lang.reflect.Method;

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

    @BeforeTest
    @Parameters({"url", "browser", "headless", "language"})
    public void initBrowser(String url, String browser, String headless, String language) throws IOException {
        initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
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



//        initBrowser.startHarSession(testContext.getName());
    }
    @BeforeTest
    public void testCase(final ITestContext testContext){
        common.setTestCase(testContext.getName());
        common.log.info("Start executing: " +common.getTestCase() + " - " +testContext.getCurrentXmlTest().getParameter("testDescription"));
    }

    @BeforeMethod
    public void testCaseAndMethod(Method method) throws IOException {
        common.log.info(common.getTestCase() +" - "+method.getName());
    }

//    @AfterTest
    public void quitBrowser() throws IOException {
//        initBrowser.stopHarSession();
        WebDriverManager.quitWebDriver();
        common.log.info("End of: " +common.getTestCase());
    }
}
