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
        common.timeoutSeconds(1);
        verifyUserData(confirmedUser);
        verifyIdpSessionAttributes();
    }

    private void verifyUserData(boolean confirmedUser){
        //First click on show attributes
        common.click(common.findWebElementByXpath("//div/div[4]/div[1]/div[1]/div/a/button"));

        //Extract all table rows in to a list of web elements
        WebElement elementName = common.findWebElementByXpath("//div/div/table/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        if(confirmedUser && testData.getMfaMethod().isEmpty()) {
            Assert.assertEquals(rows.size(), 14, "Number of rows in user data table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 14, now its " + rows.size());

            common.verifyStringByXpath("//*[@id=\"attributes\"]/h3[2]", "Result for eduID staging (https://idp.dev.eduid.se/idp.xml)");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[2]/td", "se");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[3]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[4]/td", "Sweden");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[5]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[6]/td",
                    "http://www.swamid.se/policy/assurance/al1\n" +
                            "https://refeds.org/assurance\n" +
                            "https://refeds.org/assurance/ID/unique\n" +
                            "https://refeds.org/assurance/ID/eppn-unique-no-reassign\n" +
                            "https://refeds.org/assurance/IAP/low");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[7]/td", testData.getEppn() +"@dev.eduid.se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[8]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://release-check.qa.swamid.se/shibboleth!");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[9]/td", testData.getGivenName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[10]/td", testData.getEmail());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[11]/td", testData.getEmail());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[12]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[13]/td", testData.getSurName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[14]/td", testData.getEppn() +"@dev.eduid.se");
        }
        else if(!testData.getMfaMethod().isEmpty()) {
            //Given name and Surname will always be Magic Cookie Testsson when user is confirmed using some magic
            testData.setGivenName("Cookie");
            testData.setSurName("Testsson");
            testData.setDisplayName("Cookie Testsson");

            Assert.assertEquals(rows.size(), 17, "Number of rows in user data table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 17, now its " + rows.size());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/h3[2]", "Result for eduID staging (https://idp.dev.eduid.se/idp.xml)");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[2]/td", "se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[3]/td", testData.getGivenName() + " " +testData.getSurName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[4]/td", "Sweden");
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[5]/td").getText(), testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[6]/td",
                    "http://www.swamid.se/policy/assurance/al1\n" +
                            "http://www.swamid.se/policy/assurance/al2\n" +
                            //"http://www.swamid.se/policy/assurance/al3\n" +
                            "https://refeds.org/assurance\n" +
                            "https://refeds.org/assurance/profile/cappuccino\n" +
                            //"https://refeds.org/assurance/profile/espresso\n" +
                            "https://refeds.org/assurance/ID/unique\n" +
                            "https://refeds.org/assurance/ID/eppn-unique-no-reassign\n" +
                            "https://refeds.org/assurance/IAP/low\n" +
                            "https://refeds.org/assurance/IAP/medium");
                            //"https://refeds.org/assurance/IAP/high");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[7]/td", testData.getEppn() +"@dev.eduid.se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[8]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://release-check.qa.swamid.se/shibboleth!");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[9]/td", testData.getGivenName());
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[10]/td", testData.getUsername());
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[11]/td", testData.getUsername());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[12]/td", "Magic Cookie Testsson");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[13]/td", testData.getIdentityNumber());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[14]/td", testData.getIdentityNumber());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[15]/td", testData.getIdentityNumber().substring(0,8));
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[16]/td", testData.getSurName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[17]/td", testData.getEppn() +"@dev.eduid.se");
        }
        else{
            Assert.assertEquals(rows.size(), 13, "Number of rows in user data table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 13, now its " + rows.size());

            common.verifyStringByXpath("//*[@id=\"attributes\"]/h3[2]", "Result for eduID staging (https://idp.dev.eduid.se/idp.xml)");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[2]/td", "se");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[3]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[4]/td", "Sweden");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[5]/td", testData.getDisplayName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[6]/td", "http://www.swamid.se/policy/assurance/al1\n" +
                    "https://refeds.org/assurance\n" +
                    "https://refeds.org/assurance/ID/unique\n" +
                    "https://refeds.org/assurance/ID/eppn-unique-no-reassign\n" +
                    "https://refeds.org/assurance/IAP/low");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[7]/td", testData.getEppn() +"@dev.eduid.se");
            common.verifyXpathContainsString("//*[@id=\"attributes\"]/table[1]/tbody/tr[8]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://release-check.qa.swamid.se/shibboleth!");
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[9]/td", testData.getGivenName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[10]/td", testData.getEmail().toLowerCase());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[11]/td", testData.getEmail().toLowerCase());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[12]/td", testData.getSurName());
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[1]/tbody/tr[13]/td", testData.getEppn() +"@dev.eduid.se");
        }
    }

    private void verifyIdpAttributes(){
        //Extract all table rows in to a list of webelements
        WebElement elementName = common.findWebElementByXpath("//*[@id=\"attributes\"]/table[2]/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        Assert.assertEquals(rows.size(), 12, "Number of rows in IDP attribute data table has changed, i.e eduID has " +
                "release more attributes that it should. Should be 12, now its " + rows.size());

        //Assurance-Certification
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[2]/td",
                "http://www.swamid.se/policy/assurance/al1\n" +
                "http://www.swamid.se/policy/assurance/al2\n" +
                        "http://www.swamid.se/policy/assurance/al3");

        //Entity-Category-Support
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[3]/td",
                "https://myacademicid.org/entity-categories/esi\n" +
                        "http://refeds.org/category/research-and-scholarship\n" +
                        "http://www.geant.net/uri/dataprotection-code-of-conduct/v1\n" +
                        "https://refeds.org/category/code-of-conduct/v2");

        //errorURL
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[4]/td/a",
                "https://dev.eduid.se/errors?code=ERRORURL_CODE&ts=ERRORURL_TS&rp=ERRORURL_RP&tid=ERRORURL_TID&ctx=ERRORURL_CTX");

        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[5]/td", "eduID staging");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[6]/td", "https://www.eduid.se/faq.html");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[7]/td", "");

        //OrganizationURL
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[8]/td", "http://www.eduid.se/");

        //ContactPerson (administrative)
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[9]/td", "mailto:feedback+swamidtesting@eduid.se");

        //ContactPerson (support)
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[10]/td", "mailto:feedback+swamidtesting@eduid.se");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[11]/td", "mailto:feedback+swamidtesting@eduid.se");
        common.verifyStringByXpath("//*[@id=\"attributes\"]/table[2]/tbody/tr[12]/td", "mailto:feedback+swamidtesting@eduid.se");
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

        if(testData.getMfaMethod().isEmpty()){
            //Authentication method
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[4]/td",
                    "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

            //Authentication class
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[5]/td",
                    "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        }
        else if(!testData.getMfaMethod().isEmpty()){
            //Authentication method
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[4]/td",
                    "https://refeds.org/profile/sfa");

            //Authentication class
            common.verifyStringByXpath("//*[@id=\"attributes\"]/table[3]/tbody/tr[5]/td",
                    "https://refeds.org/profile/sfa");
        }
    }
}
