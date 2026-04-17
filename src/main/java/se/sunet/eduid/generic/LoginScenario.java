package se.sunet.eduid.generic;

/**
 * Represents the login scenario to execute.
 * Resolved once from TestData so runLogin() stays free of nested conditionals.
 */
public enum LoginScenario {
    RESET_PASSWORD,
    REGISTER_ACCOUNT,
    PASSKEY,
    RE_LOGIN,
    STANDARD;

    public static LoginScenario resolve(se.sunet.eduid.utils.TestData td) {
        if (td.isResetPassword())    return RESET_PASSWORD;
        if (td.isRegisterAccount())  return REGISTER_ACCOUNT;
        if (td.isUsePasskey())       return PASSKEY;
        if (td.isReLogin())          return RE_LOGIN;
        return STANDARD;
    }
}
