package se.sunet.eduid.wcag;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import se.sunet.eduid.utils.Common;

import java.io.IOException;

public class FrameworkBase
{
    private static WebDriver driver;

    @BeforeTest
    public void setup() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterTest
    public void tearDown()
    {
        if(driver != null)
            driver.quit();
    }

    public static WebDriver webDriver()
    {
        return driver;
    }
}
