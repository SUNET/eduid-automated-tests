package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_68 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        //Disable Remember me
        testData.setRememberMe(false);
        common.rememberMe();

        //Verify remember me labels
        common.verifyStringOnPage("Kom ihåg mig på den här enheten");
        common.verifyStringOnPage("Genom att tillåta eduID " +
                "att komma ihåg dig på den här enheten kan inloggningen göras enklare och säkrare");

        //Swedish
        common.selectEnglish();
        //Verify remember me labels
        common.verifyStringOnPage("Remember me on this device");
        common.verifyStringOnPage("Allowing eduID to remember " +
                "you on this device makes logging in easier and more secure");

        common.selectSwedish();

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
