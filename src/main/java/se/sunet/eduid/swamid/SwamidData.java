package se.sunet.eduid.swamid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.util.List;


public class SwamidData {
    private final Common common;
    private final TestData testData;

    public SwamidData(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runSwamidData(boolean confirmedUser){
        verifyUserData(confirmedUser);
        verifyIdpAttributes();
        verifyIdpSessionAttributes();
    }

    private void verifyUserData(boolean confirmedUser){
        //First click on show attributes
//        common.explicitWaitClickableElement("//div/div[4]/div[1]/div[1]/div/a/button");
        common.click(common.findWebElementByXpath("//div/div[4]/div[1]/div[1]/div/a/button"));

        //Extract all table rows in to a list of web elements
        WebElement elementName = common.findWebElementByXpath("//div/div/table/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        if(confirmedUser) {
            Assert.assertEquals(rows.size(), 12, "Number of rows in user data table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 12, now its " + rows.size());


            common.verifyStringByXpath("//*[@id=\"attributes\"]/h3[2]", "Result for (https://idp.dev.eduid.se/idp.xml)");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[2]/td", "se");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[3]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[4]/td", "Sweden");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[5]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[6]/td", "http://www.swamid.se/policy/assurance/al1");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[7]/td", testData.getEppn() +"@dev.eduid.se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[8]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://release-check.swamid.se/shibboleth!793b01eb3e93dfb410f8a4e7a7e4478a87a1e2feb01b2a6ba73b65945eafe266");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[9]/td", (testData.getEppn() +"@dev.eduid.se").replace("-", ""));
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[10]/td", testData.getGivenName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[11]/td", testData.getEmail());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[12]/td", testData.getSurName());
        }
        else{
            Assert.assertEquals(rows.size(), 8, "Number of rows in user data table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 8, now its " + rows.size());

            common.verifyStringByXpath("//*[@id=\"attributes\"]/h3[2]", "Result for (https://idp.dev.eduid.se/idp.xml)");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[2]/td", "se");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[3]/td", "Sweden");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[4]/td", "http://www.swamid.se/policy/assurance/al1");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[5]/td", testData.getEppn() +"@dev.eduid.se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[6]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://release-check.swamid.se/shibboleth!");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[7]/td", (testData.getEppn() +"@dev.eduid.se").replace("-", ""));
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[8]/td", testData.getEmail());
        }
    }

    private void verifyIdpAttributes(){
        //Extract all table rows in to a list of webelements
        WebElement elementName = common.findWebElementByXpath("//*[@id=\"attributes\"]/table[2]/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        Assert.assertEquals(rows.size(), 12, "Number of rows in IDP attribute data table has changed, i.e eduID has " +
                "release more attributes that it should. Should be 12, now its " + rows.size());


        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[2]/td",
                "http://www.swamid.se/policy/assurance/al1\n" +
                "http://www.swamid.se/policy/assurance/al2\n" +
                "http://www.swamid.se/policy/authentication/swamid-al2-mfa\n" +
                "http://www.swamid.se/policy/authentication/swamid-al2-mfa-hi");

        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[3]/td",
                "http://refeds.org/category/research-and-scholarship\n" +
                "http://www.geant.net/uri/dataprotection-code-of-conduct/v1");

        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[4]/td/a",
                "https://error.swamid.se/?errorurl_code=ERRORURL_CODE&errorurl_ts=ERRORURL_TS" +
                        "&errorurl_rp=ERRORURL_RP&errorurl_tid=ERRORURL_TID&errorurl_ctx=ERRORURL_CTX&entityid=https://idp.dev.eduid.se/idp.xml");

        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[5]/td", "");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[6]/td", "");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[7]/td", "");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[8]/td", "http://www.eduid.se/");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[9]/td", "");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[10]/td", "mailto:feedback+swamidtesting@eduid.se");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[11]/td", "mailto:eduid-dev@SEGATE.SUNET.SE");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[12]/td", "");
    }

    private void verifyIdpSessionAttributes(){
        //Extract all table rows in to a list of webelements
        WebElement elementName = common.findWebElementByXpath("//*[@id=\"attributes\"]/table[3]/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        Assert.assertEquals(rows.size(), 5, "Number of rows in IDP session data table has changed, i.e eduID has " +
                "release more attributes that it should. Should be 5, now its " + rows.size());

        //IDP provider
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[2]/td",
                "https://idp.dev.eduid.se/idp.xml");

        //Authentication instant
        common.verifyStringNotEmptyByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[3]/td",
                "//*[@id=\"attributes\"]/table[3]/tbody/tr[3]/th");

        //Authentication method
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[4]/td",
                "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

        //Authentication class
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[5]/td",
                "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
    }
}
