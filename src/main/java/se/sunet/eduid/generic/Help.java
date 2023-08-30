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
        common.explicitWaitVisibilityElement("//section[2]/div/ul/li[1]/h4");
    }

    //Verify text and headings in swedish
    private void verifySwedish(){
        //Select Swedish
        if(common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul").getText().equalsIgnoreCase("svenska"))
            common.selectSwedish();

        //Heading 1
        common.verifyStringOnPage("FAQ");
        common.verifyStringOnPage("EDUID");
        common.verifyStringOnPage("Vad är eduID?");
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
        common.verifyStringOnPage("När du väl skapat ditt konto kommer du nästan aldrig behöva logga in " +
                "på eduID. Ditt konto använder du bara för att skapa och komma åt andra konton, exempelvis på antagning.se " +
                "eller dina studentkonton.");
        common.verifyStringOnPage("Logga in med eduID gör du när du:\n" +
                "Vill tacka ja till din plats på högskolan\n" +
                "Ordnar med studentkonto för mail och intranät\n" +
                "Byter lärosäte\n" +
                "Tappar ett lösenord");

        //Heading 6
        common.verifyStringOnPage("Hur byter jag standardspråk i eduID?");
        common.verifyStringOnPage("För att byta standardspråk kan du logga in på eduID och välja önskat " +
                "språk under fliken för personlig information. Det språk som kommer användas som standard i eduID är " +
                "baserat på den språkinställning som din webbläsare använder.");

        //Heading 7
        common.verifyStringOnPage("Vilket e-postkonto ska jag använda för att logga in?");
        common.verifyStringOnPage("Du kan logga in med alla e-postadresser som du angett och bekräftat i eduID.");

        //Identitet
        common.verifyStringOnPage("IDENTITET");
        common.verifyStringOnPage("Vilka är de utvalda sätten att bekräfta ett personnummer för eduID?");
        common.verifyStringOnPage("Just nu kan man bekräfta sitt personnummer via:");
        common.verifyStringOnPage("Post: Användaren får ett brev med en kod skickat till den " +
                "folkbokföringsadress som är registrerad hos Skatteverket");
        common.verifyStringOnPage("Mobil: Användaren får ett sms med en kod skickat till det telefonnummer " +
                "som är registrerat i mobiloperatörernas egna register");
        common.verifyStringOnPage("Freja eID+ (digitalt ID-kort): Användaren blir hänvisad till Freja " +
                "eIDs hemsida för att använda sig av deras tjänst. Om du inte redan har Freja eID+ kräver denna metod " +
                "skapande av ett separat Freja eID+ innan du kan bekräfta ditt eduID. Läs mer om Freja eID+.");
        common.verifyStringOnPage("Om du är EU-medborgare utan ett svenskt personnummer kan du istället " +
                "använda dig av eIDAS för att bekräfta din identitet. Läs mer om eIDAS.");
        common.verifyStringOnPage("Om du inte är EU-medborgare samt utan ett svenskt personnummer kan " +
                "du istället använda dig av Svipe iD för att bekräfta din identitet. Läs mer om Svipe iD.");


        //Heading 8
        common.verifyStringOnPage("FREJA EID+");
        common.verifyStringOnPage("Vad är Freja eID+?");
        common.verifyStringOnPage("Så här skaffar du Freja eID+:" +
                "\n" +
                "Installera Freja eID appen på din mobila enhet (iOS eller Android)\n" +
                "Skapa ett Freja eID+ konto\n" +
                "Ta med giltig legitimation till närmsta ATG ombud som kan påbörja en process som godkänner din identitet");

        //Heading 9
        common.verifyStringOnPage("Varför måste jag besöka ett ATG ombud för att skapa Freja eID+?");
        common.verifyStringOnPage("ATG ombudet kan påbörja en kontroll av din legitimation genom att " +
                "scanna den QR-kod som Freja eID appen genererat och följa instruktionerna på sin skärm. Du kommer bli " +
                "informerad när legitimationskontollen är slutförd och ditt Freja eID+ är klart att användas.");

        //Heading 10
        common.verifyStringOnPage("Hur hittar jag ett ATG ombud som kan göra en legitimationskontroll för Freja eID+?");
        common.verifyStringOnPage("Ditt närmsta ATG ombud kan hittas i appen.");

        //Heading 11
        common.verifyStringOnPage("Vad ska jag göra om legitimationskontrollen för Freja eID+ misslyckades?");
        common.verifyStringOnPage("Avinstallera appen, gör om registreringen, samt kontrollera noga att " +
                "du angivit rätt datum då ID-handlingen upphör att gälla samt att du fyllt i rätt referensnummer och personnummer.");

        //Heading 12
        common.verifyStringOnPage("Hur använder jag Freja eID+ med eduID?");
        common.verifyStringOnPage("Logga in på eduID och klicka på 'Använd mitt Freja eID+'.");

        //Heading 13
        common.verifyStringOnPage("Hur lång tid tar legitimationskontrollen för Freja eID+?");
        common.verifyStringOnPage("Det kan ta upp till 3 timmar att för din legetimation att bli godkänd.");


        common.verifyStringOnPage("EIDAS");
        common.verifyStringOnPage("Vad är eIDAS?");
        common.verifyStringOnPage("eIDAS är en federation av anslutna EU-länder som utfärdar en elektronisk " +
                "identifiering för att erbjuda inloggning till alla offentliga verksamheters system för medborgare inom EU, " +
                "med hjälp av sitt lands e-legitimation.");
        common.verifyStringOnPage("Hur används eIDAS:");
        common.verifyStringOnPage("Om du har ett elektroniskt ID från ett anslutet land har du möjlighet " +
                "att authenticera dig via eIDAS.");
        common.verifyStringOnPage("För att bekräfta din identitet i eduID, logga in och välj identifieringsmetod " +
                "för 'EU-medborgare' under fliken 'Identitet'.");
        common.verifyStringOnPage("Om du har ett svenskt personnummer använder du den metoden istället, " +
                "bl.a. för att underlätta kommunikationen med svenska institutioner.");
        common.verifyStringOnPage("Om du verifierar dig initialt med eIDAS och senare får svenskt personnummer " +
                "kan du lägga till det och verifiera dig igen med hjälp av det under fliken 'Identitet'.");
        common.verifyStringOnPage("");
        common.verifyStringOnPage("");

        //SVIPE
        common.verifyStringOnPage("SVIPE");
        common.verifyStringOnPage("Om Svipe");
        common.verifyStringOnPage("Svipe ID är en identitetsverifiering på distans med hjälp av ett " +
                "pass eller ett ID-kort.");
        common.verifyStringOnPage("För att bekräfta ditt eduID-konto med ett pass eller ID-kort behöver " +
                "använda appen Svipe, ett konto i appen Svipe, och du behöver ha laddat in ditt ID-dokument i appen.");
        common.verifyStringOnPage("Informationen som Svipe sparar om dig kan läsas i deras " +
                "dataskyddspolicy, men i korthet kan sägas att appen läser in informationen från ditt pass eller " +
                "ID-dokument, men informationen sparas bara lokalt i din enhet och företaget Svipe har inte tillgång " +
                "till informationen som finns lagrad på din enhet.");
        common.verifyStringOnPage("Att bekräfta din identitet med hjälp av Svipe ID");
        common.verifyStringOnPage("Du skaffar appen Svipe genom App Store för iOS eller Google Play " +
                "för Android. För att skaffa ett konto öppnar du appen och följer guiden.");
        common.verifyStringOnPage("När du har ett konto och har läst in ditt ID-dokument kan du " +
                "använda Svipe-appen för att bekräfta ditt konto i eduID genom att logga in på ditt eduID-konto och " +
                "välja att bekräfta ditt konto med Svipe-appen.");


        //Heading 5
        common.verifyStringOnPage("SUNET");
        common.verifyStringOnPage("Vad är SUNET?");
        common.verifyStringOnPage("SUNET är den organisation som är ansvarig för det svenska " +
                "universitets- och datanätverket och även flertal tjänster som används av universitet och högskolor i Sverige.");
        common.verifyStringOnPage("SUNET arbetar sedan länge med identitetshantering, och för att " +
                "underlätta för alla parter i högskolevärlden har SUNET tagit fram eduID. Mer information om SUNET finns på www.sunet.se.");


        //Heading 14
        common.verifyStringOnPage("ORCID");
        common.verifyStringOnPage("Vad är ORCID?");
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
        common.verifyStringOnPage("Du kan skaffa ORCID hos orcid.org.");

        //Heading 17
        common.verifyStringOnPage("Hur tar bort ORCID från eduID?");
        common.verifyStringOnPage("Om du inte längre vill att eduID ska känna till ditt ORCID iD kan du enkelt ta bort det genom att klicka på krysset.");

        //Heading 4
        common.verifyStringOnPage("PERSONUPPGIFTER");
        common.verifyStringOnPage("Hur hanterar eduID mina personuppgifter?");
        common.verifyStringOnPage("Läs mer på www.sunet.se.");

        //Heading 18
        common.verifyStringOnPage("KONTAKT");
        common.verifyStringOnPage("eduID support");
        common.verifyStringOnPage("Om du inte kan hitta svar på dina frågor om eduID på vår hjälpsida " +
                "kan du kontakta eduID-supporten via e-post till support@eduid.se.");
        common.verifyStringOnPage("Ange alltid den e-postadress som du använde när du loggade in på eduID. " +
                "Om något har blivit fel är det alltid bra att skicka med skärmdumpar med felmeddelanden för att underlätta felsökning.");
        common.verifyStringOnPage("För bästa möjliga support rekommenderar vi dig alltid att skicka e-post, " +
                "men för enklare ärenden kan du också nå oss per telefon 0455-385200.");
        common.verifyStringOnPage("Öppettider:");
    }

    private void verifyEnglish(){
        //Select Swedish
        common.selectEnglish();

        //Click on Help link
        common.click(common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a"));

        //Heading 1
        common.verifyStringOnPage("FAQ");
        common.verifyStringOnPage("EDUID");
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
        common.verifyStringOnPage("Once you have created your account, you will hardly ever need to log " +
                "in to eduID. You will only be using your account to create and access other accounts, such as " +
                "universityadmissions.se or your student account.");
        common.verifyStringOnPage("Log in at eduid.se when you:\n" +
                "Accept your place at university\n" +
                "Organise your student account for email and intranet\n" +
                "Change university\n" +
                "Lose a password");

        //Heading 6
        common.verifyStringOnPage("How do I change the default language in eduID?");
        common.verifyStringOnPage("To change the default language you can log into eduID and select the " +
                "language you want under the personal information tab. The default language in eduID is based on the " +
                "language setting that your browser uses.");
        common.verifyStringOnPage("Which email account should I use to log in?");
        common.verifyStringOnPage("You can log in with all the email addresses you have entered and confirmed in eduID.");


        //Identitet
        common.verifyStringOnPage("IDENTITY");
        common.verifyStringOnPage("What are the selected methods of verifying an id number for eduID?");
        common.verifyStringOnPage("At this moment, verifying an id number can be done via:");
        common.verifyStringOnPage("Post: The user receives a letter with a code sent to their home address " +
                "as registered at Skatteverket (the Swedish Tax Agency)");
        common.verifyStringOnPage("Mobile: The user receives a message sent to the phone number that is " +
                "registered in the Swedish telephone register");
        common.verifyStringOnPage("Freja eID+ (digital ID-card): The user will be directed to the " +
                "Freja eID website to use their service. If you don't have Freja eID+ you have to create it separately " +
                "before you can complete verification of your eduID. Read more about Freja eID+.");
        common.verifyStringOnPage("If you are an EU citizen without a Swedish personal identity number " +
                "you could use eIDAS to verify your identity. Read more about eIDAS.");
        common.verifyStringOnPage("If you are not an EU citizen and without a Swedish personal " +
                "identity number you could use Svipe iD to verify your identity. Read more about Svipe iD.");


        //Heading 8
        common.verifyStringOnPage("FREJA EID+");
        common.verifyStringOnPage("What is Freja eID+?");

        //Text 8
        common.verifyStringOnPage("Freja eID+ is a digital ID-card free of charge.");
        common.verifyStringOnPage("This is how you create Freja eID+:" +
                "\n" +
                "Install the Freja eID app on your mobile device (iOS or Android)\n" +
                "Create a Freja eID+ account\n" +
                "Bring a valid ID to the nearest ATG agent authorised to verify your identity");

        //Heading 9
        common.verifyStringOnPage("Why do I need to visit an authorised ATG agent to create Freja eID+?");

        //Text 9
        common.verifyStringOnPage("On site, the agent can start the verification process by scanning a " +
                "QR code in your app and follow the instructions in their terminal. You will be informed when you have " +
                "passed the ID verification and will be able use your Freja eID+ with your eduID.");

        //Heading 10
        common.verifyStringOnPage("How do I find an ATG agent authorised for Freja eID+?");

        //Text 10
        common.verifyStringOnPage("The nearest authorised ATG agents can be found in the app.");

        //Heading 11
        common.verifyStringOnPage("What should I do if my identity verification for Freja eID+ fails?");

        //Text 11
        common.verifyStringOnPage("Reinstall the app, redo the registration and make sure that you have " +
                "entered the correct expiration date as well as written down the correct reference number of the chosen " +
                "form of ID and personal identity number (personnummer).");

        //Heading 12
        common.verifyStringOnPage("How do I use my Freja eID+ with my eduID?");

        //Text 12
        common.verifyStringOnPage("Log in to eduID and click 'Use my Freja eID+' button.");

        //Heading 13
        common.verifyStringOnPage("How long does it take for a Freja eID+ to be processed?");

        //Text 13
        common.verifyStringOnPage("It can take up to three hours for your Freja eID+ to be fully activated.");


        common.verifyStringOnPage("EIDAS");
        common.verifyStringOnPage("What is eIDAS?");
        common.verifyStringOnPage("eIDAS is a federation of EU countries providing electronic identification " +
                "to allow access to public authority systems for EU citizens, using their country's electronic ID.");
        common.verifyStringOnPage("How to use eIDAS:");
        common.verifyStringOnPage("If you have an electronic ID from a connected country you have the " +
                "possibility to authenticate yourself via eIDAS.");
        common.verifyStringOnPage("To verify your identity in eduID, log in and choose the verification " +
                "method for 'EU-citizens' under the 'Identity' tab.");
        common.verifyStringOnPage("If you have a Swedish personal identity number use that method instead " +
                "e.g. to simplify communication with Swedish authorities.");
        common.verifyStringOnPage("If you initially verify your identity with eIDAS and later receive a " +
                "Swedish personal identity number you can add it and verify yourself again using it under the 'Identity' tab.");

        //SVIPE
        common.verifyStringOnPage("SVIPE");
        common.verifyStringOnPage("About Svipe ID");
        common.verifyStringOnPage("Svipe ID is an identity verification you can use remotely using " +
                "your passport or ID-card.");
        common.verifyStringOnPage("To verify your eduID-account using a passport or other ID-card " +
                "you will use the app Svipe, you also require an account with Svipe, and you will have had to load " +
                "the information from your ID-document into the Svipe-app.");
        common.verifyStringOnPage("You can stay informed about the information that Svipe saves about " +
                "you and your ID-document by reading their data privacy policy. In short, the information uploaded to " +
                "the app from your ID-document is saved locally on your device, and the company Svipe does not have access to it.");
        common.verifyStringOnPage("Verifying your identity using Svipe ID");
        common.verifyStringOnPage("You get the Svipe-app through App store for IOS, or Google Play for " +
                "Android. To create an account with Svipe you open the app and follow the step-by-step guide.");
        common.verifyStringOnPage("Once you have an account and you've uploaded your ID-document you " +
                "are able to use the Svipe-app to verify your eduID-account by visiting eduid.se - signing in - and " +
                "choosing to verify using the Svipe-app.");


        //Heading 5
        common.verifyStringOnPage("SUNET");
        common.verifyStringOnPage("What is SUNET?");

        //Text 5
        common.verifyStringOnPage("SUNET is the organisation responsible for Swedish universities and " +
                "data networks, as well as many services that are used by universities in Sweden.");
        common.verifyStringOnPage("SUNET has been working with the issue of identity management for a " +
                "long time and developed eduID to make things easier for all parties in the higher education community. " +
                "More information about SUNET is available at www.sunet.se.");

        //Heading 14
        common.verifyStringOnPage("ORCID");
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

        //Heading 4
        common.verifyStringOnPage("PRIVACY POLICY");
        common.verifyStringOnPage("What is the eduID privacy policy?");

        //Text 4
        common.verifyStringOnPage("Read more at www.sunet.se.");

        //Heading 18
        common.verifyStringOnPage("CONTACT");

        //Text 18
        common.verifyStringOnPage("If you can't find the answers to your questions about eduID on our " +
                "help page, you can contact the eduID support by mailing support@eduid.se.");
        common.verifyStringOnPage("Always let us know the e-mail address you used when you logged into " +
                "eduID. If something went wrong, it is always a good idea to include screenshots with error messages to " +
                "ease troubleshooting.");
        common.verifyStringOnPage("In order to get best possible support, we recommend that you send " +
                "e-mail, but for simple matters you can also reach us on phone number 0455-385200.");
        common.verifyStringOnPage("Opening hours:");
    }
}
