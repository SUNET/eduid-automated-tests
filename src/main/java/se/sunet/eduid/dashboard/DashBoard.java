package se.sunet.eduid.dashboard;

import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;

import static se.sunet.eduid.utils.Common.log;

public class DashBoard {
    private final Common common;
    private final TestData testData;
    String pageBody;
    String eduIDStatusOverviewMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[2]/ul/li/a";
    String verifyIdentityMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[4]/ul/li[1]/a";
    String nameMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[4]/ul/li[2]/a";
    String mfaMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[6]/ul/li[1]/a";
    String handleSecurityKeyMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[6]/ul/li[2]/a";
    String uniqueMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[1]/a";
    String emailMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[2]/a";
    String languageMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[3]/a";
    String changePasswordMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[4]/a";
    String orchIdMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[5]/a";
    String esiInfoMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[6]/a";
    String deleteAccountMenuLink = "//*[@id=\"header\"]/nav/div/ul/li[8]/ul/li[7]/a";

    public DashBoard(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }

    public void runDashBoard(){
        //If we are not on dashboard e.g. after security key validation, navigate to dashboard
        if(!common.getWebDriver().getCurrentUrl().equalsIgnoreCase("https://dev.eduid.se/profile/")) {
            common.navigateToDashboard();
        }


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
        log.info("Verifying dashboard labels in swedish");

        common.verifyPageTitle("Start | eduID");

        //Extract page body for validation
        pageBody = common.getPageBody();

        //Verify menu labels, not needed for every test case
        if(testData.getTestCase().equalsIgnoreCase("TC_40"))
            verifyMenuLabelsSwe();

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
            log.info("TODO - Account is not verified");
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

        //Verify menu labels, not needed for every test case
        if(testData.getTestCase().equalsIgnoreCase("TC_40"))
            verifyMenuLabelsEng();

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
            log.info("TODO - Account is not verified");
        }
        if(testData.isIdentityConfirmed()){
            log.info("Identity is verified");
            common.verifyPageBodyContainsString(pageBody, "Verified identity");
            common.verifyPageBodyContainsString(pageBody, "Read more details about your verified identity at Identity");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Verify your identity");
            common.verifyPageBodyContainsString(pageBody, "Connect your identity to eduID at Identity");
        }
        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[2]/div[2]/span/a");

        if(testData.isAddSecurityKey()){
            log.info("Security key is added");
            common.verifyPageBodyContainsString(pageBody, "Enhanced security");
            common.verifyPageBodyContainsString(pageBody, "Read more about your added two-factor authentication at Security");
        }
        else {
            common.verifyPageBodyContainsString(pageBody, "Enhance security");
            common.verifyPageBodyContainsString(pageBody, "Add two-factor authentication at Security");
        }

        common.verifyXpathIsWorkingLink("//*[@id=\"eduid-splash-and-children\"]/article/section/div[3]/div[2]/span/a");

        if(testData.isVerifySecurityKeyByFreja()) {
            log.info("Security key is verified");
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

    void verifyMenuLabelsSwe(){
        log.info("Verifying menu labels in swedish and check that sub menu links are not broken");

        expandFullNavigationMenuWithSubMenus();

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[1]/a", "Start");
        common.verifyStringByXpath(eduIDStatusOverviewMenuLink, "eduID statusöversikt");
        common.verifyXpathIsWorkingLink(eduIDStatusOverviewMenuLink);

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[3]/a", "Identitet");
        common.verifyStringByXpath(verifyIdentityMenuLink, "Verifiera identitet");
        common.verifyXpathIsWorkingLink(verifyIdentityMenuLink);
        common.verifyStringByXpath(nameMenuLink, "Namn & Visningsnamn");
        common.verifyXpathIsWorkingLink(nameMenuLink);

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[5]/a", "Säkerhet");
        common.verifyStringByXpath(mfaMenuLink, "Tvåfaktorsautentisering (2FA)");
        common.verifyXpathIsWorkingLink(mfaMenuLink);
        common.verifyStringByXpath(handleSecurityKeyMenuLink, "Hantera dina säkerhetsnycklar");
        common.verifyXpathIsWorkingLink(handleSecurityKeyMenuLink);

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[7]/a", "Konto");
        common.verifyStringByXpath(uniqueMenuLink, "Unikt ID");
        common.verifyXpathIsWorkingLink(uniqueMenuLink);
        common.verifyStringByXpath(emailMenuLink, "E-postadresser");
        common.verifyXpathIsWorkingLink(emailMenuLink);
        common.verifyStringByXpath(languageMenuLink, "Språk");
        common.verifyXpathIsWorkingLink(languageMenuLink);
        common.verifyStringByXpath(changePasswordMenuLink, "Byt lösenord");
        common.verifyXpathIsWorkingLink(changePasswordMenuLink);
        common.verifyStringByXpath(orchIdMenuLink, "Länka till ditt ORCID konto");
        common.verifyXpathIsWorkingLink(orchIdMenuLink);
        common.verifyStringByXpath(esiInfoMenuLink, "ESI information");
        common.verifyXpathIsWorkingLink(esiInfoMenuLink);
        common.verifyStringByXpath(deleteAccountMenuLink, "Radera eduID");
        common.verifyXpathIsWorkingLink(deleteAccountMenuLink);

        common.verifyStringById("logout", "LOGGA UT");
    }

    void verifyMenuLabelsEng(){
        log.info("Verifying menu labels in swedish");

        expandFullNavigationMenuWithSubMenus();

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[1]/a", "Start");
        common.verifyStringByXpath(eduIDStatusOverviewMenuLink, "eduID status overview");

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[3]/a", "Identity");
        common.verifyStringByXpath(verifyIdentityMenuLink, "Verify identity");
        common.verifyStringByXpath(nameMenuLink, "Names & Display Name");

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[5]/a", "Security");
        common.verifyStringByXpath(mfaMenuLink, "Two-factor Authentication (2FA)");
        common.verifyStringByXpath(handleSecurityKeyMenuLink, "Manage your security keys");

        common.verifyStringByXpath("//*[@id=\"header\"]/nav/div/ul/li[7]/a", "Account");
        common.verifyStringByXpath(uniqueMenuLink, "Unique ID");
        common.verifyStringByXpath(emailMenuLink, "Email addresses");
        common.verifyStringByXpath(languageMenuLink, "Language");
        common.verifyStringByXpath(changePasswordMenuLink, "Change password");
        common.verifyStringByXpath(orchIdMenuLink, "ORCID account");
        common.verifyStringByXpath(esiInfoMenuLink, "ESI information");
        common.verifyStringByXpath(deleteAccountMenuLink, "Delete eduID");

        common.verifyStringById("logout", "LOG OUT");
    }

    public void expandFullNavigationMenuWithSubMenus(){
        common.expandNavigationMenu();

        //Expand sub-menus
        common.findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[7]/button").click();
        common.findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[5]/button").click();
        common.findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[3]/button").click();
        common.findWebElementByXpath("//*[@id=\"header\"]/nav/div/ul/li[1]/button").click();
    }
}
