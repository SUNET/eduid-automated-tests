package se.sunet.eduid.registration;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Register {
    private final Common common;
    private final TestData testData;

    public Register(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runRegister(){
        verifyPageTitle();
        verifyLabels();
        enterEmailAndPressRegister();
        registerPopUp();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID signup");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void verifyLabels(){
        //Verify placeholder
        common.verifyStrings("namn@example.com", common.findWebElementById("email").getAttribute("placeholder"));


        common.verifyStringByXpath("//*[@id=\"content\"]/h1", "Registrera din e-postadress för att skapa ditt eduID.");

        common.verifyStringByXpath("//*[@id=\"content\"]/p[1]", "När du har skapat ditt eduID kan du logga in och koppla det till ditt svenska personnummer.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress");

        common.verifyStringByXpath("//*[@id=\"content\"]/p[2]", "Om du redan har ett eduID kan du logga in här.");

        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/p[2]/a").getAttribute("href")
                .contains("https://dashboard.dev.eduid.se/profile"), "The log in link is not correct! Expecting: https://dashboard.dev.eduid.se/profile");

        //Switch language to English
        common.selectEnglish();
        common.verifyStringByXpath("//*[@id=\"content\"]/h1", "Register your email address to create your eduID.");

        common.verifyStringByXpath("//*[@id=\"content\"]/p[1]", "Once you have created an eduID you will be able to log in and connect it to your Swedish national identity number.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address");

        common.verifyStringByXpath("//*[@id=\"content\"]/p[2]", "If you already have eduID you can log in here.");

        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/p[2]/a").getAttribute("href")
                .contains("https://dashboard.dev.eduid.se/profile"), "The log in link is not correct! Expecting: https://dashboard.dev.eduid.se/profile");
    }

    private void enterEmailAndPressRegister(){
        //Verify placeholder
        common.verifyStrings("name@example.com", common.findWebElementById("email").getAttribute("placeholder"));

        //Generate new username
        if(testData.isGenerateUsername())
            generateUsername();

        Common.log.info("Register user: " +testData.getUsername());

        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());
        common.click(common.findWebElementById("register-button"));
    }

    private void registerPopUp(){
        //First verify terms in english
        verifyTermsEnglish();

        //Press abort and switch to swedish
        common.click(common.findWebElementById("reject-tou-button"));

        common.timeoutMilliSeconds(200);
        common.selectSwedish();

        //Click on register button again and verify terms in swedish
        common.click(common.findWebElementById("register-button"));
        verifyTermsSwedish();

        //Click on accept or reject
        if(testData.isAcceptTerms())
            common.click(common.findWebElementById("accept-tou-button"));
        else {
            common.click(common.findWebElementById("reject-tou-button"));
            //TODO language
            common.timeoutSeconds(1);
        }
    }

    private void verifyTermsSwedish(){
        //Swedish
        common.explicitWaitVisibilityElement("//div/div[1]/h5");
        common.verifyStringByXpath("//div/div[1]/h5", "Användarvillkor för eduID.se");
        common.verifyStringByXpath("//div/div[2]/p[1]", "För eduID.se gäller generellt:");
        common.verifyStringByXpath("//div/div[2]/ul/li[1]", "att all användning av " +
                "användarkonton ska följa Sveriges lagar och förordningar,");
        common.verifyStringByXpath("//div/div[2]/ul/li[2]", "att man är " +
                "sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,");
        common.verifyStringByXpath("//div/div[2]/ul/li[3]", "att användarkonton, lösenord, " +
                "säkerhetsnycklar och koder är personliga och får endast användas av innehavaren,");
        common.verifyStringByXpath("//div/div[2]/ul/li[4]", "att SUNET:s " +
                "etiska regler reglerar övrig tillåten användning. SUNET bedömer som oetiskt när någon");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[1]", "försöker " +
                "få tillgång till nätverksresurser utan att ha rätt till det");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[2]", "försöker dölja sin användaridentitet");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[3]", "försöker " +
                "störa eller avbryta den avsedda användningen av nätverken");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[4]", "uppenbart " +
                "slösar med tillgängliga resurser (personal, maskinvara eller programvara)");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[5]", "försöker " +
                "skada eller förstöra den datorbaserade informationen");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[6]", "gör intrång i andras privatliv");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[7]", "försöker förolämpa eller förnedra andra");

        common.verifyStringOnPage("Den som överträder, eller misstänks överträda, ovanstående regler kan " +
                "stängas av från eduID.se. Dessutom kan rättsliga åtgärder komma att vidtas.");
    }

    private void verifyTermsEnglish(){
        common.explicitWaitVisibilityElement("//div/div[1]/h5");
        common.verifyStringByXpath("//div/div[1]/h5", "General rules for eduID users");
        common.verifyStringByXpath("//div/div[2]/p[1]", "The following generally applies:");
        common.verifyStringByXpath("//div/div[2]/ul/li[1]", "that all usage of user " +
                "accounts follow Sweden's laws and by-laws,");
        common.verifyStringByXpath("//div/div[2]/ul/li[2]", "that all personal information " +
                "that you provide, such as name and contact information shall be truthful,");
        common.verifyStringByXpath("//div/div[2]/ul/li[3]", "that user accounts, password, " +
                "security keys and codes are individual and shall only be used by the intended individual,");
        common.verifyStringByXpath("//div/div[2]/ul/li[4]", "that SUNET's ethical rules " +
                "regulate the \"other\" usage. SUNET judges unethical behaviour to be when someone");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[1]", "attempts to gain access to " +
                "network resources that they do not have the right to");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[2]", "attempts to conceal their " +
                "user identity");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[3]", "attempts to interfere or " +
                "disrupt the intended usage of the network");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[4]", "clearly wastes available " +
                "resources (personnel, hardware or software)");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[5]", "attempts to disrupt or " +
                "destroy computer-based information");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[6]", "infringes on the privacy of others");
        common.verifyStringByXpath("//div/div[2]/ul/ul/li[7]", "attempts to insult or offend others");

        common.verifyStringOnPage("Any person found violating or suspected of violating these rules can be disabled from eduID.se for " +
                "investigation. Furthermore, legal action can be taken.");
    }


    private void generateUsername(){
        int n = 8;

        String AlphaNumericString = "0123456789"
        + "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";

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
        testData.setUsername(sb.toString() +"@dev.eduid.sunet.se");
    }
}