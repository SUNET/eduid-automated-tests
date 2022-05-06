package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_69 extends BeforeAndAfter {
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

    @Test( dependsOnMethods = {"logout2"} )
    void startPage3() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage3"} )
    void login3(){
        //Enable Remember me
        common.findWebElementByXpath("//*[@id=\"content\"]/fieldset/label/div").click();
        common.timeoutSeconds(1);

        login.runLogin();
    }

    @Test( dependsOnMethods = {"login3"} )
    void dashboard3() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard3"} )
    void logout3() { logout.runLogout(); }

    @Test( dependsOnMethods = {"logout3"} )
    void startPage4() { startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage4"} )
    void verifySigninPage() {
        //Verify label with registered display name
        common.verifyStringByXpath("//*[@id=\"content\"]/div/form/div[1]/h3", "Welcome back, " +testData.getDisplayName() +"!");

        //Verify placeholder for username
        common.verifyStrings(testData.getUsername(), common.findWebElementByXpath("//*[@id=\"email\"]/input").getAttribute("value"));
    }

    @Test( dependsOnMethods = {"verifySigninPage"} )
    void enterPassword() { login.enterPassword(); }

    @Test( dependsOnMethods = {"enterPassword"} )
    void pressLogin() {
        //Click log in button
        common.click(common.findWebElementById("login-form-button"));

        common.timeoutSeconds(1);
    }

    @Test( dependsOnMethods = {"pressLogin"} )
    void dashboard4() { dashBoard.runDashBoard(); }

    @Test( dependsOnMethods = {"dashboard4"} )
    void logout4() { logout.runLogout(); }
}
