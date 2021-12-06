package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_35 extends BeforeAndAfter {
    @Test
    void startPage(){
        testData.setRegisterAccount(true);
        startPage.runStartPage(); }

    @Test( dependsOnMethods = {"startPage"} )
    void register(){
        testData.setAcceptTerms(false);
        register.runRegister(); }
}
