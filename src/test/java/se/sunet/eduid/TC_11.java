package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_11 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        testData.setUsername("HxFUBUCO@dev.eduid.sunet.se");
        testData.setPassword("fqv5 57l8 korp");
        testData.setDisplayName("Cookie Magic Cookie");
        testData.setAccountVerified(true);

        login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void logout() { logout.runLogout(); }
}
