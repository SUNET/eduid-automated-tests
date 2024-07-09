package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_1 extends BeforeAndAfter {
    @Test
    void startPage() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){ login.runLogin(); }

    @Test( dependsOnMethods = {"login"} )
    void dashboard() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard"} )
    void personalInfo() { personalInfo.runPersonalInfo(); }

    @Test( dependsOnMethods = {"personalInfo"} )
    void emailAddresses() { emailAddresses.runEmailAddresses(); }

    @Test( dependsOnMethods = {"emailAddresses"} )
    void phoneNumber() { phoneNumber.runPhoneNumber(); }

    @Test( dependsOnMethods = {"phoneNumber"} )
    void identity() { identity.runIdentity(); }

    @Test( dependsOnMethods = {"identity"} )
    void advancedSettings() { advancedSettings.runAdvancedSettings(); }

    @Test( dependsOnMethods = {"advancedSettings"} )
    void logout() { logout.runLogout(); }
}
