package se.sunet.eduid.supportTool;

import org.openqa.selenium.By;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import java.time.LocalDate;

public class RegisteredData {
    private final Common common;
    private final LocalDate localDate = LocalDate.now();
    private final TestData testData;

    public RegisteredData(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
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
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[1]/td"), testData.getEppn());

        //Given name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[2]/td"), "Magic Cookie");

        //Sur name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[3]/td"), "Magic Cookie");

        //Display name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[4]/td"), "Cookie Magic Cookie");

        //Type - nin
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[1]"), "nin");

        //National identity numbers - Number
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[2]"), testData.getIdentityNumber());

        //National identity numbers - Primary
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[3]"), "True");

        //National identity numbers - Verified
//        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[4]", "True");

        //National identity numbers - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[4]"), String.valueOf(localDate));

        //National identity numbers - Verified using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[5]"), "lookup_mobile_proofing");

        //National identity numbers - Verified timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[5]/td/dl/dd[6]"), String.valueOf(localDate));

        //Mail addresses - Address
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[1]"), testData.getUsername().toLowerCase());

        //Mail addresses - Primary
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[2]"), "True");

        //Mail addresses - Verified
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[3]"), "True");

        //Mail addresses - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[4]"), "signup2");

        //Mail addresses - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[5]"), String.valueOf(localDate));

        //Mail addresses - Verified timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[6]/td/dl/dd[6]"), String.valueOf(localDate));

        //Phone numbers - Number
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[1]"), testData.getPhoneNumber());

        //Phone numbers - Primary
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[2]"), "True");

        //Phone numbers - Verified
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[3]"), "True");

        //Phone numbers - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[7]/td/dl/dd[4]"), String.valueOf(localDate));

        //Credentials - Type
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[1]"), "Password");

        //Credentials - Last used successfully
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[2]"), String.valueOf(localDate));

        //Credentials - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[3]"), "signup2");

        //Credentials - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[4]"), String.valueOf(localDate));

        //Accepted terms of use - Version
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[1]"), "2016-v1");

        //Accepted terms of use - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[2]"), "signup2");

        //Accepted terms of use - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[9]/td/dl/dd[3]"), String.valueOf(localDate));

        //Termination status
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[10]/td"), "False");

        //Language preference
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[11]/td"),"sv");
    }

    private void verifySignupDB(){
        //Eppn
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[1]/td"), testData.getEppn());

        //Given name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[2]/td"), "");

        //Sur name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[3]/td"), "");

        //Display name
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[4]/td"), "");

        //National identity numbers
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[5]/td"), "");

        //Mail addresses - Address
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[1]"), testData.getUsername().toLowerCase());

        //Mail addresses - Primary
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[2]"), "True");

        //Mail addresses - Verified
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[3]"), "True");

        //Mail addresses - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[4]"), "signup2");

        //Mail addresses - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[5]"), String.valueOf(localDate));

        //Mail addresses - Verified timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[6]/td/dl/dd[6]"), String.valueOf(localDate));

        //Phone numbers
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[7]/td"), "");

        //Credentials - Type
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[1]"), "Password");

        //Credentials - Last used successfully
        //common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[1]/table/tbody/tr[8]/td/dl/dd[2]", String.valueOf(localDate));

        //Credentials - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[3]"), "signup2");

        //Credentials - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[8]/td/dl/dd[4]"), String.valueOf(localDate));

        //Accepted terms of use - Version
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[1]"), "2016-v1");

        //Accepted terms of use - Added using
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[2]"), "signup2");

        //Accepted terms of use - Added timestamp
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[9]/td/dl/dd[3]"), String.valueOf(localDate));

        //Termination status
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[10]/td"), "False");

        //Language preference
        common.verifyString(By.xpath("//div/div[2]/div/div[1]/div[2]/table/tbody/tr[11]/td"),"");
    }

    private void verifyOngoingProofings(){
        //Letter proofing
        common.verifyString(By.xpath("//div/div[2]/div/div[3]/div[1]/table/tbody/tr/td"), "No data");

        //OIDC proofing
        common.verifyString(By.xpath("//div/div[2]/div/div[3]/div[2]/table/tbody/tr/td"), "No data");

        //Email proofings
        common.verifyString(By.xpath("//div/div[2]/div/div[3]/div[3]/table/tbody/tr/td"), "No data");

        //Phone proofings
        common.verifyString(By.xpath("//div/div[2]/div/div[3]/div[4]/table/tbody/tr/td"), "No data");
    }

    private void verifyCompletedProofings(){
        //Proofing log
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[1]"), String.valueOf(localDate));
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[2]"), "lookup_mobile_proofing");
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[3]"), testData.getIdentityNumber());
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[2]/td[4]"), "TeleAdress-2014v1");

        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[1]"), String.valueOf(localDate));
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[2]"), "phone");
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[3]"), testData.getPhoneNumber());
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[3]/td[4]"), "sms-2013v1");

        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[1]"), String.valueOf(localDate));
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[2]"), "signup2");
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[3]"), testData.getUsername().toLowerCase());
        common.verifyString(By.xpath("//div/div[2]/div/div[5]/div/table/tbody/tr[4]/td[4]"), "e-mail-2013v1");
    }

    private void verifyOtherUserData(){
        //Authentication information

        //Last successful login
        common.verifyByContainsString(By.xpath("//div/div[2]/div/div[7]/div/table/tbody/tr[1]/td"), String.valueOf(localDate));

        //Failed login attempts
        common.verifyString(By.xpath("//div/div[2]/div/div[7]/div/table/tbody/tr[2]/td/dl/dd"), "No data");

        //Successful log in attempts
        common.verifyString(By.xpath("//div/div[2]/div/div[7]/div/table/tbody/tr[3]/td/dl/dd"), "1");
    }

    private void logout(){
        common.click(common.findWebElement(By.id("logout-button")));
    }
}