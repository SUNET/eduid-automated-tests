package se.sunet.eduid;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;
import se.sunet.eduid.utils.Common;

public class TC_59 extends BeforeAndAfter {
    @Test
    void swamid(){
        common.navigateToUrl("https://release-check.qa.swamid.se");
        swamid.runSwamid();
    }

    @Test( dependsOnMethods = {"swamid"} )
    void createEduIDAccountFreja(){
        common.findWebElement(By.id("register")).click();
    }

    @Test( dependsOnMethods = {"createEduIDAccountFreja"} )
    void register(){
        testData.setRegisterAccount(true);
        testData.setRegisterWithBankId(true);
        register.runRegister();}

    @Test( dependsOnMethods = {"register"} )
    void verifyBankId() {
        common.verifyBankIdTextAndLabels();
    }
}
