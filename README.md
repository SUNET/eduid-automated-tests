# eduid-automated-tests
## Test case description:
TC_1 - Log in and check account data and labels

TC_2 - Change givenName, surName and displayName

TC_3 - Check that dashboard names changed according to TC_2 and restore names to default

TC_4 - Change language to English

TC_5 - Check that dashboard language is english and restore language to default

TC_6 - Try to remove the primary email address

TC_7 - Add an additional email address and confirm it by code

TC_8 - Remove the additional email address added in TC_7

TC_9 - Try to add the default email address as extra email address

TC_10 - Add additional email address, try to confirm with empty code

TC_11 - Remove the additional email address added in TC_10

TC_12 - Add additional email address, try to confirm with incorrect code

TC_13 - Remove the additional email address added in TC_12

TC_14 - Try to add additional email address thats invalid

TC_15 - Add an extra email address and confirm it by clicking on link in email

TC_16 - Remove the extra email address added in TC_15

TC_17 - Inititiate password change but abort at pop-up window

TC_18 - Inititiate password change but abort instead of save new password

TC_19 - Change password, use recommended

TC_20 - Change password, use custom

TC_21 - Initiate password change but enter incorrect current password

TC_22 - Initiate password change but choose a too week password

TC_23 - Reset password with extra security (mobile OTP) and set a new custom password, use default pw

TC_24 - Reset password with extra security (mobile OTP) and set new recommended password

TC_25 - Reset password with extra security (mobile OTP), resend mobile OTP once. Set a new custom password, use default pw

TC_26 - Try to log in with incorrect password

TC_27 - Reset password with extra security (mobile OTP), resend mobile OTP once. Set a new custom password, use default pw

TC_28 - Try to reset password with incorrect magic code

TC_29 - Reset password without extra security (mobile OTP). Abort confirmation of the phone number from dashboard

TC_30 - After reset password without extra security (mobile OTP). Enter incorrect code for confirmation of the phone number from dashboard

TC_31 - Reset password without extra security (mobile OTP). Confirm the phone number from dashboard

TC_32 - Initiate account termination but abort in confirm pop-up

TC_33 - Delete the account

TC_34 - Reset password with extra security (mobile OTP) and set a new custom password, use default pw

TC_35 - Initiate Register of new account, abort at terms

TC_36 - Initiate Register of new account, abort at captcha

TC_37 - Initiate Register of new account, use incorrect magic code

TC_38 - Register a new account

TC_39 - Try to register new account with already existing user name

TC_40 - Register new account, add and confirm phone number. Confirm identity by phone

TC_41 - Register new account, add and confirm phone number. Confirm identity by mail

TC_98 - Confirm phone number on default account, to restore it from previous tests

TC_99 - Check the Help pages
