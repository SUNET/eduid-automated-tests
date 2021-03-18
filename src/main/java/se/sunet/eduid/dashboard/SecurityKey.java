package se.sunet.eduid.dashboard;

import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import se.sunet.eduid.generic.Login;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.WebDriverManager;
import java.time.LocalDateTime;

public class SecurityKey {
    private Common common;
    private LocalDateTime date = LocalDateTime.now();


    public SecurityKey(Common common){
        this.common = common;
    }


    public void runSecurityKey(){
        pressAdvancedSettings();

        //If we shall add extra security key
        if(common.getAddSecurityKey())
            virtualAuthenticator();

        addSecurityKey();
    }

    private void pressAdvancedSettings(){
        common.findWebElementByXpath("//*[@id=\"dashboard-nav\"]/ul/a[4]/li/span").click();

        //Wait for heading "Gör ditt eduID säkrare"
        common.explicitWaitVisibilityElement("//*[@id=\"register-securitykey-container\"]/div[1]/h4/span");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/p['non-selected']/a").getText().contains("Svenska"))
            common.findWebElementByLinkText("Svenska").click();
    }

    private void virtualAuthenticator(){
        VirtualAuthenticator authenticator = null;
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);
        authenticator =
                ((HasVirtualAuthenticator) WebDriverManager.getWebDriver()).addVirtualAuthenticator(options);

    }

    private void addSecurityKey(){
        //Click on add security key
        common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]/span").click();
        common.timeoutMilliSeconds(500);
        common.switchToPopUpWindow();

        //Enter name of key and click OK
        common.findWebElementByXpath("//*[@id=\"describeWebauthnTokenDialogControl\"]/input").sendKeys("test-key1");
        common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button/span").click();
        common.timeoutMilliSeconds(500);

        //Verify that without personal info added, no extra key can be added.
        common.explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        if(!common.getAddSecurityKey()) {
            common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span",
                    "Du måste lägga till personlig data innan du kan lägga till en säkerhetsnyckel");

            //Close the status message
            common.findWebElementByXpath("//*[@id=\"panel\"]/div[1]/div/button/span").click();
        }
        //Verify that extra key can be added.
        else{
            common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span",
                    "Säkerhetsnyckel registrerad");
            common.timeoutMilliSeconds(500);

            //Verify headings
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[1]/span", "Namn");
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[2]/span", "Skapad den");
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[3]/span", "Senast använd");

            //verify data
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[1]", "test-key1");
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[2]", date.toLocalDate().toString());
            common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[3]", "Aldrig använd");

            //Try do remove the "last" key
            common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[5]/button").click();
            common.timeoutMilliSeconds(500);
            common.verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", "Du kan inte ta bort din enda säkerhetsnyckel");

            //Verify the added key, after a new login.
            common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[4]/button/span").click();

            //Enter username, password
            Login login = new Login(common);
            login.enterUsernamePassword();

            //Click log in button
            common.findWebElementByXpath("//*[@id=\"content\"]/div/div/form/fieldset/div[2]/div[3]/span[1]/button").click();

            //Verify that Freja Login page is opened after verification
            common.explicitWaitPageTitle("Freja eID IDP");
            common.verifyPageTitle("Freja eID IDP");
        }
    }
}
