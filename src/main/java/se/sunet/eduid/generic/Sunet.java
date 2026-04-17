package se.sunet.eduid.generic;

import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.generic.SunetPageLocators.*;

/**
 * Page object for the Sunet external page describing eduID.
 *
 * Note: textOnSunetPage() has been removed — it was a dead private method
 * (never called) containing a raw wall of hardcoded text. If full-body
 * verification is needed in the future, drive it through verifyPageBody()
 * using focused XPath assertions, not a whole-page string dump.
 */
public class Sunet {

    private final Common   common;
    private final TestData testData;

    public Sunet(Common common, TestData testData) {
        this.common   = common;
        this.testData = testData;
    }

    public void runSunetPage() {
        verifyPageTitle();
        verifyPageBody();
    }

    private void verifyPageTitle() {
        common.verifyPageTitle("eduID | Sunet");
    }

    private void verifyPageBody() {
        common.verifyString(PARAGRAPH_1,
                "eduID är en digital identitet för organisationer inom utbildning och forskning. " +
                "Med eduID kan studenter och anställda vid anslutna lärosäten snabbt skaffa ett lokalt " +
                "IT-konto och därigenom komma åt sina lokala IT-resurser. En eduID-identitet kan användas " +
                "före, under och efter studietiden.");

        common.verifyString(PARAGRAPH_2,
                "Skapa en eduID-identitet via eduid.se och anpassa den till den säkerhetsnivå som krävs " +
                "av tjänsten där identiteten ska användas. Det finns flera nivåer och kombinationer för " +
                "olika behov, från låg säkerhetsnivå där bekräftelse av e-postadress räcker, till mycket " +
                "hög säkerhetsnivå via e-legitimation med genomförd legitimationskontroll.");

        // TODO: add further paragraph assertions as content is confirmed stable
    }
}
