package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Help {
    private final Common common;

    public Help(Common common){
        this.common = common;
    }

    public void runHelp(){
        clickHelp();
        verifySwedish();
        verifyEnglish();
    }

    //Click on help button
    private void clickHelp(){
        //Click on Help link
        common.click(common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a"));

        //Wait for header "What is eduID"
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/div/ul/li[1]/h2");
    }

    //Verify text and headings in swedish
    private void verifySwedish(){
        //Select Swedish
        if(common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul").getText().equalsIgnoreCase("svenska"))
            common.selectSwedish();

        //Heading 1
        common.verifyStringOnPage("Vad är eduID?");

        //Text 1
        common.verifyStringOnPage("eduID är en federerad identitet – en användaridentitet som kan " +
                "användas inom flera olika organisationer eftersom man har enats kring hur identiteter ska hanteras. " +
                "Grundidén är att en given användare som autentiserat sig hos en organisation per automatik kan bli " +
                "autentiserad hos en annan som ingår i federationen.");
        common.verifyStringOnPage("För federerade identiteter är en av grundstenarna tillit mellan " +
                "organisationer. Tilliten ligger i att en organisationerna litar på att alla genomför sin autentisering, " +
                "identifiering plus verifiering, på ett korrekt sätt och att den genomförs i en kontrollerad och " +
                "tillförlitlig IT-miljö. Därför är eduID kantaracertifierad.");

        //Heading 2
        common.verifyStringOnPage("Varför finns eduID?");

        //Text 2
        common.verifyStringOnPage("För studenters del leder eduID på sikt till ett färre antal konton " +
                "en student behöver hålla reda på under sin studietid. För många organisationer är identitetshantering " +
                "en komplex fråga, och det finns ett behov att arbeta med bekräftade användare.");
        common.verifyStringOnPage("Det är många tjänster som har behov av identifiering av användare. " +
                "Ofta sker detta genom att användaren anger en e-postadress dit tjänsten skickar ett lösenord och för " +
                "många tjänster är detta en tillräcklig nivå. En sådan användare kallas ofta obekräftad, eftersom " +
                "tjänsten egentligen inte vet vem användaren med e-postadressen är. Genom eduID kan identifieringen av " +
                "användare lyftas ett steg, till att bli bekräftade användare.");

        //Heading 3
        common.verifyStringOnPage("Hur använder jag eduID?");

        //Text 3
        common.verifyStringOnPage("När du väl skapat ditt konto kommer du nästan aldrig behöva logga in " +
                "på eduID. Ditt konto använder du bara för att skapa och komma åt andra konton, exempelvis på antagning.se " +
                "eller dina studentkonton.");
        common.verifyStringOnPage("Logga in med eduID gör du när du:\n" +
                "♦ Vill tacka ja till din plats på högskolan\n" +
                "♦ Ordnar med studentkonto för mail och intranät\n" +
                "♦ Byter lärosäte\n" +
                "♦ Tappar ett lösenord");

        //Heading 4
        common.verifyStringOnPage("Hur hanterar eduID mina personuppgifter?");

        //Text 4
        common.verifyStringOnPage("Läs mer på www.sunet.se.");

        //Heading 5
        common.verifyStringOnPage("Vad är SUNET?");

        //Text 5
        common.verifyStringOnPage("SUNET är den organisation som är ansvarig för det svenska " +
                "universitets- och datanätverket och även flertal tjänster som används av universitet och högskolor i Sverige.");
        common.verifyStringOnPage("SUNET arbetar sedan länge med identitetshantering, och för att " +
                "underlätta för alla parter i högskolevärlden har SUNET tagit fram eduID. Mer information om SUNET finns på www.sunet.se.");

        //Heading 6
        common.verifyStringOnPage("Hur byter jag standardspråk i eduID?");

        //Text 6
        common.verifyStringOnPage("För att byta standardspråk kan du logga in på eduID och välja önskat " +
                "språk under fliken för personlig information. Det språk som kommer användas som standard i eduID är " +
                "baserat på den språkinställning som din webbläsare använder.");

        //Heading 7
        common.verifyStringOnPage("Vilket e-postkonto ska jag använda för att logga in?");

        //Text 7
        common.verifyStringOnPage("Du kan logga in med alla e-postadresser som du angett och bekräftat i eduID.");

        //Heading 8
        common.verifyStringOnPage("Vad är Freja eID?");

        //Text 8
        common.verifyStringOnPage("Freja eID är ett kostnadsfritt digitalt ID-kort.");
        common.verifyStringOnPage("Så här skaffar du Freja eID:\n" +
                "♦ Installera Freja eID appen på din mobila enhet (iOS eller Android)\n" +
                "♦ Skapa ett Freja eID Plus konto\n" +
                "♦ Ta med giltig legitimation till närmsta ATG ombud som kan påbörja en process som godkänner din identitet");

        //Heading 9
        common.verifyStringOnPage("Varför måste jag besöka ett ATG ombud för att skapa Freja eID?");

        //Text 9
        common.verifyStringOnPage("ATG ombudet kan påbörja en kontroll av din legitimation genom att " +
                "scanna den QR-kod som Freja eID appen genererat och följa instruktionerna på sin skärm. Du kommer bli " +
                "informerad när legitimationskontollen är slutförd och ditt Freja eID är klart att användas.");

        //Heading 10
        common.verifyStringOnPage("Hur hittar jag ett ATG ombud som kan göra en legitimationskontroll för Freja eID?");

        //Text 10
        common.verifyStringOnPage("Ditt närmsta ATG ombud kan hittas i appen.");

        //Heading 11
        common.verifyStringOnPage("Vad ska jag göra om legitimationskontrollen för Freja eID misslyckades?");

        //Text 11
        common.verifyStringOnPage("Avinstallera appen, gör om registreringen, samt kontrollera noga att " +
                "du angivit rätt datum då ID-handlingen upphör att gälla samt att du fyllt i rätt referensnummer och personnummer.");

        //Heading 12
        common.verifyStringOnPage("Hur använder jag Freja eID med eduID?");

        //Text 12
        common.verifyStringOnPage("Logga in på eduID och klicka på 'Använd mitt Freja eID'.");

        //Heading 13
        common.verifyStringOnPage("Hur lång tid tar legitimationskontrollen för Freja eID?");

        //Text 13
        common.verifyStringOnPage("Det kan ta upp till 3 timmar att för din legetimation att bli godkänd.");

        //Heading 14
        common.verifyStringOnPage("Vad är ORCID?");

        //Text 14
        common.verifyStringOnPage("ORCID är integrerat i många system som används av förläggare, " +
                "forskningsfinansiärer, universitet, högskolor, andra forskningsorganisationer och andra forskningsrelaterade " +
                "tjänster. ORCID är en oberoende ideell organisation som tillhandahåller en bestående identifierare, ett " +
                "ORCID iD, som unikt särskiljer dig från andra forskare och en mekanism för att koppla dina forskningsresultat " +
                "och aktiviteter till ditt ORCID iD.");
        common.verifyStringOnPage("Läs mer på orcid.org.");

        //Heading 15
        common.verifyStringOnPage("Hur går det till att länka ORCID till eduID?");

        //Text 15
        common.verifyStringOnPage("Klicka på knappen \"Länka ORCID-konto\", logga in hos ORCID och ge " +
                "eduID tillstånd att använda ditt ORCID iD. Vi gör detta för att se till att din ORCID iD är korrekt kopplat till dig.");

        //Heading 16
        common.verifyStringOnPage("Vad gör jag om jag inte har ORCID?");

        //Text 16
        common.verifyStringOnPage("Du kan skaffa ORCID hos orcid.org.");

        //Heading 17
        common.verifyStringOnPage("Hur tar bort ORCID från eduID?");

        //Text 17
        common.verifyStringOnPage("Om du inte längre vill att eduID ska känna till ditt ORCID iD kan du enkelt ta bort det genom att klicka på krysset.");

        //Heading 18
        common.verifyStringOnPage("Kontakt");

        //Text 18
        common.verifyStringOnPage("Om du inte kan hitta svar på dina frågor om eduID på vår hjälpsida " +
                "kan du kontakta eduID-supporten via e-post till support@eduid.se.");
        common.verifyStringOnPage("Ange alltid den e-postadress som du använde när du loggade in på eduID. " +
                "Om något har blivit fel är det alltid bra att skicka med skärmdumpar med felmeddelanden för att underlätta felsökning.");
        common.verifyStringOnPage("För bästa möjliga support rekommenderar vi dig alltid att skicka e-post " +
                "men för enklare ärenden kan du också nå oss per telefon 0455-385200.");
    }

    private void verifyEnglish(){
        //Select Swedish
        common.selectEnglish();

        //Heading 1
        common.verifyStringOnPage("What is eduID?");

        //Text 1
        common.verifyStringOnPage("eduID is a federated identity - a user identity that can be used in " +
                "several different organisations that have agreed on how identities will be managed. The basic idea is " +
                "that a given user, who is authenticated with an organisation, is automatically authenticated with other " +
                "organisations in the federation.");
        common.verifyStringOnPage("Federated identities are one of the cornerstones of trust between " +
                "organisations. Trust is based on all the organisations relying on all the others to carry out their " +
                "authentication - identification and verification - properly and in a controlled and reliable IT environment. " +
                "This is why eduID is certified by Kantara.");

        //Heading 2
        common.verifyStringOnPage("Why have eduID?");

        //Text 2
        common.verifyStringOnPage("From the student's perspective, in the long-term eduID means fewer " +
                "accounts to keep track of during studies. For many organisations, identity management is a complex issue " +
                "and it is necessary to work with confirmed users.");
        common.verifyStringOnPage("There are many services that require identification of users. This is " +
                "often done by the user entering an email address to which the service provider sends a password - and for " +
                "many services is this a sufficient level. Such a user is normally called unconfirmed, because the service " +
                "provider does not really know who the user with that email address is. Through the use of eduID, identification " +
                "of users is taken up a level to that of confirmed users.");

        //Heading 3
        common.verifyStringOnPage("How do I use eduID?");

        //Text 3
        common.verifyStringOnPage("Once you have created your account, you will hardly ever need to log " +
                "in to eduID. You will only be using your account to create and access other accounts, such as " +
                "universityadmissions.se or your student account.");
        common.verifyStringOnPage("Log in at eduid.se when you:\n" +
                "♦ Accept your place at university\n" +
                "♦ Organise your student account for email and intranet\n" +
                "♦ Change university\n" +
                "♦ Lose a password");

        //Heading 4
        common.verifyStringOnPage("What is the eduID privacy policy?");

        //Text 4
        common.verifyStringOnPage("Read more at www.sunet.se.");

        //Heading 5
        common.verifyStringOnPage("What is SUNET?");

        //Text 5
        common.verifyStringOnPage("SUNET is the organisation responsible for Swedish universities and " +
                "data networks, as well as many services that are used by universities in Sweden.");
        common.verifyStringOnPage("SUNET has been working with the issue of identity management for a " +
                "long time and developed eduID to make things easier for all parties in the higher education community. " +
                "More information about SUNET is available at www.sunet.se.");

        //Heading 6
        common.verifyStringOnPage("How do I change the default language in eduID?");

        //Text 6
        common.verifyStringOnPage("To change the default language you can log into eduID and select the " +
                "language you want under the personal information tab. The default language in eduID is based on the " +
                "language setting that your browser uses.");

        //Heading 7
        common.verifyStringOnPage("Which email account should I use to log in?");

        //Text 7
        common.verifyStringOnPage("You can log in with all the email addresses you have entered and confirmed in eduID.");

        //Heading 8
        common.verifyStringOnPage("What is Freja eID?");

        //Text 8
        common.verifyStringOnPage("Freja eID is a digital ID-card free of charge.");
        common.verifyStringOnPage("This is how you create Freja eID:\n" +
                "♦ Install the Freja eID app on your mobile device (iOS or Android)\n" +
                "♦ Create a Freja eID Plus account\n" +
                "♦ Bring a valid ID to the nearest ATG agent authorised to verify your identity");

        //Heading 9
        common.verifyStringOnPage("Why do I need to visit an authorised ATG agent to create Freja eID?");

        //Text 9
        common.verifyStringOnPage("On site, the agent can start the verification process by scanning a " +
                "QR code in your app and follow the instructions in their terminal. You will be informed when you have " +
                "passed the ID verification and will be able use your Freja eID with your eduID.");

        //Heading 10
        common.verifyStringOnPage("How do I find an ATG agent authorised for Freja eID?");

        //Text 10
        common.verifyStringOnPage("The nearest authorised ATG agents can be found in the app.");

        //Heading 11
        common.verifyStringOnPage("What should I do if my identity verification for Freja eID fails?");

        //Text 11
        common.verifyStringOnPage("Reinstall the app, redo the registration and make sure that you have " +
                "entered the correct expiration date as well as written down the correct reference number of the chosen " +
                "form of ID and personal identity number (personnummer).");

        //Heading 12
        common.verifyStringOnPage("How do I use my Freja eID with my eduID?");

        //Text 12
        common.verifyStringOnPage("Log in to eduID and click 'Use my Freja eID' button.");

        //Heading 13
        common.verifyStringOnPage("How long does it take for a Freja eID to be processed?");

        //Text 13
        common.verifyStringOnPage("It can take up to three hours for your Freja eID to be fully activated.");

        //Heading 14
        common.verifyStringOnPage("What is ORCID?");

        //Text 14
        common.verifyStringOnPage("ORCID is integrated into many research-related services, such as " +
                "systems used by publishers, funders and institutions. ORCID is an independent non-profit organisation " +
                "that provides a persistent identifier – an ORCID iD – that distinguishes you from other researchers and " +
                "a mechanism for linking your research outputs and activities to your ORCID iD.");
        common.verifyStringOnPage("Read more at orcid.org.");

        //Heading 15
        common.verifyStringOnPage("How does linking ORCID to eduID work?");

        //Text 15
        common.verifyStringOnPage("Click the \"Connect ORCID account\" button, sign in to your ORCID " +
                "account and grant eduID permission to recieve your ORCID iD. This process ensures that the correct " +
                "ORCID iD is connected to the correct eduID.");

        //Heading 16
        common.verifyStringOnPage("What do I do if I don't have an ORCID?");

        //Text 16
        common.verifyStringOnPage("You can register for an ORCID at orcid.org.");

        //Heading 17
        common.verifyStringOnPage("How do I remove a linked ORCID from eduID?");

        //Text 17
        common.verifyStringOnPage("If you do not longer want eduID to know your ORCID iD you can easily " +
                "remove it by clicking the remove button in your eduID.");

        //Heading 18
        common.verifyStringOnPage("Contact");

        //Text 18
        common.verifyStringOnPage("If you can't find the answers to your questions about eduID on our " +
                "help page, you can contact the eduID support by mailing support@eduid.se.");
        common.verifyStringOnPage("Always let us know the e-mail address you used when you logged into " +
                "eduID. If something went wrong, it is always a good idea to include screenshots with error messages to " +
                "ease troubleshooting.");
        common.verifyStringOnPage("In order to get best possible support, we recommend that you send " +
                "e-mail, but for simple matters you can also find us on phone number 0455-385200.");
    }
}
