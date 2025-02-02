package se.sunet.eduid.dashboard;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

public class DashBoard {
    private final Common common;
    private final TestData testData;
    String pageBody;

    public DashBoard(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDashBoard(){
        common.navigateToDashboard();
        verifyPageTitle();

        verifyUserId();

        if(testData.getTestCase().equalsIgnoreCase("TC_5"))
            verifyLabelsEnglish();
        else{
            verifyLabelsSwedish();
            verifyLabelsEnglish();
        }

    }

    private void verifyPageTitle() {
        //TODO temp fix to get swedish language - needed when new accounts created
        if(common.findWebElementByXpath("//*[@id=\"language-selector\"]/span/a").getText().contains("Svenska")
                && testData.getLanguage().equalsIgnoreCase("Svenska")) {
            common.selectSwedish();
        }

        common.verifyPageTitle("Start | eduID");
    }

    private void verifyUserId() {
        common.verifyStringByXpath("//*[@id=\"header\"]/nav/button/span", testData.getUsername().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verifying dashboard labels in swedish");

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify navigation label
        common.click(common.findWebElementByXpath("//*[@id=\"header\"]/nav/button"));
        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header\"]/nav/button"));

        //Verify welcome heading
        if (testData.getDisplayName().isEmpty())
            common.verifyPageBodyContainsString(pageBody, "Välkommen, " + testData.getEmail().toLowerCase() + "!");
        else {
            //Ignore case-sensitive since when double name all names are shown with capital letter
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Välkommen, " + testData.getDisplayName() + "!");
        }

        //EPPN
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/label/strong", "Unikt ID:");
        common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));

        //Verify heading sub-text
        common.verifyPageBodyContainsString(pageBody, "eduID statusöversikt");
        common.verifyPageBodyContainsString(pageBody, "Säkerheten och användbarheten av ditt eduID kan " +
                "förbättras genom nedanstående åtgärder.\n" + "Förslag på vad som kan vara lämpligt beroende av " +
                "organisationen du använder ditt eduID för, kan hittas under avsnittet för Tillitsnivåer under Hjälp.");
        common.verifyPageBodyContainsString(pageBody, "Status på slutförda åtgärder markeras med en bockmarkering.");

        //Verify internal help-link
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/p[2]/a");

        //Account status texts
        if(testData.isAccountVerified()) {
            common.verifyPageBodyContainsString(pageBody, "Bekräftat konto");
            common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        }
        else{
            //TODO when account is not confirmed i.e. email address not verified
            Common.log.info("TODO - Account is not verified");
        }
        if(testData.isIdentityConfirmed()){
            common.verifyPageBodyContainsString(pageBody, "Verifierad identitet");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din verifierade identitet under Identitet");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verifiera din identitet");
            common.verifyPageBodyContainsString(pageBody, "Koppla din identitet till eduID under Identitet");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[2]/span/a");

        if(testData.isAddSecurityKey()){
            common.verifyPageBodyContainsString(pageBody, "Ökad säkerhet");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din tillagda tvåfaktorsautentisering under Säkerhet");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Öka säkerheten");
            common.verifyPageBodyContainsString(pageBody, "Lägg till tvåfaktorsautentisering under Säkerhet");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[2]/span/a");

        if(testData.isVerifySecurityKeyByFreja()) {
            common.verifyPageBodyContainsString(pageBody, "Verifierad säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Läs mer om din verifierade tvåfaktorsautentisering under Säkerhet");
        }
        else{
            common.verifyPageBodyContainsString(pageBody, "Verifiera din säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Verifiera din säkerhetsnyckel under Säkerhet");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[4]/div[2]/span/a");

        //Fine text at bottom
        common.verifyPageBodyContainsString(pageBody, "Obs: ytterligare inställningar för språk, " +
                "e-postadresser, lösenordshantering samt länkning till ORCID och ESI kan hanteras under Konto.");

        //Verify internal account-link
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/p[4]/a");
    }

    private void verifyLabelsEnglish() {
        //Select English
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectEnglish();

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header\"]/nav/button"));
        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header\"]/nav/button"));

        //Verify welcome heading
        if(testData.getDisplayName().isEmpty()) {
            common.verifyPageBodyContainsString(pageBody, "Welcome, " + testData.getEmail().toLowerCase() + "!");
        }
        else {
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Welcome, " + testData.getDisplayName() + "!");
        }

        //EPPN
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/label/strong", "Unique ID:");
        if(testData.isRegisterAccount()) {
            //Just check that eppn is 11 characters long
            Assert.assertEquals(common.findWebElementById("user-eppn").getAttribute("value").length(),
                    11, "EPPN seems to be missing or not correct length");
        }
        else
            common.verifyStrings(testData.getEppn(), common.findWebElementById("user-eppn").getAttribute("value"));

        //Verify heading sub-text
        common.verifyPageBodyContainsString(pageBody, "eduID status overview");
        common.verifyPageBodyContainsString(pageBody, "The strength and usage of your eduID can be improved" +
                " by following the steps listed below.\n" +"Suggestions on what might be required depending on the " +
                "organisation you are accessing with your eduID, can be found in the Assurance levels section in Help.");
        common.verifyPageBodyContainsString(pageBody, "Status of completed steps are indicated with a checkmark.");

        //Verify internal help-link
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/p[2]/a");

        //Account status texts
        if(testData.isAccountVerified()) {
            common.verifyPageBodyContainsString(pageBody, "Confirmed account");
            common.verifyPageBodyContainsString(pageBody, testData.getUsername().toLowerCase());
        }
        else{
            //TODO when account is not confirmed i.e. email address not verified
            Common.log.info("TODO - Account is not verified");
        }
        if(testData.isIdentityConfirmed()){
            Common.log.info("Identiy is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified identity");
            common.verifyPageBodyContainsString(pageBody, "Read more details about your verified identity at Identity");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verify your identity");
            common.verifyPageBodyContainsString(pageBody, "Connect your identity to eduID at Identity");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[2]/span/a");

        if(testData.isAddSecurityKey()){
            Common.log.info("Security key is added");
            common.verifyPageBodyContainsString(pageBody, "Enhanced security");
            common.verifyPageBodyContainsString(pageBody, "Read more about your added two-factor authentication at Security");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Enhance security");
            common.verifyPageBodyContainsString(pageBody, "Add two-factor authentication at Security");
        }

        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[2]/span/a");

        if(testData.isVerifySecurityKeyByFreja()) {
            Common.log.info("Security key is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified security key");
            common.verifyPageBodyContainsString(pageBody, "Read more details about your verified two-factor authentication at Security");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verify your security key");
            common.verifyPageBodyContainsString(pageBody, "Verify your security key at Security");
        }

        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[4]/div[2]/span/a");

        //Fine text at bottom
        common.verifyPageBodyContainsString(pageBody, "Note: additional settings such as language, email " +
                "addresses, password management as well as ORCID and ESI affiliation can be edited at Account.");

        //Verify internal account-link
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/p[4]/a");

        //Select swedish
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectSwedish();
    }
}
