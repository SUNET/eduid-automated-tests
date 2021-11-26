package se.sunet.eduid.supportTool;

import se.sunet.eduid.utils.Common;

import java.time.LocalDate;

public class RegisteredData {
    private final Common common;
    private final LocalDate localDate = LocalDate.now();

    public RegisteredData(Common common){
        this.common = common;
    }

    public void runRegisteredData(){
        verifyCentralDB();
        verifySignupDB();
        verifyOngoingProofings();
        verifyCompletedProofings();
        verifyOtherUserData();
        logout();
    }

    private void verifyCentralDB(){
        //Eppn
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[1]/td", common.getEppn());

        //Given name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[2]/td", "Magic Cookie");

        //Sur name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[3]/td", "Magic Cookie");

        //Display name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[4]/td", common.getDisplayName());

        //National identity numbers - Number
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[1]", common.getIdentityNumber());

        //National identity numbers - Primary
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[2]", "True");

        //National identity numbers - Verified
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[3]", "True");

        //National identity numbers - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[4]", String.valueOf(localDate));

        //National identity numbers - Verified using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[5]", "lookup_mobile_proofing");

        //National identity numbers - Verified timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[6]", String.valueOf(localDate));

        //Mail addresses - Address
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[1]", common.getUsername().toLowerCase());

        //Mail addresses - Primary
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[2]", "True");

        //Mail addresses - Verified
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[3]", "True");

        //Mail addresses - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[4]", "signup");

        //Mail addresses - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[5]", String.valueOf(localDate));

        //Mail addresses - Verified timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[6]", String.valueOf(localDate));

        //Phone numbers - Number
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[1]", common.getPhoneNumber());

        //Phone numbers - Primary
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[2]", "True");

        //Phone numbers - Verified
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[3]", "True");

        //Phone numbers - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[4]", String.valueOf(localDate));

        //Credentials - Type
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[1]", "Password");

        //Credentials - Last used successfully
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[2]", String.valueOf(localDate));

        //Credentials - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[3]", "signup");

        //Credentials - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[4]", String.valueOf(localDate));

        //Accepted terms of use - Version
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[1]", "2016-v1");

        //Accepted terms of use - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[2]", "signup");

        //Accepted terms of use - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[3]", String.valueOf(localDate));

        //Termination status
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[10]/td", "False");

        //Language preference
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[11]/td","sv");
    }

    private void verifySignupDB(){
        //Eppn
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[1]/td", common.getEppn());

        //Given name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[2]/td", "");

        //Sur name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[3]/td", "");

        //Display name
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[4]/td", "");

        //National identity numbers
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[5]/td", "");

        //Mail addresses - Address
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[1]", common.getUsername().toLowerCase());

        //Mail addresses - Primary
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[2]", "True");

        //Mail addresses - Verified
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[3]", "True");

        //Mail addresses - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[4]", "signup");

        //Mail addresses - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[5]", String.valueOf(localDate));

        //Mail addresses - Verified timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[6]", String.valueOf(localDate));

        //Phone numbers
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[7]/td", "");

        //Credentials - Type
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[1]", "Password");

        //Credentials - Last used successfully
        //common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[2]", String.valueOf(localDate));

        //Credentials - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[3]", "signup");

        //Credentials - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[4]", String.valueOf(localDate));

        //Accepted terms of use - Version
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[1]", "2016-v1");

        //Accepted terms of use - Added using
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[2]", "signup");

        //Accepted terms of use - Added timestamp
        common.verifyXpathContainsString("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[3]", String.valueOf(localDate));

        //Termination status
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[10]/td", "False");

        //Language preference
        common.verifyStringByXpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[11]/td","sv");
    }

    private void verifyOngoingProofings(){
        //Letter proofing
        common.verifyStringByXpath("//div/div[2]/div/div[3]/div[1]/table/tbody/tr/td", "No data");

        //OIDC proofing
        common.verifyStringByXpath("//div/div[2]/div/div[3]/div[2]/table/tbody/tr/td", "No data");

        //Email proofings
        common.verifyStringByXpath("//div/div[2]/div/div[3]/div[3]/table/tbody/tr/td", "No data");

        //Phone proofings
        common.verifyStringByXpath("//div/div[2]/div/div[3]/div[4]/table/tbody/tr/td", "No data");
    }

    private void verifyCompletedProofings(){
        //Proofing log
        common.verifyXpathContainsString("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[1]", String.valueOf(localDate));
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[2]", "lookup_mobile_proofing");
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[3]", common.getIdentityNumber());
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[4]", "TeleAdress-2014v1");

        common.verifyXpathContainsString("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[1]", String.valueOf(localDate));
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[2]", "phone");
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[3]", common.getPhoneNumber());
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[4]", "sms-2013v1");

        common.verifyXpathContainsString("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[1]", String.valueOf(localDate));
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[2]", "signup");
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[3]", common.getUsername().toLowerCase());
        common.verifyStringByXpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[4]", "e-mail-2013v1");
    }

    private void verifyOtherUserData(){
        //Authentication information

        //Last successful login
        common.verifyXpathContainsString("//div/div[2]/div/div[7]/div/table/tbody/tr[1]/td", String.valueOf(localDate));

        //Failed login attempts
        common.verifyStringByXpath("//div/div[2]/div/div[7]/div/table/tbody/tr[2]/td/dl/dd", "No data");

        //Successful log in attempts
        common.verifyStringByXpath("//div/div[2]/div/div[7]/div/table/tbody/tr[3]/td/dl/dd", "1");
    }

    private void logout(){
        common.findWebElementById("logout-button").click();
    }
}