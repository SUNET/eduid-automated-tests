package se.sunet.eduid;

import org.openqa.selenium.By;
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
        common.waitUntilClickable(By.xpath("//div[2]/div/div/p[2]"));
    }

    @Test( dependsOnMethods = {"navigateToFidusTestSkolverketDnp"} )
    void loginWithoutEid() {
        //Click on login button (without eID)
        common.findWebElementByXpath("//div[2]/div/div/p[2]/a").click();

        //Wait for idp search field
        common.waitUntilClickable(By.id("searchinput"));
    }

    @Test( dependsOnMethods = {"loginWithoutEid"} )
    public void navigateToEduId(){
        common.navigateToEduId();
    }

    @Test( dependsOnMethods = {"navigateToEduId"} )
    void login2(){
        login.enterUsername();
        login.enterPassword();

        //Click log in button
        login.clickLoginButton();
    }

    @Test( dependsOnMethods = {"login2"} )
    void validateSuccessfulLogin(){
        //Wait for handling of personal info link
        common.waitUntilVisible(By.xpath("//div[2]/div/div/p[5]/a"));

        common.verifyStringOnPage("Grattis!\n" +
                "Du har nu lyckats logga in till testsidan.");

        common.verifyStringOnPage(testData.getEppn() +"@dev.eduid.se");
    }
}
