package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_99 extends BeforeAndAfter {
    @Test
    void helpPage(){ help.runHelp(); }

    @Test( dependsOnMethods = {"helpPage"} )
    void navigateToHelpPageDirectLinkQA(){ common.navigateToUrl(testData.getBaseUrl() + "/faq.html"); }

    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkQA"} )
    void helpPageDirectLinkQA(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }

    @Test( dependsOnMethods = {"helpPageDirectLinkQA"} ) //Note! Presented language is default browser language
    void navigateToHelpPageDirectLinkQAEng(){ common.navigateToUrl(testData.getBaseUrl() + "/en/faq.html"); }

    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkQAEng"} )
    void helpPageDirectLinkQAEng(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }

    @Test( dependsOnMethods = {"helpPageDirectLinkQAEng"} ) //Note! Presented language is default browser language
    void navigateToHelpPageDirectLinkNewUrlQA(){ common.navigateToUrl(testData.getBaseUrl() + "/help"); }

    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkNewUrlQA"} )
    void helpPageDirectLinkNewUrlQA(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }

    @Test( dependsOnMethods = {"helpPageDirectLinkNewUrlQA"} )
    void navigateToHelpPageDirectLinkProd(){ common.navigateToUrl("https://eduid.se/faq.html"); }


    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkProd"} )
    void helpPageDirectLinkProd(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }

    @Test( dependsOnMethods = {"helpPageDirectLinkProd"} ) //Note! Presented language is default browser language
    void navigateToHelpPageDirectLinkProdEng(){ common.navigateToUrl("https://eduid.se/en/faq.html"); }

    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkProdEng"} )
    void helpPageDirectLinkProdEng(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }

    @Test( dependsOnMethods = {"helpPageDirectLinkProdEng"} ) //Note! Presented language is default browser language
    void navigateToHelpPageDirectLinkNewUrlProd(){ common.navigateToUrl("https://eduid.se/help"); }

    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkNewUrlProd"} )
    void helpPageDirectLinkNewUrlProd(){
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }
}
