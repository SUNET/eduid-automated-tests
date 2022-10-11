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
    private String sendMobileOneTimePassword = "yes";
    private String username, password, givenName, surName, displayName, magicCode, email,
            phoneNumber, identityNumber, language, eppn, confirmIdBy, supportUsername,
            emailCode, testCase, testDescription, testSuite, mfaMethod, otpPhoneNumber, otherDeviceCode, otherDeviceSubmitCode;

    private boolean buttonValuePopup = true;
    private boolean buttonValueConfirm = true;
    private boolean generateUsername = true;
    private boolean acceptTerms = true;
    private boolean sendCaptcha = true;
    private boolean addSecurityKey = false;
    private boolean registerAccount, resetPassword, incorrectPassword, removePrimary, removeNewEmail1,
            resendOTP, deleteButton, useRecommendedPw, otherDeviceFillCode, rememberMe;
    private int counter = 0;

    void setProperties(String testSuite) throws IOException {
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
    }
}
