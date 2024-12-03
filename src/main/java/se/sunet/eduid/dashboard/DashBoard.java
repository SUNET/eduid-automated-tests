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
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/button/span", testData.getUsername().toLowerCase());
    }

    private void verifyLabelsSwedish() {
        Common.log.info("Verifying dashboard labels in swedish");

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify navigation label
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));

        //Verify welcome heading
        if (testData.getDisplayName().isEmpty())
            common.verifyPageBodyContainsString(pageBody, "Välkommen, " + testData.getEmail().toLowerCase() + "!");
        else {
            //Ignore case-sensitive since when double name all names are shown with capital letter
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Välkommen, " + testData.getDisplayName() + "!");
        }

        //EPPN
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/span/strong", "Unikt ID: ");
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
            common.verifyPageBodyContainsString(pageBody, "Se mer om din verifierade identitet under Identitet");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verifiera din identitet");
            common.verifyPageBodyContainsString(pageBody, "Koppla din identitet till eduID under Identitet");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[2]/span/a");

        if(testData.isAddSecurityKey()){
            common.verifyPageBodyContainsString(pageBody, "Ökad säkerhet");
            common.verifyPageBodyContainsString(pageBody, "Se mer om din tvåfaktorsautentisering under Säkerhet");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Öka säkerheten");
            common.verifyPageBodyContainsString(pageBody, "Lägg till tvåfaktorsautentisering under Säkerhet");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[2]/span/a");

        if(testData.isVerifySecurityKey()) {
            common.verifyPageBodyContainsString(pageBody, "Verifierad säkerhetsnyckel");
            common.verifyPageBodyContainsString(pageBody, "Se mer om din verifierade tvåfaktorsautentisering under Säkerhet");
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
/*
        //Non-verified account text
        if(!testData.isAccountVerified() && testData.isRegisterAccount()) {
            //TODO do not thinke we have a test case that covers when this is not checked.
            //Maybe it can be when new email added, not verified and primary is removed.
            Common.log.info("Account is not verified, only email confirmed");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[1]/div[1]/svg")
                    .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        if(testData.isAccountVerified()) {
            Common.log.info("Account is verified, i.e. email confirmed");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[1]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Verified account text
        if(testData.isIdentityConfirmed()){
            Common.log.info("Identity is verified");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Security key added
        if(testData.isAddSecurityKey()){
            Common.log.info("Two factor is enabled");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Security key
        if(testData.isVerifySecurityKey()){
            Common.log.info("Security key is verified");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[4]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Account has been verified earlier but after reset pw its not verified anymore
//        else{
//            Common.log.info("Account has previously been verified");
            Common.log.info("Account has previously been verified....not used anymore...");
//        }*/
    }

    private void verifyLabelsEnglish() {
        //Select English
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectEnglish();

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));
        common.verifyStringByXpath("//*[@id=\"header-nav\"]/div/ul/a[1]", "Start");

        //Close site location menu
        common.click(common.findWebElementByXpath("//*[@id=\"header-nav\"]/button"));

        //Verify welcome heading
        if(testData.getDisplayName().isEmpty()) {
            common.verifyPageBodyContainsString(pageBody, "Welcome, " + testData.getEmail().toLowerCase() + "!");
        }
        else {
            common.verifyStrings(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/section/h1/strong")
                    .getText(), "Welcome, " + testData.getDisplayName() + "!");
        }

        //EPPN
        common.verifyStringByXpath("//*[@id=\"uniqueId-container\"]/span/strong", "Unique ID: ");
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
            common.verifyPageBodyContainsString(pageBody, "See more details about your verified identity at Identity");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verify your identity");
            common.verifyPageBodyContainsString(pageBody, "Connect your identity to eduID at Identity");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[2]/span/a");

        if(testData.isAddSecurityKey()){
            Common.log.info("Security key is added");
            common.verifyPageBodyContainsString(pageBody, "Enhanced security");
            common.verifyPageBodyContainsString(pageBody, "See more about your two-factor authentication at Security");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Enhance security");
            common.verifyPageBodyContainsString(pageBody, "Add two-factor authentication at Security");
        }

        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[2]/span/a");

        if(testData.isVerifySecurityKey()) {
            Common.log.info("Security key is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified security key");
            common.verifyPageBodyContainsString(pageBody, "See more details about your verified two-factor authentication at Security");
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
/*

        //Non-verified accounts
        if(!testData.isAccountVerified() && testData.isRegisterAccount()) {
            Common.log.info("Account is not verified, only email confirmed");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[1]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }
        //Verified account text
        if(testData.isIdentityConfirmed()){
            Common.log.info("Identity is verified");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Security key added
        if(testData.isAddSecurityKey()){
            Common.log.info("Two factor is enabled");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }

        //Security key
        if(testData.isVerifySecurityKey()){
            Common.log.info("Security key is verified");

            //Verify that Account status marker is checked
            Assert.assertTrue(common.findWebElementByXpath("//*[@id=\"eduid-splash-and-children\"]/article/section/div[4]/div[1]/svg")
                            .getAttribute("aria-hidden").equalsIgnoreCase("true"),
                    "Account status marker should be checked, its not.");
        }
*/

        //Account has been verified earlier but after reset pw its not verified anymore
/*        else{
            Common.log.info("Has been verified....not used...?");
        }*/

        //Select swedish
        if(!testData.getTestCase().equalsIgnoreCase("TC_5"))
            common.selectSwedish();
    }
}
