package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_87 extends BeforeAndAfter {
    @Test
    void navigateToFidusTestSkolverketDnp() {
        testData.setUsername("ySxlpB9S@dev.eduid.sunet.se");
        testData.setPassword("vjla oauz 1qzm");
        testData.setEppn("kavav-motiv");

        common.navigateToUrl("https://fidustest.skolverket.se/DNP-staging/");

        //Wait for login button (without eID) at skolverket dnp page
        common.explicitWaitClickableElement("//div[2]/div/div/p[2]");
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
    void loginWithoutEid() {
        //Click on login button (without eID)
        common.findWebElementByXpath("//div[2]/div/div/p[2]/a").click();

        //Wait for idp search field
        common.explicitWaitClickableElementId("searchinput");
    }

    @Test( dependsOnMethods = {"loginWithoutEid"} )
    public void navigateEduId(){
        common.findWebElementById("searchinput").clear();
        common.findWebElementById("searchinput").sendKeys("eduid staging");
        common.timeoutMilliSeconds(3500);

        //Select eduid staging
        common.click(common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/a[1]"));

        //Wait for the eduID log in page to load
        common.timeoutMilliSeconds(2000);
        common.explicitWaitPageTitle("Logga in | eduID");
    }

    @Test( dependsOnMethods = {"navigateEduId"} )
    void login2(){
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        common.findWebElementById("login-form-button").click();
    }

    @Test( dependsOnMethods = {"login2"} )
    void validateSuccessfulLogin(){
        //Wait for handeling of personal info link
        common.explicitWaitVisibilityElement("//div[2]/div/div/p[5]/a");

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
