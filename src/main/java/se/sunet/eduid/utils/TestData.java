package se.sunet.eduid.utils;


import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
public class TestData {
    private String addNewEmail1 = "";
    private String confirmNewEmail1 = "";
    private String newPassword = "";
    private String confirmIdBy = "";
    private String mfaMethod = "";
    private String loaLevel = "eIDAS Substantial";
//    private String refIdpUser = "Sixten von Samordnungsnummer (197010632391)";
    private String username, password, givenName, surName, displayName, magicCode, email,
            phoneNumber, identityNumber, language, eppn, supportUsername, supportPassword,
            emailCode, testCase, testDescription, testSuite, testMethod, otpPhoneNumber, otherDeviceCode,
            otherDeviceSubmitCode, emailVerificationCode, browser, headlessExecution;

    private boolean buttonValueConfirm = true;
    private boolean generateUsername = true;
    private boolean acceptTerms = true;
    private boolean verifyEmail = true;
    private boolean addSecurityKey = false;
    private boolean verifySecurityKeyByFreja;
    private boolean verifySecurityKeyByBankId;
    private boolean accountVerified = true;
    private boolean useRecommendedPw = true;
    private boolean registerAccount, resetPassword, incorrectPassword, removeNewEmail1,
            resendOTP, deleteButton, otherDeviceFillCode, rememberMe, identityConfirmed;
    private int counter = 0;

    public void setProperties(String testSuite) throws IOException {
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
        setSupportPassword(properties.getProperty("support_password"));

        setLanguage("Svenska");
    }
}
