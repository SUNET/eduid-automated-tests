package se.sunet.eduid.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAndScreenShot implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final Logger log = LogManager.getLogger(RetryAndScreenShot.class);
    private String testCase, testMethod;

    @Override
    public boolean retry(ITestResult iTestResult) {
        testCase = iTestResult.getTestContext().getName();
        testMethod = iTestResult.getName();

        //check if test case failed, then retry it. If failure, maximize window and screenshot
        if (!iTestResult.isSuccess()) {
            int maxTry = 1;
            if (retryCount < maxTry) {

                // Extract the exception
                Throwable t = iTestResult.getThrowable();
                StackTraceElement[] stack = t.getStackTrace();

                // Find the first line in your own project code
                StackTraceElement failLine = stack.length > 0 ? stack[0] : null;

                String lineInfo = "";
                if (failLine != null) {
                    lineInfo = "Failure at " + failLine.getClassName() +
                            ":" + failLine.getLineNumber() +
                            " (method: " + failLine.getMethodName() + ")";
                }

                log.info(testCase + " - " + testMethod +
                        " - Test failed at attempt " + (retryCount + 1) +
                        " will try again\n" +
                        t.getMessage() + "\n" + lineInfo);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                retryCount++;
                iTestResult.setStatus(ITestResult.FAILURE);

                return true;
            }


/*        if (!iTestResult.isSuccess()) {
            int maxTry = 1;
            if (retryCount < maxTry) {
                log.info(testCase + " - " + testMethod + " - Test failed at attempt " +(retryCount +1) +
                        " will try again\n" +iTestResult.getThrowable().getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                retryCount++;
                iTestResult.setStatus(ITestResult.FAILURE);

                return true;
            }*/
            else {
                log.info(testCase + " - " + testMethod +
                        " - Test failed at attempt " +(retryCount +1) +", test will be marked as failed\n" );
                iTestResult.setStatus(ITestResult.FAILURE);
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
}