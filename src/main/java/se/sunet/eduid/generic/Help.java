package se.sunet.eduid.generic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import se.sunet.eduid.utils.Common;

import java.util.List;

import static se.sunet.eduid.generic.HelpPageLocators.*;

/**
 * Page object for the eduID Help page.
 *
 * Flow:
 *  1. Navigate via the footer link (opens in a new tab).
 *  2. Expand all collapsible sections.
 *  3. Verify all content in Swedish.
 *  4. Switch to English, re-expand, verify all content in English.
 *  5. Restore Swedish.
 *
 * pageBody is passed as a parameter rather than stored as a mutable field,
 * so Swedish and English passes are independent and cannot bleed into each other.
 */
public class Help {

    private final Common common;

    public Help(Common common) {
        this.common = common;
    }

    public void runHelp() {
        clickHelp();
        expandAllMenusAndSubmenus();
        verifySwedish(common.getPageBody());
        common.selectEnglish();
        expandAllMenusAndSubmenus();
        verifyEnglish(common.getPageBody());
        common.selectSwedish();
    }

    public void clickHelp() {
        common.click(common.findWebElement(HELP_FOOTER_LINK));
        common.switchToPopUpWindow();
        common.waitUntilVisible(HELP_H1);
    }

    public void verifySwedish(String pageBody) {
        Common.log.info("Verifying help page in Swedish");
        common.verifyPageBodyContainsString(pageBody, "Hjälpavsnitt och kontaktuppgifter");
        common.verifyPageBodyContainsString(pageBody, "Nedan ser du allmän information om tjänsten, svar på vanliga frågor om användandet av eduID och kontaktinformation till supporten.");
        aboutEduIdSwe(pageBody); useEduIdSwe(pageBody); securityEduIdSwe(pageBody);
        verificationEduIdSwe(pageBody); loaEduIdSwe(pageBody); orcidEduIdSwe(pageBody);
        termsOfUseSwe(pageBody); privacyEduIdSwe(pageBody); aboutSunetSwe(pageBody); contactEduIdSwe(pageBody);
        Common.log.info("Help page Swedish verification complete");
    }

    public void verifyEnglish(String pageBody) {
        Common.log.info("Verifying help page in English");
        common.verifyPageBodyContainsString(pageBody, "Help and contact");
        common.verifyPageBodyContainsString(pageBody, "Listed below is general information about the service, answers to common questions about using eduID and Support contact information.");
        aboutEduIdEng(pageBody); useEduIdEng(pageBody); securityEduIdEng(pageBody);
        verificationEduIdEng(pageBody); loaEduIdEng(pageBody); orcidEduIdEng(pageBody);
        termsOfUseEng(pageBody); privacyEduIdEng(pageBody); aboutSunetEng(pageBody); contactEduIdEng(pageBody);
        Common.log.info("Help page English verification complete");
    }

    // --- Swedish sections ---

    private void aboutEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Om eduID");
        common.verifyPageBodyContainsString(pageBody, "Beskrivning och syfte");
        common.verifyPageBodyContainsString(pageBody, "Vad är eduID?");
        common.verifyPageBodyContainsString(pageBody, "eduID är en federerad identitet – en användaridentitet som kan användas inom flera olika organisationer eftersom man har enats kring hur identiteter ska hanteras. Grundidén är att en given användare som autentiserat sig hos en organisation per automatik kan bli autentiserad hos en annan som ingår i federationen.");
        common.verifyPageBodyContainsString(pageBody, "För federerade identiteter är en av grundstenarna tillit mellan organisationer. Tilliten ligger i att organisationerna litar på att alla genomför sin autentisering, identifiering och verifiering, på ett korrekt sätt och i en kontrollerad och tillförlitlig IT-miljö.");
        common.verifyPageBodyContainsString(pageBody, "Varför finns eduID?");
        common.verifyPageBodyContainsString(pageBody, "För användaren leder eduID på sikt till ett färre antal konton att hålla reda på under t.ex. studietiden. För många organisationer är identitetshantering en komplex fråga och det finns ett behov av att arbeta med bekräftade användare.");
        common.verifyPageBodyContainsString(pageBody, "Det är många tjänster som har behov av identifiering av användare. Ofta sker detta genom att användaren anger en e-postadress dit tjänsten skickar ett lösenord och för många tjänster är detta en tillräcklig nivå. En sådan användare kallas ofta obekräftad, eftersom tjänsten egentligen inte vet vem användaren med e-postadressen är. Genom eduID kan identifieringen av användare lyftas ett steg, till att bli bekräftade användare.");
        common.verifyPageBodyContainsString(pageBody, "När använder jag eduID?");
        common.verifyPageBodyContainsString(pageBody, "Hur ofta du kommer att använda ditt eduID är beroende av ditt arbete eller dina studier; vissa skolor, institutioner och tjänster använder eduID som sin tjänsteleverantör, vilket betyder att du behöver använda ditt eduID för att ha tillgång till deras IT-system. Eller så använder du bara ditt eduID konto för att skapa åtkomst till andra konton, t.ex. ditt studentkonto eller antagning.se.");
        common.verifyPageBodyContainsString(pageBody, "Logga in med eduID när du:");
        common.verifyPageBodyContainsString(pageBody, "ansöker till och accepterar plats på högskolan,\n" +
                "organiserar studentkontos e-post och intranät,\n" +
                "byter lärosäte,\n" +
                "förlorar ett lösenord och behöver återfå åtkomst till konto,\n" +
                "administrera studenter som ska utföra digitala nationella prov,\n" +
                "elektroniskt signera dokument med eduSign, läs mer på edusign.sunet.se.");
    }

    private void useEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Användning av eduID");
        common.verifyPageBodyContainsString(pageBody, "Skapa och logga in med konto");
        common.verifyPageBodyContainsString(pageBody, "Hur skaffar jag ett konto?");
        common.verifyPageBodyContainsString(pageBody, "Skapa ett eduID");
        common.verifyPageBodyContainsString(pageBody, "Hur du registrerar ditt nya eduID-konto på eduid.se/register:");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-create\"]/div/p/a"));
        common.verifyPageBodyContainsString(pageBody, "Ange ditt förnamn, efternamn och e-postadress i formuläret och klicka på knappen \"Skapa eduID\".\nBekräfta att du inte är en robot m.h.a. CAPTCHA genom att skriva in koden som presenteras/läses upp i fälten och klicka på knappen \"Fortsätt\".\nLäs och godkänn användarvillkoren genom att klicka på knappen \"Jag accepterar\".\nBekräfta din e-postadress genom att använda koden som skickats till den i formuläret på sidan och klicka på knappen \"Ok\".\nVälj med hjälp av radioknapparna mellan ett föreslaget (automatiskt genererat) lösenord eller ett du skapar själv.\nNär lösenordet bedömts som tillräckligt starkt, upprepa det i nedre fältet och klick på knappen \"Spara\".\nNotera dina inloggningsuppgifter (den använda e-postadressen och lösenordet)!\nDu kan nu logga in med ditt eduID.");
        common.verifyPageBodyContainsString(pageBody, "Hur loggar jag in med mitt konto?");
        common.verifyPageBodyContainsString(pageBody, "Logga in med eduID");
        common.verifyPageBodyContainsString(pageBody, "MED ANVÄNDARNAMN OCH LÖSENORD");
        common.verifyPageBodyContainsString(pageBody, "Om du har ett eduID-konto, skriv in dina inloggningsuppgifter i formuläret på eduid.se och klicka på knappen \"Logga in\". Ditt användarnamn kan vara");
        common.verifyPageBodyContainsString(pageBody, "alla e-postadresser som lagts till och bekräftats i eduID under Konto\nditt unika ID som visas på inloggade startsidan och under Konto.");
        common.verifyPageBodyContainsString(pageBody, "MED SPARADE ANVÄNDARUPPGIFTER\nUnder inloggningsformuläret finns en toggle-kontroll märkt \"Kom ihåg mig på den här enheten\". Om den är påslagen kommer webbläsaren försöka fylla i sparat användarnamn och dolt lösenord i formuläret. Stäng av kontrollen för att använda ett annat konto eller logga in på en enhet som inte är privat.");
        common.verifyPageBodyContainsString(pageBody, "MED ANNAN ENHET");
        common.verifyPageBodyContainsString(pageBody, "Använd dina inloggningsuppgifter från en annan enhet än du vill logga i eduID med:");
        common.verifyPageBodyContainsString(pageBody, "Klicka på knappen \"Annan enhet\" i inloggningsformuläret.\nSkanna QR-koden som visas med enheten där du har din säkerhetsnyckel eller sparat lösenord.\nPå den andra enheten; kontrollera informationen om enheten som försöker logga in och använd koden som visas, inom den angivna tiden, för att logga in på den första enheten.");
        common.verifyPageBodyContainsString(pageBody, "MED SÄKERHETSNYCKEL");
        common.verifyPageBodyContainsString(pageBody, "Om du har lagt till säkerhetsnyckel för autentisering under Säkerhet kommer den att efterfrågas i ett extra steg efter inloggningsformuläret:");
        common.verifyPageBodyContainsString(pageBody, "Klicka på knappen \"Använd min säkerhetsnyckel\" och följ instruktionerna för din säkerhetsnyckel.\n" +
                "Tillagda säkerhetsalternativ listas i rullgardinsmenyn \"Andra alternativ\" nedanför säkerhetsnyckelsknappen, till exempel BankID, Freja+, eIDAS eller Freja eID, beroende på vad som gäller för din verifierade identitet.\n" +
                "Om du bara vill använda säkerhetsnyckel för att logga in när det efterfrågas, stäng av kontrollen märkt \"Använd alltid säkerhetsnyckel för att logga in\" under Säkerhet.\n" +
                "Obs: du kan läsa mer om säkerhetsnycklar i hjälpavsnittet \"Utökad säkerhet med ditt eduID\".");
        common.verifyPageBodyContainsString(pageBody, "MED PASSKEY");
        common.verifyPageBodyContainsString(pageBody, "En passkey som lagts till ditt eduID kan antingen användas för inloggning tillsammans med användarnamn och lösenord eller utan. För lösenordsfri inloggning behöver en passkey som stöds (även kallad \"discoverable\" eller upptäckbar passkey) ha registrerats efter oktober 2025, då eduID inkluderade stöd därav, eftersom de inte räknades som passkeys i full utsträckning innan dess.");
        common.verifyPageBodyContainsString(pageBody, "När en passkey används som inloggningsuppgift varierar presentation och användningsflöde beroende av din enhet, webbläsare och typ av passkey:\n" +
                "Fokusera användarnamnsfältet, eller så kan du behöva klicka på knappen \"Logga in med passkey\" i inloggningsformuläret. (Om kontrollen \"Kom ihåg mig..\" nedanför aktiveras kan du komma direkt till inloggning med passkey vid nästa inloggning.)\n" +
                "Om några tillämpliga nycklar finns, välj den du vill använda i listan som visas.");
        common.verifyPageBodyContainsString(pageBody, "Följ instruktionerna; t.ex. för ansiktsigenkänning, fingeravtryck eller ange PIN-kod. (eduID har inte tillgång till dessa uppgifter då passkeys sparas lokalt på din egen enhet eller lösenordshanterare.)\n" +
                "Definition och stöd för passkeys utvecklas löpande. Om inloggning misslyckas med din valda nyckel kan det vara för att den inte stöder upptäckbara passkeys, vilket du kan vända dig till leverantören av din passkey för att kontrollera.\n" +
                "Det kan också vara för att den registrerats innan implementationen av denna funktionalitet, varpå den behöver läggas till igen under Säkerhet i eduID.se efter inloggning med annan metod, för att kunna nyttja din passkey för direkt inloggning.\n" +
                "Obs: du kan läsa mer om passkeys och hur man lägger till dem i hjälpavsnittet \"Utökad säkerhet med ditt eduID\".");
        common.verifyPageBodyContainsString(pageBody, "MED ANNAN ENHET");
        common.verifyPageBodyContainsString(pageBody, "Använd dina inloggningsuppgifter från en annan enhet än du vill logga i eduID med:\n" +
                "Klicka på knappen \"Annan enhet\" i inloggningsformuläret.\n" +
                "Skanna QR-koden som visas med enheten där du har din säkerhetsnyckel eller sparat lösenord.\n" +
                "På den andra enheten; kontrollera informationen om enheten som försöker logga in och använd koden som visas, inom den angivna tiden, för att logga in på den första enheten.");
        common.verifyPageBodyContainsString(pageBody, "Återfå tillgång vid glömt lösenord");
        common.verifyPageBodyContainsString(pageBody, "Klicka på länken \"Glömt ditt lösenord?\" under inloggningsformuläret.\n" +
                "Klicka på knappen \"Skicka e-post\" för att skicka en kod till adressen som presenteras på sidan.\n" +
                "Följ instruktionerna i mejlet inom 2 timmar. Stegen för att bekräfta din e-postadress och välja nytt lösenord är samma som när du skapar ditt eduID.\n" +
                "Obs: beroende av dina tidigare inställningar kan du behöva återverifiera din identitet i eduID.");
        common.verifyPageBodyContainsString(pageBody, "Jag är redan inloggad, varför behöver jag logga in igen?");
        common.verifyPageBodyContainsString(pageBody, "Vissa situationer kräver högre säkerhet, om mer än 5 minuter har passerat sedan senaste inloggning kommer du behöva logga in igen (med säkerhetsnyckel om du har en tillagd) för att slutföra åtgärderna, t.ex:");
        common.verifyPageBodyContainsString(pageBody, "Lösenordsbyte.\nPå- och avstängning av MFA vid inloggning.\nRadera eduID-konto.\nLägga till/ta bort säkerhetsnyckel.\nRadera din verifierade identitet.");
        common.verifyPageBodyContainsString(pageBody, "Hur uppdaterar jag mitt konto?");
        common.verifyPageBodyContainsString(pageBody, "När du loggar in i eduid.se finns olika inställningar uppdelade i 4 vyer; Start, Identitet, Säkerhet och Konto som nås med horisontella menyn i sidhuvudet för större skärmar eller en utfällbar meny i övre högra hörnet genom att klicka på användarnamnet. Läs mer om varje sida nedan.");
        common.verifyPageBodyContainsString(pageBody, "Start");
        common.verifyPageBodyContainsString(pageBody, "Startsidan innehåller:");
        common.verifyPageBodyContainsString(pageBody, "ditt namn\n" +
                "ditt unika användar-ID - en del av vad som ibland kallas EPPN.\n" +
                "en statusöversikt av ditt eduID som länkar till föreslagna inställningar i siten. Dessa uppgifter förstärker och utökar användbarheten av ditt eduID:\n" +
                "bekräftat konto (bekräftad e-postadress och godkända användarvillkor)\n" +
                "verifierad verklig identitet\n" +
                "ökad säkerhet (lagt till metod för multifaktor-inloggning)\n" +
                "en verifierad säkerhetsnyckel (kopplat din verifierade identitet till din multifaktor-inloggning).\n" +
                "Obs: Du kan läsa om hur statusen relaterar till anslutande tjänster i hjälpavsnittet \"Tillitsnivåer\".");
        common.verifyPageBodyContainsString(pageBody, "Identitet");
        common.verifyPageBodyContainsString(pageBody, "Identitetssidan innehåller:");
        common.verifyPageBodyContainsString(pageBody, "En tabell med dina verifierade identiteter om du har någon.\nAlternativ för identitetsverifiering om din verkliga identitet inte är verifierad eller din existerande verifiering inte är med ett svenskt person- eller samordningsnummer, beroende på din situation:\nMed svenskt digitalt ID (Freja+/BankID) eller via post.\nMed eIDAS elektronisk identifiering för EU-medborgare.\nMed Freja eID för de flesta länder.\nObs: du kan läsa mer om metoderna i hjälpavsnittet \"Verifiering av identitet\".");
        common.verifyPageBodyContainsString(pageBody, "Ett formulär för att uppdatera namn och visningsnamn genom att klicka på länken \"ändra\", där tillgängliga inställningar beror på om din identitet är verifierad.");
        common.verifyPageBodyContainsString(pageBody, "Säkerhet");
        common.verifyPageBodyContainsString(pageBody, "Säkerhetssidan innehåller:");
        common.verifyPageBodyContainsString(pageBody, "Knappar för att lägga till multifaktorautentisering för att öka säkerheten för ditt eduID m.h.a. en säkerhetsnyckel som ytterligare ett lager till inloggningsprocessen utöver ditt lösenord. Genom att även verifiera säkerhetsnyckeln kopplas den till din identitet vilket stärker kontots tillitsnivå. Du kan lägga till så många säkerhetsnycklar du önskar och beroende på din enhet (dator, mobil, operativsystem m.m.) är alternativen för att lägga till en säkerhetsnyckel följande:\nDenna enhet: inbyggd säkerhetsnyckel i mobil eller laptop, t.ex. passkey med din biometriska information.\nSäkerhetsnyckel: extern enhet som t.ex. din USB-säkerhetsnyckel,");
        common.verifyPageBodyContainsString(pageBody, "Under \"Hantera dina säkerhetsnycklar\" finns en toggle-kontroll märkt \"Använd alltid säkerhetsnyckel för att logga in\" som kan stängas av för att bara använda säkerhetsnyckel för att logga in när det efterfrågas. Standardinställning är påslagen.");
        common.verifyPageBodyContainsString(pageBody, "En tabell som visar dina tillagda nycklar med följande information:\n" +
                "motsvarande ikon för inbyggd eller separat säkerhetsnyckel\n" +
                "det beskrivande namnet du gav nyckeln då den lades till\n" +
                "datum då skapat och senast använt\n" +
                "verifierings-status / alternativ (Freja+/BankID/eIDAS/Freja eID)\n" +
                "en papperskorgs-ikon som raderar nyckeln vid klick.\n" +
                "Obs: du kan läsa mer om säkerhetsnycklar i hjälpavsnittet \"Utökad säkerhet med ditt eduID\".");
        common.verifyPageBodyContainsString(pageBody, "Konto");
        common.verifyPageBodyContainsString(pageBody, "Kontosidan innehåller:");
        common.verifyPageBodyContainsString(pageBody, "E-postadresser: Du kan lägga till så många du önskar men behöver minst en e-postadress som du har bekräftad åtkomst till. Du kan logga in med alla bekräftade adresser men den primära används om vi behöver kommunicera med dig. De listas i en tabell med följande alternativ:\n" +
                "Lägg till genom att klicka på länken \"+ lägg till fler\" och ta bort genom att klicka på papperskorgs-ikonen.\n" +
                "Bekräfta en e-postadress genom att klicka på länken \"Bekräfta\" och fylla i koden som skickas till den adressen i formuläret.\n" +
                "Gör en e-postadress primär genom att klicka på länken \"Gör primär\".");
        common.verifyPageBodyContainsString(pageBody, "Språk: Standardspråket baseras på din webbläsares språkinställning. I eduID kan du kan välja mellan svenska och engelska.\nFör att byta standardspråk kan du logga in i eduID och välja önskat språk med radioknapparna under Konto.\nDu kan även ändra visat språk för laddade sidan i sidfoten.");
        common.verifyPageBodyContainsString(pageBody, "Byt lösenord: Byt alltid ditt lösenord om du tror att någon annan har tillgång till det.\nLänken \"Byt lösenord\" tar dig igenom stegen för att byta ditt lösenord. Som när du skapade ditt eduID kommer du få välja mellan ett föreslaget (automatiskt genererat) lösenord och ett du skapar själv.\nEtt föreslaget slumpmässigt genererat lösenord är vanligtvis säkrast och du kan använda en lösenordshanterare, antigen inbyggd i webbläsaren eller installerad tredje-part, för att hjälpa dig att komma ihåg ditt lösenord.\nOm du använder en passkey för din inloggning, spara den inte på samma nyckelring som ditt lösenord.\nORCID konto: En knapp för att ansluta ditt eduID med befintligt ORCID iD.\nESI information: En toggle-kontroll och meny för att länka ditt eduID till ESI om det stöds av din institution.\nSpärra och radera eduID: Länken \"radera eduID\" öppnar en modal för att bekräfta att ditt konto ska tas bort.\nObs: du kan läsa mer om ORCID iD, Ladok och ESI i hjälpavsnittet \"Länkning till ORCID / ESI\".");
    }

    private void securityEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Utökad säkerhet med ditt eduID");
        common.verifyPageBodyContainsString(pageBody, "Lägga till MFA/2FA säkerhetsnycklar och passkeys");
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag göra mitt eduID säkrare?");
        common.verifyPageBodyContainsString(pageBody, "Vissa tjänster kräver att kontot du använder för att logga in har en högre tillitsnivå. När du skapar ett konto i eduID behöver du kännedom om ditt användarnamn (bekräftad e-postadress) och tillhörande lösenord. Lösenordet räknas som den första autentiserings-faktorn. För ett ytterligare lager av autentisering för att kunna logga in kan du lägga till en säkerhetsnyckel. Säkerhetsnyckeln kallas multifaktorautentisering (MFA) eller i vissa fall tvåfaktorautentisering (2FA).");
        common.verifyPageBodyContainsString(pageBody, "Vad är säkerhetsnycklar och passkeys?\n" +
                "Det kan variera hur termerna används men exempel på en Säkerhetsnyckel är en fysisk ändamålsenlig USB-nyckel som kräver att du är närvarande vid enheten, vilket bidrar till hög säkerhet.\n" +
                "Du har även möjlighet att lägga till en passkey/lösennyckel med biometrisk information som fingeravtryck, ansiktsigenkänning eller PIN som stöds av din enhet för att logga in. Biometriska informationen eller PIN-koden delas aldrig, endast själva nyckeln. Passkeys är ofta synkroniserade mellan dina enheter, vilket gör dem lättanvända.\n" +
                "Dessa tekniker är en form av kryptografi där ett publikt/privat nyckelpar kopplas till ditt unika användarkonto på en specifik webbplats, i vårt fall eduID.se. Den privata nyckeln lagras i en autenticeringsmodul, antingen i din hårdvarunyckel, inbyggd i din enhet eller i en lösenordshanterare. De anses resistenta för phishing/nätfiske och tar bort behovet av ihågkomna lösenord.");
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag lägga till MFA för mitt eduID?");
        common.verifyPageBodyContainsString(pageBody, "När du är inloggad kan du lägga till, verifiera och ta bort säkerhetsnycklar som du har möjlighet att använda, under Säkerhet i eduID och följa instruktionerna. Under \"Lägg till en ny säkerhetsnyckel\" finns alternativen:");
        common.verifyPageBodyContainsString(pageBody, "Knappen \"Den här enheten\" för passkeys\n" +
                "Knappen \"Säkerhetsnyckel\" för fysisk USB-nyckel\n" +
                "Det rekommenderas att lägga till mer än en nyckel för att säkerställa avsedd tillgång till ditt konto.\n" +
                "När du lägger till, eller efteråt i tabellen som listar dina tillagda nycklar, har du möjlighet att verifiera din nyckel med någon av metoderna som listas. Genom att göra det kopplas nyckeln ytterligare till din identitet och höjer generellt tillitsnivån för ditt eduID (läs mer i hjälpavsnittet \"Tillitsnivåer\").");
        common.verifyPageBodyContainsString(pageBody, "Obs: om du har lagt till en säkerhetsnyckel till ditt eduID behöver du sedan använda den för att kunna logga in, om du inte har stängt av MFA inställningen under Säkerhet. Du kan ändå behöva använda säkerhetsnyckeln för åtkomst till andra anslutande tjänster som kräver det.");
        common.verifyPageBodyContainsString(pageBody, "Vilka fysiska säkerhetsnycklar kan jag använda för eduID?");
        common.verifyPageBodyContainsString(pageBody, "Vi följer en standard utöver vår egen policy för vilka säkerhetsnycklar som är möjliga att användas för tjänsten. Mer information om de tekniska förutsättningarna samt en uppdaterad lista över nycklar som möter dem finns nedan.");
        common.verifyPageBodyContainsString(pageBody, "Om Säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Att välja säkerhetsnyckel");
        common.verifyPageBodyContainsString(pageBody, "Inte alla säkerhetsnycklar uppnår kraven för att kunna användas som säkerhetsnyckel för eduID.");
        common.verifyPageBodyContainsString(pageBody, "Kontrollera med tillverkaren eller återförsäljaren om produkten möter dessa krav innan den införskaffas:");
        common.verifyPageBodyContainsString(pageBody, "Certifierad FIDO 2.0, läs mer på fidoalliance.org.");
        common.verifyPageBodyContainsString(pageBody, "Släpper ett intyg utfärdat av tillverkaren som berättar vilken enhet det är i samband med inloggningen och kräver att personen är fysiskt vid säkerhetsnyckeln för att den ska kunna användas. ");
        common.verifyPageBodyContainsString(pageBody, "YTTERLIGARE TEKNISK INFORMATION:");
        common.verifyPageBodyContainsString(pageBody, "Säkerhetsnyckeln måste kunna genomföra en attestation och finnas i metadatat,\nfår inte ha någon annan status i metadata än några olika varianter av:  \"fido certified\",\nmåste stödja någon av följande \"user verification methods\":  \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\nsamt inte stödja någon annan \"key protection\" än:  \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");
        common.verifyPageBodyContainsString(pageBody, "Giltiga modeller av fysiska säkerhetsnycklar");
        common.verifyPageBodyContainsString(pageBody, "Här listas, från Fido Alliance, märke och modellnamn av fysiska säkerhetsnycklar som bör möta de tekniska förutsättningarna för att kunna användas för eduID, det kan även finnas andra som fungerar. Listan är sorterad alfabetiskt och uppdateras regelbundet.");
    }

    private void verificationEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Verifiering av identitet");
        common.verifyPageBodyContainsString(pageBody, "Verifieringsmetoder för olika användargrupper");
        common.verifyPageBodyContainsString(pageBody, "Vilka verifieringsmetoder finns för eduID?");
        common.verifyPageBodyContainsString(pageBody, "Tjänsten utvecklas löpande för att bäst kunna möta våra användares olika behov. För närvarande stöds verifieringsmetoderna nedan, beroende av din situation som t.ex. efterfrågad tillitsnivå, nationalitet och boplats.");
        common.verifyPageBodyContainsString(pageBody, "Obs: du kan ta bort en verifierad identitet som är kopplad till ditt eduID bland inställningarna för Identitet.");
        common.verifyPageBodyContainsString(pageBody, "Om du har svenskt personnummer eller samordningsnummer, kan det verifieras med:");
        common.verifyPageBodyContainsString(pageBody, "post - med svenskt personnummer: användaren får ett brev med en kod skickat till den folkbokföringsadress som är registrerad hos Skatteverket och instruktioner om att slutföra verifieringen i eduid.se,");
        common.verifyPageBodyContainsString(pageBody, "Freja+ (digitalt ID) - med svenskt personnummer eller samordningsnummer: användaren blir hänvisad till Freja eIDs hemsida för att använda sig av deras tjänst. Om du inte redan har Freja+ behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om Freja nedan.");
        common.verifyPageBodyContainsString(pageBody, "BankID (elektroniskt identifieringssystem) - med svenskt personnummer: användaren blir ombedd att identifiera sig med hjälp av sitt BankID i BankID-tjänsten. Om du inte redan har ett BankID behöver du skapa det innan du kan verifiera ditt eduID. Läs mer om BankID nedan.");
        common.verifyPageBodyContainsString(pageBody, "Om du är EU-medborgare och inte har ett svenskt personnummer, kan du använda eIDAS med hjälp av ett europeiskt eID för att verifiera din identitet. Läs mer om eIDAS nedan.");
        common.verifyPageBodyContainsString(pageBody, "Om du inte är EU-medborgare och inte har ett svenskt personnummer, kan du använda Freja med hjälp av ditt pass för att verifiera din identitet. Läs mer om Freja nedan.");
        common.verifyPageBodyContainsString(pageBody, "Obs: alla nationaliteter stöds inte ännu av denna lösning men arbetet med att avsevärt öka omfattningen är pågående.");
        common.verifyPageBodyContainsString(pageBody, "Om Freja (med Svenskt person/samordnings-nummer)");
        common.verifyPageBodyContainsString(pageBody, "Vad är Freja+?");
        common.verifyPageBodyContainsString(pageBody, "Freja+ (ett verifierat Freja eID) är ett kostnadsfritt digitalt ID tillgängligt för personer med svenskt personnummer eller samordningsnummer.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda Freja+ med eduID:");
        common.verifyPageBodyContainsString(pageBody, "installera Freja-appen på din mobila enhet (iOS eller Android) och skapa ett Freja+ konto enligt instruktionerna,");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-freja\"]/div/article/ul/li[1]/a"));
        common.verifyPageBodyContainsString(pageBody, "om du har ett giltigt svenskt pass kan du verifiera ditt konto direkt i appen med hjälp av mobilens kamera, eller ta med giltig ID-handling (inklusive körkort eller ID-kort) till ett auktoriserat ATG-ombud för att verifiera din identitet,");
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID och välj metoden 'Med Freja+' i identitetshanteringen och följ instruktionerna.");
        common.verifyPageBodyContainsString(pageBody, "Behöver jag besöka ett auktoriserat ATG-ombud för att skapa Freja+?");
        common.verifyPageBodyContainsString(pageBody, "Bara om du använder en annan ID-handling än svenskt pass, eller om du har skyddad identitet. ATG-ombudet kan påbörja en kontroll av din legitimation genom att scanna den QR-kod som Freja-appen genererat och följa instruktionerna på sin skärm. Du kommer bli informerad när legitimationskontrollen är slutförd och ditt Freja+ är klart att användas, det kan ta upp till 3 timmar.");
        common.verifyPageBodyContainsString(pageBody, "Vad kan jag göra om legitimationskontrollen för Freja+ misslyckades?");
        common.verifyPageBodyContainsString(pageBody, "Avinstallera appen, gör om registreringen och kontrollera noga att du angivit rätt datum då ID-handlingen upphör att gälla samt att du fyllt i rätt referensnummer och personnummer eller samordningsnummer.");
        common.verifyPageBodyContainsString(pageBody, "Om BankID");
        common.verifyPageBodyContainsString(pageBody, "Vad är BankID?");
        common.verifyPageBodyContainsString(pageBody, "BankID är en e-legitimation som är tillgänglig för innehavare av ett svenskt personnummer, svensk godkänd ID-handling (inkl. pass, körkort och ID-kort) och kund hos en av de anslutna bankerna.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda BankID med eduID:");
        common.verifyPageBodyContainsString(pageBody, "BankID erhålls från din personliga bank och installeras på din enhet som en app eller fil. Tillvägagångssättet varierar, så besök din banks hemsida och följ instruktionerna. Du kan läsa mer om att skaffa BankID på BankID.com.");
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID och välj metoden 'Med elektroniskt BankID' i identitetshanteringen och följ instruktionerna.");
        common.verifyPageBodyContainsString(pageBody, "Om eIDAS");
        common.verifyPageBodyContainsString(pageBody, "Vad är eIDAS?");
        common.verifyPageBodyContainsString(pageBody, "eIDAS är en federation av anslutna EU-länder som utfärdar en elektronisk identifiering för att erbjuda inloggning till alla offentliga verksamheters system för medborgare inom EU, med hjälp av sitt lands e-legitimation.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda eIDAS med eduID:");
        common.verifyPageBodyContainsString(pageBody, "kontrollera att du har en elektronisk legitimation från ett anslutet land för att kunna autentisera dig med hjälp av eIDAS,");
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID, navigera till Identitet och välj verifieringsmetoden för 'EU-medborgare' och följ instruktionerna.");
        common.verifyPageBodyContainsString(pageBody, "Om du har ett svenskt personnummer, använd istället någon av metoderna som stöds därav för att underlätta kommunikationen med svenska myndigheter. Obs: om du initialt har verifierat dig med eIDAS och senare får svenskt personnummer kan du lägga till det och verifiera dig igen med hjälp av det i identitetshanteringen i eduID.");
        common.verifyPageBodyContainsString(pageBody, "Om Freja (utanför EU och utan Svenskt person/samordnings-nummer)");
        common.verifyPageBodyContainsString(pageBody, "Vad är Freja?");
        common.verifyPageBodyContainsString(pageBody, "Freja är ett eID, en identitetsverifierings-plattform som med hjälp av Freja-appen i användarens mobil tillsammans med ett biometriskt pass kan verifiera en digital identitet på distans.");
        common.verifyPageBodyContainsString(pageBody, "Uppdaterad information om inkluderade länder kan hittas på: Freja eID");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"international\"]/p[2]/a"));
        common.verifyPageBodyContainsString(pageBody, "Hur du kan använda Freja med eduID:");
        common.verifyPageBodyContainsString(pageBody, "för att bekräfta ditt eduID med Freja behöver du först ett Freja-konto med en profil som är bekräftad med ditt pass, genom att installera Freja-appen på din mobila enhet (iOS eller Android) och följa instruktionerna,");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"international\"]/ul/li[1]/a"));
        common.verifyPageBodyContainsString(pageBody, "logga in i eduID, navigera till Identitet och välj verifieringsmetoden för 'Andra länder' och scanna QR-koden som genereras av Freja genom att följa instruktionerna.");
        common.verifyPageBodyContainsString(pageBody, "Obs: innehavare av svenskt person- eller samordningsnummer eller EU-medborgare uppmanas att istället använda sig av metoderna som stöds därav.");
    }

    private void loaEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Tillitsnivåer");
        common.verifyPageBodyContainsString(pageBody, "AL, LoA mm.");
        common.verifyPageBodyContainsString(pageBody, "Vad är tillitsnivåer?");
        common.verifyPageBodyContainsString(pageBody, "Tjänsteleverantörer behöver förlita sig på att organisationer autentiserar sina användare enligt vissa tillitsnivåer som sätts av relevanta myndigheter, beroende av vilken slags information som tillhandahålls. Nivåerna varierar från obekräftade, till bekräftade till verifierade användare som även använder ytterligare autentisering vid inloggning till systemet.");
        common.verifyPageBodyContainsString(pageBody, "På vilken nivå är ditt eduID?");
        common.verifyPageBodyContainsString(pageBody, "Under inloggade startsidan presenteras en statusöversikt av ditt eduID. Det här är vad den vanligtvis innebär för din tillitsnivå och tjänsterna som du kan autentisera dig för:");
        common.verifyPageBodyContainsString(pageBody, "BEKRÄFTAT KONTO:\ntjänster som kräver en låg tillitsnivå, ofta kallad AL1 (obekräftad användare) / RAF Low.");
        common.verifyPageBodyContainsString(pageBody, "VERIFIERAD IDENTITET:\ntjänster som kräver en medelhög tillitsnivå, inklusive många lärosäten, ofta kallad AL2 (bekräftad användare) / RAF Medium.");
        common.verifyPageBodyContainsString(pageBody, "ÖKAD SÄKERHET:\ntjänster som kräver att du loggar in med multifaktorautentisering, ofta kallad REFEDS MFA.");
        common.verifyPageBodyContainsString(pageBody, "VERIFIERAD SÄKERHETSNYCKEL:\ntjänster som kräver en stark koppling mellan din identitet och din inloggning, ofta kallad AL3 (verifierad användare) / RAF High / LoA2.");
        common.verifyPageBodyContainsString(pageBody, "Obs: detta är en förenklad sammanfattning och kan förändras, fullständig information angående vad som krävs av ditt eduID behöver tillgodoses av tjänsten som ansluts.");
        common.verifyPageBodyContainsString(pageBody, "Exempelvis med en verifierad svensk identitet och verified säkerhetsnyckel, uppnår kontot i allmänhet SWAMID AL3/ DIGG LoA2 och är på en tillräcklig nivå för t.ex. digitala nationella proven (DNP) och Nice.");
    }

    private void orcidEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Länkning till ORCID / ESI");
        common.verifyPageBodyContainsString(pageBody, "Vad är ORCID?");
        common.verifyPageBodyContainsString(pageBody, "ORCID är integrerat i många system som används av förläggare, universitet, högskolor och andra forskningsrelaterade tjänster och organisationer. ORCID är en oberoende ideell organisation som tillhandahåller en bestående identifierare, ett ORCID iD, som unikt särskiljer dig från andra forskare och en mekanism för att koppla dina forskningsresultat och aktiviteter till ditt ORCID iD.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan länka ORCID med eduID:");
        common.verifyPageBodyContainsString(pageBody, "läs mer och skaffa ett ORCID på orcid.org,\nklicka på knappen 'Länka ORCID konto' under Konto i eduID,\nlogga in i ORCID och ge eduID tillstånd att använda ditt ORCID iD för att försäkra att det är korrekt kopplat till dig.");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-orcid-ladok\"]/div/article[1]/ul/li[1]/a"));
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag ta bort ett länkat ORCID från eduID?");
        common.verifyPageBodyContainsString(pageBody, "Om du inte längre vill att eduID ska känna till ditt ORCID iD kan du enkelt ta bort det genom att klicka på krysset.");
        common.verifyPageBodyContainsString(pageBody, "Vad är Ladok och ESI?");
        common.verifyPageBodyContainsString(pageBody, "Ladok är ett system för studieadministration vid svenska universitet och högskolor för antagning och betygssättning. Vissa lärosäten har valt att ge eduID åtkomst till ESI-attributet (European Student Identifier) från Ladok, som t.ex. används vid ansökning till Erasmus utbytesprogram.");
        common.verifyPageBodyContainsString(pageBody, "Hur du kan länka ditt ESI med eduID:");
        common.verifyPageBodyContainsString(pageBody, "under Konto i eduID, aktivera ESI-kontrollen,\nvälj din institution från listan - om den finns tillgänglig.");
    }

    private void termsOfUseSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Användarvillkor");
        common.verifyPageBodyContainsString(pageBody, "Vad är eduIDs användarvillkor?");
        common.verifyPageBodyContainsString(pageBody, "Villkoren accepteras av användaren när eduID-kontot skapas. Det är en juridisk överenskommelse mellan eduID och dess användare att följa användarvillkoren.\nDu kan bli ombedd att acceptera villkoren på nytt om du inte har använt tjänsten sedan en tid, eller närsomhelst om villkoren har ändrats.");
        common.verifyPageBodyContainsString(pageBody, "För eduID.se gäller generellt:");
        common.verifyPageBodyContainsString(pageBody, "att all användning av användarkonton ska följa Sveriges lagar och förordningar,\natt man är sanningsenlig vid uppgivande av personlig information som namn, kontaktuppgifter el. dyl,\natt användarkonton, lösenord, säkerhetsnycklar och koder är personliga och får endast användas av innehavaren,\natt SUNET:s etiska regler (enligt nedan) reglerar övrig tillåten användning.");
        common.verifyPageBodyContainsString(pageBody, "SUNET bedömer som oetiskt när någon:");
        common.verifyPageBodyContainsString(pageBody, "försöker få tillgång till nätverksresurser utan att ha rätt till det\nförsöker dölja sin användaridentitet\nförsöker störa eller avbryta den avsedda användningen av nätverken\nuppenbart slösar med tillgängliga resurser (personal, maskinvara eller programvara)\nförsöker störa eller avbryta den avsedda användningen av nätverken\ngör intrång i andras privatliv\nförsöker förolämpa eller förnedra andra");
        common.verifyPageBodyContainsString(pageBody, "Den som överträder, eller misstänks överträda, ovanstående regler kan stängas av från eduID.se. Dessutom kan rättsliga åtgärder komma att vidtas.");
    }

    private void privacyEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Personuppgiftsbehandling och Webbtillgänglighet");
        common.verifyPageBodyContainsString(pageBody, "Vad är eduIDs Personuppgiftspolicy?");
        common.verifyPageBodyContainsString(pageBody, "Läs Integritetspolicyn i sin helhet för användning av eduID på Sunets hemsida, där du även hittar kontaktinformation till Dataskyddsombudet och Integritetsskyddsmyndigheten.");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-privacy-accessibility\"]/div/article[1]/p[1]/a"));
        common.verifyPageBodyContainsString(pageBody, "Sammanfattning av hur eduID behandlar dina personuppgifter enligt policyn:");
        common.verifyPageBodyContainsString(pageBody, "lagrar information som du har angivit samt uppdateringar från betrodda register,\növerför information enligt minimeringsprincipen - aldrig mer än vad som behövs,\nanvänder informationen för att identifiera individen för tjänster som du väljer att använda,\nskyddar och lagrar informationen säkert,\nutvecklar med öppen källkod som finns på GitHub,\nmöjliggör borttagning av ett eduID och dess kopplingar direkt i tjänsten,\nlagrar användarloggar i sex månader,\ntar bort inaktiva konton efter två år,\nsparar endast nödvändiga funktionella kakor.");
        common.verifyPageBodyContainsString(pageBody, "Vad är eduIDs Tillgänglighetsrapport?");
        common.verifyPageBodyContainsString(pageBody, "Läs Tillgänglighetsredogörelsen i sin helhet angående eduID.se på Sunets hemsida, där du även hittar instruktioner för hur du kan meddela innehåll som brister i tillgänglighet. Rapporten beskriver eduIDs arbete för förenlighet med lagen om tillgänglighet till digital offentlig service, inklusive kända brister.");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-privacy-accessibility\"]/div/article[2]/p[1]/a"));
        common.verifyPageBodyContainsString(pageBody, "Det är av stor vikt för oss att så många som möjligt kan använda tjänsten på ett bra och säkert sätt och är ett av områdena eduID alltid försöker att utvecklas.");
    }

    private void aboutSunetSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Om SUNET");
        common.verifyPageBodyContainsString(pageBody, "Vad är SUNET?");
        common.verifyPageBodyContainsString(pageBody, "eduID är en tjänst som tillhandahålls av SUNET (Swedish University Computer Network), som är en del av Vetenskapsrådet. SUNET tillgodoser bl.a. datakommunikation och nätverk hos svenska lärosäten och andra offentliga organisationer med koppling till forskning eller högre utbildning.");
        common.verifyPageBodyContainsString(pageBody, "SUNET tog fram eduID i syfte att erbjuda framförallt lärosäten gemensamma rutiner för identitetshantering av väl identifierade och autentiserade användare. Läs mer om SUNET på www.sunet.se.");
        common.verifyLocatorIsWorkingLink(By.xpath("//*[@id=\"help-about-sunet\"]/div/article/p[2]/a"));
    }

    private void contactEduIdSwe(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Kontakt med eduID support");
        common.verifyPageBodyContainsString(pageBody, "Hur kan jag kontakta eduIDs support?");
        common.verifyPageBodyContainsString(pageBody, "Om du inte hittar svar på dina frågor om eduID på vår hjälpsida kan du kontakta eduID-supporten via e-post till support@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "Ange alltid den e-postadress som du använde när du loggade in, samt ditt unika 'eppn' ID som du hittar på startsidan i eduID om du är inloggad.\nInkludera inte konfidentiell eller skyddsvärd information som t.ex. ditt personnummer!\nOm något har blivit fel är det alltid bra att skicka med skärmdumpar med felmeddelanden för att underlätta felsökning.");
        common.verifyPageBodyContainsString(pageBody, "För bästa möjliga support rekommenderar vi dig alltid att skicka e-post, men för enklare ärenden kan du också nå oss per telefonnummer 08-555 213 62");
        common.verifyPageBodyContainsString(pageBody, "Öppettider:");
    }

    // --- English sections ---

    private void aboutEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "About eduID");
        common.verifyPageBodyContainsString(pageBody, "What it is and may be used for");
        common.verifyPageBodyContainsString(pageBody, "What is eduID?");
        common.verifyPageBodyContainsString(pageBody, "eduID is a federated identity - a user identity that can be used in several different organisations that have agreed on how identities will be managed. The basic idea is that a given user, who is authenticated with an organisation, is automatically authenticated with other organisations in the federation.");
        common.verifyPageBodyContainsString(pageBody, "Federated identities are one of the cornerstones of trust between organisations. Trust is based on all the organisations relying on all the others to carry out their authentication - identification and verification - properly and in a controlled and reliable IT environment.");
        common.verifyPageBodyContainsString(pageBody, "Why have eduID?");
        common.verifyPageBodyContainsString(pageBody, "From the user's perspective, in the long-term eduID means fewer accounts to keep track of. For many organisations, identity management is a complex issue and it is necessary to work with confirmed users.");
        common.verifyPageBodyContainsString(pageBody, "There are many services that require identification of users. This is often done by the user entering an email address to which the service provider sends a password. Such a user is normally called unconfirmed, because the service provider does not really know who the user with that email address is - and for many services this is at a sufficient level. Through the use of eduID, identification of users is elevated to that of confirmed users.");
        common.verifyPageBodyContainsString(pageBody, "When will I use eduID?");
        common.verifyPageBodyContainsString(pageBody, "Depending on where you work or study you might only use your eduID account a few times, or you might use it every day. Some schools, institutions and services use eduID as their identity provider, this means you will use your eduID to gain access to their IT-systems. Or you may mainly use your eduID account to create and access other accounts, such as your student account or e.g. universityadmissions.se.");
        common.verifyPageBodyContainsString(pageBody, "Log in at eduid.se when you:");
        common.verifyPageBodyContainsString(pageBody, "apply to and accept your place at a university,\n" +
                "organise your student account for email and intranet,\n" +
                "change university,\n" +
                "lose a student account password and need to regain access,\n" +
                "administrate students taking the digital national exam,\n" +
                "electronically sign documents with eduSign, read more at edusign.sunet.se.");
    }

    private void useEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Using eduID");
        common.verifyPageBodyContainsString(pageBody, "Create and login with account");
        common.verifyPageBodyContainsString(pageBody, "How do I get an account?");
        common.verifyPageBodyContainsString(pageBody, "Create an eduID");
        common.verifyPageBodyContainsString(pageBody, "How to register your new eduID account at eduid.se/register:");
        common.verifyPageBodyContainsString(pageBody, "Enter your first name, last name and email address in the form and press the ”Create eduID” button.\n" +
                "Confirm that you are human using CAPTCHA by entering the displayed/read out code and press the ”Continue” button.\n" +
                "Read and approve the eduID terms of use by pressing the ”I Accept” button.\n" +
                "Verify your email address by entering the code emailed to you in the website form and press the ”Ok” button.\n" +
                "Choose using the radio buttons between a suggested (automatically generated) password or one you create.\n" +
                "When validated for strength, repeat the password in the corresponding field and press the ”Save” button.\n" +
                "Take careful note of your login details (used email address and password)!\n" +
                "You can now log in with your eduID.");
        common.verifyPageBodyContainsString(pageBody, "Log in with eduID");
        common.verifyPageBodyContainsString(pageBody, "WITH USERNAME AND PASSWORD");
        common.verifyPageBodyContainsString(pageBody, "If you have an eduID account, enter your credentials in the form at eduid.se and press the button \"Log in\". Your username can be\nany email address you have entered and confirmed in eduID under Account\nyour unique ID, shown on the logged in start page and under Account.");
        common.verifyPageBodyContainsString(pageBody, "WITH SAVED CREDENTIALS");
        common.verifyPageBodyContainsString(pageBody, "Underneath the login form there is a toggle control called \"Remember me on this device\". If this is switched on the web browser will attempt to fill in your username and hidden password. For a different account or on a shared device, set this to off.");
        common.verifyPageBodyContainsString(pageBody, "WITH ANOTHER DEVICE");
        common.verifyPageBodyContainsString(pageBody, "Use your credentials from another device than you wish to access eduID with:");
        common.verifyPageBodyContainsString(pageBody, "Press the \"Other device\" button in the login form.\nScan the presented QR-code with the other device where you have your login credentials, e.g. security key or saved password.\nOn that second device, review the device requesting to be logged in and use the presented code to login by entering it within the time shown, in the first device.");
        common.verifyPageBodyContainsString(pageBody, "WITH PASSKEY");
        common.verifyPageBodyContainsString(pageBody, "A passkey added to your eduID account can either be used to log in following username and password or by itself. For passwordless login using only your passkey, you need to have registered a supported passkey (sometimes called a \"discoverable passkey\") after October 2025, when eduID included support of this functionality, as they were not fully considered passkeys before.\n" +
                "When using a passkey credential, the access, presentation and required steps will vary depending on your device, browser and type of key:\n" +
                "Focus the username field, or you may need to press the \"Log in with passkey\" button in the login form. (If first setting the \"Remember me..\" toggle below you could directly use your key at your next login.)\n" +
                "If any applicable passkeys exist, select the key you wish to use from the list presented.\n" +
                "Follow the instructions; e.g. scan your face, fingerprint or enter PIN code. (eduID does not retain this information as passkeys are stored locally on your own device or credential manager.)\n" +
                "Definition and support of passkeys continually evolve. If login fails with your selected key it could be because of the key not supporting discoverable passkeys, which you can check with your passkey provider.\n" +
                "It could also be due to it being registered prior to the implementation of this feature, so will need to be readded in the Security section of eduID.se after logging in using another method, to get the full advantage of using your passkey for direct login.\n" +
                "Note: you can read more about passkeys and how to add them in the \"Enhancing the security level of eduID\" help section.");
        common.verifyPageBodyContainsString(pageBody, "WITH SECURITY KEY");
        common.verifyPageBodyContainsString(pageBody, "If you have added a security key for authentication under Security, it will be requested after the initial login form in an additional Security step:");
        common.verifyPageBodyContainsString(pageBody, "Press the \"Use my security key\" button and follow the instructions, which will vary depending on your key.\n" +
                "Added security alternatives are listed in the \"Other options\" dropdown below the security key button, such as BankID, Freja+, eIDAS or Freja eID, depending on what applies to your verified identity.\n" +
                "If you don't wish to use a security key to log in unless required, set the \"Always use a security key to log in\" toggle control under Security to off.\n" +
                "Note: you can read more about security keys in the \"Enhancing the security level of eduID\" help section.");
        common.verifyPageBodyContainsString(pageBody,"WITH PASSKEY");
        common.verifyPageBodyContainsString(pageBody,"A passkey added to your eduID account can either be used to log in following username and password or by itself. For passwordless login using only your passkey, you need to have registered a supported passkey (sometimes called a \"discoverable passkey\") after October 2025, when eduID included support of this functionality, as they were not fully considered passkeys before.\n" +
                "When using a passkey credential, the access, presentation and required steps will vary depending on your device, browser and type of key:\n" +
                "Focus the username field, or you may need to press the \"Log in with passkey\" button in the login form. (If first setting the \"Remember me..\" toggle below you could directly use your key at your next login.)\n" +
                "If any applicable passkeys exist, select the key you wish to use from the list presented.\n" +
                "Follow the instructions; e.g. scan your face, fingerprint or enter PIN code. (eduID does not retain this information as passkeys are stored locally on your own device or credential manager.)\n" +
                "Definition and support of passkeys continually evolve. If login fails with your selected key it could be because of the key not supporting discoverable passkeys, which you can check with your passkey provider.\n" +
                "It could also be due to it being registered prior to the implementation of this feature, so will need to be readded in the Security section of eduID.se after logging in using another method, to get the full advantage of using your passkey for direct login.\n" +
                "Note: you can read more about passkeys and how to add them in the \"Enhancing the security level of eduID\" help section.");
        common.verifyPageBodyContainsString(pageBody,"WITH ANOTHER DEVICE");
        common.verifyPageBodyContainsString(pageBody,"Use your credentials from another device than you wish to access eduID with:\n" +
                "Press the \"Other device\" button in the login form.\n" +
                "Scan the presented QR-code with the other device where you have your login credentials, e.g. security key or saved password.\n" +
                "On that second device, review the device requesting to be logged in and use the presented code to login by entering it within the time shown, in the first device.");
        common.verifyPageBodyContainsString(pageBody, "Regain access if forgotten password");
        common.verifyPageBodyContainsString(pageBody, "Press the \"Forgot your password?\" link below the login form.\nPress the \"Send email\" button to receive a code to the email address presented on the page.\nFollow the instructions in the email within 2 hours. The steps to verify your email address and selecting a new password are the same as when you created your eduID.\nNote: depending on your previous settings you might need to re-verify your identity in eduID.");
        common.verifyPageBodyContainsString(pageBody, "I'm already logged in, why do I need to log in again?");
        common.verifyPageBodyContainsString(pageBody, "In some situations that require added security you will be asked to log in again (with your security key if you are using one), if more than 5 minutes have passed since you logged in, e.g:");
        common.verifyPageBodyContainsString(pageBody, "Changing your password.\nToggling MFA login requirement setting.\nDeleting your eduID account.\nAdding/removing a security key.\nDeleting your verified identity.");
        common.verifyPageBodyContainsString(pageBody, "How do I update my account?");
        common.verifyPageBodyContainsString(pageBody, "When you log in to eduid.se the various settings are grouped into 4 views; Start, Identity, Security and Account, accessible from the header with a horizontal menu for larger screens or drop down menu by clicking on your username on smaller screens. Read more about the possible actions of each page below.");
        common.verifyPageBodyContainsString(pageBody, "The Start page contains:");
        common.verifyPageBodyContainsString(pageBody, "your name\nyour unique user ID - part of what is sometimes referred to as EPPN.\nan overview of the status of your eduID with links to where it can be addressed in the site. These tasks strengthen and increase the use of your eduID:\nconfirmed account (confirmed email address and accepted terms of use)\nreal identity verified\nenhanced security (added a method used for multi factor login)\na verified security key (bound your verified identity to your multi factor login).\nNote: You can read about how the status correlates to connecting services in the help section \"Assurance levels\".");
        common.verifyPageBodyContainsString(pageBody, "The Identity page contains:");
        common.verifyPageBodyContainsString(pageBody, "A table presenting your verified identities if you have any.\nOptions for identity verification if your real identity is not verified, or if your existing verification is not with a Swedish ID- or coordination number, depending on your situation:\nWith Swedish digital ID (Freja+/BankID) or by post.\nWith eIDAS electronic identification for EU citizens.\nWith Freja eID for most nationalities.\nNote: You can read more about these methods in the help section \"Verification of identity\".");
        common.verifyPageBodyContainsString(pageBody, "A form for updating name and display name by clicking on the link \"edit\", where available settings are dependent on wether your identity is verified.");
        common.verifyPageBodyContainsString(pageBody, "The Security page contains:");
        common.verifyPageBodyContainsString(pageBody, "Buttons to add multi-factor authentication to increase the security of your eduID by adding a layer called a security key to your login process besides password. By also verifying the security key it is bound to your identity, increasing the assurance level of your account. You can add as many as you wish and depending on your device (computer, mobile, operating system etc.), the options to add a security key include:\nThis device: built in security key in mobile or laptop, e.g. a passkey, including your biometrics.\nSecurity key: external device such as your USB security key.");
        common.verifyPageBodyContainsString(pageBody, "Under \"Manage your security keys\" is a toggle control marked \"Always use a security key to log in\" which can let you log in with your eduID account without using your added security key when allowed. Default setting is on.");
        common.verifyPageBodyContainsString(pageBody, "A table displaying all your added security keys with the following information:\n" +
                "corresponding icon for built in or separate security key\n" +
                "the descriptive name given by you when created\n" +
                "dates of creation and latest use\n" +
                "verification status / verification options (Freja+/BankID/eIDAS/Freja eID)\n" +
                "a bin icon which deletes the key when clicked.\n" +
                "Note: You can read more about security keys in the help section 'Enhancing the security level of eduID'.");
        common.verifyPageBodyContainsString(pageBody, "The Account page contains:");
        common.verifyPageBodyContainsString(pageBody, "Email addresses: You can add as many as you wish, but need to have at least one email address that you have confirmed access to. You can log in with all confirmed addresses but the primary one will be used for communication with you if necessary. They are listed in a table with the following options:\n" +
                "Add by clicking on the '+ add more' link and remove by clicking on the bin icon.\n" +
                "Confirm an address by clicking on the link \"Confirm\" and enter the code that is emailed to that address into the website form.\n" +
                "Make one email address your primary address by clicking on the link \"Make primary\".");
        common.verifyPageBodyContainsString(pageBody, "Language: The default language is based on the language setting that your browser uses. Available options are Swedish and English.\nTo change the default language for eduID you can log in to eduID and select your preference using the Language radio buttons under Account.\nYou can also change the language for the loaded page in the footer of the webpage.");
        common.verifyPageBodyContainsString(pageBody, "Change password: Always change your password if you believe someone else has access to it.\nClicking on the link \"Change password\" will take you through the steps for changing your password. As when you created your eduID, there is a choice between a suggested (automatically generated) password or one you create.\nA randomly created suggested password is generally considered safest and you can use a third party or browser built in Password Manager tool to help you keep track of your password.\nIf you are using a passkey for your login, don't save it on the same key chain as your password.\nORCID account: A button connecting your eduID with your existing ORCID iD.\nESI information: A toggle control and select menu connecting your eduID to ESI, if enabled by your institution.\nBlocking and deleting eduID: Clicking on the link \"delete eduID\" will open a modal confirming removal of your account.\nNote: you can read more about ORCID iD and Ladok and ESI settings in the \"Connecting account with Orcid / ESI\" help section.\"");
    }

    private void securityEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Enhancing the security level of eduID");
        common.verifyPageBodyContainsString(pageBody, "Adding MFA Security Keys and Passkeys");
        common.verifyPageBodyContainsString(pageBody, "How can I make my eduID more secure?");
        common.verifyPageBodyContainsString(pageBody, "Some services will require a higher security level and to improve the security of your eduID, in addition to knowledge of your username (e.g. confirmed email address) and password combination, you can use another layer of authentication to log in. This is called multi-Factor authentication (MFA); and in eduID's case usually two-factor authentication (2FA).");
        common.verifyPageBodyContainsString(pageBody, "What are Security keys and Passkeys?");
        common.verifyPageBodyContainsString(pageBody, "Though the terms are sometimes used interchangeably, an example of a security key would be a physical device in your possession, such as a specific type of USB hardware token for this purpose, that requires you to be present by the device, providing a high level of security.\n" +
                "You can also add a passkey on your device that you can use to log in. The passkey may be used with biometric information such as fingerprint, face-recognition or a PIN depending on your device. The biometric information or PIN is never shared with us, only the passkey itself. Passkeys are often synced between your devices, providing ease of use.\n" +
                "These techniques are a form of cryptography, where a public/private key pair is bound to your specific account on a particular website, in this case eduID.se. The private key is stored in an \"authenticator\" module, either on your hardware key, built into your device or in a credential manager app. It is considered phishing-resistant and removes the need for remembered passwords.");
        common.verifyPageBodyContainsString(pageBody, "How do I implement MFA with eduID?");
        common.verifyPageBodyContainsString(pageBody, "When logged in you can add, verify and remove keys of your choice (provided you have access to any of these methods) in the Security area of eduID and follow the instructions. Under the \"Add a new security key\" label are the options:\n" +
                "\"This device\" button for passkeys\n" +
                "\"Security key\" button for physical USB keys\n" +
                "It is recommended to add more than one key to make sure you always have supported access to your account.\n" +
                "When adding a key, or later in the table listing your added keys, you have the option of verifying your key with any method presented to you. By doing so it is further connected with your identity and generally increasing the assurance level of your eduID (read more in the \"Assurance levels\" help section).");
        common.verifyPageBodyContainsString(pageBody, "Note: if you have added a security key to your eduID it must be used to log in to eduID, unless you turn off this feature under \"Manage your security keys\" in the Security section. You might still need to use your security key if other connecting services require MFA.");
        common.verifyPageBodyContainsString(pageBody, "Which type of physical security key can I use with eduID?");
        common.verifyPageBodyContainsString(pageBody, "We follow a standard as well as our own policy for which security keys are allowed to be used with the service. More information on the standard as well as an updated list of valid keys can be found below.");
        common.verifyPageBodyContainsString(pageBody, "About Security Keys");
        common.verifyPageBodyContainsString(pageBody, "Choosing a Security Key");
        common.verifyPageBodyContainsString(pageBody, "Not all security keys meet the necessary specifications to be used as a security key for eduID.");
        common.verifyPageBodyContainsString(pageBody, "Check with the manufacturer or retailer that the product meets the following requirements before it is acquired:");
        common.verifyPageBodyContainsString(pageBody, "Certified FIDO 2.0, you can read more at fidoalliance.org.");
        common.verifyPageBodyContainsString(pageBody, "Releases a certificate issued by the manufacturer providing information about the device where used, as well as requiring the user physically present for the key to be used.");
        common.verifyPageBodyContainsString(pageBody, "FURTHER TECHNICAL INFORMATION:");
        common.verifyPageBodyContainsString(pageBody, "The key must perform an attestation and exist in the metadata,\nit must not contain any other status in the metadata than a few variants of: \"fido certified\",\nit must support any of the following user verification methods: \"faceprint_internal\", \"passcode_external\", \"passcode_internal\", \"handprint_internal\", \"pattern_internal\", \"voiceprint_internal\", \"fingerprint_internal\", \"eyeprint_internal\",\nand must not support any other key protection than: \"remote_handle\", \"hardware\", \"secure_element\", \"tee\".");
        common.verifyPageBodyContainsString(pageBody, "Valid physical Security Key options");
        common.verifyPageBodyContainsString(pageBody, "This is a list, derived from Fido Alliance, of names of maker and models of external security keys that can be used for eduID, though there might be other keys that could work for this purpose. They are listed in alphabetical order and updated regularly.");
    }

    private void verificationEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Verification of identity");
        common.verifyPageBodyContainsString(pageBody, "Methods of verifying eduID for different user groups");
        common.verifyPageBodyContainsString(pageBody, "Which are the methods of verification for eduID?");
        common.verifyPageBodyContainsString(pageBody, "The service is constantly being developed to better support the needs of our various users. At present the methods below are available, depending on your situation such as assurance level requirements, nationality and residence.");
        common.verifyPageBodyContainsString(pageBody, "Note: you can remove a verified identity connected to your eduID in the Identity area.");
        common.verifyPageBodyContainsString(pageBody, "If you have a Swedish personal identity number or coordination number, verifying it can be done via:");
        common.verifyPageBodyContainsString(pageBody, "post - for Swedish personal identity number holders: the user receives a letter with a code sent to their home address as registered at Skatteverket (the Swedish Tax Agency), and instructions on how to complete the verification on eduid.se,");
        common.verifyPageBodyContainsString(pageBody, "Freja+ (digital ID) - for Swedish personal identity or coordination number holders: the user will be directed to the Freja eID website to use their service. If you don't have Freja+ you have to create it separately before you can complete verification of your eduID. Read more about Freja below.");
        common.verifyPageBodyContainsString(pageBody, "BankID (electronic identification system) - for Swedish personal identity number holders: the user will be asked to verify themself using their BankID service. If you don't have BankID you have to create it separately before you can complete verification of your eduID. Read more about BankID below.");
        common.verifyPageBodyContainsString(pageBody, "If you are an EU citizen and without a Swedish personal identity number, you could use eIDAS to verify your identity. Read more about eIDAS below.");
        common.verifyPageBodyContainsString(pageBody, "If you are not an EU citizen and without a Swedish personal identity number, you could use Freja to verify your identity using your passport. Read more about Freja below.");
        common.verifyPageBodyContainsString(pageBody, "Note: all nationalities are not yet supported by this solution but the work to substantially increase the range is in progress.");
        common.verifyPageBodyContainsString(pageBody, "About Freja (with Swedish ID/COORD number)");
        common.verifyPageBodyContainsString(pageBody, "What is Freja+?");
        common.verifyPageBodyContainsString(pageBody, "Freja+ is a digital ID (a verified Freja eID) free of charge, available to holders of a Swedish personal identification number or coordination number.");
        common.verifyPageBodyContainsString(pageBody, "How to use Freja+ with eduID:");
        common.verifyPageBodyContainsString(pageBody, "install the Freja app on your mobile device (iOS or Android) and create a Freja+ account according to the instructions,");
        common.verifyPageBodyContainsString(pageBody, "if you have a valid Swedish passport you can complete the verification of your account in the app using your device camera, or bring a valid ID (including drivers license or ID card) to the nearest ATG agent authorised to verify your identity,");
        common.verifyPageBodyContainsString(pageBody, "log in to eduID and choose the 'With Freja+' option in the Identity area and follow the instructions.");
        common.verifyPageBodyContainsString(pageBody, "Do I need to visit an authorised ATG agent to create Freja+?");
        common.verifyPageBodyContainsString(pageBody, "Only if you use another means of identification than a Swedish passport, or if you have a protected identity. On site, the agent can start the verification process by scanning a QR code in your app and follow the instructions in their terminal. You will be informed when you have passed the ID verification and will be able use your Freja+ with your eduID. It can take up to three hours for your Freja+ to be fully activated.");
        common.verifyPageBodyContainsString(pageBody, "What should I do if my identity verification for Freja+ fails?");
        common.verifyPageBodyContainsString(pageBody, "Reinstall the Freja application, redo the registration and make sure that you have entered the correct expiration date as well as the correct reference number of the chosen form of ID and personal identity number or coordination number.");
        common.verifyPageBodyContainsString(pageBody, "About BankID");
        common.verifyPageBodyContainsString(pageBody, "What is BankID?");
        common.verifyPageBodyContainsString(pageBody, "BankID is a widely used electronic verification system, available to holders of a Swedish personal identification number, an approved Swedish ID document (e.g. passport, drivers license or ID card) and connected to a bank in Sweden.");
        common.verifyPageBodyContainsString(pageBody, "How to use BankID with eduID:");
        common.verifyPageBodyContainsString(pageBody, "the BankID is obtained from your personal bank and installed on your device as an app or file. The process varies, so visit your bank's website and follow the instructions. You can read more about obtaining a BankID on BankID.com,");
        common.verifyPageBodyContainsString(pageBody, "log in to eduID and choose the 'Electronic BankID' option in the Identity area and follow the instructions.");
        common.verifyPageBodyContainsString(pageBody, "About eIDAS");
        common.verifyPageBodyContainsString(pageBody, "What is eIDAS?");
        common.verifyPageBodyContainsString(pageBody, "eIDAS is a federation of EU countries providing electronic identification to allow access to public authority systems for EU citizens, using their country's electronic ID.");
        common.verifyPageBodyContainsString(pageBody, "How to use eIDAS with eduID:");
        common.verifyPageBodyContainsString(pageBody, "make sure you have an electronic ID from a connected country to have the possibility to authenticate yourself via eIDAS,");
        common.verifyPageBodyContainsString(pageBody, "to verify your identity in eduID, log in and choose the verification method for 'EU-citizens' in the Identity area and follow the instructions.");
        common.verifyPageBodyContainsString(pageBody, "If you have a Swedish personal identity number, use that method instead e.g. to simplify communication with Swedish authorities. Note: if you initially verify your identity with eIDAS and later receive a Swedish personal identity number you can add it in eduID and verify yourself again using it in the Identity area.");
        common.verifyPageBodyContainsString(pageBody, "About Freja (outside EU and without Swedish ID/COORD number)");
        common.verifyPageBodyContainsString(pageBody, "What is Freja?");
        common.verifyPageBodyContainsString(pageBody, "Freja is an eID based on an identity verification platform using biometric passports, combined with the users mobile device to create a verified digital identity than can be used remotely.");
        common.verifyPageBodyContainsString(pageBody, "Current information on included nationalities can be found at: Freja eID");
        common.verifyPageBodyContainsString(pageBody, "How to use Freja with eduID:");
        common.verifyPageBodyContainsString(pageBody, "to verify your eduID using Freja you first need to get a Freja account with a verified profile supported by your passport, by installing the Freja app on your mobile device (iOS or Android) and following the instructions,");
        common.verifyPageBodyContainsString(pageBody, "login to eduID and scan the QR code produced by Freja from the 'Other countries' section in the Identity area of eduID by following the instructions.");
        common.verifyPageBodyContainsString(pageBody, "Note: holders of Swedish personal identity numbers or EU citizens are advised to use those supported methods instead.");
    }

    private void loaEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Assurance levels");
        common.verifyPageBodyContainsString(pageBody, "AL, LoA etc.");
        common.verifyPageBodyContainsString(pageBody, "What are assurance levels?");
        common.verifyPageBodyContainsString(pageBody, "Service providers need to rely on organisations to manage their users credentials according to certain assurance levels set by relevant authorities, depending on the type of information accessible. The levels range from unconfirmed, to confirmed, to verified users with additional authentication when logging in to a system.");
        common.verifyPageBodyContainsString(pageBody, "At what level is your eduID?");
        common.verifyPageBodyContainsString(pageBody, "At the logged in start page an overview of the status of your eduID is presented. This is what it typically indicates regarding your assurance level and the services you may authenticate to:");
        common.verifyPageBodyContainsString(pageBody, "CONFIRMED ACCOUNT:\nservices requiring a low level of assurance, often called AL1 (unconfirmed user) / RAF Low.");
        common.verifyPageBodyContainsString(pageBody, "VERIFIED IDENTITY:\nservices requiring a medium level of assurance, including many higher education institutions, often called AL2 (confirmed user) / RAF Medium.");
        common.verifyPageBodyContainsString(pageBody, "ENHANCED SECURITY:\nservices requiring you to log in using multi factor authentication, often called REFEDS MFA.");
        common.verifyPageBodyContainsString(pageBody, "VERIFIED SECURITY KEY:\nservices requiring a strong binding between your identity and your login, often called AL3 (verified user) / RAF High / LoA2.\n");
        common.verifyPageBodyContainsString(pageBody, "Note: this is a generalization and could change, complete information as to what is required of your eduID must be provided by the connecting services.");
        common.verifyPageBodyContainsString(pageBody, "As an example, with a verified Swedish identity and a verified security key the account is at a sufficient level for the purpose of e.g. digital national exams (DNP) and Nice.");
    }

    private void orcidEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Connecting account with ORCID / ESI");
        common.verifyPageBodyContainsString(pageBody, "What is ORCID?");
        common.verifyPageBodyContainsString(pageBody, "ORCID is integrated into many research-related services, such as systems used by publishers, funders and institutions. ORCID is an independent non-profit organisation that provides a persistent identifier \u2013 an ORCID iD \u2013 that distinguishes you from other researchers and a mechanism for linking your research outputs and activities to your ORCID iD.");
        common.verifyPageBodyContainsString(pageBody, "How to link ORCID with eduID:");
        common.verifyPageBodyContainsString(pageBody, "read more and register for an ORCID at orcid.org,\nclick the 'Add ORCID account' button in the Account area of eduID,\nsign in to your ORCID account and grant eduID permission to receive your ORCID iD. This process ensures that the correct ORCID iD is connected to the correct eduID.");
        common.verifyPageBodyContainsString(pageBody, "How can I remove a linked ORCID from eduID?");
        common.verifyPageBodyContainsString(pageBody, "If you no longer want eduID to know your ORCID iD you can remove it by clicking the Remove button in your eduID.");
        common.verifyPageBodyContainsString(pageBody, "What is Ladok and ESI?");
        common.verifyPageBodyContainsString(pageBody, "Ladok is a student administration system used in all Swedish higher education institutions for registration and grading. Some schools have chosen to release the ESI (European Student Identifier) attribute to eduID, used for instance when applying to an Erasmus exchange student program.");
        common.verifyPageBodyContainsString(pageBody, "How to link ESI with eduID:");
        common.verifyPageBodyContainsString(pageBody, "in the Account area of eduID, toggle the ESI control,\nchoose your institution from the drop down list - if it is available.");
    }

    private void termsOfUseEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Terms of use");
        common.verifyPageBodyContainsString(pageBody, "What are eduIDs terms of use?");
        common.verifyPageBodyContainsString(pageBody, "These terms are accepted by the user upon creating an eduID account. It is a legal agreement between eduID and its users to abide by the terms.\nYou may be asked to accept the terms again if you haven't used the service for a period of time, or any time the terms have changed.");
        common.verifyPageBodyContainsString(pageBody, "The following generally applies:");
        common.verifyPageBodyContainsString(pageBody, "that all usage of user accounts follow the laws and by-laws of Sweden,\nthat all personal information that you provide, such as name and contact information shall be truthful,\nthat user accounts, password, security keys and codes are individual and shall only be used by the intended individual,\nthat SUNET's ethical rules (listed below) regulate other permitted usage.");
        common.verifyPageBodyContainsString(pageBody, "SUNET judges unethical behaviour to be when someone:");
        common.verifyPageBodyContainsString(pageBody, "attempts to gain access to network resources that they do not have the right to\nattempts to conceal their user identity\nattempts to interfere or disrupt the intended usage of the network\nclearly wastes available resources (personnel, hardware or software)\nattempts to disrupt or destroy computer-based information\ninfringes on the privacy of others\nattempts to insult or offend others");
        common.verifyPageBodyContainsString(pageBody, "Any person found violating or suspected of violating these rules can be disabled from eduID.se for investigation. Furthermore, legal action may be taken.");
    }

    private void privacyEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Privacy policy and Web accessibility");
        common.verifyPageBodyContainsString(pageBody, "What is eduIDs Privacy policy?");
        common.verifyPageBodyContainsString(pageBody, "Read the full Privacy Policy regarding use of eduID at the Sunet website, where you also find contact information to our Dataskyddsombud and Integritetsskyddsmyndigheten (in Swedish).");
        common.verifyPageBodyContainsString(pageBody, "Summary of how eduID treats your information according to the policy:");
        common.verifyPageBodyContainsString(pageBody, "stores information that you have provided as well as updates from trusted registers,\ntransfers information according to the data minimisation principle - never more than required,\nuses the information to identify the individual for services you have chosen to use,\nprotects and stores the information securely,\ndevelops using open source code accessible at GitHub,\nenables removal of eduID and connections directly in the service,\nstores log files recording use for six months,\nretains inactive accounts for a maximum of two years,\nonly uses necessary functional cookies.");
        common.verifyPageBodyContainsString(pageBody, "What is eduIDs Accessibility report?");
        common.verifyPageBodyContainsString(pageBody, "Read the full Accessibility Report regarding the eduID site at Sunets website, where you also find instructions on how to report accessibility issues. The report addresses how eduID adheres to the Swedish law governing accessibility to digital public services as well as currently known issues of the site (in Swedish).");
        common.verifyPageBodyContainsString(pageBody, "It is of outmost importance to us that as many as possible are able to use the service in a convenient and safe manner and one of the many ways eduID is always striving to improve.");
    }

    private void aboutSunetEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "About SUNET");
        common.verifyPageBodyContainsString(pageBody, "What is SUNET?");
        common.verifyPageBodyContainsString(pageBody, "eduID is a service provided by SUNET - the Swedish University Computer Network, which is governed by the Swedish Research Council (Vetenskapsrådet). SUNET delivers data communication networks and many other related services to public organisations and higher education and research institutions.");
        common.verifyPageBodyContainsString(pageBody, "SUNET developed eduID to provide a secure common routine for managing identity in the higher education community, with adequate authorization levels of confirmed accounts. More information about SUNET is available at www.sunet.se (in Swedish).");
    }

    private void contactEduIdEng(String pageBody) {
        common.verifyPageBodyContainsString(pageBody, "Contacting eduID support");
        common.verifyPageBodyContainsString(pageBody, "How can I contact eduID support?");
        common.verifyPageBodyContainsString(pageBody, "If you can't find the answers to your questions about eduID on this help page, you can contact the eduID support by mailing support@eduid.se.");
        common.verifyPageBodyContainsString(pageBody, "Always let us know the email address you used when you logged in to eduID, and if you are logged in include your ‘eppn’ unique ID as presented in the logged in start page.\n" +
                "Don't include confidential or sensitive information such as your personal identity number in the email!\n" +
                "If something went wrong, it is always a good idea to include screenshots with error messages to ease troubleshooting.");
        common.verifyPageBodyContainsString(pageBody, "In order to get best possible support, we recommend that you send email, but for simple matters you can also reach us on phone number 08-555 213 62");
        common.verifyPageBodyContainsString(pageBody, "Opening hours:");
    }

    public void expandAllMenusAndSubmenus() {
        boolean foundClosedMenu = true;

        // Fortsätt loopa så länge vi hittar menyer som går att öppna
        while (foundClosedMenu) {
            // Hitta alla <summary>-taggar på sidan (både för huvud- och undermenyer)
            By summarySelector = By.cssSelector("summary");
            List<WebElement> summaries = common.getWebDriver().findElements(summarySelector);

            foundClosedMenu = false; // Anta att alla är öppna tills motsatsen bevisats

            for (WebElement summary : summaries) {
                try {
                    // Hitta den direkt överliggande <details>-taggen för denna specifika meny
                    WebElement parentSection = summary.findElement(By.xpath("./ancestor::details[1]"));

                    // Om menyn är stängd (attributet "open" saknas)
                    if (parentSection.getDomAttribute("open") == null) {

                        // Kolla om det finns en klickbar <div> inuti summary (som i undermenyn)
                        List<WebElement> innerDivs = summary.findElements(By.cssSelector("div"));
                        WebElement elementToClick = innerDivs.isEmpty() ? summary : innerDivs.get(0);

                        common.timeoutMilliSeconds(100);
                        elementToClick.click();
                        common.timeoutMilliSeconds(100);

                        // Vi öppnade en meny! Starta om loopen för att hitta de nya undermenyer som nu blev synliga
                        foundClosedMenu = true;
                        break;
                    }
                } catch (Exception e) {
                    // Ignorera eventuella StaleElement- eller synlighetsfel och gå vidare
                }
            }
        }
    }
}
