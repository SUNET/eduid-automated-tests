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
        common.navigateToSettings();
        personalInfo.runPersonalInfo();
    }

    @Test( dependsOnMethods = {"personalInfo"} )
    void addPhoneNumber1(){
        //To generate a phone number set register account to true
        testData.setRegisterAccount(true);

        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber();

/*        common.click(common.findWebElementByXpathContainingText("Bekräfta"));

        //Wait for generate new captcha button
        common.switchToPopUpWindow();
        common.explicitWaitClickableElement("//*[@id=\"phone-captcha-modal-form\"]/div[1]/div[2]/button");*/

    }

    @Test( dependsOnMethods = {"addPhoneNumber1"} )
    void addPhoneNumber2(){
        phoneNumber.addPhoneNumber();
        phoneNumber.confirmNewPhoneNumber();
        common.timeoutMilliSeconds(400);
    }

    @Test( dependsOnMethods = {"addPhoneNumber1"} )
    void makeNewPhonePrimary(){
        common.timeoutMilliSeconds(500);
        //Click on the second line to make it primary phone number
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr[3]/td[2]/button"));

        //Restore to default number as primary
        common.timeoutMilliSeconds(800);
        common.click(common.findWebElementByXpath("//*[@id=\"phone-display\"]/div/table/tbody/tr[2]/td[2]/button"));
    }

    @Test( dependsOnMethods = {"makeNewPhonePrimary"} )
    void logout() { logout.runLogout(); }
}
