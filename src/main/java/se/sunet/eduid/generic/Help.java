package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;

public class Help {
    private final Common common;

    public Help(Common common){
        this.common = common;
    }

    private String pageBody;

    public void runHelp(){
        clickHelp();
        expandAllOptions();

        //Store page body in swedish
        pageBody = common.getPageBody();
        verifySwedish();

        //Select English
        common.selectEnglish();

        expandAllOptions();

        //Store page body in swedish
        pageBody = common.getPageBody();
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
        common.verifyPageBodyContainsString(pageBody, "Hjälpavsnitt och kontaktuppgifter");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
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
        common.verifyPageBodyContainsString(pageBody, "Help and contact");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Listed below is general information about the service, answers to " +
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
        common.verifyPageBodyContainsString(pageBody, "Om eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Beskrivning och syfte");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eduID är en federerad identitet – en användaridentitet som kan " +
                "användas inom flera olika organisationer eftersom man har enats kring hur identiteter ska hanteras. " +
                "Grundidén är att en given användare som autentiserat sig hos en organisation per automatik kan bli " +
                "autentiserad hos en annan som ingår i federationen.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "För federerade identiteter är en av grundstenarna tillit mellan " +
                "organisationer. Tilliten ligger i att organisationerna litar på att alla genomför sin autentisering, " +
                "identifiering och verifiering, på ett korrekt sätt och i en kontrollerad och tillförlitlig IT-miljö.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Varför finns eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "För användaren leder eduID på sikt till ett färre antal konton att " +
                "hålla reda på under t.ex. studietiden. För många organisationer är identitetshantering en komplex " +
                "fråga och det finns ett behov av att arbeta med bekräftade användare.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Det är många tjänster som har behov av identifiering av användare. " +
                "Ofta sker detta genom att användaren anger en e-postadress dit tjänsten skickar ett lösenord och för " +
                "många tjänster är detta en tillräcklig nivå. En sådan användare kallas ofta obekräftad, eftersom " +
                "tjänsten egentligen inte vet vem användaren med e-postadressen är. Genom eduID kan identifieringen av " +
                "användare lyftas ett steg, till att bli bekräftade användare.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "När använder jag eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur ofta du kommer att använda ditt eduID är beroende av ditt arbete " +
                "eller dina studier; vissa skolor, institutioner och tjänster använder eduID som sin tjänsteleverantör, " +
                "vilket betyder att du behöver använda ditt eduID för att ha tillgång till deras IT-system. Eller så " +
                "använder du bara ditt eduID konto för att skapa åtkomst till andra konton, t.ex. universityadmissions.se " +
                "eller ditt studentkonto.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-about-eduid\"]/article/p[5]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Logga in med eduID när du:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "ansöker till och accepterar plats på högskolan,\n" +
                "organiserar studentkontos e-post och intranät,\n" +
                "byter lärosäte,\n" +
                "förlorar ett lösenord och behöver återfå åtkomst till konto.");
    }

    private void useEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Användning av eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Skapa, använda och utöka ditt eduID");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag skapa ett eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan skapa ditt eduID-konto på eduid.se:");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-using-eduid\"]/article[1]/p[1]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "ange din e-postadress,\n" +
                "bekräfta att du inte är en robot m.h.a. CAPTCHA,\n" +
                "acceptera användarvillkoren,\n" +
                "bekräfta din e-postadress m.h.a. koden som skickats till den,\n" +
                "notera dina inloggningsuppgifter (e-postadress och lösenord). Ditt eduID är redo att användas.\n" +
                "Obs: du kan välja mellan ett automatiskt genererat lösenord eller ett du har skapat själv när du " +
                "registrerar dig, återställer eller byter lösenord.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag utöka mitt eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "I eduID har du möjlighet att lägga till information som t.ex:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "ditt namn för att kunna lägga till en säkerhetsnyckel eller hantera vissa tjänster från ett icke verifierat konto,\n" +
                "ditt telefonnummer för att lättare kunna återställa ditt konto om det skulle behövas,\n" +
                "en säkerhetsnyckel om du har möjlighet för ytterligare säkerhet,\n" +
                "ansluta ditt eduID till ESI och/eller befintligt ORCID iD om det stöds av din institution,\n" +
                "verifiera din identitet för att förstärka ditt eduID tillräckligt för många externa tjänsters behov.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "För mer detaljerad information om hur du bäst kan verifiera ditt " +
                "skapade eduID-konto, se hjälpavsnittet 'Verifiering av identitet'.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vilken e-postadress kan jag använda för att logga in?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Du kan logga in med alla e-postadresser som du angett och bekräftat i eduID.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag ändra standardspråk i eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Det språk som används som standard i eduID är baserat på den " +
                "språkinställning som din webbläsare använder. För att byta standardspråk kan du logga in i eduID och " +
                "välja önskat språk under Personlig information. Du kan också byta det visade språket i hemsidans sidfot. " +
                "Möjliga val är svenska och engelska.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag logga in med en annan enhet?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Inloggning kan även ske genom att logga in i eduID med hjälp av en annan enhet:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "klicka på knappen 'Annan enhet' i inloggnings-formuläret,\n" +
                "skanna QR-koden med enheten där du har din säkerhetsnyckel eller sparat lösenord,\n" +
                "på den andra enheten; kontrollera informationen om enheten som försöker logga in och använd koden " +
                "som visas, inom den angivna tiden, för att logga in på den första enheten.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Jag är redan inloggad, varför behöver jag logga in igen?");
        common.verifyPageBodyContainsString(pageBody, "Vissa situationer kräver högre säkerhet, t.ex. lösenordsbyte, " +
                "ändring av 2FA inställning, raderande av eduID-konto och ändrade säkerhetsnycklar. Om mer än 5 minuter " +
                "har passerat sedan senaste inloggning kommer du behöva logga in igen (med säkerhetsnyckel om du har " +
                "en tillagd) för att slutföra åtgärderna.");
    }

    private void securityEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Utökad säkerhet med ditt eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Lägga till MFA/2FA Säkerhetsnyckel");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag göra mitt eduID säkrare?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Vissa tjänster kräver att kontot du använder för att logga in har " +
                "en högre tillitsnivå. När du skapar ett konto i eduID behöver du kännedom om ditt användarnamn " +
                "(bekräftad epost-adress) och tillhörande lösenord. Lösenordet räknas som den första " +
                "autentiserings-faktorn. För ett ytterligare lager av autentisering för att kunna logga in kan du " +
                "lägga till en säkerhetsnyckel. Säkerhetsnyckeln kallas tvåfaktorsautentisering (2FA) eller i vissa " +
                "fall multifaktorautentisering (MFA).");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Ett exempel på en säkerhetsnyckel är en fysisk ändamålsenlig " +
                "USB-nyckel som kräver att du är närvarande vid enheten. Du har också möjlighet att lägga till " +
                "biometrisk information som fingeravtryck eller ansiktsigenkänning som stöds av enheten du loggar " +
                "in med, för att kunna låsa upp säkerhetsnyckeln vid behov.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag lägga till 2FA för mitt eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "När du har loggat in kan du lägga till och bekräfta säkerhetsnycklar " +
                "bland inställningarna i eduID genom att följa instruktionerna där.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Obs: om du har lagt till en säkerhetsnyckel till ditt eduID behöver " +
                "du sedan använda den för att kunna logga in, om du inte har stängt av 2FA inställningen i Avancerade " +
                "inställningar. Du kan ändå behöva använda säkerhetsnyckeln för åtkomst till andra anslutande tjänster " +
                "som kräver det.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vilka säkerhetsnycklar kan jag använda för eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Vi följer en standard utöver vår egen policy för vilka " +
                "säkerhetsnycklar som är möjliga att användas för tjänsten. Mer information om de tekniska " +
                "förutsättningarna samt en uppdaterad lista över nycklar som möter dem finns nedan.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om Säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Att välja säkerhetsnyckel");
        common.verifyPageBodyContainsString(pageBody, "Inte alla säkerhetsnycklar uppnår kraven för att kunna användas som säkerhetsnyckel för eduID.");
        common.verifyPageBodyContainsString(pageBody, "Kontrollera med tillverkaren eller återförsäljaren om produkten möter dessa krav:");
        common.verifyPageBodyContainsString(pageBody, "Certifierad FIDO 2.0, läs mer på fidoalliance.org.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-security-usb\"]/article/ul/li[1]/a");
        common.verifyPageBodyContainsString(pageBody, "Släpper ett intyg utfärdat av tillverkaren som berättar vilken enhet " +
                "det är i samband med inloggningen och kräver att personen är fysiskt vid säkerhetsnyckeln för att den ska kunna användas. ");
        common.verifyPageBodyContainsString(pageBody, "YTTERLIGARE TEKNISK INFORMATION:");
        common.verifyPageBodyContainsString(pageBody, "Säkerhetsnyckeln måste kunna genomföra en attestation och finnas i metadatat,\n" +
                "får inte ha någon annan status i metadata än några olika varianter av:  \"fido certified\",\n" +
                "måste stödja någon av följande \"user verification methods\":  \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\n" +
                "samt inte stödja någon annan \"key protection\" än:  \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Giltiga modeller av fysiska säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Här listas märke och modellnamn av fysiska säkerhetsnycklar som bör " +
                "möta de tekniska förutsättningarna för att kunna användas för eduID. Listan är sorterad alfabetiskt " +
                "och uppdateras en gång i månaden.");
        common.verifyPageBodyContainsString(pageBody, "Nästa uppdatering:");
    }

    private void verificationEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Verifiering av identitet");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Tillitsnivåer och verifieringsmetoder för olika användargrupper");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är tillitsnivåer?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Tjänsteleverantörer behöver förlita sig på att organisationer " +
                "autentiserar sina användare enligt vissa tillitsnivåer (t.ex. AL1-3), beroende på vilken slags " +
                "information som tillhandahålls. Nivåerna varierar från obekräftade, till bekräftade till verifierade " +
                "användare som även använder MFA vid inloggning till systemet.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vilka verifieringsmetoder finns för eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Tjänsten utvecklas löpande för att bäst kunna möta våra användares " +
                "olika behov. För närvarande stöds verifieringsmetoderna nedan, beroende av din situation som t.ex. " +
                "efterfrågad tillitsnivå, nationalitet och boplats.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du har svenskt personnummer eller samordningsnummer, kan det verifieras med:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "post - med svenskt personnummer: användaren får ett brev med en kod " +
                "skickat till den folkbokföringsadress som är registrerad hos Skatteverket och instruktioner om att " +
                "slutföra verifieringen i eduid.se,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja+ (digitalt ID-kort) - med svenskt personnummer eller " +
                "samordningsnummer: användaren blir hänvisad till Freja eIDs hemsida för att använda sig av deras tjänst. " +
                "Om du inte redan har Freja+ behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om Freja nedan.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "BankID (elektroniskt identifieringssystem) - med svenskt " +
                "personnummer: användaren blir ombedd att identifiera sig med hjälp av sitt BankID i BankID-tjänsten. " +
                "Om du inte redan har ett BankID behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om BankID nedan.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du är EU-medborgare och inte har ett svenskt personnummer, kan du " +
                "använda eIDAS med hjälp av ett europeiskt eID för att verifiera din identitet. Läs mer om eIDAS nedan.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du inte är EU-medborgare och inte har ett svenskt personnummer, " +
                "kan du använda Freja med hjälp av ditt pass för att verifiera din identitet. Läs mer om Freja nedan.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Obs: alla nationaliteter stöds inte ännu av denna lösning men " +
                "arbetet med att avsevärt öka omfattningen är pågående.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om Freja (med Svenskt person/samordnings-nummer)");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är Freja+?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja+ (ett verifierat Freja eID) är ett kostnadsfritt digitalt " +
                "ID-kort tillgängligt för personer med svenskt personnummer eller samordningsnummer.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda Freja+ med eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "installera Freja app på din mobila enhet (iOS eller Android) och " +
                "skapa ett Freja+ konto enligt instruktionerna,");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-freja\"]/article/ul/li[1]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "om du har ett giltigt svenskt pass kan du verifiera ditt konto " +
                "direkt i appen med hjälp av mobilens kamera, eller ta med giltig ID-handling (inklusive körkort eller " +
                "ID-kort) till ett auktoriserat ATG-ombud för att verifiera din identitet,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID och välj metoden 'Med Freja+ digitalt id-kort' i " +
                "identitetshanteringen och följ instruktionerna.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Behöver jag besöka ett auktoriserat ATG ombud för att skapa Freja+?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Bara om du använder en annan ID-handling än svenskt pass. ATG " +
                "ombudet kan påbörja en kontroll av din legitimation genom att scanna den QR-kod som Freja-appen " +
                "genererat och följa instruktionerna på sin skärm. Du kommer bli informerad när legitimationskontrollen " +
                "är slutförd och ditt Freja+ är klart att användas, det kan ta upp till 3 timmar.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad kan jag göra om legitimationskontrollen för Freja+ misslyckades?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Avinstallera appen, gör om registreringen och kontrollera noga att " +
                "du angivit rätt datum då ID-handlingen upphör att gälla samt att du fyllt i rätt referensnummer och " +
                "personnummer eller samordningsnummer.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om BankID");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är BankID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "BankID är en e-legitimation som är tillgänglig för innehavare av " +
                "ett svenskt personnummer, svensk godkänd ID-handling (inkl. pass, körkort och ID-kort) och kund hos " +
                "en av de anslutna bankerna.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda BankID med eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "BankID erhålls från din personliga bank och installeras på din " +
                "enhet som en app eller fil. Tillvägagångssättet varierar, så besök din banks hemsida och följ " +
                "instruktionerna. Du kan läsa mer om att skaffa BankID på the BankID website");

        //Text
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID och välj metoden 'Med elektroniskt BankID' i " +
                "identitetshanteringen och följ instruktionerna.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om eIDAS");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är eIDAS?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eIDAS är en federation av anslutna EU-länder som utfärdar en " +
                "elektronisk identifiering för att erbjuda inloggning till alla offentliga verksamheters system för " +
                "medborgare inom EU, med hjälp av sitt lands e-legitimation.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda eIDAS med eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "kontrollera att du har en elektronisk legitimation från ett anslutet " +
                "land för att kunna autentisera dig med hjälp av eIDAS,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID, navigera till Identitet och välj verifieringsmetoden " +
                "för 'EU-medborgare' och följ instruktionerna.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du har ett svenskt personnummer, använd istället någon av metoderna " +
                "som stöds därav för att underlätta kommunikationen med svenska myndigheter. Obs: om du initialt har " +
                "verifierat dig med eIDAS och senare får svenskt personnummer kan du lägga till det och verifiera dig " +
                "igen med hjälp av det i identitetshanteringen i eduID.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om Freja (utanför EU och utan Svenskt person/samordnings-nummer)");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är Freja?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja är ett eID, en identitetsverifierings-plattform som med hjälp " +
                "av Freja-appen i användarens mobil tillsammans med ett biometriskt pass kan verifiera en digital identitet på distans.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Uppdaterad information om inkluderade länder kan hittas på: Freja eID");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"international\"]/p[2]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda Freja med eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "för att bekräfta ditt eduID med Freja behöver du först ett Freja-konto " +
                "med en profil som är bekräftad med ditt pass, genom att installera Freja app på din mobila enhet (iOS " +
                "eller Android) och följa instruktionerna,");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"international\"]/ul/li[1]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID, navigera till Identitet och välj verifieringsmetoden " +
                "för 'Andra länder' och scanna QR-koden som genereras av Freja genom att följa instruktionerna.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Obs: innehavare av svenskt person- eller samordningsnummer eller " +
                "EU-medborgare uppmanas att istället använda sig av metoderna som stöds därav.");
    }

    private void orcidEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Länkning till Orcid / ESI");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är ORCID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "ORCID är integrerat i många system som används av förläggare, " +
                "universitet, högskolor och andra forskningsrelaterade tjänster och organisationer. ORCID är en " +
                "oberoende ideell organisation som tillhandahåller en bestående identifierare, ett ORCID iD, som unikt " +
                "särskiljer dig från andra forskare och en mekanism för att koppla dina forskningsresultat och " +
                "aktiviteter till ditt ORCID iD.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Hur du kan länka ORCID med eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "läs mer och skaffa ett ORCID på orcid.org,\n" +
                "klicka på knappen 'Länka Orcid konto' bland Inställningar i eduID,\n" +
                "logga in i ORCID och ge eduID tillstånd att använda ditt ORCID iD för att försäkra att det " +
                "är korrekt kopplat till dig.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-orcid-ladok\"]/article[1]/ul/li[1]/a");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag ta bort ett länkat ORCID från eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du inte längre vill att eduID ska känna till ditt ORCID iD kan du " +
                "enkelt ta bort det genom att klicka på krysset.");

        //ESI
        common.verifyPageBodyContainsString(pageBody, "Vad är Ladok och ESI?");
        common.verifyPageBodyContainsString(pageBody, "Ladok är ett system för studieadministration vid svenska universitet " +
                "och högskolor för antagning och betygssättning. Vissa lärosäten har valt att ge eduID åtkomst till " +
                "ESI-attributet (European Student Identifier) från Ladok, som t.ex. används vid ansökning till Erasmus utbytesprogram.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan länka ditt ESI med eduID:");
        common.verifyPageBodyContainsString(pageBody, "bland dina inställningar i eduID, aktivera ESI-kontrollen,\n" +
                "välj din institution från listan - om den finns tillgänglig.");
    }

    private void privacyEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Personuppgiftsbehandling och Webbtillgänglighet");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är eduIDs Personuppgiftspolicy?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Läs Integritetspolicyn i sin helhet för användning av eduID på Sunets " +
                "hemsida, där du även hittar kontaktinformation till Dataskyddsombudet och Integritetsskyddsmyndigheten.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-privacy-accessibility\"]/article[1]/p[1]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Sammanfattning av hur eduID behandlar dina personuppgifter enligt policyn:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "lagrar information som du har angivit samt uppdateringar från betrodda register,\n" +
                "överför information enligt minimeringsprincipen - aldrig mer än vad som behövs,\n" +
                "använder informationen för att identifiera individen för tjänster som du väljer att använda,\n" +
                "skyddar och lagrar informationen säkert,\n" +
                "utvecklar med öppen källkod som finns på GitHub,\n" +
                "möjliggör borttagning av ett eduID och dess kopplingar direkt i tjänsten,\n" +
                "lagrar användarloggar i sex månader,\n" +
                "tar bort inaktiva konton efter två år,\n" +
                "sparar endast nödvändiga funktionella kakor.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är eduIDs Tillgänglighetsrapport?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Läs Tillgänglighetsredogörelsen i sin helhet angående eduID.se på " +
                "Sunets hemsida, där du även hittar instruktioner för hur du kan meddela innehåll som brister i " +
                "tillgänglighet. Rapporten beskriver eduIDs arbete för förenlighet med lagen om tillgänglighet till " +
                "digital offentlig service, inklusive kända brister.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-privacy-accessibility\"]/article[2]/p[1]/a");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Det är av stor vikt för oss att så många som möjligt kan använda " +
                "tjänsten på ett bra och säkert sätt och är ett av områdena eduID alltid försöker att utvecklas.");
    }

    private void aboutSunetSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Om SUNET");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Vad är SUNET?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eduID är en tjänst som tillhandahålls av SUNET (Swedish University " +
                "Computer Network), som är en del av Vetenskapsrådet. SUNET tillgodoser bl.a. datakommunikation och " +
                "nätverk hos svenska lärosäten och andra offentliga organisationer med koppling till forskning eller högre utbildning.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "SUNET tog fram eduID i syfte att erbjuda framförallt lärosäten " +
                "gemensamma rutiner för identitetshantering av väl identifierade och autenticerade användare. Läs mer " +
                "om SUNET på www.sunet.se.");
        //Verify link is working
        common.verifyXpathIsWorkingLink("//*[@id=\"accordion__panel-help-about-sunet\"]/article/p[2]/a");
    }

    private void contactEduIdSwe(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Kontakt med eduID support");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag kontakta eduIDs support?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Om du inte hittar svar på dina frågor om eduID på vår hjälpsida kan " +
                "du kontakta eduID-supporten via e-post till support@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "Ange alltid den e-postadress som du använde när du loggade in, samt ditt unika 'eppn' ID som du hittar på startsidan i eduID om du är inloggad.\n" +
                "Inkludera inte konfidentiell eller skyddsvärd information som t.ex. ditt personnummer!\n" +
                "Om något har blivit fel är det alltid bra att skicka med skärmdumpar med felmeddelanden för att underlätta felsökning.");
        common.verifyPageBodyContainsString(pageBody, "För bästa möjliga support rekommenderar vi dig alltid att skicka " +
                "e-post, men för enklare ärenden kan du också nå oss per telefonnummer 08-555 213 62");
        common.verifyPageBodyContainsString(pageBody, "Öppettider:");
    }

    private void aboutEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "About eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "What it is and may be used for");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eduID is a federated identity - a user identity that can be used in " +
                "several different organisations that have agreed on how identities will be managed. The basic idea is " +
                "that a given user, who is authenticated with an organisation, is automatically authenticated with other " +
                "organisations in the federation.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Federated identities are one of the cornerstones of trust between " +
                "organisations. Trust is based on all the organisations relying on all the others to carry out their " +
                "authentication - identification and verification - properly and in a controlled and reliable IT environment.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Why have eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "From the user's perspective, in the long-term eduID means fewer " +
                "accounts to keep track of. For many organisations, identity management is a complex issue and it is " +
                "necessary to work with confirmed users.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "There are many services that require identification of users. This " +
                "is often done by the user entering an email address to which the service provider sends a password. " +
                "Such a user is normally called unconfirmed, because the service provider does not really know who the " +
                "user with that email address is - and for many services this is at a sufficient level. Through the use " +
                "of eduID, identification of users is elevated to that of confirmed users.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "When will I use eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Depending on where you work or study you might only use your eduID " +
                "account a few times, or you might use it every day. Some schools, institutions and services use eduID " +
                "as their identity provider, this means you will use your eduID to gain access to their IT-systems. Or " +
                "you may mainly use your eduID account to create and access other accounts, such as universityadmissions.se " +
                "or your student account.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Log in at eduid.se when you:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "apply to and accept your place at a university,\n" +
                "organise your student account for email and intranet,\n" +
                "change university,\n" +
                "lose a student account password and need to regain access.");
    }

    private void useEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Using eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "How to create, use and strengthen your eduID");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I create an eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to create your eduID account at eduid.se:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "register your email address,\n" +
                "confirm that you are human by using CAPTCHA,\n" +
                "accept the eduID terms of use,\n" +
                "verify your email address by entering the code emailed to you,\n" +
                "take note of your login details (username and password). Your eduID is now ready to use.\n" +
                "Note: you can choose between an automatically generated password or one you have created, when you " +
                "register, reset and change password.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I enhance my eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "In eduID you are encouraged to add further details such as:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "your full name to be able to add a security key or access some services from an unverified account,\n" +
                "your phone number for easier retrieval of your account should it be needed,\n" +
                "a security key if you are able to for added security,\n" +
                "connecting your eduID to ESI if enabled by your institution, or sharing it with your existing ORCID iD,\n" +
                "verifying your identity to strengthen your eduID sufficiently for many external services.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "For more detailed information on how to verify your created account " +
                "based on your situation, see the 'Verification of Identity' help section.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Which email account can I use to log in?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "You can log in with all the email addresses you have entered and confirmed in eduID.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I change the default language in eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "To change the default language you can log in to eduID and select " +
                "your language preference in the Personal information area in eduID. The default language is based on " +
                "the language setting that your browser uses. You can also change the displayed language in the footer " +
                "of the webpage. Available options are Swedish and English.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I log in with other devices?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "You can also login using another device to login to eduID on the device you are currently using:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "select 'Other device' button in the login form,\n" +
                "scan the QR-code with the device where you have your login credentials, e.g. security key or saved password,\n" +
                "on that second device, review the device requesting to be logged in, and use the presented code to log " +
                "in by entering it within the time shown, in the first device.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "I'm already logged in, why do I need to log in again?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Some situations require added security, e.g. changing your password, " +
                "toggling 2FA login requirement setting, deleting your eduID account or adding/removing a security key. " +
                "If more than 5 minutes have passed since you last logged in you will be asked to log in again (with " +
                "your security key if you are using one), to complete these actions.");
    }

    private void securityEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Improving the security level of eduID");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Adding an MFA/2FA Security Key");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I make my eduID more secure?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Depending on the service you are trying to access it might require " +
                "that the account used to log in has reached a certain level of security. When you create your account " +
                "you are required to have knowledge of your username (confirmed email address) and its associated " +
                "password. The password is considered the first factor of authentication. For an additional layer of " +
                "authentication to log in you may add a security key. The security key is called a two-factor " +
                "authentication (2FA) or in some cases multi-factor (MFA), depending on the specifics of the layers of authentication.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "An example of a security key would be a physical device in your " +
                "possession, such as a specific type of USB token for this purpose, that requires you to be present " +
                "by the device. You may also add biometric information such as fingerprint or face-recognition " +
                "supported on the device you are using, to be able to unlock your security key, if needed.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I add 2FA to eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Once you have logged in you can add your security keys in the " +
                "Settings area of eduID by following the instructions there.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Note: if you have added a security key to your eduID it must be used " +
                "to log in to eduID, unless you turn off this feature under Two-factor Authentication (2FA) in Advanced " +
                "settings. You might still need to use your security key if other connecting services require 2FA.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Which type of security key can I use with eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "We follow a standard as well as our own policy for which security " +
                "keys are allowed to be used with the service. More information on the standard as well as an updated " +
                "list of valid keys can be found below.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "About Security Keys");
        common.verifyPageBodyContainsString(pageBody, "Choosing a Security Key");
        common.verifyPageBodyContainsString(pageBody, "Not all security keys meet the necessary specifications to be used " +
                "as a security key for eduID.");
        common.verifyPageBodyContainsString(pageBody, "Check with the manufacturer or retailer that the product meets the following requirements:");
        common.verifyPageBodyContainsString(pageBody, "Certified FIDO 2.0, you can read more at fidoalliance.org.");
        common.verifyPageBodyContainsString(pageBody, "Releases a certificate issued by the manufacturer providing " +
                "information about the device where used, as well as requiring the user physically present for the key to be used.");
        common.verifyPageBodyContainsString(pageBody, "FURTHER TECHNICAL INFORMATION:");
        common.verifyPageBodyContainsString(pageBody, "The key must perform an attestation and exist in the metadata,\n" +
                "it must not contain any other status in the metadata than a few variants of: \"fido certified\",\n" +
                "it must support any of the following user verification methods: \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\n" +
                "and must not support any other key protection than: \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Valid physical Security Key options");
        common.verifyPageBodyContainsString(pageBody, "Listed below are maker and model names of physical security keys " +
                "which should meet the technical requirements to be used for eduID. They are listed in alphabetical " +
                "order and the list is updated once a month.");
        common.verifyPageBodyContainsString(pageBody, "Next update:");
    }

    private void verificationEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Verification of identity");

        //Heading - text
        common.verifyPageBodyContainsString(pageBody, "Levels and methods of verifying eduID for different user groups");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What are assurance levels?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Service providers need to rely on organisations to manage their " +
                "users credentials according to certain assurance levels (e.g. AL1-3), depending on the type of " +
                "information accessible. The levels range from unconfirmed, to confirmed, to verified users also using " +
                "MFA when logging in to the system.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Which are the methods of verification for eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "The service is constantly being developed to better support the " +
                "needs of our various users. At present the methods below are available, depending on your situation " +
                "such as assurance level requirements, nationality and residence.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you have a Swedish personal identity number or coordination " +
                "number, verifying it can be done via:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "post - for Swedish personal identity number holders: the user " +
                "receives a letter with a code sent to their home address as registered at Skatteverket " +
                "(the Swedish Tax Agency), and instructions on how to complete the verification on eduid.se,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja+ (digital ID-card) - for Swedish personal identity or " +
                "coordination number holders: the user will be directed to the Freja eID website to use their service. " +
                "If you don't have Freja+ you have to create it separately before you can complete verification of your " +
                "eduID. Read more about Freja below.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "BankID (electronic identification system) - for Swedish personal " +
                "identity number holders: the user will be asked to verify themself using their BankID service. If you " +
                "don't have BankID you have to create it separately before you can complete verification of your eduID. " +
                "Read more about BankID below.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you are an EU citizen and without a Swedish personal identity " +
                "number, you could use eIDAS to verify your identity. Read more about eIDAS below.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you are not an EU citizen and without a Swedish personal identity " +
                "number, you could use Freja to verify your identity using your passport. Read more about Freja below.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Note: all nationalities are not yet supported by this solution but " +
                "the work to substantially increase the range is in progress.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "About Freja (with Swedish ID/COORD number)");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is Freja+?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja+ is a digital ID-card (a verified Freja eID) in app format, " +
                "free of charge, available to holders of a Swedish personal identification number or coordination number.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to use Freja+ with eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "install the Freja app on your mobile device (iOS or Android) and " +
                "create a Freja+ account according to the instructions,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "if you have a valid Swedish passport you can complete the verification " +
                "of your account in the app using your device camera, or bring a valid ID (including drivers license or " +
                "ID card) to the nearest ATG agent authorised to verify your identity,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "log in to eduID and choose the 'Freja+ digital ID-card' option in " +
                "the Identity area and follow the instructions.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "Do I need to visit an authorised ATG agent to create Freja+?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Only if you use another means of identification than a Swedish " +
                "passport. On site, the agent can start the verification process by scanning a QR code in your Freja " +
                "app and follow the instructions in their terminal. You will be informed when you have passed the ID " +
                "verification and will be able use your Freja+ with your eduID. It can take up to three hours for your " +
                "Freja+ to be fully activated.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What should I do if my identity verification for Freja+ fails?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Reinstall the Freja application, redo the registration and make sure " +
                "that you have entered the correct expiration date as well as the correct reference number of the " +
                "chosen form of ID and personal identity number or coordination number.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "About BankID");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is BankID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "BankID is a widely used electronic verification system, available " +
                "to holders of a Swedish personal identification number, an approved Swedish ID document (e.g. passport, " +
                "drivers license or ID card) and connected to a bank in Sweden.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to use BankID with eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "the BankID is obtained from your personal bank and installed on your " +
                "device as an app or file. The process varies, so visit your bank's website and follow the instructions. " +
                "You can read more about obtaining a BankID on the BankID website");

        //Text
        common.verifyPageBodyContainsString(pageBody, "log in to eduID and choose the 'Electronic BankID' option in the " +
                "Identity area and follow the instructions.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "About eIDAS");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is eIDAS?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eIDAS is a federation of EU countries providing electronic " +
                "identification to allow access to public authority systems for EU citizens, using their country's electronic ID.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to use eIDAS with eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "make sure you have an electronic ID from a connected country to have " +
                "the possibility to authenticate yourself via eIDAS,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "to verify your identity in eduID, log in and choose the verification " +
                "method for 'EU-citizens' in the Identity area and follow the instructions.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you have a Swedish personal identity number, use that method " +
                "instead e.g. to simplify communication with Swedish authorities. Note: if you initially verify your " +
                "identity with eIDAS and later receive a Swedish personal identity number you can add it in eduID and " +
                "verify yourself again using it in the Identity area.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "About Freja (outside EU and without Swedish ID/COORD number)");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is Freja?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Freja is an eID based on an identity verification platform using " +
                "biometric passports, combined with the users mobile device to create a verified digital identity than " +
                "can be used remotely.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Current information on included nationalities can be found at: Freja eID");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to use Freja with eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "to verify your eduID using Freja you first need to get a Freja " +
                "account with a verified profile supported by your passport, by installing the Freja app on your mobile " +
                "device (iOS or Android) and following the instructions,");

        //Text
        common.verifyPageBodyContainsString(pageBody, "login to eduID and scan the QR code produced by Freja from the 'Other " +
                "countries' section in the Identity area of eduID by following the instructions.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Note: holders of Swedish personal identity numbers or EU citizens " +
                "are advised to use those supported methods instead.");
    }

    private void orcidEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Connecting account with Orcid / ESI");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is ORCID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "ORCID is integrated into many research-related services, such as " +
                "systems used by publishers, funders and institutions. ORCID is an independent non-profit organisation " +
                "that provides a persistent identifier – an ORCID iD – that distinguishes you from other researchers " +
                "and a mechanism for linking your research outputs and activities to your ORCID iD.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "How to link ORCID with eduID:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "read more and register for an ORCID at orcid.org,\n" +
                "click the 'Connect ORCID account' button in the Settings area of eduID,\n" +
                "sign in to your ORCID account and grant eduID permission to receive your ORCID iD. This process " +
                "ensures that the correct ORCID iD is connected to the correct eduID.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I remove a linked ORCID from eduID?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you no longer want eduID to know your ORCID iD you can remove it " +
                "by clicking the Remove button in your eduID.");


        //ESI
        common.verifyPageBodyContainsString(pageBody, "What is Ladok and ESI?");
        common.verifyPageBodyContainsString(pageBody, "Ladok is a student administration system used in all Swedish higher " +
                "education institutions for registration and grading. Some schools have chosen to release the ESI " +
                "(European Student Identifier) attribute to eduID, used for instance when applying to an Erasmus exchange student program.");
        common.verifyPageBodyContainsString(pageBody, "How to link ESI with eduID:");
        common.verifyPageBodyContainsString(pageBody, "in the Settings area of eduID, toggle the ESI control,\n" +
                "choose your institution from the drop down list - if it is available.");
    }

    private void privacyEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Privacy policy and Web accessibility");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is eduIDs Privacy policy?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Read the full Privacy Policy regarding use of eduID at the Sunet " +
                "website, where you also find contact information to our Dataskyddsombud and Integritetsskyddsmyndigheten " +
                "(in Swedish).");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Summary of how eduID treats your information according to the policy:");

        //Text
        common.verifyPageBodyContainsString(pageBody, "stores information that you have provided as well as updates from trusted registers,\n" +
                "transfers information according to the data minimisation principle - never more than required,\n" +
                "uses the information to identify the individual for services you have chosen to use,\n" +
                "protects and stores the information securely,\n" +
                "develops using open source code accessible at GitHub,\n" +
                "enables removal of eduID and connections directly in the service,\n" +
                "stores log files recording use for six months,\n" +
                "retains inactive accounts for a maximum of two years,\n" +
                "only uses necessary functional cookies.");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is eduIDs Accessibility report?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "Read the full Accessibility Report regarding the eduID site at " +
                "Sunets website, where you also find instructions on how to report accessibility issues. The report " +
                "addresses how eduID adheres to the Swedish law governing accessibility to digital public services as " +
                "well as currently known issues of the site (in Swedish).");

        //Text
        common.verifyPageBodyContainsString(pageBody, "It is of outmost importance to us that as many as possible are able " +
                "to use the service in a convenient and safe manner and one of the many ways eduID is always striving to improve.");
    }

    private void aboutSunetEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "About SUNET");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "What is SUNET?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "eduID is a service provided by SUNET - the Swedish University " +
                "Computer Network, which is governed by the Swedish Research Council (Vetenskapsrådet). SUNET delivers " +
                "data communication networks and many other related services to public organisations and higher " +
                "education and research institutions.");

        //Text
        common.verifyPageBodyContainsString(pageBody, "SUNET developed eduID to provide a secure common routine for " +
                "managing identity in the higher education community, with adequate authorization levels of confirmed " +
                "accounts. More information about SUNET is available at www.sunet.se.");
    }

    private void contactEduIdEng(){
        //Heading
        common.verifyPageBodyContainsString(pageBody, "Contacting eduID support");

        //Heading
        common.verifyPageBodyContainsString(pageBody, "How can I contact eduID support?");

        //Text
        common.verifyPageBodyContainsString(pageBody, "If you can't find the answers to your questions about eduID on this " +
                "help page, you can contact the eduID support by mailing support@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "Always let us know the email address you used when you logged in to eduID, and if you are logged in include your ‘eppn’ unique ID as presented in the logged in start page.\n" +
                "Don't include confidential or sensitive information such as your personal identity number in the email!\n" +
                "If something went wrong, it is always a good idea to include screenshots with error messages to ease troubleshooting.");
        common.verifyPageBodyContainsString(pageBody, "In order to get best possible support, we recommend that you send " +
                "e-mail, but for simple matters you can also reach us on phone number 08-555 213 62");
        common.verifyPageBodyContainsString(pageBody, "Opening hours:");
    }

    public void expandAllOptions(){
        common.click(common.findWebElementById("accordion__heading-help-contact"));
        common.click(common.findWebElementById("accordion__heading-help-about-sunet"));
        common.click(common.findWebElementById("accordion__heading-help-privacy-accessibility"));
        common.click(common.findWebElementById("accordion__heading-help-orcid-ladok"));
        common.click(common.findWebElementById("accordion__heading-help-verification"));
        common.click(common.findWebElementById("accordion__heading-help-international"));
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
