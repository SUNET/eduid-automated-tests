package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_68 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        //Disable Remember me
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div").click();
        common.timeoutSeconds(1);

        //Verify remember me labels
        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Remember me on this device");
        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/p", "Allowing eduID to remember " +
                "you on this device makes logging in easier and more secure");

        //Swedish
        common.selectSwedish();
        //Verify remember me labels
        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/label", "Kom ihåg mig på den här enheten");
        common.verifyStringByXpath("//*[@id=\"content\"]/fieldset/p", "Genom att tillåta eduID att " +
                "komma ihåg dig på den här enheten, kommer inloggningen göras enklare och säkrare");

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout"} )
    void startPage2() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage2"} )
    void login2(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login2"} )
    void dashboard2() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard2"} )
    void logout2() { logout.runLogout(); }
}
