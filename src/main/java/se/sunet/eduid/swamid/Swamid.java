package se.sunet.eduid.swamid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Swamid {
    private final Common common;
    private final TestData testData;

    public Swamid(Common common, TestData testData) {
        this.common = common;
        this.testData = testData;
    }

    public void runSwamid(){
        selectIdp();
        navigateEduId(testData.getIdpName());
    }

    private void selectIdp(){
        //Press select IDP
        common.waitUntilClickable(By.xpath("//div/div[2]/div[2]/a/button")).click();
        common.timeoutMilliSeconds(1000);

        //Switch to first iframe
        common.getWebDriver().switchTo().frame(0);

        //Click on - Choose your organisation
        common.click(common.findWebElementById("idpbutton"));

        //Switch back to default window
        common.getWebDriver().switchTo().defaultContent();

        //wait for next page
        common.waitUntilClickable(By.id("searchinput"));
    }

    public void navigateEduId(String searchString){
        WebElement searchInput = common.findWebElementById("searchinput");
        searchInput.clear();
        searchInput.sendKeys(searchString);
        common.timeoutMilliSeconds(1500);

        //Select eduid staging
        common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/li/a").click();

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.waitUntilPageTitleContains("Logga in | eduID");
    }
}
