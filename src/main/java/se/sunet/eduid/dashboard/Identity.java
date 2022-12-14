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

        //Heading
        common.verifyStringOnPage("Koppla din identitet till ditt eduID");
        common.verifyStringOnPage("För att använda vissa tjänster behöver din identitet verifieras. " +
                "Koppla din identitet till ditt eduID för att få mest användning");
        common.verifyStringOnPage("Välj din huvudsakliga identifieringsmetod");
        common.verifyStringOnPage("Svenskt personnummer");
        common.verifyStringOnPage("Med digitalt ID-kort / Via post / Via telefon");

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
        common.verifyStringOnPage("Lägg till ditt personnummer");
        common.verifyStringOnPage("Personnummer");

        //2. Verify your id number
        common.verifyStringOnPage("Bekräfta ditt personnummer");
        common.verifyStringOnPage("Välj en lämplig metod att verifiera ditt personnummer.");

        //---- Letter ----
        //Button text - letter
        common.verifyStringOnPage("För dig som har en svensk folkbokföringsadress");

        common.verifyStringOnPage("VIA POST");

        //Button text - letter - Fine text
        common.verifyStringOnPage("Ett brev skickas till dig med en kod. Av säkerhetsskäl " +
                "är koden giltig i två veckor.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "FORTSÄTT");

        verifyMailLabelsSwedish();


        //---- Phone ----
        //Main Button text - phone
        common.verifyStringOnPage("För dig med ett telefonnummer registrerat i ditt namn");

        common.verifyStringOnPage("VIA TELEFON");

        common.verifyStringOnPage("För svenska telefonnummer angivna och bekräftade i eduID.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button", "FORTSÄTT");

        //Verify phone pop-up labels
        verifyPhoneLabelsSwedish();


        //---- Freja eID ----
        //Button text - Freja
        common.verifyStringOnPage("För dig som har eller kan skapa Freja eID+ genom att besöka ett ombud i Sverige");

        common.verifyStringOnPage("MED DIGITALT ID-KORT");

        //Button text - Freja - Fine text
         common.verifyStringOnPage("För att använda det " +
                 "här alternativet behöver du först skapa ett digitalt ID-kort i Freja eID+ appen.");

         //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "FORTSÄTT");

        verifyFrejaIdLabelsSwedish();


        //---- eIDAS ----
        //Heading
        common.verifyStringOnPage("EU-medborgare");
        common.verifyStringOnPage("Med eIDAS elektronisk identifiering");

        //Fine text
        common.verifyStringOnPage("Om du har ett elektroniskt ID från ett eIDAS-anslutet land kan du " +
                "använda det för att bekräfta din identitet i eduID.");
        common.verifyStringOnPage("Knappen Fortsätt tar dig till en extern sida där du kan logga in " +
                "med ditt elektroniska ID för att koppla din identitet till eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "FORTSÄTT");

        //---- SVIPE ----
        //Heading
        common.verifyStringOnPage("Alla andra länder");
        common.verifyStringOnPage("Med Svipe ID kryptografisk identitetsverifiering");

        //Fine text
        common.verifyStringOnPage("Om du har ett Svipe ID kan du koppla det till ditt eduID.");
        common.verifyStringOnPage("Knappen nedan tar dig till en extern identifieringssida, där du " +
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
        common.verifyStringOnPage("Med Freja eID-appen kan du skapa ett digitalt ID-kort");
        common.verifyStringOnPage("Installera appen");
        common.verifyStringOnPage("Skapa ett Freja eID+ konto (godkänd svensk e-legitimation)");
        common.verifyStringOnPage("Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i " +
                "din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("Tips: Du kan hitta närmsta ombud i appen");
        common.verifyStringOnPage("Freja eID är nu redo att användas med ditt eduID, fortsätt genom att " +
                "klicka på knappen nedan");

        //Button text
        common.verifyStringById("eidas-info-modal-accept-button", "ANVÄND MITT FREJA EID+");

        //Press cancel
        common.click(common.findWebElementById("eidas-info-modal-close-button"));
    }

    public void verifyLabelsEnglish(){
        Common.log.info("Verify Identity labels in English");

        //Click on english
        common.selectEnglish();

        //Heading
        common.verifyStringOnPage("Connect your identity to your eduID");
        common.verifyStringOnPage("Some services need to know your real life identity. Connect your " +
                "identity to your eduID to get the most benefit from");
        common.verifyStringOnPage("Choose your principal identification method");
        common.verifyStringOnPage("Swedish personal ID number");
        common.verifyStringOnPage("With a digital ID-card / By post / By phone");

        //Expand all selections with verify identity options
        //First need to collapse to default
        common.scrollToPageTop();
        common.findWebElementById("accordion__heading-swedish").click();
        expandIdentityOptions();

        common.verifyStringById("show-hide-button", "SHOW");

        //Verify identity
        showHideIdNumber();


        //1. Add your id number
        common.verifyStringOnPage("Add your id number");
        common.verifyStringOnPage("Id number");

        //2. Verify your id number
        common.verifyStringOnPage("Verify your id number");
        common.verifyStringOnPage("Choose a suitable method to verify that you have access to the added id number.");


        //---- Letter ----
        //Button text - letter
        common.verifyStringOnPage("For you officially registered at an address in Sweden");

        common.verifyStringOnPage("BY POST");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-letter\"]/button", "PROCEED");

        //Button text - letter - Fine text
        common.verifyStringOnPage("You will receive a letter which contains a code that for " +
                "security reasons expires in two weeks.");

        //Verify Letter pop up labels
        verifyMailLabelsEnglish();

        //---- Phone ----
        //Button text - phone
        common.verifyStringOnPage("For you with a phone number registered in your name");

        common.verifyStringOnPage("BY PHONE");

        //Button text - phone - Fine text
        common.verifyStringOnPage("For Swedish phone numbers entered and confirmed in eduID.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button", "PROCEED");

        //Verify phone pop-up labels
        verifyPhoneLabelsEnglish();

        //---- Freja ----
        //Button text - Freja
        common.verifyStringOnPage("For you able to create a Freja eID+ by visiting one of the authorised agents");

        common.verifyStringOnPage("WITH A DIGITAL ID-CARD");

        //Button text - Freja - Fine text
        common.verifyStringOnPage("To use this option " +
                "you first need to create a digital ID-card in the Freja eID+ app.");

        //Verify button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-se-freja\"]/button", "PROCEED");

        //Verify Freja pop-up labels
        verifyFrejaIdLabelsEnglish();


        //---- eIDAS ----
        //Heading
        common.verifyStringOnPage("EU citizen");
        common.verifyStringOnPage("With eIDAS electronic identification");

        //Fine text
        common.verifyStringOnPage("If you have an electronic ID from a country connected to eIDAS, you " +
                "can connect it to your eduID.");
        common.verifyStringOnPage("The button below will take you to an external site where you log " +
                "in with your electronic ID to connect your identity to eduID.");

        //Button text
        common.verifyStringByXpath("//*[@id=\"accordion__panel-eu\"]/button", "PROCEED");


        //---- SVIPE ----
        //Heading
        common.verifyStringOnPage("All other countries");
        common.verifyStringOnPage("With Svipe ID cryptographic identity verification");

        //Fine text
        common.verifyStringOnPage("If you have a Svipe ID you can connect it to your eduID.");
        common.verifyStringOnPage("The button below will take you to an external identification site, " +
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
                "Use Freja eID+ and visit a local authorised agent");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Install the app");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Create a Freja eID+ account (awarded the \"Svensk e-legitimation\" quality mark)");
        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]",
                "The app will generate a QR-code");
        common.verifyStringOnPage("Find a local authorised agent, show them a valid ID together with the QR-code and " +
                "they will be able to verify your identity");
        common.verifyStringOnPage("Tip: Use the app to find your nearest agent");
        common.verifyStringOnPage("Freja eID is now ready to be used with your eduID, proceed by " +
                "clicking the button below");

        //Button text
        common.verifyStringById("eidas-info-modal-accept-button", "USE MY FREJA EID+");

        //Press cancel
        common.click(common.findWebElementById("eidas-info-modal-close-button"));
    }


    private void verifyPhoneLabelsSwedish(){
        common.click(common.findWebElementByXpath("//*[@id=\"accordion__panel-se-phone\"]/div/button"));

        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
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
        //div[2]/div/div[1]/div/div/div[2]",
        common.verifyStringOnPage("Om du accepterar att " +
                "få ett brev hem måste du skriva in koden här för att bevisa att personnumret är ditt. Av säkerhetsskäl " +
                "går koden ut om två veckor.");

        //Click first on abort
        common.closePopupDialog();
    }

    public void expandIdentityOptions(){
        //Scroll down to bottom of page, otherwise we get click exception
        common.scrollToPageBottom();
        common.timeoutMilliSeconds(200);

        //Expand buttons, all text is visible
        common.findWebElementById("accordion__heading-swedish").click();
        common.timeoutMilliSeconds(100);
        common.scrollToPageBottom();
        common.timeoutMilliSeconds(100);

        common.click(common.findWebElementById("accordion__heading-se-freja"));
        common.timeoutMilliSeconds(100);
        common.click(common.findWebElementById("accordion__heading-se-letter"));
        common.timeoutMilliSeconds(100);
        common.click(common.findWebElementById("accordion__heading-se-phone"));
        common.timeoutMilliSeconds(100);
        common.scrollToPageBottom();
        common.timeoutMilliSeconds(100);
        common.click(common.findWebElementById("accordion__heading-eu"));
        common.scrollToPageBottom();
        common.timeoutMilliSeconds(100);
        common.click(common.findWebElementById("accordion__heading-world"));
        common.timeoutMilliSeconds(100);
    }
}