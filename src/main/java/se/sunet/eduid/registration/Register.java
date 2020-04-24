package se.sunet.eduid.registration;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import se.sunet.eduid.utils.CommonOperations;

public class Register extends CommonOperations {
    @Test
    public void verifyPageTitle() {
        switchToPopUpWindow();

        verifyPageTitle("eduID");
    }

    @Test( dependsOnMethods = {"verifyPageTitle"} )
    private void verifyLabels(){
        verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");
        verifyStringByXpath("//*[@id=\"register-container\"]/label", "EMAIL ADDRESS");
    }

    @Test( dependsOnMethods = {"verifyLabels"} )
    @Parameters( {"username"} )
    private void enterEmailAndPressRegister(@Optional("ovemknhKYFl94fJaWaiVk2oG9Tl@idsec.se") String username){
        findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(username);
        click(findWebElementById("register-button"));
    }

    @Test( dependsOnMethods = {"enterEmailAndPressRegister"} )
    @Parameters( {"acceptTerms"} )
    private void registerPopUp(@Optional("true") String acceptTerms){
        //wait for buttons to appear
        //explicitWaitVisibilityElementId("accept-tou-button");

        //In pop-up, verify text
        verifyPopupLabels();

        //Click on accept or reject
        if(acceptTerms.equalsIgnoreCase("true"))
            click(findWebElementById("accept-tou-button"));
        else
            click(findWebElementById("reject-tou-button"));
    }

    private void verifyPopupLabels(){
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[1]/h5/span", "General rules for eduID users");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/p[1]", "The following generally applies:");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[1]", "that all usage of user " +
                "accounts follow Sweden's laws and by-laws,");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[2]", "that all personal information " +
                "that you provide, such as name and contact information shall be truthful,");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[3]", "that user accounts, password " +
                "and codes are individual and shall only be used by the intended individual,");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[4]", "that SUNET's ethical rules " +
                "regulate the \"other\" usage. SUNET judges unethical behaviour to be when someone");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[1]", "attempts to gain access to " +
                "network resources that they do not have the right to");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[2]", "attempts to conceal their " +
                "user identity");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[3]", "attempts to interfere or " +
                "disrupt the intended usage of the network");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[4]", "clearly wastes available " +
                "resources (personnel, hardware or software)");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[5]", "attempts to disrupt or " +
                "destroy computer-based information");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[6]", "infringes on the privacy of others");
        verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[7]", "attempts to insult or offend others");

        verifyStringOnPage("Any person found violating or suspected of violating these rules can be disabled from eduID.se for " +
                "investigation. Furthermore, legal action can be taken.");
    }
}
