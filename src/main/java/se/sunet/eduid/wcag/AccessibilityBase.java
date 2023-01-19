package se.sunet.eduid.wcag;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import se.sunet.eduid.utils.Common;
import se.sunet.eduid.utils.TestData;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccessibilityBase{
    private final Common common;
    private final TestData testData;

    public AccessibilityBase(Common common, TestData testData){
        this.common = common;
        this.testData = testData;
    }
    private static List<String> tags = Arrays.asList("wcag2a", "wcag2aa", "wcag21aa");
    LocalDateTime timestamp = LocalDateTime.now();
    private static String reportPath = "/Users/ovesemart/Documents/JavaProjekt/GitHub/eduid-automated-tests/reports/";

    public void checkAccessibilityViolations() throws IOException
    {
        String reportFile = reportPath + "accessibilityReport";
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        Results results = builder.analyze(common.getWebDriver());
        saveReport(results, reportFile);
        takeScreenshot();

        try {
            parseResultFile();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void checkAccessibilityViolationsOfSelector(String selector) throws FileNotFoundException
    {
        String reportFile = reportPath + "accessibilityReport";
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);

        Common.log.debug("Remember me is: " + testData.isRememberMe());
        if(testData.isRememberMe()) {
            common.switchToPopUpWindow();
            Common.log.debug("Remember me is: " + testData.isRememberMe());
        }
        Results results = builder.include(Collections.singletonList(selector)).analyze(common.getWebDriver());
        saveReport(results, reportFile);
        System.out.println("A11y test report saved to: " + reportPath);
    }

    public void saveReport(Results results, String reportFile) throws FileNotFoundException {
        List<Rule> violations = results.getViolations();
        if (violations.size() == 0)
        {
            Assert.assertTrue(true, "No violations found at " +testData.getTestCase());
        }
        else
        {
            JsonParser jsonParser = new JsonParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            AxeReporter.writeResultsToJsonFile(reportFile, results);
            //AxeReporter.writeResultsToJsonFile(reportFile, violations);
            JsonElement jsonElement = jsonParser.parse(new FileReader(reportFile + ".json"));
            String prettyJson = gson.toJson(jsonElement);
            AxeReporter.writeResultsToTextFile(reportFile, prettyJson);
 //           Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");
        }
    }


    public void parseResultFile() throws ParseException, IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("reports/" +timestamp.toLocalDate() + "/WCAG_" +testData.getCounter() +".html"));
        JSONObject storedExecution = new JSONObject((Map) readJsonFromFile("pelle"));

        JSONArray violations = new JSONArray(storedExecution.get("violations").toString());

        Common.log.debug("number of violations: " + violations.length());

        //Write html file header, title and body start to file
        bufferedWriter.write("\n" +"<html> <meta http-equiv=\"content-type\" content=\"text/plain;charset=utf-8\">\n" +
                " <TITLE>" +testData.getTestCase() +"</TITLE></HEAD><body><br>\n");

        bufferedWriter.write("\n<br><b>" +testData.getTestCase() +"</b><br><br>");
        if(violations.length() == 0){
            bufferedWriter.write("\n<br><b>No violations found! </b>");
        }
        for(int i = 0 ; i < violations.length(); i++){
            Common.log.debug("violation: " + violations.getJSONObject(i).get("help"));

            //Write violation header to file
            bufferedWriter.write("\n<br><b>Violation number: " + (i+1) +"</b>");
            bufferedWriter.write("\n<br><b> " +violations.getJSONObject(i).get("help").toString()
                    .replace("<","&lt;").replace(">", "&gt;")
                    +" </b> - at following locations:<br>\n");

            //Write all violations to file
            for(int j = 0 ; j < violations.getJSONObject(i).getJSONArray("nodes").length(); j++){
                Common.log.debug(violations.getJSONObject(i).getJSONArray("nodes").getJSONObject(j).get("html").toString());
                bufferedWriter.write( violations.getJSONObject(i).getJSONArray("nodes").getJSONObject(j)
                        .get("html").toString().replace("<","&lt;").replace(">", "&gt;") +"<br>\n");
            }
        }
        //Take screenshot
        takeScreenshot();

        //Add screenshot image to file and add previous/next buttons
        bufferedWriter.write(
                "<br><img src=\"" +testData.getTestCase() +".png\" height=\"600\" width=\"600\"> " +
                "<br><br><br> " +timestamp +"\n" +
                "<button><a href = \"WCAG_" +(testData.getCounter() - 1) +".html\"" +"> Previous </a> </button>" +
                "<button><a href = \"WCAG_" +(testData.getCounter() + 1) +".html\"" +"> Next </a> </button>" +
                "</body></html>");
        bufferedWriter.close();
    }

    public Object readJsonFromFile(String filename) throws org.json.simple.parser.ParseException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        Object objects = new JSONObject();

        try (FileReader reader = new FileReader("reports/accessibilityReport.json"))
        {
            objects = jsonParser.parse(reader);
            Common.log.debug("Reading json from file: " + objects);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public void takeScreenshot() throws IOException {
         Shutterbug.shootPage(common.getWebDriver(), Capture.FULL_SCROLL, 500, true).withName(testData.getTestCase())
                .save("reports/" +timestamp.toLocalDate() +"/");
    }
}
