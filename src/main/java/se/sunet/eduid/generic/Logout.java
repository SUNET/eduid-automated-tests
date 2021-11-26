package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Logout {
    private final Common common;

    public Logout(Common common){
        this.common = common;
    }

    public void runLogout(){
       verifyPageTitle();
       pressLogOut();
       verifyLabels();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");
    }

    private void pressLogOut(){
        common.explicitWaitClickableElementId("logout");
        common.findWebElementById("logout").click();

        //Wait for the start page
        common.explicitWaitClickableElementId("login");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//section[1]/div/h1", "eduID är enklare och säkrare inloggning.");

        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[1]", "Skapa ett eduID och koppla det " +
                "till ditt svenska personnummer för att kunna komma åt flera olika tjänster och organisationer inom högskolan.");

        common.verifyStringByXpath("//*[@id=\"content\"]/div/div/p[2]", "eduID gör det enklare för dig " +
                "eftersom du bara behöver komma ihåg ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
    }
}
