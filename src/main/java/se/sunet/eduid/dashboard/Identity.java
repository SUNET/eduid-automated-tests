package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Identity {
    private final Common common;
    private final TestData testData;
    private final Name name;

    public Identity(Common common, TestData testData, Name name) {
        this.common = common;
        this.testData = testData;
        this.name = name;
    }

    public void runIdentity(){
        common.navigateToIdentity();
        verifyPageTitle();

        if(testData.getTestSuite().equalsIgnoreCase("prod"))
            common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Ditt eduID är redo att användas");
        else {
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }

        name.runName();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("Identitet | eduID");
    }

    private void showHideIdNumber(){
        //Click on show/hide full identityNumber
        common.click(common.findWebElementById("show-hide-button"));

        common.verifyStringById("nin-number", testData.getIdentityNumber());
    }

    public void verifyLabelsSwedish() {
        Common.log.info("Verify Identity heading labels in Swedish");

        expandIdentityOptions();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identitet");

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/section/h1", "Identitet");
        common.verifyStringByXpath("//*[@id=\"content\"]/section/div/p", "För att använda vissa" +
                " tjänster behöver din identitet verifieras. Koppla din identitet till ditt eduID för att få mest användning av det.");
        common.verifyStringByXpath("//*[@id=\"content\"]/article/h2", "Välj din huvudsakliga identifieringsmetod");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/h3",
                "Svenskt personnummer eller samordningsnummer");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/p",
                "Med digitalt ID / Via post");

        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/p",
                "Verifiera att du har tillgång till ditt person- eller samordningsnummer.");

        //---- Bank ID ----
        //Button text - Bank ID
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-bankID\"]/div/p",
                "För dig som kan använda BankID");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-bankID\"]/div/h3", "MED BANKID");

        //Button text - Bank ID - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/p[1]", "För att " +
                "använda det här alternativet behöver du först skapa ett digitalt ID i BankID appen.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/p[2]", "Knappen " +
                "nedan tar dig till en extern identifieringssida, där du genom att identifiera dig med Bank ID " +
                "verifierar din identitet mot eduID.");

        //Verify link to BankID page works - bot detection will prevent this test
        //common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-se-bankID\"]/p[1]/a");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/button", "FORTSÄTT");


        //---- Freja eID ----
        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/p",
                "För dig som har eller kan skapa Freja+ genom att använda appen eller besöka ett ombud");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/h3", "MED FREJA+");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/p", "För att använda " +
                "det här alternativet behöver du först skapa ett digitalt ID i Freja appen.");

        //Verify link to Freja page works
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-se-freja\"]/p/a");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "FORTSÄTT");


        //---- Letter ----
        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/p",
                "För dig som har en svensk folkbokföringsadress");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/h3", "VIA POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p",
                "Ett brev skickas till dig med en kod. Av säkerhetsskäl är koden giltig i två veckor.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "FORTSÄTT");

        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            verifyMailTextsInPopUpSwedish();
        }

        //---- eIDAS ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/h3", "EU-medborgare");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/p", "Med eIDAS elektronisk identifiering");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[1]",
                "Om du har ett elektroniskt ID från ett eIDAS-anslutet land kan du " +
                "använda det för att bekräfta din identitet i eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[2]",
                "Knappen Fortsätt tar dig till en extern sida där du kan logga in " +
                "med ditt elektroniska ID för att koppla din identitet till eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "FORTSÄTT");

        //---- Freja without swedish personal identity number ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/h3", "De flesta länder");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/p", "Med Freja eID identitetsverifiering");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[1]",
                "Om du har ett Freja eID kan du koppla det till ditt eduID.");

        //Verify link to Freja page works
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-world\"]/p[1]/a");

        //*[@id="accordion__panel-world"]/p[1]/a
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[2]", "Knappen nedan " +
                "tar dig till en extern identifieringssida, där du genom att identifiera dig med Freja eID verifierar " +
                "din identitet mot eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/button", "FORTSÄTT");


        Common.log.info("Verify Identity heading labels in Swedish - done");
    }

    public void verifyFrejaIdLabelsSwedish(){
        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();
        Common.log.info("Verify identity labels in pop up for Freja eID - swedish");

        //Swedish
        common.verifyStringByXpath("//div/div/main/div/section/article[1]/div/div[1]/div/div/div[2]/div/dialog/div/div/div/div[1]/h5",
                "Med Freja-appen kan du skapa ett digitalt ID-kort");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Installera appen");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Skapa ett Freja+ konto (godkänd svensk e-legitimation)");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]", "Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i " +
                "din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("Tips: Du kan hitta närmsta ombud i appen");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[5]",
                "Freja+ är nu redo att användas med ditt eduID, fortsätt genom att " +
                "klicka på knappen nedan");

        common.verifyStringOnPage("Vad är Freja+?");

        //Button text
        common.verifyStringById("eidas-info-modal-accept-button", "ANVÄND MITT FREJA+");

        //Press cancel
        common.click(common.findWebElementById("eidas-info-modal-close-button"));
    }

    public void verifyLabelsEnglish(){
        //Click on english
        common.selectEnglish();
        Common.log.info("Verify Identity heading labels in English");

        expandIdentityOptions();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identity");

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/section/h1", "Identity");
        common.verifyStringByXpath("//*[@id=\"content\"]/section/div/p", "Some services need to know your real life identity. Connect your " +
                "identity to your eduID to get the most benefit from it.");
        common.verifyStringByXpath("//*[@id=\"content\"]/article/h2", "Choose your principal identification method");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/h3", "Swedish personal ID or coordination number");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/p", "With a digital ID / By post");

        //---- Bank ID ----
        //Button text - Bank ID
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-bankID\"]/div/p", "If you are able to use BankID");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-bankID\"]/div/h3", "WITH A BANKID");

        //Button text - Bank ID - Fine text
        common.timeoutMilliSeconds(500);
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/p[1]", "To use this " +
                "option you will need to first create a digital ID in the BankID app.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/p[2]", "The button " +
                "below will take you to an external identification site, where you by identifying yourself with BankID " +
                "will verify your identity towards eduID.");

        //Verify link to BankID page works - bot detection will prevent this test
        //common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-se-bankID\"]/p[1]/a");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-bankID\"]/button", "PROCEED");

        //---- Freja ----
        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/p",
                "If you are able to create a Freja+ by using the app or visiting an authorised agent");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/h3", "WITH FREJA+");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/p", "To use this " +
                "option you will need to first create a digital ID in the Freja app.");

        //Verify link to Freja page works
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-se-freja\"]/p/a");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "PROCEED");

        //---- Letter ----
        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/p", "If you are registered at your Swedish address");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/h3", "BY POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p", "You will " +
                "receive a letter which contains a code that for security reasons expires in two weeks.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "PROCEED");

        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            verifyMailTextsInPopUpEnglish();
        }


        //---- eIDAS ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/h3", "EU citizen");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/p", "With eIDAS electronic identification");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[1]",
                "If you have an electronic ID from a country connected to eIDAS, you " +
                "can connect it to your eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[2]",
                "The button below will take you to an external site where you log " +
                "in with your electronic ID to connect your identity to eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "PROCEED");


        //---- Freja without swedish personal identity number ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/h3", "Most countries");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/p",
                "With Freja eID identity verification");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[1]",
                "If you have a Freja eID you can connect it to your eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[2]", "The button " +
                "below will take you to an external identification site, where by identifying yourself with Freja eID, " +
                "you will verify your identity towards eduID.");

        //Verify link to Freja page works
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-world\"]/p[1]/a");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/button", "PROCEED");

        common.timeoutSeconds(1);

        Common.log.info("Verify Identity heading labels in English - done");

        //Click on swedish
        common.selectSwedish();

        //Expand Identity options again, since collapsed when changed language
        expandIdentityOptions();
    }

    public void verifyFrejaIdLabelsEnglish(){
        Common.log.info("Verify identity labels in pop up for Freja eID - english");

        //Click on Freja
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Verify Pop-up labels
        common.verifyStringByXpath("//div/div/main/div/section/article[1]/div/div[1]/div/div/div[2]/div/dialog/div/div/div/div[1]/h5",
                "Use Freja+ and visit a local authorised agent");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Install the app");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Create a Freja+ account (awarded the \"Svensk e-legitimation\" quality mark)");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]",
                "The app will generate a QR-code");
        common.verifyStringOnPage("Find a local authorised agent, show them a valid ID together with the QR-code and " +
                "they will be able to verify your identity");
        common.verifyStringOnPage("Tip: Use the app to find your nearest agent");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[5]", "Freja+ is now " +
                "ready to be used with your eduID, proceed by clicking the button below");

        common.verifyStringOnPage("What is Freja+?");

        //Button text
        common.verifyStringById("eidas-info-modal-accept-button", "USE MY FREJA+");
    }

    public void verifyMailTextsInPopUpEnglish(){
        Common.log.info("Verify identity information text for verify by Letter in pop up - English");

        //common.click(common.findWebElementById("accordion__heading-se-letter"));
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));

        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//*[@id=\"letter-confirm-modal\"]/div/div/div[1]/h5");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal\"]/div/div/div[1]/h5",
                "Use a code sent by post to your address");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal\"]/div/div/div[2]", "The letter will contain " +
                "a code that you enter here to verify your identity. The code sent to you will expire in 2 weeks starting " +
                "from now");

        //Close pop-up
        //common.closePopupDialog();

        //Click on accept in confirmation pop-up to receive the letter
        common.click(common.findWebElementById("letter-confirm-modal-accept-button"));
    }

    public void verifyMailTextsInPopUpSwedish(){
        Common.log.info("Verify identity information text by Letter in pop up - Swedish");

        //Click on Letter option
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));

        //Switch to pop up and verify its text
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//*[@id=\"letter-confirm-modal\"]/div/div/div[1]/h5");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal\"]/div/div/div[1]/h5",
                "Skriv in koden du fått hemskickad");
        common.verifyStringByXpath("//*[@id=\"letter-confirm-modal\"]/div/div/div[2]", "Om du accepterar att " +
                "få ett brev hem måste du skriva in koden här för att bevisa att personnumret är ditt. Av säkerhetsskäl " +
                "går koden ut om två veckor.");

        //Close pop-up
        //common.closePopupDialog();
        common.findWebElementById("letter-confirm-modal-close-button").click();
    }

    public void expandIdentityOptions(){
        //Expand buttons if not already expanded, to make all text visible
        if(common.findWebElementById("accordion__heading-swedish").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-swedish"));
        common.timeoutMilliSeconds(400);
        if(common.findWebElementById("accordion__heading-se-bankID").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-se-bankID"));
        if(common.findWebElementById("accordion__heading-se-freja").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-se-freja"));
        if(common.findWebElementById("accordion__heading-se-letter").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-se-letter"));
        if(common.findWebElementById("accordion__heading-eu").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-eu"));
        if(common.findWebElementById("accordion__heading-world").getAttribute("aria-expanded").equalsIgnoreCase("false"))
            common.click(common.findWebElementById("accordion__heading-world"));
    }
}