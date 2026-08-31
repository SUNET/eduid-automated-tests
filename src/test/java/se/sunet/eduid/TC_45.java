package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_45 extends BeforeAndAfter {
    @Test
    void navigateToSwamid(){
        testData.setUsername("9c1qsEFn@dev.eduid.sunet.se");
        testData.setPassword("bt14 fyw8 079c");
        testData.setEppn("purul-kakid");
        testData.setIdentityNumber("199001222398");
        testData.setGivenName("Erika");
        testData.setSurName("Lööf");
        testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
        testData.setEmail(testData.getUsername());

        common.navigateToUrl("https://release-check.qa.swamid.se");
    }

    @Test( dependsOnMethods = {"navigateToSwamid"} )
    void swamid(){
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void login2(){
        login.verifyPageTitle();
        login.enterUsername();
        login.enterPassword();
        common.click(common.findWebElement(By.id("login-form-button")));

        common.waitUntilPageTitleContains("Releasecheck för Swamid");
    }

    @Test( dependsOnMethods = {"login2"} )
    void swamidData(){
        testData.setMfaMethod("");
        swamidData.runSwamidData(false); }
}
