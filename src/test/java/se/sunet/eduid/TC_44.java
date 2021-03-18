package se.sunet.eduid;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.swamid.Swamid;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.InitBrowser;
import se.sunet.eduid.utils.WebDriverManager;

import java.io.IOException;

public class TC_44 {
    private Common common;
    private Swamid swamid;

    @BeforeTest
    @Parameters( {"url", "browser", "headless", "language"})
    void initBrowser(String url, String browser, String headless, String language, final ITestContext testContext) throws IOException {
        InitBrowser initBrowser = new InitBrowser();
        WebDriverManager.setWebDriver(initBrowser.initiateBrowser(browser, headless, language), url);

        common = new Common(WebDriverManager.getWebDriver());
        swamid = new Swamid(common);

        Common.log.info("Executing: " +testContext.getName());
    }

    @Test
    void swamid(){
        swamid.runSwamid(true);
    }

    @AfterTest
    void quitBrowser(){ WebDriverManager.quitWebDriver(); }
}
