package se.sunet.eduid.swamid;

import se.sunet.eduid.utils.Common;

public class Swamid {
    private final Common common;

    public Swamid(Common common){
        this.common = common;
    }

    public void runSwamid(){
        selectIdp();
        navigateEduId("eduid");
    }

    private void selectIdp(){
        //Press select IDP
        common.findWebElementByXpath("//div/div[2]/div[2]/a/button").click();
        common.timeoutMilliSeconds(1000);

        //Select swamid testing
        common.findWebElementByXpath("//*[@id=\"selectIdP\"]/div/div[2]/a/button").click();

        //wait for next page
        common.explicitWaitClickableElementId("searchinput");
    }

    public void navigateEduId(String searchString){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys(searchString);
        common.timeoutMilliSeconds(1500);
        common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/div[1]").click();

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("eduID login");
    }
}
