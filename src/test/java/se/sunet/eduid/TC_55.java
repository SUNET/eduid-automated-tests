package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_55 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void removeExtraPhonenumbers() {
        //Click on X on the second line twice to remove both extra added phone numbers in TC_51
        common.timeoutMilliSeconds(700);
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr[3]/td[3]/button"));

        common.timeoutMilliSeconds(700);
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr[3]/td[3]/button"));
        common.timeoutMilliSeconds(700);
    }

    @Test( dependsOnMethods = {"removeExtraPhonenumbers"} )
    void logout() {
        logout.runLogout();
    }
}
