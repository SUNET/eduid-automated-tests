package se.sunet.eduid.swamid;

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
        common.explicitWaitClickableElement("//div/div[2]/div[2]/a/button");
        common.findWebElementByXpath("//div/div[2]/div[2]/a/button").click();
        common.timeoutMilliSeconds(1000);

        //Switch to first iframe
        common.getWebDriver().switchTo().frame(0);

        //Click on - Choose your organisation
        common.click(common.findWebElementById("idpbutton"));

        //Switch back to default window
        common.getWebDriver().switchTo().defaultContent();

        //wait for next page
        common.explicitWaitClickableElementId("searchinput");
    }

    public void navigateEduId(String searchString){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys(searchString);
        common.timeoutMilliSeconds(1500);

        //Select eduid staging
        common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/li/a").click();

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }
}
