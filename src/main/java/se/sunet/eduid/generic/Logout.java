package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Logout {
    private final Common common;
    private final StartPage startPage;

    public Logout(Common common, StartPage startPage) {
        this.common = common;
        this.startPage = startPage;
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
        common.expandNavigationMenu();

        common.explicitWaitClickableElementId("logout");
        common.click(common.findWebElementById("logout"));

        //Wait for the start page - sign up button
        common.timeoutMilliSeconds(500);
        common.explicitWaitClickableElementId("sign-up-button");
    }

    private void verifyLabels(){
        startPage.verifyLabelsSwedish();
/*        common.verifyStringOnPage("eduID är enklare och säkrare inloggning.");

        common.verifyStringOnPage("Skapa ett eduID och koppla det till din identitet för att kunna " +
                "komma åt flera olika tjänster och organisationer inom högskolan.");

        common.verifyStringOnPage("eduID gör det enklare för dig eftersom du bara behöver komma ihåg " +
                "ett lösenord och säkrare för skolorna eftersom det är kopplat till en riktig individ.");*/
    }
}
