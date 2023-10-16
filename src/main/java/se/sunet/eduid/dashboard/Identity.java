package se.sunet.eduid.dashboard;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Identity {
    private final Common common;
    private final TestData testData;

    public Identity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runIdentity(){
        common.navigateToIdentity();
        verifyPageTitle();
        showHideIdNumber();

        if(testData.getTestSuite().equalsIgnoreCase("prod"))
            common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h4", "Ditt eduID är redo att användas");
        else {
            //Expand all selections with verify identity options
            if(!testData.isRegisterAccount())
                common.findWebElementById("accordion__heading-swedish").click();
            expandIdentityOptions();
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }
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
        Common.log.info("Verify Identity labels in Swedish");

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identitet");

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/section/h1", "Koppla din identitet till ditt eduID");
        common.verifyStringByXpath("//*[@id=\"content\"]/section/div/p", "För att använda vissa tjänster behöver din identitet verifieras. " +
                "Koppla din identitet till ditt eduID för att få mest användning");
        common.verifyStringByXpath("//*[@id=\"content\"]/article/h2", "Välj din huvudsakliga identifieringsmetod");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/h3", "Svenskt personnummer");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/p", "Med digitalt ID-kort / Via post / Via telefon");

        //Show/hide identityNumber
        if(testData.isRegisterAccount()) {
            common.verifyStringById("show-hide-button", "VISA");
        }
        else {
            common.verifyStringById("show-hide-button", "DÖLJ");
            common.click(common.findWebElementById("show-hide-button"));
        }

        //Verify identity number
        showHideIdNumber();


        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[1]/h4", "Lägg till ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[1]/div/span/strong", "Personnummer");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[2]/h4", "Bekräfta ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[2]/p", "Välj en lämplig metod att verifiera ditt personnummer.");

        //---- Letter ----
        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/p", "För dig som har en svensk folkbokföringsadress");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/h3", "VIA POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p", "Ett brev skickas till dig med en kod. Av säkerhetsskäl " +
                "är koden giltig i två veckor.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "FORTSÄTT");

        verifyMailLabelsSwedish();


        //---- Phone ----
        //Main Button text - phone
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-phone\"]/div/p", "För dig med ett telefonnummer registrerat i ditt namn");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-phone\"]/div/h3", "VIA TELEFON");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/p[1]", "För svenska telefonnummer angivna och bekräftade i eduID.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button", "FORTSÄTT");

        //Verify phone pop-up labels
        if(!testData.getPhoneNumber().isEmpty())
            verifyPhoneLabelsSwedish();


        //---- Freja eID ----
        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/p",
                "För dig som har eller kan skapa Freja+ genom att besöka ett ombud i Sverige");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/h3", "MED DIGITALT ID-KORT");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/p", "För att använda det " +
                 "här alternativet behöver du först skapa ett digitalt ID-kort i Freja eID+ appen.");

         //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "FORTSÄTT");

        verifyFrejaIdLabelsSwedish();


        //---- eIDAS ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/h3", "EU-medborgare");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/p", "Med eIDAS elektronisk identifiering");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[1]", "Om du har ett elektroniskt ID från ett eIDAS-anslutet land kan du " +
                "använda det för att bekräfta din identitet i eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[2]", "Knappen Fortsätt tar dig till en extern sida där du kan logga in " +
                "med ditt elektroniska ID för att koppla din identitet till eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "FORTSÄTT");

        //---- SVIPE ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/h3", "Alla andra länder");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/p", "Med Svipe ID kryptografisk identitetsverifiering");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[1]", "Om du har ett Svipe ID kan du koppla det till ditt eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[2]", "Knappen nedan tar dig till en extern identifieringssida, där du " +
                "genom att identifiera dig med Svipe ID verifierar din identitet mot eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/button", "FORTSÄTT");

        Common.log.info("Verify Identity labels in Swedish - done");
    }

    private void verifyFrejaIdLabelsSwedish(){
        //Click on Freja
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Swedish
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
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
        Common.log.info("Verify Identity labels in English");

        //Click on english
        common.selectEnglish();

        //Verify site location menu, beside Start link
        common.verifySiteLocation("Identity");

        //Heading
        common.verifyStringByXpath("//*[@id=\"content\"]/section/h1", "Connect your identity to your eduID");
        common.verifyStringByXpath("//*[@id=\"content\"]/section/div/p", "Some services need to know your real life identity. Connect your " +
                "identity to your eduID to get the most benefit from");
        common.verifyStringByXpath("//*[@id=\"content\"]/article/h2", "Choose your principal identification method");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/h3", "Swedish personal ID number");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-swedish\"]/div/p", "With a digital ID-card / By post / By phone");

        //Expand all selections with verify identity options
        //First need to collapse to default
        common.click(common.findWebElementById("accordion__heading-swedish"));
        expandIdentityOptions();

        common.verifyStringById("show-hide-button", "SHOW");

        //Verify identity
        showHideIdNumber();


        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[1]/h4", "Add your id number");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[1]/div/span/strong", "Id number");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[2]/h4", "Verify your id number");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-swedish\"]/ol/li[2]/p", "Choose a suitable method to verify that you have access to the added id number.");


        //---- Letter ----
        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/p", "For you officially registered at an address in Sweden");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-letter\"]/div/h3", "BY POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/p", "You will " +
                "receive a letter which contains a code that for security reasons expires in two weeks.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "PROCEED");

        //Verify Letter pop up labels
        verifyMailLabelsEnglish();

        //---- Phone ----
        //Button text - phone
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-phone\"]/div/p", "For you with a phone number registered in your name");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-phone\"]/div/h3", "BY PHONE");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/p[1]", "For Swedish phone numbers entered and confirmed in eduID.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button", "PROCEED");

        //Verify phone pop-up labels
        if(!testData.getPhoneNumber().isEmpty())
            verifyPhoneLabelsEnglish();

        //---- Freja ----
        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/p", "For you able to create a Freja+ by visiting one of the authorised agents");

        common.verifyStringByXpath("//*[@id=\"accordion__heading-se-freja\"]/div/h3", "WITH A DIGITAL ID-CARD");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/p", "To use this option " +
        "you first need to create a digital ID-card in the Freja eID+ app.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "PROCEED");

        //Verify Freja pop-up labels
        verifyFrejaIdLabelsEnglish();


        //---- eIDAS ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/h3", "EU citizen");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-eu\"]/div/p", "With eIDAS electronic identification");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[1]", "If you have an electronic ID from a country connected to eIDAS, you " +
                "can connect it to your eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/p[2]", "The button below will take you to an external site where you log " +
                "in with your electronic ID to connect your identity to eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "PROCEED");


        //---- SVIPE ----
        //Heading
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/h3", "All other countries");
        common.verifyStringByXpath("//*[@id=\"accordion__heading-world\"]/div/p", "With Svipe ID cryptographic identity verification");

        //Fine text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[1]", "If you have a Svipe ID you can connect it to your eduID.");
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/p[2]", "The button below will take you to an external identification site, "+
                "where you by identifying yourself with Svipe ID will verify your identity towards eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-world\"]/button", "PROCEED");
        common.timeoutSeconds(1);

        //Click on swedish
        common.selectSwedish();

        Common.log.info("Verify Identity labels in English - done");
    }

    private void verifyFrejaIdLabelsEnglish(){
        //Click on Freja
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-freja\"]/button"));

        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Verify Pop-up labels
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
                "Use Freja+ and visit a local authorised agent");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Install the app");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Create a Freja+ account (awarded the \"Svensk e-legitimation\" quality mark)");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]",
                "The app will generate a QR-code");
        common.verifyStringOnPage("Find a local authorised agent, show them a valid ID together with the QR-code and " +
                "they will be able to verify your identity");
        common.verifyStringOnPage("Tip: Use the app to find your nearest agent");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[5]", "Freja+ is now ready to be used with your eduID, proceed by " +
                "clicking the button below");

        common.verifyStringOnPage("What is Freja+?");

        //Button text
        common.verifyStringById("eidas-info-modal-accept-button", "USE MY FREJA+");

        //Press cancel
        common.click(common.findWebElementById("eidas-info-modal-close-button"));
    }


    private void verifyPhoneLabelsSwedish(){
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button"));

        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up - if this fails, problem might be that phone number is not confirmed and button click
        //above fails due to inactive button.
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage("Kolla om ditt telefonnummer är kopplat till ditt personnummer.");
        common.verifyStringOnPage("Den här kontrollen görs mot ett register som innehåller uppgifter " +
                "från telefonoperatörerna. Om dina uppgifter inte stämmer behöver du verifiera dig på ett annat sätt.");

        //Close pop up
        common.closePopupDialog();
    }

    private void verifyPhoneLabelsEnglish(){
        //Click on phone option
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button"));

        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage("Check if your phone number is connected to your id number.");
        common.verifyStringOnPage("This check will be done in a registry updated by the phone " +
                "operators. If it doesn't find your details you need to choose another way to verify your identity.");

        //Close pop up
        common.closePopupDialog();
    }

    private void verifyMailLabelsEnglish(){
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));

        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
                "Use a code sent by post to your address");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[2]", "The letter will contain " +
                "a code that you enter here to verify your identity. The code sent to you will expire in 2 weeks starting " +
                "from now");

        //Click first on abort
        common.closePopupDialog();
    }

    private void verifyMailLabelsSwedish(){
        //Click on Letter option
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-letter\"]/button"));

        //Switch to pop up and verify its text
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
                "Skriv in koden du fått hemskickad");
        common.verifyStringOnPage("Om du accepterar att " +
                "få ett brev hem måste du skriva in koden här för att bevisa att personnumret är ditt. Av säkerhetsskäl " +
                "går koden ut om två veckor.");

        //Click first on abort
        common.closePopupDialog();
    }

    public void expandIdentityOptions(){
        //Expand buttons, all text is visible
        common.click(common.findWebElementById("accordion__heading-swedish"));
        common.click(common.findWebElementById("accordion__heading-se-freja"));
        common.click(common.findWebElementById("accordion__heading-se-letter"));
        common.click(common.findWebElementById("accordion__heading-se-phone"));
        common.click(common.findWebElementById("accordion__heading-eu"));
        common.click(common.findWebElementById("accordion__heading-world"));
    }
}