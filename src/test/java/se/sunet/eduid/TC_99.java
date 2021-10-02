package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_99 extends BeforeAndAfter {

    @Test
    void startPage(){
        common.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void helpPage(){ help.runHelp(); }
}
