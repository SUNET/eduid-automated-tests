package se.sunet.eduid.generic;

import io.github.bonigarcia.wdm.WebDriverManager;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class LoginOtherDevice {
    private final Common common;
    private final TestData testData;

    public LoginOtherDevice(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runLoginOtherDevice(){
        selectLoginOtherDevice();
        checkLabels();
        if(testData.isOtherDeviceFillCode())
            enterCode("1", "2", "3", "4", "5", "6");
        submitCode();
    }

    private void selectLoginOtherDevice() {
        common.click(common.findWebElementById("login-other-device-button"));

        //wait for cancel button at next page
        if(testData.isRememberMe())
            common.explicitWaitClickableElementId("response-code-cancel-button");
        else
            common.explicitWaitClickableElementId("response-code-abort-button");

    }

    public void checkLabels(){
        //Swedish
        if(common.findWebElementById("language-selector").getText().equalsIgnoreCase("svenska"))
            common.selectSwedish();
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Logga in med hjälp av en annan enhet");
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]", "Skanna QR-koden med din andra enhet");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/ol/li[2]", "Logga in på den andra enheten");

        if(testData.isRememberMe()) {
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[3]", "Klicka på " +
                    "\"fortsätt\" här när du har loggat in på den andra enheten");
        }
        else{
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[3]", "Skriv " +
                    "in den sexsiffriga svarskoden som visas i den andra enheten i fältet nedan");
        }


        //English
        common.selectEnglish();
        common.verifyStringByXpath("//*[@id=\"content\"]/div/h1", "Log in using another device");
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]", "Scan this QR-code with your other device");
        common.verifyStringByXpath("//*[@id=\"content\"]/div/ol/li[2]", "Log in on the other device");

        if(testData.isRememberMe()) {
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[3]", "Click " +
                    "\"continue\" once you have logged in on the other device");
        }
        else{
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[3]", "Enter " +
                    "the six-digit response code shown on the other device in the form below");
        }
    }

    public void enterCode(String code1, String code2, String code3, String code4, String code5, String code6) {
        //Enter faulty code
        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[1]")
                .sendKeys(code1);
        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[2]")
                .sendKeys(code2);
        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[3]")
                .sendKeys(code3);

        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[4]")
                .sendKeys(code4);
        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[5]")
                .sendKeys(code5);
        common.findWebElementByXpath("//*[@id=\"response-code-form\"]/div[1]/input[6]")
                .sendKeys(code6);
    }

    public void submitCode(){
        if(testData.getOtherDeviceSubmitCode().equalsIgnoreCase("true")) {
            if(testData.isRememberMe()) {
                common.click(common.findWebElementById("response-code-continue-button"));
            }
            //Submit code
//            else
//                common.click(common.findWebElementById("response-code-submit-button"));
        }
        else if(testData.getOtherDeviceSubmitCode().equalsIgnoreCase("false")){
            //Press cancel
            if(testData.isRememberMe()) {
                common.timeoutSeconds(2);
                common.logPageBody();
                common.click(common.findWebElementById("response-code-cancel-button"));
            }
            else
                common.click(common.findWebElementById("response-code-abort-button"));
        }
    }

    public void verifyConfirmLoginLabels() {
        //Swedish
        if(testData.isRememberMe()){
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]",
                    "Du loggar in som " + testData.getDisplayName() +" (" +testData.getUsername() +") på den andra enheten");
        }
        else {
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]",
                    "Notera att du använder den här enheten för att logga in på enheten nedan");
            common.verifyStringOnPage("IP adress");
            common.verifyStringOnPage("Beskrivning");
        }
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[2]", "Logga in den här enheten");
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]/figure/figcaption", "ID# ");

        //Switch to english
        common.selectEnglish();

        //English
        if(testData.isRememberMe()){
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]",
                    "You are logging in as " + testData.getDisplayName() +" (" +testData.getUsername() +") on the other device");
        }
        else {
            common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]", "Note that you " +
                    "are using this device to log in on the device below");
            common.verifyStringOnPage("IP address");
            common.verifyStringOnPage("Description");
        }
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[2]", "Log in this device");
        common.verifyXpathContainsString("//*[@id=\"content\"]/div/ol/li[1]/figure/figcaption", "ID# ");
    }

    public void clickLoginShortcut() {
        common.navigateToUrlNewWindow(common.findWebElementByXpath("//*[@id=\"content\"]/div/div/span[2]/a").getText());

        //switch to new window
        common.switchToPopUpWindow();

        //wait for Proceed button at next page
        common.explicitWaitClickableElementId("proceed-other-device-button");
    }

    public void extractCode(){
        testData.setOtherDeviceCode(common.findWebElementById("response_code").getText());
        log.info("Code stored: " +testData.getOtherDeviceCode());
    }
}