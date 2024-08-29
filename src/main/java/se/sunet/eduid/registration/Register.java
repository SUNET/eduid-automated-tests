package se.sunet.eduid.registration;

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

        verifyLabelsAtConfirmEmailAddress();

        common.addMagicCookie();
        common.findWebElementById("value").sendKeys("123456");

        //Fill in a dummy value and continue
        common.click(common.findWebElementById("captcha-continue-button"));

        registerPopUp();
    }

    private void verifyPageTitle() {
        common.explicitWaitPageTitle("Registrera | eduID");
    }

    private void verifyLabels(){
        //Verify placeholder
        common.verifyPlaceholder("förnamn", "given_name");
        common.verifyPlaceholder("efternamn", "surname");
        common.verifyPlaceholder("namn@example.com", "email");

        common.verifyStringOnPage("Registrera: Ange dina uppgifter");
        common.verifyStringOnPage("När du har skapat ditt eduID kan du logga in och koppla det till " +
                "din identitet.");

        common.verifyStringByXpath("//*[@id=\"given_name-wrapper\"]/div/label", "Förnamn");
        common.verifyStringByXpath("//*[@id=\"surname-wrapper\"]/div/label", "Efternamn");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "E-postadress");

        //Switch language to English
        common.selectEnglish();
        common.verifyPageTitle("Register | eduID");

        common.verifyStringOnPage("Register: Enter your details");
        common.verifyStringOnPage("Once you have created an eduID you will be able to log in and " +
                "connect it to your identity.");

        common.verifyStringByXpath("//*[@id=\"given_name-wrapper\"]/div/label", "First name");
        common.verifyStringByXpath("//*[@id=\"surname-wrapper\"]/div/label", "Last name");
        common.verifyStringByXpath("//*[@id=\"email-wrapper\"]/div/label", "Email address");

        //Verify placeholder
        common.verifyPlaceholder("first name", "given_name");
        common.verifyPlaceholder("last name", "surname");
        common.verifyPlaceholder("name@example.com", "email");

    }

    public void enterEmailAndPressRegister(){
        //Generate new username
        if(testData.isGenerateUsername())
            generateUsername();

        //Generate new identity number, given name and surname if not specified in test case
        if(testData.isRegisterAccount()) {
            setIdentityNumber();
            setGivenName();
            setSurName();
            testData.setDisplayName(testData.getGivenName() + " " +testData.getSurName());
            Common.log.info("Display name set to: " +testData.getDisplayName());
        }
        else {
            Common.log.info("Identity number set to: " + testData.getIdentityNumber());
        }
        Common.log.info("Register user: " +testData.getUsername());

        //Also set the email address for future usage
        testData.setEmail(testData.getUsername().toLowerCase());

        //Enter given name
        common.findWebElementById("given_name").clear();
        common.findWebElementById("given_name").sendKeys(testData.getGivenName());

        //Enter sur name
        common.findWebElementById("surname").clear();
        common.findWebElementById("surname").sendKeys(testData.getSurName());

        //Enter email
        common.findWebElementById("email").clear();
        common.findWebElementById("email").sendKeys(testData.getUsername());

        //Press register
        common.click(common.findWebElementById("register-button"));
    }

    private void registerPopUp(){
        //First verify terms in english
        common.timeoutMilliSeconds(500);
        verifyTermsEnglish();

        //Press abort and switch to swedish
        common.click(common.findWebElementById("cancel-button"));

        common.selectSwedish();

        //Fill in registration data again
        common.findWebElementById("given_name").sendKeys(testData.getGivenName());
        common.findWebElementById("surname").sendKeys(testData.getSurName());
        common.findWebElementById("email").sendKeys(testData.getUsername());
        common.click(common.findWebElementById("register-button"));

        //Click on register button again and verify terms in swedish
        verifyTermsSwedish();

        //Click on accept or reject
        if(testData.isAcceptTerms())
            common.click(common.findWebElementById("accept-button"));
        else {
            common.click(common.findWebElementById("cancel-button"));

            common.timeoutSeconds(1);
        }
    }

    private void verifyTermsSwedish(){
        //Swedish
        Common.log.info("Verify terms - swedish");
        common.verifyStringOnPage("Registrera: Godkänn användarvillkor");
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
        common.verifyStringOnPage("Register: Approve terms of use");
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

    private void setGivenName(){
        //Select random given name from file
        List<String> lines;
        Random random = new Random();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/given_name.txt"));

            testData.setGivenName(lines.get(random.nextInt(lines.size())));
            Common.log.info("First name set to: " +testData.getGivenName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSurName(){
        //Select random sur name from file
        List<String> lines;
        Random random = new Random();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/sur_name.txt"));

            testData.setSurName(lines.get(random.nextInt(lines.size())));
            Common.log.info("Sur name set to: " +testData.getSurName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyLabelsAtConfirmEmailAddress() {
        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h1", "Register: Confirm that you are a human");
        common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/p",
                "As a protection against automated spam, you'll need to confirm that you are a human.");
        common.verifyStringOnPage("Enter the code from the image");
        common.verifyStringOnPage("Generate a new image");

        //Verify button text
        common.verifyStringById("cancel-captcha-button", "CANCEL");

        //Switch language to Swedish
        common.selectSwedish();

        //Label1
        common.verifyStringByXpath("//*[@id=\"content\"]/h1",
                "Registrera: Bekräfta att du är en människa");
        common.verifyStringByXpath("//*[@id=\"content\"]/div[1]/p",
                "Som ett skydd mot automatisk spam måste du bekräfta att du är en människa.");
        common.verifyStringOnPage("Ange koden från bilden");
        common.verifyStringOnPage("Generera en ny bild");

        //Verify button text
        common.verifyStringById("cancel-captcha-button", "AVBRYT");


        //Switch language to English
        common.selectEnglish();
    }
}