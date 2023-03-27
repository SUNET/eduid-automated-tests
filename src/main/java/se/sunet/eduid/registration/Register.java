package se.sunet.eduid.registration;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

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

        common.addMagicCookie();
        common.findWebElementById("value").sendKeys("123456");

        common.scrollToPageBottom();
        common.findWebElementById("captcha-continue-button").click();

        registerPopUp();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Registrera | eduID");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//div/footer/nav/ul/li[2]").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void verifyLabels(){
        //Verify placeholder
        common.verifyPlaceholder("namn@example.com", "email");

        common.verifyStringOnPage("Registrera din e-postadress för att skapa ditt eduID.");

        common.verifyStringOnPage("När du har skapat ditt eduID kan du logga in och koppla det till ditt svenska personnummer.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress");

/*
        common.verifyStringOnPage("Om du redan har ett eduID kan du logga in här.");

        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/p[2]/a").getAttribute("href")
                .contains("https://dashboard.dev.eduid.se/profile"), "The log in link is not correct! Expecting: https://dashboard.dev.eduid.se/profile");
*/

        //Switch language to English
        common.selectEnglish();
        common.verifyPageTitle("Register | eduID");

        common.verifyStringOnPage("Register your email address to create your eduID.");

        common.verifyStringOnPage("Once you have created an eduID you will be able to log in and connect it to your Swedish national identity number.");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address");

/*
        common.verifyStringOnPage("If you already have eduID you can log in here.");

        Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"content\"]/p[2]/a").getAttribute("href")
                .contains("https://dashboard.dev.eduid.se/profile"), "The log in link is not correct! Expecting: https://dashboard.dev.eduid.se/profile");
*/
    }

    public void enterEmailAndPressRegister(){
        //Verify placeholder
        common.verifyPlaceholder("name@example.com", "email");

        //Generate new username
        if(testData.isGenerateUsername())
            generateUsername();

        //Generate new identity number if not specified in test case
        if(testData.isRegisterAccount() && testData.getIdentityNumber().isEmpty())
            setIdentityNumber();
        else
            Common.log.info("Identity number set to: " +testData.getIdentityNumber());

        Common.log.info("Register user: " +testData.getUsername());

        //Also set the email address for future usage
        testData.setEmail(testData.getUsername().toLowerCase());

        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());
        common.click(common.findWebElementById("register-button"));
    }

    private void registerPopUp(){
        //First verify terms in english
        common.timeoutMilliSeconds(500);
        verifyTermsEnglish();

        //Press abort and switch to swedish
        common.click(common.findWebElementById("cancel-button"));

        common.selectSwedish();

        common.findWebElementById("email").sendKeys(testData.getUsername());
        common.click(common.findWebElementById("register-button"));

        //Click on register button again and verify terms in swedish
        //common.click(common.findWebElementById("register-button"));
        verifyTermsSwedish();

        //Click on accept or reject
        if(testData.isAcceptTerms())
            common.click(common.findWebElementById("accept-button"));
        else {
            common.click(common.findWebElementById("cancel-button"));
            //TODO language
            common.timeoutSeconds(1);
        }
    }

    private void verifyTermsSwedish(){
        //Swedish
        Common.log.info("Verify terms - swedish");
        common.verifyStringOnPage("Användarvillkor");
        common.verifyStringOnPage("För att skapa ditt eduID måste du acceptera användarvillkoren för eduID.");
        common.verifyStringOnPage("För eduID.se gäller generellt");
        common.verifyStringOnPage("att all användning av " +
                "användarkonton ska följa Sveriges lagar och förordningar,");
        common.verifyStringOnPage("att man är " +
                "sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,");
        common.verifyStringOnPage("att användarkonton, lösenord, " +
                "säkerhetsnycklar och koder är personliga och får endast användas av innehavaren,");
        common.verifyStringOnPage("att SUNET:s etiska regler reglerar övrig tillåten användning.");
        common.verifyStringOnPage("SUNET bedömer som oetiskt när någon:");
        common.verifyStringOnPage("försöker " +
                "få tillgång till nätverksresurser utan att ha rätt till det");
        common.verifyStringOnPage("försöker dölja sin användaridentitet");
        common.verifyStringOnPage("försöker störa eller avbryta den avsedda användningen av nätverken");
        common.verifyStringOnPage("uppenbart slösar med tillgängliga resurser (personal, maskinvara eller programvara)");
        common.verifyStringOnPage("försöker störa eller avbryta " +
                "den avsedda användningen av nätverken");
        common.verifyStringOnPage("gör intrång i andras privatliv");
        common.verifyStringOnPage("försöker förolämpa eller förnedra andra");
        common.verifyStringOnPage("Den som överträder, eller misstänks överträda, ovanstående regler " +
                "kan stängas av från eduID.se. Dessutom kan rättsliga åtgärder komma att vidtas.");
    }

    private void verifyTermsEnglish(){
        Common.log.info("Verify terms - english");
        common.verifyStringOnPage("Terms of use");
        common.verifyStringOnPage("To create your eduID you need to accept the eduID terms of use.");
        common.verifyStringOnPage("The following generally applies:");
        common.verifyStringOnPage("that all usage of user accounts " +
                "follow the laws and by-laws of Sweden,");
        common.verifyStringOnPage("that all personal information " +
                "that you provide, such as name and contact information shall be truthful,");
        common.verifyStringOnPage("that user accounts, password, " +
                "security keys and codes are individual and shall only be used by the intended individual,");
        common.verifyStringOnPage("that SUNET's ethical rules regulate the “other” usage.");
        common.verifyStringOnPage("SUNET judges unethical behaviour to be when someone:");
        common.verifyStringOnPage("attempts to gain access to " +
                "network resources that they do not have the right to");
        common.verifyStringOnPage("attempts to conceal their user identity");
        common.verifyStringOnPage("attempts to interfere or disrupt the intended usage of the network");
        common.verifyStringOnPage("clearly wastes available resources (personnel, hardware or software)");
        common.verifyStringOnPage("attempts to disrupt or destroy computer-based information");
        common.verifyStringOnPage("infringes on the privacy of others");
        common.verifyStringOnPage("attempts to insult or offend others");
        common.verifyStringOnPage("Any person found violating or suspected of violating these rules can " +
                "be disabled from eduID.se for investigation. Furthermore, legal action may be taken.");
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

    private void setIdentityNumber(){
        //Select random identity number from file
        List<String> lines;
        Random random = new Random();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/identity_numbers.txt"));

            testData.setIdentityNumber(lines.get(random.nextInt(lines.size())));
            Common.log.info("Identitynumber set to: " +testData.getIdentityNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}