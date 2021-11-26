package se.sunet.eduid;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.dashboard.*;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.generic.Logout;
import se.sunet.eduid.generic.StartPage;
import se.sunet.eduid.utils.Common;

import java.io.IOException;
import java.net.URL;

import static io.appium.java_client.remote.IOSMobileCapabilityType.USE_PREBUILT_WDA;

/**
 *  IOS Browser Local Test.
 */
public class APP_2
{
    private IOSDriver<?> mobiledriver;
    private Common common;
    private DashBoard dashBoard;
    private StartPage startPage;
    private Login login;
    private PersonalInfo personalInfo;
    private EmailAddresses emailAddresses;
    private PhoneNumber phoneNumber;
    private Password password;
    private AdvancedSettings advancedSettings;
    private Logout logout;

    public APP_2() {
    }

    @BeforeTest
    @Parameters( {"platformName", "platformVersion", "automationName", "deviceName", "browserName", "uuid"})
    void initMobileDriver(String platformName, String platformVersion, String automationName,
            String deviceName, String browserName, String uuid) throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.18.0");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,automationName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "sv");
//        capabilities.setCapability(MobileCapabilityType.ORIENTATION, orientation);
        capabilities.setCapability(MobileCapabilityType.UDID, uuid);
        capabilities.setCapability(USE_PREBUILT_WDA, true);
        //capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
        capabilities.setCapability("newCommandTimeout", 2000);
        mobiledriver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        mobiledriver.get("https://dev.eduid.se");

        common = new Common(mobiledriver);
        dashBoard = new DashBoard(common);
        startPage = new StartPage(common);
        login = new Login(common);
        personalInfo = new PersonalInfo(common);
        emailAddresses = new EmailAddresses(common);
        phoneNumber = new PhoneNumber(common);
        password = new Password(common);
        advancedSettings = new AdvancedSettings(common);
        logout = new Logout(common);

    }


    @AfterTest
    void afterTest( ) {
        mobiledriver.quit();
    }

    @Test
    void startPage(){ startPage.runStartPage();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() { personalInfo.runPersonalInfo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void emailAddresses() { emailAddresses.runEmailAddresses();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void phoneNumber() { phoneNumber.runPhoneNumber();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"phoneNumber"} )
    void password() { password.runPassword();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test( dependsOnMethods = {"password"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test( dependsOnMethods = {"password"} )
    void logout() { logout.runLogout();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}