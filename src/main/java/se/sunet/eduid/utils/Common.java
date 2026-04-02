package se.sunet.eduid.utils;

//import io.appium.java_client.ios.IOSDriver;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
//import com.gargoylesoftware.htmlunit.html.impl.SelectableTextSelectionDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.Credential;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.Assert;
import io.github.sukgu.Shadow;
import org.testng.annotations.Test;
import se.sunet.eduid.generic.Login;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {

    private final String errorMsg = "Verification failed! ";
    public static final Logger log = LogManager.getLogger(Common.class);
    private final WebDriver webDriver;
    private String firstWinHandle = null;
    private final TestData testData;

    public Common(WebDriver webDriver, TestData testData, String testSuite) throws IOException {
        this.webDriver = webDriver;
        this.testData = testData;
        testData.setProperties(testSuite);
    }

    private String getTitle() {
        int counter = 0;
        while (webDriver.getTitle().isEmpty()) {
            timeoutMilliSeconds(20);
            counter++;
            if (counter > 500) {
                break;
            }
        }

        return webDriver.getTitle();
    }

    public void selectEnglish() {
        if (findWebElementByXpath("//*[@id=\"language-selector\"]/span/button").getText().equalsIgnoreCase("English")) {
            click(findWebElementByXpath("//*[@id=\"language-selector\"]/span/button"));
            timeoutMilliSeconds(400);

            if (findWebElementByXpath("//*[@id=\"language-selector\"]/span/button").getText().equalsIgnoreCase("Svenska")) {
                log.info("English language selected");
            }
            else {
                log.info("English language not selected, trying again");
                selectEnglish();
            }
        }
        else if (findWebElementByXpath("//*[@id=\"language-selector\"]/span/button").getText().equalsIgnoreCase("Svenska"))
            log.info("English language was already selected");
        else
            Assert.fail("Failed to switch language to English");
    }

    public void selectSwedish() {
        if(findWebElementByXpath("//*[@id=\"language-selector\"]/span/button").getText().equalsIgnoreCase("Svenska")) {
            click(findWebElementByXpath("//*[@id=\"language-selector\"]/span/button"));
            timeoutMilliSeconds(400);

            log.info("Swedish language selected");
        }
        else if(findWebElementByXpath("//*[@id=\"language-selector\"]/span/button").getText().equalsIgnoreCase("English"))
            log.info("Swedish language was already selected");
        else
            Assert.fail("Failed to switch language to Swedish");
    }

    public void navigateToAccount() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Account
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[4]/div/a"));
    }

    public void navigateToIdentity() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Identity
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[2]/div/a"));
    }

    public void navigateToSecurity() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Security Settings
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[3]/div/a"));
    }

    public void navigateToDashboard() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Start
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[1]/div/a"));
    }

    public void expandNavigationMenu(){
        //Expand navigation menu, if not already expanded
        if(findWebElementByXpath("//*[@id=\"header\"]/nav/button").getDomAttribute("aria-expanded")
                .equalsIgnoreCase("false")) {
            findWebElementByXpath("//*[@id=\"header\"]/nav/button").click();
            log.info("Expanding navigation menu");
        }
        else {
            log.info("Navigation menu already expanded");
        }
    }

    public void verifyPageTitle(String pageTitle) {
        Assert.assertEquals(getTitle(), pageTitle);
    }

    public void navigateToUrl(String url) {
        webDriver.navigate().to(url);
    }

    public void navigateToUrlNewWindow(String url) {
        ((JavascriptExecutor) webDriver).executeScript("window.open('" + url + "');");
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void verifyStringByXpath(String xpath, String stringToCompareWith) {
        Assert.assertEquals(findWebElementByXpath(xpath).getText(), stringToCompareWith, errorMsg);
    }

    public void verifyStringById(String id, String stringToCompareWith) {
        Assert.assertEquals(findWebElementById(id).getText(), stringToCompareWith, errorMsg);
    }

    public void verifyXpathContainsString(String xpathToBeEval, String stringToCompareWith) {
        Assert.assertTrue(findWebElementByXpath(xpathToBeEval).getText().toLowerCase().contains(stringToCompareWith.toLowerCase()), errorMsg
                + findWebElementByXpath(xpathToBeEval).getText() + " Does not contain search string: " + stringToCompareWith);
    }

    public void verifyStringContainsXpath(String xpathToCompareWith, String stringToBeEval) {
        Assert.assertTrue(stringToBeEval.contains(findWebElementByXpath(xpathToCompareWith).getText()), errorMsg
                + findWebElementByXpath(xpathToCompareWith).getText() + " Does not contain search string: " + xpathToCompareWith);
    }

    public void verifyStrings(String stringToCompareWith, String stringToBeEval) {
        Assert.assertTrue(stringToBeEval.contains(stringToCompareWith), errorMsg
                + stringToBeEval + " Does not contain: " + stringToCompareWith);
    }

    public void verifyPageBodyContainsString(String pageBody, String stringToBeEval) {
        Assert.assertTrue(pageBody.contains(stringToBeEval), errorMsg
                    + pageBody + " Does not contain: " + stringToBeEval);
    }

    public void verifyStringNotEmptyByXpath(String xpath, String parameterNameXpath) {
        parameterNameXpath = findWebElementByXpath(parameterNameXpath).getText();
        Assert.assertFalse((findWebElementByXpath(xpath).getText()).isEmpty(), errorMsg + parameterNameXpath + " Parameter is empty or missing!");
    }

    public void verifyStringOnPage(String stringToBeVerified) {
        timeoutMilliSeconds(100);
        if (!webDriver.findElement(By.tagName("body")).getText().contains(stringToBeVerified)) {
            log.warn(errorMsg + stringToBeVerified + " - is missing on web page!");
            Assert.fail(errorMsg + stringToBeVerified + " - is missing on web page!");
        }
    }

    public String getPageBody(){
        return webDriver.findElement(By.tagName("body")).getText();
    }

    public void logPageBody() {
        timeoutSeconds(1);
        log.info(webDriver.findElement(By.tagName("body")).getText());
    }

    public void timeoutSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public WebElement clickByShadow(String cssLocator) {
        Shadow shadow = new Shadow(webDriver);
        return shadow.findElement(cssLocator);
    }

    public void clickViaPointer(By locator){
        WebElement el = waitUntilPresence(locator);

        new Actions(getWebDriver())
                .moveToElement(el)
                .pause(Duration.ofMillis(140))
                .click()
                .perform();
    }

    public void waitUntilPageTitleContains(String titleFragment) {
        new WebDriverWait(webDriver, Duration.ofSeconds(15))
                .until(ExpectedConditions.titleContains(titleFragment));
    }

    public WebElement waitUntilPresence(By locator) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitUntilClickable(By locator) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitUntilVisible(By locator) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilNotCovered(By overlay) {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(overlay));
    }

    public List<WebElement> waitUntilPresenceOfAllElements(By locator) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void expandIfCollapsed(String id) {

        WebDriverWait wait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(10));

        By sectionLocator = By.id(id);
        By buttonLocator  = By.id(id + "-button");

        // Wait until section exists
        WebElement section = wait.until(
                ExpectedConditions.presenceOfElementLocated(sectionLocator)
        );

        // Check if already open
        if (section.getDomAttribute("open") == null) {

            // Click toggle button
            wait.until(ExpectedConditions.elementToBeClickable(buttonLocator))
                    .click();

            // Wait until expanded
            wait.until(ExpectedConditions.attributeToBeNotEmpty(
                    getWebDriver().findElement(sectionLocator),
                    "open"
            ));
        }
    }


    public void explicitWaitClickableElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitClickableElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToElementToWaitFor)));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.id(xpathToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idToElementToWaitFor)));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitPageTitle(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.titleContains(pageTitle));
    }

    public void timeoutMilliSeconds(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public WebElement findWebElementById(String elementToFind) {
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind));
    }

    public WebElement findWebElementByIdNoExplWait(String elementToFind) {
        return webDriver.findElement(By.id(elementToFind));
    }

    public WebElement findWebElementByLinkText(String linkText) {
        return webDriver.findElement(By.linkText(linkText));
    }

    public WebElement findWebElementByXpath(String elementToFind) {
        explicitWaitVisibilityElement(elementToFind);
        return webDriver.findElement(By.xpath(elementToFind));
    }

    public WebElement findWebElementByXpathContainingText(String text) {
        return webDriver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }

    public String getAttributeByXpath(String elementToFind) {
        explicitWaitVisibilityElement(elementToFind);
        return webDriver.findElement(By.xpath(elementToFind)).getDomAttribute("value");
    }

    public String getAttributeById(String elementToFind) {
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind)).getDomAttribute("value");
    }

    public void click(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
        timeoutMilliSeconds(200);
    }

    public void selectDropdownScript(String elementId, String visibleTextToSelect) {
        timeoutSeconds(4);

        WebElement select = webDriver.findElement(By.id(elementId));

        ((JavascriptExecutor) webDriver).executeScript("var select = arguments[0]; for(var i = 0; " +
                        "i < select.options.length; i++){ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }"
                , select, visibleTextToSelect);
    }

    public void verifyPlaceholder(String placeholderText, String placeholderElementId) {
        //Verify placeholder
        verifyStrings(placeholderText, findWebElementById(placeholderElementId).getDomAttribute("placeholder"));
    }

    public void verifyPlaceholderXpath(String placeholderText, String placeholderElementXpath) {
        //Verify placeholder
        verifyStrings(placeholderText, findWebElementByXpath(placeholderElementXpath).getDomAttribute("placeholder"));
    }

    public void verifyXpathIsWorkingLink(String xpath) {

        WebElement element = findWebElementByXpath(xpath);

        String href = element.getDomAttribute("href");
        String onclick = element.getDomAttribute("onclick");

        // Case 1: Standard <a href="...">
        if (href != null && !href.isBlank()) {
            try {
                Assert.assertTrue(linkWorking(href),
                        "Provided xpath: " + xpath + "\nHref is not a working URL!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        // Case 2: Button with onclick navigation (e.g. location.href='...')
        if (onclick != null && onclick.contains("http")) {

            String extractedUrl = extractUrlFromOnclick(onclick);

            Assert.assertNotNull(extractedUrl,
                    "Provided xpath: " + xpath + "\nCould not extract URL from onclick!");

            try {
                Assert.assertTrue(linkWorking(extractedUrl),
                        "Provided xpath: " + xpath + "\nOnclick URL is not working!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        // Case 3: Button inside <a> (common pattern)
        try {
            WebElement parentLink = element.findElement(By.xpath("./ancestor::a"));
            String parentHref = parentLink.getDomAttribute("href");

            Assert.assertTrue(parentHref != null && linkWorking(parentHref),
                    "Provided xpath: " + xpath + "\nParent <a> does not contain a working URL!");
            return;

        } catch (Exception ignored) {
            // no parent link
        }

        // If nothing worked → fail
        Assert.fail("Provided xpath: " + xpath +
                "\nElement is neither a link nor a navigational button!");
    }

    private String extractUrlFromOnclick(String onclick) {

        // Handles patterns like:
        // location.href='https://example.com'
        // window.location='https://example.com'

        Pattern pattern = Pattern.compile("(https?://[^'\"\\s]+)");
        Matcher matcher = pattern.matcher(onclick);

        return matcher.find() ? matcher.group(1) : null;
    }

    public void verifyStatusMessage(String message) {
        //Verify the status message
        explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span/output");
        verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span/output", message);
    }

    public void closeStatusMessage() {
        //Close the status message
        findWebElementByXpath("//*[@id=\"panel\"]/div[1]/div/button").click();
    }

    public void closePopupDialog() {
        //Close the pop up dialog
        findWebElementByXpath("//div[2]/div/div[1]/div/div/div[1]/h5/button").click();
        timeoutMilliSeconds(200);
    }

    public void rememberMe() {
        boolean enableRememberMe = testData.isRememberMe();
        explicitWaitClickableElementId("remember-me");

        log.info("Status of Remember me: " + webDriver.findElement(By.id("remember-me")).isSelected());
        timeoutSeconds(1);

        if(enableRememberMe) {
            //If Remember Me is disabled. Click button
            if (!webDriver.findElement(By.id("remember-me")).isSelected())
                click(findWebElementById("remember-me"));
        }
        //Disable Remember Me, if enabled
        else {
            //If Remember Me is enabled. Click button
            if (webDriver.findElement(By.id("remember-me")).isSelected())
                click(findWebElementById("remember-me"));
        }
        log.info("Status of Remember me: " +webDriver.findElement(By.id("remember-me")).isSelected());
    }

    public void switchToPopUpWindow() {
        //Get windowhandle
        firstWinHandle = webDriver.getWindowHandle();

        //Switch to the new pop-up window
        for (String winHandle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(winHandle);
        }
        timeoutMilliSeconds(700);
    }

    //Scroll down to bottom of page, otherwise we get click exception
    public void scrollToPageBottom() {
        ((JavascriptExecutor)getWebDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        timeoutMilliSeconds(500);
    }

    //Scroll up to top of page, otherwise we get click exception
    public void scrollToPageTop() {
        ((JavascriptExecutor)getWebDriver()).executeScript("window.scrollTo(0, 0)");
        timeoutMilliSeconds(500);
    }

    public void scrollDown(int pixelsDown) {
        ((JavascriptExecutor)getWebDriver()).executeScript("window.scrollTo(0, " +pixelsDown +")");
        timeoutMilliSeconds(500);
    }

    public void clearTextField(WebElement webElementToField) {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].value=''", webElementToField);
    }

    public void switchToDefaultWindow(){
        webDriver.switchTo().window(firstWinHandle);
    }

    public LocalDate getDate(){
        return LocalDate.now();
    }

    public void takeFullPageScreenshot(String name){
        LocalDateTime timestamp = LocalDateTime.now();

        Shutterbug.shootPage(webDriver, Capture.FULL_SCROLL, 500, true).withName(name)
                .save("screenshots/" +timestamp.toLocalDate() +"/" + testData.getTestCase() +"/");
    }

    public Select selectDropDown(String dropDownId) {
        return new Select(findWebElementById(dropDownId));
    }

    public void addMagicCookie(){
        if(!isCookieSet("autotests")) {
            Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            webDriver.manage().addCookie(new Cookie("autotests", testData.getAutotestsCookieValue(),
                    testData.getDomain(), "/", tomorrow, true, true, "None"));

            logCookie("autotests");
        }
    }

    public void addNinCookie(){
        if(!isCookieSet("nin")) {
            Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            webDriver.manage().addCookie(new Cookie("nin", testData.getIdentityNumber(),
                    testData.getDomain(), "/", tomorrow, true, true, "None"));

            logCookie("nin");
        }
    }

    public void deleteCookie(String cookieName) {
        if(isCookieSet(cookieName)) {
            webDriver.manage().deleteCookieNamed(cookieName);
        }
    }

    public boolean isCookieSet(String cookieName){
        try {
            log.info("Cookie set with name: " + webDriver.manage().getCookieNamed(cookieName).getName());
        }catch (Exception ex){
            log.info("No cookie found with name:" +cookieName);
            return false;
        }
        return true;
    }

    public void logCookie(String name){
        log.info("Cookie added with name: " +name);
/*        log.info("Cookie name: " + webDriver.manage().getCookieNamed(name).getName());
        log.info("Cookie value: " + webDriver.manage().getCookieNamed(name).getValue());
        log.info("Cookie domain: " + webDriver.manage().getCookieNamed(name).getDomain());
        log.info("Cookie path: " + webDriver.manage().getCookieNamed(name).getPath());
        log.info("Cookie expire: " + webDriver.manage().getCookieNamed(name).getExpiry());
        log.info("Cookie samesite: " + webDriver.manage().getCookieNamed(name).getSameSite());*/
    }

    public String getCodeInNewTab(String fromURL, int expectedLength) {
        //Store current window handle
        switchToPopUpWindow();

        // Opens a new window and switches to new window, to continue with same session
        Common.log.info("Open new browser tab");
        getWebDriver().switchTo().newWindow(WindowType.TAB);

        //Navigate to page with otp
        navigateToUrl(fromURL);
        String code = findWebElementByXpath("/html/body").getText();

        //Sometimes code generations fails, reload the page will often help
        if(code.length() != expectedLength){
            Common.log.info("Failed to fetch the code, got: " +code +"\nwill try to reload the page!");
            getWebDriver().navigate().refresh();
            timeoutSeconds(4);
        }
        code = findWebElementByXpath("/html/body").getText();
        Assert.assertEquals(code.length(), expectedLength, "Failed to fetch the OTP code! Got: " +code);

        Common.log.info("Fetched code in new window tab: " +code);
        timeoutMilliSeconds(500);

        //Close the tab or window
        Common.log.info("Closing new browser tab");
        getWebDriver().close();
        timeoutMilliSeconds(500);

        //Switch back to the old tab
        switchToDefaultWindow();
        timeoutMilliSeconds(500);

        return code;
    }

    public void securityConfirmPopUp(String xPathToButton, String fineTextSwe, String fineTextEng){
        String closeButtonId = "security-confirm-modal-close-button";
        String acceptButtonId = "security-confirm-modal-accept-button";
        switchToPopUpWindow();

        log.info("Extra security log in pop up, verify labels - Swedish");

        if(xPathToButton.equalsIgnoreCase("")){
            log.info("Ignore security confirm popup label verification, pressing Continue button");
        }
        else {
            //Verify labels and text
            waitUntilPresence(By.id(closeButtonId));
            verifyStringOnPage("Säkerhetsskäl");
            verifyStringOnPage("Du behöver logga in igen för att kunna utföra åtgärden.");
            verifyStringOnPage(fineTextSwe);

            verifyStringById(acceptButtonId, "FORTSÄTT");

            //Close pop-up
            waitUntilClickable(By.id(closeButtonId)).click();
            log.info("Closed pop up - Swedish");

            //Select English
            selectEnglish();

            //Click on the button that will initiate the security confirm pop up
            waitUntilClickable(By.xpath(xPathToButton)).click();

            switchToPopUpWindow();

            //Verify labels and text
            //For Delete account additional click is needed
            if(testData.isDeleteButton()) {
                //Click on 'Delete my eduid' button in pop up after Delete eduid link is clicked in settings
                log.info("Clicking on extra delete button in pop up");
                waitUntilClickable(By.id("delete-account-modal-accept-button")).click();
            }

            log.info("Extra security log pop up, verify labels and press continue - English");

            waitUntilClickable(By.id(closeButtonId));
            verifyStringOnPage("Security check");
            verifyStringOnPage("You need to log in again to perform the requested action.");
            verifyStringOnPage(fineTextEng);

            verifyStringById(acceptButtonId, "CONTINUE");
        }

        click(findWebElementById(acceptButtonId));
    }

    public boolean linkWorking(String url) throws IOException {
        // First set the default cookie manager.
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        boolean httpStatus = true;

        try {
            //Replace swedish characters in url
            url = url.replace("ä", "%C3%A4")
                    .replace("å", "%C3%A5")
                    .replace("ö", "%C3%B6")
                    .replace("Ä", "%C3%84")
                    .replace("Å", "%C3%85")
                    .replace("Ö", "%C3%96");

            //Creating URL object, if url is internal, the base url is needed
            URL url_link;
            if(url.contains("http")){
                //log.info("External url: " +url);
                url_link = new URL(url);
            }
            else {
                //System.out.println("Internal url, adding base url: " + testData.getBaseUrl() + url);
                url_link = new URL(testData.getBaseUrl() + url);
            }

            //Creating conn object for the URL
            HttpURLConnection conn = (HttpURLConnection) url_link.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
            conn.addRequestProperty("Connection", "keep-alive");
            conn.addRequestProperty("Accept-Language", "sv-SE,sv;q=0.8,en-US;q=0.5,en;q=0.3");
            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
            conn.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            conn.addRequestProperty("Upgrade-Insecure-Requests", "1");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            conn.connect();

            if(conn.getResponseCode() != 200){
                log.info(testData.getTestCase() + " - Link is not working. Status code: " + conn.getResponseCode() + " URL: " + url );

                httpStatus = false;
            }

            conn.disconnect();

            return httpStatus;
        }catch (Exception ex){
            log.info(testData.getTestCase() + " - " +url + " Something went wrong at connecting to site: "+ex);
            String stringValueOfEx = String.valueOf(ex);

            if(stringValueOfEx.contains("MalformedURLException")){
                log.info("url: " +url + " is MalFormed.");
            }
            else
                log.info("Ignoring: " +ex);

            httpStatus = false;
            return httpStatus;
        }
    }

    public void refIdpEnterAndSubmitUser(){
        //Click advanced options
        findWebElementById("advancedButton").click();

        //Enter First name, family name and id number
        findWebElementById("personalIdNumber").sendKeys(testData.getIdentityNumber());
        findWebElementById("givenName").sendKeys(testData.getGivenName());
        findWebElementById("surname").sendKeys(testData.getSurName());

        //Submit
        findWebElementById("submitButton").click();

        log.info("Submitted user id: " + testData.getIdentityNumber() + ", "
                + testData.getGivenName() + ", " + testData.getSurName());
    }


    //For internal passkey option at security page, use chrome .executeCdpCommand()
    public void createVirtualWebAuthn() {
        // Enable WebAuthn
        ((ChromeDriver) webDriver).executeCdpCommand(
                "WebAuthn.enable",
                Map.of()
        );

        // Set the virtual authenticator options
        Map<String, Object> options = new HashMap<>();
        options.put("protocol", "ctap2");
        options.put("transport", "internal");
        options.put("hasResidentKey", true);
        options.put("hasUserVerification", true);
        options.put("isUserVerified", true);

        // Add the virtual authenticator options
        Map<String, Object> result = ((ChromeDriver) webDriver).executeCdpCommand(
                "WebAuthn.addVirtualAuthenticator",
                Map.of("options", options)
        );

        String authenticatorId = (String) result.get("authenticatorId");

        log.info("Virtual authenticator (executeCdpCommand() ) created with ID: " + authenticatorId);

        // From CDP-version: 143+: control presence and verification are now separate commands
        ((ChromeDriver) webDriver).executeCdpCommand(
                "WebAuthn.setAutomaticPresenceSimulation",
                Map.of(
                        "authenticatorId", authenticatorId,
                        "enabled", true
                )
        );

        ((ChromeDriver) webDriver).executeCdpCommand(
                "WebAuthn.setUserVerified",
                Map.of(
                        "authenticatorId", authenticatorId,
                        "isUserVerified", true
                )
        );

        log.info("WebAuthn credentials set to: User is set to verified and automatic presence simulation");
    }

    public void selectCountry(String country){
        //Select country
        findWebElementById("countryFlag_" +country).click();

        //Wait for idp button on next page
        explicitWaitClickableElementId("idpSubmitbutton");
    }

    public void submitEidasUser(){
        //Set LoA to substantial
        click(findWebElementByXpath("//*[@id=\"authnForm\"]/table/tbody/tr[3]/td/div/div/button"));
        click(findWebElementByXpath(
                "//*[@id=\"authnForm\"]//span[contains(text(),'" +testData.getLoaLevel() +"')]"));

        //Submit IDP identity
        findWebElementById("idpSubmitbutton").click();

        //Wait for consent button on next page
        explicitWaitClickableElementId("buttonNext");
    }

    public void submitConsent(){
        //Submit Consent
        timeoutSeconds(2);
        findWebElementById("buttonNext").click();

        timeoutSeconds(2);
    }
}