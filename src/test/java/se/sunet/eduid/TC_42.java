package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_42 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        common.setUsername(common.getEppn());
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() {
        common.setUsername("ove@idsec.se");
        dashBoard.runDashBoard();
    }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() { personalInfo.runPersonalInfo(); }

    @Test( dependsOnMethods = {"personalInfo"} )
    void emailAddresses() { emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void phoneNumber() { phoneNumber.runPhoneNumber(); }

    @Test( dependsOnMethods = {"phoneNumber"} )
    void password() { password.runPassword(); }

    @Test( dependsOnMethods = {"password"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }
}
