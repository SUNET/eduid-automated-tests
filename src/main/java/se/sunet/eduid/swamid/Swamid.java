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
        common.click(common.findWebElementByXpath("//div/div[2]/div[2]/a/button"));
        common.timeoutMilliSeconds(1000);

        //Select swamid testing
        common.click(common.findWebElementByXpath("//*[@id=\"selectIdP\"]/div/div[2]/a/button"));

        //wait for next page
        common.explicitWaitClickableElementId("searchinput");
    }

    public void navigateEduId(String searchString){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys(searchString);
        common.timeoutMilliSeconds(3500);
        common.click(common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/div[1]"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(1000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }
}
