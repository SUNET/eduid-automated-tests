package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;

public class Help {
    private final Common common;

    public Help(Common common){
        this.common = common;
    }

    public void runHelp(){
        clickHelp();

        expandAllOptions();
        verifySwedish();

        //Select English
        common.selectEnglish();

        expandAllOptions();
        verifyEnglish();
    }

    //Click on help button
    private void clickHelp(){
        //Click on Help link
        common.click(common.findWebElementByXpath("//*[@id=\"footer\"]/nav/ul/li[1]/a"));

        //Wait for header "Help and Contact info"
        common.explicitWaitVisibilityElement("//*[@id=\"content\"]/section/h1");
    }

    //Verify text and headings in swedish
    public void verifySwedish(){
        Common.log.info("Verify help pages in swedish - start");

        //Heading
        common.verifyStringOnPage("Hjälpavsnitt och kontaktuppgifter");

        //Heading - text
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");

        aboutEduIdSwe();
        useEduIdSwe();
        securityEduIdSwe();
        verificationEduIdSwe();
        orcidEduIdSwe();
        privacyEduIdSwe();
        aboutSunetSwe();
        contactEduIdSwe();

        Common.log.info("Verify help pages in swedish - done");
    }


    public void verifyEnglish(){
        Common.log.info("Verify help pages in english - start");

        //Heading
        common.verifyStringOnPage("Help and contact");

        //Heading - text
        common.verifyStringOnPage("Listed below is general information about the service, answers to " +
                "common questions about using eduID and Support contact information.");

        aboutEduIdEng();
        useEduIdEng();
        securityEduIdEng();
        verificationEduIdEng();
        orcidEduIdEng();
        privacyEduIdEng();
        aboutSunetEng();
        contactEduIdEng();

        Common.log.info("Verify help pages in english - done");
    }

    private void aboutEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Om eduID");

        //Heading - text
        common.verifyStringOnPage("Beskrivning och syfte");

        //Heading
        common.verifyStringOnPage("Vad är eduID?");

        //Text
        common.verifyStringOnPage("eduID är en federerad identitet – en användaridentitet som kan " +
                "användas inom flera olika organisationer eftersom man har enats kring hur identiteter ska hanteras. " +
                "Grundidén är att en given användare som autentiserat sig hos en organisation per automatik kan bli " +
                "autentiserad hos en annan som ingår i federationen.");

        //Text
        common.verifyStringOnPage("För federerade identiteter är en av grundstenarna tillit mellan " +
                "organisationer. Tilliten ligger i att organisationerna litar på att alla genomför sin autentisering, " +
                "identifiering och verifiering, på ett korrekt sätt och i en kontrollerad och tillförlitlig IT-miljö.");

        //Heading
        common.verifyStringOnPage("Varför finns eduID?");

        //Text
        common.verifyStringOnPage("För användaren leder eduID på sikt till ett färre antal konton att " +
                "hålla reda på under t.ex. studietiden. För många organisationer är identitetshantering en komplex " +
                "fråga och det finns ett behov av att arbeta med bekräftade användare.");

        //Text
        common.verifyStringOnPage("Det är många tjänster som har behov av identifiering av användare. " +
                "Ofta sker detta genom att användaren anger en e-postadress dit tjänsten skickar ett lösenord och för " +
                "många tjänster är detta en tillräcklig nivå. En sådan användare kallas ofta obekräftad, eftersom " +
                "tjänsten egentligen inte vet vem användaren med e-postadressen är. Genom eduID kan identifieringen av " +
                "användare lyftas ett steg, till att bli bekräftade användare.");

        //Heading
        common.verifyStringOnPage("När använder jag eduID?");

        //Text
        common.verifyStringOnPage("Hur ofta du kommer att använda ditt eduID är beroende av ditt arbete " +
                "eller dina studier; vissa skolor, institutioner och tjänster använder eduID som sin tjänsteleverantör, " +
                "vilket betyder att du behöver använda ditt eduID för att ha tillgång till deras IT-system. Eller så " +
                "använder du bara ditt eduID konto för att skapa åtkomst till andra konton, t.ex. universityadmissions.se " +
                "eller ditt studentkonto.");

        //Text
        common.verifyStringOnPage("Logga in med eduID när du:");

        //Text
        common.verifyStringOnPage("ansöker till och accepterar plats på högskolan,\n" +
                "organiserar studentkontos e-post och intranät,\n" +
                "byter lärosäte,\n" +
                "förlorar ett lösenord och behöver återfå åtkomst till konto.");
    }

    private void useEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Användning av eduID");

        //Heading - text
        common.verifyStringOnPage("Skapa, använda och utöka ditt eduID");

        //Heading
        common.verifyStringOnPage("Hur kan jag skapa ett eduID?");

        //Text
        common.verifyStringOnPage("Hur du kan skapa ditt eduID-konto på eduid.se:");

        //Text
        common.verifyStringOnPage("ange din e-postadress,\n" +
                "bekräfta att du inte är en robot m.h.a. CAPTCHA,\n" +
                "acceptera användarvillkoren,\n" +
                "bekräfta din e-postadress m.h.a. koden som skickats till den,\n" +
                "notera e-postadressen och lösenordet när dina inloggningsuppgifter visas för dig. Ditt eduID är redo att användas.");

        //Heading
        common.verifyStringOnPage("Hur kan jag utöka mitt eduID?");

        //Text
        common.verifyStringOnPage("I eduID har du möjlighet att lägga till information som t.ex:");

        //Text
        common.verifyStringOnPage("ditt namn för att kunna lägga till en säkerhetsnyckel eller hantera vissa tjänster från ett icke verifierat konto,\n" +
                "ditt telefonnummer för att lättare kunna återställa ditt konto om det skulle behövas,\n" +
                "en säkerhetsnyckel om du har möjlighet för ytterligare säkerhet,\n" +
                "ansluta ditt eduID till ESI och/eller befintligt ORCID iD om det stöds av din institution,\n" +
                "verifiera din identitet för att förstärka ditt eduID tillräckligt för många externa tjänsters behov.");

        //Text
        common.verifyStringOnPage("För mer detaljerad information om hur du bäst kan verifiera ditt " +
                "skapade eduID-konto, se hjälpavsnittet 'Verifiering av identitet'.");

        //Heading
        common.verifyStringOnPage("Vilken e-postadress kan jag använda för att logga in?");

        //Text
        common.verifyStringOnPage("Du kan logga in med alla e-postadresser som du angett och bekräftat i eduID.");

        //Heading
        common.verifyStringOnPage("Hur kan jag ändra standardspråk i eduID?");

        //Text
        common.verifyStringOnPage("Det språk som används som standard i eduID är baserat på den " +
                "språkinställning som din webbläsare använder. För att byta standardspråk kan du logga in i eduID och " +
                "välja önskat språk under Personlig information. Du kan också byta det visade språket i hemsidans sidfot. " +
                "Möjliga val är svenska och engelska.");

        //Heading
        common.verifyStringOnPage("Hur kan jag logga in med en annan enhet?");

        //Text
        common.verifyStringOnPage("Inloggning kan även ske genom att logga in i eduID med hjälp av en annan enhet:");

        //Text
        common.verifyStringOnPage("klicka på knappen 'Annan enhet' i inloggnings-formuläret,\n" +
                "skanna QR-koden med enheten där du har din säkerhetsnyckel eller sparat lösenord,\n" +
                "på den andra enheten; kontrollera informationen om enheten som försöker logga in och använd koden " +
                "som visas, inom den angivna tiden, för att logga in på den första enheten.");
    }

    private void securityEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Utökad säkerhet med ditt eduID");

        //Heading - text
        common.verifyStringOnPage("Lägga till MFA/2FA Säkerhetsnyckel");

        //Heading
        common.verifyStringOnPage("Hur kan jag göra mitt eduID säkrare?");

        //Text
        common.verifyStringOnPage("Vissa tjänster kräver att kontot du använder för att logga in har " +
                "en högre tillitsnivå. När du skapar ett konto i eduID behöver du kännedom om ditt användarnamn " +
                "(bekräftad epost-adress) och tillhörande lösenord. Lösenordet räknas som den första " +
                "autentiserings-faktorn. För ett ytterligare lager av autentisering för att kunna logga in kan du " +
                "lägga till en säkerhetsnyckel. Säkerhetsnyckeln kallas tvåfaktorsautentisering (2FA) eller i vissa " +
                "fall multifaktorautentisering (MFA).");

        //Text
        common.verifyStringOnPage("Ett exempel på en säkerhetsnyckel är en fysisk ändamålsenlig " +
                "USB-nyckel som kräver att du är närvarande vid enheten. Du har också möjlighet att lägga till " +
                "biometrisk information som fingeravtryck eller ansiktsigenkänning som stöds av enheten du loggar " +
                "in med, för att kunna låsa upp säkerhetsnyckeln vid behov.");

        //Heading
        common.verifyStringOnPage("Hur kan jag lägga till 2FA för mitt eduID?");

        //Text
        common.verifyStringOnPage("När du har loggat in kan du lägga till och bekräfta säkerhetsnycklar " +
                "bland inställningarna i eduID genom att följa instruktionerna där.");

        //Text
        common.verifyStringOnPage("Obs: om du har lagt till en säkerhetsnyckel till ditt eduID behöver " +
                "du sedan använda den för att kunna logga in och du behöver ha minst en.");

        //Heading
        common.verifyStringOnPage("Vilka säkerhetsnycklar kan jag använda för eduID?");

        //Text
        common.verifyStringOnPage("Vi följer en standard utöver vår egen policy för vilka " +
                "säkerhetsnycklar som är möjliga att användas för tjänsten. Mer information om de tekniska " +
                "förutsättningarna samt en uppdaterad lista över nycklar som möter dem finns nedan.");

        //Heading
        common.verifyStringOnPage("Om Säkerhetsnycklar");
        common.verifyStringOnPage("Att välja säkerhetsnyckel");
        common.verifyStringOnPage("Inte alla säkerhetsnycklar uppnår kraven för att kunna användas som säkerhetsnyckel för eduID.");
        common.verifyStringOnPage("Kontrollera med tillverkaren eller återförsäljaren om produkten möter dessa krav:");
        common.verifyStringOnPage("Certifierad FIDO 2.0, läs mer på fidoalliance.org.");
        common.verifyStringOnPage("Släpper ett intyg utfärdat av tillverkaren som berättar vilken enhet " +
                "det är i samband med inloggningen och kräver att personen är fysiskt vid säkerhetsnyckeln för att den ska kunna användas. ");
        common.verifyStringOnPage("YTTERLIGARE TEKNISK INFORMATION:");
        common.verifyStringOnPage("Säkerhetsnyckeln måste kunna genomföra en attestation och finnas i metadatat,\n" +
                "får inte ha någon annan status i metadata än några olika varianter av:  \"fido certified\",\n" +
                "måste stödja någon av följande \"user verification methods\":  \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\n" +
                "samt inte stödja någon annan \"key protection\" än:  \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");

        //Heading
        common.verifyStringOnPage("Giltiga modeller av fysiska säkerhetsnycklar");
        common.verifyStringOnPage("Här listas märke och modellnamn av fysiska säkerhetsnycklar som bör " +
                "möta de tekniska förutsättningarna för att kunna användas för eduID. Listan är sorterad alfabetiskt " +
                "och uppdateras en gång i månaden.");
        common.verifyStringOnPage("Nästa uppdatering:");
    }

    private void verificationEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Verifiering av identitet");

        //Heading - text
        common.verifyStringOnPage("Tillitsnivåer och verifieringsmetoder för olika användargrupper");

        //Heading
        common.verifyStringOnPage("Vad är tillitsnivåer?");

        //Text
        common.verifyStringOnPage("Tjänsteleverantörer behöver förlita sig på att organisationer " +
                "autentiserar sina användare enligt vissa tillitsnivåer (t.ex. AL1-3), beroende på vilken slags " +
                "information som tillhandahålls. Nivåerna varierar från obekräftade, till bekräftade till verifierade " +
                "användare som även använder MFA vid inloggning till systemet.");

        //Heading
        common.verifyStringOnPage("Vilka verifieringsmetoder finns för eduID?");

        //Text
        common.verifyStringOnPage("Tjänsten utvecklas löpande för att bäst kunna möta våra användares " +
                "olika behov. För närvarande stöds verifieringsmetoderna nedan, beroende av din situation som t.ex. " +
                "efterfrågad tillitsnivå, nationalitet och boplats.");

        //Text
        common.verifyStringOnPage("Om du har svenskt personnummer eller samordningsnummer, kan det verifieras med:");

        //Text
        common.verifyStringOnPage("post - med svenskt personnummer: användaren får ett brev med en kod " +
                "skickat till den folkbokföringsadress som är registrerad hos Skatteverket och instruktioner om att " +
                "slutföra verifieringen i eduid.se,");

        //Text
        common.verifyStringOnPage("mobil - med svenskt personnummer: användaren får ett sms skickat till " +
                "det telefonnummer som är registrerat i mobiloperatörernas egna register, med instruktioner om att " +
                "slutföra verifieringen i eduid.se,");

        //Text
        common.verifyStringOnPage("Freja+ (digitalt ID-kort) - med svenskt personnummer eller " +
                "samordningsnummer: användaren blir hänvisad till Freja eIDs hemsida för att använda sig av deras tjänst. " +
                "Om du inte redan har Freja+ behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om Freja+ nedan.");

        //Text
        common.verifyStringOnPage("BankID (elektroniskt identifieringssystem) - med svenskt " +
                "personnummer: användaren blir ombedd att identifiera sig med hjälp av sitt BankID i BankID-tjänsten. " +
                "Om du inte redan har ett BankID behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om BankID nedan.");

        //Text
        common.verifyStringOnPage("Om du är EU-medborgare och inte har ett svenskt personnummer, kan du " +
                "använda eIDAS med hjälp av ett europeiskt eID för att verifiera din identitet. Läs mer om eIDAS nedan.");

        //Text
        common.verifyStringOnPage("Om du inte är EU-medborgare och inte har ett svenskt personnummer, " +
                "kan du använda Svipe ID med hjälp av ditt pass för att verifiera din identitet. Läs mer om Svipe iD nedan.");

        //Text
        common.verifyStringOnPage("Obs: pga ändrade förhållanden samt regler kring personuppgiftshantering " +
                "finns detta alternativ inte tillgängligt för närvarande men utredning av lösningar är pågående.");

        //Heading
        common.verifyStringOnPage("Om Freja+");

        //Heading
        common.verifyStringOnPage("Vad är Freja+?");

        //Text
        common.verifyStringOnPage("Freja+ (ett verifierat Freja eID) är ett kostnadsfritt digitalt " +
                "ID-kort tillgängligt för personer med svenskt personnummer eller samordningsnummer.");

        //Text
        common.verifyStringOnPage("Hur du kan använda Freja+ med eduID:");

        //Text
        common.verifyStringOnPage("installera Freja app på din mobila enhet (iOS eller Android) och " +
                "skapa ett Freja+ konto enligt instruktionerna,");

        //Text
        common.verifyStringOnPage("om du har ett giltigt svenskt pass kan du verifiera ditt konto " +
                "direkt i appen med hjälp av mobilens kamera, eller ta med giltig ID-handling (inklusive körkort eller " +
                "ID-kort) till ett auktoriserat ATG-ombud för att verifiera din identitet,");

        //Text
        common.verifyStringOnPage("logga in i eduID och välj metoden 'Med Freja+ digitalt id-kort' i " +
                "identitetshanteringen och följ instruktionerna.");

        //Heading
        common.verifyStringOnPage("Behöver jag besöka ett auktoriserat ATG ombud för att skapa Freja+?");

        //Text
        common.verifyStringOnPage("Bara om du använder en annan ID-handling än svenskt pass. ATG " +
                "ombudet kan påbörja en kontroll av din legitimation genom att scanna den QR-kod som Freja-appen " +
                "genererat och följa instruktionerna på sin skärm. Du kommer bli informerad när legitimationskontrollen " +
                "är slutförd och ditt Freja+ är klart att användas, det kan ta upp till 3 timmar.");

        //Heading
        common.verifyStringOnPage("Vad kan jag göra om legitimationskontrollen för Freja+ misslyckades?");

        //Text
        common.verifyStringOnPage("Avinstallera appen, gör om registreringen och kontrollera noga att " +
                "du angivit rätt datum då ID-handlingen upphör att gälla samt att du fyllt i rätt referensnummer och " +
                "personnummer eller samordningsnummer.");

        //Heading
        common.verifyStringOnPage("Om BankID");

        //Heading
        common.verifyStringOnPage("Vad är BankID?");

        //Text
        common.verifyStringOnPage("BankID är en e-legitimation som är tillgänglig för innehavare av " +
                "ett svenskt personnummer, svensk godkänd ID-handling (inkl. pass, körkort och ID-kort) och kund hos " +
                "en av de anslutna bankerna.");

        //Text
        common.verifyStringOnPage("Hur du kan använda BankID med eduID:");

        //Text
        common.verifyStringOnPage("BankID erhålls från din personliga bank och installeras på din " +
                "enhet som en app eller fil. Tillvägagångssättet varierar, så besök din banks hemsida och följ " +
                "instruktionerna. Du kan läsa mer om att skaffa BankID på the BankID website");

        //Text
        common.verifyStringOnPage("logga in i eduID och välj metoden 'Med elektroniskt BankID' i " +
                "identitetshanteringen och följ instruktionerna.");

        //Heading
        common.verifyStringOnPage("Om eIDAS");

        //Heading
        common.verifyStringOnPage("Vad är eIDAS?");

        //Text
        common.verifyStringOnPage("eIDAS är en federation av anslutna EU-länder som utfärdar en " +
                "elektronisk identifiering för att erbjuda inloggning till alla offentliga verksamheters system för " +
                "medborgare inom EU, med hjälp av sitt lands e-legitimation.");

        //Text
        common.verifyStringOnPage("Hur du kan använda eIDAS med eduID:");

        //Text
        common.verifyStringOnPage("kontrollera att du har en elektronisk legitimation från ett anslutet " +
                "land för att kunna autentisera dig med hjälp av eIDAS,");

        //Text
        common.verifyStringOnPage("logga in i eduID, navigera till Identitet och välj verifieringsmetoden " +
                "för 'EU-medborgare' och följ instruktionerna.");

        //Text
        common.verifyStringOnPage("Om du har ett svenskt personnummer, använd istället någon av metoderna " +
                "som stöds därav för att underlätta kommunikationen med svenska myndigheter. Obs: om du initialt har " +
                "verifierat dig med eIDAS och senare får svenskt personnummer kan du lägga till det och verifiera dig " +
                "igen med hjälp av det i identitetshanteringen i eduID.");

        //Heading
        common.verifyStringOnPage("Om Svipe iD");

        //Heading
        common.verifyStringOnPage("Vad är Svipe iD?");

        //Text
        common.verifyStringOnPage("Svipe iD är en identitetsverifierings-plattform som med hjälp av " +
                "Svipe-appen, ett biometriskt pass eller ett ID-kort från 140 olika länder, tillsammans med användarens " +
                "mobils ansiktsigenkännings-teknik kan verifiera en digital identitet på distans.");

        //Text
        common.verifyStringOnPage("Informationen som Svipe sparar om dig kan läsas i deras dataskyddspolicy, " +
                "men i korthet kan sägas att appen läser in information från ditt pass eller ID-dokument, som sparas " +
                "lokalt i din enhet, så företaget Svipe har inte tillgång till informationen som finns lagrad på din enhet.");

        //Text
        common.verifyStringOnPage("Hur du kan använda Svipe iD med eduID:");

        //Text
        common.verifyStringOnPage("för att bekräfta ditt eduID med Svipe behöver du först ett Svipe-konto " +
                "med en profil som är bekräftad med ditt pass eller ID-kort i appen Svipe, tillgänglig i App store for " +
                "IOS och Google Play för Android,");

        //Text
        common.verifyStringOnPage("logga in i eduID, navigera till Identitet och välj verifieringsmetoden " +
                "för 'Alla andra länder' och scanna QR-koden som genereras av Svipe iD genom att följa instruktionerna.");

        //Text
        common.verifyStringOnPage("Innehavare av svenskt personnummer uppmanas att istället använda sig " +
                "av metoderna som stöds därav.");

        //Text
        common.verifyStringOnPage("Obs: pga ändrade förhållanden samt regler kring personuppgiftshantering " +
                "finns detta alternativ inte tillgängligt för närvarande men utredning av lösningar är pågående.");

    }

    private void orcidEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Länkning till Orcid / ESI");

        //Heading
        common.verifyStringOnPage("Vad är ORCID?");

        //Text
        common.verifyStringOnPage("ORCID är integrerat i många system som används av förläggare, " +
                "universitet, högskolor och andra forskningsrelaterade tjänster och organisationer. ORCID är en " +
                "oberoende ideell organisation som tillhandahåller en bestående identifierare, ett ORCID iD, som unikt " +
                "särskiljer dig från andra forskare och en mekanism för att koppla dina forskningsresultat och " +
                "aktiviteter till ditt ORCID iD.");

        //Text
        common.verifyStringOnPage("Hur du kan länka ORCID med eduID:");

        //Text
        common.verifyStringOnPage("läs mer och skaffa ett ORCID på orcid.org,\n" +
                "klicka på knappen 'Länka Orcid konto' bland Inställningar i eduID,\n" +
                "logga in i ORCID och ge eduID tillstånd att använda ditt ORCID iD för att försäkra att det " +
                "är korrekt kopplat till dig.");

        //Heading
        common.verifyStringOnPage("Hur kan jag ta bort ett länkat ORCID från eduID?");

        //Text
        common.verifyStringOnPage("Om du inte längre vill att eduID ska känna till ditt ORCID iD kan du " +
                "enkelt ta bort det genom att klicka på krysset.");

        //ESI
        common.verifyStringOnPage("Vad är Ladok och ESI?");
        common.verifyStringOnPage("Ladok är ett system för studieadministration vid svenska universitet " +
                "och högskolor för antagning och betygssättning. Vissa lärosäten har valt att ge eduID åtkomst till " +
                "ESI-attributet (European Student Identifier) från Ladok, som t.ex. används vid ansökning till Erasmus utbytesprogram.");
        common.verifyStringOnPage("Hur du kan länka ditt ESI med eduID:");
        common.verifyStringOnPage("bland dina inställningar i eduID, aktivera ESI-kontrollen,\n" +
                "välj din institution från listan - om den finns tillgänglig.");
    }

    private void privacyEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Personuppgiftsbehandling och Webbtillgänglighet");

        //Heading
        common.verifyStringOnPage("Vad är eduIDs Personuppgiftspolicy?");

        //Text
        common.verifyStringOnPage("Läs Integritetspolicyn i sin helhet för användning av eduID på Sunets " +
                "hemsida, där du även hittar kontaktinformation till Dataskyddsombudet och Integritetsskyddsmyndigheten.");

        //Text
        common.verifyStringOnPage("Sammanfattning av hur eduID behandlar dina personuppgifter enligt policyn:");

        //Text
        common.verifyStringOnPage("lagrar information som du har angivit samt uppdateringar från betrodda register,\n" +
                "överför information enligt minimeringsprincipen - aldrig mer än vad som behövs,\n" +
                "använder informationen för att identifiera individen för tjänster som du väljer att använda,\n" +
                "skyddar och lagrar informationen säkert,\n" +
                "utvecklar med öppen källkod som finns på GitHub,\n" +
                "möjliggör borttagning av ett eduID och dess kopplingar direkt i tjänsten,\n" +
                "lagrar användarloggar i sex månader,\n" +
                "tar bort inaktiva konton efter två år,\n" +
                "sparar endast nödvändiga funktionella kakor.");

        //Heading
        common.verifyStringOnPage("Vad är eduIDs Tillgänglighetsrapport?");

        //Text
        common.verifyStringOnPage("Läs Tillgänglighetsredogörelsen i sin helhet angående eduID.se på " +
                "Sunets hemsida, där du även hittar instruktioner för hur du kan meddela innehåll som brister i " +
                "tillgänglighet. Rapporten beskriver eduIDs arbete för förenlighet med lagen om tillgänglighet till " +
                "digital offentlig service, inklusive kända brister.");

        //Text
        common.verifyStringOnPage("Det är av stor vikt för oss att så många som möjligt kan använda " +
                "tjänsten på ett bra och säkert sätt och är ett av områdena eduID alltid försöker att utvecklas.");
    }

    private void aboutSunetSwe(){
        //Heading
        common.verifyStringOnPage("Om SUNET");

        //Heading
        common.verifyStringOnPage("Vad är SUNET?");

        //Text
        common.verifyStringOnPage("eduID är en tjänst som tillhandahålls av SUNET (Swedish University " +
                "Computer Network), som är en del av Vetenskapsrådet. SUNET tillgodoser bl.a. datakommunikation och " +
                "nätverk hos svenska lärosäten och andra offentliga organisationer med koppling till forskning eller högre utbildning.");

        //Text
        common.verifyStringOnPage("SUNET tog fram eduID i syfte att erbjuda framförallt lärosäten " +
                "gemensamma rutiner för identitetshantering av väl identifierade och autenticerade användare. Läs mer " +
                "om SUNET på www.sunet.se.");
    }

    private void contactEduIdSwe(){
        //Heading
        common.verifyStringOnPage("Kontakt med eduID support");

        //Heading
        common.verifyStringOnPage("Hur kan jag kontakta eduIDs support?");

        //Text
        common.verifyStringOnPage("Om du inte hittar svar på dina frågor om eduID på vår hjälpsida kan " +
                "du kontakta eduID-supporten via e-post till support@eduid.se.");
        common.verifyStringOnPage("Ange alltid den e-postadress som du använde när du loggade in, samt ditt unika 'eppn' ID som du hittar på startsidan i eduID om du är inloggad.\n" +
                "Inkludera inte konfidentiell eller skyddsvärd information som t.ex. ditt personnummer!\n" +
                "Om något har blivit fel är det alltid bra att skicka med skärmdumpar med felmeddelanden för att underlätta felsökning.");
        common.verifyStringOnPage("För bästa möjliga support rekommenderar vi dig alltid att skicka " +
                "e-post, men för enklare ärenden kan du också nå oss per telefonnummer 08-555 213 62");
        common.verifyStringOnPage("Öppettider:");
    }

    private void aboutEduIdEng(){
        //Heading
        common.verifyStringOnPage("About eduID");

        //Heading - text
        common.verifyStringOnPage("What it is and may be used for");

        //Heading
        common.verifyStringOnPage("What is eduID?");

        //Text
        common.verifyStringOnPage("eduID is a federated identity - a user identity that can be used in " +
                "several different organisations that have agreed on how identities will be managed. The basic idea is " +
                "that a given user, who is authenticated with an organisation, is automatically authenticated with other " +
                "organisations in the federation.");

        //Text
        common.verifyStringOnPage("Federated identities are one of the cornerstones of trust between " +
                "organisations. Trust is based on all the organisations relying on all the others to carry out their " +
                "authentication - identification and verification - properly and in a controlled and reliable IT environment.");

        //Heading
        common.verifyStringOnPage("Why have eduID?");

        //Text
        common.verifyStringOnPage("From the user's perspective, in the long-term eduID means fewer " +
                "accounts to keep track of. For many organisations, identity management is a complex issue and it is " +
                "necessary to work with confirmed users.");

        //Text
        common.verifyStringOnPage("There are many services that require identification of users. This " +
                "is often done by the user entering an email address to which the service provider sends a password. " +
                "Such a user is normally called unconfirmed, because the service provider does not really know who the " +
                "user with that email address is - and for many services this is at a sufficient level. Through the use " +
                "of eduID, identification of users is elevated to that of confirmed users.");

        //Heading
        common.verifyStringOnPage("When will I use eduID?");

        //Text
        common.verifyStringOnPage("Depending on where you work or study you might only use your eduID " +
                "account a few times, or you might use it every day. Some schools, institutions and services use eduID " +
                "as their identity provider, this means you will use your eduID to gain access to their IT-systems. Or " +
                "you may mainly use your eduID account to create and access other accounts, such as universityadmissions.se " +
                "or your student account.");

        //Text
        common.verifyStringOnPage("Log in at eduid.se when you:");

        //Text
        common.verifyStringOnPage("apply to and accept your place at a university,\n" +
                "organise your student account for email and intranet,\n" +
                "change university,\n" +
                "lose a student account password and need to regain access.");
    }

    private void useEduIdEng(){
        //Heading
        common.verifyStringOnPage("Using eduID");

        //Heading - text
        common.verifyStringOnPage("How to create, use and strengthen your eduID");

        //Heading
        common.verifyStringOnPage("How can I create an eduID?");

        //Text
        common.verifyStringOnPage("How to create your eduID account at eduid.se:");

        //Text
        common.verifyStringOnPage("register your email address,\n" +
                "confirm that you are human by using CAPTCHA,\n" +
                "accept the eduID terms of use,\n" +
                "verify your email address by entering the code emailed to you,\n" +
                "take note of the email address and password in use when your login details are presented to you. Your eduID is now ready to use.");

        //Heading
        common.verifyStringOnPage("How can I enhance my eduID?");

        //Text
        common.verifyStringOnPage("In eduID you are encouraged to add further details such as:");

        //Text
        common.verifyStringOnPage("your full name to be able to add a security key or access some services from an unverified account,\n" +
                "your phone number for easier retrieval of your account should it be needed,\n" +
                "a security key if you are able to for added security,\n" +
                "connecting your eduID to ESI if enabled by your institution, or sharing it with your existing ORCID iD,\n" +
                "verifying your identity to strengthen your eduID sufficiently for many external services.");

        //Text
        common.verifyStringOnPage("For more detailed information on how to verify your created account " +
                "based on your situation, see the 'Verification of Identity' help section.");

        //Heading
        common.verifyStringOnPage("Which email account can I use to log in?");

        //Text
        common.verifyStringOnPage("You can log in with all the email addresses you have entered and confirmed in eduID.");

        //Heading
        common.verifyStringOnPage("How can I change the default language in eduID?");

        //Text
        common.verifyStringOnPage("To change the default language you can log in to eduID and select " +
                "your language preference in the Personal information area in eduID. The default language is based on " +
                "the language setting that your browser uses. You can also change the displayed language in the footer " +
                "of the webpage. Available options are Swedish and English.");

        //Heading
        common.verifyStringOnPage("How can I log in with other devices?");

        //Text
        common.verifyStringOnPage("You can also login using another device to login to eduID on the device you are currently using:");

        //Text
        common.verifyStringOnPage("select 'Other device' button in the login form,\n" +
                "scan the QR-code with the device where you have your login credentials, e.g. security key or saved password,\n" +
                "on that second device, review the device requesting to be logged in, and use the presented code to log " +
                "in by entering it within the time shown, in the first device.");
    }

    private void securityEduIdEng(){
        //Heading
        common.verifyStringOnPage("Improving the security level of eduID");

        //Heading - text
        common.verifyStringOnPage("Adding an MFA/2FA Security Key");

        //Heading
        common.verifyStringOnPage("How can I make my eduID more secure?");

        //Text
        common.verifyStringOnPage("Depending on the service you are trying to access it might require " +
                "that the account used to log in has reached a certain level of security. When you create your account " +
                "you are required to have knowledge of your username (confirmed email address) and its associated " +
                "password. The password is considered the first factor of authentication. For an additional layer of " +
                "authentication to log in you may add a security key. The security key is called a two-factor " +
                "authentication (2FA) or in some cases multi-factor (MFA), depending on the specifics of the layers of authentication.");

        //Text
        common.verifyStringOnPage("An example of a security key would be a physical device in your " +
                "possession, such as a specific type of USB token for this purpose, that requires you to be present " +
                "by the device. You may also add biometric information such as fingerprint or face-recognition " +
                "supported on the device you are using, to be able to unlock your security key, if needed.");

        //Heading
        common.verifyStringOnPage("How can I add 2FA to eduID?");

        //Text
        common.verifyStringOnPage("Once you have logged in you can add your security keys in the " +
                "Settings area of eduID by following the instructions there.");

        //Text
        common.verifyStringOnPage("Note: if you have added a security key to your eduID it must be " +
                "used to log in and you need to keep at least one.");

        //Heading
        common.verifyStringOnPage("Which type of security key can I use with eduID?");

        //Text
        common.verifyStringOnPage("We follow a standard as well as our own policy for which security " +
                "keys are allowed to be used with the service. More information on the standard as well as an updated " +
                "list of valid keys can be found below.");

        //Heading
        common.verifyStringOnPage("About Security Keys");
        common.verifyStringOnPage("Choosing a Security Key");
        common.verifyStringOnPage("Not all security keys meet the necessary specifications to be used " +
                "as a security key for eduID.");
        common.verifyStringOnPage("Check with the manufacturer or retailer that the product meets the following requirements:");
        common.verifyStringOnPage("Certified FIDO 2.0, you can read more at fidoalliance.org.");
        common.verifyStringOnPage("Releases a certificate issued by the manufacturer providing " +
                "information about the device where used, as well as requiring the user physically present for the key to be used.");
        common.verifyStringOnPage("FURTHER TECHNICAL INFORMATION:");
        common.verifyStringOnPage("The key must perform an attestation and exist in the metadata,\n" +
                "it must not contain any other status in the metadata than a few variants of: \"fido certified\",\n" +
                "it must support any of the following user verification methods: \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\n" +
                "and must not support any other key protection than: \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");

        //Heading
        common.verifyStringOnPage("Valid physical Security Key options");
        common.verifyStringOnPage("Listed below are maker and model names of physical security keys " +
                "which should meet the technical requirements to be used for eduID. They are listed in alphabetical " +
                "order and the list is updated once a month.");
        common.verifyStringOnPage("Next update:");
    }

    private void verificationEduIdEng(){
        //Heading
        common.verifyStringOnPage("Verification of identity");

        //Heading - text
        common.verifyStringOnPage("Levels and methods of verifying eduID for different user groups");

        //Heading
        common.verifyStringOnPage("What are assurance levels?");

        //Text
        common.verifyStringOnPage("Service providers need to rely on organisations to manage their " +
                "users credentials according to certain assurance levels (e.g. AL1-3), depending on the type of " +
                "information accessible. The levels range from unconfirmed, to confirmed, to verified users also using " +
                "MFA when logging in to the system.");

        //Heading
        common.verifyStringOnPage("Which are the methods of verification for eduID?");

        //Text
        common.verifyStringOnPage("The service is constantly being developed to better support the " +
                "needs of our various users. At present the methods below are available, depending on your situation " +
                "such as assurance level requirements, nationality and residence.");

        //Text
        common.verifyStringOnPage("If you have a Swedish personal identity number or coordination " +
                "number, verifying it can be done via:");

        //Text
        common.verifyStringOnPage("post - for Swedish personal identity number holders: the user " +
                "receives a letter with a code sent to their home address as registered at Skatteverket " +
                "(the Swedish Tax Agency), and instructions on how to complete the verification on eduid.se,");

        //Text
        common.verifyStringOnPage("mobile - for Swedish personal identity number holders: the user " +
                "receives a message sent to the phone number that is registered in the Swedish telephone register, and " +
                "instructions on how to complete the verification on eduid.se,");

        //Text
        common.verifyStringOnPage("Freja+ (digital ID-card) - for Swedish personal identity or " +
                "coordination number holders: the user will be directed to the Freja eID website to use their service. " +
                "If you don't have Freja+ you have to create it separately before you can complete verification of your " +
                "eduID. Read more about Freja+ below.");

        //Text
        common.verifyStringOnPage("BankID (electronic identification system) - for Swedish personal " +
                "identity number holders: the user will be asked to verify themself using their BankID service. If you " +
                "don't have BankID you have to create it separately before you can complete verification of your eduID. " +
                "Read more about BankID below.");

        //Text
        common.verifyStringOnPage("If you are an EU citizen and without a Swedish personal identity " +
                "number, you could use eIDAS to verify your identity. Read more about eIDAS below.");

        //Text
        common.verifyStringOnPage("If you are not an EU citizen and without a Swedish personal identity " +
                "number, you could use Svipe ID to verify your identity using your passport. Read more about Svipe iD below.");

        //Heading
        common.verifyStringOnPage("About Freja+");

        //Heading
        common.verifyStringOnPage("What is Freja+?");

        //Text
        common.verifyStringOnPage("Freja+ is a digital ID-card (a verified Freja eID) in app format, " +
                "free of charge, available to holders of a Swedish personal identification number or coordination number.");

        //Text
        common.verifyStringOnPage("How to use Freja+ with eduID:");

        //Text
        common.verifyStringOnPage("install the Freja app on your mobile device (iOS or Android) and " +
                "create a Freja+ account according to the instructions,");

        //Text
        common.verifyStringOnPage("if you have a valid Swedish passport you can complete the verification " +
                "of your account in the app using your device camera, or bring a valid ID (including drivers license or " +
                "ID card) to the nearest ATG agent authorised to verify your identity,");

        //Text
        common.verifyStringOnPage("log in to eduID and choose the 'Freja+ digital ID-card' option in " +
                "the Identity area and follow the instructions.");

        //Heading
        common.verifyStringOnPage("Do I need to visit an authorised ATG agent to create Freja+?");

        //Text
        common.verifyStringOnPage("Only if you use another means of identification than a Swedish " +
                "passport. On site, the agent can start the verification process by scanning a QR code in your Freja " +
                "app and follow the instructions in their terminal. You will be informed when you have passed the ID " +
                "verification and will be able use your Freja+ with your eduID. It can take up to three hours for your " +
                "Freja+ to be fully activated.");

        //Heading
        common.verifyStringOnPage("What should I do if my identity verification for Freja+ fails?");

        //Text
        common.verifyStringOnPage("Reinstall the Freja application, redo the registration and make sure " +
                "that you have entered the correct expiration date as well as the correct reference number of the " +
                "chosen form of ID and personal identity number or coordination number.");

        //Heading
        common.verifyStringOnPage("About BankID");

        //Heading
        common.verifyStringOnPage("What is BankID?");

        //Text
        common.verifyStringOnPage("BankID is a widely used electronic verification system, available " +
                "to holders of a Swedish personal identification number, an approved Swedish ID document (e.g. passport, " +
                "drivers license or ID card) and connected to a bank in Sweden.");

        //Text
        common.verifyStringOnPage("How to use BankID with eduID:");

        //Text
        common.verifyStringOnPage("the BankID is obtained from your personal bank and installed on your " +
                "device as an app or file. The process varies, so visit your bank's website and follow the instructions. " +
                "You can read more about obtaining a BankID on the BankID website");

        //Text
        common.verifyStringOnPage("log in to eduID and choose the 'Electronic BankID' option in the " +
                "Identity area and follow the instructions.");

        //Heading
        common.verifyStringOnPage("About eIDAS");

        //Heading
        common.verifyStringOnPage("What is eIDAS?");

        //Text
        common.verifyStringOnPage("eIDAS is a federation of EU countries providing electronic " +
                "identification to allow access to public authority systems for EU citizens, using their country's electronic ID.");

        //Text
        common.verifyStringOnPage("How to use eIDAS with eduID:");

        //Text
        common.verifyStringOnPage("make sure you have an electronic ID from a connected country to have " +
                "the possibility to authenticate yourself via eIDAS,");

        //Text
        common.verifyStringOnPage("to verify your identity in eduID, log in and choose the verification " +
                "method for 'EU-citizens' in the Identity area and follow the instructions.");

        //Text
        common.verifyStringOnPage("If you have a Swedish personal identity number, use methods supported " +
                "thereby instead e.g. to simplify communication with Swedish authorities. Note: if you initially verify " +
                "your identity with eIDAS and later receive a Swedish personal identity number you can add it in eduID " +
                "and verify yourself again using it in the Identity area.");

        //Heading
        common.verifyStringOnPage("About Svipe iD");

        //Heading
        common.verifyStringOnPage("What is Svipe iD?");

        //Text
        common.verifyStringOnPage("Svipe iD is based on an identity verification platform using biometric " +
                "documents from over 140 countries, e.g. passports and ID-cards, combined with the users mobile device " +
                "face-recognition ability, to create a verified digital identity than can be used remotely.");

        //Text
        common.verifyStringOnPage("You can stay informed about the information that Svipe saves about " +
                "you and your ID-document by reading their data privacy policy. In short; the information uploaded to " +
                "the app from your ID-document is saved locally on your device, and the company Svipe does not have access to it.");

        //Text
        common.verifyStringOnPage("How to use Svipe iD with eduID:");

        //Text
        common.verifyStringOnPage("to verify your eduID using Svipe you first need a Svipe account with " +
                "a verified profile supported by your ID-document, in the Svipe app available at App store for IOS, or " +
                "Google Play for Android,");

        //Text
        common.verifyStringOnPage("log in to eduID and scan the QR code produced by Svipe iD from the " +
                "'All other countries' section in the Identity area of eduID by following the instructions.");

        //Text
        common.verifyStringOnPage("Note: Holders of a Swedish personal identity number are advised to " +
                "use methods supported thereby instead.");
    }

    private void orcidEduIdEng(){
        //Heading
        common.verifyStringOnPage("Connecting account with Orcid / ESI");

        //Heading
        common.verifyStringOnPage("What is ORCID?");

        //Text
        common.verifyStringOnPage("ORCID is integrated into many research-related services, such as " +
                "systems used by publishers, funders and institutions. ORCID is an independent non-profit organisation " +
                "that provides a persistent identifier – an ORCID iD – that distinguishes you from other researchers " +
                "and a mechanism for linking your research outputs and activities to your ORCID iD.");

        //Text
        common.verifyStringOnPage("How to link ORCID with eduID:");

        //Text
        common.verifyStringOnPage("read more and register for an ORCID at orcid.org,\n" +
                "click the 'Connect ORCID account' button in the Settings area of eduID,\n" +
                "sign in to your ORCID account and grant eduID permission to receive your ORCID iD. This process " +
                "ensures that the correct ORCID iD is connected to the correct eduID.");

        //Heading
        common.verifyStringOnPage("How can I remove a linked ORCID from eduID?");

        //Text
        common.verifyStringOnPage("If you no longer want eduID to know your ORCID iD you can remove it " +
                "by clicking the Remove button in your eduID.");


        //ESI
        common.verifyStringOnPage("What is Ladok and ESI?");
        common.verifyStringOnPage("Ladok is a student administration system used in all Swedish higher " +
                "education institutions for registration and grading. Some schools have chosen to release the ESI " +
                "(European Student Identifier) attribute to eduID, used for instance when applying to an Erasmus exchange student program.");
        common.verifyStringOnPage("How to link ESI with eduID:");
        common.verifyStringOnPage("in the Settings area of eduID, toggle the ESI control,\n" +
                "choose your institution from the drop down list - if it is available.");
    }

    private void privacyEduIdEng(){
        //Heading
        common.verifyStringOnPage("Privacy policy and Web accessibility");

        //Heading
        common.verifyStringOnPage("What is eduIDs Privacy policy?");

        //Text
        common.verifyStringOnPage("Read the full Privacy Policy regarding use of eduID at the Sunet " +
                "website, where you also find contact information to our Dataskyddsombud and Integritetsskyddsmyndigheten " +
                "(in Swedish).");

        //Text
        common.verifyStringOnPage("Summary of how eduID treats your information according to the policy:");

        //Text
        common.verifyStringOnPage("stores information that you have provided as well as updates from trusted registers,\n" +
                "transfers information according to the data minimisation principle - never more than required,\n" +
                "uses the information to identify the individual for services you have chosen to use,\n" +
                "protects and stores the information securely,\n" +
                "develops using open source code accessible at GitHub,\n" +
                "enables removal of eduID and connections directly in the service,\n" +
                "stores log files recording use for six months,\n" +
                "retains inactive accounts for a maximum of two years,\n" +
                "only uses necessary functional cookies.");

        //Heading
        common.verifyStringOnPage("What is eduIDs Accessibility report?");

        //Text
        common.verifyStringOnPage("Read the full Accessibility Report regarding the eduID site at " +
                "Sunets website, where you also find instructions on how to report accessibility issues. The report " +
                "addresses how eduID adheres to the Swedish law governing accessibility to digital public services as " +
                "well as currently known issues of the site (in Swedish).");

        //Text
        common.verifyStringOnPage("It is of outmost importance to us that as many as possible are able " +
                "to use the service in a convenient and safe manner and one of the many ways eduID is always striving to improve.");
    }

    private void aboutSunetEng(){
        //Heading
        common.verifyStringOnPage("About SUNET");

        //Heading
        common.verifyStringOnPage("What is SUNET?");

        //Text
        common.verifyStringOnPage("eduID is a service provided by SUNET - the Swedish University " +
                "Computer Network, which is governed by the Swedish Research Council (Vetenskapsrådet). SUNET delivers " +
                "data communication networks and many other related services to public organisations and higher " +
                "education and research institutions.");

        //Text
        common.verifyStringOnPage("SUNET developed eduID to provide a secure common routine for " +
                "managing identity in the higher education community, with adequate authorization levels of confirmed " +
                "accounts. More information about SUNET is available at www.sunet.se.");
    }

    private void contactEduIdEng(){
        //Heading
        common.verifyStringOnPage("Contacting eduID support");

        //Heading
        common.verifyStringOnPage("How can I contact eduID support?");

        //Text
        common.verifyStringOnPage("If you can't find the answers to your questions about eduID on this " +
                "help page, you can contact the eduID support by mailing support@eduid.se.");
        common.verifyStringOnPage("Always let us know the email address you used when you logged in to eduID, and if you are logged in include your ‘eppn’ unique ID as presented in the logged in start page.\n" +
                "Don't include confidential or sensitive information such as your personal identity number in the email!\n" +
                "If something went wrong, it is always a good idea to include screenshots with error messages to ease troubleshooting.");
        common.verifyStringOnPage("In order to get best possible support, we recommend that you send " +
                "e-mail, but for simple matters you can also reach us on phone number 08-555 213 62");
        common.verifyStringOnPage("Opening hours:");
    }

    public void expandAllOptions(){
        common.click(common.findWebElementById("accordion__heading-help-contact"));
        common.click(common.findWebElementById("accordion__heading-help-about-sunet"));
        common.click(common.findWebElementById("accordion__heading-help-privacy-accessibility"));
        common.click(common.findWebElementById("accordion__heading-help-orcid-ladok"));
        common.click(common.findWebElementById("accordion__heading-help-verification"));
        common.click(common.findWebElementById("accordion__heading-help-svipe"));
        common.click(common.findWebElementById("accordion__heading-help-eidas"));
        common.click(common.findWebElementById("accordion__heading-help-freja"));
        common.click(common.findWebElementById("accordion__heading-help-bankid"));
        common.click(common.findWebElementById("accordion__heading-help-security-key"));
        common.click(common.findWebElementById("accordion__heading-help-security-usb"));
        common.click(common.findWebElementById("accordion__heading-security-key-list"));
        common.click(common.findWebElementById("accordion__heading-help-using-eduid"));
        common.click(common.findWebElementById("accordion__heading-help-about-eduid"));
    }
}
