package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_51 extends BeforeAndAfter {
    @Test
    void startPage(){
        startPage.runStartPage();
    }

    @Test( dependsOnMethods = {"startPage"} )
    void login(){
        login.runLogin();
    }

    @Test( dependsOnMethods = {"login"} )
    void personalInfo() {
        //Navigate to settings
        dashBoard.pressSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber1(){
        common.setPhoneNumber("+46701740605");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber1"} )
    void addPhoneNumber2(){
        common.setPhoneNumber("0701740606");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }
/*
    @Test( dependsOnMethods = {"addPhoneNumber2"} )
    void logout() { logout.runLogout(); }
    */
}
