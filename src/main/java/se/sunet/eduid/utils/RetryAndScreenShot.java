package se.sunet.eduid.utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAndScreenShot implements IRetryAnalyzer {

    private int retryCount = 0;
    private Dimension windowSize;
    private static final Logger log = LogManager.getLogger(RetryAndScreenShot.class);
    private WebDriver webDriver;
    private String testCase, testMethod;

    @Override
    public boolean retry(ITestResult iTestResult) {
//        webDriver = WebDriverManager.getWebDriver();
        testCase = iTestResult.getTestContext().getName();
        testMethod = iTestResult.getName();

        //check if test case failed, then retry it. If failure, maximize window and screenshot
        if (!iTestResult.isSuccess()) {
            int maxTry = 2;
            if (retryCount < maxTry) {
                log.info(testCase + " - " + testMethod + " - Test failed at attempt " +(retryCount +1) +" will try again\n" +iTestResult.getThrowable().getMessage());
                screenshot(retryCount);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                retryCount++;
                iTestResult.setStatus(ITestResult.FAILURE);

                return true;
            }
            else {
                log.info(testCase + " - " + testMethod + " - Test failed at attempt " +(retryCount +1) +", test will be marked as failed\n" );
                //windowMaximizeSize();
                //screenshot();
                iTestResult.setStatus(ITestResult.FAILURE);
                //windowDefaultSize();
                retryCount = 0;
            }
        }
        else {
            log.info("Test succeeded at attempt " +(retryCount +1) +" continues with next test case");
            iTestResult.setStatus(ITestResult.SUCCESS);
            retryCount = 0;
        }
        return false;
    }

    private void windowMaximizeSize() {
        webDriver.switchTo().defaultContent();
        windowSize = webDriver.manage().window().getSize();
        webDriver.manage().window().fullscreen();
    }

    private void windowDefaultSize() {
        webDriver.manage().window().setSize(windowSize);
    }

    public void screenshot(int retryCount) {
//        TakesScreenshot ts;
//        ts = (TakesScreenshot) webDriver;

/*        //Scroll to almost top of page and take first screenshot
        if(retryCount == 0)
            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,250)");
        else
            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,900)");

        File screenshot1 = ts.getScreenshotAs(OutputType.FILE);
*/
//        try {
            Shutterbug.shootPage(webDriver, Capture.FULL_SCROLL, 500, true)
                    .withName(testCase +"-" +testMethod +"-" +retryCount)
                    .save("screenshots/");

            //org.apache.commons.io.FileUtils.copyFile(screenshot1, new File("screenshots/"+ testCase +"-" +testMethod +"-" +retryCount +".png"));
/*        } catch (IOException e) {
            log.warn("Failed taking a screenshot of failed test case: "+testCase +" " +e);
        }
  */      //log.error("Test case:" +testCase +" - "+testMethod +" failed, see directory: <root>/screenshots/<test case name> for screenshots");
    }
}