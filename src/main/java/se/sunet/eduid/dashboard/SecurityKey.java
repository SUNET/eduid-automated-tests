package se.sunet.eduid.dashboard;

import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class SecurityKey {
    private final Common common;
    private final TestData testData;

    public SecurityKey(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runSecurityKey(){
        pressAdvancedSettings();

        //If we shall add extra security key
        if(testData.isAddSecurityKey())
            virtualAuthenticator();

        addSecurityKey();
    }

    private void pressAdvancedSettings(){
        common.navigateToAdvancedSettings();

        //Wait for heading "Gör ditt eduID säkrare"
        common.explicitWaitVisibilityElement("//*[@id=\"register-securitykey-container\"]/div[1]/h4");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();
    }

    private void virtualAuthenticator(){
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);

        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);
    }

    private void addSecurityKey(){
        //Click on add security key and verify labels
        verifyAddSecurityKeyLabels();

        //Add a security key
        common.click(common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]"));
        common.timeoutMilliSeconds(500);
        common.switchToPopUpWindow();

        //Enter name of key and click OK
        common.findWebElementById("describeWebauthnTokenDialogControl").sendKeys("test-key1");
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[3]/button"));
        common.timeoutMilliSeconds(500);

        //Verify that without personal info added, no extra key can be added.
        if(!testData.isAddSecurityKey()) {
            common.verifyStatusMessage("Du måste lägga till personlig data innan du kan lägga till en säkerhetsnyckel");

            //Close the status message
            common.closeStatusMessage();
        }
        //Verify that extra key can be added.
        else if(testData.isAddSecurityKey()) {
            //Verify headings
            verifySecurityKeyHeaders();

            //Try to remove the "last" key
            common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[5]/button"));
            common.timeoutMilliSeconds(500);
            common.verifyStatusMessage("Du kan inte ta bort din enda säkerhetsnyckel");
        }
    }

    private void verifySecurityKeyHeaders(){
        //Verify headings
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[1]", "Namn");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[2]", "Skapad den");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[1]/th[3]", "Senast använd");

        //verify data
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[1]", "test-key1");
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[2]", common.getDate().toString());
        common.verifyStringByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[3]", "Aldrig använd");
    }

    private void verifyAddSecurityKeyLabels(){
        common.click(common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describeWebauthnTokenDialogControl-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describeWebauthnTokenDialogControl-wrapper\"]/div/span", "max 50 tecken");
        common.verifyStrings("Beskriv din säkerhetsnyckel", common.findWebElementByXpath(
                "//*[@id=\"describeWebauthnTokenDialogControl\"]").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/div/button"));

        //English
        common.selectEnglish();

        common.click(common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5", "Add a name for your security key");
        common.verifyStringByXpath("//*[@id=\"describeWebauthnTokenDialogControl-wrapper\"]/div/label", "Security key");
        common.verifyStringByXpath("//*[@id=\"describeWebauthnTokenDialogControl-wrapper\"]/div/span", "max 50 characters");
        common.verifyStrings("Describe your security key", common.findWebElementByXpath(
                "//*[@id=\"describeWebauthnTokenDialogControl\"]").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/div/button"));

        //English
        common.selectSwedish();
    }
}
