package se.sunet.eduid.utils;

//import io.appium.java_client.ios.IOSDriver;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

public class Common {

    private final String errorMsg ="Verification failed! ";
    public static final Logger log = LogManager.getLogger(Common.class);
    private final WebDriver webDriver;
    private String firstWinHandle = null;
    private String addNewEmail1 = "";
    private String confirmNewEmail1 = "";
    private String newPassword = "";
    private String username, password, givenName, surName, displayName, magicCode, email, phoneNumber, identityNumber, language, eppn,
    confirmIdBy, supportUsername, emailCode, testCase, testDescription, sendMobileOneTimePassword = "yes", testSuite, mfaMethod;
    private boolean registerAccount, resetPassword, incorrectPassword, removePrimary, removeNewEmail1, resendOTP, deleteButton,
            buttonValuePopup = true, useRecommendedPw, buttonValueConfirm = true,
            generateUsername = true, acceptTerms = true, sendCaptcha = true, addSecurityKey = false;

    public Common(WebDriver webDriver, String testSuite) throws IOException {
        this.webDriver = webDriver;
        setProperties(testSuite);
    }
/*
    public Common(IOSDriver<?> webDriver) throws IOException {
        this.webDriver = webDriver;
        setProperties();
    }
*/
    private String getTitle() {
        int counter = 0;
        while(webDriver.getTitle().isEmpty()){
            timeoutMilliSeconds(20);
            counter ++;
            if (counter > 500) {
                break;
            }
        }

        return webDriver.getTitle();
    }

    public void verifyPageTitle(String pageTitle) {
        Assert.assertEquals(getTitle(), pageTitle);
    }

    public void navigateToUrl(String url){
        webDriver.navigate().to(url);
    }

    public void verifyStringByXpath(String xpath, String stringToCompareWith) {
        Assert.assertEquals(findWebElementByXpath(xpath).getText(), stringToCompareWith, errorMsg);
    }

    public void verifyXpathContainsString(String xpathToBeEval, String stringToCompareWith) {
        Assert.assertTrue(findWebElementByXpath(xpathToBeEval).getText().toLowerCase().contains(stringToCompareWith.toLowerCase()), errorMsg
                    + findWebElementByXpath(xpathToBeEval).getText() + " Does not contain search string: " + stringToCompareWith);
    }

    public void verifyStringContainsXpath(String xpathToCompareWith, String stringToBeEval) {
        Assert.assertTrue(stringToBeEval.contains(findWebElementByXpath(xpathToCompareWith).getText()), errorMsg
                    + findWebElementByXpath(xpathToCompareWith).getText() + " Does not contain search string: " + xpathToCompareWith);
    }

    public void verifyStrings(String stringToCompareWith, String stringToBeEval) {
        Assert.assertTrue(stringToBeEval.contains(stringToCompareWith), errorMsg
                    + stringToBeEval + " Does not contain: " + stringToCompareWith);
    }

    public void verifyStringNotEmptyByXpath(String xpath, String parameterNameXpath) {
        parameterNameXpath = findWebElementByXpath(parameterNameXpath).getText();
        Assert.assertFalse((findWebElementByXpath(xpath).getText()).isEmpty(), errorMsg +parameterNameXpath +" Parameter is empty or missing!");
    }

    public void verifyStringOnPage(String stringToBeVerified){
        timeoutMilliSeconds(100);
        if(!webDriver.findElement(By.tagName("body")).getText().contains(stringToBeVerified)){
            log.warn(errorMsg + stringToBeVerified +" - is missing on web page!");
            Assert.fail(errorMsg + stringToBeVerified +" - is missing on web page!");
        }
    }

    public void logPageBody(){
        timeoutSeconds(1);
        log.info(webDriver.findElement(By.tagName("body")).getText());
    }

    public void timeoutSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void explicitWaitClickableElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitClickableElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElement(String xpathToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToElementToWaitFor)));
    }

    public void explicitWaitVisibilityElementId(String idToElementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idToElementToWaitFor)));
    }

    public void explicitWaitPageTitle(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.titleContains(pageTitle));
    }

    public void timeoutMilliSeconds(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public WebElement findWebElementById(String elementToFind){
        //At this point we do not know if element will be clicked or not
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind));
    }

    public WebElement findWebElementByLinkText(String linkText) {
        return webDriver.findElement(By.linkText(linkText));
    }

    public WebElement findWebElementByXpath(String elementToFind){
        //At this point we do not know if element will be clicked or not
        explicitWaitVisibilityElement(elementToFind);
        return webDriver.findElement(By.xpath(elementToFind));
    }

    public WebElement findWebElementByXpathContainingText(String text){
        return webDriver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }

    public String getAttributeByXpath(String elementToFind){
        explicitWaitVisibilityElement(elementToFind);
        return webDriver.findElement(By.xpath(elementToFind)).getAttribute("value");
    }

    public String getAttributeById(String elementToFind){
        explicitWaitVisibilityElementId(elementToFind);
        return webDriver.findElement(By.id(elementToFind)).getAttribute("value");
    }

    public void click(WebElement element){
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", element);
    }

    public void switchToPopUpWindow(){
        //Get windowhandle
        firstWinHandle = webDriver.getWindowHandle();

        //Switch to the new pop-up window
        for (String winHandle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(winHandle);
        }
    }

    public void verifyStatusMessage(String message){
        //Verify the saved info label
        explicitWaitVisibilityElement("//*[@id=\"panel\"]/div[1]/div/span");
        verifyStringByXpath("//*[@id=\"panel\"]/div[1]/div/span", message);

        //log.info("Status message at page: " +findWebElementByXpath("//*[@id=\"panel\"]/div[1]/div/span").getText());
    }

    public void closeStatusMessage(){
        //Close the status message
        //explicitWaitClickableElement("//div/section[2]/div[1]/div/button/span");
        findWebElementByXpath("//div/section[2]/div[1]/div/button/span").click();
    }

    public void switchToDefaultWindow(){
        webDriver.switchTo().window(firstWinHandle);
    }

    public LocalDate getDate(){
        return LocalDate.now();
    }

    public void takeScreenshot(String name){
        LocalDateTime timestamp = LocalDateTime.now();

        Shutterbug.shootPage(webDriver, Capture.FULL_SCROLL, 500, true).withName(name)
                .save("screenshots/" +timestamp.toLocalDate() +"/" + testCase +"/");

        /* full screen screenshots implemented in selenium only works with firefox that does not emulate mobile
        Date today = new Date();
        File src = ((FirefoxDriver) webDriver).getFullPageScreenshotAs(OutputType.FILE);
        try {
            copy(src, new File("screenshots/" + today.getTime() + getTestCase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    private void setProperties(String testSuite) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInput = new FileInputStream("src/main/resources/config_" +testSuite +".properties");
        properties.load(fileInput);

        setUsername(properties.getProperty("username"));
        setPassword(properties.getProperty("password"));
        setMagicCode(properties.getProperty("magiccode"));
        setIdentityNumber(properties.getProperty("identitynumber"));
        setGivenName(properties.getProperty("givenname"));
        setSurName(properties.getProperty("surname"));
        setDisplayName(properties.getProperty("displayname"));
        setPhoneNumber(properties.getProperty("phonenumber"));
        setEmail(properties.getProperty("email"));
        setEppn(properties.getProperty("eppn"));
        setSupportUsername(properties.getProperty("support_username"));

        setLanguage("Svenska");

        //log.info("Properties loaded!");
    }

    public void addMagicCookie(){
        Date today    = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
//        webDriver.manage().addCookie(new Cookie("autotests", "w9eB5yt2TwEoDsTNgzmtINq03R24DPQD8ubmRVfXPOST3gRi"));
        webDriver.manage().addCookie(new Cookie("autotests", "w9eB5yt2TwEoDsTNgzmtINq03R24DPQD8ubmRVfXPOST3gRi",
                ".dev.eduid.se", "/", tomorrow, true));
    }

    public void logMagicCookie(){
        log.info("Cookie name: " + webDriver.manage().getCookieNamed("autotests").getName());
        log.info("Cookie value: " + webDriver.manage().getCookieNamed("autotests").getValue());
        log.info("Cookie domain: " + webDriver.manage().getCookieNamed("autotests").getDomain());
        log.info("Cookie path: " + webDriver.manage().getCookieNamed("autotests").getPath());
        log.info("Cookie expire: " + webDriver.manage().getCookieNamed("autotests").getExpiry());
    }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public String getGivenName(){ return givenName; }
    public void setGivenName(String givenName){ this.givenName = givenName; }

    public String getSurName(){ return surName; }
    public void setSurName(String surName){ this.surName = surName; }

    public String getDisplayName(){ return displayName; }
    public void setDisplayName(String displayName){ this.displayName = displayName; }

    public String getMagicCode(){ return magicCode; }
    public void setMagicCode(String magicCode){ this.magicCode = magicCode; }

    public String getIdentityNumber(){ return identityNumber; }
    public void setIdentityNumber(String identityNumber){ this.identityNumber = identityNumber; }

    public String getPhoneNumber(){ return phoneNumber; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getLanguage(){ return language; }
    public void setLanguage(String language){ this.language = language; }

    public boolean getRegisterAccount(){ return registerAccount; }
    public void setRegisterAccount(boolean registerAccount){ this.registerAccount = registerAccount; }

    public boolean getResetPassword(){ return resetPassword; }
    public void setResetPassword(boolean resetPassword){ this.resetPassword = resetPassword; }

    public boolean getIncorrectPassword(){ return incorrectPassword; }
    public void setIncorrectPassword(boolean incorrectPassword){ this.incorrectPassword = incorrectPassword; }

    public boolean getRemovePrimary(){ return removePrimary; }
    public void setRemovePrimary(boolean removePrimary){ this.removePrimary = removePrimary; }

    public boolean getRemoveNewEmail1(){ return removeNewEmail1; }
    public void setRemoveNewEmail1(boolean removeNewEmail1){ this.removeNewEmail1 = removeNewEmail1; }

    public boolean getButtonValuePopup(){ return buttonValuePopup; }
    public void setButtonValuePopup(boolean buttonValuePopup){ this.buttonValuePopup = buttonValuePopup; }

    public boolean getUseRecommendedPw(){ return useRecommendedPw; }
    public void setUseRecommendedPw(boolean useRecommendedPw){ this.useRecommendedPw = useRecommendedPw; }

    public boolean getButtonValueConfirm(){ return buttonValueConfirm; }
    public void setButtonValueConfirm(boolean buttonValueConfirm){ this.buttonValueConfirm = buttonValueConfirm; }

    public String getAddNewEmail1(){ return addNewEmail1; }
    public void setAddNewEmail1(String addNewEmail1){ this.addNewEmail1 = addNewEmail1; }

    public String getConfirmNewEmail1(){ return confirmNewEmail1; }
    public void setConfirmNewEmail1(String confirmNewEmail1){ this.confirmNewEmail1 = confirmNewEmail1; }

    public String getNewPassword(){ return newPassword; }
    public void setNewPassword(String newPassword){ this.newPassword = newPassword; }

    public void setResendOTP(boolean resendOTP){ this.resendOTP = resendOTP; }
    public boolean getResendOTP(){ return resendOTP; }

    public void setSendMobileOneTimePassword(String sendMobileOneTimePassword){ this.sendMobileOneTimePassword = sendMobileOneTimePassword; }
    public String getSendMobileOneTimePassword(){ return sendMobileOneTimePassword; }

    public String getEmailCode(){ return emailCode; }
    public void setEmailCode(String emailCode){ this.emailCode = emailCode; }

    public void setDeleteButton(boolean deleteButton){ this.deleteButton = deleteButton; }
    public boolean getDeleteButton(){ return deleteButton; }

    public void setGenerateUsername(boolean generateUsername){ this.generateUsername = generateUsername; }
    public boolean getGenerateUsername(){ return generateUsername; }

    public void setAcceptTerms(boolean acceptTerms){ this.acceptTerms = acceptTerms; }
    public boolean getAcceptTerms(){ return acceptTerms; }

    public void setSendCaptcha(boolean sendCaptcha){ this.sendCaptcha = sendCaptcha; }
    public boolean getSendCaptcha(){ return sendCaptcha; }

    public String getConfirmIdBy(){ return confirmIdBy; }
    public void setConfirmIdBy(String confirmIdBy){ this.confirmIdBy = confirmIdBy; }

    public String getMfaMethod(){ return mfaMethod; }
    public void setMfaMethod(String mfaMethod){ this.mfaMethod = mfaMethod; }

    public String getEppn(){ return eppn; }
    public void setEppn(String eppn){ this.eppn = eppn; }

    public String getSupportUsername(){ return supportUsername; }
    public void setSupportUsername(String supportUsername){ this.supportUsername = supportUsername; }

    public boolean getAddSecurityKey(){ return addSecurityKey; }
    public void setAddSecurityKey(boolean addSecurityKey){ this.addSecurityKey = addSecurityKey; }

    public String getTestCase() { return testCase; }
    public void setTestCase(String testCase) { this.testCase = testCase; }

    public String getTestDescription() { return testDescription; }
    public void setTestDescription(String testDescription) { this.testDescription = testDescription; }

    public void setTestSuite(String testSuite){ this.testSuite = testSuite; }
    public String getTestSuite(){ return testSuite; }
}