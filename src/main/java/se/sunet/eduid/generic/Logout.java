package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Logout {
    private final Common common;

    public Logout(Common common){
        this.common = common;
    }

    public void runLogout(){
//       verifyPageTitle();
       pressLogOut();
       verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void pressLogOut(){
        //Expand navigation menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button/span"));

        common.explicitWaitClickableElementId("logout");
        common.findWebElementById("logout").click();

        //Wait for the start page - sign up button
        //common.explicitWaitClickableElementId("//section[2]/div/div/a");
        common.timeoutMilliSeconds(500);
        common.explicitWaitClickableElementId("sign-up-button");
    }

    private void verifyLabels(){
        common.verifyStringOnPage("eduID är enklare och säkrare inloggning.");

        common.verifyStringOnPage("Skapa ett eduID och koppla det till din identitet för att kunna " +
                "komma åt flera olika tjänster och organisationer inom högskolan.");

        common.verifyStringOnPage("eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
    }
}
