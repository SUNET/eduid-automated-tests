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
        common.explicitWaitClickableElementId("logout");
        common.click(common.findWebElementById("logout"));

        //Wait for the start page
        common.explicitWaitClickableElement("//section[2]/div/div/a");
    }

    private void verifyLabels(){
        common.verifyStringOnPage("eduID är enklare och säkrare inloggning.");

        common.verifyStringOnPage("Skapa ett eduID och koppla det till ditt svenska personnummer " +
                "för att kunna komma åt flera olika tjänster och organisationer inom högskolan.");

        common.verifyStringOnPage("eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");
    }
}
