package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_59 extends BeforeAndAfter {
    @Test
    void selectAboutSunet(){
        common.click(common.findWebElementByXpath("//*[@id=\"content\"]/div/p[2]/a")); }

    @Test( dependsOnMethods = {"selectAboutSunet"} )
    void verifyAboutSunet(){
        sunet.runLogin(); }
}
