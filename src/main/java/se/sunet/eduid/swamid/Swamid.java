package se.sunet.eduid.swamid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;

import java.util.List;


public class Swamid {
    private Common common;

    public Swamid(Common common){
        this.common = common;
    }

    public void runSwamid(boolean confirmedUser){
        navigateEduId();
        login();
        verifyUserData(confirmedUser);
        logout();
    }

    private void navigateEduId(){
        common.findWebElementById("searchinput").sendKeys("eduid");
        common.timeoutSeconds(1);
        common.findWebElementByXpath("//*[@id=\"ds-search-list\"]/div[1]").click();

        //Wait for the eduID log in page to load
        common.explicitWaitPageTitle("eduID-inloggning");
    }

    private void login(){
        Login login = new Login(common);
        login.enterUsernamePassword();

        common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();

        //Wait for the user data table to be presented
        common.explicitWaitVisibilityElement("//div/div/div[2]/p");
    }

    private void verifyUserData(boolean confirmedUser){
        //Extract all table rows in to a list of webelements
        WebElement elementName = common.findWebElementByXpath("//div/div/table/tbody");
        List<WebElement> rows = elementName.findElements(By.xpath("*"));

        if(confirmedUser) {
            Assert.assertEquals(rows.size(), 8, "Number of row in table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 8, now its " + rows.size());


            common.verifyStringByXpath("//div/div/div[2]/p", "These attributes were send from the Identity Provider (https://idp.dev.eduid.se/idp.xml). The 'eppn' attribute if present is often used as a permanent identifier for you.");
            common.verifyStringByXpath("//div/div/table/tbody/tr[1]/td", "http://www.swamid.se/policy/assurance/al1");
            common.verifyStringByXpath("//div/div/table/tbody/tr[2]/td", common.getDisplayName());
            common.verifyStringByXpath("//div/div/table/tbody/tr[3]/td", common.getDisplayName());
            common.verifyStringByXpath("//div/div/table/tbody/tr[4]/td", common.getEppn() +"@dev.eduid.se");
            common.verifyStringByXpath("//div/div/table/tbody/tr[5]/td", common.getGivenName());
            common.verifyStringByXpath("//div/div/table/tbody/tr[6]/td", common.getEmail());
            common.verifyXpathContainsString("//div/div/table/tbody/tr[7]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://sp.swamid.se/shibboleth!1640e2134cb233dfe23534dc62bbe2ab105e39225e5f926c0ca5334aa3f7440c");
            common.verifyStringByXpath("//div/div/table/tbody/tr[8]/td", common.getSurName());
        }
        else{
            Assert.assertEquals(rows.size(), 4, "Number of row in table has changed, i.e eduID has " +
                    "release more attributes that it should. Should be 4, now its " + rows.size());

            common.verifyStringByXpath("//div/div/table/tbody/tr[1]/td", "http://www.swamid.se/policy/assurance/al1");
            common.verifyStringByXpath("//div/div/table/tbody/tr[2]/td", common.getEppn() +"@dev.eduid.se");
            common.verifyStringByXpath("//div/div/table/tbody/tr[3]/td", common.getEmail());
            common.verifyXpathContainsString("//div/div/table/tbody/tr[4]/td",
                    "https://idp.dev.eduid.se/idp.xml!https://sp.swamid.se/shibboleth!");
        }
    }

    private void logout(){
        common.findWebElementByLinkText("Logout").click();

        common.verifyStringOnPage("Status of Global Logout: Logout completed successfully.");
    }
}
