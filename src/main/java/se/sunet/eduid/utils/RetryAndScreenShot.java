package se.sunet.eduid.utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        webDriver = WebDriverManager.chromedriver().getWebDriver();
        testCase = iTestResult.getTestContext().getName();
        testMethod = iTestResult.getName();

        //check if test case failed, then retry it. If failure, maximize window and screenshot
        if (!iTestResult.isSuccess()) {
            int maxTry = 2;
            if (retryCount < maxTry) {
                log.info(testCase + " - " + testMethod + " - Test failed at attempt " +(retryCount +1) +" will try again\n" +iTestResult.getThrowable().getMessage());
                 //Comment screenshot method below, since screenshot will be taken from BeforeAndAfter
                //                screenshot(retryCount);
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
        Shutterbug.shootPage(webDriver, Capture.FULL_SCROLL, 500, true)
                .withName(testCase +"-" +testMethod +"-" +retryCount)
                .save("screenshots/");
    }
//            failedTests.add(testData.getTestCase() +" - "+method.getName());

}