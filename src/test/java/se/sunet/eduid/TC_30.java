package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_30 extends BeforeAndAfter {

    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void confirmPhoneNumber() {
        common.setMagicCode("notTheCorrectCode");
        confirmPhoneNumber.runConfirmPhoneNumber(); }

    @Test( dependsOnMethods = {"confirmPhoneNumber"} )
    void logout() {
        logout.runLogout();
    }
}
