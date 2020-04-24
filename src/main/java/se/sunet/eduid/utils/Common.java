package se.sunet.eduid.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNG;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.fail;

public class Common {

    private String errorMsg ="Verification failed! Search result: ";
    public static final Logger log = LogManager.getLogger(Common.class);
    private WebDriver webDriver;
    private String firstWinHandle = null;
    private static ThreadLocal <String> recommendedPw = new ThreadLocal<>();


    public Common(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    protected void startTestNg(List<String> suites){
        TestNG testNG = new TestNG();
        testNG.setTestSuites(suites);
        testNG.setUseDefaultListeners(false);
        testNG.run();
    }

    private String getTitle() {
        int counter = 0;
        while(webDriver.getTitle().isEmpty()){
            timeoutMilliSeconds(20);
            counter ++;
            if (counter > 500) {
                break;
            }
        }

        return webDriver.getTitle();
    }

    public void verifyPageTitle(String pageTitle) {
        try {
            Assert.assertEquals(getTitle(), pageTitle);
        } catch (AssertionError e) {
            log.warn("Page title error: " + e.getMessage());
            System.out.println("Page title error: " + e.getMessage());
            fail();
            try {
 //               Assert.fail();
//                ResultReporter.jsonReasonArray.put(jsonReasonObject.put(testCase, testClass + ": " + "Page title error: " + e.getMessage()));
//                Assert.fail();
            }catch (Exception ex){
                log.warn("Test case failure message already registered for Page Title" +ex);
            }
        }
    }

    public void navigateToUrl(String url){
        webDriver.navigate().to(url);
    }

    public void verifyStringByXpath(String xpath, String stringToCompareWith) {
        try {
            Assert.assertEquals(findWebElementByXpath(xpath).getText(), stringToCompareWith, errorMsg + findWebElementByXpath(xpath).getText()
                    +" and " +stringToCompareWith + " Does not match");
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyXpathContainsString(String xpathToBeEval, String stringToCompareWith) {
        try {
            Assert.assertTrue(findWebElementByXpath(xpathToBeEval).getText().contains(stringToCompareWith), errorMsg
                    + findWebElementByXpath(xpathToBeEval).getText() + " Does not contain search string: " + stringToCompareWith);
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyStringContainsXpath(String xpathToCompareWith, String stringToBeEval) {
        try {
            Assert.assertTrue(stringToBeEval.contains(findWebElementByXpath(xpathToCompareWith).getText()), errorMsg
                    + findWebElementByXpath(xpathToCompareWith).getText() + " Does not contain search string: " + xpathToCompareWith);
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyStrings(String stringToCompareWith, String stringToBeEval) {
        try {
            Assert.assertTrue(stringToBeEval.contains(stringToCompareWith), errorMsg
                    + stringToBeEval + " Does not contain: " + stringToCompareWith);
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyStringByRegExp(String regExp, String xpathToParameter, String parameterNameXpath) {
        parameterNameXpath = findWebElementByXpath(parameterNameXpath).getText();
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(findWebElementByXpath(xpathToParameter).getText());
        try {
            Assert.assertTrue(matcher.matches(), "The parameter " + parameterNameXpath + " did not fulfill the regexp " +
                    regExp + " according to attribute specification");
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyStringNotEmptyByXpath(String xpath, String parameterNameXpath) {
        parameterNameXpath = findWebElementByXpath(parameterNameXpath).getText();
        try {
            Assert.assertFalse((findWebElementByXpath(xpath).getText()).isEmpty(), errorMsg +parameterNameXpath +" Parameter is empty or missing!");
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    public void verifyStringOnPage(String stringToBeVerified){
        timeoutMilliSeconds(100);
        if(!webDriver.findElement(By.tagName("body")).getText().contains(stringToBeVerified)){
            log.warn(errorMsg + stringToBeVerified +" - is missing on web page!");
            addJsonFailureReasonString(errorMsg + stringToBeVerified +" - is missing on web page: " +getTitle(), "");
            Assert.fail();
        }
    }

    public void verifyIdPersistance(String xpath) {
        try {
            Assert.assertTrue(findWebElementByXpath(xpath).getText().contains("A") ||
                    findWebElementByXpath(xpath).getText().contains("B") ||
                    findWebElementByXpath(xpath).getText().contains("C"), "Failed verification of: ID persistence");
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            addJsonFailureReason(e);
            fail();
        }
    }

    private void addJsonFailureReason(AssertionError e){
        try{
//            ResultReporter.jsonReasonArray.put(jsonReasonObject.put(testCase, testClass +": " + e.getMessage()));
        }catch (Exception ex){
            log.warn("Test case failure message already registered");
        }
    }

    private void addJsonFailureReasonString(String errorString, String elementToFind){
        try{
            //TODO putonce only to insert the reason one time
//            ResultReporter.jsonReasonArray.put(jsonReasonObject.put(testCase, testClass +": " + errorString + elementToFind));
        }catch (Exception ex){
            log.warn("Test case failure message already registered");
        }
    }

    public void timeout() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void timeoutSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void explicitWaitClickableElement(String xpathToElementToWaitFor) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToElementToWaitFor)));
        } catch (Exception ex) {
            log.warn("Catching exception when explicit wait for clickable element\n" + ex);
        }
    }

    public void explicitWaitVisibilityElement(String xpathToElementToWaitFor) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 20);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToElementToWaitFor)));
        } catch (Exception ex) {
            log.warn("Catching exception when explicit wait for visibility of element\n" + ex);
        }
    }

    public void explicitWaitVisibilityElementId(String idToElementToWaitFor) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 20);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idToElementToWaitFor)));
        } catch (Exception ex) {
            log.warn("Catching exception when explicit wait for visibility of element\n" + ex);
        }
    }

    public void explicitWaitPageTitle(String pageTitle) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 20);
            wait.until(ExpectedConditions.titleContains(pageTitle));
        } catch (Exception ex) {
            log.warn("Catching exception when explicit wait for page title\n" + ex);
            addJsonFailureReasonString(": " + "TimeOutException, waiting for: ", "page title");
            //skipNextTest = true;
//            sqlConnector.updateDb("testdata","skipNextTest", "true");
        }
    }

    public void timeoutMilliSeconds(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public WebElement findWebElementById(String elementToFind){
        try {
//            explicitWaitVisibilityElement(elementToFind);
            return webDriver.findElement(By.id(elementToFind));
        } catch (TimeoutException | NoSuchElementException e) {
            log.warn(e.getMessage());
            addJsonFailureReasonString(": " + "NoSuchElementException, could not find: ", elementToFind);
            throw e;
        }
    }

    public WebElement findWebElementByXpath(String elementToFind){
        try {
//            explicitWaitVisibilityElement(elementToFind);
            return webDriver.findElement(By.xpath(elementToFind));
        } catch (TimeoutException | NoSuchElementException e) {
            log.warn(e.getMessage());
            addJsonFailureReasonString(": " + "NoSuchElementException, could not find: ", elementToFind);
            throw e;
        }
    }

    public String getAttributeByXpath(String elementToFind){
        try {
            explicitWaitVisibilityElement(elementToFind);
            return webDriver.findElement(By.xpath(elementToFind)).getAttribute("value");
        } catch (TimeoutException|NoSuchElementException e) {
            log.warn(e.getMessage());
            addJsonFailureReasonString(": " + "NoSuchElementException, could not find: ", elementToFind);
            throw e;
        }
    }

    public void click(WebElement element){
        try {
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", element);
        } catch (AssertionError e) {
            log.warn(e.getMessage());
            try {
//                ResultReporter.jsonReasonArray.put(jsonReasonObject.put(testCase, testClass + ": " + "AssertionError, could not click on: " + element));
            }catch(Exception ex){
                log.warn("Test case failure message already registered");
            }
            fail();
        }
    }

    public Select selectDropDown(String dropDownId) {
        try {
            return new Select(findWebElementById(dropDownId));
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            addJsonFailureReasonString(": " + "NoSuchElementException, could not find: ", dropDownId);
            throw e;
        }
    }

    public void switchToPopUpWindow(){
        //Get windowhandle
        firstWinHandle = webDriver.getWindowHandle();

        //Switch to the new pop-up window
        for (String winHandle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(winHandle);
        }
    }

    public void switchToDefaultWindow(){
        webDriver.switchTo().window(firstWinHandle);
    }

    public String getRecommendedPw() {
        return recommendedPw.get();
    }

    public void setRecommendedPw(String value) {
        recommendedPw.set(value);
    }
}