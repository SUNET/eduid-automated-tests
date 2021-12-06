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
        testData.setPhoneNumber("+46701740605");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber1"} )
    void addPhoneNumber2(){
        testData.setPhoneNumber("0701740606");
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber(); }

    @Test( dependsOnMethods = {"addPhoneNumber1"} )
    void makeNewPhonePrimary(){
        common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr[2]/td[2]/button").click();
        common.verifyStatusMessage("Ditt primära telefonnummer har ändrats");

        //English
        common.findWebElementByLinkText("English").click();
        common.verifyStatusMessage("The phone number was set as primary");
        common.closeStatusMessage();

        //Restore to default number as primary
        common.findWebElementByXpath("//*[@id=\"phone-display\"]/div[1]/table/tbody/tr[1]/td[2]/button").click();
        common.verifyStatusMessage("The phone number was set as primary");
    }

    @Test( dependsOnMethods = {"makeNewPhonePrimary"} )
    void logout() { logout.runLogout(); }
}
