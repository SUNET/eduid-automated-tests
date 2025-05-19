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
    private String username, password, givenName, surName, displayName, email,
            phoneNumber, identityNumber, language, eppn, supportUsername, supportPassword,
            emailCode, testCase, testDescription, testSuite, testMethod, otpPhoneNumber, otherDeviceCode,
            otherDeviceSubmitCode, emailVerificationCode, browser, headlessExecution, letterProofingCodeUrl,
            emailVerificationCodeUrl, phoneConfirmationCodeUrl, emailResetPwCodeUrl, domain, bankIdTextSwe,
            bankIdTextEng, supportUrl, baseUrl, idpName, autotestsCookieValue, identityNumberFilePath,
            givenNameFilePath, surNameFilePath;

    private boolean buttonValueConfirm = true;
    private boolean generateUsername = true;
    private boolean acceptTerms = true;
    private boolean verifyEmail = true;
    private boolean addSecurityKey = false;
    private boolean verifySecurityKeyByFreja;
    private boolean verifySecurityKeyByBankId;
    private boolean verifySecurityKeyByEidas;
    private boolean accountVerified = true;
    private boolean useRecommendedPw = true;
    private boolean registerAccount, resetPassword, incorrectPassword, removeNewEmail1,
            resendOTP, deleteButton, otherDeviceFillCode, rememberMe, identityConfirmed;
    private int counter = 0;

    public void setProperties(String testSuite) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInput = new FileInputStream("src/main/resources/" +testSuite +".properties");
        properties.load(fileInput);

        setUsername(properties.getProperty("username"));
        setPassword(properties.getProperty("password"));
        setIdentityNumber(properties.getProperty("identity_number"));
        setGivenName(properties.getProperty("given_name"));
        setSurName(properties.getProperty("sur_name"));
        setDisplayName(properties.getProperty("display_name"));
        setPhoneNumber(properties.getProperty("phone_number"));
        setEmail(properties.getProperty("email"));
        setEppn(properties.getProperty("eppn"));
        setSupportUsername(properties.getProperty("support_username"));
        setSupportPassword(properties.getProperty("support_password"));
        setAutotestsCookieValue(properties.getProperty("autotests_cookie_value"));
        setIdentityNumberFilePath(properties.getProperty("identity_number_file_path"));
        setGivenNameFilePath(properties.getProperty("given_name_file_path"));
        setSurNameFilePath(properties.getProperty("surname_file_path"));

        setBaseUrl(properties.getProperty("base_url"));
        setLetterProofingCodeUrl(properties.getProperty("letter_proofing_code_url"));
        setEmailVerificationCodeUrl(properties.getProperty("email_verification_code_url"));
        setPhoneConfirmationCodeUrl(properties.getProperty("phone_confirmation_code_url"));
        setEmailResetPwCodeUrl(properties.getProperty("email_reset_pw_code_url"));
        setDomain(properties.getProperty("domain"));
        setBankIdTextSwe(properties.getProperty("bankid_text_sv"));
        setBankIdTextEng(properties.getProperty("bankid_text_eng"));
        setSupportUrl(properties.getProperty("support_url"));
        setIdpName(properties.getProperty("idp_name"));

        setLanguage("Svenska");
    }
}
