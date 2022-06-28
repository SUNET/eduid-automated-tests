package se.sunet.eduid;

import org.testng.annotations.Test;
import se.sunet.eduid.utils.BeforeAndAfter;

public class TC_59 extends BeforeAndAfter {
    @Test
    void selectAboutSunet(){
        common.click(common.findWebElementByXpath("//section[2]/div/p[4]/a")); }

    @Test( dependsOnMethods = {"selectAboutSunet"} )
    void verifyAboutSunet(){
        sunet.runLogin(); }
}
