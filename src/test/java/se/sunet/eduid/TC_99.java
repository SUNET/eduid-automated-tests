package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_99 extends BeforeAndAfter {
    @Test
    void helpPage(){ help.runHelp(); }

    @Test( dependsOnMethods = {"helpPage"} )
    void navigateToHelpPageDirectLink(){ common.navigateToUrl("https://eduid.se/faq.html"); }


    @Test( dependsOnMethods = {"navigateToHelpPageDirectLink"} )
    void helpPageDirectLink(){
        help.expandAllOptions();
        //help.verifySwedish();
        common.verifyStringOnPage("Nedan ser du allmän information om tjänsten, svar på vanliga frågor " +
                "om användandet av eduID och kontaktinformation till supporten.");
    }


//    @Test( dependsOnMethods = {"helpPageDirectLink"} )
    void navigateToHelpPageDirectLinkEng(){ common.navigateToUrl("https://eduid.se/en/faq.html"); }

//    @Test( dependsOnMethods = {"navigateToHelpPageDirectLinkEng"} )
    void helpPageDirectLinkEng(){
        help.expandAllOptions();
        //help.verifyEnglish();
        common.verifyStringOnPage("Listed below is general information about the service, answers to " +
                "common questions about using eduID and Support contact information.");
    }
}
