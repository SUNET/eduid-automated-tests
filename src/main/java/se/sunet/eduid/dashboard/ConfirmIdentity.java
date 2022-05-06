package se.sunet.eduid.dashboard;

import org.openqa.selenium.Cookie;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;
import java.util.Date;

public class ConfirmIdentity{
    private final Common common;
    private final TestData testData;

    public ConfirmIdentity(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runConfirmIdentity(){
        verifyPageTitle();
        pressIdentity();
        verifyLabels();
        enterPersonalNumber();
        pressAddButton();
        selectConfirmIdentity();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID dashboard");

        //TODO temp fix to get swedish language
//        if (common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
//            common.selectSwedish();
    }

    public void pressIdentity(){
        common.navigateToIdentity();
    }

    private void enterPersonalNumber(){
        common.findWebElementById("nin").sendKeys(testData.getIdentityNumber());
    }

    private void pressAddButton(){
        common.click(common.findWebElementByXpath("//*[@id=\"nin-form\"]/button"));
        common.timeoutMilliSeconds(500);

        //common.verifyStatusMessage("Personnummer har lagts till.");
    }

    private void selectConfirmIdentity(){
        //Select way to confirm the identity. By letter, By phone or Freja Id
        if(testData.getConfirmIdBy().equalsIgnoreCase("mail")) {
            //Verify mail pop-up labels
            verifyMailLabels();

            //Click on mail again, switch to pop-up
            common.timeoutMilliSeconds(200);
            common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));
            common.switchToPopUpWindow();

            //Click on accept
            common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]"));

            //Verify labels when letter is sent
            verifyLabelsSentLetter();

            //Press again on the letter button - Add a faulty code
            common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));
            common.explicitWaitVisibilityElementId("letterConfirmDialogControl");
            common.findWebElementById("letterConfirmDialogControl").sendKeys("1qvw3fw2q3");

            //Click OK
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));

            //Verify response
            common.timeoutSeconds(1);
            common.verifyStatusMessage("Den bekräftelsekod du angett stämmer inte. Var god försök igen");

            //Fetch the code
            common.navigateToUrl("https://dashboard.dev.eduid.se/services/letter-proofing/get-code");
            String letterProofingCode = common.findWebElementByXpath("/html/body").getText();
            Common.log.info("Letter proofing code: " +letterProofingCode);

            common.getWebDriver().navigate().back();
            common.timeoutSeconds(1);

            //Press again on the letter button - Add the correct code
            common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));
            common.findWebElementById("letterConfirmDialogControl").sendKeys(letterProofingCode);

            //Click OK
            common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button[1]"));
        }

        //By phone
        else if(testData.getConfirmIdBy().equalsIgnoreCase("phone")) {
            //Verify labels in pop up
            //TODO metod verifyPhoneLables has different message when the phone number NOT has been confirmed
            verifyPhoneLabels();

            //Click on phone option
            common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button"));

            //Press accept button
            common.click(common.findWebElementByXpath("//div[2]/div/div[1]/div/div/div[3]/button[1]"));
        }
        //Freja eID - first check that we comes to freja eID page. then add magic cookies to confirm the identity
        else {
            //TODO Verifiera freja id med nin cookie satt
            //Select Freja eID
            common.click(common.findWebElementByXpath("//*[@id=\"eidas-show-modal\"]"));

            //Verify the labels
            verifyFrejaIdLabels();

            //Remove the magic cookie
            common.getWebDriver().manage().deleteCookieNamed("autotests");
            //WebDriverManager.getWebDriver().manage().deleteCookieNamed("autotests");

            common.timeoutMilliSeconds(500);
            common.click(common.findWebElementByLinkText("ANVÄND MITT FREJA EID"));

            //Verify Freja eID page title
            common.explicitWaitPageTitle("Freja eID IDP");
            common.verifyPageTitle("Freja eID IDP");

            //Navigate back to eduID
            common.getWebDriver().navigate().back();

            //Press use Freja eID, after adding the nin cookie
            Date today    = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

            Cookie cookie = new Cookie.Builder("nin", "197501100395")
                    .domain(".dev.eduid.se")
                    .expiresOn(tomorrow)
                    .build();

            //WebDriverManager.getWebDriver().manage().addCookie(cookie);
            common.getWebDriver().manage().addCookie(cookie);

            //Add magic cookie
            common.addMagicCookie();
            common.timeoutMilliSeconds(500);

            //Select Freja eID
            common.click(common.findWebElementByXpath("//*[@id=\"eidas-show-modal\"]"));
            common.timeoutMilliSeconds(500);

            common.click(common.findWebElementByLinkText("ANVÄND MITT FREJA EID"));
            common.timeoutMilliSeconds(500);
        }
    }

    private void verifyLabels() {
        //Swedish
        common.timeoutMilliSeconds(500);

        //Verify placeholder
        common.verifyStrings("ååååmmddnnnn", common.findWebElementById("nin").getAttribute("placeholder"));

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3", "Koppla din identitet till ditt eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p", "För att kunna " +
                "använda eduID måste du bevisa din identitet. Lägg till ditt personnummer och bekräfta det i verkliga livet.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h4[1]", "1. Lägg till ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"nin-wrapper\"]/div/label", "Personnummer");
        common.verifyStringByXpath("//*[@id=\"nin-wrapper\"]/div/span", "personnummer med 12 siffror");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h4[2]", "2. Bekräfta ditt personnummer");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p", "Välj ett sätt att bekräfta att " +
                "du har tillgång till det angivna personnumret. Om en av metoderna inte fungerar får du prova en annan.");

        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]",
                "FÖR DIG SOM HAR TILLGÅNG TILL DIN FOLKBOKFÖRINGSADDRESS\nBÖRJA MED ATT LÄGGA TILL DITT PERSONNUMMER HÄR OVAN");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[2]", "VIA POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p", "Brevet innehåller " +
                "en bekräftelsekod som av säkerhetsskäl går ut efter två veckor.");

        //Button text - phone
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[1]",
                "FÖR DIG SOM HAR ETT SVENSKT TELEFONABONNEMANG I DITT EGET NAMN\nBÖRJA MED ATT LÄGGA TILL DITT PERSONNUMMER HÄR OVAN");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[2]",
                "VIA TELEFON");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p", "Registret med " +
                "telefonnummer uppdateras av mobiloperatörerna och innehåller inte nödvändigtvis alla nummer.");

        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[1]",
                "FÖR DIG SOM HAR ELLER KAN SKAPA FREJA EID GENOM ATT BESÖKA ETT OMBUD I SVERIGE");

        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[2]", "MED DIGITALT ID-KORT");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p", "För att använda " +
                "det här alternativet måste du först skaffa ett digitalt ID-kort i Freja eID appen.");

        //English

        //Click on english
        common.selectEnglish();

        //Verify placeholder
        common.verifyStrings("yyyymmddnnnn", common.findWebElementById("nin").getAttribute("placeholder"));

        //Heading
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/h3", "Connect your identity to your eduID");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/div[1]/p", "To be able to use " +
                "eduID you have to prove your identity. Add your national id number and verify it in real life.");

        //1. Add your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h4[1]", "1. Add your id number");
        common.verifyStringByXpath("//*[@id=\"nin-wrapper\"]/div/label", "Id number");
        common.verifyStringByXpath("//*[@id=\"nin-wrapper\"]/div/span", "national identity number with 12 digits");

        //2. Verify your id number
        common.verifyStringByXpath("//*[@id=\"text-content\"]/h4[2]", "2. Verify your id number");
        common.verifyStringByXpath("//*[@id=\"text-content\"]/p", "Choose a method to verify " +
                "that you have access to the added id number. If you are unable to use a method you need to try another.");


        //Button text - letter
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]",
                "FOR YOU REGISTERED AT YOUR CURRENT ADDRESS\nSTART BY ADDING YOUR ID NUMBER ABOVE");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[2]", "BY POST");

        //Button text - letter - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/p", "The letter will contain " +
                "a code that for security reasons expires in two weeks.");

        //Button text - phone
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[1]",
                "FOR YOU WITH A SWEDISH PHONE NUMBER REGISTERED IN YOUR NAME\nSTART BY ADDING YOUR ID NUMBER ABOVE");

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button/div[2]",
                "BY PHONE");

        //Button text - phone - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/p", "The phone number registry " +
                "is maintained by phone operators at their convenience and may not include all registered phone numbers.");

        //Button text - Freja
        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[1]",
                "FOR YOU ABLE TO CREATE A FREJA EID BY VISITING ONE OF THE AUTHORISED AGENTS");

        common.verifyStringByXpath("//*[@id=\"eidas-show-modal\"]/div[2]", "WITH A DIGITAL ID-CARD");

        //Button text - Freja - Fine text
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[3]/p", "To use this option you " +
                "will need to first create a digital ID-card in the Freja eID app.");

        //Click on Freja
        common.click(common.findWebElementById("eidas-show-modal"));

        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");

        //Verify Pop-up labels
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5",
                "Use Freja eID+ and pass a local authorised agent");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[1]", "Install the app");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[2]",
                "Create a Freja eID Plus account (awarded the ‘Svensk e-legitimation’ quality mark)");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[3]", "The app will generate a QR-code");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[4]",
                "Find a local authorised agent, show them a valid ID together with the QR-code and " +
                        "they will be able to verify your identity");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/label",
                "Tip: Use the app to find your nearest agent");

        common.verifyStringByXpath("//*[@id=\"freja-instructions\"]/ol/li[5]",
                "Freja eID is now ready to be used with your eduID");

        //Press cancel
        common.click(common.findWebElementById("eidas-hide-modal"));
        common.timeoutMilliSeconds(500);

        //Click on swedish
        common.selectSwedish();
    }

    private void verifyFrejaIdLabels(){
        //Switch to the Freja Id pop up
        common.switchToPopUpWindow();

        //Swedish
        common.explicitWaitVisibilityElement("//div/div[1]/h5");
        common.verifyStringOnPage("Med Freja eID appen kan du skapa ett digitalt ID-kort");
        common.verifyStringOnPage("Installera appen");
        common.verifyStringOnPage("Skapa ett Freja eID Plus konto (godkänd svensk e-legitimation)");
        common.verifyStringOnPage("Appen genererar en QR-kod");
        common.verifyStringOnPage("Gå till närmsta ombud, visa giltigt ID tillsammans med QR-koden i " +
                "din telefon och låt dem bevisa din identitet");
        common.verifyStringOnPage("Tips: Du kan hitta närmsta ombud i appen");
        common.verifyStringOnPage("Freja eID är redo att användas med ditt eduID");
    }


    private void verifyPhoneLabels(){
        //Click on phone option
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button"));

        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage("Kolla om ditt telefonnummer är kopplat till ditt personnummer.");
        common.verifyStringOnPage("Du kan acceptera en sökning i telefonregistret för att se om ditt " +
                "telefonnummer finns lagrat tillsammans med ditt personnummer. Om dina uppgifter inte lagts till i " +
                "registret av din mobiloperatör kommer vi inte kunna bevisa din identitet via telefonnummer.");

        //Close pop up
        common.closePopupDialog();

        //English
        common.selectEnglish();

        //Click on phone option
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[2]/div/div[1]/button"));

        //Switch to pop up
        common.switchToPopUpWindow();

        //Verify labels in pop-up
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringOnPage("Check if your phone number is connected to your id number.");
        common.verifyStringOnPage("This check will be done in a registry updated by the phone operators. " +
                "If they have not added your details, we won't be able to find you and you need to choose another way " +
                "to verify your identity.");

        //Close pop up
        common.closePopupDialog();

        //English
        common.selectSwedish();
        /*
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5/span");
        common.verifyStringOnPage("Lägg till ditt telefonnummer för att fortsätta");
        common.verifyStringOnPage("Lägg till ditt telefonnummer i Inställningar. Glöm inte att också " +
                "bekräfta att det är ditt! Du kan bara bevisa din identitet med ett bekräftat telefonnummer!");
                */
    }

    private void verifyMailLabels(){
        //Click on mail option
        common.timeoutMilliSeconds(200);
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));

        //Switch to pop up and verify its text
        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5", "Få en bekräftelsekod via post");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[2]", "Om du accepterar " +
                "att få ett brev hem måste du skriva in bekräftelsekoden här för att bevisa att personnumret är ditt. " +
                "Av säkerhetsskäl går koden ut om två veckor.");

        //Click first on abort
        common.closePopupDialog();

        //English
        common.selectEnglish();

        //Switch to pop up and verify its text
        //Click on mail option
        common.timeoutMilliSeconds(200);
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));

        common.switchToPopUpWindow();
        common.explicitWaitVisibilityElement("//div[2]/div/div[1]/div/div/div[1]/h5");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[1]/h5", "Use a confirmation " +
                "code sent by post to your house");
        common.verifyStringByXpath("//div[2]/div/div[1]/div/div/div[2]", "The letter will contain " +
                "a code that you enter here to verify your identity. The code sent to you will expire in 2 weeks starting " +
                "from now");

        //Click first on abort
        common.closePopupDialog();

        //English
        common.selectSwedish();
    }

    private void verifyLabelsSentLetter(){
        //Verify on the button that letter is sent text exists, with today's date
        common.explicitWaitVisibilityElement("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[1]");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[1]",
                "ETT BREV SKICKADES\n" +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[2]",
                "BREVET ÄR GILTIGT TILL\n" +common.getDate().plusDays(15));

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[3]",
                "TRYCK HÄR IGEN NÄR DU HAR FÅTT BREVET");

        //English
        common.selectEnglish();

        //Verify on the button that letter is sent text exists, with today's date
        common.timeoutMilliSeconds(200);
        common.explicitWaitVisibilityElement("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[1]");
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[1]",
                "THE LETTER WAS SENT\n" +common.getDate().toString());

        //Verify that letter is valid date is 2 weeks after today's date
        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[2]",
                "THE LETTER IS VALID TO\n" +common.getDate().plusDays(15));

        common.verifyStringByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button/div[1]/div[3]",
                "CLICK HERE AGAIN WHEN YOU HAVE RECEIVED THE LETTER");

        //Verify the placeholder
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));
        common.verifyStrings("Letter confirmation code", common.findWebElementById("letterConfirmDialogControl").getAttribute("placeholder"));
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //English
        common.selectSwedish();

        //Verify the placeholder
        common.click(common.findWebElementByXpath("//*[@id=\"nins-btn-grid\"]/div[1]/div/div[1]/button"));
        common.verifyStrings("Bekräftelsekod", common.findWebElementById("letterConfirmDialogControl").getAttribute("placeholder"));
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
    }
}