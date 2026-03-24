package se.sunet.eduid.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;

public class RetryAndScreenShot implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {

        if (result.isSuccess()) {
            return false;
        }

        Throwable cause = result.getThrowable();

        if (!isRetryableFailure(cause)) {
            return false;
        }

        if (retryCount >= MAX_RETRY) {
            return false;
        }

        retryCount++;

        logRetry(result, cause, retryCount);

        return true;
    }

    private boolean isRetryableFailure(Throwable t) {

        return t instanceof org.openqa.selenium.StaleElementReferenceException ||
                t instanceof org.openqa.selenium.ElementClickInterceptedException ||
                t instanceof org.openqa.selenium.TimeoutException ||
                t instanceof org.openqa.selenium.WebDriverException;
    }

    private void logRetry(ITestResult result, Throwable t, int attempt) {

        StackTraceElement testLine = Arrays.stream(t.getStackTrace())
                .filter(el -> el.getClassName().startsWith("se.swedenconnect"))
                .findFirst()
                .orElse(t.getStackTrace()[0]);

        Common.log.warn("""
                RETRYING TEST
                Test: %s
                Method: %s
                Attempt: %d
                Failure: %s
                Location: %s:%d
                """
                .formatted(
                        result.getTestContext().getName(),
                        result.getName(),
                        attempt,
                        t.getMessage(),
                        testLine.getClassName(),
                        testLine.getLineNumber()
                ));
    }
}