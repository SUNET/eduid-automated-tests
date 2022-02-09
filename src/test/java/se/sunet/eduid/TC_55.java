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
    void navigateToSettings() { dashBoard.pressSettings(); }

    @Test( dependsOnMethods = {"navigateToSettings"} )
    void removeExtraPhonenumbers() {
        common.timeoutMilliSeconds(700);
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr[2]/td[3]/button"));
        common.verifyStatusMessage("Telefonnummer har tagits bort");
        common.closeStatusMessage();

        common.timeoutMilliSeconds(700);
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr[2]/td[3]/button"));
        common.timeoutMilliSeconds(700);
        common.verifyStatusMessage("Telefonnummer har tagits bort");

        //Switch to english
        common.selectEnglish();
        common.verifyStatusMessage("Successfully removed phone number");
        common.closeStatusMessage();
    }

    @Test( dependsOnMethods = {"removeExtraPhonenumbers"} )
    void logout() {
        logout.runLogout();
    }
}
