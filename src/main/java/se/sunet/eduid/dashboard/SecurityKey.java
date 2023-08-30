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
        common.explicitWaitVisibilityElement("//*[@id=\"register-security-key-container\"]/h2");

        //TODO temp fix to get swedish language
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska"))
            common.selectSwedish();
    }

    //Not used any more, keeping code for example
    private void virtualAuthenticator(){
        //Add cookie for back doors
        if(!common.isCookieSet("autotests"))
            common.addMagicCookie();

        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
        options.setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasUserVerification(true)
                .setIsUserVerified(true);

        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) common.getWebDriver()).addVirtualAuthenticator(options);
        //authenticator.setUserVerified(true);
    }

    private void addSecurityKey(){
        //Click on add security key and verify labels
        verifyAddSecurityKeyLabels();

        //Add a security key
        common.click(common.findWebElementByXpath("//*[@id=\"security-webauthn-button\"]"));
        common.timeoutMilliSeconds(500);
        common.switchToPopUpWindow();

        //Enter name of key and click OK
        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").sendKeys("test-key1");
        common.click(common.findWebElementByXpath("//*[@id=\"describe-webauthn-token-modal-form\"]/div[2]/button"));
        common.timeoutMilliSeconds(500);

        //Verify that without personal info added, no extra key can be added.
        if(!testData.isAddSecurityKey()) {
            common.verifyStatusMessage("Du behöver lägga till ditt namn i Inställningar innan du kan lägga till en säkerhetsnyckel");

            //Close the status message
            common.closeStatusMessage();
        }
        //Verify that extra key can be added.
        else if(testData.isAddSecurityKey()) {
            //Verify headings
            verifySecurityKeyHeaders();

            //Try to remove the "last" key
            //common.click(common.findWebElementByXpath("//*[@id=\"register-webauthn-tokens-area\"]/table/tbody/tr[2]/td[5]/button"));
            //common.timeoutMilliSeconds(500);
            //common.verifyStatusMessage("Du kan inte ta bort din enda säkerhetsnyckel");
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
        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Ge ett namn till din säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
        common.verifyStrings("Beskriv din säkerhetsnyckel",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //This option with using your device is not visible in firefox
        try {
            if (common.findWebElementById("security-webauthn-platform-button").isDisplayed()) {

                //Verify extra security on This device labels
                common.click(common.findWebElementById("security-webauthn-platform-button"));
                common.timeoutMilliSeconds(500);

                //Labels and placeholder
                common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Ge ett namn till din säkerhetsnyckel");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Säkerhetsnyckel");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 tecken");
                common.verifyStrings("Beskriv din säkerhetsnyckel",
                        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

                //Close pop up
                common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
                common.timeoutMilliSeconds(500);
            }
        }catch (Exception ex) {
            Common.log.info("Use this device as extra security not visible");
        }


        //English
        common.selectEnglish();

        //Verify Security key
        common.click(common.findWebElementById("security-webauthn-button"));
        common.timeoutMilliSeconds(500);

        //Labels and placeholder
        common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Add a name for your security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Security key");
        common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 characters");
        common.verifyStrings("Describe your security key",
                common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

        //Close pop up
        common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));

        //This option with using your device is not visible in firefox
        try{
            if(common.findWebElementById("security-webauthn-platform-button").isDisplayed()){

                //Verify extra security on This device labels
                common.click(common.findWebElementById("security-webauthn-platform-button"));
                common.timeoutMilliSeconds(500);

                //Labels and placeholder
                common.verifyStringByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div/h5", "Add a name for your security key");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/label", "Security key");
                common.verifyStringByXpath("//*[@id=\"describe-webauthn-token-modal-wrapper\"]/div/span", "max 50 characters");
                common.verifyStrings("Describe your security key",
                        common.findWebElementByXpath("//div[2]/div/div[1]/div/div/form/div[1]/div/div/input").getAttribute("placeholder"));

                //Close pop up
                common.click(common.findWebElementByXpath("//*[@id=\"confirm-user-data-modal\"]/div/div[1]/h5/button"));
            }
        }catch (Exception ex){
            common.log.info("Use this device as extra security not visible");
        }

        //English
        common.selectSwedish();
    }
}
