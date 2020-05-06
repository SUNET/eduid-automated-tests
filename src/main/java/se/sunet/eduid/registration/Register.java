package se.sunet.eduid.registration;

import se.sunet.eduid.utils.Common;

public class Register {
    private Common common;

    public Register(Common common){
        this.common = common;
    }

    public void runRegister(boolean acceptTerms, boolean incorrectMagicCode, boolean generateUsername){
        verifyPageTitle();
        verifyLabels();
        enterEmailAndPressRegister(incorrectMagicCode, generateUsername);
        registerPopUp(acceptTerms);
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");
    }

    private void verifyLabels(){
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");
        common.verifyStringByXpath("//*[@id=\"register-container\"]/label", "EMAIL ADDRESS");
    }

    private void enterEmailAndPressRegister(boolean incorrectMagicCode, boolean generateUsername){
        //Generate new username
        if(generateUsername)
            generateUsername(incorrectMagicCode);

        common.log.info("Register user: " +common.getUsername());

        common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getUsername());
        common.click(common.findWebElementById("register-button"));
    }

    private void registerPopUp(boolean acceptTerms){
        //wait for buttons to appear
        //explicitWaitVisibilityElementId("accept-tou-button");

        //In pop-up, verify text
        verifyPopupLabels();

        //Click on accept or reject
        if(acceptTerms)
            common.click(common.findWebElementById("accept-tou-button"));
        else {
            common.click(common.findWebElementById("reject-tou-button"));
            common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        }
    }

    private void verifyPopupLabels(){
        common.explicitWaitVisibilityElement("//*[@id=\"register-modal\"]/div/div[1]/h5/span");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[1]/h5/span", "General rules for eduID users");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/p[1]", "The following generally applies:");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[1]", "that all usage of user " +
                "accounts follow Sweden's laws and by-laws,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[2]", "that all personal information " +
                "that you provide, such as name and contact information shall be truthful,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[3]", "that user accounts, password " +
                "and codes are individual and shall only be used by the intended individual,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[4]", "that SUNET's ethical rules " +
                "regulate the \"other\" usage. SUNET judges unethical behaviour to be when someone");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[1]", "attempts to gain access to " +
                "network resources that they do not have the right to");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[2]", "attempts to conceal their " +
                "user identity");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[3]", "attempts to interfere or " +
                "disrupt the intended usage of the network");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[4]", "clearly wastes available " +
                "resources (personnel, hardware or software)");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[5]", "attempts to disrupt or " +
                "destroy computer-based information");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[6]", "infringes on the privacy of others");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[7]", "attempts to insult or offend others");

        common.verifyStringOnPage("Any person found violating or suspected of violating these rules can be disabled from eduID.se for " +
                "investigation. Furthermore, legal action can be taken.");
    }

    private void generateUsername(boolean incorrectMagicCode){
        int n = 8;

//        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        String AlphaNumericString = "0123456789"
        + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        //Add the magic to random string
        if(incorrectMagicCode)
            common.setUsername(sb.toString() +"notTheCorrectMagicCode@dev.eduid.sunet.se");
        else
            common.setUsername(sb.toString() +"mknhKYFl94fJaWaiVk2oG9Tl@dev.eduid.sunet.se");
    }
}