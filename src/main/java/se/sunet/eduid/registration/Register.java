package se.sunet.eduid.registration;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;

public class Register {
    private Common common;

    public Register(Common common){
        this.common = common;
    }

    public void runRegister(){
        //common.log.info("PAGE SOURCE: " +WebDriverManager.getWebDriver().getPageSource());
        verifyPageTitle();
        verifyLabels();
        enterEmailAndPressRegister();
        registerPopUp();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("/html").getText().contains("Svenska"))
            common.findWebElementByXpath("//*[@id=\"language-selector\"]/p[2]/a").click();

    }

    private void verifyLabels(){
        //TODO I get swedish when running TC38 first in headless mode otherwise I get english. needs to be fixed.

        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Välkommen till eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Skapa ett eduID med din e-postadress för att börja.");
        //TODO should be email address in swedish.... bug?
        common.verifyStringByXpath("//*[@id=\"register-container\"]/label", "E-POSTADRESS");
        //English
        /*
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
        common.verifyStringByXpath("//*[@id=\"welcome\"]/h2/span", "Sign up with your email address to start.");
        common.verifyStringByXpath("//*[@id=\"register-container\"]/label", "EMAIL ADDRESS");
        */
    }

    private void enterEmailAndPressRegister(){
        //Generate new username
        if(common.getGenerateUsername())
            generateUsername();

        common.log.info("Register user: " +common.getUsername());

        common.findWebElementByXpath("//*[@id=\"email\"]/input").sendKeys(common.getUsername());
        common.findWebElementById("register-button").click();
    }

    private void registerPopUp(){
        //wait for buttons to appear
        //explicitWaitVisibilityElementId("accept-tou-button");

        //In pop-up, verify text
        verifyTerms();

        //Click on accept or reject
        if(common.getAcceptTerms())
            common.findWebElementById("accept-tou-button").click();
        else {
            common.findWebElementById("reject-tou-button").click();
            //TODO language
            //common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Welcome to eduID");
            common.verifyStringByXpath("//*[@id=\"welcome\"]/h1/span", "Välkommen till eduID");
        }
    }

    private void verifyTerms(){
        //Swedish
        common.explicitWaitVisibilityElement("//*[@id=\"register-modal\"]/div/div[1]/h5/span");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[1]/h5/span", "Användarvillkor för eduID.se");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/p[1]", "För eduID.se gäller generellt:");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[1]", "att all användning av " +
                "användarkonton ska följa Sveriges lagar och förordningar,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[2]", "att man är " +
                "sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[3]", "att användarkonton, " +
                "lösenord och koder är personliga och får endast användas av innehavaren,");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/li[4]", "att SUNET:s " +
                "etiska regler reglerar övrig tillåten användning. SUNET fördömer som oetiskt när någon");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[1]", "försöker " +
                "få tillgång till nätverksresurser utan att ha rätt till det");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[2]", "försöker dölja sin användaridentitet");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[3]", "försöker " +
                "störa eller avbryta den avsedda användningen av nätverken");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[4]", "uppenbart " +
                "slösar med tillgängliga resurser (personal, maskinvara eller programvara)");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[5]", "försöker " +
                "skada eller förstöra den datorbaserade informationen");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[6]", "gör intrång i andras privatliv");
        common.verifyStringByXpath("//*[@id=\"register-modal\"]/div/div[2]/ul/ul/li[7]", "försöker förolämpa eller förnedra andra");

        common.verifyStringOnPage("Den som överträder, eller misstänks överträda, ovanstående regler kan " +
                "stängas av från eduID.se. Dessutom kan rättsliga åtgärder komma att vidtas.");

        //English
        /*
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
                */
    }

    private void generateUsername(){
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
        //common.setUsername(sb.toString() +common.getMagicCode() +"@dev.eduid.sunet.se");
        common.setUsername(sb.toString() +"@dev.eduid.sunet.se");
    }
}