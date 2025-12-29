package se.sunet.eduid.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v143.webauthn.WebAuthn;
import org.openqa.selenium.devtools.v143.webauthn.model.AuthenticatorId;
import org.openqa.selenium.devtools.v143.webauthn.model.AuthenticatorProtocol;
import org.openqa.selenium.devtools.v143.webauthn.model.AuthenticatorTransport;
import org.openqa.selenium.devtools.v143.webauthn.model.VirtualAuthenticatorOptions;
import org.openqa.selenium.devtools.v143.webauthn.WebAuthn;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InitBrowser {
    private WebDriver webDriver;
    private static final Logger log = LogManager.getLogger(InitBrowser.class);
    private DevTools devTools;
    private VirtualAuthenticatorOptions authenticatorOptions;
    AuthenticatorId authenticatorId;

    public WebDriver initiateBrowser(String browser, String headless, String language){
        if(browser.equalsIgnoreCase("chrome"))
            initChromeDriver(headless, language);
        else if(browser.equalsIgnoreCase("firefox"))
            initFirefoxDriver(headless, language);
        else if(browser.equalsIgnoreCase("safari"))
            initSafariDriver(headless, language);
        else
            initMobile(browser);

        //If page does not respond within 35sec drop the session.
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        //Time we will wait before retry functionality will step in
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(11));

        //webDriver.manage().window().setSize(new Dimension(1280, 1000));
        //Setting width to 1199 to keep the expandable menu
        webDriver.manage().window().setSize(new Dimension(1199, 1000));
        webDriver.manage().window().setPosition(new Point(500, 0));

        return webDriver;
    }

    private void initSafariDriver(String headless, String language){


        // Initialize Safari Driver
        WebDriverManager.safaridriver().setup();

        SafariOptions safariOptions = new SafariOptions();

        // Create an instance of SafariDriver
        webDriver = new SafariDriver();
        webDriver.manage().window().setSize(new Dimension(1200, 800));
    }

    private void initChromeDriver(String headless, String language){
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Chrome instance with options
            WebDriverManager.chromedriver().clearDriverCache().setup();
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("disable-notifications");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-search-engine-choice-screen");

            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
//            chromeOptions.addArguments("user-data-dir=/tmp/temp-profile-" + UUID.randomUUID());


            //chromeOptions.addArguments("--disable-webauthn");
            //chromeOptions.addArguments("--disable-features=WebAuthentication,WebAuthnUI,WebAuthenticationConditionalUI,PasskeyUI,SecurePaymentConfirmation");

            // If execution should be performed headless
            if (headless.equals("true")) {
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--lang=" +language);

                //chromeOptions.addArguments("window-size=1920,1080");
                //chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            }

            //chromeOptions.addArguments("--enable-features=WebAuthenticationTestingAPI");
            //webDriver = new ChromeDriver(chromeOptions);

            //Allow notifications
/*            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 1);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            chromeOptions.setExperimentalOption("prefs", prefs);*/

            // Start ChromeDriver
            webDriver = new ChromeDriver(chromeOptions);

            devTools = ((ChromeDriver) webDriver).getDevTools();
            devTools.createSession();

            // Enable WebAuthn emulation
            devTools.send(WebAuthn.enable(Optional.empty()));

            //Enable WebAuthn with options
            authenticatorId =
                    devTools.send(WebAuthn.addVirtualAuthenticator(authenticatorOptions()));

            //From CDP-143 these are controlled separately
            devTools.send(WebAuthn.setAutomaticPresenceSimulation(authenticatorId, true));
            devTools.send(WebAuthn.setUserVerified(authenticatorId, true));

            //verifyPassKey();
            //removeVirtualAuthenticator(authenticatorId);


            // If execution should be performed headless
            if (headless.equals("true")) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--lang=" +language);

                chromeOptions.addArguments("window-size=1920,1080");
            }
        }
        else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("platformName", "LINUX");
            chromeOptions.setCapability("acceptInsecureCerts", true);
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--lang=" + language);
            chromeOptions.addArguments("--accept-lang" + language);
            chromeOptions.addArguments("--disable-search-engine-choice-screen");

            try {
                webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } catch (Exception ex) {
                log.error("Chrome driver initialization exception: " + ex);
            }
        }
    }

    private void initFirefoxDriver(String headless, String language) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {

            //Create Firefox instance with options
            WebDriverManager.firefoxdriver().clearDriverCache().setup();
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--lang=" +language);

            // If execution should be performed headless
            if (headless.equals("true"))
                firefoxOptions.addArguments("--headless");

            webDriver = new FirefoxDriver(firefoxOptions);
        } else {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("platformName", "LINUX");
            firefoxOptions.addArguments("--ignore-certificate-errors");
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("intl.accept_languages", "sv");
            firefoxOptions.setProfile(profile);

            try {
                webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
            } catch (Exception ex) {
                log.error("Firefox driver initialization exception: " + ex);
            }
        }
    }

    private void initMobile(String phoneType){
        //Create Chrome instance with option
        WebDriverManager.chromedriver().setup();

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", phoneType);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        webDriver = new ChromeDriver(chromeOptions);
    }

    private VirtualAuthenticatorOptions authenticatorOptions(){
        // Create a virtual authenticator
        authenticatorOptions = new VirtualAuthenticatorOptions(
                AuthenticatorProtocol.CTAP2,            // Protocol (String) (ctap2 = WebAuthn Level 2)
                Optional.empty(),                       // Version of CTAP2
                AuthenticatorTransport.INTERNAL,      // Transport (String) (internal = built-in device)
                //AuthenticatorTransport.USB,
                Optional.of(true),                // hasResidentKey (boolean)
                Optional.of(true),                // hasUserVerification (boolean)
                Optional.empty(),                       // hasLargeBlob
                Optional.empty(),                       // hasCredBlob
                Optional.empty(),                       // hasMinPinLength
                Optional.of(false),                // hasPrf
                Optional.of(true),                // automaticPresenceSimulation
                Optional.of(true),                // isUserVerified
                Optional.empty(),                       // defaultBackupEligibility
                Optional.empty()                        // defaultBackupState
        );

        //Common.log.info(authenticatorOptions.toString());

        return authenticatorOptions;
    }


    public void removeVirtualAuthenticator(AuthenticatorId authenticatorId){
        devTools.send(WebAuthn.removeVirtualAuthenticator(authenticatorId));
        log.info("Virtual Authenticator Removed");
    }


    private void verifyPassKey(){
        // ---------------------------------------------------------
        // Test the added security key or passkey at webAuthn.io
        // ---------------------------------------------------------
        webDriver.get("https://webauthn.io");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Start registering a passkey
        webDriver.findElement(By.id("input-email")).sendKeys("pellea@pelle.com");

        webDriver.findElement(By.xpath("//*[@id=\"advanced-settings\"]/button")).click();
        Select select = new Select(webDriver.findElement(By.id("attestation")));
        select.selectByValue("direct");

        select = new Select(webDriver.findElement(By.id("attachment")));
        select.selectByValue("platform");

        //Attestation
        //Has resident



        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        webDriver.findElement(By.id("register-button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Chrome + WebAuthn DevTools will auto-complete the ceremony
        // No popups will appear because we use virtual authenticator

        // After successful registration, the site displays credentials
        String pageContent = webDriver.getPageSource();

        if (pageContent.contains("Success! Now try to authenticate...")) {
            System.out.println("PASSKEY REGISTERED OK!");
        } else {
            System.out.println("⚠ Registration might have failed");
        }

        // ---------------------------------------------------------
        // Login flow
        // ---------------------------------------------------------
        webDriver.findElement(By.id("login-button")).click();
        try {
            Thread.sleep(4500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pageContent = webDriver.getPageSource();
        if (pageContent.contains("You're logged in!")) {
            System.out.println("PASSKEY LOGIN OK!");

            //System.out.println("\n\n" + pageContent);
        } else {
            System.out.println("⚠ Login might have failed");
        }

        // 3. Clean up
        //devTools.send(WebAuthn.removeVirtualAuthenticator(authenticatorId));
    }
}