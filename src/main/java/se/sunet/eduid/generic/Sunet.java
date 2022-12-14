package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class Sunet {
    private final Common common;
    private final TestData testData;

    public Sunet(Common common, TestData testData){
        this.testData = testData;
        this.common = common;
    }

    public void runLogin(){
        verifyPageTitle();
        verifyPageBody();
    }

    private void verifyPageTitle(){
        common.verifyPageTitle("eduID | Sunet");
    }

    private void verifyPageBody(){
        common.verifyStringByXpath("//*[@id=\"primary-content\"]/div/div/main/article/div/p[1]",
                "eduID är en digital identitet för organisationer inom utbildning och forskning. Med eduID kan studenter och anställda vid anslutna lärosäten snabbt skaffa ett lokalt IT-konto och därigenom komma åt sina lokala IT-resurser. En eduID-identitet kan användas före, under och efter studietiden.");

        common.verifyStringByXpath("//*[@id=\"primary-content\"]/div/div/main/article/div/p[2]",
                "Skapa en eduID-identitet via eduid.se och anpassa den till den säkerhetsnivå som krävs av tjänsten där identiteten ska användas. Det finns flera nivåer och kombinationer för olika behov, från låg säkerhetsnivå där bekräftelse av e-postadress räcker, till mycket hög säkerhetsnivå via e-legitimation med genomförd legitimationskontroll.");

        //TODO continue ...common.verifyStringByXpath("");
    }


    private String textOnSunetPage(){
        String sunetText = "Till innehållet\n" +
                "Till startsidan\n" +
                "Om Sunet\n" +
                "Tjänster\n" +
                "Kontakt\n" +
                "Driftstatus\n" +
                "Wiki\n" +
                "Fresia Pérez\n" +
                "KONTAKTANSVARIG FÖR EDUID\n" +
                "fresia@sunet.se\n" +
                "eduID\n" +
                "eduID är en digital identitet för organisationer inom utbildning och forskning. Med eduID kan studenter och anställda vid anslutna lärosäten snabbt skaffa ett lokalt IT-konto och därigenom komma åt sina lokala IT-resurser. En eduID-identitet kan användas före, under och efter studietiden.\n" +
                "Så fungerar tjänsten\n" +
                "Skapa en eduID-identitet via eduid.se och anpassa den till den säkerhetsnivå som krävs av tjänsten där identiteten ska användas. Det finns flera nivåer och kombinationer för olika behov, från låg säkerhetsnivå där bekräftelse av e-postadress räcker, till mycket hög säkerhetsnivå via e-legitimation med genomförd legitimationskontroll.\n" +
                "Använd eduID för att:\n" +
                "få tillgång till anslutna lärosätens lokala it-konton och därigenom till lokala IT-resurser\n" +
                "återställa lösenord för anslutna lärosätens lokala it-konton\n" +
                "använda multifaktorinloggning i de system som kräver hög inloggningssäkerhet\n" +
                "ansöka och bekräfta kurser och program hos antagning.se\n" +
                "Dessutom kan systemägare använda eduID som gästinloggning, vilket eliminerar behovet av lokala konton.\n" +
                "Syftet med eduID är att erbjuda framförallt lärosäten gemensamma rutiner för identitetshantering. Med eduID behöver organisationen inte investera i lokal hantering för e-konton. Tjänsten garanterar säker mobilanpassad inloggning och skapar väl identifierade och autenticerade användare.\n" +
                "eduID bygger på öppen källkod. Källkoden finns här\n" +
                "Vad krävs för att få tillgång till tjänsten?\n" +
                "eduID riktar sig framförallt till organisationer som är kopplade till utbildning och forskning och ingår i anslutningsavgiften till Sunet. Alla tjänster som är anslutna till SWAMID kan använda eduID direkt.\n" +
                "Användarstöd\n" +
                "eduID-supporten nås via support@eduid.se. Ange den e-postadress som du använder när du loggar in på eduID när du kontaktar supporten. Vid fel, skicka gärna med skärmdumpar med felmeddelanden för att underlätta felsökning.\n" +
                "Vad kostar det?\n" +
                "EduID ingår i avgiften till Sunet och kostar inget extra för studenter och anställda vid lärosäten som är anslutna till Sunet.\n" +
                "Vill du veta mer?\n" +
                "Har du behov av digital identitet och vill veta om och hur eduID kan användas och anpassas till din organisation, kontakta gärna tjänsteansvarig Fresia Pérez.\n" +
                "Om webbplatsen\n" +
                "Kontakt\n" +
                "Besöksadress: Tulegatan 11, Stockholm\n" +
                "\n" +
                "\n" +
                "Faktureringsadress: Vetenskapsrådet, FE 57 838 73 Frösön. (Ange referenskod 0263)\n" +
                "\n" +
                "Sunet är en del av Vetenskapsrådet\n";

        return sunetText;
    }
}
