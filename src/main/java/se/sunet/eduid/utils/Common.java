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
import java.io.FileInputStream;
import java.io.IOException;
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
        timeoutMilliSeconds(500);
        click(findWebElementByLinkText("English"));
        timeoutMilliSeconds(500);
    }

    public void selectSwedish() {
        timeoutMilliSeconds(500);
        click(findWebElementByLinkText("Svenska"));
        timeoutMilliSeconds(500);
    }

    public void navigateToSettings() {
        click(findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/li[3]/a"));
    }

    public void navigateToIdentity() {
        click(findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/li[2]/a"));
    }

    public void navigateToAdvancedSettings() {
        click(findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/li[4]/a"));
    }

    public void navigateToDashboard() {
        click(findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/li[1]/a"));
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
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitClickableElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idToElementToWaitFor)));
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
        //At this point we do not know if element will be clicked or not
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind));
    }

    public WebElement findWebElementByLinkText(String linkText) {
        return webDriver.findElement(By.linkText(linkText));
    }

    public WebElement findWebElementByXpath(String elementToFind) {
        //At this point we do not know if element will be clicked or not
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
        WebElement select = webDriver.findElement(By.id(elementId));

        ((JavascriptExecutor) webDriver).executeScript("var select = arguments[0]; for(var i = 0; " +
                        "i < select.options.length; i++){ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }"
                , select, visibleTextToSelect);
    }

    public void verifyPlaceholder(String placeholderText, String placeholderElementId) {
        //Verify placeholder
        verifyStrings(placeholderText, findWebElementById(placeholderElementId).getAttribute("placeholder"));
    }

    public void verifyStatusMessage(String message) {
        //Verify the saved info label
        explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span/output");
        verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span/output", message);

        //log.info("Status message at page: " +findWebElementByXpath("//*[@id=\"panel\"]/div[1]/div/span").getText());
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
        log.info("Status of Remember me: " + webDriver.findElement(By.id("remember-me")).isSelected());
        timeoutSeconds(1);

        if (enableRememberMe) {
            //If Remember Me is disabled. Click button
            if (!webDriver.findElement(By.id("remember-me")).isSelected())
                click(findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div"));
        }
        //Disable Remember Me, if enabled
        else {
            //If Remember Me is enabled. Click button
            if (webDriver.findElement(By.id("remember-me")).isSelected())
                click(findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div"));
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

    public void clearTextField(WebElement webElementToField) {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].value=''", webElementToField);
    }

    public void switchToDefaultWindow(){
        webDriver.switchTo().window(firstWinHandle);
    }

    public LocalDate getDate(){
        return LocalDate.now();
    }

    public void takeScreenshot(String name){
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
        }catch (NullPointerException ex){
            log.info("No cookie found with name:" +cookieName);
            return false;
        }
        return true;
    }

    public void logCookie(String name){
        log.info("Cookie added:");
        log.info("Cookie name: " + webDriver.manage().getCookieNamed(name).getName());
        log.info("Cookie value: " + webDriver.manage().getCookieNamed(name).getValue());
        log.info("Cookie domain: " + webDriver.manage().getCookieNamed(name).getDomain());
        log.info("Cookie path: " + webDriver.manage().getCookieNamed(name).getPath());
        log.info("Cookie expire: " + webDriver.manage().getCookieNamed(name).getExpiry());
        log.info("Cookie samesite: " + webDriver.manage().getCookieNamed(name).getSameSite());
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

    public String getCodeInNewTab(String fromURL) {
        //Store current window handle
        switchToPopUpWindow();

        // Opens a new window and switches to new window, to continue with same session
        Common.log.info("Open new browser tab");
        getWebDriver().switchTo().newWindow(WindowType.TAB);

        //Navigate to page with otp
        navigateToUrl(fromURL);
        String code = findWebElementByXpath("/html/body").getText();

        Common.log.info("Fetched code: " +code);
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
}