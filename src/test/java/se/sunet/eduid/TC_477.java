package se.sunet.eduid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

import java.util.List;

import static se.sunet.eduid.utils.Common.log;

public class TC_477 extends BeforeAndAfter {
    @Test
    void startPage() {
/*
        //Extract email addresses from sandbox tool
        common.navigateToUrl("https://eid.svelegtest.se/mdreg/login");

        common.findWebElementById("username").sendKeys("semart");
        common.findWebElementById("password").sendKeys("jaeqX5ff+e");

        common.findWebElementByXpath("//*[@id=\"loginForm\"]/button").click();
        common.timeoutSeconds(3);

        common.findWebElementByXpath("//*[@id=\"navbarNavAltMarkup\"]/div/div/a[3]").click();

        //Extract all table rows in to a list of web elements
        WebElement webElement = common.findWebElementByXpath("//*[@id=\"user-table\"]/tbody");
        List<WebElement> rowsInAdTable = webElement.findElements(By.xpath("*"));

        log.info("Number of rows in table: " + rowsInAdTable.size());

        for (int i = 1; i <= rowsInAdTable.size(); i++) {

            System.out.println(common.findWebElementByXpath("//*[@id=\"user-row-" + i + "\"]/td[3]").getText());
        }*/
    }
}
