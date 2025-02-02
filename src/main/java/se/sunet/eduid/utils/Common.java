package se.sunet.eduid.utils;

//import io.appium.java_client.ios.IOSDriver;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.sukgu.Shadow;
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

public class Common {

    private final String errorMsg = "Verification failed! ";
    public static final Logger log = LogManager.getLogger(Common.class);
    private final WebDriver webDriver;
    private String firstWinHandle = null;
    private final TestData testData;

    public Common(WebDriver webDriver, String testSuite, TestData testData) throws IOException {
        this.webDriver = webDriver;
        this.testData = testData;
        testData.setProperties(testSuite);
    }

    /*
        public Common(IOSDriver<?> webDriver) throws IOException {
            this.webDriver = webDriver;
            setProperties();
        }
    */
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
        if (findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().equalsIgnoreCase("English")) {
            click(findWebElementByXpath("//*[@id=\"language-selector\"]/span/a"));
            timeoutMilliSeconds(400);

            log.info("English language selected");
        } else if (findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().equalsIgnoreCase("Svenska"))
            log.info("English language was already selected");
        else
            Assert.fail("Failed to switch language to English");
    }

    public void selectSwedish() {
        if(findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().equalsIgnoreCase("Svenska")) {
            click(findWebElementByXpath("//*[@id=\"language-selector\"]/span/a"));
            timeoutMilliSeconds(400);

            log.info("Swedish language selected");
        }
        else if(findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().equalsIgnoreCase("English"))
            log.info("Swedish language was already selected");
        else
            Assert.fail("Failed to switch language to Swedish");
    }

    public void navigateToAccount() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Account
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/a[4]"));
    }

    public void navigateToIdentity() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Identity
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/a[2]"));
    }

    public void navigateToSecurity() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Security Settings
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/a[3]"));
    }

    public void navigateToDashboard() {
        //Expand navigation menu
        expandNavigationMenu();

        //Click on Start
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/a[1]"));
    }

    public void expandNavigationMenu(){
        //Expand navigation menu
        click(findWebElementByXpath("//*[@id=\"header\"]/nav/button/span"));
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
        try {
            Assert.assertEquals(findWebElementByXpath(xpath).getText(), stringToCompareWith, errorMsg);
        }catch (Exception ex){
            checkIfXpathIsUpdated(xpath, stringToCompareWith);
        }
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

    public void explicitWaitClickableElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitClickableElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitPageTitle(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
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
        return webDriver.findElement(By.xpath(elementToFind)).getAttribute("value");
    }

    public String getAttributeById(String elementToFind) {
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind)).getAttribute("value");
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
        verifyStrings(placeholderText, findWebElementById(placeholderElementId).getAttribute("placeholder"));
    }

    public void verifyPlaceholderXpath(String placeholderText, String placeholderElementXpath) {
        //Verify placeholder
        verifyStrings(placeholderText, findWebElementByXpath(placeholderElementXpath).getAttribute("placeholder"));
    }

    public void verifyXpathIsWorkingLink(String xpathToLink){
        Assert.assertTrue(findWebElementByXpath(xpathToLink).getAttribute("href") != null,
                "Provided xpath: " +xpathToLink +" \nDoes not contain a href element, failing test case!");

        try {
            Assert.assertTrue(linkWorking(findWebElementByXpath(xpathToLink).getAttribute("href")),
                    "Provided xpath: " +xpathToLink +" \nDoes not contain a working url, failing test case!");
        }catch (Exception ex){
            log.info("Catching exception when trying to validate url");
        }
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

    public void verifySiteLocation(String location) {
        //Verify site location text
        verifyStringByXpath("//*[@id=\"content\"]/nav/a[2]", location);
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
        //log.info("Status of Remember me: " +webDriver.findElement(By.id("remember-me")).isSelected());
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

        /* full screen screenshots implemented in selenium only works with firefox that does not emulate mobile
        Date today = new Date();
        File src = ((FirefoxDriver) webDriver).getFullPageScreenshotAs(OutputType.FILE);
        try {
            copy(src, new File("screenshots/" + today.getTime() + getTestCase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public Select selectDropDown(String dropDownId) {
        return new Select(findWebElementById(dropDownId));
    }

    public void addMagicCookie(){
        if(!isCookieSet("autotests")) {
            Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            webDriver.manage().addCookie(new Cookie("autotests", "w9eB5yt2TwEoDsTNgzmtINq03R24DPQD8ubmRVfXPOST3gRi",
                    ".dev.eduid.se", "/", tomorrow, true, true, "None"));

            logCookie("autotests");
        }
    }
    public void addNinCookie(){
        if(!isCookieSet("nin")) {
            Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            webDriver.manage().addCookie(new Cookie("nin", testData.getIdentityNumber(),
                    ".dev.eduid.se", "/", tomorrow, true, true, "None"));

            logCookie("nin");
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

    public void setPhoneNumber(){
        //Select random phone number from file
        List<String> lines;
        Random random = new Random();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/phone_numbers.txt"));

            testData.setPhoneNumber(lines.get(random.nextInt(lines.size())));
            Common.log.info("Phone number set to: " +testData.getPhoneNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void enterCaptcha(String reqCaptchaCode){
       String captchaCode = "";
        captchaCode = reqCaptchaCode;

        //Wait for generate new captcha button
        switchToPopUpWindow();
        timeoutMilliSeconds(300);
        explicitWaitClickableElement("//*[@id=\"phone-captcha-modal-form\"]/div[1]/div[2]/button");

        clearTextField(findWebElementById("phone-captcha-modal"));
        timeoutMilliSeconds(100);
        findWebElementById("phone-captcha-modal").sendKeys(captchaCode);

        //Press continue
        findWebElementByXpath("//*[@id=\"phone-captcha-modal-form\"]/div[2]/button").click();
        Common.log.info("Captcha code entered (" +captchaCode +"), pressing Continue");
    }

    public void securityConfirmPopUp(String xPathToButton){
        switchToPopUpWindow();

        if(xPathToButton.equalsIgnoreCase("")){
            log.info("Ignore security confirm popup label verification, pressing Continue button");
        }
        else {
            //Verify labels and text
            timeoutSeconds(3);
            explicitWaitClickableElementId("security-confirm-modal-close-button");
            verifyStringOnPage("Säkerhetsskäl");
            verifyStringOnPage("Du behöver logga in igen för att kunna utföra åtgärden.");

            verifyStringById("security-confirm-modal-accept-button", "FORTSÄTT");

            //Close pop-up
            click(findWebElementById("security-confirm-modal-close-button"));

            //Select English
            selectEnglish();

            //Click on the button that will initiate the security confirm pop up
            click(findWebElementByXpath(xPathToButton));

            switchToPopUpWindow();

            //Verify labels and text
            timeoutSeconds(3);

            //For Delete account additional click is needed
            try {
                //Click on 'Delete my eduid' button in pop up after Delete eduid link is clicked in settings
                click(findWebElementByIdNoExplWait("delete-account-modal-accept-button"));
            } catch (Exception ex) {
                //log.info("");
            }

            explicitWaitClickableElementId("security-confirm-modal-close-button");
            verifyStringOnPage("Security check");
            verifyStringOnPage("You need to log in again to perform the requested action.");

            verifyStringById("security-confirm-modal-accept-button", "CONTINUE");
        }

        click(findWebElementById("security-confirm-modal-accept-button"));
    }

    public boolean linkWorking(String url) throws IOException {
        // First set the default cookie manager.
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        boolean httpStatus = true;
        String notEncodedUrl = url;

        try {
            //Replace swedish characters in url
            url = url.replace("ä", "%C3%A4")
                    .replace("å", "%C3%A5")
                    .replace("ö", "%C3%B6")
                    .replace("Ä", "%C3%84")
                    .replace("Å", "%C3%85")
                    .replace("Ö", "%C3%96");

            //Creating URL object
            URL url_link = new URL(url);

            //creating conn object for the URL
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


    private void checkIfXpathIsUpdated(String xpath, String stringToCompareWith){
        if (getPageBody().contains(stringToCompareWith)){
            log.info("Text String on webpage, xpath is probably changed");
            log.info("Searching webpage for possible update of xpath...");

            // Find elements containing the specified text
            List<WebElement> elements = webDriver.findElements(By.xpath("//*[contains(text(), '" + stringToCompareWith + "')]"));

            // Print the XPath of each matching element
            if (elements.isEmpty()) {
                System.out.println("No elements found with the text: " + stringToCompareWith);
            }
            else {
                for (WebElement element : elements) {
                    String newXpath = generateXPath(element, getWebDriver());
                    Assert.fail("Xpath (" +xpath +") to text string: \n" +stringToCompareWith +
                            "\nIs not working. Suggested new xpath to element is: " +newXpath);
                }
            }
        }
        else{
            log.info("Text String can not be found on webpage.");
        }
    }

    // Helper method to generate an XPath for a WebElement
    private static String generateXPath(WebElement element, WebDriver driver) {
        String script = "function absoluteXPath(element) {" +
                "var comp, comps = [];" +
                "var xpath = '';" +
                "var getPos = function(element) {" +
                "var position = 1, curNode;" +
                "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {" +
                "if (curNode.nodeName == element.nodeName) {" +
                "++position;" +
                "}" +
                "}" +
                "for (curNode = element.nextSibling; curNode; curNode = curNode.nextSibling) {" +
                "if (curNode.nodeName == element.nodeName) {" +
                "return position;" + // More than one sibling, return position
                "}" +
                "}" +
                "return null;" + // No siblings, no index
                "};" +
                "if (element instanceof Document) {" +
                "return '/';" +
                "}" +
                "for (; element && !(element instanceof Document); element = element.parentNode) {" +
                "comp = {};" +
                "comp.name = element.nodeName;" +
                "comp.position = getPos(element);" +
                "comps.unshift(comp);" +
                "}" +
                "for (var i = 0; i < comps.length; i++) {" +
                "comp = comps[i];" +
                "xpath += '/' + comp.name.toLowerCase();" +
                "if (comp.position !== null) {" +
                "xpath += '[' + comp.position + ']';" +
                "}" +
                "}" +
                "return xpath;" +
                "}" +
                "return absoluteXPath(arguments[0]);";

        return (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script, element);
    }
}